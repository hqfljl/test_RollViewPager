package com.dance.rollviewpager;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ImageView;

import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.display.BitmapDisplayer;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.nostra13.universalimageloader.core.process.BitmapProcessor;

/**
 * ImageCacheLoader工具?
 *
 * @auther lxj
 * @version 1.0
 */
public class ImageCacheUtil {

	/**
	 * 必须在application create的时候调甿	 *
	 * @param context
	 */

	public static void init(Context context) {
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
				context).threadPriority(Thread.NORM_PRIORITY - 2)
				.denyCacheImageMultipleSizesInMemory().threadPoolSize(5)
				.discCacheFileNameGenerator(new Md5FileNameGenerator())
				.tasksProcessingOrder(QueueProcessingType.LIFO).enableLogging() // Not
				// necessary
				.build();
		ImageLoader.getInstance().init(config);
	}

	// options
	public interface OPTIONS {
		public DisplayImageOptions default_options = new DisplayImageOptions.Builder()
				.bitmapConfig(Bitmap.Config.RGB_565)
				.showStubImage(R.drawable.ic_launcher)
				.showImageForEmptyUri(R.drawable.ic_launcher)
				.showImageOnFail(R.drawable.ic_launcher)
				.cacheInMemory()
				.cacheOnDisc()
				.displayer(new BitmapDisplayer() {
					@Override
					public Bitmap display(Bitmap arg0, ImageView arg1) {
						arg1.setImageBitmap(arg0);
						return arg0;
					}
				})
				.build();;

	}

}
