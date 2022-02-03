package com.java.subway.view;

import static com.java.subway.view.SubwayMain.*;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import com.java.subway.controller.SubwayDao;
import com.java.subway.controller.SubwayDaoImpl;
import com.java.subway.model.Subway;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import java.awt.Font;
import javax.swing.JTextField;

public class OrderHistoryFrame extends JFrame {

	private JPanel contentPane;
	private Component parentComponent; // 부모 컴포넌트 참조값을 저장하기 위한 변수
	private SubwayMain mainApp; // 메인 화면(SubwayMain) 객체의 참조값을 저장하기 위한 변수
	private JTable table;
	private DefaultTableModel tableModel;
	private static final String[] COL_NAMES = { "주문 번호", "메뉴", "수량", "가격", "주문 시간" };
	private SubwayDao dao;
	private JTextField textField;
	
	/**
	 * Launch the application.
	 */
	public static void showFrame(Component parentComponent, SubwayMain mainApp) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					OrderHistoryFrame frame = new OrderHistoryFrame(parentComponent, mainApp);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public OrderHistoryFrame(Component parentComponent, SubwayMain mainApp) {
		dao = SubwayDaoImpl.getInstance();
		this.parentComponent = parentComponent;
		this.mainApp = mainApp;
		initialize();
		loadOrderData();
	}
	
	public void initialize() {
		setTitle("주문 내역");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 800, 600);
		setLocationRelativeTo(null); // 중앙에 띄우기
		contentPane = new JPanel();
		contentPane.setBackground(SUBWAY_YELLOW);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(14, 78, 754, 397);
		contentPane.add(scrollPane);
		
		table = new JTable();
		JTableHeader header = table.getTableHeader();
		header.setBackground(Color.WHITE);
		table.setRowHeight(30);
		
		tableModel = new DefaultTableModel(null, COL_NAMES); // 테이블에 테이블모델을 설정
		table.setModel(tableModel);
		scrollPane.setViewportView(table);
		
		JButton deleteBtn = new RoundedButton("삭제");
		deleteBtn.setFont(new Font("맑은 고딕", Font.BOLD, 20));
		deleteBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				deleteOrder();
			}
		});
		deleteBtn.setBounds(670, 487, 98, 54);
		contentPane.add(deleteBtn);
		
		JLabel label = new JLabel("주문 내역");
		label.setForeground(Color.BLACK);
		label.setFont(new Font("맑은 고딕", Font.BOLD, 20));
		label.setBounds(40, 12, 194, 54);
		contentPane.add(label);
		
		JButton updateBtn = new RoundedButton("변경");
		updateBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				showUpdateFrame();
			}
		});
		updateBtn.setFont(new Font("맑은 고딕", Font.BOLD, 20));
		updateBtn.setBounds(558, 487, 98, 54);
		contentPane.add(updateBtn);
		
		JButton searchBtn = new RoundedButton("검색");
		searchBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				searchOrder();
			}
		});
		searchBtn.setFont(new Font("맑은 고딕", Font.BOLD, 20));
		searchBtn.setBounds(558, 12, 98, 54);
		contentPane.add(searchBtn);
		
		textField = new JTextField();
		textField.setBounds(163, 12, 381, 54);
		contentPane.add(textField);
		textField.setColumns(10);
	} // end initialize()

	private void showUpdateFrame() {
		int row = table.getSelectedRow(); 
		if (row == -1) {
			JOptionPane.showMessageDialog(this, "변경할 주문을 선택하세요.", "알림", JOptionPane.WARNING_MESSAGE);
			return;
		}
		
		int no = Integer.parseInt(tableModel.getValueAt(row, 0).toString());
		UpdateFrame.showFrame(this, no, this);
	}

	private void searchOrder() {
		if (textField.getText().equals("")) {
			JOptionPane.showMessageDialog(this, "검색할 주문 번호를 입력하세요.", "알림", JOptionPane.WARNING_MESSAGE);
			return;
		}
		
		tableModel = new DefaultTableModel(null, COL_NAMES);
		table.setModel(tableModel);
		int no = Integer.parseInt(textField.getText());
		Subway s = dao.select(no);
		addOrderToTableModel(s);
		textField.setText("");
	}

	private void deleteOrder() { // DB에 저장된 주문 정보를 삭제
		int row = table.getSelectedRow(); // 테이블에서 선택된 행(row)의 인덱스를 찾음
		if (row == -1) { // 테이블에서 선택된 행이 없으면
			JOptionPane.showMessageDialog(this, "삭제할 주문을 선택하세요.", "알림", JOptionPane.WARNING_MESSAGE);
			return;
		}
		
		String[] options = {"취소", "삭제"};
		int confirm = JOptionPane.showOptionDialog(this, "주문을 삭제하겠습니까?", "확인", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[1]);
		if (confirm == 1) {
			int no = Integer.parseInt(tableModel.getValueAt(row, 0).toString());
			int result = dao.delete(no); 
			if (result == 1) {
				tableModel.removeRow(row); // 테이블에서 행 삭제
				loadOrderData();
				JOptionPane.showMessageDialog(this, "삭제가 완료되었습니다.", "알림", JOptionPane.INFORMATION_MESSAGE);
			}
		}		
	}

	private void loadOrderData() { // DB에 저장된 주문 데이터를 로딩해서 테이블로 보여주기
		List<Subway> list = dao.select();
		for (Subway s : list) {
			addOrderToTableModel(s);
		}		
	}

	private void addOrderToTableModel(Subway s) {
		Object[] rowData = {s.getOrderNo(), s.getMenu(), s.getQuantity(), s.getPrice(), s.getOrderTime()};
		tableModel.addRow(rowData);
	}

	public void updateNotify() {
		// 테이블 모델을 초기화 - 전체 데이터를 새로 로딩
		tableModel = new DefaultTableModel(null, COL_NAMES); // 데이터를 모두 삭제
		table.setModel(tableModel); // 테이블에 초기화된 테이블 모델을 세팅
		loadOrderData(); // 전체 데이터 새로 로딩
	}
	
} // end class StaffFrame