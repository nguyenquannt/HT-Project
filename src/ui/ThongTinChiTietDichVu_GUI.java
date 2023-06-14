package ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.metal.MetalButtonUI;

import components.button.Button;
import components.comboBox.ComboBox;
import components.notification.Notification;
import components.notification.Notification.Type;
import components.textField.TextField;
import dao.DichVu_DAO;
import entity.DichVu;
import utils.Utils;

public class ThongTinChiTietDichVu_GUI extends JPanel implements ItemListener {
	private static final long serialVersionUID = 1L;
	private ComboBox<String> cmbLoaiDichVu;
	@SuppressWarnings("unused")
	private DichVu dichVu;
	private DichVu_DAO dichVu_DAO;
	private Main main;
	private TextField txtMa, txtTen, txtDonViTinh, txtSoLuong, txtGiaMua;
	private final int widthPnlContainer = 948;

	public ThongTinChiTietDichVu_GUI(Main jFrame, DichVu dichVu, boolean isCapNhat) {

		main = jFrame;
		dichVu_DAO = new DichVu_DAO();
		setBackground(Utils.secondaryColor);

		setBounds(0, 0, Utils.getScreenWidth(), Utils.getBodyHeight());

		setLayout(null);

		JPanel pnlContainer = new JPanel();
		pnlContainer.setBackground(Utils.secondaryColor);
		pnlContainer.setBounds(Utils.getLeft(widthPnlContainer), 0, widthPnlContainer, Utils.getBodyHeight());
		this.add(pnlContainer);
		pnlContainer.setLayout(null);

		txtMa = new TextField();
		txtMa.setLabelText("Mã dịch vụ:");
		txtMa.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		txtMa.setBackground(Utils.secondaryColor);
		txtMa.setBounds(44, 25, 842, 55);
		pnlContainer.add(txtMa);

		txtTen = new TextField();
		txtTen.setLabelText("Tên dịch vụ:");
		txtTen.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		txtTen.setBackground(Utils.secondaryColor);
		txtTen.setBounds(44, 105, 842, 55);
		pnlContainer.add(txtTen);

		txtDonViTinh = new TextField();
		txtDonViTinh.setLabelText("Đơn vị tính:");
		txtDonViTinh.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		txtDonViTinh.setBackground(Utils.secondaryColor);
		txtDonViTinh.setBounds(44, 195, 371, 55);
		pnlContainer.add(txtDonViTinh);

		txtSoLuong = new TextField();
		txtSoLuong.setLabelText("Số lượng:");
		txtSoLuong.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		txtSoLuong.setBackground(Utils.secondaryColor);
		txtSoLuong.setBounds(516, 195, 371, 55);
		pnlContainer.add(txtSoLuong);

		cmbLoaiDichVu = new ComboBox<String>();
		List<String> listLoaiDV = dichVu_DAO.getAllLoaiDichVu();
		for (String loaiDV : listLoaiDV)
			cmbLoaiDichVu.addItem(loaiDV);
		cmbLoaiDichVu.setFont(new Font("Segoe UI", Font.PLAIN, 20));
		cmbLoaiDichVu.setBackground(Utils.primaryColor);
		cmbLoaiDichVu.setBounds(44, 290, 371, 45);
		pnlContainer.add(cmbLoaiDichVu);

		txtGiaMua = new TextField();
		txtGiaMua.setLabelText("Giá mua:");
		txtGiaMua.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		txtGiaMua.setBackground(Utils.secondaryColor);
		txtGiaMua.setBounds(516, 285, 371, 50);
		pnlContainer.add(txtGiaMua);

		Button btnCapNhat = new Button("Cập nhật");
		btnCapNhat.setIcon(Utils.getImageIcon("edit 1.png"));
		btnCapNhat.setFocusable(false);
		btnCapNhat.setRadius(8);
		btnCapNhat.setBorderColor(Utils.secondaryColor);
		btnCapNhat.setBorder(new EmptyBorder(0, 0, 0, 0));
		btnCapNhat.setColor(Utils.primaryColor);
		btnCapNhat.setColorOver(Utils.primaryColor);
		btnCapNhat.setColorClick(Utils.getOpacity(Utils.primaryColor, 0.8f));
		btnCapNhat.setForeground(Color.WHITE);
		btnCapNhat.setFont(new Font("Segoe UI", Font.PLAIN, 32));
		btnCapNhat.setBounds(200, 420, 250, 50);
		pnlContainer.add(btnCapNhat);

		Button btnHuy = new Button("Hủy");
		btnHuy.setIcon(Utils.getImageIcon("cancelled 1.png"));
		btnHuy.setFocusable(false);
		btnHuy.setRadius(8);
		btnHuy.setBorderColor(Utils.secondaryColor);
		btnHuy.setBorder(new EmptyBorder(0, 0, 0, 0));
		btnHuy.setBackground(Utils.primaryColor, 1, 0.8f);
		btnHuy.setForeground(Color.WHITE);
		btnHuy.setFont(new Font("Segoe UI", Font.PLAIN, 32));
		btnHuy.setBounds(200, 420, 250, 50);
		pnlContainer.add(btnHuy);

		Button btnLuu = new Button("Lưu");
		btnLuu.setUI(new MetalButtonUI() {
			protected Color getDisabledTextColor() {
				return Color.WHITE;
			}
		});
		btnLuu.setEnabled(false);
		btnLuu.setIcon(Utils.getImageIcon("floppy-disk 1.png"));
		btnLuu.setRadius(8);
		btnLuu.setForeground(Color.WHITE);
		btnLuu.setFont(new Font("Segoe UI", Font.PLAIN, 32));
		btnLuu.setColorOver(new Color(140, 177, 180));
		btnLuu.setColorClick(new Color(140, 177, 180, 204));
		btnLuu.setColor(new Color(140, 177, 180));
		btnLuu.setBorderColor(Utils.secondaryColor);
		btnLuu.setBorder(new EmptyBorder(0, 0, 0, 0));
		btnLuu.setBounds(500, 420, 250, 50);
		pnlContainer.add(btnLuu);

		txtMa.setEnabled(false);
		this.dichVu = dichVu;
		setDichVuVaoForm(dichVu);
		if (!isCapNhat) {
			btnCapNhat.setVisible(true);
			btnHuy.setVisible(false);
			btnLuu.setEnabled(false);
			setEnabledForm(false);
			ThongTinChiTietDichVu_GUI.this.main.repaint();
		} else {
			btnCapNhat.setVisible(false);
			btnHuy.setVisible(true);
			btnLuu.setEnabled(true);
			setEnabledForm(true);
			ThongTinChiTietDichVu_GUI.this.main.repaint();
		}

//		Sự kiện nút cập nhật
		btnCapNhat.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				btnCapNhat.setVisible(false);
				btnHuy.setVisible(true);
				btnLuu.setEnabled(true);
				setEnabledForm(true);
			}
		});

