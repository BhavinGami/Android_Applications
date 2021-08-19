package internship.InternetStore;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    String[] categoryNameArray = {"Books", "Shoes", "Watches", "Glasses"};
    int[] categoryImageArray = {R.drawable.books, R.drawable.shoes, R.drawable.watches, R.drawable.glasses};

    ArrayList<CategoryList> arrayList;

    CategoryAdapter adapter;

    RecyclerView productRecycler;

    String[] productNameArray = {"The Monk Who Sold His Ferrari", "13 Reasons Why", "Casual Shoe", "Foramal Shoe", "Analog Watch","Digital Watch", "Round Frame Goggles","Square Frame Goggles"};
    int[] productImageArray = {R.drawable.a, R.drawable.b, R.drawable.shoes1, R.drawable.shoes2, R.drawable.watch1,R.drawable.watch3, R.drawable.glass1,R.drawable.glass2};
    String[] priceArray = {"200", "300", "800", "750", "2000", "900","600","500"};
    String[] unitArray = {"1 Item", "1 Item", "1 Item", "1 Item", "1 Item", "1 Item", "1 Item", "1 Item"};

    ArrayList<ProductList> productArrayList;
    ProductAdapter productAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_home);
        getSupportActionBar().setTitle("Dashboard");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Toast.makeText(HomeActivity.this, "Login Successfully", Toast.LENGTH_LONG).show();

        recyclerView = findViewById(R.id.home_recyclerview);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        arrayList = new ArrayList<>();
        for(int i=0;i<categoryNameArray.length;i++){
            CategoryList list = new CategoryList();
            list.setName(categoryNameArray[i]);
            list.setImage(categoryImageArray[i]);
            arrayList.add(list);
        }

        //adapter = new CategoryAdapter(HomeActivity.this,categoryNameArray,categoryImageArray);
        adapter = new CategoryAdapter(HomeActivity.this,arrayList);
        recyclerView.setAdapter(adapter);

        productRecycler = findViewById(R.id.home_product_recyclerview);
        productRecycler.setLayoutManager(new StaggeredGridLayoutManager(1,StaggeredGridLayoutManager.HORIZONTAL));
        productRecycler.setItemAnimator(new DefaultItemAnimator());

        productArrayList = new ArrayList<>();
        for(int i=0;i<productNameArray.length;i++){
            ProductList list = new ProductList();
            list.setName(productNameArray[i]);
            list.setPrice(priceArray[i]);
            list.setUnit(unitArray[i]);
            list.setImage(productImageArray[i]);
            productArrayList.add(list);
        }
        productAdapter = new ProductAdapter(HomeActivity.this,productArrayList);
        productRecycler.setAdapter(productAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            onBackPressed();
        }
        if(id==R.id.home_menu_message){
            new CommonMethod(HomeActivity.this,MessageActivity.class);
        }
        return super.onOptionsItemSelected(item);
    }

    private class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.MyHolder> {

        Context context;
        ArrayList<CategoryList> arrayList;

        public CategoryAdapter(HomeActivity homeActivity, ArrayList<CategoryList> arrayList) {
            this.context = homeActivity;
            this.arrayList = arrayList;
        }

        @NonNull
        @Override
        public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_category,parent,false);
            return new MyHolder(view);
        }

        public class MyHolder extends RecyclerView.ViewHolder {

            ImageView imageView;
            TextView textView;

            public MyHolder(@NonNull View itemView) {
                super(itemView);
                imageView = itemView.findViewById(R.id.custom_category_image);
                textView = itemView.findViewById(R.id.custom_category_name);
            }
        }

        @Override
        public void onBindViewHolder(@NonNull MyHolder holder, int position) {
            holder.imageView.setImageResource(arrayList.get(position).getImage());
            holder.textView.setText(arrayList.get(position).getName());

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //new CommonMethod(context,ProductActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putInt("category_position",position);
                    Intent intent = new Intent(context,ProductActivity.class);
                    intent.putExtras(bundle);
                    context.startActivity(intent);
                }
            });

        }

        @Override
        public int getItemCount() {
            return arrayList.size();
        }
    }

    private class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.MyHolder> {

        Context context;
        ArrayList<ProductList> productArrayList;

        public ProductAdapter(HomeActivity homeActivity, ArrayList<ProductList> productArrayList) {
            this.context = homeActivity;
            this.productArrayList = productArrayList;
        }

        @NonNull
        @Override
        public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_product_horizontal,parent,false);
            return new MyHolder(view);
        }

        public class MyHolder extends RecyclerView.ViewHolder {

            ImageView imageView;
            TextView name,price;

            public MyHolder(@NonNull View itemView) {
                super(itemView);
                imageView = itemView.findViewById(R.id.custom_product_horizontal_image);
                name = itemView.findViewById(R.id.custom_product_horizontal_name);
                price = itemView.findViewById(R.id.custom_product_horizontal_price);
            }
        }

        @Override
        public void onBindViewHolder(@NonNull MyHolder holder, int position) {
            holder.imageView.setImageResource(productArrayList.get(position).getImage());
            holder.name.setText(productArrayList.get(position).getName());
            holder.price.setText(context.getResources().getString(R.string.price_symbol)+productArrayList.get(position).getPrice()+"/"+productArrayList.get(position).getUnit());

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Bundle bundle = new Bundle();
                    bundle.putString("name",productArrayList.get(position).getName());
                    bundle.putString("price",context.getResources().getString(R.string.price_symbol)+productArrayList.get(position).getPrice()+"/"+productArrayList.get(position).getUnit());
                    bundle.putInt("image",productArrayList.get(position).getImage());
                    Intent intent = new Intent(context,ProductDetailActivity.class);
                    intent.putExtras(bundle);
                    context.startActivity(intent);
                }
            });

        }

        @Override
        public int getItemCount() {
            return productArrayList.size();
        }
    }

    
}