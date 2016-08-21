package com.ciapc.anxin.utils;

import javax.mail.Authenticator;

public class EmailAuthenticator extends Authenticator {
	
	private String account,pwd;
	
	public static EmailAuthenticator mAuthenticator;
	
	public EmailAuthenticator(String account,String pwd){
		this.account = account;
		this.pwd = pwd;
	}

	protected EmailAuthenticator getPasswordAuthentication(String account,String pwd) {
		if(null == mAuthenticator){
			mAuthenticator = new EmailAuthenticator(account, pwd);
		}
		return mAuthenticator;
	}
}
