package ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.metal.MetalButtonUI;

import components.button.Button;
import components.notification.Notification;
import components.notification.Notification.Type;
import components.textField.TextField;
import dao.DichVu_DAO;
import entity.DichVu;
import utils.Utils;

public class ThemDichVu_GUI extends JPanel implements ItemListener {
	private static final long serialVersionUID = 1L;
	private JComboBox<String> cmbLoaiDichVu;
	private DichVu_DAO dichVu_DAO;
	private Main main;
	private TextField txtMa, txtTen, txtDonViTinh, txtSoLuong, txtGiaMua;
	private final int widthPnlContainer = 948;

	public ThemDichVu_GUI(Main jFrame) {
		main = jFrame;
		dichVu_DAO = new DichVu_DAO();

		setBackground(Utils.secondaryColor);
		setBounds(0, 0, Utils.getScreenWidth(), Utils.getBodyHeight());
		setLayout(null);

		JPanel pnlContainer = new JPanel();
		pnlContainer.setBackground(Utils.secondaryColor);
		// pnlContainer.setBounds(0, 0, 1100, 500);
		pnlContainer.setBounds(Utils.getLeft(widthPnlContainer), 0, widthPnlContainer, Utils.getBodyHeight());
		this.add(pnlContainer);
		pnlContainer.setLayout(null);

		txtMa = new TextField();
		txtMa.setLabelText("Mã dịch vụ");
		txtMa.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		txtMa.setBackground(Utils.secondaryColor);
		txtMa.setBounds(44, 25, 842, 55);
		pnlContainer.add(txtMa);

		txtTen = new TextField();
		txtTen.setLabelText("Tên dịch vụ");
		txtTen.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		txtTen.setBackground(Utils.secondaryColor);
		txtTen.setBounds(44, 105, 842, 55);
		pnlContainer.add(txtTen);

		txtDonViTinh = new TextField();
		txtDonViTinh.setLabelText("Đơn vị tính");
		txtDonViTinh.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		txtDonViTinh.setBackground(Utils.secondaryColor);
		txtDonViTinh.setBounds(44, 195, 371, 55);
		pnlContainer.add(txtDonViTinh);

		txtSoLuong = new TextField();
		txtSoLuong.setLabelText("Số lượng");
		txtSoLuong.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		txtSoLuong.setBackground(Utils.secondaryColor);
		txtSoLuong.setBounds(516, 195, 371, 55);
		pnlContainer.add(txtSoLuong);

		cmbLoaiDichVu = new JComboBox<String>();
		List<String> listLoaiDV = dichVu_DAO.getAllLoaiDichVu();
		for (String loaiDV : listLoaiDV)
			cmbLoaiDichVu.addItem(loaiDV);
		cmbLoaiDichVu.setFont(new Font("Segoe UI", Font.PLAIN, 20));
		cmbLoaiDichVu.setBackground(Utils.primaryColor);
		cmbLoaiDichVu.setBounds(44, 290, 371, 45);
		pnlContainer.add(cmbLoaiDichVu);

		txtGiaMua = new TextField();
		txtGiaMua.setLabelText("Giá mua");
		txtGiaMua.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		txtGiaMua.setBackground(Utils.secondaryColor);
		txtGiaMua.setBounds(516, 285, 371, 50);
		pnlContainer.add(txtGiaMua);

		Button btnLuu = new Button("Lưu");
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
		btnLuu.setBounds(501, 420, 250, 50);
		pnlContainer.add(btnLuu);

		btnLuu.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (!validator())
					return;
				String sma = txtMa.getText();
				String sten = txtTen.getText();
				int sSoLuong = Integer.valueOf(txtSoLuong.getText());
				String sDonViTinh = txtDonViTinh.getText();
				double sGiaMua = Double.valueOf(txtGiaMua.getText());
				String sLoaiDichVu = cmbLoaiDichVu.getSelectedItem().toString();
				if (dichVu_DAO.themDichVu(new DichVu(sma, sten, sSoLuong, sDonViTinh, sGiaMua, false, sLoaiDichVu))) {
					new Notification(jFrame, components.notification.Notification.Type.SUCCESS,
							"Đã thêm dịch vụ mới thành công").showNotification();
					xoaRong();

				} else {
					new Notification(jFrame, components.notification.Notification.Type.ERROR,
							"Đã thêm dịch vụ mới thất bại").showNotification();
				}
			}
		});

		Button btnHuy = new Button("Hủy");
		btnHuy.setUI(new MetalButtonUI() {
			protected Color getDisabledTextColor() {
				return Color.WHITE;
			}
		});
		btnHuy.setIcon(Utils.getImageIcon("cancelled 1.png"));
		btnHuy.setRadius(8);
		btnHuy.setForeground(Color.WHITE);
		btnHuy.setFont(new Font("Segoe UI", Font.PLAIN, 32));
		btnHuy.setColorOver(new Color(140, 177, 180));
		btnHuy.setColorClick(new Color(140, 177, 180, 204));
		btnHuy.setColor(new Color(140, 177, 180));
		btnHuy.setBorderColor(Utils.secondaryColor);
		btnHuy.setBorder(new EmptyBorder(0, 0, 0, 0));
		btnHuy.setBounds(201, 420, 250, 50);
		pnlContainer.add(btnHuy);
