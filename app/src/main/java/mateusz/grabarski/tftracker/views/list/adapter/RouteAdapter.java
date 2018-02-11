package mateusz.grabarski.tftracker.views.list.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import mateusz.grabarski.tftracker.R;
import mateusz.grabarski.tftracker.data.models.Route;
import mateusz.grabarski.tftracker.data.models.RouteLocation;

/**
 * Created by MGrabarski on 11.02.2018.
 */

public class RouteAdapter extends RecyclerView.Adapter<RouteAdapter.ViewHolder> {

    private static SimpleDateFormat hourSimpleDateFormat;
    private static SimpleDateFormat trackingSimpleDateFormat;
    private List<Route> routes;

    public RouteAdapter(List<Route> routes) {
        this.routes = routes;
        hourSimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        trackingSimpleDateFormat = new SimpleDateFormat("HH:mm:ss");
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_route, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.populate(routes.get(position), position);
    }

    @Override
    public int getItemCount() {
        return routes.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.item_route_lp_tv)
        TextView lpTv;

        @BindView(R.id.item_route_hour_tv)
        TextView hourTv;

        @BindView(R.id.item_route_tracking_time_tv)
        TextView trackingTimeTv;

        @BindView(R.id.item_route_counter_tv)
        TextView counterTv;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void populate(Route route, int position) {
            lpTv.setText(String.valueOf(position + 1));
            hourTv.setText(hourSimpleDateFormat.format(new Date(route.getCreateTimestamp())));

            List<RouteLocation> locations = new ArrayList<>(route.getLocations());
            long trackingTime;

            if (locations.size() > 1)
                trackingTime = locations.get(locations.size() - 1).getTime() - locations.get(0).getTime();
            else if (locations.size() == 1)
                trackingTime = locations.get(0).getTime();
            else
                trackingTime = 0;

            trackingTimeTv.setText(trackingSimpleDateFormat.format(new Date(trackingTime)));

            counterTv.setText(String.valueOf(locations.size()));
        }
    }
}
