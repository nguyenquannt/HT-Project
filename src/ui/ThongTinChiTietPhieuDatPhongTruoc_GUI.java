package ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;

import components.button.Button;
import components.jDialog.Glass;
import components.jDialog.JDialogCustom;
import components.notification.Notification;
import components.panelEvent.PanelEvent;
import components.textField.TextField;
import dao.ChiTietDatPhong_DAO;
import dao.DonDatPhong_DAO;
import dao.KhachHang_DAO;
import dao.NhanVien_DAO;
import entity.ChiTietDatPhong;
import entity.DonDatPhong;
import entity.KhachHang;
import entity.NhanVien;
import entity.Phong;
import utils.Utils;

public class ThongTinChiTietPhieuDatPhongTruoc_GUI extends JPanel implements ItemListener {
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

	private ThongTinChiTietPhieuDatPhongTruoc_GUI _this;
	private Button btnHuyPhong;
	private Button btnNhanPhong;
	private ChiTietDatPhong_DAO chiTietDatPhong_DAO;
	private DonDatPhong donDatPhong;
	private DonDatPhong_DAO donDatPhong_DAO;
	private Glass glass;
	private JFrame jFrameSub;
	private KhachHang khachHang;
	private KhachHang_DAO khachHang_DAO;
	private String maDatPhong;
	private Main main;
	private NhanVien nhanVien;
	private NhanVien_DAO nhanVien_DAO;
	private PanelEvent pnlSuaPhong;
	private PanelEvent pnlXuatPDF;
	private TextField txtKhachHang;
	private TextField txtMaDatPhong;
	private TextField txtNhanVien;
	private TextField txtPhong;
	private TextField txtSDT;
	private TextField txtSLPhong;
	private TextField txtTGLP;
	private TextField txtTGNP;
	private TextField txtTrangThai;

	private final int widthPnlContainer = 948;

