package selenium.test.exception;

/**
 * Created on 2019/1/7
 *
 * @author dean
 * @email loveangelo0217@gmail.com
 * @since 1.0
 */
public class SysException extends Exception{
    public SysException(int code, String msg){

    }
    private int code;
    private String msg;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
