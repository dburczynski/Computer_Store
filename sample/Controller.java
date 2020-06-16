package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.sql.*;
import java.util.ArrayList;
import java.util.SplittableRandom;

public class Controller {
    @FXML
    Pane pane;
    @FXML
    Pane logoPane;
    @FXML
    Button emptyButton;
    @FXML
    Button button1;
    @FXML
    Button button2;
    @FXML
    Button button3;
    @FXML
    Button wrocButton;
    @FXML
    Button mailButton;
    @FXML
    Button fakturaButton;
    @FXML
    Button procesor1;
    @FXML
    Button procesor2;
    @FXML
    Button procesor3;
    @FXML
    Button myszka1;
    @FXML
    Button myszka2;
    @FXML
    Button myszka3;
    @FXML
    Button monitor1;
    @FXML
    Button monitor2;
    @FXML
    Button monitor3;
    @FXML
    Text stan1;
    @FXML
    Text stan2;
    @FXML
    Text stan3;
    @FXML
    Text stanCount1;
    @FXML
    Text stanCount2;
    @FXML
    Text stanCount3;
    @FXML
    Text kosz1;
    @FXML
    Text kosz2;
    @FXML
    Text kosz3;
    @FXML
    Text koszCount1;
    @FXML
    Text koszCount2;
    @FXML
    Text koszCount3;
    @FXML
    Label money;
    @FXML
    Label amount;


    private boolean procesory;
    private boolean myszki;
    private boolean monitory;

