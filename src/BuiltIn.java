import java.io.*;

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
	    if("apply".equals(symbol.getSymbol())){
			if(Helpers.getLength(args) < 2)
				throw new Exception("Error: argument list has length " + Helpers.getLength(args) + ". Unsupported length.");
			else{
				if(!args.getCar().isProcedure())
					throw new Exception("Error: expected procedure but got non-procedure.");
				else{
					if(!fetchLastElement(args).isPair()) {
						if(fetchLastElement(args).isNull())
							return args.getCar().apply(Nil.getInstance());
						else
							throw new Exception("Error: expected last arg to be list but got an atom.");
					}						
					else if(fetchAllButLast(args.getCdr()).isNull())
						return args.getCar().apply(fetchLastElement(args));
					else					
						return args.getCar().apply(Helpers.listAppend(fetchAllButLast(args.getCdr()), fetchLastElement(args)));
				}					
			}
		}
		else {
			if(Helpers.getLength(args) == 1)
				return callUnary(args.getCar());
			else if(Helpers.getLength(args) == 2) {
				return callBinary(args.getCar(), args.getCdr().getCar());
			}
			else if(Helpers.getLength(args) == 0) {
				return callZero();
			}
		    	//else if(Helpers.getLength(args) > 2){
				//return callN(args);
			//}
			else {
				throw new Exception("Error: argument list has length: " + Helpers.getLength(args) + ". Unsupported length.");
			}
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
			if(arg1.isNull())
				throw new Exception("Error: tried to take cdr of Nil.");
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
		else if("pair?".equals(symbol.getSymbol()))
			return BooleanLit.getInstance(arg1.isPair());
		else if("string?".equals(symbol.getSymbol())) {
			return BooleanLit.getInstance(arg1.isString());
		}
		else if("write".equals(symbol.getSymbol())) {
			arg1.print(-1);
			return new Nil();
		}
		else if("load".equals(symbol.getSymbol())) {
			if(!arg1.isString()) {
				throw new Exception("Error: expected StringLit. load failed.");
			}
			else {
				String fName = ((StrLit)arg1).getStrVal();
				InputStream br = new FileInputStream(fName);
				Node root = new Parser(new Scanner(br)).parseExp();
				return root.eval(GlobalEnvironment.getGlobalEnv());
			}
		}
    	// not really sure what the difference between these two even are
		else if("display".equals(symbol.getSymbol())) {
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
		else if("b+".equals(symbol.getSymbol())) {
    		return add(num(arg1), num(arg2));
    	}
    	else if("-".equals(symbol.getSymbol())) {
    		return sub(num(arg1), num(arg2));
    	}
    	else if("b-".equals(symbol.getSymbol())) {
    		return sub(num(arg1), num(arg2));
    	}
    	else if("*".equals(symbol.getSymbol())) {
    		return mul(num(arg1), num(arg2));
    	}
    	else if("b*".equals(symbol.getSymbol())) {
    		return mul(num(arg1), num(arg2));
    	}
		else if("/".equals(symbol.getSymbol())) {
			return intDiv(num(arg1), num(arg2));
		}
		else if("b/".equals(symbol.getSymbol())) {
			return intDiv(num(arg1), num(arg2));
		}
		else if("=".equals(symbol.getSymbol())) {
			return equiv(num(arg1), num(arg2));
		}
		else if("b=".equals(symbol.getSymbol())) {
			return equiv(num(arg1), num(arg2));
		}
		else if("string=?".equals(symbol.getSymbol())) {
			if(arg1.isString() && arg2.isString()) {
				StrLit s1 = (StrLit)arg1;
				StrLit s2 = (StrLit)arg2;
				return BooleanLit.getInstance(s1.getStrVal().equals(s2.getStrVal()));
			}
			else {
				throw new Exception("Error: expected arg1 and arg2 to be strings but one wasn't.");
			}
		}
		else if("symbol=?".equals(symbol.getSymbol())) {
			Ident s1 = (Ident)arg1;
			Ident s2 = (Ident)arg2; 
			return BooleanLit.getInstance(s1.getSymbol().equals(s2.getSymbol()));
		}
		else if("<".equals(symbol.getSymbol())) {
			return isLess(num(arg1), num(arg2));
		}
		else if("b<".equals(symbol.getSymbol())) {
			return isLess(num(arg1), num(arg2));
		}
		else if("cons".equals(symbol.getSymbol())) {
			return new Cons(arg1, arg2);
		}
		else if("set-car!".equals(symbol.getSymbol())) {
			if(!arg1.isPair())
				throw new Exception("Error: expected a pair but received a " + arg1.getName() +".");
			arg1.setCar(arg2);
			return Nil.getInstance();
		}
		else if("set-cdr!".equals(symbol.getSymbol())) {
			if(!arg1.isPair())
				throw new Exception("Error: expected a pair but received a " + arg1.getName() +".");
			arg1.setCdr(arg2);
			return Nil.getInstance();
		}
		// this might work strangely since I was inconsistent about returning pointers to pre-existing objects vs creating new objects (like with nil)
		else if("eq?".equals(symbol.getSymbol()))
		{
			arg1.print(0);arg2.print(0);
			System.out.print(arg1 == arg2);
			return BooleanLit.getInstance(arg1 == arg2);
		}
		else if("eval".equals(symbol.getSymbol())) {
			if(arg2 instanceof Environment) {
				return arg1.eval((Environment)arg2);
			}
			else {
				throw new Exception("Error: expected an environment.");
			}
		}
		else {
			throw new Exception("Error: unsupported binary param built-in!");
		}
	}
	
	// all of our built ins for zero parameter procedures
	public Node callZero() throws Exception{
		if("read".equals(symbol.getSymbol())) {
			// TODO: implement read
			Node root = new Parser(new Scanner(System.in)).parseExp();
			if(!root.isNull())
				return root;
			return Nil.getInstance();
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
    	return BooleanLit.getInstance(x.getVal() == y.getVal());
    }
    
    public Node isLess(IntLit x, IntLit y) {
    	return BooleanLit.getInstance(x.getVal() < y.getVal());
    }
    
	// fetches datum from a cons node tree and returns a list whose elements are each datum
	// helper function that 
	public Node fetchAll(Node n){
		if(!n.getCdr().isNull()){
			if(n.getCar().isPair()){
				return fetchAll(n.getCar());
			}
			else{
				// car of n is data
				return new Cons(n.getCar(), fetchAll(n.getCdr()));
			}
		}
		else{
			if(n.getCar().isPair()){
				return fetchAll(n.getCar());
			}
			else{
				// car of n is data and cdr is empty
				return n;
			}
		}
	}
	
	// fetches a pointer to the last element in a list
	// helper function for fetchAll
	public Node fetchLastElement(Node n){
		if(!n.getCdr().isNull()){
			return fetchLastElement(n.getCdr());
		}
		else{
			return n.getCar();
		}
	}
	
	// fetches all elements of a list excluding the last element
	// helper function for apply
	public Node fetchAllButLast(Node n){
		if(n.getCdr().isNull()){
			return Nil.getInstance();
		}
		else{
			return new Cons(n.getCar(), fetchAllButLast(n.getCdr()));
		}
	}
    //n-ary
	
	
	
}
