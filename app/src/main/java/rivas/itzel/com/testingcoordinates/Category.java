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

    private final int INFRASTRUCTURE_INCIDENT = 2; // '2' is a constant given to 'infrastructure' types of reports

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
        // Change screen title
        this.setTitle(R.string.selectinc);

        // getIntent() method gets the latitude and longitude that was passed from the main activity
        Intent intent = getIntent();
        lat = intent.getDoubleExtra("latitude", 0);
        lon = intent.getDoubleExtra("longitude", 0);

        submit = findViewById(R.id.cat2button);
        listView = findViewById(R.id.listView);
        textDescription = findViewById(R.id.desc);
        textOther = findViewById(R.id.other);
        display = findViewById(R.id.display);

        //Init Service
        Retrofit retrofitClient = RetrofitClient.getInstance();
        myService = retrofitClient.create(MyService.class);

        // We create an adapter for the list that will display all kinds of incidents related to infrastructure
        // They are placed in a list adapter and later in a list view to be display in the device UI
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,
                getResources().getStringArray(R.array.infrastructure));

        // Copy elements that are in the resource -> Strings folders -> array 'infrastructure' into an array of strings
        // this array will be accessed later to display which element was selected from the ListView
        arrayInc = getResources().getStringArray(R.array.infrastructure);
        listView.setAdapter(adapter);

        // ListView listener
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                // When user presses on an element inside of the ListView it displays it in a TextView called 'display'
                incident = arrayInc[position];
                display.setText(incident);
            }
        });

        // Listener of the 'submit' button
        // when user clicks submit, the report gets sent out to the database
        submit.setOnClickListener(view -> {
            onClick();
        });
    }

    public void onClick(){
        // Send to Data base
        timeStamp =System.currentTimeMillis();
        description = textDescription.getText().toString();

        descriptionReports(lat, lon, timeStamp, incident, description, INFRASTRUCTURE_INCIDENT);

        // Intent to move to the last screen of the application when user clicks the submit report button
        Intent intent = new Intent(Category.this, Submit.class);
        startActivity(intent);
    }

    // Method used to send all attributes related to an Infrastructure report to the database
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

