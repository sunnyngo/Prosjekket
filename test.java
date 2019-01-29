import java.util.*;
import java.io.*;

public class test{

	public static void main(String[] args) throws Exception{
		Scanner fil1 = new  Scanner(new File("opers.s"));
		Scanner fil = new Scanner(new File("opers1.s"));
		int cntU = 0;
		while(fil.hasNextLine() && fil1.hasNextLine() ){
			String s1 = fil1.nextLine();
			String s = fil.nextLine();
			
			cntU++;
			if(s.length() < 40 || s1.length() < 40){
				if(!s.equals(s1)){
				System.out.println("line "+ cntU);
				System.out.println(s);
				System.out.println(s1);

				}
			}else{
				String line1 = s1.substring(0,40);
				String line = s.substring(0,40);
				if(!line1.equals(line)){
				System.out.println("line "+ cntU);
				System.out.println(line1);
				System.out.println(line);

				}
			}
			


		}
		System.out.println("Resten GCD.s");
		while(fil.hasNextLine()) System.out.println(fil.nextLine());

		System.out.println("Resten GCD1.s");
		while(fil.hasNextLine()) System.out.println(fil1.nextLine());

	}
}