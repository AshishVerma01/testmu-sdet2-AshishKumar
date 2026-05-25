package com.frameworkdesign.ui.actions;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;

public class ExcelSheetActions {

    FileInputStream inputStream;
    XSSFWorkbook workbook;

    public ExcelSheetActions(String path) throws IOException {
        inputStream = new FileInputStream(path);
        workbook = new XSSFWorkbook(inputStream);
    }

    public XSSFSheet getExcelSheet(int sheeetNo) {
         return workbook.getSheetAt(sheeetNo);
    }

    public int getColumnFromSheet(XSSFSheet sheet, String value) {
        Iterator<Cell> cellIterator = sheet.getRow(0).cellIterator();
        int colIndex = -1;
        int index=0;
        while (cellIterator.hasNext()) {
            if (cellIterator.next().getStringCellValue().equalsIgnoreCase(value)) {
                colIndex=index;
                break;
            }
            index++;
        }
        return colIndex;
    }

    public int getRowFromSheet(XSSFSheet sheet, String value) {
        Iterator<Row> rowIterator = sheet.rowIterator();
        int rowIndex = -1;
        int index=0;
        while (rowIterator.hasNext()) {
            Iterator<Cell> cellIterator = rowIterator.next().cellIterator();
            while (cellIterator.hasNext()) {
                Cell cell = cellIterator.next();
                if (cell.getCellType() == CellType.STRING && cell.getStringCellValue().equals(value)) {
                    rowIndex=index;
                    break;
                }
            }
            index++;
        }

        return rowIndex;
    }

    public void updateCellValue(XSSFSheet sheet, int rowIndex, int colIndex, int value) {
        Row row = sheet.getRow(rowIndex);
        Cell cell = row.getCell(colIndex);
        cell.setCellValue(value);
    }

    public void saveAndCloseExcel(String path) throws IOException {
        FileOutputStream outputStream = new FileOutputStream(path);
        workbook.write(outputStream);
        workbook.close();
        outputStream.close();
        inputStream.close();
    }
}
