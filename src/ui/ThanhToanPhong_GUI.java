package ui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;

import components.button.Button;
import components.notification.Notification;
import components.scrollbarCustom.ScrollBarCustom;
import components.textField.TextField;
import dao.ChiTietDatPhong_DAO;
import dao.ChiTietDichVu_DAO;
import dao.DichVu_DAO;
import dao.DonDatPhong_DAO;
import dao.KhachHang_DAO;
import dao.NhanVien_DAO;
import dao.Phong_DAO;
import entity.ChiTietDatPhong;
import entity.ChiTietDichVu;
import entity.DichVu;
import entity.DonDatPhong;
import entity.KhachHang;
import entity.NhanVien;
import entity.Phong;
import utils.Utils;

public class ThanhToanPhong_GUI extends JFrame implements ItemListener {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private ThanhToanPhong_GUI _this;
	private Button btnThanhToan;
	private ChiTietDatPhong_DAO chiTietDatPhong_DAO;
	private ChiTietDichVu_DAO chiTietDichVu_DAO;
	private JComboBox<String> cmbMaDatPhong;
	private JComboBox<String> cmbSoDienThoai;
	private DonDatPhong datPhong;
	private DonDatPhong_DAO datPhong_DAO;
	private DichVu_DAO dichVu_DAO;
	private List<ChiTietDatPhong> dsChiTietDatPhong;
	private KhachHang_DAO khachHang_DAO;
	private JLabel lblGioNhanPhong;
	private JLabel lblTenKhach;
	private JLabel lblTenNhanVien;
	private JLabel lblTienDichVu;
	private JLabel lblTienPhong;
	private JLabel lblTienThanhToan;
	private JLabel lblTongThoiLuong;
	private NhanVien_DAO nhanVien_DAO;
	private Phong_DAO phong_DAO;
	private JPanel pnlContent;
	private DefaultTableModel tableModel;
	private JTable tbl;
	private double tienThanhToan;
	private LocalTime timeNow;
	private int tongThoiGian;
	private double tongTien;
	private TextField txtTienNhan;
	private TextField txtTienThua;

