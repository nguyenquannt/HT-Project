package ui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDateTime;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
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
import javax.swing.table.DefaultTableCellRenderer;
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
import dao.DichVu_DAO;
import entity.DichVu;
import utils.Utils;

public class QuanLyDichVu_GUI extends JPanel {

	private static JLabel lblTime;
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static void clock() {
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
	}

	private Button btnXem, btnThem, btnSua, btnXoa, btnKhoiPhuc, btnDanhSachXoa, btnDanhSachTonTai;
	private JComboBox<String> cmbLoaiDV;
	private JComboBox<String> cmbSoLuong;
	private DichVu_DAO dichVu_DAO;
	private List<DichVu> listDV;
	private ControlPanel pnlControl;
	private DefaultTableModel tableModel;
	private JTable tbl;
	private JTextField txtSearch;

	public QuanLyDichVu_GUI(Main main) {
		dichVu_DAO = new DichVu_DAO();
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

		JLabel lblSearch = new JLabel("TÌM KIẾM DỊCH VỤ THEO TÊN:");
		lblSearch.setBounds(0, -1, 299, 27);
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
		btnSearch.setBounds(660, -4, 154, 44);
		btnSearch.setBorderColor(Utils.secondaryColor);
		btnSearch.setColorOver(new Color(134, 229, 138));
		btnSearch.setColorClick(new Color(59, 238, 66));
		btnSearch.setBorder(new EmptyBorder(0, 0, 0, 0));
		pnlSearchForm.add(btnSearch);

		PanelRound pnlSearchInput = new PanelRound();
		pnlSearchInput.setRounded(4);
		pnlSearchInput.setBackground(Utils.secondaryColor);
		pnlSearchInput.setBounds(0, 0, 631, 36);
		pnlSearchInput.setBorder(new LineBorder(Color.BLACK));
		pnlSearchInput.setRounded(4);
		pnlSearchForm.add(pnlSearchInput);
		pnlSearchInput.setLayout(null);

		txtSearch = new JTextField();
		txtSearch.setFont(new Font("Segoe UI", Font.PLAIN, 20));
		txtSearch.setBackground(Utils.secondaryColor);
		txtSearch.setBorder(new EmptyBorder(0, 0, 0, 0));
		txtSearch.setBounds(9, 1, 620, 34);
		pnlSearchInput.add(txtSearch);
		txtSearch.setColumns(10);

		btnDanhSachXoa = new Button("DS ngừng KD");
		btnDanhSachXoa.setIcon(Utils.getImageIcon("listdelete.png"));
		btnDanhSachXoa.setRadius(4);
		btnDanhSachXoa.setForeground(Color.WHITE);
		btnDanhSachXoa.setFont(new Font("Segoe UI", Font.PLAIN, 20));
		btnDanhSachXoa.setColorOver(Utils.primaryColor);
		btnDanhSachXoa.setColorClick(new Color(161, 184, 186));
		btnDanhSachXoa.setColor(Utils.primaryColor);
		btnDanhSachXoa.setBorderColor(Utils.secondaryColor);
		btnDanhSachXoa.setBorder(new EmptyBorder(0, 0, 0, 0));
		btnDanhSachXoa.setBounds(844, -4, 210, 44);
		pnlSearchForm.add(btnDanhSachXoa);

		btnDanhSachTonTai = new Button("DS còn KD");
		btnDanhSachTonTai.setIcon(Utils.getImageIcon("requirement.png"));
		btnDanhSachTonTai.setVisible(false);
		btnDanhSachTonTai.setForeground(Color.WHITE);
		btnDanhSachTonTai.setFont(new Font("Segoe UI", Font.PLAIN, 20));
		btnDanhSachTonTai.setColorOver(Utils.primaryColor);
		btnDanhSachTonTai.setColorClick(new Color(161, 184, 186));
		btnDanhSachTonTai.setColor(Utils.primaryColor);
		btnDanhSachTonTai.setBorderColor(Utils.secondaryColor);
		btnDanhSachTonTai.setBorder(new EmptyBorder(0, 0, 0, 0));
		btnDanhSachTonTai.setBounds(844, -4, 210, 44);
		pnlSearchForm.add(btnDanhSachTonTai);

		JPanel pnlButton = new JPanel();
		pnlButton.setBackground(Utils.secondaryColor);
		pnlButton.setBounds(left, 108, 1054, 40);
		this.add(pnlButton);
		pnlButton.setLayout(null);

		btnXem = new Button("Xem");

		btnXem.setFocusable(false);
		btnXem.setIcon(Utils.getImageIcon("searching.png"));
		btnXem.setRadius(4);
		btnXem.setForeground(Color.WHITE);
		btnXem.setColor(new Color(134, 229, 138));
		btnXem.setFont(new Font("Segoe UI", Font.PLAIN, 20));
		btnXem.setBounds(-2, -2, 154, 44);
		btnXem.setBorderColor(Utils.secondaryColor);
		btnXem.setColorOver(new Color(134, 229, 138));
		btnXem.setColorClick(new Color(59, 238, 66));
		btnXem.setBorder(new EmptyBorder(0, 0, 0, 0));
		pnlButton.add(btnXem);

		btnThem = new Button("Thêm");

		btnThem.setFocusable(false);
		btnThem.setIcon(Utils.getImageIcon("add 1.png"));
		btnThem.setRadius(4);
		btnThem.setForeground(Color.WHITE);
		btnThem.setColor(new Color(134, 229, 138));
		btnThem.setFont(new Font("Segoe UI", Font.PLAIN, 20));
		btnThem.setBounds(182, -2, 154, 44);
		btnThem.setBorderColor(Utils.secondaryColor);
		btnThem.setColorOver(new Color(134, 229, 138));
		btnThem.setColorClick(new Color(59, 238, 66));
		btnThem.setBorder(new EmptyBorder(0, 0, 0, 0));
		pnlButton.add(btnThem);

		btnSua = new Button("Sửa");

		btnSua.setFocusable(false);
		btnSua.setIcon(Utils.getImageIcon("update 1.png"));
		btnSua.setRadius(4);
		btnSua.setForeground(Color.WHITE);
		btnSua.setColor(new Color(134, 229, 138));
		btnSua.setFont(new Font("Segoe UI", Font.PLAIN, 20));
		btnSua.setBounds(364, -2, 154, 44);
		btnSua.setBorderColor(Utils.secondaryColor);
		btnSua.setColorOver(new Color(134, 229, 138));
		btnSua.setColorClick(new Color(59, 238, 66));
		btnSua.setBorder(new EmptyBorder(0, 0, 0, 0));
		pnlButton.add(btnSua);

		btnXoa = new Button("Xóa");

		btnXoa.setFocusable(false);
		btnXoa.setIcon(Utils.getImageIcon("download 1.png"));
		btnXoa.setRadius(4);
		btnXoa.setForeground(Color.WHITE);
		btnXoa.setColor(new Color(134, 229, 138));
		btnXoa.setFont(new Font("Segoe UI", Font.PLAIN, 20));
		btnXoa.setBounds(546, -2, 154, 44);
		btnXoa.setBorderColor(Utils.secondaryColor);
		btnXoa.setColorOver(new Color(134, 229, 138));
		btnXoa.setColorClick(new Color(59, 238, 66));
		btnXoa.setBorder(new EmptyBorder(0, 0, 0, 0));
		pnlButton.add(btnXoa);

		btnKhoiPhuc = new Button("Khôi phục");

		btnKhoiPhuc.setFocusable(false);
		btnKhoiPhuc.setIcon(Utils.getImageIcon("restore.png"));
		btnKhoiPhuc.setRadius(4);
		btnKhoiPhuc.setForeground(Color.WHITE);
		btnKhoiPhuc.setColor(new Color(134, 229, 138));
		btnKhoiPhuc.setFont(new Font("Segoe UI", Font.PLAIN, 20));
		btnKhoiPhuc.setBounds(546, -2, 154, 44);
		btnKhoiPhuc.setBorderColor(Utils.secondaryColor);
		btnKhoiPhuc.setColorOver(new Color(134, 229, 138));
		btnKhoiPhuc.setColorClick(new Color(59, 238, 66));
		btnKhoiPhuc.setBorder(new EmptyBorder(0, 0, 0, 0));
		pnlButton.add(btnKhoiPhuc);
		btnKhoiPhuc.setVisible(false);

//		sự kiện nút btnDanhSachTonTai
		btnDanhSachTonTai.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				txtSearch.setText("");
				btnXoa.setVisible(true);
				btnKhoiPhuc.setVisible(false);
				btnDanhSachXoa.setVisible(true);
				btnXem.setEnabled(true);
				btnThem.setEnabled(true);
				btnSua.setEnabled(true);
				btnDanhSachTonTai.setVisible(false);
				tableModel.setRowCount(0);
				filterDichVu(false);
				pnlControl.setTbl(tbl);
				cmbLoaiDV.setSelectedIndex(0);
				cmbSoLuong.setSelectedIndex(0);

			}
		});
