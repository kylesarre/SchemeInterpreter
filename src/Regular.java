import java.io.*;

class Regular extends Special {
 
    // TODO: Add any fields needed.

    
    // TODO: Add an appropriate constructor.

    public Regular(Node t){

	}
    void print(Node t, int n, boolean p) {
    	  Printer.printRegular(t, n, p);
    } 
    
    // assumption: the head of a regular list denotes a function call
    public Node eval(Node exp, Environment env) throws Exception{
    	if(Helpers.getLength(exp) >= 1) {
    		// scheme evaluates arguments before making the call, so lets evaluate each argument left to right
    		return exp.getCar().eval(env).apply(Helpers.evalArgs(exp.getCdr(), env));
    	}
    	else {
    		throw new Exception("Error: not a valid expression.");
    	}
    }
}
