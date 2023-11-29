package com.baphan.appdemo.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.baphan.appdemo.R;
import com.baphan.appdemo.domain.Category;
import com.baphan.appdemo.domain.Food;

import java.util.List;
import java.util.zip.Inflater;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> {

    private IClickItemCategory iClickItemCategory;

    public CategoryAdapter(IClickItemCategory iClickItemCategory) {
        this.iClickItemCategory = iClickItemCategory;
    }

    public interface IClickItemCategory {
        void click(Category category);
    }

    public void setData(List<Category> categories) {
        this.categories = categories;
        notifyDataSetChanged();
    }

    List<Category> categories;

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cate_food_row_item, parent, false);
        return new CategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
        Category category = categories.get(position);
        if (category == null) {
            return;
        }
        holder.tvCateName.setText(category.getName());
        holder.imageUrl.setImageResource(category.getImageUrl());

    }

    @Override
    public int getItemCount() {
        if (categories != null) {
            return categories.size();
        }
        return 0;
    }

    public class CategoryViewHolder extends RecyclerView.ViewHolder {

        TextView tvCateName;
        ImageView imageUrl;

        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            tvCateName = itemView.findViewById(R.id.tv_cate_name);
            imageUrl = itemView.findViewById(R.id.food_image);
        }
    }
}
