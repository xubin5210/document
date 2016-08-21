package com.ciapc.anxin.modules.setting;

import java.util.HashMap;

import org.apache.http.Header;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.ciapc.anxin.R;
import com.ciapc.anxin.common.AXSharedPreferences;
import com.ciapc.anxin.common.BaseActivity;
import com.ciapc.anxin.common.GlobalConstants;
import com.ciapc.anxin.common.InterfaceConstants;
import com.ciapc.anxin.common.pojo.GsonPojo;
import com.ciapc.anxin.common.view.CustomEditText;
import com.ciapc.anxin.common.view.PubTitle;
import com.ciapc.anxin.common.view.PubTitle.OnTitleClickListener;
import com.ciapc.anxin.modules.home.HomeActivity;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.master.util.common.DialogUtil;
import com.master.util.common.GsonUtils;
import com.master.util.common.StringUtils;
import com.master.util.http.MasterHttpClient;
/**
 * 
 * @author zhoumc   
 * @Description: 账号与安全——手机号  
 * @ClassName: AccountSafePhoneActivity.java   
 * @date 2015-6-10 下午6:11:05      
 * @说明  代码版权归 杭州安存网络科技有限公司 所有
 */
public class AccountSafePhoneActivity extends BaseActivity {
	
	public static final String TAG = "AccountSafePhoneActivity";
	
	//自己在values下面array中定义一个名称
    private PubTitle  pubTitle;
    
    private Context  mContext;;
    
    // 获取验证码
  	private final int GET_CODE = 1;
  	
    //确认更换按钮
    private Button phoneConfirmChange;
    
    //获取验证码按钮
    private Button phoneGetcode;
    private String hint = "s后重新获取";
	private int time = 60;
	
	//输入新手机号
    private CustomEditText newPhoneInput;
    //输入验证码
  	private EditText inputCode;
  	private String inputphone, code;
  	
    // 验证码发送是否成功
  	private boolean flag = true;
	
