package flysall.email.exception;

/**
 * 发送邮件异常
 */
public class SendMailException extends RuntimeException {

	public SendMailException(String s) {
		super(s);
	}
}
