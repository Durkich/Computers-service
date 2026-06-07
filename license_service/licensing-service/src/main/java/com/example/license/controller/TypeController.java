package com.example.license.controller;

import com.example.license.model.Type;
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
@RequestMapping(value = "/types")
public class TypeController {
    @Autowired
    private TypeService typeService;

    @GetMapping
    public String getAllTypes(Model model, @Param("keyword") String keyword, Principal principal) {
        Iterable<Type> types = typeService.findAllTypes(keyword);
        model.addAttribute("types", types);
        model.addAttribute("keyword", keyword);
        model.addAttribute("username", principal.getName());
        return "types";
    }

    @GetMapping(value="/create")
    public String showCreateForm(Model model, Principal principal) {
        model.addAttribute("type", new Type());
        model.addAttribute("username", principal.getName());
        return "create_type";
    }

    @PostMapping(value = "/create")
    public String createType(@ModelAttribute Type request, Model model, Principal principal) {
        typeService.createType(request);
        model.addAttribute("message", "Добавление типа лицензии прошло успешно");
        model.addAttribute("username", principal.getName());
        return "redirect:/types";
    }

    @GetMapping(value="/update/{typeId}/")
    public String showUpdateForm(@PathVariable("typeId") int typeId, Model model, Principal principal) {
        Type type = typeService.getType(typeId);
        if (type != null) {
            model.addAttribute("type", type);
            model.addAttribute("username", principal.getName());
            return "update_type";
        } else {
            model.addAttribute("message", "Тип лицензии с таким ID не найден.");
            return "redirect:/types";
        }
    }

    @PostMapping(value="/update/{typeId}/")
    public String updateType(@PathVariable("typeId") int typeId, @ModelAttribute Type request, Model model, Principal principal) {
        boolean updated = typeService.updateType(typeId, request);
        if (updated) {
            model.addAttribute("message", "Редактирование типа лицензии прошло успешно");
            model.addAttribute("username", principal.getName());
        } else {
            model.addAttribute("message", "Ошибка при редактировании типа лицензии");
        }
        return "redirect:/types";
    }

    @GetMapping(value="/delete/{typeId}")
    public String deleteTypeConfirmation(@PathVariable("typeId") int typeId, ModelMap model, Principal principal) {
        Type type = typeService.getType(typeId);
        model.addAttribute("type", type);
        model.addAttribute("username", principal.getName());
        return "delete_type :: delete-type";
    }

    @PostMapping(value="/delete/{typeId}")
    public ModelAndView deleteType(@PathVariable("typeId") int typeId, ModelMap model, @Param("keyword") String keyword, Principal principal) {
        Type type = typeService.getType(typeId);
        typeService.deleteType(type);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("redirect:/types");
        modelAndView.addObject("types", typeService.findAllTypes(keyword));
        model.addAttribute("username", principal.getName());
        return modelAndView;
    }
}
