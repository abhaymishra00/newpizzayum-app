package com.mtechyard.newpizzayum.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mtechyard.newpizzayum.R;
import com.mtechyard.newpizzayum.api.AddressList;
import com.mtechyard.newpizzayum.AddressActivity;

import java.util.List;

public class addressAdepter extends RecyclerView.Adapter<addressAdepter.addressAdapterView>{

    List<AddressList> addressList ;
    Context context;
    boolean option;

    public addressAdepter(Context context, List<AddressList> addressList, boolean option) {
        this.addressList = addressList;
        this.context = context;
        this.option = option;
    }

    @NonNull
    @Override
    public addressAdapterView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.user_address_view,parent,false);
        return new addressAdapterView(view);
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    public void onBindViewHolder(@NonNull addressAdapterView holder, int position) {
        AddressList a = addressList.get(position);
        holder.name.setText(a.getFullName());
        holder.mobile.setText(String.valueOf(a.getMobileNumber()));
        holder.address.setText(a.getFullAddress());

        holder.delete.setOnClickListener(v -> {
            AddressActivity.deleteAddress(position);
        });

        if (option){
            holder.delete.setVisibility(View.GONE);
            holder.edit.setVisibility(View.GONE);
            holder.itemView.setOnClickListener(v -> {
                AddressActivity.setDefaultAddress(position);
            });
        }else{
            holder.delete.setVisibility(View.VISIBLE);
            holder.edit.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public int getItemCount() {
        return addressList.size();
    }

    protected static class addressAdapterView extends RecyclerView.ViewHolder{

        TextView name ,address,mobile;
        ImageView edit,delete;
        LinearLayout addressItemView;

        public addressAdapterView(@NonNull View itemView)
        {
            super(itemView);
            name = itemView.findViewById(R.id.name_in_address_view);
            address = itemView.findViewById(R.id.full_address);
            mobile = itemView.findViewById(R.id.mobile_in_address_view);
            edit = itemView.findViewById(R.id.edit_btn_in_address_view);
            delete = itemView.findViewById(R.id.delete_btn_in_address_view);
            addressItemView = itemView.findViewById(R.id.addressView);

        }
    }
}
