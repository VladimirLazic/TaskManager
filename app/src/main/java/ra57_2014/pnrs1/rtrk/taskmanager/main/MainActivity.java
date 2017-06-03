package ra57_2014.pnrs1.rtrk.taskmanager.main;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

import ra57_2014.pnrs1.rtrk.taskmanager.R;
import ra57_2014.pnrs1.rtrk.taskmanager.statistics.StatisticsActivity;
import ra57_2014.pnrs1.rtrk.taskmanager.newtask.NewTaskActivity;
import ra57_2014.pnrs1.rtrk.taskmanager.newtask.Task;

public class MainActivity extends AppCompatActivity {

    Button statistics , newTask;
    TaskElementAdapter mAdapter;
    ArrayList<Task>tasks;
    String TAG = "MainActivityTag";
    ReminderService mReminder;

    TaskDbHelper mTaskDbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        statistics = (Button) findViewById(R.id.statistics);
        newTask = (Button) findViewById(R.id.newTask);

        //Dummy Tasks
        /*tasks.add(new Task("Dummy 1" , "" , "5/4/2017 18:12" , false, 3));
        tasks.add(new Task("Dummy 2" , "" , "5/4/2017 18:12" , false, 2));
        tasks.add(new Task("Dummy 3" , "" , "5/4/2017 18:12" , false, 1));
        tasks.add(new Task("Dummy 4" , "" , "5/4/2017 18:12" , false, 2));
        tasks.add(new Task("Dummy 5" , "" , "5/4/2017 18:12" , false, 1));
        tasks.add(new Task("Dummy 6" , "" , "5/4/2017 18:12" , false, 3));
        tasks.add(new Task("Dummy 7" , "" , "5/4/2017 18:12" , false, 2));
        tasks.add(new Task("Dummy 8" , "" , "5/4/2017 18:12" , false, 2));
        tasks.add(new Task("Dummy 9" , "" , "22/5/2017 17:59" , true, 2));*/

        mTaskDbHelper = new TaskDbHelper(this);

        if (mTaskDbHelper.readTasks() != null && !mTaskDbHelper.readTasks().isEmpty()) {
            tasks = new ArrayList<>(mTaskDbHelper.readTasks());
        } else {
            tasks = new ArrayList<>();
        }

        mAdapter = new TaskElementAdapter(this , tasks);

        final ListView list = (ListView) findViewById(R.id.listView);
        list.setAdapter(mAdapter);


        list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Task t = mAdapter.getItem(position);
                Intent intent = new Intent(MainActivity.this , NewTaskActivity.class);
                intent.putExtra("Left" , getResources().getString(R.string.save));
                intent.putExtra("Right" , getResources().getString(R.string.delete));
                intent.putExtra("Task" , t);
                startActivityForResult(intent , 2);
                return true;
            }
        });

        statistics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this , StatisticsActivity.class);
                int[] numberOfTasks = new int[6];

                for(int i : numberOfTasks) {
                    i = 0;
                }

                for(Task t : tasks) {
                    Log.d(TAG, "onClick: Task" + t.toString());
                    Log.d(TAG, "onClick: priority" + t.getPriority());
                    switch (t.getPriority()) {
                        case 1:
                            numberOfTasks[0]++;
                            Log.d(TAG, "onClick: Number of LOW tasks: " + Integer.toString(numberOfTasks[0]));
                            if(t.isDone())
                                numberOfTasks[1]++;
                            Log.d(TAG, "onClick: Number of LOW DONE tasks: " + numberOfTasks[1]);
                            break;
                        case 2:
                            numberOfTasks[2]++;
                            Log.d(TAG, "onClick: Number of MEDIUM tasks: " + Integer.toString(numberOfTasks[2]));
                            if(t.isDone())
                                numberOfTasks[3]++;
                            Log.d(TAG, "onClick: Number of MEDIUM DONE tasks: " + numberOfTasks[3]);
                            break;
                        case 3:
                            numberOfTasks[4]++;
                            Log.d(TAG, "onClick: Number of HIGH tasks: " + Integer.toString(numberOfTasks[4]));
                            if(t.isDone())
                                numberOfTasks[5]++;
                            Log.d(TAG, "onClick: Number of HIGH DONE tasks: " + numberOfTasks[5]);
                            break;
                    }
                }
                intent.putExtra("Tasks" , numberOfTasks);
                startActivityForResult(intent , 0);
            }
        });

        newTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext() , NewTaskActivity.class);
                intent.putExtra("Left" , getResources().getString(R.string.add));
                intent.putExtra("Right" , getResources().getString(R.string.cancel));
                startActivityForResult(intent , 1);
            }
        });


        createReminderService();
    }


    private void createReminderService() {
        Intent intent = new Intent(MainActivity.this , ReminderService.class);
        intent.putExtra("Task" , tasks);

        ServiceConnection mConnection = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                mReminder = ((ReminderService.ReminderBinder) service).getService();
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {

            }
        };

        bindService(intent , mConnection , BIND_AUTO_CREATE);
        startService(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //mTaskDbHelper.clearDatabase();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1)
            if(resultCode == Activity.RESULT_OK){
                if(data != null) {
                    Log.d(TAG, "onActivityResult: Result code : 1 , RESULT_OK");
                    Bundle extra = data.getExtras();
                    Task t = (Task) extra.get("Task");
                    tasks.add(t);
                    mAdapter.notifyDataSetChanged();


                    mTaskDbHelper.insert(t);
                    mReminder.updateTasks(t);
                }
            }

        if (requestCode == 2)
            if (resultCode == Activity.RESULT_OK) {
                if(data != null) {
                    Log.d(TAG, "onActivityResult: Result code : 2 , RESULT_OK");
                    Bundle extra = data.getExtras();
                    Task t = (Task) extra.get("Task");

                    mTaskDbHelper.deleteTask(t.getName());
                    mTaskDbHelper.insert(t);

                    Log.d(TAG, "onActivityResult: Updating tasks");
                    tasks.clear();
                    for(Task temp : mTaskDbHelper.readTasks()) {
                        tasks.add(temp);
                    }
                    mReminder.updateAllTasks(tasks);

                    Log.d(TAG, "onActivityResult: Notifing adapter");
                    mAdapter.notifyDataSetChanged();
                }
            } else if (resultCode == Activity.RESULT_CANCELED) {
                if (data != null) {
                    Log.d(TAG, "onActivityResult: Result code : 2 , RESULT_CANCELED");
                    Bundle extra = data.getExtras();
                    Task t = (Task) extra.get("Task");

                    mTaskDbHelper.deleteTask(t.getName());

                    Log.d(TAG, "onActivityResult: Updating tasks");
                    tasks.clear();
                    for(Task temp : mTaskDbHelper.readTasks()) {
                        Log.d(TAG, "onActivityResult: " + temp.toString());
                        tasks.add(temp);
                    }


                    mReminder.updateAllTasks(tasks);

                    Log.d(TAG, "onActivityResult: Notifing adapter");
                    mAdapter.notifyDataSetChanged();
                }
            }
    }
}
