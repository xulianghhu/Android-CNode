package org.cnodejs.model;

import java.util.List;

public class MessageGroup {

	private List<Message> hasReadMessages;
	private List<Message> hasnotReadMessages;

	public List<Message> getHasReadMessages() {
		return hasReadMessages;
	}

	public void setHasReadMessages(List<Message> hasReadMessages) {
		this.hasReadMessages = hasReadMessages;
	}

	public List<Message> getHasnotReadMessages() {
		return hasnotReadMessages;
	}

	public void setHasnotReadMessages(List<Message> hasnotReadMessages) {
		this.hasnotReadMessages = hasnotReadMessages;
	}
}
