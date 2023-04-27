package dev.jeremidigitallab.wimpychatty.controller.web;

import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import dev.jeremidigitallab.wimpychatty.dto.request.AddNewFriendRequest;
import dev.jeremidigitallab.wimpychatty.dto.request.GetRoomChatInformationRequest;
import dev.jeremidigitallab.wimpychatty.dto.request.GetListOfFriendRequest;
import dev.jeremidigitallab.wimpychatty.dto.request.SearchAccountByAccountNameRequest;
import dev.jeremidigitallab.wimpychatty.service.AccountService;
import dev.jeremidigitallab.wimpychatty.tool.ApplicationTool;

@Controller
@RequestMapping("/account")
public class AccountController {

	@Autowired
	AccountService accountService;
	
	@GetMapping
	public String showAccountDashboard(@RequestParam("friend-page") Integer friendPage, Model model) throws SQLException {

		GetListOfFriendRequest getListOfFriendRequest = new GetListOfFriendRequest();
		getListOfFriendRequest.setAccountId(ApplicationTool.loadAccountIdFromSecurityContext());
		getListOfFriendRequest.setPage(friendPage);
		
		Map<String, Object> pageData = new LinkedHashMap<>();
		pageData.put("listOfFriend", accountService.getListOfFriend(getListOfFriendRequest));
		
		model.addAttribute("pageData",pageData);
		model.addAttribute("searchAccountByAccountNameRequest", new SearchAccountByAccountNameRequest());
		
		return "account-dashboard";

	}
	
	@GetMapping("/chat/{connectedId}")
	public String openChatRoom(@PathVariable("connectedId") String connectedId, Model model) {
	
		GetRoomChatInformationRequest getRoomChatInformationRequest = new GetRoomChatInformationRequest();
		getRoomChatInformationRequest.setAccountId(ApplicationTool.loadAccountIdFromSecurityContext());
		getRoomChatInformationRequest.setConnectedId(connectedId);
		
		Map<String, Object> pageData = new LinkedHashMap<>();
		
		pageData.put("roomChatInformation", accountService.getChatSenderReceiverInformation(getRoomChatInformationRequest));
		
		model.addAttribute("pageData",pageData);
		
		return "chat-room";
	}
	
	@GetMapping("/search-new-friend")
	public String showSearchNewFriendPage(Model model) {
		
		SearchAccountByAccountNameRequest searchAccountByAccountNameRequest = new SearchAccountByAccountNameRequest();
		model.addAttribute("searchAccountByAccountNameRequest", searchAccountByAccountNameRequest);
		return "search-new-friend";
	}
	
	@PostMapping("/search-new-friend/submit")
	public String handleSearchNewFriendRequest(@ModelAttribute("searchAccountByAccountNameRequest") SearchAccountByAccountNameRequest searchAccountByAccountNameRequest,Model model) throws SQLException {
		
		searchAccountByAccountNameRequest.setRequestorAccountId(ApplicationTool.loadAccountIdFromSecurityContext());

		Map<String, Object> pageData = new LinkedHashMap<>();
		pageData.put("searchResult", accountService.searchAccountByAccountName(searchAccountByAccountNameRequest));
		
		SearchAccountByAccountNameRequest newSearchAccountByAccountNameRequest = new SearchAccountByAccountNameRequest();
		model.addAttribute("searchAccountByAccountNameRequest", newSearchAccountByAccountNameRequest);	
		model.addAttribute("pageData",pageData);
		
		return "search-new-friend-result";
	}
	
	@PostMapping("/search-friend/submit")
	public String handleSearchFriendRequest(@ModelAttribute("searchAccountByAccountNameRequest") SearchAccountByAccountNameRequest searchAccountByAccountNameRequest,Model model) throws SQLException {
		
		searchAccountByAccountNameRequest.setRequestorAccountId(ApplicationTool.loadAccountIdFromSecurityContext());
		
		Map<String, Object> pageData = new LinkedHashMap<>();
		pageData.put("listOfFriend", accountService.searchConnectedAccount(searchAccountByAccountNameRequest));
		
		SearchAccountByAccountNameRequest newSearchAccountByAccountNameRequest = new SearchAccountByAccountNameRequest();
		
		model.addAttribute("searchAccountByAccountNameRequest", newSearchAccountByAccountNameRequest);	
		model.addAttribute("pageData",pageData);
		
		return "search-friend-result";
	}
	
	@GetMapping("/add/{targetId}")
	public String handleAddFriendRequest(@PathVariable("targetId") String targetId) {
		
		String sourceId = ApplicationTool.loadAccountIdFromSecurityContext();
		
		AddNewFriendRequest addNewFriendRequest = new AddNewFriendRequest();
		addNewFriendRequest.setSourceId(sourceId);
		addNewFriendRequest.setTargetId(targetId);
		
		accountService.addnewFriend(addNewFriendRequest);
		
		return "redirect:/account?friend-page=1";
	}
	
}
