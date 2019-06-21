package rivas.itzel.com.testingcoordinates;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.TextureView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;
import java.util.Locale;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import rivas.itzel.com.testingcoordinates.Retrofit.MyService;
import rivas.itzel.com.testingcoordinates.Retrofit.RetrofitClient;

public class MainActivity extends AppCompatActivity implements LocationListener {

    TextView longView, latView;
    List<Address> addresses;
    TextView addressText;
    ImageButton alert;
    Button report;
    long timeStamp;
    double lat, lon;
    protected LocationManager locationManager;

    CompositeDisposable compositeDisposable = new CompositeDisposable();
    MyService myService;

    @Override
    protected void onStop(){
        compositeDisposable.clear();
        super.onStop();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Check for permissions from user to get current location
        if (ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getApplicationContext(),
                android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION,
                    android.Manifest.permission.ACCESS_COARSE_LOCATION}, 255);
        }
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 2000, 2, this);

        //Init Service
        Retrofit retrofitClient = RetrofitClient.getInstance();
        myService = retrofitClient.create(MyService.class);

        addressText = findViewById(R.id.editText3);
        latView = findViewById(R.id.latitudView);
        longView = findViewById(R.id.longitudView);
        alert = findViewById(R.id.imageButton);
        report = findViewById(R.id.report);

        /*
        When user clicks the alert button the system gets the exact time when alert b was pressed
         */
        alert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                timeStamp=System.currentTimeMillis();
                lat = Double.parseDouble(latView.getText().toString());
                lon = Double.parseDouble(longView.getText().toString());
                locationUser(lat, lon, timeStamp);
            }
        });

        // When user wants to create a report related to infrastructure it will get user current location and send it to
        // the Category activity
        report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Intents are needed to move between activities (Android screens)
                Intent intent = new Intent(MainActivity.this, Category.class);
                intent.putExtra("latitude",lat);
                intent.putExtra("longitude", lon);
                startActivity(intent);
            }
        });
    }

    /*
    Method to send user coordinates to database
     */
    private void locationUser(double latitud, double longitud, long timeStamp) {

        compositeDisposable.add(myService.locationUser(latitud, longitud,timeStamp)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String response) throws Exception {
                        Toast.makeText(MainActivity.this, "" + response, Toast.LENGTH_SHORT).show();
                    }
                }));
    }

    // Method to get the user current location
    @Override
    public void onLocationChanged(Location location) {
        lat =location.getLatitude();
        lon =location.getLongitude();
        latView.setText(Double.toString(lat));
        longView.setText(Double.toString(lon));
        try {
            // Geocoder translates coordinates into a street name
            Geocoder geocoder = new Geocoder(this, Locale.getDefault());
            addresses = geocoder.getFromLocation(lat, lon, 1);
            addressText.setText(addressText.getText() + "\n"+addresses.get(0).getAddressLine(0));
        }catch(Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }

}
