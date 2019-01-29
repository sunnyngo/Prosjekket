package parser;
import main.CodeFile;
import main.Library;
import main.Main;
import scanner.Scanner;
import scanner.TokenKind;
public class IfStatm extends Statement{
	Expression excall;
	Statement strue;
	Statement sfasle;
	
	IfStatm(int n) {
		super(n);
	}
	public String identify(){
		return "<if statm> "  + " on line " + lineNum;
	}

	public static IfStatm parse(Scanner s){
		enterParser("if-statm");
		IfStatm is = new IfStatm(s.curLineNum());
		s.skip(TokenKind.ifToken);
		is.excall = Expression.parse(s);
		s.skip(TokenKind.thenToken);
		is.strue = Statement.parse(s);
		if(s.curToken.kind == TokenKind.elseToken){
			s.skip(TokenKind.elseToken);
			is.sfasle = Statement.parse(s);
		}
		leaveParser("if-statm");
		return is;
	}
	public void prettyPrint(){
		Main.log.prettyPrint("if ");
		excall.prettyPrint();
		Main.log.prettyPrint(" then ");
		strue.prettyPrint();
		if(sfasle != null){
			Main.log.prettyPrint(" else ");
			sfasle.prettyPrint();
		}
	}
	//det sjekker expression --> statment-->statment.
	@Override
	void check(Block curScope, Library lib) {
		excall.check(curScope, lib);
		strue.check(curScope, lib);
		if(sfasle != null)sfasle.check(curScope, lib);
		excall.type.checkType(lib.decls.get("BOOLEAN").type,this.identify(), this,"if-test is not boolean.");
	}
	@Override
	void genCode(CodeFile f) {
		excall.context = this.context;
		strue.context = this.context;
		//hente lab true og false
		String sTrue = f.getLocalLabel();
		String sFasle = null;
		
		if(sfasle != null){
			sFasle = f.getLocalLabel();
			sfasle.context = this.context;
		}
		f.genInstr("", "", "", "Start if-statement");
		excall.genCode(f);
		f.genInstr("", "cmpl", "$0,%eax", "");
		f.genInstr("", "je", ""+sTrue, "");
		strue.genCode(f);
		if(sfasle != null)f.genInstr("", "jmp", ""+sFasle, "");
		f.genInstr(sTrue, "", "","");
		if(sfasle != null){
			sfasle.genCode(f);
			f.genInstr(sFasle, "", "", "");
			
		}
		f.genInstr("", "", "","End if-statement");
	}
	
}
