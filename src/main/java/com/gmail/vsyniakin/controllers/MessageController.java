package com.gmail.vsyniakin.controllers;

import com.fasterxml.jackson.annotation.JsonView;
import com.gmail.vsyniakin.model.Views;
import com.gmail.vsyniakin.model.entity.Message;
import com.gmail.vsyniakin.model.entity.Recipe;
import com.gmail.vsyniakin.model.entity.UserAccount;
import com.gmail.vsyniakin.services.interfaces.MessageService;
import com.gmail.vsyniakin.services.interfaces.RecipeService;
import com.gmail.vsyniakin.services.interfaces.UserAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@SessionAttributes("userAccount")
public class MessageController {

	private static final int MESSAGES_IN_PAGE = 7;

	@Autowired
	private MessageService messageService;
	@Autowired
	private RecipeService recipeService;
	@Autowired
	private UserAccountService userAccountService;

	@RequestMapping("/send_message")
	public String addMessage(@RequestParam(required = false) String text, @RequestParam long idr,
			@ModelAttribute UserAccount userAccount) {
		if (!text.isEmpty()) {
			Recipe recipe = recipeService.getByIdFetchMessages(idr);
			Message message = Message.addMessage(text, userAccountService.getByIdFetchMessages(userAccount.getId()),
					recipe);
			messageService.add(message);
			return "redirect:/recipe/?idr=" + idr;
		} else {
			throw new IllegalArgumentException();
		}
	}

	@RequestMapping(value = "/get_messages")
	@JsonView(Views.Message.class)
	public ResponseEntity<List<Message>> getMessagesByRecipe(
			@RequestParam(required = false, defaultValue = "0") int page, @RequestParam long idr) {
		int start = page * MESSAGES_IN_PAGE;
		Recipe recipe = recipeService.getReference(idr);
		long countMessages = messageService.count(recipe);
		if (countMessages > start) {
			List<Message> messages = messageService.getMessagesByRecipeFetchUserAccountAndSortByDate(recipe, start,
					MESSAGES_IN_PAGE);
			long countPages = (countMessages / MESSAGES_IN_PAGE) + ((countMessages % MESSAGES_IN_PAGE > 0) ? 1 : 0);
			HttpHeaders respHeaders = new HttpHeaders();
			respHeaders.add("count_pages", String.valueOf(countPages));
			respHeaders.add("active_page", String.valueOf(page));
			return new ResponseEntity<List<Message>>(messages, respHeaders, HttpStatus.OK);
		}
		return new ResponseEntity<List<Message>>(HttpStatus.OK);
	}
}
