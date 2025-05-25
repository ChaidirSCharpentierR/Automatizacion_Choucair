package utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.util.concurrent.TimeUnit;

public class DriverConfig {
    public enum DriverType {
        CHROME, FIREFOX, EDGE }

    public static WebDriver getBrowser() {

        WebDriver _driver = null;
        String _browserName = System.getProperty("browser", DriverType.CHROME.toString()).toUpperCase();
        DriverType _driverType = DriverType.valueOf(_browserName);

        switch (_driverType) {
            case CHROME:
                _driver = new ChromeDriver();
                break;
            case FIREFOX:
                _driver = new FirefoxDriver();
                break;
            case EDGE:
                _driver = new EdgeDriver();
                break;
        }
        _driver.manage().window().maximize();
        _driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        return _driver;
    }

}
