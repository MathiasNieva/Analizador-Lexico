import java.io.IOException;

import lexer.Lexer;
import lexer.Num;
import lexer.Tag;
import lexer.Token;
import lexer.Word;

public class main {

	public static void main(String[] args) {
		
		Lexer scanner = new Lexer();
		System.out.print("Introducir token\n");
		try {
			System.out.print(scanner.scan());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
