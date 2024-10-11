package com.gymohrim.controller;


import com.gymohrim.entity.User;
import com.gymohrim.service.statistics.DailyRecordService;
import com.gymohrim.service.user.UserProfileService;
import com.gymohrim.util.DateParser;
import com.gymohrim.util.DateValidator;
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
            Date date = DateParser.parseDate(selectedDate);


            DateValidator.validateDateNotInFuture(date);

            User user = userProfileService.findByEmail(userDetails.getUsername());

            if (dailyRecordService.existsByDateAndUser(date, user)) {
                model.addAttribute("error", "Запись на выбранную дату уже существует. Пожалуйста, выберите другую дату.");
                return "calendar-form"; // Возвращаем форму с ошибкой
            }

            dailyRecordService.saveDailyRecord(date, user);
            return "redirect:/daily-record?selectedDate=" + selectedDate;

        } catch (ParseException e) {
        model.addAttribute("error", "Invalid date format. Please use the format YYYY-MM-DD.");
        return "calendar-form";
    } catch (IllegalArgumentException e) {
        model.addAttribute("error", "Please ensure the selected date is valid and in the past.");
        return "calendar-form";
    }
    }
}
