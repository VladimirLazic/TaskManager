package ra57_2014.pnrs1.rtrk.taskmanager.newtask;

/**
 * Created by ironm on 3/24/2017.
 */

public class NewTaskModel {

    interface View {
        void showMessage(String message);
        void updateList(Task t);
        void goToMain();
    }

    interface Presenter {
        void addTask(String name, String description , String time ,boolean reminder , int priority);
    }
}