    public void initialize() {
        this.procesory = false;
        this.myszki = false;
        this.monitory = false;
        this.wrocButton.setVisible(false);
        this.procesor1.setVisible(false);
        this.procesor2.setVisible(false);
        this.procesor3.setVisible(false);
        this.myszka1.setVisible(false);
        this.myszka2.setVisible(false);
        this.myszka3.setVisible(false);
        this.monitor1.setVisible(false);
        this.monitor2.setVisible(false);
        this.monitor3.setVisible(false);
        this.stan1.setVisible(false);
        this.stan2.setVisible(false);
        this.stan3.setVisible(false);
        this.stanCount1.setVisible(false);
        this.stanCount2.setVisible(false);
        this.stanCount3.setVisible(false);
        this.kosz1.setVisible(false);
        this.kosz2.setVisible(false);
        this.kosz3.setVisible(false);
        this.koszCount1.setVisible(false);
        this.koszCount2.setVisible(false);
        this.koszCount3.setVisible(false);
        Connection con;
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            con = DriverManager.getConnection("jdbc:sqlserver://localhost\\mssqlserver", "dburczynski", "Souvlaki1989");
            String sql = "SELECT COUNT(nazwa) FROM koszyk;";
            Statement query = con.createStatement();
            ResultSet result = query.executeQuery(sql);
            while (result.next()) {
                amount.setText(result.getString(1));

            }
            float i = 0;
            sql = "SELECT SUM(cena) FROM koszyk";
            result = query.executeQuery(sql);
            while (result.next()) {
                i = result.getFloat(1);
            }
            money.setText(Float.toString(i) + " PLN");
            con.close();

        } catch (ClassNotFoundException e) {
            System.out.println("Driver error!");
        } catch (SQLException error_connection) {
            System.out.println("Connection error!");
        }
    }

    @FXML
    private void button1Click() {
        this.procesory = true;
        this.menuShow(false);
        this.shoppingOptions(true);
        this.procesor1.setVisible(true);
        this.procesor2.setVisible(true);
        this.procesor3.setVisible(true);
        this.getProcesorCount();
        this.getProcesorKoszyk();


    }

    @FXML
    private void button2Click() {
        this.myszki = true;
        this.menuShow(false);
        this.shoppingOptions(true);
        this.myszka1.setVisible(true);
        this.myszka2.setVisible(true);
        this.myszka3.setVisible(true);
        this.getMyszkiCount();
        this.getMyszkiKoszyk();
    }

    @FXML
    private void button3Click() {
        this.monitory = true;
        this.menuShow(false);
        this.shoppingOptions(true);
        this.monitor1.setVisible(true);
        this.monitor2.setVisible(true);
        this.monitor3.setVisible(true);
        this.getMonitoryCount();
        this.getMonitoryKoszyk();
    }

    @FXML
    private void emptyCart() {
        Connection con;
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            con = DriverManager.getConnection("jdbc:sqlserver://localhost\\mssqlserver", "dburczynski", "Souvlaki1989");
            String sql = "SELECT nazwa, COUNT(nazwa) FROM koszyk GROUP BY nazwa;";
            Statement query = con.createStatement();
            ResultSet result = query.executeQuery(sql);
            ArrayList<String> products = new ArrayList<String>();
            ArrayList<Integer> stan = new ArrayList<Integer>();
            ArrayList<Integer> stan2 = new ArrayList<Integer>();
            while (result.next()) {
                products.add(result.getString(1));
                stan.add(result.getInt(2));
            }
            for(int i = 0; i < products.size(); i++) {
                String sql2 = "SELECT stan from procesory WHERE nazwa =";
                Statement query2 = con.createStatement();
                ResultSet result2 = query2.executeQuery(sql2+"'"+products.get(i)+"';");
                while(result2.next()) {
                    stan2.add(result2.getInt(1));
                }
            }
            for(int i = 0; i < products.size(); i++) {
                int x = stan.get(i)+stan2.get(i);
                String sql3 = "UPDATE procesory SET stan="+x+" WHERE nazwa ='"+products.get(i)+"';";
                Statement query3 = con.createStatement();
                query3.executeUpdate(sql3);
            }
            String sql4 = "DELETE FROM koszyk";
            query.executeUpdate(sql4);

            con.close();
            this.updateKoszyk();
            if(procesory) {
                this.getProcesorCount();
                this.getProcesorKoszyk();
            }
            if(myszki) {
                this.getMyszkiCount();
                this.getMyszkiKoszyk();
            }
            if(monitory) {
                this.getMonitoryCount();
                this.getMonitoryKoszyk();
            }
        }


        catch(ClassNotFoundException e) {
        System.out.println("Driver error!");
        }
        catch(SQLException error_connection)
        {
            System.out.println(error_connection.toString());
//            System.out.println("Connection error!");
        }
    }

    @FXML
    private void procesor1Click() {
        Connection con;
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            con = DriverManager.getConnection("jdbc:sqlserver://localhost\\mssqlserver", "dburczynski", "Souvlaki1989");
            String sql = "UPDATE procesory SET stan =(SELECT stan FROM procesory WHERE nazwa='yntol r7')-1 WHERE nazwa='yntol r7';";
            Statement query = con.createStatement();
            query.executeUpdate(sql);
            String productName ="";
            float value = 0f;
            sql = "SELECT nazwa,cena FROM procesory WHERE nazwa='yntol r7';";

            ResultSet result = query.executeQuery(sql);
            while(result.next()) {
                productName = result.getString(1);
                value = result.getFloat(2);
            }
            sql = "INSERT INTO KOSZYK (nazwa,cena) VALUES ('"+productName+"', "+value+")";
            query.executeUpdate(sql);
            con.close();
            this.updateKoszyk();
            this.getProcesorCount();
            this.getProcesorKoszyk();

        }
        catch (ClassNotFoundException e) {
            System.out.println("Driver error!");
        }
        catch (SQLException error_connection) {
            System.out.println("Connection error!");
        }

    }
    @FXML
    private void procesor2Click() {
        Connection con;
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            con = DriverManager.getConnection("jdbc:sqlserver://localhost\\mssqlserver", "dburczynski", "Souvlaki1989");
            String sql = "UPDATE procesory SET stan =(SELECT stan FROM procesory WHERE nazwa='yntol g9')-1 WHERE nazwa='yntol g9';";
            Statement query = con.createStatement();
            query.executeUpdate(sql);
            String productName ="";
            float value = 0f;
            sql = "SELECT nazwa,cena FROM procesory WHERE nazwa='yntol g9';";

            ResultSet result = query.executeQuery(sql);
            while(result.next()) {
                productName = result.getString(1);
                value = result.getFloat(2);
            }
            sql = "INSERT INTO KOSZYK (nazwa,cena) VALUES ('"+productName+"', "+value+")";
            query.executeUpdate(sql);
            con.close();
            this.updateKoszyk();
            this.getProcesorCount();
            this.getProcesorKoszyk();

        }
        catch (ClassNotFoundException e) {
            System.out.println("Driver error!");
        }
        catch (SQLException error_connection) {
            System.out.println("Connection error!");
        }
    }
    @FXML
    private void procesor3Click() {
        Connection con;
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            con = DriverManager.getConnection("jdbc:sqlserver://localhost\\mssqlserver", "dburczynski", "Souvlaki1989");
            String sql = "UPDATE procesory SET stan =(SELECT stan FROM procesory WHERE nazwa='amb 5')-1 WHERE nazwa='amb 5';";
            Statement query = con.createStatement();
            query.executeUpdate(sql);
            String productName ="";
            float value = 0f;
            sql = "SELECT nazwa,cena FROM procesory WHERE nazwa='amb 5';";

            ResultSet result = query.executeQuery(sql);
            while(result.next()) {
                productName = result.getString(1);
                value = result.getFloat(2);
            }
            sql = "INSERT INTO KOSZYK (nazwa,cena) VALUES ('"+productName+"', "+value+")";
            query.executeUpdate(sql);
            con.close();
            this.updateKoszyk();
            this.getProcesorCount();
            this.getProcesorKoszyk();

        }
        catch (ClassNotFoundException e) {
            System.out.println("Driver error!");
        }
        catch (SQLException error_connection) {
            System.out.println("Connection error!");
        }
    }
    @FXML
    private void myszka1Click() {
        Connection con;
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            con = DriverManager.getConnection("jdbc:sqlserver://localhost\\mssqlserver", "dburczynski", "Souvlaki1989");
            String sql = "UPDATE procesory SET stan =(SELECT stan FROM procesory WHERE nazwa='myszka 1')-1 WHERE nazwa='myszka 1';";
            Statement query = con.createStatement();
            query.executeUpdate(sql);
            String productName ="";
            float value = 0f;
            sql = "SELECT nazwa,cena FROM procesory WHERE nazwa='myszka 1';";

            ResultSet result = query.executeQuery(sql);
            while(result.next()) {
                productName = result.getString(1);
                value = result.getFloat(2);
            }
            sql = "INSERT INTO KOSZYK (nazwa,cena) VALUES ('"+productName+"', "+value+")";
            query.executeUpdate(sql);
            con.close();
            this.updateKoszyk();
            this.getMyszkiCount();
            this.getMyszkiCount();

        }
        catch (ClassNotFoundException e) {
            System.out.println("Driver error!");
        }
        catch (SQLException error_connection) {
            System.out.println("Connection error!");
        }
    }
    @FXML
    private void myszka2Click() {
        Connection con;
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            con = DriverManager.getConnection("jdbc:sqlserver://localhost\\mssqlserver", "dburczynski", "Souvlaki1989");
            String sql = "UPDATE procesory SET stan =(SELECT stan FROM procesory WHERE nazwa='myszka 2')-1 WHERE nazwa='myszka 2';";
            Statement query = con.createStatement();
            query.executeUpdate(sql);
            String productName ="";
            float value = 0f;
            sql = "SELECT nazwa,cena FROM procesory WHERE nazwa='myszka 2';";

            ResultSet result = query.executeQuery(sql);
            while(result.next()) {
                productName = result.getString(1);
                value = result.getFloat(2);
            }
            sql = "INSERT INTO KOSZYK (nazwa,cena) VALUES ('"+productName+"', "+value+")";
            query.executeUpdate(sql);
            con.close();
            this.updateKoszyk();
            this.getMyszkiCount();
            this.getMyszkiCount();

        }
        catch (ClassNotFoundException e) {
            System.out.println("Driver error!");
        }
        catch (SQLException error_connection) {
            System.out.println("Connection error!");
        }
    }
    @FXML
    private void myszka3Click() {
        Connection con;
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            con = DriverManager.getConnection("jdbc:sqlserver://localhost\\mssqlserver", "dburczynski", "Souvlaki1989");
            String sql = "UPDATE procesory SET stan =(SELECT stan FROM procesory WHERE nazwa='myszka 3')-1 WHERE nazwa='myszka 3';";
            Statement query = con.createStatement();
            query.executeUpdate(sql);
            String productName ="";
            float value = 0f;
            sql = "SELECT nazwa,cena FROM procesory WHERE nazwa='myszka 3';";

            ResultSet result = query.executeQuery(sql);
            while(result.next()) {
                productName = result.getString(1);
                value = result.getFloat(2);
            }
            sql = "INSERT INTO KOSZYK (nazwa,cena) VALUES ('"+productName+"', "+value+")";
            query.executeUpdate(sql);
            con.close();
            this.updateKoszyk();
            this.getMyszkiCount();
            this.getMyszkiCount();

        }
        catch (ClassNotFoundException e) {
            System.out.println("Driver error!");
        }
        catch (SQLException error_connection) {
            System.out.println("Connection error!");
        }
    }
    @FXML
    private void monitor1Click() {
        Connection con;
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            con = DriverManager.getConnection("jdbc:sqlserver://localhost\\mssqlserver", "dburczynski", "Souvlaki1989");
            String sql = "UPDATE procesory SET stan =(SELECT stan FROM procesory WHERE nazwa='monitor 1')-1 WHERE nazwa='monitor 1';";
            Statement query = con.createStatement();
            query.executeUpdate(sql);
            String productName ="";
            float value = 0f;
            sql = "SELECT nazwa,cena FROM procesory WHERE nazwa='monitor 1';";

            ResultSet result = query.executeQuery(sql);
            while(result.next()) {
                productName = result.getString(1);
                value = result.getFloat(2);
            }
            sql = "INSERT INTO KOSZYK (nazwa,cena) VALUES ('"+productName+"', "+value+")";
            query.executeUpdate(sql);
            con.close();
            this.updateKoszyk();
            this.getMonitoryCount();
            this.getMonitoryKoszyk();

        }
        catch (ClassNotFoundException e) {
            System.out.println("Driver error!");
        }
        catch (SQLException error_connection) {
            System.out.println("Connection error!");
        }
    }
    @FXML
    private void monitor2Click() {
        Connection con;
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            con = DriverManager.getConnection("jdbc:sqlserver://localhost\\mssqlserver", "dburczynski", "Souvlaki1989");
            String sql = "UPDATE procesory SET stan =(SELECT stan FROM procesory WHERE nazwa='monitor 2')-1 WHERE nazwa='monitor 2';";
            Statement query = con.createStatement();
            query.executeUpdate(sql);
            String productName ="";
            float value = 0f;
            sql = "SELECT nazwa,cena FROM procesory WHERE nazwa='monitor 2';";

            ResultSet result = query.executeQuery(sql);
            while(result.next()) {
                productName = result.getString(1);
                value = result.getFloat(2);
            }
            sql = "INSERT INTO KOSZYK (nazwa,cena) VALUES ('"+productName+"', "+value+")";
            query.executeUpdate(sql);
            con.close();
            this.updateKoszyk();
            this.getMonitoryCount();
            this.getMonitoryKoszyk();

        }
        catch (ClassNotFoundException e) {
            System.out.println("Driver error!");
        }
        catch (SQLException error_connection) {
            System.out.println("Connection error!");
        }
    }
    @FXML
    private void monitor3Click() {
        Connection con;
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            con = DriverManager.getConnection("jdbc:sqlserver://localhost\\mssqlserver", "dburczynski", "Souvlaki1989");
            String sql = "UPDATE procesory SET stan =(SELECT stan FROM procesory WHERE nazwa='monitor 3')-1 WHERE nazwa='monitor 3';";
            Statement query = con.createStatement();
            query.executeUpdate(sql);
            String productName ="";
            float value = 0f;
            sql = "SELECT nazwa,cena FROM procesory WHERE nazwa='monitor 3';";

            ResultSet result = query.executeQuery(sql);
            while(result.next()) {
                productName = result.getString(1);
                value = result.getFloat(2);
            }
            sql = "INSERT INTO KOSZYK (nazwa,cena) VALUES ('"+productName+"', "+value+")";
            query.executeUpdate(sql);
            con.close();
            this.updateKoszyk();
            this.getMonitoryCount();
            this.getMonitoryKoszyk();

        }
        catch (ClassNotFoundException e) {
            System.out.println("Driver error!");
        }
        catch (SQLException error_connection) {
            System.out.println("Connection error!");
        }
    }
    @FXML
    private void wrocClick() {
        this.shoppingOptions(false);
        if(this.procesory) {
            this.procesor1.setVisible(false);
            this.procesor2.setVisible(false);
            this.procesor3.setVisible(false);
            this.procesory = false;
        }
        if(this.myszki) {
            this.myszka1.setVisible(false);
            this.myszka2.setVisible(false);
            this.myszka3.setVisible(false);
            this.myszki = false;
        }
        if(this.monitory) {
            this.monitor1.setVisible(false);
            this.monitor2.setVisible(false);
            this.monitor3.setVisible(false);
            this.monitory = false;
        }
        this.menuShow(true);
    }
    @FXML
    private void fakturaButtonClick() throws  Exception {
        Pane fakturaPane = FXMLLoader.load(getClass().getResource("sample2.fxml"));
        Scene fakturaScene = new Scene(fakturaPane,400,300);
        Stage fakturaWindow = new Stage();
        fakturaWindow.setScene(fakturaScene);
        fakturaWindow.setResizable(false);
        fakturaWindow.show();
    }
    @FXML void commentClick() throws Exception {
        Pane commentPane = FXMLLoader.load(getClass().getResource("sample3.fxml"));
        Scene commentScene = new Scene(commentPane,500,400);
        Stage commentWindow = new Stage();
        commentWindow.setScene(commentScene);
        commentWindow.setResizable(false);
        commentWindow.show();
    }
    @FXML
    private void emptyHover() {
        emptyButton.setPrefWidth(121);
        emptyButton.setPrefHeight(50);
        emptyButton.setLayoutX(857);
        emptyButton.setLayoutY(92);
    }

    @FXML
    private void emptyUnhover() {
        emptyButton.setPrefWidth(97);
        emptyButton.setPrefHeight(40);
        emptyButton.setLayoutX(869);
        emptyButton.setLayoutY(87);
    }
    private void shoppingOptions(boolean bool) {
        this.stan1.setVisible(bool);
        this.stan2.setVisible(bool);
        this.stan3.setVisible(bool);
        this.stanCount1.setVisible(bool);
        this.stanCount2.setVisible(bool);
        this.stanCount3.setVisible(bool);
        this.kosz1.setVisible(bool);
        this.kosz2.setVisible(bool);
        this.kosz3.setVisible(bool);
        this.koszCount1.setVisible(bool);
        this.koszCount2.setVisible(bool);
        this.koszCount3.setVisible(bool);
        this.wrocButton.setVisible(bool);
    }
    private void menuShow(boolean bool) {
        this.button1.setVisible(bool);
        this.button2.setVisible(bool);
        this.button3.setVisible(bool);
    }
    private void getProcesorCount() {
        Connection con;
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            con = DriverManager.getConnection("jdbc:sqlserver://localhost\\mssqlserver", "dburczynski", "Souvlaki1989");
            String sql = "SELECT stan from procesory WHERE nazwa='yntol r7';";
            Statement query = con.createStatement();
            query.executeQuery(sql);
            ResultSet query_response = query.executeQuery(sql);
            while (query_response.next()) {
                stanCount1.setText(query_response.getString(1));
            }
            sql = "SELECT stan from procesory WHERE nazwa='yntol g9';";
            query.executeQuery(sql);
            query_response = query.executeQuery(sql);
            while (query_response.next()) {
                stanCount2.setText(query_response.getString(1));
            }
            sql = "SELECT stan from procesory WHERE nazwa='amb';";
            query.executeQuery(sql);
            query_response = query.executeQuery(sql);
            while (query_response.next()) {
                stanCount3.setText(query_response.getString(1));
            }
            con.close();


        }
        catch (ClassNotFoundException e) {
            System.out.println("Driver error!");
        }
        catch (SQLException error_connection) {
            System.out.println("Connection error!");
        }
    }
    private void getProcesorKoszyk() {
        Connection con;
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            con = DriverManager.getConnection("jdbc:sqlserver://localhost\\mssqlserver", "dburczynski", "Souvlaki1989");
            String sql = "SELECT COUNT(nazwa) from koszyk WHERE nazwa='yntol r7';";
            Statement query = con.createStatement();
            query.executeQuery(sql);
            ResultSet query_response = query.executeQuery(sql);
            while (query_response.next()) {
                koszCount1.setText(query_response.getString(1));
            }
            sql = "SELECT COUNT(nazwa) from koszyk WHERE nazwa='yntol g9';";
            query.executeQuery(sql);
            query_response = query.executeQuery(sql);
            while (query_response.next()) {
                koszCount2.setText(query_response.getString(1));
            }
            sql = "SELECT COUNT(nazwa) from koszyk WHERE nazwa='amb';";
            query.executeQuery(sql);
            query_response = query.executeQuery(sql);
            while (query_response.next()) {
                koszCount3.setText(query_response.getString(1));
            }
            con.close();


        }
        catch (ClassNotFoundException e) {
            System.out.println("Driver error!");
        }
        catch (SQLException error_connection) {
            System.out.println("Connection error!");
        }
    }
    private void getMyszkiCount() {
        Connection con;
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            con = DriverManager.getConnection("jdbc:sqlserver://localhost\\mssqlserver", "dburczynski", "Souvlaki1989");
            String sql = "SELECT stan from procesory WHERE nazwa='myszka 1';";
            Statement query = con.createStatement();
            query.executeQuery(sql);
            ResultSet query_response = query.executeQuery(sql);
            while (query_response.next()) {
                stanCount1.setText(query_response.getString(1));
            }
            sql = "SELECT stan from procesory WHERE nazwa='myszka 2';";
            query.executeQuery(sql);
            query_response = query.executeQuery(sql);
            while (query_response.next()) {
                stanCount2.setText(query_response.getString(1));
            }
            sql = "SELECT stan from procesory WHERE nazwa='myszka 3';";
            query.executeQuery(sql);
            query_response = query.executeQuery(sql);
            while (query_response.next()) {
                stanCount3.setText(query_response.getString(1));
            }
            con.close();


        }
        catch (ClassNotFoundException e) {
            System.out.println("Driver error!");
        }
        catch (SQLException error_connection) {
            System.out.println("Connection error!");
        }
    }
    private void getMyszkiKoszyk() {
        Connection con;
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            con = DriverManager.getConnection("jdbc:sqlserver://localhost\\mssqlserver", "dburczynski", "Souvlaki1989");
            String sql = "SELECT COUNT(nazwa) from koszyk WHERE nazwa='myszka 1';";
            Statement query = con.createStatement();
            query.executeQuery(sql);
            ResultSet query_response = query.executeQuery(sql);
            while (query_response.next()) {
                koszCount1.setText(query_response.getString(1));
            }
            sql = "SELECT COUNT(nazwa) from koszyk WHERE nazwa='myszka 2';";
            query.executeQuery(sql);
            query_response = query.executeQuery(sql);
            while (query_response.next()) {
                koszCount2.setText(query_response.getString(1));
            }
            sql = "SELECT COUNT(nazwa) from koszyk WHERE nazwa='myszka 3';";
            query.executeQuery(sql);
            query_response = query.executeQuery(sql);
            while (query_response.next()) {
                koszCount3.setText(query_response.getString(1));
            }
            con.close();


        }
        catch (ClassNotFoundException e) {
            System.out.println("Driver error!");
        }
        catch (SQLException error_connection) {
            System.out.println("Connection error!");
        }
    }
    private void getMonitoryCount() {
        Connection con;
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            con = DriverManager.getConnection("jdbc:sqlserver://localhost\\mssqlserver", "dburczynski", "Souvlaki1989");
            String sql = "SELECT stan from procesory WHERE nazwa='monitor 1';";
            Statement query = con.createStatement();
            query.executeQuery(sql);
            ResultSet query_response = query.executeQuery(sql);
            while (query_response.next()) {
                stanCount1.setText(query_response.getString(1));
            }
            sql = "SELECT stan from procesory WHERE nazwa='monitor 2';";
            query.executeQuery(sql);
            query_response = query.executeQuery(sql);
            while (query_response.next()) {
                stanCount2.setText(query_response.getString(1));
            }
            sql = "SELECT stan from procesory WHERE nazwa='monitor 3';";
            query.executeQuery(sql);
            query_response = query.executeQuery(sql);
            while (query_response.next()) {
                stanCount3.setText(query_response.getString(1));
            }
            con.close();


        }
        catch (ClassNotFoundException e) {
            System.out.println("Driver error!");
        }
        catch (SQLException error_connection) {
            System.out.println("Connection error!");
        }
    }
    private void getMonitoryKoszyk() {
        Connection con;
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            con = DriverManager.getConnection("jdbc:sqlserver://localhost\\mssqlserver", "dburczynski", "Souvlaki1989");
            String sql = "SELECT COUNT(nazwa) from koszyk WHERE nazwa='monitor 1';";
            Statement query = con.createStatement();
            query.executeQuery(sql);
            ResultSet query_response = query.executeQuery(sql);
            while (query_response.next()) {
                koszCount1.setText(query_response.getString(1));
            }
            sql = "SELECT COUNT(nazwa) from koszyk WHERE nazwa='monitor 2';";
            query.executeQuery(sql);
            query_response = query.executeQuery(sql);
            while (query_response.next()) {
                koszCount2.setText(query_response.getString(1));
            }
            sql = "SELECT COUNT(nazwa) from koszyk WHERE nazwa='monitor 3';";
            query.executeQuery(sql);
            query_response = query.executeQuery(sql);
            while (query_response.next()) {
                koszCount3.setText(query_response.getString(1));
            }
            con.close();


        }
        catch (ClassNotFoundException e) {
            System.out.println("Driver error!");
        }
        catch (SQLException error_connection) {
            System.out.println("Connection error!");
        }
    }
    private void updateKoszyk() {
        Connection con;
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            con = DriverManager.getConnection("jdbc:sqlserver://localhost\\mssqlserver", "dburczynski", "Souvlaki1989");
            String sql = "SELECT COUNT(nazwa) from koszyk";
            Statement query = con.createStatement();
            int i = 0;
            ResultSet result = query.executeQuery(sql);
            while(result.next()) {
                i = result.getInt(1);
            }
            float j = 0;

            sql = "SELECT SUM(cena) from koszyk";
            result = query.executeQuery(sql);
            while(result.next())
                j = result.getFloat(1);
            amount.setText(Integer.toString(i));
            money.setText(Float.toString(j)+" PLN");

            con.close();

        }
        catch (ClassNotFoundException e) {
            System.out.println("Driver error!");
        }
        catch (SQLException error_connection) {
            System.out.println("Connection error!");
        }
    }
}
