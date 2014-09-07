import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.List;
import java.util.Date;
import java.util.Iterator;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.pentalog.nguzun.vo.User;



public class hibernate {

	private static SessionFactory factory;

	public static void main(String[] args) {
		try {
			factory = new Configuration().configure().buildSessionFactory();
		} catch (Throwable ex) {
			System.err.println("Failed to create sessionFactory object. " + ex);
			throw new ExceptionInInitializerError(ex);
		}
		hibernate ME = new hibernate();

		Session session = factory.openSession();
	      Transaction tx = null;
	      try{
	         tx = session.beginTransaction();
	         List employees = session.createQuery("FROM User").list(); 
	         for (Iterator iterator = 
	                           employees.iterator(); iterator.hasNext();){
	            User employee = (User) iterator.next(); 
	            System.out.print("First Name: " + employee.getId()); 
	            System.out.print("  Last Name: " + employee.getName()); 
	            System.out.println("  Salary: " + employee.getLogin());
	            System.out.println("  Salary: " + employee.getPassword());
	            System.out.println("  Group: " + employee.getGroup().getName());
	         }
	         tx.commit();
	      }catch (HibernateException e) {
	         if (tx!=null) tx.rollback();
	         e.printStackTrace(); 
	      }finally {
	         session.close(); 
	      }

	}

}
