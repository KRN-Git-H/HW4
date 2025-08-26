package controller.admin;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;

import com.toedter.calendar.JDateChooser;

import controller.ManagerUI;
import dao.impl.MemberDaoImpl;
import dao.impl.MemberLicenseDaoImpl;
import dao.impl.MonthRentDaoImpl;
import dao.impl.ParkingRecordDaoImpl;
import model.Employee;
import model.Member;
import model.MemberLicense;
import model.MonthRent;
import service.impl.MemberLicenseServiceImpl;
import service.impl.MemberServiceImpl;
import service.impl.MonthRentServiceImpl;
import service.impl.ParkingRecordServiceImpl;
import util.Tool;
import util.ToolUI;

import javax.swing.JLabel;
import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Vector;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import java.awt.SystemColor;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTable;
import javax.swing.border.TitledBorder;
import javax.swing.JPasswordField;
import javax.swing.JToggleButton;
import java.awt.Font;
import java.awt.Color;
import java.awt.Component;

import javax.swing.SwingConstants;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;

public class MonthRentModifyUI extends JFrame {
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable table;
    private DefaultTableModel model1, model2;
    private JScrollPane scrollPane;
 //   private JTextField license;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MonthRentModifyUI frame = new MonthRentModifyUI();
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
	public MonthRentModifyUI() {
		setTitle("月租繳費管理");
		MemberDaoImpl memberDI = new MemberDaoImpl();
		MonthRentDaoImpl monthRentDI = new MonthRentDaoImpl();
		ParkingRecordDaoImpl parkingRecordDI = new ParkingRecordDaoImpl();
		MemberLicenseDaoImpl memberLicenseDI = new MemberLicenseDaoImpl();
		MemberServiceImpl memberSI = new MemberServiceImpl();
		MonthRentServiceImpl monthRentSI = new MonthRentServiceImpl();
		ParkingRecordServiceImpl parkingRecordSI = new ParkingRecordServiceImpl();
		MemberLicenseServiceImpl memberLicenseSI = new MemberLicenseServiceImpl();

		Employee employee=(Employee)Tool.readFile("employee.txt");	
		Member member=(Member)Tool.readFile("member.txt");

	    List<Member> memberList = memberDI.readAllList();
	    List<MonthRent> monthRentList = monthRentDI.readAllList();
	    List<MemberLicense> memberLicenseList = memberLicenseDI.readAllList();	    
	    
	    List<Member> generalMemberList = ToolUI.generalMember(memberList, monthRentList);
	    List<MemberLicense> generalMemberLicenseList = ToolUI.generalMember(memberLicenseList, monthRentList);

		//======================================= panel =======================================			
		
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
		
		//======================================= panel2 =======================================	
		
		JPanel panel_2 = new JPanel();
		panel_2.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_2.setBounds(10, 10, 490, 135);
		panel.add(panel_2);
		panel_2.setLayout(null);
	
		JLabel lblRam = new JLabel("車牌");
		lblRam.setBounds(10, 10, 30, 15);
		panel_2.add(lblRam);
		
		JLabel lblLcd = new JLabel("繳費日期");
		lblLcd.setBounds(140, 10, 95, 15);
		panel_2.add(lblLcd);
		
		JLabel lblNewLabel_1 = new JLabel("繳費金額");
		lblNewLabel_1.setBounds(346, 10, 107, 19);
		panel_2.add(lblNewLabel_1);
		
		JLabel lblNewLabel = new JLabel("到期日期");
		lblNewLabel.setBounds(140, 72, 92, 15);
		panel_2.add(lblNewLabel);
		
		JTextField paymentamount = new JTextField();
		paymentamount.setBounds(346, 40, 120, 20);
		panel_2.add(paymentamount);
		paymentamount.setColumns(10);
		
		JTextField expirytime = new JTextField();
		expirytime.setBounds(140, 100, 140, 20);
		panel_2.add(expirytime);
		expirytime.setColumns(10);
		panel_2.setVisible(true);
		
		JLabel lblNewLabel_1_1 = new JLabel("繳費方式");
		lblNewLabel_1_1.setBounds(346, 70, 107, 19);
		panel_2.add(lblNewLabel_1_1);		
		
		//PaymentTime
		JDateChooser paymenttime = ToolUI.SelectData();
		paymenttime.setBounds(140, 40, 163, 20);
		panel_2.add(paymenttime);

		JComboBox licensecombo = new JComboBox();
		licensecombo.setBackground(SystemColor.text);
		licensecombo.setBounds(10, 40, 96, 21);
		panel_2.add(licensecombo);
		
		//========================================== Table ==========================================
		
	    //定義第一個表格的欄位名稱 (會員資料)
        String[] columnNames1 = {"帳號", "姓名", "電話", "車牌", "身份"};
        model1 = new DefaultTableModel(columnNames1, 0);

        //定義第二個表格的欄位名稱 (繳費記錄)
        String[] columnNames2 = {"車號", "繳費時間", "截止時間", "繳費金額", "繳費方式"};
        model2 = new DefaultTableModel(columnNames2, 0);
        
        //建立一個 JTable，並設定它的初始模型為 model1
        table = new JTable(model1);       
        
		//視窗初始化時載入會員資料
	    ToolUI.loadMemberData(table,generalMemberList,true);

        // 7. 將元件加入視窗中
        getContentPane().setLayout(null);

		table.setRowHeight(20);
        
        // 6. 啟用 JTable 的滾動功能
        scrollPane = new JScrollPane(table);
        scrollPane.setBounds(10, 150, 605, 212);
        panel.add(scrollPane);
        
        JButton btnNewButton = new JButton("返回功能表");
        btnNewButton.setBounds(250, 370, 110, 25);
        panel.add(btnNewButton);
        btnNewButton.addMouseListener(new MouseAdapter() {
        	@Override
        	public void mouseClicked(MouseEvent e) {
        		ManagerUI pm=new ManagerUI();
        		pm.setVisible(true);
        		dispose();
        	}
        });
		
	    //========================================== function ==========================================	
		
		String[] methodOpt = {"月繳","季繳"};			
		JComboBox paymentmethod = new JComboBox(methodOpt);
		paymentmethod.setBounds(346, 100, 120, 20);
		panel_2.add(paymentmethod);
		paymentmethod.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try
				{
					//取得付款方式
	                String choice = paymentmethod.getSelectedItem().toString();

	               int addMonth = choice.equals("季繳") ? 3:1;
	               
	               int amount = memberSI.readAmount(licensecombo.getSelectedItem().toString()) * addMonth;
	               paymentamount.setText(Integer.toString(amount));	               
																							
	                //取得截止日期
	             	LocalDateTime localDateTime = Tool.DatetoLocalDateTime(paymenttime.getDate(), addMonth);
	                expirytime.setText(localDateTime.format(Tool.timeFormat(true)).toString());			
				}
				catch (Exception E)
				{
					System.out.println("日期欄位空值。");
				}
			}
		});
		paymentmethod.setBackground(SystemColor.text);
	
		String[] opt = {"新增","查詢","修改","刪除","列印","匯出"}; 
		JPanel panel_3 = new JPanel();
		panel_3.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_3.setBounds(510, 10, 105, 135);
		panel.add(panel_3);
		panel_3.setLayout(null);
		JComboBox function = new JComboBox(opt);
		function.setBounds(10, 40, 80, 20);
		panel_3.add(function);
		function.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String choice = function.getSelectedItem().toString();
				
			    List<Member> memberList = memberDI.readAllList();
			    List<MonthRent> monthRentList = monthRentDI.readAllList();
			    List<Member> generalmember = ToolUI.generalMember(memberList, monthRentList);
		
				licensecombo.setSelectedItem(null);
			    
			    
			    //switch	    
			    switch(choice)
			    {
			    case "新增":
					// 切換模型
	                table.setModel(model1);	 
	                //讀取會員資料
					ToolUI.loadMemberData(table,generalmember,true);
	                // 重新設定樣式
	                ToolUI.setTableStyles(table, "model1");  	

			    
			    case "查詢":
					//reset
					paymenttime.setDate(null);
					expirytime.setText("");
					paymentamount.setText("");
					
					paymenttime.setEnabled(false);
					
					
	
	            default:
					paymenttime.setEnabled(false);
					paymenttime.setFocusable(false);
					paymentmethod.setEnabled(false);
					paymentmethod.setEditable(false);	 

					licensecombo.setEnabled(false);
					licensecombo.setEditable(false);
					
					paymenttime.setDate(null);
					expirytime.setText("");
					paymentamount.setText("");
					paymentmethod.setSelectedItem(null);
					
					licensecombo.setSelectedItem(null);
					
	            	if (choice=="新增")
	            	{
		        	    List<Member> generalMemberList = ToolUI.generalMember(memberList, monthRentList);
		        	    List<MemberLicense> generalMemberLicenseList = ToolUI.generalMember(memberLicenseList, monthRentList);
		                ToolUI.listenCombo(licensecombo,generalMemberLicenseList,MemberLicense::getLicense);	
	            	}
	            	else
	            	{
						// 切換模型
		                table.setModel(model2);
		                //讀取月租資料
		                ToolUI.loadMonthRentData(table,monthRentList,true); 
		                // 重新設定樣式
		                ToolUI.setTableStyles(table, "model2");
		                
	            	}

	            	if(choice!="新增" || choice!="修改")
	            	{
						paymenttime.setEnabled(true);
						paymenttime.setFocusable(true);
						paymentmethod.setEnabled(true);
						paymentmethod.setEditable(true);	       
						
						licensecombo.setEnabled(true);
						licensecombo.setEditable(true);
						
						
						monthRentList = monthRentDI.readAllList();
						ToolUI.listenCombo(licensecombo,monthRentList,MonthRent::getLicense);		
	            	}
	            	
	            	if(choice=="查詢" ||choice=="修改" ||choice=="刪除")
					{
						licensecombo.setEnabled(true);
						licensecombo.setEditable(true);
						licensecombo.setVisible(true);
						

					}
			    }
			}
		});
		function.setBackground(SystemColor.text);
		


	    //========================================== button ==========================================

		JButton btnNewButton_1 = new JButton("確認");
		btnNewButton_1.setBounds(10, 70, 80, 20);
		panel_3.add(btnNewButton_1);

		btnNewButton_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				String choice = function.getSelectedItem().toString();
				
				if (licensecombo.getSelectedItem()==null || licensecombo.getSelectedItem().equals(""))
				{
					if (choice!= "查詢" && choice!= "列印" && choice!= "匯出")
					{
						Tool.alarmMsg(MonthRentModifyUI.this,"請先輸入車號。");
					}
				}
				
				MonthRent addMonthRent = null;

				try {
					String License = licensecombo.getSelectedItem().toString().toUpperCase();
					LocalDateTime PaymentTime = Tool.DatetoLocalDateTime(paymenttime.getDate(), 0);	
					LocalDateTime ExpiryTime = Tool.StrToLocalDateTime(expirytime.getText(),0);			
					int PaymentAmount = Integer.parseInt(paymentamount.getText());			
					String PaymentMethod = paymentmethod.getSelectedItem().toString();

					addMonthRent = new MonthRent(License,PaymentTime,ExpiryTime,PaymentAmount,PaymentMethod);
				}
				catch(Exception E)
				{
					System.out.println("面板欄位空值。");
				}
				
				String licenseCombo = null;
				
				try
				{
					licenseCombo = licensecombo.getSelectedItem().toString();
				}
					catch(Exception E)
				{
						System.out.println("licensecombo 欄位空值。");
				}
				
				
				

				
				
				if(choice=="新增")
				{
					
					if(monthRentSI.create(addMonthRent))
					{
						//刪除指定Table Data
						ToolUI.tableDataDelFromLicense(model1,addMonthRent.getLicense());		
						
						
				        //刪除指定車牌licenseCombo
						licensecombo.removeItem(addMonthRent.getLicense());

						
//					    List<MonthRent> monthRentList = monthRentDI.readAllList();
//					    List<MemberLicense> memberLicenseList = memberLicenseDI.readAllList();	    
//					    List<MemberLicense> generalMemberLicenseList = ToolUI.generalMember(memberLicenseList, monthRentList);
//					    
//				        ToolUI.listenCombo(licensecombo,generalMemberLicenseList,MemberLicense::getLicense);	
//						
				        
					    // 刪除指定車牌 ，Table 下拉式選單，例如車牌在第 3 欄
					    ToolUI.removeLicenseFromCombo(table, 3, addMonthRent.getLicense());
		 
				        
						paymenttime.setDate(null);
						expirytime.setText("");
						paymentamount.setText("");
						paymentmethod.setSelectedItem(null);
						
						licensecombo.setSelectedItem(null);
					    
					}	
				}
				else if(choice=="查詢")
				{

					
					if(licensecombo.getSelectedItem() == null  ||  licensecombo.getSelectedItem().equals(""))
					{
						List<MonthRent> monthRentList= monthRentSI.readAll();
						ToolUI.loadMonthRentData(table, monthRentList,true);					
					}
					else
					{
						List<MonthRent> monthRentLicense= monthRentDI.readLicenseList(licenseCombo);
						
						if (!monthRentLicense.isEmpty())
						{
							//更新表格
							ToolUI.loadMonthRentData(table, monthRentLicense,true);
						}
						else
						{
							Tool.alarmMsg(MonthRentModifyUI.this,"無此車號，請註冊會員登錄車號。");
						}
					}					

					}
				else if(choice =="修改")
					{
						if(monthRentSI.update(addMonthRent))
						{
							ToolUI.tableMonthRentMod(model2,addMonthRent);
						}
					}
				
				else if(choice=="刪除")
				{
					if(monthRentSI.delete(addMonthRent))
					{
						//刪除指定Table Data
						ToolUI.tableDataDelFromLicense(model2,addMonthRent.getLicense());								

						licensecombo.setSelectedItem(null);
						paymenttime.setDate(null);
						expirytime.setText("");
						paymentamount.setText("");
						paymentmethod.setSelectedItem(null);
					}
				}
				else if(choice=="列印")
				{
					String msg = table.getModel() == model1 ? "會員資料" : "月租資料";
					Tool.print(table,msg);
				}
				else if(choice=="匯出")
				{
					Tool.TabletoExcel(table,licensecombo.getSelectedItem().toString());
				}
			}
		});
			
		JButton btnNewButton_2 = new JButton("取消");
		btnNewButton_2.setBounds(10, 100, 80, 20);
		panel_3.add(btnNewButton_2);
		
		JLabel user = new JLabel("");
		user.setHorizontalAlignment(SwingConstants.CENTER);
		user.setForeground(new Color(47, 79, 79));
		user.setFont(new Font("標楷體", Font.BOLD, 16));
		user.setBounds(9, 12, 83, 20);
		panel_3.add(user);
		btnNewButton_2.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				paymenttime.setDate(null);
				expirytime.setText("");
				paymentamount.setText("");
				paymentmethod.setSelectedItem(null);
				
				licensecombo.setSelectedItem(null);
			}
		});
		contentPane.setLayout(null);

		//========================================== License Combo ==========================================
        
