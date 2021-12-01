package Search;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class SQLHelper extends SQLiteOpenHelper {
    static private final String DB_NAME="ProductList.db";
    static private final String DV_STRING="string";
    static private final String TB_PRODUCTLIST="productList";


    public SQLHelper(Context context){
        super(context,DB_NAME,null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sqlQuery = "CREATE TABLE productList("
                +"string TEXT NOT NULL PRIMARY KEY"
                +")";
        db.execSQL(sqlQuery);
    }

//    @Override
//    public void onCreate(SQLiteDatabase db) {
//        String queryCreaTable = "CREATE TABLE productList ( " +
//                "id INT NOT NULL PRIMARY KEY AUTOINCREMENT," +
//                " string Text )";
//
//        //Chạy câu lệnh tạo bảng product
//        db.execSQL(queryCreaTable);
//    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion != newVersion){
            String strQuery ="DROP TABLE IF EXISTS "+DB_NAME;
            db.execSQL(strQuery);
            onCreate(db);
        }
    }

    public void onAddList(String string){
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("string", string );
        sqLiteDatabase.insert(TB_PRODUCTLIST,null,contentValues);
        sqLiteDatabase.close();
        contentValues.clear();

    }
    public void onDeleteList(int id){
        SQLiteDatabase sqLiteDatabase= getWritableDatabase();
        sqLiteDatabase.delete(TB_PRODUCTLIST,"id=?",new String[]{String.valueOf(id)});
        sqLiteDatabase.close();
    }

    public void onDeleteAll(int id){
        SQLiteDatabase sqLiteDatabase= getWritableDatabase();
        sqLiteDatabase.delete(TB_PRODUCTLIST,null,null);
    }


    public void onUpdateProduct(int id){
        SQLiteDatabase sqLiteDatabase= getWritableDatabase();
        ContentValues contentValues= new ContentValues();

        contentValues.put(DV_STRING,id);
        sqLiteDatabase.update(TB_PRODUCTLIST,contentValues,"id=?",new String[]{String.valueOf(id)});
    }


    public List<ProductSearch> onGetList(){
        List<ProductSearch> productSearches = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        Cursor cursor = sqLiteDatabase.query(false,TB_PRODUCTLIST,
                null,
                null,
                null,
                null,
                null,
                null,
                null

                );

        while (cursor.moveToNext()){
            String string = cursor.getString(cursor.getColumnIndex(DV_STRING));
            ProductSearch productSearch =new ProductSearch(string);
            productSearches.add(productSearch);

        }
        return productSearches;
    }



}
