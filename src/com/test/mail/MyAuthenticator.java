/**
 * 
 */
package com.test.mail;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

/**
 * 邮件用户名密码认证
 * @author huozhicheng@gmail.com
 * @date 2012-12-3上午11:17:46
 * @version 1.0
 */
public class MyAuthenticator extends Authenticator{
	
	String userName=null;  
    String password=null;  
       
    public MyAuthenticator(){  
    }  
    public MyAuthenticator(String username, String password) {   
        this.userName = username;   
        this.password = password;   
    }   
    protected PasswordAuthentication getPasswordAuthentication(){  
        return new PasswordAuthentication(userName, password);  
    }  
}
