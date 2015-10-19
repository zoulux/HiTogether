package com.lyy.hitogether.util;

import java.lang.reflect.Field;
import java.util.LinkedList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import com.lyy.hitogether.R;
import com.lyy.hitogether.activity.ShareMyTravalActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.DisplayMetrics;
import android.util.LruCache;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;

/**
 * ͼƬ������
 * 
 * @author Administrator
 * 
 */
public class ImageLoader {

	private static ImageLoader mInstance;
	// ͼƬ����ź��Ķ���
	private LruCache<String, Bitmap> mLruCache;
	// �̳߳�
	private ExecutorService mThreadPool;

	private static final int DEAFULT_THREAD_COUNT = 1;
	// ���еĵ��ȷ�ʽ
	private static Type mType = Type.LIFO;

	public enum Type {
		FIFO, LIFO;
	}

	// �������
	private LinkedList<Runnable> mTaskQueue;
	// ��̨��ѯ�߳�
	private Thread mPoolThread;

	private Handler mPoolThreadHandle;
	// UI�߳��е�Handler
	private Handler mUIHandler;
	private Semaphore mSemaphorePoolThreadHandle = new Semaphore(0);

	private Semaphore mSemaphoreThreadPool;
	private String isTrue = "1";

	private ImageLoader(int threadCount, Type type) {
		init(threadCount, type);
	}
	/**
	 * ��ʼ��
	 * 
	 * @param threadCount
	 * @param type
	 */
	private void init(int threadCount, Type type) {
		
		if (isTrue.equals("1")) {
			// ��̨��ѯ�߳�
			mPoolThread = new Thread() {
				@Override
				public void run() {
					Looper.prepare();
					mPoolThreadHandle = new Handler() {
						@Override
						public void handleMessage(Message msg) {
							// �̳߳�ȡ��һ���������ִ��

							mThreadPool.execute(getTask());
							try {
								mSemaphoreThreadPool.acquire();
							} catch (InterruptedException e) {
								e.printStackTrace();
							}

						}

					};
					// �ͷ�һ���ź���
					mSemaphorePoolThreadHandle.release();
					Looper.loop();
				};
			};

			mPoolThread.start();
		}

		// ��ȡ����Ӧ�õ��������ڴ�
		int maxMemory = (int) Runtime.getRuntime().maxMemory();
		int cachaMemory = maxMemory / 8;

		mLruCache = new LruCache<String, Bitmap>(cachaMemory) {
			@Override
			protected int sizeOf(String key, Bitmap value) {
				return value.getRowBytes() * value.getHeight();
			};
		};
		// �����̳߳�
		mThreadPool = Executors.newFixedThreadPool(threadCount);
		mSemaphoreThreadPool = new Semaphore(threadCount);
		mTaskQueue = new LinkedList<Runnable>();
		mType = type;

	}

	public void end(Context context) {
		 SPUtils.put(context,"isTrue", "0");
		 
	}

	public static ImageLoader getInstance() {
		if (mInstance == null) {
			synchronized (ImageLoader.class) {
				if (mInstance == null) {
					mInstance = new ImageLoader(DEAFULT_THREAD_COUNT, mType);
					
				}
			}
		}
		return mInstance;
	}

	public static ImageLoader getInstance(int ThreadCount, Type type) {
		if (mInstance == null) {
			synchronized (ImageLoader.class) {
				if (mInstance == null) {
					mInstance = new ImageLoader(ThreadCount, type);
				}
			}
		}
		return mInstance;
	}

	/**
	 * �����������ȡ��һ������
	 * 
	 * @return
	 */
	private synchronized Runnable getTask() {
		if (mType == Type.FIFO) {
			return mTaskQueue.removeFirst();
		} else if (mType == Type.LIFO) {
			return mTaskQueue.removeLast();
		}
		return null;
	}

	private String path;

	public void LoadImage(final String path, final ImageView imageView) {

		imageView.setTag(path);

		if (mUIHandler == null) {
			mUIHandler = new Handler() {

				@Override
				public void handleMessage(Message msg) {
					// ��ȡ�õ���ͼƬ��ΪImageView�ص�����ͼƬ
					ImageHolder holder = (ImageHolder) msg.obj;
					ImageView imageView = holder.imageView;
					Bitmap bm = holder.bitmap;
					String path = holder.path;
					setPath(path);

					// ��path��gettag�Ĵ洢��·�����бȽ�
					if (imageView.getTag().toString().equals(path)) {
						imageView.setImageBitmap(bm);
						// imageView.setColorFilter(null);
						// mSelected
						// .setImageResource(R.drawable.picture_unselected);
					}
				}
			};
		}
		// ����path�ڻ����л�ȡBitmap
		Bitmap bm = getBitmapFromLruCache(path);

		if (bm != null) {
			reFreshBitmap(path, bm, imageView);
		} else {
			addTask(new Runnable() {
				public void run() {
					// ����ͼƬ
					// ͼƬ��ѹ��
					// 1.���ͼƬ��Ҫ��ʾ�Ĵ�С
					ImageSize imageSize = getImageViewSize(imageView);

					// 2ѹ��ͼƬ
					Bitmap bm = decodeSampleBitmapFromPath(path,
							imageSize.width, imageSize.height);
					// 3,��ͼƬ���뵽������ȥ
					addBitmapToLruCache(path, bm);

					reFreshBitmap(path, bm, imageView);
					mSemaphoreThreadPool.release();
				}

			});

		}
	}

