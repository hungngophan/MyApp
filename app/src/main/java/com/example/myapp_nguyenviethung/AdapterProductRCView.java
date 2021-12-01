package com.example.myapp_nguyenviethung;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AdapterProductRCView extends RecyclerView.Adapter<AdapterProductRCView.ViewHolder> {

    ArrayList<com.example.myapp_nguyenviethung.Product> productList;

    public AdapterProductRCView(MainActivity mainActivity, ArrayList<com.example.myapp_nguyenviethung.Product> productList) {
        this.productList = productList;
    }

    @NonNull
    @Override
    public com.example.myapp_nguyenviethung.AdapterProductRCView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product_rcview, parent, false);

        return new ViewHolder(itemView);
    }




    @Override
    public void onBindViewHolder(com.example.myapp_nguyenviethung.AdapterProductRCView.ViewHolder holder, int position) {
        com.example.myapp_nguyenviethung.Product product = productList.get(position);
        holder.dt.setText(product.dt);
        holder.temp_min.setText(product.temp_min);
        holder.tem_max.setText(product.tem_max);
        Picasso.with(holder.icon.getContext()).load("https://openweathermap.org/img/w/"+product.icon+".png").into(holder.icon);


    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView dt, temp_min, tem_max;
        ImageView icon;

        public ViewHolder(View itemView) {
            super(itemView);
            dt = itemView.findViewById(R.id.it_rv_time);
            temp_min = itemView.findViewById(R.id.it_rv_tempmin);
            tem_max = itemView.findViewById(R.id.it_rv_tempmax);
            icon = itemView.findViewById(R.id.it_rv_icon);

        }
    }
}
