package com.netease.course.dao.impl;

import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Select;
import com.netease.course.model.User;

public interface UserDao {
	@Results({
		@Result(property = "id", column = "id"),
		@Result(property = "username", column = "userName"),
		@Result(property = "password", column = "password"),
		@Result(property = "nickname", column = "nickName"),
		@Result(property = "usertype", column = "userType")
	})
	@Select("Select * from person where userName=#{0} and password=#{1}")
	public User checkInfo(String userName, String userPassword);
}
