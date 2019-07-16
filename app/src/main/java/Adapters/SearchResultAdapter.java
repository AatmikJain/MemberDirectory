package Adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.aatmikjain.memberdirectory.R;

import java.util.ArrayList;

public class SearchResultAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private ArrayList<String> stringArrayList;
    public SearchResultAdapter(ArrayList<String> stringArrayList)
    {
        this.stringArrayList = stringArrayList;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder
    {
        static TextView nameTv;
        public ViewHolder(@NonNull View itemView)
        {
            super(itemView);
            nameTv = itemView.findViewById(R.id.name);
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        View view = layoutInflater.inflate(R.layout.recycler_cell_search_result, viewGroup, false);
        return new SearchResultAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        ViewHolder.nameTv.setText(stringArrayList.get(i));
    }

    @Override
    public int getItemCount() {
        return stringArrayList.size();
    }
}
