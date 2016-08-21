package com.ciapc.anxin;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.ZbarZxing.XZbar.DecodeThread;
import com.ZbarZxing.XZbar.HxBarcode;
import com.ciapc.anxin.common.BaseActivity;
import com.ciapc.anxin.common.view.AxinDialog;
import com.ciapc.anxin.common.view.AxinDialog.AxDialogClickListen;
import com.ciapc.anxin.common.view.RoundView;
import com.ciapc.anxin.modules.contacts.ContactsActivity;
import com.ciapc.anxin.modules.home.HomeActivity;
import com.ciapc.anxin.modules.leaders.LeaderActivity;
import com.ciapc.anxin.modules.login.LoginActivity;
import com.master.util.common.QRCodeUtils;

public class MainActivity extends BaseActivity implements OnClickListener {

	private Context mContext;
	RoundView img;
	HxBarcode hxBarcod;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_layout);
		mContext = this;
		Button btn = (Button) findViewById(R.id.btn);
		img = (RoundView) findViewById(R.id.img);
		findViewById(R.id.toListView).setOnClickListener(this);
		findViewById(R.id.toQRCode).setOnClickListener(this);
		findViewById(R.id.toSM).setOnClickListener(this);
		findViewById(R.id.toLeader).setOnClickListener(this);
		findViewById(R.id.toLogin).setOnClickListener(this);
		btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				startActivity(new Intent(mContext, HomeActivity.class));
			}
		});
	}

	@Override
	public void onClick(View arg0) {
		switch (arg0.getId()) {
		case R.id.toLogin:
			startActivity(new Intent(mContext, LoginActivity.class));
			break;
		case R.id.toListView:
			startActivity(new Intent(mContext, ContactsActivity.class));
			break;
		case R.id.toQRCode:
			Bitmap bitmap = QRCodeUtils.createQRCode("XXX", BitmapFactory
					.decodeResource(getResources(), R.drawable.test), 500, 800,
					20);
			new AxinDialog(mContext,AxinDialog.DIALOG_IMAGE).setImg(bitmap).setImgClick(new AxDialogClickListen() {
				
				@Override
				public void onClick(AxinDialog axinDialog) {
				axinDialog.dismiss();
				}
			}).show();
			break;

		case R.id.toSM:
			hxBarcod = new HxBarcode();
			hxBarcod.scan(this, 501, false);
			break;
		case R.id.toLeader:
			startActivity(new Intent(mContext,LeaderActivity.class));
			break;

		default:
			break;
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, final Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		System.out
				.printf("onActivityResult ： %s,%s\n", requestCode, resultCode);
		switch (requestCode) {
		case 501:
			runOnUiThread(new Runnable() {
				
				@Override
				public void run() {
					if (data != null) {
						Bundle extras = data.getBundleExtra("data");

						if (null != extras) {
							Bitmap barcode = null;
							byte[] compressedBitmap = extras
									.getByteArray(DecodeThread.BARCODE_BITMAP);
							if (compressedBitmap != null) {
								barcode = BitmapFactory.decodeByteArray(
										compressedBitmap, 0, compressedBitmap.length,
										null);
								// Mutable copy:
								barcode = barcode.copy(Bitmap.Config.RGB_565, true);
							}
							new AxinDialog(mContext,AxinDialog.DIALOG_IMAGE).show();
						}
					}
				}
			});
			
			break;
		}
	}
}
