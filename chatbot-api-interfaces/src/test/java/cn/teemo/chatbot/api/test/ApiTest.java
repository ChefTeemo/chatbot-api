package cn.teemo.chatbot.api.test;

import cn.hutool.http.HttpUtil;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicHeader;
import org.apache.http.util.EntityUtils;
import org.junit.Test;

import javax.swing.text.html.parser.Entity;
import java.io.IOException;
import java.util.Arrays;

import org.apache.http.client.methods.HttpOptions;
import org.springframework.http.HttpHeaders;

/**
 * author:yahaha
 * version:1.1
 * Date:2023/11/26
 * Theme: 单元测试
 */
@SuppressWarnings({"all"})
public class ApiTest {
    
    //Teemo备注：
    // 现在网站刷新会先执行一个和目标地址一模一样的预检地址，但方法是OPTIONS，返回成功SC_OK == 200,然后再自动访问目标地址
    // 先放着，无法解决
    
    
    @Test
    public void querry_unanswered_questions() throws IOException {
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        HttpGet get = new HttpGet("https://api.zsxq.com/v2/groups/51112854584444/topics?scope=unanswered_questions&count=20");
        //遇到的问题01：cookie不显示：将F12恢复默认设置，一切都正常了，包括preview也可以查看了
       
        get.addHeader("cookie", "zsxq_access_token=B48541DE-E0B9-2964-8579-6AA5F718B28C_0565AC5F71712128; abtest_env=product; zsxqsessionid=13d572bb6dfeef3c1238a2e16af5c67f");
        get.addHeader("Content-Type", "application/json; charset=UTF-8");
        
        CloseableHttpResponse response = httpClient.execute(get);
        if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
            String resEnti = EntityUtils.toString(response.getEntity());
            System.out.println(resEnti);
        } else {
            System.out.println(response.getStatusLine().getStatusCode());
        }
    }
    
    
    
    @Test
    public void answer() throws IOException {
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        //遇到的问题03：false, 原因：51112854584444是group_id
        HttpPost post = new HttpPost("https://api.zsxq.com/v2/topics/188122551414522/answer");
        post.addHeader("cookie", "zsxq_access_token=B48541DE-E0B9-2964-8579-6AA5F718B28C_0565AC5F71712128; abtest_env=product; zsxqsessionid=367e088676104f311be4cd89e7089fdd");
        post.addHeader("Content-Type", "application/json; charset=UTF-8");
        //编辑要回答的数据格式
        String paramJson = "{\n" +
                "  \"req_data\": {\n" +
                "    \"text\": \"都别问！\\n\",\n" +
                "    \"image_ids\": [],\n" +
                "    \"silenced\": true\n" +
                "  }\n" +
                "}";
        
        StringEntity stringEntity = new StringEntity(paramJson, ContentType.create("text/json", "UTF-8"));
        post.setEntity(stringEntity);
        
        CloseableHttpResponse response = httpClient.execute(post);
        if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
            String resEnti = EntityUtils.toString(response.getEntity());
            System.out.println(resEnti);
        } else {
            System.out.println(response.getStatusLine().getStatusCode());
        }
       
    }

}

