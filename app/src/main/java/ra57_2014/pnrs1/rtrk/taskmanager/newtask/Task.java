package ra57_2014.pnrs1.rtrk.taskmanager.newtask;

import java.io.Serializable;

/**
 * Created by ironm on 3/24/2017.
 */

public class Task implements Serializable {
    String name , description , time;
    boolean reminder , done;               //true - remind me , false - dont
    int priority;                   //3 - red , 2 - yellow , 1 - green

    public Task(String name , String description , String time ,boolean reminder , int priority) {
        this.name = name;
        this.description = description;
        this.time = time;
        this.reminder = reminder;
        this.priority = priority;
        this.done = false;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTime() {

        return time;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public boolean isReminder() {
        return reminder;
    }

    public int getPriority() {
        return priority;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setReminder(boolean reminder) {
        this.reminder = reminder;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    @Override
    public String toString() {
        return name + " " + time + " " + (description.isEmpty() ? "" : description) + " " + (reminder ? "Remindrr on" : "Reminder off") + " " + priority;
    }
}
