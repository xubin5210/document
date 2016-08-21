package com.ancun.xinhu.domain.model;

import java.io.Serializable;
import java.util.Date;

/**
*实体类
*/
public class Cpb implements Serializable {
	private static final long serialVersionUID = 14649869453162L;

	
	
						private Integer id;//ID		
					private String qh;//期号		
					private Integer wws;//万位数		
					private Integer qws;//千位数		
					private Integer bws;//百位数		
					private Integer sws;//十位数		
					private Integer gws;//个位数		
					private Integer hz;//和值		
					private Integer kd;//跨度		
					private Integer hzwh;//和值尾号		
					private Integer js;//奇数		
					private Integer os;//偶数		
					private Integer zs;//质数		
					private Integer hs;//合数		
					private Integer wwds;//万位单双 1单 2 双		
					private Integer qwds;//千位单双 1单 2 双		
					private Integer bwds;//百位单双 1单 2 双		
					private Integer swds;//十位单双 1单 2 双		
					private Integer gwds;//个位单双 1单 2 双		
					private Integer wwdx;//万位大小 1小 2 大		
					private Integer qwdx;//千位大小 1小 2 大		
					private Integer bwdx;//百位大小 1小 2 大		
					private Integer swdx;//十位大小 1小 2 大		
					private Integer gwdx;//个位大小 1小 2 大		
					private Integer wwjo;//万位奇偶 1奇 2 偶		
					private Integer qwjo;//千位奇偶 1奇 2 偶		
					private Integer bwjo;//百位奇偶 1奇 2 偶		
					private Integer swjo;//十位奇偶 1奇 2 偶		
					private Integer gwjo;//各位奇偶 1奇 2 偶		
					private Integer ww012l;//万位012路		
					private Integer qw012l;//千位012路		
					private Integer bw012l;//百位012路		
					private Integer sw012l;//十位012路		
					private Integer gw012l;//各位012路		
					private Integer zhdx;//总和大小 1小 2大		
					private Integer zhds;//总和单双 1单 2双		
					private Integer lh;//龙虎 1龙 2虎		
					private Date sj;//更新时间		
					private Integer qsbz;//前3豹子		
					private Integer qssz;//前三顺子		
					private Integer qsdz;//前三对子		
					private Integer qsbs;//前三半顺		
					private Integer qszl;//前三杂6		
					private Integer zsbz;//中3豹子		
					private Integer zssz;//中三顺子		
					private Integer zsdz;//中三对子		
					private Integer zsbs;//中三半顺		
					private Integer zszl;//中三杂6		
					private Integer hsbz;//后3豹子		
					private Integer hssz;//后三顺子		
					private Integer hsdz;//后三对子		
					private Integer hsbs;//后三半顺		
					private Integer hszl;//后三杂6		
		
		

		public Cpb() {}

		/**
	*
		 *@param id -- ID
		*/
	public Cpb(Integer id) {
					this.id = id;
			}

