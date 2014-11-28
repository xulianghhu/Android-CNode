package org.cnodejs.ui;

import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.github.kevinsawicki.wishlist.SingleTypeAdapter;
import com.github.kevinsawicki.wishlist.Toaster;

import org.cnodejs.CNodeClient;
import org.cnodejs.R;
import org.cnodejs.model.Topic;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;
import butterknife.Views;

public abstract class ItemListFragment<E, T> extends Fragment implements Response.Listener<T>, Response.ErrorListener {

	@InjectView(android.R.id.list)
	protected ListView listView;

	@InjectView(android.R.id.empty)
	protected TextView emptyView;

	@InjectView(R.id.loading)
	protected View loadingView;

	protected SingleTypeAdapter<E> adapter;
	protected List<E> items = new ArrayList<E>();
	protected boolean isLoading = false;
	protected boolean loadMore = true;

	protected int page = 1;
	protected int limit = 10;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		Log.w("", "onCreateView");
		return inflater.inflate(R.layout.item_list, container, false);
	}

	@Override
	public void onViewCreated(final View view, final Bundle savedInstanceState) {
		Log.w("", "onViewCreated");
		super.onViewCreated(view, savedInstanceState);
		Views.inject(this, view);
		((AnimationDrawable) loadingView.getBackground()).start();
		if (adapter == null) {
			adapter = createAdapter();
			loadData();
		}
		listView.setAdapter(adapter);
	}

	@Override
	public void onErrorResponse(VolleyError error) {
		isLoading = false;
		Toaster.showShort(getActivity(), R.string.load_failed);
	}

	@Override
	public void onResponse(T response) {
		List<E> itemList = getItemList(response);
		if (null == itemList)
			return;
		if (itemList.size() < limit || !isPageable())
			loadMore = false;
		items.addAll(itemList);
		adapter.setItems(items);
		isLoading = false;
	}

	protected abstract boolean isPageable();

	protected abstract SingleTypeAdapter createAdapter();

	protected abstract void loadData();

	protected abstract List<E> getItemList(T response);

	public void onPageSelected() {
		if (isLoading) {
			showLoading();
		} else if (items.isEmpty() && loadMore) {
			isLoading = true;
			page = 1;
			loadData();
			page++;
		} else {
			adapter.setItems(items);
			showList();
		}
	}

	protected void showList() {
		loadingView.setVisibility(View.GONE);
		emptyView.setVisibility(View.GONE);
		listView.setVisibility(View.VISIBLE);
	}

	protected void showLoading() {
		emptyView.setVisibility(View.GONE);
		listView.setVisibility(View.GONE);
		loadingView.setVisibility(View.VISIBLE);
	}

	protected void showEmpty(int resId) {
		loadingView.setVisibility(View.GONE);
		listView.setVisibility(View.GONE);
		emptyView.setVisibility(View.VISIBLE);
		emptyView.setText(getString(resId));
	}

}
