package sLR;

import java.util.LinkedList;
import java.util.Queue;

import lexer.Token;

public class SLRexample {
	public static void mostrarResultado() {
		Queue<Token> tokens = new LinkedList<>();
		tokens.add(new Token(257));
		tokens.add(new Token(42));
		tokens.add(new Token(257));
		tokens.add(new Token(43));
		tokens.add(new Token(257));
		tokens.add(new Token(36));
		// 257=id, 42=*, 43=+, 36=$
		
		Parser parser = new Parser(tokens);
		System.out.println(parser.analyse());
		
	}

}
