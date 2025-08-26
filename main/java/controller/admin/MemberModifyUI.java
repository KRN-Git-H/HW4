package controller.admin;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.event.CellEditorListener;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import controller.ManagerUI;
import dao.impl.MemberDaoImpl;
import dao.impl.MemberLicenseDaoImpl;
import dao.impl.MonthRentDaoImpl;
import dao.impl.ParkingRecordDaoImpl;
import model.Employee;
import model.Member;
import model.MemberLicense;
import model.MonthRent;
import model.ParkingRecord;
import service.impl.MemberLicenseServiceImpl;
import service.impl.MemberServiceImpl;
import service.impl.MonthRentServiceImpl;
import service.impl.ParkingRecordServiceImpl;
import util.Tool;
import util.ToolUI;

import javax.swing.JLabel;
import javax.swing.DefaultCellEditor;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.stream.Collectors;

import javax.swing.JTextField;
import javax.swing.JComboBox;
import java.awt.SystemColor;

import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.ActionEvent;
import javax.swing.JTable;
import javax.swing.border.TitledBorder;
import javax.swing.JPasswordField;
import javax.swing.JToggleButton;
import java.awt.Font;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;

import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import java.util.stream.Collectors;

public class MemberModifyUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable table;
	private DefaultTableModel model1, model2, model3;
	private JScrollPane scrollPane;
	private JTextField name;
	private JTextField phone;
	private JTextField license;
	private JTextField account;
	private JPasswordField password;
	private List<String> allItems = new ArrayList<>();;
	private String 	licenseSelect = null;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MemberModifyUI frame = new MemberModifyUI();
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
	public MemberModifyUI() {
		setTitle("會員資料管理");
		MemberDaoImpl memberDI = new MemberDaoImpl();
		MonthRentDaoImpl monthRentDI = new MonthRentDaoImpl();
		ParkingRecordDaoImpl parkingRecordDI = new ParkingRecordDaoImpl();
		MemberLicenseDaoImpl memberLicenseDI = new MemberLicenseDaoImpl();
		MemberServiceImpl memberSI = new MemberServiceImpl();
		MonthRentServiceImpl monthRentSI = new MonthRentServiceImpl();
		ParkingRecordServiceImpl parkingRecordSI = new ParkingRecordServiceImpl();
		MemberLicenseServiceImpl memberLicenseSI = new MemberLicenseServiceImpl();

		Employee employee = (Employee) Tool.readFile("employee.txt");
		Member member = (Member) Tool.readFile("member.txt");
		


		String[] functionOption;
		String[] categoryOption;

		final String loginUser;

		if (Tool.isAdmin(employee)) {
			categoryOption = new String[] { "一般", "身障", "里民" };
			loginUser = employee.getAccount();
		} else {
			categoryOption = new String[] { member.getCategory() };
			loginUser = member.getAccount();

			// List<MemberLicense> memberAccountList =
			// memberLicenseDI.readAccountList(loginUser);
		}

		// ======================================= panel =======================================

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 661, 463);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);

		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.setBounds(10, 10, 625, 404);
		contentPane.add(panel);
		panel.setLayout(null);

		// ======================================= panel1 =======================================
		JPanel panel_1 = new JPanel();
		panel_1.setBounds(10, 10, 490, 135);
		panel_1.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_1.setLayout(null);
		panel.add(panel_1);

		JLabel lblNewLabel_2 = new JLabel("姓名");
		lblNewLabel_2.setBounds(10, 10, 90, 20);
		panel_1.add(lblNewLabel_2);

		JLabel lblLcd_1 = new JLabel("電話");
		lblLcd_1.setBounds(130, 10, 96, 20);
		panel_1.add(lblLcd_1);

		JLabel lblRam_1 = new JLabel("車牌");
		lblRam_1.setBounds(250, 10, 30, 20);
		panel_1.add(lblRam_1);

		JLabel lblMouse = new JLabel("身份別");
		lblMouse.setBounds(370, 10, 53, 20);
		panel_1.add(lblMouse);

		JLabel lblNewLabel_3 = new JLabel("Account");
		lblNewLabel_3.setBounds(10, 80, 90, 20);
		panel_1.add(lblNewLabel_3);

		JLabel lblNewLabel_3_1 = new JLabel("Password");
		lblNewLabel_3_1.setBounds(130, 80, 96, 20);
		panel_1.add(lblNewLabel_3_1);

		account = new JTextField();
		account.setText((String) null);
		account.setForeground(SystemColor.textText);
		account.setColumns(10);
		account.setBounds(10, 100, 96, 20);
		panel_1.add(account);

		password = new JPasswordField();
		password.setText((String) null);
		password.setEchoChar('*');
		password.setBounds(130, 100, 96, 20);
		panel_1.add(password);

		name = new JTextField();
		name.setText((String) null);
		name.setColumns(10);
		name.setBounds(10, 30, 96, 20);
		panel_1.add(name);

		phone = new JTextField();
		phone.setText((String) null);
		phone.setColumns(10);
		phone.setBounds(130, 30, 96, 20);
		panel_1.add(phone);

		license = new JTextField();
		license.setText((String) null);
		license.setColumns(10);
		license.setBounds(250, 30, 96, 20);
		panel_1.add(license);

		JComboBox category = new JComboBox(categoryOption);
		category.setBackground(SystemColor.text);
		category.setBounds(370, 30, 96, 20);
		panel_1.add(category);

		JComboBox licensecombo = new JComboBox();
		licensecombo.setEditable(true);
		licensecombo.setBounds(250, 30, 96, 20);
		panel_1.add(licensecombo);

		JComboBox accountsearch = new JComboBox();
		accountsearch.setBounds(370, 65, 96, 20);
		panel_1.add(accountsearch);

		JComboBox licensesearch = new JComboBox();
		licensesearch.setBounds(370, 100, 96, 20);
		panel_1.add(licensesearch);

		// ========================================== Table ==========================================

		// 定義第一個表格的欄位名稱 (會員資料)
		String[] columnNames1 = { "帳號", "姓名", "電話", "車牌", "身份" };
		model1 = new DefaultTableModel(columnNames1, 0);

		// 定義第二個表格的欄位名稱 (月租資料)
		String[] columnNames2 = { "車號", "繳費時間", "截止時間", "繳費金額", "繳費方式" };
		model2 = new DefaultTableModel(columnNames2, 0);

		// 定義表格的欄位名稱 (歷史資料)
		String[] columnNames3 = { "車號", "進場時間", "出場時間", "停車時間", "繳費金額" };
		model3 = new DefaultTableModel(columnNames3, 0); // 0 代表一開始沒有資料

		// 8. 視窗初始化時載入會員資料
		List<Member> memberList = null;
		List<ParkingRecord> parkingRecordList = null;

		// 3. 建立一個 JTable，並設定它的初始模型為 model1
		if (Tool.isAdmin(employee)) {
			// 切換模型
			table = new JTable(model1);

			// 載入資料
			memberList = memberDI.readAllList();
			ToolUI.loadMemberData(table, memberList, true);

			// 重新設定樣式
			ToolUI.setTableStyles(table, "model1");

		} else {
			// 切換模型
			table = new JTable(model3);
			// 載入資料
			List<MemberLicense> memberAccountList = memberLicenseDI.readAccountList(member.getAccount());
			// 重新設定樣式
			ToolUI.setTableStyles(table, "model3");

			if (!memberAccountList.isEmpty()) {
				ToolUI.loadMemberLicenseData(table, memberAccountList, true);

			} else {
				System.out.println("此會員尚未登錄車號。");
			}

		}

		table.setRowHeight(20);

		// 6. 啟用 JTable 的滾動功能
		scrollPane = new JScrollPane(table);
		scrollPane.setBounds(10, 150, 605, 212);
		panel.add(scrollPane);

		// 7. 將元件加入視窗中
		getContentPane().setLayout(null);
		contentPane.setLayout(null);

		// ========================================== Function ==========================================

		functionOption = new String[] { "新增", "查詢", "修改", "刪除", "列印", "匯出" };
		JPanel panel_3 = new JPanel();
		panel_3.setBounds(510, 10, 105, 135);
		panel_3.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.add(panel_3);
		panel_3.setLayout(null);
		JComboBox function = new JComboBox(functionOption);
		function.setBounds(10, 40, 80, 20);
		panel_3.add(function);
		function.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String choice = function.getSelectedItem().toString();

				List<Member> memberList = memberDI.readAllList();
				List<MonthRent> monthRentList = monthRentDI.readAllList();
				List<Member> generalmember = ToolUI.generalMember(memberList, monthRentList);

				if (Tool.isAdmin(employee)) {
					// 載入全部會員車號資料
					List<MemberLicense> memberAllList = memberLicenseDI.readAllList();
					// 更新車牌下拉選單
					ToolUI.licenseComboEvent(memberAllList, licensesearch);
				} else {
					// 載入會員車號資料
					List<MemberLicense> memberAccountList = memberLicenseDI.readAccountList(member.getAccount());
					// 更新車牌下拉選單
					ToolUI.licenseComboEvent(memberAccountList, licensecombo);
				}

				licensecombo.setSelectedItem(null);
				
				accountsearch.setVisible(false);					
				licensesearch.setVisible(false);
				
				license.setText("");

				if (choice == "新增") {
					license.setEnabled(true);
					license.setEditable(true);
					license.setVisible(true);

					licensecombo.setVisible(false);
					
					account.setText("");
					password.setText("");
					name.setText("");
					phone.setText("");
					license.setText("");
					category.setSelectedItem(null);

					if (Tool.isAdmin(employee)) {
						// 載入會員資料
						ToolUI.loadMemberData(table, memberList, true);
						// 重新設定樣式
						ToolUI.setTableStyles(table, "model1");

						account.setEditable(true);
						password.setEditable(true);

						name.setEditable(true);
						phone.setEditable(true);

						category.setEnabled(true);

					} else {
						// 切換模型
						table.setModel(model3);
						// 載入會員資料
						List<MemberLicense> memberAccountList = memberLicenseDI.readAccountList(member.getAccount());
						ToolUI.loadMemberLicenseData(table, memberAccountList, true);

						// 重新設定樣式
						ToolUI.setTableStyles(table, "model3");

						name.setEditable(false);
						phone.setEditable(false);
						password.setEditable(false);

						account.setEditable(false);
					}

				} else if (choice == "查詢") {

					account.setEditable(false);
					password.setEditable(false);

					name.setEditable(false);
					phone.setEditable(false);

					category.setEnabled(false);
					category.setEditable(false);
					category.setSelectedItem(null);


					category.setSelectedItem(null);

					licensecombo.setEnabled(false);
					licensecombo.setEditable(false);
					licensecombo.setVisible(false);

					account.setEnabled(true);
					account.setEditable(false);
					account.setVisible(true);

					license.setEnabled(true);
					license.setEditable(false);
					license.setVisible(true);

					if (Tool.isAdmin(employee)) {
						// 清空表格內容
						model1 = new DefaultTableModel(columnNames1, 0);
						// 切換模型
						table.setModel(model1);
						// 重新設定樣式
						ToolUI.setTableStyles(table, "model1");

						// ========================================== LicenseSearch ==========================================

						List<MemberLicense> memberAccountList = memberLicenseDI.readAccountList(loginUser);

						ToolUI.listenCombo(licensecombo, memberAccountList, MemberLicense::getLicense);

						// ========================================== AccountSearch ==========================================

						List<MemberLicense> memberAllList = memberLicenseDI.readAllAccountList();

						ToolUI.listenCombo(accountsearch, memberAllList, MemberLicense::getAccount);

						// ========================================== Table event ==========================================
						
						accountsearch.setEnabled(true);
						accountsearch.setEditable(true);
						accountsearch.setVisible(true);

						licensesearch.setEnabled(true);
						licensesearch.setEditable(true);
						licensesearch.setVisible(true);

						licensesearch.setSelectedItem(null);
						
						accountsearch.setSelectedItem("account");
						licensesearch.setSelectedItem("license");
						
						account.setText("");
						password.setText("");
						name.setText("");
						phone.setText("");
						license.setText("");

					} else {
						// 清空表格內容
						model3 = new DefaultTableModel(columnNames3, 0);
						// 切換模型
						table.setModel(model3);
						// 重新設定樣式
						ToolUI.setTableStyles(table, "model3");
						// 載入會員車號資料
						
						List<MemberLicense> memberAccountList = memberLicenseDI.readAccountList(member.getAccount());
						ToolUI.licenseComboEvent(memberAccountList, licensecombo);

						license.setVisible(false);


						licensecombo.setEnabled(true);
						licensecombo.setEditable(true);
						licensecombo.setVisible(true);
						licensecombo.setSelectedItem(null);
						
						category.setEnabled(true);
						
						license.setText("");
						


					}
				} else if (choice == "修改") {
					if (Tool.isAdmin(employee)) {
						account.setEditable(false);
						password.setEditable(true);

						name.setEditable(true);
						phone.setEditable(true);
						license.setEditable(true);
						category.setEnabled(true);
						
						license.setVisible(true);

					} else {
						// 切換模型
						table.setModel(model1);
						// 載入會員資料
						List<Member> memberLicenseList = List.of(member);
						ToolUI.loadMemberData(table, memberLicenseList, true);
						// 重新設定樣式
						ToolUI.setTableStyles(table, "model1");

						System.out.println(memberLicenseList);

						name.setEditable(true);
						phone.setEditable(true);
						password.setEditable(true);

						license.setEnabled(true);
						license.setEditable(true);
						license.setVisible(true);

						
						
						licensecombo.setVisible(false);
						licensecombo.setEditable(false);
						licensecombo.setSelectedItem(null);

						account.setEditable(false);
					}
				} else if (choice == "刪除") {
					if (Tool.isAdmin(employee)) {
						account.setEditable(false);
						password.setEditable(false);

						name.setEditable(false);
						phone.setEditable(false);
						license.setEditable(false);
						category.setEnabled(false);
					} else {
						// 切換模型
						table.setModel(model3);
						// 載入會員資料
						List<MemberLicense> memberAccountList = memberLicenseDI.readAccountList(member.getAccount());
						ToolUI.loadMemberLicenseData(table, memberAccountList, true);

						name.setEditable(false);
						phone.setEditable(false);
						password.setEditable(false);

						license.setVisible(false);

						licensecombo.setVisible(true);
						licensecombo.setEditable(true);
						licensecombo.setSelectedItem(null);

						account.setEditable(false);
					}
				}

				else if (choice == "列印") {
					if (Tool.isAdmin(employee)) {
						account.setEditable(false);
						password.setEditable(false);

						name.setEditable(false);
						phone.setEditable(false);
						license.setEditable(false);
						category.setEnabled(false);
					} else {
						name.setEditable(false);
						phone.setEditable(false);
						password.setEditable(false);
						license.setEditable(false);

						license.setVisible(false);

						licensecombo.setVisible(true);
						licensecombo.setEditable(false);
						licensecombo.setSelectedItem(null);

						account.setEditable(false);
					}
				} else if (choice == "匯出") {
					if (Tool.isAdmin(employee)) {
						account.setEditable(false);
						password.setEditable(false);

						name.setEditable(false);
						phone.setEditable(false);
						license.setEditable(false);
						category.setEnabled(false);
					} else {
						name.setEditable(false);
						phone.setEditable(false);
						password.setEditable(false);
						license.setEditable(false);

						license.setVisible(false);

						licensecombo.setVisible(true);
						licensecombo.setEditable(false);
						licensecombo.setSelectedItem(null);

						account.setEditable(false);
					}
				}

			}
		});
		function.setBackground(SystemColor.text);

		// ================================================= button =============================================

		JButton btnNewButton_1 = new JButton("確認");
		btnNewButton_1.setBounds(10, 70, 80, 20);
		panel_3.add(btnNewButton_1);
		btnNewButton_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				String choice = function.getSelectedItem().toString();

