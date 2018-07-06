package com.sanprishali.feeditback;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import java.util.List;

    public class feedback2  extends RecyclerView.Adapter<feedback2.MyViewHolder> {

        private List<box> feedList;
        private ViewGroup parent;
        private int viewType;

        public class MyViewHolder extends RecyclerView.ViewHolder {
            public TextView name, desgn ,tag ,time,rate,comment;

            public MyViewHolder(View view) {
                super(view);
                name = (TextView) view.findViewById(R.id.name);
                desgn = (TextView) view.findViewById(R.id.desgn);
                tag = (TextView) view.findViewById(R.id.tag);
                time = (TextView) view.findViewById(R.id.time);
                rate =(TextView) view.findViewById(R.id.rate);
                comment= (TextView) view.findViewById(R.id.comment);
            }
        }


        public feedback(List<box> feedList) {
            this.feedList = feedList;
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.boxlayout, parent, false);

            return new MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {
            box box = feedList.get(position);
            holder.name.setText(box.getname());
            holder.desgn.setText(box.getDesgn());
            holder.tag.setText(box.getTag());
            holder.time.setText(box.getTime());
            holder.rate.setText(box.getRate());
            holder.comment.setText(box.getComment());
        }

        @Override
        public int getItemCount() {
            return feedList.size();
        }
    }

}
