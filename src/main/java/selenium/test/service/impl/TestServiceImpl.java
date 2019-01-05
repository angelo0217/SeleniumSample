package selenium.test.service.impl;

import io.github.bonigarcia.wdm.ChromeDriverManager;
import io.github.bonigarcia.wdm.InternetExplorerDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import selenium.test.script.BasicScript;
import selenium.test.service.TestService;
import selenium.test.vo.Request;
import selenium.test.vo.TestStepVo;
import selenium.test.vo.Response;

import java.util.concurrent.TimeUnit;

/**
 * Created on 2019/1/3
 *
 * @author dean
 * @email loveangelo0217@gmail.com
 * @since 1.0
 */
@Service
public class TestServiceImpl implements TestService {

    @Autowired
    BasicScript basicScript;

    @Override
    public Response<TestStepVo> chromeTest(Request request) throws Exception {
        ChromeDriverManager.getInstance().setup();
        WebDriver driver ;
        try{

            driver = new ChromeDriver();
            driver.manage().timeouts().pageLoadTimeout(5, TimeUnit.SECONDS);
            return basicScript.script(driver, request);
        } catch (Exception ex){
            System.out.println("Exception while instantiating driver. " + ex.getMessage());
        }
        return new Response(-1, "Chrome Driver error");
    }

    @Override
    public Response<TestStepVo> ieTest(Request request) throws Exception {
        try{
            InternetExplorerDriverManager.getInstance().setup();
//            System.setProperty("webdriver.ie.driver", "D:\\IE\\IEDriverServer.exe");
            DesiredCapabilities ieCapabilities = DesiredCapabilities.internetExplorer();

            ieCapabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
            ieCapabilities.setCapability("nativeEvents",false);
            ieCapabilities.setCapability("unexpectedAlertBehaviour", "accept");
            ieCapabilities.setCapability("disable-popup-blocking", true);
            ieCapabilities.setCapability(InternetExplorerDriver.ENABLE_PERSISTENT_HOVERING,false);
            ieCapabilities.setCapability(InternetExplorerDriver.REQUIRE_WINDOW_FOCUS, false);
//            ieCapabilities.setCapability(InternetExplorerDriver.UNEXPECTED_ALERT_BEHAVIOR, true);
//            ieCapabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
            ieCapabilities.setJavascriptEnabled(true);

            WebDriver driver = new InternetExplorerDriver(ieCapabilities);
            return basicScript.script(driver, request);
        }catch (Exception e){
            return new Response(-1, "IE Driver error");
        }
    }
}
