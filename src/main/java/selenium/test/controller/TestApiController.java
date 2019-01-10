package selenium.test.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import selenium.test.vo.Response;
import selenium.test.vo.TestApiReq;

/**
 * Created on 2019/1/10
 *
 * @author dean
 * @email loveangelo0217@gmail.com
 * @since 1.0
 */
@RestController
public class TestApiController {

    @PostMapping("api1")
    public Response doSuccess(@RequestBody TestApiReq req){
        return new Response(0, "success", req.getBody());
    }

    @PostMapping("api2")
    public Response doError(@RequestBody TestApiReq req){
        return new Response(-1, "test error", req.getBody());
    }
}
