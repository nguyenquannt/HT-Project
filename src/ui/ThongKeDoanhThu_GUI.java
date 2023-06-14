package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import components.barChart.Chart;
import components.barChart.ModelChart;
import components.button.Button;
import components.panelRound.PanelRound;
import components.scrollbarCustom.ScrollBarCustom;
import dao.ChiTietDatPhong_DAO;
import dao.ChiTietDichVu_DAO;
import dao.NhanVien_DAO;
import entity.ChiTietDatPhong;
import entity.ChiTietDichVu;
import entity.Phong;
import utils.NhanVien;
import utils.Utils;

public class ThongKeDoanhThu_GUI extends JPanel {

	private static final long serialVersionUID = 1L;
	private Rectangle boundsChart;
	private Rectangle boundsPnlChart;
	private Rectangle boundsPnlResultContainer;
	private Button btnDay, btnMonth, btnYear;
	private Chart chart;
	private ChiTietDatPhong_DAO chiTietDatPhong_DAO;
	private ChiTietDichVu_DAO chiTietDichVu_DAO;
	private JComboBox<String> cmbDay, cmbMonth, cmbYear;
	private ArrayList<String> dsKhachHang;
	private ArrayList<String> dsNhanVien;
	private ArrayList<Double> dsTongTien;
	private boolean isPhongVIP;
	private JLabel lblResDate, lblTongDoanhThuKQ, lblTongTienPhongKQ, lblDoanhThuPhongThuongKQ, lblDoanhThuPhongVIPKQ,
			lblTongTienDVKQ, lblTongSoGHKQ, lblTongSoHDKQ, lblDoanhThuTrungBinhKQ, lblResult;
	private String maNhanVien;
	private PanelRound pnlResultContainer, pnlChart;
	private JScrollPane scr;
	private ArrayList<String> setMaDonDatPhong;
	private DefaultTableModel tableModel;
	private JTable tbl;
	private int top;
	private JComboBox<String> cmbNV;
	private final String labelNhanVien = "Mã nhân viên";

