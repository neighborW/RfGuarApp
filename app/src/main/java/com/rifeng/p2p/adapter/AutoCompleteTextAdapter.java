package com.rifeng.p2p.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;


import com.rifeng.p2p.R;
import com.rifeng.p2p.entity.UserForeManBean;

import java.util.ArrayList;
import java.util.List;

public class AutoCompleteTextAdapter extends BaseAdapter implements Filterable {

    private ArrayFilter mFilter;//数据过滤器
    private List<UserForeManBean> mList;//传进来的数据
    private ArrayList<UserForeManBean> mFilteredData;//
    private Context context;//上下文
    public static ArrayList<UserForeManBean> newvalues;
    public AutoCompleteTextAdapter(List<UserForeManBean> mList, Context context) {
        this.mList = mList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return mList == null ? 0 : mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;
        ViewHolder viewHolder;
        if (convertView == null) {
            view = View.inflate(context, R.layout.item_fore_man_email2, null);
            viewHolder = new ViewHolder();
            viewHolder.email = view.findViewById(R.id.fore_email_list_textview);
            view.setTag(viewHolder);
        } else {
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();
        }

        UserForeManBean resultlist = mList.get(position);
        viewHolder.email.setText(resultlist.getEmail());
        return view;
    }
    class ViewHolder {
        public TextView email;
    }

    /**
     * 在后台线程执行，定义过滤算法
     * getFilter()方法会返回一个Filter对象，Filter是一个数据过滤器，其过滤操作是通过performFiltering()方法和publishResult()方法完成的。
     * performFiltering（）方法进行过滤操作，publishResult（）方法用于发表过滤操作结果。
     *
     * @return
     */
    @Override
    public Filter getFilter() {
        //创建过滤器的对象
        if (mFilter == null) {
            mFilter = new ArrayFilter();
        }
        return mFilter;
    }
    //过滤器类
    class ArrayFilter extends Filter {
        /**
         * @param constraint:用户在输入框中所输入的内容
         * @return
         */
        @Override//进行过滤操作
        protected FilterResults performFiltering(CharSequence constraint) {
            //使用过滤操作的结果
            FilterResults results = new FilterResults();

            if (mFilteredData == null) {
                //以一个集合初始化mFilteredData=new ArrayList<String>(mList);//mList(数组容量)为集合
                mFilteredData = new ArrayList<>(mList);
            }
            //如果没有输入内容，则不过滤
            if (constraint == null || constraint.length() == 0) {
                ArrayList<UserForeManBean> list = mFilteredData;
                results.values = list;
                results.count = list.size();
            } else {
                //过滤的条件
                String constraintString = constraint.toString().toLowerCase();
                //将传进来的全部数据赋值给filteredValues
                ArrayList<UserForeManBean> filteredValues = mFilteredData;
                int count = filteredValues.size();

                newvalues = new ArrayList<>(count);
                //循环变量数据源，如果有属性满足过滤条件，则添加到result中
                for (int i = 0; i < count; i++) {
                    UserForeManBean resultData = filteredValues.get(i);
                    if (resultData != null) {

                        //过滤条件
                        if (resultData.getEmail() != null && resultData.getEmail().startsWith(constraintString)) {
                            newvalues.add(resultData);
                        }
                    }

                    results.values = newvalues;
                    results.count = newvalues.size();
                }
            }
            return results;
        }
        @Override//发表过滤操作结果
        protected void publishResults(CharSequence constraint, FilterResults results) {

            mList = (List<UserForeManBean>) results.values;
            if (results.count > 0) {
                //重绘当前可见区域
                notifyDataSetChanged();
            } else {
                //重绘控件，还原到初始状态
                notifyDataSetInvalidated();
            }
        }
    }

}