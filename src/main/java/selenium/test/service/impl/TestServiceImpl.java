package selenium.test.service.impl;

import io.github.bonigarcia.wdm.ChromeDriverManager;
import io.github.bonigarcia.wdm.InternetExplorerDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.springframework.stereotype.Service;
import selenium.test.script.BasicScript;
import selenium.test.service.TestService;
import selenium.test.vo.TestFlow;
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
    public Response<TestFlow> chromeTest() throws Exception {
        ChromeDriverManager.getInstance().setup();
        WebDriver driver ;
        try{

            driver = new ChromeDriver();
            return BasicScript.script(driver);
        } catch (Exception ex){
            System.out.println("Exception while instantiating driver. " + ex.getMessage());
        }
        return new Response(-1, "Chrome Driver error");
    }

    @Override
    public Response<TestFlow> ieTest() throws Exception {
        try{
            InternetExplorerDriverManager.getInstance().setup();
            System.setProperty("webdriver.ie.driver", "D:\\IE\\IEDriverServer.exe");
            DesiredCapabilities ieCapabilities = DesiredCapabilities.internetExplorer();

            ieCapabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
            ieCapabilities.setCapability("ignoreProtectedModeSettings", true);
            ieCapabilities.setCapability("nativeEvents",false);
            ieCapabilities.setCapability("unexpectedAlertBehaviour", "accept");
            ieCapabilities.setCapability("disable-popup-blocking", true);
            ieCapabilities.setCapability("enablePersistentHover", true);

            WebDriver driver = new InternetExplorerDriver(ieCapabilities);
            return BasicScript.script(driver);
        }catch (Exception e){
            return new Response(-1, "IE Driver error");
        }
    }
}
