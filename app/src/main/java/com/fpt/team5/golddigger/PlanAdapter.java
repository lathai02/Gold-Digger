package com.fpt.team5.golddigger;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.fpt.team5.golddigger.Model.Category;
import com.fpt.team5.golddigger.Model.Plan;

import java.util.List;

public class PlanAdapter extends RecyclerView.Adapter<PlanAdapter.VH> {

    private List<Plan> plans;
    private Context context;
    private LayoutInflater inflater;

    public PlanAdapter(List<Plan> plans, Context context) {
        this.plans = plans;
        this.context = context;
        this.inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public PlanAdapter.VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = inflater.inflate(R.layout.plan_item, parent, false);
        return new PlanAdapter.VH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull PlanAdapter.VH holder, int position) {
        Plan p = plans.get(position);
        holder.setPlan(p);
    }

    @Override
    public int getItemCount() {
        return plans.size();
    }

    protected class VH extends RecyclerView.ViewHolder {
        private TextView categoryName;

        private Plan p;

        private void bindingView() {
            categoryName = itemView.findViewById(R.id.categoryNameTv);
        }

        private void bindingAction() {

        }

        private void onCategoryImageButtonClick(View view) {

//            Intent i = new Intent(context, SubcategoryActivity.class);
//            i.putExtra("cateId", c.getId());
//            i.putExtra("cateName", c.getTitle());
//            context.startActivity(i);
        }

        public VH(@NonNull View itemView) {
            super(itemView);
            bindingView();
            bindingAction();
        }

        public void setPlan(Plan plan) {
            p = plan;

        }
    }
}
