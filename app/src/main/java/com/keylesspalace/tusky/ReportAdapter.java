package com.keylesspalace.tusky;

import android.support.v7.widget.RecyclerView;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

class ReportAdapter extends RecyclerView.Adapter {
    static class ReportStatus {
        String id;
        Spanned content;
        boolean checked;

        ReportStatus(String id, Spanned content, boolean checked) {
            this.id = id;
            this.content = content;
            this.checked = checked;
        }

        @Override
        public int hashCode() {
            return id.hashCode();
        }

        @Override
        public boolean equals(Object other) {
            if (this.id == null) {
                return this == other;
            } else if (!(other instanceof ReportStatus)) {
                return false;
            }
            ReportStatus status = (ReportStatus) other;
            return status.id.equals(this.id);
        }
    }

    private List<ReportStatus> statusList;

    ReportAdapter() {
        super();
        statusList = new ArrayList<>();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_report_status, parent, false);
        return new ReportStatusViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        ReportStatusViewHolder holder = (ReportStatusViewHolder) viewHolder;
        ReportStatus status = statusList.get(position);
        holder.setupWithStatus(status);
    }

    @Override
    public int getItemCount() {
        return statusList.size();
    }

    void addItem(ReportStatus status) {
        int end = statusList.size();
        statusList.add(status);
        notifyItemInserted(end);
    }

    void addItems(List<ReportStatus> newStatuses) {
        int end = statusList.size();
        int added = 0;
        for (ReportStatus status : newStatuses) {
            if (!statusList.contains(status)) {
                statusList.add(status);
                added += 1;
            }
        }
        if (added > 0) {
            notifyItemRangeInserted(end, added);
        }
    }

    String[] getCheckedStatusIds() {
        List<String> idList = new ArrayList<>();
        for (ReportStatus status : statusList) {
            if (status.checked) {
                idList.add(status.id);
            }
        }
        return idList.toArray(new String[0]);
    }

    private static class ReportStatusViewHolder extends RecyclerView.ViewHolder {
        private TextView content;
        private CheckBox checkBox;

        ReportStatusViewHolder(View view) {
            super(view);
            content = (TextView) view.findViewById(R.id.report_status_content);
            checkBox = (CheckBox) view.findViewById(R.id.report_status_check_box);
        }

        void setupWithStatus(final ReportStatus status) {
            content.setText(status.content);
            checkBox.setChecked(status.checked);
            checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    status.checked = isChecked;
                }
            });
        }
    }
}