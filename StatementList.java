package parser;

import main.CodeFile;
import main.Library;
import main.Main;
import scanner.Scanner;
import scanner.TokenKind;

public class StatementList extends PascalSyntax {
	
	Statement list;
	types.Type type;
	public Block context;
	StatementList(int n) {
		super(n);
	}

	public String identify(){
		return null;
	}
	public static StatementList parse(Scanner s){
		enterParser("statement list");
		StatementList sl = new StatementList(s.curLineNum());
		do{
			Statement ny = Statement.parse(s);
			if(sl.list == null){
				sl.list = ny;
			}else{
				Statement tmp = sl.list;
				while(tmp.next != null){
					tmp = tmp.next;
				}
				tmp.next = ny;
			}
			if(s.curToken.kind != TokenKind.semicolonToken)break;
			s.skip(TokenKind.semicolonToken);
			
		}while(true);	
		leaveParser("statment list");
		return sl;
	}
	
	public void prettyPrint(){
		 
		Statement tmp = list;
		 while(tmp != null){
			 tmp.prettyPrint();
			 tmp = tmp.next;
			 if(tmp != null){
				 Main.log.prettyPrint(";\n");
			 }
		 }
		 Main.log.prettyPrint("\n");
	}
	@Override
	void check(Block curScope, Library lib){
		Statement tmp = list;
		for(;tmp != null;tmp = tmp.next){
			tmp.check(curScope, lib);
		}
		
	}

	@Override
	void genCode(CodeFile f) {
		Statement tmp = list;
		for(;tmp!=null;tmp = tmp.next){
			tmp.context = this.context;
			tmp.genCode(f);
		}
	}
}
