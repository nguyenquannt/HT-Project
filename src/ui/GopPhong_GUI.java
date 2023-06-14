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
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
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
import javax.swing.border.EtchedBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;

import components.button.Button;
import components.comboBox.ComboBox;
import components.notification.Notification;
import components.panelRound.PanelRound;
import components.scrollbarCustom.ScrollBarCustom;
import dao.DonDatPhong_DAO;
import entity.Phong;
import entity.Phong.TrangThai;
import utils.Utils;

public class GopPhong_GUI extends JFrame implements ItemListener {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private GopPhong_GUI _this;
	private Button btnChonPhong;
	private Button btnChuyen;
	private ComboBox<String> cmbMaDatPhong;
	private int countItem;
	private DonDatPhong_DAO datPhong_DAO;
	private List<Phong> dsPhongCanGop;
	private List<Phong> dsPhongDaChon;
	private final int gapY = 8;
	private final int heightItem = 36;
	private final String labelCmbMaDatPhong = "Mã đặt phòng";
	private JLabel lblIconClose;
	private JLabel lblMaPhong;
	private PanelRound pnlContainerItem;
	private JPanel pnlContent;
	private JPanel pnlPhongDaChon;
	private JPanel pnlPhongGop;
	private JScrollPane scrPhongDaChon;
	private DefaultTableModel tableModelPhongCanGop;
	private DefaultTableModel tableModelPhongGop;
	private JTable tblPhongCanGop;
	private JTable tblPhongGop;
	private final int top = 4;

	/**
	 * Create the frame.
	 *
	 * @param quanLyDatPhongGUI
	 * @param glass
	 */
	public GopPhong_GUI(QuanLyDatPhong_GUI quanLyDatPhongGUI, JFrame parentFrame) {
		_this = this;
		datPhong_DAO = new DonDatPhong_DAO();

		setType(Type.UTILITY);
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setBounds(100, 100, 600, 489);
		pnlContent = new JPanel();
		pnlContent.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(pnlContent);
		pnlContent.setLayout(null);
		setLocationRelativeTo(null);
		setUndecorated(true);

		JPanel pnlContainer = new JPanel();
		pnlContainer.setBackground(Color.WHITE);
		pnlContainer.setBounds(0, 0, 600, 490);
		pnlContent.add(pnlContainer);
		pnlContainer.setLayout(null);

		JPanel pnlHeader = new JPanel();
		pnlHeader.setBackground(Utils.primaryColor);
		pnlHeader.setBounds(0, 0, 600, 50);
		pnlContainer.add(pnlHeader);
		pnlHeader.setLayout(null);

		JLabel lblTitle = new JLabel("Gộp phòng");
		lblTitle.setForeground(Color.WHITE);
		lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 24));
		lblTitle.setBounds(200, 0, 200, 50);
		pnlHeader.add(lblTitle);

		JLabel lblMaDatPhong = new JLabel(labelCmbMaDatPhong);
		lblMaDatPhong.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		lblMaDatPhong.setBounds(16, 58, 100, 34);
		pnlContainer.add(lblMaDatPhong);

		cmbMaDatPhong = new ComboBox<>();
		cmbMaDatPhong.setBackground(Utils.primaryColor);
		cmbMaDatPhong.setModel(new DefaultComboBoxModel<>(new String[] { labelCmbMaDatPhong }));
		cmbMaDatPhong.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		cmbMaDatPhong.setBounds(126, 59, 150, 32);
		pnlContainer.add(cmbMaDatPhong);

		pnlPhongGop = new JPanel();
		pnlPhongGop.setBorder(new TitledBorder(
				new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)),
				"Ph\u00F2ng c\u1EA7n g\u1ED9p", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		pnlPhongGop.setBackground(Color.WHITE);
		pnlPhongGop.setBounds(16, 108, 568, 150);
		pnlContainer.add(pnlPhongGop);
		pnlPhongGop.setLayout(null);

		JScrollPane scrDanhSachPhong = new JScrollPane();
		scrDanhSachPhong.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrDanhSachPhong.setBackground(Utils.secondaryColor);
		scrDanhSachPhong.getViewport().setBackground(Color.WHITE);
		scrDanhSachPhong.setBounds(2, 17, 366, 130);
		pnlPhongGop.add(scrDanhSachPhong);

		ScrollBarCustom scbDanhSachPhong = new ScrollBarCustom();
