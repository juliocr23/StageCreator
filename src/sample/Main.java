package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.ArrayList;

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

    public static void test(){
        ArrayList<ArrayList<Integer>> matrix = new ArrayList<>();

        ArrayList<Integer> temp = new ArrayList<Integer>();

        temp.add(1);
        temp.add(2);
        temp.add(3);
        matrix.add(temp);


        ArrayList<Integer> temp2 = new ArrayList<>();
        temp2.add(4);
        temp2.add(5);
        temp2.add(6);
        matrix.add(temp2);

        for(int i = 0; i<matrix.size(); i++) {
            for(int j = 0; j<matrix.get(i).size(); j++) {
                System.out.print(matrix.get(i).get(j) + " ");
            }
            System.out.println();
        }


    }

    public static void main(String[] args) {
        launch(args);
    }


}
