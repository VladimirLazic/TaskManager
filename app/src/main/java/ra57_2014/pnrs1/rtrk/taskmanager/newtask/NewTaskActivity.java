package ra57_2014.pnrs1.rtrk.taskmanager.newtask;

import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;

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
                yellow.setEnabled(false);
                green.setEnabled(false);

                if(!taskName.getText().toString().isEmpty() && !taskTime.getText().toString().isEmpty())
                    add.setEnabled(true);
            }
        });

        yellow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                priorityButton = 2;
                red.setEnabled(false);
                green.setEnabled(false);

                if(!taskName.getText().toString().isEmpty() && !taskTime.getText().toString().isEmpty())
                    add.setEnabled(true);
            }
        });

        green.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                priorityButton = 1;
                red.setEnabled(false);
                yellow.setEnabled(false);

                if(!taskName.getText().toString().isEmpty() && !taskTime.getText().toString().isEmpty())
                    add.setEnabled(true);
            }
        });

        taskName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                if(!taskTime.getText().toString().isEmpty() && priorityButton != 0)
                    add.setEnabled(true);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!taskTime.getText().toString().isEmpty() && priorityButton != 0)
                    add.setEnabled(true);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        taskTime.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Calendar currentTime = Calendar.getInstance();
                int hour , minute;

                if(taskTime.getText().toString().isEmpty()) {
                    hour = currentTime.get(Calendar.HOUR_OF_DAY);
                    minute = currentTime.get(Calendar.MINUTE);
                } else {
                    String time[] = taskTime.getText().toString().split(":");
                    hour = Integer.parseInt(time[0]);
                    minute = Integer.parseInt(time[1]);
                }

                TimePickerDialog time = new TimePickerDialog(NewTaskActivity.this , new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        taskTime.setText(hourOfDay + ":" + minute);
                    }
                }, hour , minute , true);
                time.setTitle("Select the time");
                time.show();

                return false;
            }
        });


        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.addTask(taskName.getText().toString(),
                        taskDescription.getText().toString(),
                        reminder.isChecked(),
                        priorityButton);
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

                add.setEnabled(false);

                red.setEnabled(true);
                yellow.setEnabled(true);
                green.setEnabled(true);
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

        add.setEnabled(false);
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(this , message , Toast.LENGTH_LONG).show();
    }

    @Override
    public void updateList(Task t) {
        listOfTasks.add(t);
        Toast.makeText(this , "List updated!" , Toast.LENGTH_LONG).show();

        Intent intent = new Intent(NewTaskActivity.this , MainActivity.class);
        startActivity(intent);
        finish();
    }
}
