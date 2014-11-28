package org.cnodejs.ui;

import android.content.Context;

import com.android.volley.toolbox.NetworkImageView;
import com.github.kevinsawicki.wishlist.SingleTypeAdapter;

import org.cnodejs.R;
import org.cnodejs.model.Topic;
import org.cnodejs.utils.images.ImageCacheManager;

import java.util.List;

public class TopicListAdapter extends SingleTypeAdapter<Topic> {

	public TopicListAdapter(Context context, List<Topic> items) {
		super(context, R.layout.topic_item);
		setItems(items);
	}

	@Override
	protected int[] getChildViewIds() {
		return new int[]{
				R.id.topic_avatar,
				R.id.topic_title,
				R.id.topic_content,
				R.id.topic_visit,
				R.id.topic_comment,
				R.id.topic_author,
				R.id.topic_reply_time,
		};
	}

	@Override
	protected void update(int position, Topic topic) {
		if (null != topic) {
			getView(0, NetworkImageView.class).setImageUrl(
					topic.getAuthor().getAvatarUrl(),
					ImageCacheManager.getInstance().getImageLoader());
			setText(1, topic.getTitle());
			setText(2, topic.getContent());
			setText(3, String.valueOf(topic.getVisitCount()));
			setText(4, String.valueOf(topic.getReplyCount()));
			setText(5, topic.getAuthor().getLoginname());
			setText(6, topic.getLastReplyAt().toString());
		}
	}
}
