package homework.java;

import homework.java.domain.*;
import homework.java.service.dao.*;
import homework.java.service.db.CsvToDbBuilder;
import homework.java.service.db.DbFill;
import homework.java.service.db.DbInit;
import homework.java.service.db.Jdbc;

import homework.java.service.query.ExecuteQuery;
import homework.java.service.query.GenerateExcel;
import homework.java.service.query.VisualizeQuery;
import org.h2.jdbcx.JdbcConnectionPool;

import javax.sql.DataSource;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;

public class Main {
    /* TODO
         1. Initialize database (DONE)
         2. Fill database with values (DONE)
         3. Create scripts which would do desired in task B (6/8 DONE)
         4. ???
         5. Profit!!!*/

    public static void main(String[] args) throws Exception {
        DataSource dataSource = JdbcConnectionPool.create("jdbc:h2:mem:database", "", "");
        Jdbc source = new Jdbc(dataSource);
        new DbInit(source).create();
        System.out.println("DB successfully initialized!");
        new DbFill(source).fill();
        System.out.println("DB filled successfully!");
        ExecuteQuery executor = new ExecuteQuery(source);
        ArrayList<ExecuteQuery.Query1Result> res1 = executor.executeQuery1();
        ArrayList<ExecuteQuery.Query2Result> res2 = executor.executeQuery2();
        ArrayList<ExecuteQuery.Query3Result> res3 = executor.executeQuery3();
        ArrayList<ExecuteQuery.Query4Result> res4 = executor.executeQuery4();
        ArrayList<ExecuteQuery.Query5Result> res5 = executor.executeQuery5();
        executor.executeQuery6("{\"\"en\"\": \"\"Boeing 777-300\"\", \"\"ru\"\": \"\"Боинг 777-300\"\"}");
        new GenerateExcel<ExecuteQuery.Query1Result>().WriteExcelWorkbook(res1, "Query1Result.xlsx", "Query1Sheet");
        new GenerateExcel<ExecuteQuery.Query2Result>().WriteExcelWorkbook(res2, "Query2Result.xlsx", "Query2Sheet");
        new GenerateExcel<ExecuteQuery.Query3Result>().WriteExcelWorkbook(res3, "Query3Result.xlsx", "Query3Sheet");
        new GenerateExcel<ExecuteQuery.Query4Result>().WriteExcelWorkbook(res4, "Query4Result.xlsx", "Query4Sheet");
        new GenerateExcel<ExecuteQuery.Query5Result>().WriteExcelWorkbook(res5, "Query5Result.xlsx", "Query5Sheet");
        VisualizeQuery visualizer = new VisualizeQuery();
        visualizer.DrawBarChartQuery4(res4);
        visualizer.DrawBarChartQuery5(res5);
    }

}