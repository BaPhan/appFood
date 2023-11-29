package com.baphan.appdemo.adapter;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.baphan.appdemo.R;
import com.baphan.appdemo.domain.Category;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CateSetUpAdapter extends RecyclerView.Adapter<CateSetUpAdapter.CateSetUpViewHolder> {

    private List<Category> categories;

    private IClickItemCateSetUp iClickItemCateSetUp;

    public CateSetUpAdapter(IClickItemCateSetUp iClickItemCateSetUp) {
        this.iClickItemCateSetUp = iClickItemCateSetUp;
    }


    public interface IClickItemCateSetUp {
        void update(Category category);

        void delete(Category category);
    }

    public void setData(List<Category> categories) {
        this.categories = categories;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CateSetUpViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.set_up_cate_item, parent, false);
        return new CateSetUpViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CateSetUpViewHolder holder, int position) {
        Category category = categories.get(position);
        if (category == null) {
            return;
        }
        holder.tvCateName.setText(category.getName());
        holder.tvDes.setText(category.getDescription());
        Picasso.get().load(category.getImageCateUri()).into(holder.imgCate);

        holder.btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iClickItemCateSetUp.update(category);
            }
        });
        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iClickItemCateSetUp.delete(category);
            }
        });

    }

    @Override
    public int getItemCount() {
        if (categories != null) {
            return categories.size();
        }
        return 0;
    }

    public class CateSetUpViewHolder extends RecyclerView.ViewHolder {

        TextView tvCateName, tvDes;

        ImageView imgCate;

        Button btnUpdate, btnDelete;

        public CateSetUpViewHolder(@NonNull View itemView) {
            super(itemView);

            tvCateName = itemView.findViewById(R.id.tv_cate_name_item);
            tvDes = itemView.findViewById(R.id.tv_cate_des_item);
            imgCate = itemView.findViewById(R.id.img_cate_item);

            btnUpdate = itemView.findViewById(R.id.btn_cate_update);
            btnDelete = itemView.findViewById(R.id.btn_cate_delete);
        }
    }

}
