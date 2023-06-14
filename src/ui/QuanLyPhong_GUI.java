
package ui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
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
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;

import components.button.Button;
import components.comboBox.ComboBox;
import components.controlPanel.ControlPanel;
import components.jDialog.Glass;
import components.jDialog.JDialogCustom;
import components.notification.Notification;
import components.scrollbarCustom.ScrollBarCustom;
import dao.Phong_DAO;
import entity.Phong;
import utils.Utils;

public class QuanLyPhong_GUI extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private QuanLyPhong_GUI _this;
	private Button btnTimKiem, btnThem, btnSua, btnXoa, btnKhoiPhuc, btnDanhSachXoa, btnDanhSachTonTai;
	private Thread clock;
	private ComboBox<String> cmbLoaiPhong, cmbSoLuongKhach;
	private Glass glass;
	private Main jFrame;
	private JFrame jFrameSub;
	private JLabel lblGio;
	private JLabel lblNgay;
	private JLabel lblThu;
	private Phong_DAO phong_DAO;
	private ControlPanel pnlControl;
	private DefaultTableModel tableModel;
	private JTable tbl;
	private JTextField txtTimKiem;

	/**
	 * Create the panel.
	 * 
	 * @param _this
	 */
	public QuanLyPhong_GUI(Main jFrame) {
		phong_DAO = new Phong_DAO();

		setBackground(Utils.secondaryColor);
		setBounds(0, 0, Utils.getScreenWidth(), Utils.getBodyHeight());
		setLayout(null);
		glass = new Glass();
		this.jFrame = jFrame;
		_this = this;
		int left = Utils.getLeft(1086);

		JPanel pnlHeader = new JPanel();
		pnlHeader.setBounds(left, 6, 1086, 64);
		pnlHeader.setBackground(Utils.secondaryColor);
		add(pnlHeader);
		pnlHeader.setLayout(null);

		JPanel pnlTimKiem = new JPanel();
		pnlTimKiem.setBounds(32, 9, 829, 46);
		pnlTimKiem.setBackground(Utils.secondaryColor);
		pnlHeader.add(pnlTimKiem);
		pnlTimKiem.setLayout(null);

		JLabel lblTimKiem = new JLabel("Tìm kiếm phòng");
		lblTimKiem.setForeground(Utils.getOpacity(Color.BLACK, 0.55f));
		lblTimKiem.setFont(new Font("Segoe UI", Font.BOLD, 25));
		lblTimKiem.setBounds(0, 1, 195, 43);
		pnlTimKiem.add(lblTimKiem);

		txtTimKiem = new JTextField();
		txtTimKiem.setBorder(new LineBorder(new Color(128, 151, 172), 2, true));
		txtTimKiem.setFont(new Font("Segoe UI", Font.PLAIN, 25));
		txtTimKiem.setBackground(Utils.secondaryColor);
		txtTimKiem.setBounds(211, 1, 244, 43);
		pnlTimKiem.add(txtTimKiem);
		txtTimKiem.setColumns(10);

		btnTimKiem = new Button("Tìm kiếm");
		btnTimKiem.setIcon(Utils.getImageIcon("search_34x34.png"));
		btnTimKiem.setFocusable(false);
		btnTimKiem.setRadius(9);
		btnTimKiem.setBorder(new EmptyBorder(0, 0, 0, 0));
		btnTimKiem.setBorderColor(Utils.secondaryColor);
		btnTimKiem.setBackground(Utils.getRGBA(140, 177, 180, 0.7f), 0.9f, 0.8f);
		btnTimKiem.setFont(new Font("Segoe UI", Font.BOLD, 18));
		btnTimKiem.setForeground(Color.WHITE);
		btnTimKiem.setBounds(471, 0, 140, 47);
		pnlTimKiem.add(btnTimKiem);

		btnDanhSachXoa = new Button("DS đã xóa");
		btnDanhSachXoa.setIcon(Utils.getImageIcon("listdelete.png"));
		btnDanhSachXoa.setRadius(9);
		btnDanhSachXoa.setForeground(Color.WHITE);
		btnDanhSachXoa.setFont(new Font("Segoe UI", Font.PLAIN, 20));
		btnDanhSachXoa.setColorOver(Utils.primaryColor);
		btnDanhSachXoa.setColorClick(new Color(161, 184, 186));
		btnDanhSachXoa.setBorderColor(Utils.secondaryColor);
		btnDanhSachXoa.setBackground(Utils.getRGBA(140, 177, 180, 0.7f), 0.9f, 0.8f);
		btnDanhSachXoa.setBorder(new EmptyBorder(0, 0, 0, 0));
		btnDanhSachXoa.setBounds(626, 0, 193, 47);
		pnlTimKiem.add(btnDanhSachXoa);

		btnDanhSachTonTai = new Button("DS chưa xóa");
		btnDanhSachTonTai.setIcon(Utils.getImageIcon("requirement.png"));
		btnDanhSachTonTai.setRadius(9);
		btnDanhSachTonTai.setVisible(false);
		btnDanhSachTonTai.setForeground(Color.WHITE);
		btnDanhSachTonTai.setFont(new Font("Segoe UI", Font.PLAIN, 20));
		btnDanhSachTonTai.setColorOver(Utils.primaryColor);
		btnDanhSachTonTai.setColorClick(new Color(161, 184, 186));
		btnDanhSachTonTai.setBorderColor(Utils.secondaryColor);
		btnDanhSachTonTai.setBackground(Utils.getRGBA(140, 177, 180, 0.7f), 0.9f, 0.8f);
		btnDanhSachTonTai.setBorder(new EmptyBorder(0, 0, 0, 0));
		btnDanhSachTonTai.setBounds(626, 0, 193, 47);
		pnlTimKiem.add(btnDanhSachTonTai);

		JPanel pnlThoiGian = new JPanel();
		pnlThoiGian.setBackground(Utils.secondaryColor);
		pnlThoiGian.setBounds(864, 0, 200, 64);
		pnlHeader.add(pnlThoiGian);
		pnlThoiGian.setLayout(null);

		JLabel lnlIcon = new JLabel("");
		lnlIcon.setIcon(Utils.getImageIcon("clock (1) 1.png"));
		lnlIcon.setBounds(0, 0, 64, 64);
		pnlThoiGian.add(lnlIcon);

		lblGio = new JLabel("18:07");
		lblGio.setForeground(Utils.getOpacity(Color.BLACK, 0.55f));
		lblGio.setHorizontalAlignment(SwingConstants.CENTER);
		lblGio.setFont(new Font("Segoe UI", Font.BOLD, 16));
		lblGio.setBounds(64, 1, 136, 21);
		pnlThoiGian.add(lblGio);

		lblThu = new JLabel("T2");
		lblThu.setHorizontalAlignment(SwingConstants.CENTER);
		lblThu.setForeground(new Color(0, 0, 0, 140));
		lblThu.setFont(new Font("Segoe UI", Font.BOLD, 16));
		lblThu.setBounds(64, 22, 136, 21);
		pnlThoiGian.add(lblThu);

		lblNgay = new JLabel("26-09-2022");
		lblNgay.setHorizontalAlignment(SwingConstants.CENTER);
		lblNgay.setForeground(new Color(0, 0, 0, 140));
		lblNgay.setFont(new Font("Segoe UI", Font.BOLD, 16));
		lblNgay.setBounds(64, 43, 136, 21);
		pnlThoiGian.add(lblNgay);

		JPanel pnlActions = new JPanel();
		pnlActions.setBackground(Utils.secondaryColor);
		pnlActions.setBounds(left, 90, 1086, 49);
		add(pnlActions);
		pnlActions.setLayout(null);

		btnThem = new Button("Thêm");
		btnThem.setIcon(Utils.getImageIcon("add 1.png"));
		btnThem.setFocusable(false);
		btnThem.setRadius(9);
		btnThem.setBorder(new EmptyBorder(0, 0, 0, 0));
		btnThem.setBorderColor(Utils.secondaryColor);
		btnThem.setBackground(Utils.getRGBA(140, 177, 180, 0.7f), 0.9f, 0.8f);
		btnThem.setForeground(Color.WHITE);
		btnThem.setFont(new Font("Segoe UI", Font.BOLD, 27));
		btnThem.setBounds(32, -2, 173, 53);
		pnlActions.add(btnThem);

		btnSua = new Button("Thêm");
		btnSua.setIcon(Utils.getImageIcon("update 1.png"));
		btnSua.setText("Sửa");
		btnSua.setFocusable(false);
		btnSua.setRadius(9);
		btnSua.setBorder(new EmptyBorder(0, 0, 0, 0));
		btnSua.setBorderColor(Utils.secondaryColor);
		btnSua.setBackground(Utils.getRGBA(140, 177, 180, 0.7f), 0.9f, 0.8f);
		btnSua.setForeground(Color.WHITE);
		btnSua.setFont(new Font("Segoe UI", Font.BOLD, 27));
		btnSua.setBounds(221, -2, 173, 53);
		pnlActions.add(btnSua);

		btnXoa = new Button("Thêm");
		btnXoa.setText("Xóa");
		btnXoa.setIcon(Utils.getImageIcon("download 1.png"));
		btnXoa.setFocusable(false);
		btnXoa.setRadius(9);
		btnXoa.setBorder(new EmptyBorder(0, 0, 0, 0));
		btnXoa.setBorderColor(Utils.secondaryColor);
		btnXoa.setBackground(Utils.getRGBA(140, 177, 180, 0.7f), 0.9f, 0.8f);
		btnXoa.setForeground(Color.WHITE);
		btnXoa.setFont(new Font("Segoe UI", Font.BOLD, 27));
		btnXoa.setBounds(410, -2, 173, 53);
		pnlActions.add(btnXoa);

		btnKhoiPhuc = new Button("Khôi phục");

		btnKhoiPhuc.setFocusable(false);
		btnKhoiPhuc.setIcon(Utils.getImageIcon("restore.png"));
		btnKhoiPhuc.setRadius(9);
		btnKhoiPhuc.setForeground(Color.WHITE);
		btnKhoiPhuc.setFont(new Font("Segoe UI", Font.PLAIN, 20));
		btnKhoiPhuc.setBounds(410, -2, 173, 53);
		btnKhoiPhuc.setBorderColor(Utils.secondaryColor);
		btnKhoiPhuc.setBackground(Utils.getRGBA(140, 177, 180, 0.7f), 0.9f, 0.8f);
		btnKhoiPhuc.setColorClick(new Color(59, 238, 66));
		btnKhoiPhuc.setBorder(new EmptyBorder(0, 0, 0, 0));
		pnlActions.add(btnKhoiPhuc);

		cmbLoaiPhong = new ComboBox<String>();
		cmbLoaiPhong.setFocusable(false);
		cmbLoaiPhong.addItem("Loại phòng");
		cmbLoaiPhong.setForeground(Color.WHITE);
		cmbLoaiPhong.setBackground(Utils.getRGBA(140, 177, 180, 0.7f));
		cmbLoaiPhong.setFont(new Font("Segoe UI", Font.BOLD, 20));
		cmbLoaiPhong.setBounds(664, 0, 180, 49);
		List<String> listLoaiPhong = phong_DAO.getAllLoaiPhong();
		for (String loaiPhong : listLoaiPhong)
			cmbLoaiPhong.addItem(loaiPhong);

		pnlActions.add(cmbLoaiPhong);

		cmbSoLuongKhach = new ComboBox<String>();
		cmbSoLuongKhach.setFocusable(false);
		cmbSoLuongKhach.setModel(new DefaultComboBoxModel<String>(new String[] { "Số lượng khách", "5", "10", "20" }));
		cmbSoLuongKhach.setForeground(Color.WHITE);
		cmbSoLuongKhach.setBackground(Utils.getRGBA(140, 177, 180, 0.7f));
		cmbSoLuongKhach.setFont(new Font("Segoe UI", Font.BOLD, 20));
		cmbSoLuongKhach.setBounds(874, 0, 180, 49);

		pnlActions.add(cmbSoLuongKhach);

		int topPnlControl = Utils.getBodyHeight() - 84;

		JScrollPane scr = new JScrollPane();
		scr.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		scr.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scr.setBounds(Utils.getLeft(1022), 159, 1022, topPnlControl - 179);
		scr.setBackground(Utils.primaryColor);
		scr.getViewport().setBackground(Color.WHITE);

		ScrollBarCustom scp = new ScrollBarCustom();
//		Set color scrollbar thumb
		scp.setScrollbarColor(new Color(203, 203, 203));
		scr.setVerticalScrollBar(scp);
		this.add(scr);

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
					c.setBackground(Utils.getRGBA(96, 96, 96, 0.5f));
				else
					c.setBackground(Color.WHITE);
				return c;
			}
		};
		tbl.setForeground(Utils.getOpacity(Color.BLACK, 0.55f));
		tbl.setAutoCreateRowSorter(true);
		JTableHeader tblHeader = tbl.getTableHeader();
		TableColumnModel tableColumnModel = tbl.getColumnModel();

		tableModel = new DefaultTableModel(new String[] { "Mã phòng", "Loại phòng", "Giá tiền", "Số lượng khách" }, 0);
		tbl.setModel(tableModel);
		tbl.setFocusable(false);
		tblHeader.setBackground(Utils.primaryColor);
		tbl.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		tbl.setBackground(Color.WHITE);
		tbl.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

		tableColumnModel.getColumn(0).setPreferredWidth(250);
		tableColumnModel.getColumn(1).setPreferredWidth(260);
		tableColumnModel.getColumn(2).setPreferredWidth(250);
		tableColumnModel.getColumn(3).setPreferredWidth(250);

		tblHeader.setPreferredSize(new Dimension((int) tblHeader.getPreferredSize().getWidth(), 36));
		tblHeader.setFont(new Font("Segoe UI", Font.BOLD, 20));
		tblHeader.setForeground(Utils.getOpacity(Color.BLACK, 0.55f));
		tbl.setRowHeight(36);
		scr.setViewportView(tbl);
		DefaultTableCellRenderer dtcr = new DefaultTableCellRenderer();
		dtcr.setHorizontalAlignment(SwingConstants.RIGHT);
		tableColumnModel.getColumn(2).setCellRenderer(dtcr);
		tableColumnModel.getColumn(3).setCellRenderer(dtcr);

		pnlControl = new ControlPanel(Utils.getLeft(286), topPnlControl, jFrame);
		this.add(pnlControl);

		addRow(phong_DAO.getAllPhong());

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
						lblGio.setText(Utils.convertLocalTimeToString(LocalTime.of(hour, minute)));
						LocalDate date = LocalDate.now();
						DayOfWeek dayNow = date.getDayOfWeek();
						String thu = "T2";
						if (dayNow.getValue() == DayOfWeek.TUESDAY.getValue())
							thu = "T3";
						else if (dayNow.getValue() == DayOfWeek.WEDNESDAY.getValue())
							thu = "T4";
						else if (dayNow.getValue() == DayOfWeek.THURSDAY.getValue())
							thu = "T5";
						else if (dayNow.getValue() == DayOfWeek.FRIDAY.getValue())
							thu = "T6";
						else if (dayNow.getValue() == DayOfWeek.SATURDAY.getValue())
							thu = "T7";
						else if (dayNow.getValue() == DayOfWeek.SUNDAY.getValue())
							thu = "CN";
						lblThu.setText(thu);
						lblNgay.setText(String.format("%s-%s-%d", day < 10 ? "0" + day : day,
								month < 10 ? "0" + month : month, year));
						sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		};
		// sự kiện nút xoá phòng
		btnXoa.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int row = tbl.getSelectedRow();
				if (row == -1) {
					new JDialogCustom(jFrame, components.jDialog.JDialogCustom.Type.warning).showMessage("Warning",
							"Vui lòng chọn phòng muốn xóa");
				} else {
					int res = JOptionPane.showConfirmDialog(null, "Bạn chắc chắn muốn xóa phòng này", "Xóa phòng",
							JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
					if (res == JOptionPane.OK_OPTION) {
						String maPhong = tbl.getValueAt(row, 0).toString();
						Phong phong = phong_DAO.getPhong(maPhong);
						if (!Phong.convertTrangThaiToString(phong.getTrangThai()).equals("Trống")) {
							new JDialogCustom(jFrame, components.jDialog.JDialogCustom.Type.warning)
									.showMessage("Thông báo", "Phòng này không thể xóa");
							return;
						}
						if (phong_DAO.xoaPhong(maPhong)) {
							new Notification(jFrame, components.notification.Notification.Type.SUCCESS,
									"Xóa thông tin phòng thành công").showNotification();
							filterPhong();
						} else
							new Notification(jFrame, components.notification.Notification.Type.ERROR,
									"Xóa thông tin phòng thất bại").showNotification();
					}
				}
			}
		});

		// sự kiện nút khôi phục
		btnKhoiPhuc.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int row = tbl.getSelectedRow();
				if (row == -1) {
					new JDialogCustom(jFrame, components.jDialog.JDialogCustom.Type.warning).showMessage("Warning",
							"Vui lòng chọn phòng muốn khôi phục");
				} else {
					int res = JOptionPane.showConfirmDialog(null, "Bạn chắc chắn muốn khôi phục phòng này",
							"Khôi phục phòng", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
					if (res == JOptionPane.OK_OPTION) {
						String maPhong = tbl.getValueAt(row, 0).toString();
						if (phong_DAO.khoiPhucPhong(maPhong)) {
							new Notification(jFrame, components.notification.Notification.Type.SUCCESS,
									"Khôi phục thông tin phòng thành công").showNotification();
							filterPhongDaXoa();
						} else
							new Notification(jFrame, components.notification.Notification.Type.ERROR,
									"Khôi phục thông tin phòng thất bại").showNotification();
					}
				}
			}
		});

