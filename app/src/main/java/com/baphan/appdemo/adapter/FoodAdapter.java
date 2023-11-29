package com.baphan.appdemo.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.baphan.appdemo.R;
import com.baphan.appdemo.domain.Food;

import java.util.List;

public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.FoodViewHolder>{

    List<Food> foods;

    private IClickItemFood iClickItemFood;

    public FoodAdapter(IClickItemFood iClickItemFood){
        this.iClickItemFood  = iClickItemFood;
    }

    public interface IClickItemFood{
        void click(Food food);
    }

    public void setData(List<Food> data) {
        this.foods = data;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public FoodViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.food_row_item,parent,false);
        return new FoodViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FoodViewHolder holder, int position) {
        Food food = foods.get(position);
        if (food == null){
            return;
        }
        holder.tvName.setText(food.getName());
        holder.tvPrice.setText(food.getPrice().toString());
        holder.tvStatus.setText(food.getStatus());
        holder.image.setImageResource(food.getImageUrl());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iClickItemFood.click(food);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (foods!=null){
            return foods.size();
        }
        return 0;
    }

    // class anh xa view
    public class FoodViewHolder extends RecyclerView.ViewHolder{

        private TextView tvName,tvPrice, tvStatus;
        private ImageView image;

        public FoodViewHolder(@NonNull View itemView) {
            super(itemView);

            tvName = itemView.findViewById(R.id.tv_name);
            tvPrice = itemView.findViewById(R.id.tv_price);
            tvStatus = itemView.findViewById(R.id.tv_status);

            image = itemView.findViewById(R.id.food_image);
        }
    }
}
