package com.Kalabekov.Computersservice.controller;

import com.Kalabekov.Computersservice.model.Laptop;
import com.Kalabekov.Computersservice.service.LaptopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/laptops")
public class LaptopController {

    @Autowired
    private LaptopService laptopService;

    @GetMapping("/{laptopId}")
    public String getLaptop(@PathVariable("laptopId") int laptopId, Model model) {
        Laptop laptop = laptopService.getLaptop(laptopId);
        model.addAttribute("laptop", laptop);
        return "Laptops";
    }

    @GetMapping
    public String getAllLaptops(Model model) {
        Iterable<Laptop> laptops = laptopService.findAllLaptops();
        model.addAttribute("laptops", laptops);
        return "Laptops";
    }

    @PostMapping("/update/{laptopId}")
    public String updateLaptop(@PathVariable("laptopId") int laptopId, @ModelAttribute Laptop request, Model model) {
        laptopService.updateLaptop(laptopId, request);
        return "redirect:/laptops";
    }

    @PostMapping("/create")
    public String createLaptop(@ModelAttribute Laptop request, Model model) {
        laptopService.createLaptop(request);
        return "redirect:/laptops";
    }

    @GetMapping("/delete/{laptopId}")
    public String deleteLaptop(@PathVariable("laptopId") int laptopId, Model model) {
        laptopService.deleteLaptop(laptopId);
        return "redirect:/laptops";
    }

    @GetMapping("/modal/delete/{laptopId}")
    public String deleteLaptopConfirmation(@PathVariable("laptopId") int laptopId, ModelMap model) {
        Laptop laptop = laptopService.getLaptop(laptopId);
        model.addAttribute("laptop", laptop);
        return "DeleteLaptop :: delete-laptop";
    }
    @GetMapping("/modal/edit/{laptopId}")
    public String editLaptopModal(@PathVariable("laptopId") int laptopId, ModelMap model) {
        Laptop laptop = laptopService.getLaptop(laptopId);
        model.addAttribute("laptop", laptop);
        return "EditLaptop :: edit-laptop";
    }

    @GetMapping("/modal/add")
    public String addLaptopModal(ModelMap model) {
        Laptop laptop = new Laptop();
        model.addAttribute("laptop", laptop);
        return "CreateLaptop :: add-laptop";
    }
}
