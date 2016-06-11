import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

public class RankingPanel {
	ArrayList<Team> teamList;
	ArrayList<Team> sortedTeams;
	HashMap<Integer, ArrayList<Team>> methodRankings;
	Main tabulationGame;
	JPanel mainPanel;
	JPanel listPanel;
	JPanel rankingPanel;
	JPanel outerPanel;
	//ArrayList<JPanel> teamPanels;
	HashMap<Integer, String> rankingMethods;
	HashMap<String, JLabel> rankingLabels;
	GridBagConstraints updateConstants;
	JPanel[][] panelArray;
	
	RankingPanel(ArrayList<Team> teamList, Main game) {
		this.teamList = teamList;
		this.tabulationGame = game;
		this.rankingMethodsInstantiate();
		this.masterPanelInstantiate();
		this.instantiateOuterPanel();
		this.panelArray = new JPanel[teamList.size()][2+this.rankingMethods.size()*2];
	}
	
	public void masterPanelInstantiate() {
		this.mainPanel = new JPanel(new GridBagLayout());
		//this.mainPanel.setPreferredSize(new Dimension(1000,1000));
		this.mainPanel.setName("Ranking Panel");
		this.rankingLabels = new HashMap<String, JLabel>();
		GridBagConstraints gbc = new GridBagConstraints();
		for (int i = 1; i < this.rankingMethods.size() + 1; i++) {
			String methodName = this.rankingMethods.get(new Integer(i));
			JLabel methodLabel = new JLabel(methodName);
			methodLabel.setHorizontalAlignment(SwingConstants.CENTER);
			this.rankingLabels.put(methodName,methodLabel);			
		}
		System.out.println("Ranking Label Size: " + this.rankingLabels);
		JPanel teamNamePanel = new JPanel(new GridLayout(1,1));
		teamNamePanel.setPreferredSize(new Dimension(130,50));
		JLabel teamNameLabel = new JLabel("Team Name");
		teamNameLabel.setBorder(new EmptyBorder(0,5,0,5));
		teamNamePanel.add(teamNameLabel);
		teamNamePanel.setBorder(BorderFactory.createLineBorder(Color.black));
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.weightx = .3;
		gbc.weighty = 1;
		this.mainPanel.add(teamNamePanel, gbc);
		JPanel teamRankPanel = new JPanel(new GridLayout(1,1));
		JLabel teamRankLabel = new JLabel("Team Rank");
		teamRankLabel.setBorder(new EmptyBorder(0,5,0,5));
		teamRankPanel.add(teamRankLabel);
		teamRankPanel.setBorder(BorderFactory.createLineBorder(Color.black));
		gbc.gridx = 1;
		gbc.weightx = .1;
		this.mainPanel.add(teamRankPanel, gbc);
		System.out.println("Ranking Methods Size: " + this.rankingMethods);
		for (int i = 1; i < this.rankingMethods.size()+1; i++) {
			gbc.gridx++;
			JPanel methodPanel = new JPanel(new GridLayout(1,1));
			JLabel methodLabel = this.rankingLabels.get(this.rankingMethods.get(new Integer(i)));
			methodLabel.setBorder(new EmptyBorder(0,5,0,5));
			methodPanel.add(methodLabel);
			methodPanel.setBorder(BorderFactory.createLineBorder(Color.black));
			this.mainPanel.add(methodPanel, gbc);
			JPanel rankPanel = new JPanel(new GridLayout(1,1));
			JLabel rankLabel = new JLabel("Rank");
			rankLabel.setBorder(new EmptyBorder(0,5,0,5));
			gbc.gridx++;
			rankPanel.add(rankLabel);
			rankPanel.setBorder(BorderFactory.createLineBorder(Color.black));
			this.mainPanel.add(rankPanel, gbc);
		}
		this.updateConstants = (GridBagConstraints) gbc.clone();
		this.update(gbc);
	}	
	
	public void rankingMethodsInstantiate(){
		this.rankingMethods = new HashMap<Integer, String>();
		this.rankingMethods.put(new Integer(1), "Record");
		this.rankingMethods.put(new Integer(2), "Opponent Win-Loss");
		this.rankingMethods.put(new Integer(3), "Team Hi-Low Ranks");
		this.rankingMethods.put(new Integer(4), "Team 2x Hi-Low Ranks");
	}
	public void setRankingMethods(HashMap<Integer, String> data) {
		this.rankingMethods = data;
	}
	
	public int getTeamRank(Team team, String methodName) {
		ArrayList<Team> sortedTeam = this.sortByMethod(methodName);
		int rank = 1;
		for (int i = 0; i < sortedTeam.size(); i++) {
			if (sortedTeam.get(i).equals(team)) {
				return rank;
			}
			if (i == sortedTeam.size()-1) {
				return rank+1;
			}
			Team current = sortedTeam.get(i);
			Team next = sortedTeam.get(i+1);
			if (!current.handleSortMethod(methodName).equals(next.handleSortMethod(methodName))) {
				rank = i + 2;
			}
		}
		return rank;
	}
	
