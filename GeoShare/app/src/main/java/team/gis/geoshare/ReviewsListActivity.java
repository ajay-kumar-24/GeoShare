package team.gis.geoshare;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

import team.gis.geoshare.adapters.ReviewListAdapter;
import team.gis.geoshare.model.ReviewsModel;

public class ReviewsListActivity extends AppCompatActivity implements ReviewListAdapter.OnItemClickListener {

    RecyclerView reviewsRecycler;

    ReviewListAdapter adapter;
    ArrayList<ReviewsModel> reviewsList = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_review_list);

        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar aBar = getSupportActionBar();
        aBar.setDisplayHomeAsUpEnabled(true);
        aBar.setTitle(getString(R.string.reviews));

        reviewsRecycler = (RecyclerView) findViewById(R.id.rv_review_list_recycler);

        reviewsRecycler.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        reviewsRecycler.setLayoutManager(linearLayoutManager);

        setDummyData();

        adapter = new ReviewListAdapter(this, reviewsList);
        reviewsRecycler.setAdapter(adapter);
        adapter.SetOnItemClickListener(this);
    }

    @Override
    public void onItemClick(View view, int position) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.reviews_pop_message, null);
        dialogBuilder.setView(dialogView);

        TextView reviewText = (TextView) dialogView.findViewById(R.id.tv_reviews_pop_messageText);
        reviewText.setText(reviewsList.get(position).getReviewText());
        AlertDialog b = dialogBuilder.create();
        b.setCancelable(true);
        b.show();
    }

    private void setDummyData() {
        for (int i = 0; i < 10; i++) {
            ReviewsModel reviewsItem = new ReviewsModel();
            reviewsItem.setReviewUser("Susanth Dahal");
            reviewsItem.setReviewText("Had some awesome chicken momo's and awesome coffee here!");
            reviewsItem.setReviewDate("14/11/2016");
            reviewsList.add(reviewsItem);
        }
    }

    /*
   * Function to handle click on the back button in action bar
   * */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        switch (itemId) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
