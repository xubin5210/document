/**    
 * @author ageng  
 * @Description: 下载 图片  管理 类
 * @Package com.master.util.image   
 * @Title: DownImageManager.java   
 * @date 2015-5-16 下午3:30:17   
 * @version V1.0 
 * @说明  代码版权归 杭州反盗版中心有限公司 所有
 */
package com.master.util.image;

import java.io.File;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.widget.ImageView;

import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiskCache;
import com.nostra13.universalimageloader.cache.disc.naming.HashCodeFileNameGenerator;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.nostra13.universalimageloader.core.listener.ImageLoadingProgressListener;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
import com.nostra13.universalimageloader.utils.StorageUtils;

public class DownImageManager {

	/**
	 * 下载 对象
	 */
	private ImageLoader imageLoader;

	/**
	 * 设置 参数
	 */
	private DisplayImageOptions options;

	public DownImageManager() {
		imageLoader = ImageLoader.getInstance();
	}

	/**
	 * @Auther: ageng
	 * @Description:初始化 iamgeImageLoader
	 * @Date:2015-5-15上午9:59:08
	 * @return void
	 * @说明 代码版权归 杭州反盗版中心有限公司 所有
	 */
	public void initImageLoader(Context context,String path) {
		File cacheDir = StorageUtils.getOwnCacheDirectory(context, path); 
		ImageLoaderConfiguration.Builder config = new ImageLoaderConfiguration.Builder(
				context);
		config.threadPriority(Thread.NORM_PRIORITY - 2);
		config.denyCacheImageMultipleSizesInMemory();
		config.diskCacheFileNameGenerator(new HashCodeFileNameGenerator());
		config.memoryCache(new WeakMemoryCache());
		config.diskCache(new UnlimitedDiskCache(cacheDir));
		config.memoryCacheSize(2 * 1024 * 1024);
		config.diskCacheSize(50 * 1024 * 1024); // 50 MiB
		config.tasksProcessingOrder(QueueProcessingType.LIFO);
		config.imageDownloader(new BaseImageDownloader(context, 5 * 1000,
				30 * 1000));
		config.writeDebugLogs();
		imageLoader.init(config.build());
	}

	/**
	 * @Auther: ageng
	 * @Description:初始化 下载 option
	 * @Date:2015-5-16下午4:34:28
	 * @param failedResId
	 * @return void
	 * @说明 代码版权归 杭州反盗版中心有限公司 所有
	 */
	public void initOption(int failedResId) {
		options = new DisplayImageOptions.Builder()
				.cacheInMemory(true)
				.cacheOnDisk(true)
				.considerExifParams(true)
				.bitmapConfig(Bitmap.Config.RGB_565).build();
	}

	/**
	 * @Auther: ageng
	 * @Description: option 可以 从 外部 设置
	 * @Date:2015-5-16下午4:35:47
	 * @param options
	 * @return void
	 * @说明 代码版权归 杭州反盗版中心有限公司 所有
	 */
	public void initOption(DisplayImageOptions options) {
		this.options = options;
	}

	/**
	 * @Auther: ageng
	 * @Description: 下载 图片
	 * @Date:2015-5-16下午4:08:39
	 * @param url
	 * @param imageView
	 * @return void
	 * @说明 代码版权归 杭州反盗版中心有限公司 所有
	 */
	public void downImage(String url, ImageView imageView) {
		imageView.setImageBitmap(null);
		initOption(0);
		imageLoader.displayImage(url, imageView,options);
	}
	
	/**
	 * @Auther: ageng  
	 * @Description: 下载 本地  图片 
	 * @Date:2015-6-10上午10:35:12
	 * @param url
	 * @param imageView  
	 * @return void 
	 * @说明  代码版权归 杭州反盗版中心有限公司 所有
	 */
	public void downLocalImage(String url, ImageView imageView){
		DisplayImageOptions options=new DisplayImageOptions.Builder()
		.cacheInMemory(true)
		.considerExifParams(true)
		.bitmapConfig(Bitmap.Config.RGB_565).build();
		imageLoader.displayImage(url, imageView,options);
	}

	/**
	 * @Auther: ageng
	 * @Description: 下载 图片 类 带 下载 进度
	 * @Date:2015-5-16下午4:07:49
	 * @param url
	 *            下载 地址
	 * @param imageView
	 * @param callBack
	 *            回调 类
	 * @return void
	 * @说明 代码版权归 杭州反盗版中心有限公司 所有
	 */
	@Deprecated
	public void downImage(String url, ImageView imageView,
			final IAqueryFileCallBack callBack) {
		imageView.setImageBitmap(null);
		imageLoader.displayImage(url, imageView, options,
				new SimpleImageLoadingListener() {
					@Override
					public void onLoadingComplete(String imageUri, View view,
							Bitmap loadedImage) {
						super.onLoadingComplete(imageUri, view, loadedImage);
						callBack.callBackBitmap(loadedImage);
					}
				}, new ImageLoadingProgressListener() {

					@Override
					public void onProgressUpdate(String arg0, View arg1,
							int current, int total) {
						int degree = current / total;
						callBack.callBackString(degree + "");
					}
				});
	}
}
