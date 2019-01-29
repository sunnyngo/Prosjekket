package parser;
import main.CodeFile;
import main.Library;
import main.Main;
import scanner.Scanner;
import scanner.TokenKind;

public class ProcDecl extends PascalDecl {
	Block b;
	Block context;
	ParamDeclList pdeclist;
	ProcDecl next;
	public int cntpro = 0;
	public ProcDecl(String id,int n) {
		super(id,n);
	}
	public String identify(){
		return "<procDecl> " + name + " on line " + lineNum;
	}
	public static ProcDecl parse(Scanner s){
		enterParser("proc decl");
		s.skip(TokenKind.procedureToken);
		ProcDecl pd = new ProcDecl(s.curToken.id.toUpperCase(),s.curLineNum());
		s.skip(TokenKind.nameToken);
		if(s.curToken.kind == TokenKind.leftParToken){
			pd.pdeclist = ParamDeclList.parse(s);
		}
		s.skip(TokenKind.semicolonToken);
		pd.b = Block.parse(s);
		s.skip(TokenKind.semicolonToken);
		leaveParser("proc decl");
		
		
		return pd;
	}
	
	public void prettyPrint(){
		 Main.log.prettyPrint("procedure " + this.name );
		 if(pdeclist != null){
			 pdeclist.prettyPrint();
		 }
		 Main.log.prettyPrint("; \n");
		 b.prettyPrint();
		 Main.log.prettyPrint("; \n");
	}
	
	void checkWhetherAssignable(PascalSyntax where) {
		
	}
	
	void checkWhetherFunction(PascalSyntax where) {
		where.error("is a procedure, not a function.");
	}
	
	void checkWhetherProcedure(PascalSyntax where) {
		
	}
	
	void checkWhetherValue(PascalSyntax where) {
		
	}

	@Override
	void check(Block curScope, Library lib){
		if(pdeclist != null){
			ParamDecl tmp = pdeclist.pdlist;
				for(;tmp != null; tmp = tmp.next){
					tmp.check(curScope,lib);
					b.addDecl(tmp.name,tmp);//block
					cntpro++;
				}
		}
		curScope.addDecl(name,this);//proc
		b.outerScope = curScope;
		b.check(curScope, lib);
		
	}
	@Override
	void genCode(CodeFile f) {
		if(pdeclist != null)pdeclist.genCode(f);
		b.context = this;
		b.declLevel = context.declLevel + 1;
		b.genCode(f);
	}
}
