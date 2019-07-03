package app;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.input.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.FileChooser;
import java.io.*;
import java.util.List;

//TODO: Add a slider for zooming in and out.
//TODO: Clean up the add/set row/col
//TODO: Generate map file.
//TODO: Use getSceneX and getSceneY instead of calculating the location.
//TODO: Add map size width and height in order of 64.


/*
  When generating the file: It should contains the location, size, type for each of the sprite image, the sprites itself.
 */


public class Controller {

    @FXML
    private ChoiceBox<String> spriteTypes;

    @FXML
    private AnchorPane console;

    @FXML
    private MenuButton addRowCol;

    @FXML
    private TextField colTextField;

    @FXML
    private TextField rowTextField;

    @FXML
    private AnchorPane sceneBg;

    @FXML
    private ScrollPane scroll;

    private Pane map;
    private Point2D lastXY = null; //Delta use for image drag

    private final int CELL_SIZE = 64;
    private int rows = 0;
    private int cols = 0;


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

        rows = height/64;
        cols = width/64;

        this.rowTextField.setText(rows + "");
        this.colTextField.setText(cols + "");

        map = new Pane();
        createPane(rows,cols);
    }

    private Rectangle getCell(int c, int r, int w, int h) {
        Rectangle rectangle = new Rectangle(c,r,w,h);
        rectangle.setFill(Color.TRANSPARENT);
        rectangle.setStroke(Color.BLACK);

        return rectangle;
    }

    private void createPane(int rows, int cols){

        int rowOffset = 0;
        int colOffset = 0;

        for(int i = 0; i<rows; i++) {

            for (int j = 0; j <cols; j++) {

                map.getChildren().add(getCell(colOffset, rowOffset,CELL_SIZE,CELL_SIZE));

                colOffset += CELL_SIZE;
            }
            rowOffset += CELL_SIZE;
            colOffset = 0;
        }

        setDragAndDrop(map);
        scroll.setContent(map);

        Insets value = new Insets(10,10,10,10);
        scroll.paddingProperty().setValue(value);

        this.rows = rows;
        this.cols = cols;
    }


    //MARK: Injected methods
    //--------------------------------------------------------------------------------------------------------------//

    @FXML
    private void generateGame(){

        for(Node cell: map.getChildren()) {

            if(cell instanceof Frame) {
                Frame frame = (Frame) cell;
                System.out.print(frame.getImage());
            }
        }
    }

    @FXML
    private void loadImage(){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        fileChooser.showOpenDialog(Main.stage);
    }

    @FXML
    private void setRows(ActionEvent event){

        var newRows = Integer.parseInt(rowTextField.getText());

        map = new Pane();
        createPane(newRows,cols);
    }

    @FXML
    private void setCols(ActionEvent event) {

        var newCols = Integer.parseInt(colTextField.getText());

        map = new Pane();
        createPane(rows,newCols);
    }

    @FXML
    private void addRowAbove(ActionEvent event) {

       ObservableList<Node> newList = FXCollections.observableArrayList();

        int x = 0;
        int y = 0;

        rows++;
        for(int i = 0; i<rows; i++) {

           for(int j = 0; j<cols; j++) {

               Rectangle rect;
               if(i == 0) {   //The rows being add it above
                   rect = getCell(x,y,CELL_SIZE,CELL_SIZE);
                   rect.setFill(Color.BLUE);
                   newList.add(rect);
               }
               else { //The rows that were already there.
                   var index = (i-1)*cols + j;
                   rect = (Rectangle) map.getChildren().get(index);
                   rect.setX(x);
                   rect.setY(y);

                   newList.add(rect);
               }

               x += CELL_SIZE;
           }
           y += CELL_SIZE;
           x = 0;
       }

        map.getChildren().setAll(newList);
    }

    @FXML
    private void addRowBelow(ActionEvent event) {

        int x = 0;

        var lastRect = (Rectangle) map.getChildren().get(map.getChildren().size()-1);
        int y = (int)lastRect.getY() + CELL_SIZE;

        rows++;
        for(int i = 0; i<1; i++) {

            for(int j = 0; j<cols; j++) {

                 var rect = getCell(x,y,CELL_SIZE,CELL_SIZE);
                 rect.setFill(Color.BLUE);
                 map.getChildren().add(rect);

                x += CELL_SIZE;
            }
            y += CELL_SIZE;
            x = 0;
        }
    }

    @FXML
    private void addColBefore(ActionEvent event) {

        ObservableList<Node> newList = FXCollections.observableArrayList();

        int x = 0;
        int y = 0;

        cols++;
        for(int i = 0; i<rows; i++) {

            for(int j = 0; j<cols; j++) {

                Rectangle rect;
                if(j == 0) {
                    var index = i*cols + (j);
                    //System.out.println(index);
                    rect = getCell(x, y, CELL_SIZE, CELL_SIZE);
                    rect.setFill(Color.YELLOW);
                    newList.add(rect);
                } else {

                    var index = i*(cols-1) + (j-1);
                    System.out.println(index);
                    rect = (Rectangle) map.getChildren().get(index);
                    rect.setX(x);
                    rect.setY(y);
                    newList.add(rect);

                }

                x += CELL_SIZE;
            }

            y += CELL_SIZE;
            x = 0;
        }

        map.getChildren().setAll(newList);
    }

    @FXML
    private void addColAfter(ActionEvent event) {

    }


    @FXML
    private void selectedRowColOperation(ActionEvent event){


    }

    //MARK: Drag and Drop
    //--------------------------------------------------------------------------------------------------------------//

    private void setDragAndDrop(Node node) {
       setOutsideDragAndDrop(node);
       setInsideDragAndDrop(node);
    }

    private void setOutsideDragAndDrop(Node node) {

        //Handle the object being drop
        node.setOnDragDropped(event -> {
            handleOutsideDrop(event);
        });

        //Accept object being drop
        node.setOnDragOver(event -> {
            event.acceptTransferModes(TransferMode.MOVE);
            event.consume();
        });

        //What happens when drag is done from outside
        node.setOnDragDone(dragEvent -> {
            dragEvent.consume();
        });
    }

    private void handleOutsideDrop(DragEvent event) {

        List<File> files = event.getDragboard().getFiles();

        if(files.size() > 0) {

            Frame newView = new Frame(files.get(0),event.getX(),event.getY());
            newView.showPoly(false);

            //Add new image and poly
            setDragAndDrop(newView);
            map.getChildren().add(newView);
            for (PolyCircle polyCircle : newView.polyCircles.values()) {
                map.getChildren().add(polyCircle.circle);
                map.getChildren().add(polyCircle.polyline);
            }
        }

        event.setDropCompleted(true);
        event.consume();
    }

    private void setInsideDragAndDrop(Node node) {

        node.setOnMouseDragged(event -> {

            if(!(event.getSource() instanceof  Frame))
                return;

            event.setDragDetect(false);

            Frame source = (Frame) event.getSource();

            //Get initial last  location of mouse
            if (lastXY == null)
                lastXY = new Point2D(event.getSceneX(), event.getSceneY());

            //Get the deltaX and deltaY of the mouse
            double dx = event.getSceneX() - lastXY.getX();
            double dy = event.getSceneY() - lastXY.getY();

            //Update location of current Frame
            source.updateLocation(source.getX() + dx, source.getY() + dy);

            //Record last Location of mouse
            lastXY = new Point2D(event.getSceneX(), event.getSceneY());


            //Make moving image transparent
            source.setOpacity(0.9);
            source.showPoly(false);

            event.consume();
        });

        node.setOnMouseReleased(event -> {
            lastXY = null;

            if(!(event.getSource() instanceof  Frame))
                return;

            Frame source = (Frame) event.getSource();

            source.setOpacity(1.0);
            source.requestFocus();
        });
    }

    //MARK: Utility functions
    //----------------------------------------------------------------------------------------------------------------//
    private boolean isNumber(String str) {

        if (str.isEmpty())
            return false;

        for (int i = 0; i < str.length(); i++) {
            if (!Character.isDigit(str.charAt(i)))
                return false;
        }

        return true;
    }

    private int getIndex(int row, int col, int w) {
        return (w*row + col + 1);
    }
}
