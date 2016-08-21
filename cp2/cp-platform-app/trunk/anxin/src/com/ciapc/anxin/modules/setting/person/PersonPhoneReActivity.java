package com.ciapc.anxin.modules.setting.person;

import android.os.Bundle;
import android.util.Log;

import com.ciapc.anxin.R;
import com.ciapc.anxin.common.BaseActivity;
import com.ciapc.anxin.common.view.PubTitle;
import com.ciapc.anxin.common.view.PubTitle.OnTitleClickListener;

/**
 * 
 * @author zhoumc   
 * @Description: 个人信息——手机号   
 * @ClassName: PersonPhoneReActivity.java   
 * @date 2015-6-15 上午10:40:30      
 * @说明  代码版权归 杭州安存网络科技有限公司 所有
 */
public class PersonPhoneReActivity extends BaseActivity {
	 
	 public static final String TAG = "PersonPhoneReActivity";
		
     //自己在values下面array中定义一个名称
	 private PubTitle  pubTitle;
	 
         @Override
        protected void onCreate(Bundle savedInstanceState) {
        	super.onCreate(savedInstanceState);
        	setContentView(R.layout.person_phone_re_layout);
        	initView();
        	initClick();
        	
        	
        }

         

		/**
          * @Auther: zhoumc  
          * @Description: 初始化控件
          * @Date:2015-6-30上午10:30:19  
          * @return void 
          * @说明  代码版权归 杭州安存网络科技有限公司 所有
          */
		private void initView() {
			//标题
			pubTitle=(PubTitle) findViewById(R.id.person_phone_re_title);
			
		}
		/**
		 * @Auther: zhoumc  
		 * @Description: 初始化事件
		 * @Date:2015-6-30上午10:30:49  
		 * @return void 
		 * @说明  代码版权归 杭州安存网络科技有限公司 所有
		 */
		private void initClick() {
			//标题
			pubTitle.setShowLeftOrRight(true, false, true).setOnTitleClickListener(new OnTitleClickListener() {
				@Override
				public void onRightClick() {
					
				}
				
				@Override
				public void onLeftClick() {
					finish();
				}
			});
			
		}
}
