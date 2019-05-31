package sample;

import javafx.event.Event;
import javafx.scene.Cursor;
import javafx.scene.ImageCursor;
import javafx.scene.image.Image;

import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polyline;

import java.beans.EventHandler;
import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Random;

public class Frame extends ImageView {

    File file;

    public  final   ArrayList<Circle> circles;
    public  final   ArrayList<Polyline> polylines;
    private final   int RADIUS = 15;

    private double draggedOffset = 0;

    public Frame(File file, double x, double y){
        super();
        try {
            FileInputStream inputStream = new FileInputStream(file);

            Image image = new Image(inputStream);
            setX(x-(image.getWidth()/2));
            setY(y-(image.getHeight()/2));
            setImage(image);

            setFitWidth(image.getWidth());
            setFitHeight(image.getHeight());
            setPreserveRatio(false);

            this.file = file;
        }catch (Exception e) {
            System.out.println(e.getMessage());
        }

        double  imgX = getX();
        double  imgY = getY();
        double  imgW = getImage().getWidth();
        double  imgH = getImage().getHeight();

        polylines = new ArrayList<>();
        createAndAddPoly(imgX,imgY,imgW,imgH);

        circles = new ArrayList<>();
        createAndAddCircles(imgX,imgY,imgW,imgH);
        hideCircles();

        setOnMousePressed(EventHandler-> {
            showPoly(true);
            setFocused(true);
        });

//
//        leftTopPoly.setOnMouseMoved(event-> {
////            //Will change the cursor and enable the resize
//////
//////            System.out.println(event.getX());
//////            System.out.println(event.getY());
////
////           //if(leftTopPoly.contains(event.getX() + 5,event.getSceneY()))
////                leftTopPoly.setCursor(Cursor.NW_RESIZE);
//        });
//
//        leftTopPoly.setOnMouseDragged(EventHandler-> {
//            //Will do the resize
//        });



    }

    private void createAndAddCircles(double x, double y, double w, double h){

        Circle leftTopcircle = new Circle();
        setLeftTopCircle(leftTopcircle,x,y);
        setLeftTopCircleListener(leftTopcircle);
        circles.add(leftTopcircle);

        Circle leftMidCircle = new Circle();
        setLeftMidCircle(leftMidCircle,x,y,h);
        setLeftMidCircleListener(leftMidCircle);
        circles.add(leftMidCircle);

        Circle leftBottomCircle = new Circle();
        setLeftBottomCircle(leftBottomCircle,x,y,h);
        setLeftBottomCircleListener(leftBottomCircle);
        circles.add(leftBottomCircle);

        Circle rightTopCircle = new Circle();
        setRightTopCircle(rightTopCircle,x, y, w);
        setRightTopCircleListener(rightTopCircle);
        circles.add(rightTopCircle);

        Circle rightMidCircle = new Circle();
        setRightMidCircle(rightMidCircle, x, y, w, h);
        setRightMidCircleListener(rightMidCircle);
        circles.add(rightMidCircle);

        Circle rightBottomCircle = new Circle();
        setRightBottomCircle(rightBottomCircle,x,y,w,h);
        setRightBottomCircleListener(rightBottomCircle);
        circles.add(rightBottomCircle);

        Circle midTopCircle = new Circle();
        setMidTopCircle(midTopCircle,x,y,w);
        setMidTopCircleListener(midTopCircle);
        circles.add(midTopCircle);

        Circle midBottomCircle = new Circle();
        setMidBottomCircle(midBottomCircle,x,y,w,h);
        setMidBottomCircleListener(midBottomCircle);
        circles.add(midBottomCircle);
    }

    //----------------------------------------------------------------------------------------------------------//
    //MARK: Resize

    //MARK: Circles Location
    //-----------------------------------------------------------------------------------------------------------//
    private void hideCircles(){

        for(Circle circle: circles)
            circle.setFill(Color.TRANSPARENT);
    }

    private void setLeftTopCircle(Circle circle, double x, double y) {
        circle.setCenterX(x);
        circle.setCenterY(y);
        circle.setRadius(RADIUS);
    }

