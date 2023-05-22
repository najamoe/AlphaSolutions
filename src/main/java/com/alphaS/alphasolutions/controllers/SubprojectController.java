package com.alphaS.alphasolutions.controllers;

import com.alphaS.alphasolutions.model.SubprojectModel;
import com.alphaS.alphasolutions.service.SubprojectService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Controller
public class SubprojectController {

    private final SubprojectService subprojectService;

    public SubprojectController(SubprojectService subprojectService) {
        this.subprojectService = subprojectService;
    }

    @GetMapping("/project/{projectId}/createsubproject")
    public String createSubproject(@PathVariable int projectId, Model model) {
        model.addAttribute("subproject", new SubprojectModel());
        return "/createsubproject";
    }

    @PostMapping("/project/{projectId}/createsubproject")
    public String createSubproject(@PathVariable int projectId, @ModelAttribute SubprojectModel subprojectModel, Model model) {
        String result = subprojectService.createSubproject(projectId, subprojectModel);
        model.addAttribute("subprojectName", subprojectModel.getSubProjectName());
        return "subprojectsuccess";
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

}
