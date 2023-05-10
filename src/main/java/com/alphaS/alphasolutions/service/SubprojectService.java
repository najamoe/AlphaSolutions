package com.alphaS.alphasolutions.service;

import com.alphaS.alphasolutions.model.SubprojectModel;
import com.alphaS.alphasolutions.repositories.SubprojectRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubprojectService {

    private SubprojectRepository subprojectRepository;

    public SubprojectService(SubprojectRepository subprojectRepository) {
        this.subprojectRepository = subprojectRepository;
    }

    public String createSubProject(String subProjectName, String subProjectDescription) {
        return subprojectRepository.createSubProject(subProjectName, subProjectDescription);
    }

    public List<SubprojectModel> readSubProject(int projectId) {
        return subprojectRepository.readSubProject(projectId);
    }

    public String editSubproject(String SubProjectName, String SubProjectDescription) {
        return subprojectRepository.editSubproject(SubProjectName, SubProjectDescription);
    }

    public String deleteSubproject(int subProjectId) {
     return  subprojectRepository.deleteSubproject(subProjectId);
    }

}
