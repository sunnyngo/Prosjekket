package parser;
import scanner.TokenKind;
import main.CodeFile;
import main.Library;
import main.Main;
import scanner.Scanner;
public class FactorOpr extends Statement{
	TokenKind tokenKind;
	FactorOpr next;
	FactorOpr(int n) {
		super(n);
		
	}
	public String identify(){
		return "<Factor Opr> " +" on line " + lineNum;
	}

	public static FactorOpr parse(Scanner s){
		enterParser("factor opr");
		FactorOpr fo = new FactorOpr(s.curLineNum());
		fo.tokenKind = s.curToken.kind;
		s.skip(s.curToken.kind);
		
		leaveParser("factor opr");
		return fo;
	}
	public void prettyPrint() {
		 Main.log.prettyPrint(this.tokenKind+"");
		
	}
	
	@Override
	void check(Block curScope, Library lib) {
		
	}
	@Override
	void genCode(CodeFile f) {
		
	}
	
}
