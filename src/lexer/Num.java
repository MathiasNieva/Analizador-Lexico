package lexer;

public class Num extends Token {
	public final float value;
	
	public Num(float v) {
		super(Tag.NUM);
		value = v;
	}

	@Override
	public String toString() {
		return ""+value;
	}
	
}
