package com.kh.controller;

import java.util.ArrayList;

import com.kh.model.service.ProductService;
import com.kh.model.vo.Product;
import com.kh.view.ProductMenu;

public class ProductController {
	
	public void selectProductList() {
		ArrayList<Product> list = new ProductService().selectProductList();
		
		if(list.isEmpty()) {
			new ProductMenu().displayNoData();
		}else {
			new ProductMenu().displayProductList(list);
		}
	}
	
	public void insertProduct(Product p) {
		
		int result = new ProductService().insertProduct(p);
		
		if(result > 0) {
			new ProductMenu().displaySuccess("상품 추가 성공");
		}else {
			new ProductMenu().displayFail("상품 추가 실패");
		}
	}
	
	
	public void updateProduct(Product p) {
		
		int result = new ProductService().updateProduct(p);
		
		if(result > 0) {
			new ProductMenu().displaySuccess("상품 변경 성공");
		}else {
			new ProductMenu().displayFail("상품 변경 실패");
		}
	}
	
	public void deleteProduct(String id) {
		
		int result = new ProductService().deleteProduct(id);
		
		if(result > 0) {
			new ProductMenu().displaySuccess("상품 삭제 성공");
		}else {
			new ProductMenu().displayFail("상품 삭제 실패");
		}
		
	}
	
	public void searchProduct(String name) {
		ArrayList<Product> list = new ProductService().searchProduct(name);
		
		if(list.isEmpty()) {
			new ProductMenu().displayNoData();
		}else {
			new ProductMenu().displayProductList(list);
		}
	}
	

}
