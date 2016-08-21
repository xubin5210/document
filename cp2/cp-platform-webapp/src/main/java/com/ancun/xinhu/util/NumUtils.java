package com.ancun.xinhu.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.ancun.xinhu.domain.model.Cpb;


public class NumUtils {
	public static int[] minNum = {0,1,2,3,4};  //小
	public static int[] maxNum = {5,6,7,8,9};  //大
	public static int[] danNum = {1,3,5,7,9};  //单数
	public static int[] shuangNum = {2,6,4,8,0}; //双数
	public static int[] zhiNum = {1,2,3,5,7}; //质数
	public static int[] heNum = {0,4,6,8,9}; //合数
	public static int[] zeroNum = {0,3,6,9}; //0路
	public static int[] oneNum = {1,4,7}; //1路
	public static int[] twoNum = {2,5,8}; //2路
	public static int[] bz = {000,111,222,333,444,555,666,777,888,999}; //豹子
	public static String[] sz = {"012","123","234","345","456","567","678","789","890","019"}; //顺子
	
	
	public static void init(Cpb c){
		hs(c);
		kd(c);
		js(c);
		os(c);
		zs(c);
		hs(c);
		ds(c);
		dx(c);
		qszt(c);
		zszt(c);
		hszt(c);
	}
	
	/**
	 * 设置和值
	 * @param c
	 * <p>
	 * author: <a href="mailto:taibai@ancun.com">xubin</a><br>
	 * create at: 2016年6月16日 下午3:24:37
	 */
	public static void hz(Cpb c){
		int hz = c.getWws() + c.getQws()  + c.getBws() + c.getSws() + c.getGws();
		c.setHz(hz);
		c.setHzwh(hz%10);
	}
	
	/**
	 * 设置和值
	 * @param c
	 * <p>
	 * author: <a href="mailto:taibai@ancun.com">xubin</a><br>
	 * create at: 2016年6月16日 下午3:24:37
	 */
	public static void kd(Cpb c){
		List<Integer> list = getNumList(c);
		Integer max = Collections.max(list);
		Integer min = Collections.min(list);
		int kd = max - min;
		c.setKd(kd);
	}
	
	/**
	 * 奇数
	 * @param c
	 * <p>
	 * author: <a href="mailto:taibai@ancun.com">xubin</a><br>
	 * create at: 2016年6月16日 下午4:26:14
	 */
	public static void js(Cpb c){
		int js = isIn(danNum, c.getWws()) +isIn(danNum, c.getQws()) +isIn(danNum, c.getBws())+isIn(danNum, c.getSws())+isIn(danNum, c.getGws());
		c.setJs(js);
	}
	
	/**
	 * OU数
	 * @param c
	 * <p>
	 * author: <a href="mailto:taibai@ancun.com">xubin</a><br>
	 * create at: 2016年6月16日 下午4:26:14
	 */
	public static void os(Cpb c){
		int os = isIn(shuangNum, c.getWws()) +isIn(shuangNum, c.getQws()) +isIn(shuangNum, c.getBws())+isIn(shuangNum, c.getSws())+isIn(shuangNum, c.getGws());
		c.setOs(os);
	}
	
	/**
	 * OU数
	 * @param c
	 * <p>
	 * author: <a href="mailto:taibai@ancun.com">xubin</a><br>
	 * create at: 2016年6月16日 下午4:26:14
	 */
	public static void zs(Cpb c){
		int zs = isIn(zhiNum, c.getWws()) +isIn(zhiNum, c.getQws()) +isIn(zhiNum, c.getBws())
				+isIn(zhiNum, c.getSws())+isIn(zhiNum, c.getGws());
		c.setZs(zs);
	}
	
	/**
	 * 合数
	 * @param c
	 * <p>
	 * author: <a href="mailto:taibai@ancun.com">xubin</a><br>
	 * create at: 2016年6月16日 下午4:26:14
	 */
	public static void hs(Cpb c){
		int hs = isIn(heNum, c.getWws()) +isIn(heNum, c.getQws()) +isIn(heNum, c.getBws())
				+isIn(heNum, c.getSws())+isIn(heNum, c.getGws());
		c.setHs(hs);;
	}
	
	/**
	 * 单双
	 * @param c
	 * <p>
	 * author: <a href="mailto:taibai@ancun.com">xubin</a><br>
	 * create at: 2016年6月16日 下午4:26:14
	 */
	public static void ds(Cpb c){
		if( isIn(danNum, c.getWws()) > 0){
			c.setWwds(1);
		}else{
			c.setWwds(2);
		}
		if( isIn(danNum, c.getQws()) > 0){
			c.setQwds(1);
		}else{
			c.setQwds(2);
		}
		if( isIn(danNum, c.getBws()) > 0){
			c.setBwds(1);
		}else{
			c.setBwds(2);
		}
		if( isIn(danNum, c.getSws()) > 0){
			c.setSwds(1);
		}else{
			c.setSwds(2);
		}
		if( isIn(danNum, c.getGws()) > 0){
			c.setGwds(1);
		}else{
			c.setGwds(2);
		}
	}
	
	/**
	 * 大小
	 * @param c
	 * <p>
	 * author: <a href="mailto:taibai@ancun.com">xubin</a><br>
	 * create at: 2016年6月16日 下午4:26:14
	 */
	public static void dx(Cpb c){
		if( isIn(minNum, c.getWws()) > 0){
			c.setWwdx(1);
		}else{
			c.setWwdx(2);
		}
		if( isIn(minNum, c.getQws()) > 0){
			c.setQwdx(1);
		}else{
			c.setQwdx(2);
		}
		if( isIn(minNum, c.getBws()) > 0){
			c.setBwdx(1);
		}else{
			c.setBwdx(2);
		}
		if( isIn(minNum, c.getSws()) > 0){
			c.setSwdx(1);
		}else{
			c.setSwdx(2);
		}
		if( isIn(minNum, c.getGws()) > 0){
			c.setGwdx(1);
		}else{
			c.setGwdx(2);
		}
	}
	
