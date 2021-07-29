package com.rifeng.p2p.adapter;

import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.rifeng.p2p.R;

import java.util.ArrayList;
import java.util.List;

public class BluetoothListAdapter  extends RecyclerView.Adapter<BluetoothListAdapter.BluetoothViewHolder> {


    protected Context mContext;
    protected List<BluetoothDevice> mDatas;
    protected LayoutInflater mInflater;
    protected OnItemClickListener listener;
    private int selection = -1;


    public BluetoothListAdapter(Context context , List<BluetoothDevice> datas, BluetoothListAdapter.OnItemClickListener listener) {
        this.mContext = context;
        this.mDatas = datas;
        this.listener = listener;
    }

    @NonNull
    @Override
    public BluetoothViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = (View)View.inflate(mContext ,R.layout.bluetooth_list_item_layout ,null);
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        itemView.setLayoutParams(lp);
        return new BluetoothListAdapter.BluetoothViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull BluetoothViewHolder holder, int position) {
        BluetoothDevice bean = mDatas.get(position);
        if (bean == null){
            return;
        }

        if (position == selection) {
            holder.bluetoothNameTextView.setTextColor(mContext.getColor(R.color.Color_Main));
            holder.bluetoothUUIDTextView.setTextColor(mContext.getColor(R.color.Color_Main));
            holder.progressBar.setVisibility(View.VISIBLE);
            holder.progressBar.startActionMode(new ActionMode.Callback() {
                @Override
                public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                    return false;
                }

                @Override
                public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                    return false;
                }

                @Override
                public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
                    return false;
                }

                @Override
                public void onDestroyActionMode(ActionMode mode) {

                }
            });
        } else {
            holder.bluetoothNameTextView.setTextColor(mContext.getColor(R.color.Color_Word85));
            holder.bluetoothUUIDTextView.setTextColor(mContext.getColor(R.color.Color_Word45));
            holder.progressBar.setVisibility(View.INVISIBLE);
        }
        String devName = bean.getName();
        if (devName == null || devName.length() == 0) {
            devName = "unknow-device";
        }
        holder.bluetoothNameTextView.setText(devName);
        holder.bluetoothUUIDTextView.setText(bean.getAddress());

        holder.bgLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                listener.onItemClick(view,position,bean);

            }
        });
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }


    class BluetoothViewHolder extends RecyclerView.ViewHolder{

        LinearLayout bgLayout;
        TextView bluetoothNameTextView;
        TextView bluetoothUUIDTextView;
        ProgressBar progressBar;

        public BluetoothViewHolder(@NonNull View itemView) {
            super(itemView);
            bluetoothNameTextView = itemView.findViewById(R.id.bluetooth_name_textView);
            bluetoothUUIDTextView = itemView.findViewById(R.id.bluetooth_uuid_textview);
            progressBar = itemView.findViewById(R.id.bluetooth_progressBar);
            bgLayout = itemView.findViewById(R.id.bluetooth_list_item_bglayout);
        }
    }

    public interface OnItemClickListener<T> {
        void onItemClick(View itemView, int position, T item);
        boolean onItemLongClick(View itemView, int position, T item);

    }

    public void setSelect(int position) {
        selection = position;
        notifyDataSetChanged();
    }
}
