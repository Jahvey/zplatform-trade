/**
 * 版权所有(C) 2012 深圳市雁联计算系统有限公司
 * 创建:guojia 2012-9-5
 */

package com.zlebank.zplatform.trade.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.http.HttpEntity;
import org.apache.http.HttpException;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;

import com.zlebank.zplatform.trade.bean.ResultBean;



/** 
 * @author guojia
 * @date 2012-9-5
 * @description：http辅助类
 */

public class HttpUtils {
	
private HttpClient httpClient = null;
    
    
    /**
     * @description 打开链接
     * @author guojia
     * @date 2012-9-5
     */
    public void openConnection(){
        HttpParams params = new BasicHttpParams();
        ThreadSafeClientConnManager threadSafeClientConnManager = new ThreadSafeClientConnManager();
        // 增加最大连接到400
        threadSafeClientConnManager.setMaxTotal(400);       
        // 增加每个路由的默认最大连接到400
        threadSafeClientConnManager.setDefaultMaxPerRoute(400);
        httpClient = new DefaultHttpClient(threadSafeClientConnManager, params); 
        httpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 60000 );
        httpClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 60000);
    }
    
    public void openQueryConnection(){
        HttpParams params = new BasicHttpParams();
        ThreadSafeClientConnManager threadSafeClientConnManager = new ThreadSafeClientConnManager();
        // 增加最大连接到400
        threadSafeClientConnManager.setMaxTotal(400);       
        // 增加每个路由的默认最大连接到400
        threadSafeClientConnManager.setDefaultMaxPerRoute(400);
        httpClient = new DefaultHttpClient(threadSafeClientConnManager, params); 
        httpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 20000 );
        httpClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 20000);
    }
    
    /**
     * @description 连接和读取时间设置
     * @param connTimeOutMillis
     * @param readTimeOutMillis 
     * @author guojia
     * @date 2012-9-5
     */
    public void openConnection(int connTimeOutMillis,int readTimeOutMillis){
        httpClient = new DefaultHttpClient();
        httpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, connTimeOutMillis);
        httpClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, readTimeOutMillis);
    }
    /**
     * @description HTTP GET 提交请求，注意服务器要将获取到的参数ISO-8859-1 转化成encoding
     * @param url
     * @param encoding
     * @return
     * @throws HttpException 
     * @author guojia
     * @date 2012-9-5
     */
    public String executeHttpGet(String url,String encoding) throws HttpException{
        try{
            HttpGet httpGet = new HttpGet(url);
            HttpResponse response = this.httpClient.execute(httpGet);
            if(HttpStatus.SC_OK==response.getStatusLine().getStatusCode()){
                return EntityUtils.toString(response.getEntity(), encoding);
            }
            return null;
        }catch (Exception e) {
            throw new HttpException("");
        }
    }
    
    
    /**
     * @description HTTP GET 提交请求并传递参数，注意服务器要将ISO-8859-1 转化成encoding
     * @param url
     * @param params
     * @param encoding
     * @return
     * @throws HttpException 
     * @author guojia
     * @date 2012-9-5
     */
    public String executeHttpGet(String url,List<HttpRequestParam> params,String encoding) throws HttpException{
        try{
            List<NameValuePair> qparams = new ArrayList<NameValuePair>();
            for(HttpRequestParam param : params){
                qparams.add(new BasicNameValuePair(param.getParaName(), param.getParaValue()));
            }
            
            String qry = URLEncodedUtils.format(qparams, encoding);
            String getUrl = url + "?" + qry;
            HttpGet httpGet = new HttpGet(getUrl);
            HttpResponse response = this.httpClient.execute(httpGet);
            if(HttpStatus.SC_OK==response.getStatusLine().getStatusCode()){
                return EntityUtils.toString(response.getEntity(), encoding);
            }
            return null;
        }catch (Exception e) {
            throw new HttpException("");
        }
    }
    
    /**
     * @description http post 提交请求
     * @param url
     * @param encoding
     * @return
     * @throws HttpException 
     * @author guojia
     * @date 2012-9-5
     */
    public String executeHttpPost(String url,String encoding) throws HttpException{
        try{
            HttpPost httpPost = new HttpPost(url);
            HttpResponse response = this.httpClient.execute(httpPost);
            if(HttpStatus.SC_OK==response.getStatusLine().getStatusCode()){
                return EntityUtils.toString(response.getEntity(), encoding);
            }
            return null;
        }catch (Exception e) {
            throw new HttpException("");
        }
    }
    
    /**
     * @description http post 提交请求，传递参数
     * @param url
     * @param params
     * @param encoding
     * @return
     * @throws HttpException 
     * @author guojia
     * @date 2012-9-5
     */
    public String executeHttpPost(String url,List<HttpRequestParam> params,String encoding) throws HttpException{
        try{
            List<NameValuePair> qparams = new ArrayList<NameValuePair>();
            for(HttpRequestParam param:params){
                qparams.add(new BasicNameValuePair(param.getParaName(), param.getParaValue()));
            }
            
            HttpPost httpPost = new HttpPost(url);
            UrlEncodedFormEntity entity = new UrlEncodedFormEntity(qparams,encoding);
            httpPost.setEntity(entity);
            
            HttpResponse response = this.httpClient.execute(httpPost);
            if(HttpStatus.SC_OK==response.getStatusLine().getStatusCode()){
                return EntityUtils.toString(response.getEntity(), encoding);
            }
            return null;
        }catch (Exception e) {
            //throw new HttpException(Constants.ERROR_SENDFAIL);
            e.printStackTrace();
        }
        return "";
    }
    
    /**
     * @description http post 提交请求，传递参数
     * @param url
     * @param params
     * @param encoding
     * @return
     * @throws HttpException 
     * @author guojia
     * @date 2012-9-5
     */
    public String exeHttpPost(String url,List<NameValuePair> params,String encoding) throws HttpException{
        try{
            HttpPost httpPost = new HttpPost(url);
            UrlEncodedFormEntity entity = new UrlEncodedFormEntity(params,encoding);
            httpPost.setEntity(entity);
            
            HttpResponse response = this.httpClient.execute(httpPost);
            if(HttpStatus.SC_OK==response.getStatusLine().getStatusCode()){
                return EntityUtils.toString(response.getEntity(), encoding);
            }
            return null;
        }catch (Exception e) {
            
            e.printStackTrace();
        }
        return "";
    }
    
    public ResultBean exeHttpPostResultBean(String url,List<NameValuePair> params,String encoding) throws HttpException{
        ResultBean resultBean = null;
        try{
            HttpPost httpPost = new HttpPost(url);
            UrlEncodedFormEntity entity = new UrlEncodedFormEntity(params,encoding);
            httpPost.setEntity(entity);
            
            HttpResponse response = this.httpClient.execute(httpPost);
            
            if(HttpStatus.SC_OK==response.getStatusLine().getStatusCode()){
                resultBean = new ResultBean(response.getStatusLine().getStatusCode()+"", EntityUtils.toString(response.getEntity(), encoding));
                return resultBean;
            }else{
                resultBean = new ResultBean(response.getStatusLine().getStatusCode()+"", "");
            }
            
            return resultBean;
        }catch (Exception e) {
            e.printStackTrace();
            resultBean = new ResultBean(HttpStatus.SC_GATEWAY_TIMEOUT+"", "超时");
        }
        return resultBean;
    }
    
    /**
     * @description 关闭链接 
     * @author guojia
     * @date 2012-9-5
     */
    public void closeConnection(){
        if(null != httpClient){
            httpClient.getConnectionManager().shutdown();
        }
    }
    
    
    /**
     * 封装HTTP POST方法
     * @param
     * @param
     * @return
     * @throws ClientProtocolException
     * @throws java.io.IOException
     */
    public static String post(String url, Map<String, String> paramMap) throws ClientProtocolException, IOException {
        HttpClient httpClient = new DefaultHttpClient();
        
        HttpPost httpPost = new HttpPost(url);
        List<NameValuePair> formparams = setHttpParams(paramMap);
        UrlEncodedFormEntity param = new UrlEncodedFormEntity(formparams, "UTF-8");
        httpPost.setEntity(param);
        
        HttpResponse response = httpClient.execute(httpPost);
        String httpEntityContent = getHttpEntityContent(response);
        httpPost.abort();
        return httpEntityContent;
    }
    /**
     * 设置请求参数
     * @param
     * @return
     */
    private static List<NameValuePair> setHttpParams(Map<String, String> paramMap) {
        List<NameValuePair> formparams = new ArrayList<NameValuePair>();
        Set<Map.Entry<String, String>> set = paramMap.entrySet();
        for (Map.Entry<String, String> entry : set) {
            formparams.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
        }
        return formparams;
    }
    /**
     * 获得响应HTTP实体内容
     * @param response
     * @return
     * @throws java.io.IOException
     * @throws java.io.UnsupportedEncodingException
     */
    private static String getHttpEntityContent(HttpResponse response) throws IOException, UnsupportedEncodingException {
        HttpEntity entity = response.getEntity();
        if (entity != null) {
            InputStream is = entity.getContent();
            BufferedReader br = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            String line = br.readLine();
            StringBuilder sb = new StringBuilder();
            while (line != null) {
                sb.append(line + "\n");
                line = br.readLine();
            }
            return sb.toString();
        }
        return "";
    }
    
    public  String executeReaPaypost(String url, Map<String, String> paramMap) throws ClientProtocolException, IOException {
        HttpPost httpPost = new HttpPost(url);
        List<NameValuePair> formparams = setHttpParams(paramMap);
        UrlEncodedFormEntity param = new UrlEncodedFormEntity(formparams, "UTF-8");
        httpPost.setEntity(param);
        
        HttpResponse response = httpClient.execute(httpPost);
        String httpEntityContent = getHttpEntityContent(response);
        httpPost.abort();
        return httpEntityContent;
    }
}