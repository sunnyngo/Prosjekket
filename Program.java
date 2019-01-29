package parser;

import main.*;
import scanner.Scanner;
import static scanner.TokenKind.*;
/* <program> ::= ’program’ <name> ’;’ <block> ’.’ */

import java.util.ArrayList;

public class Program extends PascalDecl { 
	Block progBlock;
	//public static ArrayList<ConstDecl> consname = new ArrayList<ConstDecl>();
Program(String id, int lNum) { 
	super(id, lNum);
}
@Override public String identify() {
return "<program> " + name + " on line " + lineNum;
}
public static Program parse(Scanner s) { 
	enterParser("program"); 
	s.skip(programToken);
	s.test(nameToken);
	Program p = new Program(s.curToken.id.toUpperCase(), s.curLineNum());
	s.readNextToken();
	s.skip(semicolonToken);
	p.progBlock = Block.parse(s);
	p.progBlock.context = p; 
	s.skip(dotToken);
	leaveParser("program");
	return p; 
}
	
	void checkWhetherAssignable(PascalSyntax where) {
		
	}
	
	void checkWhetherFunction(PascalSyntax where) {
		
	}
	
	void checkWhetherProcedure(PascalSyntax where) {
		
	}
	void checkWhetherValue(PascalSyntax where) {
		
	}
	
	
	public void prettyPrint(){
		 Main.log.prettyPrint("********PRETTYPRINT*********\n");
		 Main.log.prettyPrint("program " + this.name + "; \n");
		 progBlock.prettyPrint();
		 Main.log.prettyPrint(".");
	}
	@Override
	public void check(Block curScope, Library lib) {
		progBlock.outerScope = lib;
		//for(String b : lib.decls.keySet()) System.out.println(b);
		progBlock.check(curScope, lib);
		
	}
	@Override
	public void genCode(CodeFile f) {
		this.name_lab = f.getLabel(this.name);
		f.genInstr("", ".globl", "main", "");
		f.genInstr("main", "", "","");
		f.genInstr("", "call","prog$" + this.name_lab, "Start program");
		f.genInstr("", "movl", "$0,%eax", "Set status 0 and");
		f.genInstr("", "ret", "", "terminate the program");
		progBlock.declLevel = 1;
		progBlock.context = this;
		progBlock.genCode(f);
	}
	
	
}