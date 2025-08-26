package controller;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;

import controller.admin.MemberModifyUI;
import controller.admin.MonthRentModifyUI;
import controller.admin.ParkingReportUI;
import controller.login.LoginUI;
import model.Employee;
import model.Member;
import service.impl.ParkingRecordServiceImpl;
import util.Tool;

import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JLabel;

public class ManagerUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	/** 
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ManagerUI frame = new ManagerUI();
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
	public <E> ManagerUI() {
		Employee employee=(Employee)Tool.readFile("employee.txt");	
		Member member=(Member)Tool.readFile("member.txt");

		setTitle("PorderManager");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnNewButton_2 = new JButton("會員資料管理");
		btnNewButton_2.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
				MemberModifyUI memberModifyUI = new MemberModifyUI();
				memberModifyUI.setVisible(true);		
				
				dispose();
			}
		});
		btnNewButton_2.setBounds(150, 50, 148, 34);
		contentPane.add(btnNewButton_2);
		
		JLabel logout = new JLabel("");
		logout.setBounds(46, 8, 135, 31);
		contentPane.add(logout);

		JButton monthRent = new JButton("月租繳費管理");
		
		String show;
		
		if(Tool.isAdmin(employee))
		{
			show=employee.getAccount();
			monthRent.setVisible(true);
		}
		else
		{
			show=member.getAccount();
			monthRent.setVisible(false);
		}	

		logout.setText(show + "歡迎");	

		monthRent.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				MonthRentModifyUI monthrentmodifyUI = new MonthRentModifyUI();
				monthrentmodifyUI.setVisible(true);
				dispose();
			}
		});
		monthRent.setBounds(150, 100, 148, 31);
		contentPane.add(monthRent);
		
		JButton btnNewButton = new JButton("登出");
		btnNewButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				LoginUI loginui = new LoginUI();
				loginui.setVisible(true);
				dispose();
			}
		});
		btnNewButton.setBounds(191, 224, 64, 23);
		contentPane.add(btnNewButton);
		
		JButton revenue = new JButton("營收報表");
		if(Tool.isAdmin(employee))
		{
			revenue.setVisible(true);
		}
		else
		{
			revenue.setVisible(false);
		}		
		revenue.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
			    // 建立 Service
			    ParkingRecordServiceImpl service = new ParkingRecordServiceImpl();

			    // 建立 UI，傳入 Service
			    ParkingReportUI parkingReportUI = new ParkingReportUI(service);
			    parkingReportUI.setVisible(true);

			    // 關閉 ManagerUI
			    dispose();
			}
		});
		revenue.setBounds(150, 145, 148, 31);
		contentPane.add(revenue);
		


	}
}
