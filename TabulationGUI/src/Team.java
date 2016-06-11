import java.awt.Font;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class Team implements Comparable<Team>{
	public Main tabData;
	public String identifier;
	public Competitor member1;
	public Competitor member2;
	public String club;
	public StatPanel statPanel;
	public JPanel rankingPanel;
	int affRounds = 0;
	int negRounds = 0;
	int totalRounds;
	private int wins = 0;
	private int losses = 0;
	public boolean hasClub = false;
	
	
	// integer represents strength of conflict
	public HashMap<Team, Integer> TeamConflicts;
	public Round[] teamResults;
	ArrayList<String> tempOpponents = new ArrayList<String>();
	
	Team(Main tabData, Competitor comp1, Competitor comp2, String name) {
		this.tabData = tabData;
		this.member1 = comp1;
		this.member2 = comp2;
		this.identifier = name;
		this.teamResults = new Round[tabData.numRounds];
	}
	
	Team(Main tabData, Competitor comp1, Competitor comp2, String name, String club) {
		this.tabData = tabData;
		this.member1 = comp1;
		this.member2 = comp2;
		this.identifier = name;
		this.club = club;
		this.hasClub = true;
		this.teamResults = new Round[tabData.numRounds];
	}
	public boolean hadBYE(){
		for (int i = 0; i < this.teamResults.length; i++) {
			if (this.teamResults[i].WinLoss.getText().trim().toUpperCase().equals("BYE")) {
				return true;
			}
		}
		return false;
	}
	
	public boolean hasFaced(Team team){
		for (String teamID : this.tempOpponents) {
			if (teamID.equals(team.identifier)) {
				return true;
			}
		}
		return false;
	}
	
	public boolean hasOpponent(int round){
		if (this.tabData.teamIdentifiers.containsKey(this.teamResults[round-1].opponentID)||this.teamResults[round-1].WinLoss.getText().trim().toUpperCase().equals("BYE")) {
			return true;
		}
		return false;	
	}
	public void addOpponent(int round, Team team) {
		this.teamResults[round-1].opponentID.setText(team.identifier);
	}
	
	public void addOpponent(int round, Team team, String affNeg) {
		this.tempOpponents.add(team.identifier);
		this.teamResults[round-1].opponentID.setText(team.identifier); 
		this.teamResults[round-1].affNeg.setText(affNeg);
		if(affNeg.toUpperCase().equals("AFF")) {
			this.affRounds +=1;
			return;
		}
		this.negRounds +=1;
		/*this.teamResults[round-1].roundPanel.invalidate();
		Component[] panelComps = this.teamResults[round-1].roundPanel.getComponents();
		for (int i = 0; i < panelComps.length; i++) {
			Component comp = panelComps[i];
			comp.revalidate();
			comp.repaint();
		}
		this.teamResults[round-1].roundPanel.revalidate();
		this.teamResults[round-1].roundPanel.repaint();*/
	}
	
	public void addBye(int round) {
		Round r = this.teamResults[round-1];
		r.opponentID.setText("");
		r.WinLoss.setText("BYE");
		r.WinLoss.setFont(new Font(r.WinLoss.getFont().getName(), Font.PLAIN, 16));
		r.affNeg.setText("");
		r.comp1Ranks.setText("");
		r.comp1Speaks.setText("");
		r.comp2Speaks.setText("");
		r.comp2Ranks.setText("");
	}
	
	
	public void removeOpponent(int round) {
		Round r = this.teamResults[round-1];
		for (String teamID : this.tempOpponents) {
			if (teamID.equals(this.teamResults[round-1].opponentID)) {
				this.tempOpponents.remove(teamID);
			}
		}
		r.labelInstantiate();
		r.roundPanel.revalidate();
		r.roundPanel.repaint();
	}
	
	public void addStatPanel(StatPanel stats) {
		this.statPanel = stats;
	}
	public void addRankingPanel(JPanel panel) {
		this.rankingPanel = panel;
	}
	public String getRecord() {
		return this.getWins()+"-"+this.getLosses();
	}
	
	public int getWins() {
		int teamWins = 0;
		for (int i = 0; i < this.teamResults.length; i++) {
			if (this.teamResults[i] != null) {
				if (this.teamResults[i].WinLoss.getText().trim().toUpperCase().equals("W")||this.teamResults[i].WinLoss.getText().trim().toUpperCase().equals("BYE")) {
					teamWins += 1;
				}
			}
		}
		this.wins = teamWins;
		return teamWins;
	}

	public int getLosses() {
		int teamLosses = 0;
		for (int i = 0; i < this.teamResults.length; i++) {
			if (this.teamResults[i] != null) {
				if (this.teamResults[i].WinLoss.getText().trim().toUpperCase().equals("L")) {
					teamLosses += 1;
				}
			}
		}
		this.losses = teamLosses;
		return teamLosses;
	}
	
	public int getSoS() {
		int netSoS = 0;
		for (int i = 0; i < this.teamResults.length; i++) {
			Round r =this.teamResults[i];
			JLabel l = (JLabel) this.teamResults[i].middleTopComponent;
			if(!l.getText().equals("N/A")) {
				for (Team team : this.tabData.teamList) {
					if (team.identifier.equals(l.getText())) {
						netSoS += team.getWins();
						netSoS -= team.getLosses();
					}
				}
			}
		}
		return netSoS;
	}
	
	public Double handleSortMethod(String method) {
		try {
			if (method.equals("Record")) {
				return new Double(this.getWins()-this.getLosses());
			}
			else if (method.equals("Opponent Win-Loss")) {
				return new Double(this.getSoS());
			}
			else if (method.equals("Team Hi-Low Ranks")) {
				return new Double(this.member1.getHiLowRanks()+this.member2.getHiLowRanks());
			}
			else if (method.equals("Team 2x Hi-Low Ranks")) {
				return new Double(this.member1.getDblHiLowRanks()+this.member2.getDblHiLowRanks());
			}
			return new Double(0);
		} catch(Exception NullPointerException) {
		//
		}
		return new Double(0.0);
	}

	public String isMethodHiOrLow(String method) {
		if (method.equals("Record") || method.equals("Opponent Win-Loss")) {
			return "Hi";
		}
		return "Low";
	}
	
	@Override
	public int compareTo(Team o) {
		for (int i = 1; i < this.tabData.rankingPanel.rankingMethods.size()+1; i++) {
			//System.out.println("entered");
			String method = this.tabData.rankingPanel.rankingMethods.get(new Integer(i));
			Double result = this.handleSortMethod(method);
			System.out.println("Sort method: " + method);
			System.out.println("Result: " + result);
			if (Math.abs(result.doubleValue()-o.handleSortMethod(method).doubleValue()) > .01){
				if (this.isMethodHiOrLow(method).equals("Hi")){
					return(o.handleSortMethod(method)).compareTo(result);
				}
				return result.compareTo(o.handleSortMethod(method));
			}
		}
		return 0;
	}
	
	@Override
	public String toString() {
		return this.member1.name + "/" + this.member2.name;
	}
}
