package ui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;

import components.button.Button;
import components.panelRound.PanelRound;
import components.scrollbarCustom.ScrollBarCustom;
import dao.ChiTietDatPhong_DAO;
import dao.ChiTietDichVu_DAO;
import dao.KhachHang_DAO;
import dao.Phong_DAO;
import entity.ChiTietDatPhong;
import entity.ChiTietDichVu;
import entity.KhachHang;
import entity.Phong;
import utils.NhanVien;
import utils.Utils;

public class ThongKeKhachHang_GUI extends JPanel {
	private static JLabel lblTime;
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static Thread clock() {
		Thread clock = new Thread() {
			@Override
			public void run() {
				for (;;) {
					try {
						LocalDateTime currTime = LocalDateTime.now();
						int day = currTime.getDayOfMonth();
						int month = currTime.getMonthValue();
						int year = currTime.getYear();
						int hour = currTime.getHour();
						int minute = currTime.getMinute();
						int second = currTime.getSecond();
						lblTime.setText(String.format("%s/%s/%s | %s:%s:%s", day < 10 ? "0" + day : day,
								month < 10 ? "0" + month : month, year, hour < 10 ? "0" + hour : hour,
								minute < 10 ? "0" + minute : minute, second < 10 ? "0" + second : second));
						sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		};

		clock.start();

		return clock;
	}

	private Button btnDay;
	private Button btnMonth;
	private Button btnYear;
	private ChiTietDatPhong_DAO chiTietDatPhong_DAO;
	private ChiTietDichVu_DAO chiTietDichVu_DAO;
	private JComboBox<String> cmbDay;
	private JComboBox<String> cmbMonth;
	private JComboBox<String> cmbYear;
	private KhachHang_DAO khachHang_DAO;
	private String maNhanVien;
	private DefaultTableModel tableModel;
	private JTable tblKhachHang;
	private List<String> dsLoaiPhong;

	/**
	 * Create the frame.
	 */
	public ThongKeKhachHang_GUI(Main main) {
		khachHang_DAO = new KhachHang_DAO();
		chiTietDatPhong_DAO = new ChiTietDatPhong_DAO();
		chiTietDichVu_DAO = new ChiTietDichVu_DAO();

		dsLoaiPhong = new Phong_DAO().getAllLoaiPhong();

		entity.NhanVien nhanVien = NhanVien.getNhanVien();
		maNhanVien = nhanVien.getChucVu().equals(entity.NhanVien.ChucVu.QuanLy) ? "" : nhanVien.getMaNhanVien();

		setBackground(new Color(242, 246, 252));
		setBounds(0, 0, Utils.getScreenWidth(), Utils.getBodyHeight());
		setLayout(null);

		PanelRound pnlContainerAction = new PanelRound();
		pnlContainerAction.setBackground(Color.WHITE);
		pnlContainerAction.setBounds(Utils.getLeft(1052), 20, 1052, 125);
		pnlContainerAction.setRoundBottomRight(20);
		pnlContainerAction.setRoundTopLeft(20);
		pnlContainerAction.setRoundTopRight(20);
		pnlContainerAction.setRoundBottomLeft(20);
		this.add(pnlContainerAction);
		pnlContainerAction.setLayout(null);

		lblTime = new JLabel("");
		lblTime.setHorizontalAlignment(SwingConstants.LEFT);
		lblTime.setFont(new Font("Segoe UI", Font.PLAIN, 18));
		lblTime.setBounds(1110, 0, 180, 24);
		this.add(lblTime);
		clock();

//		Tìm kiếm khách hàng theo tên và số điện thoại
		JLabel lblTimKiemKH = new JLabel("Tìm kiếm khách hàng theo:");
		lblTimKiemKH.setBounds(36, 15, 242, 35);
		lblTimKiemKH.setFont(new Font("Segoe UI", Font.BOLD, 18));
		lblTimKiemKH.setForeground(new Color(100, 100, 100));
		pnlContainerAction.add(lblTimKiemKH);

//		Button tìm kiếm theo ngày tháng năm
		JPanel pnlRow2 = new JPanel();
		pnlRow2.setBackground(Color.WHITE);
		pnlRow2.setBounds(278, 15, 738, 35);
		pnlContainerAction.add(pnlRow2);
		pnlRow2.setLayout(null);

		btnDay = new Button("Ngày");
		btnDay.setFocusable(false);
		btnDay.setForeground(new Color(100, 100, 100));
		btnDay.setColor(new Color(242, 246, 252));
		btnDay.setFont(new Font("Segoe UI", Font.BOLD, 16));
		btnDay.setBounds(236, 0, 118, 35);
		btnDay.setBorder(new EmptyBorder(0, 0, 0, 0));
		btnDay.setBorderColor(new Color(242, 246, 252));
		btnDay.setColorOver(new Color(242, 246, 252));
		btnDay.setColorClick(Utils.primaryColor);
		btnDay.setRadius(10);
		pnlRow2.add(btnDay);

		btnDay.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

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
		btnMonth.setBounds(428, 0, 118, 35);
		btnMonth.setBorder(new EmptyBorder(0, 0, 0, 0));
		btnMonth.setBorderColor(Utils.primaryColor);
		btnMonth.setColorOver(Utils.primaryColor);
		btnMonth.setColorClick(Utils.primaryColor);
		btnMonth.setRadius(10);
		pnlRow2.add(btnMonth);

		btnMonth.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
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
		btnYear.setBounds(620, 0, 118, 35);
		btnYear.setBorder(new EmptyBorder(0, 0, 0, 0));
		btnYear.setBorderColor(new Color(242, 246, 252));
		btnYear.setColorOver(Utils.primaryColor);
		btnYear.setColorClick(Utils.primaryColor);
		btnYear.setRadius(10);
		pnlRow2.add(btnYear);

		btnYear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

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

//		Tìm kiếm khách hàng theo ngày, tháng, năm khách hàng thuê
		JPanel pnlRow3 = new JPanel();
		pnlRow3.setBackground(Color.WHITE);
		pnlRow3.setBounds(36, 65, 980, 45);
		pnlContainerAction.add(pnlRow3);
		pnlRow3.setLayout(null);

		JLabel lblDay = new JLabel("Ngày: ");
		lblDay.setForeground(new Color(100, 100, 100));
		lblDay.setBounds(0, 8, 70, 28);
		lblDay.setFont(new Font("Segoe UI", Font.BOLD, 16));
		pnlRow3.add(lblDay);

		cmbDay = new JComboBox<String>();
		cmbDay.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		cmbDay.setBackground(Utils.secondaryColor);
		cmbDay.setBounds(70, 2, 100, 40);
		cmbDay.setEnabled(false);
		pnlRow3.add(cmbDay);

		JLabel lblMonth = new JLabel("Tháng: ");
		lblMonth.setForeground(new Color(100, 100, 100));
		lblMonth.setBounds(269, 8, 70, 28);
		lblMonth.setFont(new Font("Segoe UI", Font.BOLD, 16));
		pnlRow3.add(lblMonth);

		cmbMonth = new JComboBox<String>();
		cmbMonth.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		cmbMonth.setBackground(Utils.secondaryColor);
		cmbMonth.setAlignmentX(CENTER_ALIGNMENT);
		cmbMonth.setBounds(339, 2, 100, 40);
		cmbMonth.setSelectedItem("11");
		cmbMonth.addItemListener(e -> setDaysToCmb());
		pnlRow3.add(cmbMonth);

		for (int i = 1; i < 13; i++)
			cmbMonth.addItem(Utils.convertIntToString(i));

		JLabel lblYear = new JLabel("Năm: ");
		lblYear.setForeground(new Color(100, 100, 100));
		lblYear.setBounds(538, 8, 70, 28);
		lblYear.setFont(new Font("Segoe UI", Font.BOLD, 16));
		pnlRow3.add(lblYear);

		cmbYear = new JComboBox<String>();
		cmbYear.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		cmbYear.setBackground(Utils.secondaryColor);
		cmbYear.setAlignmentX(CENTER_ALIGNMENT);
		cmbYear.setBounds(608, 2, 100, 40);
		cmbYear.addItemListener(e -> setDaysToCmb());
		pnlRow3.add(cmbYear);

		int yearNow = LocalDate.now().getYear();
		for (int i = 2015; i <= yearNow; ++i)
			cmbYear.addItem(i + "");

		Button btnTimKiem = new Button("Tìm");
		btnTimKiem.setFocusable(false);
		btnTimKiem.setForeground(Color.WHITE);
		btnTimKiem.setColor(Utils.primaryColor);
		btnTimKiem.setBorderColor(Utils.primaryColor);
		btnTimKiem.setRadius(10);
		btnTimKiem.setFont(new Font("Segoe UI", Font.BOLD, 16));
		btnTimKiem.setBounds(820, 0, 160, 44);
		btnTimKiem.setColorOver(Utils.primaryColor);
		btnTimKiem.setColorTextOver(Color.WHITE);
		btnTimKiem.setColorTextOut(Color.WHITE);
		btnTimKiem.setColorClick(Utils.primaryColor);
		btnTimKiem.setBorder(new EmptyBorder(0, 0, 0, 0));
		btnTimKiem.setIcon(Utils.getImageIcon("statistics.png"));
		pnlRow3.add(btnTimKiem);

//		Table
		PanelRound pnlTable = new PanelRound();
		pnlTable.setBackground(Color.WHITE);
		pnlTable.setBounds(Utils.getLeft(1052), 165, 1052, Utils.getBodyHeight() - 225);
		pnlTable.setRoundBottomRight(20);
		pnlTable.setRoundTopLeft(20);
		pnlTable.setRoundTopRight(20);
		pnlTable.setRoundBottomLeft(20);
		this.add(pnlTable);
		pnlTable.setLayout(null);

		JScrollPane scr = new JScrollPane();
		scr.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		scr.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scr.setBounds(0, 0, 1052, 450);
		scr.setBackground(Utils.primaryColor);
		ScrollBarCustom scp = new ScrollBarCustom();
		scp.setScrollbarColor(new Color(203, 203, 203));
		scr.setVerticalScrollBar(scp);
		pnlTable.add(scr);

		tblKhachHang = new JTable() {
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}

			@Override
			public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
				Component c = super.prepareRenderer(renderer, row, column);
				if (isRowSelected(row))
					c.setBackground(Utils.getOpacity(Utils.primaryColor, 0.5f));
				else if (row % 2 == 0)
					c.setBackground(Color.WHITE);
				else
					c.setBackground(new Color(232, 232, 232));
				return c;
			}
		};

		TableColumnModel tableColumnModel = tblKhachHang.getColumnModel();
		JTableHeader jTableHeader = tblKhachHang.getTableHeader();
		tableModel = new DefaultTableModel(new String[] { "Mã khách hàng", "Họ tên", "SĐT", "Giới tính", "Tổng tiền" },
				0);
		tblKhachHang.setModel(tableModel);
		jTableHeader.setBackground(Utils.primaryColor);
		tblKhachHang.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		jTableHeader.setForeground(Color.WHITE);
		jTableHeader.setPreferredSize(new Dimension((int) jTableHeader.getPreferredSize().getWidth(), 36));
		tblKhachHang.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 16));
		tblKhachHang.setRowHeight(36);
		DefaultTableCellRenderer rightRender = new DefaultTableCellRenderer();
		rightRender.setHorizontalAlignment(JLabel.RIGHT);
		tableColumnModel.getColumn(4).setCellRenderer(rightRender);
		scr.setViewportView(tblKhachHang);

