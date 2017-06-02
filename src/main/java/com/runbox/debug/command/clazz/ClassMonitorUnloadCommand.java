package com.runbox.debug.command.clazz;

import java.util.List;
import java.util.LinkedList;

import com.sun.jdi.ReferenceType;
import com.sun.jdi.request.ClassUnloadRequest;
import com.sun.jdi.request.EventRequest;

import com.runbox.debug.command.clazz.ClassCommand;
import com.runbox.debug.manager.MachineManager;
import com.runbox.debug.manager.RequestManager;
import com.runbox.debug.manager.ClassManager;

public class ClassMonitorUnloadCommand extends ClassMonitorCommand {

    public ClassMonitorUnloadCommand(String command) throws Exception {
        super(command);
    }

    @Override
    public boolean execute() throws Exception {
		String clazz = clazz();
		if (null != clazz) {		
			ClassManager.instance().append(new ClassManager.UnloadEntry(clazz, true, routine()));
		} else {
			throw new Exception("invalid argument");
		}
        return super.execute();
    }    
}