//		List<MonthRent> monthRentList = monthRentDI.readAllList();		
//        ToolUI.listenCombo(licensecombo,monthRentList,MonthRent::getLicense);
        
        
//  		List<Member> generalMemberList = ToolUI.generalMember(memberList, monthRentList);    
		ToolUI.listenCombo(licensecombo,generalMemberLicenseList,MemberLicense::getLicense);	
	

		//========================================== event ==========================================	
		
		// 在你的 JTable 建立之後，加入以下程式碼
		// 為表格的選取模型新增一個監聽器
		table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
		    @Override
		    public void valueChanged(ListSelectionEvent event) {
		        // 確保選取動作已經完成，並且有選取到有效的列
		        if (!event.getValueIsAdjusting() && table.getSelectedRow() != -1) {
		            
		            // 取得被選取的列索引
		            int selectedRowIndex = table.getSelectedRow();
		            
		            // 取得目前的表格模型（可能是 model1 或 model2）
		            DefaultTableModel currentModel = (DefaultTableModel) table.getModel();
		            
		            // 取得被選取列的所有資料
		            Vector<Object> rowData = (Vector<Object>) currentModel.getDataVector().elementAt(selectedRowIndex);

		            // 你可以根據目前顯示的表格（model1 或 model2）來決定如何處理資料
		            String licenseStr = null;
		            
		            if (currentModel == model1) {
		                licenseStr = rowData.get(3).toString();
		        		licensecombo.setSelectedItem(licenseStr);
		        		
		        		LocalDateTime localdatetime = LocalDateTime.now();
		        		Date datetime = Tool.LocalDateTimeToDate(localdatetime, 0);
		        		paymenttime.setDate(datetime);
		        		
						//預設付款方式
		        		paymentmethod.setSelectedItem("月繳"	);
		        		
		        		//取得付款方式
		                String choice = paymentmethod.getSelectedItem().toString();

		                int addMonth = choice.equals("季繳") ? 3:1;
		               
		               	int amount = memberSI.readAmount(licensecombo.getSelectedItem().toString()) * addMonth;
		               	paymentamount.setText(Integer.toString(amount));	               
																								
		                //取得截止日期
		             	LocalDateTime localDateTime = Tool.DatetoLocalDateTime(paymenttime.getDate(), addMonth);
		                expirytime.setText(localDateTime.format(Tool.timeFormat(true)).toString());	
		        		

		            } else if (currentModel == model2) {
		                // 月租資料
		                licenseStr = rowData.get(0).toString();
		                String paymentTimeStr = rowData.get(1).toString();
		                String expiryTimeStr = rowData.get(2).toString();
		                String paymentAmountStr = rowData.get(3).toString();
		                String paymentMethodStr = rowData.get(4).toString();
  
		                licensecombo.setSelectedItem(licenseStr);

						LocalDateTime localdatetime = Tool.StrToLocalDateTime(paymentTimeStr,0);
						Date date = Tool.LocalDateTimeToDate(localdatetime,0);
						paymenttime.setDate(date);

						expirytime.setText(expiryTimeStr);
						paymentamount.setText(paymentAmountStr);
		                paymentmethod.setSelectedItem(paymentMethodStr); 
		            }
		            
		            String status = monthRentSI.readLicense(licenseStr) ? " - 月租/季繳" : " - 臨停";

		            // 在這裡處理你取得的資料
		            System.out.println("選取的列資料是: " + rowData + status);
		            
		            

		            
		            //=====================================================================================
		            
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
		    			    List<MemberLicense> generalMemberLicenseList = ToolUI.generalMember(memberLicenses, monthRentList);
		    				
		    				// 無或單一車牌 → 直接回傳 JLabel，不給編輯
		    				if (generalMemberLicenseList.isEmpty()) {
		    					licensecombo.setSelectedItem("");
		    					System.out.println("[No License] account=" + account + " → license 清空");
		    					return new JLabel("");
		    				} else if (generalMemberLicenseList.size() == 1) {
		    					String onlyLicense = generalMemberLicenseList.get(0).getLicense();
		    					licensecombo.setSelectedItem(onlyLicense);
		    					System.out.println("[Single License] account=" + account + " → " + onlyLicense);
		    					return new JLabel(onlyLicense);
		    				}
		    
		    				// 多車牌 → 下拉選單
		    				comboBox = new JComboBox<>();
		    				for (MemberLicense lic : generalMemberLicenseList) {
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
		    
		    						licensecombo.setSelectedItem(selected.toString());
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
		    		
		            //=====================================================================================
		    		
		    		
		    		
		        }
		    }
		});
		
		

		
