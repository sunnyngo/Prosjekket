package parser;
import main.CodeFile;
import main.Library;
import main.Main;

import scanner.Scanner;
import scanner.TokenKind;
public class Constant extends PascalSyntax{
	
	Constant next;
	PrefixOpr pfo;
	public UnsignedConstant unsigC;
	types.Type type;
	public Constant(int n) {
		super(n);
	}
	public String identify(){
		return "<Constant> " + unsigC.name + " on line " + lineNum;
	}
	
	public static Constant parse(Scanner s){
		enterParser("constant");
		Constant c = new Constant(s.curLineNum());
		//det sjekker at pluss eller minus.
		if(s.curToken.kind == TokenKind.addToken || s.curToken.kind == TokenKind.subtractToken){
			c.pfo = PrefixOpr.parse(s);
		}
		c.unsigC = UnsignedConstant.parse(s);
		leaveParser("constant");
		return c;
	}
	public void prettyPrint(){
		 if(pfo != null){
			 pfo.prettyPrint();
		 }
		 unsigC.prettyPrint();
	}
	//det sjekker prefixopr-->UnsignedConstant og sjekker type.
	@Override
	void check(Block curScope, Library lib){
		if(pfo != null)pfo.check(curScope, lib);
		unsigC.check(curScope,lib);
		type = unsigC.type;
	}
	@Override
	void genCode(CodeFile f) {
		// TODO Auto-generated method stub
		
	}
}
