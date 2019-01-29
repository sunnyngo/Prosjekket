package parser;
import main.CodeFile;
import main.Library;
import main.Main;
import scanner.Scanner;
import scanner.TokenKind;
public class InnerExpr extends Factor{
	Expression ex;
	InnerExpr(int n) {
		super(n);
	}
	public String identify(){
		return "<inner expr> "  + " on line " + lineNum;
	}
	public static InnerExpr parse(Scanner s){
		enterParser("inner expr");
		InnerExpr ie = new InnerExpr(s.curLineNum());
		s.skip(TokenKind.leftParToken);
		ie.ex = Expression.parse(s);
		s.skip(TokenKind.rightParToken);
		leaveParser("inner expr");
		return ie;
	}
	
	public void prettyPrint() {
		Main.log.prettyPrint("(");
		ex.prettyPrint();
		Main.log.prettyPrint(")");
		
	}
	
	@Override
	public void check(Block curScope, Library lib) {
		ex.check(curScope, lib);
		type = ex.type;
	}
	@Override
	void genCode(CodeFile f) {
		ex.context = this.context;
		ex.genCode(f);// new code
	}
}
