package sample.Controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.*;
import javafx.scene.control.*;

import javafx.scene.control.cell.PropertyValueFactory;
import sample.dbConnection.DbConnection;
import sample.utils.ProductsData;
import sample.utils.productType;

import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.util.ResourceBundle;

import static sample.Models.LoginModel.*;

public class ProductsController implements Initializable {
    @FXML
    private TextField prodName;

    @FXML
    private ComboBox<productType> combo;

    @FXML
    private DatePicker datePick;

    @FXML
    private TextField prodPrice;

    @FXML
    private TextField prodQuant;

    @FXML
    private Button addProd;

    @FXML
    private Button clearForm;

    @FXML
    private Button loadData;

    @FXML
    private TableView<ProductsData> productTable;

    @FXML
    private TableColumn<ProductsData, String> nameCol;

    @FXML
    private TableColumn<ProductsData, String> typeCol;

    @FXML
    private TableColumn<ProductsData, String> dateCol;

    @FXML
    private TableColumn<ProductsData, String> priceCol;

    @FXML
    private TableColumn<ProductsData, String> quantCol;

    @FXML
    private TableColumn<ProductsData, String> userCol;

    private DbConnection dbConn ;
    private ObservableList<ProductsData> data;

    public Connection conn ;


    public void initialize(URL url, ResourceBundle rb){
        this.dbConn = new DbConnection();
        try{
            conn = DbConnection.getConnection();
        }
        catch (SQLException e){
            e.printStackTrace();
        }

        this.combo.setItems(FXCollections.observableArrayList(productType.values()));
        this.datePick.setValue(LocalDate.now());

    }


     @FXML
    private void loadProdData(ActionEvent event) {
         try {

             this.data = FXCollections.observableArrayList();

             Statement state = conn.createStatement();
             String sql = "SELECT * FROM products";

             ResultSet rs = conn.createStatement().executeQuery(sql);
             while (rs.next()) {
                 this.data.add(new ProductsData(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7)));
             }
         } catch (SQLException e) {
             System.err.println("Error " + e);
         }
         this.nameCol.setCellValueFactory(new PropertyValueFactory<ProductsData, String>("name"));
         this.typeCol.setCellValueFactory(new PropertyValueFactory<ProductsData, String>("type"));
         this.dateCol.setCellValueFactory(new PropertyValueFactory<ProductsData, String>("date"));
         this.priceCol.setCellValueFactory(new PropertyValueFactory<ProductsData, String>("price"));
         this.quantCol.setCellValueFactory(new PropertyValueFactory<ProductsData, String>("quantity"));
         this.userCol.setCellValueFactory(new PropertyValueFactory<ProductsData, String>("user"));

