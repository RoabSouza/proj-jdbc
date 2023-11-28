package app;

import java.util.List;

import model.dao.DaoFactory;
import model.dao.SellerDao;
import model.entites.Department;
import model.entites.Seller;

public class main {

	public static void main(String[] args) {


		SellerDao sd = DaoFactory.createSellerDao();
		
		
		System.out.println("=== test 1: seller finbyID ===");
		Seller sl = sd.findById(3); 
		
		System.out.println(sl);
		
		System.out.println("=== test 2: seller finbyDepartmen ===");
		Department dp = new Department(3,null);
		List<Seller> list = sd.findByDepartment(dp);
		
		for(Seller obj : list ) {
			System.out.println(list);
		}

	}

}
