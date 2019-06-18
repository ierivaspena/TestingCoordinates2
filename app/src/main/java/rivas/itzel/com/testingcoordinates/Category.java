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

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import rivas.itzel.com.testingcoordinates.Retrofit.MyService;
import rivas.itzel.com.testingcoordinates.Retrofit.RetrofitClient;

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
    TextView display;
    long timeStamp;
    String description;
    private final int INFRASTRUCTURE_INCIDENT = 2;

    CompositeDisposable compositeDisposable = new CompositeDisposable();
    MyService myService;

    @Override
    protected void onStop() {
        compositeDisposable.clear();
        super.onStop();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        this.setTitle(R.string.selectinc);

        Intent intent = getIntent();
        lat = intent.getDoubleExtra("latitude", 0);
        lon = intent.getDoubleExtra("longitude", 0);

        Toast.makeText(this, "lat " + lat, Toast.LENGTH_SHORT).show();

        submit = findViewById(R.id.cat2button);
        listView = findViewById(R.id.listView);
        textDescription = findViewById(R.id.desc);
        textOther = findViewById(R.id.other);
        display = findViewById(R.id.display);
        //Init Service
        Retrofit retrofitClient = RetrofitClient.getInstance();
        myService = retrofitClient.create(MyService.class);

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,
                getResources().getStringArray(R.array.infrastructure));
        arrayInc = getResources().getStringArray(R.array.infrastructure);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                incident = arrayInc[position];
                display.setText(incident);
            }
        });
        submit.setOnClickListener(view -> {
            onClick();
        });
    }

    public void onClick(){
        // Send to Data base
        timeStamp =System.currentTimeMillis();
        description = textDescription.getText().toString();

        descriptionReports(lat, lon, timeStamp, incident, description, INFRASTRUCTURE_INCIDENT);

        Intent intent = new Intent(Category.this, Submit.class);
        startActivity(intent);
    }
    private void descriptionReports(double latitud, double longitud, long timeStamp, String incident, String description, int color) {

        compositeDisposable.add(myService.descriptionReports(latitud, longitud,timeStamp, incident, description, color)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String response) throws Exception {
                        Toast.makeText(Category.this, "" + response, Toast.LENGTH_SHORT).show();
                    }
                }));
    }
}

