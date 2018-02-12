package mateusz.grabarski.tftracker.views.list.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import mateusz.grabarski.tftracker.R;
import mateusz.grabarski.tftracker.data.models.Route;
import mateusz.grabarski.tftracker.data.models.RouteLocation;
import mateusz.grabarski.tftracker.utils.DateUtils;

/**
 * Created by MGrabarski on 11.02.2018.
 */

public class RouteAdapter extends RecyclerView.Adapter<RouteAdapter.ViewHolder> {


    private List<Route> routes;
    private RouteItemClickListener mListener;

    public RouteAdapter(List<Route> routes, RouteItemClickListener listener) {
        this.routes = routes;
        this.mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_route, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.populate(routes.get(position), position, mListener);
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

        @BindView(R.id.item_route_start_time_tv)
        TextView startTimeTv;

        @BindView(R.id.item_route_end_time_tv)
        TextView endTimeTv;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void populate(final Route route, int position, final RouteItemClickListener listener) {
            lpTv.setText(String.valueOf(position + 1));
            hourTv.setText(DateUtils.hourSimpleDateFormat.format(new Date(route.getCreateTimestamp())));

            List<RouteLocation> locations = new ArrayList<>(route.getLocations());
            long trackingTime;

            if (locations.size() > 1)
                trackingTime = locations.get(locations.size() - 1).getTime() - locations.get(0).getTime();
            else if (locations.size() == 1)
                trackingTime = locations.get(0).getTime();
            else
                trackingTime = 0;

            trackingTimeTv.setText(trackingTimeTv.getContext().getString(R.string.tracking_time) + " " + DateUtils.trackingSimpleDateFormat.format(new Date(trackingTime)));
            counterTv.setText(counterTv.getContext().getString(R.string.location_counter) + " " + String.valueOf(locations.size()));
            startTimeTv.setText(startTimeTv.getContext().getString(R.string.start) + " " + DateUtils.hourSimpleDateFormat.format(new Date(route.getCreateTimestamp())));
            endTimeTv.setText(endTimeTv.getContext().getString(R.string.end) + " " + DateUtils.hourSimpleDateFormat.format(new Date(locations.get(locations.size() - 1).getTime())));

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onRouteSelected(route);
                }
            });
        }
    }
}
