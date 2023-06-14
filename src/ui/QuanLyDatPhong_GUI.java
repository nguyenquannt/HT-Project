package ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.Rectangle;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;

import components.button.Button;
import components.comboBox.ComboBox;
import components.jDialog.Glass;
import components.jDialog.JDialogCustom;
import components.panelEvent.PanelEvent;
import components.panelRound.PanelRound;
import components.scrollbarCustom.ScrollBarCustom;
import dao.DonDatPhong_DAO;
import dao.Phong_DAO;
import entity.Phong;
import entity.Phong.TrangThai;
import utils.Utils;

public class QuanLyDatPhong_GUI extends JPanel implements KeyEventDispatcher {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private QuanLyDatPhong_GUI _this;
	private Thread clock;
	private ComboBox<String> cmbSoLuong;
	private DonDatPhong_DAO datPhong_DAO;
	private final int gapX = 21;
	private final int gapY = 30;
	private Glass glass;
	private final int heightPhong = 130;
//	private int heightPnlContainerDanhSachPhong = Utils.getBodyHeight() - 285;
	private int heightPnlContainerDanhSachPhong = Utils.getBodyHeight() - 320;
	private JDialogCustom jDialog;
	private JFrame jFrameSub;
	private JLabel lblDate;
	private JLabel lblSoLuongPhongCho;
	private JLabel lblSoLuongPhongTam;
	private JLabel lblSoPhongDSD;
	private JLabel lblSoPhongTrong;
	private JLabel lblThu;
	private JLabel lblTime;
	private String loaiPhong = "";
	private Main main;
	private MouseListener mouseListener = new MouseAdapter() {
		@Override
		public void mouseClicked(MouseEvent e) {
			handleClickOutsidePnlPhong();
			repaint();
		}
	};
	private final int numOfRow = 6;
	private Phong_DAO phong_DAO;
	private PanelEvent pnlChuyenPhong;
	private JPanel pnlContainerDanhSachPhong;
	private JPanel pnlDanhSachPhong;
	private PanelEvent pnlDatPhong;
	private PanelEvent pnlDatPhongTruoc;
	private PanelEvent pnlDichVu;
	private PanelEvent pnlFilterPhongCho;
	private PanelEvent pnlFilterPhongDangSuDung;
	private PanelEvent pnlFilterPhongTam;
	private PanelEvent pnlFilterPhongTrong;
	private PanelEvent pnlGopPhong;
	private JPanel pnlPhongItem = null;
	private PanelEvent pnlThanhToan;
	private JScrollPane scrDanhSachPhong;
	private int soPhongCho = 0;
	private int soPhongDangSuDung = 0;
	private int soPhongTam;
	private int soPhongTrong = 0;
	private String trangThai = "";
	private final int widthPhong = 131;

	/**
	 * Create the frame.
	 */
	public QuanLyDatPhong_GUI(Main main) {
		this.main = main;
		_this = this;
		phong_DAO = new Phong_DAO();
		datPhong_DAO = new DonDatPhong_DAO();
		jDialog = new JDialogCustom(main, components.jDialog.JDialogCustom.Type.warning);
		glass = new Glass();

		addAncestorListener(new AncestorListener() {
			@Override
			public void ancestorAdded(AncestorEvent event) {
				clock();
				QuanLyDatPhong_GUI.this.main.setWidthHeader(1082);
				addPhong(phong_DAO.getAllPhong());
				KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(_this);

				main.addMouseListener(mouseListener);
				addMouseListener(mouseListener);
			}

			@Override
			public void ancestorMoved(AncestorEvent event) {
				// TODO Auto-generated method stub

			}

			@Override
			@SuppressWarnings("deprecation")
			public void ancestorRemoved(AncestorEvent event) {
				QuanLyDatPhong_GUI.this.main.setWidthHeader(1054);
				clock.stop();
				KeyboardFocusManager.getCurrentKeyboardFocusManager().removeKeyEventDispatcher(_this);

				main.removeMouseListener(mouseListener);
				removeMouseListener(mouseListener);
			}
		});

		glass.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				closeJFrameSub();
			}
		});

		setBounds(0, 0, Utils.getScreenWidth(), Utils.getBodyHeight());
		setBackground(Utils.secondaryColor);
		setLayout(null);

		JPanel pnlContent = new JPanel();
		pnlContent.setBounds(Utils.getLeft(1114), 0, 1114, Utils.getBodyHeight());
		pnlContent.setLayout(null);
		pnlContent.setBackground(Utils.secondaryColor);
		add(pnlContent);

