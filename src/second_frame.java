import java.awt.*;
import java.awt.event.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.net.UnknownHostException;
import java.text.DecimalFormat;

import javax.imageio.ImageIO;
import javax.swing.*;
import org.json.*;


public class second_frame extends JFrame implements ActionListener,KeyListener {

	//color
	Color green = new Color(0,200,100);
	Color blue = new Color(0,200,255);
	Color orange = new Color(255,87,51);
	Color background_color = green;
	
	
	Color temp_color_for_green = new Color(255,80,0);
	Color temp_color_for_blue = new Color(143, 10, 189);
	Color temp_color_for_orange = new Color(244, 230, 17);
	Color temp_color = temp_color_for_green;
	
	
	
	//json
	JsonReader jr = new JsonReader();
	JSONObject json = new JSONObject();
	 
	//buttons
	JButton backward =  new JButton(new ImageIcon(getClass().getResource(("left_arrow.png"))));
	JButton exit =  new JButton(new ImageIcon(getClass().getResource(("exit.png"))));
	JButton settings =  new JButton(new ImageIcon(getClass().getResource(("Gear-icon.png"))));
	
	//radiobutton
	JRadioButton rb1 = new JRadioButton("1");
	JRadioButton rb2 = new JRadioButton("10");
	JRadioButton rb3 = new JRadioButton("30");
	
	JRadioButton rb4 = new JRadioButton("green");
	JRadioButton rb5 = new JRadioButton("blue");
	JRadioButton rb6 = new JRadioButton("orange");
			
	
	//button group
	ButtonGroup bg1 = new ButtonGroup();
	ButtonGroup bg2 = new ButtonGroup();
	
	//labels
	JLabel icon_label = new JLabel();
	JLabel city = new JLabel();
	JLabel temp = new JLabel();
	JLabel temp_min = new JLabel();
	JLabel temp_max = new JLabel();
	JLabel humidity = new JLabel();
	JLabel pressure = new JLabel();
	JLabel wind_speed = new JLabel();
	
	JLabel update_time = new JLabel();
	
	JLabel refresh_time_label = new JLabel(); //settings 
	JLabel backgraund_color_label = new JLabel(); //settings 
	
	
	//panels
	JPanel icon_panel = new JPanel();
		
	//move frame
	int pX,pY;
	
	//city
	String city_s;
	
	//last update time
	int second = 0;
	int minute = 0;
	
	//connection contol
	boolean is_connected = true;
		
	//is city exist control
	boolean is_city_exists = true;
	
	//is settings on
	boolean is_settings_on = false;
	
	//formating
	DecimalFormat df1 = new DecimalFormat("###");
	DecimalFormat df2 = new DecimalFormat("###.#");
	
	//saving
	save s = new save();
	int saved_value = 0;
		
		
		
	
	
	
	second_frame(String city) {
		super(city);
		
		city_s = city;
		
		create_frame();
		
		call_city(city);
		buttons();
		radiobuttons();
		labels();
		icon();
		
		move_frame();
		
		iscityexists();
		isconnected();
		
		using_saved_values();
		
		timer.start();
	}


	void create_frame() {
		setBounds(200,200,450,230);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		setUndecorated(true);
		setOpacity(0.7f);
		setVisible(true);
		//setAlwaysOnTop( true );
		
		this.setLayout(null);
		setFocusable(true);
		addKeyListener(this);
	}
	
	
	
	void buttons() {
		add(backward);
		backward.setBounds(350,15,40,40);
		backward.addActionListener(this);
		backward.setFocusable(false);
	
		add(exit);
		exit.setBounds(400,15,40,40);
		exit.addActionListener(this);
		exit.setFocusable(false);
		
		add(settings);
		settings.setBounds(400,60,40,40);
		settings.addActionListener(this);
		settings.setFocusable(false);
		
	}
	
	

