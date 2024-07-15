package com.fpt.team5.golddigger;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.fpt.team5.golddigger.api.interestRate.ApiResponse.Datum;
import com.fpt.team5.golddigger.api.interestRate.ApiResponse.InterestRate;

import java.util.List;

public class BankInterestRateAdapter extends RecyclerView.Adapter<BankInterestRateAdapter.ViewHolder> {
    private List<Datum> bankList;

    public BankInterestRateAdapter(List<Datum> bankList) {
        this.bankList = bankList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_bank_interest_rate, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Datum bank = bankList.get(position);
        holder.nameTextView.setText(bank.getName());

        Glide.with(holder.iconImageView.getContext())
                .load(bank.getIcon())
                .into(holder.iconImageView);

        // Set up interest rates as needed
        StringBuilder rates = new StringBuilder();
        for (InterestRate rate : bank.getInterestRates()) {
            if (rate.getValue()==null){
                rate.setValue("--");
            }
            switch (rate.getDeposit()) {
                case 1:
                    holder.deposit_1.setText(rate.getValue() +"");
                    break;
                case 3:
                    holder.deposit_3.setText(rate.getValue() +"");
                    break;
                case 6:
                    holder.deposit_6.setText(rate.getValue() +"");
                    break;
                case 9:
                    holder.deposit_9.setText(rate.getValue() +"");
                    break;
                case 12:
                    holder.deposit_12.setText(rate.getValue() +"");
                    break;
                case 18:
                    holder.deposit_18.setText(rate.getValue() +"");
                    break;
                case 24:
                    holder.deposit_24.setText(rate.getValue() +"");
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    public int getItemCount() {
        return bankList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView nameTextView;
        public ImageView iconImageView;
        public TextView deposit_1;
        public TextView deposit_3;
        public TextView deposit_6;
        public TextView deposit_9;
        public TextView deposit_12;
        public TextView deposit_18;
        public TextView deposit_24;

        public ViewHolder(View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.nameTextView);
            iconImageView = itemView.findViewById(R.id.iconImageView);
            deposit_1 = itemView.findViewById(R.id.deposit_1);
            deposit_3 = itemView.findViewById(R.id.deposit_3);
            deposit_6 = itemView.findViewById(R.id.deposit_6);
            deposit_9 = itemView.findViewById(R.id.deposit_9);
            deposit_12 = itemView.findViewById(R.id.deposit_12);
            deposit_18 = itemView.findViewById(R.id.deposit_18);
            deposit_24 = itemView.findViewById(R.id.deposit_24);
        }
    }
}
