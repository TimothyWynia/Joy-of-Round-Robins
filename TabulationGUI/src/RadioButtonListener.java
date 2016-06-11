import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JRadioButton;

public class RadioButtonListener implements ActionListener,MouseListener{

	Round round;
	Team team;
	JRadioButton button;
	
	RadioButtonListener(JRadioButton button, Round round, Team team) {
		this.round = round;
		this.team = team;
		this.button = button;
		this.button.setToolTipText("Select this to edit round data, deselect once \n you are done entering data");
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		if (this.button.isSelected()) {
			this.round.panelTextSetup();
		} else {
			this.round.displayMode();
			this.team.statPanel.updateLabels();
		}
		
	}
	
	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub.
		
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