	void radiobuttons() {
		
		//refresh time
		add(rb1);
		rb1.setBounds(25,280,40,40);
		rb1.setBackground(background_color);
		rb1.setForeground(new Color(0,0,0));
		rb1.setActionCommand("1");
		rb1.setFocusable(false);
		
		add(rb2);
		rb2.setBounds(60,280,40,40);
		rb2.setBackground(background_color);
		rb2.setForeground(new Color(0,0,0));
		rb2.setActionCommand("10");
		rb2.setFocusable(false);
		
		add(rb3);
		rb3.setBounds(95,280,40,40);
		rb3.setBackground(background_color);
		rb3.setForeground(new Color(0,0,0));
		rb3.setActionCommand("30");
		rb3.setFocusable(false);
		
		
		bg1.add(rb1);
		bg1.add(rb2);
		bg1.add(rb3);
		
		rb2.setSelected(true);
		
		
		//color
		add(rb4);
		rb4.setBounds(220,280,65,40);
		rb4.setBackground(background_color);
		rb4.setForeground(new Color(0,0,0));
		rb4.setActionCommand("1");
		rb4.addActionListener(this);
		rb4.setFocusable(false);
		
		add(rb5);
		rb5.setBounds(290,280,65,40);
		rb5.setBackground(background_color);
		rb5.setForeground(new Color(0,0,0));
		rb5.setActionCommand("10");
		rb5.addActionListener(this);
		rb5.setFocusable(false);
		
		add(rb6);
		rb6.setBounds(350,280,65,40);
		rb6.setBackground(background_color);
		rb6.setForeground(new Color(0,0,0));
		rb6.setActionCommand("30");
		rb6.addActionListener(this);
		rb6.setFocusable(false);
		
		
		bg2.add(rb4);
		bg2.add(rb5);
		bg2.add(rb6);
		
		rb4.setSelected(true);
		
		
	}
	
	
	
	void labels(){
		
		//city
		add(city);
		city.setBounds(20,20,300,60);
		city.setFont(new Font("arial",Font.BOLD,50));
		city.setForeground(new Color(0,0,0));
		
		//temp
		add(temp);
		temp.setBounds(20,80,250,60);
		temp.setFont(new Font("arial",Font.BOLD,50));
		temp.setForeground(temp_color);
		
		//temp min
		add(temp_min);
		temp_min.setBounds(25,140,200,30);
		temp_min.setFont(new Font("arial",Font.BOLD,17));
		temp_min.setForeground(new Color(0,0,0));
		
		//temp max
		add(temp_max);
		temp_max.setBounds(25,160,200,30);
		temp_max.setFont(new Font("arial",Font.BOLD,17));
		temp_max.setForeground(new Color(0,0,0));
		
		//humidity
		add(humidity);
		humidity.setBounds(235,110,200,30);
		humidity.setFont(new Font("arial",Font.BOLD,20));
		humidity.setForeground(new Color(0,0,0));
		
		//pressure
		add(pressure);
		pressure.setBounds(235,135,200,30);
		pressure.setFont(new Font("arial",Font.BOLD,20));
		pressure.setForeground(new Color(0,0,0));
		
		//wind_speed
		add(wind_speed);
		wind_speed.setBounds(235,160,200,30);
		wind_speed.setFont(new Font("arial",Font.BOLD,20));
		wind_speed.setForeground(new Color(0,0,0));
		
		//update_time
		add(update_time);
		update_time.setBounds(25,200,270,15);
		update_time.setFont(new Font("arial",Font.PLAIN,15));
		update_time.setForeground(new Color(200,0,0));
		update_time.setText("last update: " + minute + ":" + second);
		
		
		//settings
		//-------------------------------------------------------
		//refresh time 
		add(refresh_time_label);
		refresh_time_label.setBounds(25,250,150,20);
		refresh_time_label.setFont(new Font("arial",Font.BOLD,20));
		refresh_time_label.setForeground(new Color(0,0,0));
		refresh_time_label.setText("update time");
		
		//backgraund color
		add(backgraund_color_label);
		backgraund_color_label.setBounds(295,250,150,20);
		backgraund_color_label.setFont(new Font("arial",Font.BOLD,20));
		backgraund_color_label.setForeground(new Color(0,0,0));
		backgraund_color_label.setText("theme");
		//-------------------------------------------------------
		
		//name
		try {
		city.setText(json.get("name").toString());
		}
		
		catch(Exception e){
			System.out.println("json error - name");
		}	
		
		//temp
		try {
		temp.setText((df1.format(Double.valueOf(json.getJSONObject("main").get("temp").toString()) - 273)) + "°c");
		temp_min.setText("min: " + (df2.format(Double.valueOf(json.getJSONObject("main").get("temp_min").toString()) - 273)) + "°c");
		temp_max.setText("max: " + (df2.format(Double.valueOf(json.getJSONObject("main").get("temp_max").toString()) - 273)) + "°c");
		}
	
		catch(Exception e){
			System.out.println("json error - temp");
		}	
	
	
		//humidity
		try {
		humidity.setText("Humidity: " + json.getJSONObject("main").get("humidity").toString() + " %");
		}
	
		catch(Exception e){
			System.out.println("json error - humidity");
		}
	
		//pressure
		try {
		pressure.setText("Pressure: " + (df2.format(Double.valueOf(json.getJSONObject("main").get("pressure").toString()))) + " hpa");
			}
		
		catch(Exception e){
			System.out.println("json error - pressure");
		}
		
		//wind_speed
		try {
			wind_speed.setText("Wind: " + (df1.format(Double.valueOf(json.getJSONObject("wind").get("speed").toString()) * 3.6f)) + " km/h");
		}
				
		catch(Exception e){
			System.out.println("json error - wind_speed");
		}
	}

	
	
	