//		Sự kiện nút hủy
		btnHuy.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				btnCapNhat.setVisible(true);
				btnHuy.setVisible(false);
				btnLuu.setEnabled(false);
				setEnabledForm(false);
				ThongTinChiTietDichVu_GUI.this.main.repaint();
			}
		});

//		Sự kiện nút lưu
		btnLuu.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (!btnLuu.isEnabled())
					return;

				DichVu dichVu = getDichVuTuForm();
				boolean res = dichVu_DAO.suaDichVu(dichVu);

				if (res) {
					new Notification(jFrame, components.notification.Notification.Type.SUCCESS,
							"Cập nhật dịch vụ thành công").showNotification();
					btnCapNhat.setVisible(true);
					btnHuy.setVisible(false);
					btnLuu.setEnabled(false);
					setEnabledForm(false);
					ThongTinChiTietDichVu_GUI.this.main.repaint();
				} else {
					new Notification(main, Type.ERROR, "Cập nhật thông tin dịch vụ thất bại").showNotification();
				}
			}
		});

	}

	/**
	 * Get dịch vụ từ form
	 * 
	 * @return dịch vụ
	 */
	private DichVu getDichVuTuForm() {
		String maDichVu = txtMa.getText();
		String tenDichVu = txtTen.getText().trim();
		String donViTinh = txtDonViTinh.getText().trim();
		String soLuong = txtSoLuong.getText().trim();
		String giaMua = txtGiaMua.getText().trim();
		String tenLoaiDichVu = cmbLoaiDichVu.getSelectedItem().toString();
		return new DichVu(maDichVu, tenDichVu, Integer.parseInt(soLuong), donViTinh, Double.parseDouble(giaMua), false,
				tenLoaiDichVu);
	}

	@Override
	public void itemStateChanged(ItemEvent e) {
		if (e.getStateChange() != ItemEvent.SELECTED) {
			return;
		}
	}

	/**
	 * Set dịch vụ vào form
	 * 
	 * @param dichVu
	 */
	private void setDichVuVaoForm(DichVu dichVu) {
		txtMa.setText(dichVu.getMaDichVu());
		txtTen.setText(dichVu.getTenDichVu());
		txtSoLuong.setText(String.valueOf(dichVu.getSoLuong()));
		txtDonViTinh.setText(dichVu.getDonViTinh());
		txtGiaMua.setText(String.valueOf(dichVu.getGiaMua()));
		cmbLoaiDichVu.setSelectedItem(dichVu.getLoaiDichVu());
	}

	/**
	 * set Enabled input form
	 * 
	 * @param b
	 */
	private void setEnabledForm(boolean b) {
		txtSoLuong.setEnabled(b);
		txtTen.setEnabled(b);
		txtDonViTinh.setEnabled(b);
		txtGiaMua.setEnabled(b);
		cmbLoaiDichVu.setEnabled(b);
	}
}
