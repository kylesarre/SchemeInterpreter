import java.io.*;

class Define extends Special {
 
    // TODO: Add any fields needed.

 
    // TODO: Add an appropriate constructor.
	public Define(Node t){

	}
    void print(Node t, int n, boolean p) {
    	  Printer.printDefine(t, n, p);
    	  }
    
    // TODO: implement eval for define expressions
    public Node eval(Node node, Environment env) throws Exception{
    	// need to create a mapping between the fun name and lambda expression and bind to the current environment
    	Node fSpec = node.getCdr().getCar();
    	Node fBody = node.getCdr().getCdr();
    	Node fParams = Nil.getInstance();
    	Node fName = Nil.getInstance();
    	
    	if(fSpec.isSymbol() || fSpec.isPair()) {
    		if(fSpec.isSymbol()) {
    			fName = fSpec;
    			fParams = Nil.getInstance();
    		}
    		else {
    			fName = fSpec.getCar();
    	    	fParams = fSpec.getCdr();   	    	    	    	
    		}
    		
    		// construct the required lambda using fParams and fBody:
	    	//(lambda (fParams) (fBody))
	    	Node lam = new Cons(new Ident("lambda"), new Cons(fParams, new Cons(fBody, Nil.getInstance())));
	    	
	    	// we now have the env where the lambda was defined, the variable of the lambda, and the lambda itself, so now we can construct our closure
	    	Closure c = new Closure(lam, env);
	    	
	    	//now we have the closure and the variable, so lets bind the resulting association list with our environment
	    	env.define(fName, c);
	    	
	    	// define doesn't actually return anything, so we return Nil for now
	    	return Nil.getInstance();
    	}
    	else {
    		throw new Exception("Expected symbol or pair but received: " + fSpec.getName() +" .");
    	}
    	
    }
}
