package ui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;

import com.raven.datechooser.DateChooser;
import com.raven.datechooser.EventDateChooser;
import com.raven.datechooser.SelectedAction;
import com.raven.datechooser.SelectedDate;

import components.button.Button;
import components.controlPanel.ControlPanel;
import components.jDialog.Glass;
import components.jDialog.JDialogCustom;
import components.notification.Notification;
import components.scrollbarCustom.ScrollBarCustom;
import components.textField.TextField;
import dao.ChiTietDatPhong_DAO;
import dao.DonDatPhong_DAO;
import dao.KhachHang_DAO;
import dao.PhieuDatPhongTruoc_DAO;
import entity.ChiTietDatPhong;
import entity.DonDatPhong;
import entity.Phong;
import utils.Utils;

public class QuanLyPhieuDatPhongTruoc_GUI extends JPanel {
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

	private QuanLyPhieuDatPhongTruoc_GUI _this;
	private Button btnHuyPhong;
	private Button btnLamMoi;
	private Button btnNhanPhong;
	private Button btnSearch;
	private Button btnSuaPhong;
	private Button btnXemPhong;
	private JComboBox<String> cboTrangThai;
	private ChiTietDatPhong_DAO chiTietDatPhong_DAO;
	private DateChooser dateChoose;
	private DonDatPhong_DAO donDatPhong_DAO;
	private Glass glass;
	private JFrame jFrameSub;
	private KhachHang_DAO khachHang_DAO;
	private Main main;

	private PhieuDatPhongTruoc_DAO phieuDatPhongTruoc_DAO;
	private ControlPanel pnlControl;
	private DefaultTableModel tableModel;
	private JTable tbl;
	private TextField txtNgayNhanPhong;
	private JTextField txtSoDienThoai;
	private final int widthPnlContainer = 1086;

