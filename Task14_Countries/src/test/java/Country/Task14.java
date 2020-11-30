package Country;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.Set;

public class Task14 {
    private WebDriver driver;
    private WebDriverWait wait;
    @Before
    public void start(){
        driver = new ChromeDriver();

        wait = new WebDriverWait(driver,1000);
    }
    @Test
    public void myFirstTest() throws InterruptedException {
        //Переход на страницу админа
        driver.get("http://localhost/litecart/admin/");
        driver.findElement(By.name("username")).sendKeys("admin");
        driver.findElement(By.name("password")).sendKeys("admin");
        driver.findElement(By.name("login")).click();
        Thread.sleep(500);
        driver.get("http://localhost/litecart/admin/?app=countries&doc=countries");
        //Переход на Каталог
        driver.findElement(By.cssSelector("#content .button")).click();
        Thread.sleep(500);
         List<WebElement> list = driver.findElements(By.cssSelector("#content table i"));
         for (WebElement element: list) {
         String curWindow = driver.getWindowHandle();
         Set<String> activesWind = driver.getWindowHandles();
         element.click();
         String newWindow = wait.until(stringdescriptor(activesWind));
         driver.switchTo().window(newWindow);
         driver.close();
         driver.switchTo().window(curWindow);
         }
    }
    @After
    public void stop(){

        driver.quit();
        driver = null;
    }
    public String newOpenWindow (Set<String> nWindows)
    {
        Set<String> handles = driver.getWindowHandles();
        handles.removeAll(nWindows);
        if (handles.size() > 0) {
            return handles.iterator().next();
        }
        else {
            return null;
        }
    }
    public ExpectedCondition<String> stringdescriptor(Set<String> nWindows){
        return webDriver -> {
            if (driver == null)
                return null;
            return newOpenWindow(nWindows);
        };
    }
}
