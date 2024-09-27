package com.gymohrim.controller;


import com.gymohrim.entity.User;
import com.gymohrim.service.statistics.DailyRecordService;
import com.gymohrim.service.user.UserProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
@RequiredArgsConstructor
@RequestMapping("/calendar")
public class CalendarController {
    private final UserProfileService userProfileService;
    private final DailyRecordService dailyRecordService;

    @GetMapping
    public String showCalendar() {
        return "calendar-form";
    }


    @PostMapping("/submit")
    public String submitDate(@RequestParam("selectedDate") String selectedDate,
                             @AuthenticationPrincipal UserDetails userDetails, Model model) {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date date = dateFormat.parse(selectedDate);

            User user = userProfileService.findByEmail(userDetails.getUsername());
            dailyRecordService.saveDailyRecord(date, user);

            return "redirect:/profile";

        } catch (ParseException e) {
            model.addAttribute("error", "Invalid date format");
            return "calendar-form";


        }
    }
}