// sự kiện nút btnDanhSachXoa
		btnDanhSachXoa.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				txtSearch.setText("");
				btnXoa.setVisible(false);
				btnKhoiPhuc.setVisible(true);
				btnDanhSachTonTai.setVisible(true);
				btnXem.setEnabled(false);
				btnThem.setEnabled(false);
				btnSua.setEnabled(false);
				btnDanhSachXoa.setVisible(false);
				filterDichVu(true);
				cmbLoaiDV.setSelectedIndex(0);
				cmbSoLuong.setSelectedIndex(0);
			}
		});

		cmbLoaiDV = new JComboBox<String>();
		cmbLoaiDV.addItem("Phân loại");
		List<String> listLoaiDV = dichVu_DAO.getAllLoaiDichVu();
		for (String loaiDV : listLoaiDV)
			cmbLoaiDV.addItem(loaiDV);

		cmbLoaiDV.setFont(new Font("Segoe UI", Font.PLAIN, 20));
		cmbLoaiDV.setBackground(Utils.primaryColor);
		cmbLoaiDV.setBounds(728, 0, 150, 40);
		pnlButton.add(cmbLoaiDV);

		cmbSoLuong = new JComboBox<String>();

		cmbSoLuong.setModel(
				new DefaultComboBoxModel<String>(new String[] { "Số lượng", "<50", "50-100", "100-200", ">200" }));
		cmbSoLuong.setFont(new Font("Segoe UI", Font.PLAIN, 20));
		cmbSoLuong.setBackground(Utils.primaryColor);
		cmbSoLuong.setBounds(904, 0, 150, 40);
		pnlButton.add(cmbSoLuong);

		int topPnlControl = Utils.getBodyHeight() - 80;

		JScrollPane scr = new JScrollPane();
		scr.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scr.setBounds(left, 164, 1054, topPnlControl - 180);
		scr.setBackground(Utils.primaryColor);
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
				new String[] { "Mã DV", "TênDV", "Đơn vị tính", "Số lượng", "Loại", "Giá mua", "Giá bán" }, 0);
		JTableHeader tblHeader = tbl.getTableHeader();
		TableColumnModel tableColumnModel = tbl.getColumnModel();

		tbl.setModel(tableModel);
		tbl.setFocusable(false);
		tblHeader.setBackground(Utils.primaryColor);
		tbl.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		tbl.setBackground(Color.WHITE);
		tbl.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		tableColumnModel.getColumn(0).setPreferredWidth(80);
		tableColumnModel.getColumn(1).setPreferredWidth(224);
		tableColumnModel.getColumn(2).setPreferredWidth(120);
		tableColumnModel.getColumn(3).setPreferredWidth(120);
		tableColumnModel.getColumn(4).setPreferredWidth(180);
		tableColumnModel.getColumn(5).setPreferredWidth(160);
		tableColumnModel.getColumn(6).setPreferredWidth(160);
		tblHeader.setPreferredSize(new Dimension((int) tblHeader.getPreferredSize().getWidth(), 36));
		tblHeader.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		tbl.setRowHeight(36);
		scr.setViewportView(tbl);
		DefaultTableCellRenderer dtcr = new DefaultTableCellRenderer();
		dtcr.setHorizontalAlignment(SwingConstants.RIGHT);
		tableColumnModel.getColumn(3).setCellRenderer(dtcr);
		tableColumnModel.getColumn(5).setCellRenderer(dtcr);
		tableColumnModel.getColumn(6).setCellRenderer(dtcr);
		pnlControl = new ControlPanel(Utils.getLeft(286), topPnlControl, main);
		this.add(pnlControl);

		addAncestorListener(new AncestorListener() {

			@Override
			public void ancestorAdded(AncestorEvent event) {
				if (listDV == null)
					listDV = (List<DichVu>) dichVu_DAO.getAllDichVu();
				else
					listDV = dichVu_DAO.getDanhSachDichVuTheoMa(listDV);
				tableModel.setRowCount(0);
				addRow(listDV);
				pnlControl.setTbl(tbl);
			}

			@Override
			public void ancestorMoved(AncestorEvent event) {
				// TODO Auto-generated method stub

			}

			@Override
			public void ancestorRemoved(AncestorEvent event) {
				// TODO Auto-generated method stub

			}
		});

		// Sự kiện nút tìm kiếm dịch vụ
		btnSearch.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				filterDichVu(!btnDanhSachXoa.isVisible());
			}
		});
