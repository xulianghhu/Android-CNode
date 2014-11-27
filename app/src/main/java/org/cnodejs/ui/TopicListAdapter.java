package org.cnodejs.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.NetworkImageView;

import org.cnodejs.CNodeClient;
import org.cnodejs.R;
import org.cnodejs.model.Topic;
import org.cnodejs.model.TopicData;
import org.cnodejs.utils.images.ImageCacheManager;

import java.util.List;

import butterknife.InjectView;
import butterknife.Views;

public class TopicListAdapter extends ArrayAdapter<String> implements Response.Listener<TopicData>, Response.ErrorListener {

	private int page = 1;

	private final List<Topic> mData;
	private TopicData mTopicData;
	private boolean isLoading;
	private boolean moreDataToLoad;

	public TopicListAdapter(Context context, TopicData newData) {
		super(context, R.layout.topic_item);
		mData = newData.getData();
		mTopicData = newData;
		moreDataToLoad = true;
	}

	@Override
	public int getCount() {
		return mData.size();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View v = convertView;
		ViewHolder holder;

		if (shouldLoadMoreData(mData, position)) {
			loadMoreData();
		}

		if (v == null) {
			v = LayoutInflater.from(getContext()).inflate(R.layout.topic_item, parent, false);
			holder = new ViewHolder();
//			holder.iv_avatar = (NetworkImageView) v.findViewById(R.id.topic_avatar);
//			holder.tv_title = (TextView) v.findViewById(R.id.topic_title);
//			holder.tv_content = (TextView) v.findViewById(R.id.topic_content);
//			holder.tv_visit = (TextView) v.findViewById(R.id.topic_visit);
//			holder.tv_reply = (TextView) v.findViewById(R.id.topic_comment);
//			holder.tv_author = (TextView) v.findViewById(R.id.topic_author);
//			holder.tv_last_reply = (TextView) v.findViewById(R.id.topic_reply_time);
			Views.inject(holder, v);
			v.setTag(holder);
		} else {
			holder = (ViewHolder) v.getTag();
		}

		Topic topic = mData.get(position);
		if (topic != null) {
			holder.iv_avatar.setImageUrl(topic.getAuthor().getAvatarUrl(), ImageCacheManager.getInstance().getImageLoader());
			holder.tv_title.setText(topic.getTitle());
			holder.tv_content.setText(topic.getContent());
			holder.tv_visit.setText(String.valueOf(topic.getVisitCount()));
			holder.tv_reply.setText(String.valueOf(topic.getReplyCount()));
			holder.tv_author.setText(topic.getAuthor().getLoginname());
			holder.tv_last_reply.setText(topic.getLastReplyAt().toString());
		}
		return v;
	}

	private boolean shouldLoadMoreData(List<Topic> data, int position) {
		boolean scrollRangeReached = (position > (data.size() - 10));
		return (scrollRangeReached && !isLoading && moreDataToLoad);
	}

	private void loadMoreData() {
		isLoading = true;
		CNodeClient.getTopics(this, this, page++, 10, false, null);
	}

	@Override
	public void onResponse(TopicData response) {
		if (response != null) {
			mData.addAll(response.getData());
			mTopicData = response;
			moreDataToLoad = mTopicData.getData() != null && mTopicData.getData().size() == 10;
			notifyDataSetChanged();
		}
		isLoading = false;
	}

	@Override
	public void onErrorResponse(VolleyError error) {
		isLoading = false;
	}

	protected static class ViewHolder {
		@InjectView(R.id.topic_avatar)
		NetworkImageView iv_avatar;
		@InjectView(R.id.topic_title)
		TextView tv_title;
		@InjectView(R.id.topic_content)
		TextView tv_content;
		@InjectView(R.id.topic_visit)
		TextView tv_visit;
		@InjectView(R.id.topic_comment)
		TextView tv_reply;
		@InjectView(R.id.topic_author)
		TextView tv_author;
		@InjectView(R.id.topic_reply_time)
		TextView tv_last_reply;
	}
}
