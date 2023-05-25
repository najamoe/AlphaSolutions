package com.alphaS.alphasolutions.service;

import com.alphaS.alphasolutions.model.SubprojectModel;
import com.alphaS.alphasolutions.repositories.SubprojectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



@Service
public class SubprojectService {

    @Autowired
    private SubprojectRepository subprojectRepository;

    public SubprojectService(SubprojectRepository subprojectRepository) {
        this.subprojectRepository = subprojectRepository;
    }

    public SubprojectService() {

    }

    public int createSubproject(int projectId, SubprojectModel subprojectModel) {
        return subprojectRepository.createSubProject(subprojectModel.getSubProjectName(), subprojectModel.getSubProjectDescription(), projectId);
    }

    public String editSubproject(String newSubProjectName, String newSubProjectDescription) {
        return subprojectRepository.editSubproject(newSubProjectName, newSubProjectDescription);
    }

    public String deleteSubproject(int subProjectId) {
     return  subprojectRepository.deleteSubproject(subProjectId);
    }

    public SubprojectModel readSpecificSubproject(int project_id) {
        return subprojectRepository.readSpecificSubproject(project_id);
    }


    public int getSubprojectId(int projectId) {
        return subprojectRepository.getSubprojectId(projectId);
    }
}
