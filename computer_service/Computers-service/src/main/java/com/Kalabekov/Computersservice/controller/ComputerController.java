package com.Kalabekov.Computersservice.controller;

import com.Kalabekov.Computersservice.model.Computer;
import com.Kalabekov.Computersservice.service.ComputerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/computers")
public class ComputerController {

    @Autowired
    private ComputerService computerService;

    @GetMapping("/{computerId}")
    public String getComputer(@PathVariable("computerId") int computerId, Model model) {
        Computer computer = computerService.getComputer(computerId);
        model.addAttribute("computer", computer);
        return "Computers";
    }

    @GetMapping
    public String getAllComputers(Model model) {
        Iterable<Computer> computers = computerService.findAllComputers();
        model.addAttribute("computers", computers);
        return "Computers";
    }

    @PostMapping("/create")
    public String createComputer(@ModelAttribute Computer request, Model model) {
        computerService.createComputer(request);
        return "redirect:/computers";
    }

    @PostMapping("/update/{computerId}")
    public String updateComputer(@PathVariable("computerId") int computerId, @ModelAttribute Computer request, Model model) {
        computerService.updateComputer(computerId, request);
        return "redirect:/computers";
    }

    @GetMapping("/delete/{computerId}")
    public String deleteComputer(@PathVariable("computerId") int computerId, Model model) {
        computerService.deleteComputer(computerId);
        return "redirect:/computers";
    }

    @GetMapping("/modal/delete/{computerId}")
    public String deleteComputerConfirmation(@PathVariable("computerId") int computerId, ModelMap model) {
        Computer computer = computerService.getComputer(computerId);
        model.addAttribute("computer", computer);
        return "DeleteComputer :: delete-computer";
    }

    @GetMapping("/modal/edit/{computerId}")
    public String editComputerModal(@PathVariable("computerId") int computerId, ModelMap model) {
        Computer computer = computerService.getComputer(computerId);
        model.addAttribute("computer", computer);
        return "EditComputer :: edit-computer";
    }

    @GetMapping("/modal/add")
    public String addComputerModal(ModelMap model) {
        Computer computer = new Computer();
        model.addAttribute("computer", computer);
        return "CreateComputer :: add-computer";
    }
}
