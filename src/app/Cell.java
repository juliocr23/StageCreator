package app;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;
import javafx.scene.paint.Paint;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;

public class Cell extends Canvas {

    public Image image;
    private File file;

    Paint color;

    public Cell(int width, int height, Paint color){
        super(width,height);
        this.color = color;

        getGraphicsContext2D().setFill(color);
        getGraphicsContext2D().fillRect(0,0,width,height);
    }

    public void drawCell(int x, int y, int w, int h){
        getGraphicsContext2D().drawImage(image,x,y,w,h);

    }

    public void drawCell(int x, int y){
        getGraphicsContext2D().drawImage(image,x,y,getWidth(),getHeight());
    }

    public void clearCell(){
        image = null;

        getGraphicsContext2D().clearRect(0,0,getWidth(),getHeight());

        getGraphicsContext2D().setFill(color);
        getGraphicsContext2D().fillRect(0,0,getWidth(),getHeight());
    }

   public void setImageData(File file) {
        this.file = file;
        try {
            FileInputStream inputStream = new FileInputStream(file);
            image = new Image(inputStream);
            inputStream.close();
        }catch (Exception e) {
            System.out.println(e.getMessage());
        }

   }

   public FileInputStream getData(){

       FileInputStream inputStream = null;
       try {
          inputStream = new FileInputStream(file);
       }catch (Exception e) {
           System.out.println(e.getMessage());
       }
       return inputStream;
   }

   public File getFile(){
        return file;
   }


   public Image getImage(){
        return image;
    }

    public Image getImage(int w, int h) {

        BufferedImage bImage = SwingFXUtils.fromFXImage(image, null);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        InputStream inputStream = null;
        try {
            ImageIO.write(bImage, "png", outputStream);
            byte[] res  = outputStream.toByteArray();
            inputStream = new ByteArrayInputStream(res);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new Image(inputStream,64,64,false,true);
    }
}
