import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.regex.Pattern;


public class VM {
	
	private int pc = 0;
	private Pattern cmmdPattern;
	List<Command> program;
	Map<String, Integer> myProgram;
	private int count;
	private int loopPC;
	private int numInstructionsExecuted;
	
	public VM(){
		numInstructionsExecuted = 0;
		program = new ArrayList<Command>();
		myProgram = new HashMap<String, Integer>();
	}
	
	public void add(String cmmd){
		Command cmd = new Command(cmmd, pc++);

		program.add(cmd);
		
		System.out.println(cmd);
		//System.out.println(pc);
	}
	
	public void compile(String fileName){
		String line = null;
		try{
			FileReader fileReader = new FileReader(fileName);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			while((line = bufferedReader.readLine()) != null){
				Command cmd = new Command(line, pc++);
				program.add(cmd);
			}
			bufferedReader.close();
		}
		catch(FileNotFoundException ex){
			System.out.println("File" + fileName + "not found");
		}
		catch(IOException ex){
			System.out.println("Error reading" + fileName);
		}
	}
	
	public void execute(Command cmmd) throws Exception{
		numInstructionsExecuted++;
		if(cmmd.getOP().equals("load")){
			int val;
			if(cmmd.getArg2().matches("[0-9]+")){
				val = Integer.valueOf(cmmd.getArg2());
			}
			else{
				String variable = cmmd.getArg2();
				//val = Integer.parseInt(cmmd.getArg2());
				val = myProgram.get(variable);
			}
			//load x, 10
			//if myprogram doesnt contain key, initialize it
			if(!myProgram.containsKey(cmmd.getArg1())){
				myProgram.put(cmmd.getArg1(), val);
			}
			else{
				myProgram.replace(cmmd.getArg1(), val);
			}
		}
		else if(cmmd.getOP().equals("inc")){
			int value;
			String key = cmmd.getArg1();
			value = myProgram.get(key);
			value = value + 1;
			myProgram.replace(key, value);
		}
		else if(cmmd.getOP().equals("goto")){
			pc = cmmd.getTarget()-1;
		}
		else if(cmmd.getOP().equals("loop")){	
			/*//loopPC = pc;
			//System.out.println("loopPC:" + loopPC);
			//System.out.println("loop PC:" + loopPC);
			count = myProgram.get(cmmd.getArg1());
			if(count <= 0){
				pc = cmmd.getTarget() + 1;
			} else {
				cmmd.setCount(count);
			}**/
			String arg1 = cmmd.getArg1();
			int count;
			if(arg1.matches("\\d+")){
				count = Integer.parseInt(arg1);
			}
			else{
				Integer integer = myProgram.get(cmmd.getArg1());
				if(integer == null){
					myProgram.put(arg1, 0);
					count = 0;
				}
				else{
					count = integer.intValue();
				}
			}
			if(count <= 0){
				pc = cmmd.getTarget() + 1;
			}
			else{
				cmmd.setCount(count);
			}
		}
		else if(cmmd.getOP().equals("end")){
			Command myLoop = program.get(cmmd.getTarget()-1);
			myLoop.decCount();
			int count = myLoop.getCount();
			
			if(count > 0){
				pc = cmmd.getTarget();
			}
			//count--;
			//if(count > 0){
			//	pc = loopPC;	
			//}
		}
		else{
			throw new Exception("unrecognized opcode");
		}
		
	}
	
	public void run() throws Exception {
		   resolveLabels();
		   pc = 0;
		   while(pc < program.size()) {
		      execute(program.get(pc++));
		   }
		  
		}
	private void resolveLabels() {
		   Stack<Command> loopStack = new Stack<Command>();
		   Map<String, Integer> targets = new HashMap<String, Integer>();
		   // pass 1
		   for(Command cmmd: program) {
			   if(cmmd.getLabel() != null){
				   targets.put(cmmd.getLabel(), cmmd.getpc());
			   }
			   if(cmmd.getOP().equals("loop")){
				   loopStack.push(cmmd);
			   }
			   if(cmmd.getOP().equals("end")){
				   Command loop = loopStack.pop();
				   loop.setTarget(cmmd.getpc());
				   cmmd.setTarget(loop.getpc());
			   }
		   }
		   // pass 2
		   for(Command cmmd: program) {
			   if(cmmd.getOP().equals("goto")){
				  String labelInMap = cmmd.getArg1();
				  if(targets.containsKey(labelInMap)){
					  int pcOfLabel = targets.get(labelInMap);
					  cmmd.setTarget(pcOfLabel);
				  }
			   }
		   }
		   //System.out.println(targets);
		}
	
	public String toString(){
		return "pc= " + pc + "\n" + "vars= " + myProgram + "\n" + 
	"Number of Instructions Executed: " + numInstructionsExecuted;
	}

}
