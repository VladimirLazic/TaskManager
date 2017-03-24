package ra57_2014.pnrs1.rtrk.taskmanager;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button statistics , newTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        statistics = (Button) findViewById(R.id.statistics);
        newTask = (Button) findViewById(R.id.newTask);

        statistics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this , StatisticsActivity.class);
                startActivity(intent);
                finish();
            }
        });

        newTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this , NewTaskActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