//	 	Sự kiện nút tìm kiếm phòng
		btnTimKiem.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (btnDanhSachXoa.isVisible()) {
					filterPhong();
				} else
					filterPhongDaXoa();
			}
		});

//		Sự kiện JComboBox loại phong
		cmbLoaiPhong.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED) {
					if (btnDanhSachXoa.isVisible()) {
						filterPhong();
					} else
						filterPhongDaXoa();
				}
			}
		});
//		Sự kiện JComboBox số lượng khách
		cmbSoLuongKhach.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED) {
					if (btnDanhSachXoa.isVisible()) {
						filterPhong();
					} else
						filterPhongDaXoa();
				}
			}
		});

//		sự kiện nút btnDanhSachTonTai
		btnDanhSachTonTai.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				txtTimKiem.setText("");
				btnXoa.setVisible(true);
				btnKhoiPhuc.setVisible(false);
				btnDanhSachXoa.setVisible(true);
				btnThem.setEnabled(true);
				btnSua.setEnabled(true);
				btnDanhSachTonTai.setVisible(false);
				filterPhong();
				cmbLoaiPhong.setSelectedIndex(0);
				cmbSoLuongKhach.setSelectedIndex(0);

			}
		});
// sự kiện nút btnDanhSachXoa
		btnDanhSachXoa.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				txtTimKiem.setText("");
				btnXoa.setVisible(false);
				btnKhoiPhuc.setVisible(true);
				btnDanhSachTonTai.setVisible(true);
				btnThem.setEnabled(false);
				btnSua.setEnabled(false);
				btnDanhSachXoa.setVisible(false);
				cmbLoaiPhong.setSelectedIndex(0);
				cmbSoLuongKhach.setSelectedIndex(0);
				filterPhongDaXoa();
			}
		});

