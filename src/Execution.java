
public class Execution implements Runnable {
	PrincipalW mafenetre =new PrincipalW();
	
	
	public static void main(String[] args) {
      new Thread (new Execution()).start();
	}
public void run() {
	while(true) {
		mafenetre.repaint();
		
			
		}
		
	}
}

