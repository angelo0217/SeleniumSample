package selenium.test.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import selenium.test.service.TestService;

/**
 * Created on 2019/1/2
 *
 * @author dean
 * @email loveangelo0217@gmail.com
 * @since 1.0
 */
@RestController
public class TestCtrl {
    @Autowired
    TestService testService;

    @GetMapping("testChrome")
    public String testChrome() throws Exception{
        return testService.chromeTest();
    }
    @GetMapping("testError")
    public String testError() throws Exception{
        return "error";
    }
}
