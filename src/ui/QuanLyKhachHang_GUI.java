package ui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDateTime;
import java.util.List;

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
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;

import components.button.Button;
import components.controlPanel.ControlPanel;
import components.jDialog.JDialogCustom;
import components.notification.Notification;
import components.panelRound.PanelRound;
import components.scrollbarCustom.ScrollBarCustom;
import dao.DiaChi_DAO;
import dao.KhachHang_DAO;
import entity.KhachHang;
import entity.Phuong;
import entity.Quan;
import entity.Tinh;
import utils.Utils;

public class QuanLyKhachHang_GUI extends JPanel {

	private static JLabel lblTime;
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private DiaChi_DAO diaChi_DAO;
	private KhachHang_DAO khachHang_DAO;
	private ControlPanel pnlControl;
	private DefaultTableModel tableModel;
	private JTable tbl;
	private JTextField txtSearch;

	/**
	 * Create the frame.
	 */
	public QuanLyKhachHang_GUI(Main main) {
		khachHang_DAO = new KhachHang_DAO();
		diaChi_DAO = new DiaChi_DAO();
		int left = Utils.getLeft(1054);

		setBackground(Utils.secondaryColor);
		setBounds(0, 0, Utils.getScreenWidth(), Utils.getBodyHeight());
		setLayout(null);

//		Search
		JPanel pnlSearch = new JPanel();
		pnlSearch.setBackground(Utils.secondaryColor);
		pnlSearch.setBounds(left, 16, 1054, 24);
		this.add(pnlSearch);
		pnlSearch.setLayout(null);

		JLabel lblSearch = new JLabel("TÌM KIẾM KHÁCH HÀNG THEO TÊN:");
		lblSearch.setBounds(0, -1, 500, 27);
		lblSearch.setFont(new Font("Segoe UI", Font.PLAIN, 20));
		pnlSearch.add(lblSearch);

		lblTime = new JLabel("");
		lblTime.setHorizontalAlignment(SwingConstants.LEFT);
		lblTime.setFont(new Font("Segoe UI", Font.PLAIN, 18));
		lblTime.setBounds(874, 0, 180, 24);
		pnlSearch.add(lblTime);
		clock();

		JPanel pnlSearchForm = new JPanel();
		pnlSearchForm.setBackground(Utils.secondaryColor);
		pnlSearchForm.setBounds(left, 56, 1054, 36);
		this.add(pnlSearchForm);
		pnlSearchForm.setLayout(null);

		Button btnSearch = new Button("Tìm");

		btnSearch.setFocusable(false);
		btnSearch.setIcon(Utils.getImageIcon("searching.png"));
		btnSearch.setRadius(4);
		btnSearch.setForeground(Color.WHITE);
		btnSearch.setColor(new Color(134, 229, 138));
		btnSearch.setFont(new Font("Segoe UI", Font.PLAIN, 20));
		btnSearch.setBounds(904, -2, 150, 40);
		btnSearch.setBorderColor(Utils.secondaryColor);
		btnSearch.setColorOver(new Color(134, 229, 138));
		btnSearch.setColorClick(new Color(59, 238, 66));
		btnSearch.setBorder(new EmptyBorder(0, 0, 0, 0));
		pnlSearchForm.add(btnSearch);

		PanelRound pnlSearchInput = new PanelRound();
		pnlSearchInput.setRounded(4);
		pnlSearchInput.setBackground(Utils.secondaryColor);
		pnlSearchInput.setBounds(0, 0, 894, 36);
		pnlSearchInput.setBorder(new LineBorder(Color.BLACK));
		pnlSearchInput.setRounded(4);
		pnlSearchForm.add(pnlSearchInput);
		pnlSearchInput.setLayout(null);

		txtSearch = new JTextField();
		txtSearch.setFont(new Font("Segoe UI", Font.PLAIN, 20));
		txtSearch.setBackground(Utils.secondaryColor);
		txtSearch.setBorder(new EmptyBorder(0, 0, 0, 0));
		txtSearch.setBounds(9, 1, 876, 34);
		pnlSearchInput.add(txtSearch);
		txtSearch.setColumns(10);

		int topPnlControl = Utils.getBodyHeight() - 80;

//		Actions
		JPanel pnlActions = new JPanel();
		pnlActions.setBackground(Utils.secondaryColor);
		pnlActions.setBounds(left, 108, 1054, 36);
		this.add(pnlActions);
		pnlActions.setLayout(null);

		Button btnKhachHangView = new Button("Xem");
		btnKhachHangView.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int row = tbl.getSelectedRow();
				if (row == -1) {
					new JDialogCustom(main, components.jDialog.JDialogCustom.Type.warning).showMessage("Warning",
							"Vui lòng chọn khách hàng muốn xem");
				} else {
					String maKhachHang = (String) tbl.getValueAt(row, 0);
					main.addPnlBody(new XemKhachHang_GUI(main, new KhachHang(maKhachHang)), "Xem khách hàng", 2, 0);
				}
			}
		});
		btnKhachHangView.setFocusable(false);
		btnKhachHangView.setIcon(Utils.getImageIcon("user 1.png"));
		btnKhachHangView.setBounds(-2, -2, 150, 40);
		btnKhachHangView.setRadius(4);
		btnKhachHangView.setForeground(Color.WHITE);
		btnKhachHangView.setFont(new Font("Segoe UI", Font.PLAIN, 20));
		btnKhachHangView.setColorOver(Utils.primaryColor);
		btnKhachHangView.setColorClick(new Color(161, 184, 186));
		btnKhachHangView.setColor(Utils.primaryColor);
		btnKhachHangView.setBorderColor(Utils.secondaryColor);
		btnKhachHangView.setBorder(new EmptyBorder(0, 0, 0, 0));
		pnlActions.add(btnKhachHangView);

		Button btnKhachHangAdd = new Button("Thêm");
		btnKhachHangAdd.setFocusable(false);
		btnKhachHangAdd.setIcon(Utils.getImageIcon("add-user (2) 1.png"));
		btnKhachHangAdd.setRadius(4);
		btnKhachHangAdd.setForeground(Color.WHITE);
		btnKhachHangAdd.setFont(new Font("Segoe UI", Font.PLAIN, 20));
		btnKhachHangAdd.setColorOver(Utils.primaryColor);
		btnKhachHangAdd.setColorClick(new Color(161, 184, 186));
		btnKhachHangAdd.setColor(Utils.primaryColor);
		btnKhachHangAdd.setBorderColor(Utils.secondaryColor);
		btnKhachHangAdd.setBorder(new EmptyBorder(0, 0, 0, 0));
		btnKhachHangAdd.setBounds(158, -2, 150, 40);
		pnlActions.add(btnKhachHangAdd);

		btnKhachHangAdd.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				main.addPnlBody(new ThemKhachHang_GUI(main), "Thêm khách hàng", 2, 0);
			}
		});

		Button btnKhachHangEdit = new Button("Sửa");
		btnKhachHangEdit.setFocusable(false);
		btnKhachHangEdit.setIcon(Utils.getImageIcon("edit 2.png"));
		btnKhachHangEdit.setRadius(4);
		btnKhachHangEdit.setForeground(Color.WHITE);
		btnKhachHangEdit.setFont(new Font("Segoe UI", Font.PLAIN, 20));
		btnKhachHangEdit.setColorOver(Utils.primaryColor);
		btnKhachHangEdit.setColorClick(new Color(161, 184, 186));
		btnKhachHangEdit.setColor(Utils.primaryColor);
		btnKhachHangEdit.setBorderColor(Utils.secondaryColor);
		btnKhachHangEdit.setBorder(new EmptyBorder(0, 0, 0, 0));
		btnKhachHangEdit.setBounds(318, -2, 150, 40);
		pnlActions.add(btnKhachHangEdit);

		btnKhachHangEdit.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int row = tbl.getSelectedRow();
				if (row == -1) {
					new JDialogCustom(main, components.jDialog.JDialogCustom.Type.warning).showMessage("Warning",
							"Vui lòng chọn khách hàng muốn cập nhật");
				} else {
					KhachHang khachHang = new KhachHang((String) tbl.getValueAt(row, 0));
					main.addPnlBody(new CapNhatKhachHang_GUI(main, khachHang), "Cập nhật khách hàng", 2, 0);
				}
			}
		});

		Button btnKhachHangRemove = new Button("Xóa");
		btnKhachHangRemove.setFocusable(false);
		btnKhachHangRemove.setIcon(Utils.getImageIcon("unemployed 1.png"));
		btnKhachHangRemove.setRadius(4);
		btnKhachHangRemove.setForeground(Color.WHITE);
		btnKhachHangRemove.setFont(new Font("Segoe UI", Font.PLAIN, 20));
		btnKhachHangRemove.setColorOver(Utils.primaryColor);
		btnKhachHangRemove.setColorClick(new Color(161, 184, 186));
		btnKhachHangRemove.setColor(Utils.primaryColor);
		btnKhachHangRemove.setBorderColor(Utils.secondaryColor);
		btnKhachHangRemove.setBorder(new EmptyBorder(0, 0, 0, 0));
		btnKhachHangRemove.setBounds(478, -2, 150, 40);
		pnlActions.add(btnKhachHangRemove);

		btnKhachHangRemove.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int row = tbl.getSelectedRow();
				if (row == -1) {
					new JDialogCustom(main, components.jDialog.JDialogCustom.Type.warning).showMessage("Warning",
							"Vui lòng chọn khách hàng muốn xóa");
				} else {
					int res = JOptionPane.showConfirmDialog(null, "Bạn chắc chắn muốn xóa khách hàng này",
							"Xóa khách hàng", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
					if (res == JOptionPane.OK_OPTION) {
						String maKH = tbl.getValueAt(row, 0).toString();
						if (khachHang_DAO.xoaKhachHang(maKH)) {
							new Notification(main, components.notification.Notification.Type.SUCCESS,
									"Xóa thông tin khách hàng thành công").showNotification();
							loadTable();
							pnlControl.setTbl(tbl);
						} else
							new Notification(main, components.notification.Notification.Type.ERROR,
									"Xóa thông tin khách hàng thất bại").showNotification();
					}
				}
			}
		});

		Button btnKhachHangRestore = new Button("Khôi phục");
		btnKhachHangRestore.setFocusable(false);
		btnKhachHangRestore.setVisible(false);
		btnKhachHangRestore.setIcon(Utils.getImageIcon("restore.png"));
		btnKhachHangRestore.setRadius(4);
		btnKhachHangRestore.setForeground(Color.WHITE);
		btnKhachHangRestore.setFont(new Font("Segoe UI", Font.PLAIN, 20));
		btnKhachHangRestore.setColorOver(Utils.primaryColor);
		btnKhachHangRestore.setColorClick(new Color(161, 184, 186));
		btnKhachHangRestore.setColor(Utils.primaryColor);
		btnKhachHangRestore.setBorderColor(Utils.secondaryColor);
		btnKhachHangRestore.setBorder(new EmptyBorder(0, 0, 0, 0));
		btnKhachHangRestore.setBounds(478, -2, 150, 40);
		pnlActions.add(btnKhachHangRestore);

		btnKhachHangRestore.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int row = tbl.getSelectedRow();
				if (row == -1) {
					new JDialogCustom(main, components.jDialog.JDialogCustom.Type.warning).showMessage("Warning",
							"Vui lòng chọn khách hàng muốn khôi phục");
				} else {
					int res = JOptionPane.showConfirmDialog(null, "Bạn chắc chắn muốn khôi phục khách hàng này",
							"Khôi phục khách hàng", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
					if (res == JOptionPane.OK_OPTION) {
						String maKH = tbl.getValueAt(row, 0).toString();
						if (khachHang_DAO.khoiPhucKhachHang(maKH)) {
							new Notification(main, components.notification.Notification.Type.SUCCESS,
									"Khôi phục thông tin khách hàng thành công").showNotification();
							setEmptyTable();
							addRow(khachHang_DAO.getAllKhachHangDaXoa());
						} else
							new Notification(main, components.notification.Notification.Type.ERROR,
									"Khôi phục thông tin khách hàng thất bại").showNotification();
					}
				}
			}
		});

		Button btnLamMoi = new Button("Làm mới");
		btnLamMoi.setIcon(Utils.getImageIcon("refresh.png"));
		btnLamMoi.setRadius(4);
		btnLamMoi.setForeground(Color.WHITE);
		btnLamMoi.setFont(new Font("Segoe UI", Font.PLAIN, 20));
		btnLamMoi.setColorOver(Utils.primaryColor);
		btnLamMoi.setColorClick(new Color(161, 184, 186));
		btnLamMoi.setColor(Utils.primaryColor);
		btnLamMoi.setBorderColor(Utils.secondaryColor);
		btnLamMoi.setBorder(new EmptyBorder(0, 0, 0, 0));
		btnLamMoi.setBounds(744, -2, 150, 40);
		pnlActions.add(btnLamMoi);

		Button btnDanhSachXoa = new Button("DS đã xóa");
		btnDanhSachXoa.setIcon(Utils.getImageIcon("listdelete.png"));
		btnDanhSachXoa.setRadius(4);
		btnDanhSachXoa.setForeground(Color.WHITE);
		btnDanhSachXoa.setFont(new Font("Segoe UI", Font.PLAIN, 20));
		btnDanhSachXoa.setColorOver(Utils.primaryColor);
		btnDanhSachXoa.setColorClick(new Color(161, 184, 186));
		btnDanhSachXoa.setColor(Utils.primaryColor);
		btnDanhSachXoa.setBorderColor(Utils.secondaryColor);
		btnDanhSachXoa.setBorder(new EmptyBorder(0, 0, 0, 0));
		btnDanhSachXoa.setBounds(904, -2, 150, 40);
		pnlActions.add(btnDanhSachXoa);

		Button btnDanhSachTonTai = new Button("DS chưa xóa");
		btnDanhSachTonTai.setIcon(Utils.getImageIcon("requirement.png"));
		btnDanhSachTonTai.setVisible(false);
		btnDanhSachTonTai.setForeground(Color.WHITE);
		btnDanhSachTonTai.setFont(new Font("Segoe UI", Font.PLAIN, 20));
		btnDanhSachTonTai.setColorOver(Utils.primaryColor);
		btnDanhSachTonTai.setColorClick(new Color(161, 184, 186));
		btnDanhSachTonTai.setColor(Utils.primaryColor);
		btnDanhSachTonTai.setBorderColor(Utils.secondaryColor);
		btnDanhSachTonTai.setBorder(new EmptyBorder(0, 0, 0, 0));
		btnDanhSachTonTai.setBounds(904, -2, 150, 40);
		pnlActions.add(btnDanhSachTonTai);
