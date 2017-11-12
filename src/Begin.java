import java.io.*;

class Begin extends Special {
 
    // TODO: Add any fields needed.

 
    // TODO: Add an appropriate constructor.
	public Begin(Node t){
		
	}
    void print(Node t, int n, boolean p) {
    	  Printer.printBegin(t, n, p);

    }
    
    public Node eval(Node node, Environment env) throws Exception{
    	if(node.getCdr().isNull()) {
    		return node;
    	}
    	else {
    		// evaluate every fn in the begin call
        	Node result = Helpers.evalArgs(node.getCdr(), env);
        	// grab the last result and return it
        	while(!result.getCdr().isNull()) {
        		result = result.getCdr();
        	}
        	return result.getCar();
    	}
    	
    }
}