// sự kiện nút hủy
		btnHuy.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				main.backPanel();

			}
		});
//		Sự kiện txtTen
		txtTen.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				txtTen.setError(false);
			}
		});
//		Sự kiện txtGiaMua
		txtGiaMua.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				txtGiaMua.setError(false);
			}
		});
//		Sự kiện txtDonViTinh
		txtDonViTinh.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				txtDonViTinh.setError(false);
			}
		});
//		Sự kiện txtSoLuong
		txtSoLuong.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				txtSoLuong.setError(false);
			}
		});
		txtMa.setEnabled(false);
		txtMa.setText(dichVu_DAO.getMaDichVu());

	}

	@Override
	public void itemStateChanged(ItemEvent e) {
		if (e.getStateChange() != ItemEvent.SELECTED) {
			return;
		}
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
	 * Kiểm tra thông tin dịch vụ
	 *
	 * @return true nếu thông tin dịch vụ hợp lệ
	 */
	private boolean validator() {

		String ten = txtTen.getText().trim();

		if (ten.length() <= 0)
			return showThongBaoLoi(txtTen, "Vui lòng nhập tên dịch vụ");

		String donViTinh = txtDonViTinh.getText().trim();

		if (donViTinh.length() <= 0)
			return showThongBaoLoi(txtDonViTinh, "Vui lòng nhập đơn vị tính");

		String soLuong = txtSoLuong.getText().trim();

		if (soLuong.length() <= 0)
			return showThongBaoLoi(txtSoLuong, "Vui lòng nhập số lượng");

		if (!Utils.isInteger(soLuong))
			return showThongBaoLoi(txtSoLuong, "Số lượng phải là số nguyên");

		String giaMua = txtGiaMua.getText().trim();

		if (giaMua.length() <= 0)
			return showThongBaoLoi(txtGiaMua, "Vui lòng nhập giá mua");

		if (!Utils.isDouble(giaMua))
			return showThongBaoLoi(txtGiaMua, "Giá mua phải là số");

		return true;
	}

	/**
	 * Xóa rỗng các textfield và làm mới ComboBox
	 */
	private void xoaRong() {
		txtTen.setText("");
		txtDonViTinh.setText("");
		txtSoLuong.setText("");
		txtGiaMua.setText("");
		txtTen.requestFocus();
		txtMa.setText(dichVu_DAO.getMaDichVu());
		cmbLoaiDichVu.setSelectedIndex(0);
		main.repaint();
	}

}
