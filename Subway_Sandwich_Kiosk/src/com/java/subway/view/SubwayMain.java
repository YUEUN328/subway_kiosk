package com.java.subway.view;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import static com.java.subway.view.RoundedButton.*;

import java.awt.CardLayout;
import javax.swing.JPanel;

import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import com.java.subway.controller.SubwayDao;
import com.java.subway.controller.SubwayDaoImpl;
import com.java.subway.model.Subway;

import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.JTextField;

public class SubwayMain {

	private JFrame frame;
	private ImageIcon updateIcon;

	public static final Color SUBWAY_YELLOW = new Color(244, 197, 33);
	private static final String LOGO = "images/logo.jpg";
	private static final String[] COL_NAMES = { "메뉴", "수량", "가격" }; // 테이블의 컬럼 이름을 상수로
	private JPanel mainPanel;
	private JPanel orderPanel;
	private DefaultTableModel tableModel; // 테이블의 데이터(행row, 열column, 셀cell)를 관리(추가, 삭제)하는 클래스
	private JTable table;
	private SubwayDao dao;

	private int total;
	private JLabel totalLabel;
	private JTextField txtTotal;
	private JPanel sandwichPanel;
	private JPanel saladPanel;
	private JPanel sidePanel;
	private JPanel beveragePanel;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SubwayMain window = new SubwayMain();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public SubwayMain() {
		dao = SubwayDaoImpl.getInstance();
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setTitle("써브웨이 키오스크");
		frame.setBounds(100, 100, 800, 1000);
		frame.setLocationRelativeTo(null); // 중앙에 띄우기
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new CardLayout(0, 0)); // 0, 0은 윈도우 창과 레이아웃 사이의 공백 사이즈를 의미

		mainPanel = new JPanel();
		mainPanel.setBackground(Color.WHITE);
		frame.getContentPane().add(mainPanel, "main");
		mainPanel.setLayout(null);

		setImageSize(LOGO, 600, 300);
		JLabel logoBig = new JLabel(updateIcon);
		logoBig.setBounds(93, 28, 600, 300);
		mainPanel.add(logoBig);

