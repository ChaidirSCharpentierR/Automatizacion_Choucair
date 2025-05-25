package test;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import pages.PageOrangeHrm;
import utils.DriverConfig;
import utils.ExcelReader;
import utils.Report;
import java.util.Map;

public class TestChoucair {
    private static WebDriver driver;
    private static Report report;


    @BeforeAll
    public static void setUp() throws Exception {
        // Inicializar el reporte
        report = new Report();

        // Inicializar el WebDriver
        driver = DriverConfig.getBrowser();
        if (driver == null) {
            throw new IllegalStateException("No se pudo inicializar el WebDriver");
        }
    }

    @AfterAll
    public static void tearDown() {
        if (driver != null) {
            driver.quit();  // Cerrar el navegador al final de la prueba
        }
    }

    @Test
    public void testAgregarCandidato()throws Throwable {
        PageOrangeHrm pageOrangeHrm = new PageOrangeHrm(driver, report);
        report.startTest("Test Choucair", driver, "Validación Agregar Candidato","");

        try {
            // Leer datos desde la hoja de excel "candidatos"
            Map<String, String> testData = ExcelReader.getTestCaseData("candidatos", "ID1");

            // Obtener datos desde el Excel
            String usuario = testData.get("usuario");
            String contrasena = testData.get("contrasena");
            String nombre = testData.get("nombre");
            String segundoNombre = testData.get("segundoNombre");
            String apellido = testData.get("apellido");
            String vacante = testData.get("vacante");
            String correo = testData.get("correo");
            String numero = testData.get("numero");
            String archivo = testData.get("archivo");
            String palabras = testData.get("palabras");
            String fecha = testData.get("fecha");
            String nota = testData.get("nota");
            String hora = testData.get("hora");
            String entrevistador = testData.get("entrevistador");

            pageOrangeHrm.AgregarCandidato(usuario,contrasena,nombre,segundoNombre,
                    apellido,vacante,correo,numero,archivo,palabras,fecha,nota,hora,entrevistador);

        } catch (Exception e) {
            System.err.println("Error durante la ejecución del test Agregar Candidato: " + e.getMessage());
        }finally {
            report.endTest();
        }


    }

}