//		Set color scrollbar thumb
		scbDanhSachPhong.setScrollbarColor(new Color(203, 203, 203));
		scrDanhSachPhong.setVerticalScrollBar(scbDanhSachPhong);

		tblPhongCanGop = new JTable() {
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

		tableModelPhongCanGop = new DefaultTableModel(new String[] { "Mã phòng", "Loại phòng", "Số lượng" }, 0);
		JTableHeader tblHeaderPhongCanGop = tblPhongCanGop.getTableHeader();

		tblPhongCanGop.setModel(tableModelPhongCanGop);
		tblPhongCanGop.setFocusable(false);
		tblPhongCanGop.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tblHeaderPhongCanGop.setBackground(Utils.primaryColor);
		tblHeaderPhongCanGop.setForeground(Color.WHITE);
		tblPhongCanGop.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		tblHeaderPhongCanGop
				.setPreferredSize(new Dimension((int) tblHeaderPhongCanGop.getPreferredSize().getWidth(), 24));
		tblHeaderPhongCanGop.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		tblPhongCanGop.setRowHeight(24);
		scrDanhSachPhong.setViewportView(tblPhongCanGop);
//		Căn phải column 3 table
		DefaultTableCellRenderer dtcr = new DefaultTableCellRenderer();
		dtcr.setHorizontalAlignment(SwingConstants.RIGHT);
		tblPhongCanGop.getColumnModel().getColumn(2).setCellRenderer(dtcr);

		JPanel pnlPhongGopThanh = new JPanel();
		pnlPhongGopThanh.setBorder(new TitledBorder(null, "Ph\u00F2ng g\u1ED9p th\u00E0nh", TitledBorder.LEADING,
				TitledBorder.TOP, null, null));
		pnlPhongGopThanh.setBackground(Color.WHITE);
		pnlPhongGopThanh.setBounds(16, 274, 568, 150);
		pnlContainer.add(pnlPhongGopThanh);
		pnlPhongGopThanh.setLayout(null);

		JScrollPane scrDanhSachPhongGop = new JScrollPane();
		scrDanhSachPhongGop.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrDanhSachPhongGop.setBackground(Utils.secondaryColor);
		scrDanhSachPhongGop.getViewport().setBackground(Color.WHITE);
		scrDanhSachPhongGop.setBounds(3, 17, 561, 130);
		pnlPhongGopThanh.add(scrDanhSachPhongGop);

		ScrollBarCustom scbDanhSachPhongGop = new ScrollBarCustom();
//		Set color scrollbar thumb
		scbDanhSachPhongGop.setScrollbarColor(new Color(203, 203, 203));
		scrDanhSachPhongGop.setVerticalScrollBar(scbDanhSachPhongGop);

		tblPhongGop = new JTable() {
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

		tableModelPhongGop = new DefaultTableModel(new String[] { "Mã phòng", "Loại phòng", "Số lượng", "Trạng thái" },
				0);
		JTableHeader tblHeaderPhongGop = tblPhongGop.getTableHeader();

		tblPhongGop.setModel(tableModelPhongGop);
		tblPhongGop.setFocusable(false);
		tblPhongGop.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tblHeaderPhongGop.setBackground(Utils.primaryColor);
		tblHeaderPhongGop.setForeground(Color.WHITE);
		tblPhongGop.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		tblHeaderPhongGop.setPreferredSize(new Dimension((int) tblHeaderPhongGop.getPreferredSize().getWidth(), 24));
		tblHeaderPhongGop.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		tblPhongGop.setRowHeight(24);
		scrDanhSachPhongGop.setViewportView(tblPhongGop);
//		Căn phải column 3 table
		tblPhongGop.getColumnModel().getColumn(2).setCellRenderer(dtcr);

		JPanel pnlFooter = new JPanel();
		pnlFooter.setBackground(Color.WHITE);
		pnlFooter.setBounds(16, 440, 568, 34);
		pnlContainer.add(pnlFooter);
		pnlFooter.setLayout(null);

		Button btnQuayLai = new Button("Quay lại");
		btnQuayLai.setBorderColor(Color.white);
		btnQuayLai.setRadius(4);
		btnQuayLai.setBorder(new EmptyBorder(0, 0, 0, 0));
		btnQuayLai.setFocusable(false);
		btnQuayLai.setForeground(Color.BLACK);
		btnQuayLai.setColor(Utils.secondaryColor);
		btnQuayLai.setColorOver(Utils.getOpacity(Utils.secondaryColor, 0.8f));
		btnQuayLai.setColorClick(Utils.getOpacity(Utils.secondaryColor, 0.6f));
		btnQuayLai.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		btnQuayLai.setBounds(0, 0, 127, 34);
		pnlFooter.add(btnQuayLai);

		btnChuyen = new Button("Gộp phòng");
		btnChuyen.setEnabled(false);
		btnChuyen.setBorderColor(Color.WHITE);
		btnChuyen.setRadius(4);
		btnChuyen.setBorder(new EmptyBorder(0, 0, 0, 0));
		btnChuyen.setFocusable(false);
		btnChuyen.setForeground(Color.WHITE);
		btnChuyen.setColor(Utils.primaryColor);
		btnChuyen.setColorOver(Utils.getOpacity(Utils.primaryColor, 0.8f));
		btnChuyen.setColorClick(Utils.getOpacity(Utils.primaryColor, 0.6f));
		btnChuyen.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		btnChuyen.setBounds(441, 0, 127, 34);
		pnlFooter.add(btnChuyen);

		btnChonPhong = new Button("");
		btnChonPhong.setFocusable(false);
		btnChonPhong.setRadius(8);
		btnChonPhong.setBorder(new EmptyBorder(0, 0, 0, 0));
		btnChonPhong.setColor(Utils.primaryColor);
		btnChonPhong.setColorOver(Utils.getOpacity(Utils.primaryColor, 0.8f));
		btnChonPhong.setColorClick(Utils.getOpacity(Utils.primaryColor, 0.6f));
		btnChonPhong.setBorderColor(Color.WHITE);
		btnChonPhong.setIcon(Utils.getImageIcon("rightArrow_32x32.png"));
		btnChonPhong.setBounds(374, 57, 36, 36);
		btnChonPhong.setEnabled(false);
		pnlPhongGop.add(btnChonPhong);

		showDanhSachPhongDaChon();

//		Danh sách phòng đã chọn
		scrPhongDaChon = new JScrollPane();
		scrPhongDaChon.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrPhongDaChon.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrPhongDaChon.setBackground(Color.WHITE);
		scrPhongDaChon.setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0), 1, true),
				"Ph\u00F2ng \u0111\u00E3 ch\u1ECDn", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		scrPhongDaChon.setBounds(416, 17, 150, 130);
		pnlPhongGop.add(scrPhongDaChon);

		ScrollBarCustom scbPhongDaChon = new ScrollBarCustom();
		scbPhongDaChon.setBackgroundColor(Color.WHITE);
		scbPhongDaChon.setScrollbarColor(Utils.primaryColor);
		scrPhongDaChon.setVerticalScrollBar(scbPhongDaChon);

		pnlPhongDaChon = new JPanel();
		pnlPhongDaChon.setBackground(Color.WHITE);
		scrPhongDaChon.setViewportView(pnlPhongDaChon);
		pnlPhongDaChon.setLayout(null);

