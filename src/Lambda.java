import java.io.*;

class Lambda extends Special {
		
    // TODO: Add any fields needed.

	
	    // TODO: Add an appropriate constructor.
	public Lambda(Node t){}
    void print(Node t, int n, boolean p) {
    	  Printer.printLambda(t, n, p);
  	}
    // constructs a closure that wraps around the lambda and stores the environment in which it was called
    public Node eval(Node node, Environment env) {   	
    	return (Node) new Closure(node, env);    	
    }

}