//		Sự kiện nút thêm dịch vụ
		btnThem.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if (!btnThem.isEnabled())
					return;
				main.addPnlBody(new ThemDichVu_GUI(main), "Thêm dịch vụ", 1, 0);

			};
		});
//		Sự kiện nút xem dịch vụ
		btnXem.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (!btnXem.isEnabled())
					return;
				int row = tbl.getSelectedRow();
				if (row == -1) {
					new JDialogCustom(main, components.jDialog.JDialogCustom.Type.warning).showMessage("Warning",
							"Vui lòng chọn dịch vụ muốn xem");
				} else {
					String maDichVu = tableModel.getValueAt(row, 0).toString();
					main.addPnlBody(new ThongTinChiTietDichVu_GUI(main, dichVu_DAO.getDichVuTheoMa(maDichVu), false),
							"Thông tin chi tiết dịch vụ", 1, 0);
					tableModel.setRowCount(0);
					filterDichVu(false);
				}
			}
		});

//		Sự kiện nút sửa thông tin dịch vụ
		btnSua.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (!btnSua.isEnabled())
					return;
				int row = tbl.getSelectedRow();
				if (row == -1) {
					new JDialogCustom(main, components.jDialog.JDialogCustom.Type.warning).showMessage("Warning",
							"Vui lòng chọn dịch vụ muốn sửa");
				} else {
					String maDichVu = tableModel.getValueAt(row, 0).toString();
					main.addPnlBody(new ThongTinChiTietDichVu_GUI(main, dichVu_DAO.getDichVuTheoMa(maDichVu), true),
							"Thông tin chi tiết dịch vụ", 1, 0);
				}
			}
		});

