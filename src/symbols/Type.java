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
		Number = new Type(Tag.BASIC, "number", 8),
		Char = new Type(Tag.BASIC, "char", 1),
		Bool = new Type(Tag.BASIC, "bool", 1);
	
	public static boolean numeric(Type p) {
		if (p == Type.Char || p == Type.Number) return true;
		else return false;
	}
	
	public static Type max(Type p1, Type p2) {
		if ( !numeric(p1) || !numeric(p2) ) return null;
		else if ( p1 == Type.Number || p2 == Type.Number ) return Type.Number;
		else return Type.Char;
	}
}
