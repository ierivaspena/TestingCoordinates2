package rivas.itzel.com.testingcoordinates;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

public class SelectIncident extends AppCompatActivity {

    /*
    Database elements
     */
    private double lat, lon;
    private String incident;
    private String description;

    private int catType;
    private final int CATEGORY1 = 1, CATEGORY2 = 2, CATEGORY3 = 3;
    ListView listView;
    ArrayAdapter<String> arrayAdapter;
    private TextView textView;
    private EditText editTextDesc;
    String [] catTypeArray;
    private Button submitButton, takePictureButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_incident);

        Intent intent = getIntent();
        lat = intent.getDoubleExtra("latitud", 0);
        lon = intent.getDoubleExtra("longitud", 0);
        catType = intent.getIntExtra("catType", 0);

        listView = findViewById(R.id.listViewIncidents);
        textView = findViewById(R.id.textView13);
        editTextDesc = (EditText) findViewById(R.id.descriptionEditText);
        submitButton = findViewById(R.id.submitIncident);
        takePictureButton = findViewById(R.id.takeIncidentPicture);

        if(catType == CATEGORY1){
            arrayAdapter = new ArrayAdapter<String>(
                    this,
                    android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.cat1));
            catTypeArray = getResources().getStringArray(R.array.cat1);
        }else if(catType == CATEGORY2){
            arrayAdapter = new ArrayAdapter<String>(
                    this,
                    android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.cat2));
            catTypeArray = getResources().getStringArray(R.array.cat2);
        }else {
            arrayAdapter = new ArrayAdapter<String>(
                    this,
                    android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.cat3));
            catTypeArray = getResources().getStringArray(R.array.cat3);
        }
        listView.setAdapter(arrayAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                incident = catTypeArray[position];
                textView.setText(incident);
            }
        });

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SelectIncident.this, Submit.class);
                Report newReport = new Report(incident);
                newReport.setLatitude(lat);
                newReport.setLongitude(lon);
                newReport.setDescription(editTextDesc.getText().toString());
                intent.putExtra("serialize_report", newReport);
                startActivity(intent);
            }
        });

//        takePictureButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(SelectIncident.this, Picture.class);
//                Report newReport = new Report(incident);
//                newReport.setLatitude(lat);
//                newReport.setLongitude(lon);
//                newReport.setDescription(editTextDesc.getText().toString());
//
//                intent.putExtra("serialize_report", newReport);
//                startActivity(intent);
//            }
//        });


    }
}
