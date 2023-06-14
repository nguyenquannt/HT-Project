package ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.regex.Pattern;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;

import components.button.Button;
import components.jDialog.JDialogCustom;
import components.notification.Notification;
import components.panelRound.PanelRound;
import components.passwordField.PasswordField;
import components.textField.TextField;
import connectDB.ConnectDB;
import dao.NhanVien_DAO;
import keeptoo.KGradientPanel;
import utils.NhanVien;
import utils.Utils;

public class DangNhap_GUI extends JFrame {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private DangNhap_GUI _this;
	private JPanel contentPane;
	private TextField txtMaNhanVien;
	private PasswordField txtMatKhau;
	private NhanVien_DAO nhanVien_DAO;

	/**
	 * Create the frame.
	 */
	public DangNhap_GUI() {
		try {
			new ConnectDB().connect();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}

		_this = this;
		nhanVien_DAO = new NhanVien_DAO();

		setAutoRequestFocus(false);
		setTitle("Đăng nhập");
		setResizable(false);
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setBounds(0, 0, Utils.getScreenWidth(), Utils.getScreenHeight());
		setUndecorated(true);
		contentPane = new JPanel();
		contentPane.setBackground(Color.CYAN);
		contentPane.setBorder(new EmptyBorder(0, 0, 0, 0));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setLocationRelativeTo(null);

		JDialogCustom jDialogCustom = new JDialogCustom(this);
		jDialogCustom.getBtnOK().addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				System.exit(0);
			}
		});

		KGradientPanel pnlContainer = new KGradientPanel();
		pnlContainer.setBounds(0, 0, Utils.getScreenWidth(), Utils.getScreenHeight());
		pnlContainer.setkEndColor(Utils.getRGBA(0, 204, 204, 1f));
		pnlContainer.setkGradientFocus(600);
		pnlContainer.setkStartColor(Utils.getRGBA(153, 0, 153, 0.8f));
		contentPane.add(pnlContainer);
		pnlContainer.setLayout(null);

		int widthPnlDangNhap = 800;
		int heightPnlDangNhap = 400;
		int leftPnlDangNhap = (int) Math.ceil((Utils.getScreenWidth() - widthPnlDangNhap) / 2);
		int topPnlDangNhap = (int) Math.ceil((Utils.getScreenHeight() - heightPnlDangNhap) / 2);

		JPanel pnlDangNhap = new JPanel();
		pnlDangNhap.setBackground(Utils.getRGBA(0, 0, 0, 0));
		pnlDangNhap.setBounds(leftPnlDangNhap, topPnlDangNhap, widthPnlDangNhap, heightPnlDangNhap);
		pnlContainer.add(pnlDangNhap);
		pnlDangNhap.setLayout(null);

		PanelRound pnlFormDangNhap = new PanelRound();
		pnlFormDangNhap.setRounded(16);
		pnlFormDangNhap.setBounds(400, 0, 400, 400);
		pnlFormDangNhap.setBackground(Color.WHITE);
		pnlDangNhap.add(pnlFormDangNhap);
		pnlFormDangNhap.setLayout(null);

		JLabel lblDangNhap = new JLabel("Đăng nhập");
		lblDangNhap.setForeground(Utils.primaryColor);
		lblDangNhap.setHorizontalAlignment(SwingConstants.CENTER);
		lblDangNhap.setFont(new Font("Segoe UI", Font.BOLD, 34));
		lblDangNhap.setBounds(0, 40, 400, 51);
		pnlFormDangNhap.add(lblDangNhap);

		JPanel pnlForm = new JPanel();
		pnlForm.setBackground(Color.WHITE);
		pnlForm.setBounds(40, 139, 320, 221);
		pnlFormDangNhap.add(pnlForm);
		pnlForm.setLayout(null);

		txtMaNhanVien = new TextField();
		txtMaNhanVien.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				txtMaNhanVien.setError(false);
			}
		});
		txtMaNhanVien.setLabelText("Mã nhân viên");
		txtMaNhanVien.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		txtMaNhanVien.setBounds(0, 0, 320, 55);
		pnlForm.add(txtMaNhanVien);
		txtMaNhanVien.setColumns(10);

		txtMatKhau = new PasswordField() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public char getEchoChar() {
				// TODO Auto-generated method stub
				return '*';
			}
		};
		txtMatKhau.setLabelText("Mật khẩu");
		txtMatKhau.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		txtMatKhau.setColumns(10);
		txtMatKhau.setBounds(0, 75, 320, 55);
		txtMatKhau.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				txtMatKhau.setError(false);
			}
		});
		pnlForm.add(txtMatKhau);

		Button btnDangNhap = new Button("Đăng nhập");
		btnDangNhap.setIcon(Utils.getImageIcon("sign-in.png"));
		btnDangNhap.setFocusable(false);
		btnDangNhap.setFont(new Font("Segoe UI", Font.PLAIN, 20));
		btnDangNhap.setColor(Utils.primaryColor);
		btnDangNhap.setForeground(Color.white);
		btnDangNhap.setBorder(new EmptyBorder(0, 0, 0, 0));
		btnDangNhap.setBorderColor(Color.WHITE);
		btnDangNhap.setColorOver(Utils.getOpacity(Utils.primaryColor, 0.9f));
		btnDangNhap.setColorClick(Utils.getOpacity(Utils.primaryColor, 0.8f));
		btnDangNhap.setRadius(8);
		btnDangNhap.setBounds(170, 148, 150, 40);
		btnDangNhap.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (!validator())
					return;

				String maTaiKhoan = txtMaNhanVien.getText();
				@SuppressWarnings("deprecation")
				String matKhau = txtMatKhau.getText();

				boolean res = nhanVien_DAO.isTaiKhoan(maTaiKhoan, matKhau);

				if (res) {
					NhanVien.setNhanVien(new entity.NhanVien(maTaiKhoan));
					new Main().setVisible(true);
					setVisible(false);
				} else {
					new Notification(_this, components.notification.Notification.Type.ERROR,
							"Tài khoản hoặc mật khẩu không đúng").showNotification();
				}
			}
		});
		pnlForm.add(btnDangNhap);

		Button btnThoat = new Button("Thoát");
		btnThoat.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				jDialogCustom.showMessage("Thoát ứng dụng", "Bạn có chắc chắn muốn thoát ứng dụng không?");
			}
		});
		btnThoat.setIcon(Utils.getImageIcon("exit (1).png"));
		btnThoat.setFocusable(false);
		btnThoat.setBorderWidth(1);
		btnThoat.setBorderBtnColor(Utils.primaryColor);
		btnThoat.setFont(new Font("Segoe UI", Font.PLAIN, 20));
		btnThoat.setColor(Color.WHITE);
		btnThoat.setForeground(Utils.primaryColor);
		btnThoat.setBorder(new EmptyBorder(0, 0, 0, 0));
		btnThoat.setBorderColor(Color.white);
		btnThoat.setColorOver(Utils.getOpacity(Color.BLACK, 0.025f));
		btnThoat.setColorClick(Utils.getOpacity(Color.BLACK, 0.05f));
		btnThoat.setRadius(8);
		btnThoat.setBounds(0, 148, 150, 40);
		pnlForm.add(btnThoat);

		JLabel lblQuenMatKhau = new JLabel("Quên mật khẩu?");
		lblQuenMatKhau.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		lblQuenMatKhau.setForeground(Utils.primaryColor);
		lblQuenMatKhau.setBounds(210, 200, 110, 21);
		pnlForm.add(lblQuenMatKhau);

		JLabel lblBackground = new JLabel();
		lblBackground.setIcon(Utils.getImageIcon("login.png"));
		lblBackground.setBounds(72, 144, 256, 256);
		pnlDangNhap.add(lblBackground);

		JLabel lblTieuDe = new JLabel("FLEY HOTEL");
		lblTieuDe.setHorizontalAlignment(SwingConstants.CENTER);
		lblTieuDe.setForeground(Color.WHITE);
		lblTieuDe.setFont(new Font("Segoe UI", Font.BOLD, 36));
		lblTieuDe.setBounds(0, 0, 400, 83);
		

