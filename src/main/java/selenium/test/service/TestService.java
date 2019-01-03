package selenium.test.service;

import selenium.test.vo.ChromeFlow;
import selenium.test.vo.Response;

/**
 * Created on 2019/1/3
 *
 * @author dean
 * @email loveangelo0217@gmail.com
 * @since 1.0
 */
public interface TestService {
    public Response<ChromeFlow> chromeTest() throws Exception;
}
