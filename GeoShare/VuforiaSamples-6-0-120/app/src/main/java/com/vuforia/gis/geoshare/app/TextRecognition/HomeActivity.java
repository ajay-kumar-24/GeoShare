package com.vuforia.gis.geoshare.app.TextRecognition;

import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.vuforia.gis.geoshare.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class HomeActivity extends AppCompatActivity implements FriendFragment.OnFragmentInteractionListener,AddFriend.OnFragmentInteractionListener,View.OnClickListener {

    private NavigationView navigationView;
    private DrawerLayout drawer;
    CircleImageView imgProfile;
    private TextView txtName, txtWebsite;
    private Toolbar toolbar;


    // index to identify current nav menu item
    public static int navItemIndex = 0;

    // tags used to attach the fragments
    private static final String TAG_POST = "post";
    private static final String TAG_VIEW = "view";
    private static final String TAG_FRIENDS = "friends";
    private static final String TAG_ADDFRIENDS = "ADD";
    //public static String CURRENT_TAG = TAG_POST;

    public static String CURRENT_TAG = TAG_POST;

    // toolbar titles respected to selected nav menu item
    private String[] activityTitles;

    // flag to load home fragment when user presses back key
    private boolean shouldLoadHomeFragOnBackPress = true;
    private Handler mHandler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        get_intent_variables();
        mHandler = new Handler();

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.nav_view);

        // Navigation view header. Change according to logic for logged/not logged users accordingly
        View navHeader = navigationView.getHeaderView(0);
        txtName = (TextView) navHeader.findViewById(R.id.name);
        txtWebsite = (TextView) navHeader.findViewById(R.id.website);
        imgProfile = (CircleImageView) navHeader.findViewById(R.id.img_profile);

        // load toolbar titles from string resources
        activityTitles = getResources().getStringArray(R.array.nav_loggedin_titles);

