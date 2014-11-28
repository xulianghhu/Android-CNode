package org.cnodejs;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;

import org.cnodejs.ui.AccountFragment;
import org.cnodejs.ui.ItemListFragment;
import org.cnodejs.ui.MessageListFragment;
import org.cnodejs.ui.TopicListFragment;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.InjectView;
import butterknife.Views;


public class MainActivity extends FragmentActivity implements ViewPager.OnPageChangeListener, View.OnClickListener {

	@InjectView(R.id.tab_topic)
	protected ImageView mTopicTab;
	@InjectView(R.id.tab_message)
	protected ImageView mMessageTab;
	@InjectView(R.id.tab_account)
	protected ImageView mAccountTab;
	@InjectView(R.id.view_pager)
	protected ViewPager mViewPager;

	List<Fragment> mFragments = Collections.emptyList();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Views.inject(this);

		mFragments = new ArrayList<Fragment>();
		mFragments.add(new TopicListFragment());
		mFragments.add(new MessageListFragment());
		mFragments.add(new AccountFragment());
		mViewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
			@Override
			public Fragment getItem(int i) {
				return mFragments.get(i);
			}

			@Override
			public int getCount() {
				return mFragments.size();
			}
		});

		mViewPager.setOnPageChangeListener(this);
		mTopicTab.setOnClickListener(this);
		mMessageTab.setOnClickListener(this);
		mAccountTab.setOnClickListener(this);

		mViewPager.setCurrentItem(0);
		mTopicTab.setSelected(true);
	}

	@Override
	public void onPageScrolled(int i, float v, int i2) {

	}

	@Override
	public void onPageSelected(int i) {
		mTopicTab.setSelected(i == 0);
		mMessageTab.setSelected(i == 1);
		mAccountTab.setSelected(i == 2);
		switch (i) {
			case 0:
			case 1:
				((ItemListFragment) mFragments.get(i)).onPageSelected();
				break;
		}
	}

	@Override
	public void onPageScrollStateChanged(int i) {

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.tab_topic:
				mViewPager.setCurrentItem(0);
				break;
			case R.id.tab_message:
				mViewPager.setCurrentItem(1);
				break;
			case R.id.tab_account:
				mViewPager.setCurrentItem(2);
				break;
		}
	}
}
