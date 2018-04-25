package com.gmail.vsyniakin.controllers;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gmail.vsyniakin.model.entity.*;

import com.gmail.vsyniakin.model.enums.*;

import com.gmail.vsyniakin.services.interfaces.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.xml.bind.DatatypeConverter;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@SessionAttributes("userAccount")
public class RecipeController {

	private final static int VIEW_EDIT_RECIPE_PAGE = 0;
	private final static int VIEW_NEW_RECIPE_PAGE = 1;

	@Autowired
	private RecipeService recipeService;
	@Autowired
	private StepService stepService;
	@Autowired
	private IngredientByRecipeService ingredientByRecipeService;
	@Autowired
	private UserAccountService userAccountService;
	@Autowired
	private RatingFromUserService ratingFromUserService;
	@Autowired
	private IngredientService ingredientService;

	@RequestMapping("/")
	public String index(ModelMap model) {
		model.addAttribute("pageView", "index");
		return "forward:/get_recipes";
	}

	@RequestMapping("/recipe")
	public String recipe(@RequestParam long idr, HttpSession session, Model model) {
		Recipe recipe = recipeService.getRecipeByIdFetchIngrStepsAcc(idr);
		model.addAttribute("recipe", recipe);

		UserAccount userAccount = (UserAccount) session.getAttribute("userAccount");
		if (userAccount != null) {
			if (userAccount.getId() == recipe.getUserAccount().getId()
					|| userAccount.getUserData().getRole().equals(RoleUser.MODERATOR)
					|| userAccount.getUserData().getRole().equals(RoleUser.ADMIN)) {
				model.addAttribute("edit", true);
			} else {
				model.addAttribute("edit", false);
			}
		} else {
			model.addAttribute("edit", false);
		}
		return "recipe";
	}

	@RequestMapping("/add_recipe")
	public String addRecipe(Model model) {
		model.addAttribute("view", VIEW_NEW_RECIPE_PAGE);

		model.addAttribute("difficultyRecipe", DifficultyRecipe.toMap());
		model.addAttribute("typeIngredient", TypeIngredient.toMap());
		model.addAttribute("typeRecipe", TypeRecipe.toMap());
		model.addAttribute("importantIngredient", ImportantIngredient.toMap());

		return "edit_recipe";
	}

	@RequestMapping("/recipe/edit_recipe")
	public String editRecipe(@RequestParam long idr, @ModelAttribute UserAccount userAccount, Model model) {
		model.addAttribute("view", VIEW_EDIT_RECIPE_PAGE);

		model.addAttribute("difficultyRecipe", DifficultyRecipe.toMap());
		model.addAttribute("typeIngredient", TypeIngredient.toMap());
		model.addAttribute("typeRecipe", TypeRecipe.toMap());
		model.addAttribute("importantIngredient", ImportantIngredient.toMap());

		Recipe recipe = recipeService.getRecipeByIdFetchIngrStepsAcc(idr);
		if (userAccount.getId() == recipe.getUserAccount().getId()
				|| userAccount.getUserData().getRole().equals(RoleUser.ADMIN)
				|| userAccount.getUserData().getRole().equals(RoleUser.MODERATOR)) {
			model.addAttribute("recipe", recipe);
			return "edit_recipe";
		} else {
			return "redirect:/exc/access_denied";
		}
	}

	@RequestMapping(value = "/recipe/delete", method = RequestMethod.GET)
	public String deleteRecipe(@RequestParam long idr, @ModelAttribute UserAccount userAccount) {
		Recipe recipe = recipeService.getRecipeByIdFetchIngrStepsAcc(idr);
		if (recipe.getUserAccount().getId() == userAccount.getId()
				|| userAccount.getUserData().getRole().equals(RoleUser.ADMIN)
				|| userAccount.getUserData().getRole().equals(RoleUser.MODERATOR)) {
			recipeService.delete(idr);
			return "redirect:/";
		} else {
			return "redirect:/exc/access_denied";
		}
	}

