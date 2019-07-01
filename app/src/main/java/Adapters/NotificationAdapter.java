package Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aatmikjain.memberdirectory.HomeActivity;
import com.example.aatmikjain.memberdirectory.R;

import java.util.ArrayList;

public class NotificationAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>
{
    private ArrayList<ArrayList<String>> stringArrayList = new ArrayList<ArrayList<String>>();
    public NotificationAdapter(ArrayList<ArrayList<String>> stringArrayList)
    {
        this.stringArrayList = stringArrayList;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder
    {
        static TextView titleTv, summaryTv, startDateTv, endDateTv, domainTv;
        public ViewHolder(@NonNull View itemView)
        {
            super(itemView);
            titleTv = itemView.findViewById(R.id.title);
//            domainTv = itemView.findViewById(R.id.domain);
            summaryTv = itemView.findViewById(R.id.summary);
//            startDateTv = itemView.findViewById(R.id.startDate);
//            endDateTv = itemView.findViewById(R.id.endDate);
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        View view = layoutInflater.inflate(R.layout.recycler_cell, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
//       Toast.makeText(, stringArrayList.get(0).get(0), Toast.LENGTH_LONG).show();
        ViewHolder.titleTv.setText(stringArrayList.get(i).get(0));
//        ViewHolder.domainTv.setText("Domain:\n"+stringArrayList.get(i).get(1));
        ViewHolder.summaryTv.setText(stringArrayList.get(i).get(2));
//        ViewHolder.startDateTv.setText("From:\n"+stringArrayList.get(i).get(3));
//        ViewHolder.endDateTv.setText("To:\n"+stringArrayList.get(i).get(4));

    }

    @Override
    public int getItemCount() {
        return stringArrayList.size();
    }
}
