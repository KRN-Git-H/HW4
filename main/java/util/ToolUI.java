package util;

import java.awt.Component;
import java.awt.Frame;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.swing.DefaultCellEditor;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;
import com.toedter.calendar.JDateChooser;
import dao.impl.MemberDaoImpl;
import dao.impl.MemberLicenseDaoImpl;
import dao.impl.MonthRentDaoImpl;
import dao.impl.ParkingRecordDaoImpl;
import model.LicenseHolder;
import model.Member;
import model.MemberLicense;
import model.MonthRent;
import model.ParkingRecord;
import service.impl.MemberLicenseServiceImpl;
import service.impl.MemberServiceImpl;
import service.impl.MonthRentServiceImpl;
import service.impl.ParkingRecordServiceImpl;

public class ToolUI {
	static MemberDaoImpl memberDI = new MemberDaoImpl();
	static MonthRentDaoImpl monthRentDI = new MonthRentDaoImpl();
	static ParkingRecordDaoImpl parkingRecordDI = new ParkingRecordDaoImpl();
	static MemberLicenseDaoImpl memberLicenseDI = new MemberLicenseDaoImpl();
	static MemberServiceImpl memberSI = new MemberServiceImpl();
	static MonthRentServiceImpl monthRentSI = new MonthRentServiceImpl();
	static ParkingRecordServiceImpl parkingRecordSI = new ParkingRecordServiceImpl();
	static MemberLicenseServiceImpl memberLicenseSI = new MemberLicenseServiceImpl();

