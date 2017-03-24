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