			/**ID*/
	public Integer getId() {
		return id;
	}
	/**ID*/
	public void setId(Integer id) {
		this.id = id;
	}
		/**期号*/
	public String getQh() {
		return qh;
	}
	/**期号*/
	public void setQh(String qh) {
		this.qh = qh;
	}
		/**万位数*/
	public Integer getWws() {
		return wws;
	}
	/**万位数*/
	public void setWws(Integer wws) {
		this.wws = wws;
	}
		/**千位数*/
	public Integer getQws() {
		return qws;
	}
	/**千位数*/
	public void setQws(Integer qws) {
		this.qws = qws;
	}
		/**百位数*/
	public Integer getBws() {
		return bws;
	}
	/**百位数*/
	public void setBws(Integer bws) {
		this.bws = bws;
	}
		/**十位数*/
	public Integer getSws() {
		return sws;
	}
	/**十位数*/
	public void setSws(Integer sws) {
		this.sws = sws;
	}
		/**个位数*/
	public Integer getGws() {
		return gws;
	}
	/**个位数*/
	public void setGws(Integer gws) {
		this.gws = gws;
	}
		/**和值*/
	public Integer getHz() {
		return hz;
	}
	/**和值*/
	public void setHz(Integer hz) {
		this.hz = hz;
	}
		/**跨度*/
	public Integer getKd() {
		return kd;
	}
	/**跨度*/
	public void setKd(Integer kd) {
		this.kd = kd;
	}
		/**和值尾号*/
	public Integer getHzwh() {
		return hzwh;
	}
	/**和值尾号*/
	public void setHzwh(Integer hzwh) {
		this.hzwh = hzwh;
	}
		/**奇数*/
	public Integer getJs() {
		return js;
	}
	/**奇数*/
	public void setJs(Integer js) {
		this.js = js;
	}
		/**偶数*/
	public Integer getOs() {
		return os;
	}
	/**偶数*/
	public void setOs(Integer os) {
		this.os = os;
	}
		/**质数*/
	public Integer getZs() {
		return zs;
	}
	/**质数*/
	public void setZs(Integer zs) {
		this.zs = zs;
	}
		/**合数*/
	public Integer getHs() {
		return hs;
	}
	/**合数*/
	public void setHs(Integer hs) {
		this.hs = hs;
	}
		/**万位单双 1单 2 双*/
	public Integer getWwds() {
		return wwds;
	}
	/**万位单双 1单 2 双*/
	public void setWwds(Integer wwds) {
		this.wwds = wwds;
	}
		/**千位单双 1单 2 双*/
	public Integer getQwds() {
		return qwds;
	}
	/**千位单双 1单 2 双*/
	public void setQwds(Integer qwds) {
		this.qwds = qwds;
	}
		/**百位单双 1单 2 双*/
	public Integer getBwds() {
		return bwds;
	}
	/**百位单双 1单 2 双*/
	public void setBwds(Integer bwds) {
		this.bwds = bwds;
	}
		/**十位单双 1单 2 双*/
	public Integer getSwds() {
		return swds;
	}
	/**十位单双 1单 2 双*/
	public void setSwds(Integer swds) {
		this.swds = swds;
	}
		/**个位单双 1单 2 双*/
	public Integer getGwds() {
		return gwds;
	}
	/**个位单双 1单 2 双*/
	public void setGwds(Integer gwds) {
		this.gwds = gwds;
	}
		/**万位大小 1小 2 大*/
	public Integer getWwdx() {
		return wwdx;
	}
	/**万位大小 1小 2 大*/
	public void setWwdx(Integer wwdx) {
		this.wwdx = wwdx;
	}
		/**千位大小 1小 2 大*/
	public Integer getQwdx() {
		return qwdx;
	}
	/**千位大小 1小 2 大*/
	public void setQwdx(Integer qwdx) {
		this.qwdx = qwdx;
	}
		/**百位大小 1小 2 大*/
	public Integer getBwdx() {
		return bwdx;
	}
	/**百位大小 1小 2 大*/
	public void setBwdx(Integer bwdx) {
		this.bwdx = bwdx;
	}
		/**十位大小 1小 2 大*/
	public Integer getSwdx() {
		return swdx;
	}
	/**十位大小 1小 2 大*/
	public void setSwdx(Integer swdx) {
		this.swdx = swdx;
	}
		/**个位大小 1小 2 大*/
	public Integer getGwdx() {
		return gwdx;
	}
	/**个位大小 1小 2 大*/
	public void setGwdx(Integer gwdx) {
		this.gwdx = gwdx;
	}
		/**万位奇偶 1奇 2 偶*/
	public Integer getWwjo() {
		return wwjo;
	}
	/**万位奇偶 1奇 2 偶*/
	public void setWwjo(Integer wwjo) {
		this.wwjo = wwjo;
	}
		/**千位奇偶 1奇 2 偶*/
	public Integer getQwjo() {
		return qwjo;
	}
	/**千位奇偶 1奇 2 偶*/
	public void setQwjo(Integer qwjo) {
		this.qwjo = qwjo;
	}
		/**百位奇偶 1奇 2 偶*/
	public Integer getBwjo() {
		return bwjo;
	}
	/**百位奇偶 1奇 2 偶*/
	public void setBwjo(Integer bwjo) {
		this.bwjo = bwjo;
	}
		/**十位奇偶 1奇 2 偶*/
	public Integer getSwjo() {
		return swjo;
	}
	/**十位奇偶 1奇 2 偶*/
	public void setSwjo(Integer swjo) {
		this.swjo = swjo;
	}
		/**各位奇偶 1奇 2 偶*/
	public Integer getGwjo() {
		return gwjo;
	}
	/**各位奇偶 1奇 2 偶*/
	public void setGwjo(Integer gwjo) {
		this.gwjo = gwjo;
	}
		/**万位012路*/
	public Integer getWw012l() {
		return ww012l;
	}
	/**万位012路*/
	public void setWw012l(Integer ww012l) {
		this.ww012l = ww012l;
	}
		/**千位012路*/
	public Integer getQw012l() {
		return qw012l;
	}
	/**千位012路*/
	public void setQw012l(Integer qw012l) {
		this.qw012l = qw012l;
	}
		/**百位012路*/
	public Integer getBw012l() {
		return bw012l;
	}
	/**百位012路*/
	public void setBw012l(Integer bw012l) {
		this.bw012l = bw012l;
	}
		/**十位012路*/
	public Integer getSw012l() {
		return sw012l;
	}
	/**十位012路*/
	public void setSw012l(Integer sw012l) {
		this.sw012l = sw012l;
	}
		/**各位012路*/
	public Integer getGw012l() {
		return gw012l;
	}
	/**各位012路*/
	public void setGw012l(Integer gw012l) {
		this.gw012l = gw012l;
	}
		/**总和大小 1小 2大*/
	public Integer getZhdx() {
		return zhdx;
	}
	/**总和大小 1小 2大*/
	public void setZhdx(Integer zhdx) {
		this.zhdx = zhdx;
	}
		/**总和单双 1单 2双*/
	public Integer getZhds() {
		return zhds;
	}
	/**总和单双 1单 2双*/
	public void setZhds(Integer zhds) {
		this.zhds = zhds;
	}
		/**龙虎 1龙 2虎*/
	public Integer getLh() {
		return lh;
	}
	/**龙虎 1龙 2虎*/
	public void setLh(Integer lh) {
		this.lh = lh;
	}
		/**更新时间*/
	public Date getSj() {
		return sj;
	}
	/**更新时间*/
	public void setSj(Date sj) {
		this.sj = sj;
	}
		/**前3豹子*/
	public Integer getQsbz() {
		return qsbz;
	}
	/**前3豹子*/
	public void setQsbz(Integer qsbz) {
		this.qsbz = qsbz;
	}
		/**前三顺子*/
	public Integer getQssz() {
		return qssz;
	}
	/**前三顺子*/
	public void setQssz(Integer qssz) {
		this.qssz = qssz;
	}
		/**前三对子*/
	public Integer getQsdz() {
		return qsdz;
	}
	/**前三对子*/
	public void setQsdz(Integer qsdz) {
		this.qsdz = qsdz;
	}
		/**前三半顺*/
	public Integer getQsbs() {
		return qsbs;
	}
	/**前三半顺*/
	public void setQsbs(Integer qsbs) {
		this.qsbs = qsbs;
	}
		/**前三杂6*/
	public Integer getQszl() {
		return qszl;
	}
	/**前三杂6*/
	public void setQszl(Integer qszl) {
		this.qszl = qszl;
	}
		/**中3豹子*/
	public Integer getZsbz() {
		return zsbz;
	}
	/**中3豹子*/
	public void setZsbz(Integer zsbz) {
		this.zsbz = zsbz;
	}
		/**中三顺子*/
	public Integer getZssz() {
		return zssz;
	}
	/**中三顺子*/
	public void setZssz(Integer zssz) {
		this.zssz = zssz;
	}
		/**中三对子*/
	public Integer getZsdz() {
		return zsdz;
	}
	/**中三对子*/
	public void setZsdz(Integer zsdz) {
		this.zsdz = zsdz;
	}
		/**中三半顺*/
	public Integer getZsbs() {
		return zsbs;
	}
	/**中三半顺*/
	public void setZsbs(Integer zsbs) {
		this.zsbs = zsbs;
	}
		/**中三杂6*/
	public Integer getZszl() {
		return zszl;
	}
	/**中三杂6*/
	public void setZszl(Integer zszl) {
		this.zszl = zszl;
	}
		/**后3豹子*/
	public Integer getHsbz() {
		return hsbz;
	}
	/**后3豹子*/
	public void setHsbz(Integer hsbz) {
		this.hsbz = hsbz;
	}
		/**后三顺子*/
	public Integer getHssz() {
		return hssz;
	}
	/**后三顺子*/
	public void setHssz(Integer hssz) {
		this.hssz = hssz;
	}
		/**后三对子*/
	public Integer getHsdz() {
		return hsdz;
	}
	/**后三对子*/
	public void setHsdz(Integer hsdz) {
		this.hsdz = hsdz;
	}
		/**后三半顺*/
	public Integer getHsbs() {
		return hsbs;
	}
	/**后三半顺*/
	public void setHsbs(Integer hsbs) {
		this.hsbs = hsbs;
	}
		/**后三杂6*/
	public Integer getHszl() {
		return hszl;
	}
	/**后三杂6*/
	public void setHszl(Integer hszl) {
		this.hszl = hszl;
	}
		
