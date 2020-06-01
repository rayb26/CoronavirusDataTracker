package com.example.sport;

import javax.xml.transform.Result;
import java.sql.*;

public class DataBaseStore {


    public static final String DB_NAME = "Coronavirus_Data.db";

    public static final String CONNECTION_STRING = "jdbc:sqlite:C:\\Users\\sport\\Desktop\\Coronavirus_Database\\" + DB_NAME;

    public static final String TABLE_CASES = "CASES";
    public static final String COLUMN_CTIMESTAMP = "Timestamp";
    public static final String COLUMN_CASES = "Cases";

    public static final String TABLE_DEATHS = "DEATHS";
    public static final String COLUMN_DTIMESTAMP = "Timestamp";
    public static final String COLUMN_DEATHS = "Deaths";

    public static final String TABLE_RECOVERED = "RECOVERED";
    public static final String COLUMN_RTIMESTAMP = "Timestamp";
    public static final String COLUMN_RECOVERED = "Recovered";

    public static final String TABLE_COUNTRY_CASES = "Country_Cases";
    public static final String COLUMN_CCTIMESTAMP = "Timestamp";
    public static final String COLUMN_CCASES = "Cases";

    public static final String TABLE_COUNTRY_DEATHS = "Country_Deaths";
    public static final String COLUMN_CDTIMESTAMP = "Timestamp";
    public static final String COLUMN_CDDEATHS = "Deaths";

    public static final String TABLE_COUNTRY_RECOVERED = "Country_Recovered";
    public static final String COLUMN_CRTIMESTAMP = "Timestamp";
    public static final String COLUMN_CRRECOVERED = "Recovered";

    public static final String TABLE_STATE_CASES = "State_Cases";
    public static final String COLUMN_STATE_CTIMESTAMP = "Timestamp";
    public static final String COLUMN_STATE_CCASES = "Cases";

    public static final String TABLE_STATE_DEATHS = "State_Deaths";
    public static final String COLUMN_STATE_DTIMESTAMP = "Timestamp";
    public static final String COLUMN_STATE_DDEATHS = "Deaths";


    public static final String TABLE_STATE_RECOVERED = "State_Recovered";
    public static final String COLUMN_STATE_RTIMESTAMP = "Timestamp";
    public static final String COLUMN_STATE_RRECOVERED = "Recovered";




    public static final String CREATE_TABLE_CASES = "CREATE TABLE IF NOT EXISTS " + TABLE_CASES + " (" + COLUMN_CTIMESTAMP + " TEXT, " + COLUMN_CASES + " TEXT);";
    public static final String CREATE_TABLE_DEATHS = "CREATE TABLE IF NOT EXISTS " + TABLE_DEATHS + " (" + COLUMN_DTIMESTAMP + " TEXT, " + COLUMN_DEATHS + " TEXT)";
    public static final String CREATE_TABLE_RECOVERED = "CREATE TABLE IF NOT EXISTS " + TABLE_RECOVERED + " (" + COLUMN_RTIMESTAMP + " TEXT, " + COLUMN_RECOVERED + " TEXT)";

    public static final String CREATE_TABLE_COUNTRY_CASES = "CREATE TABLE IF NOT EXISTS " + TABLE_COUNTRY_CASES + " (" + COLUMN_CCTIMESTAMP + " TEXT, " + COLUMN_CCASES + " TEXT);";
    public static final String CREATE_TABLE_COUNTRY_DEATHS = "CREATE TABLE IF NOT EXISTS " + TABLE_COUNTRY_DEATHS + " (" + COLUMN_CDTIMESTAMP + " TEXT, " + COLUMN_CDDEATHS + " TEXT);";
    public static final String CREATE_TABLE_COUNTRY_RECOVERED = "CREATE TABLE IF NOT EXISTS " + TABLE_COUNTRY_RECOVERED + " (" + COLUMN_CRTIMESTAMP + " TEXT, " + COLUMN_CRRECOVERED + " TEXT);";

