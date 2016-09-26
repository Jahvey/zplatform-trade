package org.zplatform.cmbc;

import junit.framework.TestCase;

import com.alibaba.fastjson.JSON;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.thoughtworks.xstream.io.xml.XmlFriendlyNameCoder;
import com.zlebank.zplatform.trade.cmbc.bean.RealNameAuthResultBean;

/**
 * Unit test for simple App.
 */
public class AppTest extends TestCase {

    public static void main(String[] args) {
        String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><Ans><Version></Version><SettDate></SettDate><TransTime></TransTime><ReqSerialNo></ReqSerialNo><ExecType>E</ExecType><ExecCode>000097</ExecCode><ExecMsg>域[合作方流水(合作方请求流水号)]的值超出最大长度[16]</ExecMsg><ValidateStatus></ValidateStatus><PaySerialNo></PaySerialNo><Resv></Resv></Ans>";
        XStream xstream = new XStream(new DomDriver(null,new XmlFriendlyNameCoder("_-", "_")));
        xstream.processAnnotations(RealNameAuthResultBean.class);
        xstream.autodetectAnnotations(true);
        RealNameAuthResultBean resultBean =  (RealNameAuthResultBean) xstream.fromXML(xml);
        System.out.println(JSON.toJSONString(resultBean));
    }
}
