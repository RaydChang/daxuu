/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Framework.Xml;

import Framework.Data.DbHelper;
import Framework.logger.MyLogger;
import java.sql.*;
//文件包
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileWriter;
//工具包
import java.util.Iterator;
import java.util.List;
//dom4j包
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

/**
 *
 * @author 
 * @date 2010/3/29, 下午 02:41:58
 */
public class XmlDocModel {

    private final String ROOT_NAME = "xconfig";
    boolean _empty = true;
    boolean _changed = false;
    Document _document = null;
    //Constructor

    public XmlDocModel() {
        Document doc = null;
        String root = "<?xml version=\"1.0\" encoding=\"utf-8\"?><%1$s></%1$s>";
        String s = String.format(root, ROOT_NAME);

        try {
            doc = DocumentHelper.parseText(s);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        this.setDocument(doc);
    }

    public XmlDocModel(String argContent) {
        Document doc = null;
        String root = "<?xml version=\"1.0\" encoding=\"utf-8\"?><%1$s>%2$s</%1$s>";
        String s = String.format(root, ROOT_NAME, argContent);

        try {
            doc = DocumentHelper.parseText(s);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        this.setDocument(doc);
    }

    public boolean Save(Document document, String fileName) {
        boolean flag = true;
        try {
            /* 将document中的内容写入文件中 */
            //默认为UTF-8格式"
            OutputFormat format = OutputFormat.createPrettyPrint();
            format.setEncoding("utf-8");
            XMLWriter writer = new XMLWriter(new FileWriter(new File(fileName)), format);
            writer.write(document);
            writer.close();
        } catch (Exception ex) {
            flag = false;
            ex.printStackTrace();
        }
        return flag;
    }

    /*
    getColumnName:USER_ID
    getColumnType：1
    getColumnTypeName：CHAR
    getColumnDisplaySize：10
    getCatalogName：
    getColumnClassName：java.lang.String
    getColumnLabel：USER_ID
    getSchemaName：
    getPrecision：10
    getScale：0
    getTableName：
    getCatalogName：
    isReadOnly：false
    isWritable：true
    isDefinitelyWritable：false

    MyLogger.Write(
    " getColumnName:" + rsmd.getColumnName(i)
    + " getColumnType：" + rsmd.getColumnType(i)
    + " getColumnTypeName：" + rsmd.getColumnTypeName(i)
    + " getColumnDisplaySize：" + rsmd.getColumnDisplaySize(i)
    + " getCatalogName：" + rsmd.getCatalogName(i)
    + " getColumnClassName：" + rsmd.getColumnClassName(i)
    + " getColumnLabel：" + rsmd.getColumnLabel(i)
    + " getSchemaName：" + rsmd.getSchemaName(i)
    + " getPrecision：" + rsmd.getPrecision(i)
    + " getScale：" + rsmd.getScale(i)
    + " getTableName：" + rsmd.getTableName(i)
    + " getCatalogName：" + rsmd.getCatalogName(i)
    + " isReadOnly：" + rsmd.isReadOnly(i)
    + " isNullable：" + rsmd.isNullable(i)
    + " isWritable：" + rsmd.isWritable(i)
    + " isDefinitelyWritable：" + rsmd.isDefinitelyWritable(i));
     */
    public XmlDocModel CreateSchema(String dsn, String dbName)
    {
        /** 建立document对象 */
        Statement st = null;
        ResultSet rs = null;
        Connection conn = null;

        String sql = String.format("select * from %1$s where 1>1", dsn);

        //ResultSet rs;
        Document doc = new XmlDocModel().getDocument();
        try {

            conn = DbHelper.createConnection(dbName);
            st = conn.createStatement();
            rs = st.executeQuery(sql);

            ResultSetMetaData rsmd = rs.getMetaData();
            DatabaseMetaData dbmd = conn.getMetaData();
            ResultSet rsKeys = dbmd.getPrimaryKeys(null, null, dsn);
            int cols = rsmd.getColumnCount(); //得到数据集的列数
            Element tableElement = doc.getRootElement().addElement(dsn.toLowerCase());
            for (int i = 1; i <= cols; i++) {

                if (rsmd.getColumnName(i) != null) {
                    Element colElement = tableElement.addElement(rsmd.getColumnName(i).toLowerCase());
                    //s="50" d="system.string" n="true" k=""
                    colElement.addAttribute("s", String.format("%1$s", rsmd.getColumnDisplaySize(i)));
                    colElement.addAttribute("d", String.format("%1$s", rsmd.getColumnType(i)));
                    colElement.addAttribute("n", rsmd.isNullable(i) == 0 ? "false" : "true");
                    colElement.addAttribute("k", (isPrimaryKey(rsKeys, rsmd.getColumnName(i)) ? "true" : "false"));
                }
            }
            conn.close();
        } catch (SQLException exSql) {
            MyLogger.Write(exSql.getMessage());
            exSql.printStackTrace();
        }finally{

            //conn.close();
        }
        //MyLogger.Write(doc.asXML());

        Save(doc, "config//" + dsn + ".xml");

        return this;
    }

//判斷是否為Primary Key欄
    private boolean isPrimaryKey(ResultSet rsKeys, String fldName) throws SQLException {
        boolean ret = false;
        int i =1;
         //MyLogger.Write(rsKeys.);
        while (rsKeys.next()) {
           
            if (rsKeys.getString("column_name").toLowerCase() == fldName.toLowerCase()) {
                ret = true;
            }
            i++;
        }
        return ret;
    }

    /**
     * load
     * 载入一个xml文档
     * @return 成功返回Document对象，失败返回null
     * @param filename  文件路径
     */
    public Document load(String filename) {
        Document doc = null;
        try {
            SAXReader saxReader = new SAXReader();
            doc = saxReader.read(new File(filename));
        } catch (Exception ex) {
            //ex.printStackTrace();
        }
        this.setDocument(doc);
        return doc;
    }
    
    
    /**
     * load
     * 载入一个xml格式字串
     * @return 成功返回Document对象，失败返回null
     * @param s_content  xml格式字串
     */
    public Document loadXml(String s_content) {
    	byte[]  byteArrResponse = s_content.getBytes();
        Document doc = null;
        try {
            String strArrResponse = new String(byteArrResponse);  
            doc = DocumentHelper.parseText(strArrResponse); 
        } catch (Exception ex) {
            //ex.printStackTrace();
        }
        this.setDocument(doc);
        return doc;
    }
    
    /*
    <?xml version="1.0" encoding="utf-8"?>
    <xconfig>
    <cond>
    <item_id />
    <item_pid />
    <status />
    <proj_id />
    <file_id />
    <item_kind />
    <item_title />
    <item_content />
    <item_puter />
    <item_put_time />
    <item_plan_time />
    <item_handler />
    <item_real_time />
    <dataer />
    <datatime />
    </cond>
    <records>
    <record ischanged="0">
    <item_id>1</item_id>
    <item_pid>1</item_pid>
    <status>1</status>
    <proj_id>1</proj_id>
    <file_id>1</file_id>
    <item_kind>1.00000</item_kind>
    <item_title>點“停用通知”會引起OS報錯</item_title>
    <item_content>1234567890</item_content>
    <item_puter>張瑞田</item_puter>
    <item_put_time>2009-04-10</item_put_time>
    <item_plan_time>2009-04-10</item_plan_time>
    <item_handler>向澤安</item_handler>
    <item_real_time>2009-04-10</item_real_time>
    <dataer>1</dataer>
    <datatime>2009/4/2 上午 09:53:22</datatime>
    </record>
    </records>
    </xconfig>

     */

    public Document ToXml(ResultSet rs) throws SQLException {
        String _COND = "cond";
        String _RECORDS = "records";
        String _REDORD = "redord";

        /** 建立document对象 */
        Document doc = this.getDocument();
        ResultSetMetaData rsmd = rs.getMetaData();
        int cols = rsmd.getColumnCount(); //得到数据集的列数
        Element tableElement = null;
        Element colElement;
//        tableElement = doc.getRootElement().addElement(_COND);

        //COND NODE
//        for (int i = 1; i <= cols; i++) {
//            if (rsmd.getColumnName(i) != null) {
//                 colElement = tableElement.addElement(rsmd.getColumnName(i).toLowerCase());
//
//            }
//        }//end for
        //RECORDS
        Element recordsElement = doc.getRootElement().addElement(_RECORDS);
        //every row
        while (rs.next()) {
            tableElement = recordsElement.addElement(_REDORD);
            tableElement.addAttribute("onchanged", "0");
            //every col
            for (int i = 1; i <= cols; i++) {
                if (rsmd.getColumnName(i) != null) {
                    colElement = tableElement.addElement(rsmd.getColumnName(i).toLowerCase());

                    if (rs.getString(i) != null) {
                        colElement.setText(rs.getString(i));
                    }
                }
            }//end for
        }//end while
        //MyLogger.Write(doc.asXML());
        this.setDocument(doc);
        return doc;
    }

    public boolean isEmpty() {
        return _empty;
    }

    public boolean isChanged() {
        return _changed;
    }

    public Document getDocument() {
        return _document;
    }

    public void setDocument(Document _document) {
        this._document = _document;
    }
}
