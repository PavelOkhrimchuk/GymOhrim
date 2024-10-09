package com.gymohrim.controller.workout;


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
        List<String> muscleGroups = exerciseService.findAllMuscleGroups();

        model.addAttribute("exercises", exercises);
        model.addAttribute("muscleGroups", muscleGroups);
        model.addAttribute("workoutExercise", new WorkoutExercise());
        model.addAttribute("workoutId", workoutId);

        List<WorkoutExercise> addedExercises = workoutExerciseService.findByWorkoutId(workoutId);
        model.addAttribute("addedExercises", addedExercises);

        return "workout-exercise";
    }

    @PostMapping
    public String changeWorkoutExercise(@ModelAttribute WorkoutExercise workoutExercise, @RequestParam("workoutId") Integer workoutId) {
        Workout workout = workoutService.findById(workoutId);
        workoutExercise.setWorkout(workout);
        workoutExerciseService.saveOrUpdateWorkoutExercise(workoutExercise);
        return "redirect:/workout-exercise?workoutId=" + workout.getId();

    }

    @GetMapping("/exercises")
    @ResponseBody
    public List<Exercise> getExercisesByMuscleGroup(@RequestParam String muscleGroup) {
        return exerciseService.findByMuscleGroup(muscleGroup);
    }

    @PostMapping("/delete")
    public String deleteWorkoutExercise(@RequestParam("workoutId") Integer workoutId, @RequestParam("id") Integer id) {
        workoutExerciseService.deleteWorkoutExercise(id);
        return "redirect:/workout-exercise?workoutId=" + workoutId;
    }
}
