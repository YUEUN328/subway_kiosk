package com.java.subway.view;

import static com.java.subway.view.SubwayMain.SUBWAY_YELLOW;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.java.subway.controller.SubwayDao;
import com.java.subway.controller.SubwayDaoImpl;
import com.java.subway.model.Subway;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class UpdateFrame extends JFrame {

	// JComboBox의 아이템으로 사용하기 위한 문자열 배열을 정의 
	private static final String[] ITEMS = { "쉬림프", "에그마요", "로티세리 바비큐 치킨", "풀드 포크 바비큐", "이탈리안 비엠티", 
			"스테이크 & 치즈", "비엘티", "써브웨이 클럽", "터키 베이컨 아보카도", "웨지 포테이토", "베이크 포테이토 수프", 
			"라즈베리 치즈케이크", "우유", "탄산음료", "커피" };
	
	private JPanel contentPane;
	private Component parentComponent; // 업데이트 프레임을 보여주기 위한 부모 컴포넌트
	private int no; // 변경할 주문의 주문 번호
	private OrderHistoryFrame orderHistory;
	private SubwayDao dao;
	private JTextField textQty;
	private JTextField textPrice;
	private JComboBox comboBox;

	/**
	 * Launch the application.
	 */
	public static void showFrame(Component parentComponent, int no, OrderHistoryFrame orderHistory) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UpdateFrame frame = new UpdateFrame(parentComponent, no, orderHistory);
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
	public UpdateFrame(Component parentComponent, int no, OrderHistoryFrame orderHistory) {
		dao = SubwayDaoImpl.getInstance();
		this.parentComponent = parentComponent;
		this.no = no;
		this.orderHistory = orderHistory;
		initialize();
		loadOrderData(no);
	}
	
	public void initialize() {
		setTitle("주문 변경");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(735, 180, 478, 481);
		setLocationRelativeTo(null); // 중앙에 띄우기
		contentPane = new JPanel();
		contentPane.setBackground(SUBWAY_YELLOW);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel labelMenu = new JLabel("메뉴");
		labelMenu.setFont(new Font("맑은 고딕", Font.BOLD, 20));
		labelMenu.setBounds(14, 46, 155, 44);
		contentPane.add(labelMenu);
		
		comboBox = new JComboBox();
		comboBox.setFont(new Font("맑은 고딕", Font.PLAIN, 20));
		comboBox.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				setTextPrice();
			}
		});
		DefaultComboBoxModel<String> comboModel = new DefaultComboBoxModel<>(ITEMS);
		comboBox.setModel(comboModel);
		comboBox.setBounds(72, 37, 374, 71);
		contentPane.add(comboBox);
		
		JLabel labelQty = new JLabel("수량");
		labelQty.setFont(new Font("맑은 고딕", Font.BOLD, 20));
		labelQty.setBounds(14, 160, 155, 44);
		contentPane.add(labelQty);
		
		textQty = new JTextField();
		textQty.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				setTextQty();
			}
		});
		textQty.setFont(new Font("맑은 고딕", Font.PLAIN, 20));
		textQty.setBounds(72, 147, 374, 71);
		contentPane.add(textQty);
		textQty.setColumns(10);
		
		JLabel labelPrice = new JLabel("가격");
		labelPrice.setFont(new Font("맑은 고딕", Font.BOLD, 20));
		labelPrice.setBounds(14, 272, 155, 44);
		contentPane.add(labelPrice);
		
		textPrice = new JTextField();
		textPrice.setFont(new Font("맑은 고딕", Font.PLAIN, 20));
		textPrice.setBounds(72, 259, 374, 71);
		contentPane.add(textPrice);
		textPrice.setColumns(10);
		
		JButton btnUpdate = new RoundedButton("변경");
		btnUpdate.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				updateOrder();
			}
		});
		btnUpdate.setFont(new Font("맑은 고딕", Font.BOLD, 20));
		btnUpdate.setBounds(238, 368, 98, 54);
		contentPane.add(btnUpdate);
		
		JButton btnCancel = new RoundedButton("취소");
		btnCancel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btnCancel.setFont(new Font("맑은 고딕", Font.BOLD, 20));
		btnCancel.setBounds(126, 368, 98, 54);
		contentPane.add(btnCancel);
	} // end initialize()
	
	private void updateOrder() {
		// 업데이트할 내용을 읽음
		String menu = (String) comboBox.getSelectedItem();
		int qty = Integer.parseInt(textQty.getText());
		int price = Integer.parseInt(textPrice.getText());
		Subway s = new Subway(menu, qty, price);
		
		// dao를 사용해서 주문 정보를 수정
		int result = dao.update(no, s);
		if (result == 1) {
			// 업데이트 창 닫기
			dispose(); 		
			// 주문 내역 창에 주문 정보가 변경됐다고 알려줌
			orderHistory.updateNotify();
			// 성공 팝업 띄우기
			JOptionPane.showMessageDialog(parentComponent, "변경이 완료되었습니다.", "알림", JOptionPane.INFORMATION_MESSAGE);
		}
	}

	private void setTextQty() { // 수량 * 가격
		int qty = Integer.parseInt(textQty.getText());
		int price = Integer.parseInt(textPrice.getText());
		textPrice.setText(Integer.toString(qty * price));
	}

	private void setTextPrice() { // 콤보박스 선택에 따른 가격 세팅
		String selected = (String) comboBox.getSelectedItem();
		
		if (selected.equals("쉬림프")) {
			textPrice.setText("5900");
		} else if (selected.equals("에그마요")) {
			textPrice.setText("4300");
		} else if (selected.equals("로티세리 바비큐 치킨")) {
			textPrice.setText("6100");
		} else if (selected.equals("풀드 포크 바비큐")) {
			textPrice.setText("6000");
		} else if (selected.equals("이탈리안 비엠티")) {
			textPrice.setText("5400");
		} else if (selected.equals("스테이크 & 치즈")) {
			textPrice.setText("6500");
		} else if (selected.equals("비엘티")) {
			textPrice.setText("7100");
		} else if (selected.equals("써브웨이 클럽")) {
			textPrice.setText("7600");
		} else if (selected.equals("터키 베이컨 아보카도")) {
			textPrice.setText("8200");
		} else if (selected.equals("웨지 포테이토")) {
			textPrice.setText("1500");
		} else if (selected.equals("베이크 포테이토 수프")) {
			textPrice.setText("2900");
		} else if (selected.equals("라즈베리 치즈케이크")) {
			textPrice.setText("1000");
		} else if (selected.equals("우유")) {
			textPrice.setText("1500");
		} else if (selected.equals("탄산음료")) {
			textPrice.setText("1500");
		} else {
			textPrice.setText("1500");
		}	
	}

	private void loadOrderData(int no) {
		// dao를 사용해서 주문 번호를 검색하고, 해당 주문 정보를 콤보박스와 텍스트필드에 채워줌
		Subway s = dao.select(no);
		comboBox.setSelectedItem(s.getMenu());
		textQty.setText(Integer.toString(s.getQuantity()));
		textPrice.setText(Integer.toString(s.getPrice()));
	}
	
} // end class UpdateFrame