		addAncestorListener(new AncestorListener() {
			Thread clockThread;

			public void ancestorAdded(AncestorEvent event) {
				clockThread = clock();

				cmbMonth.setSelectedIndex(LocalDate.now().getMonthValue() - 1);
				cmbYear.setSelectedItem(yearNow + "");
				filterThongKe();
			}

			public void ancestorMoved(AncestorEvent event) {
			}

			@SuppressWarnings("deprecation")
			public void ancestorRemoved(AncestorEvent event) {
				clockThread.stop();
			}
		});

		btnTimKiem.addMouseListener(new MouseAdapter() {
			public void mouseClicked(java.awt.event.MouseEvent e) {
				filterThongKe();
			};
		});
	}

	private void addRow(KhachHang khachHang, double tongTien) {
		khachHang = khachHang_DAO.getKhachHangTheoMa(khachHang.getMaKhachHang());
		tableModel.addRow(new String[] { khachHang.getMaKhachHang(), khachHang.getHoTen(), khachHang.getSoDienThoai(),
				khachHang.isGioiTinh() ? "Nam" : "Nữ", Utils.formatTienTe(tongTien) });
	}

	private void filterThongKe() {
		String maKhachHang = "", maKH = "";
		int ngay = 0, thang = 0, nam = 0;

		if (cmbDay.isEnabled())
			ngay = Integer.parseInt(cmbDay.getSelectedItem().toString());
		if (cmbMonth.isEnabled())
			thang = Integer.parseInt(cmbMonth.getSelectedItem().toString());
		nam = Integer.parseInt(cmbYear.getSelectedItem().toString());

		List<ChiTietDatPhong> dsChiTietDatPhong = chiTietDatPhong_DAO.getChiTietDatPhong(ngay, thang, nam, maNhanVien,
				maKhachHang);
		List<ChiTietDichVu> dsChiTietDichVu = chiTietDichVu_DAO.getChiTietDichVu(ngay, thang, nam, maNhanVien,
				maKhachHang);

		Phong phong;
		LocalTime gioVao, gioThuePhong;
		double giaPhong;
		int gio = 0, phut = 0, index;
		List<String> dsKhachHang = new ArrayList<>();
		List<Double> dsTongTien = new ArrayList<>();

		for (ChiTietDatPhong chiTietDatPhong : dsChiTietDatPhong) {
			phong = chiTietDatPhong.getPhong();

			gioVao = chiTietDatPhong.getGioVao();
			gioThuePhong = chiTietDatPhong.getGioRa().minusHours(gioVao.getHour()).minusMinutes(gioVao.getMinute())
					.minusSeconds(gioVao.getSecond());

			gio = gioThuePhong.getHour();
			phut = gioThuePhong.getMinute();
			giaPhong = (gio + phut * 1.0 / 60) * phong.getGiaTien();

			maKH = chiTietDatPhong.getDonDatPhong().getKhachHang().getMaKhachHang();
			if (dsKhachHang.contains(maKH)) {
				index = dsKhachHang.indexOf(maKH);
				dsTongTien.set(index, dsTongTien.get(index) + giaPhong);
			} else {
				dsKhachHang.add(maKH);
				dsTongTien.add(giaPhong);
			}
		}

		for (ChiTietDichVu chiTietDichVu : dsChiTietDichVu) {
			giaPhong = chiTietDichVu.getDichVu().getGiaBan() * chiTietDichVu.getSoLuong();
			maKH = chiTietDichVu.getChiTietDatPhong().getDonDatPhong().getKhachHang().getMaKhachHang();
			index = dsKhachHang.indexOf(maKH);
			dsTongTien.set(index, dsTongTien.get(index) + giaPhong);
		}

		tableModel.setRowCount(0);
		for (int i = 0; i < dsKhachHang.size(); i++) {
			addRow(new KhachHang(dsKhachHang.get(i)), dsTongTien.get(i));
		}
	}

	private int getNumberOfDaysInMonth(int year, int month) {
		YearMonth yearMonthObject = YearMonth.of(year, month);
		int daysInMonth = yearMonthObject.lengthOfMonth();
		return daysInMonth;
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
			cmbDay.addItem(Utils.convertIntToString(i));
		cmbDay.setSelectedIndex(0);
	}
}
