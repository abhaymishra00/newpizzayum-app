package com.mtechyard.newpizzayum.adapters;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mtechyard.newpizzayum.R;
import com.mtechyard.newpizzayum.HomeActivity;
import com.mtechyard.newpizzayum.api.RequestResponse;
import com.squareup.picasso.Picasso;

import java.util.List;

public class homeProductsAdapter extends RecyclerView.Adapter<homeProductsAdapter.homeProductViews> {

    List<RequestResponse> showData;
    Activity myActivity;
    
    public homeProductsAdapter(Activity activity,List<RequestResponse> pData) {
        this.showData = pData;
        this.myActivity = activity;
    }

    @NonNull
    @Override
    public homeProductViews onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.product__item_view,parent,false);
        return new homeProductViews(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull homeProductViews holder, int position) {
        // GETTING APPLICATION CONTEXT
        Context context = myActivity.getApplicationContext();
        // GET LIST OBJECT FROM LIST ARRAY
        RequestResponse sData  = showData.get(position);

        // toggle customizable text in product item
        if (sData.getCustomizable()){
            holder.pc.setVisibility(View.VISIBLE);
        }else{
            holder.pc.setVisibility(View.GONE);
        }
        // Setting product data in product item
        holder.pn.setText(sData.getProductName());
        holder.pr.setText("₹ "+ String.valueOf(sData.getDisplayPrice()));
        Picasso.get()
                .load(sData.getProductImage())
                .placeholder(R.drawable.order_icon)
                .into(holder.pi);
        
        holder.add.setOnClickListener(v -> {

            HomeActivity.changeBucketCountInUi();
            if (sData.getCustomizable()){
                HomeActivity.toggleBottomSheetDialogForProductEdit(sData.getProductId(),
                        sData.getProductName(),
                        sData.getProductImage(),
                        sData.getsPrice(),
                        sData.getsPrice(),
                        sData.getmPrice(),
                        sData.getlPrice(),
                        sData.getProductTax());
            }else{
                HomeActivity.toggleBottomSheetDialogForProductEdit(sData.getProductId()
                        ,sData.getProductName(),
                        sData.getProductImage(),
                        sData.getDisplayPrice(),
                        0,0,0,
                        sData.getProductTax());
            }
        });


    }

    @Override
    public int getItemCount() {
        return showData.size();
    }

    public static class homeProductViews extends RecyclerView.ViewHolder {
        TextView pc ,pn,pr;
        ImageView pi;
        Button add;
        public homeProductViews(@NonNull View itemView) {


            super(itemView);
            pn = itemView.findViewById(R.id.product_name);
            pr = itemView.findViewById(R.id.product_display_price);
            pc = itemView.findViewById(R.id.product_customizable);
            pi = itemView.findViewById(R.id.product_image);
            add = itemView.findViewById(R.id.product_add_btn);
        }
    }
}