	/**
	 * Create the frame.
	 */
	public ThongTinChiTietPhieuDatPhongTruoc_GUI(Main main, ChiTietDatPhong chiTietDatPhong) {
		glass = new Glass();
		this.main = main;
		_this = this;
		int padding = (int) Math.floor((Utils.getBodyHeight() - 428) / 5);
		int top = padding;

		donDatPhong_DAO = new DonDatPhong_DAO();
		khachHang_DAO = new KhachHang_DAO();
		nhanVien_DAO = new NhanVien_DAO();
		chiTietDatPhong_DAO = new ChiTietDatPhong_DAO();

		maDatPhong = chiTietDatPhong.getDonDatPhong().getMaDonDatPhong();
		donDatPhong = donDatPhong_DAO.getDatPhong(maDatPhong);
		khachHang = khachHang_DAO.getKhachHangTheoMa(donDatPhong.getKhachHang().getMaKhachHang());
		nhanVien = nhanVien_DAO.getNhanVienTheoMa(donDatPhong.getNhanVien().getMaNhanVien());

		glass.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				closeJFrameSub();
			}
		});

		setBackground(Utils.secondaryColor);
		setBounds(0, 0, Utils.getScreenWidth(), Utils.getBodyHeight());
		setLayout(null);

		JPanel pnlContainer = new JPanel();
		pnlContainer.setBackground(Utils.secondaryColor);
		pnlContainer.setBounds(Utils.getLeft(widthPnlContainer) - 30, 0, widthPnlContainer, Utils.getBodyHeight());
		this.add(pnlContainer);
		pnlContainer.setLayout(null);

		lblTime = new JLabel("");
		lblTime.setHorizontalAlignment(SwingConstants.LEFT);
		lblTime.setFont(new Font("Segoe UI", Font.PLAIN, 18));
		lblTime.setBounds(750, 0, 180, 24);
		pnlContainer.add(lblTime);
		clock();

		JPanel pnlRow1 = new JPanel();
		pnlRow1.setBackground(Utils.secondaryColor);
		pnlRow1.setBounds(0, top, 948, 75);
		top += padding;
		top += 55;
		pnlContainer.add(pnlRow1);
		pnlRow1.setLayout(null);

		txtMaDatPhong = new TextField();
		txtMaDatPhong.setLineColor(Utils.lineTextField);
		txtMaDatPhong.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		txtMaDatPhong.setLabelText("Mã phiếu đặt");
		txtMaDatPhong.setBounds(50, 10, 449, 50);
		txtMaDatPhong.setBackground(Utils.secondaryColor);
		txtMaDatPhong.setEnabled(false);
		pnlRow1.add(txtMaDatPhong);
		txtMaDatPhong.setColumns(10);

		txtNhanVien = new TextField();
		txtNhanVien.setLineColor(Utils.lineTextField);
		txtNhanVien.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		txtNhanVien.setLabelText("Nhân viên");
		txtNhanVien.setBounds(555, 10, 449, 50);
		txtNhanVien.setBackground(Utils.secondaryColor);
		txtNhanVien.setEnabled(false);
		pnlRow1.add(txtNhanVien);
		txtNhanVien.setColumns(10);

		JPanel pnlRow2 = new JPanel();
		pnlRow2.setBackground(Utils.secondaryColor);
		pnlRow2.setBounds(0, top, 948, 75);
		top += padding;
		top += 55;
		pnlContainer.add(pnlRow2);
		pnlRow2.setLayout(null);

		txtPhong = new TextField();
		txtPhong.setLineColor(Utils.lineTextField);
		txtPhong.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		txtPhong.setBounds(50, 20, 480, 50);
		txtPhong.setLabelText("Phòng");
		txtPhong.setBackground(Utils.secondaryColor);
		txtPhong.setEnabled(false);
		pnlRow2.add(txtPhong);
		txtPhong.setColumns(10);

		txtSLPhong = new TextField();
		txtSLPhong.setLineColor(Utils.lineTextField);
		txtSLPhong.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		txtSLPhong.setBounds(560, 20, 70, 50);
		txtSLPhong.setLabelText("Số lượng");
		txtSLPhong.setBackground(Utils.secondaryColor);
		txtSLPhong.setEnabled(false);
		pnlRow2.add(txtSLPhong);
		txtSLPhong.setColumns(10);

		txtTrangThai = new TextField();
		txtTrangThai.setLineColor(Utils.lineTextField);
		txtTrangThai.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		txtTrangThai.setLabelText("Trạng thái");
		txtTrangThai.setBounds(650, 20, 100, 50);
		txtTrangThai.setBackground(Utils.secondaryColor);
		txtTrangThai.setEnabled(false);
		pnlRow2.add(txtTrangThai);
		txtTrangThai.setColumns(10);

		pnlSuaPhong = new PanelEvent(13) {
			/**
			 *
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public Color getBackground() {
				if (!isEnabled())
					return Utils.getOpacity(super.getBackground(), 0.5f);
				return super.getBackground();
			}
		};
		pnlSuaPhong.setLayout(null);
		pnlSuaPhong.setBackgroundColor(Utils.primaryColor);
		pnlRow2.add(pnlSuaPhong);
		JLabel lblSuaPhong = new JLabel("Sửa phòng");
		lblSuaPhong.setHorizontalAlignment(SwingConstants.CENTER);
		lblSuaPhong.setForeground(Color.WHITE);
		lblSuaPhong.setFont(new Font("Segoe UI", Font.PLAIN, 18));
		JLabel lblIcon = new JLabel("");
		lblIcon.setHorizontalAlignment(SwingConstants.CENTER);
		lblIcon.setIcon(Utils.getImageIcon("change-door.png"));
		pnlSuaPhong.add(lblSuaPhong);
		pnlSuaPhong.add(lblIcon);
		pnlSuaPhong.setBounds(780, 30, 160, 40);
		lblSuaPhong.setBounds(50, 5, 90, 26);
		lblIcon.setBounds(12, 5, 32, 32);

		JPanel pnlRow3 = new JPanel();
		pnlRow3.setBackground(Utils.secondaryColor);
		pnlRow3.setBounds(0, top, 948, 75);
		top += padding;
		top += 55;
		pnlContainer.add(pnlRow3);
		pnlRow3.setLayout(null);

		txtKhachHang = new TextField();
		txtKhachHang.setLineColor(Utils.lineTextField);
		txtKhachHang.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		txtKhachHang.setLabelText("Khách hàng");
		txtKhachHang.setBounds(50, 10, 449, 50);
		txtKhachHang.setBackground(Utils.secondaryColor);
		txtKhachHang.setEnabled(false);
		pnlRow3.add(txtKhachHang);
		txtKhachHang.setColumns(10);

		txtSDT = new TextField();
		txtSDT.setLineColor(Utils.lineTextField);
		txtSDT.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		txtSDT.setLabelText("Số điện thoại");
		txtSDT.setBounds(560, 10, 449, 50);
		txtSDT.setBackground(Utils.secondaryColor);
		txtSDT.setEnabled(false);
		pnlRow3.add(txtSDT);
		txtSDT.setColumns(10);

		JPanel pnlRow4 = new JPanel();
		pnlRow4.setBackground(Utils.secondaryColor);
		pnlRow4.setBounds(0, top, 948, 75);
		top += padding;
		top += 55;
		pnlContainer.add(pnlRow4);
		pnlRow4.setLayout(null);

		txtTGLP = new TextField();
		txtTGLP.setLineColor(Utils.lineTextField);
		txtTGLP.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		txtTGLP.setLabelText("Thời gian lập phiếu");
		txtTGLP.setBounds(50, 20, 449, 50);
		txtTGLP.setBackground(Utils.secondaryColor);
		txtTGLP.setEnabled(false);
		pnlRow4.add(txtTGLP);
		txtTGLP.setColumns(40);

		txtTGNP = new TextField();
		txtTGNP.setLineColor(Utils.lineTextField);
		txtTGNP.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		txtTGNP.setLabelText("Thời gian nhận phòng");
		txtTGNP.setBounds(560, 20, 449, 50);
		txtTGNP.setBackground(Utils.secondaryColor);
		txtTGNP.setEnabled(false);
		pnlRow4.add(txtTGNP);
		txtTGNP.setColumns(40);

		JPanel pnlActions = new JPanel();
		pnlActions.setBackground(Utils.secondaryColor);
		pnlActions.setBounds(0, top, 948, 48);
		pnlContainer.add(pnlActions);
		pnlActions.setLayout(null);

		btnNhanPhong = new Button("Nhận phòng");
		btnNhanPhong.setIcon(Utils.getImageIcon("check-in (1).png"));
		btnNhanPhong.setFocusable(false);
		btnNhanPhong.setRadius(8);
		btnNhanPhong.setBorderColor(Utils.secondaryColor);
		btnNhanPhong.setBorder(new EmptyBorder(0, 0, 0, 0));
		btnNhanPhong.setColor(Utils.primaryColor);
		btnNhanPhong.setColorOver(Utils.primaryColor);
		btnNhanPhong.setColorClick(Utils.getOpacity(Utils.primaryColor, 0.8f));
		btnNhanPhong.setForeground(Color.WHITE);
		btnNhanPhong.setFont(new Font("Segoe UI", Font.PLAIN, 32));
		btnNhanPhong.setBounds(70, 0, 250, 48);
		pnlActions.add(btnNhanPhong);

		btnHuyPhong = new Button("Huỷ phòng");
		btnHuyPhong.setIcon(Utils.getImageIcon("edit 1.png"));
		btnHuyPhong.setFocusable(false);
		btnHuyPhong.setRadius(8);
		btnHuyPhong.setBorderColor(Utils.secondaryColor);
		btnHuyPhong.setBorder(new EmptyBorder(0, 0, 0, 0));
		btnHuyPhong.setColor(Utils.primaryColor);
		btnHuyPhong.setColorOver(Utils.primaryColor);
		btnHuyPhong.setColorClick(Utils.getOpacity(Utils.primaryColor, 0.8f));
		btnHuyPhong.setForeground(Color.WHITE);
		btnHuyPhong.setFont(new Font("Segoe UI", Font.PLAIN, 32));
		btnHuyPhong.setBounds(370, 0, 250, 48);
		pnlActions.add(btnHuyPhong);

		pnlXuatPDF = new PanelEvent(13) {
			/**
			 *
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public Color getBackground() {
				if (!isEnabled())
					return Utils.getOpacity(super.getBackground(), 0.5f);
				return super.getBackground();
			}
		};
		pnlActions.add(pnlXuatPDF);
		pnlXuatPDF.setBounds(670, 0, 230, 48);
		pnlXuatPDF.setLayout(null);
		pnlXuatPDF.setBackgroundColor(Utils.primaryColor);
		pnlXuatPDF.setBorder(new EmptyBorder(0, 0, 0, 0));
		JLabel lblXuatPDF = new JLabel(" Xuất PDF");
		lblXuatPDF.setHorizontalAlignment(SwingConstants.CENTER);
		lblXuatPDF.setForeground(Color.WHITE);
		lblXuatPDF.setFont(new Font("Segoe UI", Font.PLAIN, 32));
		JLabel lblIconXuatPDF = new JLabel("");
		lblIconXuatPDF.setHorizontalAlignment(SwingConstants.CENTER);
		lblIconXuatPDF.setIcon(Utils.getImageIcon("add-file.png"));
		pnlXuatPDF.add(lblXuatPDF);
		pnlXuatPDF.add(lblIconXuatPDF);
		lblXuatPDF.setBounds(30, 5, 200, 32);
		lblIconXuatPDF.setBounds(20, 5, 40, 40);

		setPhieuDatPhongVaoForm(chiTietDatPhong);
		setEnabledForm();

		addAncestorListener(new AncestorListener() {
			Thread clockThread;

			public void ancestorAdded(AncestorEvent event) {
				clockThread = clock();
			}

			public void ancestorMoved(AncestorEvent event) {
			}

			@SuppressWarnings("deprecation")
			public void ancestorRemoved(AncestorEvent event) {
				clockThread.stop();
			}
		});

//		Sự kiện nút Sửa danh sách phòng
		pnlSuaPhong.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (!pnlSuaPhong.isEnabled())
					return;
				handleOpenSubFrame(pnlSuaPhong, new SuaPhong_GUI(main, _this, null, donDatPhong));

			}
		});

//		Sự kiện nút Nhận phòng
		btnNhanPhong.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (!btnNhanPhong.isEnabled())
					return;
				boolean res = false;

				List<Phong> listPhong = new ArrayList<>();
				List<ChiTietDatPhong> listChiTietDatPhong = chiTietDatPhong_DAO.getAllChiTietDatPhong(donDatPhong);
				listChiTietDatPhong.forEach(list -> listPhong.add(list.getPhong()));

//				Kiểm tra phòng có đang thuê hay không
				List<Phong> listPhongDangThue = donDatPhong_DAO.timPhongDangThue(listPhong);
				if (listPhongDangThue.size() > 0) {
					String[] maPhong = new String[listPhongDangThue.size()];
					int i = 0;
					for (Phong phong : listPhongDangThue) {
						maPhong[i++] = phong.getMaPhong();
					}

					new JDialogCustom(main, components.jDialog.JDialogCustom.Type.warning).showMessage("Warning",
							"Phòng " + String.join(", ", maPhong) + " đang thuê\n");
					return;
				}

//				Kiểm tra ngày nhận phòng = ngày hiện tại
				if (donDatPhong.getNgayNhanPhong().isAfter(LocalDate.now())) {
					new JDialogCustom(main, components.jDialog.JDialogCustom.Type.warning).showMessage("Warning",
							"Chưa đến ngày nhận phòng");
					return;
				}

//				Kiểm tra phòng có đơn đặt phòng trước khác không
				List<Phong> listPhongCoDonDatTruocKhac = donDatPhong_DAO.timPhongCoDonDatTruocKhac(listPhong,
						donDatPhong);
				if (listPhongCoDonDatTruocKhac.size() > 0) {
					String[] maPhong = new String[listPhongCoDonDatTruocKhac.size()];
					int i = 0;
					for (Phong phong : listPhongCoDonDatTruocKhac) {
						maPhong[i++] = phong.getMaPhong();
					}
					new JDialogCustom(main, components.jDialog.JDialogCustom.Type.warning).showMessage("Warning",
							"Phòng " + String.join(", ", maPhong) + " có đơn đặt phòng trước "
									+ donDatPhong.getGioNhanPhong());
					return;
				}

				res = donDatPhong_DAO.nhanPhongTrongPhieuDatPhongTruoc(donDatPhong, listPhong);
				if (res) {
					JDialogCustom jDialogCustom = new JDialogCustom(main);

					jDialogCustom.getBtnOK().addMouseListener(new MouseAdapter() {
						@Override
						public void mouseClicked(MouseEvent e) {
							QuanLyDatPhong_GUI quanLyDatPhong_GUI = new QuanLyDatPhong_GUI(main);
							main.addPnlBody(quanLyDatPhong_GUI, "Quản lý đặt phòng", 1, 0);
						}
					});
					jDialogCustom.getBtnCancel().addMouseListener(new MouseAdapter() {
						@Override
						public void mouseClicked(MouseEvent e) {
							QuanLyPhieuDatPhongTruoc_GUI quanLyPhieuDatPhong_GUI = new QuanLyPhieuDatPhongTruoc_GUI(
									main);
							main.addPnlBody(quanLyPhieuDatPhong_GUI, "Quản lý đặt phòng trước", 1, 0);
						}
					});

					jDialogCustom.showMessage("Question",
							"Nhận phòng thành công! \nBạn có muốn chuyển sang trang quản lý đặt phòng");
				}

			}
		});
//		Sự kiện nút Huỷ phòng
		btnHuyPhong.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (!btnHuyPhong.isEnabled())
					return;
				boolean res = false;

				List<Phong> listPhong = new ArrayList<>();
				List<ChiTietDatPhong> listChiTietDatPhong = chiTietDatPhong_DAO.getAllChiTietDatPhong(donDatPhong);
				listChiTietDatPhong.forEach(list -> listPhong.add(list.getPhong()));
				res = donDatPhong_DAO.huyPhongTrongPhieuDatPhongTruoc(donDatPhong, listPhong);
				if (res) {
					new Notification(main, components.notification.Notification.Type.SUCCESS, "Huỷ phòng thành công")
							.showNotification();
					txtTrangThai.setText("Đã hủy");
					setEnabledForm();
					return;
				} else {
					new Notification(main, components.notification.Notification.Type.ERROR, "Huỷ phòng thất bại")
							.showNotification();
					return;
				}

			}
		});

//		Sự kiện nút Xuất PDF
		pnlXuatPDF.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				List<Phong> listPhong = new ArrayList<>();
				List<ChiTietDatPhong> listChiTietDatPhong = chiTietDatPhong_DAO.getAllChiTietDatPhong(donDatPhong);
				listChiTietDatPhong.forEach(list -> listPhong.add(list.getPhong()));
				handleOpenSubFrame(pnlXuatPDF,
						new PhieuDatPhongTruoc_PDF(main, _this, nhanVien, khachHang, donDatPhong, listPhong));
			}
		});

	}

	public void closeJFrameSub() {
		if (jFrameSub != null)
			jFrameSub.setVisible(false);
		glass.setVisible(false);
		glass.setAlpha(0f);
		jFrameSub = null;
	}

	public void handleOpenSubFrame(JPanel pnl, JFrame jFrame) {
		if (!pnl.isEnabled())
			return;
		openJFrameSub(jFrame);
	}

	@Override
	public void itemStateChanged(ItemEvent e) {
		if (e.getStateChange() == ItemEvent.DESELECTED)
			return;

	}

	public void openJFrameSub(JFrame jFrame) {
		this.main.setGlassPane(glass);
		glass.setVisible(true);
		glass.setAlpha(0.5f);
		jFrameSub = jFrame;
		jFrameSub.setVisible(true);
	}

	private void setEnabledForm() {
		if (txtTrangThai.getText().equals("Đã hủy")) {
			pnlSuaPhong.setEnabled(false);
			btnNhanPhong.setEnabled(false);
			btnHuyPhong.setEnabled(false);
		}
	}

	public void setPhieuDatPhongVaoForm(ChiTietDatPhong chiTietDatPhong) {
		maDatPhong = chiTietDatPhong.getDonDatPhong().getMaDonDatPhong();
		donDatPhong = donDatPhong_DAO.getDatPhong(maDatPhong);
		khachHang = khachHang_DAO.getKhachHangTheoMa(donDatPhong.getKhachHang().getMaKhachHang());
		nhanVien = nhanVien_DAO.getNhanVienTheoMa(donDatPhong.getNhanVien().getMaNhanVien());

		List<String> listPhong = new ArrayList<String>();
		List<ChiTietDatPhong> listChiTietDatPhong = chiTietDatPhong_DAO.getAllChiTietDatPhong(donDatPhong);
		listChiTietDatPhong.forEach(list -> listPhong.add(list.getPhong().getMaPhong()));

		txtMaDatPhong.setText(maDatPhong);
		txtKhachHang.setText(khachHang.getMaKhachHang() + " - " + khachHang.getHoTen());
		txtNhanVien.setText(nhanVien.getMaNhanVien() + " - " + nhanVien.getHoTen());
		txtPhong.setText(String.join(", ", listPhong));
		txtSLPhong.setText(listPhong.size() + "");
		txtTrangThai.setText(DonDatPhong.convertTrangThaiToString(donDatPhong.getTrangThai()));
		txtKhachHang.setText(khachHang.getMaKhachHang() + " - " + khachHang.getHoTen());
		txtSDT.setText(khachHang.getSoDienThoai());
		txtTGLP.setText(Utils.convertLocalTimeToString(donDatPhong.getGioDatPhong()) + " - "
				+ Utils.formatDate(donDatPhong.getNgayDatPhong()));
		txtTGNP.setText(Utils.convertLocalTimeToString(donDatPhong.getGioNhanPhong()) + " - "
				+ Utils.formatDate(donDatPhong.getNgayNhanPhong()));

	}

}
