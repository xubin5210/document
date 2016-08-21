package com.ancun.xinhu.util;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.ancun.products.core.utils.DateUtils;

/**
 * Excel工具类
 * 
 * <p>
 * create at 2015年6月5日 下午4:55:01
 * 
 * @author <a href="mailto:qiande@ancun.com">QianDe</a>
 * @version %I%, %G%
 * @see
 */
public class MSExcelHelper {

	public static final String OFFICE_EXCEL_2003_POSTFIX = "xls";
	public static final String OFFICE_EXCEL_2010_POSTFIX = "xlsx";

	public static final String EMPTY = "";
	public static final String BLANKEMPTY = " ";
	public static final String POINT = ".";
	
	public static final String fields="姓名*性别*移动电话*工作邮箱*身份证号职位*部门*";

	/**
	 * 读取2003以前的EXCEL版本
	 * @param is 
	 * 
	 * @param is
	 * @param sheetIndex
	 * @param columns
	 * @return
	 * @throws IOException
	 *             <p>
	 *             author: <a href="mailto:qiande@ancun.com">QianDe</a><br>
	 *             create at: 2015年6月12日 下午4:58:02
	 */
	private static Map<String,Object> readXls(String path, InputStream is) throws IOException {
		List<List<String>> rows = new ArrayList<List<String>>();
		List<String> cells = null;
		Map<String,Object> resultMap = new HashMap<String, Object>();
		HSSFWorkbook work = new HSSFWorkbook(is);
		List<Map<String,String>> errorList = new ArrayList<Map<String,String>>();
		Map<String,String> errorMap = null;
		DecimalFormat df = new DecimalFormat("0");
		// 获取xsl的工作区数量
		for (int numSheet = 0; numSheet < work.getNumberOfSheets(); numSheet++) {
			// 获取指定位置的工作区
			HSSFSheet sheet = work.getSheetAt(numSheet);
			HSSFRow row = null;
			Iterator<Row> iteRow = sheet.rowIterator();
			int index = 0;
			while (iteRow.hasNext()) {
				String errorStr = EMPTY;//错误信息
				if(index==0){
					index++;
					boolean ifTrue = ifTrueFile((HSSFRow) iteRow.next());
					if(!ifTrue)
						resultMap.put("error", ifTrue);
				}
				row = (HSSFRow) iteRow.next();
				String blankRow = EMPTY;
				cells = new ArrayList<String>();
				for (int j = 0; j < row.getLastCellNum(); j++) {
					Cell cell = row.getCell(j);
					String cellStr = EMPTY;
					if(cell!=null && cell.getCellType()==cell.CELL_TYPE_NUMERIC){
						cellStr = df.format(cell.getNumericCellValue());  
					}else{
						cellStr = cell == null ? EMPTY : cell.toString();
						
					}
					
					if(j==0){
						cellStr = cellStr.replaceAll(BLANKEMPTY, EMPTY);
						if(!cellStr.matches("^[\u4e00-\u9fa5]{2,10}$")){
							errorStr = "姓名格式不正确,必须是由2-10为中文组成!";
						}
					}else if(j==1){
						if("".equals(cellStr)){
							errorStr += "性别不能为空!";
						}
					}else if(j==2){
						if(!cellStr.matches("^(((13[0-9]{1})|(15[0-9]{1})|(18[0-9]{1})|(14[0-9]{1}))+\\d{8})$")){
							errorStr += "手机号码不正确!";
						}
					}else if(j==3){
						if(!cellStr.matches("^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+(.[a-zA-Z0-9_-])+")){
							errorStr += "请输入正确的邮箱格式!";
						}else if(cellStr.length()>28){
							errorStr += "邮箱最大长度为28位!";
						}
					}
//					else if(j==4){
//						if(!cellStr.matches("^\\d{15}(\\d{2}(\\d|[A-Z]))?$")){
//							errorStr += ";身份证号码不正确";
//						}
//					}
					else if(j==5){
						cellStr = cellStr.replaceAll("，", ",");
						int type = getErrorType(1,cellStr);
						if(type == 1){
							errorStr += "职位不能为空!";
						}else if(type == 2){
							errorStr += "职位长度不能超过10位!";
						}else if(type == 3){
							errorStr += "职位数量不能超过3个!";
						}
					}else if(j==6){
						cellStr = cellStr.replaceAll("，", ",");
						int type = getErrorType(2,cellStr);
						if(type == 1){
							errorStr += "部门不能为空!";
						}else if(type == 2){
							errorStr += "部门长度不能超过10位!";
						}else if(type == 3){
							errorStr += "部门数量不能超过2个!";
						}
					}
					blankRow+=cellStr;
					cells.add(cellStr);
				}
				if(!"".equals(errorStr) && !EMPTY.equals((blankRow.replaceAll(BLANKEMPTY, EMPTY)))){
					errorMap = new HashMap<String, String>();
					errorMap.put("row", (index+1)+"");
					errorMap.put("error", errorStr);
					errorList.add(errorMap);
				}
				index++;
					
				// 将解析的一行添加至集合
				if(!EMPTY.equals((blankRow.replaceAll(BLANKEMPTY, EMPTY))))
					rows.add(cells);
				else
					rows.add(null);
			}
		}
		resultMap.put("rows", rows);
		resultMap.put("errorList", errorList);
		IOUtils.closeQuietly(is);
		return resultMap;
	}
	
	
	//0 正常 1不能为空 2 长度过长 3 数量限制
	private static int getErrorType(int type, String cellStr){
		if(EMPTY.equals(cellStr))
			return 1;
		if(cellStr.indexOf(",")!=-1){
			String [] arrs = cellStr.split(",");
			if(type==1 && arrs.length>3)
				return 3;
			else if(type==2 && arrs.length>2)
				return 3;
			for (String value : arrs) {
				if(value.length()>10)
					return 2;
			}
		}else{
			if(cellStr.length()>10)
				return 2;
		}
		return 0;
	}
	
