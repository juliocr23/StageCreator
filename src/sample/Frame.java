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
      //  hideCircles();

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

    private void createAndAddPoly(double x, double y, double w, double h) {

        polylines.add(getLeftTopPoly(x,y));
        polylines.add(getLeftMidPoly(x,y,h));
        polylines.add(getLeftBottomPoly(x,y,h));

        polylines.add(getRightTopPoly(x,y,w));
        polylines.add(getRightMidPoly(x,y,w,h));
        polylines.add(getRightBottomPoly(x,y,w,h));

        polylines.add(getMidBottomPoly(x,y,w,h));
        polylines.add(getMidTopPoly(x,y,w));
    }

    private void updatePoly(double x, double y, double w, double h) {

    }

    private static Polyline getLeftTopPoly(double x, double y) {

        double[] leftTopEdge = {
                x - 10, y,   //Starting point
                x, y,

                x, y,      //Ending point
                x, y-10
        };

        Polyline leftTopPoly = new Polyline(leftTopEdge);
        leftTopPoly.setStroke(Color.BLUE);

        return leftTopPoly;
    }

    private static Polyline getLeftMidPoly(double x, double y, double h) {
        double[] leftMidEdge = {

                x - 10, y+(h/2),
                x, y + (h/2),

                x, y + (h/2) - 5,
                x, y + (h/2) + 5,

        };

        Polyline leftMidPoly = new Polyline(leftMidEdge);
        leftMidPoly.setStroke(Color.BLUE);

        return leftMidPoly;
    }

    private static Polyline getLeftBottomPoly(double x, double y, double h) {


        double[] leftBottomEdge = {

                x - 10, y+h,
                x, y + h,

                x, y + h + 10,
                x, y + h
        };


        Polyline leftBottomPoly = new Polyline(leftBottomEdge);
        leftBottomPoly.setStroke(Color.BLUE);

        return leftBottomPoly;
    }

    private static Polyline getRightTopPoly(double x, double y, double w) {

        double[] rightTopEdge = {
                x + w + 10, y,
                x + w, y,

                x + w, y,
                x + w, y - 10
        };

        Polyline rightTopPoly = new Polyline(rightTopEdge);
        rightTopPoly.setStroke(Color.BLUE);

        return rightTopPoly;
    }

    private static Polyline getRightBottomPoly(double x, double y, double w, double h) {

        double[] rightBottomEdge = {
                x + w + 10, y + h,
                x + w, y + h,

                x + w, y + h + 10,
                x + w, y + h
        };


        Polyline rightBottomPoly = new Polyline(rightBottomEdge);
        rightBottomPoly.setStroke(Color.BLUE);

        return rightBottomPoly;
    }

    private static Polyline getRightMidPoly(double x, double y, double w,  double h) {

        double[] rightMidEdge = {

                x + w, y + (h/2) - 5,
                x + w, y + (h/2) + 5,

                x+w, y + (h/2),
                x + w + 10,  y + (h/2),
        };

        Polyline rightMidPoly = new Polyline(rightMidEdge);
        rightMidPoly.setStroke(Color.BLUE);

        return rightMidPoly;

    }

    private static Polyline getMidBottomPoly(double x, double y, double w, double h) {

        double[] midBottomEdge = {
                (x + (w/2)), y + h + 10,
                (x + (w/2)), y + h,

                (x + (w/2)) -5, y + h,
                (x + (w/2)) +5, y + h
        };

        Polyline midBottomPoly = new Polyline(midBottomEdge);
        midBottomPoly.setStroke(Color.BLUE);

        return midBottomPoly;
    }

    private static Polyline getMidTopPoly(double x, double y, double w) {

        double[] midTopEdge = {

                (x + (w/2)) - 5, y,
                (x + (w/2)) + 5, y,

                (x + (w/2)), y,
                (x + (w/2)), y-10
        };


        Polyline midTopPoly = new Polyline(midTopEdge);
        midTopPoly.setStroke(Color.BLUE);

        return midTopPoly;
    }
}
