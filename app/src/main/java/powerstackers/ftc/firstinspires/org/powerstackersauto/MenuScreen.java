package powerstackers.ftc.firstinspires.org.powerstackersauto;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.io.File;

import powerstackers.ftc.firstinspires.org.powerstackersauto.Paths.ConstantsLoader;
import powerstackers.ftc.firstinspires.org.powerstackersauto.Paths.PathPoints;

public class MenuScreen extends AppCompatActivity {

    Button btnMatches;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_screen);

        btnMatches = findViewById(R.id.btnMatches);
        btnMatches.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               matches();
            }
        });

        final Button btnSettings = findViewById(R.id.btnSettings);
        btnSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                settings();
            }
        });

        final Button btnClear = findViewById(R.id.btnClearData);
        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearData();
            }
        });

        final Button btnLoad = findViewById(R.id.btnLoadData);
        btnLoad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { PathPoints.createPointsFileOneTimeOnly();
            }
        });

        Context context = getApplicationContext();
        if (context.getPackageManager().checkPermission( Manifest.permission.WRITE_EXTERNAL_STORAGE, context.getPackageName()) != PackageManager.PERMISSION_GRANTED) {
            int requestCode = 200;
            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, requestCode);
        }else{
            createDir();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        createDir();
    }

    public void createDir(){
        try {
            File dir;
            dir = new File(FileResources.getDataPath());
            if (!dir.exists()) {
                dir.mkdirs();
                dir.createNewFile();
            }
            dir = new File(PathPoints.getPointsPath());
            if (!dir.exists()) {
                dir.createNewFile();
                PathPoints.createPointsFileOneTimeOnly();
            }
            dir = new File(ConstantsLoader.getPath());
            if (!dir.exists()) {
                dir.createNewFile();
            }
        }catch(Exception e ){
            e.printStackTrace();
        }
    }

    public void matches(){
        startActivity(new Intent(this, MatchesActivity.class));
    }

    public void settings(){
        startActivity(new Intent(this, Settings.class));
    }

    public void clearData(){
        FileResources.saveData(new String[]{"LOAD","DATA"});
    }
}
