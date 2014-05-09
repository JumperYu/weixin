package com.hoolai.service;

import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import javax.annotation.Resource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class WeixinService {

	private JdbcTemplate jdbcTemplate;

	@Resource
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	/**
	 * 匹配关键字的回复内容
	 * 
	 * @param key
	 * @return List<Map<String, Object>>
	 */
	public List<Map<String, Object>> getAutoMsg(String key) {
		List<Map<String, Object>> result = null;
		if (key != null && !Pattern.matches("^\\s*$", key)) {
			String sql = "select * from auto_reply where key_word=?";
			result = jdbcTemplate.queryForList(sql, key);
		}
		return result;
	}

	/**
	 * 添加回复-暂定
	 * 
	 * @param keyWord
	 * @param keyType
	 * @param reply
	 */
	public void addReply(String keyWord, String keyType, String reply) {
		String sql = "insert into auto_reply(key_word, reply) values(?, ?)";
		jdbcTemplate.update(sql, keyWord, reply);
	}

	/**
	 * 查询所有回复
	 * 
	 * @return key-value
	 */
	public List<Map<String, Object>> getAllReply() {
		String sql = "select * from auto_reply";
		List<Map<String, Object>> result = jdbcTemplate.queryForList(sql);
		return result;
	}
	
	public Map<String, Object> getReplyById(Integer id){
		String sql = "select * from auto_reply where id=?";
		return jdbcTemplate.queryForMap(sql, id);
	}
	
	/**
	 * 按id删除
	 * @param id
	 * @return
	 */
	public boolean deleteOneReply(int id) {
		String sql = "delete from auto_reply where id=?";
		int result = jdbcTemplate.update(sql, id);
		if (result == 1)
			return true;
		else
			return false;
	}

}
