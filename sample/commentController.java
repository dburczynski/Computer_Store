package sample;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.sql.*;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class commentController {
    @FXML
    TextArea commentTextArea;
    @FXML
    TextField emailTextField;

    @FXML
    private void dalejClick() {
        String textField = emailTextField.getText();
        if (textField.length() < 5 || textField.length() > 50) {

        } else {
            Pattern pat = Pattern.compile("([A-Z]|[0-9]|[a-z])*@[a-z]*\\.[a-z]*");
            Matcher match = pat.matcher(textField);
            if (match.matches()) {
                Connection con;
                try {
                    Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
                    con = DriverManager.getConnection("jdbc:sqlserver://localhost\\mssqlserver", "dburczynski", "Souvlaki1989");
                    String sql = "INSERT INTO wiadomosci (email,wiadmosc) VALUES ('" + textField + "', '" + commentTextArea.getText() + "');";
                    Statement query = con.createStatement();
                    query.executeUpdate(sql);
                    con.close();


                } catch (ClassNotFoundException e) {
                    System.out.println("Driver error!");
                } catch (SQLException error_connection) {
                    System.out.println(error_connection.toString());
                    System.out.println("Connection error!");
                }
            }

        }
    }
}
