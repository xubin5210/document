package com.ancun.xinhu.domain.model;
import java.util.List;

/**
 * Auto-generated: 2016-06-16 11:25:15
 *
 * @author aTool.org (i@aTool.org)
 * @website http://www.atool.org/json2javabean.php
 */
public class JxsscJsonBean {

    private int rows;
    private String code;
    private String info;
    private List<Data> data;
    public void setRows(int rows) {
         this.rows = rows;
     }
     public int getRows() {
         return rows;
     }

    public void setCode(String code) {
         this.code = code;
     }
     public String getCode() {
         return code;
     }

    public void setInfo(String info) {
         this.info = info;
     }
     public String getInfo() {
         return info;
     }

    public void setData(List<Data> data) {
         this.data = data;
     }
     public List<Data> getData() {
         return data;
     }
     
     public class Data {
    	    private String expect;
    	    private String opencode;
    	    private String opentime;
    	    private int opentimestamp;
    	    public void setExpect(String expect) {
    	         this.expect = expect;
    	     }
    	     public String getExpect() {
    	         return expect;
    	     }

    	    public void setOpencode(String opencode) {
    	         this.opencode = opencode;
    	     }
    	     public String getOpencode() {
    	         return opencode;
    	     }

    	    public void setOpentime(String opentime) {
    	         this.opentime = opentime;
    	     }
    	     public String getOpentime() {
    	         return opentime;
    	     }

    	    public void setOpentimestamp(int opentimestamp) {
    	         this.opentimestamp = opentimestamp;
    	     }
    	     public int getOpentimestamp() {
    	         return opentimestamp;
    	     }
     }
}