//		sự kiện nút btnDanhSachTonTai
		btnDanhSachTonTai.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				txtSearch.setText("");
				btnKhachHangRemove.setVisible(true);
				btnKhachHangRestore.setVisible(false);
				btnDanhSachXoa.setVisible(true);
				btnKhachHangView.setEnabled(true);
				btnKhachHangAdd.setEnabled(true);
				btnKhachHangEdit.setEnabled(true);
				btnDanhSachTonTai.setVisible(false);
				tableModel.setRowCount(0);
				loadTable();
				pnlControl.setTbl(tbl);

			}
		});
// sự kiện nút btnDanhSachXoa
		btnDanhSachXoa.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				txtSearch.setText("");
				btnKhachHangRemove.setVisible(false);
				btnKhachHangRestore.setVisible(true);
				btnDanhSachTonTai.setVisible(true);
				btnKhachHangView.setEnabled(false);
				btnKhachHangAdd.setEnabled(false);
				btnKhachHangEdit.setEnabled(false);
				btnDanhSachXoa.setVisible(false);
				filterKhachHangDaXoa();

			}
		});
//Sự kiện nút btnLamMoi
		btnLamMoi.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				loadTable();
				pnlControl.setTbl(tbl);
				btnKhachHangRemove.setVisible(true);
				btnKhachHangRestore.setVisible(false);
				btnDanhSachXoa.setVisible(true);
				btnKhachHangView.setEnabled(true);
				btnKhachHangAdd.setEnabled(true);
				btnKhachHangEdit.setEnabled(true);
				btnDanhSachTonTai.setVisible(false);
				txtSearch.setText("");
			}
		});

		// Sự kiện nút Search
		btnSearch.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (btnDanhSachXoa.isVisible()) {
					filterKhachHang();
				} else
					filterKhachHangDaXoa();
			}
		});
