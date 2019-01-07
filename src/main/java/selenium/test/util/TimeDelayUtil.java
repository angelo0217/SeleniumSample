package selenium.test.util;

import org.eclipse.jetty.util.StringUtil;
import org.openqa.selenium.Alert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import selenium.test.RtnCode;
import selenium.test.exception.SysException;

/**
 * Created on 2019/1/5
 *
 * @author dean
 * @email loveangelo0217@gmail.com
 * @since 1.0
 */
public class TimeDelayUtil {
    /**
     * 檢查導頁，因為導頁多少有延遲，故加上傳入秒數
     *
     * @param driver
     * @param after
     * @param times
     * @return
     * @throws Exception
     */
    public static boolean chkUrl(WebDriver driver, String after, int times) throws Exception {
        System.out.println(driver.getCurrentUrl());
        int cnt = 0;
        //登入緩衝
        while (cnt < times && !driver.getCurrentUrl().equals(after)) {
            Thread.sleep(1000);
            cnt++;
        }
        if (driver.getCurrentUrl().equals(after)) {
            return true;
        }
        return false;
    }

    /**
     * 檢查欄位Text，加上傳入秒數是預防可能是執行特定動作有延遲
     *
     * @param element
     * @param after
     * @param times
     * @return
     * @throws Exception
     */
    public static boolean chkText(WebElement element, String after, int times) throws Exception {
        int cnt = 0;
        while (cnt < times && !element.getText().equals(after)) {
            Thread.sleep(1000);
            cnt++;
        }
        if (element.getText().equals(after)) {
            return true;
        }
        return false;
    }

    /**
     * 檢查欄位Value，加上傳入秒數是預防可能是執行特定動作有延遲
     *
     * @param element
     * @param after
     * @param times
     * @return
     * @throws Exception
     */
    public static boolean chkValue(WebDriver driver, WebElement element, String after, int times, boolean empty) throws Exception {
        int cnt = 0;

        if (empty) {
            while (cnt < times && !StringUtil.isBlank(element.getAttribute("value"))) {
                Thread.sleep(1000);
                cnt++;
            }
            if (StringUtil.isBlank(element.getAttribute("value"))) {
                return true;
            }
        } else {
            while (cnt < times && !element.getAttribute("value").equals(after)) {
                Thread.sleep(1000);
                cnt++;
            }
            Thread.sleep(100);

            if (element.getAttribute("value").equals(after)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 最後檢查隱藏欄位是否被塞入成功
     *
     * @param driver
     * @return
     * @throws Exception
     */
    public static String chkCommonHidden(WebDriver driver, String field, int waitAlert) throws Exception {
        WebElement element = null;
        for(int i = 0; i< waitAlert; i++){
            Thread.sleep(1000);
            try{
                TimeDelayUtil.closeAlert(driver);
                element = ((RemoteWebDriver) driver).findElementById(field);
                if(element != null) break;
            }catch (Exception e){

            }
        }
        if(element != null) {
            //這邊諾有拋錯，通常是Alert超過上面秒數，取元素當下彈出視窗會錯
            if (TimeDelayUtil.chkValue(driver, element, "ok", 5, false)) {
                return "success";
            } else {
                return element.getAttribute("value");
            }
        }else{
            //這邊若拋錯，就真的拿不到
            throw new SysException(RtnCode.NOT_GET_ELEMENT_ERROR, "element not get");
        }

    }

    public static boolean closeAlert(WebDriver driver) {
        try {
            Alert alert_box = driver.switchTo().alert();
            alert_box.dismiss();
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