	public ArrayList<Team> sortByMethod(String methodName) {
		ArrayList<Team> unsortedTeams = new ArrayList<Team>(this.teamList);
		ArrayList<Team> sortedTeamArrayList = new ArrayList<Team>();
		sortedTeamArrayList.add(this.teamList.get(0));
		for (int i = 1; i < unsortedTeams.size(); i++) {
			Team team = unsortedTeams.get(i);
			Double teamValue = team.handleSortMethod(methodName);
			for (int j = 0; j < sortedTeamArrayList.size(); j++) {
					Team toComp = sortedTeamArrayList.get(j);
					Double toCompValue = team.handleSortMethod(methodName);
					String hiOrLow = team.isMethodHiOrLow(methodName);
					if (teamValue.doubleValue() > toCompValue.doubleValue() && hiOrLow.equals("Hi")) {
						sortedTeamArrayList.add(j, team);
						break;
					}
					if (teamValue.doubleValue() < toCompValue.doubleValue() && hiOrLow.equals("Low")) {
						sortedTeamArrayList.add(j, team);
						break;
					}
			}
			if (!sortedTeamArrayList.contains(team)){
				sortedTeamArrayList.add(team);
			}
		}
		
		return sortedTeamArrayList;
	}
	
	/*public ArrayList<Team> getSortedTeams(ArrayList<Team> teams, HashMap<Integer, String> sortingMethods) {
		ArrayList<Team> returnedTeams = new ArrayList<Team>();
		return returnedTeams;
	}*/
	
	public void instantiateOuterPanel() {
		JButton sortButton = new JButton("Save Tabulation");
		ButtonListener myButtonListener = new ButtonListener(this);
		sortButton.addActionListener(myButtonListener);
		this.outerPanel = new JPanel(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.BOTH;
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.weightx = 0;
		gbc.weighty = 0;
		this.outerPanel.add(sortButton, gbc);
		gbc.gridy = 1;
		this.outerPanel.add(this.mainPanel, gbc);
		JPanel rightFiller = new JPanel();
		gbc.gridy = 0;
		gbc.gridx = 1;
		gbc.gridheight = 2;
		gbc.weightx = 1;
		this.outerPanel.add(rightFiller, gbc);
		
		JPanel bottomFiller = new JPanel();
		gbc.gridheight = 1;
		gbc.gridx = 0;
		gbc.weighty = 1;
		gbc.gridwidth = 2;
		gbc.gridy = 2;
		this.outerPanel.add(bottomFiller, gbc);
	}
	
	public JPanel getPanel() {
		this.outerPanel.revalidate();
		//System.out.println("------------------Tab added---------------");
		//this.tabulationGame.tabbedPane.addTab("Test", this.outerPanel);
		return this.outerPanel;
	}
	
	public void update(GridBagConstraints GBC) {
		this.tabulationGame.rankingPanel = this;
		GridBagConstraints gbc = GBC;
		//System.out.println("Team List: " + this.teamList.toString());
		Collections.sort(this.teamList);
		ArrayList<Team> sortedTeam = new ArrayList<Team>(this.teamList);
		//System.out.println(sortedTeam);
		//System.out.println("Sorted Team List: " + sortedTeam.toString());
		for (int i = 0; i < sortedTeam.size(); i++) {
			Team team = sortedTeam.get(i);
			gbc.gridy +=1;
			gbc.gridx = 0;
			gbc.weightx = .5;
			JPanel teamNamePanel1 = new JPanel(new GridLayout(3,1));
			teamNamePanel1.add(new JLabel(" " + team.member1.name));
			teamNamePanel1.add(new JLabel(" " + team.identifier));
			teamNamePanel1.add(new JLabel(" " + team.member2.name));
			teamNamePanel1.setBorder(BorderFactory.createLineBorder(Color.black));
			this.mainPanel.add(teamNamePanel1, gbc);
			gbc.gridx +=1;
			JPanel teamRankPanel1 = new JPanel(new GridLayout(1,1));
			teamRankPanel1.setBorder(BorderFactory.createLineBorder(Color.black));
			int teamRank = i+1;
			JLabel teamRankLabel = new JLabel(new Integer(teamRank).toString());
			teamRankLabel.setHorizontalAlignment(SwingConstants.CENTER);
			teamRankPanel1.add(teamRankLabel);
			this.mainPanel.add(teamRankPanel1, gbc);
			for (int j = 1; j < this.rankingMethods.size()+1; j++) {
				gbc.gridx += 1;
				JPanel methodPanel = new JPanel(new GridBagLayout());
				GridBagConstraints gbc1 = new GridBagConstraints();
				gbc1.gridx = 0;
				gbc1.gridy = 0;
				gbc1.fill = GridBagConstraints.BOTH;
				gbc1.anchor = GridBagConstraints.CENTER;
				Double methodValue = team.handleSortMethod(this.rankingMethods.get(new Integer(j)));
				System.out.println("Team:" + team.member1.name + "/" + team.member2.name);
				System.out.println("Method: " + this.rankingMethods.get(new Integer(j)));
				System.out.println("Sort method value: " + methodValue);
				JLabel methodLabel = new JLabel(methodValue.toString());
				if (j == 1) {
					methodLabel = new JLabel((new Integer((int)methodValue.doubleValue())).toString());
				}
				methodPanel.add(methodLabel, gbc1);
				methodPanel.setBorder(BorderFactory.createLineBorder(Color.black));
				this.mainPanel.add(methodPanel, gbc);
				gbc.gridx += 1;
				JPanel methodRankPanel = new JPanel(new GridBagLayout());
				int rank = this.getTeamRank(team, this.rankingMethods.get(new Integer(j)));
				JLabel methodRankLabel = new JLabel(new Integer(rank).toString());
				methodRankPanel.setBorder(BorderFactory.createLineBorder(Color.black));
				methodRankPanel.add(methodRankLabel, gbc1);
				this.mainPanel.add(methodRankPanel, gbc);				
			}
		}		
	}
}
