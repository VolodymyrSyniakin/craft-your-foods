package com.gmail.vsyniakin.controllers;

import com.gmail.vsyniakin.model.entity.UserAccount;
import com.gmail.vsyniakin.model.entity.UserData;
import com.gmail.vsyniakin.model.enums.RoleUser;
import com.gmail.vsyniakin.services.interfaces.UserAccountService;
import com.gmail.vsyniakin.services.interfaces.UserDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;


@Controller
@SessionAttributes("userAccount")
public class AuthenticationController {

    @Autowired
    UserDataService userDataService;

    @Autowired
    UserAccountService userAccountService;

    @RequestMapping("/form_login")
    public String showFormLogin(Model model) {
        model.addAttribute("formAuth", "login");
        return "forward:/";
    }

    @RequestMapping("/success")
    public String successAuth(HttpSession session) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String email = user.getUsername();

        UserAccount userAccount = userDataService.getByEmail(email).getUserAccount();
        session.setAttribute("userAccount", userAccount);

        return "forward:/";
    }

    @RequestMapping("/registration_submit")
    public String registration(@RequestParam String email,
    							@RequestParam String login, 
    							@RequestParam String password, Model model) {
        if (userDataService.existsByEmail(email)) {
            model.addAttribute("formAuth", "registration");
            model.addAttribute("exists", "email");
            return "forward:/";
        } else if (userAccountService.existByLogin(login)){
            model.addAttribute("formAuth", "registration");
            model.addAttribute("exists", "login");
            return "forward:/";
        } else {
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            String passHash = encoder.encode(password);
            UserData userData = new UserData(email, passHash, RoleUser.USER);
            userData.addUser(new UserAccount(login));
            userDataService.add(userData);
            return "forward:/";
        }
    }

    @RequestMapping("/access_fail")
    public String failureAccess (Model model) {
        model.addAttribute("exc", "noAuth");
        return "forward:/form_login";
    }

}
