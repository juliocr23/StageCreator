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

public class Frame extends ImageView {

    File file;

    public  final   ArrayList<Circle> circles;
    public  final   ArrayList<Polyline> polylines;
    private final   int RADIUS = 15;

    private Cursor cursor;

    public Frame(File file, double x, double y){
        super();
        try {
            FileInputStream inputStream = new FileInputStream(file);

            Image image = new Image(inputStream);
            setX(x-(image.getWidth()/2));
            setY(y-(image.getHeight()/2));
            setImage(image);

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

    //----------------------------------------------------------------------------------------------------------//
    //MARK: Resize

    //MARK: Circles
    //-----------------------------------------------------------------------------------------------------------//
    private void hideCircles(){

        for(Circle circle: circles)
            circle.setFill(Color.TRANSPARENT);
    }

    private void createAndAddCircles(double x, double y, double w, double h){

        circles.add(getLeftTopCircle(x,y));
        circles.add(getLeftBottomCircle(x,y,h));
        circles.add(getLeftMidCircle(x,y,h));

        circles.add(getRightTopCircle(x,y,w));
        circles.add(getRightMidCircle(x,y,w,h));
        circles.add(getRightBottomCircle(x,y,w,h));

        circles.add(getMidTopCircle(x,y,w));
        circles.add(getMidBottomCircle(x,y,w,h));
    }

    private Circle getLeftTopCircle(double x, double y){
       Circle circle = new Circle(x,y,RADIUS);

       circle.setOnMouseMoved(event -> {
           if(isFocused())
            circle.setCursor(Cursor.NW_RESIZE);
       });

       return circle;
    }

    private Circle getLeftMidCircle(double x, double y, double h) {
       Circle circle = new Circle(x, y + (h/2),RADIUS);

       circle.setOnMouseMoved(event -> {
            if(isFocused())
                circle.setCursor(Cursor.W_RESIZE);
       });

       return circle;
    }

    private Circle getLeftBottomCircle(double x, double y, double h) {
      Circle circle = new Circle(x, y + h, RADIUS);

      circle.setOnMouseMoved(event -> {

          if(isFocused())
            circle.setCursor(Cursor.SW_RESIZE);
      });

      return circle;
    }

    private Circle getRightTopCircle(double x, double y, double w) {
      Circle circle = new Circle(x+w, y,RADIUS);

      circle.setOnMouseMoved(event ->  {
            if(isFocused())
                circle.setCursor(Cursor.NE_RESIZE);
      });

      return circle;
    }

    private Circle getRightMidCircle(double x, double y, double w, double h) {
        Circle circle = new Circle(x+w, y + (h/2), RADIUS);

        circle.setOnMouseMoved(event ->  {
                if(isFocused())
                    circle.setCursor(Cursor.E_RESIZE);
        });

        return circle;
    }

    private Circle getRightBottomCircle(double x, double y, double w, double h) {
       Circle circle = new Circle(x + w, y+h, RADIUS);

        circle.setOnMouseMoved(event ->  {
            if(isFocused())
                circle.setCursor(Cursor.SE_RESIZE);
        });

        return circle;
    }

    private Circle getMidTopCircle(double x, double y, double w) {

        Circle circle = new Circle(x + (w/2), y, RADIUS);

        circle.setOnMouseMoved(event ->  {
            if(isFocused())
                circle.setCursor(Cursor.N_RESIZE);
        });

        return circle;
    }

    private Circle getMidBottomCircle(double x, double y, double w, double h) {

        Circle circle = new Circle(x + (w/2), y + h, RADIUS);

        circle.setOnMouseMoved(event ->  {
            if(isFocused())
                circle.setCursor(Cursor.S_RESIZE);
        });

        return circle;
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
