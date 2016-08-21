package com.ciapc.anxin.utils.ListView.listener;

import com.ciapc.anxin.utils.ListView.ZSwipeItem;


/**
 * 滑动监听器
 * 
 * @class: com.socks.zlistview.bean.SwipeListener
 * @author zhaokaiqiang
 * @date 2015-1-6 下午5:49:10
 * 
 */
public interface SwipeListener {

	public void onStartOpen(ZSwipeItem layout);

	public void onOpen(ZSwipeItem layout);

	public void onStartClose(ZSwipeItem layout);

	public void onClose(ZSwipeItem layout);

	public void onUpdate(ZSwipeItem layout, int leftOffset, int topOffset);

	public void onHandRelease(ZSwipeItem layout, float xvel, float yvel);

}