	public ThongKeDoanhThu_GUI() {
		chiTietDatPhong_DAO = new ChiTietDatPhong_DAO();
		chiTietDichVu_DAO = new ChiTietDichVu_DAO();
		int padding = (int) Math.floor((Utils.getBodyHeight() - 509) * 1.0 / 3);
		top = padding;
		entity.NhanVien nhanVien = NhanVien.getNhanVien();
		maNhanVien = nhanVien.getChucVu().equals(entity.NhanVien.ChucVu.QuanLy) ? "" : nhanVien.getMaNhanVien();

		setBackground(new Color(242, 246, 252));
		setBounds(0, 0, Utils.getScreenWidth(), Utils.getBodyHeight());
		setLayout(null);

		// ================== Bắt đầu phần Button Tính doanh thu

		PanelRound pnlContainerAction = new PanelRound();
		pnlContainerAction.setBackground(Color.WHITE);
		pnlContainerAction.setBounds(Utils.getLeft(1052), top, 1052, 137);
		top += 137 + padding;
		pnlContainerAction.setRoundBottomRight(20);
		pnlContainerAction.setRoundTopLeft(20);
		pnlContainerAction.setRoundTopRight(20);
		pnlContainerAction.setRoundBottomLeft(20);
		this.add(pnlContainerAction);
		pnlContainerAction.setLayout(null);

		JPanel pnlRevenueCalculation = new JPanel();
		pnlRevenueCalculation.setBackground(Color.WHITE);
		pnlRevenueCalculation.setBounds(20, 15, 1012, 46);
		pnlContainerAction.add(pnlRevenueCalculation);
		pnlRevenueCalculation.setLayout(null);

		JLabel lblRevenueCalculation = new JLabel("Thống kê doanh thu theo:");
		lblRevenueCalculation.setBounds(329, 9, 299, 28);
		lblRevenueCalculation.setFont(new Font("Segoe UI", Font.BOLD, 18));
		lblRevenueCalculation.setForeground(new Color(100, 100, 100));
		pnlRevenueCalculation.add(lblRevenueCalculation);

		btnDay = new Button("Ngày");
		btnDay.setFocusable(false);
		btnDay.setForeground(new Color(100, 100, 100));
		btnDay.setColor(new Color(242, 246, 252));
		btnDay.setFont(new Font("Segoe UI", Font.BOLD, 16));
		btnDay.setBounds(638, 1, 118, 44);
		btnDay.setBorder(new EmptyBorder(0, 0, 0, 0));
		btnDay.setBorderColor(new Color(242, 246, 252));
		btnDay.setColorOver(new Color(242, 246, 252));
		btnDay.setColorClick(Utils.primaryColor);
		btnDay.setRadius(10);
		pnlRevenueCalculation.add(btnDay);

		btnDay.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				pnlChart.setBounds((Utils.getScreenWidth() - 235) / 2 + 100, 180, 0, 0);
				pnlResultContainer.setBounds(90, 180, 0, 0);

				btnDay.setColor(Utils.primaryColor);
				btnDay.setBorderColor(Utils.primaryColor);
				btnDay.setColorOver(Utils.primaryColor);
				btnDay.setForeground(Color.WHITE);

				btnMonth.setColor(new Color(242, 246, 252));
				btnMonth.setForeground(new Color(100, 100, 100));
				btnMonth.setColorTextOut(new Color(100, 100, 100));
				btnMonth.setBorderColor(new Color(242, 246, 252));
				btnMonth.setColorOver(new Color(242, 246, 252));

				btnYear.setColor(new Color(242, 246, 252));
				btnYear.setForeground(new Color(100, 100, 100));
				btnYear.setColorTextOut(new Color(100, 100, 100));
				btnYear.setBorderColor(new Color(242, 246, 252));
				btnYear.setColorOver(new Color(242, 246, 252));

				cmbDay.setEnabled(true);
				cmbMonth.setEnabled(true);
				setDaysToCmb();
			}
		});

		btnMonth = new Button("Tháng");
		btnMonth.setFocusable(false);
		btnMonth.setForeground(Color.WHITE);
		btnMonth.setColor(Utils.primaryColor);
		btnMonth.setFont(new Font("Segoe UI", Font.BOLD, 16));
		btnMonth.setBounds(766, 1, 118, 44);
		btnMonth.setBorder(new EmptyBorder(0, 0, 0, 0));
		btnMonth.setBorderColor(Utils.primaryColor);
		btnMonth.setColorOver(Utils.primaryColor);
		btnMonth.setColorClick(Utils.primaryColor);
		btnMonth.setRadius(10);
		pnlRevenueCalculation.add(btnMonth);

		btnMonth.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				pnlChart.setBounds((Utils.getScreenWidth() - 235) / 2 + 100, 180, 0, 0);
				pnlResultContainer.setBounds(90, 180, 0, 0);

				btnMonth.setColor(Utils.primaryColor);
				btnMonth.setBorderColor(Utils.primaryColor);
				btnMonth.setColorOver(Utils.primaryColor);
				btnMonth.setForeground(Color.WHITE);

				btnYear.setColor(new Color(242, 246, 252));
				btnYear.setForeground(new Color(100, 100, 100));
				btnYear.setColorTextOut(new Color(100, 100, 100));
				btnYear.setBorderColor(new Color(242, 246, 252));
				btnYear.setColorOver(new Color(242, 246, 252));

				btnDay.setColor(new Color(242, 246, 252));
				btnDay.setForeground(new Color(100, 100, 100));
				btnDay.setColorTextOut(new Color(100, 100, 100));
				btnDay.setBorderColor(new Color(242, 246, 252));
				btnDay.setColorOver(new Color(242, 246, 252));

				cmbDay.setEnabled(false);
				cmbMonth.setEnabled(true);
			}
		});

		btnYear = new Button("Năm");
		btnYear.setFocusable(false);
		btnYear.setForeground(new Color(100, 100, 100));
		btnYear.setColor(new Color(242, 246, 252));
		btnYear.setFont(new Font("Segoe UI", Font.BOLD, 16));
		btnYear.setBounds(894, 1, 118, 44);
		btnYear.setBorder(new EmptyBorder(0, 0, 0, 0));
		btnYear.setBorderColor(new Color(242, 246, 252));
		btnYear.setColorOver(Utils.primaryColor);
		btnYear.setColorClick(Utils.primaryColor);
		btnYear.setRadius(10);
		pnlRevenueCalculation.add(btnYear);

		btnYear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				pnlChart.setBounds((Utils.getScreenWidth() - 235) / 2 + 100, 180, 0, 0);
				pnlResultContainer.setBounds(90, 180, 0, 0);

				btnYear.setColor(Utils.primaryColor);
				btnYear.setBorderColor(Utils.primaryColor);
				btnYear.setColorOver(Utils.primaryColor);
				btnYear.setForeground(Color.WHITE);

				btnMonth.setColor(new Color(242, 246, 252));
				btnMonth.setForeground(new Color(100, 100, 100));
				btnMonth.setColorTextOut(new Color(100, 100, 100));
				btnMonth.setBorderColor(new Color(242, 246, 252));
				btnMonth.setColorOver(new Color(242, 246, 252));

				btnDay.setColor(new Color(242, 246, 252));
				btnDay.setForeground(new Color(100, 100, 100));
				btnDay.setColorTextOut(new Color(100, 100, 100));
				btnDay.setBorderColor(new Color(242, 246, 252));
				btnDay.setColorOver(new Color(242, 246, 252));

				cmbDay.setEnabled(false);
				cmbMonth.setEnabled(false);

			}
		});

		// ================== Kết thúc phần Button Tính doanh thu
		// ================== Bắt đầu phần hiển thị ngày, tháng, năm
		JPanel pnlDate = new JPanel();
		pnlDate.setBackground(Color.WHITE);
		pnlDate.setBounds(20, 76, 1012, 46);
		pnlContainerAction.add(pnlDate);
		pnlDate.setLayout(null);

		JLabel lblDay = new JLabel("Ngày: ");
		lblDay.setForeground(new Color(100, 100, 100));
		lblDay.setBounds(0, 9, 70, 28);
		lblDay.setFont(new Font("Segoe UI", Font.BOLD, 16));
		pnlDate.add(lblDay);

		cmbDay = new JComboBox<String>();
		cmbDay.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		cmbDay.setBackground(Color.WHITE);
		cmbDay.setBounds(70, 3, 100, 40);
		cmbDay.setBorder(new EmptyBorder(0, 0, 0, 0));
		cmbDay.setEnabled(false);
		pnlDate.add(cmbDay);

		JLabel lblMonth = new JLabel("Tháng: ");
		lblMonth.setForeground(new Color(100, 100, 100));
		lblMonth.setBounds(269, 9, 70, 28);
		lblMonth.setFont(new Font("Segoe UI", Font.BOLD, 16));
		pnlDate.add(lblMonth);

		cmbMonth = new JComboBox<String>();
		cmbMonth.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		cmbMonth.setBackground(Color.WHITE);
		cmbMonth.setAlignmentX(CENTER_ALIGNMENT);
		cmbMonth.setBounds(339, 3, 100, 40);
		cmbMonth.setBorder(new EmptyBorder(0, 0, 0, 0));
		cmbMonth.setSelectedItem("11");
		cmbMonth.addItemListener(e -> setDaysToCmb());
		pnlDate.add(cmbMonth);

		for (int i = 1; i < 13; i++)
			cmbMonth.addItem(Utils.convertIntToString(i));

		JLabel lblYear = new JLabel("Năm: ");
		lblYear.setForeground(new Color(100, 100, 100));
		lblYear.setBounds(538, 9, 70, 28);
		lblYear.setFont(new Font("Segoe UI", Font.BOLD, 16));
		pnlDate.add(lblYear);

		cmbYear = new JComboBox<String>();
		cmbYear.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		cmbYear.setBackground(Color.WHITE);
		cmbYear.setAlignmentX(CENTER_ALIGNMENT);
		cmbYear.setBounds(608, 3, 100, 40);
		cmbYear.setBorder(new EmptyBorder(0, 0, 0, 0));
		cmbYear.addItemListener(e -> setDaysToCmb());
		pnlDate.add(cmbYear);

		JPanel pnlNhanVien = new JPanel();
		pnlNhanVien.setBackground(Color.WHITE);
		pnlNhanVien.setBounds(0, 9, 209, 28);
		pnlContainerAction.add(pnlDate);
		pnlNhanVien.setLayout(null);

		JLabel lblNhanVien = new JLabel("Nhân viên: ");
		lblNhanVien.setForeground(new Color(100, 100, 100));
		lblNhanVien.setBounds(0, 0, 100, 28);
		lblNhanVien.setFont(new Font("Segoe UI", Font.BOLD, 16));
		pnlNhanVien.add(lblNhanVien);

		cmbNV = new JComboBox<String>();
		cmbNV.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		cmbNV.setBackground(Color.WHITE);
		cmbNV.setBounds(109, 0, 100, 28);
		cmbNV.setBorder(new EmptyBorder(0, 0, 0, 0));
		pnlNhanVien.add(cmbNV);

