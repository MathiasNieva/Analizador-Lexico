package lexer;

public class Word extends Token {
	public final String lexema;
	
	public Word(int tag, String s) {
		super(tag);
		lexema = new String(s);
	}

	@Override
	public String toString() {
		return "Word [lexema=" + lexema + ", Tag=" + tag + "]";
	}
	
	public static final Word 
		and = new Word(Tag.AND, "and"), or = new Word(Tag.OR, "or"),
		eq = new Word( Tag.EQUAL, "==" ), diff = new Word( Tag.DIFF, "!=" ),
		lessEq = new Word( Tag.LESSER_EQ, "<=" ), greatEq = new Word( Tag.GREATER_EQ, ">=" ),
		True = new Word( Tag.TRUE, "true" ), False = new Word( Tag.FALSE, "false" ),
		atune = new Word(Tag.ATUNE, "Atune"), rhythm = new Word(Tag.RHYTHM, "Rhythm"),
		of = new Word(Tag.OF, "Of"), antici = new Word(Tag.ANTICIPATION, "Anticipation"),
		consid = new Word(Tag.CONSIDERATION, "Consideraton"), glyph = new Word(Tag.GLYPH, "Glyph"),
		begin = new Word(Tag.BEGIN, "Begin"), end = new Word(Tag.END, "End");
}
