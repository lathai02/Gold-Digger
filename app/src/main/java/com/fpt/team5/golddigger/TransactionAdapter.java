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

import com.fpt.team5.golddigger.Model.SubCategory;
import com.fpt.team5.golddigger.Model.Transaction;

import java.util.List;

public class TransactionAdapter extends RecyclerView.Adapter<TransactionAdapter.VH>{
    private List<Transaction> transactions;
    private Context context;
    private LayoutInflater inflater;

    public TransactionAdapter(List<Transaction> transactions, Context context) {
        this.transactions = transactions;
        this.context = context;
        this.inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public TransactionAdapter.VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = inflater.inflate(R.layout.subcategory_item, parent, false);
        return new TransactionAdapter.VH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull TransactionAdapter.VH holder, int position) {
        Transaction t = transactions.get(position);
        holder.setTransaction(t);
    }

    @Override
    public int getItemCount() {
        return transactions.size();
    }

    protected class VH extends RecyclerView.ViewHolder {

        private ImageButton subcategoryImageButton;
        private TextView subcategoryName;
        private Transaction t;

        private void bindingView() {
            subcategoryImageButton = itemView.findViewById(R.id.subcategoryIbtn);
            subcategoryName = itemView.findViewById(R.id.subcategoryNameTv);
        }

        private void bindingAction() {
            itemView.setOnClickListener(this::onItemViewClick);
        }

        private void onItemViewClick(View view) {

        }


        public VH(@NonNull View itemView) {
            super(itemView);
            bindingView();
            bindingAction();
        }

        public void setTransaction(Transaction transaction) {
            t = transaction;
        }
    }
}
