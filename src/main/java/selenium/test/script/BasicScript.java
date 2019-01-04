package selenium.test.script;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import selenium.test.vo.Response;
import selenium.test.vo.TestFlow;

/**
 * Created on 2019/1/4
 *
 * @author dean
 * @email loveangelo0217@gmail.com
 * @since 1.0
 */
public class BasicScript {

    public static Response<TestFlow> script(WebDriver driver) throws Exception{
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


                int cnt = 0;
                //登入緩衝
                while(cnt < 20 && driver.getCurrentUrl().equals("http://"+serverIp+":8080/login")){
                    Thread.sleep(1000);
                    cnt ++;
                }

                if (driver.getCurrentUrl().equals("http://"+serverIp+":8080/com/helloJsp")) {
                    testFlow.setLogin(true);
                }
//                element = ((RemoteWebDriver) driver).findElementByClassName("container-fluid");
                element = ((RemoteWebDriver) driver).findElementById("helloText");
                if(element.getText().equals("helloJsp")){
                    testFlow.setChkFormInfo(true);
                }
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