//		TableColumn licensePlateColumn = table.getColumnModel().getColumn(3);
//		
//		// ================= Renderer (顯示用) =================
//		licensePlateColumn.setCellRenderer(new DefaultTableCellRenderer() {
//			@Override
//			public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
//					boolean hasFocus, int row, int column) {
//				JLabel label = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row,
//						column);
//
//				String account = table.getValueAt(row, 0).toString();
//				List<MemberLicense> memberLicenses = memberLicenseDI.readAccountList(account);
//
//				if (memberLicenses.isEmpty()) {
//					label.setText(""); // 無車牌
//				} else if (memberLicenses.size() == 1) {
//					label.setText(memberLicenses.get(0).getLicense()); // 單一車牌
//				} else {
//					label.setText(value != null ? value.toString() : memberLicenses.get(0).getLicense()); // 多車牌
//				}
//				return label;
//			}
//		});
//	
//		// ================= Editor (編輯用) =================
//		licensePlateColumn.setCellEditor(new DefaultCellEditor(new JComboBox<String>()) {
//			private JComboBox<String> comboBox;
//
//			@Override
//			public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row,
//					int column) {
//				String account = table.getValueAt(row, 0).toString();
//				List<MemberLicense> memberLicenses = memberLicenseDI.readAccountList(account);
//			    List<MemberLicense> generalMemberLicenseList = ToolUI.generalMember(memberLicenses, monthRentList);
//				
//				// 無或單一車牌 → 直接回傳 JLabel，不給編輯
//				if (generalMemberLicenseList.isEmpty()) {
//					licensecombo.setSelectedItem("");
//					System.out.println("[No License] account=" + account + " → license 清空");
//					return new JLabel("");
//				} else if (generalMemberLicenseList.size() == 1) {
//					String onlyLicense = generalMemberLicenseList.get(0).getLicense();
//					licensecombo.setSelectedItem(onlyLicense);
//					System.out.println("[Single License] account=" + account + " → " + onlyLicense);
//					return new JLabel(onlyLicense);
//				}
//
//				// 多車牌 → 下拉選單
//				comboBox = new JComboBox<>();
//				for (MemberLicense lic : generalMemberLicenseList) {
//					comboBox.addItem(lic.getLicense());
//				}
//
//				if (value != null && !value.toString().isEmpty()) {
//					comboBox.setSelectedItem(value.toString());
//				} else {
//					comboBox.setSelectedIndex(0);
//				}
//
//				comboBox.addActionListener(e -> {
//					Object selected = comboBox.getSelectedItem();
//					if (selected != null) {
//
//						licensecombo.setSelectedItem(selected.toString());
//						System.out.println("[ComboBox Immediate] row=" + row + " license=" + selected);
//					}
//				});
//
//				return comboBox;
//			}
//
//			@Override
//			public Object getCellEditorValue() {
//				if (comboBox != null && comboBox.getSelectedItem() != null) {
//					return comboBox.getSelectedItem().toString();
//				}
//				return "";
//			}
//		});
		
		
		//================================================= 修改功能Event =========================================
		
		licensecombo.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
		        // 確認事件是「選擇」一個項目，而不是取消選擇
		        if (e.getStateChange() == ItemEvent.SELECTED) 
		        {
		            // 取得目前的表格模型（可能是 model1 或 model2）
		            	DefaultTableModel currentModel = (DefaultTableModel) table.getModel();
		        	
			        	if (currentModel == model2) 
			        	{
				            // 獲取被選中的項目
				            // 這裡假設你的下拉選單顯示的是字串
				            String selectedItem = (String) e.getItem();
				            
				            System.out.println(selectedItem);
				            
				            MonthRent monthRent =  monthRentDI.readLicense(selectedItem);
				          
			        		Date paymentTime = Tool.LocalDateTimeToDate(monthRent.getPaymentTime(), 0);
			        		String expiryTime = monthRent.getExpiryTime().format(Tool.timeFormat(true)).toString();
			        		String paymentAmount = String.valueOf(monthRent.getPaymentAmount());
			        		String paymentMethod = monthRent.getPaymentMethod();
			        		
			        		paymenttime.setDate(paymentTime);
							expirytime.setText(expiryTime);
							paymentamount.setText(paymentAmount);
			                paymentmethod.setSelectedItem(paymentMethod); 
			        	}
		        }
			}
		});

		
		//========================================== initial ==========================================	
		
		user.setText(employee.getAccount());	
		
		paymentmethod.setSelectedItem(null);
		
		expirytime.setEnabled(false);
		expirytime.setEditable(false);
		paymentamount.setEnabled(false);
		paymentamount.setEditable(false);
		
		licensecombo.setEnabled(true);
		licensecombo.setEditable(true);

	}	
	

}


