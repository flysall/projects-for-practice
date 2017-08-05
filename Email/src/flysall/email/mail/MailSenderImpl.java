package flysall.email.mail;

import java.util.*;

import javax.mail.Address;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;

import flysall.email.exception.SendMailException;
import flysall.email.object.*;
import flysall.email.ui.*;

/**
 * 邮件发送实现类
 */
public class MailSenderImpl implements MailSender {
	@Override
	public Mail send(Mail mail, MailContext ctx) {
		try {
			Session session = ctx.getSession();
			Message mailMessage = new MimeMessage(session);
			// 设置发送人地址
			Address from = new InternetAddress(ctx.getUser() + " <" + ctx.getAccount() + ">");
			mailMessage.setFrom(from);
			// 设置收件人地址
			Address[] to = getAddress(mail.getReceivers());
			mailMessage.setRecipients(Message.RecipientType.TO, to);
			// 设置抄送人地址
			Address[] cc = getAddress(mail.getCcs());
			mailMessage.setRecipients(Message.RecipientType.CC, cc);
			// 设置主题
			mailMessage.setSubject(mail.getSubject());
			// 发送日期
			mailMessage.setSentDate(new Date());
			// 构建整封邮件的容器
			Multipart main = new MimeMultipart();
			// 正文的body
			BodyPart body = new MimeBodyPart();
			body.setContent(mail.getContent(), "text/html;charset=utf-8");
			main.addBodyPart(body);
			// 处理附件
			for (FileObject f : mail.getFiles()) {
				// 每个附件的body
				MimeBodyPart fileBody = new MimeBodyPart();
				fileBody.attachFile(f.getFile());
				// 为文件名转码
				fileBody.setFileName(MimeUtility.encodeText(f.getSourceName()));
				main.addBodyPart(fileBody);
			}
			// 将正文的Mutipart对象摄入Message
			mailMessage.setContent(main);
			Transport.send(mailMessage);
			return mail;
		} catch (Exception e) {
			e.printStackTrace();
			throw new SendMailException("发送邮件错误，请检查邮箱配置及邮箱相关信息");
		}
	}

	/*
	 * 获得所有收件人的地址或抄送的地址
	 */
	private Address[] getAddress(List<String> addList) throws Exception {
		Address[] result = new Address[addList.size()];
		for (int i = 0; i < addList.size(); i++) {
			if (addList.get(i) == null || "".equals(addList.get(i))) {
				continue;
			}
			result[i] = new InternetAddress(addList.get(i));
		}
		return result;
	}
}