//		Lắng nghe sự kiện window
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowActivated(WindowEvent e) {
				List<String> dsMaDatPhongDangThue = datPhong_DAO.getMaDatPhongGop();

				cmbMaDatPhong.removeAllItems();
				cmbMaDatPhong.addItem(labelCmbMaDatPhong);
				dsMaDatPhongDangThue.forEach(string -> cmbMaDatPhong.addItem(string));

				cmbMaDatPhong.addItemListener(_this);
			}
		});

//		Sự kiện JTable phòng cần gộp
		tblPhongCanGop.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int row = tblPhongCanGop.getSelectedRow();
				if (row >= 0)
					btnChonPhong.setEnabled(true);
			}
		});

		tblPhongCanGop.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent lse) {
				if (!lse.getValueIsAdjusting()) {
					int row = tblPhongCanGop.getSelectedRow();
					btnChonPhong.setEnabled(row != -1);
				}
			}
		});

//		Sự kiện nút chọn phòng
		btnChonPhong.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int row = tblPhongCanGop.getSelectedRow();
				if (row != -1 && row < tblPhongCanGop.getRowCount()) {
					if (dsPhongDaChon == null)
						dsPhongDaChon = new ArrayList<>();
					Phong phong = new Phong((String) tableModelPhongCanGop.getValueAt(row, 0));
					if (dsPhongDaChon.contains(phong))
						return;
					dsPhongDaChon.add(phong);

					showDanhSachPhongDaChon();
					showDanhSachPhongGop();
					setEnabledBtnChuyenPhong();
					capNhatDanhSachPhongDatTruoc();
					if (tblPhongCanGop.getRowCount() > 0)
						tblPhongCanGop.setRowSelectionInterval(0, 0);
					else
						btnChonPhong.setEnabled(false);

					if (tblPhongGop.getSelectedRow() != -1)
						btnChuyen.setEnabled(true);
				}
			}
		});

