package ra57_2014.pnrs1.rtrk.taskmanager.newtask;

/**
 * Created by ironm on 3/24/2017.
 */

public class Task {
    String name , description;
    boolean reminder;               //true - remind me , false - dont
    int priority;                   //3 - red , 2 - yellow , 1 - green

    public Task(String name , String description , boolean reminder , int priority) {
        this.name = name;
        this.description = description;
        this.reminder = reminder;
        this.priority = priority;
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
        return "Task{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", reminder=" + reminder +
                ", priority=" + priority +
                '}';
    }
}
