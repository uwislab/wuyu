package com.fiveup.core.monitor.common;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Date;
import java.util.Properties;

public class SendMailText {
    public static String senderAddress = "a17713939995@163.com";
    public static String recipientAddress = "1762530316@qq.com";
    public static String senderAccount = "a17713939995";
    public static String senderPassword = "FEBZEFYGHGTKGWDO";
    public static void send(String ss) throws Exception {
        Properties props = new Properties();
        props.setProperty("mail.smtp.auth", "true");
        props.setProperty("mail.transport.protocol", "smtp");
        props.setProperty("mail.smtp.host", "smtp.163.com");
        Session session = Session.getInstance(props);
        session.setDebug(true);
        Message msg = getMimeMessage(session,ss);
        Transport transport = session.getTransport();
        transport.connect(senderAccount, senderPassword);
        transport.sendMessage(msg, new Address[]{new InternetAddress("1762530316@qq.com")});
        transport.close();
    }
    public static MimeMessage getMimeMessage(Session session,String ss) throws Exception{
        MimeMessage msg = new MimeMessage(session);
        msg.setFrom(new InternetAddress(senderAddress));
        msg.setRecipient(MimeMessage.RecipientType.TO,new InternetAddress(recipientAddress));
        msg.setSubject("错误提示","UTF-8");
        msg.setContent(ss, "text/html;charset=UTF-8");
        msg.setSentDate(new Date());

        return msg;
    }

}

