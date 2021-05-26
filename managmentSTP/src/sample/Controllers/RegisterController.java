package sample.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sample.Models.LoginModel;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class RegisterController {
    @FXML
    private TextField nameId;

    @FXML
    private PasswordField pasw1;

    @FXML
    private PasswordField pasw2;

    @FXML
    private Button backToLogin;

    @FXML
    private Label errorLabel;


    @FXML
    private Button regBtnR;

    @FXML
    public void backtoLgn(ActionEvent event){
        try{
            Stage stage = (Stage) this.backToLogin.getScene().getWindow();
            stage.close();
            Stage logStage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("/sample/FXML/login.fxml"));
            Scene scene = new Scene(root, 400,350);
            logStage.setScene(scene);
            logStage.setTitle("Login");
            logStage.setResizable(false);
            logStage.show();

        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    @FXML
    public void regNewUser(ActionEvent event) {
        if(this.pasw1.getText().equals(this.pasw2.getText())) {
            String sql = "INSERT INTO users(name,pasw) VALUES (?,?)";
            try {
                PreparedStatement stmnt = this.logMod.conn.prepareStatement(sql);

                stmnt.setString(1, this.nameId.getText());
                stmnt.setString(2, this.pasw1.getText());

                stmnt.execute();

                System.out.println("Reg new user");

                if(this.logMod.isLogin(this.nameId.getText(), this.pasw1.getText())){
                    System.out.println("www");
                    Stage stage = (Stage) this.regBtnR.getScene().getWindow();
                    stage.close();
                    try{
                        Stage prodStage = new Stage();
                        Parent root = FXMLLoader.load(getClass().getResource("/sample/FXML/products.fxml"));

                        Scene scene = new Scene(root);
                        prodStage.setScene(scene);
                        prodStage.setTitle("Products Dashboard");
                        prodStage.setResizable(false);
                        prodStage.show();
                    }
                    catch (IOException ex){
                        ex.printStackTrace();
                    }
                }


            } catch (SQLException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else{
            System.out.println(this.pasw1.getText());
            System.out.println(this.pasw2.getText());

            this.errorLabel.setText("Passwords are not equal!");
        }

    }

    LoginModel logMod = new LoginModel();


}
