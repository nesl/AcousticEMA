package ucla.nesl.audiorecording.audiorecording;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button recordButton;
    private Button loopButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        permissionSelfCheck();

        recordButton = (Button)findViewById(R.id.buttonRecord);
        loopButton = (Button)findViewById(R.id.buttonPlay);

        recordButton.setOnClickListener(recordClickEventListener);
        //playButton.setOnClickListener(playClickEventListener);

    }

    // ---- button callbacks --------------------------------------------------------------------
    private final View.OnClickListener recordClickEventListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(MainActivity.this, SelfRecordingActivity.class);
            startActivity(intent);
        }
    };

    private final View.OnClickListener playClickEventListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

        }
    };

    // ---- permission --------------------------------------------------------------------------
    private void permissionSelfCheck() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.RECORD_AUDIO}, 0);
        }
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        }
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 2);
        }
    }
}
