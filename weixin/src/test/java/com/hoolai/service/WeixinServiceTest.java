package com.hoolai.service;

import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class) 
@ContextConfiguration("/config/applicationContext**.xml") 
public class WeixinServiceTest {
	
	Logger log = LoggerFactory.getLogger(WeixinService.class);
	
	@Resource
	private WeixinService weixinService;
	
	@Test
	public void testPattern(){
		String str = " ";
		System.out.println(Pattern.matches("^\\s*$", str));
	}
	
	@Test
	public void testSQL(){
		List<Map<String, Object>> result = weixinService.getAutoMsg("12");
		log.info(result.toString());
	}
	
	@Test
	public void testUpdate(){
		weixinService.addReply("4", "text", "haa");
	}
	
	@Test
	public void testDelete(){
		System.out.println(weixinService.deleteOneReply(1));
	}
	
	@Test
	public void testFind(){
		System.out.println(weixinService.getReplyById(22));
	}
}
