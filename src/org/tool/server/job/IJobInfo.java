package org.tool.server.job;

import java.util.Date;
import java.util.Map;

public interface IJobInfo {
    
    /**
     * <p>
     * Used to indicate the 'repeat count' of the trigger is indefinite. Or in
     * other words, the trigger should repeat continually until the trigger's
     * ending timestamp.
     * </p>
     */
    int REPEAT_INDEFINITELY = -1;
	
	String getName();
	
	String getGroup();
	
	Map<String, Object> getAttributes();
    /**
     * Get the time at which the <code>Trigger</code> should occur.
     */
    Date getStartTime();
    /**
     * Get the time at which the <code>Trigger</code> should quit repeating -
     * regardless of any remaining repeats (based on the trigger's particular 
     * repeat settings). 
     * 
     * @see #getFinalFireTime()
     */
    Date getEndTime();
    /**
     * <p>
     * Get the the number of times the <code>SimpleTrigger</code> should
     * repeat, after which it will be automatically deleted.
     * </p>
     * 
     * @see #REPEAT_INDEFINITELY
     */
    default int getRepeatCount() {
    	return REPEAT_INDEFINITELY;
    }
    /**
     * <p>
     * Get the the time interval (in milliseconds) at which the <code>SimpleTrigger</code> should repeat.
     * </p>
     */
    long getRepeatInterval();

}