//        If your header is clickable. Else remove this line
        navHeader.setOnClickListener(this);

        // load nav menu header data
        loadNavHeader();

        // initializing navigation menu
        setUpNavigationView();

        if (savedInstanceState == null) {
            navItemIndex = 0;
            CURRENT_TAG = TAG_POST;
            loadHomeFragment();
        }
    }

    /*
    * This function is for other views clickable
    * onitemSelected in setUpNavigationView() is called when menu item is clicked
    * */
    @Override
    public void onClick(View view) {
//        TODO: handle clicks on navigation header
        drawer.closeDrawers();
    }

    /***
     * Load navigation menu header information
     * like background image, profile image
     * name, website, notifications action view (dot)
     */
    public int user_id;
    public void get_intent_variables(){
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            user_id = extras.getInt("user_id");
            System.out.println("inside home activity "+user_id);
            //The key argument here must match that used in the other activity
        }
    }
    private void loadNavHeader() {
        // name, website
        if(user_id == 1) {
            txtName.setText("Indhu");
            txtWebsite.setText("kamalaku@usc.edu");

            // Loading profile image
            final String urlProfileImg = "https://encrypted-tbn2.gstatic.com/images?q=tbn:ANd9GcSyuz40djpGQQkEtL9a_AcA2ToLRLyCMeTTkj4q2Gp_oBfF9FHzCeI7gw";
            Glide.with(this).load(urlProfileImg)
                    .crossFade()
                    .thumbnail(0.5f)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(imgProfile);
        }
        else{
            txtName.setText("Ajay");
            txtWebsite.setText("ajay@usc.edu");

            // Loading profile image
            final String urlProfileImg = "https://media.licdn.com/mpr/mpr/shrinknp_200_200/AAEAAQAAAAAAAAcEAAAAJGNhOWE2YWYwLWVhN2ItNDg1Ni1hYjBjLWU2ODQwMmE3OGM1OA.jpg";
            Glide.with(this).load(urlProfileImg)
                    .crossFade()
                    .thumbnail(0.5f)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(imgProfile);

        }
    }

    /***
     * Returns respected fragment that user
     * selected from navigation menu
     */
    private void loadHomeFragment() {
        // selecting appropriate nav menu item
        selectNavMenu();

        // set toolbar title
        setToolbarTitle();

        // if user select the current navigation menu again, don't do anything
        // just close the navigation drawer
        if (getSupportFragmentManager().findFragmentByTag(CURRENT_TAG) != null) {
            drawer.closeDrawers();
            return;
        }

        // Sometimes, when fragment has huge data, screen seems hanging
        // when switching between navigation menus
        // So using runnable, the fragment is loaded with cross fade effect
        // This effect can be seen in GMail app
        Runnable mPendingRunnable = new Runnable() {
            @Override
            public void run() {
                // update the main content by replacing fragments
                Fragment fragment = getHomeFragment();
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.setCustomAnimations(android.R.anim.fade_in,
                        android.R.anim.fade_out);
                fragmentTransaction.replace(R.id.frame, fragment, CURRENT_TAG);
                fragmentTransaction.commitAllowingStateLoss();
            }
        };

        // If mPendingRunnable is not null, then add to the message queue
        if (mPendingRunnable != null) {
            mHandler.post(mPendingRunnable);
        }

        //Closing drawer on item click
        drawer.closeDrawers();

        // refresh toolbar menu
        invalidateOptionsMenu();
    }

    /*
    * Change fragment according to user click in the menu
    * */
    private Fragment getHomeFragment() {
        switch (navItemIndex) {
            case 0:
                // post default page open
                PostFragment postFragment = new PostFragment();
                // add any arguments you want to pass to the fragment here
                return postFragment;
            case 1:
                // photos
                ViewFragment viewFragment = new ViewFragment();
                // add any arguments you want to pass to the fragment here
                return viewFragment;
            case 2:
                FriendFragment friendFragment = new FriendFragment();
                return  friendFragment;
            case 3:
                AddFriend addFriend = new AddFriend();
                return  addFriend;
            default:
                return new PostFragment();
        }
    }

    private void setToolbarTitle() {
        getSupportActionBar().setTitle(activityTitles[navItemIndex]);
    }

    private void selectNavMenu() {
        navigationView.getMenu().getItem(navItemIndex).setChecked(true);
    }

    private void setUpNavigationView() {
        //Setting Navigation View Item Selected Listener to handle the item click of the navigation menu
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

            // This method will trigger on item Click of navigation menu
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {

                //Check to see which item was being clicked and perform appropriate action
                switch (menuItem.getItemId()) {
                    //Replacing the main content with ContentFragment Which is our Post View;
                    case R.id.nav_post:
                        navItemIndex = 0;
                        CURRENT_TAG = TAG_POST;
                        break;
                    case R.id.nav_view:
                        navItemIndex = 1;
                        CURRENT_TAG = TAG_VIEW;
                        break;
                    case R.id.nav_friends:
                        navItemIndex = 2;
                        CURRENT_TAG = TAG_FRIENDS;
                        break;
                    case R.id.nav_addfriend:
                        navItemIndex = 3;
                        CURRENT_TAG = TAG_ADDFRIENDS;
                        break;

                    default:
                        navItemIndex = 0;
                }

                //Checking if the item is in checked state or not, if not make it in checked state
                if (menuItem.isChecked()) {
                    menuItem.setChecked(false);
                } else {
                    menuItem.setChecked(true);
                }
                menuItem.setChecked(true);

                loadHomeFragment();

                return true;
            }
        });


        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.openDrawer, R.string.closeDrawer) {

            @Override
            public void onDrawerClosed(View drawerView) {
                // Code here will be triggered once the drawer closes as we dont want anything to happen so we leave this blank
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                // Code here will be triggered once the drawer open as we dont want anything to happen so we leave this blank
                super.onDrawerOpened(drawerView);
            }
        };

        //Setting the actionbarToggle to drawer layout
        drawer.addDrawerListener(actionBarDrawerToggle);

        //calling sync state is necessary or else your hamburger icon wont show up
        actionBarDrawerToggle.syncState();
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawers();
            return;
        }

        // This code loads home fragment when back key is pressed
        // when user is in other fragment than open post view
        if (shouldLoadHomeFragOnBackPress) {
            // checking if user is on other navigation menu
            // rather than post view
            if (navItemIndex != 0) {
                navItemIndex = 0;
                CURRENT_TAG = TAG_POST;
                loadHomeFragment();
                return;
            }
        }

        super.onBackPressed();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.

        // show menu only when home fragment is selected
        /*if (navItemIndex == 0) {
            getMenuInflater().inflate(R.menu.main, menu);
        }

        // when fragment is notifications, load the menu created for notifications
        if (navItemIndex == 3) {
            getMenuInflater().inflate(R.menu.notifications, menu);
        }*/
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}