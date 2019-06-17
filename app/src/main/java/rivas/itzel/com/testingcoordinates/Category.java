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
import android.widget.Toast;

public class Category extends AppCompatActivity {

    private Button submit;
    private double lat;
    private double lon;
    private TextView textView;
    private String incident;
    String[] arrayInc;
    ArrayAdapter<String> adapter;
    private ListView listView;
    EditText textDescription;
    EditText textOther;
    private final int INFRASTRUCTURE_INCIDENT = 2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        Intent intent = getIntent();
        lat = intent.getDoubleExtra("latitud", 0);
        lon = intent.getDoubleExtra("longitud", 0);

        Toast.makeText(this, "lat " + lat, Toast.LENGTH_SHORT).show();

        submit = findViewById(R.id.cat2button);
        listView = findViewById(R.id.listView);
        textDescription = findViewById(R.id.desc);
        textOther = findViewById(R.id.other);

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,
                getResources().getStringArray(R.array.infrastructure));
        arrayInc = getResources().getStringArray(R.array.infrastructure);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                incident = arrayInc[position];
            }
        });
        submit.setOnClickListener(view -> {
            onClick();
        });
    }

    public void onClick(){
        // Send to Data base

        Intent intent = new Intent(Category.this, Submit.class);
        startActivity(intent);
    }
}

