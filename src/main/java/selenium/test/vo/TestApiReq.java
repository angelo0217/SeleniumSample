package selenium.test.vo;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created on 2019/1/10
 *
 * @author dean
 * @email loveangelo0217@gmail.com
 * @since 1.0
 */
public class TestApiReq {
    @JsonProperty("header")
    private Header header;
    @JsonProperty("body")
    private Body body;

    public Header getHeader() {
        return header;
    }

    public void setHeader(Header header) {
        this.header = header;
    }

    public Body getBody() {
        return body;
    }

    public void setBody(Body body) {
        this.body = body;
    }

    public static class Header{
        @JsonProperty("client_id")
        private String clientId;

        public String getClientId() {
            return clientId;
        }

        public void setClientId(String clientId) {
            this.clientId = clientId;
        }
    }
    public static class Body{
        @JsonProperty("name")
        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
