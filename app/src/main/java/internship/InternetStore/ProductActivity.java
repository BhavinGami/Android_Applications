package internship.InternetStore;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class ProductActivity extends AppCompatActivity {

    RecyclerView productRecycler;
    ArrayList<ProductList> arrayList;
    ProductAdapter adapter;

    String[] productNameArray0 = {"The Monk Who Sold His Ferrari", "13 Reasons Why","Seven Habbits Of Highly Effective People","Gone Girl"};
    int[] productImageArray0 = {R.drawable.a, R.drawable.b,R.drawable.c,R.drawable.d};
    String[] priceArray0 = {"800", "300","200","250"};
    String[] unitArray0 = {"1 Item", "1 Item","1 Item", "1 Item"};

    String[] productNameArray1 = {"Casual Shoe", "Formal shoe","Casual shoe"};
    int[] productImageArray1 = {R.drawable.shoes1, R.drawable.shoes2,R.drawable.shoes3};
    String[] priceArray1 = {"300", "350","500"};
    String[] unitArray1 = {"1 Item", "1 Item","1 Item"};

    String[] productNameArray2 = {"Black watch with broun belt","Blue analog watch","Black digital watch"};
    int[] productImageArray2 = {R.drawable.watch1,R.drawable.watch2,R.drawable.watch3};
    String[] priceArray2 = {"8000","2500","1500"};
    String[] unitArray2 = {"1 Item","1 Item", "1 Item"};

    String[] productNameArray3 = {"Round golden fram goggles","Square black goggles","Square silver goggles"};
    int[] productImageArray3 = {R.drawable.glass1,R.drawable.glass2,R.drawable.glass3};
    String[] priceArray3 = {"550","600","700"};
    String[] unitArray3 = {"1 Item","1 Item", "1 Item"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);
        getSupportActionBar().setTitle("Product");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Bundle bundle = getIntent().getExtras();
        int iCategoryPosition = bundle.getInt("category_position");

        productRecycler = findViewById(R.id.product_recycler);
        productRecycler.setLayoutManager(new LinearLayoutManager(ProductActivity.this));
        productRecycler.setItemAnimator(new DefaultItemAnimator());
        
        if(iCategoryPosition==0) {
            arrayList = new ArrayList<>();
            for (int i = 0; i < productNameArray0.length; i++) {
                ProductList list = new ProductList();
                list.setName(productNameArray0[i]);
                list.setPrice(priceArray0[i]);
                list.setUnit(unitArray0[i]);
                list.setImage(productImageArray0[i]);
                arrayList.add(list);
            }
        }
        if(iCategoryPosition==1) {
            arrayList = new ArrayList<>();
            for (int i = 0; i < productNameArray1.length; i++) {
                ProductList list = new ProductList();
                list.setName(productNameArray1[i]);
                list.setPrice(priceArray1[i]);
                list.setUnit(unitArray1[i]);
                list.setImage(productImageArray1[i]);
                arrayList.add(list);
            }
        }
        if(iCategoryPosition==2) {
            arrayList = new ArrayList<>();
            for (int i = 0; i < productNameArray2.length; i++) {
                ProductList list = new ProductList();
                list.setName(productNameArray2[i]);
                list.setPrice(priceArray2[i]);
                list.setUnit(unitArray2[i]);
                list.setImage(productImageArray2[i]);
                arrayList.add(list);
            }
        }
        if(iCategoryPosition==3) {
            arrayList = new ArrayList<>();
            for (int i = 0; i < productNameArray3.length; i++) {
                ProductList list = new ProductList();
                list.setName(productNameArray3[i]);
                list.setPrice(priceArray3[i]);
                list.setUnit(unitArray3[i]);
                list.setImage(productImageArray3[i]);
                arrayList.add(list);
            }
        }
        adapter = new ProductAdapter(ProductActivity.this,arrayList);
        productRecycler.setAdapter(adapter);
        
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if(id==android.R.id.home){
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    private class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.MyHolder> {

        Context context;
        ArrayList<ProductList> productArrayList;

        public ProductAdapter(ProductActivity homeActivity, ArrayList<ProductList> productArrayList) {
            this.context = homeActivity;
            this.productArrayList = productArrayList;
        }

        @NonNull
        @Override
        public ProductAdapter.MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_product,parent,false);
            return new ProductAdapter.MyHolder(view);
        }

        public class MyHolder extends RecyclerView.ViewHolder {

            ImageView imageView;
            TextView name,price;

            public MyHolder(@NonNull View itemView) {
                super(itemView);
                imageView = itemView.findViewById(R.id.custom_product_image);
                name = itemView.findViewById(R.id.custom_product_name);
                price = itemView.findViewById(R.id.custom_product_price);
            }
        }

        @Override
        public void onBindViewHolder(@NonNull ProductAdapter.MyHolder holder, int position) {
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