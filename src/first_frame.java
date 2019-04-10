import java.awt.*;
import java.awt.event.*;

import javax.imageio.ImageIO;
import javax.swing.*;


public class first_frame extends JFrame implements ActionListener,KeyListener{

	//combobox
	String []cities = new String[] {"choose or write","istanbul","ankara","eskisehir","new york","miami","london","paris","tokyo"};
	JComboBox<String> cb = new JComboBox<String>(cities); 
	
	//buttons
	JButton forward =  new JButton(new ImageIcon(getClass().getResource(("right_arrow.png"))));
	JButton exit =  new JButton(new ImageIcon(getClass().getResource(("exit.png"))));
	
	//move frame
	int pX,pY;
		
		
	
	
	
	first_frame(){
	
		create_frame();
		
		buttons();
		combobox();
		
		move_frame();
		combobox_keylistener();
		
	}
	
	
	void create_frame() {
		setBounds(200,200,340,90);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		setUndecorated(true);
		setOpacity(0.7f);
		setVisible(true);
		
		this.getContentPane().setBackground(new Color(0,200,100));
		
		//seting small icon of app
		this.setIconImage(Toolkit.getDefaultToolkit().getImage("C:\\Users\\user\\eclipse-workspace\\weather app\\weather-icon.png"));
		
		this.setLayout(null);
		setFocusable(true);
		addKeyListener(this);	
	}
	
	
	
	void buttons() {
		
		add(forward);
		forward.setBounds(240,25,40,40);
		forward.addActionListener(this);
		
		add(exit);
		exit.setBounds(290,25,40,40);
		exit.addActionListener(this);
		
	}
	
	
	
	void combobox() {
		
		add(cb);
		cb.setBounds(10,25,223,40);
		cb.setFont(new Font("arial",Font.PLAIN,30));
		cb.setBackground(new Color(40,160,0));
		cb.setForeground(new Color(0,0,0));
		cb.setEditable(true);
		
	}



	void second_frame() {
		second_frame f = new second_frame(cb.getSelectedItem().toString());
		this.dispose();
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
	
	
	
	
	
	public void actionPerformed(ActionEvent e) {
		
		if(e.getSource() == forward && cb.getSelectedIndex() != 0) {
			second_frame();
		}
		
		
		if(e.getSource() == exit) {
			System.exit(0);
		}
		
	}


	
	
	
	//---------------------------------------------------------action-----------------------------------------------------------------------
	
	
	
	//keylistener if combobox focused
	
	void combobox_keylistener() {
		cb.getEditor().getEditorComponent().addKeyListener(new KeyAdapter() {
			public void keyReleased(KeyEvent e) {
		      

		    	int key= e.getKeyCode();
		    	char key_s = e.getKeyChar();
		    	
		    	
		    	if((key>=0) && (key<=255) && (key!=8) && (key!=37) && (key!=38) && (key!=39) && (key!=40) && (key!=13)){
					if(cb.getSelectedIndex() == 0) {
					cb.setSelectedItem(key_s);
					cb.requestFocus();
					}
				}
		    
				
				if (e.getKeyCode() == KeyEvent.VK_ENTER && cb.getSelectedIndex() != 0) {
		        	second_frame();
		        }
		    	if(e.getKeyCode() == KeyEvent.VK_ESCAPE) {
					System.exit(0);
				}
				
			}	
		});
	}
	
	
	
	//keylistener
	
	
	public void keyPressed(KeyEvent e) {
		
		int key = e.getKeyCode();
		char key_s = e.getKeyChar();
		
		if((key>=0) && (key<=255) && (key!=8) && (key!=37) && (key!=38) && (key!=39) && (key!=40) && (key!=13)){
			if(cb.getSelectedIndex() == 0) {
			cb.setSelectedItem(key_s);
			cb.requestFocus();
			}
		}
		
		if(e.getKeyCode() == KeyEvent.VK_ESCAPE) {
			System.exit(0);
		}
		if(e.getKeyCode() == KeyEvent.VK_ENTER && cb.getSelectedIndex() != 0) {
			second_frame();
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
