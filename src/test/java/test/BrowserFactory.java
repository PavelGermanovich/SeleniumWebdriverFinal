package test;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;

public class BrowserFactory {
    public static WebDriver getBrowser(OnlinerTest.Browsers browser) {
        WebDriver webDriver = null;
        setProperty();
        switch (browser) {
            case Chrome: {
                webDriver = new ChromeDriver();
                break;
            }
            case Firefox: {
                webDriver = new FirefoxDriver();
                break;
            }
            case IE: {
                webDriver = new InternetExplorerDriver();
                break;
            }
            default: {
                System.out.println("You have inputted wrong browser");
                break;
            }
        }
        return webDriver;
    }

    public static boolean isWindows(String osName) {
        return (osName.indexOf("Win") >= 0);
    }

    public static void setProperty(){
        final String osName = System.getProperty("os.name");
        if (osName.equals("Linux")){
            System.setProperty("webdriver.chrome.driver", "LinuxDrivers/chromedriver");
            System.setProperty("webdriver.gecko.driver", "LinuxDrivers/geckodriver");
        }
        else if(isWindows(osName)){
            System.setProperty("webdriver.chrome.driver", "Windowsdrivers/chromedriver.exe");
            System.setProperty("webdriver.gecko.driver","Windowsdrivers/geckodriver.exe");
            System.setProperty("webdriver.ie.driver", "Windowsdrivers/IEDriverServer.exe");
        }

    }
}