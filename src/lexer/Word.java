package lexer;

public class Word extends Token {
	public final String lexema;
	
	public Word(int tag, String s) {
		super(tag);
		lexema = new String(s);
	}

	@Override
	public String toString() {
		return lexema;
	}
	
	public static final Word 
		and = new Word(Tag.AND, "and"), or = new Word(Tag.OR, "or"),
		eq = new Word( Tag.EQUAL, "==" ), diff = new Word( Tag.DIFF, "!=" ),
		lessEq = new Word( Tag.LESSER_EQ, "<=" ), greatEq = new Word( Tag.GREATER_EQ, ">=" ),
		True = new Word( Tag.TRUE, "true" ), False = new Word( Tag.FALSE, "false" ),
		atune = new Word(Tag.ATUNE, "Atune"), rhythm = new Word(Tag.RHYTHM, "Rhythm"),
		of = new Word(Tag.OF, "Of"), antici = new Word(Tag.ANTICIPATION, "Anticipation"),
		consid = new Word(Tag.CONSIDERATION, "Consideration"),
		temp = new Word(Tag.TEMP, "t"), Else = new Word(Tag.ELSE, "else"), Break = new Word(Tag.BREAK, "break");
}
