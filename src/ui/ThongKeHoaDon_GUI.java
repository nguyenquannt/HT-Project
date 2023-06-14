package ui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalTime;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import components.button.Button;
import components.jDialog.JDialogCustom;
import components.panelRound.PanelRound;
import components.scrollbarCustom.ScrollBarCustom;
import dao.ChiTietDatPhong_DAO;
import dao.ChiTietDichVu_DAO;
import dao.DichVu_DAO;
import dao.DonDatPhong_DAO;
import dao.KhachHang_DAO;
import dao.NhanVien_DAO;
import dao.Phong_DAO;
import entity.ChiTietDatPhong;
import entity.ChiTietDichVu;
import entity.DonDatPhong;
import entity.Phong;
import utils.Utils;

public class ThongKeHoaDon_GUI extends JPanel implements ItemListener {

	private static final long serialVersionUID = 1L;
	private ChiTietDatPhong_DAO chiTietDatPhong_DAO;
	private ChiTietDichVu_DAO chiTietDichVu_DAO;
	private DichVu_DAO dichVu_DAO;
	private DonDatPhong_DAO donDatPhong_DAO;
	private String gioVaoPhong, gioRaPhong, thoiGianSuDung;
	private KhachHang_DAO khachHang_DAO;
	private Main main;
	private NhanVien_DAO nhanVien_DAO;
	private Phong_DAO phong_DAO;
	private DefaultTableModel tableModel;
	private JTable tblThongKe;
	private double tienPhong, tienDichVu, tongTienPhong = 0;
	private JTextField txtMaHD, txtTenKhach, txtTenNhanVien, txtNgayLap;

