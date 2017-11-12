class Cond extends Special {
 
    // TODO: Add any fields needed.

 
    // TODO: Add an appropriate constructor.
	public Cond(Node t){

	}
    void print(Node t, int n, boolean p) { 
    	Printer.printCond(t, n, p);
    }
    
    public Node eval(Node node, Environment env) throws Exception {
    	Node cList = node.getCdr();
    	
    	while(!cList.isNull()) {
    		Node bExp = cList.getCar();
    		if(cList.getCdr().isNull()) {
    			if(bExp.getCar().isSymbol()) {
    				String sym = ((Ident)(bExp.getCar())).getSymbol();
    				if("else".equals(sym)) {
    					return bExp.getCdr().eval(env);
    				}
    				else {
    					throw new Exception("Error: expected else but found " + sym + ".");
    				}
    			}
    			else if(bExp.getCar().isPair()) {
    				Node result = bExp.getCar().eval(env);
    	    		if(result == BooleanLit.getInstance(true)) {
    	    			return bExp.getCdr().getCar().eval(env);
    	    		}
    			}
    			else {
    				throw new Exception("Error: expected boolean expression or else");
    			}
    		}
    		else {
    			if(bExp.getCar().isPair()) {
    				Node result = bExp.getCar().eval(env);
    	    		if(result == BooleanLit.getInstance(true)) {
    	    			System.out.println(result == BooleanLit.getInstance(true));
    	    			return bExp.getCdr().eval(env);
    	    		}
    			}
    			else {
    				throw new Exception("Error: expected a boolean expression.");
    			}
    		}
 		
    		cList = cList.getCdr();
    	}
    	
    	return Nil.getInstance();
    }
}
