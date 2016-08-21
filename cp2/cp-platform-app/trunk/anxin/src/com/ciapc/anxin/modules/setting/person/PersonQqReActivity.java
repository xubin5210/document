package com.ciapc.anxin.modules.setting.person;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;

import com.ciapc.anxin.R;
import com.ciapc.anxin.common.BaseActivity;
import com.ciapc.anxin.common.view.CustomEditText;
import com.ciapc.anxin.common.view.PubTitle;
import com.ciapc.anxin.common.view.PubTitle.OnTitleClickListener;
import com.ciapc.anxin.utils.CheckStringUtils;
import com.master.util.common.DialogUtil;
import com.master.util.common.StringUtils;

/**
 * 
 * @author zhoumc   
 * @Description: 个人信息——QQ  
 * @ClassName: PersonQQReActivity.java   
 * @date 2015-6-15 下午2:04:31      
 * @说明  代码版权归 杭州安存网络科技有限公司 所有
 */
public class PersonQqReActivity extends BaseActivity {
	
	 public static final String TAG = "PersonQqReActivity";
		
     //自己在values下面array中定义一个名称
	 private PubTitle  pubTitle;
	 
	 private Context  mContext;
	 
	 private CustomEditText inputQQ;
	
        @Override
        protected void onCreate(Bundle savedInstanceState) {
        	super.onCreate(savedInstanceState);
        	setContentView(R.layout.person_qq_re_layout);
        	mContext=this;
        	initView();
        	initClick();
        	
        	inputQQ=(CustomEditText) findViewById(R.id.et_person_qq);
        	if(StringUtils.isNotEmpty(null != getMapValue("qq") ? getMapValue(
    				"qq").toString() : "")){
        		inputQQ.setText(getMapValue("qq").toString());
    		}
        	
        }

        

		/**
         * @Auther: zhoumc  
         * @Description: 初始化控件
         * @Date:2015-6-26下午6:03:59  
         * @return void 
         * @说明  代码版权归 杭州安存网络科技有限公司 所有
         */
		private void initView() {
			//标题
			pubTitle=(PubTitle) findViewById(R.id.person_qq_re_title);
			//QQ
			inputQQ=(CustomEditText) findViewById(R.id.et_person_qq);
		}
		/**
		 * @Auther: zhoumc  
		 * @Description: 初始化事件
		 * @Date:2015-6-26下午6:04:56  
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
					String str = inputQQ.getText().toString();
				    setMapValue("qq", str);
				    finish();
					
					
				}
			});
			
		}
}