//		Sự kiện nút xóa dịch vụ
		btnXoa.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (!btnXoa.isEnabled())
					return;

				int row = tbl.getSelectedRow();

				if (row != -1) {

					JDialogCustom jDialogCustom = new JDialogCustom(main);

					jDialogCustom.getBtnOK().addMouseListener(new MouseAdapter() {
						public void mouseClicked(MouseEvent e) {
							boolean res = dichVu_DAO.xoaDichVu(tbl.getValueAt(row, 0).toString());
							if (res)
								new Notification(main, components.notification.Notification.Type.SUCCESS,
										"Xóa dịch vụ thành công");
							else
								new Notification(main, components.notification.Notification.Type.ERROR,
										"Xóa dịch vụ thất bại");
							tableModel.setRowCount(0);
							filterDichVu(false);
						};
					});

					jDialogCustom.getBtnCancel().addMouseListener(new MouseAdapter() {
						public void mouseClicked(MouseEvent e) {
							return;
						};
					});
					jDialogCustom.showMessage("Warning", "Bạn chắc chắn muốn xóa dịch vụ này");

				} else {
					new JDialogCustom(main, components.jDialog.JDialogCustom.Type.warning).showMessage("Warning",
							"Vui lòng chọn dịch vụ muốn xóa");
				}
			}
		});

