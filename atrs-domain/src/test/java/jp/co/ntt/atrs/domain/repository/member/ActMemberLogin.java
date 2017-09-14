package jp.co.ntt.atrs.domain.repository.member;

import java.io.Serializable;
import java.util.Date;

/*
 * DBへセットアップ後の検証用model
 */
public class ActMemberLogin implements Serializable {

	/**
     * serialVersionUID。
     */
    private static final long serialVersionUID = 4454136798154120517L;

    /* 追加 */
    private String customerNo;
    
    /**
     * パスワード。
     */
    private String password;

    /**
     * 前回パスワード。
     */
    private String lastPassword;

    /**
     * ログイン時刻。
     */
    private Date loginDateTime;

    /**
     * ログインフラグ。
     */
    private Boolean loginFlg;

	public String getCustomerNo() {
		return customerNo;
	}

	public void setCustomerNo(String customerNo) {
		this.customerNo = customerNo;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getLastPassword() {
		return lastPassword;
	}

	public void setLastPassword(String lastPassword) {
		this.lastPassword = lastPassword;
	}

	public Date getLoginDateTime() {
		return loginDateTime;
	}

	public void setLoginDateTime(Date loginDateTime) {
		this.loginDateTime = loginDateTime;
	}

	public Boolean getLoginFlg() {
		return loginFlg;
	}

	public void setLoginFlg(Boolean loginFlg) {
		this.loginFlg = loginFlg;
	}
	
    

}
