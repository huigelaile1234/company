package com.diamond.common.controller;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.io.ByteArrayInputStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

@Slf4j
@RestController
public class MyTest {

    @Autowired
    private RestTemplate restTemplate;

    @RequestMapping("getRss")
    public String getRss() throws UnsupportedEncodingException {
        setRestTemplateEncode(restTemplate);

        String result = restTemplate.getForObject("http://buy.yicai.com/xml/dycjrb/getlast.php", String.class);
        log.info("api返回结果：{}", result);
        return result;
    }

    public static void main(String[] args) throws Exception {
        RestTemplate restTemplate = new RestTemplate();
        setRestTemplateEncode(restTemplate);
        String result = restTemplate.getForObject("http://buy.yicai.com/xml/dycjrb/getlast.php", String.class);
//        String s = result.replace("<?xml version=\"1.0\" encoding=\"UTF-8\"?>", "").replaceAll("\n", "");;
//        String s1 = s.replaceAll(" ", "");
        SAXReader reader = new SAXReader();
        Document document = reader.read(new ByteArrayInputStream(result.getBytes("UTF-8")));
        Element rootElement = document.getRootElement();
        List<Element> elements = rootElement.elements();
        Flag:
        for (Element element : elements) {
            for(Iterator<Element> it = element.elementIterator(); it.hasNext();){
                Element e = it.next();
                String articleTitle = e.elementText("articleTitle");
                String subTitle = e.elementText("subTitle");
                String author = e.elementText("author");
                String content = e.elementText("content");
                System.out.println(articleTitle);
                System.out.println(subTitle);
                System.out.println(author);
                System.out.println(content);
                break Flag;
            }
        }


    }

    public static void setRestTemplateEncode(RestTemplate restTemplate) {
        if (null == restTemplate || ObjectUtils.isEmpty(restTemplate.getMessageConverters())) {
            return;
        }

        List<HttpMessageConverter<?>> messageConverters = restTemplate.getMessageConverters();
        for (int i = 0; i < messageConverters.size(); i++) {
            HttpMessageConverter<?> httpMessageConverter = messageConverters.get(i);
            if (httpMessageConverter.getClass().equals(StringHttpMessageConverter.class)) {
                messageConverters.set(i, new StringHttpMessageConverter(StandardCharsets.UTF_8));
            }
        }
    }
}
