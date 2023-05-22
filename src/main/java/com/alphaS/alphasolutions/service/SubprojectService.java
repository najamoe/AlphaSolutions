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

    public String createSubproject(int projectId, SubprojectModel subprojectModel) {
        // Kalder subprojectRepository.createSubProject() metoden og returnerer resultatet
        return subprojectRepository.createSubProject(subprojectModel.getSubProjectName(), subprojectModel.getSubProjectDescription(), projectId);
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
