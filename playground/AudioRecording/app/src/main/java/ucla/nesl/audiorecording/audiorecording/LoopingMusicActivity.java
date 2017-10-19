package ucla.nesl.audiorecording.audiorecording;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import java.io.File;
import java.io.IOException;

public class LoopingMusicActivity extends AppCompatActivity {

    private MediaPlayer mediaPlayer;

    private String voiceFilePath;

    private Button playButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_looping_music);

        voiceFilePath = Environment.getExternalStorageDirectory().getAbsolutePath()
                + File.separator + "hallowed.mp3";

        playButton = (Button) findViewById(R.id.buttonPlay);
        playButton.setOnClickListener(playClickEventListener);
    }

    // ---- button callbacks --------------------------------------------------------------------
    private final View.OnClickListener playClickEventListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            mediaPlayer = new MediaPlayer();
            try {
                mediaPlayer.setDataSource(voiceFilePath);
                mediaPlayer.prepare();
            } catch (IOException e) {
                e.printStackTrace();
            }

            mediaPlayer.setLooping(true);
            mediaPlayer.start();
        }
    };

}
