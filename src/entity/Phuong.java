package entity;

import java.util.Objects;

public class Phuong {
	public static String getPhuongLabel() {
		return "Phường/Xã";
	}

	private String id;
	private String phuong;
	private Quan quan;

	public Phuong() {
		super();
	}

	public Phuong(String id) {
		super();
		setId(id);
	}

	public Phuong(String id, String phuong, Quan quan) {
		super();
		setId(id);
		setPhuong(phuong);
		setQuan(quan);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Phuong other = (Phuong) obj;
		return Objects.equals(id, other.id);
	}

	public String getId() {
		return id;
	}

	public String getPhuong() {
		return phuong;
	}

	public Quan getQuan() {
		return quan;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setPhuong(String phuong) {
		this.phuong = phuong;
	}

	public void setQuan(Quan quan) {
		this.quan = quan;
	}

	@Override
	public String toString() {
		return "Phuong [id=" + id + ", phuong=" + phuong + ", quan=" + quan + "]";
	}

}
