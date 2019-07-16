package Adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.aatmikjain.memberdirectory.R;

import java.util.ArrayList;

public class ProfessionalAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private ArrayList<String> stringArrayList = new ArrayList<>();
    public ProfessionalAdapter(ArrayList<String> stringArrayList)
    {
        this.stringArrayList = stringArrayList;
    }

    /*public static class ViewHolder extends RecyclerView.ViewHolder
    {
        static TextView companyTv, positionTv, fromTv, toTv;
        public ViewHolder(@NonNull View itemView)
        {
            super(itemView);
            degreeTv = itemView.findViewById(R.id.degree);
            instituteTv = itemView.findViewById(R.id.institute);
            boardTv = itemView.findViewById(R.id.board);
            fromTv = itemView.findViewById(R.id.from);
            toTv = itemView.findViewById(R.id.to);
            resultTv = itemView.findViewById(R.id.result);
        }
    }*/

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        View view = layoutInflater.inflate(R.layout.recycler_cell_education, viewGroup, false);
        return new NotificationAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        EducationalAdapter.ViewHolder.degreeTv.setText(stringArrayList.get(i));
        EducationalAdapter.ViewHolder.instituteTv.setText(stringArrayList.get(i));
        EducationalAdapter.ViewHolder.boardTv.setText(stringArrayList.get(i));
        EducationalAdapter.ViewHolder.fromTv.setText(stringArrayList.get(i));
        EducationalAdapter.ViewHolder.toTv.setText(stringArrayList.get(i));
        EducationalAdapter.ViewHolder.resultTv.setText(stringArrayList.get(i));

    }

    @Override
    public int getItemCount() {
        return stringArrayList.size();
    }
}
