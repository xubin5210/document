package com.ciapc.anxin.modules.setting;

import android.os.Bundle;

import com.ciapc.anxin.R;
import com.ciapc.anxin.common.BaseActivity;
import com.ciapc.anxin.common.view.PubTitle;
import com.ciapc.anxin.common.view.PubTitle.OnTitleClickListener;
/**
 * 
 * @author zhoumc   
 * @Description: 隐私——黑名单页面   
 * @ClassName: ConcealBlackListActivity.java   
 * @date 2015-6-10 上午10:48:08      
 * @说明  代码版权归 杭州安存网络科技有限公司 所有
 */
public class ConcealBlackListActivity extends BaseActivity {
	public static final String TAG = "ConcealBlackListActivity";
	
	    //自己在values下面array中定义一个名称
		private PubTitle  pubTitle;
       @Override
    protected void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
    	setContentView(R.layout.conceal_black_list_layout);
    	pubTitle = (PubTitle) findViewById(R.id.conceal_black_list);
    	pubTitle.setShowLeftOrRight(true, false, false).setOnTitleClickListener(new OnTitleClickListener() {
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
