package com.fpt.team5.golddigger;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
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
        private TextView titleTv;
        private TextView amountTv;
        private ImageView statusTvv;
        private TextView createDate;
        private TextView dueDate;

        private Plan p;

        private void bindingView() {
            titleTv = itemView.findViewById(R.id.titleTv);
            amountTv = itemView.findViewById(R.id.amountTv);
            statusTvv = itemView.findViewById(R.id.statusTvv);
            createDate = itemView.findViewById(R.id.createDate);
            dueDate = itemView.findViewById(R.id.dueDate);
        }

        private void bindingAction() {
            itemView.setOnClickListener(this::OnItemViewClick);
        }

        private void OnItemViewClick(View view) {

            Intent i = new Intent(context, PlanDetailActivity.class);
            i.putExtra("title", p.getTitle());
            i.putExtra("amount", p.getFormattedAmount());
            i.putExtra("createDate", p.getCreateDate());
            i.putExtra("dueDate", p.getDueDate());
            i.putExtra("id", p.getId());
            i.putExtra("description", p.getDescription());

            context.startActivity(i);
        }

        public VH(@NonNull View itemView) {
            super(itemView);
            bindingView();
            bindingAction();
        }

        public void setPlan(Plan plan) {
            p = plan;
            titleTv.setText(plan.getTitle());
            amountTv.setText(plan.getFormattedAmount() + "");
            createDate.setText(plan.getCreateDate());
            dueDate.setText(plan.getDueDate());
            if(plan.getStatus() == 1){
                statusTvv.setImageResource(R.drawable.ic_done);
            }else{
                statusTvv.setImageResource(R.drawable.not_done);
            }
        }
    }
}
