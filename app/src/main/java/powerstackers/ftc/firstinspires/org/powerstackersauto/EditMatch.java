package powerstackers.ftc.firstinspires.org.powerstackersauto;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;

public class EditMatch extends AppCompatActivity {

    static String inputData = "";

    EditText txtName;

    //Items
    TextView dataTime;
    Switch dataPos;
    Switch dataHang;
    Switch dataSample;
    Switch dataSample2;
    Switch dataClaim;
    Switch dataPark;
    Switch dataPark2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_match);

        final Button btnSave = findViewById(R.id.btnSave);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                save();
            }
        });

        txtName = findViewById(R.id.txtName);


        //Items
        dataTime = findViewById(R.id.dataTime);
        dataPos = findViewById(R.id.dataPos);
        dataHang = findViewById(R.id.dataHang);
        dataSample = findViewById(R.id.dataSample);
        dataSample2 = findViewById(R.id.dataSample2);
        dataClaim = findViewById(R.id.dataClaim);
        dataPark = findViewById(R.id.dataPark);
        dataPark2 = findViewById(R.id.dataPark2);

        final Button btnTimeRemove = findViewById(R.id.btnTimeRemove);
        btnTimeRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double i = Double.valueOf(dataTime.getText().toString()) - 0.5;
                if(i < 0) i = 0;
                dataTime.setText(String.valueOf(i));
            }
        });

        final Button btnTimeAdd = findViewById(R.id.btnTimeAdd);
        btnTimeAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double i = Double.valueOf(dataTime.getText().toString()) + 0.5;
                if(i > 30) i = 30;
                dataTime.setText(String.valueOf(i));
            }
        });

        //Items
        if(!inputData.equals("")){
            String[] data = inputData.split(",");

            txtName.setText(data[0].split("=")[1]);

            dataTime.setText(data[1].split("=")[1]);
            dataPos.setChecked(Boolean.valueOf(data[2].split("=")[1]));
            dataHang.setChecked(Boolean.valueOf(data[3].split("=")[1]));
            dataSample.setChecked(Boolean.valueOf(data[4].split("=")[1]));
            dataSample2.setChecked(Boolean.valueOf(data[5].split("=")[1]));
            dataClaim.setChecked(Boolean.valueOf(data[6].split("=")[1]));
            dataPark.setChecked(Boolean.valueOf(data[7].split("=")[1]));
            dataPark2.setChecked(Boolean.valueOf(data[8].split("=")[1]));
        }

        final Button btnLoad = findViewById(R.id.btnLoad);
        btnLoad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { loadMatch();
            }
        });

        final Button btnDelete = findViewById(R.id.btnDelete);
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { delete();
            }
        });

        final Button btnPreview = findViewById(R.id.btnPreview);
        btnPreview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                preview();
            }
        });
    }

    public void preview(){
        Preview.data = getData();
        startActivity(new Intent(this, Preview.class));
    }

    public String getData(){
        //Items
        String name = txtName.getText().toString();
        String r = "name=" + name;
        r += ",time=" + dataTime.getText().toString();
        r += ",pos=" + String.valueOf(dataPos.isChecked());
        r += ",hang=" + String.valueOf(dataHang.isChecked());
        r += ",sample=" + String.valueOf(dataSample.isChecked());
        r += ",sample2=" + String.valueOf(dataSample2.isChecked());
        r += ",claim=" + String.valueOf(dataClaim.isChecked());
        r += ",park=" + String.valueOf(dataPark.isChecked());
        r += ",park2=" + String.valueOf(dataPark2.isChecked());

        return r;
    }

    public void save(){
        String newData = getData();

        String name = txtName.getText().toString();

        String[] file = FileResources.readData();

        if(!name.equals("")) {
            if (file[1].contains(":")) {
                String[] data = file[1].split(":");

                boolean replace = true;
                for (int i = 1; i < data.length; i++) {
                    if (data[i].split(",")[0].split("=")[1].equals(name)) {
                        data[i] = newData;
                        replace = false;
                    }
                }

                if (replace) {
                    ArrayList<String> arrayList = new ArrayList(Arrays.asList(data));
                    arrayList.add(newData);
                    data = arrayList.toArray(new String[]{});
                }

                file[1] = "DATA";

                for (String s : data) {
                    if (!s.equals("DATA"))
                        file[1] += ":" + s;
                }
            } else {
                file[1] = "DATA:" + newData;
            }

            FileResources.saveData(file);

            makeToast(this, name +" saved!");

            back();
        }else{
            makeToast(this,"You must give the configuration a name!");
        }
    }

    public void loadMatch(){
        String[] file = FileResources.readData();

        String name = txtName.getText().toString();

        file[0] = "LOAD:" + getData();

        FileResources.saveData(file);

        makeToast(this, name + " loaded!");
    }

    public void delete(){
        String[] file = FileResources.readData();

        String name = txtName.getText().toString();

        if(file[1].contains(":")) {
            String ret = "DATA";

            String[] data = file[1].split(":");

            for(int i = 1; i < data.length; i++){
                if(!(data[i].split(",")[0].split("=")[1].equals(name))){
                    ret += ":" + data[i];
                }
            }

            file[1] = ret;

            FileResources.saveData(file);
        }

        back();
    }

    public void back(){
        startActivity(new Intent(this, MatchesActivity.class));
    }

    public static void makeToast(Context context, String msg){
        Toast t = Toast.makeText(context, msg, Toast.LENGTH_SHORT);
        t.show();
    }

}
