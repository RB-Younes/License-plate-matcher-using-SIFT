import java.awt.Cursor;
import java.awt.EventQueue;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.geom.RoundRectangle2D;
import java.io.IOException;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import com.formdev.flatlaf.FlatLightLaf;

////////////////////////////////////////////////////////////////////////////////-----------Fenetre Menu------------///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

public class Step2 extends JFrame {
	

	private static final long serialVersionUID = 1L;
	
	
	private int posX = 0;   //Position X de la souris au clic
    private int posY = 0;   //Position Y de la souris au clic

	private JPanel contentPane;
	private boolean selectedoui =false;
	private boolean selectedAUTO =false;
	private boolean selectedman =false;
	private JRadioButton rdbtnAuto;
	private JRadioButton rdbtnMan;
	private JRadioButton rdbtnOui;
	private JRadioButton rdbtnNon;
	private JButton Search;


	/**
	 * Launch the application.
	 * @throws UnsupportedLookAndFeelException 
	 */
	public static void main(String[] args) throws UnsupportedLookAndFeelException {
		FlatLightLaf.install();	
		UIManager.setLookAndFeel(new FlatLightLaf() );
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Step2 frame = new Step2("");
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
	public Step2(String File_Selected) {
		setIconImage(Toolkit.getDefaultToolkit().getImage(Step2.class.getResource("/Menu_img/artificial-intelligence.png")));
		
		setUndecorated(true);	
		setResizable(false);
		//setIconImage(Toolkit.getDefaultToolkit().getImage(Menu.class.getResource("/Menu_img/medical-care.png")));
		setTitle("Menu");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(0, 0, 400, 250);
		setShape(new RoundRectangle2D.Double(0d, 0d, 400d, 250d, 25d, 25d));
		setLocationRelativeTo(null);
		//vu que la frame est Undecorated on a besoin de ces MouseListeners pour la faire bouger(frame)
		  addMouseListener(new MouseAdapter() {
	            @Override
	            //on recupere les coordonnées de la souris
	            public void mousePressed(MouseEvent e) {
	                posX = e.getX();    //Position X de la souris au clic
	                posY = e.getY();    //Position Y de la souris au clic
	            }
	        });
	        addMouseMotionListener(new MouseMotionAdapter() {
	            // A chaque deplacement on recalcul le positionnement de la fenetre
	            @Override
	            public void mouseDragged(MouseEvent e) {
	                int depX = e.getX() - posX;
	                int depY = e.getY() - posY;
	                setLocation(getX()+depX, getY()+depY);
	            }
	        });
	    
	        contentPane = new JPanel();
			setContentPane(contentPane);
			contentPane.setLayout(null);
		
			   
		JLabel BGMenu = new JLabel("");
		BGMenu.setIcon(new ImageIcon(Step2.class.getResource("/Suite2/Suite2.png")));	// Back ground de base	
       
// Bouton Reduire ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		JButton Minimise_BTN = new JButton("");
		Minimise_BTN.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				Minimise_BTN.setIcon(new ImageIcon(Step2.class.getResource("/Menu_img/Minimize ML selected.png")));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				Minimise_BTN.setIcon(new ImageIcon(Step2.class.getResource("/Menu_img/Minimize ML .png")));
			}
		});
		Minimise_BTN.setToolTipText("Minimize");
		ButtonStyle(Minimise_BTN);
		Minimise_BTN.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setState(ICONIFIED);
			}
		});
		Minimise_BTN.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		Minimise_BTN.setIcon(new ImageIcon(Step2.class.getResource("/Menu_img/Minimize ML .png")));
		Minimise_BTN.setBounds(151, 11, 32, 32);
		contentPane.add(Minimise_BTN);
		
		
// Exit bouton//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		
		JButton Exit_BTN = new JButton("");
		Exit_BTN.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent arg0) {
				Exit_BTN.setIcon(new ImageIcon(Step2.class.getResource("/Menu_img/Exit ML selected.png")));
			}
			@Override
			public void mouseExited(MouseEvent arg0) {
				Exit_BTN.setIcon(new ImageIcon(Step2.class.getResource("/Menu_img/Exit ML.png")));
			}
		});
		Exit_BTN.setToolTipText("Exit");
		ButtonStyle(Exit_BTN);
		Exit_BTN.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
					int ClickedButton	=JOptionPane.showConfirmDialog(null, "Do you really want to leave?", "Close", JOptionPane.YES_NO_OPTION);
					if(ClickedButton==JOptionPane.YES_OPTION)
					{					
						dispose();
					}
			}	
		});
		Exit_BTN.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		Exit_BTN.setIcon(new ImageIcon(Step2.class.getResource("/Menu_img/Exit ML.png")));
		Exit_BTN.setBounds(235, 11, 32, 32);
		contentPane.add(Exit_BTN);
