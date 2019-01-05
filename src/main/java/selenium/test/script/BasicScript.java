package selenium.test.script;

import org.eclipse.jetty.util.StringUtil;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.springframework.stereotype.Component;
import selenium.test.util.TimeDelayUtil;
import selenium.test.vo.Request;
import selenium.test.vo.Response;
import selenium.test.vo.TestStepVo;

/**
 * Created on 2019/1/4
 *
 * @author dean
 * @email loveangelo0217@gmail.com
 * @since 1.0
 */
@Component
public class BasicScript {

    public Response<TestStepVo> script(WebDriver driver, Request request) throws Exception {
        TestStepVo testStepVo = new TestStepVo();
        testStepVo.setActs(request.getRange());

        if (driver != null) {
            try {
                //要登入才能做事，無可避免
                System.out.println("=============1");
                if (doLogin(driver, request.getServerIp())) {
                    System.out.println("=============1");
                    testStepVo.setDoLogin("true");

                    String[] acts = request.getRange().split(",");

                    //判斷步驟，執行需要跑的測試
                    for (String act : acts) {
                        System.out.println("=============1"+act);
                        if (act.trim().equals("chk")) {
                            testStepVo.setChkField(doChkHidden(driver) + "");
                        } else if (act.trim().equals("insert")) {
                            testStepVo.setDoInsert(doInsert(driver, request)+ "");
                        } else if (act.trim().equals("query")) {
                            testStepVo.setDoQuery(doQuery(driver, request.getServerIp())+ "");
                        }
                    }
                    Thread.sleep(3000);
                    return new Response(0, "success" , testStepVo);
                }else{
                    return new Response(-1, "login fail" , testStepVo);
                }


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
//        driver.navigate().refresh("http://" + serverIp + ":8080/login");
        WebElement element = driver.findElement(By.id("username1"));
        element.sendKeys("admin");

        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("$('#password1').val('12345');");

        js.executeScript("$('#testBtn').click();");
//        driver.navigate().to("javascript:document.getElementById('testBtn').click()");

        System.out.println("===========2");
        return TimeDelayUtil.chkUrl(driver, "http://" + serverIp + ":8080/com/helloJsp", 20);
    }

    public boolean doChkHidden(WebDriver driver) throws Exception {
        WebElement element = ((RemoteWebDriver) driver).findElementById("helloText");
        System.out.println(element.getText());
        return TimeDelayUtil.chkText(element, "helloJsp", 5);
    }

    public boolean doInsert(WebDriver driver, Request request) throws Exception {
        driver.get("http://" + request.getServerIp() + ":8080/com/helloJsp");
        if(TimeDelayUtil.chkUrl(driver, "http://" + request.getServerIp() + ":8080/com/helloJsp", 20)) {

            WebElement element = ((RemoteWebDriver) driver).findElementById("name");
            element.sendKeys(request.getName());

            element = ((RemoteWebDriver) driver).findElementById("age");
            element.sendKeys(request.getAge() + "");

//            WebElement elementBtn = ((RemoteWebDriver) driver).findElementById("insertBtn");
            element = driver.findElement(By.id("insertBtn"));
            ((JavascriptExecutor) driver).executeScript("return arguments[0].click();", element);

            element = ((RemoteWebDriver) driver).findElementById("name");
            return TimeDelayUtil.chkValue(element, "", 10, true);
        }
        return false;
    }

    public boolean doQuery(WebDriver driver, String serverIp) throws Exception {
        driver.get("http://" + serverIp + ":8080/com/queryPage");
        if(TimeDelayUtil.chkUrl(driver, "http://" + serverIp + ":8080/com/queryPage", 10)){
            WebElement element = ((RemoteWebDriver) driver).findElementById("queryBtn");

            ((JavascriptExecutor) driver).executeScript("return arguments[0].click();", element);

            element = ((RemoteWebDriver) driver).findElementById("chkField");

            return TimeDelayUtil.chkValue(element, "ok", 10, false);
        }

        return false;
    }

}