	void call_city(String city) {
		try {
			json = jr.readJsonFromUrl("http://api.openweathermap.org/data/2.5/weather?appid=" + Api_Key.api_key + "=" + city);
			 System.out.println("temp: " + json.getJSONObject("main").get("temp"));
			
			 
			 //connection control
			 is_connected = true;
			
			 //is city exist control
			 is_city_exists = true;
					 
		}
		
		catch(FileNotFoundException e) {
			
			System.out.println("city not found");
			
			 //is city exist control
			is_city_exists = false;
		}
		catch(IOException e) {
			
			System.out.println("no connection");
			
			//connection control
			is_connected = false;
		}
		
		catch(Exception e){
			e.printStackTrace();
			System.out.println("ERROR");

		}
		
		
	}
	
	
	
	
	
	void icon() {
		
		add(icon_panel);
		icon_panel.setBounds(160,90,50,50);
		icon_panel.setBackground(background_color);
	
		try {
		
		JSONArray weatherArray = json.getJSONArray("weather");
		JSONObject obj = weatherArray.getJSONObject(0);
		String icon_code = obj.getString("icon");	
	
		
		URL url = new URL("http://openweathermap.org/img/w/" + icon_code + ".png");
		Image image = ImageIO.read(url);

		icon_label = new JLabel(new ImageIcon(image));
		icon_panel.add(icon_label);
		
		//seting small icon of app
		this.setIconImage(Toolkit.getDefaultToolkit().getImage(url));
		}
		
		catch(Exception e){
			System.out.println("icon load failed");
			
		}
	}
	
	
	
	
	
	

	
	
	
	
	
	
	//----------------------------------------timers------------------------------------------------------
	
	//last update timer
	Timer timer  = new Timer(1000, new ActionListener() {
	    public void actionPerformed(ActionEvent evt) {

	    	
	    	//time
	    	second++;
	    	
	    	if(second == 60) {
	    		second = 0;
	    		minute++;
	    	}
	    	
	    	
	    	//connection control
	    	if(is_connected) {
	    	update_time.setText("last update: " + minute + ":" + second);
	    	}
	    	else {
	    	update_time.setText("last update attempt: " + minute + ":" + second + " (no connection)");
	    	}
	    	
	    	
	    
	    	//refresh time
	    	if(minute >= Integer.valueOf(bg1.getSelection().getActionCommand())) {  //gets refresh time from radiobuttons
	    		refresh(city_s);
		    	
	    		
		    	minute = 0;
		    	second = 0;
	    		
	    	}
	    	
	    	
	    	System.out.println("clock: " + minute + ":" + second);
	    }
	});
	
	//-------------------------------------------------------------------------------------------------------
	
	
	
	
	
	
	
	//restart from first frame
	void restart() {
		first_frame f = new first_frame();
		
		//stop timer it doesnt stops with frame
		timer.stop();
		dispose();
	}
	
	
	
	//refresh labels and icon is enough
	void refresh(String city) {
	
		call_city(city);
		labels();
		icon();
		
	}
	
	
	//open setting menu
	void setting() {
	
		if(!is_settings_on) {
			setSize(450,330);
			is_settings_on = true;
		}
		else {
			setSize(450,230);
			is_settings_on = false;
		}
		
	}
	

	
	//city exists control
	void iscityexists() {
		if(!is_city_exists) {
			city.setText("No such city");
		}
	}
	
	
	//is connected control
	void isconnected() {
		if(!is_connected) {
			city.setText("No internet");
		}
	}
	
	
	
	//change bacakgraund color
	void change_backgraund_color(Color color,Color temp_color) {
		
		getContentPane().setBackground(color);
		
		rb1.setBackground(color);
		rb2.setBackground(color);
		rb3.setBackground(color);
		rb4.setBackground(color);
		rb5.setBackground(color);
		rb6.setBackground(color);
		
		icon_panel.setBackground(color);
		
		
		background_color = color;
		this.temp_color = temp_color;
		temp.setForeground(temp_color);
	}
	
	
	
