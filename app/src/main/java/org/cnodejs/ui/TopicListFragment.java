package org.cnodejs.ui;

import com.github.kevinsawicki.wishlist.SingleTypeAdapter;

import org.cnodejs.CNodeClient;
import org.cnodejs.model.Topic;
import org.cnodejs.model.TopicData;

import java.util.List;

public class TopicListFragment extends ItemListFragment<Topic, TopicData> {

	@Override
	protected boolean isPageable() {
		return true;
	}

	@Override
	protected SingleTypeAdapter createAdapter() {
		return new TopicListAdapter(getActivity(), items);
	}

	@Override
	protected void loadData() {
		CNodeClient.getTopics(this, this, page, limit, false, null);
	}

	@Override
	protected List<Topic> getItemList(TopicData response) {
		return response.getData();
	}

}
