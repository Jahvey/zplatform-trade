package com.zlebank.zplatform.trade;

import java.net.URLEncoder;
import java.util.Calendar;
import java.util.Date;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import com.zlebank.zplatform.commons.utils.RSAUtils;
import com.zlebank.zplatform.trade.utils.DateUtil;

/**
 * Unit test for simple App.
 */
public class AppTest 
    extends TestCase
{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public AppTest( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( AppTest.class );
    }

    /**
     * Rigourous Test :-)
     */
    public void testApp()
    {
        assertTrue( true );
    }
    
    public static void main(String[] args) {
		/*String message= "version=v1.0|encoding=1|certId=123|frontUrl=http://192.168.101.209:8081/demo/ReciveNotifyServlet|backUrl=http://192.168.101.209:8081/demo/ReciveNotifyServlet|signMethod=01|coopInstiId=200000000000001|merId=200000000000002|merName=|merAbbr=|orderId=2016011413915708|txnType=01|txnSubType=00|bizType=000201|channelType=07|accessType=0|txnTime=20160114135345|accType=|accNo=|txnAmt=1|currencyCode=156|customerInfo=|orderTimeout=10000|payTimeout=20160115135345|reqReserved=00|reserved=|riskRateInfo=shippingFlag=000&shippingCountryCode=0&shippingProvinceCode=0&shippingCityCode=0&shippingDistrictCode=0&shippingStreet=0&commodityCategory=0&commodityName=iphone&commodityUrl=0&commodityUnitPrice=0&commodityQty=0&shippingMobile=0&addressModifyTim=0&userRegisterTime=0&orderNameModifyTime=0&userId=0&orderName=0&userFlag=0&mobileModifyTime=0&riskLevel=0&merUserId=100000000000406&merUserRegDt=0&merUserEmail=0|encryptCertId=1234|frontFailUrl=|instalTransInfo=|defaultPayType=|issInsCode=|supPayType=|userMac=|customerIp=|cardTransData=|orderDesc=";
		               //version=v1.0|encoding=1|certId=123|frontUrl=http://192.168.101.209:8081/demo/ReciveNotifyServlet|backUrl=http://192.168.101.209:8081/demo/ReciveNotifyServlet|signMethod=01|coopInstiId=200000000000001|merId=200000000000001|merName=|merAbbr=|orderId=2016011413915708|txnType=01|txnSubType=00|bizType=000201|channelType=07|accessType=0|txnTime=20160114135345|accType=|accNo=|txnAmt=1|currencyCode=156|customerInfo=|orderTimeout=10000|payTimeout=20160115135345|reqReserved=00|reserved=|riskRateInfo=shippingFlag=000&shippingCountryCode=0&shippingProvinceCode=0&shippingCityCode=0&shippingDistrictCode=0&shippingStreet=0&commodityCategory=0&commodityName=iphone&commodityUrl=0&commodityUnitPrice=0&commodityQty=0&shippingMobile=0&addressModifyTim=0&userRegisterTime=0&orderNameModifyTime=0&userId=0&orderName=0&userFlag=0&mobileModifyTime=0&riskLevel=0&merUserId=100000000000406&merUserRegDt=0&merUserEmail=0|encryptCertId=1234|frontFailUrl=|instalTransInfo=|defaultPayType=|issInsCode=|supPayType=|userMac=|customerIp=|cardTransData=|orderDesc=
		String priKey="MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAKVocXcH0w9+dHyM76SowsBRDrwrKw5hMPdCILnt279W7QTIokSfzUquJYevhaUFPXKJGF3JcIwh/23rRVmdHTWlr6gYE8lD1MhvEqMd1CjUirQqrmW3iKyoTLSuZVsxKNqXn19fpM7u2PWBbW9US5QmlLCjBqWtby0QMf1Lk1/JAgMBAAECgYAS/V6pb7iHetkcw4FvwgA53BR4/eu/huD8QhdHwUsNOImVlwMqJb6H6F5eqYQKYgOf9qVZfxrwu6Z7lBsuIp+LrOs01liUKxshW3OiC9fIRsqgmRDi0gX4YHi1TTU4OO1S/O6OKNr0YKj+Of3p5XMESJFhP4M0/EeGqVfqTGxKAQJBAPz7X43UYC1RQuRsOHTKeNeM3Dx3q0IXyzXImopoXGGvusIchcGd++N7H101Ic9cWk001dLQf5CBdrm+VXkhhdkCQQCnYZy7VLE7AweQMkY0NCVaZFiNq4EQBWr1ipMQ/CFOZliCkzZ0wEilYfUR+mHRzHZKakXBf1pBhX9TGQbOi8NxAkEAzHqyJYV/p6GDG/aNO3lca32jlgx0FJv+2J4oq67m3N6ZacR8KJ91tc0S8Ne9WEohgz4thpSYR0kdmczKcXozIQJAPurvWm+Ui2UxI5/ySc9MNzU/IEnWouoWcaQccicEcg8AuFyT/MEGzbwLvi1LNezQjlpyTDEOAVEoMllW+K8K0QJAaJ+eP3iZq7INzLbGUc3MyPb/2yKI4eOtS2/kPYbBs+RqrGyv5B159s8X6cCL46K3zs5gbOlTmWdCDLO6Og8OMw==";
		try {
			String signMsg = RSAUtils.sign(URLEncoder.encode(message,"utf-8").toString().getBytes(), priKey);
			
			System.out.println(signMsg.equals("pHX6HRhxYvUAC8MXmEiirUl5Hs6PcBMgSAbdqmLbLKgQdJMaL2zM3O8XGHzFYRFrpDJjJdOrs93N+0tCydGKhaGaNFPnPbIQVXxDUTt6J8zSwiU+17nR4cDSHM9JnHS6Jk0WEQCXK2S7lKQgmMfkWQ5q7AoCdQV42vHghvtEahI="));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
    	Date failureDateTime = DateUtil.skipDateTime(new Date(), 1);//失效的日期
    	Calendar first_date = Calendar.getInstance();
		first_date.setTime(new Date());
		Calendar d_end = Calendar.getInstance();
		d_end.setTime(failureDateTime);
		System.out.println(DateUtil.formatDateTime(DateUtil.SIMPLE_DATE_FROMAT, new Date()));
		System.out.println(DateUtil.formatDateTime(DateUtil.SIMPLE_DATE_FROMAT, failureDateTime));
		
		System.out.println(DateUtil.calendarCompare(first_date, d_end));
    }
}