	@RequestMapping("/recipes/search_recipe")
	public String searchRecipeByName(@RequestParam String name, ModelMap modelMap) {
		modelMap.addAttribute("pageView", "recipes");
		modelMap.addAllAttributes(mapAttributesInSearchMenu(null, null, 0.0, 0, null));
		modelMap.addAttribute("linkButtons", "/recipes/?name=" + name + "&");
		return "forward:/get_recipes_by_name";
	}

	@RequestMapping("/recipes")
	public String recipes(ModelMap modelMap) {
		modelMap.addAttribute("pageView", "recipes");
		modelMap.addAllAttributes(mapAttributesInSearchMenu(null, null, 0.0, 0, null));
		modelMap.addAttribute("linkButtons", "/recipes/?");
		return "forward:/get_recipes";
	}

	@RequestMapping("/last_recipes")
	public String getLastRecipes(Model model) {
		model.addAttribute("lastRecipes", recipeService.getRecipesDescByDate(0, 4));
		return "last_recipes";
	}

	@RequestMapping("/recipes/parameters")
	public String recipesByParameters(@RequestParam(required = false) String type,
			@RequestParam(required = false) String difficulty,
			@RequestParam(required = false, defaultValue = "0") double rating,
			@RequestParam(required = false, defaultValue = "0") int timeMin, @RequestParam(required = false) long[] idi,
			ModelMap modelMap) {
		TypeRecipe typeRecipe = null;
		DifficultyRecipe difficultyRecipe = null;
		try {
			if (!type.isEmpty()) {
				typeRecipe = TypeRecipe.valueOf(type);
			}

			if (!difficulty.isEmpty()) {
				difficultyRecipe = DifficultyRecipe.valueOf(difficulty);
			}
		} catch (IllegalArgumentException e) {
		}

		List<Ingredient> ingredients = null;
		if (idi != null) {
			ingredients = ingredientService.getIngredientsById(idi);
		}
		modelMap.addAllAttributes(
				mapAttributesInSearchMenu(typeRecipe, difficultyRecipe, rating, timeMin, ingredients));
		modelMap.addAttribute("linkButtons", createLinkButtons(type, difficulty, rating, timeMin, idi));
		return "forward:/get_by_parameters";
	}

	private Map<String, Object> mapAttributesInSearchMenu(TypeRecipe type, DifficultyRecipe difficulty, double rating,
			int timeMin, List<Ingredient> ingredients) {
		Map<String, Object> attributes = new HashMap<>();
		attributes.put("difficultyRecipe", DifficultyRecipe.toMap());
		attributes.put("typeRecipe", TypeRecipe.toMap());
		attributes.put("type", type);
		attributes.put("difficulty", difficulty);
		attributes.put("rating", rating);
		attributes.put("timeMin", timeMin);
		attributes.put("ingredients", ingredients);
		return attributes;
	}

	private String createLinkButtons(String type, String difficulty, double rating, int timeMin, long[] idi) {
		StringBuilder linkButtons = new StringBuilder("/recipes/parameters/?");
		linkButtons.append("type=" + type + "&");
		linkButtons.append("difficulty=" + difficulty + "&");
		linkButtons.append("rating=" + rating + "&");
		linkButtons.append("timeMin=" + timeMin + "&");

		if (idi != null) {
			for (long id : idi) {
				linkButtons.append("idi=" + id + "&");
			}
		}
		return linkButtons.toString();
	}

