package parser;

import scanner.Scanner;
import scanner.TokenKind;
import types.BoolType;

import static scanner.TokenKind.*;

import java.util.HashMap;

import main.CodeFile;
import main.Library;
import main.Main;
public class Block extends PascalDecl{
	public PascalDecl context;
	int linenr;
	ConstDeclPart condpar;
	VarDeclPart vardp;
	FuncDecl funcd;
	ProcDecl procd;
	StatementList statL;
	Block outerScope;
	
	public HashMap<String,PascalDecl> decls = new HashMap<>();
	public Block (int n){
		super ("",n);
	}
	
	public static Block parse(Scanner s){
		enterParser("block");
		Block block = new Block(s.curLineNum());
		if(s.curToken.kind == constToken){// det skal komme til ConstDeclPart
			block.condpar = ConstDeclPart.parse(s);
			ConstDecl tmp = block.condpar.cdlist;
			while(tmp != null){
				tmp = tmp.next;
			}
		}
		
		if(s.curToken.kind == varToken){// det skal komme til VarDeclPart
			block.vardp = VarDeclPart.parse(s);
			VarDecl tmp = block.vardp.varList;
			while(tmp != null){
				tmp = tmp.next;
			}
		}
		//det er func decl og func lenkeliste.
		while(s.curToken.kind == TokenKind.functionToken || s.curToken.kind == TokenKind.procedureToken){ // det skal komme til funcDecl eller ProcDecl
			if(s.curToken.kind == TokenKind.functionToken){
				FuncDecl ny = FuncDecl.parse(s);
				
				if(block.funcd == null){
					block.funcd = ny;
				}else{
					FuncDecl tmp = block.funcd;
					while(tmp.next != null){
						tmp = tmp.next;
					}
					tmp.next = ny;
				}
				FuncDecl tmp2 = block.funcd.next;
				while(tmp2 != null){
					//tmp2.b.outerScope = block;
					tmp2 = tmp2.next;
				}
			}
			//det er proc decl og proc lenkeliste.
			if(s.curToken.kind == TokenKind.procedureToken){
				ProcDecl pny = ProcDecl.parse(s);
				if(block.procd == null){
					block.procd = pny;
				}else{
					ProcDecl pd_tmp = block.procd;
					while(pd_tmp.next != null){
						pd_tmp = pd_tmp.next;
						//pd_tmp.b.outerScope = block;
					}
					pd_tmp.next = pny;
				}
			}
			
			if(s.curToken.kind == TokenKind.beginToken){
				break;
			}
			
		}
		s.skip(TokenKind.beginToken);
		block.statL = StatementList.parse(s);//statm list
		block.statL.context = block;
		s.skip(endToken);
		leaveParser("block");
		return block;
	}
	public String identify(){
		 return "<block> " + " on line " + lineNum;
	}
	public void addDecl(String id, PascalDecl d) {
        if (decls.containsKey(id))
            d.error(id + " declared twice in same block!");
        decls.put(id, d);
	}
	
	public void prettyPrint(){
		if(condpar != null){
			condpar.prettyPrint();
		}
		if(vardp != null){
			vardp.prettyPrint();
		}
		FuncDecl tmp = funcd;
		while(tmp != null){
			tmp.prettyPrint();
			tmp = tmp.next;
		}
		ProcDecl tmppd = procd;
		while(tmppd != null){
			tmppd.prettyPrint();
			tmppd = tmppd.next;
		}
		 Main.log.prettyPrint("begin\n");
		 statL.prettyPrint();
	     Main.log.prettyPrintLn(" end"); 

	}


	
	PascalDecl findDecl(String id, PascalSyntax where) {
        PascalDecl d = decls.get(id);//get from hashmap valuer
        if (d != null) {
            Main.log.noteBinding(id, where, d);
            return d; 
        }
        if (outerScope != null)
        	return outerScope.findDecl(id,where);
        where.error("Name " + id + " is unknown!");
        return null;  // Required by the Java compiler.
    }
	//det sjekker Constdeclpart-->vardeclpart-->fucdecl-->procdecl-->statment.
	@Override
	void check(Block curScope, Library lib) {
		//outerScope = curScope;
		if(condpar != null){
			condpar.check(curScope, lib);
		}

		if(vardp != null){
			vardp.check(curScope, lib);
		}

		FuncDecl tmpfd = funcd;
		//for(String s: this.decls.keySet()) System.out.println(s);
		for(;tmpfd != null;tmpfd = tmpfd.next){
			tmpfd.check(this,lib);
			
		}
		ProcDecl tmppd = procd;
		for(;tmppd != null;tmppd = tmppd.next){
			tmppd.check(this,lib);
			
		}
		statL.check(this, lib);
	}

	@Override
	void genCode(CodeFile f) {
		
		if(condpar != null){
			ConstDecl tmp = condpar.cdlist;
			while(tmp != null){
				tmp.context = this;
				tmp = tmp.next;
				
			}
		}
		
		int countV = 0;
		if(vardp != null){
			
			VarDecl tmp = vardp.varList;
			while(tmp != null){
				tmp.declOffset = -36+countV*(-4);
				tmp.context = this;
				tmp = tmp.next;
				countV++;
			}
		}
		
		
		ProcDecl tmppd = procd;
		while(tmppd != null){
			tmppd.context = this;
			tmppd.name_lab = f.getLabel(tmppd.name);
			tmppd.genCode(f);
			tmppd = tmppd.next;
		}
		
		
		FuncDecl tmpfd = funcd;
		while(tmpfd != null){
			tmpfd.context = this;
			tmpfd.name_lab = f.getLabel(tmpfd.name);
			tmpfd.genCode(f);
			tmpfd = tmpfd.next;
		}
		String nameP = context.name_lab;
		if(context instanceof Program){
			nameP = "prog$"+nameP;
		}else if(context instanceof FuncDecl){
			nameP = "func$"+nameP;
		}else if(context instanceof ProcDecl){
			nameP = "proc$"+nameP;
		}
		int b_enter = 32+(4*countV);
		f.genInstr(nameP, "", "", "");
		f.genInstr("", "enter", "$"+b_enter+",$"+this.declLevel, "Start of "+context.name_lab);
		
		
		statL.context = this;
		statL.genCode(f);
		if(context instanceof FuncDecl){
			f.genInstr("", "movl", "-32(%ebp),%eax", "");
		}
		f.genInstr("", "leave", "", "End of "+context.name_lab);
		f.genInstr("", "ret", "", "");
		
	}

	@Override
	void checkWhetherAssignable(PascalSyntax where) {
		// TODO Auto-generated method stub
		
	}

	@Override
	void checkWhetherFunction(PascalSyntax where) {
		// TODO Auto-generated method stub
		
	}

	@Override
	void checkWhetherProcedure(PascalSyntax where) {
		// TODO Auto-generated method stub
		
	}

	@Override
	void checkWhetherValue(PascalSyntax where) {
		// TODO Auto-generated method stub
		
	}
}
