package Weather;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.*;

public class MyAppServletContextListener implements ServletContextListener{

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        System.out.println("ServletContextListener destroyed");
    }

    //Run this before web application is started
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        PreparedStatement createStatement = null;
        Connection connection = connect();
        try {
            String CreateQuery = "CREATE TABLE Raion (id_Raion int primary key, NAME_Raion varchar(255))";
            createStatement = connection.prepareStatement(CreateQuery);
            createStatement.executeUpdate();
            createStatement.close();

            String CreateQuery1 = "CREATE TABLE WeatherPhenomen (id_Phenomen int primary key, NAME_Phenomen varchar(255))";
            createStatement = connection.prepareStatement(CreateQuery1);
            createStatement.executeUpdate();
            createStatement.close();

            String CreateQuery2 = "CREATE TABLE Parameters (id_Parameters int primary key, NAME_Parameters varchar(255))";
            createStatement = connection.prepareStatement(CreateQuery2);
            createStatement.executeUpdate();
            createStatement.close();

            String CreateQuery3 = "CREATE TABLE Link (id_Link int primary key, id_Phenomen int, id_Parameters int)";
            createStatement = connection.prepareStatement(CreateQuery3);
            createStatement.executeUpdate();
            createStatement.close();

            String CreateQuery4 = "CREATE TABLE Journal (id_Journal int primary key, DATA_Journal DATE, id_Phenomen int)";
            createStatement = connection.prepareStatement(CreateQuery4);
            createStatement.executeUpdate();
            createStatement.close();
        } catch (Exception e) {
            System.out.println(e);
        }


        PreparedStatement InsertStatement = null;
        try {
            String InsertQuery = "INSERT INTO Raion (id_Raion, Name_Raion) values" + "(1,'Удмуртская Республика')";
            InsertStatement = connection.prepareStatement(InsertQuery);
            InsertStatement.executeUpdate();
            InsertStatement.close();
        } catch (Exception e) {
            System.out.println(e);
        }

        PreparedStatement InsertStatement1 = null;

        String FILENAME = "/home/user/IdeaProjects/HobbyStudent/file/WeatherPhenomen.txt";
        BufferedReader br = null;
        FileReader fr = null;
        try {
            fr = new FileReader(FILENAME);
            br = new BufferedReader(fr);
            int i = 0;
            String CurrentLine;
            while ((CurrentLine = br.readLine()) != null) {
                i++;
                String InsertQuery1 = "INSERT INTO WeatherPhenomen (id_Phenomen, NAME_Phenomen) values (" + i + ",'" + CurrentLine + "')";
                InsertStatement1 = connection.prepareStatement(InsertQuery1);
                InsertStatement1.executeUpdate();
                InsertStatement1.close();
            }
        } catch (Exception e) {
            System.out.println(e);
        }

        PreparedStatement InsertStatement2 = null;

        String FILENAME_Parameters = "/home/user/IdeaProjects/HobbyStudent/file/Parameters.txt";
        BufferedReader br_Parameters = null;
        FileReader fr_Parameters = null;
        try {
            fr_Parameters = new FileReader(FILENAME_Parameters);
            br_Parameters = new BufferedReader(fr_Parameters);
            int i = 0;
            String CurrentLine;
            while ((CurrentLine = br_Parameters.readLine()) != null) {
                i++;
                String InsertQuery2 = "INSERT INTO Parameters (id_Parameters, NAME_Parameters) values (" + i + ",'" + CurrentLine + "')";
                InsertStatement2 = connection.prepareStatement(InsertQuery2);
                InsertStatement2.executeUpdate();
                InsertStatement2.close();
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }


    PreparedStatement createStatement = null;
    Connection connection = connect();


    private static Connection connect() {
        Connection conn = null;
        try {

            Class.forName("org.h2.Driver");
            conn = DriverManager.getConnection("jdbc:h2:mem:test", "user", "user");
        } catch (Exception e) {
            System.out.println("Ошибка: " + e);
        }
        return conn;

    }
}
