package sample;

import javafx.scene.image.Image;

import javafx.scene.image.ImageView;
import java.io.File;
import java.io.FileInputStream;

public class Frame extends ImageView {

    File file;

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
    }
}
