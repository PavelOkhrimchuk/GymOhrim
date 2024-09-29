package com.gymohrim.controller;


import com.gymohrim.entity.Exercise;
import com.gymohrim.entity.Workout;
import com.gymohrim.entity.WorkoutExercise;
import com.gymohrim.service.statistics.ExerciseService;
import com.gymohrim.service.statistics.WorkoutExerciseService;
import com.gymohrim.service.statistics.WorkoutService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/workout-exercise")

public class WorkoutExerciseController {

    private final WorkoutExerciseService workoutExerciseService;
    private final ExerciseService exerciseService;
    private final WorkoutService workoutService;


    @GetMapping
    public String showWorkoutExercise(@RequestParam("workoutId") Integer workoutId, Model model) {
        List<Exercise> exercises = exerciseService.findAllExercises();
        model.addAttribute("exercises", exercises);
        model.addAttribute("workoutExercise", new WorkoutExercise());
        model.addAttribute("workoutId", workoutId);
        return "workout-exercise";
    }

    @PostMapping
    public String changeWorkoutExercise(@ModelAttribute WorkoutExercise workoutExercise, @RequestParam("workoutId") Integer workoutId) {
        Workout workout = workoutService.findById(workoutId);
        workoutExercise.setWorkout(workout);
        workoutExerciseService.saveOrUpdateWorkoutExercise(workoutExercise);
        return "redirect:/workout-exercise?workoutId=" + workout.getId();

    }
}
