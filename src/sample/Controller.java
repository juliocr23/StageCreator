package sample;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;

import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.FileChooser;

public class Controller {

    @FXML
    private ChoiceBox<String> spriteTypes;

    @FXML
    private MenuButton addRowCol;

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
        spriteList.addAll("Tile","background",
                                     "Power up", "Coin",
                                      "Enemy", "Player");

        spriteTypes.setItems(spriteList);
        spriteTypes.setValue("Tile");

        int height = (int)sceneBg.getHeight();
        int width  = (int)sceneBg.getWidth();

        int rows = height/64;
        int cols = width/64;

        this.rows.setText(rows + "");
        this.cols.setText(cols + "");

        createRows(rows,cols);
        createCols(rows,cols);
        scroll.setContent(map);
    }

    //MARK: Injected methods
    //--------------------------------------------------------------------------------------------------------------//
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


    @FXML
    private void selectedRowColOperation(ActionEvent event){

        MenuItem selectedItem =  (MenuItem)event.getSource();
        System.out.println("Selected new Item: " + selectedItem.getText());

       if(selectedItem.getText().equals("Add Row Above")) {
            addRowAbove();
       } else if(selectedItem.getText().equals("Add Row Below")){
            addRowBelow();

       } else if(selectedItem.getText().equals("Add Column Before")){
            addColBefore();

       }else if(selectedItem.getText().equals("Add Column After")){
            addColAfter();
       }
    }




    //MARK: Utilities functions
    //--------------------------------------------------------------------------------------------------------------//
    private void createRows(int rows, int cols){
        map = getNewMap();
        for(int i = 0; i<rows; i++) {
            for (int j = 0; j <cols; j++) {
                replaceRowCol(i,j);
            }
        }
        scroll.setContent(map);
    }

    private void createCols(int rows, int cols){

        map = getNewMap();
        for(int i = 0; i<rows; i++) {
            for (int j = 0; j <cols; j++) {
                replaceRowCol(i,j);
            }
        }
        scroll.setContent(map);
    }

    private void replaceRowCol(int i, int j){
        Canvas newCanvas = getNewCanvas(Color.BLACK);

        newCanvas.getGraphicsContext2D().setFill(Color.WHITE);
        newCanvas.getGraphicsContext2D().fillText("(" + i + "," + j + ")",0,32);
        GridPane.setConstraints(newCanvas,j,i,1,1);
        map.getChildren().add(newCanvas);
    }

    private Canvas getNewCanvas(Color color){
        Canvas canvas = new Canvas(64,64);
        canvas.getGraphicsContext2D().setFill(color);
        canvas.getGraphicsContext2D().fillRect(0,0,64,64);

        return canvas;
    }

    private GridPane getNewMap(){
        GridPane newGridPane = new GridPane();
        newGridPane.setGridLinesVisible(true);
        newGridPane.setHgap(5);
        newGridPane.setVgap(5);
        newGridPane.setAlignment(Pos.CENTER);

        Insets value = new Insets(10,10,10,10);
        newGridPane.paddingProperty().setValue(value);

        return newGridPane;
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

    private void addRowAbove(){
        
        ObservableList<Node> nodes = map.getChildren();

        Canvas newCanvas1 = getNewCanvas(Color.RED);
        Canvas newCanvas2 = getNewCanvas(Color.RED);

        map.getChildren().add(newCanvas1);
        map.getChildren().add(newCanvas2);

        int k = map.getChildren().size() -1;
        for(int row = 0; k > 0; row++) {

            for(int col =0; col<map.getColumnCount() && k > 0; col++) {

                System.out.println(k + "," + col + "," + row + "," + 1 + "," + 1);
                GridPane.setConstraints(nodes.get(k),col,row,1,1);
                k--;
            }
        }
    }

    private void addRowBelow(){
        for(int i = 0; i<map.getColumnCount(); i++)
            map.addColumn(i,getNewCanvas(Color.BLACK));
    }

    private void addColBefore(){}

    private void addColAfter(){
        for(int i = 0; i<map.getRowCount(); i++)
            map.addRow(i,getNewCanvas(Color.BLACK));
    }
}
