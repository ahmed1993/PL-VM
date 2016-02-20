import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Command {
	private String label;
	private String opcode;
	private String arg1;
	private String arg2;
	private int target;
	private int pc = 0;
	private int count;
	private Pattern cmmdPattern;

	public Command(String cmmd, int programCounter) {
		pc = programCounter;
		// optional label
		String label1 = "(([A-Z]+):\\s+)?";
		String labelNoParanthesis = "([A-Z]+)";
		String var = "([a-z]+)";
		String nat = "([0-9]+)";

		String inc = "(inc)\\s+" + var;

		String arg2Regex = "(" + var + "|" + nat + ")";
		String load = "(load)\\s+" + var + ",\\s+" + arg2Regex;

		String gotoCMMD = "(goto)\\s+" + labelNoParanthesis;
		
		String loop = "(loop)\\s+" + arg2Regex;

		String command = label1 + load + "|" + label1 + inc + "|" + label1
				+ gotoCMMD + "|" + label1 + loop + "|" + "(end)";
		
		// TODO take care of loop later in command

		cmmdPattern = Pattern.compile(command);
		Matcher matchercmmd = cmmdPattern.matcher(cmmd);
		while (matchercmmd.find()) {
			
			label = matchercmmd.group(2);
			
			//if third one is not empty, aka its load
			if (matchercmmd.group(3) != null){
				label = matchercmmd.group(2);
				opcode = matchercmmd.group(3);
				arg1 = matchercmmd.group(4);
				arg2 = matchercmmd.group(5);
			}
			
			//if 10th one is not empty, aka its inc
			else if(matchercmmd.group(10) != null){
				opcode = matchercmmd.group(10); 
				arg1 = matchercmmd.group(11);
				label = matchercmmd.group(9);
			}
			
			//if 14th one is not empty, aka its goto
			else if(matchercmmd.group(14) != null){
				opcode = matchercmmd.group(14);
				arg1 = matchercmmd.group(15);
			}
			else if(matchercmmd.group(18) != null){
				opcode = matchercmmd.group(18);
				label = matchercmmd.group(17);
				arg1 = matchercmmd.group(19);
			}
			else if(matchercmmd.group(22)!=null){
				opcode = matchercmmd.group(22);
			}
			
			//opcode = matchercmmd.group(3) || matchercmmd.group(10);
			//for (int i = 1; i < matchercmmd.groupCount(); i++)
			//{
			//	System.out.println(matchercmmd.group(i));
			//}

			/**if (opcode.equals("load") || opcode.equals("inc")) {
				arg1 = matchercmmd.group(4);
			} else {
				arg1 = null;
			}

			if (opcode.equals("load")) {
				arg2 = matchercmmd.group(5);
			} else {
				arg2 = null;
			}*/

		}
	}

	public void setTarget(int x){
		target = x;
	}
	public int getTarget(){
		return target;
	}
	public String getOP(){
		return opcode;
	}
	public int getpc(){
		pc++;
		return pc;
	}
	public String getArg1(){
		return arg1;
	}
	public String getArg2(){
		return arg2;
	}
	public String getLabel(){
		return label;
	}
	public int getCount(){
		return count;
	}
	public void setCount(int x){
		count = x;
	}
	public void decCount(){
		count--;
	}
	public String toString() {
		return "label: " + label + " opcode: " + opcode + " arg1: " + arg1
				+ " arg2: " + arg2;
	}
	

}
