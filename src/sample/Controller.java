package sample;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;

public class Controller {
    @FXML
    private ChoiceBox<String> spriteTypes;

    public void initialize(){
        ObservableList list = FXCollections.observableArrayList();
        list.addAll("Tile", "Power up",
                              "Coin","Enemy",
                              "Player");
       spriteTypes.setItems(list);
       spriteTypes.setValue("Tile");
    }
}
