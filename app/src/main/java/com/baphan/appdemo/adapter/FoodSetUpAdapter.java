package com.baphan.appdemo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.baphan.appdemo.R;
import com.baphan.appdemo.dao.AppDatabase;
import com.baphan.appdemo.domain.Category;
import com.baphan.appdemo.domain.Food;
import com.squareup.picasso.Picasso;

import java.util.List;

public class FoodSetUpAdapter extends RecyclerView.Adapter<FoodSetUpAdapter.FoodSetUpViewHolder> {

    private List<Food> foods;
    private Context context;

    private IClickItemFoodSetUp iClickItemFoodSetUp;

    public FoodSetUpAdapter(IClickItemFoodSetUp iClickItemFoodSetUp,Context context) {
        this.iClickItemFoodSetUp = iClickItemFoodSetUp;
        this.context = context;
    }

    public interface IClickItemFoodSetUp {
        void update(Food food);

        void delete(Food food);
    }

    public void setData(List<Food> foods) {
        this.foods = foods;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public FoodSetUpViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.set_up_food_item, parent, false);
        return new FoodSetUpViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FoodSetUpViewHolder holder, int position) {
        Food food = foods.get(position);
        if (food == null) {
            return;
        }
        holder.tvFoodName.setText(food.getName());
        holder.tvFoodPrice.setText(food.getPrice().toString());
        Picasso.get().load(food.getImageUri()).into(holder.imgFood);
        Category category = AppDatabase.getInstance(context).categoryDao().findById(food.getCategoryId());
        if (category != null){
            holder.tvFoodCate.setText(category.getName());
        }

    }

    @Override
    public int getItemCount() {
        if (foods != null) {
            return foods.size();
        }
        return 0;
    }

    public class FoodSetUpViewHolder extends RecyclerView.ViewHolder {

        TextView tvFoodName, tvFoodCate, tvFoodPrice;

        ImageView imgFood;

        Button btnUpdate, btnDelete;

        public FoodSetUpViewHolder(@NonNull View itemView) {
            super(itemView);
            tvFoodName = itemView.findViewById(R.id.tv_food_name_item);
            tvFoodCate = itemView.findViewById(R.id.tv_cate_food_item);
            tvFoodPrice = itemView.findViewById(R.id.tv_price_food_item);

            imgFood = itemView.findViewById(R.id.img_food_item);
            btnUpdate = itemView.findViewById(R.id.btn_food_update);
            btnDelete = itemView.findViewById(R.id.btn_food_delete);

        }
    }
}
