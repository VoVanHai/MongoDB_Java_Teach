package entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.bson.types.ObjectId;
@Entity
@Table(name="sinhvien")
public class Sinhvien {
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	private ObjectId id;
	
	private long mssv;
	private String hoten;
	private String diachi;
	private String malop;
	public Sinhvien() {
	}
	public Sinhvien(long mssv, String hoten, String diachi, String malop) {
		this.mssv = mssv;
		this.hoten = hoten;
		this.diachi = diachi;
		this.malop = malop;
	}
	public long getMssv() {
		return mssv;
	}
	public void setMssv(long mssv) {
		this.mssv = mssv;
	}
	public String getHoten() {
		return hoten;
	}
	public void setHoten(String hoten) {
		this.hoten = hoten;
	}
	public String getDiachi() {
		return diachi;
	}
	public void setDiachi(String diachi) {
		this.diachi = diachi;
	}
	public String getMalop() {
		return malop;
	}
	public void setMalop(String malop) {
		this.malop = malop;
	}
	@Override
	public String toString() {
		return "Sinhvien [id=" + id + ", mssv=" + mssv + ", hoten=" + hoten + ", diachi=" + diachi + ", malop=" + malop
				+ "]";
	}
	
}
