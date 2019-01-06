package selenium.test.service.impl;

import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import selenium.test.script.BasicScript;
import selenium.test.service.TestService;
import selenium.test.util.BrowserDriverUtil;
import selenium.test.vo.Request;
import selenium.test.vo.TestStepVo;
import selenium.test.vo.Response;

import java.util.HashMap;
import java.util.Map;

/**
 * Created on 2019/1/3
 *
 * @author dean
 * @email loveangelo0217@gmail.com
 * @since 1.0
 */
@Service
public class TestServiceImpl implements TestService {

    @Autowired
    BasicScript basicScript;

    @Override
    public Response<TestStepVo> chromeTest(Request request){
        try {
            WebDriver driver = null;
            try {
                driver = BrowserDriverUtil.getChromeDriver();
            } catch (Exception e) {
                return new Response(-1, "Chrome Driver error");
            }
            if (driver == null) return new Response(-2, "Chrome Driver is null");
            return new Response(0, "success", basicScript.script(driver, request));
        } catch (Exception ex) {
            return new Response(99, "Chrome Exception :" + ex.getMessage());
        }
    }

    @Override
    public Response<TestStepVo> ieTest(Request request){
        try {
            WebDriver driver = null;
            try {
                driver = BrowserDriverUtil.getIEDriver();
            } catch (Exception e) {
                return new Response(-1, "IE Driver error");
            }
            if (driver == null) return new Response(-2, "IE Driver is null");
            return new Response(0, "success", basicScript.script(driver, request));
        } catch (Exception ex) {
            return new Response(99, "IE Exception :" + ex.getMessage());
        }
    }

    @Override
    public Response allTest(Request request) {
        Map<String, Object> map = new HashMap();

        Response chrome = chromeTest(request);
        if(chrome.getCode() != 0) return chrome;

        Response ie = ieTest(request);
        if(ie.getCode() != 0) return ie;

        map.put("chrome", chrome);
        map.put("ie", ie);
        return new Response(0, "success", map);
    }
}