//		Chọn loại phòng
		JPanel pnlLoaiPhong = new JPanel();
		pnlLoaiPhong.setBackground(Utils.secondaryColor);
		pnlLoaiPhong.setBounds(16, 0, 1082, 58);
		pnlContent.add(pnlLoaiPhong);
		pnlLoaiPhong.setLayout(null);

		JLabel lblLoaiPhong = new JLabel("Chọn loại phòng:");
		lblLoaiPhong.setForeground(new Color(0, 0, 0, 130));
		lblLoaiPhong.setFont(new Font("Segoe UI", Font.BOLD, 24));
		lblLoaiPhong.setBounds(0, 10, 215, 37);
		pnlLoaiPhong.add(lblLoaiPhong);

		Button btnTatCa = new Button("Tất cả");
		btnTatCa.setFocusable(false);
		btnTatCa.setRadius(9);
		btnTatCa.setBorderColor(Utils.secondaryColor);
		btnTatCa.setBorder(new EmptyBorder(0, 0, 0, 0));
		btnTatCa.setForeground(Color.WHITE);
		btnTatCa.setBorderBtnColor(new Color(0, 0, 0, 81));
		btnTatCa.setBorderWidth(2);
		btnTatCa.setColor(new Color(140, 177, 180, 178));
		btnTatCa.setColorOver(new Color(140, 177, 180, 178));
		btnTatCa.setColorClick(new Color(140, 177, 180, 127));
		btnTatCa.setFont(new Font("Segoe UI", Font.BOLD, 16));
		btnTatCa.setBounds(222, 10, 132, 38);
		pnlLoaiPhong.add(btnTatCa);

		Button btnTatCaUnactive = new Button("Tất cả");
		btnTatCaUnactive.setRadius(9);
		btnTatCaUnactive.setForeground(new Color(0, 0, 0, 148));
		btnTatCaUnactive.setFont(new Font("Segoe UI", Font.BOLD, 16));
		btnTatCaUnactive.setFocusable(false);
		btnTatCaUnactive.setColorOver(Color.WHITE);
		btnTatCaUnactive.setColorClick(new Color(255, 255, 255, 204));
		btnTatCaUnactive.setColor(Color.WHITE);
		btnTatCaUnactive.setBorderWidth(2);
		btnTatCaUnactive.setBorderColor(Utils.secondaryColor);
		btnTatCaUnactive.setBorderBtnColor(new Color(119, 96, 204));
		btnTatCaUnactive.setBorder(new EmptyBorder(0, 0, 0, 0));
		btnTatCaUnactive.setBounds(222, 10, 132, 38);
		pnlLoaiPhong.add(btnTatCaUnactive);

		Button btnPhongThuong = new Button("Phòng thường");
		btnPhongThuong.setRadius(9);
		btnPhongThuong.setForeground(Color.WHITE);
		btnPhongThuong.setFont(new Font("Segoe UI", Font.BOLD, 16));
		btnPhongThuong.setFocusable(false);
		btnPhongThuong.setColorOver(new Color(140, 177, 180, 178));
		btnPhongThuong.setColorClick(new Color(140, 177, 180, 127));
		btnPhongThuong.setColor(new Color(140, 177, 180, 178));
		btnPhongThuong.setBorderWidth(2);
		btnPhongThuong.setBorderColor(Utils.secondaryColor);
		btnPhongThuong.setBorderBtnColor(new Color(0, 0, 0, 81));
		btnPhongThuong.setBorder(new EmptyBorder(0, 0, 0, 0));
		btnPhongThuong.setBounds(374, 10, 132, 38);
		pnlLoaiPhong.add(btnPhongThuong);

		Button btnPhongThuongUnactive = new Button("Phòng thường");
		btnPhongThuongUnactive.setRadius(9);
		btnPhongThuongUnactive.setForeground(new Color(0, 0, 0, 148));
		btnPhongThuongUnactive.setFont(new Font("Segoe UI", Font.BOLD, 16));
		btnPhongThuongUnactive.setFocusable(false);
		btnPhongThuongUnactive.setColorOver(Color.WHITE);
		btnPhongThuongUnactive.setColorClick(new Color(255, 255, 255, 204));
		btnPhongThuongUnactive.setColor(Color.WHITE);
		btnPhongThuongUnactive.setBorderWidth(2);
		btnPhongThuongUnactive.setBorderColor(Utils.secondaryColor);
		btnPhongThuongUnactive.setBorderBtnColor(new Color(119, 96, 204));
		btnPhongThuongUnactive.setBorder(new EmptyBorder(0, 0, 0, 0));
		btnPhongThuongUnactive.setBounds(374, 10, 132, 38);
		pnlLoaiPhong.add(btnPhongThuongUnactive);

		Button btnPhongVip = new Button("Phòng VIP");
		btnPhongVip.setRadius(9);
		btnPhongVip.setForeground(Color.WHITE);
		btnPhongVip.setFont(new Font("Segoe UI", Font.BOLD, 16));
		btnPhongVip.setFocusable(false);
		btnPhongVip.setColorOver(new Color(140, 177, 180, 178));
		btnPhongVip.setColorClick(new Color(140, 177, 180, 127));
		btnPhongVip.setColor(new Color(140, 177, 180, 178));
		btnPhongVip.setBorderWidth(2);
		btnPhongVip.setBorderColor(Utils.secondaryColor);
		btnPhongVip.setBorderBtnColor(new Color(0, 0, 0, 81));
		btnPhongVip.setBorder(new EmptyBorder(0, 0, 0, 0));
		btnPhongVip.setBounds(526, 10, 120, 38);
		pnlLoaiPhong.add(btnPhongVip);

		Button btnPhongVipUnactive = new Button("Phòng VIP");
		btnPhongVipUnactive.setRadius(9);
		btnPhongVipUnactive.setForeground(new Color(0, 0, 0, 148));
		btnPhongVipUnactive.setFont(new Font("Segoe UI", Font.BOLD, 16));
		btnPhongVipUnactive.setFocusable(false);
		btnPhongVipUnactive.setColorOver(Color.WHITE);
		btnPhongVipUnactive.setColorClick(new Color(255, 255, 255, 204));
		btnPhongVipUnactive.setColor(Color.WHITE);
		btnPhongVipUnactive.setBorderWidth(2);
		btnPhongVipUnactive.setBorderColor(Utils.secondaryColor);
		btnPhongVipUnactive.setBorderBtnColor(new Color(119, 96, 204));
		btnPhongVipUnactive.setBorder(new EmptyBorder(0, 0, 0, 0));
		btnPhongVipUnactive.setBounds(526, 10, 120, 38);
		pnlLoaiPhong.add(btnPhongVipUnactive);

//		Date time
		JPanel pnlDateTime = new JPanel();
		pnlDateTime.setBackground(Utils.secondaryColor);
		pnlDateTime.setBounds(949, 0, 105, 58);
		pnlLoaiPhong.add(pnlDateTime);
		pnlDateTime.setLayout(null);

		lblTime = new JLabel("18:07");
		lblTime.setForeground(new Color(0, 0, 0, 115));
		lblTime.setHorizontalAlignment(SwingConstants.CENTER);
		lblTime.setFont(new Font("Segoe UI", Font.BOLD, 16));
		lblTime.setBounds(0, 0, 105, 19);
		pnlDateTime.add(lblTime);

		lblThu = new JLabel("T2");
		lblThu.setHorizontalAlignment(SwingConstants.CENTER);
		lblThu.setForeground(new Color(0, 0, 0, 115));
		lblThu.setFont(new Font("Segoe UI", Font.BOLD, 16));
		lblThu.setBounds(0, 19, 105, 19);
		pnlDateTime.add(lblThu);

		lblDate = new JLabel("29-09-2022");
		lblDate.setHorizontalAlignment(SwingConstants.CENTER);
		lblDate.setForeground(new Color(0, 0, 0, 115));
		lblDate.setFont(new Font("Segoe UI", Font.BOLD, 16));
		lblDate.setBounds(0, 38, 105, 19);
		pnlDateTime.add(lblDate);

		JLabel lblIconDongHo = new JLabel("");
		lblIconDongHo.setIcon(Utils.getImageIcon("clock (1) 1.png"));
		lblIconDongHo.setBounds(885, -3, 64, 64);
		pnlLoaiPhong.add(lblIconDongHo);

//		Seperator
		JPanel pnlSeperatorTop = new JPanel();
		pnlSeperatorTop.setBounds(16, 59, 1082, 4);
		pnlSeperatorTop.setBackground(new Color(0, 0, 0, 64));
		pnlContent.add(pnlSeperatorTop);

