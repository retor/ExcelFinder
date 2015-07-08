package main.book;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.io.IOException;
import java.util.List;

/**
 * Created by retor on 07.07.2015.
 */
public interface IBook {
    HSSFWorkbook openFile() throws IOException;
    List<String> searchNumber(String num);
    boolean isBookOpened();
}
