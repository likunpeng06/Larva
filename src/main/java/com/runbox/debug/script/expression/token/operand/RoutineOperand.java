package com.runbox.debug.script.expression.token.operand;

import java.util.List;

import com.runbox.script.Engine;
import com.runbox.script.statement.node.BlockNode;
import com.runbox.script.statement.node.RoutineNode;

public class RoutineOperand extends Operand {

    public RoutineOperand(String name) throws Exception {
        super(name);
		routine = Engine.instance().findRoutine(name());
		if (null == routine) {
			throw new Exception("invalid routine name");
		}
    }    		
	
	public void arguments(List<Operand> autos) throws Exception {
		if (null != routine) {
			List<String> names = routine.arguments();
			int i = 0; for (Operand item : autos) {
				AutoOperand auto = new AutoOperand(names.get(i++));
				auto.type(item.type());
				auto.value(item.value());
				Engine.instance().append(auto);
			}
		}
	}
	
	public int count() {
		if (null != routine) {
			return routine.count();
		}
		return 0;
	}

	private RoutineNode routine = null;

	public RoutineNode routine() {
		return routine;
	}

	public Operand invoke() throws Exception {
		if (null != routine) {			
			if (!Engine.instance().execute(routine)) {
				throw new Exception("execute vm command in routine context -> " + routine.name());
			}
			if (null != routine.result()) {
				if (!Operand.subClass((Operand)routine.result())) {
					throw new Exception("invalid routine return");
				}
			}
			return (Operand)routine.result();
		}
		return null;
	}		
}
