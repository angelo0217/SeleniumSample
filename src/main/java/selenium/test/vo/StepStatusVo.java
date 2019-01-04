package selenium.test.vo;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created on 2019/1/3
 *
 * @author dean
 * @email loveangelo0217@gmail.com
 * @since 1.0
 */
public class StepStatusVo {
    private String acts;

    private boolean doLogin = false;

    private boolean chkField = false;

    private boolean doInsert = false;

    private boolean doQuery = false;

    private String exceptionStr = "";

    public String getActs() {
        return acts;
    }

    public void setActs(String acts) {
        this.acts = acts;
    }

    public boolean isDoLogin() {
        return doLogin;
    }

    public void setDoLogin(boolean doLogin) {
        this.doLogin = doLogin;
    }

    public boolean isChkField() {
        return chkField;
    }

    public void setChkField(boolean chkField) {
        this.chkField = chkField;
    }

    public boolean isDoInsert() {
        return doInsert;
    }

    public void setDoInsert(boolean doInsert) {
        this.doInsert = doInsert;
    }

    public boolean isDoQuery() {
        return doQuery;
    }

    public void setDoQuery(boolean doQuery) {
        this.doQuery = doQuery;
    }

    public String getExceptionStr() {
        return exceptionStr;
    }

    public void setExceptionStr(String exceptionStr) {
        this.exceptionStr = exceptionStr;
    }
}
