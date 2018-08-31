package com.example.kuasmis.fishingmonitor;

import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import data.DataGetter;
import data.DataGetterManager;
import data.MiDataRecorder;


public class MainActivity extends Activity {

    private final static long DELAY = 100;

    private int STATE_ON_COLOR;
    private int STATE_OFF_COLOR;
    private int START_COLOR;
    private int STOP_COLOR;
    private String START_TEXT;
    private String STOP_TEXT;
    private String STATE_MSG_1;
    private String STATE_MSG_2;
    private String STATE_MSG_3;
    private String STATE_MSG_4;
    private String STATE_MSG_5;
    private Button currentStateButton;
    private boolean isStart = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loadResources();

        FrameLayout chartLayout = (FrameLayout) findViewById(R.id.chart_layout);
        final SensorChart chart = new SensorChart(this, DataGetterManager.getMiDataGetter());
        chartLayout.addView(chart.getChartView());

        final Handler handler = new Handler() {
            @Override
            public void handleMessage(Message message) {
                chart.update();
            }
        };

        final ChartTimer timer = new ChartTimer(handler, DELAY);
        timer.start();

        final MiDataRecorder dataRecorder = DataGetterManager.getMiDataRecorder();
        dataRecorder.setLevel(1);

        final Button saveButton = (Button) findViewById(R.id.save_button);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    String fileName = dateFormat.format(new Date()) + ".csv";
                    String recordPath = dataRecorder.save(fileName);
                    Toast.makeText(MainActivity.this, "Saved record to " + recordPath, Toast.LENGTH_SHORT).show();
                    Log.e("IO", "Saved record to " + recordPath);
                } catch (IOException e) {
                    Log.e("IOError", e.getMessage());
                    Toast.makeText(MainActivity.this, "Failed to save record", Toast.LENGTH_SHORT).show();
                    Log.e("IO", "Failed to save record");
                }
            }
        });

        final Button startButton = (Button) findViewById(R.id.start_button);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isStart) {
                    dataRecorder.stop();
                    startButton.setText(START_TEXT);
                    startButton.setBackgroundColor(START_COLOR);
                    saveButton.setEnabled(true);
                    isStart = false;
                } else {
                    dataRecorder.restart();
                    startButton.setText(STOP_TEXT);
                    startButton.setBackgroundColor(STOP_COLOR);
                    saveButton.setEnabled(false);
                    isStart = true;
                }
            }
        });

        final TextView stateText = (TextView) findViewById(R.id.state_text);
        final Button s1Button = (Button) findViewById(R.id.s1_button);
        final Button s2Button = (Button) findViewById(R.id.s2_button);
        final Button s3Button = (Button) findViewById(R.id.s3_button);
        final Button s4Button = (Button) findViewById(R.id.s4_button);
        final Button s5Button = (Button) findViewById(R.id.s5_button);
        currentStateButton = s1Button;
        currentStateButton.setBackgroundColor(STATE_ON_COLOR);
        View.OnClickListener stateButtonOnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentStateButton.setBackgroundColor(STATE_OFF_COLOR);
                if (v == s1Button) {
                    dataRecorder.setLevel(1);
                    stateText.setText(STATE_MSG_1);
                } else if (v == s2Button) {
                    dataRecorder.setLevel(2);
                    stateText.setText(STATE_MSG_2);
                } else if (v == s3Button) {
                    dataRecorder.setLevel(3);
                    stateText.setText(STATE_MSG_3);
                } else if (v == s4Button) {
                    dataRecorder.setLevel(4);
                    stateText.setText(STATE_MSG_4);
                } else if (v == s5Button) {
                    dataRecorder.setLevel(5);
                    stateText.setText(STATE_MSG_5);
                }
                currentStateButton = (Button) v;
                currentStateButton.setBackgroundColor(STATE_ON_COLOR);
            }
        };

        s1Button.setOnClickListener(stateButtonOnClickListener);
        s2Button.setOnClickListener(stateButtonOnClickListener);
        s3Button.setOnClickListener(stateButtonOnClickListener);
        s4Button.setOnClickListener(stateButtonOnClickListener);
        s5Button.setOnClickListener(stateButtonOnClickListener);



    }

    private void loadResources() {
        STATE_ON_COLOR = getResources().getColor(R.color.state_on_color);
        STATE_OFF_COLOR = getResources().getColor(R.color.state_off_color);
        START_COLOR = getResources().getColor(R.color.start_color);
        STOP_COLOR = getResources().getColor(R.color.stop_color);
        START_TEXT = getResources().getString(R.string.start);
        STOP_TEXT = getResources().getString(R.string.stop);
        STATE_MSG_1 = getResources().getString(R.string.state_1_msg);
        STATE_MSG_2 = getResources().getString(R.string.state_2_msg);
        STATE_MSG_3 = getResources().getString(R.string.state_3_msg);
        STATE_MSG_4 = getResources().getString(R.string.state_4_msg);
        STATE_MSG_5 = getResources().getString(R.string.state_5_msg);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
