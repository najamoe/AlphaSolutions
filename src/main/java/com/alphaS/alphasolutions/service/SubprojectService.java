package com.alphaS.alphasolutions.service;

import com.alphaS.alphasolutions.model.SubprojectModel;
import com.alphaS.alphasolutions.repositories.SubprojectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;


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
        return subprojectRepository.createSubProject(subprojectModel.getSubprojectName(), subprojectModel.getSubprojectDescription(), projectId);
    }

    public List<SubprojectModel> readSubProject( ) throws SQLException {
        return subprojectRepository.readSubProjects();
    }

    public String editSubproject(String SubProjectName, String SubProjectDescription, int subprojectId) {
        return subprojectRepository.editSubproject(SubProjectName, SubProjectDescription, subprojectId);
    }

    public String deleteSubproject(int subProjectId) {
     return  subprojectRepository.deleteSubproject(subProjectId);
    }

    public SubprojectModel readSpecificSubproject(int projectId) {
        return subprojectRepository.readSpecificSubproject(projectId);
    }


    public int getSubprojectId(int projectId) {
        return subprojectRepository.getSubprojectId(projectId);
    }
}
