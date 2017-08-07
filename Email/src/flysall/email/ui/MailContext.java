package flysall.email.ui;

import java.util.Properties;

import javax.mail.*;

import flysall.email.exception.MailConnectionException;

/**
 * 邮箱上下文
 */
public class MailContext {
	//系统用户
	private String user;
	//用户账号
	private String account;
	//密码
	private String password;
	//SMTP邮件服务器
	private String smtpHost;
	//smtp端口
	private int smtpPort;
	//pop3邮件服务器
	private String pop3Host;
	//pop3端口
	private int pop3Port;
	//是否重置信息
	private boolean reset = false;
	
	public MailContext(String user, String account, String password, String smtpHost,
			int smtpPort, String pop3Host, int pop3Port) {
		super();
		this.user = user;
		this.account = account;
		this.password = password;
		this.smtpHost = smtpHost;
		this.smtpPort = smtpPort;
		this.pop3Host = pop3Host;
		this.pop3Port = pop3Port;
	}
	
	public String getUser() {
		return user;
	}
	
	public void setUser(String user) {
		this.user = user;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getSmtpHost() {
		return smtpHost;
	}

	public void setSmtpHost(String smtpHost) {
		this.smtpHost = smtpHost;
	}

	public int getSmtpPort() {
		return smtpPort;
	}

	public void setSmtpPort(int smtpPort) {
		this.smtpPort = smtpPort;
	}

	public String getPop3Host() {
		return pop3Host;
	}

	public void setPop3Host(String pop3Host) {
		this.pop3Host = pop3Host;
	}

	public int getPop3Port() {
		return pop3Port;
	}

	public void setPop3Port(int pop3Port) {
		this.pop3Port = pop3Port;
	}
	
	private Store store;
	
	public Store getStore() {
		//重置了信息，设置session为null
		if (this.reset) {
			this.store = null;
			this.session = null;
			this.reset = false;
		}
		if (this.store == null || !this.store.isConnected()) {
			try {
				Properties props = System.getProperties();
				if(isGmail()) {
					props.setProperty("mail.pop3.socketFactory.class", SSL_FACTORY);
				}
				//创建mail的session
				Session session = Session.getDefaultInstance(props, getAuthenticator());
				//使用pop3协议接收邮件
				URLName url = new URLName("pop3", getPop3Host(), getPop3Port(), null,
						getAccount(), getPassword());
				//得到邮箱的存储对象
				Store store = session.getStore(url);
				store.connect();
				this.store = store;
			} catch(Exception e) {
				e.printStackTrace();
				throw new MailConnectionException("连接邮箱异常，请检查配置");
			}
		}
		return this.store;
	}
	
	private Session session;
	private final static String SSL_FACTORY = "javax.net.ssl.SSLSocketFactory";
	
	public Session getSession() {
		if (this.reset) {
			this.session = null;
			this.store = null;
			this.reset = false;
		}
		if(this.session == null) {
			Properties props = System.getProperties();
			if (isGmail()) {
				props.setProperty("mail.smtp.socketFactory.class", SSL_FACTORY);
			}
			System.out.println(this.getSmtpPort());
			props.put("mail.smtp.host",  this.getSmtpHost());
			props.put("mail.smtp.port", this.getSmtpPort());
			props.put("mail.smtp.auth", true);
			Session sendMailSession = Session.getDefaultInstance(props, getAuthenticator());
			this.session = sendMailSession;
		}
		return this.session;
	}
	
	/*
	 * 判断是否为gmail邮箱
	 */
	private boolean isGmail() {
		if (this.account == null || this.account.trim().equals("")) 
			return false;
		if (this.account.indexOf("@gamil.com") != -1) 
			return true;
		return false;
	}
	
	private Authenticator getAuthenticator() {
		return new Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication (getAccount(), getPassword());
			}
		};
	}
	
	/**
	 * 判断是否重置
	 */
	public boolean isReset() {
		return this.reset;
	}
	
	public void setReset(boolean reset) {
		this.reset = reset;
	}
}


















