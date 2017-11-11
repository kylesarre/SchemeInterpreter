
public class Helpers {
	// returns the number of elements in the list; Nil terminated. 
	public static int getLength(Node list) {
		// throws a null pointer exception if the list doesn't terminate with nil
		if(list == null) {
			return 0;
		}
		
		if(list.isNull()) {
			return 0;   		
    	}
    	else {
    		return 1+getLength(list.getCdr());
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
