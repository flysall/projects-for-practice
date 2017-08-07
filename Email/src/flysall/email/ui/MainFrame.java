package flysall.email.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Vector;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.JToolBar;
import javax.swing.JTree;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;

import flysall.email.box.DeletedBox;
import flysall.email.object.*;
import flysall.email.system.*;
import flysall.email.util.*;

/**
 * 主界面
 */
public class MainFrame {
	//欢迎的JLabel
	private JLabel welcome = new JLabel("欢迎您：");
	//分隔左边的树与右边邮件信息的JSplitPane
	private JSplitPane mailSplitPane;
	//右边邮件列表与邮件信息的JSplitPane
	private JSplitPane mailListInfoPane;
	//邮件详细信息的JSplitPane, 左边是邮件信息, 右边是附件
	private JSplitPane mailInfoPane;
	//邮件列表的JTable
	private MailListTable mailListTable;
	//存放列表的的JScrollPane
	private JScrollPane tablePane;
	//邮件导航树的JScrollPane
	private JScrollPane treePane;
	//邮件导航树
	private JTree tree;
	//邮件显示JTextArea
	private JTextArea mailTextArea = new JTextArea(10, 80);
	//邮件显示的JScrollPane, 存放显示邮件的JTextArea
	private JScrollPane mailScrollPane;
	//邮件附件列表
	private JScrollPane filePane;
	//邮件附件名称显示
	private JList fileList;
	//工具栏
	private JToolBar toolBar = new JToolBar();
	
	//收件箱的Mail对象集合，代表所有在收件箱中的邮件
	private List<Mail> inMails;
	//发件箱的邮件集合
	private List<Mail> outMails;
	//成功发送的邮件集合
	private List<Mail> sentMails;
	//草稿箱的邮件集合
	private List<Mail> draftMails;
	//垃圾箱的邮件集合
	private List<Mail> deleteMails;
	//当前界面列表所显示的对象
	private List<Mail> currentMails;

	//写邮件的JFrame
	private MailFrame mailFrame;
	//系统设置界面对象
	private SetupFrame setupFrame;
	//邮箱加载对象
	private MailLoader mailLoader = new MailLoaderImpl();
	//本地中的邮件处理对象
	private SystemHandler systemHandler = new SystemHandlerImpl();
	//本地中的邮件加载对象
	private SystemLoader systemLoader = new SystemLoaderImpl();
	//发送邮件对象
	private MailSender mailSender = new MailSenderImpl();
	//当前打开的文件对象
	private Mail currentMail;
	//接收邮件的间隔, 单位毫秒
	private long receiveInterval = 1000 * 10;
}






















