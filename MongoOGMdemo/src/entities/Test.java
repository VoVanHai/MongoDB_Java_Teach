package entities;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

public class Test {
	public static void main(String[] args) throws Exception{
		//build the EntityManagerFactory as you would build in in Hibernate ORM
		EntityManagerFactory emf = 
				Persistence.createEntityManagerFactory("ogm-jpa");
		EntityManager em = emf.createEntityManager();

		/*----------------------------------------------------------------------
		//chú ý dùng: 
		//<persistence-unit name="ogm-jpa-persistent_name" 
		//	transaction-type="RESOURCE_LOCAL">
		//để có thể dùng transaction local. nếu là JTA sẽ không dùng được transaction
		//----------------------------------------------------------------------
		 */
		EntityTransaction trans = em.getTransaction();
		/*//insert
		trans.begin();
		em.persist(new Lophoc("123","lop hoc 123"));
		em.persist(new Sinhvien(1001, "nguyen van teo", "12 nvb", "DHTH10A"));
		trans.commit();*/

		//update
		/*String sql="select l from Lophoc l where l.malop='123'";
		TypedQuery<Lophoc> query = em.createQuery(sql,Lophoc.class);
		if(query.getResultList().size()!=0){
			Lophoc selLop = query.getSingleResult();
			selLop.setTenlop("lop dai chu to abc");
			try {
				trans.begin();
				em.merge(selLop);
				trans.commit();
			} catch (Exception e) {
				trans.rollback();
			}
		}
		else
			System.out.println("nothing to update");*/

		/*//delete
		String delsql="select l from Lophoc l where l.malop='123'";
		TypedQuery<Lophoc> queryD = em.createQuery(delsql,Lophoc.class);
		if(queryD.getResultList().size()!=0){
			Lophoc delLop = queryD.getSingleResult();
			try {
				trans.begin();
				em.remove(delLop);
				trans.commit();
			} catch (Exception e) {
				trans.rollback();
			}
			System.out.println("deleted: "+delLop);
		}
		else
			System.out.println("nothing to delete");*/
		String q = "select l from Lophoc l";
		TypedQuery<Lophoc> query = em.createQuery(q,Lophoc.class);
		List<Lophoc> lst = query.getResultList();
		lst.stream().forEach((l)->{System.out.println(l);});

		emf.close();
	}
}
