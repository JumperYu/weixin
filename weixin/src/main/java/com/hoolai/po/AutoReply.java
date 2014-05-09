package com.hoolai.po;

import org.hibernate.validator.constraints.NotEmpty;

public class AutoReply {
	
	private Integer id;
	@NotEmpty
	private String keyWord;
	@NotEmpty
	private String keyType;   // text image news
	private String reply;     
	private String title;     // 图片消息标题
	private String imageDesc; // 图片描述
	private String picUrl;	  // 图片资源地址
	private String linked;    // 图片跳转

	public AutoReply() {
	}

	// -->> get/set
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getKeyWord() {
		return keyWord;
	}

	public void setKeyWord(String keyWord) {
		this.keyWord = keyWord;
	}
	
	@NotEmpty
	public String getKeyType() {
		return keyType;
	}

	public void setKeyType(String keyType) {
		this.keyType = keyType;
	}

	public String getReply() {
		return reply;
	}

	public void setReply(String reply) {
		this.reply = reply;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getImageDesc() {
		return imageDesc;
	}

	public void setImageDesc(String imageDesc) {
		this.imageDesc = imageDesc;
	}

	public String getPicUrl() {
		return picUrl;
	}

	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}

	public String getLinked() {
		return linked;
	}

	public void setLinked(String linked) {
		this.linked = linked;
	}
	
	
}
