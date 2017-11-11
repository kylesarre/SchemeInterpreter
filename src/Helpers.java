
public class Helpers {
	// returns the number of elements in the list; Nil terminated. 
	public static int getLength(Node list) {
		if(!list.isNull()) {
    		return 1+getLength(list.getCdr());
    	}
    	else {
    		return 0;
    	}
	}
	
	//returns a list with the results of evaluating each arg in the provided arg list
	public static Node evalArgs(Node args, Environment env) throws Exception{
		if(!args.isNull()) {
    		return new Cons(args.getCar().eval(env), evalArgs(args.getCdr(), env));
    	}
    	else {
    		return new Nil();
    	}
	}
}
