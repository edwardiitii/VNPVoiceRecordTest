package ouhk.y2015.smartfyp.vnpvoicerecordtest;

import android.media.MediaRecorder;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Environment;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.File;

public class MainActivity extends ActionBarActivity {
    private Button btnRecord;
    private MediaPlayer playSound;
    private MediaRecorder recordSound;
    private String soundFile;
    private TextView txtRecord;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       // playSound = MediaPlayer.create(this, R.raw.song);
        soundFile = Environment.getExternalStorageDirectory() + "/testSound.3gpp";
        txtRecord = (TextView)findViewById(R.id.txtRecord);
        btnRecord = (Button) findViewById(R.id.btnRecord);
        btnRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (btnRecord.getText().toString().equals("Record")) {
                    try {
                        startRecord();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    txtRecord.setText("recording ... ");
                    btnRecord.setText("end");
                } else if (btnRecord.getText().toString().equals("end")) { //stop recording
                    stopRecord();
                    btnRecord.setText("Play");
                    txtRecord.setText("");


                } else if (btnRecord.getText().toString().equals("Play")) { // start playback
                    try {
                        startPlayBack();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    btnRecord.setText("Stop");
                } else { //stop playback
                    stopPlayBack();
                    btnRecord.setText("Record");
                }

            }
        });
        // btnPlay = (Button)findViewById(R.id.btnPlay);


    }

    public void startRecord() throws Exception {
        if (recordSound != null) {
            recordSound.release();
        }
        File fileOut = new File(soundFile);
        if (fileOut != null) {
            fileOut.delete(); //overwrite the existing file
        }
        recordSound = new MediaRecorder();
        recordSound.setAudioSource(MediaRecorder.AudioSource.MIC);
        recordSound.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        recordSound.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
        recordSound.setOutputFile(soundFile); // file path

        recordSound.prepare();
        recordSound.start();
    }

    public void stopRecord() {
        recordSound.stop();
        recordSound.release();
    }

    public void startPlayBack() throws Exception {
        if (playSound != null) {
            playSound.stop();
            playSound.release();
        }

        playSound = new MediaPlayer();
        playSound.setDataSource(soundFile);
        playSound.prepare();
        playSound.start();
        playSound.setOnCompletionListener(new OnCompletionListener() {
            public void onCompletion(MediaPlayer playSound) {
                playSound.release();
            }
        });

    }


    public void stopPlayBack() {


    }
}