	public static void loadMemberData(JTable table, List<Member> memberList, boolean clear) {
		DefaultTableModel model = (DefaultTableModel) table.getModel();

		if (clear) {
			model.setRowCount(0);
		}

		// 建立帳號 -> 車牌的 map
		Map<String, List<String>> accountLicenseMap = new HashMap<>();

		for (Member member : memberList) {
			List<MemberLicense> memberAccountList = memberLicenseDI.readAccountList(member.getAccount());

			List<String> licenseStrings = new ArrayList<>();
			for (MemberLicense licenseObj : memberAccountList) {
				licenseStrings.add(licenseObj.getLicense());
			}

			accountLicenseMap.put(member.getAccount(), licenseStrings);

			// ✅ 預設填入第一個車牌（若存在）
			String defaultLicense = licenseStrings.isEmpty() ? "" : licenseStrings.get(0);

			model.addRow(new Object[] { member.getAccount(), member.getName(), member.getPhone(), defaultLicense, // 預設顯示第一個車牌
					member.getCategory(), });
		}

		// 設定第 4 欄 (index = 3) 使用自訂 editor
		TableColumn licensePlateColumn = table.getColumnModel().getColumn(3);

		licensePlateColumn.setCellEditor(new DefaultCellEditor(new JComboBox<>()) {
			private JComboBox<String> currentComboBox;

			@Override
			public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row,
					int column) {
				currentComboBox = new JComboBox<>();

				// 依據 row 取出對應的帳號
				String account = (String) table.getValueAt(row, 0);
				List<String> licenses = accountLicenseMap.getOrDefault(account, new ArrayList<>());

				for (String lic : licenses) {
					currentComboBox.addItem(lic);
				}

				// 如果原本有值，就選到它
				if (value != null && !value.toString().isEmpty()) {
					currentComboBox.setSelectedItem(value.toString());
				}

				return currentComboBox;
			}

			@Override
			public Object getCellEditorValue() {
				if (currentComboBox != null) {
					return currentComboBox.getSelectedItem();
				}
				return super.getCellEditorValue();
			}

			@Override
			public boolean stopCellEditing() {
				Object selectedValue = getCellEditorValue();
				int row = table.getEditingRow();
				int col = table.getEditingColumn();

				// 更新到 model
				if (row >= 0 && col >= 0 && selectedValue != null) {
					table.setValueAt(selectedValue, row, col);
				}

				return super.stopCellEditing();
			}
		});
	}


	public static void loadMonthRentData(JTable table, List<MonthRent> monthRentList, boolean clear) {
		TableModel defaultModel = table.getModel();
		DefaultTableModel model = (DefaultTableModel) defaultModel;

		if (clear) {
			// 清除label資料
			model.getDataVector().removeAllElements();
			model.fireTableDataChanged();
		}

		for (MonthRent monthrent : monthRentList) {
			model.addRow(
					new Object[] { monthrent.getLicense(), monthrent.getPaymentTime().format(Tool.timeFormat(true)),
							monthrent.getExpiryTime().format(Tool.timeFormat(true)), monthrent.getPaymentAmount(),
							monthrent.getPaymentMethod(), });

			System.out.println(monthrent.getLicense() + "\t" + monthrent.getPaymentTime().format(Tool.timeFormat(true))
					+ "\t" + monthrent.getExpiryTime().format(Tool.timeFormat(true)) + "\t"
					+ monthrent.getPaymentAmount() + "\t" + monthrent.getPaymentMethod());
		}
	}

	public static void loadParkingRecordData(JTable table, List<ParkingRecord> parkingRecordList, boolean clear) {
		TableModel defaultModel = table.getModel();
		DefaultTableModel model = (DefaultTableModel) defaultModel;

		if (clear) {
			// 清除label資料
			model.getDataVector().removeAllElements();
			model.fireTableDataChanged();
		}

		for (ParkingRecord parkingrecord : parkingRecordList) {
			model.addRow(new Object[] { parkingrecord.getLicense(),
					parkingrecord.getEntryTime().format(Tool.timeFormat(true)),
					parkingrecord.getExitTime().format(Tool.timeFormat(true)), parkingrecord.getParkingTime(),
					parkingrecord.getPaymentAmount() });

			System.out.println(
					parkingrecord.getLicense() + "\t" + parkingrecord.getEntryTime().format(Tool.timeFormat(true))
							+ "\t" + parkingrecord.getExitTime().format(Tool.timeFormat(true)) + "\t"
							+ parkingrecord.getParkingTime() + "\t" + parkingrecord.getPaymentAmount());
		}
	}

	public static void loadMemberLicenseData(JTable table, List<MemberLicense> memberLicense, boolean clear) {
		TableModel defaultModel = table.getModel();
		DefaultTableModel model = (DefaultTableModel) defaultModel;

		if (clear) {
			// 清除label資料
			model.getDataVector().removeAllElements();
			model.fireTableDataChanged();
		}

		for (MemberLicense memberlicense : memberLicense) {

			model.addRow(new Object[] { memberlicense.getLicense(), "", "", "", "" });

			System.out.println(memberlicense.getAccount() + "\t" + memberlicense.getLicense());
		}
	}

	public static String[] memberLicenseListToArray(List<MemberLicense> memberLicense) {
		// 使用 Stream API 進行轉換，這是一個現代化且高效的做法
		return memberLicense.stream().map(MemberLicense::getLicense) // 將每個物件映射（map）成它的 license 屬性
				.toArray(String[]::new); // 將結果收集到一個新的 String 陣列
	}

	public static void loadAccountParkingRecordData(JTable table, List<MemberLicense> memberLicense, boolean clear) {
		TableModel defaultModel = table.getModel();
		DefaultTableModel model = (DefaultTableModel) defaultModel;

		if (clear) {
			// 清除label資料
			model.getDataVector().removeAllElements();
			model.fireTableDataChanged();
		}

		for (MemberLicense memberlicense : memberLicense) {
			System.out.println(memberlicense.getAccount() + "\t" + memberlicense.getLicense());

			List<ParkingRecord> parkingRecordList = parkingRecordDI.readLicenseList(memberlicense.getLicense());
			ToolUI.loadParkingRecordData(table, parkingRecordList, false);
		}
	}

	public static void loadMemberDataFromMemberLicense_license(JTable table, List<MemberLicense> memberLicense,
			boolean clear) {
		TableModel defaultModel = table.getModel();
		DefaultTableModel model = (DefaultTableModel) defaultModel;

		if (clear) {
			// 清除label資料
			model.getDataVector().removeAllElements();
			model.fireTableDataChanged();
		}

		for (MemberLicense memberlicense : memberLicense) {
			System.out.println(memberlicense.getAccount() + "\t" + memberlicense.getLicense());

	//		List<Member> memberList = memberDI.readLicenseList(memberlicense.getLicense());
			
			List<Member> memberList = memberDI.readAccountList(memberlicense.getAccount());
			
			ToolUI.loadMemberData(table, memberList, false);
		}
	}

	public static void loadMemberDataFromMemberLicense_account(JTable table, List<MemberLicense> memberLicense,
			boolean clear) {
		TableModel defaultModel = table.getModel();
		DefaultTableModel model = (DefaultTableModel) defaultModel;

		if (clear) {
			// 清除label資料
			model.getDataVector().removeAllElements();
			model.fireTableDataChanged();
		}


		for (MemberLicense memberlicense : memberLicense) {
			System.out.println(memberlicense.getAccount() + "\t" + memberlicense.getLicense());

			List<Member> memberList = memberDI.readAccountList(memberlicense.getAccount());
			ToolUI.loadMemberData(table, memberList, false);
		}

	}

	// 封裝設定表格樣式的邏輯
	public static void setTableStyles(JTable currentTable, String model) {
		// ... (根據 currentModel 的欄位數量來設定樣式)
		DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
		rightRenderer.setHorizontalAlignment(SwingConstants.RIGHT);

		if (model == "model1") {
			currentTable.getColumnModel().getColumn(4).setCellRenderer(rightRenderer);
			currentTable.getColumnModel().getColumn(4).setPreferredWidth(0);
		} else if (model == "model2") {
			currentTable.getColumnModel().getColumn(0).setPreferredWidth(0);
		} else if (model == "model3") {
			currentTable.getColumnModel().getColumn(0).setPreferredWidth(0);
			currentTable.getColumnModel().getColumn(3).setPreferredWidth(0);
			currentTable.getColumnModel().getColumn(4).setPreferredWidth(0);
		}
	}

	public static void loadSelectData(JTable table, DefaultTableModel model) {
		DefaultTableModel tableModel = (DefaultTableModel) table.getModel();

		// 新增滑鼠監聽器，當點擊表格時取得選取資料
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				// 取得選取列的索引
				int selectedRowIndex = table.getSelectedRow();

				// 檢查是否有選取列
				if (selectedRowIndex != -1) {
					// 取得模型中的真實索引，以處理排序問題
					int modelRowIndex = table.convertRowIndexToModel(selectedRowIndex);

					if (tableModel == model) {
						// 從模型中取得該列的資料
						Object objLicense = tableModel.getValueAt(modelRowIndex, 0);
						Object objPaymenttime = tableModel.getValueAt(modelRowIndex, 1);
						Object objExpirytime = tableModel.getValueAt(modelRowIndex, 2);
						Object objPaymentamount = tableModel.getValueAt(modelRowIndex, 3);
						Object objPaymentmethod = tableModel.getValueAt(modelRowIndex, 4);

						System.out.println(objLicense + "\t" + objPaymenttime + "\t" + objExpirytime + "\t"
								+ objPaymentamount + "\t" + objPaymentmethod);
					}
				}
			}
		});
	}

	public static JDateChooser SelectData() {
		// 將 JTextField 替換為 JDateChooser
		JDateChooser paymenttime = new JDateChooser();

		// 設定顯示格式，使其包含時間
		paymenttime.setDateFormatString("yyyy-MM-dd HH:mm:ss");

		// 監聽日期變動事件，自動加入當前時間
		// 使用 Lambda 表達式，取代舊有的匿名內部類別
		paymenttime.addPropertyChangeListener(evt -> {
			if ("date".equals(evt.getPropertyName())) {
				Date selectedDate = paymenttime.getDate();
				if (selectedDate != null) {
					// 取得使用者選定的日期
					Calendar selectedCal = Calendar.getInstance();
					selectedCal.setTime(selectedDate);

					// 取得當前時間
					Calendar nowCal = Calendar.getInstance();

					// 將當前時間（時、分、秒）合併到選定的日期中
					selectedCal.set(Calendar.HOUR_OF_DAY, nowCal.get(Calendar.HOUR_OF_DAY));
					selectedCal.set(Calendar.MINUTE, nowCal.get(Calendar.MINUTE));
					selectedCal.set(Calendar.SECOND, nowCal.get(Calendar.SECOND));

					Date combinedDateTime = selectedCal.getTime();

					// 將合併後的日期時間設定回 JDateChooser
					paymenttime.setDate(combinedDateTime);
				}
			}
		});

		return paymenttime;
	}

