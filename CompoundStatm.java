package parser;
import main.CodeFile;
import main.Library;
import main.Main;

import scanner.Scanner;
import scanner.TokenKind;
public class CompoundStatm extends Statement {
	StatementList slist;
	CompoundStatm(int n) {
		super(n);
	}
	public String identify(){
		return "<compound statm> " + " on line " + lineNum;
	}
	public static CompoundStatm parse(Scanner s){
		enterParser("compound statm");
		CompoundStatm cs = new CompoundStatm(s.curLineNum());
		s.skip(TokenKind.beginToken);
		cs.slist = StatementList.parse(s);
		s.skip(TokenKind.endToken);
		enterParser("compound statm");
		return cs;
	}
	
	public void prettyPrint() {
	     Main.log.prettyPrint(" begin\n ");
	     slist.prettyPrint();
	     Main.log.prettyPrint(" end");
	}
	
	@Override
	void check(Block curScope, Library lib)  {
		slist.check(curScope, lib);

	}
	@Override
	void genCode(CodeFile f) {
		slist.context = this.context;
		slist.genCode(f);
	}
	
	
}
