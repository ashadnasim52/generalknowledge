package datastructure.app.forhitest.getready;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class mAdapter extends RecyclerView.Adapter<mAdapter.ExampleViewHolder> {
    private Context mContext;
    private ArrayList<itemcalss> mylistofitem;

    public mAdapter(Context context, ArrayList<itemcalss> exampleList) {
        mContext = context;
        mylistofitem = exampleList;
    }

    @Override
    public ExampleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.layoutitem, parent, false);
        return new ExampleViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ExampleViewHolder holder, int position) {
        itemcalss currentItem = mylistofitem.get(position);

        String myQuestion = currentItem.getQuestion();
        String myAnswer = currentItem.getAnswer();

        holder.mTextViewQuesstion.setText(myQuestion);
        holder.mTextViewAnswer.setText(myAnswer);

//        Picasso.with(mContext).load(myQuestion).fit().centerInside().into(holder.mImageView);
    }


    @Override
    public int getItemCount() {
        return mylistofitem.size();
    }

    public class ExampleViewHolder extends RecyclerView.ViewHolder {
        public TextView mTextViewQuesstion;
        public TextView mTextViewAnswer;

        public ExampleViewHolder(View itemView) {
            super(itemView);

            mTextViewQuesstion = itemView.findViewById(R.id.text_view_question);
            mTextViewAnswer = itemView.findViewById(R.id.text_view_answer);


        }
    }
}