	/**
	 * Create the frame.
	 */
	public QuanLyPhieuDatPhongTruoc_GUI(Main main) {
		glass = new Glass();
		_this = this;
		khachHang_DAO = new KhachHang_DAO();
		phieuDatPhongTruoc_DAO = new PhieuDatPhongTruoc_DAO();
		chiTietDatPhong_DAO = new ChiTietDatPhong_DAO();
		donDatPhong_DAO = new DonDatPhong_DAO();
		this.main = main;

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
		pnlContainer.setBounds(Utils.getLeft(widthPnlContainer), 0, widthPnlContainer, Utils.getBodyHeight());
		pnlContainer.setLayout(null);
		this.add(pnlContainer);

//		Search
		JPanel pnlSearch = new JPanel();
		pnlSearch.setBackground(Utils.secondaryColor);
		pnlSearch.setBounds(16, 10, 1054, 75);
		pnlContainer.add(pnlSearch);
		pnlSearch.setLayout(null);

		lblTime = new JLabel("");
		lblTime.setHorizontalAlignment(SwingConstants.LEFT);
		lblTime.setFont(new Font("Segoe UI", Font.PLAIN, 18));
		lblTime.setBounds(874, 0, 180, 24);
		pnlSearch.add(lblTime);
		clock();

		JPanel pnlNgayNhanPhong = new JPanel();
		pnlNgayNhanPhong.setBackground(Utils.secondaryColor);
		pnlNgayNhanPhong.setBounds(0, 30, 380, 55);
		pnlSearch.add(pnlNgayNhanPhong);
		pnlNgayNhanPhong.setLayout(null);

		JLabel lblNgayNhanPhong = new JLabel("Ngày nhận phòng");
		lblNgayNhanPhong.setFont(new Font("Segoe UI", Font.PLAIN, 23));
		lblNgayNhanPhong.setBounds(0, 0, 200, 36);
		pnlNgayNhanPhong.add(lblNgayNhanPhong);

		txtNgayNhanPhong = new TextField();
		txtNgayNhanPhong.setIcon(Utils.getImageIcon("add-event 2.png"));
		txtNgayNhanPhong.setLabelText("");
		txtNgayNhanPhong.setFont(new Font("Segoe UI", Font.PLAIN, 20));
		txtNgayNhanPhong.setColumns(10);
		txtNgayNhanPhong.setBackground(Utils.primaryColor);
		txtNgayNhanPhong.setBounds(200, -12, 180, 50);
		pnlNgayNhanPhong.add(txtNgayNhanPhong);
		dateChoose = new DateChooser();
		dateChoose.setDateFormat("dd/MM/yyyy");
		dateChoose.setTextRefernce(txtNgayNhanPhong);

		JPanel pnlTrangThai = new JPanel();
		pnlTrangThai.setBackground(Utils.secondaryColor);
		pnlTrangThai.setBounds(385, 30, 275, 36);
		pnlSearch.add(pnlTrangThai);
		pnlTrangThai.setLayout(null);

		JLabel lblTrangThai = new JLabel("Trạng thái");
		lblTrangThai.setBounds(0, 0, 115, 36);
		lblTrangThai.setFont(new Font("Segoe UI", Font.PLAIN, 24));
		pnlTrangThai.add(lblTrangThai);

		cboTrangThai = new JComboBox<String>();
		cboTrangThai.setModel(new DefaultComboBoxModel<String>(new String[] { "Trạng thái", "Đang chờ", "Đã hủy" }));
		cboTrangThai.setFont(new Font("Segoe UI", Font.PLAIN, 20));
		cboTrangThai.setBackground(Utils.primaryColor);
		cboTrangThai.setBounds(125, 0, 140, 36);
		pnlTrangThai.add(cboTrangThai);

		JPanel pnlSoDienThoai = new JPanel();
		pnlSoDienThoai.setBackground(Utils.secondaryColor);
		pnlSoDienThoai.setBounds(665, 30, 275, 36);
		pnlSearch.add(pnlSoDienThoai);
		pnlSoDienThoai.setLayout(null);

		JLabel lblSoDienThoai = new JLabel("SĐT khách");
		lblSoDienThoai.setBounds(0, 0, 115, 36);
		lblSoDienThoai.setFont(new Font("Segoe UI", Font.PLAIN, 24));
		pnlSoDienThoai.add(lblSoDienThoai);

		JPanel pnlInputSoDienThoai = new JPanel();
		pnlInputSoDienThoai.setBackground(Utils.secondaryColor);
		pnlInputSoDienThoai.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		pnlInputSoDienThoai.setBounds(125, 0, 150, 36);
		pnlSoDienThoai.add(pnlInputSoDienThoai);
		pnlInputSoDienThoai.setLayout(null);

		txtSoDienThoai = new JTextField();
		txtSoDienThoai.setFont(new Font("Segoe UI", Font.PLAIN, 24));
		txtSoDienThoai.setColumns(10);
		txtSoDienThoai.setBorder(null);
		txtSoDienThoai.setBackground(Utils.secondaryColor);
		txtSoDienThoai.setBounds(8, 1, 134, 34);
		pnlInputSoDienThoai.add(txtSoDienThoai);

		btnSearch = new Button("Tìm");
		btnSearch.setFocusable(false);
		btnSearch.setIcon(Utils.getImageIcon("searching.png"));
		btnSearch.setRadius(4);
		btnSearch.setForeground(Color.WHITE);
		btnSearch.setColor(new Color(134, 229, 138));
		btnSearch.setFont(new Font("Segoe UI", Font.PLAIN, 20));
		btnSearch.setBounds(954, 30, 100, 40);
		btnSearch.setBorderColor(Utils.secondaryColor);
		btnSearch.setColorOver(new Color(134, 229, 138));
		btnSearch.setColorClick(new Color(59, 238, 66));
		btnSearch.setBorder(new EmptyBorder(0, 0, 0, 0));
		pnlSearch.add(btnSearch);

		JPanel pnlActions = new JPanel();
		pnlActions.setBackground(Utils.secondaryColor);
		pnlActions.setBounds(16, 104, 1054, 36);
		pnlContainer.add(pnlActions);
		pnlActions.setLayout(null);

		btnXemPhong = new Button("Xem phòng");
		btnXemPhong.setFocusable(false);
		btnXemPhong.setIcon(Utils.getImageIcon("room.png"));
		btnXemPhong.setRadius(4);
		btnXemPhong.setForeground(Color.WHITE);
		btnXemPhong.setFont(new Font("Segoe UI", Font.PLAIN, 20));
		btnXemPhong.setColorOver(Utils.primaryColor);
		btnXemPhong.setColorClick(new Color(161, 184, 186));
		btnXemPhong.setColor(Utils.primaryColor);
		btnXemPhong.setBorderColor(Utils.secondaryColor);
		btnXemPhong.setBorder(new EmptyBorder(0, 0, 0, 0));
		btnXemPhong.setBounds(0, 0, 200, 36);
		pnlActions.add(btnXemPhong);

		btnNhanPhong = new Button("Nhận phòng");
		btnNhanPhong.setRadius(4);
		btnNhanPhong.setIcon(Utils.getImageIcon("check-in (1).png"));
		btnNhanPhong.setForeground(Color.WHITE);
		btnNhanPhong.setFont(new Font("Segoe UI", Font.PLAIN, 20));
		btnNhanPhong.setFocusable(false);
		btnNhanPhong.setColorOver(Utils.primaryColor);
		btnNhanPhong.setColorClick(new Color(161, 184, 186));
		btnNhanPhong.setColor(Utils.primaryColor);
		btnNhanPhong.setBorderColor(Utils.secondaryColor);
		btnNhanPhong.setBorder(new EmptyBorder(0, 0, 0, 0));
		btnNhanPhong.setBounds(220, 0, 200, 36);
		pnlActions.add(btnNhanPhong);

		btnHuyPhong = new Button("Hủy phòng");
		btnHuyPhong.setRadius(4);
		btnHuyPhong.setIcon(Utils.getImageIcon("door.png"));
		btnHuyPhong.setForeground(Color.WHITE);
		btnHuyPhong.setFont(new Font("Segoe UI", Font.PLAIN, 20));
		btnHuyPhong.setFocusable(false);
		btnHuyPhong.setColorOver(Utils.primaryColor);
		btnHuyPhong.setColorClick(new Color(161, 184, 186));
		btnHuyPhong.setColor(Utils.primaryColor);
		btnHuyPhong.setBorderColor(Utils.secondaryColor);
		btnHuyPhong.setBorder(new EmptyBorder(0, 0, 0, 0));
		btnHuyPhong.setBounds(440, 0, 200, 36);
		pnlActions.add(btnHuyPhong);

		btnSuaPhong = new Button("Sửa phòng");
		btnSuaPhong.setRadius(4);
		btnSuaPhong.setIcon(Utils.getImageIcon("change-door.png"));
		btnSuaPhong.setForeground(Color.WHITE);
		btnSuaPhong.setFont(new Font("Segoe UI", Font.PLAIN, 20));
		btnSuaPhong.setFocusable(false);
		btnSuaPhong.setColorOver(Utils.primaryColor);
		btnSuaPhong.setColorClick(new Color(161, 184, 186));
		btnSuaPhong.setColor(Utils.primaryColor);
		btnSuaPhong.setBorderColor(Utils.secondaryColor);
		btnSuaPhong.setBorder(new EmptyBorder(0, 0, 0, 0));
		btnSuaPhong.setBounds(660, 0, 200, 36);
		pnlActions.add(btnSuaPhong);

		btnLamMoi = new Button("Làm mới");
		btnLamMoi.setRadius(4);
		btnLamMoi.setIcon(Utils.getImageIcon("reset.png"));
		btnLamMoi.setForeground(Color.WHITE);
		btnLamMoi.setFont(new Font("Segoe UI", Font.PLAIN, 20));
		btnLamMoi.setFocusable(false);
		btnLamMoi.setColorOver(Utils.primaryColor);
		btnLamMoi.setColorClick(new Color(161, 184, 186));
		btnLamMoi.setColor(Utils.primaryColor);
		btnLamMoi.setBorderColor(Utils.secondaryColor);
		btnLamMoi.setBorder(new EmptyBorder(0, 0, 0, 0));
		btnLamMoi.setBounds(880, 0, 180, 36);
		pnlActions.add(btnLamMoi);

//		Table danh sách phiếu đặt phòng trước
		int topPnlControl = Utils.getBodyHeight();
		topPnlControl -= 80;

		JScrollPane scr = new JScrollPane();
		scr.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scr.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scr.setBounds(16, 158, 1054, topPnlControl - 174);
		scr.setBackground(Utils.primaryColor);
		scr.getViewport().setBackground(Color.WHITE);

		ScrollBarCustom scp = new ScrollBarCustom();
		scp.setScrollbarColor(new Color(203, 203, 203));
		scr.setVerticalScrollBar(scp);
		pnlContainer.add(scr);
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
		tbl.setAutoCreateRowSorter(true);
		tableModel = new DefaultTableModel(new String[] { "Mã phiếu đặt", "SĐT khách", "Thời gian lập phiếu",
				"Thời gian nhận phòng", "Phòng", "Trạng thái" }, 0);
		JTableHeader tblHeader = tbl.getTableHeader();
		TableColumnModel tableColumnModel = tbl.getColumnModel();
		tbl.setModel(tableModel);
		tbl.setFocusable(false);

		tableColumnModel.getColumn(0).setPreferredWidth(150);
		tableColumnModel.getColumn(1).setPreferredWidth(150);
		tableColumnModel.getColumn(2).setPreferredWidth(200);
		tableColumnModel.getColumn(3).setPreferredWidth(200);
		tableColumnModel.getColumn(4).setPreferredWidth(218);
		tableColumnModel.getColumn(5).setPreferredWidth(125);
		tblHeader.setBackground(Utils.primaryColor);
		tbl.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		tbl.setBackground(Color.WHITE);
		tbl.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		tblHeader.setPreferredSize(new Dimension((int) tblHeader.getPreferredSize().getWidth(), 36));
		tblHeader.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		tbl.setRowHeight(36);
		scr.setViewportView(tbl);

		pnlControl = new ControlPanel(Utils.getLeft(widthPnlContainer, 286), topPnlControl, main);
		pnlContainer.add(pnlControl);

//		Sự kiện nút tìm kiếm phiếu đặt phòng
		btnSearch.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				filterDonDatPhong();
			}
		});

