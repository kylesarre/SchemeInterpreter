import java.io.*;

class Quote extends Special {
 
    // TODO: Add any fields needed.
 
 
    // TODO: Add an appropriate constructor.
	
	public Quote(Node t){	}

    void print(Node t, int n, boolean p) {
    	Printer.printQuote(t, n, p);
    }
    
    public Node eval(Node exp, Environment env)
    {
      if (Helpers.getLength(exp) == 2)
        return exp.getCdr().getCar();
      System.out.println("Error: invalid expression");
      return (Node) Nil.getInstance();
    }

}
    

