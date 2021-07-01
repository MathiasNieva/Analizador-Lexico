package symbols;

import java.util.Hashtable;

import intercode.Id;
import lexer.Token;

public class Tables {
	private Hashtable table;
	protected Tables prev;
	
	public Tables(Tables p) {
		table = new Hashtable();
		prev = p;
	}
	
	public void put(Token w, Id i) {
		table.put(w, i);
	}
	
	public Id get(Token w) {
		for(Tables t = this; t != null; t = t.prev) {
			Id found = (Id)(t.table.get(w));
			if (found != null) return found;
		}
		return null;
	}
}
