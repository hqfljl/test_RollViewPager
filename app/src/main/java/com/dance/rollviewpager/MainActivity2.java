package com.dance.rollviewpager;

import java.util.ArrayList;
import android.R.integer;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.dance.rollviewpager.RollViewPager.OnPagerClickCallback;
import com.nostra13.universalimageloader.core.ImageLoader;

public class MainActivity2 extends Activity  {
	private int[] imageIDs;
	private String[] titles;
	private ArrayList<View> dots;
	private TextView title;
	private RollViewPager2 rollViewPager2;// 只需要一个layout即可,ViewPager是动态加载的
	private ArrayList<String> uriList;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initUI();
		initListener();
		initData();
	}

	private void initUI() {
		setContentView(R.layout.activity_main2);
		title = (TextView) findViewById(R.id.title);
		rollViewPager2 = (RollViewPager2) findViewById(R.id.viewpager);
	}

	private void initListener() {
	}

	private void initData() {
		// 图片ID资源
		imageIDs = new int[] { R.drawable.a, R.drawable.b, R.drawable.c,
				R.drawable.d, R.drawable.e };

		// 图片名称
		titles = new String[] { "黑马训练营8期学员", "俺叫李晓俊", "qq：609195946", "至今仍单身",
				"跪求妹纸啊" };

		// 用来显示的点
		dots = new ArrayList<View>();
		dots.add(findViewById(R.id.dot_0));
		dots.add(findViewById(R.id.dot_1));
		dots.add(findViewById(R.id.dot_2));
		dots.add(findViewById(R.id.dot_3));
		dots.add(findViewById(R.id.dot_4));

		// 构造网络图片
		uriList = new ArrayList<String>();
		uriList.add("http://h.hiphotos.baidu.com/album/w%3D2048/sign=730e7fdf95eef01f4d141fc5d4c69825/94cad1c8a786c917b8bf9482c83d70cf3ac757c9.jpg");
		uriList.add("http://g.hiphotos.baidu.com/album/w%3D2048/sign=00d4819db8014a90813e41bd9d4f3812/562c11dfa9ec8a137de469cff603918fa0ecc026.jpg");
		uriList.add("http://c.hiphotos.baidu.com/album/w%3D2048/sign=a8631adb342ac65c67056173cfcab011/b8389b504fc2d56217d11656e61190ef77c66cb4.jpg");
		uriList.add("http://e.hiphotos.baidu.com/album/w%3D2048/sign=ffac8994a71ea8d38a227304a332314e/1ad5ad6eddc451da4d9d32c4b7fd5266d01632b1.jpg");
		uriList.add("http://a.hiphotos.baidu.com/album/w%3D2048/sign=afbe93839a504fc2a25fb705d1e5e611/d058ccbf6c81800a99489685b03533fa838b478f.jpg");

//		RollViewPager mViewPager = new RollViewPager(this, dots,
//				R.drawable.dot_focus, R.drawable.dot_normal,
//				new OnPagerClickCallback() {
//					@Override
//					public void onPagerClick(int position) {
//						Toast.makeText(MainActivity2.this,
//								"第" + position + "张图片被点击了", 0).show();
//					}
//				});
		
//		mViewPager.setResImageIds(imageIDs);//设置res的图片id
		rollViewPager2.setUriList(uriList);//设置网络图片的url
		
		rollViewPager2.setDot(dots, R.drawable.dot_focus, R.drawable.dot_normal);
		rollViewPager2.setPagerCallback(new RollViewPager2.OnPagerClickCallback() {
			
			@Override
			public void onPagerClick(int position) {
				// TODO Auto-generated method stub
				Toast.makeText(MainActivity2.this,
						"第" + position + "张图片被点击了", 0).show();
			}
		});
		
		rollViewPager2.setTitle(title, titles);//不需要显示标题，可以不设置
		rollViewPager2.startRoll();//不调用的话不滚动
		
		
	}


}