//		Default Value
		txtMaNhanVien.setText("NV111");
		txtMatKhau.setText("1234Abc@");

		setAlwaysOnTop(false);
	}

	private boolean validator() {
		String maTaiKhoan = txtMaNhanVien.getText();
		components.notification.Notification.Type type = components.notification.Notification.Type.ERROR;

		if (maTaiKhoan.equals("")) {
			new Notification(_this, type, "Mã tài khoản không được rỗng").showNotification();
			txtMaNhanVien.setError(true);
			txtMaNhanVien.requestFocus();
			return false;
		}

		if (!Pattern.matches("NV[0-9]{3}", maTaiKhoan)) {
			new Notification(_this, type, "Mã tài khoản phải có dạng NVxxx, x là các chữ số").showNotification();
			txtMaNhanVien.setError(true);
			txtMaNhanVien.requestFocus();
			return false;
		}

		@SuppressWarnings("deprecation")
		String matKhau = txtMatKhau.getText();

		if (matKhau.equals("")) {
			new Notification(_this, type, "Mật khẩu không được rỗng").showNotification();
			txtMatKhau.setError(true);
			txtMatKhau.requestFocus();
			return false;
		}
		if (matKhau.length() < 8) {
			new Notification(_this, type, "Mật khẩu phải lớn hơn 8 ký tự").showNotification();
			txtMatKhau.setError(true);
			txtMatKhau.requestFocus();
			return false;
		}

		return true;
	}
}
