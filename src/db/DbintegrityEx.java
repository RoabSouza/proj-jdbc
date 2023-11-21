package db;

public class DbintegrityEx extends RuntimeException {
	
	private static final long serialVersionUID = 1L; 
	
	public DbintegrityEx(String msg) {
		super(msg);
	}

}
