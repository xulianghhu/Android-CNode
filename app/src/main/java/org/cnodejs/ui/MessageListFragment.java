package org.cnodejs.ui;

import android.text.TextUtils;

import com.github.kevinsawicki.wishlist.SingleTypeAdapter;

import org.cnodejs.CNodeApplication;
import org.cnodejs.CNodeClient;
import org.cnodejs.R;
import org.cnodejs.model.Message;
import org.cnodejs.model.MessageData;

import java.util.ArrayList;
import java.util.List;

public class MessageListFragment extends ItemListFragment<Message, MessageData> {

	@Override
	protected boolean isPageable() {
		return false;
	}

	@Override
	protected SingleTypeAdapter createAdapter() {
		return new MessageListAdapter(getActivity(), items);
	}

	@Override
	protected void loadData() {
		String accessToken = ((CNodeApplication) getActivity().getApplication()).getAccessToken();
		if (TextUtils.isEmpty(accessToken)) {
			isLoading = false;
			showEmpty(R.string.has_not_login);
		} else {
			isLoading = true;
			CNodeClient.getMessages(this, this, accessToken);
		}

	}

	@Override
	protected List<Message> getItemList(MessageData response) {
		List<Message> messageList = new ArrayList<Message>();
		if (null == response.getData())
			return null;
		if (null != response.getData().getHasnotReadMessages())
			messageList.addAll(response.getData().getHasnotReadMessages());
		if (null != response.getData().getHasReadMessages())
			messageList.addAll(response.getData().getHasReadMessages());
		return messageList;
	}
}
