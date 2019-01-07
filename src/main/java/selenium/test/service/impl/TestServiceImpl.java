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
    public Response<TestStepVo> chromeTest(Request request) throws Exception {
        WebDriver driver = BrowserDriverUtil.getChromeDriver();
        return new Response(0, "success", basicScript.script(driver, request));
    }

    @Override
    public Response<TestStepVo> ieTest(Request request) throws Exception {
        WebDriver driver = BrowserDriverUtil.getIEDriver();
        return new Response(0, "success", basicScript.script(driver, request));
    }

    @Override
    public Response<Map<String, TestStepVo>> allTest(Request request) throws Exception {
        Map<String, TestStepVo> map = new HashMap();
        map.put("chrome", basicScript.script(BrowserDriverUtil.getChromeDriver(), request));
        map.put("ie", basicScript.script(BrowserDriverUtil.getIEDriver(), request));
        return new Response(0, "success", map);
    }
}
