package com.alphaS.alphasolutions.controllers;

import com.alphaS.alphasolutions.model.SubprojectModel;
import com.alphaS.alphasolutions.repositories.SubProjectRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;


@Controller
public class SubProjectController {

    private final SubProjectRepository subprojectRepository;
    public SubProjectController(SubProjectRepository subprojectRepository) {
        this.subprojectRepository = subprojectRepository;
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
        List<SubprojectModel> subprojects = subprojectRepository.readSubProject(projectId);
        return ResponseEntity.ok(subprojects);
    }

    @PostMapping("/project/{projectId}/subprojects/{subprojectId}/edit")
    @ResponseBody
    public ResponseEntity<String> editSubproject(@RequestParam String SubprojectName, @RequestParam String SubprojectDescription) {
        try {
            String result = subprojectRepository.editSubproject(SubprojectName, SubprojectDescription);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to edit subproject");
        }
    }
    @PostMapping("/project/{projectId}/subproject/delete")
    public String deleteSubproject(@RequestParam int subprojectId) {
        String message = subprojectRepository.deleteSubproject(subprojectId);
        return message;
    }

}
