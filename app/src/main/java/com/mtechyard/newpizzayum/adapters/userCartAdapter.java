package com.mtechyard.newpizzayum.adapters;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.L;
import com.mtechyard.newpizzayum.R;
import com.mtechyard.newpizzayum.project_rec.Toppings;
import com.mtechyard.newpizzayum.project_rec.UserOrderList;
import com.mtechyard.newpizzayum.user_cart;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.List;

public class userCartAdapter extends RecyclerView.Adapter<userCartAdapter.userCardAdapterView>{

    List<UserOrderList> dataList;
    Activity myActivity;

    public userCartAdapter(Activity myActivity,List<UserOrderList> dataList) {
        this.dataList = dataList;
        this.myActivity = myActivity;
    }

    @NonNull
    @Override
    public userCardAdapterView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View itemView = layoutInflater.inflate(R.layout.cart_product_item,parent,false);
        return new userCardAdapterView(itemView);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull userCardAdapterView holder, int position) {
        UserOrderList d = dataList.get(position);
        holder.pn.setText(d.getProductName());
        int tRate = 0;

        if (d.getHaveToppings()){
            tRate = d.getToppingRate();
        }

        holder.prate.setText("₹"+String.valueOf((d.getRate()+tRate)));
        String p = "";
        switch (d.getSize()){
            case "small":
                p = "Small";
                break;
            case "medium":
                p = "Medium";
                break;
            case "large":
                p = "Large";
                break;
            case "No Size":
                holder.pst.setVisibility(View.GONE);
                break;
            default:
                holder.pst.setVisibility(View.VISIBLE);
        }


        if (d.getHaveToppings()){
            List<Toppings> t = (List<Toppings>) d.getToppingList();
            for (int i = 0; i < t.size(); i++) {
                p = p + " | " + t.get(i).getToppingName();
            }
        }

        holder.pst.setText(p);

        Picasso.get()
                .load(d.getProductImage())
                .placeholder(R.drawable.order_icon)
                .into(holder.pi);

        holder.pr.setOnClickListener(v -> {
            user_cart.removeQuantity(position);
        });

        holder.pa.setOnClickListener(v -> {
            user_cart.addQuantity(position);
        });
        holder.pq.setText(String.valueOf(d.getQty()));
        if (d.getQty()>1){
            holder.pr.setImageResource(R.drawable.remove_icon);
        }else{
            holder.pr.setImageResource(R.drawable.delete_icon);
        }

    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    protected static class userCardAdapterView extends RecyclerView.ViewHolder {
        TextView  pn,pst,prate,pq;
        ImageView pi,pa,pr;
        public userCardAdapterView(@NonNull View itemView) {
            super(itemView);
            pn = itemView.findViewById(R.id.cart_product_name);
            prate = itemView.findViewById(R.id.cart_product_rate);
            pq = itemView.findViewById(R.id.cart_product_qyt);
            pst = itemView.findViewById(R.id.cart_product_size_topping);
            pi = itemView.findViewById(R.id.cart_product_image);
            pa = itemView.findViewById(R.id.cart_product_qty_1);
            pr = itemView.findViewById(R.id.cart_product_qty_0);


        }
    }
}
