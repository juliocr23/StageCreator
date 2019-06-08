package app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    public static Stage stage;

    @Override
    public void start(Stage primaryStage) throws Exception{


        stage = primaryStage;

        setUserAgentStylesheet(STYLESHEET_CASPIAN);

        FXMLLoader fxmlLoader = new FXMLLoader();
        Parent root = fxmlLoader.load(getClass().getResource("View.fxml").openStream());

        Scene scene = new Scene(root);
        primaryStage.setTitle("Game Engine");
        primaryStage.setScene(scene);
        primaryStage.setMaximized(true);
        primaryStage.show();

        Controller controller =  fxmlLoader.getController();
        controller.createComponents();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