//		Sự kiện nút xem thông tin phiếu đặt phòng
		btnXemPhong.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int row = tbl.getSelectedRow();
				if (row == -1) {
					new JDialogCustom(main, components.jDialog.JDialogCustom.Type.warning).showMessage("Warning",
							"Vui lòng chọn phòng");
					return;
				}

				String maPhieuDat = (String) tableModel.getValueAt(row, 0);
				ChiTietDatPhong chiTietDatPhong = phieuDatPhongTruoc_DAO
						.getChiTietDatPhongTheoMa(new DonDatPhong(maPhieuDat));
				ThongTinChiTietPhieuDatPhongTruoc_GUI jFrame = new ThongTinChiTietPhieuDatPhongTruoc_GUI(main,
						chiTietDatPhong);
				main.addPnlBody(jFrame, "Thông tin chi tiêt phiếu đặt phòng trước", 1, 0);

			}
		});
//		Sự kiện nút nhận phòng
		btnNhanPhong.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (!btnNhanPhong.isEnabled())
					return;

				boolean res = false;
				int row = tbl.getSelectedRow();

				if (row == -1) {
					new JDialogCustom(main, components.jDialog.JDialogCustom.Type.warning).showMessage("Warning",
							"Vui lòng chọn phòng");
					return;
				}

				String maPhieuDat = (String) tableModel.getValueAt(row, 0);
				ChiTietDatPhong chiTietDatPhong = phieuDatPhongTruoc_DAO
						.getChiTietDatPhongTheoMa(new DonDatPhong(maPhieuDat));
				DonDatPhong donDatPhong = donDatPhong_DAO.getDatPhong(maPhieuDat);

				List<Phong> listPhong = new ArrayList<>();
				List<ChiTietDatPhong> listChiTietDatPhong = chiTietDatPhong_DAO
						.getAllChiTietDatPhong(chiTietDatPhong.getDonDatPhong());
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

				if (!res) {
					new JDialogCustom(main, components.jDialog.JDialogCustom.Type.warning).showMessage("Warning",
							"Chưa đến giờ nhận phòng");
					return;
				}

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
						filterDonDatPhong();
					}
				});
				jDialogCustom.showMessage("Question",
						"Nhận phòng thành công! \nBạn có muốn chuyển sang trang quản lý đặt phòng");

			}
		});

