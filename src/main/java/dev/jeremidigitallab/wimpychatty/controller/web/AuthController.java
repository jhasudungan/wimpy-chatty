package dev.jeremidigitallab.wimpychatty.controller.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import dev.jeremidigitallab.wimpychatty.dto.AccountStandardDTO;
import dev.jeremidigitallab.wimpychatty.dto.request.GetAccountInfoRequest;
import dev.jeremidigitallab.wimpychatty.dto.request.RegistrationRequest;
import dev.jeremidigitallab.wimpychatty.dto.request.UpdateAccountInfoRequest;
import dev.jeremidigitallab.wimpychatty.dto.request.UpdatePasswordRequest;
import dev.jeremidigitallab.wimpychatty.exception.RegistrationProcessExeption;
import dev.jeremidigitallab.wimpychatty.service.AccountService;
import dev.jeremidigitallab.wimpychatty.tool.ApplicationTool;

@Controller
@RequestMapping("/auth")
public class AuthController {
	
	@Autowired
	AccountService accountService;
	
	@Autowired
	BCryptPasswordEncoder bCryptPasswordEncoder;

	@GetMapping("/login")
	public String showLoginForm(Model model) {
		return "login";
	}
	
	@GetMapping("/registration")
	public String showRegistrationForm(Model model) {
		
		RegistrationRequest newRegistrationRequest = new RegistrationRequest();
		model.addAttribute("registrationRequest",newRegistrationRequest);
		
		return "registration";
	}
	
	@PostMapping("/registration/submit")
	public String handleRegistrationRequest(@ModelAttribute("registrationRequest") RegistrationRequest registrationRequest) throws RegistrationProcessExeption {
		
		accountService.registerNewAccount(registrationRequest);
		return "redirect:/auth/registration/success";
	}
	
	@GetMapping("/registration/success")
	public String showRegistrationSuccessPage() {
		return "registration-succes";
	}
	
	@GetMapping("/profile")
	public String showAccountInformationPage(Model model) {
		 
		GetAccountInfoRequest request = new GetAccountInfoRequest();
		request.setAccountId(ApplicationTool.loadAccountIdFromSecurityContext());
		
		AccountStandardDTO accountData = accountService.getAccountInfo(request);
		
		// Object binding For form update account information
		UpdateAccountInfoRequest updateAccountInfoRequest = new UpdateAccountInfoRequest();
		updateAccountInfoRequest.setAccountId(accountData.getAccountId());
		updateAccountInfoRequest.setAccountName(accountData.getAccountName());
		updateAccountInfoRequest.setAccountDisplayName(accountData.getAccountDisplayName());
		
		// Object binding For form update password
		UpdatePasswordRequest updatePasswordRequest = new UpdatePasswordRequest();
		
		model.addAttribute("updateAccountInfoRequest", updateAccountInfoRequest);
		model.addAttribute("updatePasswordRequest", updatePasswordRequest);

		return "account-profile"; 
		
	}
	
	@PostMapping("/update-account-information/submit")
	public String handleUpdateAccountInformationRequest(@ModelAttribute("updateAccountInfoRequest") UpdateAccountInfoRequest updateAccountInfoRequest) {
		
		accountService.updateAccountInformation(updateAccountInfoRequest);
		return "redirect:/auth/profile";
	}
	
	@PostMapping("/update-password/submit")
	public String handleUpdatePasswordRequest(@ModelAttribute("updatePasswordRequest") UpdatePasswordRequest updatePasswordRequest) {
		
		updatePasswordRequest.setAccountId(ApplicationTool.loadAccountIdFromSecurityContext());
		accountService.updatePassword(updatePasswordRequest);
		
		return "redirect:/auth/profile";
	}
}