//		Thống kê loại phòng
		JPanel pnlThongKeLoaiPhong = new JPanel();
		pnlThongKeLoaiPhong.setBackground(Utils.secondaryColor);
		pnlThongKeLoaiPhong.setBounds(952, 79, 146, 285);
		pnlContent.add(pnlThongKeLoaiPhong);
		pnlThongKeLoaiPhong.setLayout(null);

		PanelRound pnlPhongTrong = new PanelRound(10);
		pnlPhongTrong.setBackground(Utils.phongTrong);
		pnlPhongTrong.setBounds(0, 0, 146, 60);
		pnlThongKeLoaiPhong.add(pnlPhongTrong);
		pnlPhongTrong.setLayout(null);

		JLabel lblIconPhongTrong = new JLabel("");
		lblIconPhongTrong.setForeground(Color.WHITE);
		lblIconPhongTrong.setIcon(Utils.getImageIcon("cua.png"));
		lblIconPhongTrong.setBounds(12, 12, 35, 35);
		pnlPhongTrong.add(lblIconPhongTrong);

		JLabel lblText1 = new JLabel("Phòng trống");
		lblText1.setForeground(Color.WHITE);
		lblText1.setBounds(59, 11, 75, 19);
		pnlPhongTrong.add(lblText1);
		lblText1.setFont(new Font("Segoe UI", Font.BOLD, 12));

		lblSoPhongTrong = new JLabel("(0)");
		lblSoPhongTrong.setForeground(Color.WHITE);
		lblSoPhongTrong.setHorizontalAlignment(SwingConstants.CENTER);
		lblSoPhongTrong.setFont(new Font("Segoe UI", Font.BOLD, 12));
		lblSoPhongTrong.setBounds(59, 30, 75, 19);
		pnlPhongTrong.add(lblSoPhongTrong);

		PanelRound pnlPhongDangSuDung = new PanelRound(10);
		pnlPhongDangSuDung.setBackground(Utils.phongDangSuDung);
		pnlPhongDangSuDung.setLayout(null);
		pnlPhongDangSuDung.setBounds(0, 75, 146, 60);
		pnlThongKeLoaiPhong.add(pnlPhongDangSuDung);

		JLabel lblIconPhongDSD = new JLabel("");
		lblIconPhongDSD.setIcon(Utils.getImageIcon("cua.png"));
		lblIconPhongDSD.setBounds(12, 12, 35, 35);
		pnlPhongDangSuDung.add(lblIconPhongDSD);

		JLabel lblText2 = new JLabel("Phòng đang");
		lblText2.setForeground(Color.WHITE);
		lblText2.setFont(new Font("Segoe UI", Font.BOLD, 12));
		lblText2.setBounds(59, 2, 75, 19);
		pnlPhongDangSuDung.add(lblText2);

		JLabel lblText3 = new JLabel("sử dụng");
		lblText3.setForeground(Color.WHITE);
		lblText3.setFont(new Font("Segoe UI", Font.BOLD, 12));
		lblText3.setBounds(59, 21, 75, 19);
		pnlPhongDangSuDung.add(lblText3);

		lblSoPhongDSD = new JLabel("(0)");
		lblSoPhongDSD.setForeground(Color.WHITE);
		lblSoPhongDSD.setHorizontalAlignment(SwingConstants.CENTER);
		lblSoPhongDSD.setFont(new Font("Segoe UI", Font.BOLD, 12));
		lblSoPhongDSD.setBounds(59, 40, 75, 19);
		pnlPhongDangSuDung.add(lblSoPhongDSD);

		PanelRound pnlPhongCho = new PanelRound(10);
		pnlPhongCho.setBackground(Utils.phongCho);
		pnlPhongCho.setLayout(null);
		pnlPhongCho.setBounds(0, 150, 146, 60);
		pnlThongKeLoaiPhong.add(pnlPhongCho);

		JLabel lblIconPhongCho = new JLabel("");
		lblIconPhongCho.setIcon(Utils.getImageIcon("cua.png"));
		lblIconPhongCho.setBounds(12, 12, 35, 35);
		pnlPhongCho.add(lblIconPhongCho);

		JLabel lblText4 = new JLabel("Phòng chờ");
		lblText4.setForeground(Color.WHITE);
		lblText4.setFont(new Font("Segoe UI", Font.BOLD, 12));
		lblText4.setBounds(59, 11, 75, 19);
		pnlPhongCho.add(lblText4);

		lblSoLuongPhongCho = new JLabel("(0)");
		lblSoLuongPhongCho.setForeground(Color.WHITE);
		lblSoLuongPhongCho.setHorizontalAlignment(SwingConstants.CENTER);
		lblSoLuongPhongCho.setFont(new Font("Segoe UI", Font.BOLD, 12));
		lblSoLuongPhongCho.setBounds(59, 30, 75, 19);
		pnlPhongCho.add(lblSoLuongPhongCho);

		PanelRound pnlPhongTam = new PanelRound(10);
		pnlPhongTam.setLayout(null);
		pnlPhongTam.setBackground(Utils.phongTam);
		pnlPhongTam.setBounds(0, 225, 146, 60);
		pnlThongKeLoaiPhong.add(pnlPhongTam);

		JLabel lblIconPhongTam = new JLabel("");
		lblIconPhongTam.setIcon(Utils.getImageIcon("cua.png"));
		lblIconPhongTam.setBounds(12, 12, 35, 35);
		pnlPhongTam.add(lblIconPhongTam);

		JLabel lblPhongTam = new JLabel("Phòng tạm");
		lblPhongTam.setForeground(Color.WHITE);
		lblPhongTam.setFont(new Font("Segoe UI", Font.BOLD, 12));
		lblPhongTam.setBounds(59, 11, 75, 19);
		pnlPhongTam.add(lblPhongTam);

		lblSoLuongPhongTam = new JLabel("(0)");
		lblSoLuongPhongTam.setHorizontalAlignment(SwingConstants.CENTER);
		lblSoLuongPhongTam.setForeground(Color.WHITE);
		lblSoLuongPhongTam.setFont(new Font("Segoe UI", Font.BOLD, 12));
		lblSoLuongPhongTam.setBounds(59, 30, 75, 19);
		pnlPhongTam.add(lblSoLuongPhongTam);

