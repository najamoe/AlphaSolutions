package com.alphaS.alphasolutions.controllers;

import com.alphaS.alphasolutions.model.SubprojectModel;
import com.alphaS.alphasolutions.service.SubprojectService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;
import java.util.List;
import java.util.Map;


@Controller
public class SubprojectController {

    private final SubprojectService subprojectService;

    public SubprojectController(SubprojectService subprojectService) {
        this.subprojectService = subprojectService;
    }

    @GetMapping("/project/{projectId}/createsubproject")
    public String createSubproject(@PathVariable int projectId, Model model) {
        model.addAttribute("subproject", new SubprojectModel());
        return "createsubproject";
    }

    @PostMapping("/project/{projectId}/createsubproject")
    public String CreateSubprojectForm(@PathVariable int projectId, Model model) {
        model.addAttribute("projectId", projectId);
        model.addAttribute("subProject", new SubprojectModel());
        return "createsubproject";
    }

    @GetMapping("/project/{projectId}/readsubproject")
    @ResponseBody
    public ResponseEntity<List<SubprojectModel>> readSubproject(@PathVariable int projectId) {
        List<SubprojectModel> subprojects = subprojectService.readSubProject(projectId);
        return ResponseEntity.ok(subprojects);
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
}




