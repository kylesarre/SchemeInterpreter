import java.io.*;

class If extends Special {
 
    // TODO: Add any fields needed.
	
 
    // TODO: Add an appropriate constructor.
	public If(Node t){	}
    void print(Node t, int n, boolean p) {
    	  Printer.printIf(t, n, p);
    }
    
    public Node eval(Node node, Environment env) throws Exception{
    	if(Helpers.getLength(node.getCdr()) != 3) {
    		throw new Exception("Error: invalid syntax for if construct. Expected 3 parameters but got " + Helpers.getLength(node.getCdr()) + ".");
    	}
    	
    	Node boolExp = node.getCdr().getCar();
    	Node alt1 = node.getCdr().getCdr().getCar();
    	Node alt2 = node.getCdr().getCdr().getCdr().getCar();
    	Node result = boolExp.eval(env);
    	
    	if(!result.isBoolean())
    		throw new Exception("Error: error on if statement. Expected a boolean value but got " + result.getName() +".");
    	
    	if(BooleanLit.getInstance(true) == result) {
    		return alt1.eval(env);
    	}
    	else {
    		return alt2.eval(env);
    	}
    }
}
