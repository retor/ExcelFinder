package main.book;

import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Created by retor on 07.07.2015.
 */
public class BookWorker implements IBook {
    private Stage stage;
    private HSSFWorkbook book;

    public BookWorker(Stage stage) {
        this.stage = stage;
    }

    @Override
    public HSSFWorkbook openFile() throws IOException {
        File file = getFileChooser().showOpenDialog(stage);
        this.book = new HSSFWorkbook(new FileInputStream(file));
        if (isBookOpened())
            return this.book;
        else
            throw new IOException("Can't open file: " + file.getName() + " " + file.getPath());
    }

    @Override
    public List<String> searchNumber(String num) {
        String num_in = num;
        List<String> out = new ArrayList<>();
        HSSFSheet openSheet = book.getSheetAt(0);
        openSheet.forEach(cells -> {
            Cell cell = cells.getCell(3);
            if (cell != null && num != null && cell.getRichStringCellValue() != null) {
                if (cell.getRichStringCellValue().getString().contains(num))
                    out.add(getCell(cells));
            }
        });
        return out;
    }

    private String getCell(Row cells) {
        return cells.getCell(3) + " at date" + cells.getCell(0) + " at time" + cells.getCell(1) + " length" + cells.getCell(8);
    }

    @Override
    public boolean isBookOpened() {
        return Objects.nonNull(this.book);
    }

    private FileChooser getFileChooser() {
        FileChooser fc = new FileChooser();
        fc.setInitialDirectory(new File("c:/"));
        fc.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("XLS", "*.xls"),
                new FileChooser.ExtensionFilter("XLSX", "*.xlsx")
        );
        fc.setTitle("Choose file to searching...");
        return fc;
    }
}
