// singleton pattern
public class RuntimeEnvironment extends Environment{

	public static Environment globalEnv = null;
	
	// constructs the builtIn environment, then wraps it with the file environment
	private RuntimeEnvironment() {		
		Environment builtIn = new Environment();
		initBuiltIn(builtIn);
		globalEnv = new Environment(builtIn);
	}
	
	public static Environment getGlobalEnv() {
		if(globalEnv == null) {
			// sets a global pointer to the file environment
			new RuntimeEnvironment();
		}
		// if the global pointer has been set, we simply return it
		return globalEnv;
	}
	
	// creates an association list of the form (val, builtIn(val)) containing all built in functions for our scheme environment 
	public void initBuiltIn(Environment builtIn) {
		Ident add = new Ident("+");
		builtIn.define(add, new BuiltIn(add));
		Ident sub = new Ident("-");
		builtIn.define(sub, new BuiltIn(sub));
		Ident mul = new Ident("*");
		builtIn.define(mul, new BuiltIn(mul));
	}
}