//		Các nút chức năng
		JPanel pnlSeperatorBottom = new JPanel();
		pnlSeperatorBottom.setBackground(new Color(0, 0, 0, 64));
		pnlSeperatorBottom.setBounds(16, heightPnlContainerDanhSachPhong + 150, 1082, 4);
		pnlContent.add(pnlSeperatorBottom);

		JPanel pnlActions = new JPanel();
		pnlActions.setBackground(Utils.secondaryColor);
		pnlContent.add(pnlActions);
		pnlActions.setLayout(null);
		pnlActions.setBounds(16, heightPnlContainerDanhSachPhong + 170, 1082, 69);

		pnlDatPhong = new PanelEvent(13) {
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
		pnlDatPhong.setBackgroundColor(new Color(255, 154, 97));
		pnlActions.add(pnlDatPhong);
		pnlDatPhong.setLayout(null);
		JLabel lblDatPhong = new JLabel("Đặt phòng");
		lblDatPhong.setHorizontalAlignment(SwingConstants.CENTER);
		lblDatPhong.setForeground(Color.WHITE);
		lblDatPhong.setFont(new Font("Segoe UI", Font.BOLD, 17));
		pnlDatPhong.add(lblDatPhong);
		JLabel lblDatPhongF = new JLabel("F1");
		lblDatPhongF.setHorizontalAlignment(SwingConstants.CENTER);
		lblDatPhongF.setForeground(Color.WHITE);
		lblDatPhongF.setFont(new Font("Segoe UI", Font.BOLD, 17));
		pnlDatPhong.add(lblDatPhongF);
		pnlDatPhong.setBounds(0, 0, 125, 69);
		lblDatPhong.setBounds(10, 13, 105, 22);
		lblDatPhongF.setBounds(10, 35, 105, 22);

		pnlDatPhongTruoc = new PanelEvent(13) {
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
		pnlDatPhongTruoc.setLayout(null);
		pnlDatPhongTruoc.setBackgroundColor(new Color(255, 154, 97));
		pnlActions.add(pnlDatPhongTruoc);
		JLabel lblDatPhongTruoc = new JLabel("Đặt phòng trước");
		lblDatPhongTruoc.setHorizontalAlignment(SwingConstants.CENTER);
		lblDatPhongTruoc.setForeground(Color.WHITE);
		lblDatPhongTruoc.setFont(new Font("Segoe UI", Font.BOLD, 17));
		pnlDatPhongTruoc.add(lblDatPhongTruoc);
		JLabel lblDatPhongTruocF = new JLabel("F2");
		lblDatPhongTruocF.setHorizontalAlignment(SwingConstants.CENTER);
		lblDatPhongTruocF.setForeground(Color.WHITE);
		lblDatPhongTruocF.setFont(new Font("Segoe UI", Font.BOLD, 17));
		pnlDatPhongTruoc.add(lblDatPhongTruocF);
		pnlDatPhongTruoc.setBounds(140, 0, 170, 69);
		lblDatPhongTruoc.setBounds(10, 13, 150, 22);
		lblDatPhongTruocF.setBounds(10, 35, 150, 22);

		pnlChuyenPhong = new PanelEvent(13) {
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
		pnlChuyenPhong.setLayout(null);
		pnlChuyenPhong.setBackgroundColor(new Color(255, 154, 97));
		pnlActions.add(pnlChuyenPhong);
		JLabel lblChuyenPhong = new JLabel("Chuyển phòng");
		lblChuyenPhong.setHorizontalAlignment(SwingConstants.CENTER);
		lblChuyenPhong.setForeground(Color.WHITE);
		lblChuyenPhong.setFont(new Font("Segoe UI", Font.BOLD, 17));
		pnlChuyenPhong.add(lblChuyenPhong);
		JLabel lblChuyenPhongF = new JLabel("F3");
		lblChuyenPhongF.setHorizontalAlignment(SwingConstants.CENTER);
		lblChuyenPhongF.setForeground(Color.WHITE);
		lblChuyenPhongF.setFont(new Font("Segoe UI", Font.BOLD, 17));
		pnlChuyenPhong.add(lblChuyenPhongF);
		pnlChuyenPhong.setBounds(325, 0, 160, 69);
		lblChuyenPhong.setBounds(10, 13, 140, 22);
		lblChuyenPhongF.setBounds(10, 35, 140, 22);

		pnlGopPhong = new PanelEvent(13) {
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
		pnlGopPhong.setLayout(null);
		pnlGopPhong.setBackgroundColor(new Color(255, 154, 97));
		pnlActions.add(pnlGopPhong);
		JLabel lblGopPhong = new JLabel("Gộp phòng");
		lblGopPhong.setHorizontalAlignment(SwingConstants.CENTER);
		lblGopPhong.setForeground(Color.WHITE);
		lblGopPhong.setFont(new Font("Segoe UI", Font.BOLD, 17));
		pnlGopPhong.add(lblGopPhong);
		JLabel lblGopPhongF = new JLabel("F4");
		lblGopPhongF.setHorizontalAlignment(SwingConstants.CENTER);
		lblGopPhongF.setForeground(Color.WHITE);
		lblGopPhongF.setFont(new Font("Segoe UI", Font.BOLD, 17));
		pnlGopPhong.add(lblGopPhongF);
		pnlGopPhong.setBounds(500, 0, 125, 69);
		lblGopPhong.setBounds(10, 13, 105, 22);
		lblGopPhongF.setBounds(10, 35, 105, 22);

		pnlDichVu = new PanelEvent(13) {
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
		pnlDichVu.setLayout(null);
		pnlDichVu.setBackgroundColor(new Color(255, 154, 97));
		pnlActions.add(pnlDichVu);
		JLabel lblDichVu = new JLabel("Dịch vụ");
		lblDichVu.setHorizontalAlignment(SwingConstants.CENTER);
		lblDichVu.setForeground(Color.WHITE);
		lblDichVu.setFont(new Font("Segoe UI", Font.BOLD, 17));
		pnlDichVu.add(lblDichVu);
		JLabel lblDichVuF = new JLabel("F5");
		lblDichVuF.setHorizontalAlignment(SwingConstants.CENTER);
		lblDichVuF.setForeground(Color.WHITE);
		lblDichVuF.setFont(new Font("Segoe UI", Font.BOLD, 17));
		pnlDichVu.add(lblDichVuF);
		pnlDichVu.setBounds(640, 0, 125, 69);
		lblDichVu.setBounds(10, 13, 105, 22);
		lblDichVuF.setBounds(10, 35, 105, 22);

		pnlThanhToan = new PanelEvent(13) {
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
		pnlThanhToan.setLayout(null);
		pnlThanhToan.setBackgroundColor(new Color(255, 154, 97));
		pnlActions.add(pnlThanhToan);
		JLabel lblThanhToan = new JLabel("Thanh toán");
		lblThanhToan.setHorizontalAlignment(SwingConstants.CENTER);
		lblThanhToan.setForeground(Color.WHITE);
		lblThanhToan.setFont(new Font("Segoe UI", Font.BOLD, 17));
		pnlThanhToan.add(lblThanhToan);
		JLabel lblThanhToanF = new JLabel("F6");
		lblThanhToanF.setHorizontalAlignment(SwingConstants.CENTER);
		lblThanhToanF.setForeground(Color.WHITE);
		lblThanhToanF.setFont(new Font("Segoe UI", Font.BOLD, 17));
		pnlThanhToan.add(lblThanhToanF);
		pnlThanhToan.setBounds(780, 0, 125, 69);
		lblThanhToan.setBounds(10, 13, 105, 22);
		lblThanhToanF.setBounds(10, 35, 105, 22);

		btnPhongThuong.setVisible(false);
		btnPhongVip.setVisible(false);
		btnTatCa.setVisible(true);
		btnPhongThuongUnactive.setVisible(true);
		btnPhongVipUnactive.setVisible(true);

		JPanel pnlFilter = new JPanel();
		pnlFilter.setBackground(Utils.secondaryColor);
		pnlFilter.setBounds(16, 79, 900, 39);
		pnlContent.add(pnlFilter);
		pnlFilter.setLayout(null);

		pnlFilterPhongCho = new PanelEvent(8);
		pnlFilterPhongCho.setBackgroundColor(Utils.phongCho);
		pnlFilterPhongCho.setBounds(387, 0, 153, 39);
		pnlFilter.add(pnlFilterPhongCho);
		pnlFilterPhongCho.setLayout(null);

		JLabel lblFilterPhongCho = new JLabel("Phòng chờ");
		lblFilterPhongCho.setForeground(Color.WHITE);
		lblFilterPhongCho.setHorizontalAlignment(SwingConstants.CENTER);
		lblFilterPhongCho.setFont(new Font("Segoe UI", Font.BOLD, 16));
		lblFilterPhongCho.setBounds(0, 0, 153, 39);
		pnlFilterPhongCho.setActive(false);
		pnlFilterPhongCho.add(lblFilterPhongCho);

		pnlFilterPhongDangSuDung = new PanelEvent(8);
		pnlFilterPhongDangSuDung.setLayout(null);
		pnlFilterPhongDangSuDung.setBackgroundColor(Utils.phongDangSuDung);
		pnlFilterPhongDangSuDung.setBounds(174, 0, 192, 39);
		pnlFilter.add(pnlFilterPhongDangSuDung);

		JLabel lblFilterPhongDangSuDung = new JLabel("Phòng đang sử dụng");
		lblFilterPhongDangSuDung.setForeground(Color.WHITE);
		lblFilterPhongDangSuDung.setHorizontalAlignment(SwingConstants.CENTER);
		lblFilterPhongDangSuDung.setFont(new Font("Segoe UI", Font.BOLD, 16));
		lblFilterPhongDangSuDung.setBounds(0, 0, 192, 39);
		pnlFilterPhongDangSuDung.setActive(false);
		pnlFilterPhongDangSuDung.add(lblFilterPhongDangSuDung);

		pnlFilterPhongTrong = new PanelEvent(8);
		pnlFilterPhongTrong.setLayout(null);
		pnlFilterPhongTrong.setBackgroundColor(Utils.phongTrong);
		pnlFilterPhongTrong.setBounds(0, 0, 153, 39);
		pnlFilterPhongTrong.setActive(false);
		pnlFilter.add(pnlFilterPhongTrong);

		JLabel lblFilterPhongTrong = new JLabel("Phòng trống");
		lblFilterPhongTrong.setForeground(Color.WHITE);
		lblFilterPhongTrong.setHorizontalAlignment(SwingConstants.CENTER);
		lblFilterPhongTrong.setFont(new Font("Segoe UI", Font.BOLD, 16));
		lblFilterPhongTrong.setBounds(0, 0, 153, 39);
		pnlFilterPhongTrong.add(lblFilterPhongTrong);

		cmbSoLuong = new ComboBox<>();
		cmbSoLuong.setModel(new DefaultComboBoxModel<>(new String[] { "Số lượng khách", "5", "10", "20" }));
		cmbSoLuong.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED) {
					List<Phong> list = filterDanhSachPhong();
					if (list.size() <= 0) {
						jDialog.showMessage("Thông báo", "Mục này không có phòng nào");
						return;
					}
					addPhong(list);
				}
			}
		});
		cmbSoLuong.setBackground(Utils.primaryColor);
		cmbSoLuong.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		cmbSoLuong.setBounds(750, 2, 150, 35);
		pnlFilter.add(cmbSoLuong);

		pnlFilterPhongTam = new PanelEvent(8);
		pnlFilterPhongTam.setLayout(null);
		pnlFilterPhongTam.setBackgroundColor(Utils.phongTam);
		pnlFilterPhongTam.setActive(false);
		pnlFilterPhongTam.setBounds(561, 0, 153, 39);
		pnlFilter.add(pnlFilterPhongTam);

		JLabel lblFiletrPhongTam = new JLabel("Phòng tạm");
		lblFiletrPhongTam.setHorizontalAlignment(SwingConstants.CENTER);
		lblFiletrPhongTam.setForeground(Color.WHITE);
		lblFiletrPhongTam.setFont(new Font("Segoe UI", Font.BOLD, 16));
		lblFiletrPhongTam.setBounds(0, 0, 153, 39);
		pnlFilterPhongTam.add(lblFiletrPhongTam);

		pnlContainerDanhSachPhong = new JPanel();
		pnlContainerDanhSachPhong.setBounds(16, 134, 900, heightPnlContainerDanhSachPhong);
		pnlContainerDanhSachPhong.setBackground(Utils.secondaryColor);
		pnlContainerDanhSachPhong.setLayout(null);
		pnlContent.add(pnlContainerDanhSachPhong);

		JPanel pnlSeperatorRight = new JPanel();
		pnlSeperatorRight.setBackground(new Color(0, 0, 0, 64));
		pnlSeperatorRight.setBounds(932, 63, 4, heightPnlContainerDanhSachPhong + 87);
		pnlContent.add(pnlSeperatorRight);

//		Event loại phòng
		btnTatCaUnactive.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				setVisibleLoaiPhong(btnTatCa, btnPhongThuong, btnPhongVip);
				loaiPhong = "";
				List<Phong> list = filterDanhSachPhong();
				if (list.size() <= 0) {
					jDialog.showMessage("Thông báo", "Mục này không có phòng nào");
					return;
				}
				addPhong(list);
			}
		});

		btnPhongThuongUnactive.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				String loaiPhong = _this.loaiPhong;
				_this.loaiPhong = "Thường";
				List<Phong> list = filterDanhSachPhong();
				if (list.size() <= 0) {
					_this.loaiPhong = loaiPhong;
					jDialog.showMessage("Thông báo", "Mục này không có phòng nào");
					return;
				}
				setVisibleLoaiPhong(btnPhongThuong, btnPhongVip, btnTatCa);
				addPhong(list);
			}
		});

		btnPhongVipUnactive.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				String loaiPhong = _this.loaiPhong;
				_this.loaiPhong = "VIP";
				List<Phong> list = filterDanhSachPhong();
				if (list.size() <= 0) {
					_this.loaiPhong = loaiPhong;
					jDialog.showMessage("Thông báo", "Mục này không có phòng nào");
					return;
				}
				setVisibleLoaiPhong(btnPhongVip, btnPhongThuong, btnTatCa);
				addPhong(list);
			}
		});

