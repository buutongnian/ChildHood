package edu.buu.childhood.util;

import java.io.File;  
import java.io.FileInputStream;  
import java.io.FileNotFoundException;
import java.io.IOException;  
import java.util.Properties;
/** 
 * @note 读取properties文件工具类 
 */  
public abstract class ProperUtil {  
      
	/** 
     * @note 获取指定路径下property文件中的某个字段(没有默认值) 
     * @param key 字段 
     * @param filePath 属性文件 
     * @return 与key对应的value 
     * @throws Exception 
     */   
    public static String getProperties(String key,String filePath) throws Exception{  
        File file = new File(filePath);  
        if(!file.exists()){
        	throw new Exception("Property file doesn't exist,please create!"+filePath);
        }
        Properties props = new Properties();  
        props.load(new FileInputStream(file));  
        return props.getProperty(key);  
    }  
      
    /** 
     * @note 获取指定路径下property文件中的某个字段(可以设置默认值) 
     * @param key 字段 
     * @param value 默认值 
     * @param filePath 属性文件 
     * @return 与key对应的value 
     * @throws FileNotFoundException if property file doesn't exists 
     * @throws IOException if there is some exception when load from property file 
     */  
    public static String getProperties(String key,String defaultValue,String filePath) throws FileNotFoundException, IOException{  
        File file = new File(filePath);  
        Properties props = new Properties();  
        props.load(new FileInputStream(file));  
        return props.getProperty(key,defaultValue);  
    }  
}  