package org.cnodejs;

import android.app.Application;
import android.graphics.Bitmap;

import org.cnodejs.utils.images.ImageCacheManager;

public class CNodeApplication extends Application {

	private String accessToken;

	private static int DISK_IMAGECACHE_SIZE = 1024 * 1024 * 10;
	private static Bitmap.CompressFormat DISK_IMAGECACHE_COMPRESS_FORMAT = Bitmap.CompressFormat.PNG;
	private static int DISK_IMAGECACHE_QUALITY = 100;

	@Override
	public void onCreate() {
		super.onCreate();
		init();
	}

	private void init() {
		RequestManager.init(this);
		createImageCache();
	}

	private void createImageCache() {
		ImageCacheManager.getInstance().init(this,
				this.getPackageCodePath()
				, DISK_IMAGECACHE_SIZE
				, DISK_IMAGECACHE_COMPRESS_FORMAT
				, DISK_IMAGECACHE_QUALITY
				, ImageCacheManager.CacheType.MEMORY);
	}

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}
}