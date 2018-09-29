package com.seventeen.goradar.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.seventeen.goradar.R;
import com.seventeen.goradar.model.EventModel;
import com.seventeen.goradar.util.Advertisement;
import com.seventeen.goradar.util.Constant;
import com.seventeen.goradar.util.StatusBarUtil;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;

import org.greenrobot.eventbus.EventBus;

import java.util.Locale;


public class MoreActivity extends Activity implements OnClickListener {
	private static final String TAG = MoreActivity.class.getSimpleName();
	private Button but_back;
	private TextView tvTrial;
	//	@Bind(main_adView)
	AdView mAdView;
	private InterstitialAd interstitial;
	private TextView tvPlolicy;
	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_more_view);
		StatusBarUtil.setColor(this, getResources().getColor(R.color.app));

		findViews();
	}



	@SuppressLint("NewApi")
	private void findViews() {
		mAdView = (AdView)findViewById(R.id.main_adView);
		//横幅
		AdRequest adRequest = new AdRequest.Builder().build();
		// 最后，请求广告。
		mAdView.loadAd(adRequest);
		// 插屏广告
		interstitial = new InterstitialAd(this);
		interstitial.setAdUnitId(getString(R.string.ad_unit_id));
		AdRequest request = new AdRequest.Builder().build();
		interstitial.loadAd(request);


		tvTrial = (TextView)findViewById(R.id.tv_trial);
		tvTrial.setOnClickListener(this);


		tvPlolicy = (TextView) findViewById(R.id.tv_policy);
		tvPlolicy.setOnClickListener(this);
	}

	private void setLang(Locale l) {
		// 获得res资源对象
		Resources resources = getResources();
		// 获得设置对象
		Configuration config = resources.getConfiguration();
		// 获得屏幕参数：主要是分辨率，像素等。
		DisplayMetrics dm = resources.getDisplayMetrics();
		// 语言
		config.locale = l;
		resources.updateConfiguration(config, dm);
	}

	public void ButtonClick(View v){

		try {
			//先拿到单例模式里面的 实例  因为封装的是MAP  传入KEY去拿值 在显现广告
			Advertisement.getInstance().show(getString(R.string.ad_unit_id));
		} catch (Exception e) {
			Log.d(TAG, "setContentView: 显示广告失败");
		}
		finish();
	}

	public void onEmailClick(View v){
		String[] email = {"3802**92@qq.com"}; // 需要注意，email必须以数组形式传入
		Intent intent = new Intent(Intent.ACTION_SEND);
		intent.setType("message/rfc822"); // 设置邮件格式
		intent.putExtra(Intent.EXTRA_EMAIL, email); // 接收人
		intent.putExtra(Intent.EXTRA_CC, email); // 抄送人
		intent.putExtra(Intent.EXTRA_SUBJECT, "这是邮件的主题部分"); // 主题
		intent.putExtra(Intent.EXTRA_TEXT, "这是邮件的正文部分"); // 正文
		startActivity(Intent.createChooser(intent, "请选择邮件类应用"));
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.tv_policy:
				Intent intent = new Intent(this, WebViewActivity.class);
				intent.putExtra("url", MapsActivity.mrivacyPolicyUrl);
				startActivity(intent);

				break;
			case R.id.tv_trial:
				//开始订阅
				EventBus.getDefault().post(new EventModel(Constant.Event.QUERY_SUB_AND_BUY));
				break;
		}
	}
}
