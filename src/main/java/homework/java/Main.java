package homework.java;

import homework.java.domain.*;
import homework.java.service.dao.*;
import homework.java.service.db.CsvToDbBuilder;
import homework.java.service.db.DbFill;
import homework.java.service.db.DbInit;
import homework.java.service.db.Jdbc;

import org.h2.jdbcx.JdbcConnectionPool;

import javax.sql.DataSource;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;

public class Main {
    /* TODO
         1. Initialize database (DONE)
         2. Fill database with values
         3. Create scripts which would do desired in task B
         4. ???
         5. Profit!!!*/

    public static void main(String[] args) throws Exception {
        DataSource dataSource = JdbcConnectionPool.create("jdbc:h2:mem:database", "", "");
        Jdbc source = new Jdbc(dataSource);
        new DbInit(source).create();
        System.out.println("DB successfully initialized!");
        new DbFill(source).fill();

//        source.statement(stmt -> stmt.execute());
//        try (Connection conn = dataSource.getConnection();
//             Statement stmt = conn.createStatement()){
//
//            ResultSet result = stmt.executeQuery("select name from speaker");
//
//            while (result.next()) {
//
//                System.out.println(result.getString("name"));
//
//            }
//        }
    }

}