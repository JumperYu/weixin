package com.hoolai.web;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.hoolai.po.AutoReply;
import com.hoolai.service.WeixinService;
import com.hoolai.util.Exceptions;
import com.hoolai.util.MessageConverter;
import com.hoolai.util.MessageValidater;

/**
 * LoginController
 * 
 * 微信后台管理 2014-05
 * 
 * @author zxm
 */
@Controller
public class WeixinController {

	private static Logger log = LoggerFactory.getLogger(WeixinController.class);

	private WeixinService weixinService;

	// 处理匹配请求 如login index input list
	@RequestMapping(value = "{uri}")
	public String getUrl(@PathVariable String uri) {
		return uri;
	}

	@RequestMapping(value = "/input")
	public String doInput(@ModelAttribute AutoReply reply,
			Map<String, Object> context) {
		if (reply.getId() != null) {
			context.putAll(weixinService.getReplyById(reply.getId()));
			System.out.println(context);
		}
		return "input";
	}

	// 响应微信回调请求,根据请求内容发送对应信息
	@RequestMapping(value = "/callback", method = RequestMethod.POST)
	@ResponseBody
	public String callback(HttpServletRequest req) throws IOException {
		// To User's message
		String echo = "";

		// --> 1.receive msg(xml)
		String xmlData = MessageConverter.readFromInputStream(
				req.getInputStream(), req.getContentLength());
		Map<String, Object> fromMsg = MessageConverter.toMapFromXML(xmlData);

		// --> 2.validate xml and transform
		if (MessageValidater.correctMsgType(fromMsg)) {
			String key = fromMsg.get("Content").toString();
			List<Map<String, Object>> replys = weixinService.getAutoMsg(key
					.trim());
			if (replys != null && replys.size() > 0) {
				echo = MessageConverter.toStringFromMap(fromMsg, replys.get(0));
			} else {
				log.info("不知道你说什么");
			}
		}// --> End of db
		log.info(echo + "\n");
		return echo;
	}

	// 配置关键字和回复内容
	@RequestMapping(value = "/textReply", method = RequestMethod.POST)
	@ResponseBody
	public String textReply(@Valid @ModelAttribute AutoReply autoReply,
			BindingResult result) {

		if (result.hasErrors()) {
			System.out.println("has error");
			return "配置发生错误";
		}

		if (autoReply.getId() == null) {// -->> save in database.
			weixinService.addReply(autoReply.getKeyWord(), autoReply.getKeyType(), autoReply.getReply());
			return "配置成功";
		} else {
			return "配置失败";
		}
	}

	// 配置关键字和回复内容
	@RequestMapping(value = "/imageReply", method = RequestMethod.POST)
	public String imageReply(@RequestParam("keyWord") String keyWord,
			@RequestParam("keyType") String keyType,
			@RequestParam("file") MultipartFile file,
			@RequestParam("reply") String reply) {

		log.info(keyWord + "|" + keyType + "|" + reply);

		try {
			InputStream in = file.getInputStream();
			FileOutputStream out = new FileOutputStream("d:/"
					+ file.getOriginalFilename());
			IOUtils.copy(in, out);
			IOUtils.closeQuietly(in);
			IOUtils.closeQuietly(out);
		} catch (IOException e) {
			Exceptions.unchecked(e);
		}
		return "redirect:list";
	}

	// 返回查询所有回复的JSON结果
	@RequestMapping(value = "/listAll")
	@ResponseBody
	public List<Map<String, Object>> listAllReply() {
		return weixinService.getAllReply();
	}

	// 执行删除操作
	@RequestMapping(value = "/delete")
	@ResponseBody
	public String doDelete(@RequestParam String id) {
		boolean result = weixinService.deleteOneReply(Integer.valueOf(id));
		if (result)
			return "删除成功";
		else
			return "删除失败";
	}

	// -->> Get&Set
	@Resource
	public void setWeixinService(WeixinService weixinService) {
		this.weixinService = weixinService;
	}

}
