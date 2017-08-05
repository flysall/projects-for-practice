package flysall.email.mail;

/**
 * 加载邮件异常 
 */
public class LoadMailException extends RuntimeException{
	public LoadMailException(String s){
		super(s);
	}
}