    private void setLeftMidCircle(Circle circle, double x, double y, double h){
        circle.setRadius(RADIUS);
        circle.setCenterX(x);
        circle.setCenterY(y + (h/2));
    }

    private void setLeftBottomCircle(Circle circle, double x, double y, double h) {
        circle.setRadius(RADIUS);
        circle.setCenterX(x);
        circle.setCenterY(y + h);
    }

    private void setRightTopCircle(Circle circle, double x, double y, double w) {
        circle.setRadius(RADIUS);
        circle.setCenterX(x+w);
        circle.setCenterY(y);
    }

    private void setRightMidCircle(Circle circle, double x, double y, double w, double h) {
        circle.setRadius(RADIUS);
        circle.setCenterX(x+w);
        circle.setCenterY(y + (h/2));
    }

    private void setRightBottomCircle(Circle circle, double x, double y, double w, double h) {
        circle.setRadius(RADIUS);
        circle.setCenterX(x + w);
        circle.setCenterY(y + h);
    }

    private void setMidTopCircle(Circle circle, double x, double y, double w) {
        circle.setRadius(RADIUS);
        circle.setCenterX(x + (w/2));
        circle.setCenterY(y);
    }

    private void setMidBottomCircle(Circle circle, double x, double y, double w, double h) {
        circle.setRadius(RADIUS);
        circle.setCenterX(x + (w/2));
        circle.setCenterY(y + h);
    }

    //MARK: Circles Listener
    //---------------------------------------------------------------------------------------------------------------//

    private void setLeftTopCircleListener(Circle circle) {

        circle.setOnMouseMoved(event -> {
            if(isFocused())
                circle.setCursor(Cursor.NW_RESIZE);
        });

        //TODO: Resize
        circle.setOnMouseDragged(event -> {
            System.out.println("Resizing");
        });
    }

    private void setLeftMidCircleListener(Circle circle){
        circle.setOnMouseMoved(event -> {
            if(isFocused())
                circle.setCursor(Cursor.W_RESIZE);
        });
    }

    private void setLeftBottomCircleListener(Circle circle) {
        circle.setOnMouseMoved(event -> {

            if(isFocused())
                circle.setCursor(Cursor.SW_RESIZE);
        });
    }

    private void setRightTopCircleListener(Circle circle) {
        circle.setOnMouseMoved(event ->  {
            if(isFocused())
                circle.setCursor(Cursor.NE_RESIZE);
        });
    }

    private void setRightMidCircleListener(Circle circle) {

        circle.setOnMouseMoved(event ->  {
            if(isFocused())
                circle.setCursor(Cursor.E_RESIZE);
        });
    }

    private void setRightBottomCircleListener(Circle circle) {
        circle.setOnMouseMoved(event ->  {
            if(isFocused())
                circle.setCursor(Cursor.SE_RESIZE);
        });
    }

    private void setMidTopCircleListener(Circle circle) {
        circle.setOnMouseMoved(event ->  {
            if(isFocused())
                circle.setCursor(Cursor.N_RESIZE);
        });
    }

    private void setMidBottomCircleListener(Circle circle) {

        circle.setOnMouseMoved(event ->  {
            if(isFocused())
                circle.setCursor(Cursor.S_RESIZE);
        });
    }

    //MARK: Poly
    //----------------------------------------------------------------------------------------------------------------//

    public void showPoly(boolean flag) {

        for(Polyline poly: polylines)
            poly.setVisible(flag);
    }


    //MARK: Poly Location
    //-----------------------------------------------------------------------------------------------------------//

