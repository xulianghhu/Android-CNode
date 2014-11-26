package org.cnodejs;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import android.content.Context;
import android.net.Uri;
import android.text.TextUtils;

import org.cnodejs.model.TopicData;

public class RequestManager {

	private static RequestQueue mRequestQueue;

	private RequestManager() {
	}

	public static void init(Context context) {
		mRequestQueue = Volley.newRequestQueue(context);
	}

	/**
	 * 在{@link org.cnodejs.CNodeApplication#onCreate()}中初始化
	 *
	 * @return
	 */
	public static RequestQueue getRequestQueue() {
		if (null != mRequestQueue) {
			return mRequestQueue;
		} else {
			throw new IllegalStateException("Not initialized");
		}
	}

	/**
	 * 主题首页
	 *
	 * @param listener      加载成功监听
	 * @param errorListener 加载失败监听
	 * @param page          页数
	 * @param limit         每一页的主题数量
	 * @param mdrender      是否渲染html
	 * @param tab           主题分类
	 */
	public static void getTopics(Response.Listener<TopicData> listener,
	                             Response.ErrorListener errorListener,
	                             int page,
	                             int limit,
	                             boolean mdrender,
	                             String tab) {

		Uri.Builder uriBuilder = Uri.parse(Url.GET_TOPICS).buildUpon()
				.appendQueryParameter("page", String.valueOf(page))
				.appendQueryParameter("limit", String.valueOf(limit))
				.appendQueryParameter("mdrender", String.valueOf(mdrender));
		if (!TextUtils.isEmpty(tab))
			uriBuilder.appendQueryParameter("tab", tab);
		String uri = uriBuilder.build().toString();

		GsonRequest<TopicData> request = new GsonRequest<TopicData>(Request.Method.GET
				, uri
				, TopicData.class
				, listener
				, errorListener);
		getRequestQueue().add(request);
	}
}