// Sự kiện nút thêm
		btnThem.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (!btnThem.isEnabled())
					return;
				openJFrameSub(new ThemPhong_GUI(_this));
			}
		});

//		Sự kiện nút sửa thông tin phòng
		btnSua.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (!btnSua.isEnabled())
					return;
				int row = tbl.getSelectedRow();
				if (row == -1) {
					new JDialogCustom(jFrame, components.jDialog.JDialogCustom.Type.warning).showMessage("Warning",
							"Vui lòng chọn phòng muốn sửa");
				} else {
					String maPhong = tableModel.getValueAt(row, 0).toString();
					openJFrameSub(new ThongTinChiTietPhong_GUI(_this, phong_DAO.getPhong(maPhong)));
				}
			}
		});

//		Sự kiện JTable
		tbl.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				pnlControl.setTrangHienTai(tbl.getSelectedRow() + 1);
			}
		});

		addAncestorListener(new AncestorListener() {
			public void ancestorAdded(AncestorEvent event) {
				clock.start();
			}

			public void ancestorMoved(AncestorEvent event) {
			}

			@SuppressWarnings("deprecation")
			public void ancestorRemoved(AncestorEvent event) {
				clock.stop();
			}
		});

		glass.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				closeJFrameSub();
			}
		});

	}

	private List<Phong> addRow(List<Phong> list) {
		list.forEach(phong -> addRow(phong));
		pnlControl.setTbl(tbl);
		return list;
	}

	private void addRow(Phong phong) {
		tableModel.addRow(new String[] { phong.getMaPhong(), phong.getTenLoai(), Utils.formatTienTe(phong.getGiaTien()),
				String.valueOf(phong.getSoLuongKhach()) });
	}

	public void closeJFrameSub() {
		if (jFrameSub != null)
			jFrameSub.setVisible(false);
		glass.setVisible(false);
		glass.setAlpha(0f);
		jFrameSub = null;
	}

	private void filterPhong() {
		String maPhong = txtTimKiem.getText();
		String tenLoaiPhong = cmbLoaiPhong.getSelectedItem().toString();
		String soLuongKhach = cmbSoLuongKhach.getSelectedItem().toString();
		if (tenLoaiPhong.equals("Loại phòng"))
			tenLoaiPhong = "";
		if (soLuongKhach.equals("Số lượng khách"))
			soLuongKhach = "";

		List<Phong> list = new ArrayList<Phong>();
		list = phong_DAO.getPhongTheoLoaiVaSoLuongKhach(maPhong, tenLoaiPhong, soLuongKhach);

		setEmptyTable();
		addRow(list);

	}

	private void filterPhongDaXoa() {
		String maPhong = txtTimKiem.getText();
		String tenLoaiPhong = cmbLoaiPhong.getSelectedItem().toString();
		String soLuongKhach = cmbSoLuongKhach.getSelectedItem().toString();
		if (tenLoaiPhong.equals("Loại phòng"))
			tenLoaiPhong = "";
		if (soLuongKhach.equals("Số lượng khách"))
			soLuongKhach = "";

		List<Phong> list = new ArrayList<Phong>();
		list = phong_DAO.getPhongTheoLoaiVaSoLuongKhachDaXoa(maPhong, tenLoaiPhong, soLuongKhach);

		setEmptyTable();
		addRow(list);

	}

	public void loadTable() {
		tableModel.setRowCount(0);
		addRow(phong_DAO.getAllPhong());

	}

	public void openJFrameSub(JFrame jFrame) {
		this.jFrame.setGlassPane(glass);
		glass.setVisible(true);
		glass.setAlpha(0.5f);
		jFrameSub = jFrame;
		jFrameSub.setVisible(true);

		setEmptyTable();
		addRow(phong_DAO.getAllPhong());
	}

	private void setEmptyTable() {
		while (tbl.getRowCount() > 0)
			tableModel.removeRow(0);
	}

}
