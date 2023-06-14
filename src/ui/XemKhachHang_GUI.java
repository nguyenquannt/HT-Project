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
import components.radio.RadioButtonCustom;
import components.textField.TextField;
import dao.DiaChi_DAO;
import dao.KhachHang_DAO;
import entity.KhachHang;
import entity.Phuong;
import entity.Quan;
import entity.Tinh;
import utils.Utils;

public class XemKhachHang_GUI extends JPanel implements ItemListener {
	private static final long serialVersionUID = 1L;
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
	@SuppressWarnings("unused")
	private Phuong phuong;
	private Quan quan;
	private RadioButtonCustom radNam, radNu;
	private Tinh tinh;
	private TextField txtMa, txtTen, txtCCCD, txtSDT, txtNgaySinh, txtDiaChiCT;

	public XemKhachHang_GUI(Main jFrame, KhachHang khachHang) {
		main = jFrame;
		khachHang_DAO = new KhachHang_DAO();
		DiaChi_DAO = new DiaChi_DAO();
		this.khachHang = khachHang_DAO.getKhachHangTheoMa(khachHang.getMaKhachHang());
		int padding = (int) Math.ceil((Utils.getBodyHeight() - 365) * 1.0 / 7);
		int top = padding;
		int left = Utils.getLeft(792);

		setBackground(Utils.secondaryColor);
		setBounds(0, 0, Utils.getScreenWidth(), Utils.getBodyHeight());
		setLayout(null);

		JPanel pnlRow1 = new JPanel();
		pnlRow1.setBounds(left, top, 792, 55);
		top += padding + 55;
		pnlRow1.setBackground(Utils.secondaryColor);
		pnlRow1.setLayout(null);
		add(pnlRow1);

		txtMa = new TextField();
		txtMa.setLabelText("Mã khách hàng:");
		txtMa.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		txtMa.setBackground(Utils.secondaryColor);
		txtMa.setBounds(0, 0, 371, 55);
		pnlRow1.add(txtMa);

		txtTen = new TextField();
		txtTen.setLabelText("Họ tên khách hàng:");
		txtTen.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		txtTen.setBackground(Utils.secondaryColor);
		txtTen.setBounds(421, 0, 371, 55);
		pnlRow1.add(txtTen);

		JPanel pnlRow2 = new JPanel();
		pnlRow2.setBounds(left, top, 792, 55);
		top += padding + 55;
		pnlRow2.setBackground(Utils.secondaryColor);
		pnlRow2.setLayout(null);
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
		pnlRow3.setBounds(left, top, 792, 55);
		top += padding + 55;
		pnlRow3.setBackground(Utils.secondaryColor);
		pnlRow3.setLayout(null);
		add(pnlRow3);

		JPanel pnlGioiTinh = new JPanel();
		pnlGioiTinh.setBackground(Utils.secondaryColor);
		pnlGioiTinh.setBounds(0, 0, 371, 55);
		pnlRow3.add(pnlGioiTinh);
		pnlGioiTinh.setLayout(null);

		JLabel lblGioiTinh = new JLabel("Giới tính:");
		lblGioiTinh.setForeground(Utils.labelTextField);
		lblGioiTinh.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		lblGioiTinh.setBounds(2, 0, 371, 19);
		pnlGioiTinh.add(lblGioiTinh);

		JPanel pnlGroupGioiTinh = new JPanel();
		pnlGroupGioiTinh.setBackground(Utils.secondaryColor);
		pnlGroupGioiTinh.setBounds(2, 30, 138, 16);
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
		radNam.setSelected(true);

		ButtonGroup buttonGroupGioiTinh = new ButtonGroup();
		buttonGroupGioiTinh.add(radNam);
		buttonGroupGioiTinh.add(radNu);

		txtSDT = new TextField();
		txtSDT.setLabelText("Số điện thoại:");
		txtSDT.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		txtSDT.setBackground(Utils.secondaryColor);
		txtSDT.setBounds(421, 0, 371, 55);
		pnlRow3.add(txtSDT);

		JPanel pnlRow4 = new JPanel();
		pnlRow4.setBounds(left, top, 792, 65);
		top += padding + 65;
		pnlRow4.setBackground(Utils.secondaryColor);
		pnlRow4.setLayout(null);
		add(pnlRow4);

		JLabel lblDiaChi = new JLabel("Địa chỉ:");
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
		cmbTinh.setBackground(Utils.primaryColor);
		cmbTinh.setBounds(0, 29, 220, 36);
		pnlRow4.add(cmbTinh);

		cmbQuan = new ComboBox<String>();
		String tinhSelected = (String) cmbTinh.getSelectedItem();
		Tinh tinh = DiaChi_DAO.getTinh(tinhSelected);
		XemKhachHang_GUI.this.tinh = tinh;
		ArrayList<Quan> listQuan = (ArrayList<Quan>) DiaChi_DAO.getQuan(XemKhachHang_GUI.this.tinh);
		for (Quan quan : listQuan) {
			cmbQuan.addItem(quan.getQuan());
		}

		cmbQuan.setFont(new Font("Segoe UI", Font.PLAIN, 20));
		cmbQuan.setBackground(new Color(140, 177, 180));
		cmbQuan.setBounds(250, 29, 220, 36);
		pnlRow4.add(cmbQuan);

		cmbPhuong = new ComboBox<String>();
		cmbQuan.setModel(new DefaultComboBoxModel<String>());
		cmbPhuong.setFont(new Font("Segoe UI", Font.PLAIN, 20));
		cmbPhuong.setBackground(new Color(140, 177, 180));
		cmbPhuong.setBounds(500, 29, 220, 36);
		pnlRow4.add(cmbPhuong);

		JPanel pnlRow5 = new JPanel();
		pnlRow5.setBounds(left, top, 792, 55);
		top += padding + 55;
		pnlRow5.setBackground(Utils.secondaryColor);
		pnlRow5.setLayout(null);
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
		pnlActions.setBounds(left, top, 792, 50);
		pnlActions.setBackground(Utils.secondaryColor);
		pnlActions.setLayout(null);
		add(pnlActions);

		Button btnCapNhat = new Button("Cập nhật");
		btnCapNhat.setUI(new MetalButtonUI() {
			protected Color getDisabledTextColor() {
				return Color.WHITE;
			}
		});
		btnCapNhat.setIcon(Utils.getImageIcon("floppy-disk 1.png"));
		btnCapNhat.setRadius(8);
		btnCapNhat.setForeground(Color.WHITE);
		btnCapNhat.setFont(new Font("Segoe UI", Font.PLAIN, 32));
		btnCapNhat.setColorOver(new Color(140, 177, 180));
		btnCapNhat.setColorClick(new Color(140, 177, 180, 204));
		btnCapNhat.setColor(new Color(140, 177, 180));
		btnCapNhat.setBorderColor(Utils.secondaryColor);
		btnCapNhat.setBorder(new EmptyBorder(0, 0, 0, 0));
		btnCapNhat.setBounds(320, -2, 254, 54);
		pnlActions.add(btnCapNhat);

		btnCapNhat.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {

				main.addPnlBody(new CapNhatKhachHang_GUI(main, khachHang), "Cập nhật khách hàng", 2, 0);
			}
		});

		cmbTinh.addItemListener(this);
		cmbQuan.addItemListener(this);
		cmbPhuong.addItemListener(this);

		setKhachHangVaoForm(this.khachHang);
		setEnabledForm(false);
	}

	@SuppressWarnings("unused")
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
			phuong = null;

			if (tinhSelected.equals(Tinh.getTinhLabel())) {
				cmbQuan.setSelectedIndex(0);
				cmbQuan.setEnabled(false);
				tinh = null;
				return;
			}
			Tinh tinh = DiaChi_DAO.getTinh(tinhSelected);
			XemKhachHang_GUI.this.tinh = tinh;
			setQuanToComboBox(XemKhachHang_GUI.this.tinh);
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
				Quan quan = DiaChi_DAO.getQuan(tinh, quanSelected);
				XemKhachHang_GUI.this.quan = quan;
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

			Phuong phuong = DiaChi_DAO.getPhuong(quan, phuongSelect);
			XemKhachHang_GUI.this.phuong = phuong;
			isEnabledEventPhuong = false;
		}

	}

	@SuppressWarnings("unchecked")
	private <E> ComboBox<E> resizeComboBox(ComboBox<E> list, String firstLabel) {
		list.removeAllItems();
		list.addItem((E) firstLabel);
		return list;
	}

	private void setEnabledForm(boolean b) {
		txtMa.setEnabled(b);
		txtTen.setEnabled(b);
		txtCCCD.setEnabled(b);
		txtDiaChiCT.setEnabled(b);
		txtNgaySinh.setEnabled(b);
		txtSDT.setEnabled(b);
		radNam.setEnabled(b);
		radNu.setEnabled(b);
		cmbTinh.setEnabled(b);
		cmbQuan.setEnabled(b);
		cmbPhuong.setEnabled(b);

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
			if (phuong.getId().equals(XemKhachHang_GUI.this.khachHang.getPhuong().getId())) {
				cmbPhuong.setSelectedIndex(index + 1);
				XemKhachHang_GUI.this.phuong = phuong;
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
			if (quan.getId().equals(XemKhachHang_GUI.this.khachHang.getQuan().getId())) {
				cmbQuan.setSelectedIndex(index + 1);
				XemKhachHang_GUI.this.quan = quan;
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
			if (tinh.getId().equals(XemKhachHang_GUI.this.khachHang.getTinh().getId())) {
				cmbTinh.setSelectedIndex(index + 1);
				XemKhachHang_GUI.this.tinh = tinh;
			}
		});

		isEnabledEventTinh = true;
	}

	@SuppressWarnings("unused")
	private boolean validator() {
		return true;
	}

}
