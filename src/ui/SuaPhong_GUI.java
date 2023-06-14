package ui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;

import components.button.Button;
import components.jDialog.JDialogCustom;
import components.notification.Notification;
import components.panelRound.PanelRound;
import components.scrollbarCustom.ScrollBarCustom;
import dao.ChiTietDatPhong_DAO;
import dao.DonDatPhong_DAO;
import dao.Phong_DAO;
import entity.ChiTietDatPhong;
import entity.DonDatPhong;
import entity.Phong;
import utils.Utils;

public class SuaPhong_GUI extends JFrame implements ItemListener {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private SuaPhong_GUI _this;
	private Button btnChonPhong;
	private Button btnLamMoi;
	private Button btnQuayLai;
	private Button btnSuaPhong;
	private ChiTietDatPhong_DAO chiTietDatPhong_DAO;
	private JComboBox<String> cmbLoaiPhong;
	private JComboBox<String> cmbMaPhong;
	private JComboBox<String> cmbSoLuong;
	private int countItem;
	private DonDatPhong_DAO datPhong_DAO;
	private List<Phong> dsPhongDaChon;
	private List<Phong> dsPhongDaChonBanDau;
	private List<Phong> dsPhongDatTruoc;
	private final int gapY = 8;
	private LocalTime gioNhanPhong;
	private final int heightItem = 36;
	private JLabel lblIconClose;
	private JLabel lblMaPhong;
	private List<ChiTietDatPhong> listChiTietDatPhong;
	private LocalDate ngayNhanPhong;
	private Phong_DAO phong_DAO;
	private PanelRound pnlContainerItem;
	private JPanel pnlContent;
	private JPanel pnlPhong;
	private JPanel pnlPhongDaChon;
	private JScrollPane scrPhongDaChon;
	private DefaultTableModel tableModel;
	private JTable tbl;
	private final int top = 11;

	/**
	 * Create the frame.
	 *
	 * @param parentFrame
	 */
	public SuaPhong_GUI(Main main, ThongTinChiTietPhieuDatPhongTruoc_GUI tTCTPDPT_GUI,
			QuanLyPhieuDatPhongTruoc_GUI qLPDPT_GUI, DonDatPhong donDatPhong) {
		_this = this;
		datPhong_DAO = new DonDatPhong_DAO();
		phong_DAO = new Phong_DAO();
		chiTietDatPhong_DAO = new ChiTietDatPhong_DAO();
		listChiTietDatPhong = chiTietDatPhong_DAO.getAllChiTietDatPhong(donDatPhong);
		DonDatPhong datPhong = datPhong_DAO.getDatPhong(donDatPhong.getMaDonDatPhong());
		gioNhanPhong = datPhong.getGioNhanPhong();
		ngayNhanPhong = datPhong.getNgayNhanPhong();

		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setBounds(0, 0, 850, 466);
		setUndecorated(true);
		setLocationRelativeTo(null);

		pnlContent = new JPanel();
		pnlContent.setBorder(new EmptyBorder(5, 5, 5, 5));
		pnlContent.setLayout(null);
		setContentPane(pnlContent);

		JPanel pnlContainer = new JPanel();
		pnlContainer.setBackground(Color.WHITE);
		pnlContainer.setBounds(0, 0, 850, 466);
		pnlContent.add(pnlContainer);
		pnlContainer.setLayout(null);

		JPanel pnlHeading = new JPanel();
		pnlHeading.setBackground(Utils.primaryColor);
		pnlHeading.setBounds(0, 0, 850, 50);
		pnlContainer.add(pnlHeading);
		pnlHeading.setLayout(null);

		JLabel lblTitle = new JLabel("Sửa phòng");
		lblTitle.setForeground(Color.WHITE);
		lblTitle.setBounds(325, 9, 200, 32);
		lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 24));
		pnlHeading.add(lblTitle);

		JPanel pnlBody = new JPanel();
		pnlBody.setBackground(Color.WHITE);
		pnlBody.setBounds(40, 50, 770, 398);
		pnlContainer.add(pnlBody);
		pnlBody.setLayout(null);

		pnlPhong = new JPanel();
		pnlPhong.setBackground(Color.WHITE);
		pnlPhong.setBounds(0, 80, 810, 270);
		pnlBody.add(pnlPhong);
		pnlPhong.setLayout(null);

		JPanel pnlActions = new JPanel();
		pnlActions.setBackground(Color.WHITE);
		pnlActions.setBounds(574, 0, 36, 225);
		pnlPhong.add(pnlActions);
		pnlActions.setLayout(null);

		btnChonPhong = new Button("");
		btnChonPhong.setFocusable(false);
		btnChonPhong.setRadius(8);
		btnChonPhong.setBorder(new EmptyBorder(0, 0, 0, 0));
		btnChonPhong.setBackground(Utils.primaryColor, 0.8f, 0.6f);
		btnChonPhong.setBorderColor(Color.WHITE);
		btnChonPhong.setIcon(Utils.getImageIcon("rightArrow_32x32.png"));
		btnChonPhong.setBounds(0, 94, 36, 36);
		btnChonPhong.setEnabled(false);
		pnlActions.add(btnChonPhong);

		JScrollPane scrDanhSachPhong = new JScrollPane();
		scrDanhSachPhong.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrDanhSachPhong.setBackground(Utils.secondaryColor);
		scrDanhSachPhong.setBounds(0, 0, 564, 270);
		scrDanhSachPhong.getViewport().setBackground(Color.WHITE);
		pnlPhong.add(scrDanhSachPhong);

		ScrollBarCustom scbDanhSachPhong = new ScrollBarCustom();
