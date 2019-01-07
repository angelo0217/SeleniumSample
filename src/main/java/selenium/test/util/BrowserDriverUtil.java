package selenium.test.util;

import io.github.bonigarcia.wdm.ChromeDriverManager;
import io.github.bonigarcia.wdm.InternetExplorerDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.util.concurrent.TimeUnit;

/**
 * Created on 2019/1/6
 *
 * @author dean
 * @email loveangelo0217@gmail.com
 * @since 1.0
 */
public class BrowserDriverUtil {
    public static WebDriver getChromeDriver() throws Exception {
        ChromeDriverManager.getInstance().setup();
        WebDriver driver = new ChromeDriver();
        driver.manage().timeouts().pageLoadTimeout(5, TimeUnit.SECONDS);
        return driver;
    }

    public static WebDriver getIEDriver() throws Exception {
        InternetExplorerDriverManager.getInstance().setup();
//            System.setProperty("webdriver.ie.driver", "D:\\IE\\IEDriverServer.exe");
        DesiredCapabilities ieCapabilities = DesiredCapabilities.internetExplorer();

        ieCapabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
        ieCapabilities.setCapability("nativeEvents", false);
        ieCapabilities.setCapability("unexpectedAlertBehaviour", "accept");
        ieCapabilities.setCapability("disable-popup-blocking", true);
        ieCapabilities.setCapability(InternetExplorerDriver.ENABLE_PERSISTENT_HOVERING, false);
        ieCapabilities.setCapability(InternetExplorerDriver.REQUIRE_WINDOW_FOCUS, false);
        ieCapabilities.setJavascriptEnabled(true);

        WebDriver driver = new InternetExplorerDriver(ieCapabilities);
        return driver;
    }
}
