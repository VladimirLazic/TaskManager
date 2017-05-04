package ra57_2014.pnrs1.rtrk.taskmanager.main;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        statistics = (Button) findViewById(R.id.statistics);
        newTask = (Button) findViewById(R.id.newTask);

        tasks = new ArrayList<>();

        //Dummy Tasks
        tasks.add(new Task("Dummy" , "" , "5/4/2017 18:12" , false, 3));
        tasks.add(new Task("Dummy" , "" , "5/4/2017 18:12" , false, 2));
        tasks.add(new Task("Dummy" , "" , "5/4/2017 18:12" , false, 1));
        tasks.add(new Task("Dummy" , "" , "5/4/2017 18:12" , false, 2));
        tasks.add(new Task("Dummy" , "" , "5/4/2017 18:12" , false, 1));
        tasks.add(new Task("Dummy" , "" , "5/4/2017 18:12" , false, 3));
        tasks.add(new Task("Dummy" , "" , "5/4/2017 18:12" , false, 2));
        tasks.add(new Task("Dummy" , "" , "5/4/2017 18:12" , false, 2));
        tasks.add(new Task("Dummy" , "" , "5/4/2017 18:12" , false, 2));

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
                startActivity(intent);
                return true;
            }
        });

        statistics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this , StatisticsActivity.class);
                int[] numberOfTasks = new int[3];

                for(int i : numberOfTasks) {
                    i = 0;
                }

                for(Task t : tasks) {
                    switch (t.getPriority()) {
                        case 1:
                            numberOfTasks[0]++;
                            break;
                        case 2:
                            numberOfTasks[1]++;
                            break;
                        case 3:
                            numberOfTasks[2]++;
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
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1)
            if(resultCode == Activity.RESULT_OK){
                if(data != null) {
                    Bundle extra = data.getExtras();
                    tasks.add((Task) extra.get("Task"));
                    mAdapter.notifyDataSetChanged();
                }
            }
    }
}
