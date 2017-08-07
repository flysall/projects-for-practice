package flysall.email.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.Box;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.*;

import flysall.email.exception.LoginException;
import flysall.email.util.FileUtil;
import flysall.email.util.PropertiesUtil;


/**
 * 登录界面
 */
public class LoginFrame {
	//用户名
	private JLabel userLabel = new JLabel("用户名：");
	private JTextField userField = new JTextField(20);
	//确定按钮
	private JButton confirmButton = new JButton("确定");
	//取消按钮
	private JButton cancelButton = new JButton("取消");
	//按钮Box
	private Box buttonBox = Box.createHorizontalBox();
	//用户Box
	private Box userBox = Box.createHorizontalBox();
	//最大Box
	private Box mainBox = Box.createVerticalBox();
	//主界面
	private MainFrame mainFrame;
	
	public LoginFrame() {
		this.userBox.add(Box.createHorizontalStrut(30));
		this.userBox.add(userLabel);
		this.userBox.add(Box.createHorizontalStrut(20));
		this.userBox.add(userField);
		this.userBox.add(Box.createHorizontalStrut(30));
		
		//按钮的Box
		this.buttonBox.add(Box.createHorizontalStrut(30));
		this.buttonBox.add(this.confirmButton);
		this.buttonBox.add(Box.createHorizontalStrut(20));
		this.buttonBox.add(this.cancelButton);
		this.buttonBox.add(Box.createHorizontalStrut(30));
		
		this.mainBox.add(this.mainBox.createVerticalStrut(20));
		this.mainBox.add(this.userBox);
		this.mainBox.add(this.mainBox.createVerticalStrut(20));
		this.mainBox.add(this.buttonBox);
		this.mainBox.add(this.mainBox.createVerticalStrut(20));
		this.add(mainBox);
		this.setLocation(300, 200);
		this.pack();
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle("邮件收发客户端");
		initListener();
	}
				
				
}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	


















