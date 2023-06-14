package ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.regex.Pattern;

import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;

import com.raven.datechooser.DateChooser;

import components.button.Button;
//import components.comboBox.ComboBox;
import components.notification.Notification;
import components.notification.Notification.Type;
import components.radio.RadioButtonCustom;
import components.textField.TextField;
import dao.DiaChi_DAO;
import dao.NhanVien_DAO;
import entity.NhanVien;
import entity.NhanVien.ChucVu;
import entity.NhanVien.TrangThai;
import entity.Phuong;
import entity.Quan;
import entity.Tinh;
import utils.Utils;

public class ThemNhanVien_GUI extends JPanel implements ItemListener {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private ButtonGroup buttonGroupGioiTinh;
	private JComboBox<String> cmbChucVu;
	private JComboBox<String> cmbPhuong;
	private JComboBox<String> cmbQuan;
	private JComboBox<String> cmbTinh;
	private JComboBox<String> cmbTrangThai;
	private DateChooser dateChoose;
	private DiaChi_DAO diaChi_DAO;
	private boolean isEnabledEventPhuong = false;
	private boolean isEnabledEventQuan = false;
	private boolean isEnabledEventTinh = false;
	private Main main;
	private NhanVien_DAO nhanVien_DAO;
	private Phuong phuong;
	private Quan quan;
	private RadioButtonCustom radNam;
	private RadioButtonCustom radNu;
	private Tinh tinh;
	private TextField txtCCCD;
	private TextField txtDiaChiCT;
	private TextField txtHoTen;
	private TextField txtLuong;
	private TextField txtMaNhanVien;
	private TextField txtMatKhau;
	private TextField txtNgaySinh;
	private TextField txtSoDienThoai;
	private final int widthPnlContainer = 948;

