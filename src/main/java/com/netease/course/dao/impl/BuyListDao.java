package com.netease.course.dao.impl;

import java.util.List;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import com.netease.course.model.Buy;

public interface BuyListDao {
	@Results({
		@Result(property = "id", column = "contentId"),
		@Result(property = "buyPrice", column = "price"),
		@Result(property = "buyTime", column = "time"),
		@Result(property = "buyNum", column = "number")
	})
	@Select("SELECT contentId, price, time, number From trx WHERE personId=#{0}")
	public List<Buy> getBuyListByPersonId(int personId);
}
