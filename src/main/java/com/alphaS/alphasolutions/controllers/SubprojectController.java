package com.alphaS.alphasolutions.controllers;

import com.alphaS.alphasolutions.model.*;
import com.alphaS.alphasolutions.service.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;



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
        int subprojectId = subprojectService.getSubprojectId(projectId); // Retrieve subprojectId from subproject object
        List<TaskModel> tasks = taskService.readSpecificTask(subprojectId);
        String taskTime = taskService.getTotalTime(subprojectId); // Get total time as a string
        model.addAttribute("subproject", subproject);
        model.addAttribute("tasks", tasks);
        model.addAttribute("taskTime", taskTime);
        return "subproject";
    }


    @PostMapping("/subproject/edit")
    public String editSubproject(@RequestParam("newSubprojectName") String newSubprojectName,
                                 @RequestParam("newSubprojectDescription") String newSubprojectDescription, int project_id,
                                 Model model) {
        String result = subprojectService.editSubproject(newSubprojectName, newSubprojectDescription);
        SubprojectModel subproject = subprojectService.readSpecificSubproject(project_id);
        model.addAttribute("subproject", subproject);
        model.addAttribute("result", result);
        return "subproject";
    }



    @PostMapping("/project/{projectId}/subproject/delete")
    public String deleteSubproject(@RequestParam int subprojectId) {
        String message = subprojectService.deleteSubproject(subprojectId);
        return message;
    }
}




