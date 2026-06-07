package com.Kalabekov.Computersservice.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AnalyticContoller {
    @GetMapping("/analytic")
    public String Analytic() {
        return "Analytic.html";
    }
}
