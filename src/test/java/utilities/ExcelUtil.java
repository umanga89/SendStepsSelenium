package utilities;

import org.apache.poi.ss.usermodel.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExcelUtil {

    public static Map<String,Integer> getColumnsIndexesFromExcel(String filename, String sheetName) throws FileNotFoundException, IOException{
        try{
            File f = new File(filename);
            FileInputStream fis = new FileInputStream(f);
            Workbook wb = WorkbookFactory.create(fis);
            Sheet wbSheetName = wb.getSheet(sheetName);
            Row firstRow = wbSheetName.getRow(0);

            int firstColumnIndex = firstRow.getFirstCellNum();
            int lastColumnIndex = firstRow.getLastCellNum();

            Map<String, Integer> columnIndexMap = new HashMap<>();

            for(int i=firstColumnIndex; i<lastColumnIndex; i++) {
                Cell cell = firstRow.getCell(i);
                columnIndexMap.put(cell.getStringCellValue(),cell.getColumnIndex());
            }
            return columnIndexMap;
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static List<Map<Integer,String>> getDataForRowFromExcel(String filename, String sheetName) throws FileNotFoundException, IOException{
        try{
            File f = new File(filename);
            FileInputStream fis = new FileInputStream(f);
            Workbook wb = WorkbookFactory.create(fis);
            Sheet wbSheetName = wb.getSheet(sheetName);
            Row firstRow = wbSheetName.getRow(0);

            int firstColumnIndex = firstRow.getFirstCellNum();
            int lastColumnIndex = firstRow.getLastCellNum();

            List<Map<Integer,String>> excelDataMap = new ArrayList<>();

            for(int i=1;i<=wbSheetName.getLastRowNum();i++) {
                Map<Integer,String> rowDataMap = new HashMap<>();
                for (int j = firstColumnIndex; j < lastColumnIndex; j++) {
                    Row row = wbSheetName.getRow(i);
                    Cell cell = row.getCell(j);
                    CellType cellType = cell.getCellType();
                    if (cellType.equals(CellType.NUMERIC)) {
                        rowDataMap.put(cell.getColumnIndex(), String.valueOf(cell.getNumericCellValue()));
                    } else if (cellType.equals(CellType.STRING)) {
                        rowDataMap.put(cell.getColumnIndex(), cell.getStringCellValue());
                    }
                }
                excelDataMap.add(rowDataMap);
            }
            return excelDataMap;
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
