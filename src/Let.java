import java.io.*;

class Let extends Special {
 
    // TODO: Add any fields needed.
	
 
    // TODO: Add an appropriate constructor.
	public Let(Node t){}
    void print(Node t, int n, boolean p) {
    	  Printer.printLet(t, n, p);
    }
    
    // Let takes an association list of the form ((id1 val1) (id2 val2) ... (idn valn))
    // constructs a new environment, binding the variables in the association list,
    // and executes the function body within this environment, if one exists.
    // the return value is the last function to be evaluated
    public Node eval(Node node, Environment env) throws Exception{
    	Node aList = node.getCdr().getCar();
    	Environment frame = new Environment(env);    	
    	
    	while(!aList.isNull()) {
    		Node ele = aList.getCar();
    		if(Helpers.getLength(ele) != 2) {
    			throw new Exception("Error: pair required in Let expression.");
    		}
    		frame.define(ele.getCar(), ele.getCdr().getCar());
    		aList = aList.getCdr();
    	}
    	
    	Node expList = node.getCdr().getCdr();
    	if(expList.isNull()) {
    		throw new Exception("Error: invalid let syntax. Expected body but got Nil."); 
    	}    		
    	if(expList.getCar().isNull())
    		return expList.getCar();
    	Node resultList = Helpers.evalArgs(expList, frame);
    	// grab the last result and return it
    	while(!resultList.getCdr().isNull()) {
    		resultList = resultList.getCdr();
    	}
    	return resultList.getCar();
    }
}
