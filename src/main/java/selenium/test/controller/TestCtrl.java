package selenium.test.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import selenium.test.service.TestService;
import selenium.test.vo.TestFlow;
import selenium.test.vo.Response;
import selenium.test.vo.TestVo;

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
    public Response<TestFlow> testChrome() throws Exception{
        return testService.chromeTest();
    }
    @GetMapping("testIE")
    public Response<TestFlow> testIe() throws Exception{
        return testService.ieTest();
    }
    @GetMapping("testError")
    public Response testError() throws Exception{
        return new Response(-1, "test error");
    }

    @PostMapping("testPost")
    public TestVo testPost(@RequestBody TestVo testVo) throws Exception{
        return testVo;
    }
}
