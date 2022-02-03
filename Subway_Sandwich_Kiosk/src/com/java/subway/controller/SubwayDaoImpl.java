package com.java.subway.controller;

import static com.java.subway.jdbc.OracleJdbc.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.java.subway.model.Subway;

import oracle.jdbc.OracleDriver;

//MVC 아키텍처에서 Controller를 구현하는 클래스
public class SubwayDaoImpl implements SubwayDao {

	// Singleton 패턴
	private static SubwayDaoImpl instance = null;

	private SubwayDaoImpl() {
		try {
			DriverManager.registerDriver(new OracleDriver());
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static SubwayDaoImpl getInstance() {
		if (instance == null) {
			instance = new SubwayDaoImpl();
		}
		return instance;
	}

	@Override
	public List<Subway> select() {
		List<Subway> list = new ArrayList<Subway>();

		String sql = SQL_SELECT_ALL;
		System.out.println(sql);	
		
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		
		try {
			conn = DriverManager.getConnection(URL, USER, PASSWORD);
			stmt = conn.createStatement();	
			rs = stmt.executeQuery(sql);

			while (rs.next()) { // ResultSet에 레코드(행, row)가 있으면
				int no = rs.getInt(COL_ORDER_NO);
				String menu = rs.getString(COL_MENU);
				int qty = rs.getInt(COL_QUANTITY);
				int price = rs.getInt(COL_PRICE);
				Date date = rs.getTimestamp(COL_ORDER_TIME);

				Subway s = new Subway(no, menu, qty, price, date);
				list.add(s);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				rs.close(); // ResultSet 리소스 해제
				stmt.close(); // Statement 리소스 해제
				conn.close(); // DB connection 해제
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return list;
	}

	@Override
	public Subway select(int no) {
		Subway s = null;
		
		String sql = SQL_SELECT;
		System.out.println(sql);
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			conn = DriverManager.getConnection(URL, USER, PASSWORD);
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, no);
			
			rs = pstmt.executeQuery();
			if (rs.next()) { // 검색된 결과가 있으면
				String menu = rs.getString(COL_MENU);
				int qty = rs.getInt(COL_QUANTITY);
				int price = rs.getInt(COL_PRICE);
				Date date = rs.getTimestamp(COL_ORDER_TIME);
				
				s = new Subway(no, menu, qty, price, date);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				rs.close();
				pstmt.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return s;
	}

	@Override
	public int insert(Subway s) {
		String sql = SQL_INSERT;
		System.out.println(sql);
		
		int result = 0;
		Connection conn = null; 
		PreparedStatement pstmt = null; 

		try {
			conn = DriverManager.getConnection(URL, USER, PASSWORD);
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, s.getMenu()); 
			pstmt.setInt(2, s.getQuantity()); 
			pstmt.setInt(3, s.getPrice()); 

			result = pstmt.executeUpdate();
			System.out.println(result + "행이 삽입됐습니다.");

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				pstmt.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return result;
	}

	@Override
	public int update(int no, Subway s) {
		String sql = SQL_UPDATE;
		System.out.println(sql);
		
		int result = 0;
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			conn = DriverManager.getConnection(URL, USER, PASSWORD);			
			pstmt = conn.prepareStatement(sql);	
			pstmt.setString(1, s.getMenu());
			pstmt.setInt(2, s.getQuantity());
			pstmt.setInt(3, s.getPrice());
			pstmt.setInt(4, no);
			
			result = pstmt.executeUpdate();
			System.out.println(result + "행이 수정됐습니다.");
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				pstmt.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return result;
	}

	@Override
	public int delete(int no) {
		String sql = SQL_DELETE;
		System.out.println(sql);
		
		int result = 0;
		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			conn = DriverManager.getConnection(URL, USER, PASSWORD);
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, no);

			result = pstmt.executeUpdate();
			System.out.println(result + "행이 삭제됐습니다.");

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				pstmt.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return result;
	}

}