//		
		cmbNV.addItem(labelNhanVien);
		new NhanVien_DAO().getAllNhanVien().forEach(nv -> cmbNV.addItem(nv.getMaNhanVien()));

		if (maNhanVien.equals(""))
			pnlRevenueCalculation.add(pnlNhanVien);

		int yearNow = LocalDate.now().getYear();
		for (int i = 2015; i <= yearNow; ++i)
			cmbYear.addItem(i + "");

		// ================== Kết thúc phần hiển thị ngày, tháng, năm

		Button btnStatisticize = new Button("Thống kê doanh thu");
		btnStatisticize.setFocusable(false);
		btnStatisticize.setForeground(Color.WHITE);
		btnStatisticize.setColor(Utils.primaryColor);
		btnStatisticize.setBorderColor(Utils.primaryColor);
		btnStatisticize.setRadius(10);
		btnStatisticize.setFont(new Font("Segoe UI", Font.BOLD, 16));
		btnStatisticize.setBounds(807, 1, 205, 44);
		btnStatisticize.setColorOver(Utils.primaryColor);
		btnStatisticize.setColorTextOver(Color.WHITE);
		btnStatisticize.setColorTextOut(Color.WHITE);
		btnStatisticize.setColorClick(Utils.primaryColor);
		btnStatisticize.setBorder(new EmptyBorder(0, 0, 0, 0));
		pnlDate.add(btnStatisticize);

		btnStatisticize.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				handleThongKe();
				cmbYear.showPopup();
				cmbYear.hidePopup();
			}

		});

		// Kết quả doanh thu
		boundsPnlResultContainer = new Rectangle(Utils.getLeft(1052), top, 516, 335);
		boundsPnlChart = new Rectangle(Utils.getLeft(1052) + 536, top, 516, 335);
		boundsChart = new Rectangle(10, 10, 496, 315);
		pnlResultContainer = new PanelRound();
		pnlResultContainer.setBounds(boundsPnlResultContainer);
		pnlResultContainer.setBackground(Color.WHITE);
		pnlResultContainer.setRoundBottomRight(20);
		pnlResultContainer.setRoundTopLeft(20);
		pnlResultContainer.setRoundTopRight(20);
		pnlResultContainer.setRoundBottomLeft(20);
		pnlResultContainer.setLayout(null);
		this.add(pnlResultContainer);

		// Phần Header Doanh thu hiện tại
		lblResult = new JLabel("Doanh thu trong tháng hiện tại: ", SwingConstants.LEFT);
		lblResult.setBounds(20, 15, 299, 24);
		lblResult.setFont(new Font("Segoe UI", Font.BOLD, 18));
		lblResult.setForeground(new Color(100, 100, 100));
		pnlResultContainer.add(lblResult);

		lblResDate = new JLabel("11/2022", SwingConstants.RIGHT);
		lblResDate.setBounds(197, 15, 299, 24);
		lblResDate.setFont(new Font("Segoe UI", Font.BOLD, 18));
		lblResDate.setForeground(new Color(100, 100, 100));
		pnlResultContainer.add(lblResDate);

		// Phần line
		JPanel line = new JPanel();
		line.setBounds(20, 50, 476, 2);
		line.setLayout(null);
		line.setBackground(new Color(100, 100, 100));
		pnlResultContainer.add(line);

		// Phần Result
		PanelRound pnlResult = new PanelRound();
		pnlResult.setBounds(20, 65, 476, 249);
		pnlResult.setBackground(Color.WHITE);
		pnlResult.setLayout(null);
		pnlResultContainer.add(pnlResult);

		// Tổng doanh thu
		JPanel pnlTongDoanhThu = new JPanel();
		pnlTongDoanhThu.setBounds(0, 0, 476, 30);
		pnlTongDoanhThu.setBackground(Color.WHITE);
		pnlTongDoanhThu.setLayout(new BorderLayout());
		pnlResult.add(pnlTongDoanhThu);

		JLabel lblTongDoanhThu = new JLabel("Tổng doanh thu: ");
		lblTongDoanhThu.setFont(new Font("Segoe UI", Font.BOLD, 16));
		lblTongDoanhThu.setForeground(Utils.primaryColor);
		pnlTongDoanhThu.add(lblTongDoanhThu, BorderLayout.WEST);

		lblTongDoanhThuKQ = new JLabel("");
		lblTongDoanhThuKQ.setBounds(-1, 0, 299, 28);
		lblTongDoanhThuKQ.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		lblTongDoanhThuKQ.setForeground(new Color(100, 100, 100));
		pnlTongDoanhThu.add(lblTongDoanhThuKQ, BorderLayout.EAST);

		// Tổng tiền phòng
		JPanel pnlTongTienPhong = new JPanel();
		pnlTongTienPhong.setBounds(59, 30, 417, 30);
		pnlTongTienPhong.setBackground(Color.WHITE);
		pnlTongTienPhong.setLayout(new BorderLayout());
		pnlResult.add(pnlTongTienPhong);

		JLabel lblTongTienPhong = new JLabel("Tổng tiền phòng: ");
		lblTongTienPhong.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		lblTongTienPhong.setForeground(new Color(100, 100, 100));
		pnlTongTienPhong.add(lblTongTienPhong, BorderLayout.WEST);

		lblTongTienPhongKQ = new JLabel("");
		lblTongTienPhongKQ.setBounds(-1, 0, 299, 28);
		lblTongTienPhongKQ.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		lblTongTienPhongKQ.setForeground(new Color(100, 100, 100));
		pnlTongTienPhong.add(lblTongTienPhongKQ, BorderLayout.EAST);

		// Doanh thu phòng thường
		JPanel pnlDoanhThuPhongThuong = new JPanel();
		pnlDoanhThuPhongThuong.setBounds(109, 60, 367, 30);
		pnlDoanhThuPhongThuong.setBackground(Color.WHITE);
		pnlDoanhThuPhongThuong.setLayout(new BorderLayout());
		pnlResult.add(pnlDoanhThuPhongThuong);

		JLabel lblDoanhThuPhongThuong = new JLabel("Doanh thu phòng thường: ");
		lblDoanhThuPhongThuong.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		lblDoanhThuPhongThuong.setForeground(new Color(100, 100, 100));
		pnlDoanhThuPhongThuong.add(lblDoanhThuPhongThuong, BorderLayout.WEST);

		lblDoanhThuPhongThuongKQ = new JLabel("");
		lblDoanhThuPhongThuongKQ.setBounds(-1, 0, 299, 28);
		lblDoanhThuPhongThuongKQ.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		lblDoanhThuPhongThuongKQ.setForeground(new Color(100, 100, 100));
		pnlDoanhThuPhongThuong.add(lblDoanhThuPhongThuongKQ, BorderLayout.EAST);

		// Doanh thu phòng VIP
		JPanel pnlDoanhThuPhongVIP = new JPanel();
		pnlDoanhThuPhongVIP.setBounds(109, 90, 367, 30);
		pnlDoanhThuPhongVIP.setBackground(Color.WHITE);
		pnlDoanhThuPhongVIP.setLayout(new BorderLayout());
		pnlResult.add(pnlDoanhThuPhongVIP);

		JLabel lblDoanhThuPhongVIP = new JLabel("Doanh thu phòng VIP: ");
		lblDoanhThuPhongVIP.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		lblDoanhThuPhongVIP.setForeground(new Color(100, 100, 100));
		pnlDoanhThuPhongVIP.add(lblDoanhThuPhongVIP, BorderLayout.WEST);

		lblDoanhThuPhongVIPKQ = new JLabel("");
		lblDoanhThuPhongVIPKQ.setBounds(-1, 0, 299, 28);
		lblDoanhThuPhongVIPKQ.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		lblDoanhThuPhongVIPKQ.setForeground(new Color(100, 100, 100));
		pnlDoanhThuPhongVIP.add(lblDoanhThuPhongVIPKQ, BorderLayout.EAST);

		// Tổng tiền dịch vụ
		JPanel pnlTongTienDV = new JPanel();
		pnlTongTienDV.setBounds(59, 120, 417, 30);
		pnlTongTienDV.setBackground(Color.WHITE);
		pnlTongTienDV.setLayout(new BorderLayout());
		pnlResult.add(pnlTongTienDV);

		JLabel lblTongTienDV = new JLabel("Tổng tiền dịch vụ: ");
		lblTongTienDV.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		lblTongTienDV.setForeground(new Color(100, 100, 100));
		pnlTongTienDV.add(lblTongTienDV, BorderLayout.WEST);

		lblTongTienDVKQ = new JLabel("");
		lblTongTienDVKQ.setBounds(-1, 0, 299, 28);
		lblTongTienDVKQ.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		lblTongTienDVKQ.setForeground(new Color(100, 100, 100));
		pnlTongTienDV.add(lblTongTienDVKQ, BorderLayout.EAST);

		// Tổng số giờ hát
		JPanel pnlTongSoGH = new JPanel();
		pnlTongSoGH.setBounds(59, 150, 417, 30);
		pnlTongSoGH.setBackground(Color.WHITE);
		pnlTongSoGH.setLayout(new BorderLayout());
		pnlResult.add(pnlTongSoGH);

		JLabel lblTongSoGH = new JLabel("Tổng số lưu trú: ");
		lblTongSoGH.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		lblTongSoGH.setForeground(new Color(100, 100, 100));
		pnlTongSoGH.add(lblTongSoGH, BorderLayout.WEST);

		lblTongSoGHKQ = new JLabel("");
		lblTongSoGHKQ.setBounds(-1, 0, 299, 28);
		lblTongSoGHKQ.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		lblTongSoGHKQ.setForeground(new Color(100, 100, 100));
		pnlTongSoGH.add(lblTongSoGHKQ, BorderLayout.EAST);

		// Tổng số hoá đơn
		JPanel pnlTongSoHD = new JPanel();
		pnlTongSoHD.setBounds(0, 185, 476, 30);
		pnlTongSoHD.setBackground(Color.WHITE);
		pnlTongSoHD.setLayout(new BorderLayout());
		pnlResult.add(pnlTongSoHD);

		JLabel lblTongSoHD = new JLabel("Tổng số hoá đơn: ");
		lblTongSoHD.setFont(new Font("Segoe UI", Font.BOLD, 16));
		lblTongSoHD.setForeground(Utils.primaryColor);
		pnlTongSoHD.add(lblTongSoHD, BorderLayout.WEST);

		lblTongSoHDKQ = new JLabel("");
		lblTongSoHDKQ.setBounds(-1, 0, 299, 28);
		lblTongSoHDKQ.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		lblTongSoHDKQ.setForeground(new Color(100, 100, 100));
		pnlTongSoHD.add(lblTongSoHDKQ, BorderLayout.EAST);

		// Doanh thu trung bình
		JPanel pnlDoanhThuTrungBinh = new JPanel();
		pnlDoanhThuTrungBinh.setBounds(0, 220, 476, 30);
		pnlDoanhThuTrungBinh.setBackground(Color.WHITE);
		pnlDoanhThuTrungBinh.setLayout(new BorderLayout());
		pnlResult.add(pnlDoanhThuTrungBinh);

		JLabel lblDoanhThuTrungBinh = new JLabel("Doanh thu trung bình: ");
		lblDoanhThuTrungBinh.setFont(new Font("Segoe UI", Font.BOLD, 16));
		lblDoanhThuTrungBinh.setForeground(Utils.primaryColor);
		pnlDoanhThuTrungBinh.add(lblDoanhThuTrungBinh, BorderLayout.WEST);

		lblDoanhThuTrungBinhKQ = new JLabel("");
		lblDoanhThuTrungBinhKQ.setBounds(-1, 0, 299, 28);
		lblDoanhThuTrungBinhKQ.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		lblDoanhThuTrungBinhKQ.setForeground(new Color(100, 100, 100));
		pnlDoanhThuTrungBinh.add(lblDoanhThuTrungBinhKQ, BorderLayout.EAST);

		// pnlChart
		pnlChart = new PanelRound();
		pnlChart.setBounds(boundsPnlChart);
		pnlChart.setBackground(Color.WHITE);
		pnlChart.setRoundBottomRight(20);
		pnlChart.setRoundTopLeft(20);
		pnlChart.setRoundTopRight(20);
		pnlChart.setRoundBottomLeft(20);
		pnlChart.setLayout(null);
		this.add(pnlChart);

