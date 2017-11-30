import java.io.FileInputStream;
import java.io.FileNotFoundException;

// singleton pattern
public class GlobalEnvironment extends Environment{

	public static Environment globalEnv = null;
	
	// constructs the builtIn environment, extends the built in with ini.scm, then wraps the resultant environment with the file environment
	private GlobalEnvironment() {		
		Environment builtIn = new Environment();
		initBuiltIn(builtIn);
		loadIni(builtIn);
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
				"b+", // for baumgartner's test cases
				"-", //implemented
				"b-",// for baumgartner's test cases
				"*", //implemented
				"b*", // for baumgartner's test cases
				"/", //implemented
				"b/", // for baumgartner's test cases
				"=", //implemented
				"b=", // for baumgartner's test cases
				"<", //implemented
				"b<", // for baumgartner's test cases
				"car", //implemented
				"cdr", //implemented
				"cons", // implemented
				"null?", //implemented
				"pair?", //implemented
				"symbol?", // implemented
				"symbol=?", // implemented
				"number?", // implemented
				"eq?", //implemented
				"procedure?", //implemented
				// IO/interpreter built-ins
				"read", // implemented
				"write", //implemented
				"display", //needs further research
				"load",  //implemented
				"eval", //implemented
				"apply", // implemented
				"newline", //implemented
				"interaction-environment", //implemented
				"set-car!", //implemented
				"set-cdr!", //implemented
				// for project 3
				"string=?", //implemented
				"string?"}; //implemented
		for(String s: builtIns) {
			builtIn.define(new Ident(s), new BuiltIn(new Ident(s)));
		}
	}
	
	public void loadIni(Environment env){
		String fName = "ini.scm";
		try {
			Parser p = new Parser(new Scanner(new FileInputStream(fName)));
			try {				
				for(Node exp = p.parseExp(); exp != null; exp = p.parseExp()) {
					if(exp.isNull())
						exp.print(0);
					else {
						exp.eval(env);
					}
				}
			}
			catch(Exception e) {
				System.err.println("Error: error encountered when trying to load definitions from ini.scm.");
				e.printStackTrace();
				System.err.println("Skipping ini.scm...");				
			}
		}
		catch(FileNotFoundException e) {
			e.printStackTrace();
			System.err.println("Skipping ini.scm...");
		}
	}
}
