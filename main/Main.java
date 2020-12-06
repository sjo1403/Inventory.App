package Main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import model.InHouse;
import model.Inventory;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{ //loads the main screen
        Parent root = FXMLLoader.load(getClass().getResource("/view/MainScreen.fxml"));
        primaryStage.setTitle("Inventory Management System");
        primaryStage.setScene(new Scene(root, 840, 440));
        primaryStage.show();
    }

    public static void main(String[] args) { //provides sample part data
        InHouse examplePart1 = new InHouse(3852,"mouse",18.50,46,1,50,61005);
        InHouse examplePart2 = new InHouse(4024,"speaker",37.00,10,2,24,61005);
        Inventory.addPart(examplePart1);
        Inventory.addPart(examplePart2);

        launch(args);
    }

    }
