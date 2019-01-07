package selenium.test.controller;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import selenium.test.RtnCode;
import selenium.test.vo.Response;

import javax.servlet.http.HttpServletRequest;

/**
 * 系統若拋出Exception 都會經過此
 * Created on 2018-12-19
 *
 * @author dean
 * @email loveangelo0217@gmail.com
 * @since 1.0
 */
@RestController
@ControllerAdvice
//@ControllerAdvice(basePackages ="com.demo.basic")
public class ExceptionController {

    @ExceptionHandler(value = Exception.class)
    public Response exCenter(HttpServletRequest req, Exception ex) {
        ex.printStackTrace();
        return new Response(RtnCode.SYSTEM_ERROR, "系統錯誤 : "+ ex.getMessage());
    }
}
