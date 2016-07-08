package model.state;

import java.util.ArrayList;
import java.util.List;

import model.operation.Operation;

public class State {
	
	private String name;
	private List<Operation> operationList;
	
	public State(String name){
		this.name = name;
		operationList = new ArrayList<Operation>();
	}
	
	public void addToOperations(Operation operation){
		
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		State other = (State) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
	
	
	
	
}
