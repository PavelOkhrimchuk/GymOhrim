package com.gymohrim.controller.exception;

import com.gymohrim.exception.api.OpenFoodFactsException;
import com.gymohrim.exception.file.BucketCreationException;
import com.gymohrim.exception.file.FileUploadException;
import com.gymohrim.exception.statistics.DailyRecordNotFoundException;
import com.gymohrim.exception.statistics.WorkoutNotFoundException;
import com.gymohrim.exception.user.UserNotFoundException;
import com.gymohrim.exception.user.UserProfileNotFoundException;
import com.gymohrim.exception.user.UserProfileSaveException;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.text.ParseException;

@ControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleIllegalArgumentException(IllegalArgumentException e, Model model) {
        model.addAttribute("error", "Something went wrong");
        return "error";
    }


    @ExceptionHandler(ParseException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleParseException(ParseException e, Model model) {
        model.addAttribute("error", "Invalid date format. Please use the correct format.");
        return "error";
    }


    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String handleAllExceptions(Exception e, Model model) {
        model.addAttribute("error", "An unexpected error occurred. Please try again later.");
        return "error";
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleMissingException(MissingServletRequestParameterException e, Model model) {
        model.addAttribute("error", "Please create a workout first.");
        return "error";
    }


    @ExceptionHandler(FileUploadException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String handleFileUploadException(FileUploadException e, Model model) {
        model.addAttribute("error", "Failed to upload the file. Please try again later.");
        return "error";
    }

    @ExceptionHandler(BucketCreationException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String handleBucketCreationException(BucketCreationException e, Model model) {
        model.addAttribute("error", "Failed to initialize the file storage. Please contact support.");
        return "error";
    }

    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleUserNotFoundException(UserNotFoundException e, Model model) {
        model.addAttribute("error", "User not found. Please check the email address and try again.");
        return "error";
    }

    @ExceptionHandler(UserProfileNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleUserProfileNotFoundException(UserProfileNotFoundException e, Model model) {
        model.addAttribute("error", "User profile not found. Please make sure the user profile exists.");
        return "error";
    }

    @ExceptionHandler(UserProfileSaveException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String handleUserProfileSaveException(UserProfileSaveException e, Model model) {
        model.addAttribute("error", "Failed to save or update user profile. Please try again later.");
        return "error";
    }

    @ExceptionHandler(OpenFoodFactsException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String handleOpenFoodFactsException(OpenFoodFactsException e, Model model) {
        model.addAttribute("error", "Failed to fetch product information. Please try again later.");
        return "error";
    }

    @ExceptionHandler(WorkoutNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleWorkoutNotFoundException(WorkoutNotFoundException e, Model model) {
        model.addAttribute("error", "Workout not found. Please check the workout ID and try again.");
        return "error";
    }

    @ExceptionHandler(DailyRecordNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleDailyRecordNotFoundException(DailyRecordNotFoundException e, Model model) {
        model.addAttribute("error", "Daily record not found. Please check the record ID and try again.");
        return "error";
    }



}
