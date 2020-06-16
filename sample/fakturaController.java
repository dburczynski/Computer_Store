package sample;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;

import java.sql.*;
import java.util.ArrayList;

public class fakturaController {

    @FXML
    TextArea faktura;

    public void initialize() {

        Connection con;
        try {
            String text ="_________________________________________________________\n";
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            con = DriverManager.getConnection("jdbc:sqlserver://localhost\\mssqlserver", "dburczynski", "Souvlaki1989");
            String sql = "SELECT nazwa,COUNT(nazwa) FROM koszyk GROUP BY nazwa;";
            ArrayList<String> names = new ArrayList<String>();
            ArrayList<Integer> count = new ArrayList<Integer>();
            Statement query = con.createStatement();
            ResultSet query_response = query.executeQuery(sql);
            while (query_response.next()) {
                names.add(query_response.getString(1));
                count.add(query_response.getInt(2));
            }
            for(int i = 0; i < names.size(); i++) {
                String sql2 = "SELECT cena from procesory WHERE nazwa ='"+names.get(i)+"'";
                Statement query2 = con.createStatement();
                ResultSet query_response2 = query2.executeQuery(sql2);
                while(query_response2.next()) {
                    text+=names.get(i)+"  Ilosc: "+count.get(i)+"   Cena: "+(count.get(i)+query_response2.getInt(1))+"\n";
                }

            }
            text+="_________________________________________________________\n";
            faktura.setText(text);
            con.close();


        }
        catch (ClassNotFoundException e) {
            System.out.println("Driver error!");
        }
        catch (SQLException error_connection) {
            System.out.println(error_connection.toString());
            System.out.println("Connection error!");
        }
    }
}
