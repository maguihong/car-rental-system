package com.maguihong.carrental;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit.jupiter.SpringExtension;


@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class CarrentalApplicationTests {
	
	@LocalServerPort
	private int port;

	@Test
	void contextLoads() {
	}
	
	@Test
	public void customerManagementTest() {
		String baseUrl = "http://localhost:" + port;
		
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpPost httpPost = new HttpPost(baseUrl + "/login");
		httpPost.setHeader("Connection", "keep-alive");
		httpPost.setHeader("Accept", "application/json");
		httpPost.setHeader("Content-Type", "application/json");
		httpPost.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/63.0.3239.132 Safari/537.36");
		List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
		params.add(new BasicNameValuePair("username", "alice"));
        params.add(new BasicNameValuePair("password", "123456"));
        
        HttpGet httpGet = new HttpGet(baseUrl + "/customers/i");
		
        CloseableHttpResponse httpPostResponse = null;
        CloseableHttpResponse httpGetResponse = null;
        try {
            httpPost.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
            httpPostResponse = httpClient.execute(httpPost);
            HttpEntity httpPostEntity = httpPostResponse.getEntity();
            System.out.println(EntityUtils.toString(httpPostEntity));
            
            httpGetResponse = httpClient.execute(httpGet);
            HttpEntity httpGetEntity = httpGetResponse.getEntity();
            System.out.println(EntityUtils.toString(httpGetEntity));
            
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                if (httpPostResponse != null) {
                	httpPostResponse.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            
            try {
                if (httpGetResponse != null) {
                	httpGetResponse.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                if (httpClient != null) {
                    httpClient.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
		
		
		
	}
	
	@Test
	public void carManagementTest() {
		
	}
	
	@Test
	public void orderOperationTest() {
		
	}

}
