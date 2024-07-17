package com.fpt.team5.golddigger;

import android.graphics.Color;
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

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class BankInterestRateAdapter extends RecyclerView.Adapter<BankInterestRateAdapter.ViewHolder> {
    private final List<Datum> bankList;

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
        for (InterestRate rate : bank.getInterestRates()) {
            String rateValue;
            if (rate.getValue() == null) {
                rateValue = "  -  ";
            } else {
                rateValue = rate.getValue().toString() + "%";
            }
            switch (rate.getDeposit()) {
                case 1:
                    holder.deposit_1.setText(rateValue);
                    break;
                case 3:
                    holder.deposit_3.setText(rateValue);
                    break;
                case 6:
                    holder.deposit_6.setText(rateValue);
                    break;
                case 9:
                    holder.deposit_9.setText(rateValue);
                    break;
                case 12:
                    holder.deposit_12.setText(rateValue);
                    break;
                case 18:
                    holder.deposit_18.setText(rateValue);
                    break;
                case 24:
                    holder.deposit_24.setText(rateValue);
                    break;
                default:
                    break;
            }
        }
        int backgroundColor = (position % 2 == 0) ?
                Color.WHITE :
                Color.parseColor("#f0f0f0");

        holder.itemView.setBackgroundColor(backgroundColor);
    }

    public void sortByDeposit(int deposit, boolean isAscending) {
        Collections.sort(bankList, (bank1, bank2) -> {
            double rate1 = findInterestRateForDeposit(bank1, deposit);
            double rate2 = findInterestRateForDeposit(bank2, deposit);
            return Double.compare(rate1, rate2) * (isAscending?1:-1);
        });
        notifyItemRangeChanged(0, bankList.size());
    }

    public void sortByBankName(boolean isAscending) {
        if (isAscending) {
            Collections.sort(bankList, (bank1, bank2) -> bank1.getName().compareToIgnoreCase(bank2.getName()));
        } else {
            Collections.sort(bankList, (bank1, bank2) -> bank2.getName().compareToIgnoreCase(bank1.getName()));
        }
        notifyItemRangeChanged(0, bankList.size());
    }

    private double findInterestRateForDeposit(Datum bank, int deposit) {
        try {
            for (InterestRate rate : bank.getInterestRates()) {
                if (rate.getDeposit() == deposit) {
                    if (rate.getValue() != null)
                        return (double) rate.getValue();
                    else
                        return 0.0;
                }
            }
        } catch (Exception e) {
            return 0.0;
        }
        return 0.0;
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
