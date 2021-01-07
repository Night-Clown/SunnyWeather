package com.example.sunnyweather.ui.place;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sunnyweather.R;
import com.example.sunnyweather.logic.model.Place;
import com.example.sunnyweather.ui.weather.WeatherAcitvity;

import java.util.List;

public class PlaceAdapter extends RecyclerView.Adapter<PlaceAdapter.ViewHolder> {
    private PlaceFragment fragment;
    private List<Place> placeList;

    public PlaceAdapter(PlaceFragment fragment, List<Place> placeList) {
        this.fragment = fragment;
        this.placeList = placeList;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView placeName;
        private TextView placeAddress;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            placeName = itemView.findViewById(R.id.placeName);
            placeAddress = itemView.findViewById(R.id.placeAddress);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.place_item, parent, false);
        ViewHolder holder = new ViewHolder(view);
        holder.itemView.setOnClickListener(v -> {
            int position = holder.getAdapterPosition();
            Place place = placeList.get(position);
            FragmentActivity activity = fragment.getActivity();
            if (activity instanceof WeatherAcitvity) {
                DrawerLayout drawerLayout = activity.findViewById(R.id.drawerLayout);
                drawerLayout.closeDrawers();
                ((WeatherAcitvity) activity).getViewModel().setLocationLat(place.getLocation().getLat());
                ((WeatherAcitvity) activity).getViewModel().setLocationLng(place.getLocation().getLng());
                ((WeatherAcitvity) activity).getViewModel().setPlaceName(place.getName());
                ((WeatherAcitvity) activity).refreshWeather();
            } else {
                Intent intent = new Intent(parent.getContext(), WeatherAcitvity.class);
                intent.putExtra("location_lng", place.getLocation().getLng())
                        .putExtra("location_lat", place.getLocation().getLat())
                        .putExtra("place_name", place.getName());
                activity.startActivity(intent);
                activity.finish();
            }
            fragment.getViewModel().savePlace(place);
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Place place = placeList.get(position);
        holder.placeName.setText(place.getName());
        holder.placeAddress.setText(place.getAddress());
    }

    @Override
    public int getItemCount() {
        return placeList.size();
    }
}