	/**
	 * Create the frame.
	 *
	 * @wbp.parser.constructor
	 */
	public ThemNhanVien_GUI(Main jFrame) {
		nhanVien_DAO = new NhanVien_DAO();
		diaChi_DAO = new DiaChi_DAO();
		main = jFrame;
		int padding = (int) Math.floor((Utils.getBodyHeight() - 428) * 1.0 / 8);
		int top = padding;

		setBackground(Utils.secondaryColor);
		setBounds(0, 0, Utils.getScreenWidth(), Utils.getBodyHeight());
		setLayout(null);

		JPanel pnlContainer = new JPanel();
		pnlContainer.setBackground(Utils.secondaryColor);
		pnlContainer.setBounds(Utils.getLeft(widthPnlContainer), 0, widthPnlContainer, Utils.getBodyHeight());
		this.add(pnlContainer);
		pnlContainer.setLayout(null);

		JPanel pnlRow1 = new JPanel();
		pnlRow1.setBackground(Utils.secondaryColor);
		pnlRow1.setBounds(0, top, 948, 55);
		top += 55 + padding;
		pnlContainer.add(pnlRow1);
		pnlRow1.setLayout(null);

		txtMaNhanVien = new TextField();
		txtMaNhanVien.setLineColor(Utils.lineTextField);
		txtMaNhanVien.setBackground(Utils.secondaryColor);
		txtMaNhanVien.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		txtMaNhanVien.setLabelText("Mã nhân viên");
		txtMaNhanVien.setBounds(0, 0, 449, 55);
		pnlRow1.add(txtMaNhanVien);
		txtMaNhanVien.setColumns(10);

		txtHoTen = new TextField();
		txtHoTen.setLineColor(Utils.lineTextField);
		txtHoTen.setLabelText("Họ tên");
		txtHoTen.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		txtHoTen.setColumns(10);
		txtHoTen.setBackground(Utils.secondaryColor);
		txtHoTen.setBounds(499, 0, 449, 55);
		pnlRow1.add(txtHoTen);

		JPanel pnlRow2 = new JPanel();
		pnlRow2.setLayout(null);
		pnlRow2.setBackground(Utils.secondaryColor);
		pnlRow2.setBounds(0, top, 948, 55);
		top += 55 + padding;
		pnlContainer.add(pnlRow2);

		txtCCCD = new TextField();
		txtCCCD.setLineColor(new Color(149, 166, 248));
		txtCCCD.setLabelText("Căn cước công dân");
		txtCCCD.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		txtCCCD.setColumns(10);
		txtCCCD.setBackground(Utils.secondaryColor);
		txtCCCD.setBounds(0, 0, 449, 55);
		pnlRow2.add(txtCCCD);

		txtSoDienThoai = new TextField();
		txtSoDienThoai.setLineColor(new Color(149, 166, 248));
		txtSoDienThoai.setLabelText("Số điện thoại");
		txtSoDienThoai.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		txtSoDienThoai.setColumns(10);
		txtSoDienThoai.setBackground(Utils.secondaryColor);
		txtSoDienThoai.setBounds(499, 0, 449, 55);
		pnlRow2.add(txtSoDienThoai);

		JPanel pnlRow3 = new JPanel();
		pnlRow3.setLayout(null);
		pnlRow3.setBackground(Utils.secondaryColor);
		pnlRow3.setBounds(0, top, 948, 55);
		top += 55 + padding;
		pnlContainer.add(pnlRow3);

		txtNgaySinh = new TextField();
		txtNgaySinh.setIcon(Utils.getImageIcon("add-event 2.png"));
		txtNgaySinh.setLineColor(new Color(149, 166, 248));
		txtNgaySinh.setLabelText("Ngày sinh");
		txtNgaySinh.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		txtNgaySinh.setColumns(10);
		txtNgaySinh.setBackground(Utils.secondaryColor);
		txtNgaySinh.setBounds(0, 0, 449, 55);
		pnlRow3.add(txtNgaySinh);
		dateChoose = new DateChooser();
		dateChoose.setDateFormat("dd/MM/yyyy");
		dateChoose.setTextRefernce(txtNgaySinh);

		JPanel pnlGioiTinh = new JPanel();
		pnlGioiTinh.setBackground(Utils.secondaryColor);
		pnlGioiTinh.setBounds(499, 0, 550, 55);
		pnlRow3.add(pnlGioiTinh);
		pnlGioiTinh.setLayout(null);

		JLabel lblGioiTinh = new JLabel("Giới tính");
		lblGioiTinh.setForeground(Utils.labelTextField);
		lblGioiTinh.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		lblGioiTinh.setBounds(4, 6, 260, 19);
		pnlGioiTinh.add(lblGioiTinh);

		JPanel pnlGroupGioiTinh = new JPanel();
		pnlGroupGioiTinh.setBackground(Utils.secondaryColor);
		pnlGroupGioiTinh.setBounds(4, 39, 138, 16);
		pnlGioiTinh.add(pnlGroupGioiTinh);
		pnlGroupGioiTinh.setLayout(null);

		radNam = new RadioButtonCustom("Nam");
		radNam.setFocusable(false);
		radNam.setBackground(Utils.secondaryColor);
		radNam.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		radNam.setBounds(0, -2, 59, 21);
		pnlGroupGioiTinh.add(radNam);

		radNu = new RadioButtonCustom("Nữ");
		radNu.setFocusable(false);
		radNu.setBackground(Utils.secondaryColor);
		radNu.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		radNu.setBounds(79, -2, 59, 21);
		pnlGroupGioiTinh.add(radNu);

		buttonGroupGioiTinh = new ButtonGroup();
		buttonGroupGioiTinh.add(radNam);
		buttonGroupGioiTinh.add(radNu);

		JPanel pnlRow4 = new JPanel();
		pnlRow4.setBackground(Utils.secondaryColor);
		pnlRow4.setBounds(0, top, 948, 65);
		top += 65 + padding;
		pnlContainer.add(pnlRow4);
		pnlRow4.setLayout(null);

		JLabel lblDiaChi = new JLabel("Địa chỉ");
		lblDiaChi.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		lblDiaChi.setBounds(4, 6, 50, 19);
		lblDiaChi.setForeground(Utils.labelTextField);
		pnlRow4.add(lblDiaChi);

		cmbTinh = new JComboBox<>();
		cmbTinh.setModel(new DefaultComboBoxModel<String>());
		cmbTinh.setFont(new Font("Segoe UI", Font.PLAIN, 20));
		cmbTinh.setBackground(Utils.lightColor);
		cmbTinh.setBounds(4, 29, 200, 36);
		pnlRow4.add(cmbTinh);

		cmbQuan = new JComboBox<>();
		cmbQuan.setModel(new DefaultComboBoxModel<String>());
		cmbQuan.setFont(new Font("Segoe UI", Font.PLAIN, 20));
		cmbQuan.setBackground(Utils.lightColor);
		cmbQuan.setBounds(220, 29, 200, 36);
		pnlRow4.add(cmbQuan);

		cmbPhuong = new JComboBox<>();
		cmbPhuong.setModel(new DefaultComboBoxModel<String>());
		cmbPhuong.setFont(new Font("Segoe UI", Font.PLAIN, 20));
		cmbPhuong.setBackground(Utils.lightColor);
		cmbPhuong.setBounds(440, 29, 200, 36);
		pnlRow4.add(cmbPhuong);

		txtDiaChiCT = new TextField();
		txtDiaChiCT.setLineColor(new Color(149, 166, 248));
		txtDiaChiCT.setLabelText("Địa chỉ cụ thể");
		txtDiaChiCT.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		txtDiaChiCT.setColumns(10);
		txtDiaChiCT.setBackground(Utils.secondaryColor);
		txtDiaChiCT.setBounds(660, 10, 288, 55);
		pnlRow4.add(txtDiaChiCT);

		JPanel pnlRow5 = new JPanel();
		pnlRow5.setLayout(null);
		pnlRow5.setBackground(Utils.secondaryColor);
		pnlRow5.setBounds(0, top, 948, 55);
		top += 55 + padding;
		pnlContainer.add(pnlRow5);

		txtLuong = new TextField();
		txtLuong.setLineColor(new Color(149, 166, 248));
		txtLuong.setLabelText("Lương");
		txtLuong.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		txtLuong.setColumns(10);
		txtLuong.setBackground(Utils.secondaryColor);
		txtLuong.setBounds(0, 0, 449, 55);
		pnlRow5.add(txtLuong);

		txtMatKhau = new TextField();
		txtMatKhau.setLineColor(new Color(149, 166, 248));
		txtMatKhau.setLabelText("Mật khẩu");
		txtMatKhau.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		txtMatKhau.setColumns(10);
		txtMatKhau.setBackground(Utils.secondaryColor);
		txtMatKhau.setBounds(499, 0, 449, 55);
		txtMatKhau.setEditable(false);
		pnlRow5.add(txtMatKhau);

		JPanel pnlRow6 = new JPanel();
		pnlRow6.setLayout(null);
		pnlRow6.setBackground(Utils.secondaryColor);
		pnlRow6.setBounds(0, top, 948, 65);
		top += 65 + padding;
		pnlContainer.add(pnlRow6);

		JPanel pnlChucVu = new JPanel();
		pnlChucVu.setBackground(Utils.secondaryColor);
		pnlChucVu.setBounds(0, 0, 449, 65);
		pnlRow6.add(pnlChucVu);
		pnlChucVu.setLayout(null);

		JLabel lblChucVu = new JLabel("Chức vụ");
		lblChucVu.setForeground(Utils.labelTextField);
		lblChucVu.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		lblChucVu.setBounds(4, 6, 100, 19);
		pnlChucVu.add(lblChucVu);

		cmbChucVu = new JComboBox<>();
		cmbChucVu.setModel(new DefaultComboBoxModel<>(new String[] { NhanVien.convertChucVuToString(ChucVu.QuanLy),
				NhanVien.convertChucVuToString(ChucVu.NhanVien) }));
		cmbChucVu.setFont(new Font("Segoe UI", Font.PLAIN, 20));
		cmbChucVu.setBackground(Utils.lightColor);
		cmbChucVu.setBounds(4, 29, 200, 36);
		pnlChucVu.add(cmbChucVu);

		JPanel pnlTrangThai = new JPanel();
		pnlTrangThai.setLayout(null);
		pnlTrangThai.setBackground(Utils.secondaryColor);
		pnlTrangThai.setBounds(499, 0, 449, 65);
		pnlRow6.add(pnlTrangThai);

		JLabel lblTrangThai = new JLabel("Trạng thái");
		lblTrangThai.setForeground(Utils.labelTextField);
		lblTrangThai.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		lblTrangThai.setBounds(4, 6, 100, 19);
		pnlTrangThai.add(lblTrangThai);

		cmbTrangThai = new JComboBox<>();
		cmbTrangThai.setModel(
				new DefaultComboBoxModel<>(new String[] { NhanVien.convertTrangThaiToString(TrangThai.DangLam),
						NhanVien.convertTrangThaiToString(TrangThai.NghiLam) }));
		cmbTrangThai.setFont(new Font("Segoe UI", Font.PLAIN, 20));
		cmbTrangThai.setBackground(Utils.lightColor);
		cmbTrangThai.setBounds(4, 29, 200, 36);
		pnlTrangThai.add(cmbTrangThai);

		txtMaNhanVien.setText(nhanVien_DAO.taoMaNhanVien());
		txtMatKhau.setText("1234Abc@");
		cmbQuan.addItem(Quan.getQuanLabel());
		cmbPhuong.addItem(Phuong.getPhuongLabel());

		setTinhToComboBox();
		cmbQuan.setEnabled(false);
		cmbPhuong.setEnabled(false);
		txtMaNhanVien.setEnabled(false);

		JPanel pnlActions = new JPanel();
		pnlActions.setLayout(null);
		pnlActions.setBackground(Utils.secondaryColor);
		pnlActions.setBounds(0, top, 948, 48);
		pnlContainer.add(pnlActions);

		Button btnThem = new Button("Thêm");
		btnThem.setIcon(Utils.getImageIcon("add-user (2) 1.png"));
		btnThem.setRadius(8);
		btnThem.setForeground(Color.WHITE);
		btnThem.setFont(new Font("Segoe UI", Font.PLAIN, 32));
		btnThem.setFocusable(false);
		btnThem.setColorOver(new Color(140, 177, 180));
		btnThem.setColorClick(new Color(140, 177, 180, 204));
		btnThem.setColor(new Color(140, 177, 180));
		btnThem.setBorderColor(Utils.secondaryColor);
		btnThem.setBorder(new EmptyBorder(0, 0, 0, 0));
		btnThem.setBounds(698, 0, 250, 48);
		pnlActions.add(btnThem);

		Button btnHuy = new Button("Hủy");
		btnHuy.setIcon(Utils.getImageIcon("cancelled 1.png"));
		btnHuy.setRadius(8);
		btnHuy.setForeground(new Color(51, 51, 51));
		btnHuy.setFont(new Font("Segoe UI", Font.PLAIN, 32));
		btnHuy.setFocusable(false);
		btnHuy.setColorOver(new Color(255, 255, 255, 204));
		btnHuy.setColorClick(new Color(255, 255, 255, 153));
		btnHuy.setColor(Color.WHITE);
		btnHuy.setBorderColor(Utils.secondaryColor);
		btnHuy.setBorder(new EmptyBorder(0, 0, 0, 0));
		btnHuy.setBounds(0, 0, 250, 48);
		pnlActions.add(btnHuy);

		Button btnLamMoi = new Button("Làm mới");
		btnLamMoi.setRadius(8);
		btnLamMoi.setForeground(new Color(51, 51, 51));
		btnLamMoi.setFont(new Font("Segoe UI", Font.PLAIN, 32));
		btnLamMoi.setFocusable(false);
		btnLamMoi.setColorOver(new Color(255, 255, 255, 204));
		btnLamMoi.setColorClick(new Color(255, 255, 255, 153));
		btnLamMoi.setColor(Color.WHITE);
		btnLamMoi.setBorderColor(Utils.secondaryColor);
		btnLamMoi.setBorder(new EmptyBorder(0, 0, 0, 0));
		btnLamMoi.setBounds(428, 0, 250, 48);
		pnlActions.add(btnLamMoi);

		cmbTinh.addItemListener(this);
		cmbQuan.addItemListener(this);
		cmbPhuong.addItemListener(this);

//		Sự kiện txtHoten
		txtHoTen.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				txtHoTen.setError(false);
			}
		});

