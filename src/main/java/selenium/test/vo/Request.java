package selenium.test.vo;

/**
 * Created on 2019/1/4
 *
 * @author dean
 * @email loveangelo0217@gmail.com
 * @since 1.0
 */
public class Request {
    private String serverIp;
    private String range;
    private String name;
    private int age;

    public String getServerIp() {
        return serverIp;
    }

    public void setServerIp(String serverIp) {
        this.serverIp = serverIp;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRange() {
        return range;
    }

    public void setRange(String range) {
        this.range = range;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
