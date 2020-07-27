package com.lfy.blog.utils;

import com.sun.mail.util.MailSSLSocketFactory;
import org.apache.log4j.Logger;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

/**
 * 发送邮件 2018/3/7.
 */
public class SendEmail {
    private final static Logger log = Logger.getLogger( SendEmail.class);
    public static void sendEmailMessage(String email,String validateCode) {
       try {

           final String from = "962819475@qq.com";    //发邮件的出发地（发件人的信箱）
           String to = email;   //发邮件的目的地（收件人信箱）
           // Get system properties
           Properties props = new Properties();
           //设置qq邮箱服务器
           props.setProperty("mail.host","smtp.qq.com");
           //邮件发送协议
           props.setProperty("mail.transport.protocol","smtp");
           //需要验证用户名和密码
           props.setProperty("mail.smtp.auth", "true");

           //关于qq邮箱，需要加上ssl加密
           MailSSLSocketFactory sf=new MailSSLSocketFactory();
           sf.setTrustAllHosts(true);
           props.put("mail.smtp.ssl.enable","true");
           props.put("mail.smtp.ssl.socketFactory",sf);
           // Setup mail server



           // Get session
           Session session=Session.getDefaultInstance(props, new Authenticator() {
               @Override
               protected PasswordAuthentication getPasswordAuthentication() {
                   //发件人邮件用户名和授权码
                   return new PasswordAuthentication(from,"bivccpxtrutdbced");
               }
           });


           //通过session得到tranport对象
            Transport ts=session.getTransport();
            //使用邮箱的用户名和搜全码连接邮件服务器
           ts.connect("smtp.qq.com",from,"bivccpxtrutdbced");

//    session.setDebug(true);

           //创建邮件：写邮件
           // Define message
           MimeMessage message = new MimeMessage(session);


           //致命邮件的发件人
           // Set the from address
           message.setFrom(new InternetAddress(from));

           //指明邮件的收件人
           // Set the to address
           message.addRecipient( Message.RecipientType.TO,
                   new InternetAddress(to));

            //邮件标题
           // Set the subject
           message.setSubject("激活邮件通知");

           //邮件的文本内容
           // Set the content
           message.setContent( "<a href=\"http://localhost:8089/activecode?email="+email+"&validateCode="+validateCode+"\" target=\"_blank\">请于24小时内点击激活</a>","text/html;charset=gb2312");
           message.saveChanges();

           //发送邮件
           Transport.send(message);

           log.info( "send validateCode to " + email );
       }catch (Exception e){

           log.info( "Send Email Exception:"+e.getMessage() );
       }

    }
}
