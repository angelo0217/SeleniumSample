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
            return script(driver);
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
            return script(driver);
        }catch (Exception e){
            return new Response(-1, "IE Driver error");
        }
    }

    private Response<TestFlow> script(WebDriver driver) throws Exception{
        String serverIp = "192.168.43.127";
        TestFlow testFlow = new TestFlow();
        if(driver != null) {
            try {
                driver.get("http://"+serverIp+":8080/login");
                WebElement element = driver.findElement(By.name("username1"));
                element.sendKeys("admin");

                element = driver.findElement(By.name("password1"));
                element.sendKeys("12345");

                testFlow.setEnterData(true);

                element = ((RemoteWebDriver) driver).findElementById("testBtn");
//                element.click();
                //麻煩的IE 要改這樣 Cheome 可用
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();",element);
                testFlow.setLogin(true);

                int cnt = 0;
                //登入緩衝
                while(cnt < 20 && driver.getCurrentUrl().equals("http://"+serverIp+":8080/login")){
                    Thread.sleep(1000);
                    cnt ++;
                }

                if (driver.getCurrentUrl().equals("http://"+serverIp+":8080/com/helloJsp")) {
                    testFlow.setChkFormInfo(true);
                }
//                element = ((RemoteWebDriver) driver).findElementByClassName("container-fluid");
                element = ((RemoteWebDriver) driver).findElementById("helloText");
                testFlow.setChkData(element.getText());

                Thread.sleep(2000);

                return new Response(0, "success", testFlow);
            }catch (Exception e){
                e.printStackTrace();
                return new Response(99, "Exception :"+e.getMessage());
            }finally {
                driver.quit();
            }
        }else {
            return new Response(-2, "Driver is null");
        }

    }
}
