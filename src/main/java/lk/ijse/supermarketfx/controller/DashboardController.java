package lk.ijse.supermarketfx.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import lk.ijse.supermarketfx.db.DBConnection;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class DashboardController implements Initializable {
    public AnchorPane ancMainContainer;
    public Button btnCus;
    public Button btnItem;
    public Button btnOrder;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
//        if(user.role.exjd){
//        btnItem.setVisible(false);
//        btnItem.setDisable(false);
//    }
//        System.out.println("Dashboard loaded, I'm initialize");
        navigateTo("/view/CustomerPage.fxml");
    }

    public void btnCustomerOnAction(ActionEvent actionEvent) throws IOException {
        navigateTo("/view/CustomerPage.fxml");
    }

    public void btnItemOnAction(ActionEvent actionEvent) {
        navigateTo("/view/ItemPage.fxml");
    }

    public void btnOrderOnAction(ActionEvent actionEvent) {
        navigateTo("/view/OrderPage.fxml");
    }

    private void navigateTo(String path) {
        try {
            ancMainContainer.getChildren().clear();

            AnchorPane anchorPane = FXMLLoader.load(getClass().getResource(path));

            anchorPane.prefWidthProperty().bind(ancMainContainer.widthProperty());
            anchorPane.prefHeightProperty().bind(ancMainContainer.heightProperty());

            ancMainContainer.getChildren().add(anchorPane);
        } catch (IOException e) {
            new Alert(Alert.AlertType.ERROR, "Page not found..!").show();
            e.printStackTrace();
        }
    }

    public void btnMainOnAction(ActionEvent actionEvent) {
//        navigateTo("/view/SendMailPage.fxml");
    }
}
