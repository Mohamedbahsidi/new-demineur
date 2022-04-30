import javax.swing.*;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.Date;
import java.util.Random;





public class PrincipalW extends JFrame{
	int espace=5;
	int mx;
	int my;
	 int[] i_cliquer= {0,1};
	int neights=0;
	
	
	 Random rand=new Random();
	 public int[][] nbre_click_droit= new int[16][9];
	int[][] mines=new int[16][9];
	int[][] neighbours=new int [16][19];
	boolean[][] reveler=new boolean[16][9];
	boolean[][] drapeau=new boolean[16][9];
	int nbre_mine=0;
	
	Date startDate =new Date();
	public int timex=1130;
	public int timey=5;
	public int sec=0;
	public int similex=605;
	public int similey=5;
	public boolean happiness=true;
	public boolean victory=false;
	public boolean defeat=false;
	
	
	public PrincipalW() {
		//parametrage de la Fenetre Principal
		this.setTitle("Ma premier fenetre");
		
		this.setSize(1286,800);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
		 this.setResizable(false);
		
		//generaton de nbre aleatoires
		 for(int i=0; i<16; i++) {
			for (int j=0; j<9; j++) {
				if(   rand.nextInt(100)     <20) {
					mines[i][j]=1;
					nbre_mine  +=1;
					
				}else {
					mines[i][j]=0;
				}
				reveler[i][j]=false;
				
			}
		}
		for(int i=0; i<16; i++) {
			for (int j=0; j<9; j++) {
				neights=0;
				for(int fx=0; fx<16; fx++) {
					for (int fy=0; fy<9; fy++) {
						
						if(isN(i, j, fx, fy)==true) {
							neights++;
						}
						
						
					}	
				
				}
				if(mines[i][j]==1) neighbours[i][j]=neights-1; else neighbours[i][j]=neights;
						
			}
		}
		
		
		
		
		
		
		
		//creation  de l'objet Jpanel
		Board board=new Board();
		
		this.setContentPane(board);
		
		//creation d'oblet evenements
		Move move=new Move();
		this.addMouseMotionListener(move);  
		
		Click click=new Click();
		this.addMouseListener(click);
		
	
	}
	//	le Jpanel
	public class Board extends JPanel{
		//jouer sur le jframe
		public void paintComponent(Graphics g) {
			g.setColor(Color.DARK_GRAY);
			
			g.fillRect(0,0,1280,800);
			 //creation de grilles
			for(int i=0; i<16; i++) {
				for (int j=0; j<9; j++) {
					g.setColor(Color.gray);
					//la couleur des case sans mine une fois reveler
					if(reveler[i][j]==true) {
						g.setColor(Color.blue);
						
						if(mines[i][j]==1) {
							g.setColor(Color.red);
						}
					}
					//pointage de la souris
					if(mx>=espace+i*80 && mx<espace+i*80+80-2*espace && my>=espace+j*80+80+26 && my< espace+j*80+26+80+80-2*espace) {
						g.setColor(Color.LIGHT_GRAY); 
					}
					
					g.fillRect( espace+i*80, espace+j*80+80, 80-2*espace,80-2*espace);
					//
					if(reveler[i][j]==true) {
						g.setColor(Color.black);
						if(mines[i][j]==0) {
							g.setFont(new Font("SansSerif",Font.BOLD,40));
							g.drawString(Integer.toString(neighbours[i][j]), i*80+27, j*80+80+55);	
						}
						
						//forme de mines
						else {
							g.fillRect( i*80+10+20, +j*80+80+20, 20,40);
							g.fillRect( i*80+20 , j*80+80+ 10+20, 40,20);
							g.fillRect( i*80+5+20, j*80+80+5+20, 30,30);
						}
						
					}
					
					if(nbre_click_droit[i][j]==2 && reveler[i][j]==false) {
						g.setColor(Color.white);
						g.setFont(new Font("SansSerif",Font.BOLD,40));
						g.drawString("?", i*80+27, j*80+112+15+2);
						
					}
					
					
					if(nbre_click_droit[i][j]==1) {
						//inserer le drapeau dans la case
						g.setColor(Color.white);
						g.fillRect( i*80+27, j*80+112+25, 20, 5);
						g.fillRect( i*80+27+5, j*80+112+15, 10, 10);
						g.fillRect( i*80+27+5, j*80+112+5, 10, 10);
						g.fillRect( i*80+27+5, j*80+112, 10, 10);
						g.setColor(Color.red);
						g.fillRect( i*80+27+5-5-2, j*80+112-10+5, 10, 12);
						
						
						
					}
					
					
					
					
					
				}
			}
			
			//g.setColor(Color.yellow);
			// g.fillOval(similex, similey, 70, 70);
			g.setColor(Color.white);
			g.fillRect(similex, similey+2, 70, 70);
			g.setColor(Color.black);
			g.fillRect(similex+25, similey+25, 23, 23);
			g.setColor(Color.white);
			g.setFont(new Font("SansSerif",Font.BOLD,20));
			g.drawString("le nombre de mine restante est: "+nbre_mine, similex-570, similey+35);
			
		//	g.fillOval(similex+15  ,similey+20, 10, 10);
			//g.fillOval(similex+45  ,similey+20, 10, 10);
			
			
			
			
			
	//definition du compteur
			
			g.setColor(Color.black);
			g.fillRect(timex, timey, 150, 70); 
			
			sec = (int) ((new Date().getTime()-startDate.getTime())/1000);
			if(sec>999) {
				sec=999;
			}
			g.setColor(Color.WHITE);
			g.setFont(new Font("SansSerif",Font.PLAIN,80));
			if(sec<10) {
				g.drawString("00"+Integer.toString(sec), timex, timey+65);
			}
			else if(sec<100) {
				g.drawString("0"+Integer.toString(sec), timex, timey+65);
			}else {
				g.drawString(Integer.toString(sec), timex, timey+65);
			}

			for(int i=0; i<1;i++) {
				for (int j=0; j<1;j++ ) {
					i=10;j=10;
					
				
				
					for(int fx=0; fx<16; fx++) {
						for (int fy=0; fy<9; fy++) {
							if(reveler[i_cliquer[0]][i_cliquer[1]]==true & neighbours[i_cliquer[0]][i_cliquer[1]]==0) {
								if(devoiler(i_cliquer[0], i_cliquer[1], fx, fy)==true) {
									
								
									g.setColor(Color.white);
									reveler[fx][fy]=true;
									//reveler[fx][fy]
								}
							}
							
							
							
						}
					}
					
							
				}
			}
		
		
	}

	}
	//evenements
	public class Move implements MouseMotionListener{

