package com.zlebank.zplatform.wechat.wx;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.KeyStore;

import javax.net.ssl.SSLContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContexts;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import com.zlebank.zplatform.wechat.wx.common.WXConfigure;


/**
 * 
 * 微信双向认证工具类
 * 
 *  @see https://pay.weixin.qq.com/wiki/doc/api/app/app.php?chapter=4_3
 */
public class SSLSender {
    
    private static final Log log = LogFactory.getLog(SSLSender.class);

    
    public static String sendXml(String xml) {
        StringBuffer rtnXml = new StringBuffer(); // 返回结果
        CloseableHttpClient httpclient = null;
        FileInputStream instream = null;
        try {
            // 加载PKCS12证书（可以从微信商户里下载）
            KeyStore keyStore  = KeyStore.getInstance("PKCS12");
            instream = new FileInputStream(new File(WXConfigure.getCerUrl()));
            // 根据密码加载证书
            keyStore.load(instream, WXConfigure.getMchid().toCharArray());
            // SSLContext
            SSLContext sslcontext = SSLContexts.custom().loadKeyMaterial(keyStore, WXConfigure.getMchid().toCharArray()).build();
            SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory( sslcontext, new String[] { "TLSv1" }, null, SSLConnectionSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);
            // 初始化httpclient
            httpclient = HttpClients.custom().setSSLSocketFactory(sslsf).build();
            // 初始化POST提交 
            log.info("【发送给微信退款的url为】"+WXConfigure.REFUND_URL);
            HttpPost httppost = new HttpPost(WXConfigure.REFUND_URL);
            HttpEntity requestEntity = new StringEntity(xml,ContentType.APPLICATION_XML);
            httppost.setEntity(requestEntity);
            if (log.isDebugEnabled()) {
                log.debug("【退款发送的报文如下】");
                log.debug(xml);
            }
            // 执行
            CloseableHttpResponse response = httpclient.execute(httppost);
            try {
                HttpEntity entity = response.getEntity();
                if (log.isDebugEnabled()) {
                    log.debug("【退款收到的状态如下】");
                    log.debug(response.getStatusLine());
                }
                if (entity != null) {
                    System.out.println("Response content length: " + entity.getContentLength());
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(entity.getContent()));
                    String text;
                    while ((text = bufferedReader.readLine()) != null) {
                        rtnXml.append(text);
                    }
                    if (log.isDebugEnabled()) {
                        log.debug("【退款收到的内容如下】");
                        log.debug(rtnXml.toString());
                    }
                }
                EntityUtils.consume(entity);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                response.close();
            }
        }catch (Exception e) {
            e.printStackTrace();
        }  finally {
            try {
            	if (httpclient != null)  httpclient.close();
                if( instream!= null ) instream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return rtnXml.toString();
    }

}
