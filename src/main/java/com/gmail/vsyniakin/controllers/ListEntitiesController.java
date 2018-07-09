package com.gmail.vsyniakin.controllers;

import com.gmail.vsyniakin.model.ViewModerationObject;
import com.gmail.vsyniakin.model.entity.Ingredient;
import com.gmail.vsyniakin.model.entity.Message;
import com.gmail.vsyniakin.model.entity.Recipe;
import com.gmail.vsyniakin.model.entity.UserAccount;
import com.gmail.vsyniakin.model.enums.DifficultyRecipe;
import com.gmail.vsyniakin.model.enums.TypeRecipe;
import com.gmail.vsyniakin.services.interfaces.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@SessionAttributes("userAccount")
public class ListEntitiesController {

	private final int COUNT_RECIPES_IN_PAGE = 9;
	private final int COUNT_MESSAGES_IN_PAGE = 15;
	private final int COUNT_ENTITIES_IN_PAGE = 15;

	@Autowired
	private RecipeService recipeService;
	@Autowired
	private MessageService messageService;
	@Autowired
	private UserAccountService userAccountService;

	@RequestMapping("/get_recipes")
	public String getRecipes(@RequestParam(required = false, defaultValue = "0") int page,
			@RequestAttribute String pageView, Model model) {

		List<Recipe> recipes = recipeService.getRecipesDescByRating(0.0, page * COUNT_RECIPES_IN_PAGE,
				COUNT_RECIPES_IN_PAGE);
		model.addAttribute("recipes", recipes);
		int countOfRecipes = recipeService.count();
		int countOfPages = countOfRecipes / COUNT_RECIPES_IN_PAGE
				+ (((countOfRecipes % COUNT_RECIPES_IN_PAGE) == 0) ? 0 : 1);
		model.addAttribute("countOfPages", countOfPages);
		model.addAttribute("page", page);
		model.addAttribute("first", getFirstOfButtonGroup(page, countOfPages));
		model.addAttribute("last", getLastOfButtonGroup(page, countOfPages));
		return pageView;
	}

	@RequestMapping("/get_recipes_by_name")
	public String getRecipesByName(@RequestParam(required = false, defaultValue = "0") int page,
			@RequestParam String name, @RequestAttribute String pageView, Model model) {

		int countOfRecipes = recipeService.count(name);
		int countOfPages = countOfRecipes / COUNT_ENTITIES_IN_PAGE
				+ (((countOfRecipes % COUNT_ENTITIES_IN_PAGE) == 0) ? 0 : 1);

		model.addAttribute("countOfPages", countOfPages);
		model.addAttribute("page", page);
		model.addAttribute("first", getFirstOfButtonGroup(page, countOfPages));
		model.addAttribute("last", getLastOfButtonGroup(page, countOfPages));

		List<ViewModerationObject> viewModerationObjects = new ArrayList<>();
		List<Recipe> recipes = recipeService.getRecipesByName(name, page * COUNT_ENTITIES_IN_PAGE,
				COUNT_ENTITIES_IN_PAGE);
		if (pageView.equals("acc")) {
			if (recipes != null) {
				ViewModerationObject.convertRecipesToListViewModeration(recipes, viewModerationObjects);
			}
			model.addAttribute("viewObjects", viewModerationObjects);
		} else {
			model.addAttribute("recipes", recipes);
		}
		return pageView;
	}

	@RequestMapping("/get_by_parameters")
	public String getRecipesByParameters(@RequestAttribute(required = false) TypeRecipe type,
			@RequestAttribute(required = false) DifficultyRecipe difficulty,
			@RequestParam(required = false, defaultValue = "0") double rating,
			@RequestParam(required = false, defaultValue = "0") int timeMin,
			@RequestAttribute(required = false) List<Ingredient> ingredients,
			@RequestParam(required = false, defaultValue = "0") int page, Model model) {

		List<Recipe> recipes = recipeService.getRecipesByParameters(type, difficulty, rating, timeMin, ingredients,
				page * COUNT_RECIPES_IN_PAGE, COUNT_RECIPES_IN_PAGE);
		model.addAttribute("recipes", recipes);

		int countOfRecipes = recipeService.count(type, difficulty, rating, timeMin, ingredients);
		int countOfPages = countOfRecipes / COUNT_RECIPES_IN_PAGE
				+ (((countOfRecipes % COUNT_RECIPES_IN_PAGE) == 0) ? 0 : 1);
		model.addAttribute("countOfPages", countOfPages);
		model.addAttribute("page", page);
		model.addAttribute("first", getFirstOfButtonGroup(page, countOfPages));
		model.addAttribute("last", getLastOfButtonGroup(page, countOfPages));
		return "recipes";
	}

	@RequestMapping("/get_recipes_by_user")
	public String getRecipesByUser(@RequestParam(required = false, defaultValue = "0") int page, @RequestParam long idu,
			Model model) {

		UserAccount userAcc = userAccountService.getReference(idu);
		model.addAttribute("userAcc", userAcc);
		int countOfRecipes = recipeService.count(userAcc);
		int countOfPages = countOfRecipes / COUNT_RECIPES_IN_PAGE
				+ (((countOfRecipes % COUNT_RECIPES_IN_PAGE) == 0) ? 0 : 1);
		model.addAttribute("countOfPages", countOfPages);
		model.addAttribute("page", page);
		model.addAttribute("first", getFirstOfButtonGroup(page, countOfPages));
		model.addAttribute("last", getLastOfButtonGroup(page, countOfPages));
		List<Recipe> recipes = recipeService.getRecipesByUserAccount(userAcc, page * COUNT_RECIPES_IN_PAGE,
				COUNT_RECIPES_IN_PAGE);
		model.addAttribute("recipes", recipes);
		return "acc";
	}

