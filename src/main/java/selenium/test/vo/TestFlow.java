package selenium.test.vo;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created on 2019/1/3
 *
 * @author dean
 * @email loveangelo0217@gmail.com
 * @since 1.0
 */
public class TestFlow {
    @JsonProperty("enter_act_pwd")
    private boolean enterData = false;
    @JsonProperty("do_login")
    private boolean login = false;
    @JsonProperty("chk_form_info")
    private boolean chkFormInfo = false;
    @JsonProperty("get_chk_data")
    private String chkData = "";

    public boolean isEnterData() {
        return enterData;
    }

    public void setEnterData(boolean enterData) {
        this.enterData = enterData;
    }

    public boolean isLogin() {
        return login;
    }

    public void setLogin(boolean login) {
        this.login = login;
    }

    public boolean isChkFormInfo() {
        return chkFormInfo;
    }

    public void setChkFormInfo(boolean chkFormInfo) {
        this.chkFormInfo = chkFormInfo;
    }

    public String getChkData() {
        return chkData;
    }

    public void setChkData(String chkData) {
        this.chkData = chkData;
    }
}
