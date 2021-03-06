package selenium.test.script;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.springframework.stereotype.Component;
import selenium.test.RtnCode;
import selenium.test.exception.SysException;
import selenium.test.util.TimeDelayUtil;
import selenium.test.vo.Request;
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
    /**
     * 腳本入口
     *
     * @param driver
     * @param request
     * @return
     * @throws Exception
     */
    public TestStepVo script(WebDriver driver, Request request) throws Exception {
        TestStepVo testStepVo = new TestStepVo();
        testStepVo.setActs(request.getRange());

        try {
            //要登入才能做事，無可避免
            if (doLogin(driver, request.getServerIp())) {
                testStepVo.setDoLogin("true");

                String[] acts = request.getRange().split(",");

                //判斷步驟，執行需要跑的測試
                for (String act : acts) {
                    if (act.trim().equals("chk")) {
                        testStepVo.setChkField(doChkHidden(driver) + "");
                    } else if (act.trim().equals("insert")) {
                        testStepVo.setDoInsert(doInsert(driver, request) + "");
                    } else if (act.trim().equals("query")) {
                        testStepVo.setDoQuery(doQuery(driver, request.getServerIp()) + "");
                    }
                }
                Thread.sleep(3000);

            } else {
                testStepVo.setDoLogin("login fail");
            }

        } catch (Exception e) {
            throw e;
        } finally {
            driver.quit();
        }
        return testStepVo;
    }

    /**
     * 登入腳本-default
     *
     * @param driver
     * @param serverIp
     * @return
     * @throws Exception
     */
    public boolean doLogin(WebDriver driver, String serverIp) throws Exception {
        driver.get("http://" + serverIp + "/login");
        WebElement element = driver.findElement(By.id("username1"));
        element.sendKeys("admin");

        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("$('#password1').val('12345');");
        js.executeScript("$('#testBtn').click();");
        return TimeDelayUtil.chkUrl(driver, "http://" + serverIp + "/com/helloJsp", 20);
    }

    /**
     * 登入後檢查第一頁資訊 - chk
     *
     * @param driver
     * @return
     * @throws Exception
     */
    public String doChkHidden(WebDriver driver) throws Exception {
        WebElement element = ((RemoteWebDriver) driver).findElementById("helloText");
        if (TimeDelayUtil.chkText(element, "helloJsp", 5)) {
            return "success";
        } else {
            return "chk text error";
        }
    }

    /**
     * 新增資料腳本 - insert
     *
     * @param driver
     * @param request
     * @return
     * @throws Exception
     */
    public String doInsert(WebDriver driver, Request request) throws Exception {
        driver.get("http://" + request.getServerIp() + "/com/helloJsp");
        if (TimeDelayUtil.chkUrl(driver, "http://" + request.getServerIp() + "/com/helloJsp", 20)) {

            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("$('#name').val('"+request.getName()+"');");
            js.executeScript("$('#age').val('"+request.getAge()+"');");
            js.executeScript("$('#insertBtn').click();");

//            WebElement element = ((RemoteWebDriver) driver).findElementById("name");
//            element.sendKeys(request.getName());
//
//            element = ((RemoteWebDriver) driver).findElementById("age");
//            element.sendKeys(request.getAge() + "");
//
//            element = driver.findElement(By.id("insertBtn"));
//            ((JavascriptExecutor) driver).executeScript("return arguments[0].click();", element);
            return TimeDelayUtil.chkCommonHidden(driver, "chkField", 5);
        }
        return "redirect error";
    }

    /**
     * 查詢資料腳本 - query
     *
     * @param driver
     * @param serverIp
     * @return
     * @throws Exception
     */
    public String doQuery(WebDriver driver, String serverIp) throws Exception {
        driver.get("http://" + serverIp + "/com/queryPage");
        if (TimeDelayUtil.chkUrl(driver, "http://" + serverIp + "/com/queryPage", 10)) {
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("$('#queryBtn').click();");

            return TimeDelayUtil.chkCommonHidden(driver, "chkField", 5);
        }
        return "redirect error";
    }
}
