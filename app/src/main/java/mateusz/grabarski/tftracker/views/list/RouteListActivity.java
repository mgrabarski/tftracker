package mateusz.grabarski.tftracker.views.list;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import mateusz.grabarski.tftracker.R;
import mateusz.grabarski.tftracker.base.App;
import mateusz.grabarski.tftracker.base.BaseActivity;
import mateusz.grabarski.tftracker.data.database.managers.interfaces.RouteManagerInterface;
import mateusz.grabarski.tftracker.data.models.Route;
import mateusz.grabarski.tftracker.views.details.RouteDetailsActivity;
import mateusz.grabarski.tftracker.views.list.adapter.RouteAdapter;
import mateusz.grabarski.tftracker.views.list.adapter.RouteItemClickListener;

public class RouteListActivity extends BaseActivity implements RouteItemClickListener {

    @BindView(R.id.activity_route_rv)
    RecyclerView routeRv;

    @Inject
    RouteManagerInterface routeManager;

    private RouteAdapter mAdapter;
    private List<Route> mRoutes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route_list);
        ButterKnife.bind(this);

        routeRv.setLayoutManager(new LinearLayoutManager(this));

        mRoutes = new ArrayList<>();
        try {
            mRoutes.addAll(routeManager.getAllRoutes());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        mAdapter = new RouteAdapter(mRoutes, this);

        routeRv.setAdapter(mAdapter);
    }

    @Override
    protected void inject() {
       ((App) getApplicationContext()).getApplicationComponent().inject(this);
    }

    @Override
    public void onRouteSelected(Route route) {
        Intent intent = new Intent(this, RouteDetailsActivity.class);
        intent.putExtra(RouteDetailsActivity.KEY_ROUTE_OBJECT, route);
        startActivity(intent);
    }
}
