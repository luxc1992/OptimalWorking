package com.yst.onecity.utils;

import com.yst.onecity.bean.CityBean;
import com.yst.onecity.bean.DiQuBean;
import com.yst.onecity.bean.ProvinceBean;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * 解析本地地址xml文件
 *
 * @author jiaofan
 * @version 1.0.1
 * @date 2018/2/25
 */

public class ParserTool {
    public static List<ProvinceBean> parserProvince(InputStream in) throws XmlPullParserException, IOException
    {

        List<ProvinceBean> provs = null;

        ProvinceBean prov =null;
        List<CityBean> citys = null;

        CityBean city =null;
        List<DiQuBean> diQus =null;

        DiQuBean diQu=null;
        String tagName=null;

        XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
        XmlPullParser parser = factory.newPullParser();

        parser.setInput(in,"utf-8");

        int event = parser.getEventType();
        while(event!=XmlPullParser.END_DOCUMENT)
        {
            switch (event)
            {
                case XmlPullParser.START_DOCUMENT:
                    provs = new ArrayList<ProvinceBean>();
                    break;
                case XmlPullParser.START_TAG:
                    tagName=parser.getName();
                    if("p".equals(tagName))
                    {
                        prov=new ProvinceBean();
                        citys = new ArrayList<CityBean>();
                        int count =parser.getAttributeCount();
                        for(int i=0;i<count;i++)
                        {
                            String attName =parser.getAttributeName(i);
                            String attValue = parser.getAttributeValue(i);
                            if("p_id".equals(attName))
                            {
                                prov.setId(attValue);
                            }
                        }
                    }
                    else if("pn".equals(tagName))
                    {
                        prov.setName(parser.nextText());
                    }
                    else if("c".equals(tagName))
                    {
                        city = new CityBean();
                        diQus = new ArrayList<DiQuBean>();
                        int count =parser.getAttributeCount();
                        for(int i=0;i<count;i++)
                        {
                            String attName =parser.getAttributeName(i);
                            String attValue = parser.getAttributeValue(i);
                            if("c_id".equals(attName))
                            {
                                city.setId(attValue);
                            }
                        }
                    }
                    else if("cn".equals(tagName))
                    {
                        city.setName(parser.nextText());
                    }
                    else if("d".equals(tagName))
                    {
                        diQu = new DiQuBean();
                        int count =parser.getAttributeCount();
                        for(int i=0;i<count;i++)
                        {
                            String attName =parser.getAttributeName(i);
                            String attValue = parser.getAttributeValue(i);
                            if("d_id".equals(attName))
                            {
                                diQu.setId(attValue);
                            }
                        }
                        diQu.setName(parser.nextText());
                        diQus.add(diQu);
                    }
                    break;
                case XmlPullParser.END_TAG:
                    tagName =parser.getName();
                    if("c".endsWith(tagName))
                    {
                        city.setDiQus(diQus);
                        citys.add(city);
                    }
                    else if("p".equals(tagName))
                    {
                        prov.setCitys(citys);
                        provs.add(prov);
                    }
                    break;
                default:
                    break;
            }
            event = parser.next();
        }
        return provs;
    }
}
