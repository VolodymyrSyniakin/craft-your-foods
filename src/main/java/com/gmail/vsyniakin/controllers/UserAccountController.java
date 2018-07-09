package com.gmail.vsyniakin.controllers;

import com.gmail.vsyniakin.model.entity.Message;
import com.gmail.vsyniakin.model.entity.Recipe;
import com.gmail.vsyniakin.model.entity.UserAccount;
import com.gmail.vsyniakin.model.entity.UserData;
import com.gmail.vsyniakin.model.enums.RoleUser;
import com.gmail.vsyniakin.services.interfaces.MessageService;
import com.gmail.vsyniakin.services.interfaces.RecipeService;
import com.gmail.vsyniakin.services.interfaces.UserAccountService;
import com.gmail.vsyniakin.services.interfaces.UserDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

@Controller
@SessionAttributes("userAccount")
public class UserAccountController {

	private final int INFO_PAGE = 0;
	private final int RECIPES_PAGE = 1;
	private final int MESSAGES_PAGE = 2;
	private final int MODERATOR_PAGE = 3;
	private final int ADMIN_PAGE = 4;

	@Autowired
	UserDataService userDataService;
	@Autowired
	UserAccountService userAccountService;
	@Autowired
	RecipeService recipeService;
	@Autowired
	MessageService messageService;

	@RequestMapping("/acc")
	public String accInfo(@RequestParam long idu, Model model) {

		model.addAttribute("view", INFO_PAGE);
		User userAuth = null;
		try {
			userAuth = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		} catch (Exception e) {
		}

		UserAccount userAcc = userAccountService.getByIdFetchUserData(idu);
		model.addAttribute("userAcc", userAcc);

		if (userAuth != null) {
			UserData userData = userDataService.getByEmail(userAuth.getUsername());
			if (userData.getId() == userAcc.getUserData().getId() || userData.getRole().equals(RoleUser.ADMIN)) {
				model.addAttribute("edit", true);
			}
		}
		return "/acc";
	}

	@RequestMapping("/acc/recipes")
	public String accRecipes(@RequestParam long idu, Model model) {
		model.addAttribute("view", RECIPES_PAGE);
		model.addAttribute("linkButtons", "/acc/recipes/?idu=" + idu + "&");
		return "forward:/get_recipes_by_user";
	}

	@RequestMapping("/acc/messages")
	public String accMessages(@RequestParam long idu, Model model) {
		model.addAttribute("view", MESSAGES_PAGE);
		model.addAttribute("linkButtons", "/acc/messages/?idu=" + idu + "&");
		return "forward:/get_messages_by_user";
	}

	@RequestMapping("/acc/moderation")
	public String accModerator(@ModelAttribute UserAccount userAccount, Model model) {
		model.addAttribute("view", MODERATOR_PAGE);
		model.addAttribute("userAcc", userAccount);
		model.addAttribute("linkButtons", "/acc/moderation/?");
		return "forward:/get_entities_to_moderation";
	}

	@RequestMapping("/acc/moderation/search_recipe")
	public String accModeratorSearchRecipe(@RequestParam String name, @ModelAttribute UserAccount userAccount,
			ModelMap modelMap) {
		modelMap.addAttribute("pageView", "acc");
		modelMap.addAttribute("view", MODERATOR_PAGE);
		modelMap.addAttribute("userAcc", userAccount);
		modelMap.addAttribute("linkButtons", "/acc/moderation/search_recipe/?name=" + name + "&");
		return "forward:/get_recipes_by_name";
	}

	@RequestMapping("/acc/moderation/search_message")
	public String accModeratorSearchMessage(@RequestParam String text, @ModelAttribute UserAccount userAccount,
			Model model) {
		model.addAttribute("view", MODERATOR_PAGE);
		model.addAttribute("userAcc", userAccount);
		model.addAttribute("linkButtons", "/acc/moderation/search_message/?text=" + text + "&");
		return "forward:/get_messages_by_text";
	}

	@RequestMapping("/acc/admin")
	public String accAdmin(@ModelAttribute UserAccount userAccount, Model model) {
		model.addAttribute("view", ADMIN_PAGE);
		model.addAttribute("userAcc", userAccount);
		return "/acc";
	}

	@RequestMapping("/acc/admin/search_user")
	public String searchUserByLogin(@ModelAttribute UserAccount userAccount, Model model) {
		model.addAttribute("view", ADMIN_PAGE);
		model.addAttribute("userAcc", userAccount);
		return "forward:/get_users_by_login";
	}

	@RequestMapping(value = "/acc/save_info")
	public String updateInfo(@RequestParam String info, @ModelAttribute UserAccount userAccount, Model model) {

		userAccount.setInfo(info);
		userAccountService.update(userAccount);
		model.addAttribute("idu", userAccount.getId());
		return "redirect:/acc";
	}

	@RequestMapping("/acc/moderation/set_moderation")
	public ResponseEntity moderation(@RequestParam String type, @RequestParam long ide, @RequestParam String value) {
		try {
			switch (type) {
			case "RECIPE":
				if (value.equals("TRUE") || value.equals("FALSE")) {
					Recipe recipe = recipeService.getRecipe(ide);
					recipe.setModeration(Boolean.valueOf(value));
					recipeService.update(recipe);
				} else if (value.equals("DELETE")) {
					recipeService.delete(ide);
				}
			case "MESSAGE":
				if (value.equals("TRUE") || value.equals("FALSE")) {
					Message message = messageService.getMessage(ide);
					message.setModeration(Boolean.valueOf(value));
					messageService.update(message);
				} else if (value.equals("DELETE")) {
					messageService.delete(ide);
				}
			}
			return new ResponseEntity(HttpStatus.OK);
		} catch (NullPointerException e) {
			return new ResponseEntity(HttpStatus.NOT_FOUND);
		}
	}

	@RequestMapping("/acc/admin/change_role")
	public ResponseEntity changeRole(@RequestParam long idu, @RequestParam RoleUser role) {

		UserData userData = userDataService.getById(idu);
		if (userData != null) {
			userData.setRole(role);
			userDataService.update(userData);
		}
		return new ResponseEntity(HttpStatus.OK);
	}

	@RequestMapping("/acc/change_password")
	public String changePassword(@RequestParam String pass, @RequestParam String repeat,
			@ModelAttribute UserAccount userAccount) {
		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		UserData userData = userDataService.getByEmail(user.getUsername());
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		if (pass.equals(repeat)) {
			String passHash = encoder.encode(pass);
			userData.setPassword(passHash);
			userDataService.update(userData);
		}
		return "redirect:/logout";
	}

	@RequestMapping("/exc/access_denied")
	public String accessDenied(Model model) {
		model.addAttribute("exc", "denied");
		return "forward:/";
	}

	@RequestMapping("/exc/auth")
	public String notAuthenticated(Model model) {
		model.addAttribute("exc", "auth");
		return "forward:/form_login";
	}
}
