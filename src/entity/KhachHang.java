package entity;

import java.time.LocalDate;
import java.util.Objects;

public class KhachHang {
	private String cccd;
	private String diaChiCuThe;
	private boolean gioiTinh;
	private String hoTen;
	private String maKhachHang;
	private LocalDate ngaySinh;
	private Phuong phuong;
	private Quan quan;
	private String soDienThoai;
	private Tinh tinh;
	private boolean trangThaiXoa;

	public KhachHang() {
		super();
	}

	public KhachHang(String maKhachHang) {
		super();
		this.maKhachHang = maKhachHang;
	}

	public KhachHang(String maKhachHang, String hoTen, String cccd, LocalDate ngaySinh, boolean gioiTinh,
			String soDienThoai, Tinh tinh, Quan quan, Phuong phuong, String diaChiCuThe, boolean trangThaiXoa) {
		super();
		this.maKhachHang = maKhachHang;
		this.hoTen = hoTen;
		this.cccd = cccd;
		this.ngaySinh = ngaySinh;
		this.gioiTinh = gioiTinh;
		this.soDienThoai = soDienThoai;
		this.tinh = tinh;
		this.quan = quan;
		this.phuong = phuong;
		this.diaChiCuThe = diaChiCuThe;
		this.trangThaiXoa = trangThaiXoa;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		KhachHang other = (KhachHang) obj;
		return Objects.equals(maKhachHang, other.maKhachHang);
	}

	public String getCccd() {
		return cccd;
	}

	public String getDiaChiCuThe() {
		return diaChiCuThe;
	}

	public String getHoTen() {
		return hoTen;
	}

	public String getMaKhachHang() {
		return maKhachHang;
	}

	public LocalDate getNgaySinh() {
		return ngaySinh;
	}

	public Phuong getPhuong() {
		return phuong;
	}

	public Quan getQuan() {
		return quan;
	}

	public String getSoDienThoai() {
		return soDienThoai;
	}

	public Tinh getTinh() {
		return tinh;
	}

	@Override
	public int hashCode() {
		return Objects.hash(maKhachHang);
	}

	public boolean isGioiTinh() {
		return gioiTinh;
	}

	public boolean isTrangThaiXoa() {
		return trangThaiXoa;
	}

	public void setCccd(String cccd) {
		this.cccd = cccd;
	}

	public void setDiaChiCuThe(String diaChiCuThe) {
		this.diaChiCuThe = diaChiCuThe;
	}

	public void setGioiTinh(boolean gioiTinh) {
		this.gioiTinh = gioiTinh;
	}

	public void setHoTen(String hoTen) {
		this.hoTen = hoTen;
	}

	public void setMaKhachHang(String maKhachHang) {
		this.maKhachHang = maKhachHang;
	}

	public void setNgaySinh(LocalDate ngaySinh) {
		this.ngaySinh = ngaySinh;
	}

	public void setPhuong(Phuong phuong) {
		this.phuong = phuong;
	}

	public void setQuan(Quan quan) {
		this.quan = quan;
	}

	public void setSoDienThoai(String soDienThoai) {
		this.soDienThoai = soDienThoai;
	}

	public void setTinh(Tinh tinh) {
		this.tinh = tinh;
	}

	public void setTrangThaiXoa(boolean trangThaiXoa) {
		this.trangThaiXoa = trangThaiXoa;
	}

	@Override
	public String toString() {
		return "KhachHang [maKhachHang=" + maKhachHang + ", hoTen=" + hoTen + ", cccd=" + cccd + ", ngaySinh="
				+ ngaySinh + ", gioiTinh=" + gioiTinh + ", soDienThoai=" + soDienThoai + ", tinh=" + tinh + ", quan="
				+ quan + ", phuong=" + phuong + ", diaChiCuThe=" + diaChiCuThe + ", trangThaiXoa=" + trangThaiXoa + "]";
	}
}
