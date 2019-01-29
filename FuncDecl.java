package parser;
import main.CodeFile;
import main.Library;
import main.Main;
import scanner.Scanner;
import scanner.TokenKind;
public class FuncDecl extends PascalDecl{
	Block b;
	Block context;
	TypeName tname;
	ParamDeclList pdeclist;
	FuncDecl next;
	public int cntpro = 0;
	FuncDecl(String id,int n) {
		super(id,n);
		
	}
	public String identify(){
		return "<FuncDecl> " + name + " on line " + lineNum;
	}
	
	public static FuncDecl parse(Scanner s){
		enterParser("func decl");
		s.skip(TokenKind.functionToken);
		FuncDecl fd = new FuncDecl(s.curToken.id.toUpperCase(),s.curLineNum());
		
		s.skip(TokenKind.nameToken);
		
		//det sjekker om param decl list
		if(s.curToken.kind == TokenKind.leftParToken){
			fd.pdeclist = ParamDeclList.parse(s);
		}
		s.skip(TokenKind.colonToken);
		fd.tname = TypeName.parse(s);
		s.skip(TokenKind.semicolonToken);
		fd.b = Block.parse(s);
		
		s.skip(TokenKind.semicolonToken);
		
		
		leaveParser("func decl");
		return fd;
	}
	
	 public void prettyPrint(){
		 Main.log.prettyPrint("function " + this.name );
		 if(this.pdeclist != null) this.pdeclist.prettyPrint();
		 Main.log.prettyPrint(":");
		 tname.prettyPrint();
		 Main.log.prettyPrint("; \n");
		 b.prettyPrint();
		 Main.log.prettyPrint("; \n");
	}
	
	void checkWhetherAssignable(PascalSyntax where) {
		
	}
	
	void checkWhetherFunction(PascalSyntax where) {
		
	}
	
	void checkWhetherProcedure(PascalSyntax where) {
		where.error("is a function, not a procedure.");
	}
	
	void checkWhetherValue(PascalSyntax where) {
		
	}
	//det sjekker  paramdecl --> typename -->block .
	@Override
	void check(Block curScope, Library lib) {
		ParamDecl tmp = pdeclist.pdlist;
				
				for(;tmp != null; tmp = tmp.next){
					//System.out.println("her      "+tmp.name);
					tmp.check(curScope,lib);
					b.addDecl(tmp.name,tmp);//block
					cntpro++;
				}
				curScope.addDecl(name,this);//function 
				tname.check(curScope, lib);
					
					/*for(String p :curScope.decls.keySet()){
						System.out.println(p);
					}*/
				
				type = tname.type;
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
