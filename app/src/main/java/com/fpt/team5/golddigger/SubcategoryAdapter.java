package com.fpt.team5.golddigger;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
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
    private String category;

    public SubcategoryAdapter(List<SubCategory> subCategories, Context context, String category) {
        this.subCategories = subCategories;
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.category = category;
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

        private ImageButton subcategoryImageButton;
        private TextView subcategoryName;

        private SubCategory sc;

        private void bindingView() {

            subcategoryImageButton = itemView.findViewById(R.id.subcategoryIbtn);
            subcategoryName = itemView.findViewById(R.id.subcategoryNameTv);
        }

        private void bindingAction() {
            subcategoryImageButton.setOnClickListener(this::onSubcategoryImageButtonClick);
        }

        private void onSubcategoryImageButtonClick(View view) {
            Intent i = null;
            if(category.equals("Income") || category.equals("Expense")){
                i = new Intent(context, TransactionActivity2.class);
            }else{
                i = new Intent(context, TransactionActivity.class);
            }
            i.putExtra("subCategory", sc.getTitle());
            i.putExtra("category", category);

            context.startActivity(i);
        }

        public VH(@NonNull View itemView) {
            super(itemView);
            bindingView();
            bindingAction();
        }

        public void setSubCategory(SubCategory subCategory) {
            sc = subCategory;
            subcategoryImageButton.setImageResource(subCategory.getImageId());
            subcategoryName.setText(subCategory.getTitle());
        }
    }
}
