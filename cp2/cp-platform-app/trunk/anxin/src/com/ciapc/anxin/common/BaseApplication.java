/**    
 * @author maomy  
 * @Description: 全局application 
 * @Package com.ciapc.tzd.common   
 * @Title: BaseApplication.java   
 * @date 2015-5-25 下午2:33:26   
 * @version V1.0  
   @说明  代码版权归  杭州安存网络科技有限公司 所有
 */ 
package com.ciapc.anxin.common;



import com.ciapc.anxin.common.db.DBHelper;
import com.master.util.http.MasterHttpClient;

import android.app.Application;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;


public class BaseApplication extends Application {
    
    private static BaseApplication context;
    
    /**
     * @Auther: maomy  
     * @Description: 获取context
     * @Date:2015年5月25日上午9:59:44
     * @return  
     * @return Context 
     * @说明  代码版权归 杭州安存网络科技有限公司 所有
     */
    public static BaseApplication getInstance(){
        return context;
    }

    
    
    @Override
    public void onCreate() {
       
        super.onCreate();
        context = this;
        
        MasterHttpClient.setHttpUrl(GlobalConstants.URL);
    }
 
    
   
    
}