//		Event trạng thái phòng
		pnlFilterPhongTrong.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				boolean isActive = pnlFilterPhongTrong.isActive();
				String trangThai = _this.trangThai;
				_this.trangThai = isActive ? "" : Phong.convertTrangThaiToString(TrangThai.Trong);
				List<Phong> list = filterDanhSachPhong();
				int length = list.size();
				setActivePnlFilter(length, isActive, pnlFilterPhongTrong, pnlFilterPhongCho, pnlFilterPhongDangSuDung,
						pnlFilterPhongTam);
				if (length <= 0) {
					_this.trangThai = trangThai;
					jDialog.showMessage("Thông báo", "Mục này không có phòng nào");
					return;
				}
				addPhong(list);
			}
		});

		pnlFilterPhongDangSuDung.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				boolean isActive = pnlFilterPhongDangSuDung.isActive();
				String trangThai = _this.trangThai;
				_this.trangThai = isActive ? "" : Phong.convertTrangThaiToString(TrangThai.DangThue);
				List<Phong> list = filterDanhSachPhong();
				int length = list.size();
				setActivePnlFilter(length, isActive, pnlFilterPhongDangSuDung, pnlFilterPhongTrong, pnlFilterPhongCho,
						pnlFilterPhongTam);
				if (length <= 0) {
					_this.trangThai = trangThai;
					jDialog.showMessage("Thông báo", "Mục này không có phòng nào");
					return;
				}
				addPhong(list);
			}
		});

		pnlFilterPhongCho.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				boolean isActive = pnlFilterPhongCho.isActive();
				String trangThai = _this.trangThai;
				_this.trangThai = isActive ? "" : Phong.convertTrangThaiToString(TrangThai.DaDat);
				List<Phong> list = filterDanhSachPhong();
				int length = list.size();
				setActivePnlFilter(length, isActive, pnlFilterPhongCho, pnlFilterPhongTrong, pnlFilterPhongDangSuDung,
						pnlFilterPhongTam);
				if (length <= 0) {
					_this.trangThai = trangThai;
					jDialog.showMessage("Thông báo", "Mục này không có phòng nào");
					return;
				}
				addPhong(list);
			}
		});

		pnlFilterPhongTam.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				boolean isActive = pnlFilterPhongTam.isActive();
				String trangThai = _this.trangThai;
				_this.trangThai = isActive ? "" : Phong.convertTrangThaiToString(TrangThai.PhongTam);
				List<Phong> list = filterDanhSachPhong();
				int length = list.size();
				setActivePnlFilter(length, isActive, pnlFilterPhongTam, pnlFilterPhongDangSuDung, pnlFilterPhongTrong,
						pnlFilterPhongCho);
				if (length <= 0) {
					_this.trangThai = trangThai;
					jDialog.showMessage("Thông báo", "Mục này không có phòng nào");
					return;
				}
				addPhong(list);
			}
		});