//				if (license.getText().isEmpty())
//				{
//					if (choice!= "查詢" && choice!= "列印" && choice!= "匯出")
//					{
//						Tool.alarmMsg(MemberModifyUI.this,"請先輸入車號。");
//					}
//				}					

				// adminAddMember
				Member selectMember = null;
				MemberLicense addMemberLicense = null;

				String Account = null;
				String Password;
				String Name;
				String Phone;
				String License = null;
				String Category;

				try {
					Account = account.getText();
					Password = String.valueOf(password.getPassword());
					Name = name.getText();
					Phone = phone.getText();
					License = license.getText().toUpperCase();
					Category = category.getSelectedItem().toString();

					selectMember = new Member(Account, Password, Name, Phone, License, Category);
					addMemberLicense = new MemberLicense(Account, License);
				} catch (Exception E) {
					System.out.println("selectMember、addMemberLicense 面板欄位空值。");
				}

				MemberLicense modMemberLicense = null;
				
				try	{
					String modLicense = licenseSelect  + ">" + license.getText().toUpperCase();
					modMemberLicense = new MemberLicense(Account, modLicense);
					
				}catch(Exception E)	{
					System.out.println("面板欄位空值。");
				}
				
				System.out.println(modMemberLicense.getLicense());
				
				
				MemberLicense delMemberLicense = null;
				String licenseCombo = null;

				try {
					licenseCombo = licensecombo.getSelectedItem().toString();

					delMemberLicense = new MemberLicense(Account, licenseCombo);
				} catch (Exception E) {
					System.out.println("Account | licenseCombo 欄位空值。");
				}

				String AccountSearch = null;

				try {
					AccountSearch = accountsearch.getSelectedItem().toString();

				} catch (Exception E) {
					System.out.println("AccountCombo 欄位空值。");
				}

				String LicenseSearch = null;

				try {
					LicenseSearch = licensesearch.getSelectedItem().toString();

				} catch (Exception E) {
					System.out.println("AccountCombo 欄位空值。");
				}

				if (choice == "新增") {
					if (Tool.isAdmin(employee)) {
						if (memberSI.create(selectMember)) {
							memberLicenseSI.create(addMemberLicense);

							List<Member> memberList = memberDI.readAllList();
							ToolUI.loadMemberData(table, memberList, true);
							
					//		ToolUI.tableMemberAdd(model1, selectMember);
						}
					} 
					else 
					{
						if (memberLicenseSI.create(addMemberLicense)) {
							List<MemberLicense> memberAccountList = memberLicenseDI.readAccountList(member.getAccount());
							ToolUI.loadMemberLicenseData(table, memberAccountList, true);
						}
					}
					

					license.setText("");


				} else if (choice == "查詢") {

					// 判斷是否有搜尋條件
					String licenseSearchVal = (licensesearch.getSelectedItem() != null) && (!licensesearch.getSelectedItem().equals("license"))
							? licensesearch.getSelectedItem().toString().trim()
							: "";
					String accountSearchVal = (accountsearch.getSelectedItem() != null) && (!accountsearch.getSelectedItem().equals("account"))
							? accountsearch.getSelectedItem().toString().trim()
							: "";

					System.out.println(licenseSearchVal+"    " + accountSearchVal);
					
					if (Tool.isAdmin(employee)) {
						if (licenseSearchVal.isEmpty() && accountSearchVal.isEmpty()) {
							// 沒有任何條件 → 載入所有會員
							List<Member> memberList = memberSI.readAll();
							ToolUI.loadMemberData(table, memberList, true);
						} else if (!licenseSearchVal.isEmpty() && accountSearchVal.isEmpty()) {
							// 只輸入車牌 → 查詢車牌
							List<MemberLicense> memberLicenseList = memberLicenseDI.readLicenseList(licenseSearchVal);
							ToolUI.loadMemberDataFromMemberLicense_license(table, memberLicenseList, true);
						} else if (licenseSearchVal.isEmpty() && !accountSearchVal.isEmpty()) {
							// 只輸入帳號 → 查詢帳號
//							List<MemberLicense> memberAccountList = memberLicenseDI.readAccountList(accountSearchVal);
//							ToolUI.loadMemberDataFromMemberLicense_account(table, memberAccountList, true);
							// 只輸入帳號 → 查詢帳號(單一帳號)
							List<Member> memberlist = memberDI.readAccountList(accountSearchVal);
							ToolUI.loadMemberData(table, memberlist, true);
							
						} else {
							// 同時輸入車牌與帳號 → 交集查詢
							List<MemberLicense> memberLicenseList = memberLicenseDI.readLicenseList(licenseSearchVal);
							List<MemberLicense> memberAccountList = memberLicenseDI.readAccountList(accountSearchVal);

							// 取交集
							List<MemberLicense> result = new ArrayList<>();
							for (MemberLicense lic : memberLicenseList) {
								for (MemberLicense acc : memberAccountList) {
									if (lic.getAccount().equals(acc.getAccount())
											&& lic.getLicense().equals(acc.getLicense())) {
										result.add(lic);
									}
								}
							}

							ToolUI.loadMemberDataFromMemberLicense_account(table, result, true);
						}
					} else {
						if (licenseCombo == null || licenseCombo.equals("")) 
						{

							// 載入會員車牌資料
							List <MemberLicense> memberAccountList = memberLicenseDI.readAccountList(member.getAccount());
							//更新表格
							ToolUI.loadAccountParkingRecordData(table,memberAccountList,true);
							
						}
						else
						{
							// 載入會員車牌資料
							List <MemberLicense> memberLicenseList = memberLicenseDI.readLicenseList(licenseCombo);
							//更新表格
							ToolUI.loadAccountParkingRecordData(table,memberLicenseList,true);
						}
						
						
					}

//					if(licensesearch.getSelectedItem() == null  ||  licensesearch.getSelectedItem().equals("") )
//					{
//						if (Tool.isAdmin(employee))
//						{
//							List<Member> memberList= memberSI.readAll();
//							ToolUI.loadMemberData(table, memberList,true);
//						}
//						else
//						{
//							// 載入會員車牌資料
//							List <MemberLicense> memberAccountList = memberLicenseDI.readAccountList(member.getAccount());
//							//更新表格
//							ToolUI.loadAccountParkingRecordData(table,memberAccountList,true);
//						}
//					}
//					else
//					{
//						if (Tool.isAdmin(employee))
//						{
//							// 載入會員車牌資料
//							List <MemberLicense> memberLicenseList = memberLicenseDI.readLicenseList(LicenseSearch);
//
//							//更新表格
//							ToolUI.loadMemberDataFromMemberLicense_license(table,memberLicenseList,true);
//
//						}
//						else
//						{
//							// 載入會員車牌資料
//							List <MemberLicense> memberLicenseList = memberLicenseDI.readLicenseList(licenseCombo);
//							//更新表格
//							ToolUI.loadAccountParkingRecordData(table,memberLicenseList,true);							
//						}
//					}

				} else if (choice == "修改") {
					if (memberSI.update(selectMember)) {
						memberLicenseSI.update(modMemberLicense);
						
//						List<Member> memberList = memberDI.readAllList();
//						ToolUI.loadMemberData(table, memberList, true);
						
						// 更新表格
						ToolUI.tableMemberMod(model1, selectMember);
					}
				} else if (choice == "刪除") {
					if (Tool.isAdmin(employee)) {
						if (ToolUI.DeleteCheck(MemberModifyUI.this, "會員帳號")) {
							if (memberSI.delete(selectMember)) {
								memberLicenseSI.delete(addMemberLicense);

								// 刪除指定Table Data
								ToolUI.tableDataDelFromAccount(model1, account.getText());

								account.setText("");
								password.setText("");
								name.setText("");
								phone.setText("");
								license.setText("");
								category.setSelectedItem(null);
							}
						}
					} else {
						if (memberLicenseSI.delete(delMemberLicense)) {
							// 載入會員車號資料
							List<MemberLicense> memberAccountList = memberLicenseDI
									.readAccountList(member.getAccount());
							// 更新表格
							ToolUI.loadMemberLicenseData(table, memberAccountList, true);
							// 更新車牌下拉選單
							ToolUI.licenseComboEvent(memberAccountList, licensecombo);

							licensecombo.setSelectedItem(null);
						}
					}
				} else if (choice == "列印") {
					Tool.print(table, "會員資料");
				} else if (choice == "匯出") {
					Tool.TabletoExcel(table, license.getText());
				}
			}
		});

		JButton btnNewButton_2 = new JButton("取消");
		btnNewButton_2.setBounds(10, 100, 80, 20);
		panel_3.add(btnNewButton_2);
		btnNewButton_2.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (Tool.isAdmin(employee)) {
					account.setText("");
					password.setText("");
					name.setText("");
					phone.setText("");
					license.setText("");
					category.setSelectedItem(null);
					
					accountsearch.setSelectedItem("account");
					licensesearch.setSelectedItem("license");
				} else {
					account.setText(member.getAccount());
					password.setText(member.getPassword());
					name.setText(member.getName());
					phone.setText(member.getPhone());
					licensecombo.setSelectedItem(null);
					category.setSelectedItem(member.getCategory());
				}
			}
		});

		JToggleButton showPassword = new JToggleButton("顯示密碼");
		showPassword.setBounds(250, 100, 99, 20);
		showPassword.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if (showPassword.isSelected()) {
					// 顯示密碼
					password.setEchoChar((char) 0);
				} else {
					// 隱藏密碼
					password.setEchoChar('*');
				}
			}
		});
		panel_1.add(showPassword);

		JButton btnNewButton = new JButton("返回功能表");
		btnNewButton.setBounds(250, 370, 110, 25);
		panel.add(btnNewButton);
		btnNewButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				ManagerUI pm = new ManagerUI();
				pm.setVisible(true);
				dispose();
			}
		});

		// ================================================= label =============================================

		JLabel user = new JLabel("");
		user.setHorizontalAlignment(SwingConstants.CENTER);
		user.setForeground(new Color(47, 79, 79));
		user.setFont(new Font("標楷體", Font.BOLD, 16));
		user.setBounds(9, 12, 83, 20);
		panel_3.add(user);
		user.setText(loginUser);



		// 在你的 JTable 建立之後，加入以下程式碼
		// 為表格的選取模型新增一個監聽器
		table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent event) {
				// 確保選取動作已經完成，並且有選取到有效的列
				if (!event.getValueIsAdjusting() && table.getSelectedRow() != -1) {

					// 取得被選取的列索引
					int selectedRowIndex = table.getSelectedRow();

					// 取得目前的表格模型
					DefaultTableModel currentModel = (DefaultTableModel) table.getModel();

					// 取得被選取列的所有資料
					Vector<Object> rowData = (Vector<Object>) currentModel.getDataVector().elementAt(selectedRowIndex);

					// 你可以根據目前顯示的表格（model1 或 model2）來決定如何處理資料
					String licenseStr = null;

					if (currentModel == model1) {
						// 👉 先取車牌欄位
						licenseStr = rowData.get(3) != null ? rowData.get(3).toString() : "";

						account.setText(rowData.get(0).toString());
						Member member = memberDI.readAccount(account.getText());
						password.setText(member.getPassword());
						license.setText(licenseStr);
						
						name.setText(rowData.get(1).toString());
						phone.setText(rowData.get(2).toString());
						category.setSelectedItem(rowData.get(4).toString());
					}

					else if (currentModel == model3) {
						licenseStr = rowData.get(0).toString();

						license.setText(licenseStr);
						licensecombo.setSelectedItem(rowData.get(0).toString());
					}

					if (licenseStr != null && !licenseStr.isEmpty()) {
						String status = monthRentSI.readLicense(licenseStr) ? " - 月租/季繳" : " - 臨停";

						// 在這裡處理你取得的資料
						System.out.println("選取的 " + selectedRowIndex + " 列資料是: " + rowData + status);
					} else {
						System.out.println("licenseStr null or \"\"");
					}
					
					
					
					
					TableColumn licensePlateColumn = table.getColumnModel().getColumn(3);

					// ================= Renderer (顯示用) =================
					licensePlateColumn.setCellRenderer(new DefaultTableCellRenderer() {
						@Override
						public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
								boolean hasFocus, int row, int column) {
							JLabel label = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row,
									column);

							String account = table.getValueAt(row, 0).toString();
							List<MemberLicense> memberLicenses = memberLicenseDI.readAccountList(account);

							if (memberLicenses.isEmpty()) {
								label.setText(""); // 無車牌
							} else if (memberLicenses.size() == 1) {
								label.setText(memberLicenses.get(0).getLicense()); // 單一車牌
							} else {
								label.setText(value != null ? value.toString() : memberLicenses.get(0).getLicense()); // 多車牌
							}
							return label;
						}
					});

					// ================= Editor (編輯用) =================
					licensePlateColumn.setCellEditor(new DefaultCellEditor(new JComboBox<String>()) {
						private JComboBox<String> comboBox;

						@Override
						public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row,
								int column) {
							String account = table.getValueAt(row, 0).toString();
							List<MemberLicense> memberLicenses = memberLicenseDI.readAccountList(account);

							// 無或單一車牌 → 直接回傳 JLabel，不給編輯
							if (memberLicenses.isEmpty()) {
								license.setText("");
								System.out.println("[No License] account=" + account + " → license 清空");
								return new JLabel("");
							} else if (memberLicenses.size() == 1) {
								String onlyLicense = memberLicenses.get(0).getLicense();
								license.setText(onlyLicense);
								System.out.println("[Single License] account=" + account + " → " + onlyLicense);
								return new JLabel(onlyLicense);
							}

							// 多車牌 → 下拉選單
							comboBox = new JComboBox<>();
							for (MemberLicense lic : memberLicenses) {
								comboBox.addItem(lic.getLicense());
							}

							if (value != null && !value.toString().isEmpty()) {
								comboBox.setSelectedItem(value.toString());
							} else {
								comboBox.setSelectedIndex(0);
							}

							comboBox.addActionListener(e -> {
								Object selected = comboBox.getSelectedItem();
								if (selected != null) {
									license.setText(selected.toString());
									licenseSelect = selected.toString();
									System.out.println("[ComboBox Immediate] row=" + row + " license=" + selected);
								}
							});

							return comboBox;
						}

						@Override
						public Object getCellEditorValue() {
							if (comboBox != null && comboBox.getSelectedItem() != null) {
								return comboBox.getSelectedItem().toString();
							}
							return "";
						}
					});
					
					
					
					
					
				}
			}
		});



		// ========================================== initial ==========================================

		accountsearch.setVisible(false);					
		licensesearch.setVisible(false);
		
		
		if (Tool.isAdmin(employee)) {
			account.setVisible(true);
			license.setVisible(true);



			category.setSelectedItem(null);
		} else {
			account.setEditable(false);
			name.setEditable(false);
			phone.setEditable(false);
			password.setEditable(false);

			license.setVisible(true);
			licensecombo.setVisible(false);

			account.setText(member.getAccount());
			password.setText(member.getPassword());
			name.setText(member.getName());
			phone.setText(member.getPhone());
			
			
			// license.setText(member.getLicense());
		}

	}
}
