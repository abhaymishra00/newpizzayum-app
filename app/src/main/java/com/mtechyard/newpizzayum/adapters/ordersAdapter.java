package com.mtechyard.newpizzayum.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.mtechyard.newpizzayum.OrderReview;
import com.mtechyard.newpizzayum.R;
import com.mtechyard.newpizzayum.project_rec.UserOrderData;
import com.mtechyard.newpizzayum.user_order;
import com.squareup.picasso.Picasso;

public class ordersAdapter extends RecyclerView.Adapter<ordersAdapter.userOrderView> {

    Context context;
    UserOrderData[] data;

    public ordersAdapter(Context context, UserOrderData[] data) {
        this.context = context;
        this.data = data;
    }

    @NonNull
    @Override
    public userOrderView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.user_order_item_view, parent, false);
        return new userOrderView(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull userOrderView holder, int position) {
        UserOrderData d = data[position];

        holder.paymentMode.setText(d.getPaymentMode());
        holder.date.setText(d.getOrderDate());
        holder.statusMessage.setText(d.getStatusMessage());
        holder.rate.setText("₹ " + d.getOrderRate() + " /-");
        holder.orderName.setText(d.getOrderName());

        switch (d.getColorCode()){
            case 1:
                holder.statusMessage.setTextColor(context.getResources().getColor(R.color.preGreen));
                holder.statusLight.setCardBackgroundColor(context.getResources().getColor(R.color.preGreen));
                break;

            case 2:
                holder.statusMessage.setTextColor(context.getResources().getColor(R.color.goodYellow));
                holder.statusLight.setCardBackgroundColor(context.getResources().getColor(R.color.goodYellow));
                break;

            case 3:
                holder.statusMessage.setTextColor(context.getResources().getColor(R.color.preYellow));
                holder.statusLight.setCardBackgroundColor(context.getResources().getColor(R.color.preYellow));
                break;

            case 4:
                holder.statusMessage.setTextColor(context.getResources().getColor(R.color.darkGreen));
                holder.statusLight.setCardBackgroundColor(context.getResources().getColor(R.color.darkGreen));
                break;

            case 5:
                holder.statusMessage.setTextColor(context.getResources().getColor(R.color.red));
                holder.statusLight.setCardBackgroundColor(context.getResources().getColor(R.color.red));
                break;
        }


        if (d.getItemCount() == 1) {
            holder.fullImageCard.setVisibility(View.VISIBLE);
            Picasso.get()
                    .load(d.getImageUrl1())
                    .placeholder(R.drawable.order_icon)
                    .into(holder.fullImage);
        } else {
            holder.fullImageCard.setVisibility(View.GONE);
            Picasso.get()
                    .load(d.getImageUrl1())
                    .placeholder(R.drawable.order_icon)
                    .into(holder.imageView1);

            Picasso.get()
                    .load(d.getImageUrl2())
                    .placeholder(R.drawable.order_icon)
                    .into(holder.imageView2);
        }

        holder.itemView.setOnClickListener(v -> {
            if (d.getStatusMessage().equals("Pending For Payment")){
                //user_order.showPayment(d.getOrderId(),String.valueOf(d.getOrderRate()));
            }else{
                //context.startActivity(new Intent(context, OrderReview.class).putExtra("orderId",d.getOrderId()));
            }

        });

    }

    @Override
    public int getItemCount() {
        return data.length;
    }

    protected static class userOrderView extends RecyclerView.ViewHolder {

        CardView imageCard, statusLight, fullImageCard;
        TextView orderName, date, rate, paymentMode, statusMessage;
        ImageView imageView1, imageView2, fullImage;

        public userOrderView(@NonNull View itemView) {
            super(itemView);

            imageCard = (CardView) itemView.findViewById(R.id.orderCard);
            fullImageCard = (CardView) itemView.findViewById(R.id.fullImageCard);
            statusLight = itemView.findViewById(R.id.orderStatusLight);
            orderName = itemView.findViewById(R.id.orderName);
            date = itemView.findViewById(R.id.orderData);
            rate = itemView.findViewById(R.id.orderAmount);
            paymentMode = itemView.findViewById(R.id.orderPaymentMode);
            statusMessage = itemView.findViewById(R.id.orderStatus);
            imageView1 = itemView.findViewById(R.id.orderImage0);
            imageView2 = itemView.findViewById(R.id.orderImage1);
            fullImage = itemView.findViewById(R.id.orderFullImage);

        }
    }
}
