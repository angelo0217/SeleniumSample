package selenium.test.util;

import org.eclipse.jetty.util.StringUtil;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

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
     * @param driver
     * @param after
     * @param times
     * @return
     * @throws Exception
     */
    public static boolean chkUrl(WebDriver driver, String after, int times) throws Exception{
        System.out.println(driver.getCurrentUrl());
        int cnt = 0;
        //登入緩衝
        while (cnt < times && !driver.getCurrentUrl().equals(after)) {
            Thread.sleep(1000);
            cnt++;
        }
        if(driver.getCurrentUrl().equals(after)){
            return true;
        }
        return false;
    }

    /**
     * 檢查欄位Text，加上傳入秒數是預防可能是執行特定動作有延遲
     * @param element
     * @param after
     * @param times
     * @return
     * @throws Exception
     */
    public static boolean chkText(WebElement element, String after, int times) throws Exception{
        int cnt = 0;
        while (cnt < times && !element.getText().equals(after)) {
            Thread.sleep(1000);
            cnt++;
        }
        if(element.getText().equals(after)){
            return true;
        }
        return false;
    }
    /**
     * 檢查欄位Value，加上傳入秒數是預防可能是執行特定動作有延遲
     * @param element
     * @param after
     * @param times
     * @return
     * @throws Exception
     */
    public static boolean chkValue(WebElement element, String after, int times, boolean empty) throws Exception{
        int cnt = 0;

        if(empty){
            while (cnt < times && !StringUtil.isBlank(element.getAttribute("value"))) {
                Thread.sleep(1000);
                cnt++;
            }

            if(StringUtil.isBlank(element.getAttribute("value"))){
                return true;
            }
        }else {
            while (cnt < times && !element.getAttribute("value").equals(after)) {
                Thread.sleep(1000);
                cnt++;
            }
            if (element.getAttribute("value").equals(after)) {
                return true;
            }
        }
        return false;
    }
}
