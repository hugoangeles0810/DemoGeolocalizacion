package io.github.hugoangeles0810.demogeolocalizacion;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements LocationListener {

    public static final String TAG = MainActivity.class.getSimpleName();

    public static final Long MIN_TIME_LOCATION_UPDATE = 2000L; // 2 seg
    public  static final Float MIN_DISTANCE_LOCATION_UPDATE = 0f;

    private LocationManager locationManager;

    private Location currentLocation;

    private Button btnRegistrar;
    private TextView tvUbicacion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnRegistrar = (Button) findViewById(R.id.btn_registrar);
        tvUbicacion = (TextView) findViewById(R.id.tv_ubicacion);

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (currentLocation == null) {
                    tvUbicacion.setText("Todavía no se logro obtener la ubicación");
                } else {
                    tvUbicacion.setText("Lat: " + currentLocation.getLatitude() + ", Lng: " + currentLocation.getLongitude());
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        try {
            locationManager.requestLocationUpdates(
                    LocationManager.GPS_PROVIDER,
                    MIN_TIME_LOCATION_UPDATE,
                    MIN_DISTANCE_LOCATION_UPDATE,
                    this
            );
            Toast.makeText(this, "GPS activado!", Toast.LENGTH_SHORT).show();
        } catch (SecurityException e) {
            Toast.makeText(this, "No se han otorgrado los permisos para localización", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onPause() {
        try {
            locationManager.removeUpdates(this);
        } catch (SecurityException e) {
            Toast.makeText(this, "No se han otorgrado los permisos para localización", Toast.LENGTH_SHORT).show();
        }
        super.onPause();
    }

    @Override
    public void onLocationChanged(Location location) {
        Log.d(TAG, location.toString());
        currentLocation = location;
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {
        // Do nothing
    }

    @Override
    public void onProviderEnabled(String s) {
        // Do nothing
    }

    @Override
    public void onProviderDisabled(String s) {
        // Do nothing
    }
}
