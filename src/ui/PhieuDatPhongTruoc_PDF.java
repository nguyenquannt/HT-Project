package ui;

import java.awt.Color;
import java.awt.Desktop;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileOutputStream;
import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;

import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfTemplate;
import com.itextpdf.text.pdf.PdfWriter;

import components.button.Button;
import components.panelRound.PanelRound;
import entity.DonDatPhong;
import entity.KhachHang;
import entity.NhanVien;
import entity.Phong;
import utils.Utils;

public class PhieuDatPhongTruoc_PDF extends JFrame implements ItemListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Button btnBack;
	private Button btnInHD;
	private PanelRound pnlContent;

	public PhieuDatPhongTruoc_PDF(Main main,
			ThongTinChiTietPhieuDatPhongTruoc_GUI thongTinChiTietPhieuDatPhongTruoc_GUI, NhanVien nV, KhachHang kH,
			DonDatPhong dDP, List<Phong> phongs) {

		Time time = Time.valueOf(LocalTime.now());
		Date date = Date.valueOf(LocalDate.now());
		List<String> phong = new ArrayList<>();
		for (Phong p : phongs) {
			phong.add(p.getMaPhong());
		}

		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setUndecorated(true);
		setLocationRelativeTo(null);
		setTitle("Thông tin chi tiết phiếu đặt phòng trước");
		this.getContentPane().setBackground(new Color(242, 246, 252));
		setBounds((Utils.getScreenWidth() - 500) / 2, 100, 500, 650);
		setLayout(null);

		PanelRound pnlHeader = new PanelRound();
		pnlHeader.setBackground(Color.WHITE);
		pnlHeader.setBounds(10, 10, 480, 90);
		this.add(pnlHeader);
		pnlHeader.setLayout(null);

		JLabel lblTenQuanBold = new JLabel("Tên quán:");
		lblTenQuanBold.setBounds(10, 0, 400, 20);
		lblTenQuanBold.setFont(new Font("Arial", Font.BOLD, 14));
		lblTenQuanBold.setForeground(new Color(100, 100, 100));
		pnlHeader.add(lblTenQuanBold);

		JLabel lblTenQuanPlain = new JLabel("Karaoke Nice");
		lblTenQuanPlain.setBounds(83, 0, 400, 20);
		lblTenQuanPlain.setFont(new Font("Arial", Font.PLAIN, 14));
		lblTenQuanPlain.setForeground(new Color(100, 100, 100));
		pnlHeader.add(lblTenQuanPlain);

		JLabel lblDiaChiBold = new JLabel("Địa chỉ:");
		lblDiaChiBold.setBounds(10, 30, 400, 20);
		lblDiaChiBold.setFont(new Font("Arial", Font.BOLD, 14));
		lblDiaChiBold.setForeground(new Color(100, 100, 100));
		pnlHeader.add(lblDiaChiBold);

		JLabel lblDiaChiPlain = new JLabel("Lê Thị Hồng, Phường 17, Gò Vấp, T.P Hồ Chí Minh");
		lblDiaChiPlain.setBounds(65, 30, 400, 20);
		lblDiaChiPlain.setFont(new Font("Arial", Font.PLAIN, 14));
		lblDiaChiPlain.setForeground(new Color(100, 100, 100));
		pnlHeader.add(lblDiaChiPlain);

		JLabel lblLienHeBold = new JLabel("Liên hệ:");
		lblLienHeBold.setBounds(10, 60, 400, 20);
		lblLienHeBold.setFont(new Font("Arial", Font.BOLD, 14));
		lblLienHeBold.setForeground(new Color(100, 100, 100));
		pnlHeader.add(lblLienHeBold);

		JLabel lblLienHePlain = new JLabel("033 673 5243 - 097 888 6331");
		lblLienHePlain.setBounds(68, 60, 400, 20);
		lblLienHePlain.setFont(new Font("Arial", Font.PLAIN, 14));
		lblLienHePlain.setForeground(new Color(100, 100, 100));
		pnlHeader.add(lblLienHePlain);

		pnlContent = new PanelRound();
		pnlContent.setBackground(new Color(242, 246, 252));
		pnlContent.setBounds(10, 100, 480, 550);
		this.add(pnlContent);
		pnlContent.setLayout(null);

		JLabel lblTieuDe = new JLabel("THÔNG TIN CHI TIẾT PHIẾU ĐẶT PHÒNG TRƯỚC");
		lblTieuDe.setBounds(15, 0, 450, 28);
		lblTieuDe.setFont(new Font("Arial", Font.BOLD, 18));
		lblTieuDe.setForeground(new Color(100, 100, 100));
		pnlContent.add(lblTieuDe);

		PanelRound pnlThongTin = new PanelRound();
		pnlThongTin.setBackground(new Color(242, 246, 252));
		pnlThongTin.setBounds(10, 35, 450, 100);
		pnlContent.add(pnlThongTin);
		pnlThongTin.setLayout(null);

		JPanel pnlPhieuDatPhong = new JPanel();
		pnlPhieuDatPhong.setBounds(0, 0, 150, 20);
		pnlPhieuDatPhong.setBackground(new Color(242, 246, 252));
		pnlPhieuDatPhong.setLayout(null);
		pnlThongTin.add(pnlPhieuDatPhong);

		JLabel lblTitleMaPhieuDatPhong = new JLabel("Mã phiếu: ");
		lblTitleMaPhieuDatPhong.setFont(new Font("Arial", Font.BOLD, 14));
		lblTitleMaPhieuDatPhong.setForeground(new Color(100, 100, 100));
		lblTitleMaPhieuDatPhong.setBounds(0, 0, 75, 20);
		pnlPhieuDatPhong.add(lblTitleMaPhieuDatPhong);

		JLabel lblMaPhieuDatPhong = new JLabel("");
		lblMaPhieuDatPhong.setText(dDP.getMaDonDatPhong());
		lblMaPhieuDatPhong.setBounds(77, 0, 80, 20);
		lblMaPhieuDatPhong.setFont(new Font("Arial", Font.PLAIN, 14));
		lblMaPhieuDatPhong.setForeground(new Color(100, 100, 100));
		pnlPhieuDatPhong.add(lblMaPhieuDatPhong);

		JPanel pnlNgayLap = new JPanel();
		pnlNgayLap.setBounds(0, 25, 450, 20);
		pnlNgayLap.setBackground(new Color(242, 246, 252));
		pnlNgayLap.setLayout(null);
		pnlThongTin.add(pnlNgayLap);

		JLabel lblTitleNgayLap = new JLabel("Thời gian lập phiếu: ");
		lblTitleNgayLap.setFont(new Font("Arial", Font.BOLD, 14));
		lblTitleNgayLap.setForeground(new Color(100, 100, 100));
		lblTitleNgayLap.setBounds(0, 0, 145, 20);
		pnlNgayLap.add(lblTitleNgayLap);

		JLabel lblNgayLap = new JLabel("");
		lblNgayLap.setText(time + " - " + date);
		lblNgayLap.setBounds(147, 0, 300, 20);
		lblNgayLap.setFont(new Font("Arial", Font.PLAIN, 14));
		lblNgayLap.setForeground(new Color(100, 100, 100));
		pnlNgayLap.add(lblNgayLap);

		JPanel pnlMaNV = new JPanel();
		pnlMaNV.setBounds(0, 50, 450, 20);
		pnlMaNV.setBackground(new Color(242, 246, 252));
		pnlMaNV.setLayout(null);
		pnlThongTin.add(pnlMaNV);

		JLabel lblTitleNV = new JLabel("Mã nhân viên: ");
		lblTitleNV.setFont(new Font("Arial", Font.BOLD, 14));
		lblTitleNV.setForeground(new Color(100, 100, 100));
		lblTitleNV.setBounds(0, 0, 110, 20);
		pnlMaNV.add(lblTitleNV);

		JLabel lblNV = new JLabel("");
		lblNV.setText(nV.getMaNhanVien());
		lblNV.setBounds(112, 0, 150, 20);
		lblNV.setFont(new Font("Arial", Font.PLAIN, 14));
		lblNV.setForeground(new Color(100, 100, 100));
		pnlMaNV.add(lblNV);

		JPanel pnlTenNV = new JPanel();
		pnlTenNV.setBounds(0, 75, 450, 20);
		pnlTenNV.setBackground(new Color(242, 246, 252));
		pnlTenNV.setLayout(null);
		pnlThongTin.add(pnlTenNV);

		JLabel lblTitleTenNV = new JLabel("Tên nhân viên: ");
		lblTitleTenNV.setFont(new Font("Arial", Font.BOLD, 14));
		lblTitleTenNV.setForeground(new Color(100, 100, 100));
		lblTitleTenNV.setBounds(0, 0, 110, 20);
		pnlTenNV.add(lblTitleTenNV);

		JLabel lblTenNV = new JLabel("");
		lblTenNV.setText(nV.getHoTen());
		lblTenNV.setBounds(112, 0, 300, 20);
		lblTenNV.setFont(new Font("Arial", Font.PLAIN, 14));
		lblTenNV.setForeground(new Color(100, 100, 100));
		pnlTenNV.add(lblTenNV);

//		Thông tin	
		PanelRound pnlThongTinChiTiet = new PanelRound();
		pnlThongTinChiTiet.setBackground(Color.WHITE);
		pnlThongTinChiTiet.setBounds(0, 145, 480, 300);
		pnlThongTinChiTiet.setRoundBottomRight(10);
		pnlThongTinChiTiet.setRoundTopLeft(10);
		pnlThongTinChiTiet.setRoundTopRight(10);
		pnlThongTinChiTiet.setRoundBottomLeft(10);
		pnlContent.add(pnlThongTinChiTiet);
		pnlThongTinChiTiet.setLayout(null);

		// Thông tin khách hàng
		JPanel pnlKH = new JPanel();
		pnlKH.setBounds(10, 7, 445, 125);
		pnlKH.setBackground(Color.WHITE);
		pnlKH.setLayout(null);
		pnlThongTinChiTiet.add(pnlKH);

		JLabel lblTitleKH = new JLabel("KHÁCH HÀNG ");
		lblTitleKH.setFont(new Font("Arial", Font.BOLD, 15));
		lblTitleKH.setForeground(new Color(100, 100, 100));
		lblTitleKH.setBounds(2, 0, 250, 20);
		pnlKH.add(lblTitleKH);

		JLabel lblTitleMaKH = new JLabel("Mã khách hàng: ");
		lblTitleMaKH.setFont(new Font("Arial", Font.PLAIN, 14));
		lblTitleMaKH.setForeground(new Color(100, 100, 100));
		lblTitleMaKH.setBounds(0, 30, 110, 15);
		pnlKH.add(lblTitleMaKH);

		JLabel lblMaKH = new JLabel("");
		lblMaKH.setText(kH.getMaKhachHang());
		lblMaKH.setBounds(108, 30, 100, 15);
		lblMaKH.setFont(new Font("Arial", Font.PLAIN, 14));
		lblMaKH.setForeground(new Color(100, 100, 100));
		pnlKH.add(lblMaKH);

		JLabel lblTitleGioiTinh = new JLabel("Giới tính: ");
		lblTitleGioiTinh.setFont(new Font("Arial", Font.PLAIN, 14));
		lblTitleGioiTinh.setForeground(new Color(100, 100, 100));
		lblTitleGioiTinh.setBounds(240, 30, 65, 15);
		pnlKH.add(lblTitleGioiTinh);

		JLabel lblGioiTinh = new JLabel("");
		lblGioiTinh.setText(kH.isGioiTinh() ? "Nam" : "Nữ");
		lblGioiTinh.setBounds(307, 30, 60, 15);
		lblGioiTinh.setFont(new Font("Arial", Font.PLAIN, 14));
		lblGioiTinh.setForeground(new Color(100, 100, 100));
		pnlKH.add(lblGioiTinh);

		JLabel lblTitleTenKH = new JLabel("Tên khách hàng: ");
		lblTitleTenKH.setFont(new Font("Arial", Font.PLAIN, 14));
		lblTitleTenKH.setForeground(new Color(100, 100, 100));
		lblTitleTenKH.setBounds(0, 50, 120, 20);
		pnlKH.add(lblTitleTenKH);

		JLabel lblTenKH = new JLabel("");
		lblTenKH.setText(kH.getHoTen());
		lblTenKH.setBounds(112, 50, 370, 20);
		lblTenKH.setFont(new Font("Arial", Font.PLAIN, 14));
		lblTenKH.setForeground(new Color(100, 100, 100));
		pnlKH.add(lblTenKH);

		JLabel lblTitleSDT = new JLabel("Số điện thoại: ");
		lblTitleSDT.setFont(new Font("Arial", Font.PLAIN, 14));
		lblTitleSDT.setForeground(new Color(100, 100, 100));
		lblTitleSDT.setBounds(0, 75, 120, 20);
		pnlKH.add(lblTitleSDT);

		JLabel lblSDT = new JLabel("");
		lblSDT.setText(kH.getSoDienThoai());
		lblSDT.setBounds(95, 75, 150, 20);
		lblSDT.setFont(new Font("Arial", Font.PLAIN, 14));
		lblSDT.setForeground(new Color(100, 100, 100));
		pnlKH.add(lblSDT);

		JLabel lblTitleCCCD = new JLabel("Căn cước công dân: ");
		lblTitleCCCD.setFont(new Font("Arial", Font.PLAIN, 14));
		lblTitleCCCD.setForeground(new Color(100, 100, 100));
		lblTitleCCCD.setBounds(0, 100, 135, 20);
		pnlKH.add(lblTitleCCCD);

		JLabel lblCCCD = new JLabel("");
		lblCCCD.setText(kH.getCccd());
		lblCCCD.setBounds(137, 100, 150, 20);
		lblCCCD.setFont(new Font("Arial", Font.PLAIN, 14));
		lblCCCD.setForeground(new Color(100, 100, 100));
		pnlKH.add(lblCCCD);

		// Chi tiết phiếu đặt phòng
		JLabel lblTitleThongTinChiTiet = new JLabel("THÔNG TIN CHI TIẾT ");
		lblTitleThongTinChiTiet.setFont(new Font("Arial", Font.BOLD, 15));
		lblTitleThongTinChiTiet.setForeground(new Color(100, 100, 100));
		lblTitleThongTinChiTiet.setBounds(12, 140, 200, 20);
		pnlThongTinChiTiet.add(lblTitleThongTinChiTiet);

		JLabel lblTitlePhong = new JLabel("Phòng: ");
		lblTitlePhong.setFont(new Font("Arial", Font.PLAIN, 14));
		lblTitlePhong.setForeground(new Color(100, 100, 100));
		lblTitlePhong.setBounds(12, 165, 50, 20);
		pnlThongTinChiTiet.add(lblTitlePhong);

		JLabel lblPhong = new JLabel("");
		lblPhong.setText(String.join(", ", phong));
		lblPhong.setBounds(62, 165, 300, 20);
		lblPhong.setFont(new Font("Arial", Font.PLAIN, 14));
		lblPhong.setForeground(new Color(100, 100, 100));
		pnlThongTinChiTiet.add(lblPhong);

		JLabel lblTitleSL = new JLabel("Số lượng: ");
		lblTitleSL.setFont(new Font("Arial", Font.PLAIN, 14));
		lblTitleSL.setForeground(new Color(100, 100, 100));
		lblTitleSL.setBounds(12, 190, 70, 20);
		pnlThongTinChiTiet.add(lblTitleSL);

		JLabel lblSL = new JLabel("");
		lblSL.setText(phongs.size() + "");
		lblSL.setBounds(80, 190, 50, 20);
		lblSL.setFont(new Font("Arial", Font.PLAIN, 14));
		lblSL.setForeground(new Color(100, 100, 100));
		pnlThongTinChiTiet.add(lblSL);

		JLabel lblTitleTrangThai = new JLabel("Trạng thái: ");
		lblTitleTrangThai.setFont(new Font("Arial", Font.PLAIN, 14));
		lblTitleTrangThai.setForeground(new Color(100, 100, 100));
		lblTitleTrangThai.setBounds(12, 215, 75, 20);
		pnlThongTinChiTiet.add(lblTitleTrangThai);

		JLabel lblTrangThai = new JLabel("");
		lblTrangThai.setText(DonDatPhong.convertTrangThaiToString(dDP.getTrangThai()));
		lblTrangThai.setBounds(90, 215, 70, 20);
		lblTrangThai.setFont(new Font("Arial", Font.PLAIN, 14));
		lblTrangThai.setForeground(new Color(100, 100, 100));
		pnlThongTinChiTiet.add(lblTrangThai);

		JLabel lblTitleNDP = new JLabel("Ngày đặt phòng: ");
		lblTitleNDP.setFont(new Font("Arial", Font.PLAIN, 14));
		lblTitleNDP.setForeground(new Color(100, 100, 100));
		lblTitleNDP.setBounds(12, 240, 115, 20);
		pnlThongTinChiTiet.add(lblTitleNDP);

		JLabel lblNDP = new JLabel("");
		lblNDP.setText(Utils.formatDate(dDP.getNgayDatPhong()));
		lblNDP.setBounds(128, 240, 100, 20);
		lblNDP.setFont(new Font("Arial", Font.PLAIN, 14));
		lblNDP.setForeground(new Color(100, 100, 100));
		pnlThongTinChiTiet.add(lblNDP);

		JLabel lblTitleGDP = new JLabel("Giờ đặt phòng: ");
		lblTitleGDP.setFont(new Font("Arial", Font.PLAIN, 14));
		lblTitleGDP.setForeground(new Color(100, 100, 100));
		lblTitleGDP.setBounds(230, 240, 100, 20);
		pnlThongTinChiTiet.add(lblTitleGDP);

		JLabel lblGDP = new JLabel("");
		lblGDP.setText(Utils.convertLocalTimeToString(dDP.getGioDatPhong()));
		lblGDP.setBounds(332, 240, 100, 20);
		lblGDP.setFont(new Font("Arial", Font.PLAIN, 14));
		lblGDP.setForeground(new Color(100, 100, 100));
		pnlThongTinChiTiet.add(lblGDP);

		JLabel lblTitleNNP = new JLabel("Ngày nhận phòng: ");
		lblTitleNNP.setFont(new Font("Arial", Font.PLAIN, 14));
		lblTitleNNP.setForeground(new Color(100, 100, 100));
		lblTitleNNP.setBounds(12, 265, 125, 20);
		pnlThongTinChiTiet.add(lblTitleNNP);

		JLabel lblNNP = new JLabel("");
		lblNNP.setText(Utils.formatDate(dDP.getNgayNhanPhong()));
		lblNNP.setBounds(139, 265, 100, 20);
		lblNNP.setFont(new Font("Arial", Font.PLAIN, 14));
		lblNNP.setForeground(new Color(100, 100, 100));
		pnlThongTinChiTiet.add(lblNNP);

		JLabel lblTitleGNP = new JLabel("Giờ nhận phòng: ");
		lblTitleGNP.setFont(new Font("Arial", Font.PLAIN, 14));
		lblTitleGNP.setForeground(new Color(100, 100, 100));
		lblTitleGNP.setBounds(230, 265, 115, 20);
		pnlThongTinChiTiet.add(lblTitleGNP);

		JLabel lblGNP = new JLabel("");
		lblGNP.setText(Utils.convertLocalTimeToString(dDP.getGioNhanPhong()));
		lblGNP.setBounds(347, 265, 100, 20);
		lblGNP.setFont(new Font("Arial", Font.PLAIN, 14));
		lblGNP.setForeground(new Color(100, 100, 100));
		pnlThongTinChiTiet.add(lblGNP);

		btnBack = new Button("Trở lại");
		btnBack.setFocusable(false);
		btnBack.setForeground(Color.WHITE);
		btnBack.setColor(Utils.primaryColor);
		btnBack.setBorderColor(Utils.primaryColor);
		btnBack.setRadius(10);
		btnBack.setFont(new Font("Arial", Font.BOLD, 16));
		btnBack.setBounds(40, 470, 160, 44);
		btnBack.setColorOver(Utils.primaryColor);
		btnBack.setColorTextOver(Color.WHITE);
		btnBack.setColorTextOut(Color.WHITE);
		btnBack.setColorClick(Utils.primaryColor);
		btnBack.setBorder(new EmptyBorder(0, 0, 0, 0));
		pnlContent.add(btnBack);

		btnInHD = new Button("Xuất PDF");
		btnInHD.setFocusable(false);
		btnInHD.setForeground(Color.WHITE);
		btnInHD.setColor(Utils.primaryColor);
		btnInHD.setBorderColor(Utils.primaryColor);
		btnInHD.setRadius(10);
		btnInHD.setFont(new Font("Arial", Font.BOLD, 16));
		btnInHD.setBounds(280, 470, 160, 44);
		btnInHD.setColorOver(Utils.primaryColor);
		btnInHD.setColorTextOver(Color.WHITE);
		btnInHD.setColorTextOut(Color.WHITE);
		btnInHD.setColorClick(Utils.primaryColor);
		btnInHD.setBorder(new EmptyBorder(0, 0, 0, 0));
		pnlContent.add(btnInHD);

		btnBack.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				thongTinChiTietPhieuDatPhongTruoc_GUI.closeJFrameSub();
			}
		});

		btnInHD.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				// print the panel to pdf
				Document document = new Document();
				try {
					String path = "HoaDon" + ".pdf";
					PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(path));
					document.open();
					PdfContentByte contentByte = writer.getDirectContent();
					PdfTemplate template = contentByte.createTemplate(500, 500);
					@SuppressWarnings("deprecation")
					Graphics2D g2 = template.createGraphics(500, 500);
					pnlContent.print(g2);
					g2.dispose();
					contentByte.addTemplate(template, 30, 300);

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

	@Override
	public void itemStateChanged(ItemEvent e) {
		// TODO Auto-generated method stub

	}

}