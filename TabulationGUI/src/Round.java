import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class Round {
	JFrame frame;
	
	JPanel roundPanel;
	JPanel leftPanel;
	JPanel centerPanel;
	JPanel rightPanel;
	
	JLabel comp1Speaks;
	JLabel comp2Speaks;
	JLabel comp1Ranks;
	JLabel comp2Ranks;
	JLabel affNeg;
	JLabel WinLoss;
	JLabel opponentID;
	
	JComponent leftMiddleComponent;
	JComponent leftBottomComponent;
	JComponent middleTopComponent;
	JComponent middleMiddleComponent;
	JComponent middleBottomComponent;
	JComponent rightMiddleComponent;
	JComponent rightBottomComponent;	
	
	JRadioButton editButton;
	RadioButtonListener buttonListener;
	
	Team owner;
	Team AFF;
	Team NEG;
	int roundNumber;
	Color standardColor;
	
	Round(JFrame frame, JPanel panel, Team owner, int round) {
		this.frame = frame;
		this.roundPanel = panel;
		this.standardColor = this.frame.getBackground();
		GridBagLayout gbl = new GridBagLayout();
		this.roundPanel.setLayout(gbl);
		this.owner = owner;
		this.roundNumber = round;
		this.labelInstantiate();
		this.rightPanelInstantiate();
		this.centerPanelInstantiate();
		this.leftPanelInstantiate();
		this.panelSetup();
	}
	
	Round(Team owner, int roundNumber, String oppID, String affNeg, String winLoss, double comp1Speaks, double comp1Ranks, double comp2Speaks, double comp2Ranks){
		this.owner = owner;
		this.roundNumber = roundNumber;
		this.altLabelInstantiate(oppID, affNeg, winLoss, comp1Speaks, comp1Ranks, comp2Speaks, comp2Ranks);
	}
	public void addGraphics(JFrame frame, JPanel panel) {
		this.frame = frame;
		this.roundPanel = panel;
		this.rightPanelInstantiate();
		this.centerPanelInstantiate();
		this.leftPanelInstantiate();
		this.panelSetup();
	}
	
	
	@SuppressWarnings("hiding")
	public void altLabelInstantiate(String oppID, String affNeg, String winLoss, double comp1Speaks, double comp1Ranks, double comp2Speaks, double comp2Ranks){
		this.editButton = new JRadioButton();
		this.buttonListener = new RadioButtonListener(this.editButton, this, this.owner);
		this.editButton.addActionListener(this.buttonListener);
		
		this.opponentID = new JLabel();
		this.opponentID.setOpaque(true);
		this.opponentID.setHorizontalAlignment(SwingConstants.CENTER);
		this.opponentID.setText(oppID);
		
		this.comp1Speaks = new JLabel();
		this.comp1Speaks.setText(" " + comp1Speaks);
		this.comp1Speaks.setOpaque(true);
		this.comp1Speaks.setHorizontalAlignment(SwingConstants.LEFT);
		
		this.WinLoss = new JLabel();
		this.WinLoss.setHorizontalAlignment(SwingConstants.CENTER);
		if (winLoss.toUpperCase().equals("BYE")){
			this.WinLoss.setText(winLoss);
		} else {
			this.WinLoss.setText(" " + winLoss);
		}
		this.WinLoss.setOpaque(true);
		this.WinLoss.setFont(new Font(this.WinLoss.getFont().getName(), Font.PLAIN, 20));
		
		this.comp1Ranks = new JLabel();
		this.comp1Ranks.setText(new Double(comp1Ranks).toString());
		this.comp1Ranks.setOpaque(true);
		this.comp1Ranks.setHorizontalAlignment(SwingConstants.RIGHT);
		
		this.comp2Speaks = new JLabel();
		this.comp2Speaks.setText(" " + comp2Speaks);
		this.comp2Speaks.setOpaque(true);
		this.comp2Speaks.setHorizontalAlignment(SwingConstants.LEFT);
		
		this.affNeg = new JLabel();
		this.affNeg.setText(affNeg);
		this.affNeg.setOpaque(true);
		this.affNeg.setHorizontalAlignment(SwingConstants.CENTER);
		
		this.comp2Ranks = new JLabel();
		this.comp2Ranks.setText(new Double(comp2Ranks).toString());
		this.comp2Ranks.setOpaque(true);
		this.comp2Ranks.setHorizontalAlignment(SwingConstants.RIGHT);		
	}
	
	public void labelInstantiate() {
		this.editButton = new JRadioButton();
		this.buttonListener = new RadioButtonListener(this.editButton, this, this.owner);
		this.editButton.addActionListener(this.buttonListener);
		
		this.opponentID = new JLabel();
		this.opponentID.setOpaque(true);
		this.opponentID.setHorizontalAlignment(SwingConstants.CENTER);
		this.opponentID.setText("N/A");
		
		this.comp1Speaks = new JLabel();
		this.comp1Speaks.setText(" 0.0");
		this.comp1Speaks.setOpaque(true);
		this.comp1Speaks.setHorizontalAlignment(SwingConstants.LEFT);
		
		this.WinLoss = new JLabel();
		this.WinLoss.setHorizontalAlignment(SwingConstants.CENTER);
		this.WinLoss.setText(" NA");
		this.WinLoss.setOpaque(true);
		this.WinLoss.setFont(new Font(this.WinLoss.getFont().getName(), Font.PLAIN, 20));
		
		this.comp1Ranks = new JLabel();
		this.comp1Ranks.setText("0.0");
		this.comp1Ranks.setOpaque(true);
		this.comp1Ranks.setHorizontalAlignment(SwingConstants.RIGHT);
		
		this.comp2Speaks = new JLabel();
		this.comp2Speaks.setText(" 0.0");
		this.comp2Speaks.setOpaque(true);
		this.comp2Speaks.setHorizontalAlignment(SwingConstants.LEFT);
		
		this.affNeg = new JLabel();
		this.affNeg.setText("Null");
		this.affNeg.setOpaque(true);
		this.affNeg.setHorizontalAlignment(SwingConstants.CENTER);
		
		this.comp2Ranks = new JLabel();
		this.comp2Ranks.setText("0.0");
		this.comp2Ranks.setOpaque(true);
		this.comp2Ranks.setHorizontalAlignment(SwingConstants.RIGHT);		
	}
	
	public void panelSetup() {
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.weighty = 1;
		gbc.fill = GridBagConstraints.BOTH;
		this.roundPanel.removeAll();
		this.roundPanel.setLayout(new GridBagLayout());
			
		gbc.gridx = 0;
		gbc.weightx = .3;
		this.roundPanel.add(this.rightPanel, gbc);
	
		gbc.gridx = 1;
		gbc.weightx = .3;
		this.roundPanel.add(this.centerPanel, gbc);
		
		gbc.gridx = 2;
		gbc.weightx = 0;
		this.roundPanel.add(this.leftPanel, gbc);	
	}
	
	public void centerPanelInstantiate() {
		this.centerPanel = new JPanel();
		this.centerPanel.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.weightx = 1;
		gbc.fill = GridBagConstraints.BOTH;
		
		gbc.gridy = 0;
		gbc.weighty = 0;
		gbc.anchor = GridBagConstraints.PAGE_START;
		this.middleTopComponent = this.opponentID;
		this.centerPanel.add(this.middleTopComponent, gbc);
		
		gbc.gridy = 1;
		gbc.weighty = .8;
		gbc.anchor  = GridBagConstraints.CENTER;
		this.middleMiddleComponent = this.WinLoss;
		this.centerPanel.add(this.middleMiddleComponent, gbc);
		
		gbc.gridy = 2;
		gbc.weighty = .2;
		gbc.anchor = GridBagConstraints.PAGE_END;
		this.middleBottomComponent = this.affNeg;
		this.centerPanel.add(this.middleBottomComponent, gbc);		
	}
	
	public void rightPanelInstantiate() {
		this.rightPanel = new JPanel();
		this.rightPanel.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.weightx = 1;
		gbc.fill = GridBagConstraints.BOTH;
		
		JPanel emptyPanel = new JPanel();
		gbc.gridy = 0;
		gbc.weighty = 1;
		
		this.rightPanel.add(emptyPanel, gbc);
		gbc.gridy = 1;
		gbc.weighty = 0;
		gbc.anchor = GridBagConstraints.LAST_LINE_END;
		this.rightMiddleComponent = this.comp1Speaks;
		this.rightPanel.add(this.rightMiddleComponent, gbc);
		
		gbc.gridy = 2;
		gbc.weighty = 0;
		this.rightBottomComponent = this.comp2Speaks;
		this.rightPanel.add(this.rightBottomComponent, gbc);
	}
	
	public void leftPanelInstantiate() {
		this.leftPanel = new JPanel();
		this.leftPanel.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.weightx = 1;
		gbc.fill = GridBagConstraints.BOTH;
		
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new GridBagLayout());
		GridBagConstraints gbcbp = new GridBagConstraints();
		gbcbp.fill = GridBagConstraints.BOTH;
		gbcbp.weighty = 1;
		
		JPanel emptySpace = new JPanel();
		gbcbp.weightx = 1;
		gbcbp.gridx = 0;
		gbcbp.gridheight = 2;
		
		buttonPanel.add(emptySpace, gbcbp);
		gbcbp.gridx = 1;
		gbcbp.anchor = GridBagConstraints.FIRST_LINE_END;
		gbcbp.weightx = 0;
		gbcbp.weighty = 0;
		gbcbp.gridheight = 1;
		buttonPanel.add(this.editButton, gbcbp);
		
		JPanel emptySpace1 = new JPanel();
		gbcbp.gridx = 1;
		gbcbp.gridy = 1;
		gbcbp.weighty = 1;
		buttonPanel.add(emptySpace1, gbcbp);
		
		gbc.gridy = 0;
		gbc.weighty = 1;
		gbc.anchor = GridBagConstraints.FIRST_LINE_END;
		this.leftPanel.add(buttonPanel, gbc);
		
		gbc.gridy = 1;
		gbc.weighty = 0;
		gbc.anchor = GridBagConstraints.LAST_LINE_END;
		this.leftMiddleComponent = this.comp1Ranks;
		this.leftPanel.add(this.leftMiddleComponent, gbc);
		
		gbc.gridy = 2;
		gbc.weighty = 0;
		gbc.anchor = GridBagConstraints.LAST_LINE_END;
		this.leftBottomComponent = this.comp2Ranks;
		this.leftPanel.add(this.leftBottomComponent, gbc);
	}
	
	public void rightPanelTextEntry() {
		this.rightPanel = null;
		this.rightPanel = new JPanel();
		this.rightPanel.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.weightx = 1;
		gbc.fill = GridBagConstraints.BOTH;
		
		JPanel emptyPanel = new JPanel();
		gbc.gridy = 0;
		gbc.weighty = 1;
		
		this.rightPanel.add(emptyPanel, gbc);
		gbc.gridy = 1;
		gbc.weighty = 0;
		this.rightMiddleComponent = new JTextField(this.comp1Speaks.getText());
		this.rightMiddleComponent.setToolTipText("Please enter " + this.owner.member1.name + "'s speaker points");
		this.rightPanel.add(this.rightMiddleComponent, gbc);
		
		gbc.gridy = 2;
		gbc.weighty = 0;
		this.rightBottomComponent = new JTextField(this.comp2Speaks.getText());
		this.rightBottomComponent.setToolTipText("Please enter " + this.owner.member2.name + "'s speaker points");
		this.rightPanel.add(this.rightBottomComponent, gbc);
	}
	
	public void middlePanelTextEntry() {
		this.centerPanel = null;
		this.centerPanel = new JPanel();
		this.centerPanel.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.weightx = 1;
		gbc.fill = GridBagConstraints.BOTH;
		
		gbc.gridy = 0;
		gbc.weighty = 0;
		gbc.anchor = GridBagConstraints.PAGE_START;
		this.middleTopComponent = new JTextField(this.opponentID.getText());
		this.middleTopComponent.setToolTipText("Enter the opponent's team number ");
		this.centerPanel.add(this.middleTopComponent, gbc);
		
		gbc.gridy = 1;
		gbc.weighty = 1;
		gbc.anchor  = GridBagConstraints.PAGE_END;		
		this.middleMiddleComponent = new JTextField(this.WinLoss.getText());
		this.middleMiddleComponent.setToolTipText("Enter 'W' for win or 'L' for loss");
		this.centerPanel.add(this.middleMiddleComponent, gbc);
		
		gbc.gridy = 2;
		gbc.weighty = 0;
		gbc.anchor = GridBagConstraints.PAGE_END;
		this.middleBottomComponent = new JTextField(this.affNeg.getText());
		this.middleBottomComponent.setToolTipText("Enter 'AFF' or 'NEG'");
		this.centerPanel.add(this.middleBottomComponent, gbc);	
	}
	
	public void leftPanelTextEntry() {
		this.leftPanel = new JPanel();
		this.leftPanel.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.weightx = 1;
		gbc.fill = GridBagConstraints.BOTH;
		
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new GridBagLayout());
		GridBagConstraints gbcbp = new GridBagConstraints();
		gbcbp.fill = GridBagConstraints.BOTH;
		gbcbp.weighty = 1;
		
		JPanel emptySpace = new JPanel();
		gbcbp.weightx = 1;
		gbcbp.gridx = 0;
		gbcbp.gridheight = 2;
		
		buttonPanel.add(emptySpace, gbcbp);
		gbcbp.gridx = 1;
		gbcbp.anchor = GridBagConstraints.FIRST_LINE_END;
		gbcbp.weightx = 0;
		gbcbp.weighty = 0;
		gbcbp.gridheight = 1;
		buttonPanel.add(this.editButton, gbcbp);
		
		JPanel emptySpace1 = new JPanel();
		gbcbp.gridx = 1;
		gbcbp.gridy = 1;
		gbcbp.weighty = 1;
		buttonPanel.add(emptySpace1, gbcbp);
		
		gbc.gridy = 0;
		gbc.weighty = 1;
		gbc.anchor = GridBagConstraints.FIRST_LINE_END;
		this.leftPanel.add(buttonPanel, gbc);
		
		gbc.gridy = 1;
		gbc.weighty = 0;
		gbc.anchor = GridBagConstraints.LAST_LINE_END;
		this.leftMiddleComponent = new JTextField(this.comp1Ranks.getText());
		this.leftMiddleComponent.setToolTipText("Please enter " + this.owner.member1.name + "'s speaker rank");
		this.leftPanel.add(this.leftMiddleComponent, gbc);
		
		gbc.gridy = 2;
		gbc.weighty = 0;
		gbc.anchor = GridBagConstraints.LAST_LINE_END;
		this.leftBottomComponent = new JTextField(this.comp2Ranks.getText());
		this.leftBottomComponent.setToolTipText("Please enter " + this.owner.member2.name+ "'s speaker rank");
		this.leftPanel.add(this.leftBottomComponent, gbc);
	}	
	
	// create everything with textfields
	public void panelTextSetup(){
		this.rightPanelTextEntry();
		this.middlePanelTextEntry();
		this.leftPanelTextEntry();
		this.panelSetup();
		this.frame.revalidate();
		this.frame.repaint();
	}
	// creates everything with labels
	public void panelGraphicsUpdate() {
		this.rightPanelInstantiate();
		this.centerPanelInstantiate();
		this.leftPanelInstantiate();
		this.panelSetup();
		this.frame.revalidate();
		this.frame.repaint();
	}
	@SuppressWarnings("hiding")
	public void displayMode(){
		this.changePanelColors(this.standardColor);
		Double mem1Speaks = null;
		JTextField comp1Speaks = (JTextField) this.rightMiddleComponent;
		if(!comp1Speaks.getText().trim().equals("")){
			try {
				this.comp1Speaks.setText(" " + Double.parseDouble(comp1Speaks.getText().trim()));
				mem1Speaks = new Double(Double.parseDouble(comp1Speaks.getText().trim()));
			} catch (NumberFormatException ex) {
				//
			}
		}
		Double mem2Speaks = null;
		JTextField comp2Speaks = (JTextField) this.rightBottomComponent;
		if (!comp2Speaks.getText().trim().equals("")){
			try {
				this.comp2Speaks.setText(" " + Double.parseDouble(comp2Speaks.getText().trim()));
				mem2Speaks = new Double(Double.parseDouble(comp2Speaks.getText().trim()));
			} catch (NumberFormatException ex) {
				//
			}
		}
		JTextField WinLoss = (JTextField) this.middleMiddleComponent;
		if (!WinLoss.getText().trim().equals("")) {
			this.WinLoss.setText(WinLoss.getText());
		}
		JTextField affNeg = (JTextField) this.middleBottomComponent;
		if (affNeg.getText().trim().equals("AFF") || affNeg.getText().trim().equals("NEG")) {
			this.affNeg.setText(affNeg.getText());
		}
		Integer mem1Ranks = null;
		JTextField comp1Ranks = (JTextField) this.leftMiddleComponent;
		if (!comp1Ranks.getText().trim().equals("")) {
			try {
				this.comp1Ranks.setText(Integer.parseInt(comp1Ranks.getText().trim()) + " ");
				mem1Ranks = new Integer(Integer.parseInt(comp1Ranks.getText().trim()));
			} catch (NumberFormatException ex) {
				//
			}			
		}
		Integer mem2Ranks = null;
		JTextField comp2Ranks = (JTextField) this.leftBottomComponent;
		if(!comp2Ranks.getText().trim().equals("")) {
			try {
				this.comp2Ranks.setText(Integer.parseInt(comp2Ranks.getText().trim()) + " ");
				mem2Ranks = new Integer(Integer.parseInt(comp2Ranks.getText().trim()));
			} catch (NumberFormatException ex) {
				//
			}		
		}
		if (mem1Speaks!= null && mem1Ranks != null && mem2Speaks != null && mem2Ranks != null) {
			this.owner.member1.addResult(mem1Speaks, mem1Ranks, new Integer(this.roundNumber));
			this.owner.member2.addResult(mem2Speaks, mem2Ranks, new Integer(this.roundNumber));
			if (mem1Speaks.doubleValue() > mem2Speaks.doubleValue() && mem2Ranks.intValue() < mem1Ranks.intValue()) {
				this.changePanelColors(Color.red);
			} else if (mem2Speaks.doubleValue() > mem1Speaks.doubleValue() && mem1Ranks.intValue() < mem2Ranks.intValue()) {
				System.out.println("color change");
				this.changePanelColors(Color.red);
			}
		}
		JTextField oppID = (JTextField) this.middleTopComponent;
		if (!oppID.getText().equals("")) {
			this.opponentID.setText(oppID.getText());
		}
		
		this.panelGraphicsUpdate();
		this.owner.tabData.rankingPanel.masterPanelInstantiate();
		this.owner.tabData.rankingPanel.instantiateOuterPanel();
		this.owner.tabData.tabbedPane.removeTabAt(1);
		this.owner.tabData.tabbedPane.addTab("Ranking Panel", this.owner.tabData.rankingPanel.outerPanel);
		//this.owner.tabData.rankingPanel.getPanel();
	}
	public void changePanelColors(Color c) {
		this.rightPanel.setBackground(c);
		this.centerPanel.setBackground(c);
		this.leftPanel.setBackground(c);
	}
	
	public JPanel getRoundPanel() {
		return this.roundPanel;
	}
	
	
}
