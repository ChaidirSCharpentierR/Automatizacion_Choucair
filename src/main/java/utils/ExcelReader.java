package utils;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class ExcelReader {

    // Mapa global que guarda los datos cargados del Excel:
    // hoja -> códigoCaso -> campo -> valor
    private static Map<String, Map<String, Map<String, String>>> excelData = null;

    private static String ExcelFilePath = ProjectParameters.ExcelFile;

    /**
     * Obtiene los datos de un caso de prueba específico dentro de una hoja.
     */
    public static Map<String, String> getTestCaseData(String sheetName, String codeCaseFilter) throws IOException {
        if (excelData == null) {
            loadExcelData(ExcelFilePath);
        }
        return excelData.getOrDefault(sheetName, Collections.emptyMap())
                .getOrDefault(codeCaseFilter, new HashMap<>());
    }

    /**
     * Obtiene todos los casos (y sus datos) de una hoja.
     */
    public static Map<String, Map<String, String>> getAllTestCases(String sheetName) throws IOException {
        if (excelData == null) {
            loadExcelData(ExcelFilePath);
        }
        return excelData.getOrDefault(sheetName, Collections.emptyMap());
    }

    /**
     * Carga todos los datos del archivo Excel en memoria.
     */
    private static void loadExcelData(String filePath) throws IOException {
        excelData = new HashMap<>();

        try (FileInputStream fis = new FileInputStream(new File(filePath));
             Workbook workbook = new XSSFWorkbook(fis)) {

            for (int i = 0; i < workbook.getNumberOfSheets(); i++) {
                Sheet sheet = workbook.getSheetAt(i);
                if (sheet == null) continue;

                Map<String, Map<String, String>> sheetData = new HashMap<>();

                for (Row row : sheet) {
                    Cell codeCell = row.getCell(0);
                    Cell caseCell = row.getCell(1);
                    Cell fieldCell = row.getCell(2);
                    Cell valueCell = row.getCell(3);

                    if (codeCell != null && caseCell != null && fieldCell != null && valueCell != null) {
                        String codeText = getCellValueAsString(codeCell).trim();
                        String caseText = getCellValueAsString(caseCell).trim();
                        String fieldText = getCellValueAsString(fieldCell).trim();
                        String valueText = getCellValueAsString(valueCell).trim();

                        sheetData.putIfAbsent(codeText, new HashMap<>());
                        sheetData.get(codeText).put(fieldText, valueText);
                        sheetData.get(codeText).put("casoDePrueba", caseText);
                    }
                }
                excelData.put(sheet.getSheetName(), sheetData);
            }
        }
    }

    /**
     * Convierte el valor de una celda en String.
     */
    private static String getCellValueAsString(Cell cell) {
        if (cell == null) return "";

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue();
            case NUMERIC:
                if (DateUtil.isCellDateFormatted(cell)) {
                    return dateFormat.format(cell.getDateCellValue());
                } else {
                    double numericValue = cell.getNumericCellValue();
                    return (numericValue % 1 == 0) ? String.valueOf((long) numericValue) : String.valueOf(numericValue);
                }
            case BOOLEAN:
                return String.valueOf(cell.getBooleanCellValue());
            case FORMULA:
                return cell.getCellFormula();
            default:
                return "";
        }
    }
}