//		jTable
		scr = new JScrollPane();
		scr.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		scr.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scr.setBounds(boundsChart);
		scr.setBackground(Utils.primaryColor);
		scr.getViewport().setBackground(Color.WHITE);

		ScrollBarCustom scp = new ScrollBarCustom();
//		Set color scrollbar thumb
		scp.setScrollbarColor(new Color(203, 203, 203));
		scr.setVerticalScrollBar(scp);
		tbl = new JTable() {
			/**
			 *
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}

		};
		tbl.setAutoCreateRowSorter(true);

		tableModel = new DefaultTableModel(new String[] { "Mã đặt phòng", "Khách hàng", "Tổng tiền", "Nhân viên" }, 0);
		JTableHeader tblHeader = tbl.getTableHeader();

		tbl.setModel(tableModel);
		tbl.setFocusable(false);
		tblHeader.setBackground(Utils.primaryColor);
		tbl.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		tbl.setBackground(Color.WHITE);
		tbl.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tblHeader.setPreferredSize(new Dimension((int) tblHeader.getPreferredSize().getWidth(), 36));
		tblHeader.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		tbl.setRowHeight(36);
		scr.setViewportView(tbl);
		DefaultTableCellRenderer dtcr = new DefaultTableCellRenderer();
		dtcr.setHorizontalAlignment(SwingConstants.RIGHT);
		tbl.getColumnModel().getColumn(2).setCellRenderer(dtcr);

		cmbMonth.setSelectedIndex(LocalDate.now().getMonthValue() - 1);
		cmbYear.setSelectedItem(yearNow + "");
		handleThongKe();
	}

	private int getNumberOfDaysInMonth(int year, int month) {
		YearMonth yearMonthObject = YearMonth.of(year, month);
		int daysInMonth = yearMonthObject.lengthOfMonth();
		return daysInMonth;
	}

	private void handleThongKe() {
		pnlResultContainer.setBounds(boundsPnlResultContainer);

		LocalDate dateNow = LocalDate.now();
		int ngay = 0, thang = 0, nam = 0, countDate = 0, yearNow = dateNow.getYear();

		if (cmbDay.isEnabled())
			ngay = Integer.parseInt(cmbDay.getSelectedItem().toString());
		if (cmbMonth.isEnabled())
			thang = Integer.parseInt(cmbMonth.getSelectedItem().toString());
		nam = Integer.parseInt(cmbYear.getSelectedItem().toString());

		countDate = setTextLblResDate(ngay, thang, nam, dateNow);

		int count = cmbMonth.isEnabled() ? countDate : (nam == yearNow ? dateNow.getMonthValue() : 12);
		int div = count / 3, mod = count % 3, q1 = div + (mod-- > 0 ? 1 : 0), q2 = q1 + div + (mod-- > 0 ? 1 : 0);
		double dsTongTienPhongThuong[] = { 0, 0, 0 };
		double dsTongTienPhongVIP[] = { 0, 0, 0 };
		double dsTongTienDichVu[] = { 0, 0, 0 };
		String temp = (String) cmbNV.getSelectedItem();
		String filterNv = temp.equals(labelNhanVien) ? "" : temp;

		List<ChiTietDatPhong> dsChiTietDatPhong = chiTietDatPhong_DAO.getChiTietDatPhong(ngay, thang, nam, maNhanVien,
				"", filterNv);
		List<ChiTietDichVu> dsChiTietDichVu = chiTietDichVu_DAO.getChiTietDichVu(ngay, thang, nam, maNhanVien, "",
				filterNv);
		Phong phong;
		LocalTime gioVao, gioThuePhong;
		double doanhThuPhongThuong = 0, doanhThuPhongVIP = 0, giaPhong;
		int tongGioHat = 0, tongPhutHat = 0, gio = 0, phut = 0, dayOfMonth = 0, indexOf;
		setMaDonDatPhong = new ArrayList<>();
		dsNhanVien = new ArrayList<>();
		dsKhachHang = new ArrayList<>();
		dsTongTien = new ArrayList<>();
		String maDonDatPhong;
		for (ChiTietDatPhong chiTietDatPhong : dsChiTietDatPhong) {
			phong = chiTietDatPhong.getPhong();
			isPhongVIP = false;

			gioVao = chiTietDatPhong.getGioVao();
			gioThuePhong = chiTietDatPhong.getGioRa().minusHours(gioVao.getHour()).minusMinutes(gioVao.getMinute())
					.minusSeconds(gioVao.getSecond());

			gio = gioThuePhong.getHour();
			phut = gioThuePhong.getMinute();

			tongGioHat += gio;
			tongPhutHat += phut;
			giaPhong = (gio + phut * 1.0 / 60) * phong.getGiaTien();

			if (isPhongVIP)
				doanhThuPhongVIP += giaPhong;
			else
				doanhThuPhongThuong += giaPhong;

			LocalDate ngayNhanPhong = chiTietDatPhong.getDonDatPhong().getNgayNhanPhong();
			if (cmbMonth.isEnabled()) {
				dayOfMonth = ngayNhanPhong.getDayOfMonth();
			} else if (!cmbDay.isEnabled()) {
				dayOfMonth = ngayNhanPhong.getMonthValue();
			}
			if (dayOfMonth <= q1) {
				if (isPhongVIP)
					dsTongTienPhongVIP[0] += giaPhong;
				else
					dsTongTienPhongThuong[0] += giaPhong;
			} else if (dayOfMonth <= q2) {
				if (isPhongVIP)
					dsTongTienPhongVIP[1] += giaPhong;
				else
					dsTongTienPhongThuong[1] += giaPhong;
			} else {
				if (isPhongVIP)
					dsTongTienPhongVIP[2] += giaPhong;
				else
					dsTongTienPhongThuong[2] += giaPhong;
			}
			maDonDatPhong = chiTietDatPhong.getDonDatPhong().getMaDonDatPhong();
			if (setMaDonDatPhong.contains(maDonDatPhong)) {
				indexOf = setMaDonDatPhong.indexOf(maDonDatPhong);
				dsTongTien.set(indexOf, giaPhong + dsTongTien.get(indexOf));
			} else {
				setMaDonDatPhong.add(maDonDatPhong);
				dsTongTien.add(giaPhong);
				dsNhanVien.add(chiTietDatPhong.getDonDatPhong().getNhanVien().getHoTen());
				dsKhachHang.add(chiTietDatPhong.getDonDatPhong().getKhachHang().getHoTen());
			}
		}

		double tongTienDichVu = 0, giaDichVu;
		for (ChiTietDichVu chiTietDichVu : dsChiTietDichVu) {
			giaDichVu = chiTietDichVu.getSoLuong() * chiTietDichVu.getDichVu().getGiaBan();
			tongTienDichVu += giaDichVu;

			LocalDate ngayNhanPhong = chiTietDichVu.getChiTietDatPhong().getDonDatPhong().getNgayNhanPhong();
			if (cmbMonth.isEnabled()) {
				dayOfMonth = ngayNhanPhong.getDayOfMonth();
			} else if (!cmbDay.isEnabled())
				dayOfMonth = ngayNhanPhong.getMonthValue();
			if (dayOfMonth <= q1) {
				dsTongTienDichVu[0] += giaDichVu;
			} else if (dayOfMonth <= q2) {
				dsTongTienDichVu[1] += giaDichVu;
			} else
				dsTongTienDichVu[2] += giaDichVu;
			maDonDatPhong = chiTietDichVu.getChiTietDatPhong().getDonDatPhong().getMaDonDatPhong();
			indexOf = setMaDonDatPhong.indexOf(maDonDatPhong);
			dsTongTien.set(indexOf, giaDichVu + dsTongTien.get(indexOf));
		}

		tongGioHat += tongPhutHat / 60;
		tongPhutHat %= 60;
		double tongTienPhong = doanhThuPhongThuong, tongDoanhThu = 0;
		tongTienPhong += doanhThuPhongVIP;
		tongDoanhThu = tongTienDichVu + tongTienPhong;
		lblDoanhThuPhongVIPKQ.setText(Utils.formatTienTe(Math.round(doanhThuPhongVIP)));
		lblDoanhThuPhongThuongKQ.setText(Utils.formatTienTe(Math.round(doanhThuPhongThuong)));
		lblTongTienPhongKQ.setText(Utils.formatTienTe(Math.round(tongTienPhong)));
		String lblSoGio = ((tongGioHat > 0 ? tongGioHat + " giờ" : "")
				+ (tongPhutHat > 0 ? " " + tongPhutHat + " phút" : "")).trim();
		lblTongSoGHKQ.setText(lblSoGio.length() <= 0 ? "0 giờ" : lblSoGio);
		lblTongSoHDKQ.setText(setMaDonDatPhong.size() + "");
		lblTongTienDVKQ.setText(Utils.formatTienTe(Math.round(tongTienDichVu)));
		lblTongDoanhThuKQ.setText(Utils.formatTienTe(Math.round(tongDoanhThu)));
		lblDoanhThuTrungBinhKQ
				.setText(Utils.formatTienTe(Math.round(countDate > 0 ? tongDoanhThu / countDate : tongDoanhThu)));
		pnlChart.setBounds(boundsPnlChart);
		pnlChart.removeAll();

		if (ngay == 0) {
			chart = new Chart();
			chart.addLegend("Phòng thường", new Color(238, 255, 65));
			chart.addLegend("Phòng VIP", new Color(24, 255, 255));
			chart.addLegend("Dịch vụ", new Color(105, 240, 174));
			int dsMoc[] = { 0, q1, q2, count };
			String monthYear, labelDay;
			if (thang == 0) {
				for (int i = 0; i < Math.min(countDate, 3); i++) {
					monthYear = dsMoc[i] + 1 == dsMoc[i + 1] ? Utils.convertIntToString(dsMoc[i] + 1) + "/"
							: String.format("%s-%s/", Utils.convertIntToString(dsMoc[i] + 1),
									Utils.convertIntToString(dsMoc[i + 1])) + nam;
					chart.addData(new ModelChart(monthYear,
							new double[] { dsTongTienPhongThuong[i], dsTongTienPhongVIP[i], dsTongTienDichVu[i] }));
				}
			} else {
				monthYear = Utils.convertIntToString(thang) + "/" + nam;
				for (int i = 0; i < Math.min(countDate, 3); i++) {
					labelDay = dsMoc[i] + 1 == dsMoc[i + 1] ? Utils.convertIntToString(dsMoc[i] + 1) + "/"
							: String.format("%s-%s/", Utils.convertIntToString(dsMoc[i] + 1),
									Utils.convertIntToString(dsMoc[i + 1]));
					labelDay += monthYear;
					chart.addData(new ModelChart(labelDay,
							new double[] { dsTongTienPhongThuong[i], dsTongTienPhongVIP[i], dsTongTienDichVu[i] }));
				}
			}
			chart.setBounds(boundsChart);
			pnlChart.add(chart);
			chart.start();
		} else
			thongKeDoanhThuTheoNgay();
	}

	private void setDaysToCmb() {
		if (!cmbDay.isEnabled())
			return;

		int month = Integer.parseInt(cmbMonth.getSelectedItem().toString());
		int year = Integer.parseInt(cmbYear.getSelectedItem().toString());
		int daysOfMonth = getNumberOfDaysInMonth(year, month);
		LocalDate dateNow = LocalDate.now();

		if (month == dateNow.getMonthValue() && year == dateNow.getYear())
			daysOfMonth = dateNow.getDayOfMonth();

		cmbDay.removeAllItems();
		for (int i = 1; i <= daysOfMonth; ++i)
			if (i < 10)
				cmbDay.addItem("0" + i);
			else
				cmbDay.addItem(i + "");
		cmbDay.setSelectedIndex(0);
	}

	private int setTextLblResDate(int ngay, int thang, int nam, LocalDate dateNow) {
		int yearNow = dateNow.getYear(), countDate = 0;
		if (!cmbMonth.isEnabled()) {
			Calendar cal = Calendar.getInstance();
			cal.setTime(new Date(nam));

			if (nam == yearNow)
				countDate = dateNow.getDayOfYear();
			else
				countDate = cal.getActualMaximum(Calendar.DAY_OF_YEAR);

			lblResDate.setText(nam + "");
		} else if (cmbDay.isEnabled()) {
			lblResDate.setText(Utils.convertIntToString(ngay) + "/" + Utils.convertIntToString(thang) + "/" + nam);
		} else {
			if (nam == yearNow && thang == dateNow.getMonthValue())
				countDate = dateNow.getDayOfMonth();
			else
				countDate = getNumberOfDaysInMonth(nam, thang);

			lblResDate.setText(Utils.convertIntToString(thang) + "/" + nam);
		}
		return countDate;
	}

	private void thongKeDoanhThuTheoNgay() {
		tableModel.setRowCount(0);

		for (int i = 0; i < setMaDonDatPhong.size(); i++)
			tableModel.addRow(new String[] { setMaDonDatPhong.get(i), dsKhachHang.get(i),
					Utils.formatTienTe(dsTongTien.get(i)), dsNhanVien.get(i) });

		pnlChart.add(scr);
	}
}