	protected void setPath(String path) {
		this.path = path;
	}

	public void reFreshBitmap(String path, Bitmap bm, ImageView imageView) {
		ImageHolder holder = new ImageHolder();
		holder.bitmap = bm;
		holder.path = path;
		holder.imageView = imageView;
		Message message = Message.obtain();
		message.obj = holder;
		mUIHandler.sendMessage(message);

	}

	/**
	 * ��ͼƬ���뵽������
	 * 
	 * @param path
	 * @param bm
	 */
	protected void addBitmapToLruCache(String path, Bitmap bm) {
		if (getBitmapFromLruCache(path) == null) {
			if (bm != null) {
				mLruCache.put(path, bm);
			}
		}
	}

	/**
	 * ����ͼƬ��Ҫ��ʾ�Ŀ�͸����ѹ��
	 * 
	 * @param path
	 * @param width
	 * @param height
	 * @return
	 */
	private Bitmap decodeSampleBitmapFromPath(String path, int width, int height) {
		// ���ͼƬ�Ŀ�͸ߣ�������ͼƬ���ص��ڴ���
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;

		BitmapFactory.decodeFile(path, options);
		options.inSampleSize = cuculateInSampleSize(options, width, height);

		// ʹ�û�ȡ����insampleSize��ȡͼƬ,���԰�ͼƬ���ص��ڴ�����
		options.inJustDecodeBounds = false;
		Bitmap bmp = BitmapFactory.decodeFile(path, options);
		return bmp;
	}

	/**
	 * ��������Ŀ�͸��Լ�ͼƬ��ʵ�ʵĿ�͸߼���SampleSize
	 * 
	 * @param options
	 * @param width
	 * @param height
	 * @return
	 */
	private int cuculateInSampleSize(BitmapFactory.Options options,
			int reqWidth, int reqHeight) {
		int width = options.outWidth;
		int height = options.outHeight;

		int inSampleSize = 1;

		if (width > reqWidth && height > reqHeight) {
			int widthRadio = Math.round(width * 1.0f / reqWidth);
			int heightRadio = Math.round(height * 1.0f / reqHeight);
			inSampleSize = Math.max(widthRadio, heightRadio);
		}

		return inSampleSize;
	}

	/**
	 * ����ImgaView��ȡ�ʵ���ѹ���Ŀ�͸�
	 * 
	 * @param imageView
	 * @return
	 */

	private ImageSize getImageViewSize(ImageView imageView) {

		DisplayMetrics displayMetrics = imageView.getResources()
				.getDisplayMetrics();

		ImageSize imageSize = new ImageSize();
		LayoutParams lp = imageView.getLayoutParams();
		int width = imageView.getWidth();// ��ȡImageView��ʵ�ʿ��
		// int width = getImageViewFieldValue(imageView, "mMaxWidth");
		if (width <= 0) {
			width = lp.width;// ��ȡimgeview��layout�������Ŀ��
		}
		if (width <= 0) {
			width = getImageViewFieldValue(imageView, "mMaxWidth");// ������ֵ
		}
		if (width <= 0) {
			width = displayMetrics.widthPixels;
		}

		int height = imageView.getHeight();// ��ȡImageView��ʵ�ʸ߶�
		// int height = ;

		if (height <= 0) {
			height = lp.height;// ��ȡimgeview��layout�������ĸ߶�
		}
		if (height <= 0) {
			height = getImageViewFieldValue(imageView, "mMaxHeight");// ������ֵ
		}
		if (height <= 0) {
			height = displayMetrics.heightPixels;
		}

		imageSize.width = width;
		imageSize.height = height;
		return imageSize;
	}

	private synchronized void addTask(Runnable runnable) {

		try {
			if (mPoolThreadHandle == null) {
				mSemaphorePoolThreadHandle.acquire();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		mTaskQueue.add(runnable);
		mPoolThreadHandle.sendEmptyMessage(0x110);
	}

	/**
	 * ͨ�������ȡImageView����������ֵ
	 * 
	 * @param object
	 * @param fieldName
	 * @return
	 */
	private static int getImageViewFieldValue(Object object, String fieldName) {
		int value = 0;
		try {
			Field field = ImageView.class.getDeclaredField(fieldName);
			field.setAccessible(true);

			int fieldValue = field.getInt(object);
			if (fieldValue > 0 && fieldValue < Integer.MAX_VALUE) {
				value = fieldValue;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return value;
	}

	/**
	 * ����path�ڻ����л�ȡBitmap
	 * 
	 * @param path
	 * @return
	 */
	private Bitmap getBitmapFromLruCache(String path) {
		return mLruCache.get(path);
	}

	private class ImageSize {
		int width;
		int height;
	}

	private class ImageHolder {
		Bitmap bitmap;
		ImageView imageView;
		String path;
	}

		/**
	 * ��ȡѹ�����Bitmap
	 * @param imageView
	 * @param path
	 * @return
	 */
	public Bitmap getCompressedBitmap(ImageView imageView, String path) {
		ImageSize imageSize = getImageViewSize(imageView);
		return decodeSampleBitmapFromPath(path, imageSize.width,
				imageSize.height);
	}
}
