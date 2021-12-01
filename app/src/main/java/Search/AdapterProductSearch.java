package Search;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.myapp_nguyenviethung.R;

import java.util.List;

public class AdapterProductSearch extends BaseAdapter {

    List<ProductSearch> productSearches;

    public AdapterProductSearch(List<ProductSearch> productSearches) {
        this.productSearches = productSearches;
    }

    @Override
    public int getCount() {
        return productSearches.size();
    }

    @Override
    public Object getItem(int position) {
        return productSearches.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater=LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_product_search,parent,false);
        TextView tv;
        tv = view.findViewById(R.id.tvString);
        tv.setText(productSearches.get(position).getString());

        return view;
    }
}
