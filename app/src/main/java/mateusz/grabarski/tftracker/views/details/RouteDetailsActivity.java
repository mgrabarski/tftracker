package mateusz.grabarski.tftracker.views.details;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;

import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import mateusz.grabarski.tftracker.R;
import mateusz.grabarski.tftracker.data.models.Route;
import mateusz.grabarski.tftracker.data.models.RouteLocation;
import mateusz.grabarski.tftracker.utils.DateUtils;
import mateusz.grabarski.tftracker.utils.MapUtils;

public class RouteDetailsActivity extends AppCompatActivity implements OnMapReadyCallback {

    public static final String KEY_ROUTE_OBJECT = "route_object";

    @BindView(R.id.activity_route_details_text)
    TextView detailsText;

    private Route mRoute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route_details);
        ButterKnife.bind(this);

        detailsText.setMovementMethod(new ScrollingMovementMethod());

        mRoute = (Route) getIntent().getExtras().getSerializable(KEY_ROUTE_OBJECT);

        detailsText.setText(getRouteInformation());

        initMap();
    }

    private void initMap() {
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.activity_route_details_map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        RouteLocation startLocation = mRoute.getLocations().iterator().next();

        LatLng startPoint = new LatLng(startLocation.getLatitude(), startLocation.getLongitude());

        MapUtils.moveCamera(startPoint, googleMap);
        MapUtils.drawRoutePath(mRoute, googleMap);
    }

    private String getRouteInformation() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Id = " + mRoute.getId() + "\n");
        stringBuilder.append("Create date = " + DateUtils.trackingSimpleDateFormat.format(new Date(mRoute.getCreateTimestamp())) + "\n");
        stringBuilder.append("Locations:\n");

        for (RouteLocation location : mRoute.getLocations()) {
            stringBuilder.append(location.toString());
        }

        return stringBuilder.toString();
    }
}
