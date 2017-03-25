package ra57_2014.pnrs1.rtrk.taskmanager.newtask;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

import ra57_2014.pnrs1.rtrk.taskmanager.MainActivity;
import ra57_2014.pnrs1.rtrk.taskmanager.R;


public class NewTaskActivity extends AppCompatActivity implements NewTaskModel.View {

    EditText taskName , taskDescription;
    EditText taskTime;
    CheckBox reminder;
    Button red, green, yellow, add , cancel;
    NewTaskPresenter presenter;
    int priorityButton = 0;
    ArrayList<Task> listOfTasks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_task);

        presenter = new NewTaskPresenter(this);
        listOfTasks = new ArrayList<Task>();
        initView();

        red.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                priorityButton = 3;
            }
        });

        yellow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                priorityButton = 2;
            }
        });

        green.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                priorityButton = 1;
            }
        });


        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(taskName.getText() != null && taskTime.getText() != null && priorityButton != 0) {
                    presenter.addTask(taskName.getText().toString(),
                            taskDescription.getText().toString(),
                            reminder.isChecked(),
                            priorityButton);
                } else {
                    showMessage("Not enough data!");
                }

            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                priorityButton = 0;

                taskName.getText().clear();
                taskDescription.getText().clear();
                taskTime.getText().clear();
                reminder.setChecked(false);
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(NewTaskActivity.this , MainActivity.class);
        startActivity(intent);
        finish();
    }

    private void initView() {
        taskName = (EditText) findViewById(R.id.taskName);
        taskDescription = (EditText) findViewById(R.id.taskDescription);
        taskTime = (EditText) findViewById(R.id.taskTime);

        reminder = (CheckBox) findViewById(R.id.reminder);

        red = (Button) findViewById(R.id.red);
        yellow = (Button) findViewById(R.id.yellow);
        green = (Button) findViewById(R.id.green);
        add = (Button) findViewById(R.id.add);
        cancel = (Button) findViewById(R.id.cancel);
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(this , message , Toast.LENGTH_LONG).show();
    }

    @Override
    public void updateList(Task t) {
        listOfTasks.add(t);
        Toast.makeText(this , "List updated!" , Toast.LENGTH_LONG).show();
    }
}