//		Lắng nghe sự kiện table gộp phòng
		tblPhongGop.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				setEnabledBtnChuyenPhong();
			}
		});

		tblPhongGop.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent lse) {
				if (!lse.getValueIsAdjusting()) {
					int row = tblPhongGop.getSelectedRow();
					if (row != -1)
						setEnabledBtnChuyenPhong();
				}
			}
		});

//		Lắng nghe sự kiện nút gộp phòng
		btnChuyen.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (!btnChuyen.isEnabled())
					return;

				boolean res = datPhong_DAO.gopPhong((String) cmbMaDatPhong.getSelectedItem(), dsPhongDaChon,
						getPhongTuTablePhongGop());

				if (res) {
					quanLyDatPhongGUI.capNhatTrangThaiPhong();
					quanLyDatPhongGUI.closeJFrameSub();
					new Notification(parentFrame, components.notification.Notification.Type.SUCCESS,
							"Gộp phòng thành công").showNotification();
				} else {
					quanLyDatPhongGUI.closeJFrameSub();
					new Notification(parentFrame, components.notification.Notification.Type.ERROR, "Gộp phòng thất bại")
							.showNotification();
				}
			}
		});

//		Lắng nghe sự kiện nút quay lại
		btnQuayLai.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				quanLyDatPhongGUI.closeJFrameSub();
			}
		});
	}

	/**
	 * Thêm một phòng vào table phòng cần gộp
	 *
	 * @param phong
	 */
	private void addRowTableCanGop(Phong phong) {
		tableModelPhongCanGop
				.addRow(new String[] { phong.getMaPhong(), phong.getTenLoai(), phong.getSoLuongKhach() + "" });
	}

	/**
	 * Thêm một phòng vào Table gộp phòng
	 *
	 * @param phong
	 */
	private void addRowTablePhongGop(Phong phong) {
		tableModelPhongGop.addRow(new String[] { phong.getMaPhong(), phong.getTenLoai(), phong.getSoLuongKhach() + "",
				Phong.convertTrangThaiToString(phong.getTrangThai()) });
	}

	private void capNhatDanhSachPhongDatTruoc() {
		if (dsPhongCanGop == null || dsPhongDaChon == null)
			return;

		tableModelPhongCanGop.setRowCount(0);

		for (Phong phong : dsPhongCanGop)
			if (!dsPhongDaChon.contains(phong))
				addRowTableCanGop(phong);
		repaint();
	}

	/**
	 * Get pnlContainer phòng đã chọn
	 *
	 * @param top   khoảng cách top từ container đến item
	 * @param phong phòng được chọn
	 * @return pnlContainer
	 */
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
				showDanhSachPhongGop();
				showDanhSachPhongDaChon();
				setEnabledBtnChuyenPhong();
				capNhatDanhSachPhongDatTruoc();
				btnChonPhong.setEnabled(false);

				if (dsPhongDaChon == null || dsPhongDaChon.size() <= 0)
					btnChuyen.setEnabled(false);
			}
		});

		return pnlContainerItem;
	}

	private Phong getPhongTuTablePhongGop() {
		int row = tblPhongGop.getSelectedRow();

		if (row == -1)
			return null;

		String maPhong = (String) tableModelPhongGop.getValueAt(row, 0);
		String loaiPhong = (String) tableModelPhongGop.getValueAt(row, 1);
		int soLuongKhach = Integer.parseInt((String) tableModelPhongGop.getValueAt(row, 2));
		TrangThai trangThai = Phong.convertStringToTrangThai((String) tableModelPhongGop.getValueAt(row, 3));
		return new Phong(maPhong, soLuongKhach, trangThai, false, loaiPhong);
	}

	/**
	 * Sự kiện ComboBox Mã đặt phòng
	 */
	@Override
	public void itemStateChanged(ItemEvent e) {
		if (e.getStateChange() == ItemEvent.DESELECTED)
			return;

		String maDatPhong = (String) cmbMaDatPhong.getSelectedItem();
		dsPhongDaChon = null;
		showDanhSachPhongDaChon();
		tableModelPhongGop.setRowCount(0);
		tableModelPhongCanGop.setRowCount(0);

		if (!maDatPhong.equals(labelCmbMaDatPhong)) {
			dsPhongCanGop = datPhong_DAO.getPhongDangThue(maDatPhong);

			dsPhongCanGop.forEach(this::addRowTableCanGop);

			showDanhSachPhongGop();
		}

		setEnabledBtnChuyenPhong();
		btnChonPhong.setEnabled(false);
		repaint();
	}

	/**
	 * Set Enabled nút chuyển phòng
	 */
	private void setEnabledBtnChuyenPhong() {
		if (dsPhongDaChon == null || dsPhongDaChon.size() <= 0 || tblPhongGop.getSelectedRow() == -1)
			btnChuyen.setEnabled(false);
		else
			btnChuyen.setEnabled(true);
	}

	/**
	 * Hiển thị các phòng đã chọn vào mục phòng đã chọn
	 */
	private void showDanhSachPhongDaChon() {
		if (pnlPhongDaChon != null)
			pnlPhongDaChon.removeAll();
		if (dsPhongDaChon == null)
			return;
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
				new Dimension(140, Math.max(107, top + heightItem * countItem + gapY * (countItem - 1))));
		repaint();
	}

	/**
	 * Show danh sách phòng gộp to Table
	 */
	private void showDanhSachPhongGop() {
		String maDatPhong = (String) cmbMaDatPhong.getSelectedItem();

		if (maDatPhong.equals(labelCmbMaDatPhong))
			return;

		tableModelPhongGop.setRowCount(0);

		List<Phong> dsPhongGop = datPhong_DAO.getPhongCoTheGop(maDatPhong, dsPhongDaChon);

		dsPhongGop.forEach(this::addRowTablePhongGop);

		Utils.scrollToVisiable(tblPhongGop, 0, 0);
	}
}
