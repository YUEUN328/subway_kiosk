package com.java.subway.controller;

import java.util.List;

import com.java.subway.model.Subway;

// MVC 아키텍처에서 Controller에 해당하는 인터페이스
public interface SubwayDao {

	/**
	 * 리스트에 저장된 주문 정보를 리턴.
	 * 
	 * @return - Subway 타입 객체가 저장된 리스트.
	 */
	List<Subway> select();
	
	/**
	 * 주문 번호에 해당하는 리스트의 원소(Subway 객체)를 리턴.
	 * 
	 * @param no - 검색할 주문의 주문 번호.
	 * @return - 주문 번호에 해당하는 Subway 객체.
	 */
	Subway select(int no);
	
	/**
	 * Subway 객체를 전달받아서 리스트에 저장.
	 * 
	 * @param s - 리스트에 저장할 Subway 타입 객체.
	 * @return - 저장 성공하면 1, 실패하면 0을 리턴.
	 */
	int insert(Subway s);
	
	/**
	 * 리스트에서 업데이트할 데이터의 주문 번호(no)와 주문 정보(Subway 타입 객체)를 전달받아서, 
	 * 리스트의 해당 주문 번호의 주문 정보를 업데이트.
	 *
	 * @param no - 리스트에서 업데이트할 주문의 주문 번호. 
	 * @param s - Subway 타입 객체. 업데이트할 메뉴, 수량, 가격 정보를 가지고 있음.
	 * @return - 업데이트 성공하면 1, 실패하면 0을 리턴.
	 */
	int update(int no, Subway s);
	
	/**
	 * 리스트에서 주문 번호에 해당하는 주문 정보를 삭제.
	 * 
	 * @param no - 리스트에서 삭제할 주문의 주문 번호.
	 * @return - 삭제 성공하면 1, 실패하면 0을 리턴.
	 */	
	int delete(int no);
	
}