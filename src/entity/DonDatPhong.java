package entity;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;

public class DonDatPhong {
	/**
	 * Các trạng thái đặt phòng
	 * 
	 * @author ThaoHa
	 *
	 */
	public static enum TrangThai {
		DaHuy, DangCho, DangThue, DaTra;
	}

	/**
	 * Chuyển String sang trạng thái đặt phòng
	 * 
	 * @param trangThai
	 * @return
	 */
	public static TrangThai convertStringToTrangThai(String trangThai) {
		if (trangThai.equals("Đã hủy"))
			return TrangThai.DaHuy;
		if (trangThai.equals("Đang chờ"))
			return TrangThai.DangCho;
		if (trangThai.equals("Đang thuê"))
			return TrangThai.DangThue;
		return TrangThai.DaTra;
	}

	/**
	 * Chuyển trạng thái sang String
	 * 
	 * @param trangThai
	 * @return
	 */
	public static String convertTrangThaiToString(TrangThai trangThai) {
		if (trangThai.equals(TrangThai.DaHuy))
			return "Đã hủy";
		if (trangThai.equals(TrangThai.DangCho))
			return "Đang chờ";
		if (trangThai.equals(TrangThai.DangThue))
			return "Đang thuê";
		return "Đã trả";
	}

	private LocalTime gioDatPhong;
	private LocalTime gioNhanPhong;
	private KhachHang khachHang;
	private String maDonDatPhong;
	private LocalDate ngayDatPhong;
	private LocalDate ngayNhanPhong;
	private NhanVien nhanVien;
	private TrangThai trangThai;

	public DonDatPhong() {
		super();
	}

	public DonDatPhong(String maDonDatPhong) {
		super();
		this.maDonDatPhong = maDonDatPhong;
	}

	public DonDatPhong(String maDonDatPhong, KhachHang khachHang, NhanVien nhanVien, LocalDate ngayDatPhong,
			LocalTime gioDatPhong, LocalDate ngayNhanPhong, LocalTime gioNhanPhong, TrangThai trangThai) {
		super();
		this.maDonDatPhong = maDonDatPhong;
		this.khachHang = khachHang;
		this.nhanVien = nhanVien;
		this.ngayDatPhong = ngayDatPhong;
		this.gioDatPhong = gioDatPhong;
		this.ngayNhanPhong = ngayNhanPhong;
		this.gioNhanPhong = gioNhanPhong;
		this.trangThai = trangThai;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DonDatPhong other = (DonDatPhong) obj;
		return Objects.equals(maDonDatPhong, other.maDonDatPhong);
	}

	public LocalTime getGioDatPhong() {
		return gioDatPhong;
	}

	public LocalTime getGioNhanPhong() {
		return gioNhanPhong;
	}

	public KhachHang getKhachHang() {
		return khachHang;
	}

	public String getMaDonDatPhong() {
		return maDonDatPhong;
	}

	public LocalDate getNgayDatPhong() {
		return ngayDatPhong;
	}

	public LocalDate getNgayNhanPhong() {
		return ngayNhanPhong;
	}

	public NhanVien getNhanVien() {
		return nhanVien;
	}

	public TrangThai getTrangThai() {
		return trangThai;
	}

	@Override
	public int hashCode() {
		return Objects.hash(maDonDatPhong);
	}

	public void setGioDatPhong(LocalTime gioDatPhong) {
		this.gioDatPhong = gioDatPhong;
	}

	public void setGioNhanPhong(LocalTime gioNhanPhong) {
		this.gioNhanPhong = gioNhanPhong;
	}

	public void setKhachHang(KhachHang khachHang) {
		this.khachHang = khachHang;
	}

	public void setMaDonDatPhong(String maDonDatPhong) {
		this.maDonDatPhong = maDonDatPhong;
	}

	public void setNgayDatPhong(LocalDate ngayDatPhong) {
		this.ngayDatPhong = ngayDatPhong;
	}

	public void setNgayNhanPhong(LocalDate ngayNhanPhong) {
		this.ngayNhanPhong = ngayNhanPhong;
	}

	public void setNhanVien(NhanVien nhanVien) {
		this.nhanVien = nhanVien;
	}

	public void setTrangThai(TrangThai trangThai) {
		this.trangThai = trangThai;
	}

	@Override
	public String toString() {
		return "DonDatPhong [maDonDatPhong=" + maDonDatPhong + ", khachHang=" + khachHang + ", nhanVien=" + nhanVien
				+ ", ngayDatPhong=" + ngayDatPhong + ", gioDatPhong=" + gioDatPhong + ", ngayNhanPhong=" + ngayNhanPhong
				+ ", gioNhanPhong=" + gioNhanPhong + ", trangThai=" + trangThai + "]";
	}

}
