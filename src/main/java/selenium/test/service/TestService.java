package selenium.test.service;

import selenium.test.vo.Request;
import selenium.test.vo.TestStepVo;
import selenium.test.vo.Response;

import java.util.Map;

/**
 * Created on 2019/1/3
 *
 * @author dean
 * @email loveangelo0217@gmail.com
 * @since 1.0
 */
public interface TestService {
    public Response<TestStepVo> chromeTest(Request request) throws Exception;

    public Response<TestStepVo> ieTest(Request request) throws Exception;

    public Response<Map<String, TestStepVo>> allTest(Request request) throws Exception;
}
