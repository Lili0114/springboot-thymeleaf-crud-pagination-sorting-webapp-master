package net.javaguides.springboot.service;

import net.javaguides.springboot.model.Book;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public interface ExcelService {

    public Workbook generateExcel(List<Book> books) throws IOException;
}

