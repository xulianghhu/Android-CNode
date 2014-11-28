package org.cnodejs.ui;

import android.content.Context;

import com.android.volley.toolbox.NetworkImageView;
import com.github.kevinsawicki.wishlist.SingleTypeAdapter;

import org.cnodejs.R;
import org.cnodejs.model.Message;
import org.cnodejs.model.MessageData;
import org.cnodejs.model.Topic;
import org.cnodejs.utils.images.ImageCacheManager;

import java.util.List;

public class MessageListAdapter extends SingleTypeAdapter<Message> {

	public MessageListAdapter(Context context, List<Message> items) {
		super(context, R.layout.message_item);
		setItems(items);
	}

	@Override
	protected int[] getChildViewIds() {
		return new int[]{
				R.id.message_avatar,
				R.id.message_author,
				R.id.message_topic
		};
	}

	@Override
	protected void update(int position, Message message) {
		if (null != message) {
			getView(0, NetworkImageView.class).setImageUrl(
					message.getAuthor().getAvatarUrl(),
					ImageCacheManager.getInstance().getImageLoader());
			setText(1, message.getAuthor().getLoginname());
			setText(2, message.getTopic().getTitle());
		}
	}
}
