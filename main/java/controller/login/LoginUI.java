package controller.login;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import controller.ManagerUI;
import controller.member.AddMember;
import model.Employee;
import model.Member;
import service.impl.EmployeeServiceImpl;
import service.impl.MemberServiceImpl;
import util.Tool;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JPasswordField;

public class LoginUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField account;
	private JPasswordField password;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LoginUI frame = new LoginUI();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public LoginUI() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(51, 30, 338, 186);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("帳號：");
		lblNewLabel.setBounds(43, 26, 46, 15);
		panel.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("密碼：");
		lblNewLabel_1.setBounds(43, 58, 46, 15);
		panel.add(lblNewLabel_1);
	
		account = new JTextField();
		account.setBounds(99, 23, 96, 21);
		panel.add(account);
		account.setColumns(10);
		
		password = new JPasswordField();
		password.setBounds(99, 55, 96, 21);
		panel.add(password);
		
		JButton btnNewButton = new JButton("登入");
		btnNewButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				String Account=account.getText();
				String Password=String.valueOf(password.getPassword());
				
				Employee employee=new EmployeeServiceImpl().login(Account, Password);
				Member member=new MemberServiceImpl().login(Account, Password);
				
				Tool.saveFile(employee,"employee.txt");
				Tool.saveFile(member,"member.txt");				

				if(employee!=null)
				{
					ManagerUI managerUI = new ManagerUI();
					managerUI.setVisible(true);		
					dispose();
				}
				else
				{
					if(member!=null)
					{
						Tool.saveFile(member,"member.txt");
						ManagerUI managerUI = new ManagerUI();
						managerUI.setVisible(true);		
						dispose();
					}
					else
					{
						LoginErrorUI loginError=new LoginErrorUI();
						loginError.setVisible(true);
						dispose();
					}
				}
			}
		});
		btnNewButton.setBounds(43, 108, 87, 23);
		panel.add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("註冊");
		btnNewButton_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				AddMember addmember=new AddMember();
				addmember.setVisible(true);
				dispose();
			}
		});
		btnNewButton_1.setBounds(163, 108, 87, 23);
		panel.add(btnNewButton_1);
		


	}
}
