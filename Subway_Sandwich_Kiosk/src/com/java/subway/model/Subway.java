package com.java.subway.model;

import java.util.Date;

//MVC(Model-View-Controller) 아키텍처에서 Model에 해당하는 클래스
public class Subway {
	// 멤버 변수
	private int orderNo; // order_no 컬럼
	private String menu; // menu 컬럼
	private int quantity; // quantity 컬럼
	private int price; // price 컬럼	
	private Date orderTime; // order_time 컬럼
	
	// 생성자
	public Subway() {}

	public Subway(String menu, int quantity, int price) {
		this.menu = menu;
		this.quantity = quantity;
		this.price = price;
	}

	public Subway(int orderNo, String menu, int quantity, int price, Date orderTime) {
		this.orderNo = orderNo;
		this.menu = menu;
		this.quantity = quantity;
		this.price = price;
		this.orderTime = orderTime;
	}

	// getters & setters	
	public int getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(int orderNo) {
		this.orderNo = orderNo;
	}

	public String getMenu() {
		return menu;
	}

	public void setMenu(String menu) {
		this.menu = menu;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public Date getOrderTime() {
		return orderTime;
	}

	public void setOrderTime(Date orderTime) {
		this.orderTime = orderTime;
	}
	
	// toString()
	@Override
	public String toString() {
		return "Subway{orderNo:" + this.orderNo + "| menu:" + this.menu + "| quantity:" + this.quantity + "| price:" + this.price + "| orderTime:" + this.orderTime + "}";
	}

}