package com.cucumber007.prototypes.activities._ui.recycler;

import android.animation.ValueAnimator;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.TextView;

import com.cucumber007.prototypes.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {

    private Context context;
    private List<String> dataset;

    public RecyclerAdapter(Context context, List<String> dataset) {
        this.context = context;
        this.dataset = dataset;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //called when recycler need VH (data) to represent item

        View v = LayoutInflater.from(context)
                .inflate(R.layout.item_menu_list_expandable, parent, false);

        return new ViewHolder(v);
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.text.setText(dataset.get(position));
    }


    @Override
    public int getItemCount() {
        return dataset.size();
    }


    //VH to View = 1 to 1
    //VH != data, VH = link to view
    public static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.text) TextView text;
        @BindView(R.id.animate_text) TextView animateText;

        public ViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);

            //it is here because of recycler logic ¯\_(ツ)_/¯
            v.setOnClickListener(v1 -> {
                ValueAnimator valueAnimator;
                valueAnimator = ValueAnimator.ofInt(200, 0);
                valueAnimator.setDuration(300);
                valueAnimator.setInterpolator(new LinearInterpolator());
                valueAnimator.addUpdateListener(animation -> {
                    Integer value = (Integer) animation.getAnimatedValue();
                    animateText.getLayoutParams().height = value.intValue();
                    animateText.requestLayout();
                });
                valueAnimator.start();

                //real position, "POST LAYOUT"
                Log.d("cutag", "" + getAdapterPosition());

                //visible position, differs if there is pending changes, "PRE LAYOUT"
                Log.d("cutag", "" + getLayoutPosition());
            });
        }



    }
}
