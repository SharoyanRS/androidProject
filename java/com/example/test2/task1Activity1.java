package com.example.test2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import android.Manifest;
import android.content.Intent;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Environment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import java.io.File;


public class task1Activity1 extends AppCompatActivity {
    params experiments = new params();
    private int a = (int) ( Math.random() * 1001 );
    private int max=0;
    int iterator=0;
    boolean myBool=false;
    private double massa_gr=0;
    private int start_h=0;
    private int volume_barrier=20000;
    private TextView hit_sec;
    private TextView display_content;
    private TextView microTip;
    String fileName = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC).getAbsolutePath() + "/Temp_BGPU_"+a+"_heat.wav";
    String tempStr="";
    CountDownTimer myTimer;
    CountDownTimer my2Timer;
    private MediaRecorder mediaRecorder;
    private boolean isRecording=false;
    private int current_volume=1;
    private long hit_duration=0;
    SeekBar micSetting;
    Button startRecording,deleteOne,deleteAll,repeatRecord,goGraph,addMass,stopRecording;
    EditText massa;
    EditText visota;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        String[] permissions = { Manifest.permission.RECORD_AUDIO,Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE };
        ActivityCompat.requestPermissions(this, permissions, 124);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task1activity1);
        startRecording = (Button) findViewById(R.id.startrecording);
        goGraph=(Button) findViewById(R.id.go_graph);
        deleteOne = (Button) findViewById(R.id.delete);
        deleteAll=(Button) findViewById(R.id.clear);
        repeatRecord = (Button) findViewById(R.id.repeat_record);
        addMass = (Button) findViewById(R.id.addm);
        stopRecording = (Button) findViewById(R.id.stopRecordButton);
        micSetting = (SeekBar) findViewById(R.id.mic_setting);
        massa = (EditText) findViewById(R.id.massa);
        visota = (EditText) findViewById(R.id.visota);
        display_content=(TextView) findViewById(R.id.display_content);
        microTip = (TextView) findViewById(R.id.microTip);
        goGraph.setOnClickListener(click);
        startRecording.setOnClickListener(click);
        stopRecording.setOnClickListener(click);
        deleteOne.setOnClickListener(click);
        deleteAll.setOnClickListener(click);
        repeatRecord.setOnClickListener(click);
        addMass.setOnClickListener(click);
        micSetting.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {}
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {volume_barrier=micSetting.getProgress(); Toast.makeText(task1Activity1.this, "Порог считывания звука "+volume_barrier/1000+"у.е.", Toast.LENGTH_SHORT).show();}
        });
        massa.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void afterTextChanged(Editable editable) { if(!massa.getText().toString().isEmpty()) { massa_gr=new Double(massa.getText().toString()).doubleValue();}}
        });
        visota.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void afterTextChanged(Editable editable) { if(!visota.getText().toString().isEmpty()) {start_h=new Integer(visota.getText().toString()).intValue();}}
        });
    }







    private final View.OnClickListener click = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.startrecording:
                    experiments.clear();
                    if(!(visota.getText().toString().isEmpty())&&!(massa.getText().toString().isEmpty())) start(); else
                        Toast.makeText(task1Activity1.this, "Введите параметры", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.repeat_record:
                    if(!(visota.getText().toString().isEmpty())&&!(massa.getText().toString().isEmpty())) start(); else
                        Toast.makeText(task1Activity1.this, "Введите параметры", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.clear:
                    clear();
                    break;
                case R.id.delete:
                    delete();
                    break;
                case R.id.go_graph:    if(iterator+1>=2)goExperiment(); else Toast.makeText(task1Activity1.this, "Необходимо хотя-бы 2 опыта", Toast.LENGTH_SHORT).show(); break;
                case R.id.addm: massa_gr+=5;if(!massa.getText().toString().isEmpty())massa.setText(String.valueOf(Double.parseDouble(String.valueOf(massa.getText()))+5));
                else {massa_gr=5;massa.setText(String.valueOf(massa_gr));};break;
                case R.id.stopRecordButton: stop();myTimer.cancel();break;
                default:
                    break;
            }
        }
    };



    public void goExperiment(){
        Intent intent = new Intent(this, graphics.class);
        intent.putExtra("massArray",experiments.getAllMasses());
        intent.putExtra("qArray",experiments.getAllQ());
        intent.putExtra("amount",experiments.getSize());
        startActivity(intent);
    }

    public void clear(){
        iterator=0;
        hit_sec.setText("0.0 sec");
        addMass.setClickable(true);
        experiments.clear();
        tempStr="";
        display_content.setText(tempStr);
        visota.setEnabled(true);
    }

    public void delete(){
        hit_sec.setText("0.0 sec");
        if(!experiments.isEmpty()) {
            addMass.setClickable(true);
            iterator--;
            String[] parts = tempStr.split("\\s+");
            tempStr = "";
            for (int i = 0; i < parts.length - 4; i++) {
                tempStr += parts[i] + "  ";
                if ((i + 1) % 4 == 0) tempStr += '\n';
            }
            display_content.setText(tempStr);
            experiments.deleteLast();
        }
    }


    public void start(){
        goGraph.setVisibility(View.INVISIBLE);
        addMass.setClickable(false);
        goGraph.setClickable(false);
        deleteAll.setClickable(false);
        deleteOne.setClickable(false);
        startRecording.setClickable(false);
        repeatRecord.setClickable(false);
        microTip.setVisibility(View.VISIBLE);
        stopRecording.setClickable(true);
        stopRecording.setVisibility(View.VISIBLE);
        massa.setEnabled(false);
        visota.setEnabled(false);
        micSetting.setEnabled(false);
        startRecording.setVisibility(View.INVISIBLE);
        deleteOne.setVisibility(View.INVISIBLE);
        deleteAll.setVisibility(View.INVISIBLE);
        repeatRecord.setVisibility(View.INVISIBLE);
        try {
            File file = new File(fileName);
            if (file.exists()) {
               file.delete();
            }
            hit_sec = (TextView) findViewById(R.id.hit_secs);
            hit_sec.setText("0.0 sec");
            mediaRecorder = new MediaRecorder();
            mediaRecorder.setAudioSource(MediaRecorder.AudioSource.VOICE_RECOGNITION);
            mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.AAC_ADTS);
            mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);
            mediaRecorder.setOutputFile(fileName);
            mediaRecorder.setOnErrorListener(new MediaRecorder.OnErrorListener() {
                @Override
                public void onError(MediaRecorder mediaRecorder, int what, int extra) {
                    mediaRecorder.stop();
                    mediaRecorder.release();
                    mediaRecorder = null;
                    isRecording = false;
                    Toast.makeText(task1Activity1.this, "Error recording", Toast.LENGTH_SHORT).show();
                }
            });
            mediaRecorder.prepare();
            mediaRecorder.start();
            isRecording = true;
            myTimer = new CountDownTimer(200000,5){
                @Override
                public void onTick(long l) {
                    current_volume=mediaRecorder.getMaxAmplitude();
                    if(current_volume>max) max = current_volume;
                    if(max>volume_barrier){
                        myTimer.cancel();
                        myBool=true;
                        my2Timer=new CountDownTimer(200000, 5) {
                            @Override
                            public void onTick(long p) {
                                current_volume=mediaRecorder.getMaxAmplitude();
                                hit_duration=200000-p;
                                if(current_volume>volume_barrier&&hit_duration>60)  {
                                    stop();
                                    experiments.add(massa_gr,hit_duration,start_h);
                                    tempStr+="#"+(iterator+1)+"  "+massa_gr+"гр  "+experiments.getQstring()+"  Дж\n";
                                    iterator++;
                                    display_content.setText(tempStr);
                                }
                                hit_sec.setText(""+hit_duration/1000.+" sec");
                            }
                            @Override
                            public void onFinish() {}
                        }.start();
                    }
                }

                @Override
                public void onFinish() {

                }
            }.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void stop(){
        goGraph.setVisibility(View.VISIBLE);
        stopRecording.setClickable(false);
        goGraph.setClickable(true);
        stopRecording.setVisibility(View.INVISIBLE);
        microTip.setVisibility(View.INVISIBLE);
        addMass.setClickable(true);
        goGraph.setClickable(true);
        goGraph.setVisibility(View.VISIBLE);
        massa.setEnabled(true);
        micSetting.setEnabled(true);
        deleteOne.setClickable(true);
        deleteAll.setClickable(true);
        deleteOne.setVisibility(View.VISIBLE);
        deleteAll.setVisibility(View.VISIBLE);
        repeatRecord.setVisibility(View.VISIBLE);
        repeatRecord.setClickable(true);
        max=0;
        current_volume=0;
        if(myBool)my2Timer.cancel();
        if(isRecording){
            mediaRecorder.stop();
            mediaRecorder.release();
            mediaRecorder=null;
            isRecording=false;
            File file = new File(fileName);
            if (file.exists()) {
                file.delete();
            }
        }
    }

    @Override
    protected void onDestroy(){
        if (isRecording){
            mediaRecorder.stop();
            mediaRecorder.release();
            mediaRecorder=null;
            File file = new File(fileName);
            if (file.exists()) {
                file.delete();
            }
        }
        super.onDestroy();
    }
}