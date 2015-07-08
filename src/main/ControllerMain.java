package main;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import main.book.BookWorker;
import main.book.IBook;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

/**
 * Created by retor on 03.07.2015.
 */
public class ControllerMain {
    @FXML
    TextArea textarea;
    @FXML
    Button bexit;
    @FXML
    Button bopen;
    @FXML
    Button bsave;
    @FXML
    Button badd;
    @FXML
    ListView<String> listnum;
    @FXML
    TextField numtext;
    private IBook worker;
    private ObservableList<String> items = FXCollections.observableArrayList();

    public ControllerMain() {
        listnum = new ListView<>(items);
    }

    public void systemExit(ActionEvent actionEvent) {
        System.exit(0);
    }

    public void openDialog(ActionEvent actionEvent) {
        try {
            worker.openFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (worker.isBookOpened()) {
            List<String> nums = listnum.getItems().sorted();
            nums.forEach(s -> {
                List<String> l = worker.searchNumber(s);
                l.listIterator().forEachRemaining(ControllerMain.this::addToTextArea);
                addToTextArea("Numbers find count: " + String.valueOf(l.size()));
            });
        }
    }

    private void addToTextArea(String txt) {
        textarea.appendText(txt + " ");
        textarea.appendText("" + '\n');
    }

    public void setupStage(Stage stage) {
        this.worker = new BookWorker(stage);
    }

    public void addNumber(ActionEvent actionEvent) {
        listnum.setEditable(true);
        if (numtext.getText() != null || !numtext.getText().equals("")) {
            items.add(numtext.getText());
            numtext.clear();
        }
        listnum.setItems(items);
    }

    public void saveToFile(ActionEvent actionEvent) {
        try {
            FileWriter fw = new FileWriter("results.txt", false);
            fw.write(textarea.getText());
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
