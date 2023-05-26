package com.alphaS.alphasolutions.repositories;

import com.alphaS.alphasolutions.model.SubprojectModel;
import com.alphaS.alphasolutions.service.SubprojectService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

import javax.sql.DataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class SubprojectRepositoryTest {

    private DataSource dataSource;

    @BeforeEach
    public void setup() {
        dataSource = mock(DataSource.class);
    }






}