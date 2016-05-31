package connect.asyncm;

import java.util.List;

import dataobjects.Lophoc;

public class Test {
	public static void main(String[] args) throws Exception{
		CRUDactionsDemo cr=new CRUDactionsDemo();
		//insert
		//cr.insert(new Lophoc("DHHH10H","Đại học hóa hữu cơ 10H"));
		
		//cập nhật
		//cr.update("DHHH10H", new Lophoc("DHHH11H","Đại học hóa hữu cơ 11H"));
		
		//xóa
		cr.delete("10a");
		
		//tìm kiếm
		/*Lophoc lh = cr.getByMalop("DHTH10As");
		System.out.println(lh);*/
		
		//in danh sách
		List<Lophoc> lstLH = cr.getAll();
		lstLH.forEach(x->System.out.println(x));
		cr.close();
	}
}
