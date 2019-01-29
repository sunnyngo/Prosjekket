package parser;
import main.CodeFile;
import main.Library;
import main.Main;
import scanner.Scanner;
import scanner.TokenKind;
public class Expression  extends PascalSyntax{
	Expression next;
	SimpleExpr se2;
	SimpleExpr se1;
	RelOpr ro;
	types.Type type;
	Block context;
	Expression(int n) {
		super(n);
	}

	public String identify(){
		return "<Expression> "  + " on line " + lineNum;
	}
	
	public static Expression parse(Scanner s){
		enterParser("expression");
		Expression ex = new Expression(s.curLineNum());
		ex.se1 = SimpleExpr.parse(s);
		//det sjekker om =,<>,<,>,<=,>= på rel opr.
		if(s.curToken.kind == TokenKind.equalToken || s.curToken.kind == TokenKind.notEqualToken || s.curToken.kind == TokenKind.lessEqualToken ||
				s.curToken.kind == TokenKind.greaterEqualToken || s.curToken.kind == TokenKind.greaterToken|| s.curToken.kind == TokenKind.lessToken){
			ex.ro = RelOpr.parse(s);
			ex.se2 = SimpleExpr.parse(s);
		}
		
		leaveParser("expression");
		return ex;
	}
	public void prettyPrint(){
		se1.prettyPrint();
		if(ro != null){
			ro.prettyPrint();
			se2.prettyPrint();
		}
	}
	//det sjekker simpleepxr --> relopr-->simpleepxr og de to simpleepxr er like type.
	@Override
	void check(Block curScope, Library lib) {
		se1.check(curScope, lib);
		type = se1.type;
		if(ro != null){
			ro.check(curScope, lib);
			se2.check(curScope,lib);
			se1.type.checkType(se2.type,this.identify(), this,"ulike type.");
			type = lib.decls.get("BOOLEAN").type;
		}
		
	}

	@Override
	void genCode(CodeFile f) {
		se1.context = this.context;
		se1.genCode(f);
		if(ro != null){
			f.genInstr("", "pushl", "%eax", "");
			se2.context = this.context;
			se2.genCode(f);
			if(ro.tokenkind == TokenKind.equalToken){
				f.genInstr("", "popl", "%ecx", "");
				f.genInstr("", "cmpl", "%eax,%ecx", "");
				f.genInstr("", "movl", "$0,%eax", "");
				f.genInstr("", "sete", "%al", "Test "+TokenKind.equalToken);
			}else if(ro.tokenkind == TokenKind.notEqualToken){
				f.genInstr("", "popl", "%ecx", "");
				f.genInstr("", "cmpl", "%eax,%ecx", "");
				f.genInstr("", "movl", "$0,%eax", "");
				f.genInstr("", "setne", "%al", "Test "+TokenKind.notEqualToken);
			}else if(ro.tokenkind == TokenKind.lessEqualToken){
				f.genInstr("", "popl", "%ecx", "");
				f.genInstr("", "cmpl", "%eax,%ecx", "");
				f.genInstr("", "movl", "$0,%eax", "");
				f.genInstr("", "setle", "%al", "");
			}else if(ro.tokenkind == TokenKind.greaterEqualToken){
				f.genInstr("", "popl", "%ecx", "");
				f.genInstr("", "cmpl", "%eax,%ecx", "");
				f.genInstr("", "movl", "$0,%eax", "");
				f.genInstr("", "setge", "%al", "Test "+TokenKind.greaterEqualToken);
			}else if(ro.tokenkind == TokenKind.greaterToken){
				f.genInstr("", "popl", "%ecx", "");
				f.genInstr("", "cmpl", "%eax,%ecx", "");
				f.genInstr("", "movl", "$0,%eax", "");
				f.genInstr("", "setg", "%al", "Test "+TokenKind.greaterToken);
			}else{
				f.genInstr("", "popl", "%ecx", "");
				f.genInstr("", "cmpl", "%eax,%ecx", "");
				f.genInstr("", "movl", "$0,%eax", "");
				f.genInstr("", "setl", "%al", "Test <");
			}
		}
	}
	
}