//	public static List<MemberLicense> generalMember(List<MemberLicense> memberLicenseList, List<MonthRent> monthRentList) {
//		// 1. 從 monthRentList 中取出所有已繳費的車牌號碼，並存入一個 Set
//		Set<String> paidLicenses = monthRentList.stream().map(MonthRent::getLicense).collect(Collectors.toSet());
//
//		// 使用 Stream 的 filter() 方法來過濾出差集
//		List<MemberLicense> unpaidMembers = memberLicenseList.stream().filter(item -> !paidLicenses.contains(item.getLicense()))
//				.collect(Collectors.toList());
//
//		unpaidMembers.forEach(item -> System.out.println("未繳費會員車牌: " + item.getLicense()));
//
//		return unpaidMembers;
//	}
	
	public static <T extends LicenseHolder> List<T>generalMember(List<T> memberLicenseList, List<MonthRent> monthRentList) {
		// 1. 從 monthRentList 中取出所有已繳費的車牌號碼，並存入一個 Set
		Set<String> paidLicenses = monthRentList.stream().map(MonthRent::getLicense).collect(Collectors.toSet());

		// 使用 Stream 的 filter() 方法來過濾出差集
		List<T> unpaidMembers = memberLicenseList.stream().filter(item -> !paidLicenses.contains(item.getLicense()))
				.collect(Collectors.toList());

		unpaidMembers.forEach(item -> System.out.println("未繳費會員車牌: " + item.getLicense()));

		return unpaidMembers;
	}

	public static boolean tableDataDelFromLicense(DefaultTableModel model, String license) {
		boolean result = false;

		// 檢查第一個欄位名稱來判斷是哪個表格
		String firstColumnName = model.getColumnName(0);

		// 定位Table License 欄位
		int locate = firstColumnName.equals("帳號") ? 3 : 0;

		// 刪除指定Table
		for (int i = 0; i < model.getRowCount(); i++) {
			if (model.getValueAt(i, locate).equals(license)) {
				model.removeRow(i);
				result = true;
				break;
			}
		}

		return result;
	}
	
	public static boolean tableDataDelFromAccount(DefaultTableModel model, String account) {
		boolean result = false;

		// 定位Table License 欄位
		int locate = 0;

		// 刪除指定Table
		for (int i = 0; i < model.getRowCount(); i++) {
			if (model.getValueAt(i, locate).equals(account)) {
				model.removeRow(i);
				result = true;
				break;
			}
		}

		return result;
	}

	public static boolean tableMemberAdd(DefaultTableModel model, Member member) {
		boolean result = false;

		try {
			model.addRow(new Object[] { member.getAccount(), member.getName(), member.getPhone(), member.getCategory(), });

			result = true;
		} catch (Exception E) {
			System.out.println("ToolUI.tableMemberAdd Error");
		}
		return result;
	}

	public static boolean tableMemberLicenseAdd(DefaultTableModel model, MemberLicense memberLicense) {
		boolean result = false;

		try {
			model.addRow(new Object[] { memberLicense.getAccount(), memberLicense.getLicense(), });

			result = true;
		} catch (Exception E) {
			System.out.println("ToolUI.tableMemberLicenseAdd Error");
		}
		return result;
	}

	public static boolean tableMemberMod(DefaultTableModel model, Member member) {
		boolean result = false;

		// 修改指定Table
		for (int i = 0; i < model.getRowCount(); i++) {
			if (model.getValueAt(i, 0).equals(member.getAccount())) {
				model.setValueAt(member.getAccount(), i, 0);
				model.setValueAt(member.getName(), i, 1);
				model.setValueAt(member.getPhone(), i, 2);
				model.setValueAt(member.getLicense(), i, 3);
				model.setValueAt(member.getCategory(), i, 4);

				result = true;
				break;
			}
		}

		return result;
	}

	public static boolean tableMonthRentMod(DefaultTableModel model, MonthRent monthRent) {
		boolean result = false;

		// 修改指定Table
		for (int i = 0; i < model.getRowCount(); i++) {
			if (model.getValueAt(i, 0).equals(monthRent.getLicense())) {
				model.setValueAt(monthRent.getLicense(), i, 0);
				model.setValueAt(monthRent.getPaymentTime(), i, 1);
				model.setValueAt(monthRent.getExpiryTime(), i, 2);
				model.setValueAt(monthRent.getPaymentAmount(), i, 3);
				model.setValueAt(monthRent.getPaymentMethod(), i, 4);

				result = true;
				break;
			}
		}

		return result;
	}

	public static boolean DeleteCheck(Frame frame, String msg) {
		boolean result = false;

		// 顯示確認視窗
		int option = JOptionPane.showConfirmDialog(frame, "您確定要刪除" + msg + "嗎？", "刪除確認", JOptionPane.YES_NO_OPTION,
				JOptionPane.WARNING_MESSAGE);

		if (option == JOptionPane.YES_OPTION) {
			result = true;

		} else if (option == JOptionPane.NO_OPTION) {
			System.out.println("取消刪除" + msg + "。");

		} else {
			System.out.println("視窗已關閉。");
		}

		return result;
	}

	public static void licenseComboEvent(List<MemberLicense> memberList, JComboBox combo) {
		List<String> allItems = new ArrayList<>();

		// List<MemberLicense> memberAccountList =
		// memberLicenseDI.readAccountList(memberList.getAccount());

		for (MemberLicense memberlicense : memberList) {
			allItems.add(memberlicense.getLicense());
		}

		// 2. 建立 DefaultComboBoxModel 並將所有項目加入
		DefaultComboBoxModel model = new DefaultComboBoxModel<>(allItems.toArray(new String[0]));

		// 更新模型
		combo.setModel(model);

		// 4. 新增 KeyListener 監聽鍵盤輸入
		JTextField editor = (JTextField) combo.getEditor().getEditorComponent();
		editor.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				// 取得使用者輸入的文字
				String input = editor.getText();
				// 根據輸入內容更新下拉選單
				// updateComboBoxItems(input);

				// 建立新的模型來存放篩選後的項目
				DefaultComboBoxModel<String> newModel = new DefaultComboBoxModel<>();

				// 遍歷所有項目，只將符合輸入的項目加入新模型
				for (String item : allItems) {
					if (item.toUpperCase().contains(input.toUpperCase())) {
						newModel.addElement(item);
					}
				}

				// 更新 JComboBox 的模型
				SwingUtilities.invokeLater(() -> {
					boolean wasPopupVisible = combo.isPopupVisible(); // 檢查選單是否彈開
					combo.setModel(newModel); // 更新模型
					combo.setPopupVisible(true); // 重新彈開選單

					// 將游標移回輸入框並選取文字
					JTextField editor = (JTextField) combo.getEditor().getEditorComponent();
					editor.setText(input);
					editor.setCaretPosition(input.length());
				});
			}
		});
	}

	// ========================================== LicenseCombo ==========================================
	
	public static <T> void listenCombo(JComboBox<String> combo, List<T> objectList, Function<T, String> toStringFunc) {
	    // 把原始資料存到一個 List
	    List<String> allItems = new ArrayList<>();
	    for (T obj : objectList) {
	        allItems.add(toStringFunc.apply(obj));
	    }

	    // 建立 Model 並設定
	    DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>(allItems.toArray(new String[0]));
	    combo.setModel(model);
	    combo.setSelectedItem(null);
	    combo.setEditable(true);  // 一定要 editable 才能輸入過濾

	    // 取得輸入框
	    JTextField editor = (JTextField) combo.getEditor().getEditorComponent();

	    // 移除舊的監聽器，避免重複綁定
	    for (KeyListener kl : editor.getKeyListeners()) {
	        editor.removeKeyListener(kl);
	    }

	    // 加入 KeyListener 做即時過濾
	    editor.addKeyListener(new KeyAdapter() {
	        @Override
	        public void keyReleased(KeyEvent e) {
	            String input = editor.getText();

	            DefaultComboBoxModel<String> newModel = new DefaultComboBoxModel<>();
	            for (String item : allItems) {
	                if (item.toUpperCase().contains(input.toUpperCase())) {
	                    newModel.addElement(item);
	                }
	            }

	            SwingUtilities.invokeLater(() -> {
	                combo.setModel(newModel);
	                combo.setSelectedItem(input);
	                combo.setPopupVisible(true);
	                editor.setText(input);
	                editor.setCaretPosition(input.length());
	            });
	        }
	    });
	}
	
	public JComboBox<String> getEditingComboBox(JTable table, int columnIndex) {
	    if (table.getCellEditor() != null && table.getEditingColumn() == columnIndex) {
	        Component editorComponent = table.getEditorComponent();
	        if (editorComponent instanceof JComboBox) {
	            return (JComboBox<String>) editorComponent;
	        }
	    }
	    return null;
	}
	
	// 外部呼叫：移除下拉選單的某個車牌
	public static void removeLicenseFromCombo(JTable table, int columnIndex, String licenseToRemove) {
	    if (table.getCellEditor() != null && table.getEditingColumn() == columnIndex) {
	        Component editorComponent = table.getEditorComponent();
	        if (editorComponent instanceof JComboBox) {
	            JComboBox<String> comboBox = (JComboBox<String>) editorComponent;
	            comboBox.removeItem(licenseToRemove);
	            
	            // 👉 如果還有其他選項，就預設選第一個
	            if (comboBox.getItemCount() > 0) {
	                comboBox.setSelectedIndex(0);
	            }
	            else
	            {
	            	comboBox.setSelectedItem(null);	
	            }
	            
	            
	            System.out.println("[外部移除] license=" + licenseToRemove);
	        }
	    }
	}

}
