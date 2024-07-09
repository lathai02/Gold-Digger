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
import com.fpt.team5.golddigger.dal.MyDbContext;

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
        View v = inflater.inflate(R.layout.transaction_item, parent, false);
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

        private Transaction t;
        private TextView titleTv;
        private TextView amountTv;
        private TextView createDateTv;
        private TextView categoryTv;
        private TextView subCateTV;
        private MyDbContext dbContext;
        private String cateName;
        private String subCateName;

        private void bindingView() {
            titleTv = itemView.findViewById(R.id.titleTv);
            amountTv = itemView.findViewById(R.id.amountTv);
            createDateTv = itemView.findViewById(R.id.createDateTv);
            categoryTv = itemView.findViewById(R.id.categoryTv);
            subCateTV = itemView.findViewById(R.id.subCateTV);
            dbContext = new MyDbContext(itemView.getContext());
        }

        private void bindingAction() {
            itemView.setOnClickListener(this::onItemViewClick);
        }

        private void onItemViewClick(View view) {
            Intent i = null;
            if(cateName.equals("Borrow") || cateName.equals("Lending")){
                i = new Intent(context,TransactionDetailActivity.class);
            }else{
                i = new Intent(context,TransactionDetailActivity2.class);
            }

            i.putExtra("category",cateName);
            i.putExtra("subCategory",subCateName);
            i.putExtra("transactionId",t.getId());
            context.startActivity(i);
        }


        public VH(@NonNull View itemView) {
            super(itemView);
            bindingView();
            bindingAction();
        }

        public void setTransaction(Transaction transaction) {
            t = transaction;
            cateName = dbContext.getCategoryById(transaction.getCategoryId());
            subCateName = dbContext.getSubCategoryById(transaction.getSubCategoryId());
            if(!cateName.isEmpty() && !subCateName.isEmpty()){
                titleTv.setText(transaction.getTitle());
                amountTv.setText(transaction.getFormattedAmount());
                createDateTv.setText(transaction.getCreateDate());
                categoryTv.setText(cateName);
                subCateTV.setText(subCateName);
            }

        }
    }
}
