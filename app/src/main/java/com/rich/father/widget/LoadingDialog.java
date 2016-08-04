package com.rich.father.widget;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.rich.father.R;

public class LoadingDialog {

	private static Dialog mDialog;
	private static ImageView spaceshipImage;

	//得到的Dialog
	public static Dialog getDialog(Context context) {
		Dialog loadingDialog = null;
		if(context != null){
			LayoutInflater inflater = LayoutInflater.from(context);
			LinearLayout v = (LinearLayout)inflater.inflate(R.layout.dialog_loading, null);
			spaceshipImage = (ImageView) v.findViewById(R.id.img);
			Animation hyperspaceJumpAnimation = AnimationUtils.loadAnimation(context, R.anim.loading_dialog_anim);
			spaceshipImage.startAnimation(hyperspaceJumpAnimation);
			loadingDialog = new Dialog(context, R.style.loading_dialog_style);
			loadingDialog.setCancelable(true);
			loadingDialog.setContentView(v, new LinearLayout.LayoutParams(
					LinearLayout.LayoutParams.WRAP_CONTENT,
					LinearLayout.LayoutParams.WRAP_CONTENT));
		}
		return loadingDialog;
	}

	//显示Dialog
	public static void showDialog(Context context){
		mDialog = getDialog(context);
		if(mDialog != null){
			try{
				mDialog.show();
			}catch (Exception e){
				e.printStackTrace();
			}
		}
	}

	//关闭Dialog
	public static void dismissDialog(){
		if(mDialog != null){
			try{
				mDialog.dismiss();
			}catch (Exception e){
				e.printStackTrace();
			}
		}
	}

}