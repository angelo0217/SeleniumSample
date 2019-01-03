package selenium.test.service.impl;

import io.github.bonigarcia.wdm.ChromeDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.springframework.stereotype.Service;
import selenium.test.service.TestService;
import selenium.test.vo.ChromeFlow;
import selenium.test.vo.Response;

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
    public Response<ChromeFlow> chromeTest() throws Exception {
        ChromeDriverManager.getInstance().setup();
        WebDriver driver ;
        try{

            driver = new ChromeDriver();
            return script(driver);
        } catch (Exception ex){
            System.out.println("Exception while instantiating driver. " + ex.getMessage());
        }
        return new Response(-1, "Driver error");
    }

    private Response<ChromeFlow> script(WebDriver driver) throws Exception{
        ChromeFlow chromeFlow = new ChromeFlow();
        if(driver != null) {
            try {
                driver.get("http://localhost:8080/login");
                WebElement element = driver.findElement(By.name("username1"));
                element.sendKeys("admin");

                element = driver.findElement(By.name("password1"));
                element.sendKeys("12345");

                chromeFlow.setEnterData(true);

                element = ((RemoteWebDriver) driver).findElementById("testBtn");
                element.click();
                chromeFlow.setLogin(true);

                if (driver.getCurrentUrl().equals("http://localhost:8080/com/helloJsp")) {
                    chromeFlow.setChkFormInfo(true);
                }
                element = ((RemoteWebDriver) driver).findElementByClassName("container-fluid");
                chromeFlow.setChkData(element.getText());

                Thread.sleep(2000);
                driver.quit();
                return new Response(0, "success", chromeFlow);
            }catch (Exception e){
                e.printStackTrace();
                return new Response(99, "Exception :"+e.getMessage());
            }
        }else {
            return new Response(-2, "Driver is null");
        }

    }
}
