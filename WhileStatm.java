package parser;
import main.CodeFile;
import main.Library;
import main.Main;
import scanner.Scanner;
import scanner.TokenKind;
public class WhileStatm extends Statement {
	Statement s;
	Expression ex;
	
	WhileStatm(int n) {
		super(n);
	}
	
	public String identify(){
		return "<While> " + " on line " + lineNum;
	}
	public static WhileStatm parse(Scanner s){
		enterParser("while-statm");
		WhileStatm ws = new WhileStatm(s.curLineNum());
		s.skip(TokenKind.whileToken);
		ws.ex = Expression.parse(s);
		s.skip(TokenKind.doToken);
		ws.s = Statement.parse(s);
		leaveParser("while-statm");
		return ws;
	}
	
	public void prettyPrint(){
		 Main.log.prettyPrint("while ");  
		 ex.prettyPrint();
	     Main.log.prettyPrintLn(" do"); 
	     s.prettyPrint(); 
	}

	@Override
	void check(Block curScope, Library lib) {
		ex.check(curScope, lib);
		ex.type.checkType(lib.decls.get("BOOLEAN").type,this.identify() , this, "while-test is not boolean.");
		s.check(curScope, lib);
	}

	@Override
	void genCode(CodeFile f) {
		String testLabel = f.getLocalLabel(),
               endLabel  = f.getLocalLabel();
        f.genInstr(testLabel, "", "", "Start while-statement");
        ex.context = this.context;
        ex.genCode(f);
        f.genInstr("", "cmpl", "$0,%eax", "");
        f.genInstr("", "je", endLabel, "");
        s.context = this.context;
        s.genCode(f);
        f.genInstr("", "jmp", testLabel, "");
        f.genInstr(endLabel, "", "", "End while-statement");
		
	}
}
