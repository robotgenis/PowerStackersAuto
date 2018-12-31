package powerstackers.ftc.firstinspires.org.powerstackersauto;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Settings extends AppCompatActivity {

    EditText txtVel;
    EditText txtAcc;
    EditText txtRotationVel;
    EditText txtRotationAcc;
    EditText txtMotionP;
    EditText txtMotionI;
    EditText txtMotionD;
    EditText txtMotionV;
    EditText txtMotionA;
    EditText txtRotationP;
    EditText txtRotationI;
    EditText txtRotationD;
    EditText txtRotationV;
    EditText txtRotationA;
    
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        
        String[] load = FileResources.readSettings();

        txtVel = findViewById(R.id.txtVel);
        txtAcc = findViewById(R.id.txtAcc);
        txtRotationVel = findViewById(R.id.txtRotationVel);
        txtRotationAcc = findViewById(R.id.txtRotationAcc);
        txtMotionP = findViewById(R.id.txtMotionP);
        txtMotionI = findViewById(R.id.txtMotionI);
        txtMotionD = findViewById(R.id.txtMotionD);
        txtMotionV = findViewById(R.id.txtMotionV);
        txtMotionA = findViewById(R.id.txtMotionA);
        txtRotationP = findViewById(R.id.txtRotationP);
        txtRotationI = findViewById(R.id.txtRotationI);
        txtRotationD = findViewById(R.id.txtRotationD);
        txtRotationV = findViewById(R.id.txtRotationV);
        txtRotationA = findViewById(R.id.txtRotationA);

        if(load.length > 13) {
            txtVel.setText(load[0]);
            txtAcc.setText(load[1]);
            txtRotationVel.setText(load[2]);
            txtRotationAcc.setText(load[3]);
            txtMotionP.setText(load[4]);
            txtMotionI.setText(load[5]);
            txtMotionD.setText(load[6]);
            txtMotionV.setText(load[7]);
            txtMotionA.setText(load[8]);
            txtRotationP.setText(load[9]);
            txtRotationI.setText(load[10]);
            txtRotationD.setText(load[11]);
            txtRotationV.setText(load[12]);
            txtRotationA.setText(load[13]);
        }
        
        final Button btnSave = findViewById(R.id.btnSave);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                save();
            }
        });
    }
    
    public void save(){
        String[] save = new String[14];

        save[0] = txtVel.getText().toString();
        save[1] = txtAcc.getText().toString();
        save[2] = txtRotationVel.getText().toString();
        save[3] = txtRotationAcc.getText().toString();
        save[4] = txtMotionP.getText().toString();
        save[5] = txtMotionI.getText().toString();
        save[6] = txtMotionD.getText().toString();
        save[7] = txtMotionV.getText().toString();
        save[8] = txtMotionA.getText().toString();
        save[9] = txtRotationP.getText().toString();
        save[10] = txtRotationI.getText().toString();
        save[11] = txtRotationD.getText().toString();
        save[12] = txtRotationV.getText().toString();
        save[13] = txtRotationA.getText().toString();

        FileResources.saveSettings(save);

        startActivity(new Intent(this, MenuScreen.class));
    }
}
