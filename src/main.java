import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;

import Parser.Parser;
import lexer.Lexer;
import lexer.Token;
import sLR.SLRexample;
import tareaImplementacionNoRecursiva.Ejemplo2;
import tareaImplementacionrecursiva.ArithmeticExpression;
import tareaImplementacionrecursiva.Ejemplo;


public class main {

	public static void main(String[] args) {
		/*
		Lexer scanner = new Lexer();
		System.out.print("Introducir token\n");
		try {
			System.out.print(scanner.scan());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		*/
		
		//Revisar paquete sLR
		//SLRexample.mostrarResultado();
		
		Lexer lex = new Lexer("/auxiliary/input.txt");
		
		try {
			Parser parser = new Parser(lex);
			parser.program();
			System.out.println('\n');
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
	}

}
