package it.unicam.cs.mpgc.rpg126763;

import it.unicam.cs.mpgc.rpg126763.ui.MainWindowController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("MainWindow.fxml"));
        Parent root = loader.load();

        MainWindowController controller = loader.getController();
        controller.setPrimaryStage(primaryStage);


        Scene scene = new Scene(root);
        primaryStage.setTitle("LindenRPG");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}