	@RequestMapping("/add_value_rating")
	public ResponseEntity<String> addValueRating(@RequestParam long idr, @RequestParam int value,
			@ModelAttribute UserAccount userAccount) {

		Recipe recipe = recipeService.getReference(idr);

		RatingFromUser ratingFromUser = ratingFromUserService.searchByRecipeAndUser(recipe, userAccount);
		if (ratingFromUser == null) {
			userAccount = userAccountService.getByIdFetchRatingFromUser(userAccount.getId());
			recipe = recipeService.getByIdFetchRatingFromUsers(idr);
			ratingFromUser = RatingFromUser.add(value, recipe, userAccount);
			recipe.updateRating();
			ratingFromUserService.add(ratingFromUser);
		} else {
			ratingFromUser.setValue(value);
			ratingFromUserService.update(ratingFromUser);
			recipe = recipeService.getByIdFetchRatingFromUsers(idr);
			recipe.updateRating();
			recipeService.update(recipe);
		}

		HttpHeaders respHeaders = new HttpHeaders();
		respHeaders.add("id_recipe", String.valueOf(idr));
		respHeaders.add("rating_value", String.valueOf(recipe.getRating()));

		return new ResponseEntity<String>("", respHeaders, HttpStatus.OK);
	}

	@RequestMapping(value = "/submit_recipe", method = RequestMethod.POST)
	@ResponseBody
	public String addRecipe(@RequestParam String strRecipe, @RequestParam String encodeImage,
			@ModelAttribute UserAccount userAccount) {
		try {
			Recipe recipe = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
					.readValue(strRecipe, Recipe.class);
			recipe.setDate(new Date());
			recipe.addLinksToEntities(userAccountService.getByIdFetchRecipes(userAccount.getId()),
					convertStringToImage(encodeImage));
			recipe = recipeService.add(recipe);

			return "/recipe/?idr=" + recipe.getId();
		} catch (Exception e) {
			return "/exc/parse";
		}
	}

	@RequestMapping(value = "/update_recipe", method = RequestMethod.POST)
	@ResponseBody
	public String updateRecipe(@RequestParam String strRecipe, @RequestParam String encodeImage, @RequestParam long idr,
			@ModelAttribute UserAccount userAccount) {

		try {
			Recipe updateRecipe = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
					.readValue(strRecipe, Recipe.class);
			deleteStepsAndIngredientByRecipe(recipeService.getRecipeByIdFetchIngrStepsAcc(idr), updateRecipe);
			Image image = convertStringToImage(encodeImage);
			if (image == null) {
				image = recipeService.getByIdFetchImage(idr).getImage();
			}
			updateRecipe.addLinksToEntities(userAccountService.getByIdFetchRecipes(userAccount.getId()), image);
			updateRecipe = recipeService.update(updateRecipe);
			return "/recipe/?idr=" + updateRecipe.getId();
		} catch (Exception e) {
			return "/exc/parse";
		}
	}

	@RequestMapping("/exc/parse")
	public String parseRecipeExc(Model model) {
		model.addAttribute("exc", "parse");
		return "forward:/";
	}

	private void deleteStepsAndIngredientByRecipe(Recipe recipe, Recipe updateRecipe) {
		boolean deleteStatus;
		for (Step step : recipe.getSteps()) {
			deleteStatus = true;
			for (Step stepUpdate : updateRecipe.getSteps()) {
				try {
					if (step.getId() == stepUpdate.getId()) {
						deleteStatus = false;
					}
				} catch (NullPointerException e) {
				}
			}
			if (deleteStatus) {
				stepService.delete(step.getId());
			}
		}
		for (IngredientByRecipe ingredientByRecipe : recipe.getIngredientsByRecipe()) {
			deleteStatus = true;
			for (IngredientByRecipe ingredientByRecipeUpdate : updateRecipe.getIngredientsByRecipe()) {
				try {
					if (ingredientByRecipe.getId() == ingredientByRecipeUpdate.getId()) {
						deleteStatus = false;
					}
				} catch (NullPointerException e) {
				}
			}
			if (deleteStatus) {
				ingredientByRecipeService.delete(ingredientByRecipe.getId());
			}
		}
	}

	private Image convertStringToImage(String encodeImage) {
		if (encodeImage.contains("data:image/png;base64")) {
			byte[] image = DatatypeConverter.parseBase64Binary(encodeImage.split(",")[1]);
			return new Image(image);
		} else {
			return null;
		}
	}

}
