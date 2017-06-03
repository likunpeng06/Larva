package com.runbox.debug.manager;

import java.util.HashMap;
import java.util.Map;

import com.sun.jdi.ThreadReference;
import com.sun.jdi.event.Event;
import com.sun.jdi.event.ThreadDeathEvent;
import com.sun.jdi.request.EventRequest;
import com.sun.jdi.request.StepRequest;
import com.sun.jdi.request.ThreadDeathRequest;

import com.runbox.script.statement.node.RoutineNode;

public class ExecuteManager extends Manager {

    private ExecuteManager() {

    }

    private static ExecuteManager instance = new ExecuteManager();

    public static ExecuteManager instance() {
        return instance;
    }

    @Override
    public void clean() throws Exception {
        if (0 < map.size()) {
            for (ThreadReference key : map.keySet()) {
                RequestManager.instance().deleteEventRequest(map.get(key));
            }
            map.clear();
        }
    }

    private Map<ThreadReference, StepRequest> map = new HashMap<ThreadReference, StepRequest>();

	public StepRequest get(ThreadReference thread) throws Exception {
		if (null != thread) {
			return map.get(thread);            
        }
        throw new Exception("invalid thread context");
	}    
	
    public StepRequest create(ThreadReference thread, int size, int depth, RoutineNode routine) throws Exception {
        if (null != thread) {			
			StepRequest request = RequestManager.instance().createStepRequest(thread, size, depth, routine);
            map.put(thread, request);
            return request;
        }
        throw new Exception("invalid thread context");
    }    

    public void delete(ThreadReference thread) {
        if (null != thread) {
            for (ThreadReference key : map.keySet()) {
                if (key == thread) {
                    RequestManager.instance().deleteEventRequest(map.get(key));
                    map.remove(thread); break;
                }
            }
        }
    }    

    private ThreadDeathRequest request = null;

    @Override
    public void monitor(boolean flag) {
        if (flag && null == request) {
            request = RequestManager.instance().createThreadDeathRequest(null);
        } else if (!flag && null != request) {
            RequestManager.instance().deleteEventRequest(request); request = null;
        }
    }

    @Override
    public boolean need(Event event) {
        if (event instanceof ThreadDeathEvent && event.request() == request) {
			return super.need(event);
        }
        return !super.need(event);
    }

    @Override
    public boolean handle(Event event) throws Exception {
        for (ThreadReference key : map.keySet()) {
            if (key == ((ThreadDeathEvent)event).thread()) {
                RequestManager.instance().deleteEventRequest(map.get(key));
                map.remove(key); break;
            }
        }
        return super.handle(event);
    }	
}
