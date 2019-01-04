package selenium.test.script;

import org.eclipse.jetty.util.StringUtil;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.springframework.stereotype.Component;
import selenium.test.vo.Request;
import selenium.test.vo.Response;
import selenium.test.vo.StepStatusVo;

/**
 * Created on 2019/1/4
 *
 * @author dean
 * @email loveangelo0217@gmail.com
 * @since 1.0
 */
@Component
public class BasicScript {

    public Response<StepStatusVo> script(WebDriver driver, Request request) throws Exception {
        StepStatusVo stepStatusVo = new StepStatusVo();
        stepStatusVo.setActs(request.getRange());

        if (driver != null) {
            try {
                if (doLogin(driver, request.getServerIp())) {
                    stepStatusVo.setDoLogin(true);

                    String[] acts = request.getRange().split(",");

                    for (String act : acts) {
                        if (act.trim().equals("chk")) {
                            stepStatusVo.setChkField(doChkHidden(driver));
                        } else if (act.trim().equals("insert")) {
                            stepStatusVo.setDoInsert(doInsert(driver, request));
                        } else if (act.trim().equals("query")) {
                            stepStatusVo.setDoQuery(doQuery(driver));
                        }
                    }
                }

                return new Response(0, "success" , stepStatusVo);
            } catch (Exception e) {
                e.printStackTrace();
                return new Response(99, "Exception :" + e.getMessage());
            } finally {
                driver.quit();
            }
        } else {
            return new Response(-2, "Driver is null");
        }

    }

    public boolean doLogin(WebDriver driver, String serverIp) throws Exception {
        driver.get("http://" + serverIp + ":8080/login");
        WebElement element = driver.findElement(By.name("username1"));
        element.sendKeys("admin");

        element = driver.findElement(By.name("password1"));
        element.sendKeys("12345");

        element = ((RemoteWebDriver) driver).findElementById("testBtn");

        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);


        int cnt = 0;
        //登入緩衝
        while (cnt < 20 && driver.getCurrentUrl().equals("http://" + serverIp + ":8080/login")) {
            Thread.sleep(1000);
            cnt++;
        }

        if (driver.getCurrentUrl().equals("http://" + serverIp + ":8080/com/helloJsp")) {
            return true;
        }
        return false;
    }

    public boolean doChkHidden(WebDriver driver) throws Exception {
        WebElement element = ((RemoteWebDriver) driver).findElementById("helloText");
        if (element.getText().equals("helloJsp")) {
            return true;
        }
        return false;
    }

    public boolean doInsert(WebDriver driver, Request request) throws Exception {
        WebElement element = ((RemoteWebDriver) driver).findElementById("name");
        element.sendKeys(request.getName());

        element = ((RemoteWebDriver) driver).findElementById("age");
        element.sendKeys(request.getAge() + "");

        element = ((RemoteWebDriver) driver).findElementById("insertBtn");

        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);

        Thread.sleep(1000);
        int cnt = 0;
        //登入緩衝
        while (cnt < 10 && !StringUtil.isBlank(element.getAttribute("value"))) {
            Thread.sleep(1000);
            cnt++;
        }
        if (StringUtil.isBlank(element.getAttribute("value"))) {
            return true;
        }
        return false;
    }

    public boolean doQuery(WebDriver driver) throws Exception {
        WebElement element = ((RemoteWebDriver) driver).findElementById("queryBtn");

        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);

        Thread.sleep(1000);
        return true;
    }
}