//		Sự kiện txtCCCD
		txtCCCD.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				txtCCCD.setError(false);
			}
		});

//		Sự kiện txtSoDienThoai
		txtSoDienThoai.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				txtSoDienThoai.setError(false);
			}
		});

//		Sự kiện txtDiaChiCT
		txtDiaChiCT.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				txtDiaChiCT.setError(false);
			}
		});

//		Sự kiện txtLuong
		txtLuong.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				txtLuong.setError(false);
			}
		});

//		Sự kiện txtMatKhau
		txtMatKhau.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				txtMatKhau.setError(false);
			}
		});

//		Sự kiện nút hủy
		btnHuy.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				main.backPanel();
			}
		});

//		Sự kiện nút làm mới
		btnLamMoi.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				xoaRong();

				repaint();
			}
		});

//		Sự kiện nút thêm
		btnThem.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (!validator())
					return;
				NhanVien nhanVien = getNhanVienTuForm();
				boolean res = nhanVien_DAO.themNhanVien(nhanVien);

				if (res) {
					new Notification(jFrame, Type.SUCCESS, String.format("Thêm nhân viên %s - %s thành công",
							nhanVien.getMaNhanVien(), nhanVien.getHoTen())).showNotification();
					xoaRong();
					txtMaNhanVien.setText(nhanVien_DAO.taoMaNhanVien());
				} else
					new Notification(jFrame, Type.ERROR, "Thêm nhân viên thất bại").showNotification();
			}
		});

