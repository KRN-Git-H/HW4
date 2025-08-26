package util;

import java.awt.Desktop;
import java.awt.Frame;
import java.awt.Window;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.MessageFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import model.Employee;
import model.Member;
import model.MonthRent;

public class Tool {

	public static void main(String[] args) {
		
		
	}

	//存物件檔
	public static void saveFile(Object object,String fileName)
	{
		try {
			FileOutputStream fos=new FileOutputStream(fileName);
			ObjectOutputStream oos=new ObjectOutputStream(fos);
			oos.writeObject(object);
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	//讀取物件檔
	public static Object readFile(String fileName)
	{
		Object object=null;
		
		try {
			FileInputStream fis=new FileInputStream(fileName);
			ObjectInputStream ois=new ObjectInputStream(fis);
			object=ois.readObject();
		
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		
		return object;
	}
	
	//查詢管理者
	public static boolean isAdmin(Employee employee)
	{
		return employee!=null ? true:false;	
	}

    public static LocalDateTime StrToLocalDateTime(String strDateTime,int addMonth)
    {
	    DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
	    LocalDateTime localDateTime = LocalDateTime.parse(strDateTime, format);
	    
	    LocalDateTime newDateTime = localDateTime.plusMonths(addMonth);

	    return newDateTime;
    }
    
    public static Date LocalDateTimeToDate(LocalDateTime localDateTime,int addMonth)
    {
    	LocalDateTime newDateTime = localDateTime.plusMonths(addMonth);

	    // 2. 取得系統預設時區
	    ZoneId zoneId = ZoneId.systemDefault();
	
	    // 3. 結合 LocalDateTime 和時區，建立 ZonedDateTime
	    // 4. 再將其轉換為 Instant
	    Instant instant = localDateTime.atZone(zoneId).toInstant();
	
	    // 5. 最後，從 Instant 轉換為 Date
	    Date date = Date.from(instant);
    
    	return date;
    }

    public static LocalDateTime DatetoLocalDateTime(Date date,int addMonth)
    {
        LocalDateTime localDateTime = date.toInstant()
                						  .atZone(ZoneId.systemDefault())
                						  .toLocalDateTime();
	    
	    LocalDateTime newDateTime = localDateTime.plusMonths(addMonth);

	    return newDateTime;
    }  

    public static void alarmMsg(Frame frame,String Msg)
	{
    	JOptionPane.showMessageDialog(frame,Msg,"Message",JOptionPane.INFORMATION_MESSAGE);
		System.out.println(Msg);
	}    
    
    public static JFrame tableFindFrame(JTable table)
    {
        JFrame parentFrame = null;
        
    	// 從 table 元件開始，往上尋找父視窗
		Window window = SwingUtilities.getWindowAncestor(table);
		
		if (window instanceof JFrame) {
			parentFrame = (JFrame) window;
		    System.out.println("成功從 JTable 抓取到主視窗！");
		
		} else {
			System.out.println("無法從 JTable 抓取到 JFrame。");
		}
		
		return parentFrame;
    }

    public static void TabletoExcel(JTable table,String license)
    {
    	if(checkTableNull(table))
    	{	
    		String filePath = null;
    	
	    	JFileChooser fileChooser = new JFileChooser();
	        fileChooser.setDialogTitle("另存為 Excel 檔案");
	        
	        // 設定檔案過濾器，讓使用者只能選擇 .xlsx 檔案
	        FileNameExtensionFilter filter = new FileNameExtensionFilter("Excel 檔案 (*.xlsx)", "xlsx");
	        fileChooser.setFileFilter(filter);
	
	        //預設檔名
	        license = license.equals("") ? "License" : license;
	        String filename = license + " - " + LocalDateTime.now().format(timeFormat(false)).toString();
	        
	        File defaultFile = new File(filename);
	        fileChooser.setSelectedFile(defaultFile);
	        
	        // 顯示另存新檔對話框
	        int userSelection = fileChooser.showSaveDialog(null);
	
	        // 如果使用者點擊了「儲存」按鈕
	        if (userSelection == JFileChooser.APPROVE_OPTION) {
	            File fileToSave = fileChooser.getSelectedFile();
	            
	            // 確保檔案名以 .xlsx 結尾
	            filePath = fileToSave.getAbsolutePath();
	            if (!filePath.endsWith(".xlsx")) {
	                filePath += ".xlsx";
	            }
	        }
	        	
	    	try
	    	{
	    		Workbook workbook = new XSSFWorkbook();
	    		FileOutputStream fileOut = new FileOutputStream(filePath);

	    		Sheet sheet = workbook.createSheet("JTable Data"); // 這行現在會正常運作
	    		DefaultTableModel model = (DefaultTableModel) table.getModel();
	
	            // 寫入表頭
	            org.apache.poi.ss.usermodel.Row headerRow = sheet.createRow(0);
	            for (int i = 0; i < model.getColumnCount(); i++) {
	                Cell cell = headerRow.createCell(i);
	                cell.setCellValue(model.getColumnName(i));
	            }
	
	            // 寫入表格資料
	            for (int rows = 0; rows < model.getRowCount(); rows++) {
	                org.apache.poi.ss.usermodel.Row row = sheet.createRow(rows + 1);
	                for (int cols = 0; cols < model.getColumnCount(); cols++) {
	                    Cell cell = row.createCell(cols);
	                    Object value = model.getValueAt(rows, cols);
	                    if (value != null) {
	                        cell.setCellValue(value.toString());
	                    }
	                }
	            }
	
	            // 手動設定每一欄的寬度
	            for (int i = 0; i < model.getColumnCount(); i++) {
	                int maxLength = model.getColumnName(i).length(); // 初始欄寬為標題長度
	                for (int j = 0; j < model.getRowCount(); j++) {
	                    int cellLength = model.getValueAt(j, i).toString().length(); // 取得單元格資料長度
	                    maxLength = Math.max(maxLength, cellLength); // 計算最大長度
	                }

	                // 設定每一欄的寬度，根據最大長度設定寬度（單位：1/256）
	                sheet.setColumnWidth(i, (maxLength + 6) * 256); // +2是為了避免太窄
	            }

	           workbook.write(fileOut);
	
	           alarmMsg(tableFindFrame(table),"表單匯出成功。");
	           
	           // --- 新增的程式碼區塊：自動開啟檔案 ---
	           File excelFile = new File(filePath);
	           if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.OPEN)) {
	               Desktop.getDesktop().open(excelFile);
	           }           

	    	} catch(NullPointerException a) {
	    		System.out.println("表單匯出失敗。");
 	
	    	} catch (Exception e) {
	    		e.printStackTrace();
	    		System.out.println("Tool.TabletoExcel Error");
	    	}
    	}	
    }
    
    
    public static boolean checkTableNull(JTable table)
    {
        boolean result = false;
        String msg = "";
        
    	// 判斷 JTable 有無資料
        if (table.getModel().getRowCount() > 0) {
        	result = true;
            System.out.println("Table 有資料。");
        } 
        
        else {
            msg = "Table 沒有資料。";
            
            alarmMsg(tableFindFrame(table),msg);
        }
        
        return result;
    }
    
    public static void print(JTable table,String title)
    {
        if(checkTableNull(table))
        {
        	PrinterJob job = PrinterJob.getPrinterJob();
        	MessageFormat header = new MessageFormat(title);
	        MessageFormat footer = new MessageFormat("第 {0} 頁");   

			try {
				table.print(JTable.PrintMode.FIT_WIDTH, header, footer);
			} catch (PrinterException e) {
				e.printStackTrace();
			}   
        }
    }
    
    public static DateTimeFormatter timeFormat(boolean type)
    {
    	DateTimeFormatter format;
    	
    	if (type)
    	{
    		format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    	}
    	
    	else
    	{
    		format = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss");
    	}
    	
    	return format;
    }
    
    public static boolean checkMemberFormat(Member member)
    {
		boolean result = true;
		
    	if(member.getAccount().trim().equals("") ||
    	member.getPassword().trim().equals("") ||
    	member.getName().trim().equals("")||
    	member.getPhone().trim().equals(""))
    //	member.getLicense().equals(""))
    	{
    		result = false;
    	}
		
		return result;
    }
    
    public static boolean checkMemberRentFormat(MonthRent monthRent)
    {
		boolean result = true;
		
    	if(monthRent.getLicense().trim().equals("") ||
    			monthRent.getPaymentTime().toString().trim().equals("") ||
    			monthRent.getExpiryTime().toString().trim().equals("")||
    			monthRent.getPaymentAmount()==0||
    			monthRent.getPaymentMethod().toString().trim().equals(""))
    	{
    		result = false;
    	}
		
		return result;
    }
}