	public ThongKeHoaDon_GUI(Main jFrame) {
		chiTietDatPhong_DAO = new ChiTietDatPhong_DAO();
		chiTietDichVu_DAO = new ChiTietDichVu_DAO();
		donDatPhong_DAO = new DonDatPhong_DAO();
		khachHang_DAO = new KhachHang_DAO();
		nhanVien_DAO = new NhanVien_DAO();
		phong_DAO = new Phong_DAO();
		dichVu_DAO = new DichVu_DAO();
		chiTietDatPhong_DAO = new ChiTietDatPhong_DAO();
		chiTietDichVu_DAO = new ChiTietDichVu_DAO();
		donDatPhong_DAO = new DonDatPhong_DAO();
		khachHang_DAO = new KhachHang_DAO();
		nhanVien_DAO = new NhanVien_DAO();
		phong_DAO = new Phong_DAO();
		dichVu_DAO = new DichVu_DAO();
		main = jFrame;
		setBackground(new Color(242, 246, 252));
		setBounds(0, 0, Utils.getScreenWidth(), Utils.getBodyHeight());
		setLayout(null);

		PanelRound pnlContainerAction = new PanelRound();
		pnlContainerAction.setBackground(Color.WHITE);
		pnlContainerAction.setBounds(90, 20, Utils.getScreenWidth() - 195, 180);
		pnlContainerAction.setRoundBottomRight(20);
		pnlContainerAction.setRoundTopLeft(20);
		pnlContainerAction.setRoundTopRight(20);
		pnlContainerAction.setRoundBottomLeft(20);
		this.add(pnlContainerAction);
		pnlContainerAction.setLayout(null);

		JLabel lblTimKiemHD = new JLabel("Tìm kiếm hoá đơn theo:");
		lblTimKiemHD.setBounds(20, 15, 299, 28);
		lblTimKiemHD.setFont(new Font("Segoe UI", Font.BOLD, 18));
		lblTimKiemHD.setForeground(new Color(100, 100, 100));
		pnlContainerAction.add(lblTimKiemHD);

		JPanel pnlTop = new JPanel();
		pnlTop.setBackground(Color.WHITE);
		pnlTop.setBounds(20, 60, Utils.getScreenWidth() - 90, 30);
		pnlContainerAction.add(pnlTop);
		pnlTop.setLayout(null);

		JLabel lblTenKhach = new JLabel("Tên khách: ");
		lblTenKhach.setBounds(10, 1, 90, 28);
		lblTenKhach.setFont(new Font("Segoe UI", Font.BOLD, 16));
		lblTenKhach.setForeground(new Color(100, 100, 100));
		pnlTop.add(lblTenKhach);

		txtTenKhach = new JTextField("");
		txtTenKhach.setText("");
		txtTenKhach.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		txtTenKhach.setBounds(115, 0, 250, 30);
		txtTenKhach.setBorder(new LineBorder(Utils.primaryColor, 1));
		pnlTop.add(txtTenKhach);
		txtTenKhach.setColumns(10);

		JLabel lblTenNhanVien = new JLabel("Tên nhân viên: ");
		lblTenNhanVien.setBounds(410, 1, 130, 28);
		lblTenNhanVien.setFont(new Font("Segoe UI", Font.BOLD, 16));
		lblTenNhanVien.setForeground(new Color(100, 100, 100));
		pnlTop.add(lblTenNhanVien);

		txtTenNhanVien = new JTextField("");
		txtTenNhanVien.setText("");
		txtTenNhanVien.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		txtTenNhanVien.setBounds(530, 0, 280, 30);
		txtTenNhanVien.setBorder(new LineBorder(Utils.primaryColor, 1));
		pnlTop.add(txtTenNhanVien);
		txtTenNhanVien.setColumns(10);

		JPanel pnlBottom = new JPanel();
		pnlBottom.setBackground(Color.WHITE);
		pnlBottom.setBounds(20, 100, Utils.getScreenWidth() - 90, 45);
		pnlContainerAction.add(pnlBottom);
		pnlBottom.setLayout(null);

		JLabel lblMaHD = new JLabel("Mã hoá đơn: ");
		lblMaHD.setBounds(10, 12, 130, 28);
		lblMaHD.setFont(new Font("Segoe UI", Font.BOLD, 16));
		lblMaHD.setForeground(new Color(100, 100, 100));
		pnlBottom.add(lblMaHD);

		txtMaHD = new JTextField("");
		txtMaHD.setText("");
		txtMaHD.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		txtMaHD.setBounds(115, 14, 250, 30);
		txtMaHD.setBorder(new LineBorder(Utils.primaryColor, 1));
		pnlBottom.add(txtMaHD);
		txtMaHD.setColumns(10);

		JLabel lblNgayLap = new JLabel("Ngày lập: ");
		lblNgayLap.setBounds(410, 12, 130, 28);
		lblNgayLap.setFont(new Font("Segoe UI", Font.BOLD, 16));
		lblNgayLap.setForeground(new Color(100, 100, 100));
		pnlBottom.add(lblNgayLap);

		txtNgayLap = new JTextField();
		txtNgayLap.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		txtNgayLap.setBounds(530, 14, 280, 30);
		txtNgayLap.setBorder(new LineBorder(Utils.primaryColor, 1));
		pnlBottom.add(txtNgayLap);
		txtNgayLap.setColumns(10);

		Button btnTimKiem = new Button("Tìm");
		btnTimKiem.setFocusable(false);
		btnTimKiem.setForeground(Color.WHITE);
		btnTimKiem.setColor(Utils.primaryColor);
		btnTimKiem.setBorderColor(Utils.primaryColor);
		btnTimKiem.setRadius(10);
		btnTimKiem.setFont(new Font("Segoe UI", Font.BOLD, 16));
		btnTimKiem.setBounds(860, 0, 160, 44);
		btnTimKiem.setColorOver(Utils.primaryColor);
		btnTimKiem.setColorTextOver(Color.WHITE);
		btnTimKiem.setColorTextOut(Color.WHITE);
		btnTimKiem.setColorClick(Utils.primaryColor);
		btnTimKiem.setBorder(new EmptyBorder(0, 0, 0, 0));
		btnTimKiem.setIcon(new ImageIcon("Icon\\searchIcon.png"));
		pnlBottom.add(btnTimKiem);

		PanelRound pnlTable = new PanelRound();
		pnlTable.setBackground(Color.WHITE);
		pnlTable.setBounds(90, 210, Utils.getScreenWidth() - 195, 320);
		pnlTable.setRoundBottomRight(20);
		pnlTable.setRoundTopLeft(20);
		pnlTable.setRoundTopRight(20);
		pnlTable.setRoundBottomLeft(20);
		this.add(pnlTable);
		pnlTable.setLayout(null);

		JScrollPane scr = new JScrollPane();
		scr.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scr.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scr.setBounds(10, 10, Utils.getScreenWidth() - 215, 300);
		scr.setBackground(Utils.primaryColor);
		ScrollBarCustom scp = new ScrollBarCustom();
		scp.setScrollbarColor(new Color(203, 203, 203));
		scr.setVerticalScrollBar(scp);
		pnlTable.add(scr);

		tblThongKe = new JTable() {
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

		tableModel = new DefaultTableModel(new String[] { "Mã Hoá Đơn", "Phòng", "Tên Khách", "Tên Nhân Viên",
				"Ngày Lập", "Tiền Phòng", "Tiền Dịch Vụ", "Tổng Tiền" }, 0);
		tblThongKe.setModel(tableModel);
		tblThongKe.getColumnModel().getColumn(0).setPreferredWidth((Utils.getScreenWidth() - 215) / 8 - 40);
		tblThongKe.getColumnModel().getColumn(1).setPreferredWidth((Utils.getScreenWidth() - 215) / 8);
		tblThongKe.getColumnModel().getColumn(2).setPreferredWidth((Utils.getScreenWidth() - 215) / 8 + 30);
		tblThongKe.getColumnModel().getColumn(3).setPreferredWidth((Utils.getScreenWidth() - 215) / 8 + 30);
		tblThongKe.getColumnModel().getColumn(4).setPreferredWidth((Utils.getScreenWidth() - 215) / 8 - 20);
		tblThongKe.getColumnModel().getColumn(5).setPreferredWidth((Utils.getScreenWidth() - 215) / 8);
		tblThongKe.getColumnModel().getColumn(6).setPreferredWidth((Utils.getScreenWidth() - 215) / 8);
		tblThongKe.getColumnModel().getColumn(7).setPreferredWidth((Utils.getScreenWidth() - 215) / 8 - 8);

		tblThongKe.getTableHeader().setBackground(Utils.primaryColor);
		tblThongKe.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		tblThongKe.getTableHeader().setForeground(Color.WHITE);
		tblThongKe.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		tblThongKe.getTableHeader()
				.setPreferredSize(new Dimension((int) tblThongKe.getTableHeader().getPreferredSize().getWidth(), 36));
		tblThongKe.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 16));
		tblThongKe.setRowHeight(36);
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment(JLabel.CENTER);
		tblThongKe.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
		scr.setViewportView(tblThongKe);