	@Override
	public String toString() {
		return "Cpb [ id=" + id + ", qh=" + qh + ", wws=" + wws + ", qws=" + qws + ", bws=" + bws + ", sws=" + sws + ", gws=" + gws + ", hz=" + hz + ", kd=" + kd + ", hzwh=" + hzwh + ", js=" + js + ", os=" + os + ", zs=" + zs + ", hs=" + hs + ", wwds=" + wwds + ", qwds=" + qwds + ", bwds=" + bwds + ", swds=" + swds + ", gwds=" + gwds + ", wwdx=" + wwdx + ", qwdx=" + qwdx + ", bwdx=" + bwdx + ", swdx=" + swdx + ", gwdx=" + gwdx + ", wwjo=" + wwjo + ", qwjo=" + qwjo + ", bwjo=" + bwjo + ", swjo=" + swjo + ", gwjo=" + gwjo + ", ww012l=" + ww012l + ", qw012l=" + qw012l + ", bw012l=" + bw012l + ", sw012l=" + sw012l + ", gw012l=" + gw012l + ", zhdx=" + zhdx + ", zhds=" + zhds + ", lh=" + lh + ", sj=" + sj + ", qsbz=" + qsbz + ", qssz=" + qssz + ", qsdz=" + qsdz + ", qsbs=" + qsbs + ", qszl=" + qszl + ", zsbz=" + zsbz + ", zssz=" + zssz + ", zsdz=" + zsdz + ", zsbs=" + zsbs + ", zszl=" + zszl + ", hsbz=" + hsbz + ", hssz=" + hssz + ", hsdz=" + hsdz + ", hsbs=" + hsbs + ", hszl=" + hszl + "]";
	}
}
