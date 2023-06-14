package entity;

import java.util.Objects;

public class ChiTietDichVu {
	private ChiTietDatPhong chiTietDatPhong;
	private DichVu dichVu;
	private int soLuong;

	public ChiTietDichVu() {
		super();
	}

	public ChiTietDichVu(DichVu dichVu, ChiTietDatPhong chiTietDatPhong) {
		super();
		this.dichVu = dichVu;
		this.chiTietDatPhong = chiTietDatPhong;
	}

	public ChiTietDichVu(DichVu dichVu, ChiTietDatPhong chiTietDatPhong, int soLuong) {
		super();
		this.dichVu = dichVu;
		this.chiTietDatPhong = chiTietDatPhong;
		this.soLuong = soLuong;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ChiTietDichVu other = (ChiTietDichVu) obj;
		return Objects.equals(chiTietDatPhong, other.chiTietDatPhong) && Objects.equals(dichVu, other.dichVu);
	}

	public ChiTietDatPhong getChiTietDatPhong() {
		return chiTietDatPhong;
	}

	public DichVu getDichVu() {
		return dichVu;
	}

	public int getSoLuong() {
		return soLuong;
	}

	@Override
	public int hashCode() {
		return Objects.hash(chiTietDatPhong, dichVu);
	}

	public void setChiTietDatPhong(ChiTietDatPhong chiTietDatPhong) {
		this.chiTietDatPhong = chiTietDatPhong;
	}

	public void setDichVu(DichVu dichVu) {
		this.dichVu = dichVu;
	}

	public void setSoLuong(int soLuong) {
		this.soLuong = soLuong;
	}

	@Override
	public String toString() {
		return "ChiTietDichVu [dichVu=" + dichVu + ", chiTietDatPhong=" + chiTietDatPhong + ", soLuong=" + soLuong
				+ "]";
	}
}