    private void createAndAddPoly(double x, double y, double w, double h) {

        Polyline leftTopPoly = new Polyline();
        setLeftTopPoly(leftTopPoly,x,y);
        polylines.add(leftTopPoly);

        Polyline leftMidPoly = new Polyline();
        setLeftMidPoly(leftMidPoly,x,y,h);
        polylines.add(leftMidPoly);

        Polyline leftBottomPoly = new Polyline();
        setLeftBottomPoly(leftBottomPoly,x,y,h);
        polylines.add(leftBottomPoly);

        Polyline rightTopPoly = new Polyline();
        setRightTopPoly(rightTopPoly,x,y,w);
        polylines.add(rightTopPoly);

        Polyline rightMidPoly = new Polyline();
        setRightMidPoly(rightMidPoly,x,y,w,h);
        polylines.add(rightMidPoly);

        Polyline rightBottomPoly = new Polyline();
        setRightBottomPoly(rightBottomPoly,x,y,w,h);
        polylines.add(rightBottomPoly);

        Polyline midTopPoly = new Polyline();
        setMidTopPoly(midTopPoly,x,y,w);
        polylines.add(midTopPoly);

        Polyline midBottomPoly = new Polyline();
        setMidBottomPoly(midBottomPoly,x,y,w,h);
        polylines.add(midBottomPoly);
    }

    private static void setLeftTopPoly(Polyline polyline, double x, double y) {

        Double[] leftTopEdge = {
                x - 10, y,   //Starting point
                x, y,

                x, y,      //Ending point
                x, y-10
        };

        polyline.getPoints().clear();
        polyline.getPoints().addAll(leftTopEdge);
        polyline.setStroke(Color.BLUE);
    }

    private static void setLeftMidPoly(Polyline polyline, double x, double y, double h) {
        Double[] leftMidEdge = {

                x - 10, y+(h/2),
                x, y + (h/2),

                x, y + (h/2) - 5,
                x, y + (h/2) + 5,

        };

        polyline.getPoints().clear();
        polyline.getPoints().addAll(leftMidEdge);
        polyline.setStroke(Color.BLUE);
    }

    private static void setLeftBottomPoly(Polyline polyline, double x, double y, double h) {


        Double[] leftBottomEdge = {

                x - 10, y+h,
                x, y + h,

                x, y + h + 10,
                x, y + h
        };


        polyline.getPoints().clear();
        polyline.getPoints().addAll(leftBottomEdge);
        polyline.setStroke(Color.BLUE);
    }

    private static void setRightTopPoly(Polyline polyline, double x, double y, double w) {

        Double[] rightTopEdge = {
                x + w + 10, y,
                x + w, y,

                x + w, y,
                x + w, y - 10
        };

        polyline.getPoints().clear();
        polyline.getPoints().addAll(rightTopEdge);
        polyline.setStroke(Color.BLUE);
    }

    private static void setRightBottomPoly(Polyline polyline, double x, double y, double w, double h) {

        Double[] rightBottomEdge = {
                x + w + 10, y + h,
                x + w, y + h,

                x + w, y + h + 10,
                x + w, y + h
        };


        polyline.getPoints().clear();
        polyline.getPoints().addAll(rightBottomEdge);
        polyline.setStroke(Color.BLUE);
    }

    private static void setRightMidPoly(Polyline polyline, double x, double y, double w,  double h) {

        Double[] rightMidEdge = {

                x + w, y + (h/2) - 5,
                x + w, y + (h/2) + 5,

                x+w, y + (h/2),
                x + w + 10,  y + (h/2),
        };

        polyline.getPoints().clear();
        polyline.getPoints().addAll(rightMidEdge);
        polyline.setStroke(Color.BLUE);
    }

    private static void setMidBottomPoly(Polyline polyline, double x, double y, double w, double h) {

        Double[] midBottomEdge = {
                (x + (w/2)), y + h + 10,
                (x + (w/2)), y + h,

                (x + (w/2)) -5, y + h,
                (x + (w/2)) +5, y + h
        };

        polyline.getPoints().clear();
        polyline.getPoints().addAll(midBottomEdge);
        polyline.setStroke(Color.BLUE);
    }

    private static void setMidTopPoly(Polyline polyline, double x, double y, double w) {

        Double[] midTopEdge = {

                (x + (w/2)) - 5, y,
                (x + (w/2)) + 5, y,

                (x + (w/2)), y,
                (x + (w/2)), y-10
        };

        polyline.getPoints().clear();
        polyline.getPoints().addAll(midTopEdge);
        polyline.setStroke(Color.BLUE);
    }
}
