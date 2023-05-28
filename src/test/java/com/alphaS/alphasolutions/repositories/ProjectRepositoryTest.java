package com.alphaS.alphasolutions.repositories;

import com.alphaS.alphasolutions.model.ProjectModel;
import com.alphaS.alphasolutions.repositories.ProjectRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.sql.DataSource;
import java.sql.*;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class ProjectRepositoryTest {

    @InjectMocks
    private ProjectRepository projectRepository;

    @Mock
    private DataSource dataSource;

    @BeforeEach
    public void setup() throws SQLException {
        MockitoAnnotations.openMocks(this);

        Connection connection = mock(Connection.class);
        when(dataSource.getConnection()).thenReturn(connection);
    }

    @Test
    void readSpecificProject() throws SQLException {
        // Arrange - create test data
        int projectId = 1;
        String username = "testuser";
        String password = "testpassword";

        // Set up a mock Connection, PreparedStatement, and ResultSet
        Connection connection = mock(Connection.class);
        PreparedStatement projectStmt = mock(PreparedStatement.class);
        ResultSet projectRs = mock(ResultSet.class);

        // Set up the expected behavior of the Connection, PreparedStatement, and ResultSet
        when(dataSource.getConnection()).thenReturn(connection);
        when(connection.prepareStatement(anyString())).thenReturn(projectStmt);
        when(projectStmt.executeQuery()).thenReturn(projectRs);
        when(projectRs.next()).thenReturn(true);
        when(projectRs.getInt("project_id")).thenReturn(projectId);
        when(projectRs.getString("project_name")).thenReturn("project");
        when(projectRs.getString("project_description")).thenReturn("description");
        when(projectRs.getDate("start_date")).thenReturn(Date.valueOf(LocalDate.now()));
        when(projectRs.getDate("end_date")).thenReturn(Date.valueOf(LocalDate.now()));
        when(projectRs.getInt("client_id")).thenReturn(1);
        when(projectRs.getInt("employee_id")).thenReturn(1);

        // Act
        ProjectModel result = projectRepository.readSpecificProject(projectId, username, password);

        // Assert
        assertEquals(projectId, result.getProjectId());
        assertEquals("project", result.getProjectName());
        assertEquals("description", result.getProjectDescription());
        assertEquals(LocalDate.now(), result.getStartDate());
        assertEquals(LocalDate.now(), result.getEndDate());
        assertEquals(1, result.getClientId());
        assertEquals(1, result.getEmployeeId());

        // Verify that the necessary methods were called
        verify(dataSource.getConnection()).prepareStatement(anyString());
        verify(projectStmt).setInt(1, projectId);
        verify(projectStmt).setString(2, username);
        verify(projectStmt).setString(3, password);
        verify(projectStmt).executeQuery();
        verify(projectRs).next();
        verify(projectRs).getInt("project_id");
        verify(projectRs).getString("project_name");
        verify(projectRs).getString("project_description");
        verify(projectRs).getDate("start_date");
        verify(projectRs).getDate("end_date");
        verify(projectRs).getInt("client_id");
        verify(projectRs).getInt("employee_id");
    }
}