//		Set color scrollbar thumb
		scbDanhSachPhong.setScrollbarColor(new Color(203, 203, 203));
		scrDanhSachPhong.setVerticalScrollBar(scbDanhSachPhong);

		tbl = new JTable() {
			/**
			 *
			 */
			private static final long serialVersionUID = 1L;

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
		tableModel = new DefaultTableModel(new String[] { "Mã phòng", "Loại phòng", "Số lượng", "Trạng Thái" }, 0);
		JTableHeader tblHeader = tbl.getTableHeader();

		tbl.setModel(tableModel);
		tbl.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tbl.setFocusable(false);
		tblHeader.setBackground(Utils.primaryColor);
		tbl.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		tbl.setBackground(Color.WHITE);
		tblHeader.setPreferredSize(new Dimension((int) tblHeader.getPreferredSize().getWidth(), 36));
		tblHeader.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		tbl.setRowHeight(36);
		scrDanhSachPhong.setViewportView(tbl);

//		Căn phải column 3 table
		DefaultTableCellRenderer dtcr = new DefaultTableCellRenderer();
		dtcr.setHorizontalAlignment(SwingConstants.RIGHT);
		tbl.getColumnModel().getColumn(2).setCellRenderer(dtcr);

		JPanel pnlBtnGroup = new JPanel();
		pnlBtnGroup.setBackground(Color.WHITE);
		pnlBtnGroup.setBounds(0, 362, 770, 36);
		pnlBody.add(pnlBtnGroup);
		pnlBtnGroup.setLayout(null);

		btnSuaPhong = new Button("Sửa phòng");
		btnSuaPhong.setFocusable(false);
		btnSuaPhong.setRadius(8);
		btnSuaPhong.setBorder(new EmptyBorder(0, 0, 0, 0));
		btnSuaPhong.setBorderColor(Color.WHITE);
		btnSuaPhong.setForeground(Color.WHITE);
		btnSuaPhong.setBackground(Utils.primaryColor, 0.8f, 0.6f);
		btnSuaPhong.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		btnSuaPhong.setBounds(620, 0, 150, 36);
		btnSuaPhong.setEnabled(false);
		pnlBtnGroup.add(btnSuaPhong);

		btnQuayLai = new Button("Quay lại");
		btnQuayLai.setFocusable(false);
		btnQuayLai.setRadius(8);
		btnQuayLai.setBorder(new EmptyBorder(0, 0, 0, 0));
		btnQuayLai.setBorderColor(Color.WHITE);
		btnQuayLai.setBackground(Utils.secondaryColor, 0.8f, 0.8f);
		btnQuayLai.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		btnQuayLai.setBounds(0, 0, 150, 36);
		pnlBtnGroup.add(btnQuayLai);

		JPanel pnlFilter = new JPanel();
		pnlFilter.setBackground(Color.WHITE);
		pnlFilter.setBounds(0, 20, 770, 36);
		pnlBody.add(pnlFilter);
		pnlFilter.setLayout(null);

		cmbMaPhong = new JComboBox<>();
		cmbMaPhong.setBackground(Utils.primaryColor);
		cmbMaPhong.setFont(new Font("Segoe UI", Font.PLAIN, 20));
		cmbMaPhong.setModel(new DefaultComboBoxModel<>(new String[] { "Mã phòng" }));
		cmbMaPhong.setBounds(0, 0, 200, 36);
		pnlFilter.add(cmbMaPhong);

		cmbLoaiPhong = new JComboBox<>();
		cmbLoaiPhong.setBackground(Utils.primaryColor);
		cmbLoaiPhong.setModel(new DefaultComboBoxModel<>(new String[] { "Loại phòng", "Phòng thường", "Phòng VIP" }));
		cmbLoaiPhong.setFont(new Font("Segoe UI", Font.PLAIN, 20));
		cmbLoaiPhong.setBounds(220, 0, 200, 36);
		pnlFilter.add(cmbLoaiPhong);

		cmbSoLuong = new JComboBox<>();
		cmbSoLuong.setBackground(Utils.primaryColor);
		cmbSoLuong.setModel(new DefaultComboBoxModel<>(new String[] { "Số lượng", "5", "10", "20" }));
		cmbSoLuong.setFont(new Font("Segoe UI", Font.PLAIN, 20));
		cmbSoLuong.setBounds(440, 0, 200, 36);
		pnlFilter.add(cmbSoLuong);

		btnLamMoi = new Button("Làm mới");
		btnLamMoi.setFocusable(false);
		btnLamMoi.setBorder(new EmptyBorder(0, 0, 0, 0));
		btnLamMoi.setRadius(4);
		btnLamMoi.setBorderColor(Color.WHITE);
		btnLamMoi.setBackground(Utils.secondaryColor, 0.8f, 0.8f);
		btnLamMoi.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		btnLamMoi.setBounds(660, 0, 110, 36);
		pnlFilter.add(btnLamMoi);

		scrPhongDaChon = new JScrollPane();
		scrPhongDaChon.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrPhongDaChon.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrPhongDaChon.setBackground(Color.WHITE);
		scrPhongDaChon.setBorder(new TitledBorder(new LineBorder(Color.BLACK, 1, true),
				"Ph\u00F2ng \u0111\u00E3 ch\u1ECDn", TitledBorder.LEADING, TitledBorder.TOP, null, Color.BLACK));
		scrPhongDaChon.setBounds(620, 0, 150, 270);
		pnlPhong.add(scrPhongDaChon);

		ScrollBarCustom scbPhongDaChon = new ScrollBarCustom();
		scbPhongDaChon.setBackgroundColor(Color.WHITE);
		scbPhongDaChon.setScrollbarColor(Utils.primaryColor);
		scrPhongDaChon.setVerticalScrollBar(scbPhongDaChon);

		pnlPhongDaChon = new JPanel();
		pnlPhongDaChon.setBackground(Color.WHITE);
		scrPhongDaChon.setViewportView(pnlPhongDaChon);
		pnlPhongDaChon.setLayout(null);

		dsPhongDaChonBanDau = new ArrayList<>();
		listChiTietDatPhong.forEach(list -> dsPhongDaChonBanDau.add(list.getPhong()));
		showDanhSachPhongDaChon();

//		Sự kiện window
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowActivated(WindowEvent e) {
				dsPhongDatTruoc = datPhong_DAO.getPhongDatTruoc(ngayNhanPhong, gioNhanPhong);
				List<String> loaiPhongs = phong_DAO.getAllLoaiPhong();

				cmbMaPhong.removeItemListener(_this);

				emptyComboBox(cmbMaPhong, "Mã phòng");
				emptyComboBox(cmbLoaiPhong, "Loại phòng");

				for (Phong phong : dsPhongDatTruoc) {
					if (dsPhongDaChon.contains(phong))
						continue;
					addRow(phong);
					cmbMaPhong.addItem(phong.getMaPhong());
				}

				loaiPhongs.forEach(loaiPhong -> cmbLoaiPhong.addItem(loaiPhong));

				setEventFilterComboBox(true);
			}
		});

