package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileOutputStream;
import java.time.LocalTime;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfTemplate;
import com.itextpdf.text.pdf.PdfWriter;

import components.button.Button;
import components.panelRound.PanelRound;
import components.scrollbarCustom.ScrollBarCustom;
import dao.DichVu_DAO;
import dao.Phong_DAO;
import entity.ChiTietDatPhong;
import entity.ChiTietDichVu;
import entity.DichVu;
import entity.Phong;
import utils.Utils;

public class HoaDon_GUI extends JFrame implements ItemListener {
	private static final long serialVersionUID = 1L;
	private DichVu_DAO dichVu_DAO;
	private Phong_DAO phong_DAO;
	private PanelRound pnlContent;
	private DefaultTableModel tableModel, tableModel1;
	private JTable tblTienPhong, tblTienDV;

	public HoaDon_GUI(String maHoaDon, String ngayLap, String tenKhach, String tenNV, String gioVaoPhong,
			String gioRaPhong, String thoiGianSuDung, String tongTienPhong, String tongTienDV,
			List<ChiTietDatPhong> dsChiTietDatPhong, List<ChiTietDichVu> dsChiTietDichVu) {

		setTitle("Hoá đơn tính tiền");
		this.getContentPane().setBackground(new Color(242, 246, 252));
		setBounds((Utils.getScreenWidth() - 500) / 2, 0, 500, Utils.getBodyHeight() + 40);
		setLayout(null);
		dichVu_DAO = new DichVu_DAO();
		phong_DAO = new Phong_DAO();

		PanelRound pnlContainer = new PanelRound();
		pnlContainer.setBackground(new Color(242, 246, 252));
		pnlContainer.setBounds(0, 0, 500, Utils.getBodyHeight() - 60);
		this.add(pnlContainer);
		pnlContainer.setLayout(null);

		PanelRound pnlHeader = new PanelRound();
		pnlHeader.setBackground(Color.WHITE);
		pnlHeader.setBounds(0, 0, 485, 70);
		pnlContainer.add(pnlHeader);
		pnlHeader.setLayout(null);

		JLabel lblTenQuanPlain = new JLabel("FLEY HOTEL");
		lblTenQuanPlain.setBounds((Utils.getScreenWidth() - 500) * 1 / 4 - 40, 0, 400, 36);
		lblTenQuanPlain.setFont(new Font("Segoe UI", Font.BOLD, 30));
		lblTenQuanPlain.setForeground(new Color(100, 100, 100));
		pnlHeader.add(lblTenQuanPlain);

		JLabel lblDiaChiBold = new JLabel("Địa chỉ:");
		lblDiaChiBold.setBounds(10, 36, 400, 18);
		lblDiaChiBold.setFont(new Font("Segoe UI", Font.BOLD, 12));
		lblDiaChiBold.setForeground(new Color(100, 100, 100));
		pnlHeader.add(lblDiaChiBold);

		JLabel lblDiaChiPlain = new JLabel("Lê Thị Hồng, Phường 17, Gò Vấp, T.P Hồ Chí Minh");
		lblDiaChiPlain.setBounds(60, 36, 400, 18);
		lblDiaChiPlain.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		lblDiaChiPlain.setForeground(new Color(100, 100, 100));
		pnlHeader.add(lblDiaChiPlain);

		JLabel lblLienHeBold = new JLabel("Liên hệ:");
		lblLienHeBold.setBounds(10, 52, 400, 18);
		lblLienHeBold.setFont(new Font("Segoe UI", Font.BOLD, 12));
		lblLienHeBold.setForeground(new Color(100, 100, 100));
		pnlHeader.add(lblLienHeBold);

		JLabel lblLienHePlain = new JLabel("0866 432 245 - 0387 228 810");
		lblLienHePlain.setBounds(65, 52, 400, 18);
		lblLienHePlain.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		lblLienHePlain.setForeground(new Color(100, 100, 100));
		pnlHeader.add(lblLienHePlain);

		pnlContent = new PanelRound();
		pnlContent.setBackground(new Color(242, 246, 252));
		pnlContent.setBounds(10, 80, 465, 500);
		pnlContainer.add(pnlContent);
		pnlContent.setLayout(null);

		JLabel lblTieuDe = new JLabel("HOÁ ĐƠN TÍNH TIỀN");
		lblTieuDe.setBounds(130, 0, 400, 28);
		lblTieuDe.setFont(new Font("Segoe UI", Font.BOLD, 20));
		lblTieuDe.setForeground(new Color(100, 100, 100));
		pnlContent.add(lblTieuDe);

		PanelRound pnlThongTin = new PanelRound();
		pnlThongTin.setBackground(new Color(242, 246, 252));
		pnlThongTin.setBounds(10, 35, 445, 50);
		pnlContent.add(pnlThongTin);
		pnlThongTin.setLayout(null);

		// Mã hoá đơn
		JPanel pnlHoaDon = new JPanel();
		pnlHoaDon.setBounds(0, 0, 445 / 2 - 10, 28);
		pnlHoaDon.setBackground(new Color(242, 246, 252));
		pnlHoaDon.setLayout(new BorderLayout());
		pnlThongTin.add(pnlHoaDon);

		JLabel lblHoaDon = new JLabel("Mã hoá đơn: ");
		lblHoaDon.setFont(new Font("Segoe UI", Font.BOLD, 14));
		lblHoaDon.setForeground(new Color(100, 100, 100));
		pnlHoaDon.add(lblHoaDon, BorderLayout.WEST);

		JLabel lblHoaDonKQ = new JLabel("");
		lblHoaDonKQ.setText(maHoaDon + " ");
		lblHoaDonKQ.setBounds(0, 0, 299, 28);
		lblHoaDonKQ.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		lblHoaDonKQ.setForeground(new Color(100, 100, 100));
		pnlHoaDon.add(lblHoaDonKQ, BorderLayout.EAST);

		JPanel pnlNgayLap = new JPanel();
		pnlNgayLap.setBounds(220, 0, 445 / 2, 28);
		pnlNgayLap.setBackground(new Color(242, 246, 252));
		pnlNgayLap.setLayout(new BorderLayout());
		pnlThongTin.add(pnlNgayLap);

		JLabel lblNgayLap = new JLabel("Ngày lập: ");
		lblNgayLap.setFont(new Font("Segoe UI", Font.BOLD, 14));
		lblNgayLap.setForeground(new Color(100, 100, 100));
		pnlNgayLap.add(lblNgayLap, BorderLayout.WEST);

		JLabel lblNgayLapKQ = new JLabel("");
		lblNgayLapKQ.setText(ngayLap + " ");
		lblNgayLapKQ.setBounds(0, 0, 299, 28);
		lblNgayLapKQ.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		lblNgayLapKQ.setForeground(new Color(100, 100, 100));
		pnlNgayLap.add(lblNgayLapKQ, BorderLayout.EAST);

		JPanel pnlTenKH = new JPanel();
		pnlTenKH.setBounds(0, 25, 445 / 2 - 10, 28);
		pnlTenKH.setBackground(new Color(242, 246, 252));
		pnlTenKH.setLayout(new BorderLayout());
		pnlThongTin.add(pnlTenKH);

		JLabel lblKH = new JLabel("Tên KH: ");
		lblKH.setFont(new Font("Segoe UI", Font.BOLD, 14));
		lblKH.setForeground(new Color(100, 100, 100));
		pnlTenKH.add(lblKH, BorderLayout.WEST);

		JLabel lblKHKQ = new JLabel("");
		lblKHKQ.setText(tenKhach);
		lblKHKQ.setBounds(0, 0, 299, 28);
		lblKHKQ.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		lblKHKQ.setForeground(new Color(100, 100, 100));
		pnlTenKH.add(lblKHKQ, BorderLayout.EAST);

		JPanel pnlTenNV = new JPanel();
		pnlTenNV.setBounds(220, 25, 445 / 2, 28);
		pnlTenNV.setBackground(new Color(242, 246, 252));
		pnlTenNV.setLayout(new BorderLayout());
		pnlThongTin.add(pnlTenNV);

		JLabel lblNV = new JLabel("Tên NV: ");
		lblNV.setFont(new Font("Segoe UI", Font.BOLD, 14));
		lblNV.setForeground(new Color(100, 100, 100));
		pnlTenNV.add(lblNV, BorderLayout.WEST);

		JLabel lblNVKQ = new JLabel("");
		lblNVKQ.setText(tenNV);
		lblNVKQ.setBounds(0, 0, 299, 28);
		lblNVKQ.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		lblNVKQ.setForeground(new Color(100, 100, 100));
		pnlTenNV.add(lblNVKQ, BorderLayout.EAST);

		PanelRound pnlTienPhong = new PanelRound();
		pnlTienPhong.setBackground(Color.WHITE);
		pnlTienPhong.setBounds(0, 100, 465, 180);
		pnlTienPhong.setRoundBottomRight(10);
		pnlTienPhong.setRoundTopLeft(10);
		pnlTienPhong.setRoundTopRight(10);
		pnlTienPhong.setRoundBottomLeft(10);
		pnlContent.add(pnlTienPhong);
		pnlTienPhong.setLayout(null);

		// Tổng tiền phòng
		JPanel pnlTongTienPhong = new JPanel();
		pnlTongTienPhong.setBounds(10, 0, 445, 28);
		pnlTongTienPhong.setBackground(Color.WHITE);
		pnlTongTienPhong.setLayout(new BorderLayout());
		pnlTienPhong.add(pnlTongTienPhong);

		JLabel lblTongTienPhong = new JLabel("Tổng tiền phòng: ");
		lblTongTienPhong.setFont(new Font("Segoe UI", Font.BOLD, 15));
		lblTongTienPhong.setForeground(new Color(100, 100, 100));
		pnlTongTienPhong.add(lblTongTienPhong, BorderLayout.WEST);

		JLabel lblTongTienPhongKQ = new JLabel("");
		lblTongTienPhongKQ.setText(Utils.formatTienTe(Double.parseDouble(tongTienPhong)));
		lblTongTienPhongKQ.setBounds(0, 0, 299, 28);
		lblTongTienPhongKQ.setFont(new Font("Segoe UI", Font.PLAIN, 15));
		lblTongTienPhongKQ.setForeground(new Color(100, 100, 100));
		pnlTongTienPhong.add(lblTongTienPhongKQ, BorderLayout.EAST);

		JPanel line = new JPanel();
		line.setBounds(10, 30, 445, 1);
		line.setLayout(null);
		line.setBackground(new Color(100, 100, 100));
		pnlTienPhong.add(line);

		JScrollPane scr = new JScrollPane();
		scr.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scr.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scr.setBounds(10, 40, 445, 130);
		scr.setBackground(Utils.primaryColor);
		ScrollBarCustom scp = new ScrollBarCustom();
		scp.setScrollbarColor(new Color(203, 203, 203));
		scr.setVerticalScrollBar(scp);
		pnlTienPhong.add(scr);

		tblTienPhong = new JTable() {
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}

			@Override
			public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
				Component c = super.prepareRenderer(renderer, row, column);
				if (row % 2 == 0)
					c.setBackground(Color.WHITE);
				else
					c.setBackground(new Color(232, 232, 232));
				return c;
			}
		};

		tableModel = new DefaultTableModel(
				new String[] { "Mã phòng", "Loại phòng", "Thời gian thuê", "Giá phòng", "Tổng tiền" }, 0);
		tblTienPhong.setModel(tableModel);
		tblTienPhong.getColumnModel().getColumn(0).setPreferredWidth(445 / 5 - 20);
		tblTienPhong.getColumnModel().getColumn(1).setPreferredWidth(445 / 5 + 20);
		tblTienPhong.getColumnModel().getColumn(2).setPreferredWidth(445 / 5 - 10);
		tblTienPhong.getColumnModel().getColumn(3).setPreferredWidth(445 / 5);
		tblTienPhong.getColumnModel().getColumn(4).setPreferredWidth(445 / 5);

		tblTienPhong.getTableHeader().setBackground(Utils.primaryColor);
		tblTienPhong.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		tblTienPhong.getTableHeader().setForeground(Color.WHITE);
		tblTienPhong.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		tblTienPhong.getTableHeader()
				.setPreferredSize(new Dimension((int) tblTienPhong.getTableHeader().getPreferredSize().getWidth(), 25));
		tblTienPhong.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
		tblTienPhong.setRowHeight(25);
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment(JLabel.CENTER);
		tblTienPhong.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
		tblTienPhong.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);
		scr.setViewportView(tblTienPhong);

		DefaultTableCellRenderer dtcr = new DefaultTableCellRenderer();
		dtcr.setHorizontalAlignment(SwingConstants.RIGHT);
		tblTienPhong.getColumnModel().getColumn(2).setCellRenderer(dtcr);
		tblTienPhong.getColumnModel().getColumn(3).setCellRenderer(dtcr);
		tblTienPhong.getColumnModel().getColumn(4).setCellRenderer(dtcr);

		PanelRound pnlTienDV = new PanelRound();
		pnlTienDV.setBackground(Color.WHITE);
		pnlTienDV.setBounds(0, 280, 465, 220);
		pnlTienDV.setRoundBottomRight(10);
		pnlTienDV.setRoundTopLeft(10);
		pnlTienDV.setRoundTopRight(10);
		pnlTienDV.setRoundBottomLeft(10);
		pnlTienDV.setLayout(null);
		pnlTienDV.setLayout(null);
		pnlContent.add(pnlTienDV);

		JPanel pnlTongTienDV = new JPanel();
		pnlTongTienDV.setBounds(10, 0, 445, 28);
		pnlTongTienDV.setBackground(Color.WHITE);
		pnlTongTienDV.setLayout(new BorderLayout());
		pnlTienDV.add(pnlTongTienDV);

		JLabel lblTongTienDV = new JLabel("Tổng tiền dịch vụ: ");
		lblTongTienDV.setFont(new Font("Segoe UI", Font.BOLD, 15));
		lblTongTienDV.setForeground(new Color(100, 100, 100));
		pnlTongTienDV.add(lblTongTienDV, BorderLayout.WEST);

		JLabel lblTongTienDVKQ = new JLabel("");
		lblTongTienDVKQ.setText(Utils.formatTienTe(Double.parseDouble(tongTienDV)));
		lblTongTienDVKQ.setBounds(0, 0, 299, 28);
		lblTongTienDVKQ.setFont(new Font("Segoe UI", Font.PLAIN, 15));
		lblTongTienDVKQ.setForeground(new Color(100, 100, 100));
		pnlTongTienDV.add(lblTongTienDVKQ, BorderLayout.EAST);

		JPanel line2 = new JPanel();
		line2.setBounds(10, 30, 445, 1);
		line2.setLayout(null);
		line2.setBackground(new Color(100, 100, 100));
		pnlTienDV.add(line2);

		JScrollPane scr1 = new JScrollPane();
		scr1.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scr1.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scr1.setBounds(10, 40, 445, 130);
		scr1.setBackground(Utils.primaryColor);
		ScrollBarCustom scp1 = new ScrollBarCustom();
		scp1.setScrollbarColor(new Color(203, 203, 203));
		scr1.setVerticalScrollBar(scp1);
		pnlTienDV.add(scr1);

		tblTienDV = new JTable() {
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}

			@Override
			public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
				Component c = super.prepareRenderer(renderer, row, column);
				if (row % 2 == 0)
					c.setBackground(Color.WHITE);
				else
					c.setBackground(new Color(232, 232, 232));
				return c;
			}
		};

		tableModel1 = new DefaultTableModel(new String[] { "Tên", "Số lượng", "Giá", "Tổng tiền" }, 0);
		tblTienDV.setModel(tableModel1);
		tblTienDV.getColumnModel().getColumn(0).setPreferredWidth(445 / 4 + 30);
		tblTienDV.getColumnModel().getColumn(1).setPreferredWidth(445 / 4 - 40);
		tblTienDV.getColumnModel().getColumn(2).setPreferredWidth(445 / 4);
		tblTienDV.getColumnModel().getColumn(3).setPreferredWidth(445 / 4);

		tblTienDV.getTableHeader().setBackground(Utils.primaryColor);
		tblTienDV.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		tblTienDV.getTableHeader().setForeground(Color.WHITE);
		tblTienDV.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		tblTienDV.getTableHeader()
				.setPreferredSize(new Dimension((int) tblTienDV.getTableHeader().getPreferredSize().getWidth(), 25));
		tblTienDV.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
		tblTienDV.setRowHeight(25);
		scr1.setViewportView(tblTienDV);

		DefaultTableCellRenderer dtcr1 = new DefaultTableCellRenderer();
		dtcr1.setHorizontalAlignment(SwingConstants.RIGHT);
		tblTienDV.getColumnModel().getColumn(1).setCellRenderer(dtcr1);
		tblTienDV.getColumnModel().getColumn(2).setCellRenderer(dtcr1);
		tblTienDV.getColumnModel().getColumn(3).setCellRenderer(dtcr1);
		addRowRandomData(dsChiTietDatPhong, dsChiTietDichVu);

		JPanel pnlTongTien = new JPanel();
		pnlTongTien.setBounds(10, 180, 445, 28);
		pnlTongTien.setBackground(Color.WHITE);
		pnlTongTien.setLayout(new BorderLayout());
		pnlTienDV.add(pnlTongTien);

		JLabel lblTongTien = new JLabel("Tổng tiền: ");
		lblTongTien.setFont(new Font("Segoe UI", Font.BOLD, 15));
		lblTongTien.setForeground(new Color(100, 100, 100));
		pnlTongTien.add(lblTongTien, BorderLayout.WEST);

		JLabel lblTongTienKQ = new JLabel("");
		lblTongTienKQ.setText(
				Utils.formatTienTe((Double.parseDouble(tongTienDV) + Double.parseDouble(tongTienPhong)) * 1.1));
		lblTongTienKQ.setBounds(0, 0, 299, 28);
		lblTongTienKQ.setFont(new Font("Segoe UI", Font.PLAIN, 15));
		lblTongTienKQ.setForeground(new Color(100, 100, 100));
		pnlTongTien.add(lblTongTienKQ, BorderLayout.EAST);

		PanelRound pnlFooter = new PanelRound();
		pnlFooter.setBounds(10, Utils.getBodyHeight() - 60, 465, 50);
		pnlFooter.setBackground(new Color(242, 246, 252));
		this.add(pnlFooter);
		pnlFooter.setLayout(null);

		Button btnInHD = new Button("In hoá đơn");
		btnInHD.setFocusable(false);
		btnInHD.setForeground(Color.WHITE);
		btnInHD.setColor(Utils.primaryColor);
		btnInHD.setBorderColor(Utils.primaryColor);
		btnInHD.setRadius(10);
		btnInHD.setFont(new Font("Segoe UI", Font.BOLD, 16));
		btnInHD.setBounds(465 - 160, 0, 160, 44);
		btnInHD.setColorOver(Utils.primaryColor);
		btnInHD.setColorTextOver(Color.WHITE);
		btnInHD.setColorTextOut(Color.WHITE);
		btnInHD.setColorClick(Utils.primaryColor);
		btnInHD.setBorder(new EmptyBorder(0, 0, 0, 0));
		pnlFooter.add(btnInHD);

		btnInHD.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				// print the panel to pdf
				Document document = new Document();
				try {
					String path = "HoaDon" + maHoaDon + ".pdf";
					PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(path));
					document.open();

					PdfContentByte cb = writer.getDirectContent();
					PdfTemplate tp = cb.createTemplate(485, Utils.getBodyHeight());
					@SuppressWarnings("deprecation")
					Graphics2D g2 = tp.createGraphicsShapes(485, Utils.getBodyHeight());
					java.awt.Font font = new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 14);
					g2.setFont(font);
					pnlContainer.print(g2);
					g2.dispose();
					cb.addTemplate(tp, 50, 100);

					File pdfFile = new File(path);
					if (Desktop.isDesktopSupported()) {
						Desktop.getDesktop().open(pdfFile);
					}

				} catch (Exception e1) {
					e1.printStackTrace();
				} finally {
					if (document.isOpen()) {
						document.close();
					}
				}
			}
		});

	}

	private void addRowRandomData(List<ChiTietDatPhong> dsChiTietDatPhong, List<ChiTietDichVu> dsChiTietDichVu) {

		DichVu dichVu;
		for (ChiTietDichVu chiTietDichVu : dsChiTietDichVu) {
			dichVu = dichVu_DAO.getDichVuTheoMa(chiTietDichVu.getDichVu().getMaDichVu());
			String ten = dichVu.getTenDichVu();
			int soLuong = chiTietDichVu.getSoLuong();
			double gia = dichVu.getGiaBan();
			double thanhTien = soLuong * gia;
			tableModel1
					.addRow(new String[] { ten, soLuong + "", Utils.formatTienTe(gia), Utils.formatTienTe(thanhTien) });

		}
		Phong phong;
		int hours, minutes, hieu = 0;
		LocalTime timeNow = LocalTime.now();
		double tienPhong = 0;
		for (ChiTietDatPhong chiTietDatPhong : dsChiTietDatPhong) {
			String maPhong = chiTietDatPhong.getPhong().getMaPhong();
			phong = phong_DAO.getPhong(maPhong);
			String loaiPhong = phong.getTenLoai();
			LocalTime gioRa = chiTietDatPhong.getGioRa() == null ? timeNow : chiTietDatPhong.getGioRa();
			LocalTime gioVao = chiTietDatPhong.getGioVao();
			hours = gioRa.getHour() - gioVao.getHour();
			minutes = gioRa.getMinute() - gioVao.getMinute();
			hieu = hours * 60 + minutes;
			tienPhong = hieu * phong.getGiaTien() * 1.0 / 60;
			tableModel.addRow(new String[] { maPhong, loaiPhong, hieu + " phút", Utils.formatTienTe(phong.getGiaTien()),
					Utils.formatTienTe(tienPhong) });
		}

	}

	@Override
	public void itemStateChanged(ItemEvent e) {
		// TODO Auto-generated method stub

	}

}
