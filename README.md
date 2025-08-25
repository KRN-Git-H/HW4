# 🚗 停車場管理系統 Parking Management System

## 專案說明
這是一個基於 **Java Swing + MySQL + JFreeChart** 的停車場管理系統，功能包含：  

- 會員資料管理  
- 月租繳費管理  
- 停車進出紀錄統計  
- 每日與每月營收圖表  
- 每日停車次數及平均停車時間分析  
- 中文字型顯示及可切換圖表類型（圓餅圖 / 長條圖）  

---

## 功能列表

| 功能 | 說明 |
|------|------|
| 會員資料管理 | 可新增、修改、查詢會員資料 |
| 月租繳費管理 | 管理月租會員繳費紀錄 |
| 停車紀錄統計 | 從 MySQL 讀取停車進出資料 |
| 每日營收圖表 | 顯示每日總營收，長條圖 |
| 每月營收圖表 | 顯示每月總營收，可切換圓餅圖 / 長條圖 |
| 平均停車時間 | 每日平均停車時間折線圖 |
| 停車次數 | 每日停車次數統計 |

---

## 專案架構

├─ controller  
│ │ MainUI.java  
│ │ ManagerUI.java
│ │ ParkingReportUI.java
│ │
│ ├─ admin
│ │ MemberModifyUI.java
│ │ MonthRentModifyUI.java
│ │
│ ├─ login
│ │ LoginErrorUI.java
│ │ LoginUI.java
│ │
│ └─ member
│ AddMember.java
│
├─ dao
│ │ EmployeeDao.java
│ │ MemberDao.java
│ │ MemberLicenseDao.java
│ │ MonthRentDao.java
│ │ ParkingRecordDao.java
│ │
│ └─ impl
│ EmployeeDaoImpl.java
│ MemberDaoImpl.java
│ MemberLicenseDaoImpl.java
│ MonthRentDaoImpl.java
│ ParkingRecordDaoImpl.java
│
├─ model
│ Employee.java
│ LicenseHolder.java
│ Member.java
│ MemberLicense.java
│ MonthRent.java
│ ParkingRecord.java
│ ParkingRecords.java
│
├─ service
│ │ EmployeeService.java
│ │ MemberLicenseService.java
│ │ MemberService.java
│ │ MonthRentService.java
│ │ ParkingRecordService.java
│ │
│ └─ impl
│ EmployeeServiceImpl.java
│ MemberLicenseServiceImpl.java
│ MemberServiceImpl.java
│ MonthRentServiceImpl.java
│ ParkingRecordServiceImpl.java
│
└─ util
DbConnection.java
Tool.java
ToolUI.java

---

## 環境需求

- **Java JDK 11** 或以上  
- **MySQL 8+**  
- **Maven** (建議管理依賴)  
- **JFreeChart / JCommon** (已加入 pom.xml)  
