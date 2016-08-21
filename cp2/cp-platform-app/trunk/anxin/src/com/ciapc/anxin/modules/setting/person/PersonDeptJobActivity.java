package com.ciapc.anxin.modules.setting.person;

import android.content.Context;
import android.os.Bundle;

import com.ciapc.anxin.R;
import com.ciapc.anxin.common.BaseActivity;
import com.ciapc.anxin.common.view.CustomEditText;
import com.ciapc.anxin.common.view.PubTitle;
import com.ciapc.anxin.common.view.PubTitle.OnTitleClickListener;
import com.master.util.common.DialogUtil;
import com.master.util.common.StringUtils;
/**
 * 
 * @author zhoumc   
 * @Description: 职位  
 * @ClassName: PersonJobActivity.java   
 * @date 2015-6-12 下午5:33:27      
 * @说明  代码版权归 杭州安存网络科技有限公司 所有
 */
public class PersonDeptJobActivity extends BaseActivity {
	
    public static final String TAG = "PersonNameActivity";
	
	//自己在values下面array中定义一个名称
    private PubTitle  pubTitle;
	
    private Context mContext;
    
    private CustomEditText etPersonDept,etPersonJob;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
    	setContentView(R.layout.person_deptjob_layout);
    	mContext = this;
    	initView();
    	initClick();
    	if((StringUtils.isNotEmpty(null != getMapValue("deptName") ? getMapValue(
				"deptName").toString() : ""))){
    		etPersonDept.setText(getMapValue("deptName").toString());
		}
    	
    }

/**
 * @Auther: zhoumc  
 * @Description: 初始化控件 
 * @Date:2015-6-29下午3:30:55  
 * @return void 
 * @说明  代码版权归 杭州安存网络科技有限公司 所有
 */
	private void initView() {
		//标题
		pubTitle=(PubTitle) findViewById(R.id.person_job_title);
		//部门
		etPersonDept=(CustomEditText) findViewById(R.id.et_person_dept);
	    //职务
		etPersonJob=(CustomEditText) findViewById(R.id.et_person_job);
		
	}
	
	/**
	 * @Auther: zhoumc  
	 * @Description: 初始化事件
	 * @Date:2015-6-29下午3:31:31  
	 * @return void 
	 * @说明  代码版权归 杭州安存网络科技有限公司 所有
	 */
	private void initClick() {
		    //标题
			pubTitle.setShowLeftOrRight(true, false, false).setOnTitleClickListener(new OnTitleClickListener() {
				
				@Override
				public void onRightClick() {}
				
				@Override
				public void onLeftClick() {
					String strDept = etPersonDept.getText().toString();
					if(StringUtils.isNotEmpty(strDept)){
						if(strDept.length() > 10){
							DialogUtil.showSystemToast(mContext, "内容过长，请保持在10字以内");
							return;
						}
					}
					setMapValue("deptName", strDept);
					finish();
				}
			});
		
	}
}
