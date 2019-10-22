package com.example.mynotepad;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    public List<MyNote> myNoteList;
    Context context;

    public MyAdapter(List<MyNote> myNoteList, Context context) {
        this.myNoteList = myNoteList;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View view = inflater.inflate(R.layout.item,viewGroup,false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder,final int i) {

        final MyNote myNote;

        myNote = myNoteList.get(i);
        myViewHolder.title.setText(myNote.getTitle());
        myViewHolder.note.setText(myNote.getNote());

        myViewHolder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,EditNote.class);
                intent.putExtra("note",myNote.getTitle());
                intent.putExtra("textNote",myNote.getNote());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return myNoteList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder
    {
        TextView title;
        TextView note;
        RelativeLayout relativeLayout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.title);
            note = itemView.findViewById(R.id.note);
            relativeLayout = itemView.findViewById(R.id.relativeLayout);
        }
    }
}
