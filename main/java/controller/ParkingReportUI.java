package controller;

import model.ParkingRecord;
import service.ParkingRecordService;
import service.impl.ParkingRecordServiceImpl;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.jfree.chart.*;
import org.jfree.chart.plot.*;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;

public class ParkingReportUI extends JFrame {

    private ParkingRecordService service;
    private JComboBox<String> monthBox;
    private JTabbedPane tabbedPane;
    private JComboBox<String> chartTypeBox; 
    private JPanel monthlyChartPanel;

    public ParkingReportUI(ParkingRecordService service) {
        this.service = service;

        setTitle("2025 停車場統計報表");
        setSize(1200, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // 控制區
        JPanel controlPanel = new JPanel();
        controlPanel.add(new JLabel("月份:"));
        monthBox = new JComboBox<>();
        for (int i = 1; i <= 12; i++) {
            monthBox.addItem(String.format("%02d", i));
        }
        controlPanel.add(monthBox);
        add(controlPanel, BorderLayout.NORTH);

        // 分頁容器
        tabbedPane = new JTabbedPane();
        add(tabbedPane, BorderLayout.CENTER);

        // 每月營收分頁
        monthlyChartPanel = new JPanel(new BorderLayout());
        chartTypeBox = new JComboBox<>(new String[]{"圓餅圖", "長條圖"});
        JPanel topPanel = new JPanel();
        topPanel.add(new JLabel("圖表類型:"));
        topPanel.add(chartTypeBox);

        // 回上一頁按鈕
        JButton backButton = new JButton("回上一頁");
        backButton.addActionListener(e -> {
            ManagerUI managerUI = new ManagerUI();
            managerUI.setVisible(true);
            this.dispose();
        });
        topPanel.add(backButton);

        monthlyChartPanel.add(topPanel, BorderLayout.NORTH);
        tabbedPane.addTab("每月營收", monthlyChartPanel);

        // 事件監聽
        monthBox.addActionListener(e -> loadCharts());
        chartTypeBox.addActionListener(e -> loadMonthlyChart());

        // 預設載入一月
        monthBox.setSelectedIndex(0);

        setVisible(true);
    }

    private void loadCharts() {
        String month = (String) monthBox.getSelectedItem();

        List<ParkingRecord> records = service.readAll().stream()
                .filter(r -> r.getEntryTime().getYear() == 2025)
                .filter(r -> String.format("%02d", r.getEntryTime().getMonthValue()).equals(month))
                .collect(Collectors.toList());

        // 移除舊分頁除了每月營收
        while (tabbedPane.getTabCount() > 1) {
            tabbedPane.removeTabAt(0);
        }

        if (records.isEmpty()) {
            JOptionPane.showMessageDialog(this, "該月份沒有資料");
            return;
        }

        // 每日營收
        DefaultCategoryDataset revenueDataset = new DefaultCategoryDataset();
        Map<LocalDate, Integer> dailyRevenue = records.stream()
                .collect(Collectors.groupingBy(
                        r -> r.getEntryTime().toLocalDate(),
                        Collectors.summingInt(ParkingRecord::getPaymentAmount)
                ));
        dailyRevenue.forEach((day, sum) ->
                revenueDataset.addValue(sum, "營收", day.format(DateTimeFormatter.ofPattern("MM-dd")))
        );
        JFreeChart revenueChart = ChartFactory.createBarChart(
                "2025-" + month + " 每日營收", "日期", "金額 (NTD)",
                revenueDataset, PlotOrientation.VERTICAL, true, true, false);
        setChineseFont(revenueChart);
        tabbedPane.insertTab("每日營收", null, new ChartPanel(revenueChart), null, 0);

        // 每日平均停車時間
        DefaultCategoryDataset timeDataset = new DefaultCategoryDataset();
        Map<LocalDate, Double> dailyAvgMinutes = records.stream()
                .filter(r -> r.getParkingTime() != null)
                .collect(Collectors.groupingBy(
                        r -> r.getEntryTime().toLocalDate(),
                        Collectors.averagingDouble(r -> r.getParkingTime().getHour() * 60 + r.getParkingTime().getMinute())
                ));
        dailyAvgMinutes.forEach((day, avg) ->
                timeDataset.addValue(avg, "平均停車時間(分鐘)", day.format(DateTimeFormatter.ofPattern("MM-dd")))
        );
        JFreeChart timeChart = ChartFactory.createLineChart(
                "2025-" + month + " 每日平均停車時間", "日期", "分鐘",
                timeDataset, PlotOrientation.VERTICAL, true, true, false);
        setChineseFont(timeChart);
        tabbedPane.insertTab("停車時間", null, new ChartPanel(timeChart), null, 2);

        // 每日停車次數
        DefaultCategoryDataset countDataset = new DefaultCategoryDataset();
        Map<LocalDate, Long> dailyCount = records.stream()
                .collect(Collectors.groupingBy(
                        r -> r.getEntryTime().toLocalDate(),
                        Collectors.counting()
                ));
        dailyCount.forEach((day, cnt) ->
                countDataset.addValue(cnt, "停車次數", day.format(DateTimeFormatter.ofPattern("MM-dd")))
        );
        JFreeChart countChart = ChartFactory.createBarChart(
                "2025-" + month + " 每日停車次數", "日期", "次數",
                countDataset, PlotOrientation.VERTICAL, true, true, false);
        setChineseFont(countChart);
        tabbedPane.insertTab("停車次數", null, new ChartPanel(countChart), null, 3);

        tabbedPane.revalidate();
        tabbedPane.repaint();

        // 更新每月營收圖表
        loadMonthlyChart();
    }

    private void loadMonthlyChart() {
        monthlyChartPanel.removeAll();

        JPanel topPanel = new JPanel();
        topPanel.add(new JLabel("圖表類型:"));
        topPanel.add(chartTypeBox);

        // 回上一頁按鈕
        JButton backButton = new JButton("回上一頁");
        backButton.addActionListener(e -> {
            ManagerUI managerUI = new ManagerUI();
            managerUI.setVisible(true);
            this.dispose();
        });
        topPanel.add(backButton);

        monthlyChartPanel.add(topPanel, BorderLayout.NORTH);

        List<ParkingRecord> all2025 = service.readAll().stream()
                .filter(r -> r.getEntryTime().getYear() == 2025)
                .collect(Collectors.toList());

        Map<Integer, Integer> monthlyRevenue = all2025.stream()
                .collect(Collectors.groupingBy(
                        r -> r.getEntryTime().getMonthValue(),
                        Collectors.summingInt(ParkingRecord::getPaymentAmount)
                ));

        String chartType = (String) chartTypeBox.getSelectedItem();
        JFreeChart chart;

        if ("圓餅圖".equals(chartType)) {
            DefaultPieDataset pieDataset = new DefaultPieDataset();
            monthlyRevenue.forEach((m, sum) -> pieDataset.setValue(String.format("%02d 月", m), sum));
            chart = ChartFactory.createPieChart("2025 每月營收佔比", pieDataset, true, true, false);
        } else {
            DefaultCategoryDataset barDataset = new DefaultCategoryDataset();
            monthlyRevenue.forEach((m, sum) -> barDataset.addValue(sum, "營收", String.format("%02d 月", m)));
            chart = ChartFactory.createBarChart("2025 每月營收", "月份", "金額 (NTD)",
                    barDataset, PlotOrientation.VERTICAL, true, true, false);
        }

        setChineseFont(chart);
        monthlyChartPanel.add(new ChartPanel(chart), BorderLayout.CENTER);

        monthlyChartPanel.revalidate();
        monthlyChartPanel.repaint();
    }

    private void setChineseFont(JFreeChart chart) {
        Font titleFont = new Font("Microsoft JhengHei", Font.BOLD, 18);
        chart.getTitle().setFont(titleFont);

        if (chart.getPlot() instanceof CategoryPlot) {
            CategoryPlot plot = (CategoryPlot) chart.getPlot();
            Font labelFont = new Font("Microsoft JhengHei", Font.PLAIN, 14);
            plot.getDomainAxis().setLabelFont(labelFont);
            plot.getDomainAxis().setTickLabelFont(labelFont);
            plot.getRangeAxis().setLabelFont(labelFont);
            plot.getRangeAxis().setTickLabelFont(labelFont);

            if (plot.getRenderer() instanceof BarRenderer) {
                BarRenderer barRenderer = (BarRenderer) plot.getRenderer();
                barRenderer.setDefaultItemLabelFont(labelFont);
            }
        } else if (chart.getPlot() instanceof PiePlot) {
            PiePlot piePlot = (PiePlot) chart.getPlot();
            Font labelFont = new Font("Microsoft JhengHei", Font.PLAIN, 14);
            piePlot.setLabelFont(labelFont);
        }
    }

    public static void main(String[] args) {
        ParkingRecordService service = new ParkingRecordServiceImpl();
        SwingUtilities.invokeLater(() -> new ParkingReportUI(service));
    }
}
