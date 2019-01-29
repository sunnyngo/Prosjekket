package parser;
import main.CodeFile;
import main.Library;
import main.Main;
import scanner.Scanner;
import scanner.TokenKind;
public class Negation extends Factor{
	Factor fa;
	Negation(int n) {
		super(n);
	}
	public String identify(){
		return null;
	}

	public static Negation parse(Scanner s){
		enterParser("negation");
		Negation n = new Negation(s.curLineNum());
		s.skip(TokenKind.notToken);
		n.fa = Factor.parse(s);
		leaveParser("negation");
		return n;
		
	}
	public void prettyPrint() {
		Main.log.prettyPrint(" not ");
		fa.prettyPrint();
	}
	
	@Override
	public void check(Block curScope, Library lib)  {
		fa.check(curScope, lib);
		type = fa.type;
	}
	@Override
	void genCode(CodeFile f) {
		fa.context = this.context;
		fa.genCode(f);
		f.genInstr("", "xorl", "$0x1,%eax", "not");
	}
}
