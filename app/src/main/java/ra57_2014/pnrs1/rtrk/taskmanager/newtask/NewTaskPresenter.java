package ra57_2014.pnrs1.rtrk.taskmanager.newtask;


import java.util.ArrayList;

/**
 * Created by ironm on 3/24/2017.
 */

public class NewTaskPresenter implements NewTaskModel.Presenter{

    NewTaskModel.View view;

    public NewTaskPresenter(NewTaskModel.View view) {
        this.view = view;
    }

    @Override
    public void addTask(String name, String description , boolean reminder , int priority) {
        if(name != null && priority != 0) {
            Task t = new Task(name, description, reminder, priority);
            view.updateList(t);
        } else {
            view.showMessage("Something went wrond :(");
        }
    }
}
