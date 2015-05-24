package org.bartelby.console;

import java.util.ArrayList;
import java.util.HashMap;

public class ConsoleArgument {
	
	private HashMap<String, Object> option = new HashMap<String, Object>();
	private ArrayList<String> argument = new ArrayList<String>();

	public ConsoleArgument(String[] args) {
		int j;
		for(int i=0;i < args.length;i++){
			j = i + 1;
			if(args[i].startsWith("-") && (args.length - 1) > j && !args[j].startsWith("-")){
				if(args[i].startsWith("--")){
					this.option.put(args[i].substring(2), args[j]);
				}else{
					this.option.put(args[i].substring(1), args[j]);
				}
				
				i++;
			}else if(args[i].startsWith("-")){
				if(args[i].startsWith("--")){
					this.option.put(args[i].substring(2), true);
				}else{
					this.option.put(args[i].substring(1), true);
				}
			}else{
				this.argument.add(args[i]);
			}
		}
	}
	
	public boolean optionExist(String option){
		return this.option.containsKey(option);
	}
	
	public Object getOption(String option){
		if(this.optionExist(option)){
			return this.option.get(option);
		}else{
			return false;
		}
	}
	
	public boolean argumentExist(Integer index){
		return (this.argument.size() > index);
	}
	
	public String getArgument(Integer index){
		if(this.argumentExist(index)){
			return this.argument.get(index);
		}else{
			return null;
		}
	}

}
