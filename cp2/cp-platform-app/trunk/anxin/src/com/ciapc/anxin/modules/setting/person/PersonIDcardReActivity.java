package com.ciapc.anxin.modules.setting.person;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;

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
 * @Description: 个人信息——身份证   
 * @ClassName: PersonIDcardReActivity.java   
 * @date 2015-6-15 下午3:18:37      
 * @说明  代码版权归 杭州安存网络科技有限公司 所有
 */
public class PersonIDcardReActivity extends BaseActivity {
	
	public static final String TAG = "PersonQqReActivity";
	
    //自己在values下面array中定义一个名称
	 private PubTitle  pubTitle;
	 
	 private CustomEditText  etPersonIdcard;
	 private Context mContext;
	 
	
	
       @Override
    protected void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
    	setContentView(R.layout.person_idcard_re_layout);
    	initView();
    	initClick();
    	
    	etPersonIdcard=(CustomEditText) findViewById(R.id.et_person_idcard);
    	if(StringUtils.isNotEmpty(null != getMapValue("nameId") ? getMapValue(
				"nameId").toString() : "")){
    		etPersonIdcard.setText(getMapValue("nameId").toString());
		}
    	
    }

     

	/**
        * @Auther: zhoumc  
        * @Description: 初始化控件
        * @Date:2015-6-26下午6:28:06  
        * @return void 
        * @说明  代码版权归 杭州安存网络科技有限公司 所有
        */
	private void initView() {
		//标题
		pubTitle=(PubTitle) findViewById(R.id.person_idcard_re_title);
		//身份证
		etPersonIdcard=(CustomEditText) findViewById(R.id.et_person_idcard);
		
	}
	
	/**
	 * @Auther: zhoumc  
	 * @Description: 初始化事件
	 * @Date:2015-6-26下午6:28:45  
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
					String str = etPersonIdcard.getText().toString();
					if (!CheckStringUtils.checkUserIdCrad(str)) {
						DialogUtil.showSystemToast(mContext, "身份证格式不正确或为空");
						return;
					}
				    setMapValue("nameId", str);
					finish();
					
				}
			});
			
		}
}
