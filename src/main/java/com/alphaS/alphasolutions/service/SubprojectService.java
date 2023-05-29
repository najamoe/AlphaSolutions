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
        return subprojectRepository.createSubproject(subprojectModel.getSubprojectName(), subprojectModel.getSubprojectDescription(), projectId);
    }

    public List<SubprojectModel> readSubproject( ) throws SQLException {
        return subprojectRepository.readSubprojects();
    }

    public String editSubproject(String SubprojectName, String SubprojectDescription, int subprojectId) {
        return subprojectRepository.editSubproject(SubprojectName, SubprojectDescription, subprojectId);
    }

    public String deleteSubproject(int subprojectId) {
     return  subprojectRepository.deleteSubproject(subprojectId);
    }

    public SubprojectModel readSpecificSubproject(int projectId) {
        return subprojectRepository.readSpecificSubproject(projectId);
    }


    public int getSubprojectId(int projectId) {
        return subprojectRepository.getSubprojectId(projectId);
    }
}
