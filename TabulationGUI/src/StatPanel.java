import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;


public class StatPanel{
	Team team;
	JPanel statPanel;
	JPanel topPanel;
	JPanel middlePanel;
	JPanel bottomPanel;	
	JLabel comp1Speaks;
	Double comp1SpeakerPoints;
	Integer comp1SpeakerRanks;	
	JLabel comp2Speaks;
	Double comp2SpeakerPoints;
	Integer comp2SpeakerRanks;
	JLabel teamSpeaks;
	Double teamSpeakerPoints;
	JLabel teamRanks;
	Integer teamSpeakerRanks;
	JLabel teamRecord;
	Integer teamWins;
	Integer teamLosses;
	
	StatPanel(Team team) {
		this.team = team;
		this.statPanel = new JPanel();
		this.team.addStatPanel(this);
		this.labelInstantiate();
		this.topPanelInstantiate();
		this.middlePanelInstantiate();
		this.bottomPanelInstantiate();
		this.initializeGraphics();
		this.updateLabels();
	}
	
	public void labelInstantiate() {
		this.comp1Speaks = new JLabel();
		this.comp1Speaks.setText("0.0/0");
		
		this.comp2Speaks = new JLabel();
		this.comp2Speaks.setText("0.0/0");
		
		this.teamSpeaks = new JLabel();
		this.teamSpeaks.setText("0.0");

		this.teamRanks = new JLabel();
		this.teamRanks.setText("0");
		
		this.teamRecord = new JLabel();
		this.teamRecord.setText("0-0");
		this.teamRecord.setFont(new Font(this.teamRecord.getFont().getName(), Font.PLAIN, 25));
	}
	
	public void topPanelInstantiate() {
		this.topPanel = new JPanel();
		this.topPanel.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.weighty = 1;
		gbc.gridx = 0;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.weightx = .7;
		gbc.anchor = GridBagConstraints.FIRST_LINE_START;
		gbc.insets = new Insets(0,5,0,0);
		
		this.topPanel.add(this.comp1Speaks, gbc);
		
		gbc.gridx = 1;
		gbc.weightx = .3;
		gbc.insets = new Insets(0,0,0,0);
		gbc.anchor = GridBagConstraints.LAST_LINE_END;
		this.topPanel.add(this.teamSpeaks, gbc);		
	}
	
	public void middlePanelInstantiate() {
		this.middlePanel = new JPanel();
		this.middlePanel.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.BOTH;
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.weightx = 1;
		gbc.weighty = 1;
		gbc.anchor = GridBagConstraints.CENTER;
		this.teamRecord.setHorizontalAlignment(SwingConstants.CENTER);
		this.middlePanel.add(this.teamRecord, gbc);		
	}
	
	public void bottomPanelInstantiate() {
		this.bottomPanel = new JPanel();
		this.bottomPanel.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.weighty = 1;
		gbc.gridx = 0;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.weightx = .7;
		gbc.anchor = GridBagConstraints.LAST_LINE_START;
		gbc.insets = new Insets(0,5,0,0);
		this.bottomPanel.add(this.comp2Speaks, gbc);
		
		gbc.gridx = 1;
		gbc.weightx = .3;
		gbc.anchor = GridBagConstraints.LAST_LINE_END;
		gbc.insets = new Insets(0,0,0,0);
		this.bottomPanel.add(this.teamRanks, gbc);
	}
	
	public void initializeGraphics() {
		this.statPanel.removeAll();
		this.statPanel.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.weightx = 1;
		gbc.weighty = .1;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.anchor = GridBagConstraints.PAGE_START;
		this.statPanel.add(this.topPanel, gbc);
		gbc.gridy = 1;
		gbc.weighty = .8;
		gbc.anchor = GridBagConstraints.CENTER;
		this.statPanel.add(this.middlePanel, gbc);
		gbc.gridy = 2;
		gbc.weighty = .1;
		gbc.anchor = GridBagConstraints.PAGE_END;
		this.statPanel.add(this.bottomPanel, gbc);		
	}
	
	@SuppressWarnings("boxing")
	public void updateLabels() {
		Double comp1pts = this.team.member1.getPoints();
		this.comp1SpeakerPoints = comp1pts;
		String comp1points = comp1pts.toString();
		Integer comp1ranks = this.team.member1.getRanks();
		this.comp1SpeakerRanks = comp1ranks;
		String comp1Ranks = comp1ranks.toString();
		this.comp1Speaks.setText(comp1points+"/"+ comp1Ranks);
		
		Double comp2pts = this.team.member2.getPoints();
		this.comp2SpeakerPoints = comp2pts;
		String comp2points = comp2pts.toString();
		Integer comp2ranks = this.team.member2.getRanks();
		this.comp2SpeakerRanks = comp2ranks;
		String comp2Ranks = comp2ranks.toString();
		this.comp2Speaks.setText(comp2points+"/"+comp2Ranks);
		
		this.teamSpeaks.setText((comp1pts+comp2pts)+"");
		this.teamSpeakerPoints = comp1pts+comp2pts;
		
		this.teamRanks.setText((comp1ranks+comp2ranks)+"");
		this.teamSpeakerRanks = comp1ranks+comp2ranks;
		
		this.teamWins = this.team.getWins();
		this.teamLosses = this.team.getLosses();
		this.teamRecord.setText(this.teamWins + "-"+ this.teamLosses);
		
		this.statPanel.revalidate();
		this.statPanel.repaint();
	}
	
	public JPanel getStatPanel() {
		return this.statPanel;
	}
}
