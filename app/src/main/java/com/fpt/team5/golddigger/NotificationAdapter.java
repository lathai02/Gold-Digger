package com.fpt.team5.golddigger;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.fpt.team5.golddigger.Model.Notification;
import com.fpt.team5.golddigger.Model.Transaction;
import com.fpt.team5.golddigger.dal.MyDbContext;

import java.util.List;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.VH> {
    private List<Notification> notifications;
    private Context context;
    private LayoutInflater inflater;

    public NotificationAdapter(List<Notification> notifications, Context context) {
        this.notifications = notifications;
        this.context = context;
        this.inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public NotificationAdapter.VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = inflater.inflate(R.layout.notification_item, parent, false);
        return new NotificationAdapter.VH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationAdapter.VH holder, int position) {
        Notification n = notifications.get(position);
        holder.setTransaction(n);
    }

    @Override
    public int getItemCount() {
        return notifications.size();
    }

    protected class VH extends RecyclerView.ViewHolder {

        private Notification n;
        private TextView tvMessage;
        private TextView tvDate;

        private void bindingView() {
            tvDate = itemView.findViewById(R.id.tvDate);
            tvMessage = itemView.findViewById(R.id.tvMessage);
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

        public void setTransaction(Notification notification) {
            n = notification;
            tvDate.setText(n.getCreateDate());
            tvMessage.setText(n.getTitle());
        }
    }
}
