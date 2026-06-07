package com.Kalabekov.Computersservice.controller;

import com.Kalabekov.Computersservice.model.Software;
import com.Kalabekov.Computersservice.service.SoftwareService;
import com.Kalabekov.Computersservice.model.Computer;
import com.Kalabekov.Computersservice.model.Laptop;
import com.Kalabekov.Computersservice.repository.ComputerRepository;
import com.Kalabekov.Computersservice.repository.LaptopRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/software")
public class SoftwareController {

    @Autowired
    SoftwareService softwareService;

    @Autowired
    ComputerRepository computerRepository;

    @Autowired
    LaptopRepository laptopRepository;
    

    @GetMapping
    public String getAllSoftware(Model model) {
        Iterable<Software> software = softwareService.getAllSoftware();
        model.addAttribute("softwareList", software);
        return "Software";  
    }

    @PostMapping("/update/{softwareId}")
    public String updateSoftware(@PathVariable("softwareId") int softwareId,
                                 @ModelAttribute Software request,
                                 @RequestParam(name = "isLicensed", required = false) String isLicensed,
                                 Model model) {
        boolean isLicensedValue = isLicensed != null;
        request.setLicensed(isLicensedValue);
        softwareService.updateSoftware(softwareId, request);
        return "redirect:/software";
    }

    @PostMapping("/create")
    public String createSoftware(@ModelAttribute Software request,
                                 @RequestParam(name = "isLicensed", required = false) String isLicensed,
                                 Model model) {
        boolean isLicensedValue = isLicensed != null;
        request.setLicensed(isLicensedValue);
        softwareService.createSoftware(request);
        return "redirect:/software";
    }

    @GetMapping("/delete/{softwareId}")
    public String deleteSoftware(@PathVariable("softwareId") int softwareId, Model model) {
        softwareService.deleteSoftware(softwareId);
        return "redirect:/software";
    }

    @GetMapping("/modal/delete/{softwareId}")
    public String deleteSoftwareConfirmation(@PathVariable("softwareId") int softwareId, ModelMap model) {
        Software software = softwareService.getSoftware(softwareId);
        model.addAttribute("software", software);
        return "DeleteSoftware :: delete-software";
    }

    @GetMapping("/modal/edit/{softwareId}")
    public String editSoftwareModal(@PathVariable("softwareId") int softwareId, ModelMap model) {
        Software software = softwareService.getSoftware(softwareId);
        Iterable<Computer> computers = computerRepository.findAll();
        Iterable<Laptop> laptops = laptopRepository.findAll();
        model.addAttribute("software", software);
        model.addAttribute("computers", computers);
        model.addAttribute("laptops", laptops);
        return "EditSoftware :: edit-software";
    }

    @GetMapping("/modal/add")
    public String addSoftwareModal(ModelMap model) {
        Software software = new Software();
        Iterable<Computer> computers = computerRepository.findAll();
        Iterable<Laptop> laptops = laptopRepository.findAll();
        model.addAttribute("software", software);
        model.addAttribute("computers", computers);
        model.addAttribute("laptops", laptops);
        return "CreateSoftware :: add-software";
    }
}
