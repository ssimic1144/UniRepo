package sample.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sample.Models.LoginModel;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
    @FXML
    private Label dbStatus;

    @FXML
    private TextField username;

    @FXML
    private PasswordField passw;

    @FXML
    private Button loginBtn;

    @FXML
    private Button regBtn;

    @FXML
    private Label wrongId;

    LoginModel logMod = new LoginModel();

    public void initialize(URL url, ResourceBundle rb){
        if(this.logMod.isDbConn()){
            this.dbStatus.setText("Connected to DB!");
            System.out.println("test!");
        }else{
            this.dbStatus.setText("Not Connected to DB!");
            System.out.println("not test");
        }
    }

    @FXML
    public void loginMethod(ActionEvent event) {
        try {
            if(this.logMod.isLogin(this.username.getText(), this.passw.getText())){
                System.out.println("www");
                Stage stage = (Stage) this.loginBtn.getScene().getWindow();
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
            else{
                System.out.println(this.username.getText());
                System.out.println(this.passw.getText());
                System.out.println("Something went wrong!");
                this.wrongId.setText("Wrong name and/or password!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void registerMethod(ActionEvent event){
        try {
            Stage stage = (Stage) this.regBtn.getScene().getWindow();
            stage.close();
            Stage regStage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("/sample/FXML/register.fxml"));
            Scene scene = new Scene(root, 400,350);
            regStage.setScene(scene);
            regStage.setTitle("Register page");
            regStage.setResizable(false);
            regStage.show();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

}