	//using saved values
	void using_saved_values() {
		
		s.read();
		
		saved_value = s.return_saved_value();
		
		if(saved_value == 1) {
			rb1.setSelected(true);
			rb4.setSelected(true);
			change_backgraund_color(green,temp_color_for_green);
		}
		else if(saved_value == 2) {
			rb2.setSelected(true);
			rb4.setSelected(true);
			change_backgraund_color(green,temp_color_for_green);
		}
		else if(saved_value == 3) {
			rb3.setSelected(true);
			rb4.setSelected(true);
			change_backgraund_color(green,temp_color_for_green);
		}
		
		
		
		else if(saved_value == 4) {
			rb1.setSelected(true);
			rb5.setSelected(true);
			change_backgraund_color(blue,temp_color_for_blue);
		}
		else if(saved_value == 5) {
			rb2.setSelected(true);
			rb5.setSelected(true);
			change_backgraund_color(blue,temp_color_for_blue);
		}
		else if(saved_value == 6) {
			rb3.setSelected(true);
			rb5.setSelected(true);
			change_backgraund_color(blue,temp_color_for_blue);
		}
		
		
		
		else if(saved_value == 7) {
			rb1.setSelected(true);
			rb6.setSelected(true);
			change_backgraund_color(orange,temp_color_for_orange);
		}
		else if(saved_value == 8) {
			rb2.setSelected(true);
			rb6.setSelected(true);
			change_backgraund_color(orange,temp_color_for_orange);
		}
		else if(saved_value == 9) {
			rb3.setSelected(true);
			rb6.setSelected(true);
			change_backgraund_color(orange,temp_color_for_orange);
		}
		
		
		
		else {
		rb2.setSelected(true);
		rb4.setSelected(true);
		change_backgraund_color(green,temp_color_for_green);
		}
	}
	
	
	
	//saving values
	void save_values() {
		if(rb1.isSelected() && rb4.isSelected()) {
			saved_value = 1;
		}
		else if(rb2.isSelected() && rb4.isSelected()) {
			saved_value = 2;
		}
		else if(rb3.isSelected() && rb4.isSelected()) {
			saved_value = 3;
		}
		
		
		
		else if(rb1.isSelected() && rb5.isSelected()) {
			saved_value = 4;
		}
		else if(rb2.isSelected() && rb5.isSelected()) {
			saved_value = 5;
		}
		else if(rb3.isSelected() && rb5.isSelected()) {
			saved_value = 6;
		}
		
		
		
		else if(rb1.isSelected() && rb6.isSelected()) {
			saved_value = 7;
		}
		else if(rb2.isSelected() && rb6.isSelected()) {
			saved_value = 8;
		}
		else if(rb3.isSelected() && rb6.isSelected()) {
			saved_value = 9;
		}
		
		
		
		else {
			saved_value = 0;
		}
		
		s.write(saved_value);
	}
	
	
	
	
	
	
	
	
	void move_frame() {
		
		addMouseListener(new MouseAdapter(){
	        public void mousePressed(MouseEvent me)
	        {
	            // Get x,y and store them
	            pX=me.getX();
	            pY=me.getY();
	        }
	    });
		
		

		addMouseMotionListener(new MouseAdapter(){
	         public void mouseDragged(MouseEvent me)
	         {
	             // Set the location
	             // get the current location x-co-ordinate and then get
	             // the current drag x co-ordinate, add them and subtract most recent
	             // mouse pressed x co-ordinate
	             // do same for y co-ordinate
	             setLocation(getLocation().x+me.getX()-pX,getLocation().y+me.getY()-pY);
	         }
	     });
	}
	

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	//---------------------------------------------------------action-----------------------------------------------------------------------
	
	
	
	
	
	
	public void actionPerformed(ActionEvent e) {
	
		//buttons
		if(e.getSource() == backward) {
			save_values();
			restart();
		}
			
		if(e.getSource() == exit) {
			save_values();
			System.exit(0);
		}

		if(e.getSource() == settings) {
			setting();
		}
		
		
		//radiobuttons
		if(e.getSource() == rb4) {
			change_backgraund_color(green,temp_color_for_green);
		}
		
		if(e.getSource() == rb5) {
			change_backgraund_color(blue,temp_color_for_blue);
		}
		
		if(e.getSource() == rb6) {
			change_backgraund_color(orange,temp_color_for_orange);
		}
	}

	
	
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_ESCAPE) {
			save_values();
			System.exit(0);
		}
		if(e.getKeyCode() == KeyEvent.VK_ENTER) {
			save_values();
			restart();
		}
	}



	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
	
}
