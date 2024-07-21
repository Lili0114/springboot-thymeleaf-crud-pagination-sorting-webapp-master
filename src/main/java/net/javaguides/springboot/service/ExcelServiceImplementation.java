package net.javaguides.springboot.service;

import net.javaguides.springboot.model.Book;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

@Service
public class ExcelServiceImplementation implements ExcelService{
    @Override
    public Workbook generateExcel(List<Book> books) throws IOException {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Book List");

        Row headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("Title");
        headerRow.createCell(1).setCellValue("Author");
        headerRow.createCell(2).setCellValue("Publication");

        int rowNum = 1;
        for (Book book : books) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(book.getTitle());
            row.createCell(1).setCellValue(book.getAuthor());
            row.createCell(2).setCellValue(book.getPublication());
        }
        return workbook;
    }
}
