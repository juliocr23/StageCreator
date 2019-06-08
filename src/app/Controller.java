package app;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Point2D;
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
import java.util.List;

//TODO: Add a slider for zooming in and out.
//TODO: Fix: When moving an image the resize goes to original, it should stay the same size.
//TODO: Clean up the add/set row/col
//TODO: Generate map file.

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

    private Frame temp;  //Use for drag and drop temporary.

    Pane pane;
    Point2D lastXY = null;



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

        createPane(width,height);
        scroll.setContent(pane);

        Insets value = new Insets(10,10,10,10);
        scroll.paddingProperty().setValue(value);
    }


    private void createPane(int width, int height){
        pane = new Pane();
        pane.setPrefSize(width,height);

        int w = 64;
        int h = 64;

        int rowOffset = 0;
        int colOffset = 0;

        for(int i = 0; i<height/64; i++) {

            for (int j = 0; j <width/64; j++) {

                Rectangle rectangle = new Rectangle(colOffset,rowOffset,w,h);
                rectangle.setFill(Color.TRANSPARENT);
                rectangle.setStroke(Color.BLACK);

                pane.getChildren().add(rectangle);

                colOffset += w;
            }
            rowOffset += h;
            colOffset = 0;
        }

        setDragAndDrop(pane);
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

        return canvas;
    }

    //MARK: Drag and Drop
    //--------------------------------------------------------------------------------------------------------------//

    private void setDragAndDrop(Node node) {

        //When moving sprites inside the GUI
        node.setOnDragDetected(event -> {

            if(!(event.getTarget() instanceof  Frame))
                return;

//            Frame on = (Frame)event.getTarget();
//            Dragboard db = on.startDragAndDrop(TransferMode.MOVE);
//
//            Image imgView = null;
//            try {
//                FileInputStream inputStream = new FileInputStream(on.file);
//                imgView = new Image(inputStream, on.getFitWidth(), on.getFitHeight(), false, true);
//                inputStream.close();
//
//                System.out.println("w: " + on.getFitWidth() + "h: " + on.getFitHeight());
//            }catch (Exception e) {
//                System.out.println(e.getMessage());
//            }
//
//            ClipboardContent cb = new ClipboardContent();
//            cb.putImage(imgView);
//            db.setContent(cb);

            event.consume();
        });

        node.setOnDragDropped(event -> {
            handleDrop(event);
        });

        node.setOnDragOver(event -> {
            event.acceptTransferModes(TransferMode.MOVE);
            event.consume();
        });


        node.setOnDragExited(event ->  {
            event.consume();
        });

        node.setOnDragDone(dragEvent -> {

        if(!dragEvent.isDropCompleted())
            if(temp != null)
                temp.setVisible(true);

            dragEvent.consume();
        });

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

            //If there's been a change drag image
            if (!source.intersects(event.getSceneX(), event.getSceneY(), 1, 1))
                    event.setDragDetect(true);

              event.consume();
        });

        node.setOnMouseReleased(event -> {
            lastXY = null;
        });
    }

    private void handleDragStart(MouseEvent event) {

        if(!(event.getSource() instanceof  Frame))
            return;

         temp = (Frame) event.getSource();
         ClipboardContent content = new ClipboardContent();

        Image imgView = null;
        try {
            FileInputStream inputStream = new FileInputStream(temp.file);
            imgView = new Image(inputStream, temp.getFitWidth(), temp.getFitHeight(), false, true);
            content.putImage(imgView);
            inputStream.close();

            System.out.println("w: " + temp.getFitWidth() + "h: " + temp.getFitHeight());
        }catch (Exception e) {
            System.out.println(e.getMessage());
        }

        Dragboard db = temp.startDragAndDrop(TransferMode.MOVE);
        db.setContent(content);
        db.setDragView(imgView);
        System.out.println(db.getDragView().getWidth() + " " + db.getDragView().getHeight());

        temp.showPoly(false);
        temp.setVisible(false);
        event.consume();
    }

    private void handleDrop(DragEvent event) {

        List<File> files = event.getDragboard().getFiles();

        if(files.size() > 0) {

            Frame newView = new Frame(files.get(0),event.getX(),event.getY());
            newView.showPoly(false);

            //Add new image and poly
            setDragAndDrop(newView);
            pane.getChildren().add(newView);
            for (PolyCircle polyCircle : newView.polyCircles.values()) {
                pane.getChildren().add(polyCircle.circle);
                pane.getChildren().add(polyCircle.polyline);
            }
        }
        else if(temp != null) {

                temp.updateLocation(event.getX(), event.getY());
                temp.setVisible(true);
                temp.showPoly(true);

                temp = null;
        }

        event.setDropCompleted(true);
        event.consume();
    }

    private void addImage(File file, double x, double y) {

            if(temp != null) {

                temp.updateLocation(x, y);
                temp.setVisible(true);
                temp.showPoly(true);

                temp = null;
            } else  {

                Frame newView = new Frame(file,x,y);
                newView.showPoly(false);

                //Add new image and poly
                setDragAndDrop(newView);
                pane.getChildren().add(newView);
                for (PolyCircle polyCircle : newView.polyCircles.values()) {
                    pane.getChildren().add(polyCircle.circle);
                    pane.getChildren().add(polyCircle.polyline);
                }
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