//		Sự kiện nút Huỷ phòng
		btnHuyPhong.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (!btnHuyPhong.isEnabled())
					return;

				boolean res = false;
				int row = tbl.getSelectedRow();

				if (row == -1) {
					new JDialogCustom(main, components.jDialog.JDialogCustom.Type.warning).showMessage("Warning",
							"Vui lòng chọn phòng");
					return;
				}

				String maPhieuDat = (String) tableModel.getValueAt(row, 0);
				ChiTietDatPhong chiTietDatPhong = phieuDatPhongTruoc_DAO
						.getChiTietDatPhongTheoMa(new DonDatPhong(maPhieuDat));
				List<Phong> listPhong = new ArrayList<>();
				List<ChiTietDatPhong> listChiTietDatPhong = chiTietDatPhong_DAO
						.getAllChiTietDatPhong(chiTietDatPhong.getDonDatPhong());
				listChiTietDatPhong.forEach(list -> listPhong.add(list.getPhong()));

				String trangThai = (String) tableModel.getValueAt(row, 5);
				if (trangThai.equals("Đã hủy")) {
					new Notification(main, components.notification.Notification.Type.ERROR, "Phòng đã huỷ")
							.showNotification();
					return;
				}

				res = donDatPhong_DAO.huyPhongTrongPhieuDatPhongTruoc(donDatPhong_DAO.getDatPhong(maPhieuDat),
						listPhong);

				if (res) {
					new Notification(main, components.notification.Notification.Type.SUCCESS, "Huỷ phòng thành công")
							.showNotification();
					loadTable();
					return;
				} else {
					new Notification(main, components.notification.Notification.Type.ERROR, "Huỷ phòng thất bại")
							.showNotification();
					return;
				}

			}
		});

