package utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

/**
 * Clase utilitaria para la generación de reportes automatizados con capturas de pantalla usando ExtentReports.
 *
 * Autor: Chaidir Charpentier
 * Fecha: 2025
 */
public class Report {

    private ExtentTest test;
    private WebDriver driver;
    private ExtentReports extent = new ExtentReports();

    private static final String fs = File.separator;

    private String reportNameFolder;
    private String testName;
    private String fechaFormateada;

    /***
     * Constructor que recibe el driver para usar en capturas
     * @param driver Instancia de WebDriver
     */
    public Report(WebDriver driver) {
        this.driver = driver;
    }

    /***
     * Constructor vacío
     */
    public Report() {}

    /***
     * Finaliza y guarda el reporte generado
     */
    public void endTest() {
        extent.flush();
    }

    /**
     * Crea el reporte HTML y configura el nombre del archivo
     * @param testName Nombre de la prueba
     * @param testDescription Descripción de la prueba
     * @throws Throwable en caso de error al crear el archivo
     */
    public void createTestReport(String testName, String testDescription) throws Throwable {
        Files files = new Files(); // Clase utilitaria para manejo de carpetas

        if (Objects.isNull(this.reportNameFolder) &&
                Objects.isNull(this.testName) &&
                Objects.isNull(this.fechaFormateada)) {

            this.testName = sanitizePath(testName);
            this.reportNameFolder = files.createFolder();
            this.fechaFormateada = getFormattedDate();
        }

        String reportPath = this.reportNameFolder + "/" + this.testName + "_" + this.fechaFormateada + ".html";
        System.out.println("Ruta del archivo creado: " + reportPath);

        ExtentSparkReporter htmlReporter = new ExtentSparkReporter(reportPath);
        extent.attachReporter(htmlReporter);

        String header = reportHeader(testName, testDescription);
        test = extent.createTest(header);
    }

    /**
     * Inicia la prueba en el reporte con nombre, descripción e ID
     * @param testName Nombre del test
     * @param driver WebDriver en uso
     * @param testDescription Descripción del test
     * @param testId Identificador único del test
     * @throws Exception en caso de error
     */
    public void startTest(String testName, WebDriver driver, String testDescription, String testId) throws Exception {

        this.driver = driver;

        try {
            this.createTestReport(testName, testDescription);
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Marca la prueba como pasada
     * @param escenario Descripción del paso
     * @param takeScreenShoot Si se desea incluir captura
     */
    public void testPassed(String escenario, boolean takeScreenShoot) {
        if (takeScreenShoot) {
            try {
                String screenshotPath = saveScreenshot();
                test.log(Status.PASS, escenario, MediaEntityBuilder.createScreenCaptureFromPath(screenshotPath).build());
            } catch (IOException e) {
                test.log(Status.PASS, escenario + " (Error al guardar captura: " + e.getMessage() + ")");
            }
        } else {
            test.pass(escenario);
        }
    }

    public void testPass(String description) throws Exception {
        this.testPassed(description, true);
    }

    /**
     * Marca la prueba como fallida
     * @param escenario Descripción del paso
     * @param takeScreenShoot Si se desea incluir captura
     */
    public void testFailed(String escenario, boolean takeScreenShoot) {
        if (takeScreenShoot) {
            try {
                String screenshotPath = saveScreenshot();
                test.log(Status.FAIL, escenario, MediaEntityBuilder.createScreenCaptureFromPath(screenshotPath).build());
            } catch (IOException e) {
                test.log(Status.FAIL, escenario + " (Error al guardar captura: " + e.getMessage() + ")");
            }
        } else {
            test.fail(escenario);
        }
    }

    public void testFail(String description) throws Exception {
        this.testFailed(description, true);
    }

    /**
     * Marca la prueba con advertencia
     * @param escenario Descripción del paso
     * @param takeScreenShoot Si se desea incluir captura
     */
    public void testWarn(String escenario, boolean takeScreenShoot) {
        if (takeScreenShoot) {
            try {
                String screenshotPath = saveScreenshot();
                test.log(Status.WARNING, escenario, MediaEntityBuilder.createScreenCaptureFromPath(screenshotPath).build());
            } catch (IOException e) {
                test.log(Status.WARNING, escenario + " (Error al guardar captura: " + e.getMessage() + ")");
            }
        } else {
            test.warning(escenario);
        }
    }

    public void testWarning(String description) throws Exception {
        this.testWarn(description, true);
    }

    // ============================
    // MÉTODOS INTERNOS Y AUXILIARES
    // ============================

    /**
     * Genera el encabezado HTML del test
     */
    private String reportHeader(String escenario, String description) {
        return "Escenario: <b>" + escenario + "</b><br>";
    }

    /**
     * Guarda una captura de pantalla y retorna la ruta relativa
     */
    private String saveScreenshot() throws IOException {
        TakesScreenshot screenshot = (TakesScreenshot) driver;
        File srcFile = screenshot.getScreenshotAs(OutputType.FILE);
        String screenshotRelativePath = "screenshots" + fs + System.currentTimeMillis() + ".png";
        File destFile = new File(reportNameFolder + fs + screenshotRelativePath);
        FileUtils.copyFile(srcFile, destFile);
        return screenshotRelativePath;
    }

    /**
     * Devuelve la fecha actual en formato yyyy-MM-dd_HHmmss
     */
    private String getFormattedDate() {
        Date fechaActual = new Date();
        SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy-MM-dd_HHmmss");
        return formatoFecha.format(fechaActual);
    }

    /**
     * Evita errores en los nombres de archivos
     */
    private String sanitizePath(String input) {
        if (input == null) {
            return null;
        }
        return input.replaceAll("[;,:/\\\\]", "_");
    }

}
