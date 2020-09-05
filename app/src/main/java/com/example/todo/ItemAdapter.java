package com.example.todo;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

class ItemsAdapter extends RecyclerView.Adapter<ItemsAdapter.ViewHolder> {

    public interface OnClickListener {
        void onClickListener(int position);
    }

    public interface OnLongClickListener {
        void onLongClickListener(int position);
    }

    ArrayList<String> items;
    OnLongClickListener LongClickListener;
    OnClickListener ClickListener;
    public ItemsAdapter(ArrayList<String> items,OnLongClickListener LongClickListener,OnClickListener onClickListener){
        this.items= items;
        this.LongClickListener = LongClickListener;
        this.ClickListener = onClickListener;
    }
    @NonNull
    @Override
    public ItemsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View todoView = LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_list_item_1,parent,false);

        return new ViewHolder(todoView);
        //return null;
    }

    @Override
        public void onBindViewHolder(@NonNull ItemsAdapter.ViewHolder holder, int position) {
            String item = items.get(position) ;
            holder.bind(item);
    }


    @Override
    public int getItemCount(){
        //return 0;
        return items.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        TextView tvItem;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvItem = itemView.findViewById(android.R.id.text1);
        }
        public void bind(String item){
            tvItem.setText(item);
            tvItem.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view) {
                    ClickListener.onClickListener(getAdapterPosition());

                }
            });

            tvItem.setOnLongClickListener(new View.OnLongClickListener(){
                @Override
                public boolean onLongClick(View view) {
                    //remove the item from the list;
                    LongClickListener.onLongClickListener(getAdapterPosition());
                    return true;
                }
            });
        }
    }

}
