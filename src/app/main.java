package app;

import model.dao.DaoFactory;
import model.dao.SellerDao;
import model.dao.impl.SellerDaoJDBC;
import model.entites.Seller;

public class main {

	public static void main(String[] args) {


		SellerDao sd = DaoFactory.createSellerDao();
		
		Seller sl = sd.findById(3); 
		System.out.println(sl.toString());

	}

}
