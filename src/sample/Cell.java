package sample;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class Cell extends Canvas {

    private Image image;

    public Cell(int width, int height){
        super(width,height);
    }

    public void drawCell(int x, int y, int w, int h){
        getGraphicsContext2D().drawImage(image,x,y,w,h);
    }

    public void drawCell(int x, int y){
        getGraphicsContext2D().drawImage(image,x,y);
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public Image getImage(){
        return image;
    }

    public Image getImage(int w, int h) {
     //  // image.getPixelReader().
     //   ImageInput input = new ImageInput(image);
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
