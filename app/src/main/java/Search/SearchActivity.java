package Search;


import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.myapp_nguyenviethung.MainActivity;
import com.example.myapp_nguyenviethung.R;


import java.util.List;

public class SearchActivity extends AppCompatActivity {

    SearchView searchView;
    ListView lvSearch;
    ImageView imgBackSearch;
    AdapterProductSearch adapterProductSearch;
    SQLHelper sqlHelper;
    List<ProductSearch> productSearches;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        searchView = findViewById(R.id.svSearch);
        lvSearch = findViewById(R.id.lvSearch);
        imgBackSearch = findViewById(R.id.imgBackSearch);
        sqlHelper = new SQLHelper(this);

        searchView.setFocusable(true);
        searchView.setIconified(false);
        searchView.requestFocusFromTouch();


        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                sqlHelper.onAddList(query);
                Intent intent1 =new Intent();
                intent1.putExtra("cityname",query);
//                startActivity(intent1);
                setResult(RESULT_OK,intent1);
                finish();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
//
//
//
        lvSearch.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                List<ProductSearch> productSearches = sqlHelper.onGetList();
                ProductSearch productSearch = productSearches.get(position);
                Intent intent1 = new Intent();
                String cityname = productSearch.getString();
                intent1.putExtra("cityname",cityname);
//                startActivity(intent1);
                setResult(RESULT_OK,intent1);
                finish();
            }
        });
//
//
        lvSearch.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                new AlertDialog.Builder(SearchActivity.this)
                        .setIcon(R.drawable.dv_img)
                        .setTitle("Are you sure")
                        .setMessage("Do you want delete all item ")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                sqlHelper.onDeleteAll(position);
                                adapterProductSearch.notifyDataSetChanged();

                            }
                        })
                        .setNegativeButton("No",null).show();


                return true;
            }
        });

        imgBackSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        List<ProductSearch> productSearches =sqlHelper.onGetList();
        adapterProductSearch = new AdapterProductSearch(productSearches);
        lvSearch.setAdapter(adapterProductSearch);
//
//    }

    }
}