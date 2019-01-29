package parser;
import main.CodeFile;
import main.Library;
import main.Main;
import scanner.Scanner;
import scanner.TokenKind;
public class SimpleExpr extends PascalSyntax {
	PrefixOpr pOpr;
	TermOpr tlist;
	Term termlist;
	types.Type type;
	Block context;
	SimpleExpr(int n) {
		super(n);
		
	}
	public String identify(){
		return "<simple expr> "  + " on line " + lineNum;
	}
	public static SimpleExpr parse(Scanner s){
		enterParser("simple exp");
		SimpleExpr se = new SimpleExpr(s.curLineNum());
		if(s.curToken.kind == TokenKind.addToken || s.curToken.kind == TokenKind.subtractToken){
			se.pOpr = PrefixOpr.parse(s);
		}
		do{
			//jeg følger jernbanediagram. jeg definer to lenkelist av term og term opr.  
			Term ny = Term.parse(s);
			if(se.termlist == null){
				se.termlist = ny;
			}else{
				Term tmp = se.termlist;
				while(tmp.next != null){
					tmp = tmp.next;
				}
				tmp.next = ny;
			}
			//det sjekker term opr ("+","-","or")
			if(!(s.curToken.kind == TokenKind.addToken || s.curToken.kind == TokenKind.subtractToken || s.curToken.kind == TokenKind.orToken))
				break;
			TermOpr ny2 = TermOpr.parse(s);
			if(se.tlist == null){
				se.tlist = ny2;
			}else{
				TermOpr tmp2 = se.tlist;
				while(tmp2.next != null){
					tmp2 = tmp2.next;
				}
				tmp2.next = ny2;
			}
		}while(true);
		leaveParser("simple exp");
		return se;
		
	}
	public void prettyPrint(){
		if(pOpr != null){
			pOpr.prettyPrint();
		}
		Term tmp1 = termlist;
		TermOpr tmp2 = tlist;
		while(tmp1 != null){
			tmp1.prettyPrint();
			tmp1 = tmp1.next;
			if(tmp2 != null){
				tmp2.prettyPrint();
				tmp2 = tmp2.next;
			}
		}
	}
	@Override
	void check(Block curScope, Library lib){
		if(pOpr != null){
			pOpr.check(curScope, lib);
		}
		Term tmp1 = termlist;
		TermOpr tmp2 = tlist;
		while(tmp1 != null){
			tmp1.check(curScope, lib);
			
			if(tmp2 != null){
				tmp2.check(curScope, lib);
				//tmp1.type.checkType(lib.decls.get("INTEGER").type, this.identify(), this, "Left operand to + is not a number! ");
				tmp2 = tmp2.next;
			}
			tmp1 = tmp1.next;
		}	
		type = termlist.type;
	}
	@Override
	void genCode(CodeFile f) {
		
		Term term1 = termlist;
		TermOpr termOpr = tlist;
		while(term1 != null){
			term1.context = this.context;
			term1.genCode(f);
			if(pOpr != null){
				pOpr.genCode(f);
				pOpr = null;
			}
			Term term2 = term1.next;
			if(termOpr != null){
				term2.context = this.context;
				if(termOpr.tokenKind == TokenKind.addToken){
					f.genInstr("", "pushl", "%eax", "");
					term2.genCode(f);
					f.genInstr("", "movl", "%eax,%ecx", "");
					f.genInstr("", "popl", "%eax", "");
					f.genInstr("", "addl", "%ecx,%eax", "+");
				}else if(termOpr.tokenKind == TokenKind.subtractToken){
					f.genInstr("", "pushl", "%eax", "");
					term2.genCode(f);
					f.genInstr("", "movl", "%eax,%ecx", "");
					f.genInstr("", "popl", "%eax", "");
					f.genInstr("", "subl", "%ecx,%eax", "-");
				}else if(termOpr.tokenKind == TokenKind.orToken){
					f.genInstr("", "pushl", "%eax", "");
					term2.genCode(f);
					f.genInstr("", "movl", "%eax,%ecx", "");
					f.genInstr("", "popl", "%eax", "");
					f.genInstr("", "orl", "%ecx,%eax", "or");
				}
				termOpr = termOpr.next;
			}
			term1 = term1.next;
			if(term1 != null&& term1.next == null)break;// du kan ta bort den linje. Når kjøre du på easter.pas
		}	
	}
	
}
