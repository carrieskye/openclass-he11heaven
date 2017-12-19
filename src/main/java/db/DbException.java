package db;

public class DbException extends RuntimeException {

	public DbException(String msg) {
		super(msg);
	}
	
	public DbException(String msg, Exception e) {
		super(msg, e);
	}
	
	public DbException() {
		super();
	}
}
