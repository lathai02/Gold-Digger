package com.fpt.team5.golddigger;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.fpt.team5.golddigger.Model.Category;
import com.fpt.team5.golddigger.Model.SubCategory;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.VH> {
    private List<Category> categories;
    private Context context;
    private LayoutInflater inflater;

    public CategoryAdapter(List<Category> categories, Context context) {
        this.categories = categories;
        this.context = context;
        this.inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public CategoryAdapter.VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = inflater.inflate(R.layout.category_item, parent, false);
        return new CategoryAdapter.VH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryAdapter.VH holder, int position) {
        Category c = categories.get(position);
        holder.setCategory(c);
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    protected class VH extends RecyclerView.ViewHolder {

        private ImageButton categoryImageButton;
        private TextView categoryName;

        private Category c;

        private void bindingView() {
            categoryImageButton = itemView.findViewById(R.id.categoryIbtn);
            categoryName = itemView.findViewById(R.id.categoryNameTv);
        }

        private void bindingAction() {
            categoryImageButton.setOnClickListener(this::onCategoryImageButtonClick);
        }

        private void onCategoryImageButtonClick(View view) {

            Intent i = new Intent(context, SubcategoryActivity.class);
            i.putExtra("cateId", c.getId());
            i.putExtra("cateName", c.getTitle());
            context.startActivity(i);
        }

        public VH(@NonNull View itemView) {
            super(itemView);
            bindingView();
            bindingAction();
        }

        public void setCategory(Category category) {
            c = category;
            categoryImageButton.setImageResource(category.getImageId());
            categoryName.setText(category.getTitle());
        }
    }
}
