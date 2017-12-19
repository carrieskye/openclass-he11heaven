package domain;

public class DomainException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public DomainException() {
		super();
	}

	public DomainException(String string) {
		super(string);

	}

}
