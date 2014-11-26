package org.cnodejs.ui;

import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.cnodejs.R;
import org.cnodejs.RequestManager;
import org.cnodejs.model.TopicData;

import butterknife.InjectView;
import butterknife.Views;

public class TopicFragment extends Fragment {

	protected ListView mTopicList;
	private TopicListAdapter mAdapter;
	protected View mLoading;


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		mTopicList = (ListView) inflater.inflate(R.layout.fragment_topic, container, false);
//		Views.inject(this, v);
//		((AnimationDrawable) mLoading.getBackground()).start();
		return mTopicList;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		if (mAdapter == null) {
			RequestManager.getTopics(createReqSuccessListener(), createReqErrorListener(), 1, 10, false, null);
		} else {
			mTopicList.setAdapter(mAdapter);
		}

	}

	private Response.Listener<TopicData> createReqSuccessListener() {
		return new Response.Listener<TopicData>() {
			@Override
			public void onResponse(TopicData response) {
				mAdapter = new TopicListAdapter(getActivity(), response);
				mTopicList.setAdapter(mAdapter);
			}
		};
	}

	private Response.ErrorListener createReqErrorListener() {
		return new Response.ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError error) {
			}
		};
	}
}