//		Sự kiện nút quay lại
		btnQuayLai.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (qLPDPT_GUI == null) {
					tTCTPDPT_GUI.closeJFrameSub();
					return;
				}
				qLPDPT_GUI.closeJFrameSub();
			}
		});

//		Sự kiện nút làm mới
		btnLamMoi.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				setEventFilterComboBox(false);

				btnChonPhong.setEnabled(false);
				tableModel.setRowCount(0);
				addRow(datPhong_DAO.getPhongDatTruoc(ngayNhanPhong, gioNhanPhong));
				cmbLoaiPhong.setSelectedIndex(0);
				cmbMaPhong.setSelectedIndex(0);
				cmbSoLuong.setSelectedIndex(0);
				dsPhongDaChon.removeAll(dsPhongDaChon);
				capNhatDanhSachPhongDatTruoc();
				showDanhSachPhongDaChon();

				setEventFilterComboBox(true);

			}
		});

//		Sự kiện nút chọn phòng
		btnChonPhong.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int row = tbl.getSelectedRow();
				if (row != -1 && row < tbl.getRowCount()) {
					if (dsPhongDaChon == null)
						dsPhongDaChon = new ArrayList<>();
					Phong phong = new Phong((String) tableModel.getValueAt(row, 0));
					if (dsPhongDaChon.contains(phong))
						return;
					dsPhongDaChon.add(phong);

					capNhatDanhSachPhongDatTruoc();
					showDanhSachPhongDaChon();
					repaint();
				}
			}
		});

