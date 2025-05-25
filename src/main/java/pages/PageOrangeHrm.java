package pages;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import utils.Report;
import java.util.NoSuchElementException;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import org.openqa.selenium.By;
import java.io.File;



public class PageOrangeHrm {
    private WebDriver _driver = null;
    private Report report;

    @FindBy(xpath = "//img[@alt='company-branding']")
    private WebElement imgLogin;
    @FindBy(xpath = "//input[@name='username']")
    private WebElement campoUsuario;
    @FindBy(xpath = "//input[@name='password']")
    private WebElement campoContrasena;
    @FindBy(xpath = "//button[@type='submit']")
    private WebElement botonLogin;
    @FindBy(xpath = "//h6[text()='Dashboard']")
    private WebElement pagDashboard;
    @FindBy(xpath = "//span[text()='Recruitment']")
    private WebElement botonRecruitment;
    @FindBy(xpath = "//a[text()='Candidates']")
    private WebElement textCandidatos;
    @FindBy(xpath = "//button[contains(., 'Add')]")
    private WebElement botonAdd;
    @FindBy(xpath = "//input[@name='firstName']")
    private WebElement campoNombre;
    @FindBy(xpath = "//input[@name='middleName']")
    private WebElement campoSegNombre;
    @FindBy(xpath = "//input[@name='lastName']")
    private WebElement campoApellido;
    @FindBy(xpath = "//div[contains(@class, 'oxd-select-text')]")
    private WebElement listVacante;
    @FindBy(xpath = "//label[text()='Email']/parent::div/following-sibling::div//input")
    private WebElement campoCorreo;
    @FindBy(xpath = "//label[text()='Contact Number']/parent::div/following-sibling::div//input")
    private WebElement campoNumero;
    @FindBy(xpath = "//input[@type='file']")
    private WebElement campoArchivo;
    @FindBy(xpath = "//input[@placeholder='Enter comma seperated words...']")
    private WebElement campoPalabras;
    @FindBy(xpath = "//div[contains(@class,'oxd-date-input')]/input")
    private WebElement fchFecha;
    @FindBy(xpath = "//textarea[@placeholder='Type here']")
    private WebElement campoNota;
    @FindBy(xpath = "//i[@class='oxd-icon bi-check oxd-checkbox-input-icon']")
    private WebElement checkBoxConservar;
    @FindBy(xpath = "//button[text()=' Save ']")
    private WebElement botonGuardar;
    @FindBy(xpath = "//button[text()=' Shortlist ']")
    private WebElement botonShortlist;
    @FindBy(xpath = "//button[text()=' Schedule Interview ']")
    private WebElement botonScheduleInterview;
    @FindBy(xpath = "//label[text()='Interview Title']/ancestor::div[contains(@class,'oxd-input-group__label-wrapper')]/following-sibling::div//input")
    private WebElement campoTitulo;
    @FindBy(xpath = "//input[@placeholder='Type for hints...']")
    private WebElement campoEntrevistador;
    @FindBy(xpath = "//input[@placeholder='hh:mm']")
    private WebElement campoHora;
    @FindBy(xpath = "//button[text()=' Mark Interview Passed ']")
    private WebElement botonMarkInterviewPassed;
    @FindBy(xpath = "//button[text()=' Offer Job ']")
    private WebElement botonOfferJob;
    @FindBy(xpath = "//button[text()=' Hire ']")
    private WebElement botonHire;


    public PageOrangeHrm(WebDriver driver, Report report){
        this._driver = driver;
        this.report = report;
        PageFactory.initElements(_driver, this);
    }


