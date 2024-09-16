package com.gymohrim.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/calendar")
public class CalendarController {

    @GetMapping
    public String showCalendar() {
        return "calendar-form";
    }


    @PostMapping("/submit")
    public String submitDate(@RequestParam("selectedDate") String selectedDate, Model model) {
        model.addAttribute("selectedDate", selectedDate);

        return "redirect:/profile";

    }
}