	@RequestMapping("/get_messages_by_user")
	public String getMessagesByUser(@RequestParam long idu,
			@RequestParam(required = false, defaultValue = "0") int page, Model model) {
		UserAccount userAcc = userAccountService.getReference(idu);
		model.addAttribute("userAcc", userAcc);
		int countOfMessages = messageService.count(userAcc);
		int countOfPages = countOfMessages / COUNT_MESSAGES_IN_PAGE
				+ (((countOfMessages % COUNT_MESSAGES_IN_PAGE) == 0) ? 0 : 1);
		model.addAttribute("countOfPages", countOfPages);
		model.addAttribute("page", page);
		model.addAttribute("first", getFirstOfButtonGroup(page, countOfPages));
		model.addAttribute("last", getLastOfButtonGroup(page, countOfPages));
		List<Message> messages = messageService.getMessagesByUserAccountFetchRecipeAndSortByDate(userAcc,
				page * COUNT_MESSAGES_IN_PAGE, COUNT_MESSAGES_IN_PAGE);
		model.addAttribute("messages", messages);
		return "acc";
	}

	@RequestMapping("/get_messages_by_text")
	public String getMessageByText(@RequestParam(required = false, defaultValue = "0") int page,
			@RequestParam String text, Model model) {

		int countOfMessages = messageService.count(text);
		int countOfPages = countOfMessages / COUNT_ENTITIES_IN_PAGE
				+ (((countOfMessages % COUNT_ENTITIES_IN_PAGE) == 0) ? 0 : 1);
		model.addAttribute("countOfPages", countOfPages);
		model.addAttribute("page", page);
		model.addAttribute("first", getFirstOfButtonGroup(page, countOfPages));
		model.addAttribute("last", getLastOfButtonGroup(page, countOfPages));
		List<ViewModerationObject> viewModerationObjects = new ArrayList<>();
		List<Message> messages = messageService.getMessageByText(text, page * COUNT_ENTITIES_IN_PAGE,
				COUNT_ENTITIES_IN_PAGE);
		if (messages != null) {
			ViewModerationObject.convertMessagesToListViewModeration(messages, viewModerationObjects);
		}
		model.addAttribute("viewObjects", viewModerationObjects);
		return "acc";
	}

	@RequestMapping("/get_users_by_login")
	public String getUsersByLogin(@RequestParam String log, Model model) {

		List<UserAccount> userAccounts = userAccountService.getByLoginFetchUserData(log);
		model.addAttribute("userAccounts", userAccounts);

		return "acc";
	}

	@RequestMapping("/get_entities_to_moderation")
	public String getEntitiesToModeration(@RequestParam(required = false, defaultValue = "0") int page, Model model) {

		int countOfRecipes = recipeService.count(false);

		int countOfEntities = messageService.count(false) + recipeService.count(false);
		int countOfPages = countOfEntities / COUNT_ENTITIES_IN_PAGE
				+ (((countOfEntities % COUNT_ENTITIES_IN_PAGE) == 0) ? 0 : 1);

		model.addAttribute("countOfPages", countOfPages);
		model.addAttribute("page", page);
		model.addAttribute("first", getFirstOfButtonGroup(page, countOfPages));
		model.addAttribute("last", getLastOfButtonGroup(page, countOfPages));

		List<ViewModerationObject> viewModerationObjects = new ArrayList<>();

		if (page * COUNT_ENTITIES_IN_PAGE < countOfRecipes) {
			List<Recipe> recipes = recipeService.getRecipesByModeration(false, page * COUNT_ENTITIES_IN_PAGE,
					COUNT_ENTITIES_IN_PAGE);
			if (recipes != null) {
				ViewModerationObject.convertRecipesToListViewModeration(recipes, viewModerationObjects);
			}
			if (recipes.size() < COUNT_ENTITIES_IN_PAGE) {
				List<Message> messages = messageService.getMessagesByModeration(false,
						(page * COUNT_ENTITIES_IN_PAGE - countOfRecipes), (COUNT_ENTITIES_IN_PAGE - recipes.size()));
				if (messages != null) {
					ViewModerationObject.convertMessagesToListViewModeration(messages, viewModerationObjects);
				}
			}
		} else {
			List<Message> messages = messageService.getMessagesByModeration(false,
					(page * COUNT_ENTITIES_IN_PAGE - countOfRecipes), COUNT_ENTITIES_IN_PAGE);
			if (messages != null) {
				ViewModerationObject.convertMessagesToListViewModeration(messages, viewModerationObjects);
			}
		}
		model.addAttribute("viewObjects", viewModerationObjects);
		return "acc";
	}

	private int getFirstOfButtonGroup(int active, int max) {
		if ((max <= 10) || ((active - 5) <= 0)) {
			return 0;
		} else if (max <= (active + 5)) {
			return (max - 10);
		} else {
			return (active - 4);
		}
	}

	private int getLastOfButtonGroup(int active, int max) {
		if ((max <= 10) || (max <= (active + 5))) {
			return max - 1;
		} else if ((active - 5) <= 0) {
			return 9;
		} else {
			return (active + 4);
		}
	}

}
