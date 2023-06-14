package ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.metal.MetalButtonUI;

import com.raven.datechooser.DateChooser;

import components.button.Button;
import components.comboBox.ComboBox;
import components.notification.Notification;
import components.radio.RadioButtonCustom;
import components.textField.TextField;
import dao.DiaChi_DAO;
import dao.KhachHang_DAO;
import entity.KhachHang;
import entity.Phuong;
import entity.Quan;
import entity.Tinh;
import utils.Utils;

public class CapNhatKhachHang_GUI extends JPanel implements ItemListener {
	private static final long serialVersionUID = 1L;
	private Button btnLuu;
	private ComboBox<String> cmbPhuong;
	private ComboBox<String> cmbQuan;
	private ComboBox<String> cmbTinh;
	private DateChooser dateChoose;
	private DiaChi_DAO DiaChi_DAO;
	private boolean isEnabledEventPhuong = false;
	private boolean isEnabledEventQuan = false;
	private boolean isEnabledEventTinh = false;
	private KhachHang khachHang;
	private KhachHang_DAO khachHang_DAO;
	private Main main;
	private Quan quan;
	private RadioButtonCustom radNam;
	private RadioButtonCustom radNu;
	private Tinh tinh;
	private TextField txtCCCD;
	private TextField txtDiaChiCT;
	private TextField txtMa;
	private TextField txtNgaySinh;
	private TextField txtSDT;
	private TextField txtTen;

