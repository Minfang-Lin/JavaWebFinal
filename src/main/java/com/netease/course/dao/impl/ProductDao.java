package com.netease.course.dao.impl;

import java.sql.Blob;
import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.netease.course.model.Product;
import com.netease.course.model.Trx;

public interface ProductDao {
//	@Options(useGeneratedKeys=true, keyProperty="0", keyColumn = "id")
	@Insert("INSERT INTO content (price, title, icon, abstract, text) VALUES (#{0}, #{1}, #{2}, #{3}, #{4})")
	public void insertProductInfo(long price, String title, Blob image, String summary, Blob detail);

	@Select("SELECT LAST_INSERT_ID()")
	public int getLastPublishId();
	
	@Results({
		@Result(property = "id", column = "id"),
		@Result(property = "price", column = "price"),
		@Result(property = "title", column = "title"),
		@Result(property = "image", column = "icon", javaType = Blob.class, typeHandler=com.netease.course.utils.ConvertBlobTypeHandler.class),
		@Result(property = "summary", column = "abstract"),
		@Result(property = "detail", column = "text", javaType = Blob.class, typeHandler=com.netease.course.utils.ConvertBlobTypeHandler.class)
	})
	@Select("SELECT id, price, title, icon, abstract, text FROM content WHERE ID=#{id}")
	public Product getProductInfoById(int id);
	
	@Insert("INSERT INTO trx (contentId, personId, price, time, number) VALUES (#{0}, #{1}, #{2}, #{3}, #{4})")
	public boolean insertPurchase(int contentId, int personId, int price, long time, int buyNum);
	
	@Results({
		@Result(property = "id", column = "id"),
		@Result(property = "price", column = "price"),
		@Result(property = "title", column = "title"),
		@Result(property = "image", column = "icon", javaType = Blob.class, typeHandler=com.netease.course.utils.ConvertBlobTypeHandler.class),
		@Result(property = "summary", column = "abstract"),
		@Result(property = "detail", column = "text", javaType = Blob.class, typeHandler=com.netease.course.utils.ConvertBlobTypeHandler.class)
	})
	@Select("SELECT id, price, title, icon, abstract, text FROM content")
	public List<Product> getProudctList();
	
	@Select("SELECT id, contentId, personId, price, time, number FROM trx WHERE contentId=#{0}")
	public Trx getTrxByContentId(int contentId);
	
	@Select("SELECT id, contentId, personId, price, time, number FROM trx WHERE contentId=#{0} AND personId=#{1}")
	public Trx getTrxByContentIdAndPeronId(int contentId, int personId);
	
	@Update("UPDATE content SET price=#{1}, title=#{2}, icon=#{3}, abstract=#{4}, text=#{5} WHERE id=#{0}")
	public void editProductInfo(int id, long price, String title, Blob image, String summary, Blob detail);
	
	@Delete("DELETE FROM content WHERE id = #{0}")
	public boolean deleteProductById(int id);

}