//		Event các chức năng
		pnlDatPhong.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				handleClickOutsidePnlPhong();
				handleOpenSubFrame(pnlDatPhong, new DatPhong_GUI(_this, QuanLyDatPhong_GUI.this.main));
			}
		});

		pnlDatPhongTruoc.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				handleClickOutsidePnlPhong();
				handleOpenSubFrame(pnlDatPhongTruoc, new DatPhongTruoc_GUI(_this, QuanLyDatPhong_GUI.this.main));
			}
		});

		pnlChuyenPhong.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				handleClickOutsidePnlPhong();
				handleOpenSubFrame(pnlChuyenPhong, new ChuyenPhong_GUI(_this, QuanLyDatPhong_GUI.this.main));
			}
		});

		pnlGopPhong.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				handleClickOutsidePnlPhong();
				handleOpenSubFrame(pnlGopPhong, new GopPhong_GUI(_this, QuanLyDatPhong_GUI.this.main));
			}
		});

		pnlDichVu.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				handleClickOutsidePnlPhong();
				handleOpenSubFrame(pnlDichVu, new QuanLyDichVuPhongDat_GUI(_this, QuanLyDatPhong_GUI.this.main));
			}
		});

		pnlThanhToan.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				handleClickOutsidePnlPhong();
				handleOpenSubFrame(pnlThanhToan, new ThanhToan_GUI(_this, QuanLyDatPhong_GUI.this.main));
			}
		});
	}

	/**
	 * Thêm danh sách các phòng vào JPanel Container
	 */
	private void addPhong(List<Phong> dsPhong) {
		pnlPhongItem = null;
		pnlContainerDanhSachPhong.removeAll();

		soPhongCho = 0;
		soPhongDangSuDung = 0;
		soPhongTrong = 0;
		soPhongTam = 0;

		pnlDanhSachPhong = new JPanel();
		pnlDanhSachPhong.addAncestorListener(new AncestorListener() {
			@Override
			public void ancestorAdded(AncestorEvent event) {
				pnlDanhSachPhong.addMouseListener(mouseListener);
			}

			@Override
			public void ancestorMoved(AncestorEvent event) {
			}

			@Override
			public void ancestorRemoved(AncestorEvent event) {
				pnlDanhSachPhong.removeMouseListener(mouseListener);
			}
		});
		pnlDanhSachPhong.setBackground(Utils.secondaryColor);
		pnlDanhSachPhong.setLayout(null);
		JPanel phong1;
		for (int i = 0; i < dsPhong.size(); ++i) {
			phong1 = getPhong(dsPhong.get(i), getBounds(i));
			pnlDanhSachPhong.add(phong1);
		}

		pnlDanhSachPhong.setPreferredSize(getSizeContainerDanhSachPhong(dsPhong.size()));

		scrDanhSachPhong = new JScrollPane(pnlDanhSachPhong);
		scrDanhSachPhong.setBorder(new EmptyBorder(0, 0, 0, 0));
		scrDanhSachPhong.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrDanhSachPhong.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrDanhSachPhong.setBackground(Utils.secondaryColor);
		scrDanhSachPhong.setBounds(0, 0, 900, heightPnlContainerDanhSachPhong);
		pnlContainerDanhSachPhong.add(scrDanhSachPhong);
		ScrollBarCustom scb = new ScrollBarCustom();
		scb.setBackgroundColor(Utils.secondaryColor);
		scb.setScrollbarColor(Utils.primaryColor);
		scrDanhSachPhong.setVerticalScrollBar(scb);

		List<Phong> dsPhongDatNgay = datPhong_DAO.getPhongDatNgay();

		pnlThanhToan.setEnabled(soPhongDangSuDung + soPhongTam > 0);
		pnlDatPhong.setEnabled(dsPhongDatNgay.size() > 0);
		pnlChuyenPhong.setEnabled(dsPhongDatNgay.size() > 0 && soPhongDangSuDung + soPhongTam > 0);
		pnlGopPhong.setEnabled(datPhong_DAO.isGopPhong());
		pnlDichVu.setEnabled(soPhongDangSuDung + soPhongTam > 0);
		capNhatThongKeLoaiPhong();
		repaint();
	}

	/**
	 * Cập nhật số lượng các loại phòng theo trạng thái
	 */
	private void capNhatThongKeLoaiPhong() {
		lblSoPhongTrong.setText(String.format("(%d)", soPhongTrong));
		lblSoLuongPhongCho.setText(String.format("(%d)", soPhongCho));
		lblSoPhongDSD.setText(String.format("(%d)", soPhongDangSuDung));
		lblSoLuongPhongTam.setText(String.format("(%d)", soPhongTam));
	}

	public void capNhatTrangThaiPhong() {
		addPhong(filterDanhSachPhong());
	}

	/**
	 * Cập nhật thời gian thực
	 */
	public void clock() {
		clock = new Thread() {
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
						lblTime.setText(Utils.convertLocalTimeToString(LocalTime.of(hour, minute)));
						LocalDate date = LocalDate.now();
						DayOfWeek dayNow = date.getDayOfWeek();
						String thu = "T2";
						int dayValue = dayNow.getValue();
						if (dayValue == DayOfWeek.TUESDAY.getValue())
							thu = "T3";
						else if (dayValue == DayOfWeek.WEDNESDAY.getValue())
							thu = "T4";
						else if (dayValue == DayOfWeek.THURSDAY.getValue())
							thu = "T5";
						else if (dayValue == DayOfWeek.FRIDAY.getValue())
							thu = "T6";
						else if (dayValue == DayOfWeek.SATURDAY.getValue())
							thu = "T7";
						else if (dayValue == DayOfWeek.SUNDAY.getValue())
							thu = "CN";
						lblThu.setText(thu);
						lblDate.setText(String.format("%s-%s-%d", day < 10 ? "0" + day : day,
								month < 10 ? "0" + month : month, year));
						sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		};

		clock.start();
	}

	public void closeJFrameSub() {
		if (jFrameSub != null)
			jFrameSub.setVisible(false);
		glass.setVisible(false);
		glass.setAlpha(0f);
		jFrameSub = null;
	}

	@Override
	public boolean dispatchKeyEvent(KeyEvent e) {

		if (!_this.isShowing()) {
			KeyboardFocusManager.getCurrentKeyboardFocusManager().removeKeyEventDispatcher(this);
			return false;
		}
		if (jFrameSub != null && jFrameSub.isVisible())
			return false;

		int keyCode = e.getKeyCode();

		switch (keyCode) {
		case KeyEvent.VK_F1:
			handleOpenSubFrame(pnlDatPhong, new DatPhong_GUI(_this, QuanLyDatPhong_GUI.this.main));
			break;
		case KeyEvent.VK_F2:
			handleOpenSubFrame(pnlDatPhongTruoc, new DatPhongTruoc_GUI(_this, QuanLyDatPhong_GUI.this.main));
			break;
		case KeyEvent.VK_F3:
			handleOpenSubFrame(pnlChuyenPhong, new ChuyenPhong_GUI(_this, QuanLyDatPhong_GUI.this.main));
			break;
		case KeyEvent.VK_F4:
			handleOpenSubFrame(pnlGopPhong, new GopPhong_GUI(_this, QuanLyDatPhong_GUI.this.main));
			break;
		case KeyEvent.VK_F5:
			handleOpenSubFrame(pnlDatPhong, new QuanLyDichVuPhongDat_GUI(_this, QuanLyDatPhong_GUI.this.main));
			break;
		case KeyEvent.VK_F6:
			handleOpenSubFrame(pnlThanhToan, new ThanhToan_GUI(_this, QuanLyDatPhong_GUI.this.main));
			break;
		default:
			break;
		}

		return false;
	}

	private List<Phong> filterDanhSachPhong() {
		return phong_DAO.getAllPhong(trangThai, loaiPhong, (String) cmbSoLuong.getSelectedItem());
	}

	/**
	 * Get Bounds JPanel Item theo index
	 *
	 * @param itemIndex
	 * @return
	 */
	private Rectangle getBounds(int itemIndex) {
		int row = itemIndex / numOfRow;

		return new Rectangle(itemIndex % numOfRow * (gapX + widthPhong), row * (gapY + heightPhong), widthPhong,
				heightPhong);
	}

	public Glass getGlass() {
		return glass;
	}

	public Main getjFrame() {
		return main;
	}

	/**
	 * Get JPanel Item phòng
	 *
	 * @param phong
	 * @param rectangle
	 * @return JPanel Item
	 */
	private JPanel getPhong(Phong phong, Rectangle rectangle) {
		JPanel pnlPhong1 = new JPanel();
		pnlPhong1.setBackground(Utils.secondaryColor);
		pnlPhong1.setBounds(rectangle);
		pnlPhong1.setLayout(null);

		JLabel lblIcon1 = new JLabel("");
		lblIcon1.setIcon(Utils.getImageIcon("vip 1.png"));
		lblIcon1.setBounds(53, 0, 25, 25);
		if (phong.getTenLoai().equals("L002"))
			pnlPhong1.add(lblIcon1);

//		Trạng thái
		TrangThai trangThai = phong.getTrangThai();

		Color bg = Utils.phongDangSuDung;
		if (trangThai.equals(TrangThai.DaDat)) {
			soPhongCho++;
			bg = Utils.phongCho;
		} else if (trangThai.equals(TrangThai.Trong)) {
			soPhongTrong++;
			bg = Utils.phongTrong;
		} else if (trangThai.equals(TrangThai.PhongTam)) {
			soPhongTam++;
			bg = Utils.phongTam;
		} else
			soPhongDangSuDung++;

		PanelRound pnlChiTietPhong = new PanelRound(10);
		pnlChiTietPhong.setBackground(bg);
		pnlChiTietPhong.setBounds(0, 25, 131, 105);
		pnlPhong1.add(pnlChiTietPhong);
		pnlChiTietPhong.setLayout(null);

		JLabel lblMaPhong = new JLabel(phong.getMaPhong());
		lblMaPhong.setHorizontalAlignment(SwingConstants.CENTER);
		lblMaPhong.setForeground(Color.WHITE);
		lblMaPhong.setFont(new Font("Segoe UI", Font.BOLD, 26));
		lblMaPhong.setBounds(0, 0, 131, 33);
		pnlChiTietPhong.add(lblMaPhong);

		LocalTime gioVao = datPhong_DAO.getGioVao(phong);

		JLabel lblTongGio = new JLabel("Tổng giờ: " + "00:00");

		Thread clock = new Thread() {
			@Override
			public void run() {
				LocalDate ngayNhanPhong = datPhong_DAO.getNgayNhanPhongCuaPhongDangThue(phong.getMaPhong());
				for (;;) {
					try {
						long daysElapsed = java.time.temporal.ChronoUnit.DAYS.between(ngayNhanPhong, LocalDate.now());
						LocalTime timeNow = LocalTime.now();
						int hieuPhut = timeNow.getHour() * 60 + timeNow.getMinute() - gioVao.getHour() * 60
								- gioVao.getMinute();

						if (daysElapsed > 0)
							hieuPhut += 24 * daysElapsed * 60;

						String tongGio = String.format("%s:%s", Utils.convertIntToString(hieuPhut / 60),
								Utils.convertIntToString(hieuPhut % 60));

						lblTongGio.setText("Tổng giờ: " + tongGio);
						sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		};

		if (gioVao != null && (trangThai.equals(TrangThai.DangThue) || trangThai.equals(TrangThai.PhongTam)))
			clock.start();

		lblTongGio.setForeground(Color.WHITE);
		lblTongGio.setFont(new Font("Segoe UI", Font.BOLD, 14));
		lblTongGio.setBounds(5, 80, 121, 18);
		pnlChiTietPhong.add(lblTongGio);

		JLabel lblGioVao = new JLabel("Giờ vào: " + (gioVao != null ? Utils.convertLocalTimeToString(gioVao) : "0"));
		lblGioVao.setForeground(Color.WHITE);
		lblGioVao.setFont(new Font("Segoe UI", Font.BOLD, 14));
		lblGioVao.setBounds(5, 62, 121, 18);
		pnlChiTietPhong.add(lblGioVao);

		JLabel lblSoKhach = new JLabel("Số khách: " + phong.getSoLuongKhach());
		lblSoKhach.setForeground(Color.WHITE);
		lblSoKhach.setFont(new Font("Segoe UI", Font.BOLD, 14));
		lblSoKhach.setBounds(5, 44, 121, 18);
		pnlChiTietPhong.add(lblSoKhach);

		MouseListener mouseListener = new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				handleClickOutsidePnlPhong();
				if (trangThai.equals(TrangThai.PhongTam) || trangThai.equals(TrangThai.DangThue)) {
					pnlPhongItem = pnlPhong1;
					pnlDanhSachPhong.remove(pnlDanhSachPhong.getComponentAt(pnlPhong1.getLocation()));
					pnlDanhSachPhong.add(new Popup(_this, phong, rectangle));
					repaint();
				}
			}
		};
		pnlChiTietPhong.addAncestorListener(new AncestorListener() {

			@Override
			public void ancestorAdded(AncestorEvent event) {
				pnlChiTietPhong.addMouseListener(mouseListener);
			}

			@Override
			public void ancestorMoved(AncestorEvent event) {
				// TODO Auto-generated method stub

			}

			@Override
			public void ancestorRemoved(AncestorEvent event) {
				pnlChiTietPhong.removeMouseListener(mouseListener);
			}
		});

		return pnlPhong1;
	}

	/**
	 * Get kích thước JPanel chứa các JPanel Phong Item
	 *
	 * @param soPhong
	 * @return
	 */
	private Dimension getSizeContainerDanhSachPhong(int soPhong) {
		int row = (int) Math.ceil(soPhong * 1.0 / numOfRow);

		return new Dimension(890, Math.max((row - 1) * gapY + row * heightPhong, heightPnlContainerDanhSachPhong));
	}

	public void handleClickOutsidePnlPhong() {
		if (pnlDanhSachPhong == null)
			return;

		if (pnlPhongItem != null) {
			pnlDanhSachPhong.remove(pnlDanhSachPhong.getComponentAt(pnlPhongItem.getLocation()));
			pnlDanhSachPhong.add(pnlPhongItem);
			pnlPhongItem = null;
			repaint();
		}
	}

	public void handleOpenSubFrame(JPanel pnl, JFrame main) {
		if (!pnl.isEnabled())
			return;
		openJFrameSub(main);
	}

	public void openJFrameSub(JFrame main) {
		this.main.setGlassPane(glass);
		glass.setVisible(true);
		glass.setAlpha(0.5f);
		jFrameSub = main;
		jFrameSub.setVisible(true);
	}

	private void setActivePnlFilter(int lengthList, boolean isActive, PanelEvent pnlActive, PanelEvent... pnls) {
		if (lengthList > 0) {
			if (!isActive)
				for (PanelEvent panelEvent : pnls)
					panelEvent.setActive(false);
			pnlActive.setActive(!isActive);
		}
	}

	private void setVisibleLoaiPhong(Button btnVisible, Button... btns) {
		for (Button btn : btns)
			btn.setVisible(false);
		btnVisible.setVisible(true);
	}
}
