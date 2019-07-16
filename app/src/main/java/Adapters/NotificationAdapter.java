package Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import com.example.aatmikjain.memberdirectory.R;
import java.util.ArrayList;

public class NotificationAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>
{
    private Context context;
    private ArrayList<ArrayList<String>> stringArrayList;
    public NotificationAdapter(ArrayList<ArrayList<String>> stringArrayList, Context context)
    {
        this.context = context;
        this.stringArrayList = stringArrayList;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder
    {
        static TextView titleTv, summaryTv, startDateTv;
        public ViewHolder(@NonNull View itemView)
        {
            super(itemView);
            titleTv = itemView.findViewById(R.id.title);
            summaryTv = itemView.findViewById(R.id.summary);
            startDateTv = itemView.findViewById(R.id.startDate);
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        View view = layoutInflater.inflate(R.layout.recycler_cell_notification, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        ViewHolder.titleTv.setText(stringArrayList.get(i).get(0));
        ViewHolder.summaryTv.setText(stringArrayList.get(i).get(1));
        ViewHolder.startDateTv.setText("From:\n"+stringArrayList.get(i).get(2));
        final String description = stringArrayList.get(i).get(3);
        viewHolder.itemView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                LayoutInflater inflater = LayoutInflater.from(context);
                View popupView = inflater.inflate(R.layout.popup_description, null);
                TextView textView = popupView.findViewById(R.id.textview);
                textView.setText("Description:\n"+description);
                // create the popup window
                int width = LinearLayout.LayoutParams.WRAP_CONTENT;
                int height = LinearLayout.LayoutParams.WRAP_CONTENT;
                boolean focusable = true;
                final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);

                popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);

                // dismiss the popup window when touched
                popupView.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        popupWindow.dismiss();
                        return true;
                    }
                });
            }
        });

    }

    @Override
    public int getItemCount() {
        return stringArrayList.size();
    }
}
