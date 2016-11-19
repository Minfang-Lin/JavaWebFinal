package com.netease.course.web.controller;

import java.io.UnsupportedEncodingException;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import javax.sql.rowset.serial.SerialBlob;
import javax.sql.rowset.serial.SerialException;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.netease.course.model.Buy;
import com.netease.course.model.Product;
import com.netease.course.model.User;
import com.netease.course.service.impl.ProductService;
import com.netease.course.service.impl.UserService;

@Controller
public class HelloController {

	@Resource
	private UserService userService;
	@Resource
	private ProductService productService;

	@RequestMapping(value = "/login")
	public String login(HttpSession session) {
		// 未登录用户显示登录页面，已登录则跳转到首页
		if (session.getAttribute("user") != null) {
			return "redirect:/";
		}
		return "login";
	}

	@RequestMapping(value = "/logout")
	public String logout(HttpSession session) {
		session.invalidate();
		return "redirect:/login";
	}

	@RequestMapping(value = "/")
	public String indexPage(ModelMap map) {
		List<Product> productList = productService.getAllProductList();
		if (productList != null) {
			map.addAttribute(productList);
		}
		return "index";
	}

	@RequestMapping(value = "/show")
	public String showProductInfo(ModelMap map, HttpSession session, @RequestParam Integer id) {
		User user = (User) session.getAttribute("user");
		Product product;
		// 已登录用户根据用户id获取isBuy和isSell信息
		if (user != null) {
			product = productService.getProductById(id, user.getId());
		} else {
			product = productService.getProductById(id);
		}
		if (product != null) {
			map.addAttribute(product);
		}
		return "show";
	}

	@RequestMapping(value = "/account")
	public String showUserAccount(ModelMap map, HttpSession session) {
		User user = (User) session.getAttribute("user");
		List<Buy> buyList = productService.getBuyList(user.getId());
		if (buyList != null) {
			map.addAttribute(buyList);
		}
		return "account";
	}

	@RequestMapping(value = "/public")
	public String publicProduct() {
		return "public";
	}

	@RequestMapping(value = "/publicSubmit")
	public String showPublicResult(ModelMap map, @RequestParam String image, @RequestParam String detail,
			@RequestParam String title, @RequestParam String summary, @RequestParam Double price)
			throws SerialException, UnsupportedEncodingException, SQLException {
		Blob imageBlob = new SerialBlob(image.getBytes("UTF-8"));
		Blob detailBlob = new SerialBlob(detail.getBytes("UTF-8"));
		Product product = productService.insertProduct(price, title, imageBlob, summary, detailBlob);
		if (product != null) {
			map.addAttribute(product);
		}
		return "publicSubmit";
	}

	@RequestMapping(value = "/edit")
	public String editProduct(ModelMap map, @RequestParam Integer id) {
		Product product = productService.getProductById(id);
		if (product != null) {
			map.addAttribute(product);
		}
		return "edit";
	}

	@RequestMapping(value = "/editSubmit")
	public String editProductSubmit(ModelMap map, @RequestParam Integer id, @RequestParam String title,
			@RequestParam String summary, @RequestParam Double price, @RequestParam String image,
			@RequestParam String detail) throws SerialException, UnsupportedEncodingException, SQLException {
		Blob imageBlob = new SerialBlob(image.getBytes("UTF-8"));
		Blob detailBlob = new SerialBlob(detail.getBytes("UTF-8"));
		Product product = productService.editProductInfoById(id, price, title, imageBlob, summary, detailBlob);
		if (product != null) {
			map.addAttribute(product);
		}
		return "editSubmit";
	}

	@RequestMapping(value = "/settleAccount")
	public String settleAccount() {
		return "settleAccount";
	}
}
