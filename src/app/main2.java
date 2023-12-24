package app;

import java.util.List;
import java.util.Scanner;

import model.dao.DaoFactory;
import model.dao.DepartmentDao;
import model.entites.Department;
import model.entites.Seller;

public class main2 {

	private static Seller Seller;

	public static void main(String[] args) {

		DepartmentDao sd = DaoFactory.createDepartmentDao();
		Department dp = new Department();
		Scanner sc = new Scanner(System.in);

		System.out.println("=== test 1: Department Insert ===");
		dp = new Department(5, "Mesas");
		sd.insert(dp);
		System.out.println(dp.getId() + " insert in bank");

		System.out.println("=== test 2: Department FindAll ===");
		dp = sd.findById(5);
		System.out.println(dp);

		System.out.println("=== test 3: Department Delete ===");
		int id = 5;
		sd.deleteByid(id);

		System.out.println(id + "= Delete");

		System.out.println("=== test 4: Department FindAll ===");

		List<Department> list = sd.findAll();
		for (Department obj : list) {
			System.out.println(obj);
		}

	}

}
