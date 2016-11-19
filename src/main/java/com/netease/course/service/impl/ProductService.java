package com.netease.course.service.impl;

import java.sql.Blob;
import java.util.List;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.netease.course.dao.impl.BuyListDao;
import com.netease.course.dao.impl.ProductDao;
import com.netease.course.model.Buy;
import com.netease.course.model.Product;
import com.netease.course.model.SettleAccount;
import com.netease.course.model.Trx;

@Service
public class ProductService {

	@Resource
	private ProductDao dao;
	@Resource
	private BuyListDao buyListdao;

	@Transactional(propagation = Propagation.REQUIRED)
	public Product insertProduct(double price, String title, Blob image, String summary, Blob detail) {
		price = price > 0 ? price : 0;
		dao.insertProductInfo((int) (price * 100), title, image, summary, detail);
		int id = dao.getLastPublishId();
		return dao.getProductInfoById(id);
	}

	public List<Product> getAllProductList() {
		List<Product> productList = dao.getProudctList();
		for (Product p : productList) {
			Trx t = dao.getTrxByContentId(p.getId());
			if (t != null) {
				p.setBuyPrice(t.getPrice() / 100.0);
				p.setPrice(p.getPrice() / 100.0);
				p.setIsBuy(true);
				p.setBuyTime(t.getTime());
				p.setIsSell(true);
			} else {
				p.setPrice(p.getPrice() / 100.0);
				p.setIsBuy(false);
				p.setIsSell(false);
			}
		}
		return productList;
	}

	public Product getProductById(int id) {
		Product product = dao.getProductInfoById(id);
		if (product != null) {
			product.setBuyPrice(product.getBuyPrice() / 100.0);
			product.setPrice(product.getPrice() / 100.0);
		}
		return product;
	}

	public Product getProductById(int contentId, int personId) {
		Product product = dao.getProductInfoById(contentId);
		if (product == null) {
			return null;
		}
		Trx trx = dao.getTrxByContentIdAndPeronId(contentId, personId);
		if (trx != null) {
			product.setIsBuy(true);
			product.setIsSell(true);
			product.setBuyTime(trx.getTime());
			product.setBuyPrice(trx.getPrice() / 100.0);
		}
		product.setPrice(product.getPrice() / 100.0);
		return product;
	}

	public Product editProductInfoById(int id, double price, String title, Blob image, String summary, Blob detail) {
		price = price > 0 ? price : 0;
		dao.editProductInfo(id, (long) (price * 100), title, image, summary, detail);
		Product product = dao.getProductInfoById(id);
		if (product != null) {
			product.setPrice(product.getPrice() / 100.0);
		}
		return product;
	}

	public boolean deleteProductInfoById(int id) {
		return dao.deleteProductById(id);
	}

	public boolean insertPurchase(List<SettleAccount> buyList, int personId, long time) {
		for (SettleAccount buyInfo : buyList) {
			Product product = dao.getProductInfoById(buyInfo.getId());
			if (product == null) {
				return false;
			}
			int price = (int) product.getPrice();
			if (!dao.insertPurchase(buyInfo.getId(), personId, price, time, buyInfo.getNumber())){
				return false;
			}
		}
		return true;
	}

	public List<Buy> getBuyList(int personId) {
		List<Buy> buyList = buyListdao.getBuyListByPersonId(personId);
		for (Buy b : buyList) {
			Product p = dao.getProductInfoById(b.getId());
			b.setTitle(p.getTitle());
			b.setImage(p.getImage());
			b.setBuyPrice(b.getBuyPrice() / 100.0 * b.getBuyNum());
		}
		return buyList;
	}
}