    public void AgregarCandidato(String usuario, String contrasena,String nombre,String segundoNombre,
                                 String apellido, String vacante,String correo,String numero,
                                 String archivo,String palabras,String fecha,String nota,String hora,
                                 String entrevistador)throws Exception{

        try{
            // Esperar a que un elemento esté visible para asegurar que la página ha cargado
            WebDriverWait wait = new WebDriverWait(_driver, Duration.ofSeconds(10));

            // Ruta relativa del archivo
            File file = new File(archivo);
            // Obtén la ruta absoluta
            String archivoabs = file.getAbsolutePath();

            //Seleccionar Vacante
            String opVacante="//div[@role='listbox']//*[contains(text(), '"+vacante+"')]";

            //Seleccionar Entrevistador
            String opEntrevistador="//div[@role='listbox']//*[contains(text(), '"+entrevistador+"')]";

            //Validar Candidato contratado
            String candidatoContratado ="//div[@class='oxd-table-row oxd-table-row--with-border'][.//div[text()='"+nombre+" "+segundoNombre+" "+apellido+"'] and .//div[text()='Hired']]//button[i[contains(@class, 'bi-eye-fill')]]";

            //Ingresar a Orange HRM
            try {
                _driver.get("https://opensource-demo.orangehrmlive.com/web/index.php/auth/login");
                wait.until(ExpectedConditions.visibilityOf(imgLogin));
                report.testPassed("Se ingresó correctamente al sitio web.",true);
            } catch (NoSuchElementException e) {
                report.testFailed("Error: no se puede acceder al sitio web. Verifique la conexión o el enlace.", true);

            }

            //Ingresar Usuario
            try {
                campoUsuario.sendKeys(usuario);
                report.testPassed("Se ingresó el usuario correctamente.",true);
            } catch (NoSuchElementException e) {
                report.testFailed("Error: no se puede ingresar el usuario.", true);
            }

            //Ingresar Contraseña
            try {
                campoContrasena.sendKeys(contrasena);
                report.testPassed("Se ingresó la contraseña correctamente.",true);
            } catch (NoSuchElementException e) {
                report.testFailed("Error: no se puede ingresar la contraseña.", true);
            }

            //Clic al botón Login
            try{
                botonLogin.click();
                wait.until(ExpectedConditions.visibilityOf(pagDashboard));
                report.testPassed("Se permitió dar clic al botón Login correctamente y se permitió ingresar al sistema.",true);
            }catch (NoSuchElementException e) {
                report.testFailed("Error: no se pudo hacer clic al botón Login.", true);
            }

            //Clic al botón Recruitment
            try{
                botonRecruitment.click();
                wait.until(ExpectedConditions.visibilityOf(textCandidatos));
                report.testPassed("Se permitió dar clic al botón Recruitment correctamente.",true);
            }catch (NoSuchElementException e) {
                report.testFailed("Error: no se pudo hacer clic al botón Recruitment.", true);
            }

            //Clic al botón Add
            try{
                botonAdd.click();
                Thread.sleep(1000);
                report.testPassed("Se permitió dar clic al botón Add correctamente.",true);
            }catch (NoSuchElementException e) {
                report.testFailed("Error: no se pudo hacer clic al botón Add.", true);
            }

            //Ingresar Primer Nombre
            try{
                campoNombre.sendKeys(nombre);
                report.testPassed("Se ingresó el nombre correctamente.",true);
            }catch (NoSuchElementException e) {
                report.testFailed("Error: no se pudo ingresar el nombre.", true);
            }

            //Ingresar Segundo Nombre
            try{
                campoSegNombre.sendKeys(segundoNombre);
                report.testPassed("Se ingresó el segundo nombre correctamente.",true);
            }catch (NoSuchElementException e) {
                report.testFailed("Error: no se pudo ingresar el segundo nombre.", true);
            }

            //Ingresar Apellido
            try{
                campoApellido.sendKeys(apellido);
                report.testPassed("Se ingresó el apellido correctamente.",true);
            }catch (NoSuchElementException e) {
                report.testFailed("Error: no se pudo ingresar el apellido.", true);
            }

            //Seleccionar Vacante
            try{
                listVacante.click();
                _driver.findElement(By.xpath(opVacante)).click();
                report.testPassed("Se seleccionó la vacante correctamente.",true);
            }catch (NoSuchElementException e) {
                report.testFailed("Error: no se pudo seleccionar la vacante.", true);
            }

            //Ingresar Correo
            try{
                campoCorreo.sendKeys(correo);
                report.testPassed("Se ingresó el correo correctamente.",true);
            }catch (NoSuchElementException e) {
                report.testFailed("Error: no se pudo ingresar el correo", true);
            }

            //Ingresar Numero de contacto
            try{
                campoNumero.sendKeys(numero);
                report.testPassed("Se ingresó el numero de contacto correctamente.",true);
            }catch (NoSuchElementException e) {
                report.testFailed("Error: no se pudo ingresar el numero de contacto.", true);
            }

            //Adjuntar Archivo
            try{
                campoArchivo.sendKeys(archivoabs);
                report.testPassed("Se adjunto el archivo correctamente.",true);
            }catch (NoSuchElementException e) {
                report.testFailed("Error: no se pudo adjuntar el archivo.", true);
            }

            //Ingresar Palabras claves
            try{
                campoPalabras.sendKeys(palabras);
                report.testPassed("Se ingresaron las palabras correctamente.",true);
            }catch (NoSuchElementException e) {
                report.testFailed("Error: no se pudo ingresar las palabras.", true);
            }

            //Ingresar Fecha
            try{
                for (int i = 0; i < 10; i++) {
                    fchFecha.sendKeys(Keys.BACK_SPACE);
                }
                fchFecha.sendKeys(fecha);
                report.testPassed("Se ingresó la fecha correctamente.",true);
            }catch (NoSuchElementException e) {
                report.testFailed("Error: no se pudo ingresar la fecha.", true);
            }

            //Ingresar una Nota
            try{
                campoNota.sendKeys(nota);
                report.testPassed("Se ingresó la nota correctamente.",true);
            }catch (NoSuchElementException e) {
                report.testFailed("Error: no se pudo ingresar la nota.", true);
            }

            //Seleccionar Check para Conservar datos
            try{
                checkBoxConservar.click();
                report.testPassed("Se seleccionó el check correctamente.",true);
            }catch (NoSuchElementException e) {
                report.testFailed("Error: no se pudo seleccionar el check.", true);
            }

            //Clic al botón Guardar
            try{
                botonGuardar.click();
                wait.until(ExpectedConditions.visibilityOf(botonShortlist));
                report.testPassed("Se permitió dar clic al botón guardar correctamente.",true);
            }catch (NoSuchElementException e) {
                report.testFailed("Error: no se pudo hacer clic en el botón guardar.", true);
            }

            //Clic al botón Shortlist
            try{
                botonShortlist.click();
                report.testPassed("Se permitió dar clic al botón Shortlist correctamente.",true);
            }catch (NoSuchElementException e) {
                report.testFailed("Error: no se pudo hacer clic en el botón Shortlist.", true);
            }

            //Ingresar una Nota
            try{
                campoNota.sendKeys(nota);
                wait.until(ExpectedConditions.visibilityOf(botonGuardar));
                report.testPassed("Se ingresó la nota en Shortlist Candidate correctamente.",true);
            }catch (NoSuchElementException e) {
                report.testFailed("Error: no se pudo ingresar la nota en Shortlist Candidate.", true);
            }

            //Clic al botón Guardar
            try{
                botonGuardar.click();
                wait.until(ExpectedConditions.visibilityOf(botonScheduleInterview));
                report.testPassed("Se permitió dar clic al botón guardar correctamente.",true);
            }catch (NoSuchElementException e) {
                report.testFailed("Error: no se pudo hacer clic en el botón guardar.", true);
            }

            //Clic al botón  Schedule Interview
            try{
                botonScheduleInterview.click();
                report.testPassed("Se permitió dar clic al botón  Schedule Interview  correctamente.",true);
            }catch (NoSuchElementException e) {
                report.testFailed("Error: no se pudo hacer clic en el botón Schedule Interview.", true);
            }

            //Ingresar campo titulo
            try{
                campoTitulo.sendKeys(vacante);
                wait.until(ExpectedConditions.visibilityOf(botonGuardar));
                report.testPassed("Se ingresó titulo correctamente.",true);
            }catch (NoSuchElementException e) {
                report.testFailed("Error: no se pudo ingresar titulo.", true);
            }

            //Ingresar campo Entrevistador
            try{
                campoEntrevistador.sendKeys(entrevistador);
                _driver.findElement(By.xpath(opEntrevistador)).click();
                report.testPassed("Se ingresó entrevistador correctamente.",true);
            }catch (NoSuchElementException e) {
                report.testFailed("Error: no se pudo ingresar entrevistador.", true);
            }

            //Ingresar Fecha
            try{
                fchFecha.sendKeys(fecha);
                report.testPassed("Se ingresó la fecha correctamente.",true);
            }catch (NoSuchElementException e) {
                report.testFailed("Error: no se pudo ingresar la fecha.", true);
            }

            //Ingresar hora
            try{
                campoHora.sendKeys(hora);
                report.testPassed("Se ingresó la hora correctamente.",true);
            }catch (NoSuchElementException e) {
                report.testFailed("Error: no se pudo ingresar la hora.", true);
            }


            //Ingresar una Nota
            try{
                campoNota.sendKeys(nota);
                report.testPassed("Se ingresó la nota en Shortlist Candidate correctamente.",true);
            }catch (NoSuchElementException e) {
                report.testFailed("Error: no se pudo ingresar la nota en Shortlist Candidate.", true);
            }


            //Clic al botón Guardar
            try{
                botonGuardar.click();
                wait.until(ExpectedConditions.visibilityOf(botonMarkInterviewPassed));
                report.testPassed("Se permitió dar clic al botón guardar correctamente.",true);
            }catch (NoSuchElementException e) {
                report.testFailed("Error: no se pudo hacer clic en el botón guardar.", true);
            }

            //Clic al botón Mark Interview Passed
            try{
                wait.until(ExpectedConditions.visibilityOf(botonMarkInterviewPassed));
                botonMarkInterviewPassed.click();
                report.testPassed("Se permitió dar clic al botón Mark Interview Passed correctamente.",true);
            }catch (NoSuchElementException e) {
                report.testFailed("Error: no se pudo hacer clic en el botón Mark Interview Passed.", true);
            }

            //Ingresar una Nota
            try{
                campoNota.sendKeys(nota);
                wait.until(ExpectedConditions.visibilityOf(botonGuardar));
                report.testPassed("Se ingresó la nota en Entrevista de Mark Aprobada correctamente.",true);
            }catch (NoSuchElementException e) {
                report.testFailed("Error: no se pudo ingresar la nota en Entrevista de Mark Aprobada.", true);
            }

            //Clic al botón Guardar
            try{
                botonGuardar.click();
                wait.until(ExpectedConditions.visibilityOf(botonOfferJob));
                report.testPassed("Se permitió dar clic al botón guardar correctamente.",true);
            }catch (NoSuchElementException e) {
                report.testFailed("Error: no se pudo hacer clic en el botón guardar.", true);
            }

            //Clic al botón Oferta de trabajo
            try{
                wait.until(ExpectedConditions.visibilityOf(botonOfferJob));
                botonOfferJob.click();
                report.testPassed("Se permitió dar clic al botón Oferta de trabajo correctamente.",true);
            }catch (NoSuchElementException e) {
                report.testFailed("Error: no se pudo hacer clic en el botón Oferta de trabajo.", true);
            }

            //Ingresar una Nota
            try{
                campoNota.sendKeys(nota);
                wait.until(ExpectedConditions.visibilityOf(botonGuardar));
                report.testPassed("Se ingresó la nota en Oferta de trabajo correctamente.",true);
            }catch (NoSuchElementException e) {
                report.testFailed("Error: no se pudo ingresar la nota en Oferta de trabajo.", true);
            }


            //Clic al botón Guardar
            try{
                botonGuardar.click();
                wait.until(ExpectedConditions.visibilityOf(botonHire));
                report.testPassed("Se permitió dar clic al botón guardar correctamente.",true);
            }catch (NoSuchElementException e) {
                report.testFailed("Error: no se pudo hacer clic en el botón guardar.", true);
            }

            //Clic al botón Contratar
            try{
                wait.until(ExpectedConditions.visibilityOf(botonHire));
                botonHire.click();
                report.testPassed("Se permitió dar clic al botón Contratar correctamente.",true);
            }catch (NoSuchElementException e) {
                report.testFailed("Error: no se pudo hacer clic en el botón Contratar.", true);
            }

            //Ingresar una Nota
            try{
                campoNota.sendKeys(nota);
                wait.until(ExpectedConditions.visibilityOf(botonGuardar));
                report.testPassed("Se ingresó la nota en Contratar correctamente.",true);
            }catch (NoSuchElementException e) {
                report.testFailed("Error: no se pudo ingresar la nota en Contratar.", true);
            }

            //Clic al botón Guardar
            try{
                botonGuardar.click();
                Thread.sleep(2000);
                report.testPassed("Se permitió dar clic al botón guardar correctamente.",true);
            }catch (NoSuchElementException e) {
                report.testFailed("Error: no se pudo hacer clic en el botón guardar.", true);
            }

            //Clic al botón Recruitment
            try{
                botonRecruitment.click();
                wait.until(ExpectedConditions.visibilityOf(textCandidatos));
                report.testPassed("Se permitió dar clic al botón Recruitment correctamente.",true);
            }catch (NoSuchElementException e) {
                report.testFailed("Error: no se pudo hacer clic al botón Recruitment.", true);
            }

            //validar Candidato aprezca contratado
            try{
                _driver.findElement(By.xpath(candidatoContratado)).click();
                Thread.sleep(2000);
                report.testPassed("Se seleccionó el candidato contratado correctamente.",true);
            }catch (NoSuchElementException e) {
                report.testFailed("Error: no se pudo seleccionar el candidato contratado.", true);
            }


        }catch (Exception ex) {
            System.out.println("Validación de Agregar Candidato finalizada con error" + ex);
        }

    }

}
