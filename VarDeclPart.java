package parser;

import main.CodeFile;
import main.Library;
import main.Main;
import scanner.Scanner;
import scanner.TokenKind;

public class VarDeclPart extends PascalSyntax {
	VarDecl varList;
	public int antall = 0;
	VarDeclPart(int n){
		super(n);
	}
	
	public String identify(){
		return "<varDecl> " + " on line " + lineNum;
	}
	
	public static VarDeclPart parse(Scanner s){
		enterParser("var decl part");
		VarDeclPart vdp = new VarDeclPart(s.curLineNum());
		s.skip(TokenKind.varToken);
		//var decl
		while(s.curToken.kind == TokenKind.nameToken && s.nextToken.kind == TokenKind.colonToken){
			VarDecl nyVar = VarDecl.parse(s);
			if(vdp.varList == null){
				vdp.varList = nyVar;
			}else{
				VarDecl vdp_tmp = vdp.varList;
				while(vdp_tmp.next != null){
					vdp_tmp = vdp_tmp.next;
				}
				vdp_tmp.next = nyVar;
			}
		}
		leaveParser("var decl part");
		return vdp;
	}
	public void prettyPrint(){
		 Main.log.prettyPrint("var ");
		 VarDecl tmp = varList;
		 while(tmp != null){
			 tmp.prettyPrint();
			 tmp = tmp.next;
		 }
		 Main.log.prettyPrint("\n");
	}

	@Override
	void check(Block curScope, Library lib){
		VarDecl tmp = varList;
		for(;tmp != null;tmp = tmp.next){
			tmp.check(curScope, lib);
			curScope.addDecl(tmp.name, tmp);
		}
	}

	@Override
	void genCode(CodeFile f) {
		VarDecl tmp = varList;
		for(;tmp != null;tmp = tmp.next){
			tmp.declOffset = -36+antall*(4);
			antall++;
		}
	}
}