    public static final String CREATE_TABLE_STATE_CASES = "CREATE TABLE IF NOT EXISTS " + TABLE_STATE_CASES + " (" + COLUMN_STATE_CTIMESTAMP + " TEXT, " + COLUMN_STATE_CCASES + " TEXT);";
    public static final String CREATE_TABLE_STATE_DEATHS = "CREATE TABLE IF NOT EXISTS " + TABLE_STATE_DEATHS + " (" + COLUMN_STATE_DTIMESTAMP + " TEXT, " + COLUMN_STATE_DDEATHS + " TEXT);";
    public static final String CREATE_TABLE_STATE_RECOVERED = "CREATE TABLE IF NOT EXISTS " + TABLE_STATE_RECOVERED + " (" + COLUMN_STATE_RTIMESTAMP + " TEXT, "  + COLUMN_STATE_RRECOVERED + " TEXT)";


    private Connection connection;

    public boolean open() {
        try {
            connection = DriverManager.getConnection(CONNECTION_STRING);
            return true;
        } catch (SQLException e) {

            System.out.println("Couldn't Connect to database" + e.getMessage());
            return false;

        }
    }

    public void close() {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            System.out.println("Couldn't close connection: " + e.getMessage());
        }
    }

    public void createTableCases() {

        if (open()) {
            try (Statement statement = connection.createStatement()) {

                statement.executeUpdate(CREATE_TABLE_CASES);


            } catch (SQLException e) {
                System.out.println("Error TABLE Not created ");
                System.out.println(e.getMessage());


            }
            close();
        } else {
            System.out.println("error creating table");
        }


    }

    public void createTableDeaths() {
        if (open()) {
            try (Statement statement = connection.createStatement()) {

                statement.executeUpdate(CREATE_TABLE_DEATHS);


            } catch (SQLException e) {
                System.out.println("Error TABLE Not created ");
                System.out.println(e.getMessage());


            }
            close();
        } else {
            System.out.println("error creating table");
        }
    }

    public void createTableRecovered() {
        if (open()) {
            try (Statement statement = connection.createStatement()) {

                statement.executeUpdate(CREATE_TABLE_RECOVERED);

            } catch (SQLException e) {
                System.out.println("Error TABLE Not created ");
                System.out.println(e.getMessage());


            }
            close();
        } else {
            System.out.println("error creating table");
        }
    }

    public void insertIntoCaseTable(String timeStamp, String dataValue) {

        if (open()) {
            String sql = "INSERT INTO " + TABLE_CASES + " VALUES(?,?)";

            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1, timeStamp);
                preparedStatement.setString(2, dataValue);
                preparedStatement.executeUpdate();


            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
            close();
        } else {
            System.out.println("Error Opening");
        }


    }

    public void insertIntoDeathsTable(String timeStamp, String dataValue) {

        if (open()) {
            String sql = "INSERT INTO " + TABLE_DEATHS + " VALUES(?,?)";

            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1, timeStamp);
                preparedStatement.setString(2, dataValue);
                preparedStatement.executeUpdate();


            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
            close();
        } else {
            System.out.println("Error Opening");
        }


    }

    public void insertIntoRecoveredTable(String timeStamp, String dataValue) {
        if (open()) {
            String sql = "INSERT INTO " + TABLE_RECOVERED + " VALUES(?,?)";

            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1, timeStamp);
                preparedStatement.setString(2, dataValue);
                preparedStatement.executeUpdate();


            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
            close();
        } else {
            System.out.println("Error Opening");
        }

    }

    //Country Database Configuration Code Below
    public void createTableCountryData(String tableDataType) {

        if (open()) {
            try (Statement statement = connection.createStatement()) {

                statement.executeUpdate(tableDataType);
                System.out.println("Country" + tableDataType + " data added");
            } catch (SQLException e) {
                System.out.println("Error TABLE Not created ");
                System.out.println(e.getMessage());


            }
            close();
        } else {
            System.out.println("error creating table");
        }

    }
    public void insertIntoCountryData(String timeStamp, String dataValue, String tableDataType) {

        if (open()) {
            String sql = "INSERT INTO " + tableDataType + " VALUES(?,?)";

            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1, timeStamp);
                preparedStatement.setString(2, dataValue);
                preparedStatement.executeUpdate();


            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
            close();
        } else {
            System.out.println("Error Opening");
        }


    }
    //State coronavirus data below


}