// sự kiện nút khôi phục dịch vụ
		btnKhoiPhuc.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (!btnKhoiPhuc.isEnabled())
					return;

				int row = tbl.getSelectedRow();

				if (row != -1) {

					JDialogCustom jDialogCustom = new JDialogCustom(main);

					jDialogCustom.getBtnOK().addMouseListener(new MouseAdapter() {
						public void mouseClicked(MouseEvent e) {
							boolean res = dichVu_DAO.khoiPhucDichVu(tbl.getValueAt(row, 0).toString());
							if (res)
								new Notification(main, components.notification.Notification.Type.SUCCESS,
										"Khôi phục dịch vụ thành công");
							else
								new Notification(main, components.notification.Notification.Type.ERROR,
										"Khôi phục dịch vụ thất bại");
							tableModel.setRowCount(0);

							filterDichVu(true);
						};
					});

					jDialogCustom.getBtnCancel().addMouseListener(new MouseAdapter() {
						public void mouseClicked(MouseEvent e) {
							return;
						};
					});
					jDialogCustom.showMessage("Warning", "Bạn chắc chắn muốn khôi phục dịch vụ này");

				} else {
					new JDialogCustom(main, components.jDialog.JDialogCustom.Type.warning).showMessage("Warning",
							"Vui lòng chọn dịch vụ muốn khôi phục");
				}
			}
		});
//		Sự kiện JComboBox loại dịch vụ
		cmbLoaiDV.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED) {
					filterDichVu(!btnDanhSachXoa.isVisible());
				}
			}
		});

//		Sự kiện JComboBox trạng thái
		cmbSoLuong.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED) {
					filterDichVu(!btnDanhSachXoa.isVisible());
				}
			}
		});

//		Sự kiện JTable
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

	}

	private void addRow(DichVu dichVu) {
		tableModel.addRow(new String[] { dichVu.getMaDichVu(), dichVu.getTenDichVu(), dichVu.getDonViTinh(),
				String.valueOf(dichVu.getSoLuong()), dichVu.getTenDichVu(), Utils.formatTienTe(dichVu.getGiaMua()),
				Utils.formatTienTe(dichVu.getGiaBan()) });
	}

	private List<DichVu> addRow(List<DichVu> list) {
		list.forEach(dichVu -> addRow(dichVu));
		pnlControl.setTbl(tbl);
		return list;
	}

	private void filterDichVu(boolean daNgungKinhDoanh) {
		String tenDichVu = txtSearch.getText();
		String tenLoaiDV = cmbLoaiDV.getSelectedItem().toString();
		String soLuong = cmbSoLuong.getSelectedItem().toString();

		if (tenLoaiDV.equals("Phân loại"))
			tenLoaiDV = "";
		if (soLuong.equals("Số lượng"))
			soLuong = "";
		listDV = dichVu_DAO.filterDichVu(tenDichVu, tenLoaiDV, soLuong, daNgungKinhDoanh);
		tableModel.setRowCount(0);
		addRow(listDV);
	}

	public void loadTable() {
		tableModel.setRowCount(0);
		addRow(dichVu_DAO.getAllDichVu());
	}

	private void setEnabledBtnActions() {
		int row = tbl.getSelectedRow();

		if (row == -1 || !btnDanhSachXoa.isVisible()) {
			btnXem.setEnabled(false);
			btnSua.setEnabled(false);
			btnXoa.setEnabled(false);
		} else {
			btnXem.setEnabled(true);
			btnSua.setEnabled(true);
			btnXoa.setEnabled(true);
		}
	}

}