//		Sự kiện nút sửa phòng
		btnSuaPhong.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (!btnSuaPhong.isEnabled())
					return;

				int row = tbl.getSelectedRow();

				if (row == -1) {
					new JDialogCustom(main, components.jDialog.JDialogCustom.Type.warning).showMessage("Warning",
							"Vui lòng chọn phòng");
					return;
				}
				openJFrameSub(new SuaPhong_GUI(main, null, _this,
						donDatPhong_DAO.getDatPhong((String) tableModel.getValueAt(row, 0))));
			}
		});

//		Sự kiện nút làm mới
		btnLamMoi.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				txtNgayNhanPhong.setText("");
				cboTrangThai.setSelectedIndex(0);
				txtSoDienThoai.setText("");
				tableModel.setRowCount(0);
				List<DonDatPhong> list = phieuDatPhongTruoc_DAO.getAllDonDatPhong();
				addRow(list);
				pnlControl.setTbl(tbl);
			}
		});

//		Sự kiện txtNgayDat
		dateChoose.addEventDateChooser(new EventDateChooser() {

			@Override
			public void dateSelected(SelectedAction arg0, SelectedDate arg1) {
				filterDonDatPhong();
			}
		});

//		Sự kiện JCombobox trạng thái
		cboTrangThai.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED) {
					filterDonDatPhong();
				}
			}
		});