	/**
	 * Create the frame.
	 *
	 * @param quanLyDatPhongGUI
	 */
	public ThanhToanPhong_GUI(QuanLyDatPhong_GUI quanLyDatPhongGUI, JFrame parentFrame, String maPhong) {
		_this = this;
		datPhong_DAO = new DonDatPhong_DAO();
		khachHang_DAO = new KhachHang_DAO();
		nhanVien_DAO = new NhanVien_DAO();
		chiTietDatPhong_DAO = new ChiTietDatPhong_DAO();
		phong_DAO = new Phong_DAO();
		chiTietDichVu_DAO = new ChiTietDichVu_DAO();
		dichVu_DAO = new DichVu_DAO();

		setType(Type.UTILITY);
		setResizable(false);
		setTitle("Thanh toán");
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setBounds(0, 0, 646, 513);
		setLocationRelativeTo(null);
		setUndecorated(true);
		pnlContent = new JPanel();
		pnlContent.setBorder(new EmptyBorder(0, 0, 0, 0));
		setContentPane(pnlContent);
		pnlContent.setLayout(null);

		JPanel pnlContainer = new JPanel();
		pnlContainer.setBackground(Color.WHITE);
		pnlContainer.setBounds(0, 0, 646, 513);
		pnlContent.add(pnlContainer);
		pnlContainer.setLayout(null);

		JPanel pnlHeader = new JPanel();
		pnlHeader.setBackground(Utils.primaryColor);
		pnlHeader.setBounds(0, 0, 646, 50);
		pnlContainer.add(pnlHeader);
		pnlHeader.setLayout(null);

		JLabel lblTitle = new JLabel("THANH TOÁN");
		lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitle.setForeground(Color.WHITE);
		lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 24));
		lblTitle.setBounds(45, 0, 536, 50);
		pnlHeader.add(lblTitle);

		JPanel pnlBody = new JPanel();
		pnlBody.setBackground(Color.WHITE);
		pnlBody.setBounds(10, 65, 626, 433);
		pnlContainer.add(pnlBody);
		pnlBody.setLayout(null);

		JPanel pnlRow1 = new JPanel();
		pnlRow1.setBackground(Color.WHITE);
		pnlRow1.setBounds(0, 0, 626, 24);
		pnlBody.add(pnlRow1);
		pnlRow1.setLayout(null);

		JPanel pnlMaDatPhong = new JPanel();
		pnlMaDatPhong.setBackground(Color.WHITE);
		pnlMaDatPhong.setBounds(0, 0, 303, 24);
		pnlRow1.add(pnlMaDatPhong);
		pnlMaDatPhong.setLayout(null);

		JLabel lblLabelMaDatPhong = new JLabel("Mã đặt phòng");
		lblLabelMaDatPhong.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		lblLabelMaDatPhong.setBounds(0, 0, 105, 24);
		pnlMaDatPhong.add(lblLabelMaDatPhong);

		cmbMaDatPhong = new JComboBox<>();
		cmbMaDatPhong.setBackground(Utils.primaryColor);
		cmbMaDatPhong.setForeground(Color.BLACK);
		cmbMaDatPhong.setModel(new DefaultComboBoxModel<>(new String[] { "Mã đặt phòng" }));
		cmbMaDatPhong.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		cmbMaDatPhong.setBounds(105, 0, 198, 24);
		pnlMaDatPhong.add(cmbMaDatPhong);

		JPanel pnlTenNhanVien = new JPanel();
		pnlTenNhanVien.setBackground(Color.WHITE);
		pnlTenNhanVien.setLayout(null);
		pnlTenNhanVien.setBounds(323, 0, 303, 24);
		pnlRow1.add(pnlTenNhanVien);

		JLabel lblLabelTenNhanVien = new JLabel("Tên nhân viên");
		lblLabelTenNhanVien.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		lblLabelTenNhanVien.setBounds(0, 0, 120, 24);
		pnlTenNhanVien.add(lblLabelTenNhanVien);

		lblTenNhanVien = new JLabel("");
		lblTenNhanVien.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		lblTenNhanVien.setBounds(125, 0, 177, 24);
		pnlTenNhanVien.add(lblTenNhanVien);

		JPanel pnlRow2 = new JPanel();
		pnlRow2.setBackground(Color.WHITE);
		pnlRow2.setLayout(null);
		pnlRow2.setBounds(0, 32, 626, 24);
		pnlBody.add(pnlRow2);

		JPanel pnlTenKhach = new JPanel();
		pnlTenKhach.setBackground(Color.WHITE);
		pnlTenKhach.setLayout(null);
		pnlTenKhach.setBounds(0, 0, 303, 24);
		pnlRow2.add(pnlTenKhach);

		JLabel lblLabelTenKhach = new JLabel("Tên khách");
		lblLabelTenKhach.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		lblLabelTenKhach.setBounds(0, 0, 105, 24);
		pnlTenKhach.add(lblLabelTenKhach);

		lblTenKhach = new JLabel("");
		lblTenKhach.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		lblTenKhach.setBounds(105, 0, 198, 24);
		pnlTenKhach.add(lblTenKhach);

		JPanel pnlGioNhanPhong = new JPanel();
		pnlGioNhanPhong.setBackground(Color.WHITE);
		pnlGioNhanPhong.setLayout(null);
		pnlGioNhanPhong.setBounds(323, 0, 303, 24);
		pnlRow2.add(pnlGioNhanPhong);

		JLabel lbllabelGioNhanPhong = new JLabel("Giờ nhận phòng");
		lbllabelGioNhanPhong.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		lbllabelGioNhanPhong.setBounds(0, 0, 120, 24);
		pnlGioNhanPhong.add(lbllabelGioNhanPhong);

		lblGioNhanPhong = new JLabel("");
		lblGioNhanPhong.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		lblGioNhanPhong.setBounds(125, 0, 177, 24);
		pnlGioNhanPhong.add(lblGioNhanPhong);

		JPanel pnlRow3 = new JPanel();
		pnlRow3.setBackground(Color.WHITE);
		pnlRow3.setLayout(null);
		pnlRow3.setBounds(0, 64, 626, 24);
		pnlBody.add(pnlRow3);

		JPanel pnlSoDienThoai = new JPanel();
		pnlSoDienThoai.setBackground(Color.WHITE);
		pnlSoDienThoai.setLayout(null);
		pnlSoDienThoai.setBounds(0, 0, 303, 24);
		pnlRow3.add(pnlSoDienThoai);

		JLabel lblLabelSoDienThoai = new JLabel("SĐT khách");
		lblLabelSoDienThoai.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		lblLabelSoDienThoai.setBounds(0, 0, 105, 24);
		pnlSoDienThoai.add(lblLabelSoDienThoai);

		cmbSoDienThoai = new JComboBox<>();
		cmbSoDienThoai.setModel(new DefaultComboBoxModel<>(new String[] { "Số điện thoại" }));
		cmbSoDienThoai.setForeground(Color.BLACK);
		cmbSoDienThoai.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		cmbSoDienThoai.setBackground(new Color(140, 177, 180));
		cmbSoDienThoai.setBounds(105, 0, 198, 24);
		pnlSoDienThoai.add(cmbSoDienThoai);

		JPanel pnlTongThoiLuong = new JPanel();
		pnlTongThoiLuong.setBackground(Color.WHITE);
		pnlTongThoiLuong.setLayout(null);
		pnlTongThoiLuong.setBounds(323, 0, 303, 24);
		pnlRow3.add(pnlTongThoiLuong);

		JLabel lblLabelTongThoiLuong = new JLabel("Tổng thời lượng");
		lblLabelTongThoiLuong.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		lblLabelTongThoiLuong.setBounds(0, 0, 120, 24);
		pnlTongThoiLuong.add(lblLabelTongThoiLuong);

		lblTongThoiLuong = new JLabel("");
		lblTongThoiLuong.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		lblTongThoiLuong.setBounds(125, 0, 177, 24);
		pnlTongThoiLuong.add(lblTongThoiLuong);

		JPanel pnlActions = new JPanel();
		pnlActions.setBackground(Color.WHITE);
		pnlActions.setBounds(0, 395, 626, 38);
		pnlBody.add(pnlActions);
		pnlActions.setLayout(null);

		Button btnQuayLai = new Button("Quay lại");
		btnQuayLai.setFocusable(false);
		btnQuayLai.setBorder(new EmptyBorder(0, 0, 0, 0));
		btnQuayLai.setRadius(4);
		btnQuayLai.setForeground(Color.BLACK);
		btnQuayLai.setColor(Utils.secondaryColor);
		btnQuayLai.setColorOver(Utils.getOpacity(Utils.secondaryColor, 0.8f));
		btnQuayLai.setColorClick(Utils.getOpacity(Utils.secondaryColor, 0.6f));
		btnQuayLai.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		btnQuayLai.setBounds(0, 0, 100, 38);
		pnlActions.add(btnQuayLai);

		btnThanhToan = new Button("Thanh toán");
		btnThanhToan.setEnabled(false);
		btnThanhToan.setFocusable(false);
		btnThanhToan.setBorder(new EmptyBorder(0, 0, 0, 0));
		btnThanhToan.setRadius(4);
		btnThanhToan.setColor(Utils.primaryColor);
		btnThanhToan.setColorOver(Utils.getOpacity(Utils.primaryColor, 0.8f));
		btnThanhToan.setColorClick(Utils.getOpacity(Utils.primaryColor, 0.6f));
		btnThanhToan.setForeground(Color.WHITE);
		btnThanhToan.setColor(Utils.primaryColor);
		btnThanhToan.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		btnThanhToan.setBounds(526, 0, 100, 38);
		pnlActions.add(btnThanhToan);

		JCheckBox chkXuatHoaDon = new JCheckBox("Xuất hóa đơn");
		chkXuatHoaDon.setFocusable(false);
		chkXuatHoaDon.setBackground(Color.WHITE);
		chkXuatHoaDon.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		chkXuatHoaDon.setBounds(396, 7, 120, 24);
		pnlActions.add(chkXuatHoaDon);

		JPanel pnlFooter = new JPanel();
		pnlFooter.setBackground(Color.WHITE);
		pnlFooter.setBounds(0, 268, 626, 119);
		pnlBody.add(pnlFooter);
		pnlFooter.setLayout(null);

		JPanel pnlFooterRow1 = new JPanel();
		pnlFooterRow1.setLayout(null);
		pnlFooterRow1.setBackground(Color.WHITE);
		pnlFooterRow1.setBounds(0, 0, 626, 24);
		pnlFooter.add(pnlFooterRow1);

		JPanel pnlTienDichVu = new JPanel();
		pnlTienDichVu.setLayout(null);
		pnlTienDichVu.setBackground(Color.WHITE);
		pnlTienDichVu.setBounds(0, 0, 303, 24);
		pnlFooterRow1.add(pnlTienDichVu);

		JLabel lblLabelTienDichVu = new JLabel("Tiền dịch vụ");
		lblLabelTienDichVu.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		lblLabelTienDichVu.setBounds(0, 0, 105, 24);
		pnlTienDichVu.add(lblLabelTienDichVu);

		lblTienDichVu = new JLabel("");
		lblTienDichVu.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		lblTienDichVu.setBounds(105, 0, 197, 24);
		pnlTienDichVu.add(lblTienDichVu);

		JPanel pnlTienPhong = new JPanel();
		pnlTienPhong.setLayout(null);
		pnlTienPhong.setBackground(Color.WHITE);
		pnlTienPhong.setBounds(323, 0, 303, 24);
		pnlFooterRow1.add(pnlTienPhong);

		JLabel lblLabelTienPhong = new JLabel("Tiền phòng");
		lblLabelTienPhong.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		lblLabelTienPhong.setBounds(0, 0, 115, 24);
		pnlTienPhong.add(lblLabelTienPhong);

		lblTienPhong = new JLabel("");
		lblTienPhong.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		lblTienPhong.setBounds(125, 0, 177, 24);
		pnlTienPhong.add(lblTienPhong);

		JPanel pnlFooterRow2 = new JPanel();
		pnlFooterRow2.setLayout(null);
		pnlFooterRow2.setBackground(Color.WHITE);
		pnlFooterRow2.setBounds(0, 32, 626, 24);
		pnlFooter.add(pnlFooterRow2);

		JPanel pnlThueVAT = new JPanel();
		pnlThueVAT.setLayout(null);
		pnlThueVAT.setBackground(Color.WHITE);
		pnlThueVAT.setBounds(0, 0, 303, 24);
		pnlFooterRow2.add(pnlThueVAT);

		JLabel lblLabelThueVAT = new JLabel("Thuế VAT");
		lblLabelThueVAT.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		lblLabelThueVAT.setBounds(0, 0, 105, 24);
		pnlThueVAT.add(lblLabelThueVAT);

		JLabel lblThueVAT = new JLabel("10%");
		lblThueVAT.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		lblThueVAT.setBounds(105, 0, 197, 24);
		pnlThueVAT.add(lblThueVAT);

		JPanel pnlTienThanhToan = new JPanel();
		pnlTienThanhToan.setLayout(null);
		pnlTienThanhToan.setBackground(Color.WHITE);
		pnlTienThanhToan.setBounds(323, 0, 303, 24);
		pnlFooterRow2.add(pnlTienThanhToan);

		JLabel lblLabelTienThanhToan = new JLabel("Tiền thanh toán");
		lblLabelTienThanhToan.setForeground(Color.RED);
		lblLabelTienThanhToan.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		lblLabelTienThanhToan.setBounds(0, 0, 115, 24);
		pnlTienThanhToan.add(lblLabelTienThanhToan);

		lblTienThanhToan = new JLabel("");
		lblTienThanhToan.setForeground(Color.RED);
		lblTienThanhToan.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		lblTienThanhToan.setBounds(125, 0, 177, 24);
		pnlTienThanhToan.add(lblTienThanhToan);

		JPanel pnlFooterRow3 = new JPanel();
		pnlFooterRow3.setBackground(Color.WHITE);
		pnlFooterRow3.setBounds(0, 64, 626, 55);
		pnlFooter.add(pnlFooterRow3);
		pnlFooterRow3.setLayout(null);

		txtTienNhan = new TextField() {
			/**
			 *
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public Color getBackground() {
				if (!isEditable())
					return Color.WHITE;
				return super.getBackground();
			}
		};
		txtTienNhan.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				txtTienNhan.setError(false);
			}

			@Override
			public void focusLost(FocusEvent e) {
				xuLyTienNhanHopLe();
			}
		});
		txtTienNhan.setEditable(false);
		txtTienNhan.setLabelText("Tiền nhận");
		txtTienNhan.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		txtTienNhan.setBounds(0, 0, 303, 55);
		pnlFooterRow3.add(txtTienNhan);
		txtTienNhan.setColumns(10);

		txtTienThua = new TextField() {
			/**
			 *
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public Color getBackground() {
				if (!isEditable())
					return Color.WHITE;
				return super.getBackground();
			}
		};
		txtTienThua.setEditable(false);
		txtTienThua.setLabelText("Tiền thừa");
		txtTienThua.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		txtTienThua.setColumns(10);
		txtTienThua.setBounds(323, 0, 303, 55);
		pnlFooterRow3.add(txtTienThua);

//		Table danh sách
		JScrollPane scr = new JScrollPane();
		scr.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scr.setBounds(0, 98, 626, 156);
		scr.setBackground(Utils.primaryColor);
		scr.getViewport().setBackground(Color.WHITE);

		ScrollBarCustom scb = new ScrollBarCustom();
//		Set color scrollbar thumb
		scb.setScrollbarColor(new Color(203, 203, 203));
		scr.setVerticalScrollBar(scb);
		pnlBody.add(scr);
		tbl = new JTable() {
			/**
			 *
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public boolean getShowVerticalLines() {
				return false;
			}

			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}

			@Override
			/**
			 * Set màu từng dòng cho Table
			 */
			public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
				Component c = super.prepareRenderer(renderer, row, column);
				if (isRowSelected(row))
					c.setBackground(Utils.getOpacity(Utils.primaryColor, 0.3f));
				else if (row % 2 == 0)
					c.setBackground(Color.WHITE);
				else
					c.setBackground(new Color(232, 232, 232));
				return c;
			}
		};

		tableModel = new DefaultTableModel(
				new String[] { "STT", "Tên dịch vụ", "Số lượng/Thời gian", "Đơn giá", "Thành tiền" }, 0);
		JTableHeader tblHeader = tbl.getTableHeader();
		TableColumnModel tableColumnModel = tbl.getColumnModel();

		tbl.setModel(tableModel);
		tbl.setFocusable(false);
		tblHeader.setBackground(Utils.primaryColor);
		tbl.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		tbl.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		tableColumnModel.getColumn(0).setPreferredWidth(40);
		tableColumnModel.getColumn(1).setPreferredWidth(170);
		tableColumnModel.getColumn(2).setPreferredWidth(150);
		tableColumnModel.getColumn(3).setPreferredWidth(123);
		tableColumnModel.getColumn(4).setPreferredWidth(123);
		tblHeader.setPreferredSize(new Dimension((int) tblHeader.getPreferredSize().getWidth(), 24));
		tblHeader.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		tbl.setRowHeight(24);
		DefaultTableCellRenderer dtcr = new DefaultTableCellRenderer();
		dtcr.setHorizontalAlignment(SwingConstants.RIGHT);
		tableColumnModel.getColumn(0).setCellRenderer(dtcr);
		tableColumnModel.getColumn(2).setCellRenderer(dtcr);
		tableColumnModel.getColumn(3).setCellRenderer(dtcr);
		tableColumnModel.getColumn(4).setCellRenderer(dtcr);
		scr.setViewportView(tbl);
		txtTienNhan.setEnabled(false);

