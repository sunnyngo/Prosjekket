package parser;
import main.CodeFile;
import main.Library;
import main.Main;
import scanner.Scanner;
import scanner.TokenKind;
public class ConstDeclPart extends  PascalSyntax {
	ConstDecl cdlist;
	Program consname;
	ConstDeclPart(int n){
		super(n);
	}
	public String identify(){
		return "<const Decl part> " + " on line " + lineNum;
	}
	public static ConstDeclPart parse(Scanner s){
		enterParser("const decl part");
		ConstDeclPart cdp = new ConstDeclPart(s.curLineNum());
		s.skip(TokenKind.constToken);
		//det sjekker av const decl og lenkeliste
		while(s.curToken.kind == TokenKind.nameToken && s.nextToken.kind == TokenKind.equalToken){
			ConstDecl ny = ConstDecl.parse(s);
			//Program.consname.add(ny);
			if(cdp.cdlist == null){
				cdp.cdlist = ny;
			}else{
				ConstDecl cdp_tmp = cdp.cdlist;
				while(cdp_tmp.next != null){
					cdp_tmp = cdp_tmp.next;
				}
				cdp_tmp.next = ny;
			}
		}
		leaveParser("const decl part");
		return cdp;
	}
	
	public void prettyPrint(){
		 Main.log.prettyPrint("const ");
		 ConstDecl tmp = cdlist;
		 while(tmp != null){
			 tmp.prettyPrint();
			 tmp = tmp.next;
		 }
		 Main.log.prettyPrint("\n");
	}
	//det sjekker constdecl list.
	@Override
	void check(Block curScope, Library lib) {
		ConstDecl tmp = cdlist;
		for(;tmp != null;tmp = tmp.next){
			tmp.check(curScope, lib);
			//System.out.println("her : "+tmp.name);
			curScope.addDecl(tmp.name, tmp);
		}
	}
	@Override
	void genCode(CodeFile f) {
		
	}
}
