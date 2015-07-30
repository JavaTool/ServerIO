package persist;

public class DataAccessException extends RuntimeException {
	
	private static final long serialVersionUID = -5265114939314913539L;

	public DataAccessException(Throwable cause) {
		super(cause);
	}
	
	public DataAccessException(String msg) {
		super(msg);
	}
	
}