//		Sự kiện window
		this.addWindowListener(new WindowAdapter() {
			KhachHang khachHang;

			@Override
			public void windowOpened(WindowEvent e) {
				List<DonDatPhong> list = datPhong_DAO.getAllDonDatPhongDangThue();
				list.forEach(datPhong -> {
					cmbMaDatPhong.addItem(datPhong.getMaDonDatPhong());
					khachHang = khachHang_DAO.getKhachHangTheoMa(datPhong.getKhachHang().getMaKhachHang());
					cmbSoDienThoai.addItem(khachHang.getSoDienThoai());
				});

				cmbMaDatPhong.addItemListener(_this);
				cmbSoDienThoai.addItemListener(_this);
			}
		});

//		Sự kiện txtTienNhan
		txtTienNhan.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if ((e.getKeyChar() >= '0' && e.getKeyChar() <= '9') || e.getKeyChar() == KeyEvent.VK_BACK_SPACE)
					txtTienNhan.setEditable(true);
				else
					txtTienNhan.setEditable(false);
			}

			@Override
			public void keyReleased(KeyEvent e) {
				xuLyTienNhanHopLe();
			}
		});

//		Sự kiện nút quay lại
		btnQuayLai.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				quanLyDatPhongGUI.closeJFrameSub();
			}
		});