	public CapNhatKhachHang_GUI(Main jFrame, KhachHang khachHang) {
		main = jFrame;
		khachHang_DAO = new KhachHang_DAO();
		DiaChi_DAO = new DiaChi_DAO();
		this.khachHang = khachHang_DAO.getKhachHangTheoMa(khachHang.getMaKhachHang());

		setBackground(Utils.secondaryColor);
		setBounds(0, 0, Utils.getScreenWidth(), Utils.getBodyHeight());
		setLayout(null);

		int padding = Math.min((Utils.getBodyHeight() - 365) / 7, 50);
		int top = padding;
		int left = Utils.getLeft(792);

		JPanel pnlRow1 = new JPanel();
		pnlRow1.setBackground(Utils.secondaryColor);
		pnlRow1.setBounds(left, top, 792, 55);
		top += padding + 55;
		add(pnlRow1);
		pnlRow1.setLayout(null);

		txtMa = new TextField();
		txtMa.setBounds(0, 0, 371, 55);
		txtMa.setLabelText("Mã khách hàng:");
		txtMa.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		txtMa.setEnabled(false);
		txtMa.setBackground(Utils.secondaryColor);
		pnlRow1.add(txtMa);

		txtTen = new TextField();
		txtTen.setBounds(421, 0, 371, 55);
		txtTen.setLabelText("Họ tên khách hàng:");
		txtTen.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		txtTen.setBackground(Utils.secondaryColor);
		pnlRow1.add(txtTen);

		JPanel pnlRow2 = new JPanel();
		pnlRow2.setLayout(null);
		pnlRow2.setBackground(Utils.secondaryColor);
		pnlRow2.setBounds(left, top, 792, 55);
		top += padding + 55;
		add(pnlRow2);

		txtCCCD = new TextField();
		txtCCCD.setLabelText("Căn cước công dân:");
		txtCCCD.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		txtCCCD.setBackground(Utils.secondaryColor);
		txtCCCD.setBounds(0, 0, 371, 55);
		pnlRow2.add(txtCCCD);

		txtNgaySinh = new TextField();
		txtNgaySinh.setIcon(Utils.getImageIcon("add-event 2.png"));
		txtNgaySinh.setLineColor(new Color(149, 166, 248));
		txtNgaySinh.setLabelText("Ngày sinh:");
		txtNgaySinh.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		txtNgaySinh.setColumns(10);
		txtNgaySinh.setBackground(Utils.secondaryColor);
		txtNgaySinh.setBounds(421, 0, 371, 55);
		pnlRow2.add(txtNgaySinh);
		dateChoose = new DateChooser();
		dateChoose.setDateFormat("dd/MM/yyyy");
		dateChoose.setTextRefernce(txtNgaySinh);

		JPanel pnlRow3 = new JPanel();
		pnlRow3.setLayout(null);
		pnlRow3.setBackground(Utils.secondaryColor);
		pnlRow3.setBounds(left, top, 792, 55);
		top += padding + 55;
		add(pnlRow3);

		JPanel pnlGioiTinh = new JPanel();
		pnlGioiTinh.setLayout(null);
		pnlGioiTinh.setBackground(Utils.secondaryColor);
		pnlGioiTinh.setBounds(0, 0, 371, 55);
		pnlRow3.add(pnlGioiTinh);

		JLabel lblGioiTinh = new JLabel("Giới tính:");
		lblGioiTinh.setForeground(new Color(150, 150, 150));
		lblGioiTinh.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		lblGioiTinh.setBounds(2, 0, 371, 19);
		pnlGioiTinh.add(lblGioiTinh);

		JPanel pnlGroupGioiTinh = new JPanel();
		pnlGroupGioiTinh.setLayout(null);
		pnlGroupGioiTinh.setBackground(Utils.secondaryColor);
		pnlGroupGioiTinh.setBounds(2, 30, 138, 16);
		pnlGioiTinh.add(pnlGroupGioiTinh);

		radNam = new RadioButtonCustom("Nam");
		radNam.setSelected(true);
		radNam.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		radNam.setFocusable(false);
		radNam.setBackground(Utils.secondaryColor);
		radNam.setBounds(0, -2, 59, 21);
		pnlGroupGioiTinh.add(radNam);

		radNu = new RadioButtonCustom("Nữ");
		radNu.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		radNu.setFocusable(false);
		radNu.setBackground(Utils.secondaryColor);
		radNu.setBounds(79, -2, 59, 21);
		pnlGroupGioiTinh.add(radNu);

		ButtonGroup buttonGroupGioiTinh = new ButtonGroup();
		buttonGroupGioiTinh.add(radNu);
		buttonGroupGioiTinh.add(radNam);

		txtSDT = new TextField();
		txtSDT.setLabelText("Số điện thoại:");
		txtSDT.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		txtSDT.setBackground(Utils.secondaryColor);
		txtSDT.setBounds(421, 0, 371, 50);
		pnlRow3.add(txtSDT);

		JPanel pnlRow4 = new JPanel();
		pnlRow4.setLayout(null);
		pnlRow4.setBackground(Utils.secondaryColor);
		pnlRow4.setBounds(left, top, 792, 65);
		top += padding + 65;
		add(pnlRow4);

		JLabel lblDiaChi = new JLabel("Địa chỉ:");
		lblDiaChi.setForeground(new Color(150, 150, 150));
		lblDiaChi.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		lblDiaChi.setBounds(0, 0, 200, 19);
		lblDiaChi.setForeground(Utils.labelTextField);
		pnlRow4.add(lblDiaChi);

		cmbTinh = new ComboBox<String>();
		ArrayList<Tinh> listTinh = (ArrayList<Tinh>) DiaChi_DAO.getTinh();
		for (Tinh tinh : listTinh) {
			cmbTinh.addItem(tinh.getTinh());
		}
		cmbTinh.setFont(new Font("Segoe UI", Font.PLAIN, 20));
		cmbTinh.setBackground(new Color(140, 177, 180));
		cmbTinh.setBounds(0, 29, 220, 36);
		pnlRow4.add(cmbTinh);

		cmbQuan = new ComboBox<String>();
		cmbQuan.setModel(new DefaultComboBoxModel<String>());
		cmbQuan.setFont(new Font("Segoe UI", Font.PLAIN, 20));
		cmbQuan.setBackground(new Color(140, 177, 180));
		cmbQuan.setBounds(250, 29, 220, 36);
		pnlRow4.add(cmbQuan);

		String tinhSelected = (String) cmbTinh.getSelectedItem();
		Tinh tinh = DiaChi_DAO.getTinh(tinhSelected);
		CapNhatKhachHang_GUI.this.tinh = tinh;
		ArrayList<Quan> listQuan = (ArrayList<Quan>) DiaChi_DAO.getQuan(CapNhatKhachHang_GUI.this.tinh);
		for (Quan quan : listQuan) {
			cmbQuan.addItem(quan.getQuan());
		}

		cmbPhuong = new ComboBox<String>();
		cmbPhuong.setFont(new Font("Segoe UI", Font.PLAIN, 20));
		cmbPhuong.setBackground(new Color(140, 177, 180));
		cmbPhuong.setBounds(500, 29, 220, 36);
		pnlRow4.add(cmbPhuong);

		JPanel pnlRow5 = new JPanel();
		pnlRow5.setLayout(null);
		pnlRow5.setBackground(Utils.secondaryColor);
		pnlRow5.setBounds(left, top, 792, 55);
		top += padding + 55;
		add(pnlRow5);

		txtDiaChiCT = new TextField();
		txtDiaChiCT.setLineColor(new Color(149, 200, 248));
		txtDiaChiCT.setLabelText("Địa chỉ cụ thể");
		txtDiaChiCT.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		txtDiaChiCT.setColumns(10);
		txtDiaChiCT.setBackground(Utils.secondaryColor);
		txtDiaChiCT.setBounds(0, 0, 371, 55);
		pnlRow5.add(txtDiaChiCT);

		JPanel pnlActions = new JPanel();
		pnlActions.setLayout(null);
		pnlActions.setBackground(Utils.secondaryColor);
		pnlActions.setBounds(left, top, 792, 50);
		add(pnlActions);

		btnLuu = new Button("Lưu");
		btnLuu.setUI(new MetalButtonUI() {
			protected Color getDisabledTextColor() {
				return Color.WHITE;
			}
		});
		btnLuu.setIcon(Utils.getImageIcon("floppy-disk 1.png"));
		btnLuu.setRadius(8);
		btnLuu.setForeground(Color.WHITE);
		btnLuu.setFont(new Font("Segoe UI", Font.PLAIN, 32));
		btnLuu.setColorOver(new Color(140, 177, 180));
		btnLuu.setColorClick(new Color(140, 177, 180, 204));
		btnLuu.setColor(new Color(140, 177, 180));
		btnLuu.setBorderColor(Utils.secondaryColor);
		btnLuu.setBorder(new EmptyBorder(0, 0, 0, 0));
		btnLuu.setBounds(300, 0, 250, 50);
		pnlActions.add(btnLuu);

		btnLuu.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (khachHang_DAO.suaKhachHang(getKhachHangTuForm())) {
					new Notification(main, components.notification.Notification.Type.SUCCESS,
							"Cập nhật thông tin khách hàng mới thành công").showNotification();
				} else
					new Notification(main, components.notification.Notification.Type.ERROR,
							"Cập nhật thông tin khách hàng thất bại").showNotification();
			}
		});

		cmbTinh.addItemListener(this);
		cmbQuan.addItemListener(this);
		cmbPhuong.addItemListener(this);

		setKhachHangVaoForm(this.khachHang);
		txtMa.setEnabled(false);
	}

	private KhachHang getKhachHangTuForm() {
		String sma = txtMa.getText();
		String sten = txtTen.getText();
		LocalDate sngaySinh = Utils.getLocalDate(txtNgaySinh.getText());
		Boolean gioiTinh = radNam.isSelected();
		String sCCCD = txtCCCD.getText();
		String sSDT = txtSDT.getText();
		String sTinh = cmbTinh.getSelectedItem().toString();
		String sQuan = cmbQuan.getSelectedItem().toString();
		String sPhuong = cmbPhuong.getSelectedItem().toString();
		String sDCCT = txtDiaChiCT.getText();
		return new KhachHang(sma, sten, sCCCD, sngaySinh, gioiTinh, sSDT, DiaChi_DAO.getTinh(sTinh),
				DiaChi_DAO.getQuan(tinh, sQuan), DiaChi_DAO.getPhuong(quan, sPhuong), sDCCT, false);
	}

	@Override
	public void itemStateChanged(ItemEvent e) {
		Object object = e.getSource();
		if (e.getStateChange() != ItemEvent.SELECTED) {
			return;
		}
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

			if (tinhSelected.equals(Tinh.getTinhLabel())) {
				cmbQuan.setSelectedIndex(0);
				cmbQuan.setEnabled(false);
				tinh = null;
				return;
			}
			Tinh tinh = DiaChi_DAO.getTinh(tinhSelected);
			CapNhatKhachHang_GUI.this.tinh = tinh;
			setQuanToComboBox(CapNhatKhachHang_GUI.this.tinh);
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

			if (quanSelected.equals(Quan.getQuanLabel())) {
				cmbPhuong.setSelectedIndex(0);
				cmbPhuong.setEnabled(false);
				quan = null;
			} else {
				Quan quan = DiaChi_DAO.getQuan(tinh, quanSelected);
				CapNhatKhachHang_GUI.this.quan = quan;
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
				return;
			}

			isEnabledEventPhuong = false;
		}

	}

	@SuppressWarnings("unchecked")
	private <E> ComboBox<E> resizeComboBox(ComboBox<E> list, String firstLabel) {
		list.removeAllItems();
		list.addItem((E) firstLabel);
		return list;
	}

	private void setKhachHangVaoForm(KhachHang khachHang) {
		txtMa.setText(khachHang.getMaKhachHang());
		txtCCCD.setText(khachHang.getCccd());
		txtDiaChiCT.setText(khachHang.getDiaChiCuThe());
		txtTen.setText(khachHang.getHoTen());
		txtNgaySinh.setText(Utils.formatDate(khachHang.getNgaySinh()));
		txtSDT.setText(khachHang.getSoDienThoai());
		setTinhToComboBox();
		setQuanToComboBox(tinh);
		setPhuongToComboBox(quan);
		if (khachHang.isGioiTinh())
			radNam.setSelected(true);
		else
			radNu.setSelected(true);
	}

	private void setPhuongToComboBox(Quan quan) {
		isEnabledEventPhuong = false;

		resizeComboBox(cmbPhuong, Phuong.getPhuongLabel());

		List<Phuong> phuongs = DiaChi_DAO.getPhuong(quan);

		phuongs.sort(new Comparator<Phuong>() {

			@Override
			public int compare(Phuong o1, Phuong o2) {
				return o1.getPhuong().compareToIgnoreCase(o2.getPhuong());
			}
		});

		phuongs.forEach(phuong -> {
			int index = phuongs.indexOf(phuong);
			cmbPhuong.addItem(phuong.getPhuong());
			if (phuong.getId().equals(CapNhatKhachHang_GUI.this.khachHang.getPhuong().getId())) {
				cmbPhuong.setSelectedIndex(index + 1);
			}
		});

		isEnabledEventPhuong = true;
	}

	private void setQuanToComboBox(Tinh tinh) {
		isEnabledEventQuan = false;

		resizeComboBox(cmbQuan, Quan.getQuanLabel());
		List<Quan> quans = DiaChi_DAO.getQuan(tinh);
		quans.sort(new Comparator<Quan>() {
			@Override
			public int compare(Quan o1, Quan o2) {
				return o1.getQuan().compareToIgnoreCase(o2.getQuan());
			}
		});
		quans.forEach(quan -> {
			int index = quans.indexOf(quan);
			cmbQuan.addItem(quan.getQuan());
			if (quan.getId().equals(CapNhatKhachHang_GUI.this.khachHang.getQuan().getId())) {
				cmbQuan.setSelectedIndex(index + 1);
				CapNhatKhachHang_GUI.this.quan = quan;
			}
		});

		isEnabledEventQuan = true;
	}

	private void setTinhToComboBox() {
		isEnabledEventTinh = false;

		resizeComboBox(cmbTinh, Tinh.getTinhLabel());

		List<Tinh> tinhs = DiaChi_DAO.getTinh();

		tinhs.sort(new Comparator<Tinh>() {
			@Override
			public int compare(Tinh o1, Tinh o2) {
				return o1.getTinh().compareToIgnoreCase(o2.getTinh());
			}
		});

		tinhs.forEach(tinh -> {
			int index = tinhs.indexOf(tinh);
			cmbTinh.addItem(tinh.getTinh());
			if (tinh.getId().equals(CapNhatKhachHang_GUI.this.khachHang.getTinh().getId())) {
				cmbTinh.setSelectedIndex(index + 1);
				CapNhatKhachHang_GUI.this.tinh = tinh;
			}
		});

		isEnabledEventTinh = true;
	}
}
