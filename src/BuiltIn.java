// BuiltIn.java -- the data structure for function closures

// Class BuiltIn is used for representing the value of built-in functions
// such as +.  Populate the initial environment with
// (name, new BuiltIn(name)) pairs.

// The object-oriented style for implementing built-in functions would be
// to include the Java methods for implementing a Scheme built-in in the
// BuiltIn object.  This could be done by writing one subclass of class
// BuiltIn for each built-in function and implementing the method apply
// appropriately.  This requires a large number of classes, though.
// Another alternative is to program BuiltIn.apply() in a functional
// style by writing a large if-then-else chain that tests the name of
// of the function symbol.

class BuiltIn extends Node {
    private Ident symbol;

    public BuiltIn(Ident s)		{ symbol = s; }

    public Node getSymbol()		{ return symbol; }

    // TODO: The method isProcedure() should be defined in
    // class Node to return false.
    public boolean isProcedure()	{ return true; }

    public void print(int n) {
	// there got to be a more efficient way to print n spaces
	for (int i = 0; i < n; i++)
	    System.out.print(' ');
	System.out.println("#{Built-in Procedure");
	symbol.print(n+3);
	for (int i = 0; i < n; i++)
	    System.out.print(' ');
	System.out.println('}');
    }

    // TODO: The method apply() should be defined in class Node
    // to report an error.  It should be overwritten only in classes
    // BuiltIn and Closure.
    public Node apply (Node args) throws Exception{
		if(Helpers.getLength(args) == 1)
			return callUnary(args.getCar());
		else if(Helpers.getLength(args) == 2) {
			return callBinary(args.getCar(), args.getCdr().getCar());
		}
		else if(Helpers.getLength(args) == 0) {
			return callZero();
		}
		else {
			throw new Exception("Error: argument list has length: " + Helpers.getLength(args) + ". Unsupported length.");
		}	
    }
    
    // all of our built ins for single parameter procedures
    public Node callUnary(Node arg1) throws Exception{
    	if("+".equals(symbol.getSymbol())) {
    		return add(num(arg1));
    	}
    	else if("-".equals(symbol.getSymbol())) {
    		return sub(num(arg1));
    	}
		else if("car".equals(symbol.getSymbol())) {
			return arg1.getCar();
		}
		else if("cdr".equals(symbol.getSymbol())) {
			return arg1.getCdr();
		}
		else if("pair?".equals(symbol.getSymbol())) {
			return BooleanLit.getInstance(arg1.isPair());
		}
		else if("number?".equals(symbol.getSymbol())) {
			return BooleanLit.getInstance(arg1.isNumber());
		}
		else if("symbol?".equals(symbol.getSymbol())) {
			return BooleanLit.getInstance(arg1.isSymbol());
		}
		else if("null?".equals(symbol.getSymbol())) {
			return BooleanLit.getInstance(arg1.isNull());
		}
		else if("procedure?".equals(symbol.getSymbol())) {
			return BooleanLit.getInstance(arg1.isProcedure());
		}
		else if("write".equals(symbol.getSymbol())) {
			arg1.print(-1);
			return new Nil();
		}
		else {
			throw new Exception("Error: unsupported unary param built-in!");
		}
    }
    
    // all of our built ins for two parameter procedures
	public Node callBinary(Node arg1, Node arg2) throws Exception{
		if("+".equals(symbol.getSymbol())) {
    		return add(num(arg1), num(arg2));
    	}
    	else if("-".equals(symbol.getSymbol())) {
    		return sub(num(arg1), num(arg2));
    	}
    	else if("*".equals(symbol.getSymbol())) {
    		return mul(num(arg1), num(arg2));
    	}
		else if("/".equals(symbol.getSymbol())) {
			return intDiv(num(arg1), num(arg2));
		}
		else if("=".equals(symbol.getSymbol())) {
			return equiv(num(arg1), num(arg2));
		}
		else if("<".equals(symbol.getSymbol())) {
			return isLess(num(arg1), num(arg2));
		}
		else {
			throw new Exception("Error: unsupported binary param built-in!");
		}
	}
	
	// all of our built ins for zero parameter procedures
	public Node callZero() throws Exception{
		if("read".equals(symbol.getSymbol())) {
			// TODO: implement read
			return new Nil();
		}
		else if("newline".equals(symbol.getSymbol())) {
			System.out.print("\n");
			return new Nil();
		}
		else if("interaction-environment".equals(symbol.getSymbol())) {
			return GlobalEnvironment.getGlobalEnv();
		}
		else {
			throw new Exception("Error: unsupported zero param built-in");
		}
	}
    
    // helper function that throws an error if the Node isn't a number
    public IntLit num(Node n) throws Exception{
    	if(n.isNumber()) {
    		return ((IntLit)n);
    	}
    	else {
    		throw new Exception("Error: argument is not an integer.");
    	}
    }
    
    //unary
	public Node add(IntLit x) {
	    	return new IntLit(x.getVal());
    }  
    
    public Node sub(IntLit x) {
    	return new IntLit(-1*x.getVal());
    } 

    //binary
    public Node add(IntLit x, IntLit y) {
    	return new IntLit(x.getVal() + y.getVal());
    }
    
    public Node mul(IntLit x, IntLit y) {
    	return new IntLit(x.getVal() * y.getVal());
	}
    
    public Node sub(IntLit x, IntLit y) {
    	return new IntLit(x.getVal() - y.getVal());
	}
    
    public Node intDiv(IntLit x, IntLit y) {
    	return new IntLit(x.getVal()/y.getVal());
    }
    
    public Node equiv(IntLit x, IntLit y) {
    	return BooleanLit.getInstance(x == y);
    }
    
    public Node isLess(IntLit x, IntLit y) {
    	return BooleanLit.getInstance(x.getVal() < y.getVal());
    }
}
