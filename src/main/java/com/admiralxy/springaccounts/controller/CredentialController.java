package com.admiralxy.springaccounts.controller;

import com.admiralxy.springaccounts.entity.Credential;
import com.admiralxy.springaccounts.service.interfaces.ICredentialService;
import com.admiralxy.springaccounts.service.interfaces.IUserService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping("/credentials")
public class CredentialController {
    private final ICredentialService credentialService;
    private final IUserService userService;

    @Autowired
    public CredentialController(ICredentialService credentialService, IUserService userService) {
        this.credentialService = credentialService;
        this.userService = userService;
    }

    @GetMapping("/check/{id}")
    public String security(@PathVariable Long id) throws Exception {
        Credential credential = credentialService.findById(id, null);
        if (credential == null)
            throw new NotFoundException("Credential not found!");
        return "securityCredentials";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable Long id, @RequestParam String key, RedirectAttributes redirectAttributes) throws Exception {
        Credential credential = credentialService.findById(id, key);
        if (credential == null) {
            throw new NotFoundException("Credential not found!");
        } else {
            redirectAttributes.addFlashAttribute("credentialShow", credential);
        }
        return "redirect:/";
    }

    @GetMapping("/share/{id}")
    public String showShare(@PathVariable Long id, @RequestParam String key, Model model) throws Exception {
        Credential credential = credentialService.findById(id, key);
        if (credential == null)
            throw new NotFoundException("Credential not found!");
        model.addAttribute("credential", credential);
        return "shareCredentials";
    }

    @PostMapping
    public String store(@ModelAttribute("credential") @Valid Credential credential, BindingResult bindingResult, RedirectAttributes redirectAttributes) throws Exception {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.credential", bindingResult);
            redirectAttributes.addFlashAttribute("credential", credential);
            return "redirect:/?show=addAccountModal";
        }
        credentialService.save(credential);
        return "redirect:/";
    }
}