         this.productTable.setItems(null);
         this.productTable.setItems(this.data);
     }

     @FXML
    private void addProdData(ActionEvent event){
        String sql = "INSERT INTO products(prodName, prodType, prodDate, prodPrice, prodQuantity, userName) VALUES (?,?,?,?,?,?)";
        try{
            PreparedStatement stmnt = conn.prepareStatement(sql);

            stmnt.setString(1, this.prodName.getText());
            stmnt.setString(2, this.combo.getValue().toString());
            stmnt.setString(3, this.datePick.getValue().toString());
            stmnt.setString(4, this.prodPrice.getText());
            stmnt.setString(5, this.prodQuant.getText());
            stmnt.setString(6, CURRENT_USER);

            stmnt.execute();
            System.out.println("Add!");
        }
        catch (SQLException e){
            e.printStackTrace();
        }
     }

     @FXML
    private void clearFormMe(ActionEvent event){
        this.prodName.setText("");
        this.datePick.setValue(LocalDate.now());
        this.prodPrice.setText("");
        this.prodQuant.setText("");

     }

    @FXML
    private Button deleteItem;

    @FXML
    private void deleteSelected(ActionEvent event) {
        Integer delID = Integer.parseInt(this.productTable.getSelectionModel().getSelectedItem().getId());
        this.productTable.getItems().removeAll(
                productTable.getSelectionModel().getSelectedItems()
        );


        System.out.println(delID);

        String sql = "DELETE FROM products WHERE prodId = ?";
        try{
            PreparedStatement stmnt = conn.prepareStatement(sql);

            stmnt.setString(1,String.valueOf(delID));
            stmnt.execute();
            System.out.println("Del");
        }
        catch (SQLException e){
            e.printStackTrace();
        }

    }

    @FXML
    private PieChart pieID;

    @FXML
    private Button getPieBtn;

    private ObservableList<Integer> typeNumber;

    @FXML
    private void getDataForPie(ActionEvent event) {
        int counter = 0;
        int counter2 =0;
        int counter3 =0;
        int counter4 =0;

        try {
            Statement state = conn.createStatement();
            String sql = "SELECT * FROM products WHERE prodType='Type1'";
            String sql2 = "SELECT * FROM products WHERE prodType='Type2'";
            String sql3 = "SELECT * FROM products WHERE prodType='Type3'";
            String sql4 = "SELECT * FROM products WHERE prodType='Type4'";

            ResultSet rs = conn.createStatement().executeQuery(sql);
            ResultSet rs2 = conn.createStatement().executeQuery(sql2);
            ResultSet rs3 = conn.createStatement().executeQuery(sql3);
            ResultSet rs4 = conn.createStatement().executeQuery(sql4);

            while (rs.next()) {
                counter++;
            }
            while(rs2.next()){
                counter2++;
            }
            while(rs3.next()){
                counter3++;
            }
            while(rs4.next()){
                counter4++;
            }
        } catch (SQLException e) {
            System.err.println("Error " + e);
        }

        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(new PieChart.Data("Type1", counter),new PieChart.Data ("Type2", counter2),new PieChart.Data("Type3",counter3),new PieChart.Data("Type4",counter4));
        pieID.setData(pieChartData);

    }

    @FXML
    private BarChart<?, ?> quantityChart;

    @FXML
    private CategoryAxis xType;

    @FXML
    private NumberAxis yQuant;

    @FXML
    private BarChart<?, ?> pricesChart;

    @FXML
    private CategoryAxis xType2;

    @FXML
    private NumberAxis yPrice;

    @FXML
    private Button getBarBtn;

    @FXML
    private void getDataForBar(ActionEvent event) {
        int counter = 0;
        int counter2 =0;
        int counter3 =0;
        int counter4 =0;

        try {
            Statement state = conn.createStatement();
            String sql = "SELECT * FROM products WHERE prodType='Type1'";
            String sql2 = "SELECT * FROM products WHERE prodType='Type2'";
            String sql3 = "SELECT * FROM products WHERE prodType='Type3'";
            String sql4 = "SELECT * FROM products WHERE prodType='Type4'";

            ResultSet rs = conn.createStatement().executeQuery(sql);
            ResultSet rs2 = conn.createStatement().executeQuery(sql2);
            ResultSet rs3 = conn.createStatement().executeQuery(sql3);
            ResultSet rs4 = conn.createStatement().executeQuery(sql4);

            while (rs.next()) {
                counter = Integer.parseInt(rs.getString(6))+ counter;
            }
            while(rs2.next()){
                counter2 = Integer.parseInt(rs2.getString(6)) + counter2;
            }
            while(rs3.next()){
                counter3 = Integer.parseInt(rs3.getString(6)) + counter3;
            }
            while(rs4.next()){
                counter4 = Integer.parseInt(rs4.getString(6))+ counter4;
            }
        } catch (SQLException e) {
            System.err.println("Error " + e);
        }
        XYChart.Series set1 = new XYChart.Series<>();

        set1.getData().add(new XYChart.Data("Type1", counter));
        set1.getData().add(new XYChart.Data("Type2", counter2));
        set1.getData().add(new XYChart.Data("Type3", counter3));
        set1.getData().add(new XYChart.Data("Type4", counter4));

        quantityChart.getData().addAll(set1);

        float price = 0;
        float price2 = 0;
        float price3 = 0;
        float price4 = 0;


        try {
            Statement state = conn.createStatement();
            String sql = "SELECT * FROM products WHERE prodType='Type1'";
            String sql2 = "SELECT * FROM products WHERE prodType='Type2'";
            String sql3 = "SELECT * FROM products WHERE prodType='Type3'";
            String sql4 = "SELECT * FROM products WHERE prodType='Type4'";

            ResultSet rs = conn.createStatement().executeQuery(sql);
            ResultSet rs2 = conn.createStatement().executeQuery(sql2);
            ResultSet rs3 = conn.createStatement().executeQuery(sql3);
            ResultSet rs4 = conn.createStatement().executeQuery(sql4);

            while (rs.next()) {
                price = Float.parseFloat(rs.getString(5))+ price;
            }
            while(rs2.next()){
                price2 = Float.parseFloat(rs2.getString(5)) + price2;
            }
            while(rs3.next()){
                price3 = Float.parseFloat(rs3.getString(5)) + price3;
            }
            while(rs4.next()){
                price4 = Float.parseFloat(rs4.getString(5))+ price4;
            }
        } catch (SQLException e) {
            System.err.println("Error " + e);
        }

        XYChart.Series set2 = new XYChart.Series<>();

        set2.getData().add(new XYChart.Data("Type1", price));
        set2.getData().add(new XYChart.Data("Type2", price2));
        set2.getData().add(new XYChart.Data("Type3", price3));
        set2.getData().add(new XYChart.Data("Type4", price4));

        pricesChart.getData().addAll(set2);


    }

}
