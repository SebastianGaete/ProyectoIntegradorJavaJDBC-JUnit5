package org.sebastian.database.utilities;
import org.junit.jupiter.api.*;
import org.sebastian.exceptions.*;
import org.sebastian.models.*;
import java.util.List;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

class MethodsBDTest {
    MethodsBD method;

    @BeforeEach
    void initMethodTest(TestInfo testInfo, TestReporter testReporter){
        method = new MethodsBD();
        testReporter.publishEntry("Initialized: " + testInfo.getTestMethod().get().getName());
    }

    @AfterEach
    void tearDown(TestInfo testInfo, TestReporter testReporter) {
        testReporter.publishEntry(testInfo.getTestMethod().get().getName() + " Aprobated!".concat("\n"));
    }


    @Nested
    @Tag("methods")
    @DisplayName("Check all methods from class MethodsBD")
    class MethodsClassTest{

        @Test
        @Disabled
        @DisplayName("Check Create")
        void testCreateUser(){
            User user = new User("namedtest2", "lastnametest2", 32, "test2@gmail.com");
            method.create(user);
        }

        @Test
        @DisplayName("Check ListAll")
        void testListAll(){
            List<User> listUsers = method.listAll("users");
            assertEquals("nameUpdate2", listUsers.stream()
                    .filter( u -> u.getFirstName().equals("nameUpdate2") )
                    .findFirst().get().getFirstName());
        }

        @Test
        @DisplayName("Check Read")
        void testRead(){
            assertEquals("test2@gmail.com", method.read(1).getEmail());
        }

        @Test
        @Disabled
        @DisplayName("Check Delete")
        void testDelete(){
            method.delete("test2@gmail.com");
        }

        @Nested
        @DisplayName("Check overload method update")
        class MethodUpdateOverload{
            @Test
            @DisplayName("Check Update1")
            void testUpdate(){
                method.update(1, "nameUpdate2", "lastnameupdate");
            }

            @Test
            @DisplayName("Check Update2")
            void testUpdate2(){
                method.update(1, "nameUpdate2", "lastnameupdate", 90);
            }

            @Test
            @DisplayName("Check Update3")
            void testUpdate3(){
                method.update(1, "nameUpdate2", "lastnameupdate", 90, "emailUpdate@gmail.com");
            }

            @Test
            @DisplayName("Check Update4")
            void testUpdate4(){
                method.update(1, "nameUpdate2", "lastnameupdate", 90, "emailUpdate@gmail.com");
            }
        }

    }

    @Nested
    @Tag("exceptions")
    @DisplayName("Check all exceptions")
    class MethodsExceptions{

        @Test
        @Disabled
        @DisplayName("DuplicateEmailException")
        void testExceptionDuplicateEmailException(){
            User user = new User("namedtest", "lastnametest", 23, "test@gmail.com");
            assertThrows(DuplicateEmailException.class, ()-> method.create(user));
        }

        @Test
        @DisplayName("TableDontNotExistException")
        void testTableDontNotExistException(){
            assertThrows(TableDontNotExistException.class, ()-> method.listAll("anything"));
        }

        @Test
        @DisplayName("UserNotFoundException on Read")
        void testUserNotFoundException(){
            assertThrows(UserNotFoundException.class, ()-> method.read(-1));
        }

        @Test
        @DisplayName("UserNotFoundException on Delete")
        void testUserNotFoundExceptionDelete(){
            assertThrows(UserNotFoundException.class, ()-> method.delete("test2@gmail.com"));
        }

        @Test
        @DisplayName("UserNotFoundException on Update")
        void testUserNotFoundExceptionUpdate(){
            assertThrows(UserNotFoundException.class, ()-> method.update(-12, "nameUpdate8"));
        }



    }

}