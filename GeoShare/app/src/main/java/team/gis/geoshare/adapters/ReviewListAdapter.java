package team.gis.geoshare.adapters;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import team.gis.geoshare.R;
import team.gis.geoshare.model.ReviewsModel;

public class ReviewListAdapter extends RecyclerView.Adapter<ReviewListAdapter.ReviewsViewHolder>{

    AppCompatActivity activity;
    ArrayList<ReviewsModel> reviewsList;

    OnItemClickListener mItemClickListener;

    public ReviewListAdapter(AppCompatActivity activity, ArrayList<ReviewsModel> list) {
        this.activity = activity;
        this.reviewsList = list;
    }

    public class ReviewsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public final View mView;

        TextView reviewUser, reviewText, reviewDate;

        public ReviewsViewHolder(View view) {
            super(view);
            mView = view;

            reviewUser = (TextView) mView.findViewById(R.id.tv_reviews_item_userName);
            reviewText = (TextView) mView.findViewById(R.id.tv_reviews_item_review);
            reviewDate = (TextView) mView.findViewById(R.id.tv_reviews_item_reviewDate);

            mView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mItemClickListener != null)
                mItemClickListener.onItemClick(view, getPosition());
        }
    }

    @Override
    public ReviewsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.reviews_item, parent, false);
        return new ReviewsViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ReviewsViewHolder holder, final int position) {
        if (holder instanceof ReviewsViewHolder) {
            final ReviewsModel currentItem = reviewsList.get(position);

            holder.reviewUser.setText(currentItem.getReviewUser());
            holder.reviewText.setText(currentItem.getReviewText());
            holder.reviewDate.setText(currentItem.getReviewDate());
        }
    }

    @Override
    public int getItemCount() {
        return reviewsList.size();
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public void SetOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }
}
