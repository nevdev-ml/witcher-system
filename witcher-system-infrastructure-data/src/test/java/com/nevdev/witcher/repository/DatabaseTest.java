package com.nevdev.witcher.repository;

import com.nevdev.witcher.core.Location;
import com.nevdev.witcher.core.Reward;
import com.nevdev.witcher.core.Task;
import com.nevdev.witcher.core.User;
import com.nevdev.witcher.enums.Currency;
import com.nevdev.witcher.enums.Region;
import com.nevdev.witcher.enums.Role;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.containers.JdbcDatabaseContainer;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import static org.junit.Assert.*;

@Testcontainers
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@RunWith(MockitoJUnitRunner.class)
public class DatabaseTest {
    @Container
    private static PostgreSQLContainer postgreSQLContainer = CustomPostgresqlContainer.getInstance();

    @Test
    public void testTaskPSQL() throws SQLException {
        postgreSQLContainer.start();
        Long taskId = 2L;
        ResultSet taskResult = performQuery(postgreSQLContainer,
                String.format("SELECT * FROM task WHERE reward_id = %d", taskId));
        Task task = new Task();
        task.setCompletionOn(taskResult.getDate(1));
        task.setCreateOn(taskResult.getDate(2));
        task.setCustomerId(taskResult.getLong(3));
        task.setDone(taskResult.getBoolean(4));
        task.setLocationComment(taskResult.getString(5));
        task.setPaid(taskResult.getBoolean(6));
        task.setTitle(taskResult.getString(7));
        task.setWitcherId(taskResult.getLong(8));
        ResultSet rewardResult = performQuery(postgreSQLContainer,
                String.format("SELECT * FROM reward WHERE id = %d", taskResult.getLong(9)));
        Reward reward = new Reward(rewardResult.getDouble(2), Currency.values()[rewardResult.getInt(4)]);
        reward.setId(rewardResult.getLong(1));
        task.setReward(reward);
        ResultSet locationResult = performQuery(postgreSQLContainer,
                String.format("SELECT * FROM location WHERE id = %d", taskResult.getLong(10)));
        Location location = new Location(Region.values()[locationResult.getInt(2)]);
        location.setId(locationResult.getLong(1));
        task.setLocation(location);

        ResultSet witchersTaskResult = performQueryCollection(postgreSQLContainer,
                String.format("SELECT * FROM task_witcher WHERE task_id = %d", taskId));

        ArrayList<User> users = new ArrayList<>();
        while (witchersTaskResult.next()) {
            ResultSet resultSet = performQuery(postgreSQLContainer,
                    String.format("SELECT * FROM users WHERE bank_id = %d", witchersTaskResult.getLong(2)));
            User user = new User();
            user.setEmail(resultSet.getString(1));
            user.setEnabled(resultSet.getBoolean(2));
            user.setFirstName(resultSet.getString(3));
            user.setLastName(resultSet.getString(4));
            user.setLastPasswordResetDate(resultSet.getDate(5));
            user.setPassword(resultSet.getString(6));
            user.setResetToken(resultSet.getString(7));
            user.setRole(Role.values()[resultSet.getInt(8)]);
            user.setUsername(resultSet.getString(9));
            users.add(user);
        }
        task.setWitchers(users);

        assertNotNull(task.getWitchers());
        assertNotNull(task.getLocation());
        assertNotNull(task.getReward());
        assertEquals(task.getWitchers().size(), 2);
        assertEquals(task.getWitchersCompleted(), new ArrayList<>());
        assertFalse(task.getDone());
        assertFalse(task.getPaid());
        assertTrue(postgreSQLContainer.isRunning());
    }

    private ResultSet performQuery(JdbcDatabaseContainer container, String sql) throws SQLException {
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setJdbcUrl(container.getJdbcUrl());
        hikariConfig.setUsername(container.getUsername());
        hikariConfig.setPassword(container.getPassword());

        HikariDataSource ds = new HikariDataSource(hikariConfig);
        Statement statement = ds.getConnection().createStatement();
        statement.execute(sql);
        ResultSet resultSet = statement.getResultSet();
        resultSet.next();
        return resultSet;
    }

    private ResultSet performQueryCollection(JdbcDatabaseContainer container, String sql) throws SQLException {
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setJdbcUrl(container.getJdbcUrl());
        hikariConfig.setUsername(container.getUsername());
        hikariConfig.setPassword(container.getPassword());

        HikariDataSource ds = new HikariDataSource(hikariConfig);
        Statement statement = ds.getConnection().createStatement();
        statement.execute(sql);
        return statement.getResultSet();
    }
}
