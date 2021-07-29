package com.rifeng.p2p.adapter;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.rifeng.p2p.R;
import com.rifeng.p2p.activity.PressureTestActivity;
import com.rifeng.p2p.entity.Option;
import com.rifeng.p2p.util.DisplayUtil;

import java.util.ArrayList;
import java.util.List;

public class RoundAdapter extends RecyclerView.Adapter<RoundAdapter.RoundViewHolder> {


    private Context context;
    private List<Option> datas; //接受传入数据

    public void setCurrentPositon(int currentPositon) {
        this.currentPositon = currentPositon;
    }

    private int currentPositon = -1;

    public RoundAdapter(Context context, List<Option> datas) {
        this.context = context;
        this.datas = datas;
    }

    @NonNull
    @Override
    public RoundAdapter.RoundViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = (View) View.inflate(context, R.layout.round_item_layout, null);
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        itemView.setLayoutParams(lp);
        return new RoundAdapter.RoundViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RoundAdapter.RoundViewHolder holder, int position) {

        Option option = datas.get(position);
        holder.roundTextView.setText("Round" + (position + 1));
        String content = String.format(context.getString(R.string.pressuretest_test_progress_content),  (int)(Double.parseDouble(option.getStartPressure())),  (int)(Double.parseDouble(option.getTestTime())),  (int)(Double.parseDouble(option.getStartPressure()) +  (int)(Double.parseDouble(option.getCannotpressureNum()))),  (int)(Double.parseDouble(option.getPressureDecRange())));

        holder.detailTextView.setText("");

        holder.topLineView.setVisibility(View.VISIBLE);
        holder.bottomLineView.setVisibility(View.VISIBLE);
        if (position == 0){
            holder.topLineView.setVisibility(View.INVISIBLE);
        }
        if (position == datas.size() - 1){
            holder.bottomLineView.setVisibility(View.INVISIBLE);
        }

        if (position == currentPositon && position != datas.size() - 1){
            holder.detailLineView.setVisibility(View.VISIBLE);
        }else{
            holder.detailLineView.setVisibility(View.INVISIBLE);
        }

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(DisplayUtil.dip2px(context,20),
                DisplayUtil.dip2px(context,20));
        holder.iconImageView.setLayoutParams(params);


        holder.detailBgLayout.setVisibility(View.GONE);
        if (position == currentPositon){
            LinearLayout.LayoutParams params2 = new LinearLayout.LayoutParams(DisplayUtil.dip2px(context,20),
                    DisplayUtil.dip2px(context,20));
            holder.iconImageView.setLayoutParams(params2);
            //当前轮
            holder.detailTextView.setText(content);
            holder.detailTextView.setTextColor(ContextCompat.getColor(context, R.color.Color_Word45));
            holder.iconImageView.setImageResource(R.mipmap.oval_round);
            holder.detailBgLayout.setVisibility(View.VISIBLE);
            holder.roundTextView.setTextColor(ContextCompat.getColor(context, R.color.Color_Word45));

        }else if(position < currentPositon){
            //
            holder.roundTextView.setTextColor(ContextCompat.getColor(context, R.color.Color_Pass_Text));
            holder.iconImageView.setImageResource(R.mipmap.over_test);
        }else{
            holder.iconImageView.setImageResource(R.mipmap.oval_round);
            holder.roundTextView.setTextColor(ContextCompat.getColor(context, R.color.Color_Word45));

        }

    }

    @Override
    public int getItemCount() {
        Log.i("====data size:" , datas.size() + "");
        return datas.size();
    }

    class RoundViewHolder extends RecyclerView.ViewHolder {

        View topLineView;
        View bottomLineView;

        TextView roundTextView;
        ImageView iconImageView;
        TextView detailTextView;

        View detailLineView;

        LinearLayout detailBgLayout;


        public RoundViewHolder(@NonNull View itemView) {
            super(itemView);
            roundTextView = itemView.findViewById(R.id.round_textview);
            iconImageView = itemView.findViewById(R.id.round_icon_imageview);
            detailTextView = itemView.findViewById(R.id.round_Detail_textview);

            topLineView = itemView.findViewById(R.id.round_top_line_view);
            bottomLineView = itemView.findViewById(R.id.round_bottom_line_view);

            detailLineView  = itemView.findViewById(R.id.round_Detail_line_view);

            detailBgLayout = itemView.findViewById(R.id.detail_bg_layout);
        }
    }

    class ItemClick {


    }

}
