class Cond extends Special {
 
    // TODO: Add any fields needed.

 
    // TODO: Add an appropriate constructor.
	public Cond(Node t){

	}
    void print(Node t, int n, boolean p) { 
    	Printer.printCond(t, n, p);
    }
    // TODO: fix bug where where its possible for cond to attempt to eval nil (calls node's eval which throws an error).
    public Node eval(Node node, Environment env) throws Exception {
    	Node cList = node.getCdr();
    	while(!cList.isNull()) {
    		Node bExp = cList.getCar();
    		if(cList.getCdr().isNull()) {
    			bExp.print(0);
    			if(bExp.getCar().isSymbol()) {
    				String sym = ((Ident)(bExp.getCar())).getSymbol();
    				if("else".equals(sym)) {
    					if(bExp.getCdr().getCar().isNull())
    	    				return Nil.getInstance();
    					else if(!bExp.getCdr().getCar().isPair())
    						return bExp.getCdr().getCar();
    					else
    						return bExp.getCdr().getCar().eval(env);
    				}
    				else {
    					throw new Exception("Error: expected else but found " + sym + ".");
    				}
    			}
    			else if(bExp.getCar().isPair()) {
    				Node result = bExp.getCar().eval(env);
    	    		if(result == BooleanLit.getInstance(true)) {
    	    			if(bExp.getCdr().getCar().isNull())
    	    				return Nil.getInstance();
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
    	    			if(bExp.getCdr().getCar().isNull())
    	    				return Nil.getInstance();
    	    			else if(!bExp.getCdr().getCar().isPair())
    	    				return bExp.getCdr().getCar();
    	    			else
    	    				return bExp.getCdr().getCar().eval(env);
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