//		Sự kiện Component được hiển thị
		addAncestorListener(new AncestorListener() {
			@Override
			public void ancestorAdded(AncestorEvent event) {
				dateChoose.showPopup();
				dateChoose.hidePopup();
			}

			@Override
			public void ancestorMoved(AncestorEvent event) {
			}

			@Override
			public void ancestorRemoved(AncestorEvent event) {
			}
		});
	}

	/**
	 * Get nhân viên từ form
	 *
	 * @return nhanVien
	 */
	private NhanVien getNhanVienTuForm() {
		String maNhanVien = txtMaNhanVien.getText();
		String hoTen = txtHoTen.getText().trim();
		String cccd = txtCCCD.getText().trim();
		String soDienThoai = txtSoDienThoai.getText().trim();
		LocalDate ngaySinh = Utils.getLocalDate(txtNgaySinh.getText());
		String diaChiCuThe = txtDiaChiCT.getText().trim();
		String luong = txtLuong.getText().trim();
		double luongDouble = luong.endsWith(" ₫") ? Utils.convertStringToTienTe(luong) : Double.parseDouble(luong);
		boolean gioiTinh = radNam.isSelected();
		ChucVu chucVu = NhanVien.convertStringToChucVu((String) cmbChucVu.getSelectedItem());
		TrangThai trangThai = NhanVien.convertStringToTrangThai((String) cmbTrangThai.getSelectedItem());
		return new NhanVien(maNhanVien, hoTen, cccd, soDienThoai, ngaySinh, gioiTinh, tinh, quan, phuong, diaChiCuThe,
				chucVu, luongDouble, trangThai, "1234Abc@");
	}

	/**
	 * Lắng nghe sự kiện JComboBox
	 */
	@Override
	public void itemStateChanged(ItemEvent e) {
		Object object = e.getSource();
		if (e.getStateChange() != ItemEvent.SELECTED)
			return;
		if (cmbTinh.equals(object)) {
			if (!isEnabledEventTinh)
				return;
			isEnabledEventQuan = false;
			isEnabledEventPhuong = false;
			String tinhSelected = (String) cmbTinh.getSelectedItem();

			cmbPhuong.setSelectedIndex(0);
			cmbPhuong.setEnabled(false);
			cmbQuan = resizeComboBox(cmbQuan, Quan.getQuanLabel());
			quan = null;
			phuong = null;

			if (tinhSelected.equals(Tinh.getTinhLabel())) {
				cmbQuan.setSelectedIndex(0);
				cmbQuan.setEnabled(false);
				tinh = null;
				return;
			}
			Tinh tinh = diaChi_DAO.getTinh(tinhSelected);
			ThemNhanVien_GUI.this.tinh = tinh;
			setQuanToComboBox(ThemNhanVien_GUI.this.tinh);
			repaint();
			cmbQuan.setEnabled(true);
			isEnabledEventQuan = true;
			isEnabledEventPhuong = true;
		} else if (cmbQuan.equals(object)) {
			if (!isEnabledEventQuan)
				return;
			isEnabledEventPhuong = false;
			isEnabledEventQuan = false;
			String quanSelected = (String) cmbQuan.getSelectedItem();
			cmbPhuong = resizeComboBox(cmbPhuong, Quan.getQuanLabel());
			phuong = null;

			if (quanSelected.equals(Quan.getQuanLabel())) {
				cmbPhuong.setSelectedIndex(0);
				cmbPhuong.setEnabled(false);
				quan = null;
			} else {
				Quan quan = diaChi_DAO.getQuan(tinh, quanSelected);
				ThemNhanVien_GUI.this.quan = quan;
				cmbPhuong.setEnabled(true);
				setPhuongToComboBox(this.quan);
			}

			isEnabledEventPhuong = true;
			isEnabledEventQuan = true;
		} else if (cmbPhuong.equals(object)) {
			if (!isEnabledEventPhuong)
				return;
			isEnabledEventPhuong = false;
			String phuongSelect = (String) cmbPhuong.getSelectedItem();

			if (phuongSelect.equals(Phuong.getPhuongLabel())) {
				phuong = null;
				return;
			}

			Phuong phuong = diaChi_DAO.getPhuong(quan, phuongSelect);
			ThemNhanVien_GUI.this.phuong = phuong;
			isEnabledEventPhuong = false;
		}

	}

	/**
	 * Xóa tất cả các items của JComboBox và thêm chuỗi vào JComboBox
	 *
	 * @param <E>
	 * @param list       JComboBox cần xóa
	 * @param firstLabel chuỗi cần thêm
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private <E> JComboBox<E> resizeComboBox(JComboBox<E> list, String firstLabel) {
		list.removeAllItems();
		list.addItem((E) firstLabel);
		return list;
	}

	/**
	 * Set danh sách phường của quận vào JComboBox
	 *
	 * @param quan quận cần lấy phường
	 */
	private void setPhuongToComboBox(Quan quan) {
		isEnabledEventPhuong = false;

		resizeComboBox(cmbPhuong, Phuong.getPhuongLabel());

		List<Phuong> phuongs = diaChi_DAO.getPhuong(quan);

		phuongs.sort(new Comparator<Phuong>() {

			@Override
			public int compare(Phuong o1, Phuong o2) {
				return o1.getPhuong().compareToIgnoreCase(o2.getPhuong());
			}
		});

		phuongs.forEach(phuong -> cmbPhuong.addItem(phuong.getPhuong()));

		isEnabledEventPhuong = true;
	}

	/**
	 * Set danh sách quận của tỉnh vào JComboBox
	 *
	 * @param tinh tỉnh cần lấy quận
	 */
	private void setQuanToComboBox(Tinh tinh) {
		isEnabledEventQuan = false;

		resizeComboBox(cmbQuan, Quan.getQuanLabel());
		List<Quan> quans = diaChi_DAO.getQuan(tinh);
		quans.sort(new Comparator<Quan>() {
			@Override
			public int compare(Quan o1, Quan o2) {
				return o1.getQuan().compareToIgnoreCase(o2.getQuan());
			}
		});
		quans.forEach(quan -> cmbQuan.addItem(quan.getQuan()));

		isEnabledEventQuan = true;
	}

	/**
	 * Set danh sách tỉnh vào JComboBox
	 */
	private void setTinhToComboBox() {
		isEnabledEventTinh = false;

		resizeComboBox(cmbTinh, Tinh.getTinhLabel());

		List<Tinh> tinhs = diaChi_DAO.getTinh();

		tinhs.sort(new Comparator<Tinh>() {
			@Override
			public int compare(Tinh o1, Tinh o2) {
				return o1.getTinh().compareToIgnoreCase(o2.getTinh());
			}
		});

		tinhs.forEach(tinh -> cmbTinh.addItem(tinh.getTinh()));

		isEnabledEventTinh = true;
	}

	/**
	 * Hiển thị thông báo lỗi và focus các JTextField
	 *
	 * @param txt     JtextField cần focus
	 * @param message thông báo lỗi
	 * @return false
	 */
	private boolean showThongBaoLoi(TextField txt, String message) {
		new Notification(main, Type.ERROR, message).showNotification();
		txt.setError(true);
		txt.selectAll();
		txt.requestFocus();
		return false;
	}

	/**
	 * Kiểm tra thông tin nhân viên
	 *
	 * @return true nếu thông tin nhân viên hợp lệ
	 */
	private boolean validator() {
		String vietNamese = Utils.getVietnameseDiacriticCharacters() + "A-Z";
		String vietNameseLower = Utils.getVietnameseDiacriticCharactersLower() + "a-z";

		String hoTen = txtHoTen.getText().trim();

		if (hoTen.length() <= 0)
			return showThongBaoLoi(txtHoTen, "Vui lòng nhập họ tên nhân viên");

		if (Pattern.matches(String.format(".*[^%s%s ].*", vietNamese, vietNameseLower), hoTen))
			return showThongBaoLoi(txtHoTen, "Họ tên chỉ chứa các ký tự chữ cái");

		if (!Pattern.matches(
				String.format("[%s][%s]*( [%s][%s]*)+", vietNamese, vietNameseLower, vietNamese, vietNameseLower),
				hoTen))
			return showThongBaoLoi(txtHoTen, "Họ tên phải bắt đầu bằng ký tự hoa và có ít nhất 2 từ");

		String cccd = txtCCCD.getText().trim();

		if (cccd.length() <= 0)
			return showThongBaoLoi(txtCCCD, "Vui lòng nhập số căn cước công dân");

		if (!Pattern.matches("\\d{12}", cccd))
			return showThongBaoLoi(txtCCCD, "Số căn cước công dân phải là 12 ký tự số");

		if (nhanVien_DAO.isCCCDDaTonTai(cccd))
			return showThongBaoLoi(txtCCCD, "Số căn cước công dân đã tồn tại");

		String soDienThoai = txtSoDienThoai.getText().trim();

		if (soDienThoai.length() <= 0)
			return showThongBaoLoi(txtSoDienThoai, "Vui lòng nhập số điện thoại");

		if (!Utils.isSoDienThoai(soDienThoai))
			return showThongBaoLoi(txtSoDienThoai, "Số điện thoại phải bắt đầu bằng số 0, theo sau là 9 ký tự số");

		if (nhanVien_DAO.isSoDienThoaiDaTonTai(soDienThoai))
			return showThongBaoLoi(txtSoDienThoai, "Số điện thoại đã tồn tại");

		String ngaySinh = txtNgaySinh.getText();
		long daysElapsed = java.time.temporal.ChronoUnit.DAYS.between(Utils.getLocalDate(ngaySinh), LocalDate.now());
		boolean isDuTuoi = daysElapsed / (18 * 365) > 0;

		if (!isDuTuoi) {
			new Notification(main, Type.ERROR, "Nhân viên chưa đủ 18 tuổi").showNotification();
			dateChoose.showPopup();
			return false;
		}

		boolean isNamSelected = radNam.isSelected();
		boolean isNuSelected = radNu.isSelected();

		if (!isNamSelected && !isNuSelected) {
			new Notification(main, Type.ERROR, "Vui lòng chọn giới tính").showNotification();
			return false;
		}

		String tinh = (String) cmbTinh.getSelectedItem();

		if (tinh.equals(Tinh.getTinhLabel())) {
			new Notification(main, Type.ERROR, "Vui lòng chọn tỉnh/ thành phố").showNotification();
			cmbTinh.showPopup();
			return false;
		}

		String quan = (String) cmbQuan.getSelectedItem();

		if (quan.equals(Quan.getQuanLabel())) {
			new Notification(main, Type.ERROR, "Vui lòng chọn quận/ huyện").showNotification();
			cmbQuan.showPopup();
			return false;
		}

		String phuong = (String) cmbPhuong.getSelectedItem();

		if (phuong.equals(Phuong.getPhuongLabel())) {
			new Notification(main, Type.ERROR, "Vui lòng chọn phường/ xã").showNotification();
			cmbPhuong.showPopup();
			return false;
		}

		String diaChi = txtDiaChiCT.getText().trim();

		if (diaChi.length() <= 0)
			return showThongBaoLoi(txtDiaChiCT, "Vui lòng nhập địa chỉ");

		String luong = txtLuong.getText().trim();

		if (luong.length() <= 0)
			return showThongBaoLoi(txtLuong, "Vui lòng nhập lương");

		if (!Utils.isDouble(luong))
			return showThongBaoLoi(txtLuong, "Lương chỉ chứa các ký tự số");

		double luongNumber = Double.parseDouble(luong);

		if (luongNumber <= 0)
			return showThongBaoLoi(txtLuong, "Lương phải lớn hơn 0");

		return true;
	}

	/**
	 * Xóa rỗng các textfield và làm mới ComboBox
	 */
	private void xoaRong() {
		txtHoTen.setText("");
		txtCCCD.setText("");
		txtSoDienThoai.setText("");
		dateChoose.toDay();
		buttonGroupGioiTinh.clearSelection();
		setTinhToComboBox();
		cmbChucVu.setSelectedIndex(0);
		cmbTrangThai.setSelectedIndex(0);
		cmbQuan.setSelectedIndex(0);
		cmbPhuong.setSelectedIndex(0);
		cmbQuan.setEnabled(false);
		cmbPhuong.setEnabled(false);
		txtDiaChiCT.setText("");
		txtLuong.setText("");
		txtHoTen.requestFocus();
		main.repaint();
	}
}
