package parser;
import main.CodeFile;
import main.Library;
import main.Main;
import scanner.Scanner;
import scanner.TokenKind;
public class Term extends Statement {
	FactorOpr list;
	Term next;
	Factor factor;
	types.Type type;
	
	Term(int n) {
		super(n);
		
	}
	public String identify(){
		return "<term> " + " on line " + lineNum;
	}
	//jeg definerer to lenkelister av term og factor opr 
	public static Term parse(Scanner s){
		enterParser("term");
		Term t = new Term(s.curLineNum());
		do{
			Factor ny = Factor.parse(s);
			if(t.factor == null){
				t.factor = ny;
			}else{
				Factor tmp = t.factor;
				while(tmp.next != null)tmp = tmp.next;
				tmp.next = ny;
			}
			//det sjekker om factor opr ("*","div","mod","and")
			if(!(s.curToken.kind == TokenKind.multiplyToken || s.curToken.kind == TokenKind.divToken || s.curToken.kind == TokenKind.modToken|| s.curToken.kind == TokenKind.andToken))
			break;
			FactorOpr ny2 = FactorOpr.parse(s);
			if(t.list == null){
				t.list = ny2;
			}else{
				FactorOpr tmp2 = t.list;
				while(tmp2.next != null)tmp2 = tmp2.next;
				tmp2.next = ny2;
			}
			
		}while(true);
		leaveParser("term");
		return t;
	}
	public void prettyPrint(){
		 
		Factor tmp1 = factor;
		FactorOpr tmp2 = list;
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
	void check(Block curScope, Library lib) {
		if(factor instanceof Variable){
			Variable va  = (Variable)factor;
			PascalDecl pd = curScope.findDecl(va.name, this);
			
			if(pd instanceof ConstDecl){
				ConstDecl cd = (ConstDecl)pd;
				UnsignedConstant unc = new UnsignedConstant(cd.lineNum);
			//	System.out.println(cd.name+ "  --- Term");
				unc.charl = cd.c.unsigC.charl;
				unc.name = cd.c.unsigC.name;
				unc.numliteral = cd.c.unsigC.numliteral;
				unc.nameC = cd.c.unsigC.nameC;
				unc.next = factor.next;
				factor = unc;
			}
		}
		
		
		
		
		Factor fatmp = factor;

		while(fatmp.next != null){
			if(fatmp instanceof Variable){
				Variable va  = (Variable)factor;
				PascalDecl pd = curScope.findDecl(va.name, this);
				
				if(pd instanceof ConstDecl){
					ConstDecl cd = (ConstDecl)pd;
					UnsignedConstant unc = new UnsignedConstant(cd.lineNum);
					unc.charl = cd.c.unsigC.charl;
					unc.name = cd.c.unsigC.name;
					unc.numliteral = cd.c.unsigC.numliteral;
					unc.nameC = cd.c.unsigC.nameC;
					unc.next = factor.next.next;
					factor = unc;
				}
				
			}
			fatmp = fatmp.next;
		}
		
		
		
		Factor tmp1 = factor;
		FactorOpr tmp2 = list;
		 while(tmp1 != null){
			 tmp1.check(curScope, lib);
			 if(tmp2 != null){
				 tmp2.check(curScope, lib);
				 tmp2 = tmp2.next;
			 }
			 tmp1 = tmp1.next;
		 }
		 type = factor.type;
	}
	@Override
	void genCode(CodeFile f) {
		Factor fac = factor;
		FactorOpr factorOpr = list;
		while(fac != null){
			 fac.context = this.context;
			 fac.genCode(f);
			 if(factorOpr != null){
				 Factor tmp_next = fac.next;
				 tmp_next.context = this.context;
				 fac = fac.next;
				 if(factorOpr.tokenKind == TokenKind.multiplyToken){
					 f.genInstr("", "pushl", "%eax", "");
					 tmp_next.genCode(f);
					 f.genInstr("", "movl", "%eax,%ecx", "");
					 f.genInstr("", "popl", "%eax", "");
					 f.genInstr("", "imull", "%ecx,%eax", "*");
				 }else if(factorOpr.tokenKind == TokenKind.divToken){
					 f.genInstr("", "pushl", "%eax", "");
					 tmp_next.genCode(f);
					 f.genInstr("", "movl", "%eax,%ecx", "");
					 f.genInstr("", "popl", "%eax", "");
					 f.genInstr("", "cdq", "", "");
					 f.genInstr("", "idivl", "%ecx", " /");
				 }else if(factorOpr.tokenKind == TokenKind.modToken){
					 f.genInstr("", "pushl", "%eax", "");
					 tmp_next.genCode(f);
					 f.genInstr("", "movl", "%eax,%ecx", "");
					 f.genInstr("", "popl", "%eax", "");
					 f.genInstr("", "cdq", "", "");
					 f.genInstr("", "idivl", "%ecx", "");
					 f.genInstr("", "movl", "%edx,%eax", "mod");
				 }else if(factorOpr.tokenKind == TokenKind.andToken){
					 f.genInstr("", "pushl", "%eax", "");
					 tmp_next.genCode(f);
					 f.genInstr("", "movl", "%eax,%ecx", "");
					 f.genInstr("", "popl", "%eax", "");
					 f.genInstr("", "andl", "%ecx,%eax", "and");
				 }
				 factorOpr = factorOpr.next;
			 }
			fac = fac.next;
			if(fac!= null) fac = fac.next;
		 }
		
	}
	

}
