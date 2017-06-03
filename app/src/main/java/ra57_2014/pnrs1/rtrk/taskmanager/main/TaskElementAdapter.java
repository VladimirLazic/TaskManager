package ra57_2014.pnrs1.rtrk.taskmanager.main;

import android.content.Context;
import android.graphics.Paint;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import ra57_2014.pnrs1.rtrk.taskmanager.R;
import ra57_2014.pnrs1.rtrk.taskmanager.newtask.Task;

/**
 * Created by ironm on 4/24/2017.
 */

public class TaskElementAdapter extends ArrayAdapter<Task> {

    ArrayList<Task> tasks;

    public TaskElementAdapter(Context context , @NonNull List<Task> objects) {
        super(context, R.layout.task_element, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View elementView = inflater.inflate(R.layout.task_element , parent , false);

        final Task currentElement = getItem(position);

        final TextView elementName = (TextView) elementView.findViewById(R.id.elementName);
        elementName.setText(currentElement.getName());

        final TextView elementPrioriy = (TextView) elementView.findViewById(R.id.elementPriority);
        switch (currentElement.getPriority()) {
            case 1:
                elementPrioriy.setBackgroundResource(R.color.colorGreen);
                break;
            case 2:
                elementPrioriy.setBackgroundResource(R.color.colorYellow);
                break;
            case 3:
                elementPrioriy.setBackgroundResource(R.color.colorRed);
                break;
        }

        final CheckBox taskEnd = (CheckBox) elementView.findViewById(R.id.checkBox);
        taskEnd.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(taskEnd.isChecked()) {
                    elementName.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
                    currentElement.setDone(true);
                } else {
                    elementName.setPaintFlags(Paint.LINEAR_TEXT_FLAG);
                    currentElement.setDone(false);
                }
            }
        });

        RadioButton time = (RadioButton) elementView.findViewById(R.id.radioButton);

        Date currentDate = new Date();
        String datePattern = "dd/MM/yyyy";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(datePattern);
        Date selectedDate = null;

        String dateAndTime[] = currentElement.getTime().split(" ");
        String justTheDate[] = dateAndTime[0].split("/");

        try {
            selectedDate = simpleDateFormat.parse(justTheDate[0]+"/"+justTheDate[1]+"/"+justTheDate[2]);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if(currentDate.getYear() == selectedDate.getYear()) {
            if(currentDate.getMonth() == selectedDate.getMonth()) {
                if(currentDate.getDay() == selectedDate.getDay()) {
                    time.setText(R.string.today);
                } else if((currentDate.getTime()+1*24*60*60*1000) > selectedDate.getTime()) {
                    time.setText(R.string.tommorow);
                } else if((currentDate.getTime()+7*24*60*60*1000) > selectedDate.getTime()) {
                    String weekDay;
                    Locale RS = new Locale.Builder().setLanguage("sr").setScript("Latn").setRegion("RS").build();
                    SimpleDateFormat dayFormat = new SimpleDateFormat("EEEE", RS);

                    Calendar calendar = Calendar.getInstance();
                    weekDay = dayFormat.format(selectedDate.getTime());
                    time.setText(weekDay);
                } else {
                    time.setText(simpleDateFormat.format(selectedDate));
                }
            }
            else {
                time.setText(simpleDateFormat.format(selectedDate));
            }
        } else {
            time.setText(simpleDateFormat.format(selectedDate));
        }

        return elementView;
    }
}
