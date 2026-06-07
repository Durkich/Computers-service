package com.Kalabekov.Computersservice.controller;

import com.Kalabekov.Computersservice.model.Peripheral;
import com.Kalabekov.Computersservice.service.PeripheralService;
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
@RequestMapping("/peripherals")
public class PeripheralController {

    @Autowired
    private PeripheralService peripheralService;

    @Autowired
    private ComputerRepository computerRepository;

    @Autowired
    private LaptopRepository laptopRepository;

    @GetMapping("/{peripheralId}")
    public String getPeripheral(@PathVariable("peripheralId") int peripheralId, Model model) {
        Peripheral peripheral = peripheralService.getPeripheral(peripheralId);
        model.addAttribute("peripheral", peripheral);
        return "PeripheralDetail";
    }

    @GetMapping
    public String getAllPeripherals(Model model) {
        Iterable<Peripheral> peripherals = peripheralService.getAllPeripherals();
        model.addAttribute("peripherals", peripherals);
        return "Peripherals";
    }

    @PostMapping("/update/{peripheralId}")
    public String updatePeripheral(@PathVariable("peripheralId") int peripheralId, @ModelAttribute Peripheral request, Model model) {
        peripheralService.updatePeripheral(peripheralId, request);
        return "redirect:/peripherals";
    }

    @PostMapping("/create")
    public String createPeripheral(@ModelAttribute Peripheral request, Model model) {
        peripheralService.createPeripheral(request);
        return "redirect:/peripherals";
    }

    @GetMapping("/delete/{peripheralId}")
    public String deletePeripheral(@PathVariable("peripheralId") int peripheralId, Model model) {
        peripheralService.deletePeripheral(peripheralId);
        return "redirect:/peripherals";
    }

    @GetMapping("/modal/delete/{peripheralId}")
    public String deletePeripheralConfirmation(@PathVariable("peripheralId") int peripheralId, ModelMap model) {
        Peripheral peripheral = peripheralService.getPeripheral(peripheralId);
        model.addAttribute("peripheral", peripheral);
        return "DeletePeripheral :: delete-peripheral";
    }

    @GetMapping("/modal/edit/{peripheralId}")
    public String editPeripheralModal(@PathVariable("peripheralId") int peripheralId, ModelMap model) {
        Peripheral peripheral = peripheralService.getPeripheral(peripheralId);
        Iterable<Computer> computers = computerRepository.findAll();
        Iterable<Laptop> laptops = laptopRepository.findAll();
        model.addAttribute("peripheral", peripheral);
        model.addAttribute("computers", computers);
        model.addAttribute("laptops", laptops);
        return "EditPeripheral :: edit-peripheral";
    }

    @GetMapping("/modal/add")
    public String addPeripheralModal(ModelMap model) {
        Peripheral peripheral = new Peripheral();
        Iterable<Computer> computers = computerRepository.findAll();
        Iterable<Laptop> laptops = laptopRepository.findAll();
        model.addAttribute("peripheral", peripheral);
        model.addAttribute("computers", computers);
        model.addAttribute("laptops", laptops);
        return "CreatePeripheral :: add-peripheral";
    }
}