//		Sự kiện nút sửa phòng
		btnSuaPhong.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (!btnSuaPhong.isEnabled())
					return;

//				Kiểm tra phòng đã được chọn hay chưa?
				if (dsPhongDaChon.size() <= 0) {
					new Notification(_this, components.notification.Notification.Type.ERROR, "Chọn phòng muốn đặt")
							.showNotification();
					return;
				}

				JDialogCustom jDialogCustom = new JDialogCustom(_this);

				jDialogCustom.getBtnOK().addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						boolean res = datPhong_DAO.capNhatPhongTrongPhieuDatPhongTruoc(donDatPhong.getMaDonDatPhong(),
								gioNhanPhong, dsPhongDaChon, dsPhongDaChonBanDau);
						if (res) {
							if (qLPDPT_GUI == null) {
								tTCTPDPT_GUI.setPhieuDatPhongVaoForm(chiTietDatPhong_DAO
										.getChiTietDatPhongTheoMaDatPhong(donDatPhong.getMaDonDatPhong()));
								tTCTPDPT_GUI.closeJFrameSub();
							} else {
								qLPDPT_GUI.filterDonDatPhong();
								qLPDPT_GUI.closeJFrameSub();

							}
							new Notification(main, components.notification.Notification.Type.SUCCESS,
									"Cập nhật phòng thành công").showNotification();
						} else {
							new Notification(_this, components.notification.Notification.Type.ERROR,
									"Cập nhật phòng thất bại").showNotification();
						}
					}
				});

				jDialogCustom.getBtnCancel().addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						return;
					}
				});
				jDialogCustom.showMessage("Warning", "Bạn có chắc muốn sửa phòng?");

			}
		});

