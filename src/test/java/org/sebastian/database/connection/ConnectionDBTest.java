package org.sebastian.database.connection;

import org.junit.jupiter.api.*;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class ConnectionDBTest {


    @BeforeEach
    void initMethodTest(TestInfo testInfo, TestReporter testReporter) {
        testReporter.publishEntry("Initialized: " + testInfo.getTestMethod().get().getName());
    }

    @Test
    @DisplayName("checking method getInstanceConnection")
    void testGetInstanceConnection() throws SQLException {
        assertInstanceOf(Connection.class, ConnectionDB.getIntanceConnection());
    }

}