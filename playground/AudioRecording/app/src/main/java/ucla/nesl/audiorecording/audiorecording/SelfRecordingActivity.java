package ucla.nesl.audiorecording.audiorecording;

import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import java.io.File;
import java.io.IOException;

public class SelfRecordingActivity extends AppCompatActivity {

    private Button recordButton;
    private Button playButton;

    private MediaRecorder mediaRecorder;
    private MediaPlayer mediaPlayer;

    private String voiceFilePath;

    private boolean isRecording = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_self_recording);

        voiceFilePath = Environment.getExternalStorageDirectory().getAbsolutePath()
                + File.separator + "selfRecording.3gpp";

        recordButton = (Button)findViewById(R.id.buttonRecord);
        playButton = (Button)findViewById(R.id.buttonPlay);
        recordButton.setOnClickListener(recordClickEventListener);
        playButton.setOnClickListener(playClickEventListener);

        File audioVoice = new File(voiceFilePath);
        if (!audioVoice.exists()) {
            playButton.setEnabled(false);
        }

        initializeMediaRecord();
    }

    // ---- initialization functions -------------------------------------------------------------
    private void initializeMediaRecord(){
        mediaRecorder = new MediaRecorder();
        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        mediaRecorder.setAudioEncoder(MediaRecorder.OutputFormat.AMR_NB);
        mediaRecorder.setOutputFile(voiceFilePath);
    }

    // ---- button callbacks --------------------------------------------------------------------
    private final View.OnClickListener recordClickEventListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (!isRecording) {
                initializeMediaRecord();
                startAudioRecording();
                recordButton.setText("Stop recording");
                isRecording = true;
            } else {
                stopAudioRecording();
                playButton.setEnabled(true);
                recordButton.setText("Start recording");
                isRecording = false;
            }
        }
    };

    private final View.OnClickListener playClickEventListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            playAudioMusic();
            //mediaPlayerPlaying();
        }
    };

    // ---- audio related -----------------------------------------------------------------------
    private void startAudioRecording() {
        try {
            mediaRecorder.prepare();
            mediaRecorder.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void stopAudioRecording() {
        if (mediaRecorder != null){
            mediaRecorder.stop();
            mediaRecorder.release();
            mediaRecorder = null;
        }
    }

    private void playAudioMusic() {
        mediaPlayer = new MediaPlayer();
        try {
            //mediaPlayer.setDataSource(voiceFilePath);
            mediaPlayer.setDataSource(Environment.getExternalStorageDirectory().getAbsolutePath()
                    + File.separator + "hallowed.mp3");
            mediaPlayer.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }

        mediaPlayer.start();
    }

    private void stopAudioPlay() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }
}
