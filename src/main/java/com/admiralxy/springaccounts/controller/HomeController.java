package com.admiralxy.springaccounts.controller;

import com.admiralxy.springaccounts.entity.Credential;
import com.admiralxy.springaccounts.service.interfaces.ICredentialService;
import com.admiralxy.springaccounts.util.AuthUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class HomeController {

    private final ICredentialService credentialService;
    private final AuthUtils authUtils;

    @Autowired
    public HomeController(ICredentialService credentialService, AuthUtils authUtils) {
        this.credentialService = credentialService;
        this.authUtils = authUtils;
    }

    @GetMapping("/")
    public String index(Model model) {
        List<Credential> credentials = credentialService.getAllByUser(authUtils.getCurrentUser());
        model.addAttribute("credentials", credentials);
        if (!model.containsAttribute("credential")) {
            model.addAttribute("credential", new Credential());
        }
        return "index";
    }
}
