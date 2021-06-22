package symbols;

import lexer.Tag;
import lexer.Word;

public class Type extends Word {
	public int width = 0;
	
	public Type(int tag, String s, int w) {
		super(tag, s);
		width = w;
	}
	
	public static final Type
		Number = new Type(Tag.BASIC, "number", 4),
		Char = new Type(Tag.BASIC, "char", 1),
		Bool = new Type(Tag.BASIC, "bool", 1);
}