//		Table danh sách khách hàng
		JScrollPane scr = new JScrollPane();
		scr.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scr.setBounds(left, 160, 1054, topPnlControl - 176);
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

		tableModel = new DefaultTableModel(
				new String[] { "Mã KH", "Họ tên", "CCCD", "Ngày sinh", "Giới tính", "SĐT", "Địa chỉ" }, 0);

		tbl.setModel(tableModel);
		tbl.setFocusable(false);
		JTableHeader tblHeader = tbl.getTableHeader();
		TableColumnModel tableColumnModel = tbl.getColumnModel();
		tblHeader.setBackground(Utils.primaryColor);
		tbl.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		tbl.setBackground(Color.WHITE);
		tbl.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		tableColumnModel.getColumn(0).setPreferredWidth(70);
		tableColumnModel.getColumn(1).setPreferredWidth(190);
		tableColumnModel.getColumn(2).setPreferredWidth(120);
		tableColumnModel.getColumn(3).setPreferredWidth(105);
		tableColumnModel.getColumn(4).setPreferredWidth(100);
		tableColumnModel.getColumn(5).setPreferredWidth(130);
		tableColumnModel.getColumn(6).setPreferredWidth(330);
		tblHeader.setPreferredSize(new Dimension((int) tblHeader.getPreferredSize().getWidth(), 36));
		tblHeader.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		tbl.setRowHeight(36);
		scr.setViewportView(tbl);

		pnlControl = new ControlPanel(Utils.getLeft(286), topPnlControl, main);
		this.add(pnlControl);

		tbl.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (tbl.isEnabled()) {
					pnlControl.setTrangHienTai(tbl.getSelectedRow() + 1);
				}
			}
		});

