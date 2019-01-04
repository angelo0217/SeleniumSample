package selenium.test.vo;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created on 2019/1/3
 *
 * @author dean
 * @email loveangelo0217@gmail.com
 * @since 1.0
 */
public class TestStepVo {
    private String acts;

    private String doLogin = "N";

    private String chkField = "N";

    private String doInsert = "N";

    private String doQuery = "N";

    private String exceptionStr = "";

    public String getActs() {
        return acts;
    }

    public void setActs(String acts) {
        this.acts = acts;
    }

    public String getDoLogin() {
        return doLogin;
    }

    public void setDoLogin(String doLogin) {
        this.doLogin = doLogin;
    }

    public String getChkField() {
        return chkField;
    }

    public void setChkField(String chkField) {
        this.chkField = chkField;
    }

    public String getDoInsert() {
        return doInsert;
    }

    public void setDoInsert(String doInsert) {
        this.doInsert = doInsert;
    }

    public String getDoQuery() {
        return doQuery;
    }

    public void setDoQuery(String doQuery) {
        this.doQuery = doQuery;
    }

    public String getExceptionStr() {
        return exceptionStr;
    }

    public void setExceptionStr(String exceptionStr) {
        this.exceptionStr = exceptionStr;
    }
}
