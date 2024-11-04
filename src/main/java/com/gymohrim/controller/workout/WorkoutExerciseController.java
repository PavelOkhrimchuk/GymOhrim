package com.gymohrim.controller.workout;


import com.gymohrim.entity.Exercise;
import com.gymohrim.entity.Workout;
import com.gymohrim.entity.WorkoutExercise;
import com.gymohrim.service.statistics.DailyRecordService;
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
    private final DailyRecordService dailyRecordService;

    @GetMapping
    public String showWorkoutExercise(
            @RequestParam(value = "workoutId", required = false) Integer workoutId,
            @RequestParam(value = "selectedDate", required = false) String selectedDate,
            @RequestParam(value = "dailyRecordId", required = false) Integer dailyRecordId,
            Model model) {

        Workout workout = getOrCreateWorkout(workoutId, dailyRecordId);
        workoutId = workout.getId();


        List<Exercise> exercises = exerciseService.findAllExercises();
        List<String> muscleGroups = exerciseService.findAllMuscleGroups();


        model.addAttribute("exercises", exercises);
        model.addAttribute("muscleGroups", muscleGroups);
        model.addAttribute("workoutExercise", new WorkoutExercise());
        model.addAttribute("workoutId", workoutId);
        model.addAttribute("selectedDate", selectedDate);


        List<WorkoutExercise> addedExercises = workoutExerciseService.findByWorkoutId(workoutId);
        model.addAttribute("addedExercises", addedExercises);

        return "workout-exercise";
    }


    @PostMapping
    public String addWorkoutExercise(@ModelAttribute WorkoutExercise workoutExercise,
                                        @RequestParam("workoutId") Integer workoutId,
                                        @RequestParam("selectedDate") String selectedDate) {
        Workout workout = workoutService.findById(workoutId);
        workoutExercise.setWorkout(workout);
        workoutExerciseService.saveExercise(workoutExercise);
        return "redirect:/workout-exercise?workoutId=" + workout.getId() + "&selectedDate=" + selectedDate;
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

    @GetMapping("/exercise/{id}")
    public String showExerciseDetails(@PathVariable("id") Integer exerciseId,
                                      @RequestParam("workoutId") Integer workoutId,
                                      @RequestParam(value = "selectedDate", required = false) String selectedDate,
                                      Model model) {
        Exercise exercise = exerciseService.findById(exerciseId);
        model.addAttribute("workoutId", workoutId);
        model.addAttribute("exerciseId", exerciseId);
        model.addAttribute("exercise", exercise);
        model.addAttribute("selectedDate", selectedDate);
        return "exercise-details";
    }


    private Workout getOrCreateWorkout(Integer workoutId, Integer dailyRecordId) {
        Workout workout;


        if (workoutId == null || (workout = workoutService.findById(workoutId)) == null) {
            workout = new Workout();
            workout.setDailyRecord(dailyRecordService.findById(dailyRecordId));
            workoutService.saveOrUpdateWorkout(workout);
        }

        return workout;
    }


}
