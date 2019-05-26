package sample;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.*;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.FileChooser;
import java.io.*;
import java.util.Arrays;
import java.util.List;

//TODO: Add a slider for zooming in and out.

public class Controller {

    @FXML
    private ChoiceBox<String> spriteTypes;

    @FXML
    private AnchorPane console;

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

    private Cell temp;  //Use for drag and drop temporary.

    Canvas canvas;

    Pane pane;

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

        pane = new Pane();
        pane.setPrefSize(width,height);


        Rectangle rectangle = new Rectangle(0,0,64,64);
        rectangle.setFill(Color.TRANSPARENT);
        rectangle.setStroke(Color.BLACK);


        pane.getChildren().add(rectangle);
        //createRows(rows,cols);
        //createCols(rows,cols);

        //createCanvas(width,height);
         scroll.setContent(pane);

        Insets value = new Insets(10,10,10,10);
        scroll.paddingProperty().setValue(value);
    }

    

    private void createCanvas(int width, int height) {

        canvas = new Canvas(width,height);
        canvas.getGraphicsContext2D().setFill(Color.BLACK);
        canvas.getGraphicsContext2D().fillRect(0,0,width,height);
        canvas.getGraphicsContext2D().setStroke(Color.WHITE);

        int w = 64;
        int h = 64;

        int rowOffset = 0;
        int colOffset = 0;

        for(int i = 0; i<height/64; i++) {

            for (int j = 0; j <width/64; j++) {
               canvas.getGraphicsContext2D().strokeRect(10 + colOffset,10 + rowOffset,w,h);
               colOffset += w;
            }
            rowOffset += h;
            colOffset = 0;
        }

        setDragAndDrop(canvas);
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
//
//       TextField field =  (TextField) event.getSource();
//
//        if(!isNumber(field.getText())){
//            field.setText("");
//            return;
//        }
//
//        //Get row input
//        int rows = Integer.valueOf(field.getText());
//
//        //Get current cols
//        int cols =  map.getColumnCount();
//
//        createRows(rows,cols);
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

    //MARK: Creation of Rows and Cols
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

        Canvas canvas = new Cell(64,64, color);
        setDragAndDrop(canvas);

        return canvas;
    }

    //MARK: Drag and Drop
    //--------------------------------------------------------------------------------------------------------------//
    private void setDragAndDrop(Canvas canvas) {

        //When moving sprites inside the GUI
        canvas.setOnDragDetected(event -> {

            temp = (Cell) event.getSource();
            ClipboardContent content = new ClipboardContent();
            content.putFiles(Arrays.asList(temp.getFile()));

            Dragboard db = temp.startDragAndDrop(TransferMode.MOVE);
            db.setContent(content);

            event.consume();
        });

        canvas.setOnDragDropped(event -> {

            System.out.println(event.getPickResult().getIntersectedNode());
            List<File> files = event.getDragboard().getFiles();

            int x = (int)(event.getX()/64)* 64 + 10;
            int y = (int)(event.getY()/64)* 64 + 10;

            System.out.println(x);
            System.out.println(y);

            if(files.size() > 0) {
                addImage(files.get(0),x,y);
            }

            event.setDropCompleted(true);
            event.consume();


//            Cell cell = (Cell) event.getSource();
//            if(files.size() > 0) {
//
//                cell.setImageData(files.get(0));
//                cell.drawCell(0,0);
//
//                //If drop is from within UI
//                if(temp != null) {
//                    temp.clearCell();
//                    temp = null;
//                }
//            }
//

        });

        canvas.setOnDragOver(event -> {
            event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
            event.consume();
        });

        canvas.setOnDragExited(event -> event.consume());
    }


    private void addImage(File file, double x, double y) {

        try {
            FileInputStream inputStream = new FileInputStream(file);
            Image image = new Image(inputStream);
            canvas.getGraphicsContext2D().drawImage(image,x,y);

            inputStream.close();
        }catch (Exception e) {
            System.out.println(e.getMessage());
        }
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

    //MARK: Add Rows and Cols
    //--------------------------------------------------------------------------------------------------------------//
    private void addRowAbove(){

        var nodes = map.getChildren();
        int cols = map.getColumnCount();
        int size = nodes.size();

        //Add new canvas above
        for(int i = 0; i<cols; i++) {
            Canvas newCanvas = getNewCanvas(Color.RED);
            GridPane.setConstraints(newCanvas, i, 0, 1, 1);
            map.getChildren().add(newCanvas);
        }

        //Shift rows constraints by 1
        for(int i = 1; i<size; i++) {
            int r = GridPane.getRowIndex(nodes.get(i)) + 1;
            int c = GridPane.getColumnIndex(nodes.get(i));
            GridPane.setConstraints(nodes.get(i),c,r,1,1);
        }
        fixMatrix();

        int rows = Integer.valueOf(this.rows.getText()) + 1;
        this.rows.setText(rows + "");
    }

    private void addRowBelow(){

        int cols = map.getColumnCount();
        int rows = map.getRowCount();

        //Add new canvas below
        for(int i = 0; i<cols; i++) {
            Canvas newCanvas = getNewCanvas(Color.GREEN);
            GridPane.setConstraints(newCanvas, i, rows, 1, 1);
            map.getChildren().add(newCanvas);
        }

        rows = Integer.valueOf(this.rows.getText()) + 1;
        this.rows.setText(rows + "");
    }

    private void addColBefore(){

        var nodes = map.getChildren();
        int rows = map.getRowCount();
        int size = nodes.size();

        //Add new canvas to the left
        for(int i = 0; i<rows; i++) {
            Canvas newCanvas = getNewCanvas(Color.YELLOW);
            GridPane.setConstraints(newCanvas, 0, i, 1, 1);
            map.getChildren().add(newCanvas);
        }

        //Shift columns constraints by 1
        for(int i = 1; i<size; i++) {
            int r = GridPane.getRowIndex(nodes.get(i));
            int c = GridPane.getColumnIndex(nodes.get(i)) + 1;
            GridPane.setConstraints(nodes.get(i),c,r,1,1);
        }

        fixMatrix();

        rows = Integer.valueOf(this.cols.getText()) + 1;
        this.cols.setText(rows+"");
    }

    private void addColAfter(){

        int cols = map.getColumnCount();
        int rows = map.getRowCount();

        for(int i = 0; i< rows; i++) {
              Canvas newCanvas = getNewCanvas(Color.BLUE);
              GridPane.setConstraints(newCanvas, cols, i, 1, 1);
              map.getChildren().add(newCanvas);
        }

        fixMatrix();

        rows = Integer.valueOf(this.cols.getText()) + 1;
        this.cols.setText(rows+"");
    }

    private void fixMatrix(){

       var nodes = map.getChildren();
       var cols = map.getColumnCount();

       Node matrix[] = new Node[nodes.size()];

       matrix[0] = nodes.get(0);
       for(int i = 1; i<nodes.size(); i++) {

           //Get the row, col where i is.
           int r = GridPane.getRowIndex(nodes.get(i));
           int c = GridPane.getColumnIndex(nodes.get(i));

           //Get the index where i is supposed to be.
           int index = getIndex(r,c,cols);

           if(matrix[index] == null)
                matrix[index] = nodes.get(i);
           else
               System.out.println("is not null");
       }

       nodes.clear();
       for(int i = 0; i<matrix.length; i++)
           nodes.add(matrix[i]);
    }

    private int getIndex(int row, int col, int w) {
        return (w*row + col + 1);
    }
}
