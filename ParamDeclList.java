package parser;
import main.CodeFile;
import main.Library;
import main.Main;
import scanner.Scanner;
import scanner.TokenKind;
public class ParamDeclList extends PascalSyntax{
	ParamDecl pdlist;
	public int countParam = 0;
	Block context;
	ParamDeclList(int n) {
		super(n);
	}
	public String identify(){
		return "<paramDecllist> " + pdlist.name + " on line " + lineNum;
	}

	public static ParamDeclList parse(Scanner s){
		enterParser("param decl list");
		ParamDeclList pdl = new ParamDeclList(s.curLineNum());
		s.skip(TokenKind.leftParToken);
		do{
			//hvis piler kom tilbake og møte ";". Det blir skip.
			if(s.curToken.kind == TokenKind.semicolonToken){
				s.skip(TokenKind.semicolonToken);
			}
			ParamDecl nyPar = ParamDecl.parse(s);
			if(pdl.pdlist == null){
				pdl.pdlist = nyPar;
			}else{
				ParamDecl pdl_tmp = pdl.pdlist;
				while(pdl_tmp.next != null){
					pdl_tmp = pdl_tmp.next;
					pdl.countParam++;// ny 
				}
				pdl_tmp.next = nyPar;
			}
			//hvis det har ikke flere ";" .Det slutter  While_loop
		}while(s.curToken.kind == TokenKind.semicolonToken);  
		s.skip(TokenKind.rightParToken);
		leaveParser("param decl list");
		return pdl;
	}
	 public void prettyPrint(){
		 Main.log.prettyPrint("(");
		 ParamDecl tmp = pdlist;
		 do{
			 tmp.prettyPrint();
			 tmp = tmp.next;
			 if(tmp == null)break;
			 Main.log.prettyPrint(";");
		 }while(true);
		 Main.log.prettyPrint(")");
	}
	
	 @Override
		void check(Block curScope, Library lib) {
		ParamDecl tmp = pdlist;
		/*ParamDeclList pdl = null;
		int cnt = countList(tmp);
		if(pdl.countParam>= 0){
			if(cnt > pdl.countParam){
				Main.error("Too few parameters in call on p!");
			}
			if(cnt < pdl.countParam){
				Main.error("Too many parameters in call on p!");
			}
		}*/
		for(;tmp != null;tmp = tmp.next){
			tmp.check(curScope, lib);
		}
	}
	public int countList(ParamDecl pd){
		int count = 0;
		ParamDecl tmp = pd;
		for(;tmp != null;tmp = tmp.next){
			count++;
		}
		return count;
	}
	@Override
	void genCode(CodeFile f) {
		int antall = 0;
		ParamDecl tmp = pdlist;
		for(;tmp != null;tmp = tmp.next){
			tmp.declOffset = 8 +antall*(4);
			antall++;
		}
	}
}
