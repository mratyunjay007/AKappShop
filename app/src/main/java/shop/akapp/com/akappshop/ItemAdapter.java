package shop.akapp.com.akappshop;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ViewHolder> {

     private ArrayList<ItemDetails> list;

     ItemClicked activity;

     public interface ItemClicked
     {
         void onItemclick(int index);
     }

    public ItemAdapter(Context context, ArrayList<ItemDetails> list)
    {
        activity=(ItemClicked) context;
        this.list=list;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivItem;
        TextView tvCompany,tvItemName,tvPrice,tvDescription;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            ivItem=itemView.findViewById(R.id.ivItem);
            tvItemName=itemView.findViewById(R.id.tvItem);
            tvCompany=itemView.findViewById(R.id.tvCompany);
            tvPrice=itemView.findViewById(R.id.tvPrice);
            tvDescription=itemView.findViewById(R.id.tvDesc);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    activity.onItemclick(list.indexOf((ItemDetails) v.getTag()));

                }
            });


        }
    }

    @NonNull
    @Override
    public ItemAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View v=LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.listitem_layout,viewGroup,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemAdapter.ViewHolder viewHolder, int i) {

        viewHolder.itemView.setTag(list.get(i));
        viewHolder.ivItem.setImageResource(list.get(i).getItemImage());
        viewHolder.tvCompany.setText(list.get(i).getCompanyName());
        viewHolder.tvItemName.setText(list.get(i).getItemName());
        viewHolder.tvPrice.setText(list.get(i).getPrice());
        viewHolder.tvDescription.setText(list.get(i).getItemDescription());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
