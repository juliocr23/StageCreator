package sample;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;

import javafx.scene.canvas.Canvas;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;

public class Controller {

    @FXML
    private ChoiceBox<String> spriteTypes;

    @FXML
    private ComboBox<String> addRowCol;

    private GridPane map;

    @FXML
    private TextField rows;

    @FXML
    private TextField cols;

    @FXML
    private AnchorPane sceneBg;

    @FXML
    private ScrollPane scroll;

    public void createComponents(){

        //Add types of sprites
        ObservableList spriteList = FXCollections.observableArrayList();
        spriteList.addAll("Tile", "Power up",
                                     "Coin","Enemy",
                                     "Player");

        spriteTypes.setItems(spriteList);
        spriteTypes.setValue("Tile");

        //Different type of adding rows and cols
        ObservableList rowColList = FXCollections.observableArrayList();
        rowColList.addAll("Add Row Above", "Add Row Below",
                                     "Add Column Before","Add Column After");
        addRowCol.setItems(rowColList);

        int height = (int)sceneBg.getHeight();
        int width  = (int)sceneBg.getWidth();

        System.out.println("h: " + height);
        System.out.println("w: " + width);

        int rows = height/64;
        int cols = width/64;

        this.rows.setText(rows + "");
        this.cols.setText(cols + "");

        createRows(rows,cols);
        createCols(rows,cols);
    }

    @FXML
    private void loadImage(){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        fileChooser.showOpenDialog(Main.stage);
    }

    @FXML
    private void setRows(ActionEvent event){

       TextField field =  (TextField) event.getSource();

        if(!isNumber(field.getText())){
            field.setText("");
            return;
        }

        //Get row input
        int rows = Integer.valueOf(field.getText());

        //Get current cols
        int cols =  map.getColumnCount();

        createRows(rows,cols);
    }

    private void createRows(int rows, int cols){
        createNewMap();
        for(int i = 0; i<rows; i++) {
            for (int j = 0; j <cols; j++) {
                replaceRowCol(i,j);
            }
        }
        scroll.setContent(map);
    }

    @FXML
    private void setCols(ActionEvent event) {
        TextField field =  (TextField) event.getSource();

        if(!isNumber(field.getText())){
            field.setText("");
            return;
        }

        //Get cols input
        int cols = Integer.valueOf(field.getText());

        //Get current cols
        int rows =  map.getRowCount();

        createCols(rows,cols);
    }

    private void createCols(int rows, int cols){

        createNewMap();
        for(int i = 0; i<rows; i++) {
            for (int j = 0; j <cols; j++) {
                replaceRowCol(i,j);
            }
        }
        scroll.setContent(map);
    }

    private void replaceRowCol(int i, int j){
        Canvas canvas = new Canvas(64,64);
        canvas.getGraphicsContext2D().setFill(Color.BLACK);
        canvas.getGraphicsContext2D().fillRect(0,0,64,64);
        GridPane.setConstraints(canvas,j,i,1,1);
        map.getChildren().add(canvas);
    }

    private void createNewMap(){
        map = new GridPane();
        map.setGridLinesVisible(true);
        map.setHgap(5);
        map.setVgap(5);
        map.setAlignment(Pos.CENTER);

        Insets value = new Insets(10,10,10,10);
        map.paddingProperty().setValue(value);
    }

    private boolean isNumber(String str) {

        if(str.isEmpty())
            return false;

        for(int i = 0; i<str.length(); i++) {
            if(!Character.isDigit(str.charAt(i)))
                return false;
        }

        return true;
    }
}
