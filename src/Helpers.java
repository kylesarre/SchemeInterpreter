
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
}
