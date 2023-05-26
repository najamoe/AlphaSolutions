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
        model.addAttribute("subprojectName", subprojectModel.getSubprojectName());
        return "redirect:/subprojectsuccess";
    }

    @GetMapping("/subprojectsuccess")
    public String subprojectSuccess(@RequestParam("subprojectId") int subprojectId, Model model) {
        model.addAttribute("subprojectId", subprojectId);
        return "subprojectsuccess";
    }


    @GetMapping("/subproject/{projectId}")
    public String readSubproject(@PathVariable("projectId") int projectId, Model model) {
        SubprojectModel subproject = subprojectService.readSpecificSubproject(projectId);

        if (subproject == null) {
            // Handle case where subproject is not found
            return "error";
        }

        model.addAttribute("subproject", subproject);

        return "subproject";
    }

    @PostMapping("/subproject/edit/{projectId}")
    public String editSubproject(@RequestParam("subprojectName") String subprojectName,
                                 @RequestParam("subprojectDescription") String subprojectDescription,
                                 @RequestParam("subprojectId") int subprojectId,
                                 Model model) {

        String result = subprojectService.editSubproject(subprojectName, subprojectDescription, subprojectId);
        // Update the model with the updated subproject attributes
        SubprojectModel subproject = subprojectService.readSpecificSubproject(subprojectId);

        model.addAttribute("subproject", subproject);
        model.addAttribute("result", result);
        return "subproject";
    }



}





