package com.example.license.controller;

import com.example.license.model.License;
import com.example.license.model.Type;
import com.example.license.service.LicenseService;
import com.example.license.service.TypeService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;

@Controller
@RequestMapping(value = "/licenses")
public class LicenseController {
    @Autowired
    private LicenseService licenseService;
    @Autowired
    private TypeService typeService;

    @GetMapping
    public String getAllLicenses(Model model, @Param("keyword") String keyword, Principal principal) {
        Iterable<License> licenses = licenseService.findAllLicenses(keyword);
        Iterable<Type> types = typeService.findAllTypes(keyword);
        model.addAttribute("licenses", licenses);
        model.addAttribute("types", types);
        model.addAttribute("keyword", keyword);
        model.addAttribute("username", principal.getName());
        return "licenses";
    }

    @GetMapping(value="/create")
    public String showCreateForm(Model model, @Param("keyword") String keyword, Principal principal) {
        model.addAttribute("license", new License());
        model.addAttribute("types", typeService.findAllTypes(keyword));
        model.addAttribute("keyword", keyword);
        model.addAttribute("username", principal.getName());
        return "create_license";
    }

    @PostMapping(value = "/create")
    public String createLicense(@ModelAttribute License request, Model model, Principal principal) {
        licenseService.createLicense(request);
        model.addAttribute("message", "Добавление лицензии прошло успешно");
        model.addAttribute("username", principal.getName());
        return "redirect:/licenses";
    }

    @GetMapping(value="/update/{licenseId}/")
    public String showUpdateForm(@PathVariable("licenseId") int licenseId, Model model, @Param("keyword") String keyword, Principal principal) {
        License license = licenseService.getLicense(licenseId);
        //Iterable<Type> types = typeService.findAllTypes(keyword);
        if (license != null) {
            model.addAttribute("license", license);
            model.addAttribute("types", typeService.findAllTypes(keyword));
            model.addAttribute("keyword", keyword);
            model.addAttribute("username", principal.getName());
            return "update_license";
        } else {
            model.addAttribute("message", "Лицензия с таким ID не найдена");
            return "redirect:/licenses";
        }
    }

    @PostMapping(value="/update/{licenseId}/")
    public String updateLicense(@PathVariable("licenseId") int licenseId, @ModelAttribute License request, Model model, Principal principal) {
        boolean updated = licenseService.updateLicense(licenseId, request);
        if (updated) {
            model.addAttribute("message", "Редактирование лицензии прошло успешно");
            model.addAttribute("username", principal.getName());
        } else {
            model.addAttribute("message", "Ошибка при редактировании лицензии");
        }
        return "redirect:/licenses";
    }

    @GetMapping(value="/delete/{licenseId}")
    public String deleteLicenseConfirmation(@PathVariable("licenseId") int licenseId, ModelMap model, Principal principal) {
        License license = licenseService.getLicense(licenseId);
        model.addAttribute("license", license);
        model.addAttribute("username", principal.getName());
        return "delete_license :: delete-license";
    }

    @PostMapping(value="/delete/{licenseId}")
    public ModelAndView deleteLicense(@PathVariable("licenseId") int licenseId, ModelMap model, @Param("keyword") String keyword, Principal principal) {
        License license = licenseService.getLicense(licenseId);
        licenseService.deleteLicense(license);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("redirect:/licenses");
        modelAndView.addObject("licenses", licenseService.findAllLicenses(keyword));
        model.addAttribute("username", principal.getName());
        return modelAndView;
    }
}
