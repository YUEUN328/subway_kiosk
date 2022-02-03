package com.java.subway.jdbc;

import static com.java.subway.jdbc.OracleJdbc.COL_MENU;
import static com.java.subway.jdbc.OracleJdbc.COL_ORDER_NO;
import static com.java.subway.jdbc.OracleJdbc.COL_ORDER_TIME;
import static com.java.subway.jdbc.OracleJdbc.COL_PRICE;
import static com.java.subway.jdbc.OracleJdbc.COL_QUANTITY;
import static com.java.subway.jdbc.OracleJdbc.TABLE_ORDERS;

public interface OracleJdbc {
	// Oracle DB 접속 주소(포트, SID)/접속 방식
	String URL = "jdbc:oracle:thin:@localhost:1521:xe";
	// Oracle DB 접속 사용자 이름
	String USER = "scott";
	// Oracle DB 접속 비밀번호
	String PASSWORD = "tiger";
	
	// DB 테이블 이름 
	String TABLE_ORDERS = "orders";
	// orders 테이블의 컬럼 이름
	String COL_ORDER_NO = "order_no";
	String COL_MENU = "menu";
	String COL_QUANTITY = "quantity";
	String COL_PRICE = "price";
	String COL_ORDER_TIME = "order_time";
	
	// SQL 문장
	// SELECT * FROM orders ORDER BY order_no DESC; 
	String SQL_SELECT_ALL = "select * from " + TABLE_ORDERS + " order by " + COL_ORDER_NO + " desc";	
	
	// SELECT * FROM orders WHERE order_no = ?;
	String SQL_SELECT = "select * from " + TABLE_ORDERS + " where " + COL_ORDER_NO + " = ?";
	
	// INSERT INTO orders (menu, quantity, price) VALUES (?, ?, ?);
	String SQL_INSERT = "insert into " + TABLE_ORDERS 
			+ " (" + COL_MENU + ", " + COL_QUANTITY + ", " + COL_PRICE + ")"
			+ " values (?, ?, ?)";
	
	// UPDATE orders SET menu = ?, quantity = ?, price = ?, update_time = sysdate WHERE order_no = ?;
	String SQL_UPDATE = "update " + TABLE_ORDERS
			+ " set " + COL_MENU + " = ?, "
			+ COL_QUANTITY + " = ?, "
			+ COL_PRICE + " = ?, "
			+ COL_ORDER_TIME + " = sysdate"
			+ " where " + COL_ORDER_NO + " = ?";
	
	// DELETE FROM orders WHERE order_no = ?;
	String SQL_DELETE = "delete from " + TABLE_ORDERS + " where " + COL_ORDER_NO + " = ?";
	
}
