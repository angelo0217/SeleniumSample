package selenium.test.service.impl;

import io.github.bonigarcia.wdm.ChromeDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.springframework.stereotype.Service;
import selenium.test.service.TestService;

/**
 * Created on 2019/1/3
 *
 * @author dean
 * @email loveangelo0217@gmail.com
 * @since 1.0
 */
@Service
public class TestServiceImpl implements TestService {

    @Override
    public String chromeTest() throws Exception {
        ChromeDriverManager.getInstance().setup();
        WebDriver driver ;
        try{

            driver = new ChromeDriver();
            return script(driver);
        } catch (Exception ex){
            System.out.println("Exception while instantiating driver. " + ex.getMessage());
        }
        return "drive not get";
    }

    private String script(WebDriver driver) throws Exception{
        if(driver != null) {
            driver.get("http://localhost:8080/login");
            WebElement element = driver.findElement(By.name("username1"));
            element.sendKeys("admin");

            element = driver.findElement(By.name("password1"));
            element.sendKeys("12345");

            element = ((RemoteWebDriver) driver).findElementById("testBtn");
            element.click();
            if(driver.getCurrentUrl().equals("http://localhost:8080/com/helloJsp")){
                System.out.println("success");
            } else{
                System.out.println("error");
            }
            element = ((RemoteWebDriver) driver).findElementByClassName("container-fluid");
            System.out.println("Title: " + element.getText());

            Thread.sleep(5000);
            driver.quit();
        }
        return "success";
    }
}
