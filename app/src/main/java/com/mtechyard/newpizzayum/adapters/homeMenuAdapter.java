package com.mtechyard.newpizzayum.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mtechyard.newpizzayum.R;
import com.mtechyard.newpizzayum.home;
import com.mtechyard.newpizzayum.project_rec.GlobalFunctions;

public class homeMenuAdapter extends RecyclerView.Adapter<homeMenuAdapter.home_menu_view_holder> {

    String[] menuName = {"PIZZAS","TANDOORI FLAVOUR PIZZA","MASALA PIZZA","PIZZA MANIA","COMBOS","BEVERAGES","SIDE"};
    int[] imageInt = {
            R.drawable.pizza_maniya,
            R.drawable.tandoori_pizza,
            R.drawable.masal_pizza,
            R.drawable.pizzas,
            R.drawable.pizza_combo,
            R.drawable.beverages,
            R.drawable.side};
    Context context;
    @NonNull
    @Override
    public home_menu_view_holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.home_menu_view,parent,false);
        return new home_menu_view_holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull home_menu_view_holder holder, int position) {
         holder.menuImage.setImageResource(imageInt[position]);
         holder.menuText.setText(menuName[position]);

         holder.itemView.setOnClickListener(v -> {
             home.setMenu(menuName[position].toLowerCase());
             //Toast.makeText(context, menuName[position].toLowerCase(), Toast.LENGTH_SHORT).show();
         });

    }

    @Override
    public int getItemCount() {
        return menuName.length;
    }

    protected static class home_menu_view_holder extends RecyclerView.ViewHolder{

        ImageView menuImage;
        TextView menuText;

        public home_menu_view_holder(@NonNull View itemView) {
            super(itemView);

            menuImage = itemView.findViewById(R.id.filter_image_view);
            menuText = itemView.findViewById(R.id.filter_text_view);
        }
    }
}