     @Override
    protected void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
    	setContentView(R.layout.account_safe_phone_layout);
    	mContext=this;
    	initView();
    	initClick();
        
    	
    }
    
	
    /**
     * @Auther: zhoumc  
     * @Description: 初始化控件
     * @Date:2015-6-25下午5:51:51  
     * @return void 
     * @说明  代码版权归 杭州安存网络科技有限公司 所有
     */
	private void initView() {
		pubTitle = (PubTitle) findViewById(R.id.account_safe_phone_title);
		//获取验证码按钮
	    phoneGetcode=(Button) findViewById(R.id.phone_getcode);
	    //输入手机号
	    newPhoneInput=(CustomEditText) findViewById(R.id.newphone_input);
	    //输入验证码
	    inputCode=(EditText) findViewById(R.id.phone_input_verification);
	    //确认更换
	    phoneConfirmChange=(Button) findViewById(R.id.phone_confirm_change);
	}
	/**
	 * @Auther: zhoumc  
	 * @Description: 初始化监听事件
	 * @Date:2015-6-25下午5:52:08  
	 * @return void 
	 * @说明  代码版权归 杭州安存网络科技有限公司 所有
	 */
	private void initClick() {
		//标题
        pubTitle.setShowLeftOrRight(true, false, false).setOnTitleClickListener(new OnTitleClickListener() {
			
			@Override
			public void onRightClick() {
				
			}
			
			@Override
			public void onLeftClick() {
				finish();
			}
		});
        
		 //获取验证码
		phoneGetcode.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				 inputphone = newPhoneInput.getText().toString();
				 if (!StringUtils.isMobileNO(inputphone)) {
						DialogUtil.showSystemToast(mContext, "手机号不能为空或错误");
						return;
					}
				// 没有点击
				if (phoneGetcode.isClickable()) {
					flag = true;
					getCode();
					phoneGetcode.setSelected(true);
					phoneGetcode.setClickable(false);
					mHandler.postDelayed(new Runnable() {

						@Override
						public void run() {
							time--;
							if (flag) {
								mHandler.sendEmptyMessage(GET_CODE);
								mHandler.postDelayed(this, 1000);
							}
						}
					}, 0);
				}
			}
		});
		
		//确认更换
		 phoneConfirmChange.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				inputphone = newPhoneInput.getText().toString();
				if (!StringUtils.isMobileNO(inputphone)) {
					DialogUtil.showSystemToast(mContext, "手机号码不能为空!");
					return;
				}
				code = inputCode.getText().toString();
				if (!StringUtils.isNotEmpty(code)) {
					DialogUtil.showSystemToast(mContext, "验证码不能为空");
					return;
				}
			   //去修改手机号
				ChangePhone();
			}

			
		});
	}
	
	/**
	  * @Auther: zhoumc  
	  * @Description: 修改手机号
	  * @Date:2015-6-25下午5:28:24  
	  * @return void 
	  * @说明  代码版权归 杭州安存网络科技有限公司 所有
	  */
	 private void ChangePhone() {
		 HashMap<String, String> map = new HashMap<String, String>();
		    map.put("tokenid", AXSharedPreferences.getTokenId(mContext));
		    map.put("mobile", inputphone);
			map.put("code", code);
			map.put(InterfaceConstants.INTERFACE_NAME, InterfaceConstants.BIND_NEW_PHONE_CODE);
			MasterHttpClient.postForJava(mContext, map, new AsyncHttpResponseHandler() {
				
				@Override
				public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
					String result = new String(arg2);
					if (StringUtils.isNotEmpty(result)) {
						if (GlobalConstants.isDebug) {
							Log.i(TAG, result + "," + arg0);
						}
						GsonPojo gr = GsonUtils.toObject(result,GsonPojo.class);
						if (GlobalConstants.HTTP_SUCCESS_CODE.equals(gr.getCode())) {
							AXSharedPreferences.setUserPhone(mContext, inputphone);
						    finish();
						    
						}
					}
					
				}
				
				@Override
				public void onFailure(int arg0, Header[] arg1, byte[] arg2, Throwable arg3) {
					DialogUtil.showSystemToast(mContext, "网络异常,请稍后再试！");
					
				}
			});
			
		}
     
	 /**
	  * @Auther: zhoumc  
	  * @Description: 获取验证码
	  * @Date:2015-6-25下午4:03:18  
	  * @return void 
	  * @说明  代码版权归 杭州安存网络科技有限公司 所有
	  */
	 private void getCode() {
		 inputphone = newPhoneInput.getText().toString();
		 if (!StringUtils.isMobileNO(inputphone)) {
				DialogUtil.showSystemToast(mContext, "手机号不能为空或错误");
				return;
			}
		 HashMap<String, String> map = new HashMap<String, String>();
			map.put("mobile", inputphone);
			map.put("type", "bindNewMobile");
			map.put("sys_request_code", InterfaceConstants.GET_CODE_CODE);
			MasterHttpClient.postForJava(mContext, map, new AsyncHttpResponseHandler() {
				
				@Override
				public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
					String result = new String(arg2);
					if (GlobalConstants.isDebug) {
						Log.i(TAG, result + "," + arg0);
					}
					GsonPojo gr = GsonUtils.toObject(result, GsonPojo.class);
					// 返回失败
					if (!GlobalConstants.HTTP_SUCCESS_CODE.equals(gr
							.getCode())) {
						DialogUtil.showSystemToast(mContext, gr.getMsg());
					}
				}
				
				@Override
				public void onFailure(int arg0, Header[] arg1, byte[] arg2, Throwable arg3) {
					DialogUtil.showSystemToast(mContext, "网络连接错误，请稍后再试");
				}
			});
		}
	 
	 
	 private Handler mHandler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				super.handleMessage(msg);
				switch (msg.what) {
				/*//去登录
				case LOGIN:
					toLogin();
					break;*/
				
				//获取验证码
				case GET_CODE:
					runOnUiThread(new Runnable() {
						public void run() {
							if (time == 0) {
								phoneGetcode.setSelected(false);
								phoneGetcode.setClickable(true);
								phoneGetcode.setText("获取验证码");
								time = 60;
								flag = false;
								return;
							} else {
								phoneGetcode.setText(time + hint);
							}
						}
					});
					break;

				default:
					break;
				}
			}
		};
     
		
}
