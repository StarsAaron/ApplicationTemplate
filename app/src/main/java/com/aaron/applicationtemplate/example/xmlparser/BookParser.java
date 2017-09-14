package com.aaron.applicationtemplate.example.xmlparser;


import com.aaron.applicationtemplate.data.local.entity.Book;

import java.io.InputStream;
import java.util.List;


/**
 * XML文件操作接口
 * 数据解析
 *
 * @author zhrjian
 */
public interface BookParser {
    /**
     * 从xml文件获取Book列表
     *
     * @param in
     * @return
     */
    public List<Book> parse(InputStream in) throws Exception;

    /**
     * 把Book列表序列化成xml文件
     *
     * @param booksList
     * @return
     */
    public String serialize(List<Book> booksList) throws Exception;
}
