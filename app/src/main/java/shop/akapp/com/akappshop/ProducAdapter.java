package shop.akapp.com.akappshop;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;

public class ProducAdapter extends RecyclerView.Adapter <ProducAdapter.ViewHolder>{
    private ArrayList<Product> sample;

    ItemClicked activity;

    public interface ItemClicked
    {
        public void OnItemClicked(int index);
    }

    ProducAdapter(Context context,ArrayList<Product> items)
    {
        activity=(ItemClicked) context;
        sample=items;
    }

    public class  ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivItem;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            ivItem=itemView.findViewById(R.id.ivItem);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    activity.OnItemClicked(sample.indexOf((Product) v.getTag()));


                }
            });
        }
    }

    @NonNull
    @Override
    public ProducAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
      View v= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recycler_view_layout,viewGroup,false);
      return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ProducAdapter.ViewHolder viewHolder, int i) {

        viewHolder.itemView.setTag(sample.get(i));

        viewHolder.ivItem.setImageResource(sample.get(i).getImage());

    }

    @Override
    public int getItemCount() {
        return sample.size();

    }
}
