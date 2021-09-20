package com.mtechyard.newpizzayum.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mtechyard.newpizzayum.R;
import com.mtechyard.newpizzayum.home;
import com.mtechyard.newpizzayum.project_rec.Toppings;

import java.util.List;

public class productToppingListAdapter extends RecyclerView.Adapter<productToppingListAdapter.productToppingListView> {

    List<Toppings> data;
    Context myContext;

    public productToppingListAdapter(Context myContext,List<Toppings> toppings) {
        this.data = toppings;
        this.myContext = myContext;
    }

    @NonNull
    @Override
    public productToppingListView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View v = layoutInflater.inflate(R.layout.topping_view,parent,false);
        return new productToppingListView(v);
    }

    @SuppressLint({"SetTextI18n", "UseCompatLoadingForDrawables"})
    @Override
    public void onBindViewHolder(@NonNull productToppingListView holder, int position) {
        Toppings t = data.get(position);
        holder.textView1.setText("₹ "+t.getToppingPrice());
        holder.textView.setText(t.getToppingName());
        holder.textView.setOnClickListener(v -> {
            if (home.toggleTopping(t.getToppingName(),t.getToppingPrice())){
                holder.bl.setBackground(myContext.getResources().getDrawable(R.drawable.round_corner_layout_with_main));
                holder.textView.setTextColor(myContext.getResources().getColor(R.color.white));
                holder.textView1.setTextColor(myContext.getResources().getColor(R.color.white));
            }else{

                holder.textView.setTextColor(myContext.getResources().getColor(R.color.light_gray));
                holder.textView1.setTextColor(myContext.getResources().getColor(R.color.light_gray));

                if (!t.getToppingName().equals("Cheese Burst") || !t.getToppingName().equals("Extra Cheese")){
                    holder.bl.setBackground(myContext.getResources().getDrawable(R.drawable.round_corner_layout));
                }
            }
        });

        if(t.getToppingName().equals("Cheese Burst") || t.getToppingName().equals("Extra Cheese")){
            addRemove(holder.bl);
        }




    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    protected static class productToppingListView extends RecyclerView.ViewHolder {
        TextView textView,textView1;
        LinearLayout bl;
        public productToppingListView(@NonNull View itemView) {
            super(itemView);
            textView  = itemView.findViewById(R.id.textView);
            textView1  = itemView.findViewById(R.id.textView1);
            bl = itemView.findViewById(R.id.toppingBackgroundLayout);
        }
    }


    @SuppressLint("UseCompatLoadingForDrawables")
    protected void addRemove(View view){
        view.setBackground(myContext.getResources().getDrawable(R.drawable.round_corner_layout_with_main_color_stoke));
    }

}
