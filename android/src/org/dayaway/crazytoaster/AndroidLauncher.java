package org.dayaway.crazytoaster;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.badlogic.gdx.backends.android.AndroidAudio;
import com.badlogic.gdx.backends.android.AsynchronousAndroidAudio;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.OnUserEarnedRewardListener;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.google.android.gms.ads.rewarded.RewardItem;
import com.google.android.gms.ads.rewarded.RewardedAd;
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback;

import org.dayaway.crazytoaster.utill.ActionAd;

public class AndroidLauncher extends AndroidApplication implements ActionAd {
	private static final String AD_UNIT_ID = "ca-app-pub-3940256099942544/6300978111";//"ca-app-pub-5500397671621074/5320458210";
	private static final String AD_UNIT_ID_INTERSTITIAL = "ca-app-pub-3940256099942544/1033173712";//"ca-app-pub-5500397671621074/7103324465";
	private static final String AD_UNIT_ID_REWARD = "ca-app-pub-3940256099942544/5224354917";//"ca-app-pub-5500397671621074/7075404302";

	private RelativeLayout adContainerView;
	private AdView adView;

	private InterstitialAd mInterstitialAd;
	private RewardedAd mRewardedAd;

	private CrazyToaster crazyToaster;


	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//Скрывает нижние кнопки
		getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);

		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		config.useCompass = false;
		config.useAccelerometer = false;

		//Получаем view игры
		crazyToaster = new CrazyToaster(AndroidLauncher.this);
		View gameView = initializeForView(crazyToaster);

		//Создаем пустой view
		RelativeLayout layout = new RelativeLayout(this);
		//Создаем пустой view для рекламы
		adContainerView = new RelativeLayout(this);


		//задаем параметры для нашего баннера
		RelativeLayout.LayoutParams adParams =
				new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,
						RelativeLayout.LayoutParams.WRAP_CONTENT);
		adParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
		adParams.addRule(RelativeLayout.CENTER_HORIZONTAL);


		MobileAds.initialize(this, new OnInitializationCompleteListener() {
			@Override
			public void onInitializationComplete(InitializationStatus initializationStatus) {
			}
		});

		//Добавляем в наш пустой View представление игры
		layout.addView(gameView);
		//Добавляем в наш пустой View рекламный контейнер с параметрами
		layout.addView(adContainerView, adParams);


		//Передаем наш View на отображение
		setContentView(layout);

		//Получаем рекламный баннер
		adContainerView.post(new Runnable() {
			@Override
			public void run() {
				loadBanner();
			}
		});

		//Подгружаем межстраничное видео
		loadInterstitialAd();
		//Подгружаем видео за вознаграждение
		loadRewardAd();
	}

	private void loadBanner() {
		adView = new AdView(this);
		adView.setAdUnitId(AD_UNIT_ID);
		adContainerView.removeAllViews();
		adContainerView.addView(adView);

		AdSize adSize = getAdSize();
		adView.setAdSize(adSize);

		AdRequest adRequest = new AdRequest.Builder().build();

		adView.loadAd(adRequest);
	}

	private AdSize getAdSize() {
		Display display = getWindowManager().getDefaultDisplay();
		DisplayMetrics outMetrics = new DisplayMetrics();
		display.getMetrics(outMetrics);

		float density = outMetrics.density;

		float adWidthPixels = adContainerView.getWidth();

		if (adWidthPixels == 0) {
			adWidthPixels = outMetrics.widthPixels;
		}

		int adWidth = (int) (adWidthPixels / density);
		return AdSize.getCurrentOrientationAnchoredAdaptiveBannerAdSize(this, adWidth);
	}

	public void loadInterstitialAd() {
		AdRequest adRequest = new AdRequest.Builder().build();
		InterstitialAd.load(AndroidLauncher.this,AD_UNIT_ID_INTERSTITIAL, adRequest,
				new InterstitialAdLoadCallback() {
					@Override
					public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
						// The mInterstitialAd reference will be null until
						// an ad is loaded.
						mInterstitialAd = interstitialAd;
						//mInterstitialAd.show(AndroidLauncher.this);
					}

					@Override
					public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
						// Handle the error
						mInterstitialAd = null;
					}
				}
		);
	}

	public void loadRewardAd() {
		AdRequest adRequest = new AdRequest.Builder().build();

		RewardedAd.load(this, AD_UNIT_ID_REWARD,
				adRequest, new RewardedAdLoadCallback() {
					@Override
					public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
						// Handle the error.
						mRewardedAd = null;
					}

					@Override
					public void onAdLoaded(@NonNull RewardedAd rewardedAd) {
						mRewardedAd = rewardedAd;

					}
				});
	}


	@Override
	public void showAd() {
		try {
			//Запускаем в пользовательском потоке
			runOnUiThread(new Runnable() {
				public void run() {
					if(mInterstitialAd != null) {
						mInterstitialAd.show(AndroidLauncher.this);
						loadInterstitialAd();
					} else {
						loadInterstitialAd();
					}
				}
			});
		} catch (Exception e) {
		}
	}

	@Override
	public void showRewardAd() {
		try {
			//Запускаем в пользовательском потоке
			runOnUiThread(new Runnable() {
				public void run() {
					if (mRewardedAd != null) {
						Activity activityContext = AndroidLauncher.this;
						mRewardedAd.show(activityContext, new OnUserEarnedRewardListener() {
							@Override
							public void onUserEarnedReward(@NonNull RewardItem rewardItem) {
								// Handle the reward.
								//int rewardAmount = rewardItem.getAmount();
								//String rewardType = rewardItem.getType();
								crazyToaster.turnREWARD_FOR_AD();
								loadRewardAd();
							}
						});
					} else {
						loadRewardAd();
					}
				}
			});
		} catch (Exception e) {
		}
	}

	@Override
	public AndroidAudio createAudio(Context context, AndroidApplicationConfiguration config) {
		return new AsynchronousAndroidAudio(context, config);
	}
}
