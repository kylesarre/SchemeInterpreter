import java.io.*;

class Set extends Special {
 
    // TODO: Add any fields needed.

 
    // TODO: Add an appropriate constructor.
	public Set(Node t){

	}
	
    void print(Node t, int n, boolean p) {
    	Printer.printSet(t, n, p);
    }
    
    public Node eval(Node node, Environment env) throws Exception{
    	Node list = node.getCdr();
    	Node temp = list;
    	Node[] ids = new Node[Helpers.getLength(list) - 1];
    	
    	int counter = 0;
    	while(!temp.getCdr().isNull()) {
    		ids[counter] = temp.getCar();  
    		temp = temp.getCdr();
    		counter++;
    	}
    	Node exp = temp.getCar();
    	Node result = exp.eval(env);
    	
    	for(Node id: ids) {
    		env.assign(id, result);
    	}
    	
    	return Nil.getInstance();
    }
}