//		Sự kiện nút thanh toán
		btnThanhToan.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (!btnThanhToan.isEnabled())
					return;
				boolean res = datPhong_DAO.thanhToanDatPhong(datPhong.getMaDonDatPhong(), timeNow);

				if (res) {
					quanLyDatPhongGUI.capNhatTrangThaiPhong();
					quanLyDatPhongGUI.closeJFrameSub();
					new Notification(parentFrame, components.notification.Notification.Type.SUCCESS,
							String.format("Thanh toán %s thành công", datPhong.getMaDonDatPhong())).showNotification();
				} else
					new Notification(parentFrame, components.notification.Notification.Type.ERROR,
							String.format("Thanh toán thất bại", datPhong.getMaDonDatPhong())).showNotification();
			}
		});
	}

	private void addRow(ChiTietDatPhong chiTietDatPhong, LocalDate ngayThanhToan) {
		int stt = tbl.getRowCount();
		++stt;
		String maPhong = chiTietDatPhong.getPhong().getMaPhong();
		Phong phong = phong_DAO.getPhong(maPhong);
		double donGia = phong.getGiaTien();
		LocalDate ngayNhanPhong = datPhong_DAO.getNgayNhanPhongCuaPhongDangThue(phong.getMaPhong());
		long daysElapsed = java.time.temporal.ChronoUnit.DAYS.between(ngayNhanPhong, ngayThanhToan);
		LocalTime gioRa = chiTietDatPhong.getGioRa() == null ? timeNow : chiTietDatPhong.getGioRa();
		LocalTime gioVao = chiTietDatPhong.getGioVao();
		int hours, minutes, hieu = 0;

		hours = gioRa.getHour();
		hours += 24 * daysElapsed;
		hours -= gioVao.getHour();
		minutes = gioRa.getMinute() - gioVao.getMinute();
		hieu = hours * 60 + minutes;

		double thanhTien = hieu;
		thanhTien *= donGia;
		thanhTien *= 1.0 / 60;

		tongThoiGian += hieu;
		tongTien += thanhTien;

		hours = hieu;
		hours *= 1.0 / 60;
		minutes = hieu;
		minutes %= 60;

		tableModel.addRow(new String[] { stt + "", maPhong,
				String.format("%s:%s", Utils.convertIntToString(hours), Utils.convertIntToString(minutes)),
				Utils.formatTienTe(donGia), Utils.formatTienTe(thanhTien) });
	}

	private void handleItemStateChangedCmbMaDatPhong() {
		if (((String) cmbMaDatPhong.getSelectedItem()).equals("Mã đặt phòng")) {
			datPhong = null;
			cmbSoDienThoai.setSelectedIndex(0);
			txtTienNhan.setEnabled(false);
		} else
			datPhong = datPhong_DAO.getDatPhong((String) cmbMaDatPhong.getSelectedItem());
	}

	private void handleItemStateChangedCmbSoDienThoai() {
		if (((String) cmbSoDienThoai.getSelectedItem()).equals("Số điện thoại")) {
			datPhong = null;
			cmbMaDatPhong.setSelectedIndex(0);
			txtTienNhan.setEnabled(false);
		} else
			datPhong = datPhong_DAO.getDonDatPhongNgayTheoSoDienThoai((String) cmbSoDienThoai.getSelectedItem());
	}

	@Override
	public void itemStateChanged(ItemEvent e) {
		if (e.getStateChange() == ItemEvent.DESELECTED)
			return;
		txtTienNhan.setText("");
		txtTienThua.setText("");
		btnThanhToan.setEnabled(false);
		txtTienNhan.setError(false);

		tableModel.setRowCount(0);
		Object o = e.getSource();
		boolean isMaDatPhong = true;
		cmbMaDatPhong.removeItemListener(_this);
		cmbSoDienThoai.removeItemListener(_this);

		if (o.equals(cmbMaDatPhong))
			handleItemStateChangedCmbMaDatPhong();
		else if (o.equals(cmbSoDienThoai)) {
			isMaDatPhong = false;
			handleItemStateChangedCmbSoDienThoai();
		}

		if (datPhong != null) {
			txtTienNhan.setEnabled(true);
			KhachHang khachHang = khachHang_DAO.getKhachHangTheoMa(datPhong.getKhachHang().getMaKhachHang());
			NhanVien nhanVien = nhanVien_DAO.getNhanVienTheoMa(datPhong.getNhanVien().getMaNhanVien());
			List<ChiTietDichVu> dsChiTietDichVu = chiTietDichVu_DAO
					.getDichVuTheoPhieuDatPhong(datPhong.getMaDonDatPhong());

			if (isMaDatPhong)
				cmbSoDienThoai.setSelectedItem(khachHang.getSoDienThoai());
			else
				cmbMaDatPhong.setSelectedItem(datPhong.getMaDonDatPhong());

			lblTenKhach.setText(khachHang.getHoTen());
			lblTenNhanVien.setText(nhanVien.getHoTen());
			lblGioNhanPhong.setText(Utils.convertLocalTimeToString(datPhong.getGioNhanPhong()) + " - "
					+ Utils.formatDate(datPhong.getNgayNhanPhong()));
			dsChiTietDatPhong = chiTietDatPhong_DAO.getAllChiTietDatPhong(datPhong);

			timeNow = LocalTime.now();
			LocalDate ngayThanhToan = LocalDate.now();
			tongThoiGian = 0;
			tongTien = 0;
			tienThanhToan = 0;

			int length = dsChiTietDatPhong.size();
			for (int i = 0; i < length; ++i)
				addRow(dsChiTietDatPhong.get(i), ngayThanhToan);

			int n = dsChiTietDichVu.size();
			DichVu dichVu;
			ChiTietDichVu chiTietDichVu;
			double tongTienDichVu = 0;
			double tienDichVu;
			for (int i = 0; i < n; ++i) {
				chiTietDichVu = dsChiTietDichVu.get(i);
				dichVu = dichVu_DAO.getDichVuTheoMa(chiTietDichVu.getDichVu().getMaDichVu());
				tienDichVu = dichVu.getGiaBan() * chiTietDichVu.getSoLuong();
				tongTienDichVu += tienDichVu;
				tableModel.addRow(
						new String[] { length + i + 1 + "", dichVu.getTenDichVu(), chiTietDichVu.getSoLuong() + "",
								Utils.formatTienTe(dichVu.getGiaBan()), Utils.formatTienTe(tienDichVu) });
			}

			int gio = tongThoiGian / 60;
			int phut = tongThoiGian % 60;
			tienThanhToan = tongTien + tongTienDichVu;
			lblTongThoiLuong.setText(((gio > 0 ? gio + " giờ" : "") + " " + (phut > 0 ? phut + " phút" : "")).trim());
			lblTienPhong.setText(Utils.formatTienTe(tongTien));
			lblTienDichVu.setText(Utils.formatTienTe(tongTienDichVu));
			tienThanhToan *= 1.1;
			lblTienThanhToan.setText(Utils.formatTienTe(tienThanhToan));
		} else {
			lblTenKhach.setText("");
			lblTenNhanVien.setText("");
			lblGioNhanPhong.setText("");
			lblTongThoiLuong.setText("");
			lblTienPhong.setText("");
			lblTienThanhToan.setText("");
			lblTienDichVu.setText("");
			dsChiTietDatPhong = null;
			btnThanhToan.setEnabled(false);
			tableModel.setRowCount(0);
		}
		cmbMaDatPhong.addItemListener(_this);
		cmbSoDienThoai.addItemListener(_this);
		repaint();
	}

	/**
	 * Xử lý tiền nhận
	 */
	private void xuLyTienNhanHopLe() {
		String tienNhanS = txtTienNhan.getText().trim();

		if (tienNhanS.length() <= 0) {
			txtTienNhan.setError(true);
			btnThanhToan.setEnabled(false);
			txtTienThua.setText("");
			return;
		}

		double tienNhan = Double.parseDouble(tienNhanS);
		tienThanhToan = Math.round(tienThanhToan);
		if (tienNhan < tienThanhToan) {
			txtTienNhan.setError(true);
			btnThanhToan.setEnabled(false);
			txtTienThua.setText("");
		} else {
			txtTienNhan.setError(false);
			btnThanhToan.setEnabled(true);
			txtTienThua.setText(Utils.formatTienTe(tienNhan - tienThanhToan));
		}
	}
}