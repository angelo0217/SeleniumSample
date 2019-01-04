import com.google.gson.Gson;
import io.github.bonigarcia.wdm.InternetExplorerDriverManager;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import selenium.test.vo.Response;
import selenium.test.vo.TestFlow;

/**
 * Created on 2019/1/3
 *
 * @author dean
 * @email loveangelo0217@gmail.com
 * @since 1.0
 */
public class TestIE {

    @Test
    public void testIE() throws Exception {
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
        TestFlow testFlow = new TestFlow();

        try {
            driver.get("http://localhost:8080/login");
            WebElement element = driver.findElement(By.name("username1"));
            element.sendKeys("admin");

            element = driver.findElement(By.name("password1"));
            element.sendKeys("12345");

            testFlow.setEnterData(true);

            element = ((RemoteWebDriver) driver).findElementById("testBtn");
//            element.click();
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();",element);

//            driver.findElement(By.id("testBtn")).click();
            testFlow.setLogin(true);
            Thread.sleep(1000);

//            int cnt = 0;
//            while (cnt < 10 && driver.getCurrentUrl().equals("http://localhost:8080/login")) {
//                System.out.println(cnt);
//                Thread.sleep(1000);
//                cnt++;
//            }
            Thread.sleep(1000);
            if (driver.getCurrentUrl().equals("http://localhost:8080/com/helloJsp")) {
                testFlow.setChkFormInfo(true);
            }

//            System.out.println(driver.getCurrentUrl());
            element = ((RemoteWebDriver) driver).findElementById("helloText");
            testFlow.setChkData(element.getText());


            Thread.sleep(2000);
            System.out.println(new Gson().toJson(new Response(0, "success", testFlow)));
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            driver.quit();
        }

    }
}
