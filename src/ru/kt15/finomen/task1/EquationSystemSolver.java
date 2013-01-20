package ru.kt15.finomen.task1;

import java.util.Locale;

import com.wolfram.jlink.*;

public class EquationSystemSolver {
	private KernelLink ml = null;
	public EquationSystemSolver() {
		try {
            ml = MathLinkFactory.createKernelLink("-linkmode launch -linkname 'c:\\program files\\wolfram research\\mathematica\\9.0\\mathkernel.exe'");
            ml.discardAnswer();
        } catch (MathLinkException e) {
            System.out.println("Fatal error opening link: " + e.getMessage());
        }
		
		
	}
	
	public void free() {
		ml.close();
	}
		
	public double[] solve(double[] x, double h, Function[] system) {
		try {
			/*System.out.println(String.format("Flatten[Pick[Flatten[Take[Transpose[NSolve[{%s,%s,%s},{x,y,z}] /. Rule->List, {1,3,2}],1], 1], {{0,0,0},{1,1,1}}, 1]]", 
            		system[0].equation(h, x),
            		system[1].equation(h, x),
            		system[2].equation(h, x)));
            
            System.out.println("Solving");*/
			String e = String.format("Flatten[Pick[Flatten[Take[Transpose[NSolve[{%s,%s,%s},{x,y,z},Reals] /. Rule->List, {1,3,2}],1], 1], {{0,0,0},{1,1,1}}, 1]]", 
            		system[0].equation(h, x),
            		system[1].equation(h, x),
            		system[2].equation(h, x));
			
			//System.out.println(e);
            ml.evaluate(e);

            ml.waitForAnswer();
            //System.out.println(ml.getExpr().toString());
            double[] result = ml.getDoubleArray1();
            
            //System.out.println(result[0] + " " + result[1] + " " + result[2]);
            
           
            
            return result;
            

        } catch (MathLinkException e) {
            System.out.println("MathLinkException occurred: " + e.getMessage());
        }
        return null;
	}
}
