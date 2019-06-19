package com.example.aatmikjain.memberdirectory;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>
{
    private ArrayList<String> stringArrayList = new ArrayList<>();
    public MyAdapter(ArrayList<String> stringArrayList)
    {
        this.stringArrayList = stringArrayList;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder
    {
        static TextView textView;
        public ViewHolder(@NonNull View itemView)
        {
            super(itemView);
            textView = itemView.findViewById(R.id.text);
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
        ViewHolder.textView.setText(stringArrayList.get(i));
    }

    @Override
    public int getItemCount() {
        return stringArrayList.size();
    }
}
