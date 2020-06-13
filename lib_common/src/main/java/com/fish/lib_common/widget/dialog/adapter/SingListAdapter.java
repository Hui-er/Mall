package com.fish.lib_common.widget.dialog.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;


import java.util.List;


/**
 * Created by xieshulan on 2019/6/19.
 * 通用的底部单选弹窗
 */

public abstract class SingListAdapter<T, VH extends SingListAdapter.SingleViewHolder> extends RecyclerView.Adapter<VH> {

    private int mLayoutId;
    private List<T> mList;
    public OnItemClickListener<T> onItemClickListener;
    public OnItemLongClickListener<T> onItemLongClickListener;
    private String selectData;

    public SingListAdapter(int mLayoutId, List<T> list, String selectData) {
        this(null, mLayoutId, list, selectData);
    }

    public SingListAdapter(Context context, int mLayoutId, List<T> list, String selectData) {
        this.mLayoutId = mLayoutId;
        this.mList = list;
        this.selectData = selectData;
    }

    public List<T> getList() {
        return mList;
    }

    public void setList(List<T> mList) {
        this.mList = mList;
    }

    public String getSelectData() {
        return selectData;
    }

    public void setSelectData(String selectData) {
        this.selectData = selectData;
    }

    public void setOnItemClickListener(OnItemClickListener<T> onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void setOnItemLongClickListener(OnItemLongClickListener<T> onItemLongClickListener) {
        this.onItemLongClickListener = onItemLongClickListener;
    }

    public abstract VH onCreateSingleViewHolder(Context context, View view);

    public abstract void onBindSingleViewHolder(VH holder, T data);

    public abstract void markSelectItem(VH holder,String selectData, T selectItem);

    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(mLayoutId, parent, false);
        return onCreateSingleViewHolder(parent.getContext(), view);
    }

    @Override
    public void onBindViewHolder(VH holder, int position) {
        onBindSingleViewHolder(holder, mList.get(position));
        markSelectItem(holder, selectData,mList.get(position));
    }


    @Override
    public int getItemCount() {
        return mList != null ? mList.size() : 0;
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    public abstract class SingleViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {

        public SingleViewHolder(Context context, View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (onItemClickListener != null) {
                onItemClickListener.onItemClickListener(v, mList.get(getAdapterPosition()));
            }
        }

        @Override
        public boolean onLongClick(View v) {
            if (onItemLongClickListener != null) {
                onItemLongClickListener.onItemLongClickListener(v, mList.get(this.getAdapterPosition()));
            }
            return true;
        }
    }

    public interface OnItemLongClickListener<A> {
        void onItemLongClickListener(View view, A data);
    }

    public interface OnItemClickListener<A> {
        void onItemClickListener(View view, A data);
    }
}
