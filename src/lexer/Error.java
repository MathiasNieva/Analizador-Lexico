package lexer;

public class Error extends Token {
	
	public Error() {
		super(Tag.ERROR);
	}

	@Override
	public String toString() {
		return "Error";
	}
	
}