		@Override
		public void mouseDragged(java.awt.event.MouseEvent e) {
			// TODO Auto-generated method stub 
			
		}
		  //mouvement de la souris 
		@Override
		public void mouseMoved(java.awt.event.MouseEvent e) {
			  mx=e.getX();
			   my=e.getY();
		
			
		}
		
	}
	public class Click implements MouseListener{
	
//		detection des mouvement des clicks
		@Override
		public void mouseClicked(java.awt.event.MouseEvent e) {
			
			if(e.getButton() == java.awt.event.MouseEvent.BUTTON3) {
			
				
				//nbres de click droit 
				if(dansX()!=-1 && dansY()!=-1) {
					
					nbre_click_droit[dansX()][dansY()] +=1;
					if(nbre_click_droit[dansX()][dansY()]==1) {
						nbre_mine -=1;
					}
					
					
					
					
					if(nbre_click_droit[dansX()][dansY()]==3) {
						
						nbre_click_droit[dansX()][dansY()]=0;
						nbre_mine +=1;
					}
					
				
					
				}
	          }
			
		
			
			 mx=e.getX();
			   my=e.getY();
			//mettons reveler a true pour la case cliquer
		//	System.out.print("-----------cliquer----------\n");
			if(dansX()!=-1 && dansY()!=-1 && !(e.getButton() == java.awt.event.MouseEvent.BUTTON3) ) {
				reveler[dansX()][dansY()]=true;
				i_cliquer[0]=dansX();
				i_cliquer[1]=dansY();
				
			
				
			}
			
			if(dansX()!=-1 && dansY()!=-1) {
			
			}else System.out.print("mauvais\n");
			
		}
	
			
		
				
			
		
		@Override
		public void mousePressed(java.awt.event.MouseEvent e) {
	
			
		}

		@Override
		public void mouseReleased(java.awt.event.MouseEvent e) {
		
			
		}
		
		@Override
		public void mouseEntered(java.awt.event.MouseEvent e) {
		
			
		}
	

		@Override
		public void mouseExited(java.awt.event.MouseEvent e) {
			
			
		}
		public void azero() {
			happiness=true;
			victory=false;
			defeat=false;
		}
		
	}
	//dans la grille alors renvoie de x
	public int dansX() {
		for(int i=0; i<16; i++) {
			for (int j=0; j<9; j++) {
				if(    mx>=espace+i*80&& mx<i*80+80-espace &&my>=espace+j*80+26 && my< espace+j*80+186-espace) {
					return i;  
				}
				
				
			}
		}
		return -1;
		
	}
	//dans la grille alors renvoie de y
	public int dansY() {
		for(int i=0; i<16; i++) {
			for (int j=0; j<9; j++) {
				if(    mx>=espace+i*80&& mx<i*80+80-espace &&my>=espace+j*80+26 && my< espace+j*80+186-espace) {
					return j;
				}	
			}
		}return -1;
		
	}
	public boolean isN(int i,int j, int fx,int fy) {
		if(i-fx<2 && i-fx>-2 && j-fy<2 && j-fy>-2 && mines[fx][fy]==1 ) {
			return true;
		}
		
		return false;
		
	}
	public boolean devoiler(int i,int j, int fx,int fy) {
		if(i-fx<2 && i-fx>-2 && j-fy<2 && j-fy>-2 ) {
			
			return true;
			 	
		}
		return false;
		
	}
}
