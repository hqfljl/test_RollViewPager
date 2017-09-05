package com.dance.rollviewpager;

import java.util.ArrayList;
import com.nostra13.universalimageloader.core.ImageLoader;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * ѭ�������л�ͼƬ(֧�ִ�����,�������⴫null����),�Դ������� ֧����ʾ����resͼƬ������ͼƬ��ָ��uri��ͼƬ
 * OnPagerClickCallback onPagerClickCallback ����page������Ļص��ӿ�,
 * ���û��ֶ�����ʱ����ͣ��������ǿ�û��Ѻ���
 * 
 * @author dance
 * 
 */
public class RollViewPager2 extends ViewPager {

	public RollViewPager2(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;

		viewPagerTask = new ViewPagerTask();
		myOnTouchListener = new MyOnTouchListener();
	}

	public RollViewPager2(Context context) {
		super(context);
		this.context = context;

		viewPagerTask = new ViewPagerTask();
		myOnTouchListener = new MyOnTouchListener();
	}

	private String TAG = "RollViewPager";
	private Context context;
	private int currentItem;
	private ArrayList<String> uriList;// ͼƬ��ַ
	private ArrayList<View> dots;// ���λ�ò��̶���������Ҫ�õ����ߴ���
	private TextView title;// ������ʾÿ��ͼƬ�ı���
	private String[] titles;
	private int[] resImageIds;
	private int dot_focus_resId;// ��ȡ�����ͼƬid
	private int dot_normal_resId;// δ��ȡ�����ͼƬid
	private OnPagerClickCallback onPagerClickCallback;
	private boolean isShowResImage = false;
	MyOnTouchListener myOnTouchListener;
	ViewPagerTask viewPagerTask;

	private long start = 0;

	public class MyOnTouchListener implements OnTouchListener {
		@Override
		public boolean onTouch(View v, MotionEvent event) {
			switch (event.getAction()) {
			case MotionEvent.ACTION_DOWN:
				start = System.currentTimeMillis();
				handler.removeCallbacksAndMessages(null);
				break;
			case MotionEvent.ACTION_MOVE:
				handler.removeCallbacks(viewPagerTask);
				break;
			case MotionEvent.ACTION_CANCEL:
				startRoll();
				break;
			case MotionEvent.ACTION_UP:
				long duration = System.currentTimeMillis() - start;
				if (duration <= 400) {
					if (onPagerClickCallback != null)
						onPagerClickCallback.onPagerClick(currentItem);
				}
				startRoll();
				break;
			}
			return true;
		}
	}

	public class ViewPagerTask implements Runnable {
		@Override
		public void run() {
			currentItem = (currentItem + 1)
					% (isShowResImage ? resImageIds.length : uriList.size());
			handler.obtainMessage().sendToTarget();
		}
	}

	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			setCurrentItem(currentItem);
			startRoll();
		}
	};

	public void setDot(ArrayList<View> dots, int dot_focus_resId,
			int dot_normal_resId) {
		this.dots = dots;
		this.dot_focus_resId = dot_focus_resId;
		this.dot_normal_resId = dot_normal_resId;
	}

	public void setPagerCallback(OnPagerClickCallback onPagerClickCallback) {
		this.onPagerClickCallback = onPagerClickCallback;
	}

	/**
	 * ��������ͼƬ��url���ϣ�Ҳ�����Ǳ���ͼƬ��uri
	 * ͼƬuriList���ϣ������������ַ���磺http://www.ssss.cn/3.jpg��Ҳ�����Ǳ��ص�uri,�磺
	 * assest://image/3.jpg��uriList��resImageIdsֻ�贫��һ��
	 * 
	 * @param uriList
	 */
	public void setUriList(ArrayList<String> uriList) {
		isShowResImage = false;
		this.uriList = uriList;
	}

	/**
	 * ����resͼƬ��id ͼƬuriList���ϣ������������ַ���磺http://www.ssss.cn/3.jpg��Ҳ�����Ǳ��ص�uri,�磺
	 * assest://image/3.jpg��uriList��resImageIdsֻ�贫��һ��
	 * 
	 * @param resImageIds
	 */
	public void setResImageIds(int[] resImageIds) {
		isShowResImage = true;
		this.resImageIds = resImageIds;
	}

	/**
	 * �������
	 * 
	 * @param title
	 *            ������ʾ�����TextView
	 * @param titles
	 *            ��������
	 */
	public void setTitle(TextView title, String[] titles) {
		this.title = title;
		this.titles = titles;
		if (title != null && titles != null && titles.length > 0)
			title.setText(titles[0]);// Ĭ����ʾ��һ�ŵı���
	}

	private boolean hasSetAdapter = false;
	private final int SWITCH_DURATION = 3500;

	/**
	 * ��ʼ����
	 */
	public void startRoll() {
		if (!hasSetAdapter) {
			hasSetAdapter = true;
			this.setOnPageChangeListener(new MyOnPageChangeListener());
			this.setAdapter(new ViewPagerAdapter());
		}
		handler.postDelayed(viewPagerTask, SWITCH_DURATION);
	}

	class MyOnPageChangeListener implements OnPageChangeListener {
		int oldPosition = 0;

		@Override
		public void onPageSelected(int position) {
			currentItem = position;
			if (title != null)
				title.setText(titles[position]);
			if (dots != null && dots.size() > 0) {
				dots.get(position).setBackgroundResource(dot_focus_resId);
				dots.get(oldPosition).setBackgroundResource(dot_normal_resId);
			}
			oldPosition = position;
		}

		@Override
		public void onPageScrollStateChanged(int state) {
		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
		}
	}

	/**
	 * �Դ�������,��Ҫ�ص���������page�ĵ���¼�
	 * 
	 * @author dance
	 * 
	 */
	class ViewPagerAdapter extends PagerAdapter {
		@Override
		public int getCount() {
			return isShowResImage ? resImageIds.length : uriList.size();
		}

		@Override
		public Object instantiateItem(View container, final int position) {
			View view = View.inflate(context, R.layout.viewpager_item, null);
			((ViewPager) container).addView(view);
			view.setOnTouchListener(myOnTouchListener);
			ImageView imageView = (ImageView) view.findViewById(R.id.image);
			if (isShowResImage) {
				imageView.setImageResource(resImageIds[position]);
			} else {
				ImageLoader.getInstance().displayImage(uriList.get(position),
						imageView, ImageCacheUtil.OPTIONS.default_options);
			}
			return view;
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0 == arg1;
		}

		@Override
		public void destroyItem(View arg0, int arg1, Object arg2) {
			// ��ImageView��ViewPager�Ƴ�
			((ViewPager) arg0).removeView((View) arg2);
		}
	}

	@Override
	protected void onDetachedFromWindow() {
		handler.removeCallbacksAndMessages(null);
		super.onDetachedFromWindow();
	}

	/**
	 * ����page����Ļص��ӿ�
	 * 
	 * @author dance
	 * 
	 */
	interface OnPagerClickCallback {
		public abstract void onPagerClick(int position);
	}
}
