package powerstackers.ftc.firstinspires.org.powerstackersauto;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

public class MatchesActivity extends AppCompatActivity {

    ListView lst;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matches);

        lst = findViewById(R.id.lstItems);


        ArrayList<String> arrayList = new ArrayList<String>();

        String[] file = FileResources.readData();
        String data = file[1];

        if(data.contains(":")){
            String[] sort = data.split(":");
            for(int i = 1; i < sort.length; i++){
                arrayList.add(sort[i].split(",")[0].split("=")[1]);
            }
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1 , arrayList);//android.R.layout.simple_list_content
        lst.setAdapter(adapter);


        final Button btnNew = findViewById(R.id.btnNew);
        btnNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { newMatch();
            }
        });

        final Button btnMenu = findViewById(R.id.btnMenu);
        btnMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { menu();
            }
        });

        lst.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                editMatch(position);
            }
        });
    }

    public void newMatch(){
        EditMatch.inputData = "";
        startEditMatch();
    }

    public void editMatch(int k){
        String s = lst.getItemAtPosition(k).toString();


        EditMatch.inputData = "";

        String[] file = FileResources.readData();
        if(file[1].contains(":")) {
            String[] data = file[1].split(":");

            String ret = "";

            for (int i = 1; i < data.length; i++) {
                if(data[i].split(",")[0].split("=")[1].equals(s)){
                    ret = data[i];
                }
            }

            EditMatch.inputData = ret;
        }

        startEditMatch();
    }

    public void startEditMatch(){
        startActivity(new Intent(this, EditMatch.class));
    }

    public void menu(){
        startActivity(new Intent(this, MenuScreen.class));
    }
}
