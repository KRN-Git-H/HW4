package controller.member;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import controller.login.LoginUI;
import model.Member;
import model.MemberLicense;
import service.impl.MemberLicenseServiceImpl;
import service.impl.MemberServiceImpl;
import service.impl.MonthRentServiceImpl;
import service.impl.ParkingRecordServiceImpl;
import util.Tool;

import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.UIManager;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JComboBox;
import java.awt.SystemColor;

public class AddMember extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField account;
	private JTextField password;
	private JTextField name;
	private JTextField phone;
	private JTextField license;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AddMember frame = new AddMember();
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
	public AddMember() {
		
		MemberServiceImpl memberSI = new MemberServiceImpl();
		MonthRentServiceImpl monthRentSI = new MonthRentServiceImpl();
		ParkingRecordServiceImpl parkingRecordSI = new ParkingRecordServiceImpl();
		MemberLicenseServiceImpl memberLicenseSI = new MemberLicenseServiceImpl();
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 328);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("註冊會員");
		lblNewLabel.setBounds(173, 10, 87, 34);
		contentPane.add(lblNewLabel);
		
		JPanel panel = new JPanel();
		panel.setBackground(UIManager.getColor("Button.background"));
		panel.setBounds(31, 54, 369, 192);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JLabel lblNewLabel_1 = new JLabel("帳號");
		lblNewLabel_1.setBounds(9, 11, 46, 15);
		panel.add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("密碼");
		lblNewLabel_2.setBounds(9, 40, 46, 15);
		panel.add(lblNewLabel_2);
		
		JLabel lblNewLabel_2_1 = new JLabel("姓名");
		lblNewLabel_2_1.setBounds(9, 71, 46, 15);
		panel.add(lblNewLabel_2_1);
		
		JLabel lblNewLabel_2_2 = new JLabel("電話");
		lblNewLabel_2_2.setBounds(9, 99, 46, 15);
		panel.add(lblNewLabel_2_2);
		
		JLabel lblNewLabel_2_3 = new JLabel("車號");
		lblNewLabel_2_3.setBounds(9, 127, 46, 15);
		panel.add(lblNewLabel_2_3);
		
		JLabel lblNewLabel_2_3_1 = new JLabel("身份別");
		lblNewLabel_2_3_1.setBounds(9, 155, 46, 15);
		panel.add(lblNewLabel_2_3_1);
		
		account = new JTextField();
		account.setBounds(66, 7, 96, 21);
		panel.add(account);
		account.setColumns(10);
		
		password = new JTextField();
		password.setColumns(10);
		password.setBounds(66, 36, 96, 21);
		panel.add(password);
		
		name = new JTextField();
		name.setColumns(10);
		name.setBounds(65, 65, 96, 21);
		panel.add(name);
		
		phone = new JTextField();
		phone.setColumns(10);
		phone.setBounds(65, 96, 96, 21);
		panel.add(phone);
		
		license = new JTextField();
		license.setColumns(10);
		license.setBounds(65, 124, 96, 21);
		panel.add(license);
		
		String[] Opt = {"一般","身障","里民"};
		JComboBox category = new JComboBox(Opt);
		category.setBackground(SystemColor.text);
		category.setBounds(66, 152, 96, 21);
		panel.add(category);
		
		JButton btnNewButton = new JButton("確認");
		btnNewButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				String Account = account.getText();
				String Password = password.getText();			
				String Name = name.getText();			
				String Phone = phone.getText();			
				String License = license.getText();	
				String Category = category.getSelectedItem().toString();
				
				Member member = new Member(Account,Password,Name,Phone,null,Category);
				MemberLicense addMemberLicense = new MemberLicense(Account, License);
				
				if (memberSI.create(member)) {
					memberLicenseSI.create(addMemberLicense);
					
					LoginUI loginui=new LoginUI();
					loginui.setVisible(true);
					dispose();
				}
				
				
			}
		});
		btnNewButton.setBounds(110, 255, 87, 23);
		contentPane.add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("回上一頁");
		btnNewButton_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
				LoginUI loginui=new LoginUI();
				loginui.setVisible(true);
				dispose();
			}
		});
		btnNewButton_1.setBounds(220, 255, 87, 23);
		contentPane.add(btnNewButton_1);

	}
}
