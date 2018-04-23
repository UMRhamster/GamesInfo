package com.whut.umrhamster.gamesinfo.View;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.whut.umrhamster.gamesinfo.Model.GameEvaluation;
import com.whut.umrhamster.gamesinfo.R;

import java.util.List;

/**
 * Created by 12421 on 2018/4/4.
 */

public class EvaluationRVAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener{
    private static final int TYPE_ITEM = 0;
    private static final int TYPE_FOOTER = 1;
    private boolean hasMore = false;
    private int showStatus = 0;
    private List<GameEvaluation> list;
    private Context context;
    //
    private onItemClickListener itemClickListener;

    public EvaluationRVAdapter(List<GameEvaluation> list, Context context){
        this.list = list;
        this.context = context;
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_ITEM){
            View view = LayoutInflater.from(context).inflate(R.layout.rv_item,parent,false);
            ViewHolder viewHolder = new ViewHolder(view);
            view.setOnClickListener(this);
            return viewHolder;
        }else if(viewType == TYPE_FOOTER){
            View view = LayoutInflater.from(context).inflate(R.layout.rv_footer,parent,false);
            FooterViewHolder footerViewHolder = new FooterViewHolder(view);
            return footerViewHolder;
        }else {
            return null;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ViewHolder){
            GameEvaluation gameEvaluation = list.get(position);
            ((ViewHolder)holder).title.setText(gameEvaluation.getTitle());
            ((ViewHolder)holder).description.setText(gameEvaluation.getTest());
            ((ViewHolder)holder).pubdate.setText("发布时间："+gameEvaluation.getPubdate().getDate());
            ((ViewHolder)holder).author.setText("作者："+gameEvaluation.getAuthor());
        }else if (holder instanceof FooterViewHolder){
            if (showStatus == 0){
                ((FooterViewHolder) holder).tips.setText("松开手指进行加载...");
                ((FooterViewHolder) holder).progressBar.setVisibility(View.GONE);
            }else if (showStatus == 1){
                ((FooterViewHolder) holder).tips.setText("正在加载更多...");
                ((FooterViewHolder) holder).progressBar.setVisibility(View.VISIBLE);
            }else {
                ((FooterViewHolder) holder).tips.setText("已经没有更多了...");
                ((FooterViewHolder) holder).progressBar.setVisibility(View.GONE);
            }
        }

        holder.itemView.setTag(position);
    }

    @Override
    public int getItemCount() {
        //第一次加载是不显示正在加载到的布局
        return list.isEmpty() ? 0 : list.size()+1;
    }

    @Override
    public int getItemViewType(int position) {
        if (position+1==getItemCount()){
            return TYPE_FOOTER;
        }else {
            return TYPE_ITEM;
        }
    }

    @Override
    public void onClick(View view) {
        if (itemClickListener != null){
            itemClickListener.onItemClick(view,(int)view.getTag());
        }
    }
    //正常item的ViewHolder
    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView title;
        TextView description;
        TextView pubdate;
        TextView author;

        public ViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.item_evaluation_title);
            description = itemView.findViewById(R.id.item_evaluation_description);
            pubdate = itemView.findViewById(R.id.item_evaluation_pubdate);
            author = itemView.findViewById(R.id.item_evaluation_author);

        }
    }
    //脚步布局的ViewHolder
    static class FooterViewHolder extends RecyclerView.ViewHolder{
        TextView tips;
        ProgressBar progressBar;
        public FooterViewHolder(View itemView) {
            super(itemView);
            tips = itemView.findViewById(R.id.rv_footer_tv);
            progressBar = itemView.findViewById(R.id.rv_footer_pb);
        }
    }

    public void changeShowStatus(int value){
        showStatus = value;
        notifyDataSetChanged();
    }

    public void setOnItemClickListener(onItemClickListener itemClickListener){
        this.itemClickListener = itemClickListener;
    }
    public interface onItemClickListener{
        void onItemClick(View view, int position);
    }
}