//		Sự kiện JTable
		tbl.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				btnChonPhong.setEnabled(true);
			}
		});
		tbl.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent lse) {
				if (!lse.getValueIsAdjusting()) {
					if (tbl.getRowCount() == -1)
						btnChonPhong.setEnabled(false);
				}
			}
		});
	}

	/**
	 * Thêm danh sách các phòng vào table
	 *
	 * @param list danh sách các phòng cần thêm
	 */
	private void addRow(List<Phong> list) {
		tableModel.setRowCount(0);
		Collections.sort(list);
		list.forEach(phong -> addRow(phong));
	}

	/**
	 * Thêm một phòng vào table
	 *
	 * @param phong phòng muốn thêm
	 */
	private void addRow(Phong phong) {
		tableModel.addRow(new String[] { phong.getMaPhong(), phong.getTenLoai(), phong.getSoLuongKhach() + "",
				Phong.convertTrangThaiToString(phong.getTrangThai()) });
	}

	private void capNhatDanhSachPhongDatTruoc() {
		if (dsPhongDaChon == null || dsPhongDatTruoc == null)
			return;

		int row = tbl.getSelectedRow();
		if (row != -1 && row < tbl.getRowCount()) {
			tableModel.removeRow(tbl.getSelectedRow());
			btnChonPhong.setEnabled(false);
		}
		emptyComboBox(cmbMaPhong, "Mã phòng");
		tableModel.setRowCount(0);

		themPhongBanDau();
		for (Phong phong : dsPhongDatTruoc) {
			if (dsPhongDaChon.contains(phong))
				continue;

			addRow(phong);
			cmbMaPhong.addItem(phong.getMaPhong());
		}

		if (tbl.getRowCount() > 0) {
			tbl.setRowSelectionInterval(0, 0);
			btnChonPhong.setEnabled(true);
		}
	}

	/**
	 * Xóa tất các các item trong ComboBox và thêm label vào ComboBox
	 *
	 * @param jComboBox
	 */
	private void emptyComboBox(JComboBox<String> cmb) {
		cmb.removeAllItems();
	}

	/**
	 * Xóa tất các các item trong ComboBox và thêm label vào ComboBox
	 *
	 * @param jComboBox ComboBox
	 * @param label
	 */

	private void emptyComboBox(JComboBox<String> cmb, String label) {
		emptyComboBox(cmb);
		cmb.addItem(label);
	}

	/**
	 * Lọc danh sách các phòng theo mã phòng, loại phòng và số lượng
	 */
	private void filterPhongDatTruoc() {
		String maPhong = (String) cmbMaPhong.getSelectedItem();
		String loaiPhong = (String) cmbLoaiPhong.getSelectedItem();
		String soLuong = (String) cmbSoLuong.getSelectedItem();

		if (maPhong.equals("Mã phòng"))
			maPhong = "";
		if (loaiPhong.equals("Loại phòng"))
			loaiPhong = "";

		dsPhongDatTruoc = datPhong_DAO.getPhongDatTruoc(ngayNhanPhong, gioNhanPhong, maPhong, loaiPhong, soLuong);

		for (Phong pBD : dsPhongDaChonBanDau) {
			pBD = phong_DAO.getPhong(pBD.getMaPhong());
			if (!dsPhongDaChon.contains(pBD)) {
				if (maPhong.equals(""))
					maPhong = pBD.getMaPhong();
				String loaiPhongLoc = loaiPhong;
				if (loaiPhong.equals(""))
					loaiPhongLoc = pBD.getTenLoai();
				int sLBD = pBD.getSoLuongKhach();
				if (!soLuong.equals("Số lượng"))
					sLBD = Integer.parseInt(soLuong);

				if (pBD.getMaPhong().equals(maPhong) && pBD.getTenLoai().equals(loaiPhongLoc)
						&& pBD.getSoLuongKhach() == sLBD) {
					dsPhongDatTruoc.add(pBD);
				}
			}
		}
		if (dsPhongDatTruoc.size() == 0) {
			tableModel.setRowCount(0);
			return;
		}

		if (cmbMaPhong.getSelectedItem().toString().equals("Mã phòng")
				&& cmbLoaiPhong.getSelectedItem().toString().equals("Loại phòng")
				&& cmbSoLuong.getSelectedItem().toString().equals("Số lượng")) {
			themPhongBanDau();
		}

		tableModel.setRowCount(0);
		addRow(dsPhongDatTruoc);
	}

	private PanelRound getPanelPhongDaChonItem(int top, Phong phong) {
		pnlContainerItem = new PanelRound(8);
		pnlContainerItem.setBackground(Utils.primaryColor);
		pnlContainerItem.setBounds(11, top, 118, 36);
		pnlContainerItem.setLayout(null);

		lblMaPhong = new JLabel(phong.getMaPhong());
		lblMaPhong.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		lblMaPhong.setBounds(4, 0, 94, 36);
		pnlContainerItem.add(lblMaPhong);

		lblIconClose = new JLabel("");
		lblIconClose.setIcon(Utils.getImageIcon("close_16x16.png"));
		lblIconClose.setBounds(94, 10, 16, 16);
		pnlContainerItem.add(lblIconClose);
		lblIconClose.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				dsPhongDaChon.remove(phong);
				if (dsPhongDaChon.size() <= 0)
					btnSuaPhong.setEnabled(false);

				capNhatDanhSachPhongDatTruoc();
				showDanhSachPhongDaChon();
			}
		});

		return pnlContainerItem;
	}

	@Override
	public void itemStateChanged(ItemEvent e) {

		if (e.getStateChange() == ItemEvent.DESELECTED)
			return;

		filterPhongDatTruoc();
	}

	private void setEventFilterComboBox(boolean b) {
		if (b) {
			cmbLoaiPhong.addItemListener(_this);
			cmbMaPhong.addItemListener(_this);
			cmbSoLuong.addItemListener(_this);
			return;
		}
		cmbLoaiPhong.removeItemListener(_this);
		cmbMaPhong.removeItemListener(_this);
		cmbSoLuong.removeItemListener(_this);
	}

	private void showDanhSachPhongDaChon() {
		if (dsPhongDaChon == null) {
			dsPhongDaChon = new ArrayList<>();
			dsPhongDaChonBanDau.forEach(list -> dsPhongDaChon.add(list));
		}
		pnlPhongDaChon.removeAll();
		scrPhongDaChon.setViewportView(pnlPhongDaChon);

		countItem = dsPhongDaChon.size();
		Phong phong;
		PanelRound pnlPhongDaChonItem;
		for (int i = 0; i < countItem; ++i) {
			phong = dsPhongDaChon.get(i);
			pnlPhongDaChonItem = getPanelPhongDaChonItem(top + i * (gapY + heightItem), phong);
			pnlPhongDaChon.add(pnlPhongDaChonItem);
		}

		pnlPhongDaChon.setPreferredSize(
				new Dimension(140, Math.max(202, top + heightItem * countItem + gapY * (countItem - 1))));

		if (!dsPhongDaChonBanDau.equals(dsPhongDaChon))
			btnSuaPhong.setEnabled(true);
		else
			btnSuaPhong.setEnabled(false);

		repaint();
	}

	private void themPhongBanDau() {
		for (Phong phongBanDau : dsPhongDaChonBanDau) {
			if (!dsPhongDatTruoc.contains(phongBanDau) && !dsPhongDaChon.contains(phongBanDau)) {
				dsPhongDatTruoc.add(phong_DAO.getPhong(phongBanDau.getMaPhong()));
			}
		}
	}
}
