package selenium.test.vo;

/**
 * Created on 2019/1/3
 *
 * @author dean
 * @email loveangelo0217@gmail.com
 * @since 1.0
 */
public class Response<T> {
    public Response(int code, String mag) {
        this.code = code;
        this.msg = mag;
    }

    public Response(int code, String mag, T data) {
        this.code = code;
        this.msg = mag;
        this.data = data;
    }

    private int code;
    private String msg;
    private T data;

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

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
