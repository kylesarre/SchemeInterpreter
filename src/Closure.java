// Closure.java -- the data structure for function closures

// Class Closure is used to represent the value of lambda expressions.
// It consists of the lambda expression itself, together with the
// environment in which the lambda expression was evaluated.

// The method apply() takes the environment out of the closure,
// adds a new frame for the function call, defines bindings for the
// parameters with the argument values in the new frame, and evaluates
// the function body.

class Closure extends Node {
    private Node fun;		// a lambda expression
    private Environment env;	// the environment in which the function
				// was defined

    public Closure(Node f, Environment e)	{ fun = f;  env = e; }

    public Node getFun()		{ return fun; }
    public Environment getEnv()		{ return env; }

    // TODO: The method isProcedure() should be defined in
    // class Node to return false.
    public boolean isProcedure()	{ return true; }

    public void print(int n) {
	// there got to be a more efficient way to print n spaces
	for (int i = 0; i < n; i++)
	    System.out.print(' ');
	System.out.println("#{Procedure");
	fun.print(n+3);
	for (int i = 0; i < n; i++)
	    System.out.print(' ');
	System.out.println('}');
    }

    // TODO: The method apply() should be defined in class Node
    // to report an error.  It should be overwritten only in classes
    // BuiltIn and Closure.
    public Node apply (Node args) throws Exception{
    	// somehow this isn't creating a call frame for us...
    	// TODO: bugfix the call frame. bug: call frame is a duplication of the current environment when it should be a new one
    	Environment frame = new Environment(this.env); // call frame
    	Node params = fun.getCdr().getCar(); // lambda parameters
    	//TODO: bugfix this function body. somehow the correct arg list is surrounded by another cons which is causing error: not a valid procedure to occur.
    	// must have made an error during construction of the lambda
    	Node body = fun.getCdr().getCdr().getCar(); // lambda body
    	while(!params.isNull()) {
    		if(!args.isNull()) {
    			frame.define(params.getCar(), args.getCar()); // bind arg to param
    			args = args.getCdr(); // move pointer to next arg
    		}
    		else {
    			frame.define(params.getCar(), new Nil()); // bind nil to arg (throws exception if evaluated: declared but not initialized)
    		}   		
    		params = params.getCdr();
    	}
    	if(body.isNull()) {
    		return Nil.getInstance();
    	}
    	return body.eval(frame);
    }
}
