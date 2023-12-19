package app;

import java.sql.Date;
import java.util.List;
import java.util.Scanner;

import model.dao.DaoFactory;
import model.dao.SellerDao;
import model.entites.Department;
import model.entites.Seller;

public class main {

	private static Seller Seller;

	public static void main(String[] args) {

		SellerDao sd = DaoFactory.createSellerDao();
		Scanner sc = new Scanner(System.in);

		System.out.println("=== test 1: seller finbyID ===");
		Seller sl = sd.findById(3);

		System.out.println(sl);

		System.out.println("=== test 2: seller finbyDepartmen ===");
		Department dp = new Department(3, null);
		List<Seller> list = sd.findByDepartment(dp);
		for (Seller obj : list) {
			System.out.println(obj);
		}

		System.out.println("=== test 3: seller finbyAll ===");
		list = sd.findAll();
		for (Seller obj : list) {
			System.out.println(obj);
		}

		System.out.println("=== test 4: seller Insert ===");
		Seller newSeller = new Seller(null, "Greg", "greg@gmail.com", new Date(10, 2, 1992), 4000.0, dp);
//		sd.insert(newSeller);
		System.out.println(newSeller.getId() + " inserido no banco");

		System.out.println("=== test 5: seller update");
		Seller = sd.findById(5);
		Seller.setName("Marta");
		sd.update(Seller);
		System.out.println("update");

		System.out.println("=== test 6: seller Delete");
		System.out.println("entre com o a id");
		int id = sc.nextInt();
		sd.deleteByid(id);
		System.out.println("Delete");
		sc.close();

	}

}