		DefaultTableCellRenderer dtcr = new DefaultTableCellRenderer();
		dtcr.setHorizontalAlignment(SwingConstants.RIGHT);
		tblThongKe.getColumnModel().getColumn(4).setCellRenderer(dtcr);
		tblThongKe.getColumnModel().getColumn(5).setCellRenderer(dtcr);
		tblThongKe.getColumnModel().getColumn(6).setCellRenderer(dtcr);
		tblThongKe.getColumnModel().getColumn(7).setCellRenderer(dtcr);

		addRowRandomData();

		Button btnXemChiTiet = new Button("Xem chi tiết");
		btnXemChiTiet.setFocusable(false);
		btnXemChiTiet.setForeground(Color.WHITE);
		btnXemChiTiet.setColor(new Color(140, 177, 180, 70));
		btnXemChiTiet.setBorderColor(new Color(193, 214, 217));
		btnXemChiTiet.setRadius(10);
		btnXemChiTiet.setFont(new Font("Segoe UI", Font.BOLD, 16));
		btnXemChiTiet.setBounds(986, 550, 160, 44);
		btnXemChiTiet.setColorOver(new Color(140, 177, 180, 70));
		btnXemChiTiet.setColorTextOver(Color.WHITE);
		btnXemChiTiet.setColorTextOut(Color.WHITE);
		btnXemChiTiet.setColorClick(new Color(140, 177, 180, 70));
		btnXemChiTiet.setBorder(new EmptyBorder(0, 0, 0, 0));
		this.add(btnXemChiTiet);