	/**
	 * 前三状态
	 * @param c
	 * <p>
	 * author: <a href="mailto:taibai@ancun.com">xubin</a><br>
	 * create at: 2016年6月17日 下午2:37:56
	 */
	public static void qszt(Cpb c){
		if((c.getWws() == c.getQws() ) && (c.getWws() == c.getBws())){ //豹子
			c.setQsbz(1);
		}else if(isSz(c.getWws(),c.getQws(),c.getBws())){ //顺子
			c.setQssz(1);
		}else if(isDz(c.getWws(),c.getQws(),c.getBws())){ //对子
			c.setQsdz(1);
		}else if(isBs(c.getWws(),c.getQws(),c.getBws())){//半顺
			c.setQsbs(1);
		}else{//杂6
			c.setQszl(1);
		}
	}
	
	/**
	 * 中三状态
	 * @param c
	 * <p>
	 * author: <a href="mailto:taibai@ancun.com">xubin</a><br>
	 * create at: 2016年6月17日 下午2:37:56
	 */
	public static void zszt(Cpb c){
		if((c.getQws() == c.getBws() ) && (c.getQws() == c.getSws())){ //豹子
			c.setZsbz(1);
		}else if(isSz(c.getQws(),c.getBws(),c.getSws())){ //顺子
			c.setZssz(1);
		}else if(isDz(c.getQws(),c.getBws(),c.getSws())){ //对子
			c.setZsdz(1);
		}else if(isBs(c.getQws(),c.getBws(),c.getSws())){//半顺
			c.setZsbs(1);
		}else{//杂6
			c.setZszl(1);
		}
	}
	
	/**
	 * 后三状态
	 * @param c
	 * <p>
	 * author: <a href="mailto:taibai@ancun.com">xubin</a><br>
	 * create at: 2016年6月17日 下午2:37:56
	 */
	public static void hszt(Cpb c){
		if((c.getBws() == c.getSws() ) && (c.getBws() == c.getGws())){ //豹子
			c.setHsbz(1);
		}else if(isSz(c.getBws(),c.getSws(),c.getGws())){ //顺子
			c.setHssz(1);
		}else if(isDz(c.getBws(),c.getSws(),c.getGws())){ //对子
			c.setHsdz(1);
		}else if(isBs(c.getBws(),c.getSws(),c.getGws())){//半顺
			c.setHsbs(1);
		}else{//杂6
			c.setHszl(1);
		}
	}
	
	/**
	 * 判断是否半顺
	 * @param wws
	 * @param qws
	 * @param bws
	 * @return
	 * <p>
	 * author: <a href="mailto:taibai@ancun.com">xubin</a><br>
	 * create at: 2016年6月17日 下午4:57:18
	 */
	private static boolean isBs(Integer wws, Integer qws, Integer bws) {
		List<Integer> list  = new ArrayList<Integer>();
		list.add(wws+1);
		list.add(qws+1);
		list.add(bws+1);
		Collections.sort(list);
		
		if(list.get(2) - list.get(1) == 1 || list.get(2) - list.get(0) == 1 || list.get(1) - list.get(0) == 1 ){
			return true;
		}
		return false;
	}

	/**
	 * 判断是否对子
	 * @param wws
	 * @param qws
	 * @param bws
	 * @return
	 * <p>
	 * author: <a href="mailto:taibai@ancun.com">xubin</a><br>
	 * create at: 2016年6月17日 下午4:52:53
	 */
	private static boolean isDz(Integer wws, Integer qws, Integer bws) {
		return wws == qws || wws == bws || qws == bws;
	}

	/**
	 * 判断是否顺子
	 * @param c
	 * @return
	 * <p>
	 * author: <a href="mailto:taibai@ancun.com">xubin</a><br>
	 * create at: 2016年6月17日 下午3:03:17
	 */
	private static boolean isSz(Integer wws, Integer qws, Integer bws) {
		List<Integer> list  = new ArrayList<Integer>();
		list.add(wws);
		list.add(qws);
		list.add(bws);
		Collections.sort(list);
		String s = list.get(0).toString() + list.get(1).toString() + list.get(2).toString();
		return isIn(sz, s);
	}


	public static List<Integer> getNumList(Cpb c){
		List<Integer> list  = new ArrayList<Integer>();
	    list.add(c.getWws());
	    list.add(c.getQws());
	    list.add(c.getBws());
	    list.add(c.getSws());
	    list.add(c.getGws());
	    return list;
	}
	
	/**
	 * 如果包含就返回1
	 * @param num
	 * @param val
	 * @return
	 * <p>
	 * author: <a href="mailto:taibai@ancun.com">xubin</a><br>
	 * create at: 2016年6月16日 下午4:37:07
	 */
	public static int isIn(int[] num ,int val){
		int i = 0;
		for(int k = 0;k<num.length;k++){
			int j = num[k];
			if(j == val){
				i = 1;
				break;
			}
		}
		return i;
	} 
	
	/**
	 * 如果包含就返回1
	 * @param num
	 * @param val
	 * @return
	 * <p>
	 * author: <a href="mailto:taibai@ancun.com">xubin</a><br>
	 * create at: 2016年6月16日 下午4:37:07
	 */
	public static boolean isIn(String[] num ,String val){
		int i = 0;
		for(int k = 0;k<num.length;k++){
			String j = num[k];
			if(j.equals(val)){
				return true;
			}
		}
		return false;
	} 
	
	
	public static void main(String[] args) {
		int i = 37;
		System.out.println(i%10);
				
	}
}