//Boutton home//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		JButton btnHome = new JButton("");
		btnHome.setEnabled(false);
		btnHome.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				if (btnHome.isEnabled()) {
					btnHome.setIcon(new ImageIcon(Step2.class.getResource("/Models_img/home selected.png")));//changer les couleurs button
				}
			}
			@Override
			public void mouseExited(MouseEvent e) {
				if (btnHome.isEnabled()) {
					btnHome.setIcon(new ImageIcon(Step2.class.getResource("/Models_img/home.png")));//remetre le bouton de base
				}
			}
		});
		btnHome.setIcon(new ImageIcon(Step2.class.getResource("/Models_img/home.png")));
		btnHome.setToolTipText("Menu");
		btnHome.setBounds(193, 11, 32, 32);
		ButtonStyle(btnHome);
		contentPane.add(btnHome);
		
		
		
	    rdbtnOui = new JRadioButton("");
	    rdbtnOui.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		rdbtnOui.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				selectedoui=true;
				rdbtnMan.setEnabled(true);
				rdbtnAuto.setEnabled(true);
				Search.setEnabled(true);
			}
		});
		rdbtnOui.setBounds(116, 103, 21, 23);
		contentPane.add(rdbtnOui);
		
		rdbtnNon = new JRadioButton("");
	    rdbtnNon.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		rdbtnNon.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				selectedoui=false;
				rdbtnMan.setEnabled(false);
				rdbtnAuto.setEnabled(false);
				Search.setEnabled(false);
				dispose();
			
			}
		});
		rdbtnNon.setBounds(204, 103, 21, 23);
		contentPane.add(rdbtnNon);
		
		rdbtnAuto = new JRadioButton("");
		rdbtnAuto.setEnabled(false);
	    rdbtnAuto.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		rdbtnAuto.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				selectedAUTO= true;
				selectedman = false;
			}
		});
		rdbtnAuto.setBounds(74, 143, 21, 23);
		contentPane.add(rdbtnAuto);
		
		rdbtnMan = new JRadioButton("");
	    rdbtnMan.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		rdbtnMan.setEnabled(false);
		rdbtnMan.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				selectedman=true;
				selectedAUTO=false;
			}
		});
		rdbtnMan.setBounds(74, 190, 21, 23);
		contentPane.add(rdbtnMan);
		
		
		ButtonGroup group1 = new ButtonGroup();
		group1.add(rdbtnOui);
		group1.add(rdbtnNon);
		
		ButtonGroup group = new ButtonGroup();
		group.add(rdbtnAuto);
		group.add(rdbtnMan);
		
		
		Search = new JButton("");
		Search.setEnabled(false);
		Search.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		Search.setIcon(new ImageIcon(Step2.class.getResource("/Suite2/check 1.png")));
		Search.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent arg0) {
				Search.setIcon(new ImageIcon(Step2.class.getResource("/Suite2/check 2.png")));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				Search.setIcon(new ImageIcon(Step2.class.getResource("/Suite2/check 1.png")));

			}
		});
		Search.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (selectedoui) {
					
					if(selectedman!=false || selectedAUTO!=false)
					{
						
						
						String path_script ="";
			              
			              if(selectedAUTO==true)
			              {
			            	  path_script = Menu.class.getResource("/scripts_py/mat_auto.py").getPath();
				             
			              }
			              else {
			            	  path_script = Menu.class.getResource("/scripts_py/mat_man.py").getPath();

			              }
			              
			              path_script = path_script.substring(1, path_script.length());
			              ProcessBuilder pb = null;
		            	  pb = new ProcessBuilder("python",path_script,"--path_image",File_Selected ).inheritIO();
						 
	                      @SuppressWarnings("unused")
							Process process = null;
							try {
								process = pb.start();
								try {
									 process.waitFor();
								} catch (InterruptedException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}
							} catch (IOException e2) {
								// TODO Auto-generated catch block
								e2.printStackTrace();
							}
							JOptionPane.showMessageDialog(null,  "Découpaage terminé (vehicule black listé).","state" ,JOptionPane.PLAIN_MESSAGE, new ImageIcon(Menu.class.getResource("/state/stamp.png")));	
							dispose();
	    
					}
					else {
						JOptionPane.showMessageDialog(null,  "Veuillez choisir le mode de découpage.","state" ,JOptionPane.PLAIN_MESSAGE, new ImageIcon(Menu.class.getResource("/state/error.png")));	

					}
					
				}
				
			}
		});
		Search.setToolTipText("la voiture est elle black list\u00E9e");
		Search.setBounds(326, 90, 64, 64);
		contentPane.add(Search);
		ButtonStyle(Search);
		
		

//le background////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		BGMenu.setBounds(0, 0, 400, 250);
		contentPane.add(BGMenu);
		
	}
//methode du style des buttons/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	 private void ButtonStyle(JButton btn) {
	//enlecer les bordures des btn
	 btn.setOpaque(false);
	 btn.setFocusPainted(false);
	 btn.setBorderPainted(false);
	 btn.setContentAreaFilled(false);
	
}
}