		tblThongKe.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent lse) {
				if (!lse.getValueIsAdjusting()) {
					btnXemChiTiet.setEnabled(true);
					btnXemChiTiet.setColor(Utils.primaryColor);
					btnXemChiTiet.setBorderColor(Utils.primaryColor);
					btnXemChiTiet.setColorOver(Utils.primaryColor);
					btnXemChiTiet.setColorClick(Utils.primaryColor);

				}
			}
		});

		btnXemChiTiet.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (!btnXemChiTiet.isEnabled())
					return;
				int row = tblThongKe.getSelectedRow();
				jFrame.setVisible(true);
				if (row == -1) {
					new JDialogCustom(main, components.jDialog.JDialogCustom.Type.warning).showMessage("Warning",
							"Vui lòng chọn hoá đơn muốn xem chi tiết!");
				} else {
					LocalTime timeNow = LocalTime.now();
					String maHoaDon = (String) tableModel.getValueAt(row, 0);
					String maDonDatPhong = maHoaDon.replace("HD", "MDP");
					String ngayLap = (String) tableModel.getValueAt(row, 4);
					String tenKhach = (String) tableModel.getValueAt(row, 2);
					String tenNV = (String) tableModel.getValueAt(row, 3);
					int hours, minutes, hieu = 0;
					double tongTienPhong = 0, tongTienDichVu = 0;
					Phong phong;
					DonDatPhong donDatPhongChon = donDatPhong_DAO.getDatPhong(maDonDatPhong);
					List<ChiTietDatPhong> dsChiTietDatPhong = chiTietDatPhong_DAO
							.getAllChiTietDatPhong(donDatPhong_DAO.getDatPhong(maDonDatPhong));
					List<ChiTietDichVu> dsChiTietDichVu = chiTietDichVu_DAO.getDichVuTheoPhieuDatPhong(maDonDatPhong);

					List<ChiTietDatPhong> dsChiTietDatPhongThanhToan = chiTietDatPhong_DAO
							.getAllChiTietDatPhongThanhToan();
					for (ChiTietDatPhong chiTietDatPhong : dsChiTietDatPhongThanhToan) {
						if (chiTietDatPhong.getDonDatPhong().getMaDonDatPhong()
								.equals(donDatPhongChon.getMaDonDatPhong())) {
							phong = phong_DAO.getPhong(chiTietDatPhong.getPhong().getMaPhong());
							LocalTime gioRa = chiTietDatPhong.getGioRa() == null ? timeNow : chiTietDatPhong.getGioRa();
							LocalTime gioVao = chiTietDatPhong.getGioVao();
							hours = gioRa.getHour() - gioVao.getHour();
							minutes = gioRa.getMinute() - gioVao.getMinute();
							hieu = hours * 60 + minutes;
							tienPhong = hieu;
							tienPhong *= phong.getGiaTien();
							tienPhong *= 1.0 / 60;
							tongTienPhong += tienPhong;

						}
					}

					for (ChiTietDichVu chiTietDichVu : dsChiTietDichVu) {
						if (chiTietDichVu.getChiTietDatPhong().getDonDatPhong().getMaDonDatPhong()
								.equals(maDonDatPhong)) {
							tongTienDichVu += dichVu_DAO.getDichVuTheoMa(chiTietDichVu.getDichVu().getMaDichVu())
									.getGiaBan() * chiTietDichVu.getSoLuong();
						}
					}

					HoaDon_GUI jFrame = new HoaDon_GUI(maHoaDon, ngayLap, tenKhach, tenNV, gioVaoPhong, gioRaPhong,
							thoiGianSuDung, tongTienPhong + "", tongTienDichVu + "", dsChiTietDatPhong,
							dsChiTietDichVu);
					jFrame.setVisible(true);

				}
			}
		});

		btnTimKiem.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(tableModel);
				tblThongKe.setRowSorter(sorter);
				if (txtTenKhach.getText().trim().length() != 0) {
					sorter.setRowFilter(RowFilter.regexFilter(txtTenKhach.getText()));
				} else if (txtMaHD.getText().trim().length() != 0) {
					sorter.setRowFilter(RowFilter.regexFilter(txtMaHD.getText()));
				} else if (txtTenNhanVien.getText().trim().length() != 0) {
					sorter.setRowFilter(RowFilter.regexFilter(txtTenNhanVien.getText()));
				} else if (txtNgayLap.getText().trim().length() != 0) {
					sorter.setRowFilter(RowFilter.regexFilter(txtNgayLap.getText()));
				}
			}

		});
	}

	private void addRowRandomData() {
		List<ChiTietDatPhong> dsChiTietDatPhongThanhToan = chiTietDatPhong_DAO.getAllChiTietDatPhongThanhToan();
		List<DonDatPhong> dsDonDatPhongDaTra = donDatPhong_DAO.getAllDonDatPhongDaTra();
		for (DonDatPhong donDatPhong : dsDonDatPhongDaTra) {
			// Mã hoá đơn
			String maHD = donDatPhong.getMaDonDatPhong().replace("MDP", "HD");
			String maPhong = "";
			double donGia = 0;
			Phong phong;
			tongTienPhong = 0;
			LocalTime timeNow = LocalTime.now();
			int hours, minutes, hieu = 0;

			for (ChiTietDatPhong chiTietDatPhong : dsChiTietDatPhongThanhToan) {
				if (chiTietDatPhong.getDonDatPhong().getMaDonDatPhong().equals(donDatPhong.getMaDonDatPhong())) {
					if (maPhong == "") {
						maPhong += chiTietDatPhong.getPhong().getMaPhong();

					} else {
						maPhong += ", " + chiTietDatPhong.getPhong().getMaPhong();
					}
					phong = phong_DAO.getPhong(chiTietDatPhong.getPhong().getMaPhong());
					donGia = phong.getGiaTien();
					LocalTime gioRa = chiTietDatPhong.getGioRa() == null ? timeNow : chiTietDatPhong.getGioRa();
					LocalTime gioVao = chiTietDatPhong.getGioVao();
					hours = gioRa.getHour() - gioVao.getHour();
					minutes = gioRa.getMinute() - gioVao.getMinute();
					hieu = hours * 60 + minutes;
					tienPhong = hieu;
					tienPhong *= donGia;
					tienPhong *= 1.0 / 60;
					tongTienPhong += tienPhong;

				}
			}

			// Tiền dịch vụ
			tienDichVu = 0;
			List<ChiTietDichVu> dsChiTietDichVu = chiTietDichVu_DAO.getAllChiTietDichVu();
			for (ChiTietDichVu chiTietDichVu : dsChiTietDichVu) {
				if (chiTietDichVu.getChiTietDatPhong().getDonDatPhong().getMaDonDatPhong()
						.equals(donDatPhong.getMaDonDatPhong())) {
					tienDichVu += dichVu_DAO.getDichVuTheoMa(chiTietDichVu.getDichVu().getMaDichVu()).getGiaBan()
							* chiTietDichVu.getSoLuong();

				}
			}
			String tenKhach = khachHang_DAO.getKhachHangTheoMa(donDatPhong.getKhachHang().getMaKhachHang()).getHoTen();
			String tenNhanVien = nhanVien_DAO.getNhanVienTheoMa(donDatPhong.getNhanVien().getMaNhanVien()).getHoTen();
			String ngayLap = Utils
					.formatDate(donDatPhong_DAO.getDatPhong(donDatPhong.getMaDonDatPhong()).getNgayNhanPhong())
					.toString();

			tableModel.addRow(new String[] { maHD, maPhong, tenKhach, tenNhanVien, ngayLap + "",
					Utils.formatTienTe(tongTienPhong), Utils.formatTienTe(tienDichVu),
					Utils.formatTienTe((tongTienPhong + tienDichVu) * 1.1) });

		}

	}

	@Override
	public void itemStateChanged(ItemEvent e) {
		// TODO Auto-generated method stub

	}
}
