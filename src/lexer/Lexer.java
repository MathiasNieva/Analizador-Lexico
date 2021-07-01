package lexer;
import java.io.*;
import java.util.*;

import symbols.Type;

public class Lexer {
	public static int line = 1;
	private char peek = ' ';
	private Hashtable words = new Hashtable();
	
	private File file;
	private BufferedReader br;
	
	private void reserve(Word t) {
		words.put(t.lexema, t);
	}
	
	public Lexer(String path) {
		reserve(Word.and);
		reserve(Word.or);
		reserve(Word.True);
		reserve(Word.False);
		reserve(Word.atune);
		reserve(Word.of);
		reserve(Word.rhythm);
		reserve(Word.antici);
		reserve(Word.consid);
		reserve(Word.Else);
		reserve(Word.Break);
		reserve(Type.Char);
		reserve(Type.Number);
		reserve(Type.Bool);
		
		file = new File(getClass().getResource(path).getFile());
		try {
			br = new BufferedReader(new FileReader(file));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void readChar() throws IOException {
		//peek = (char)System.in.read();
		peek = (char)br.read();
		
	}
	
	boolean readChar(char c) throws IOException {
		readChar();
		if (peek != c) return false;
		peek = ' ';
		return true;
	}
	
	public Token scan() throws IOException {
		for(;; readChar()) {
			if (peek == ' ' || peek == '\t') continue;
			else if (peek == '\r') {
				readChar();
				if (peek == '\n') line = line + 1;
				else break;
			}
			else break;
		}
		
		switch (peek) {
		case '=':
			if (readChar('=')) return Word.eq; else return new Token('=');
		case '!':
			if (readChar('=')) return Word.diff; else return new Token('!');
		case '<':
			if (readChar('=')) return Word.lessEq; else return new Token('<');
		case '>':
			if (readChar('=')) return Word.greatEq; else return new Token('>');
		}
		
		if (Character.isDigit(peek)) {
			int v = 0;
			do {
				v = 10*v + Character.digit(peek, 10);
				readChar();
			} while (Character.isDigit(peek));
			if (peek == '.') {                       //retornar un token normal si no????
				float x = v; float d = 10;
				for (;;) {
					readChar();
					if (!Character.isDigit(peek)) break;
					x = x + Character.digit(peek, 10) / d; d = d*10;
				}
				return new Num(x);
			}
		}
		
		if (Character.isLetter(peek)) {
			StringBuffer b = new StringBuffer();
			do {
				b.append(peek);
				readChar();;
			} while (Character.isLetterOrDigit(peek));
			String s = b.toString();
			Word w = (Word)words.get(s);
			if (w != null) return w;
			w = new Word(Tag.ID, s);
			words.put(s, w);
			return w;
		}
		
		Token t = new Token(peek);
		peek = ' ';
		return t;
	}
}
