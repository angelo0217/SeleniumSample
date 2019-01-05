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
    public static boolean chkUrl(WebDriver driver, String after, int times) throws Exception{
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
