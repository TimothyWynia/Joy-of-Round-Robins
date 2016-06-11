import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JMenu;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;

public class MyMenuListener implements MenuListener,MouseListener {
	Main tabulationMain;
	JMenu menu;
	
	MyMenuListener(JMenu menu, Main main) {
		this.menu = menu;
		this.tabulationMain = main;
	}

	@Override
	public void menuCanceled(MenuEvent e) {
		// TODO Auto-generated method stub.
		
	}

	@Override
	public void menuDeselected(MenuEvent e) {
		// TODO Auto-generated method stub.
		
	}

	@Override
	public void menuSelected(MenuEvent e) {
		/*// TODO Auto-generated method st
		JMenu menu = (JMenu) e.getSource();
		if (menu.getText().equals("Save File")) {
			this.tabulationMain.saveGameToFile();
		}
		else if(menu.getText().equals("Load Tabulation File")){
			this.tabulationMain.readCCAFile();
		}*/
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub.
		JMenu menu = (JMenu) e.getSource();
		if (menu.getText().equals("Save File")) {
			this.tabulationMain.saveGameToFile();
		}
		else if(menu.getText().equals("Load Tabulation File")){
			this.tabulationMain.readCCAFile(null);
		}
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub.
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub.
		
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub.
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub.
		
	}

}
