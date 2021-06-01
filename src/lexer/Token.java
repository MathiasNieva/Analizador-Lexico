package lexer;

public class Token {
	public final int tag; //can't be change once set
	
	public Token(int t) {
		tag = t;
	}

	@Override
	public String toString() {
		return "Token [tag=" + tag + "]";
	}
	
}