	/**
	 * 判断是否是正确的文件
	* @param is
	* @return
	* <p>
	* author: <a href="mailto:qiande@ancun.com">QianDe</a><br>
	* create at: 2015年6月30日 下午6:04:36
	 */
	public static boolean ifTrueFile(HSSFRow row){
		String allCell = "";
		for (int j = 0; j < row.getLastCellNum(); j++) {
			Cell cell = row.getCell(j);
			String cellStr = cell == null ? EMPTY : cell.toString();
			allCell = allCell+cellStr;
		}
		if(fields.equals(allCell))
			return true;
		else
			return false;
	}
	
	/**
	 * 下载excel文件
	* @param path
	* @param values
	* @throws IOException
	* <p>
	* author: <a href="mailto:qiande@ancun.com">QianDe</a><br>
	* create at: 2015年7月16日 下午4:27:26
	 */
	public static String writeXlsText(String path,  List<List<Object>> values) {
		String fileName = System.currentTimeMillis()+".xls";
		OutputStream out = null;
		HSSFWorkbook work = new HSSFWorkbook();
		// 获取xsl的工作区数量
		
		// 获取指定位置的工作区
		HSSFSheet sheet = work.createSheet("sheet1");
		int rownum = 0;
		for (List<Object>  cols:values) {
			HSSFRow row = sheet.createRow(rownum++);
			int colNum = 0;
			for (Object colValue : cols) {
					Cell cell = row.createCell(colNum++);
					cell.setCellValue(colValue.toString());
			}
			
		}
		try {
			out = new FileOutputStream(path+fileName);
			work.write(out);
		} catch (IOException e) {
			e.printStackTrace();
		}
		IOUtils.closeQuietly(out);
		return fileName;
	}

	/**
	 * 读取2007版本以后的EXCEL
	 * 
	 * @param path
	 * @param is 
	 * @return
	 * @throws IOException
	 *             <p>
	 *             author: <a href="mailto:qiande@ancun.com">QianDe</a><br>
	 *             create at: 2015年6月12日 下午4:57:29
	 */
	private static List<List<String>> readXlsx(String path, InputStream is) throws IOException {
		XSSFWorkbook xssfWorkbook = new XSSFWorkbook(is);
		List<List<String>> rows = new ArrayList<List<String>>();
		List<String> cells = null;
		for (int numSheet = 0; numSheet < xssfWorkbook.getNumberOfSheets(); numSheet++) {
			XSSFSheet xssfSheet = xssfWorkbook.getSheetAt(numSheet);
			if (xssfSheet == null) {
				continue;
			}
			for (int rowNum = 1; rowNum <= xssfSheet.getLastRowNum(); rowNum++) {
				XSSFRow xssfRow = xssfSheet.getRow(rowNum);
				cells = new ArrayList<String>();
				if (xssfRow != null) {
					for (int j = 0; j < xssfRow.getLastCellNum(); j++) {
						Cell cell = xssfRow.getCell(j);
						String cellStr = cell == null ? EMPTY : cell.toString();
						if (cell != null) {
							if (Cell.CELL_TYPE_NUMERIC == cell.getCellType() && HSSFDateUtil.isCellDateFormatted(cell)) {
								cellStr = DateUtils.formatDate("yyyy-MM-dd", cell.getDateCellValue());
							}
						}
						cells.add(cellStr);
					}
				}
				rows.add(cells);
			}
		}
		return rows;
	}


	/**
	 * 读取EXCEL
	* @param path
	 * @param is 
	* @return
	* @throws IOException
	* <p>
	* author: <a href="mailto:qiande@ancun.com">QianDe</a><br>
	* create at: 2015年6月12日 下午4:58:35
	 */
	public static Map<String,Object> readExcel(String path, InputStream is) throws IOException {
		if (path == null || EMPTY.equals(path)) {
			return null;
		} else {
			String postfix = getPostfix(path);
			if (!EMPTY.equals(postfix)) {
				if (OFFICE_EXCEL_2003_POSTFIX.equals(postfix)) {
					return readXls(path,is);
				} 
//				else if (OFFICE_EXCEL_2010_POSTFIX.equals(postfix)) {
//					return readXlsx(path,is);
//				}
			}
		}
		return null;
	}

	/**
	 * 截取文件后缀
	* @param path
	* @return
	* <p>
	* author: <a href="mailto:qiande@ancun.com">QianDe</a><br>
	* create at: 2015年6月12日 下午5:38:49
	 */
	public static String getPostfix(String path) {
		if (path == null || EMPTY.equals(path.trim())) {
			return EMPTY;
		}
		if (path.contains(POINT)) {
			return path.substring(path.lastIndexOf(POINT) + 1, path.length());
		}
		return EMPTY;
	}

	// 测试
	public static void main(String[] args) throws Exception {
//		String path = "D:\\test\\2003.xls";
//		StringBuffer sb = new StringBuffer();
//		List<List<String>> dataList = readExcel(path,);
//		List<String> list = dataList.get(0);
//		//
//		for (String colName : list) {
//			sb.append("\n<colBean>\n" + "<colName>" + colName + "</colName>" + " <beanFieldName>" + "</beanFieldName>"
//					+ " <dataType>" + "String" + "</dataType>" + "\n</colBean>");
//
//			// ExcelColName col = ref.getExcelColName(colName);
//			// System.out.println(col.getBeanFieldName()+"   "+col.getDataType());
//		}
//		System.out.println(sb.toString());
	}
}