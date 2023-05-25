package com.alphaS.alphasolutions.controllers;

import com.alphaS.alphasolutions.model.SubprojectModel;
import com.alphaS.alphasolutions.model.TaskModel;
import com.alphaS.alphasolutions.service.SubprojectService;
import com.alphaS.alphasolutions.service.TaskService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.sql.SQLException;
import java.time.Duration;
import java.util.List;
import java.util.Map;


@Controller
public class SubprojectController {

    private final SubprojectService subprojectService;
    private final TaskService taskService;

    public SubprojectController(SubprojectService subprojectService, TaskService taskService) {
        this.subprojectService = subprojectService;
        this.taskService = taskService;
    }

    @GetMapping("/clientsuccess/{projectId}/createsubproject")
    public String createSubproject(@PathVariable int projectId, Model model) {
        model.addAttribute("projectId", projectId);
        model.addAttribute("subproject", new SubprojectModel());
        return "createsubproject";
    }

    @PostMapping("/clientsuccess/{projectId}/createsubproject")
    public String createSubproject(@PathVariable int projectId, @ModelAttribute SubprojectModel subprojectModel, Model model, RedirectAttributes redirectAttributes) {
        int subprojectId = subprojectService.createSubproject(projectId, subprojectModel);
        redirectAttributes.addAttribute("subprojectId", subprojectId);
        model.addAttribute("subprojectName", subprojectModel.getSubProjectName());
        return "redirect:/subprojectsuccess";
    }

    @GetMapping("/subprojectsuccess")
    public String subprojectSuccess(@RequestParam("subprojectId") int subprojectId, Model model) {
        model.addAttribute("subprojectId", subprojectId);
        return "subprojectsuccess";
    }


    @GetMapping("/subproject/{projectId}")
    public String getSubproject(@PathVariable("projectId") int projectId, Model model) throws SQLException {
        SubprojectModel subproject = subprojectService.readSpecificSubproject(projectId);
        int subprojectId = subprojectService.getSubprojectId(); // Retrieve subprojectId from subproject object
        List<TaskModel> tasks = taskService.readSpecificTask(subprojectId);
        model.addAttribute("subproject", subproject);
        model.addAttribute("tasks", tasks);
        return "subproject";
    }


    @PostMapping("/project/{projectId}/subprojects/{subprojectId}/edit")
    @ResponseBody
    public ResponseEntity<String> editSubproject(@RequestParam String SubprojectName, @RequestParam String SubprojectDescription) {
        try {
            String result = subprojectService.editSubproject(SubprojectName, SubprojectDescription);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to edit subproject");
        }
    }
    @PostMapping("/project/{projectId}/subproject/delete")
    public String deleteSubproject(@RequestParam int subprojectId) {
        String message = subprojectService.deleteSubproject(subprojectId);
        return message;
    }

/*
    @GetMapping("/project/{projectId}/subproject/{subprojectId}/estimatedtime")
    public String getTotalEstimatedTimeForSubproject(@PathVariable int subprojectId) {
        try {
            Duration estimatedTime = Duration.parse(subprojectService.getTotalEstimatedTimeForSubproject(subprojectId));
            long totalMinutes = estimatedTime.toMinutes();
            long days = totalMinutes / (24 * 60);
            long hours = (totalMinutes % (24 * 60)) / 60;
            long minutes = totalMinutes % 60;

            return String.format("%d days, %d hours, %d minutes", days, hours, minutes);
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }
    }

    @GetMapping("/project/{projectId}/combinedtime")
    @ResponseBody
    public ResponseEntity<String> getCombinedTimeForProject(@PathVariable int projectId) {
        try {
            String combinedTime = subprojectService.getCombinedTimeForProject(projectId);
            return ResponseEntity.ok(combinedTime);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to calculate combined time for project");
        }
    }

 */
}




