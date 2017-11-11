// singleton pattern
public class GlobalEnvironment extends Environment{

	public static Environment globalEnv = null;
	
	// constructs the builtIn environment, then wraps it with the file environment
	private GlobalEnvironment() {		
		Environment builtIn = new Environment();
		initBuiltIn(builtIn);
		globalEnv = new Environment(builtIn);
	}
	
	public static Environment getGlobalEnv() {
		if(globalEnv == null) {
			// sets a global pointer to the file environment
			new GlobalEnvironment();
		}
		// if the global pointer has been set, we simply return it
		return globalEnv;
	}
	
	// creates an association list of the form (val, builtIn(val)) containing all built in functions for our scheme environment 
	public void initBuiltIn(Environment builtIn) {
		// define all of the default built ins
		String[] builtIns = {
				"+", //implemented
				"-", //implemented
				"*", //implemented
				"/", //implemented
				"=", //implemented
				"<", //implemented
				"car", //implemented
				"cdr", //implemented
				"cons", // implemented
				"null?", //implemented
				"pair?", //implemented
				"eq?", //implemented
				"procedure?", //implemented
				"read",
				"write",
				"display",
				"load",
				"newline", //implemented
				"interaction-environment", //implemented
				"set-car!", //implemented
				"set-cdr!"}; //implemented
		for(String s: builtIns) {
			builtIn.define(new Ident(s), new BuiltIn(new Ident(s)));
		}
	}
}
