package com.example.kino2;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.ArrayList;

public class MovieAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<Movie> originalList;
    private ArrayList<Movie> filteredList;

    public MovieAdapter(Context context, ArrayList<Movie> movies) {
        this.context = context;
        this.originalList = new ArrayList<>(movies);
        this.filteredList = new ArrayList<>(movies);
    }

    @Override
    public int getCount() { return filteredList.size(); }

    @Override
    public Movie getItem(int position) { return filteredList.get(position); }

    @Override
    public long getItemId(int position) { return position; }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_movie, parent, false);
        }

        Movie movie = filteredList.get(position);

        ImageView image = convertView.findViewById(R.id.movieImage);
        TextView title = convertView.findViewById(R.id.movieTitle);
        TextView details = convertView.findViewById(R.id.movieDetails);

        image.setImageResource(movie.imageRes);
        title.setText(movie.title);
        details.setText(movie.genre + " • " + movie.year);

        return convertView;
    }

    public void filter(String text) {
        filteredList.clear();
        if (text.isEmpty()) {
            filteredList.addAll(originalList);
        } else {
            for (Movie movie : originalList) {
                if (movie.title.toLowerCase().contains(text.toLowerCase())) {
                    filteredList.add(movie);
                }
            }
        }
        notifyDataSetChanged();
    }
}