//		Sự kiện cho JTable
		tbl.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent lse) {
				if (!lse.getValueIsAdjusting()) {
					setEnabledBtnActions();
				}
			}
		});

		tbl.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (tbl.isEnabled()) {
					pnlControl.setTrangHienTai(tbl.getSelectedRow() + 1);
				}
			}
		});

		addAncestorListener(new AncestorListener() {
			Thread clockThread;

			public void ancestorAdded(AncestorEvent event) {
				clockThread = clock();
				filterDonDatPhong();
			}

			public void ancestorMoved(AncestorEvent event) {
			}

			@SuppressWarnings("deprecation")
			public void ancestorRemoved(AncestorEvent event) {
				clockThread.stop();
			}
		});

	}

	private void addRow(DonDatPhong donDatPhong) {
		String maDatPhong = donDatPhong.getMaDonDatPhong();
		String maKhachHang = donDatPhong.getKhachHang().getMaKhachHang();
		List<ChiTietDatPhong> listChiTietDatPhong = chiTietDatPhong_DAO.getAllChiTietDatPhong(donDatPhong);
		List<String> listPhong = new ArrayList<String>();
		listChiTietDatPhong.forEach(chiTietDatPhong -> listPhong.add(chiTietDatPhong.getPhong().getMaPhong()));

		DateTimeFormatter formatDate = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		DateTimeFormatter formatTime = DateTimeFormatter.ofPattern("HH:mm");

		tableModel.addRow(new String[] { maDatPhong, khachHang_DAO.getKhachHangTheoMa(maKhachHang).getSoDienThoai(),
				String.format("%s - %s", donDatPhong.getGioDatPhong().format(formatTime),
						donDatPhong.getNgayDatPhong().format(formatDate)),
				String.format("%s - %s", donDatPhong.getGioNhanPhong().format(formatTime),
						donDatPhong.getNgayNhanPhong().format(formatDate)),
				String.format("%s - %s", listPhong.size(), String.join(", ", listPhong)),
				DonDatPhong.convertTrangThaiToString(donDatPhong.getTrangThai()) });
	}

	private List<DonDatPhong> addRow(List<DonDatPhong> list) {
		list.forEach(datPhong -> addRow(datPhong));
		return list;
	}

	public void closeJFrameSub() {
		if (jFrameSub != null)
			jFrameSub.setVisible(false);
		glass.setVisible(false);
		glass.setAlpha(0f);
		jFrameSub = null;
	}

	public void filterDonDatPhong() {
		DateTimeFormatter formatDate = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		String ngayNhanPhong;
		if (txtNgayNhanPhong.getText().equals(""))
			ngayNhanPhong = "%%";
		else
			ngayNhanPhong = Utils.getLocalDate(txtNgayNhanPhong.getText()).format(formatDate);
		String trangThai = (String) cboTrangThai.getSelectedItem();
		String soDienThoai = txtSoDienThoai.getText();

		if (trangThai.equals("Trạng thái"))
			trangThai = "";
		if (soDienThoai.trim().equals(""))
			soDienThoai = "%%";

		List<DonDatPhong> list = phieuDatPhongTruoc_DAO.filterDonDatPhong(ngayNhanPhong, soDienThoai, trangThai);
		if (list.size() == 0) {
			new Notification(main, components.notification.Notification.Type.ERROR, "Không tìm thấy")
					.showNotification();
			return;
		}

		tableModel.setRowCount(0);
		addRow(list);
		pnlControl.setTbl(tbl);
	}

	private void loadTable() {
		cboTrangThai.setSelectedIndex(0);
		txtSoDienThoai.setText("");
		tableModel.setRowCount(0);
		filterDonDatPhong();
		pnlControl.setTbl(tbl);
	}

	public void openJFrameSub(JFrame jFrame) {
		this.main.setGlassPane(glass);
		glass.setVisible(true);
		glass.setAlpha(0.5f);
		jFrameSub = jFrame;
		jFrameSub.setVisible(true);
	}

	private void setEnabledBtnActions() {
		int row = tbl.getSelectedRow();
		if (row == -1)
			return;
		String trangThai = (String) tableModel.getValueAt(row, 5);
		if (trangThai.equals("Đã hủy")) {
			btnNhanPhong.setEnabled(false);
			btnHuyPhong.setEnabled(false);
			btnSuaPhong.setEnabled(false);
		} else {
			btnNhanPhong.setEnabled(true);
			btnHuyPhong.setEnabled(true);
			btnSuaPhong.setEnabled(true);
		}
	}

}
