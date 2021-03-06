import java.io.*;

// There are several different approaches for how to implement the Special
// hierarchy.  We'll discuss some of them in class.  The easiest solution
// is to not add any fields and to use empty constructors.

abstract class Special {
    abstract void print(Node t, int n, boolean p);
    
    //TODO: implement eval for all of the special keywords
    // Define - implemented
    // If - implemented
    // Begin - implemented
    // Set - implemented
    // Cond - implemented
    // Let - implemented
    // Closure - implemented
    // Quote - Implemented
    // lambda - implemented
    public Node eval(Node node, Environment env) throws Exception {
    	throw new Exception("Error: not a valid expression.");
    }
}