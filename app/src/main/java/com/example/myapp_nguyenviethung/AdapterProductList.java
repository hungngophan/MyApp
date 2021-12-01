package com.example.myapp_nguyenviethung;



import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AdapterProductList extends BaseAdapter {

    Context context;
    ArrayList<Product> arrayList;

    public AdapterProductList(Context context, ArrayList<Product> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }


    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int i) {
        return arrayList.get(i);
    }

    @Override
    public long getItemId(int i) {

        return 0;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.items_product_list, null);

        Product product=arrayList.get(i);
        TextView tvDaynd =view.findViewById(R.id.tvDaynd);
        TextView tvStatusnd =view.findViewById(R.id.tvStatusnd);
        TextView tvTempMaxnd =view.findViewById(R.id.tvTempmaxnd);
        TextView tvTempMinnd =view.findViewById(R.id.tvTempminnd);
        ImageView imageStatusnd =view.findViewById(R.id.imgStatusnd);

        tvDaynd.setText(product.dt);
        tvStatusnd.setText(product.description);
        tvTempMaxnd.setText(product.tem_max+"°");
        tvTempMinnd.setText(product.temp_min+"° /");
        Picasso.with(context).load("https://openweathermap.org/img/w/"+product.icon+".png").into(imageStatusnd);

        return view;
    }
}