		JButton storeBtn = new RoundedButton("먹고가기");
		storeBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				startOrder();
			}
		});
		storeBtn.setFont(new Font("맑은 고딕", Font.BOLD, 24));
		storeBtn.setBounds(187, 400, 184, 132);
		mainPanel.add(storeBtn);

		JButton pickUpBtn = new RoundedButton("포장하기");
		pickUpBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				startOrder();
			}
		});
		pickUpBtn.setFont(new Font("맑은 고딕", Font.BOLD, 24));
		pickUpBtn.setBounds(412, 400, 184, 132);
		mainPanel.add(pickUpBtn);

		JButton staffBtn = new RoundedButton("직원 전용");
		staffBtn.setFont(new Font("맑은 고딕", Font.BOLD, 20));
		staffBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				OrderHistoryFrame.showFrame(frame, SubwayMain.this);
			}
		});
		staffBtn.setBounds(634, 874, 134, 67);
		mainPanel.add(staffBtn);

		orderPanel = new JPanel();
		orderPanel.setBackground(Color.WHITE);
		frame.getContentPane().add(orderPanel, "name_3717262579927200");
		orderPanel.setLayout(null);

		setImageSize(LOGO, 200, 100);
		JLabel logoSmall = new JLabel(updateIcon);
		logoSmall.setBounds(200, 0, 382, 71);
		orderPanel.add(logoSmall);

		JPanel cardLayPanel = new JPanel();
		cardLayPanel.setBounds(14, 126, 754, 517);
		orderPanel.add(cardLayPanel);
		cardLayPanel.setLayout(new CardLayout(0, 0));

		sandwichPanel = new JPanel();
		sandwichPanel.setBackground(SUBWAY_GREEN);
		cardLayPanel.add(sandwichPanel, "name_412769635252200");
		sandwichPanel.setLayout(null);

		setImageSize("images/쉬림프.png", 228, 160);
		JButton shrimpBtn = new JButton(updateIcon);
		shrimpBtn.setBackground(Color.WHITE);
		shrimpBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				addToBasket("쉬림프", 1, 5900);
			}
		});
		shrimpBtn.setBounds(14, 12, 228, 160);
		sandwichPanel.add(shrimpBtn);

		setImageSize("images/에그마요.png", 228, 160);
		JButton eggMayoBtn = new JButton(updateIcon);
		eggMayoBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				addToBasket("에그마요", 1, 4300);
			}
		});
		eggMayoBtn.setBackground(Color.WHITE);
		eggMayoBtn.setBounds(263, 12, 228, 160);
		sandwichPanel.add(eggMayoBtn);

		setImageSize("images/로티세리바비큐치킨.png", 228, 160);
		JButton rotisserieBtn = new JButton(updateIcon);
		rotisserieBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				addToBasket("로티세리 바비큐 치킨", 1, 6100);
			}
		});
		rotisserieBtn.setBackground(Color.WHITE);
		rotisserieBtn.setBounds(512, 12, 228, 160);
		sandwichPanel.add(rotisserieBtn);

		setImageSize("images/풀드포크바비큐.png", 228, 160);
		JButton pulledPorkBtn = new JButton(updateIcon);
		pulledPorkBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				addToBasket("풀드 포크 바비큐", 1, 6000);
			}
		});
		pulledPorkBtn.setBackground(Color.WHITE);
		pulledPorkBtn.setBounds(14, 268, 228, 160);
		sandwichPanel.add(pulledPorkBtn);

		setImageSize("images/이탈리안비엠티.png", 228, 160);
		JButton bmtBtn = new JButton(updateIcon);
		bmtBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				addToBasket("이탈리안 비엠티", 1, 5400);
			}
		});
		bmtBtn.setBackground(Color.WHITE);
		bmtBtn.setBounds(263, 268, 228, 160);
		sandwichPanel.add(bmtBtn);

		setImageSize("images/스테이크&치즈.png", 228, 160);
		JButton steakBtn = new JButton(updateIcon);
		steakBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				addToBasket("스테이크 & 치즈", 1, 6500);
			}
		});
		steakBtn.setBackground(Color.WHITE);
		steakBtn.setBounds(512, 268, 228, 160);
		sandwichPanel.add(steakBtn);

		JLabel shrimpName = new JLabel("쉬림프");
		shrimpName.setOpaque(true);
		shrimpName.setBackground(Color.WHITE);
		shrimpName.setFont(new Font("맑은 고딕", Font.PLAIN, 20));
		shrimpName.setHorizontalAlignment(SwingConstants.CENTER);
		shrimpName.setBounds(14, 172, 228, 40);
		sandwichPanel.add(shrimpName);

		JLabel eggMayoName = new JLabel("에그마요");
		eggMayoName.setOpaque(true);
		eggMayoName.setHorizontalAlignment(SwingConstants.CENTER);
		eggMayoName.setFont(new Font("맑은 고딕", Font.PLAIN, 20));
		eggMayoName.setBackground(Color.WHITE);
		eggMayoName.setBounds(263, 172, 228, 40);
		sandwichPanel.add(eggMayoName);

		JLabel rotisserieName = new JLabel("로티세리 바비큐 치킨");
		rotisserieName.setOpaque(true);
		rotisserieName.setHorizontalAlignment(SwingConstants.CENTER);
		rotisserieName.setFont(new Font("맑은 고딕", Font.PLAIN, 20));
		rotisserieName.setBackground(Color.WHITE);
		rotisserieName.setBounds(512, 172, 228, 40);
		sandwichPanel.add(rotisserieName);

		JLabel pulledPorkName = new JLabel("풀드 포크 바비큐");
		pulledPorkName.setOpaque(true);
		pulledPorkName.setHorizontalAlignment(SwingConstants.CENTER);
		pulledPorkName.setFont(new Font("맑은 고딕", Font.PLAIN, 20));
		pulledPorkName.setBackground(Color.WHITE);
		pulledPorkName.setBounds(14, 427, 228, 40);
		sandwichPanel.add(pulledPorkName);

		JLabel bmtName = new JLabel("이탈리안 비엠티");
		bmtName.setOpaque(true);
		bmtName.setHorizontalAlignment(SwingConstants.CENTER);
		bmtName.setFont(new Font("맑은 고딕", Font.PLAIN, 20));
		bmtName.setBackground(Color.WHITE);
		bmtName.setBounds(263, 427, 228, 40);
		sandwichPanel.add(bmtName);

		JLabel steakName = new JLabel("스테이크 & 치즈");
		steakName.setOpaque(true);
		steakName.setHorizontalAlignment(SwingConstants.CENTER);
		steakName.setFont(new Font("맑은 고딕", Font.PLAIN, 20));
		steakName.setBackground(Color.WHITE);
		steakName.setBounds(512, 427, 228, 40);
		sandwichPanel.add(steakName);

		JLabel shrimpPrice = new JLabel("5900");
		shrimpPrice.setHorizontalAlignment(SwingConstants.CENTER);
		shrimpPrice.setBackground(Color.WHITE);
		shrimpPrice.setOpaque(true);
		shrimpPrice.setFont(new Font("맑은 고딕", Font.PLAIN, 20));
		shrimpPrice.setBounds(14, 193, 228, 54);
		sandwichPanel.add(shrimpPrice);

		JLabel pulledPorkPrice = new JLabel("6000");
		pulledPorkPrice.setOpaque(true);
		pulledPorkPrice.setHorizontalAlignment(SwingConstants.CENTER);
		pulledPorkPrice.setFont(new Font("맑은 고딕", Font.PLAIN, 20));
		pulledPorkPrice.setBackground(Color.WHITE);
		pulledPorkPrice.setBounds(14, 450, 228, 54);
		sandwichPanel.add(pulledPorkPrice);

		JLabel bmtPrice = new JLabel("5400");
		bmtPrice.setOpaque(true);
		bmtPrice.setHorizontalAlignment(SwingConstants.CENTER);
		bmtPrice.setFont(new Font("맑은 고딕", Font.PLAIN, 20));
		bmtPrice.setBackground(Color.WHITE);
		bmtPrice.setBounds(263, 450, 228, 54);
		sandwichPanel.add(bmtPrice);

		JLabel steakPrice = new JLabel("6500");
		steakPrice.setOpaque(true);
		steakPrice.setHorizontalAlignment(SwingConstants.CENTER);
		steakPrice.setFont(new Font("맑은 고딕", Font.PLAIN, 20));
		steakPrice.setBackground(Color.WHITE);
		steakPrice.setBounds(512, 450, 228, 54);
		sandwichPanel.add(steakPrice);

		JLabel eggMayoPrice = new JLabel("4300");
		eggMayoPrice.setOpaque(true);
		eggMayoPrice.setHorizontalAlignment(SwingConstants.CENTER);
		eggMayoPrice.setFont(new Font("맑은 고딕", Font.PLAIN, 20));
		eggMayoPrice.setBackground(Color.WHITE);
		eggMayoPrice.setBounds(263, 193, 228, 54);
		sandwichPanel.add(eggMayoPrice);

		JLabel rotisseriePrice = new JLabel("6100");
		rotisseriePrice.setOpaque(true);
		rotisseriePrice.setHorizontalAlignment(SwingConstants.CENTER);
		rotisseriePrice.setFont(new Font("맑은 고딕", Font.PLAIN, 20));
		rotisseriePrice.setBackground(Color.WHITE);
		rotisseriePrice.setBounds(512, 193, 228, 54);
		sandwichPanel.add(rotisseriePrice);

		saladPanel = new JPanel();
		saladPanel.setBackground(SUBWAY_GREEN);
		cardLayPanel.add(saladPanel, "name_509497355263700");
		saladPanel.setLayout(null);

		setImageSize("images/비엘티샐러드.png", 228, 160);
		JButton bltBtn = new JButton(updateIcon);
		bltBtn.setBackground(Color.WHITE);
		bltBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				addToBasket("비엘티", 1, 7100);
			}
		});
		bltBtn.setBounds(14, 12, 228, 160);
		saladPanel.add(bltBtn);

		setImageSize("images/클럽샐러드.png", 228, 160);
		JButton clubBtn = new JButton(updateIcon);
		clubBtn.setBackground(Color.WHITE);
		clubBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				addToBasket("써브웨이 클럽", 1, 7600);
			}
		});
		clubBtn.setBounds(263, 12, 228, 160);
		saladPanel.add(clubBtn);

		setImageSize("images/아보카도샐러드.png", 228, 160);
		JButton avocadoBtn = new JButton(updateIcon);
		avocadoBtn.setBackground(Color.WHITE);
		avocadoBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				addToBasket("터키 베이컨 아보카도", 1, 8200);
			}
		});
		avocadoBtn.setBounds(512, 12, 228, 160);
		saladPanel.add(avocadoBtn);

		JLabel bltName = new JLabel("비엘티");
		bltName.setOpaque(true);
		bltName.setFont(new Font("맑은 고딕", Font.PLAIN, 20));
		bltName.setHorizontalAlignment(SwingConstants.CENTER);
		bltName.setBackground(Color.WHITE);
		bltName.setBounds(14, 172, 228, 40);
		saladPanel.add(bltName);

		JLabel clubName = new JLabel("써브웨이 클럽");
		clubName.setOpaque(true);
		clubName.setHorizontalAlignment(SwingConstants.CENTER);
		clubName.setFont(new Font("맑은 고딕", Font.PLAIN, 20));
		clubName.setBackground(Color.WHITE);
		clubName.setBounds(263, 172, 228, 40);
		saladPanel.add(clubName);

		JLabel avocadoName = new JLabel("터키 베이컨 아보카도");
		avocadoName.setOpaque(true);
		avocadoName.setHorizontalAlignment(SwingConstants.CENTER);
		avocadoName.setFont(new Font("맑은 고딕", Font.PLAIN, 20));
		avocadoName.setBackground(Color.WHITE);
		avocadoName.setBounds(512, 172, 228, 40);
		saladPanel.add(avocadoName);

		JLabel bltPrice = new JLabel("7100");
		bltPrice.setHorizontalAlignment(SwingConstants.CENTER);
		bltPrice.setFont(new Font("맑은 고딕", Font.PLAIN, 20));
		bltPrice.setBackground(Color.WHITE);
		bltPrice.setOpaque(true);
		bltPrice.setBounds(14, 193, 228, 54);
		saladPanel.add(bltPrice);

		JLabel clubPrice = new JLabel("7600");
		clubPrice.setOpaque(true);
		clubPrice.setHorizontalAlignment(SwingConstants.CENTER);
		clubPrice.setFont(new Font("맑은 고딕", Font.PLAIN, 20));
		clubPrice.setBackground(Color.WHITE);
		clubPrice.setBounds(263, 193, 228, 54);
		saladPanel.add(clubPrice);

		JLabel avocadoPrice = new JLabel("8200");
		avocadoPrice.setOpaque(true);
		avocadoPrice.setHorizontalAlignment(SwingConstants.CENTER);
		avocadoPrice.setFont(new Font("맑은 고딕", Font.PLAIN, 20));
		avocadoPrice.setBackground(Color.WHITE);
		avocadoPrice.setBounds(512, 193, 228, 54);
		saladPanel.add(avocadoPrice);

		sidePanel = new JPanel();
		sidePanel.setBackground(SUBWAY_GREEN);
		cardLayPanel.add(sidePanel, "name_581479711855500");
		sidePanel.setLayout(null);

		setImageSize("images/포테이토.jpg", 228, 160);
		JButton potatoBtn = new JButton(updateIcon);
		potatoBtn.setBackground(Color.WHITE);
		potatoBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				addToBasket("웨지 포테이토", 1, 1500);
			}
		});
		potatoBtn.setBounds(14, 12, 228, 160);
		sidePanel.add(potatoBtn);

		setImageSize("images/수프.png", 228, 160);
		JButton soupBtn = new JButton(updateIcon);
		soupBtn.setBackground(Color.WHITE);
		soupBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				addToBasket("베이크 포테이토 수프", 1, 2900);
			}
		});
		soupBtn.setBounds(263, 12, 228, 160);
		sidePanel.add(soupBtn);

		setImageSize("images/쿠키.jpg", 228, 160);
		JButton cookieBtn = new JButton(updateIcon);
		cookieBtn.setBackground(Color.WHITE);
		cookieBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				addToBasket("라즈베리 치즈케이크", 1, 1000);
			}
		});
		cookieBtn.setBounds(512, 12, 228, 160);
		sidePanel.add(cookieBtn);

		JLabel potatoName = new JLabel("웨지 포테이토");
		potatoName.setHorizontalAlignment(SwingConstants.CENTER);
		potatoName.setFont(new Font("맑은 고딕", Font.PLAIN, 20));
		potatoName.setOpaque(true);
		potatoName.setBackground(Color.WHITE);
		potatoName.setBounds(14, 172, 228, 40);
		sidePanel.add(potatoName);

		JLabel soupName = new JLabel("베이크 포테이토 수프");
		soupName.setOpaque(true);
		soupName.setHorizontalAlignment(SwingConstants.CENTER);
		soupName.setFont(new Font("맑은 고딕", Font.PLAIN, 20));
		soupName.setBackground(Color.WHITE);
		soupName.setBounds(263, 172, 228, 40);
		sidePanel.add(soupName);

		JLabel cookieName = new JLabel("라즈베리 치즈케이크");
		cookieName.setOpaque(true);
		cookieName.setHorizontalAlignment(SwingConstants.CENTER);
		cookieName.setFont(new Font("맑은 고딕", Font.PLAIN, 20));
		cookieName.setBackground(Color.WHITE);
		cookieName.setBounds(512, 172, 228, 40);
		sidePanel.add(cookieName);

		JLabel potatoPrice = new JLabel("1500");
		potatoPrice.setOpaque(true);
		potatoPrice.setHorizontalAlignment(SwingConstants.CENTER);
		potatoPrice.setFont(new Font("맑은 고딕", Font.PLAIN, 20));
		potatoPrice.setBackground(Color.WHITE);
		potatoPrice.setBounds(14, 193, 228, 54);
		sidePanel.add(potatoPrice);

		JLabel soupPrice = new JLabel("2900");
		soupPrice.setOpaque(true);
		soupPrice.setHorizontalAlignment(SwingConstants.CENTER);
		soupPrice.setFont(new Font("맑은 고딕", Font.PLAIN, 20));
		soupPrice.setBackground(Color.WHITE);
		soupPrice.setBounds(263, 193, 228, 54);
		sidePanel.add(soupPrice);

		JLabel cookiePrice = new JLabel("1000");
		cookiePrice.setOpaque(true);
		cookiePrice.setHorizontalAlignment(SwingConstants.CENTER);
		cookiePrice.setFont(new Font("맑은 고딕", Font.PLAIN, 20));
		cookiePrice.setBackground(Color.WHITE);
		cookiePrice.setBounds(512, 193, 228, 54);
		sidePanel.add(cookiePrice);

		beveragePanel = new JPanel();
		beveragePanel.setBackground(SUBWAY_GREEN);
		cardLayPanel.add(beveragePanel, "name_581510979600100");
		beveragePanel.setLayout(null);

		setImageSize("images/우유.jpg", 228, 160);
		JButton milkBtn = new JButton(updateIcon);
		milkBtn.setBackground(Color.WHITE);
		milkBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				addToBasket("우유", 1, 1500);
			}
		});
		milkBtn.setBounds(14, 12, 228, 160);
		beveragePanel.add(milkBtn);

		setImageSize("images/탄산음료.jpg", 228, 160);
		JButton sodaBtn = new JButton(updateIcon);
		sodaBtn.setBackground(Color.WHITE);
		sodaBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				addToBasket("탄산음료", 1, 1500);
			}
		});
		sodaBtn.setBounds(263, 12, 228, 160);
		beveragePanel.add(sodaBtn);

		setImageSize("images/커피.jpg", 228, 160);
		JButton coffeeBtn = new JButton(updateIcon);
		coffeeBtn.setBackground(Color.WHITE);
		coffeeBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				addToBasket("커피", 1, 1500);
			}
		});
		coffeeBtn.setBounds(512, 12, 228, 160);
		beveragePanel.add(coffeeBtn);

		JLabel milkName = new JLabel("우유");
		milkName.setOpaque(true);
		milkName.setHorizontalAlignment(SwingConstants.CENTER);
		milkName.setFont(new Font("맑은 고딕", Font.PLAIN, 20));
		milkName.setBackground(Color.WHITE);
		milkName.setBounds(14, 172, 228, 40);
		beveragePanel.add(milkName);

		JLabel sodaName = new JLabel("탄산음료");
		sodaName.setOpaque(true);
		sodaName.setHorizontalAlignment(SwingConstants.CENTER);
		sodaName.setFont(new Font("맑은 고딕", Font.PLAIN, 20));
		sodaName.setBackground(Color.WHITE);
		sodaName.setBounds(263, 172, 228, 40);
		beveragePanel.add(sodaName);

		JLabel coffeeName = new JLabel("커피");
		coffeeName.setOpaque(true);
		coffeeName.setHorizontalAlignment(SwingConstants.CENTER);
		coffeeName.setFont(new Font("맑은 고딕", Font.PLAIN, 20));
		coffeeName.setBackground(Color.WHITE);
		coffeeName.setBounds(512, 172, 228, 40);
		beveragePanel.add(coffeeName);

		JLabel milkPrice = new JLabel("1500");
		milkPrice.setOpaque(true);
		milkPrice.setHorizontalAlignment(SwingConstants.CENTER);
		milkPrice.setFont(new Font("맑은 고딕", Font.PLAIN, 20));
		milkPrice.setBackground(Color.WHITE);
		milkPrice.setBounds(14, 193, 228, 54);
		beveragePanel.add(milkPrice);

		JLabel sodaPrice = new JLabel("1500");
		sodaPrice.setOpaque(true);
		sodaPrice.setHorizontalAlignment(SwingConstants.CENTER);
		sodaPrice.setFont(new Font("맑은 고딕", Font.PLAIN, 20));
		sodaPrice.setBackground(Color.WHITE);
		sodaPrice.setBounds(263, 193, 228, 54);
		beveragePanel.add(sodaPrice);

		JLabel coffeePrice = new JLabel("1500");
		coffeePrice.setOpaque(true);
		coffeePrice.setHorizontalAlignment(SwingConstants.CENTER);
		coffeePrice.setFont(new Font("맑은 고딕", Font.PLAIN, 20));
		coffeePrice.setBackground(Color.WHITE);
		coffeePrice.setBounds(512, 193, 228, 54);
		beveragePanel.add(coffeePrice);

		JButton orderBtn = new RoundedButton("주문하기");
		orderBtn.setFont(new Font("맑은 고딕", Font.BOLD, 20));
		orderBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				insertOrder();
			}
		});
		orderBtn.setBounds(658, 655, 110, 137);
		orderPanel.add(orderBtn);

		JButton deleteBtn = new RoundedButton("X");
		deleteBtn.setFont(new Font("맑은 고딕", Font.BOLD, 24));
		deleteBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				deleteFromBasket();
			}
		});
		deleteBtn.setBounds(658, 804, 110, 137);
		orderPanel.add(deleteBtn);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(14, 655, 630, 231);
		orderPanel.add(scrollPane);

		table = new JTable(); // 장바구니 테이블
		JTableHeader header = table.getTableHeader();
		header.setBackground(Color.WHITE);
		table.setRowHeight(30);

		tableModel = new DefaultTableModel(null, COL_NAMES); // 테이블에 테이블모델을 설정
		tableModel.addTableModelListener(new TableModelListener() {
			@Override
			public void tableChanged(TableModelEvent e) {
				handleTableChange(e);
			}
		});
		table.setModel(tableModel);
		scrollPane.setViewportView(table);

		totalLabel = new JLabel("총 주문 금액");
		totalLabel.setOpaque(true);
		totalLabel.setBackground(SUBWAY_YELLOW);
		totalLabel.setFont(new Font("맑은 고딕", Font.BOLD, 20));
		totalLabel.setBounds(14, 887, 123, 54);
		orderPanel.add(totalLabel);

		txtTotal = new JTextField();
		txtTotal.setBackground(Color.WHITE);
		txtTotal.setText("0");
		txtTotal.setFont(new Font("맑은 고딕", Font.PLAIN, 20));
		txtTotal.setBounds(137, 887, 507, 54);
		orderPanel.add(txtTotal);
		txtTotal.setEditable(false);
		txtTotal.setColumns(10);

		JButton sandwichBtn = new JButton("샌드위치"); // 샌드위치 패널로 이동하는 버튼
		sandwichBtn.setForeground(Color.WHITE);
		sandwichBtn.setFont(new Font("맑은 고딕", Font.BOLD, 16));
		sandwichBtn.setBackground(SUBWAY_GREEN);
		sandwichBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				nextPanel(saladPanel, sidePanel, beveragePanel, sandwichPanel);
			}
		});
		sandwichBtn.setBounds(14, 89, 168, 38);
		orderPanel.add(sandwichBtn);

		JButton saladBtn = new JButton("샐러드"); // 샐러드 패널로 이동하는 버튼
		saladBtn.setForeground(Color.WHITE);
		saladBtn.setFont(new Font("맑은 고딕", Font.BOLD, 16));
		saladBtn.setBackground(SUBWAY_GREEN);
		saladBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				nextPanel(sandwichPanel, sidePanel, beveragePanel, saladPanel);
			}
		});
		saladBtn.setBounds(210, 89, 168, 38);
		orderPanel.add(saladBtn);

		JButton sideBtn = new JButton("사이드"); // 사이드 패널로 이동하는 버튼
		sideBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				nextPanel(sandwichPanel, saladPanel, beveragePanel, sidePanel);
			}
		});
		sideBtn.setForeground(Color.WHITE);
		sideBtn.setFont(new Font("맑은 고딕", Font.BOLD, 16));
		sideBtn.setBackground(SUBWAY_GREEN);
		sideBtn.setBounds(403, 89, 168, 38);
		orderPanel.add(sideBtn);

		JButton beverageBtn = new JButton("음료"); // 음료 패널로 이동하는 버튼
		beverageBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				nextPanel(sandwichPanel, saladPanel, sidePanel, beveragePanel);
			}
		});
		beverageBtn.setForeground(Color.WHITE);
		beverageBtn.setFont(new Font("맑은 고딕", Font.BOLD, 16));
		beverageBtn.setBackground(SUBWAY_GREEN);
		beverageBtn.setBounds(600, 89, 168, 38);
		orderPanel.add(beverageBtn);

		JButton restartBtn = new RoundedButton("처음으로"); // 메인 패널로 이동하는 버튼
		restartBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				clearBasket();
				nextPanel(orderPanel, mainPanel);
			}
		});
		restartBtn.setFont(new Font("맑은 고딕", Font.BOLD, 20));
		restartBtn.setBounds(658, 17, 110, 54);
		orderPanel.add(restartBtn);
	} // end initialize()

	private void startOrder() {
		nextPanel(mainPanel, orderPanel);
		nextPanel(saladPanel, sidePanel, beveragePanel, sandwichPanel);
	}

	private void handleTableChange(TableModelEvent e) {
		// 테이블 모델 변화 이벤트의 타입을 찾음
		int eventType = e.getType();
		if (eventType == TableModelEvent.UPDATE) {			
			int row = e.getFirstRow(); // (업데이트) 이벤트가 발생한 행 인덱스
			int col = e.getColumn(); // (업데이트) 이벤트가 발생한 열 인덱스
			if (col == 1) { // 수량 변경이 일어났을 때
				int quantity = Integer.parseInt(tableModel.getValueAt(row, 1).toString());
				int price = Integer.parseInt(tableModel.getValueAt(row, 2).toString());
				
				tableModel.setValueAt((quantity * price), row, 2); // 변경된 수량으로 계산된 가격을 테이블 모델에 반영
			}	
		} else if (eventType == TableModelEvent.INSERT) {
			setTotal();
		} else if (eventType == TableModelEvent.DELETE) {
			setTotal();
		}	
	}

	private void insertOrder() { // 장바구니에 담긴 주문 정보를 DB에 저장
		int rows = tableModel.getRowCount();
		
		if (rows == 0) {
			JOptionPane.showMessageDialog(frame, "주문할 메뉴를 선택하세요.", "알림", JOptionPane.WARNING_MESSAGE);
			return;
		}
		
		for (int i = 0; i < rows; i++) {
			String menu = (String) tableModel.getValueAt(i, 0);
			int quantity = Integer.parseInt(tableModel.getValueAt(i, 1).toString());
			int price = Integer.parseInt(tableModel.getValueAt(i, 2).toString());
			Subway s = new Subway(menu, quantity, price);
			dao.insert(s);
		}

		clearBasket();
		JOptionPane.showMessageDialog(frame, "주문이 완료되었습니다.", "알림", JOptionPane.INFORMATION_MESSAGE);
		nextPanel(orderPanel, mainPanel);
	}

	private void clearBasket() { // 장바구니 비우기
		tableModel.setNumRows(0);
		setTotal();
	}

	private void deleteFromBasket() { // 장바구니에서 선택한 메뉴를 삭제
		int row = table.getSelectedRow(); // 테이블에서 선택된 행(row)의 인덱스를 찾음
		if (row == -1) { // 테이블에서 선택된 행이 없으면
			JOptionPane.showMessageDialog(frame, "삭제할 메뉴를 선택하세요.", "알림", JOptionPane.WARNING_MESSAGE);
			return;
		}

		tableModel.removeRow(row);
	}

	private void addToBasket(String menu, int qty, int price) { // 장바구니에 선택한 메뉴를 담기
		Subway basket = new Subway(menu, qty, price);
		Object[] rowData = { basket.getMenu(), basket.getQuantity(), basket.getPrice() }; // 테이블의 행에 추가할 데이터를 1차원 배열로 생성
		tableModel.addRow(rowData); // 테이블 모델에 행(row)를 추가
	}

	private void setTotal() { // 총 주문 금액 계산
		int total = 0;
		int rowsCount = tableModel.getRowCount();
		
		for (int i = 0; i < rowsCount; i++) {
			total += Integer.parseInt(tableModel.getValueAt(i, 2).toString());
		}
		
		txtTotal.setText(total + "");
	}

	private void nextPanel(JPanel other1, JPanel other2, JPanel other3, JPanel next) { // 메뉴 패널 간 이동
		other1.setVisible(false); // 다른 메뉴 패널 안 보이게 하기
		other2.setVisible(false);
		other3.setVisible(false);
		next.setVisible(true); // 이동할 패널 보여주기
	}

	private void nextPanel(JPanel now, JPanel next) { // 메인 패널과 오더 패널 간 이동
		now.setVisible(false); // 현재 패널 안 보이게 하기
		next.setVisible(true); // 이동할 패널 보여주기
	}

	private void setImageSize(String image, int width, int height) {
		ImageIcon icon = new ImageIcon(image);
		Image img = icon.getImage(); // ImageIcon을 Image로 변환
		Image updateImg = img.getScaledInstance(width, height, Image.SCALE_SMOOTH); // 이미지 크기 설정. 원래 비율은 맞춰야 함
		updateIcon = new ImageIcon(updateImg); // Image로 ImageIcon을 생성
	}
	
} // end class SubwayMain