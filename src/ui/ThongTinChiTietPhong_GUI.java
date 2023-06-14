package ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.KeyAdapter;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.regex.Pattern;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;

import components.button.Button;
import components.comboBox.ComboBox;
import components.notification.Notification;
import components.textField.TextField;
import connectDB.ConnectDB;
import dao.Phong_DAO;
import entity.Phong;
import entity.Phong.TrangThai;
import utils.Utils;

public class ThongTinChiTietPhong_GUI extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ThongTinChiTietPhong_GUI _this;
	private ComboBox<String> cmbLoaiPhong;
	private ComboBox<String> cmbSoLuong;
	private Phong_DAO phong_DAO;
	private JPanel pnlContent;
	private TextField txtMaPhong;

	/**
	 * Create the frame.
	 * 
	 * @param quanLyPhong_GUI
	 */
	public ThongTinChiTietPhong_GUI(QuanLyPhong_GUI quanLyPhong_GUI, Phong phong) {
		try {
			new ConnectDB().connect();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		_this = this;

		phong_DAO = new Phong_DAO();
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setBounds(0, 0, 482, 300);
		setUndecorated(true);
		setLocationRelativeTo(null);

		pnlContent = new JPanel();
		pnlContent.setBorder(new EmptyBorder(5, 5, 5, 5));
		pnlContent.setLayout(null);
		setContentPane(pnlContent);

		JPanel pnlContainer = new JPanel();
		pnlContainer.setBackground(Utils.secondaryColor);
		pnlContainer.setBounds(0, 0, 482, 300);
		pnlContent.add(pnlContainer);
		pnlContainer.setLayout(null);

		JPanel pnlHeading = new JPanel();
		pnlHeading.setBackground(Utils.primaryColor);
		pnlHeading.setBounds(0, 0, 482, 50);
		pnlContainer.add(pnlHeading);
		pnlHeading.setLayout(null);

		JLabel lblTitle = new JLabel("Sửa phòng");
		lblTitle.setForeground(Color.WHITE);
		lblTitle.setBounds(141, 9, 200, 32);
		lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 24));
		pnlHeading.add(lblTitle);

		JPanel pnlBody = new JPanel();
		pnlBody.setBackground(Utils.secondaryColor);
		pnlBody.setBounds(16, 82, 450, 186);
		pnlContainer.add(pnlBody);
		pnlBody.setLayout(null);

		txtMaPhong = new TextField();
		txtMaPhong.setLabelText("Mã phòng");
		txtMaPhong.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		txtMaPhong.setBackground(Utils.secondaryColor);
		txtMaPhong.setBounds(0, 0, 450, 55);
		pnlBody.add(txtMaPhong);
		txtMaPhong.setColumns(10);

		JPanel pnlActions = new JPanel();
		pnlActions.setBackground(Utils.secondaryColor);
		pnlActions.setBounds(0, 152, 450, 34);
		pnlBody.add(pnlActions);
		pnlActions.setLayout(null);

		Button btnQuayLai = new Button("Quay lại");
		btnQuayLai.setFocusable(false);
		btnQuayLai.setBorder(new EmptyBorder(0, 0, 0, 0));
		btnQuayLai.setRadius(4);
		btnQuayLai.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		btnQuayLai.setColor(Color.WHITE);
		btnQuayLai.setBorderColor(Utils.secondaryColor);
		btnQuayLai.setForeground(Color.BLACK);
		btnQuayLai.setBounds(0, -2, 100, 38);
		pnlActions.add(btnQuayLai);

		Button btnLuu = new Button("Lưu");
		btnLuu.setFocusable(false);
		btnLuu.setBorder(new EmptyBorder(0, 0, 0, 0));
		btnLuu.setRadius(4);
		btnLuu.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		btnLuu.setBackground(Utils.primaryColor, 0.8f, 0.6f);
		btnLuu.setBorderColor(Utils.secondaryColor);
		btnLuu.setForeground(Color.WHITE);
		btnLuu.setBounds(350, -2, 100, 38);
		pnlActions.add(btnLuu);

		cmbLoaiPhong = new ComboBox<String>();
		cmbLoaiPhong.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		cmbLoaiPhong.setModel(new DefaultComboBoxModel<String>(new String[] { "Loại phòng" }));
		cmbLoaiPhong.setBackground(Utils.primaryColor);
		cmbLoaiPhong.setBounds(0, 86, 217, 34);
		pnlBody.add(cmbLoaiPhong);

		cmbSoLuong = new ComboBox<String>();
		cmbSoLuong.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		cmbSoLuong.addItem("Số lượng khách");
		cmbSoLuong.addItem("5");
		cmbSoLuong.addItem("10");
		cmbSoLuong.addItem("20");
		cmbSoLuong.setBackground(Utils.primaryColor);
		cmbSoLuong.setBounds(233, 86, 217, 34);
		pnlBody.add(cmbSoLuong);

		phong_DAO.getAllLoaiPhong().forEach(cmbLoaiPhong::addItem);
		repaint();
		setPhongVaoForm(phong);

