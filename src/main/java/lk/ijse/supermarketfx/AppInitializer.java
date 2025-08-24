package lk.ijse.supermarketfx;

import javafx.application.Application;
import javafx.concurrent.Task;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lk.ijse.supermarketfx.dao.CrudDAO;
import lk.ijse.supermarketfx.dao.DAOFactory;
import lk.ijse.supermarketfx.dao.DAOTypes;
import lk.ijse.supermarketfx.dao.SuperDAO;
import lk.ijse.supermarketfx.dao.custom.CustomerDAO;
import lk.ijse.supermarketfx.dao.custom.ItemDAO;
import lk.ijse.supermarketfx.dao.custom.OrderDAO;
import lk.ijse.supermarketfx.dao.custom.impl.CustomerDAOImpl;
import lk.ijse.supermarketfx.dao.custom.impl.ItemDAOImpl;
import lk.ijse.supermarketfx.dao.custom.impl.OrderDAOImpl;
import lk.ijse.supermarketfx.dao.custom.impl.QueryDAOImpl;
import lk.ijse.supermarketfx.entity.Customer;

public class AppInitializer extends Application {
    public static void main(String[] args) {
//        DAOFactory.getInstance().getDAO(DAOTypes.CUSTOMER);

        // Boy Girl Agreement
        // Agreement = Girl
//        SuperDAO dao = new OrderDAOImpl();
//        DAOFactory daoFactory = DAOFactory.getInstance();
//        daoFactory.getDAO(DAOTypes.CUSTOMER);
//
//        CustomerDAO customerDAO = DAOFactory.getInstance().getDAO(DAOTypes.CUSTOMER);
//        OrderDAO orderDAO = DAOFactory.getInstance().getDAO(DAOTypes.ORDER);

        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setScene(new Scene(
                new FXMLLoader(getClass().getResource("/view/LoadinScreen.fxml")).load()
        ));
        primaryStage.show();

        Task<Scene> loadingTask = new Task<>() {
            @Override
            protected Scene call() throws Exception {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/Dashboard.fxml"));
                return new Scene(fxmlLoader.load());
            }
        };

        loadingTask.setOnSucceeded(event -> {
            Scene value = loadingTask.getValue();

            primaryStage.setTitle("Dashboard");
            primaryStage.setScene(value);
        });

        loadingTask.setOnFailed(event -> {
            System.out.println("Fail to load application");
        });

        new Thread(loadingTask).start();

//        Parent parent = FXMLLoader.load(getClass().getResource("/view/Dashboard.fxml"));
//        primaryStage.setScene(new Scene(parent));
//        primaryStage.show();
    }
}
