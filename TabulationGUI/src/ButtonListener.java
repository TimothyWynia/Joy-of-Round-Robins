import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

public class ButtonListener implements ActionListener{

	RankingPanel rankingPanel;
	
	ButtonListener(RankingPanel rankingPanel) {
		this.rankingPanel = rankingPanel;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub.
		if(((JButton) e.getSource()).getText().equals("Save Tabulation")){
			this.rankingPanel.tabulationGame.saveGameToFile();
		}
	}

}