//		Sự kiện txtMaPhong
		txtMaPhong.addKeyListener(new KeyAdapter() {
			public void keyPressed(java.awt.event.KeyEvent e) {
				txtMaPhong.setError(false);
			};
		});

//		Sự kiện nút quay lai
		btnQuayLai.addMouseListener(new MouseAdapter() {
			public void mouseClicked(java.awt.event.MouseEvent e) {
				quanLyPhong_GUI.closeJFrameSub();
			};
		});

//		Sự kiện nút lưu
		btnLuu.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (!btnLuu.isEnabled())
					return;
				if (!validator())
					return;

				Phong phong = getPhongTuForm();
				boolean res = phong_DAO.suaPhong(phong);

				if (res) {
					new Notification(_this, components.notification.Notification.Type.SUCCESS,
							"Cập nhật thông tin phòng thành công").showNotification();
					quanLyPhong_GUI.loadTable();
					repaint();
				} else {
					new Notification(_this, components.notification.Notification.Type.ERROR,
							"Cập nhật thông tin phòng thất bại").showNotification();
				}
			}
		});

	}

	/**
	 * Get phòng từ form
	 * 
	 * @return phòng
	 */
	private Phong getPhongTuForm() {
		String maPhong = txtMaPhong.getText();
		String soLuongKhach = cmbSoLuong.getSelectedItem().toString();
		String tenLoai = cmbLoaiPhong.getSelectedItem().toString();
		return new Phong(maPhong, Integer.parseInt(soLuongKhach), TrangThai.Trong, false, tenLoai);
	}

	/**
	 * Set phòng vào form
	 * 
	 * @param phong
	 */
	private void setPhongVaoForm(Phong phong) {
		txtMaPhong.setText(phong.getMaPhong());
		cmbSoLuong.setSelectedItem(String.valueOf(phong.getSoLuongKhach()));
		cmbLoaiPhong.setSelectedItem(phong.getTenLoai());
	}

	private boolean validator() {
		Notification notification;
		String maPhong = txtMaPhong.getText();

		if (maPhong.length() == 0) {
			notification = new Notification(_this, components.notification.Notification.Type.ERROR,
					"Vui lòng nhập mã phòng");
			txtMaPhong.setError(true);
			notification.showNotification();
			return false;
		}

		if (!Pattern.matches("\\d{2}\\.\\d{2}", maPhong)) {
			notification = new Notification(_this, components.notification.Notification.Type.ERROR,
					"Mã phòng phải có dạng XX.YY");
			txtMaPhong.setError(true);
			notification.showNotification();
			return false;
		}

		if (!phong_DAO.isMaPhongTonTai(maPhong)) {
			notification = new Notification(_this, components.notification.Notification.Type.ERROR,
					"Mã phòng chưa tồn tại");
			txtMaPhong.setError(true);
			notification.showNotification();
			return false;
		}

		String loaiPhong = (String) cmbLoaiPhong.getSelectedItem();

		if (loaiPhong.equals("Loại phòng")) {
			notification = new Notification(_this, components.notification.Notification.Type.ERROR,
					"Vui lòng chọn loại phòng");
			notification.showNotification();
			cmbLoaiPhong.showPopup();
			return false;
		}

		String soLuong = (String) cmbSoLuong.getSelectedItem();

		if (soLuong.equals("Số lượng khách")) {
			notification = new Notification(_this, components.notification.Notification.Type.ERROR,
					"Vui lòng chọn số lượng khách");
			notification.showNotification();
			cmbSoLuong.showPopup();
			return false;
		}

		return true;
	}
}
