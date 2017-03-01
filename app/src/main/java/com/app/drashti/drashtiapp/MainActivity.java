package com.app.drashti.drashtiapp;

        import android.content.Intent;
        import android.graphics.Color;
        import android.graphics.PorterDuff;
        import android.os.Bundle;
        import android.support.design.widget.TabLayout;
        import android.support.v4.app.Fragment;
        import android.support.v4.app.FragmentManager;
        import android.support.v4.app.FragmentPagerAdapter;
        import android.support.v4.view.ViewPager;
        import android.support.v7.app.AppCompatActivity;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.widget.TextView;


        import com.app.drashti.drashtiapp.Fragment.MyAcceptedFoodFragment;
        import com.app.drashti.drashtiapp.Fragment.MyDonationFragment;
        import com.app.drashti.drashtiapp.Fragment.DonateFragment;
        import com.app.drashti.drashtiapp.Fragment.RequestReciveFoodFragment;
        import com.app.drashti.drashtiapp.Fragment.RequestSendFragment;
        import com.app.drashti.drashtiapp.Fragment.VolunteersFragment;

        import java.util.ArrayList;
        import java.util.List;

/**
 */
public class MainActivity extends AppCompatActivity {
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private TextView profile ;
    public  DonateFragment donateFragment;
    public  RequestReciveFoodFragment  requestReciveFoodFragment;
    public  VolunteersFragment volunteersFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        initCommponant();
        initData();
        setupTabLayout(tabLayout);
        initOnClickListner();

        viewPager.setCurrentItem(tabLayout.getSelectedTabPosition());

    }

    public void initCommponant() {
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        profile = (TextView) findViewById(R.id.profile);
    }

    public void initData() {

        donateFragment = new DonateFragment();
        requestReciveFoodFragment = new RequestReciveFoodFragment();
        volunteersFragment = new VolunteersFragment();

        setupViewPager(viewPager);

    }

    public void initOnClickListner() {

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this,ProfileSettingActivity.class);
                startActivity(intent);
            }
        });
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new MyDonationFragment(), "MY DONATIONS");
        adapter.addFragment(donateFragment, "ACCEPT");
        adapter.addFragment(new MyAcceptedFoodFragment(), "MY ACCEPTED DONATIONS ");
        adapter.addFragment(volunteersFragment, "BE A VOLUNTEER");
        adapter.addFragment(new RequestSendFragment(),"REQUEST SENT");
        adapter.addFragment(requestReciveFoodFragment, "REQUEST RECEIVED ");

        viewPager.setAdapter(adapter);

    }

    public void setupTabLayout(TabLayout tabLayout) {
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        tabLayout.setTabGravity(TabLayout.GRAVITY_CENTER);
        tabLayout.setupWithViewPager(viewPager);

        TextView tab1 = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        tab1.setText("MY DONATIONS");
        tab1.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_donate, 0, 0);
        tabLayout.getTabAt(0).setCustomView(tab1);

        TextView tab2 = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        tab2.setText("ACCEPT");
        tab2.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_accept, 0, 0);
        tabLayout.getTabAt(1).setCustomView(tab2);

        TextView tab3 = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        tab3.setText("MY ACCEPTED DONATIONS");
        tab3.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_my_accept, 0, 0);
        tabLayout.getTabAt(2).setCustomView(tab3);

        TextView tab4 = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        tab4.setText("BE A VOLUNTEER");
        tab4.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_volunter, 0, 0);
        tabLayout.getTabAt(3).setCustomView(tab4);

        TextView tab5 = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        tab5.setText("REQUEST SENT");
        tab5.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.icreq_sent, 0, 0);
        tabLayout.getTabAt(4).setCustomView(tab5);

        TextView tab6 = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        tab6.setText("REQUEST RECEIVED");
        tab6.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.icreq_receive, 0, 0);
        tabLayout.getTabAt(5).setCustomView(tab6);

        //..
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

}
