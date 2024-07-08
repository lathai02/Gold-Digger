package com.fpt.team5.golddigger;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.fpt.team5.golddigger.Model.SubCategory;

import java.util.List;

public class SubcategoryAdapter extends RecyclerView.Adapter<SubcategoryAdapter.VH> {
    private List<SubCategory> subCategories;
    private Context context;
    private LayoutInflater inflater;

    public SubcategoryAdapter(List<SubCategory> subCategories, Context context) {
        this.subCategories = subCategories;
        this.context = context;
        this.inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = inflater.inflate(R.layout.subcategory_item, parent, false);
        return new VH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        SubCategory sc = subCategories.get(position);
        holder.setSubCategory(sc);
    }

    @Override
    public int getItemCount() {
        return subCategories.size();
    }

    protected class VH extends RecyclerView.ViewHolder {

        private Button subCategoryButton;;

        private SubCategory sc;

        private void bindingView() {
            subCategoryButton = itemView.findViewById(R.id.subCateBtn);
        }

        private void bindingAction() {
            itemView.setOnClickListener(this::onItemViewClick);
        }

        private void onItemViewClick(View view) {
//            Intent i = new Intent(context, DetailActivity.class);
//            i.putExtra("productName", sc.getTitle());
//
//            context.startActivity(i);
        }

        public VH(@NonNull View itemView) {
            super(itemView);
            bindingView();
            bindingAction();
        }

        public void setSubCategory(SubCategory subCategory) {
            sc = subCategory;
            subCategoryButton.setText(subCategory.getTitle());
        }
    }
}