//		Panel event
		addAncestorListener(new AncestorListener() {
			Thread clockThread;

			@Override
			public void ancestorAdded(AncestorEvent event) {
				clockThread = clock();

				loadTable();
				pnlControl.setTbl(tbl);
			}

			@Override
			public void ancestorMoved(AncestorEvent event) {
			}

			@Override
			@SuppressWarnings("deprecation")
			public void ancestorRemoved(AncestorEvent event) {
				clockThread.stop();
			}
		});

		loadTable();
		pnlControl.setTbl(tbl);
	}

	private void addRow(KhachHang khachHang) {
		Tinh tinh = diaChi_DAO.getTinh(khachHang.getTinh());
		Quan quan = diaChi_DAO.getQuan(tinh, khachHang.getQuan());
		Phuong phuong = diaChi_DAO.getPhuong(quan, khachHang.getPhuong());
		tableModel.addRow(new String[] { khachHang.getMaKhachHang(), khachHang.getHoTen(), khachHang.getCccd(),
				Utils.formatDate(khachHang.getNgaySinh()), khachHang.isGioiTinh() ? "Nam" : "Nữ",
				khachHang.getSoDienThoai(), String.format("%s, %s, %s, %s", tinh.getTinh(), quan.getQuan(),
						phuong.getPhuong(), khachHang.getDiaChiCuThe()), });
	}

	private List<KhachHang> addRow(List<KhachHang> list) {
		list.forEach(khachHang -> addRow(khachHang));
		return list;
	}

	public Thread clock() {
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

	private void filterKhachHang() {
		String hoTen = txtSearch.getText();
		List<KhachHang> list = khachHang_DAO.filterKhachHang(hoTen);
		setEmptyTable();
		addRow(list);
		pnlControl.setTbl(tbl);

		if (list.size() == 0)
			JOptionPane.showMessageDialog(this, "Không tìm thấy khách hàng cần tìm");

	}

	private void filterKhachHangDaXoa() {
		String hoTen = txtSearch.getText();
		List<KhachHang> list = khachHang_DAO.filterKhachHangDaXoa(hoTen);
		setEmptyTable();
		tableModel.setRowCount(0);
		addRow(list);
		pnlControl.setTbl(tbl);

		if (list.size() == 0)
			JOptionPane.showMessageDialog(this, "Không tìm thấy khách hàng cần tìm");

	}

	public void loadTable() {
		setEmptyTable();
		addRow(khachHang_DAO.getAllKhachHang());
	}

	private void setEmptyTable() {
		while (tbl.getRowCount() > 0)
			tableModel.removeRow(0);
	}
}
