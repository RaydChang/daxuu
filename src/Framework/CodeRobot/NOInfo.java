/**
 * 
 */
package Framework.CodeRobot;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import Framework.Xml.*;
//dom4j包
import Framework.logger.MyLogger;
import java.util.*;
//import javax.swing.text.html.HTMLDocument.*;
import java.sql.SQLException;

import javax.swing.text.html.HTMLDocument.HTMLReader.PreAction;
//import java.util.*;
import org.dom4j.*;

/**
 * 
 * 项目名称：Framework 类名称：NOInfo 类描述：编码器
 * 
 * @author rayd 创建时间：Jun 7, 2013 2:44:44 PM 修改人：rayd 修改时间：Jun 7, 2013 2:44:44 PM
 *         修改备注： 待加强功能 1.保存XML文件用Cache，减少读写硬盘的次数 2.
 * @version
 * 
 */
public class NOInfo {

	/**
	 * 
	 */
	public NOInfo() {
		// TODO Auto-generated constructor stub
	}

	private int maxValue = 0;// 最大ID
	private int nextValue = 0;// 下一ID
	private boolean needBegin = false;// 是否從開始重新計數

	private int poolSize = 0;// Pool size
	private String name;// 取得Code的識別名
	private String nextCode;// 編碼結果

	public int getMaxValue() {
		return maxValue;
	}

	public void setMaxValue(int maxValue) {
		this.maxValue = maxValue;
	}

	public int getNextValue() {
		return nextValue;
	}

	public void setNextValue(int nextValue) {
		this.nextValue = nextValue;
	}

	public boolean isNeedBegin() {
		return needBegin;
	}

	public void setNeedBegin(boolean needBegin) {
		this.needBegin = needBegin;
	}

	public int getPoolSize() {
		return poolSize;
	}

	public void setPoolSize(int poolSize) {
		this.poolSize = poolSize;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNextCode() {
		return nextCode;
	}

	public void setNextCode(String nextCode) {
		this.nextCode = nextCode;
	}

	/**
	 * 
	 * 功能概述：
	 * 
	 * @param s_filepath
	 *            編碼規則的XML文件路徑
	 * @param i_count
	 *            批量取得，
	 * @return 编码值,若i_count大于1，则返回以逗号（“，”）分隔的多个值
	 * @author rayd 创建时间：Jun 7, 2013 2:56:57 PM 修改人：rayd 修改时间：Jun 7, 2013
	 *         2:56:57 PM 修改备注：
	 * @version
	 * 
	 */
	public String MakeCode(String s_filepath, int i_count) {
		String ret = "";
		XmlDocModel xCode = new XmlDocModel();
		xCode.load(s_filepath);
		StringBuilder sbCode = new StringBuilder();
		// String[] values = new String[10];
		int i = 0;
		// String code = "";
		String newValue = "";
		Element el;
		String kind = "";
		String oldValue = "";
		String pre = "";
		String nextVal = "";

		DecimalFormat df = new DecimalFormat();
		Document doc = xCode.getDocument();
		Element root = doc.getRootElement();

		for (Iterator<?> it = root.elementIterator(); it.hasNext();) {
			el = (Element) it.next();
			kind = el.getText().toLowerCase();
			if (kind.equalsIgnoreCase("$date")) {

				oldValue = el.attribute("value").getValue();
				// format 格式参考JDK API 的 java.text.SimpleDateFormat类
				if (el.attribute("format") != null) {
					newValue = formatDate(el.attribute("format").getValue());
				} else {
					newValue = formatDate(el.attribute("yyyyMMdd").getValue());
				}
				// 若當前日期與記錄值不一致，則返回當前值，並要更新
				// 若兩個值不等，則更新為新值。
				if (!oldValue.equals(newValue)
						&& el.attribute("needbegin") != null
						&& el.attribute("needbegin").getValue().equals("1")) {
					this.needBegin = true;
					oldValue = newValue;
					el.attribute("value").setValue(newValue);
				}
				// values[i] = newValue;
				sbCode.append(newValue);

			} else if (kind.equalsIgnoreCase("$serial")) {
				int currentValue = Integer.parseInt(el.attribute("value")
						.getValue());

				if (this.needBegin) {
					this.nextValue = 1;
					this.needBegin = false;
				}else{
					// 將下一個值設定成當前值
					this.nextValue = currentValue;
				}
				for (int j =1 ;j<=i_count ;j++) {
					
					if (el.attribute("format") != null) {
						df = new DecimalFormat(el.attribute("format").getValue());
					}
					nextVal = el.attribute("format") != null?df.format(this.nextValue + j):Integer.toString(this.nextValue + j);
					
					if (j==1) {
						sbCode.append(df.format(this.nextValue + j));
						pre = sbCode.toString();
					}else{
						sbCode.append(",");
						sbCode.append(pre);
						sbCode.append(df.format(this.nextValue + j));
					}

				}
				//加上共本次取出数量
				this.nextValue = currentValue + i_count;
				el.attribute("value").setValue(Integer.toString(this.nextValue));
				
			} else {
				sbCode.append(kind);
			}
		}
		//保存文件
		xCode.Save(doc, s_filepath);
		return sbCode.toString();
	}

	/**
	 * 
	 * 功能概述：格式化日期
	 * 
	 * @param s_format
	 *            格式样式字串
	 * @return 格式化日期后的日期，若有异常返回错误
	 * @author rayd 
	 * @创建时间：Jun 7, 2013 2:54:10 PM
	 * @修改人：rayd 修改时间：Jun 7, 2013 2:54:10 PM 
	 * @note：
	 * @version
	 * 
	 */
	public String formatDate(String s_format) {
		String ret = "";
		String defaultFormat = "yyMMdd";
		SimpleDateFormat sFormat = new SimpleDateFormat(s_format);
		try {
			ret = sFormat.format(System.currentTimeMillis());
		} catch (NullPointerException e) {
			sFormat = new SimpleDateFormat(defaultFormat);
			ret = sFormat.format(System.currentTimeMillis());
			e.printStackTrace();
		} catch (IllegalArgumentException e2) {
			sFormat = new SimpleDateFormat(defaultFormat);
			ret = sFormat.format(System.currentTimeMillis());
			e2.printStackTrace();
		}
		return ret;
	}

}
