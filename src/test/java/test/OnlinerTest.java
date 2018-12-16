package test;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


import java.io.IOException;
import java.util.List;

import static java.util.concurrent.TimeUnit.SECONDS;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class OnlinerTest {

    public enum Browsers{
        Chrome, Firefox, IE;
    }
    private static WebDriver webDriver;
    public final String siteName = "https://www.onliner.by/";
    static final String login = "afl_test_account@mail.ru";
    static final String password = "seleniumTest";
    static final Browsers browser = Browsers.Chrome;

    @BeforeClass
    public static void setup() throws IOException{
        webDriver = BrowserFactory.getBrowser(browser);
    }

    @Test
    public void a_openSite() throws IOException {
        webDriver.get(siteName);
        webDriver.manage().window().maximize();
        Assert.assertEquals(siteName, webDriver.getCurrentUrl());
    }

    @Test
    public void b_login(){
        webDriver.manage().timeouts().implicitlyWait(10, SECONDS); //// задает максимум 10 секунд ожидания для каждого поиска
        webDriver.findElement(By.xpath("//div[@class='auth-bar__item auth-bar__item--text']")).click();
        webDriver.findElement(By.xpath("//input[@placeholder='Ник или e-mail']")).sendKeys(login);
        webDriver.findElement(By.xpath("//input[@placeholder='Пароль']")).sendKeys(password);
        webDriver.findElement(By.xpath("//button[contains(text(),'Войти')]")).click();

        Boolean isPresent =  !webDriver.findElements(By.xpath("//div[@class='b-top-profile__image']")).isEmpty();
        Assert.assertTrue(isPresent);
    }

    @Test
    public void  c_goToCataLog(){
        final String expectedAdressOfCatalog= "https://catalog.onliner.by/";
         WebDriverWait wait = new WebDriverWait(webDriver, 30); //test
         wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[@class='b-main-navigation__text'][contains(text(),'Каталог')]"))); //Explicit Wait
        webDriver.findElement(By.xpath("//span[@class='b-main-navigation__text'][contains(text(),'Каталог')]")).click();

        Assert.assertEquals(expectedAdressOfCatalog, webDriver.getCurrentUrl());

    }

    @Test
    public void d_chooseRand() {
        List<WebElement> list = webDriver.findElements(By.cssSelector(".catalog-bar__link.catalog-bar__link_strong"));
        final int numberOfSection = (int) (Math.random() * list.size());
        String nameOfSection = list.get(numberOfSection).getText();
        list.get(numberOfSection).click();
        //class="schema-header__title"

        Assert.assertEquals(nameOfSection, webDriver.findElement(By.cssSelector(".schema-header__title")).getText());
    }

    @Test
    public void e_userLogout(){
        webDriver.findElement(By.cssSelector(".b-top-profile__image")).click();
        ////div[@class='b-top-profile__image']
        Actions act = new Actions(webDriver);
        WebElement logout = webDriver.findElement(By.cssSelector(".b-top-profile__link.b-top-profile__link_secondary"));
        act.moveToElement(logout);
        act.click();
        act.build().perform();
        webDriver.quit();
    }
}
