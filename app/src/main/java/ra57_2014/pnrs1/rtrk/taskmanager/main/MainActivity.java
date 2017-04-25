package ra57_2014.pnrs1.rtrk.taskmanager.main;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

import ra57_2014.pnrs1.rtrk.taskmanager.R;
import ra57_2014.pnrs1.rtrk.taskmanager.StatisticsActivity;
import ra57_2014.pnrs1.rtrk.taskmanager.newtask.NewTaskActivity;
import ra57_2014.pnrs1.rtrk.taskmanager.newtask.Task;

public class MainActivity extends AppCompatActivity {

    Button statistics , newTask;
    TaskElementAdapter mAdapter;
    ArrayList<Task> tasks;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        statistics = (Button) findViewById(R.id.statistics);
        newTask = (Button) findViewById(R.id.newTask);

        tasks = new ArrayList<>();
        mAdapter = new TaskElementAdapter(this , tasks);

        final ListView list = (ListView) findViewById(R.id.listView);
        list.setAdapter(mAdapter);

        //Dummy elements
        mAdapter.add(new Task("Dummy one" , "", "25/4/2017" , false , 1));
        mAdapter.add(new Task("Dummy two" , "", "25/4/2017" , false , 2));
        mAdapter.add(new Task("Dummy three" , "", "25/4/2017" , false , 3));
        mAdapter.add(new Task("Dummy 4" , "", "26/4/2017" , false , 1));
        mAdapter.add(new Task("Dummy 5" , "", "27/4/2017" , false , 2));
        mAdapter.add(new Task("Dummy 6" , "", "28/4/2017" , false , 3));
        mAdapter.add(new Task("Dummy 7" , "", "27/4/2017" , false , 1));
        mAdapter.add(new Task("Dummy 8" , "", "28/4/2017" , false , 2));
        mAdapter.add(new Task("Dummy 8" , "", "26/4/2017" , false , 3));
        mAdapter.add(new Task("Dummy 10" , "", "25/4/2017" , false , 1));


        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            Task newListItem = ((Task) extras.get("Task"));
            mAdapter.add(newListItem);
        }

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
                startActivity(intent);
            }
        });

        newTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this , NewTaskActivity.class);
                intent.putExtra("Left" , getResources().getString(R.string.add));
                intent.putExtra("Right" , getResources().getString(R.string.cancel));
                startActivity(intent);
            }
        });
    }
}
