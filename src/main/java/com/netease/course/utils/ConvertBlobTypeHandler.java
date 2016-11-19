package com.netease.course.utils;

import java.io.ByteArrayInputStream;  
import java.io.UnsupportedEncodingException;  
import java.sql.Blob;  
import java.sql.CallableStatement;  
import java.sql.PreparedStatement;  
import java.sql.ResultSet;  
import java.sql.SQLException;  
  
import org.apache.commons.logging.Log;  
import org.apache.commons.logging.LogFactory;  
import org.apache.ibatis.type.BaseTypeHandler;  
import org.apache.ibatis.type.JdbcType;  
  
/** 
 * 项目名称： 
 *  
 * @description: 
 *  
 * @author spg 
 *  
 * @create_time：2014年7月24日 下午8:49:02 
 *  
 * @version V1.0.0 
 *  
 */  
public class ConvertBlobTypeHandler extends BaseTypeHandler<String>  
{  
  
    private static final Log LOGGER = LogFactory  
            .getLog(ConvertBlobTypeHandler.class);  
    // 指定字符集  
    private static final String DEFAULT_CHARSET = "utf-8";  
  
    @Override  
    public void setNonNullParameter(PreparedStatement ps, int i,  
            String parameter, JdbcType jdbcType) throws SQLException  
    {  
        ByteArrayInputStream bis;  
        try  
        {  
            // 把String转化成byte流  
            bis = new ByteArrayInputStream(parameter.getBytes(DEFAULT_CHARSET));  
        } catch (UnsupportedEncodingException e)  
        {  
            throw new RuntimeException("Blob Encoding Error!");  
        }  
        ps.setBinaryStream(i, bis, parameter.length());  
    }  
  
    @Override  
    public String getNullableResult(ResultSet rs, String columnName)  
            throws SQLException  
    {  
        Blob blob = rs.getBlob(columnName);  
        byte[] returnValue = null;  
        String result = null;  
        if (null != blob)  
        {  
            returnValue = blob.getBytes(1, (int) blob.length());  
        }  
        try  
        {  
            if (null != returnValue)  
            {  
                // 把byte转化成string  
                result = new String(returnValue, DEFAULT_CHARSET);  
            }  
        } catch (UnsupportedEncodingException e)  
        {  
            throw new RuntimeException("Blob Encoding Error!");  
        }  
        return result;  
    }  
  
    @Override  
    public String getNullableResult(CallableStatement cs, int columnIndex)  
            throws SQLException  
    {  
        Blob blob = cs.getBlob(columnIndex);  
        byte[] returnValue = null;  
        String result = null;  
        if (null != blob)  
        {  
            returnValue = blob.getBytes(1, (int) blob.length());  
        }  
        try  
        {  
            if (null != returnValue)  
            {  
                result = new String(returnValue, DEFAULT_CHARSET);  
            }  
        } catch (UnsupportedEncodingException e)  
        {  
            throw new RuntimeException("Blob Encoding Error!");  
        }  
        return result;  
    }  
  
    /** 
     * @Description: 
     *  
     * @param arg0 
     * @param arg1 
     * @return 
     * @throws SQLException 
     *  
     * @see org.apache.ibatis.type.BaseTypeHandler#getNullableResult(java.sql.ResultSet, 
     *      int) 
     *  
     */  
  
    @Override  
    public String getNullableResult(ResultSet rs, int columnName)  
            throws SQLException  
    {  
        LOGGER.debug("enter function");  
        String result = null;  
        Blob blob = rs.getBlob(columnName);  
        byte[] returnValue = null;  
        if (null != blob)  
        {  
            returnValue = blob.getBytes(1, (int) blob.length());  
        }  
        try  
        {  
            // 把byte转化成string  
            if (null != returnValue)  
            {  
                result = new String(returnValue, DEFAULT_CHARSET);  
            }  
        } catch (UnsupportedEncodingException e)  
        {  
            throw new RuntimeException("Blob Encoding Error!");  
        }  
        LOGGER.debug("exit function");  
        return result;  
  
    }  
}  