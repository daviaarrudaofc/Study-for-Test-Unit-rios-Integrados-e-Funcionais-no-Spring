package io.github.daviaarrudaofc.locadora;

import org.junit.jupiter.api.*;

import java.sql.Connection;
import java.sql.DriverManager;

public class DatabaseTest {

     static Connection connection;

     @BeforeAll// smp se coloca em method "static" obrigatoriamente -- antes de tudo
     static void setUpDatabase() throws Exception{
         connection = DriverManager.getConnection("jdbc:h2:mem:testedb", "sa", "");
         connection.createStatement().execute("CREATE TABLE users (id INT, name VARCHAR) ");


     }

     @BeforeEach
    void insertUserTest() throws Exception{
         connection.createStatement().execute("insert into users(id, name) values(1, 'José')");
     }
     @Test
     //@Disabled -- vai ta desabilitado
     void testUserExists() throws Exception{
         var result = connection
                 .createStatement()
                 .executeQuery("select * from users where id = 1");

         Assertions.assertTrue(result.next());

     }

    @AfterAll// smp se coloca em method "static" obrigatoriamente -- dps de tudo
    static void closeDatabase() throws Exception {
         connection.close();
    }
}
