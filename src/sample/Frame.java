package sample;

import javafx.scene.Cursor;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polyline;
import java.io.File;
import java.io.FileInputStream;
import java.util.HashMap;

public class Frame extends ImageView {

    File file;
    public  final   HashMap<Circle,PolyCircle> polyCircles;
    private final   int RADIUS = 15;

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

        polyCircles = new HashMap<>();
        createPolyCircles(imgX,imgY,imgW,imgH);

        setOnMousePressed(EventHandler-> {
            showPoly(true);
            setFocused(true);
        });
    }

    private void createPolyCircles(double x, double y, double w, double h){

        PolyCircle temp;

        //Left Top Conner
        Circle leftTopcircle = new Circle();
        setLeftTopCircle(leftTopcircle,x,y);
        setCircleListener(leftTopcircle);

        Polyline leftTopPoly = new Polyline();
        setLeftTopPoly(leftTopPoly,x,y);
        temp = new PolyCircle("NW",leftTopcircle,leftTopPoly);
        polyCircles.put(leftTopcircle,temp);

        //Left mid conner
        Circle leftMidCircle = new Circle();
        setLeftMidCircle(leftMidCircle,x,y,h);
        setCircleListener(leftMidCircle);

        Polyline leftMidPoly = new Polyline();
        setLeftMidPoly(leftMidPoly,x,y,h);
        temp = new PolyCircle("W",leftMidCircle,leftMidPoly);
        polyCircles.put(leftMidCircle,temp);

        //Left bottom conner
        Circle leftBottomCircle = new Circle();
        setLeftBottomCircle(leftBottomCircle,x,y,h);
        setCircleListener(leftBottomCircle);

        Polyline leftBottomPoly = new Polyline();
        setLeftBottomPoly(leftBottomPoly,x,y,h);
        temp = new PolyCircle("SW",leftBottomCircle,leftBottomPoly);
        polyCircles.put(leftBottomCircle,temp);

        //Right top conner
        Circle rightTopCircle = new Circle();
        setRightTopCircle(rightTopCircle,x, y, w);
        setCircleListener(rightTopCircle);

        Polyline rightTopPoly = new Polyline();
        setRightTopPoly(rightTopPoly,x,y,w);
        temp = new PolyCircle("NE",rightTopCircle,rightTopPoly);
        polyCircles.put(rightTopCircle,temp);

        //Right mid conner
        Circle rightMidCircle = new Circle();
        setRightMidCircle(rightMidCircle, x, y, w, h);
        setCircleListener(rightMidCircle);

        Polyline rightMidPoly = new Polyline();
        setRightMidPoly(rightMidPoly,x,y,w,h);
        temp = new PolyCircle("E",rightMidCircle,rightMidPoly);
        polyCircles.put(rightMidCircle,temp);

        //Right bottom conner
        Circle rightBottomCircle = new Circle();
        setRightBottomCircle(rightBottomCircle,x,y,w,h);
        setCircleListener(rightBottomCircle);

        Polyline rightBottomPoly = new Polyline();
        setRightBottomPoly(rightBottomPoly,x,y,w,h);
        temp = new PolyCircle("SE",rightBottomCircle,rightBottomPoly);
        polyCircles.put(rightBottomCircle,temp);

        //Mid top conner
        Circle midTopCircle = new Circle();
        setMidTopCircle(midTopCircle,x,y,w);
        setCircleListener(midTopCircle);

        Polyline midTopPoly = new Polyline();
        setMidTopPoly(midTopPoly,x,y,w);
        temp = new PolyCircle("N",midTopCircle,midTopPoly);
        polyCircles.put(midTopCircle,temp);

        //Mid bottom conner
        Circle midBottomCircle = new Circle();
        setMidBottomCircle(midBottomCircle,x,y,w,h);
        setCircleListener(midBottomCircle);

        Polyline midBottomPoly = new Polyline();
        setMidBottomPoly(midBottomPoly,x,y,w,h);
        temp = new PolyCircle("S",midBottomCircle,midBottomPoly);
        polyCircles.put(midBottomCircle,temp);
    }

    //----------------------------------------------------------------------------------------------------------//
    //MARK: Resize

    //MARK: Circles Location
    //-----------------------------------------------------------------------------------------------------------//
    private void hideCircles(){

        for(PolyCircle polyCircle: polyCircles.values())
            polyCircle.circle.setFill(Color.TRANSPARENT);
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

    private void setCircleListener(Circle circle) {
        circle.setOnMouseMoved(event -> {

            Circle src = (Circle) event.getSource();
            if(isFocused()) {

              PolyCircle polyCircle = polyCircles.get(src);

              if(polyCircle.id.equals("N")) {
                    polyCircle.circle.setCursor(Cursor.N_RESIZE);
              } else if(polyCircle.id.equals("S")) {
                  polyCircle.circle.setCursor(Cursor.S_RESIZE);
              } else if(polyCircle.id.equals("E")){
                  polyCircle.circle.setCursor(Cursor.E_RESIZE);
              } else if(polyCircle.id.equals("SE")) {
                  polyCircle.circle.setCursor(Cursor.SE_RESIZE);
              } else if(polyCircle.id.equals("NE")) {
                  polyCircle.circle.setCursor(Cursor.NE_RESIZE);
              } else if(polyCircle.id.equals("W")) {
                  polyCircle.circle.setCursor(Cursor.W_RESIZE);
              } else if(polyCircle.id.equals("SW")) {
                  polyCircle.circle.setCursor(Cursor.SW_RESIZE);
              } else if(polyCircle.id.equals("NW")) {
                  polyCircle.circle.setCursor(Cursor.NW_RESIZE);
              }
            }
        });

        circle.setOnMouseDragged(event -> {

            double newX = event.getX();
            double newY = event.getY();


            Circle src = (Circle) event.getSource();
            PolyCircle polySrc = polyCircles.get(src);

            //Resize
            resizeImage(polySrc,newX,newY);

            //Location
           // updatePolyCircle(newX,newY,getFitWidth(),getFitHeight());

          //  setX(newX);
           // setY(newY);

        });
    }


    private void updatePolyCircle(double x, double y, double w, double h) {

        for(PolyCircle polyCircle: polyCircles.values()) {

            if (polyCircle.id.equals("N")) {
                setMidTopPoly(polyCircle.polyline,x,y,w);
                setMidTopCircle(polyCircle.circle,x, y, w);
            } else if (polyCircle.id.equals("S")) {
                setMidBottomPoly(polyCircle.polyline, x, y, w,h);
                setMidBottomCircle(polyCircle.circle,x, y, w,h);
            } else if (polyCircle.id.equals("E")) {
                setRightMidPoly(polyCircle.polyline,x, y, w, h);
                setRightMidCircle(polyCircle.circle,x, y, w, h);
            } else if (polyCircle.id.equals("SE")) {
                setRightBottomPoly(polyCircle.polyline, x, y, w, h);
                setRightBottomCircle(polyCircle.circle, x,y,w, h);
            } else if (polyCircle.id.equals("NE")) {
                setRightTopPoly(polyCircle.polyline, x, y, w);
                setRightTopCircle(polyCircle.circle, x, y, w);
            } else if (polyCircle.id.equals("W")) {
                setLeftMidPoly(polyCircle.polyline, x, y, h);
                setLeftMidCircle(polyCircle.circle, x, y, h);
            } else if (polyCircle.id.equals("SW")) {
                setLeftBottomPoly(polyCircle.polyline, x, y, h);
                setLeftBottomCircle(polyCircle.circle, x, y, h);
            } else if (polyCircle.id.equals("NW")) {
                setLeftTopPoly(polyCircle.polyline,x, y);
                setLeftTopCircle(polyCircle.circle,x , y);
            }
        }
    }

    private void resizeImage(PolyCircle polyCircle, double x, double y) {

        double yOffset;
        double xOffset;

        if (polyCircle.id.equals("N")) {

            yOffset = getY() - y;
            setFitHeight(getFitHeight()+yOffset);

            updatePolyCircle(getX(),y, getFitWidth(),getFitHeight() );
            setY(y);
        }
        else if (polyCircle.id.equals("S")) {

            yOffset = y - (getY() + getFitHeight());
            setFitHeight(getFitHeight() + yOffset);

            updatePolyCircle(getX(),getY(), getFitWidth(),getFitHeight());
        }
        else if (polyCircle.id.equals("E")) {

            xOffset = x - (getX() + getFitWidth());
            setFitWidth(getFitWidth() + xOffset);

            updatePolyCircle(getX(),getY(),getFitWidth(),getFitHeight());
        }
        else if (polyCircle.id.equals("SE")) {

            xOffset = x - (getX() + getFitWidth());
            yOffset = y - (getY() + getFitHeight());

            setFitWidth(getFitWidth() + xOffset);
            setFitHeight(getFitHeight() + yOffset);

            updatePolyCircle(getX(),getY(),getFitWidth(),getFitHeight());
        }
        else if (polyCircle.id.equals("NE")) {

            xOffset = x -  (getX() + getFitWidth());
            yOffset = getY() - y;

            setFitHeight(getFitHeight() + yOffset);
            setFitWidth(getFitWidth() + xOffset);

            updatePolyCircle(getX(),y, getFitWidth(), getFitHeight());
            setY(y);
        }
        else if (polyCircle.id.equals("W")) {
            xOffset = getX()-x;

            setFitWidth(getFitWidth() + xOffset);

            updatePolyCircle(x, getY(),getFitWidth(),getFitHeight());
            setX(x);
        }
        else if (polyCircle.id.equals("SW")) {

            xOffset = getX() - x;
            yOffset = y - (getY() + getFitHeight());

            setFitWidth(getFitWidth() + xOffset);
            setFitHeight(getFitHeight() + yOffset);

            updatePolyCircle(x, getY(),getFitWidth(),getFitHeight());

            setX(x);
        }
        else if (polyCircle.id.equals("NW")) {

            yOffset = getY() - y;
            xOffset = getX() - x;

            setFitWidth(getFitWidth()+xOffset);
            setFitHeight(getFitHeight()+yOffset);

            updatePolyCircle(x,y, getFitWidth(),getFitHeight());
            setY(y);
            setX(x);
        }
    }



    private void setLeftTopCircleListener(Circle circle) {

        circle.setOnMouseMoved(event -> {
            Circle evntCircle = (Circle) event.getSource();
            if(isFocused())
              evntCircle.setCursor(Cursor.NW_RESIZE);
        });

        //TODO: Resize
        circle.setOnMouseDragged(event -> {


           Circle evntCircle = (Circle) event.getSource();

           double x =  event.getX();
           double y =  event.getY();

           double newX = evntCircle.getCenterX()-x;
           double newY = evntCircle.getCenterY()-y;


           System.out.println("x: " + x + "\ty: " + y);
        });
    }


    //MARK: Poly
    //----------------------------------------------------------------------------------------------------------------//

    public void showPoly(boolean flag) {

        for(PolyCircle polyCircle: polyCircles.values())
           polyCircle.polyline.setVisible(flag);
    }


    //MARK: Poly Location
    //-----------------------------------------------------------------------------------------------------------//
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

class PolyCircle {

    Circle circle;
    Polyline polyline;
    String id;

    PolyCircle(String id, Circle circle, Polyline polyline) {
        this.id = id;
        this.circle = circle;
        this.polyline = polyline;
    }

    PolyCircle(){
        circle = new Circle();
        polyline = new Polyline();
        id = "";
    }
}

