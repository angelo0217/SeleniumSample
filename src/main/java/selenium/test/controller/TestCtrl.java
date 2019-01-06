package selenium.test.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import selenium.test.service.TestService;
import selenium.test.vo.Request;
import selenium.test.vo.Response;
import selenium.test.vo.TestStepVo;

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

    @PostMapping("testChrome")
    public Response<TestStepVo> testChrome(@RequestBody Request request){
        return testService.chromeTest(request);
    }
    @PostMapping("testIE")
    public Response<TestStepVo> testIe(@RequestBody Request request){
        return testService.ieTest(request);
    }
    @PostMapping("testAll")
    public Response<TestStepVo> testAll(@RequestBody Request request){
        return testService.allTest(request);
    }
    @PostMapping("testError")
    public Response testError() throws Exception{
        return new Response(-1, "test error");
    }

}
