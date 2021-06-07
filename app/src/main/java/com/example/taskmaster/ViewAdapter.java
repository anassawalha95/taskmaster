package com.example.taskmaster;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.amplifyframework.datastore.generated.model.Task;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ViewAdapter extends RecyclerView.Adapter<ViewAdapter.ViewHolder>   {
    public  interface OnTaskListener{
        void onTaskClick(int position);
    }

    public List<Task> tasks;
    private OnTaskListener onTaskListener;

    public ViewAdapter(List<Task> tasks, OnTaskListener onTaskListener ) {
        this.tasks = tasks;
        this.onTaskListener=onTaskListener;
    }



    public static class ViewHolder extends RecyclerView.ViewHolder  implements View.OnClickListener {
        private  Task task;
        private TextView title;
        private TextView body;
        private OnTaskListener onTaskListener;

        public ViewHolder(View view, OnTaskListener onTaskListener) {
            super(view);

            this.title= view.findViewById(R.id.f_title);
            this.body=view.findViewById(R.id.f_description);
            this.onTaskListener=onTaskListener;

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
          onTaskListener.onTaskClick(getAdapterPosition());
        }

    }



    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.fragment_task, parent, false);

        return new ViewHolder(listItem, onTaskListener);

    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Task task= tasks.get(position);
        holder.title.setText(task.getTitle());
        holder.body.setText(task.getDescription());
    }

    @Override
    public int getItemCount() {
        return tasks.size();
    }
}
