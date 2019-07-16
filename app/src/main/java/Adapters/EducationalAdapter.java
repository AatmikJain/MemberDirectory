package Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aatmikjain.memberdirectory.MyProfileActivity;
import com.example.aatmikjain.memberdirectory.R;

import java.util.ArrayList;

import Database.DatabaseHelper;

public class EducationalAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private ArrayList<ArrayList<String>> stringArrayList;
    Context context;
    static String degree, institute, board, from, to, result;

    public EducationalAdapter(ArrayList<ArrayList<String>> stringArrayList, Context context)
    {
        this.stringArrayList = stringArrayList;
        this.context = context;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder
    {
        static TextView degreeTv, instituteTv, boardTv, fromTv, toTv, resultTv;
        ImageView deleteIv;
        public ViewHolder(@NonNull final View itemView)
        {
            super(itemView);
            degreeTv = itemView.findViewById(R.id.degree);
            instituteTv = itemView.findViewById(R.id.institute);
            boardTv = itemView.findViewById(R.id.board);
            fromTv = itemView.findViewById(R.id.from);
            toTv = itemView.findViewById(R.id.to);
            resultTv = itemView.findViewById(R.id.result);
            deleteIv = itemView.findViewById(R.id.deleteIcon);
            deleteIv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String details[] = new String[]{degree, institute, board, from, to, result};
                    Context context = itemView.getContext();
                    DatabaseHelper databaseHelper = DatabaseHelper.getInstance(context);
                    if(databaseHelper.deleteEducationDetail(details))
                    {
                        Toast.makeText(context, "Deleted", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(context, MyProfileActivity.class);
                        context.startActivity(intent);
                    }
                }
            });
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        View view = layoutInflater.inflate(R.layout.recycler_cell_education, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        degree = stringArrayList.get(i).get(0);
        institute = stringArrayList.get(i).get(1);
        board = stringArrayList.get(i).get(2);
        from = stringArrayList.get(i).get(3);
        to = stringArrayList.get(i).get(4);
        result = stringArrayList.get(i).get(5);
        ViewHolder.degreeTv.setText(degree);
        ViewHolder.instituteTv.setText(institute);
        ViewHolder.boardTv.setText(board);
        ViewHolder.fromTv.setText(from);
        ViewHolder.toTv.setText(to);
        ViewHolder.resultTv.setText(result);
    }

    @Override
    public int getItemCount() {
        return stringArrayList.size();
    }
}
