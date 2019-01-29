package main;

import parser.Block;
import parser.CharLiteral;
import parser.ConstDecl;
import parser.Constant;
import parser.PascalDecl;
import parser.ProcDecl;
import parser.TypeDecl;
import parser.UnsignedConstant;
import types.BoolType;
import types.CharType;
import types.IntType;

public class Library extends Block{
	public Block outerScope = null;
	
	//definer alle type.
	public Library() {
		super(-1);
		PascalDecl intdec = new TypeDecl("integer".toUpperCase(),-1);
		intdec.type = new IntType();
		decls.put(intdec.name,intdec);

		types.Type bool = new BoolType();
		PascalDecl boolT = new TypeDecl("Boolean".toUpperCase(),-1);
		boolT.type = bool;
		decls.put(boolT.name,boolT);

		types.Type  chart = new CharType();
		PascalDecl charT = new TypeDecl("char".toUpperCase(),-1);
		charT.type = chart;
		decls.put(charT.name, charT);

		ConstDecl consteol = new ConstDecl("eol".toUpperCase(),-1);
		consteol.type = chart;
		consteol.c = new Constant(-1);
		consteol.c.unsigC = new UnsignedConstant(-1);
		consteol.c.unsigC.charl = new CharLiteral(-1);
		consteol.c.unsigC.charl.ch = 10; //EOL
		decls.put(consteol.name,consteol);

		ConstDecl constTrue = new ConstDecl("True".toUpperCase(),-1);
		constTrue.type = bool;
		constTrue.c = new Constant(-1);
		constTrue.c.unsigC = new UnsignedConstant(-1);
		constTrue.c.unsigC.charl = new CharLiteral(-1);
		constTrue.c.unsigC.charl.ch = 1;
		decls.put(constTrue.name,constTrue);
		
		ConstDecl constFasle = new ConstDecl("False".toUpperCase(),-1);
		constFasle.type = bool;
		constFasle.c = new Constant(-1);
		constFasle.c.unsigC = new UnsignedConstant(-1);
		constFasle.c.unsigC.charl = new CharLiteral(-1);
		constFasle.c.unsigC.charl.ch = 0;
		decls.put(constFasle.name,constFasle);
		
		ProcDecl procWrite = new ProcDecl("write".toUpperCase(),-1);
		procWrite.cntpro = -1;
		decls.put(procWrite.name, procWrite);
	}
	
	public void genCode(CodeFile f) {
		
	}
}
