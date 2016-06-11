import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.Scanner;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;


public class Main {
	int revisionNumber = 0;
	ArrayList<Team> teamList;
	ArrayList<Competitor> speakerList;
	JFrame frame;
	JPanel tabsheet;
	JPanel masterTabSheet;
	JScrollPane tabsheetMainPanel;
	RankingPanel rankingPanel;
	JComponent topLeftComponent;
	ArrayList<JPanel> rankingPanels;
	JTabbedPane tabbedPane;
	JMenuBar menu;
	int numRounds = 6;
	int presets;
	boolean hasClubs;
	boolean clubProtects = false;
	int clubProtectRounds = 0;
	HashMap<Team, HashMap<Integer, Round>> loadData;
	HashMap<String, ArrayList<Team>> clubMembers;
	HashMap<String, Team> teamIdentifiers;	
	File gameFile;
	
	Main () {
		this.teamList = new ArrayList<>();
		this.speakerList = new ArrayList<Competitor>();
		this.clubMembers = new HashMap<String, ArrayList<Team>>();
	}
	
	
	public static void main(String[] args) {
		Main tab = new Main();
		tab.menuInstantiate();
		tab.basicInstantiation();
		tab.printTeams();
		tab.assignPresetRounds();
	}
	
	public void basicInstantiation() {
		if (this.frame == null) {
			this.frame = new JFrame();
		}
		this.teamList = new ArrayList<Team>();
		this.speakerList = new ArrayList<Competitor>();
		this.teamIdentifiers = new HashMap<String, Team>();
		// gets file
		FileHandler fileHandler = new FileHandler(this);
		//gets numRound, numPresets, boolean clubProtects, create IDHashMap
		fileHandler.readTextFile();
		this.rankingPanelInstantiate();
		this.initialGraphicalRendering(this.numRounds);
		
	}
	
	/*public void getLoadFile() {
		JFileChooser chooser = new JFileChooser();
		chooser.showOpenDialog(this.frame);
		File inputFile = chooser.getSelectedFile();
		String fileName = inputFile.getName();
		System.out.println("Selected File: " + fileName);
		if (fileName.substring(fileName.length()-4).equals("xlsx")) {
			System.out.println("Is Excel File");
		}
		else if (fileName.substring(fileName.length()-4).equals("docx") || fileName.substring(fileName.length()-3).equals("doc")) {
			System.out.println("Is Word File");
		}
		else if (fileName.substring(fileName.length()-3).equals("txt")) {
			readTextFile(inputFile);
			this.getColumns();
			int columns = this.numRounds;
			System.out.println("Team size: " + this.teamList.size());
			this.getPresets();
			this.addTeamByID();
			this.rankingPanelInstantiate();
			this.initialGraphicalRendering(columns);
			//this.assignRounds(presets);
			return;
		}
		else if (fileName.substring(fileName.length()-6).equals("debate")) {
			this.readCCAFile(inputFile);
			this.addTeamByID();
			return;
		}
	}*/
	
	public void initialGraphicalRendering(int columns) {
		this.tabsheet = new JPanel();
		this.tabsheet.setLayout(new GridLayout(this.teamList.size(), 1));
		for (int i = 0; i < this.teamList.size(); i++){
			JPanel rowMasterPanel = new JPanel();
			rowMasterPanel.setLayout(new GridBagLayout());
			GridBagConstraints gbc = new GridBagConstraints();
			gbc.fill = GridBagConstraints.BOTH;
			gbc.gridx = 0;			
			
			Team team = this.teamList.get(i);
			//team.teamResults = new Round[columns];
			JPanel rowPanel = new JPanel();
			rowPanel.setPreferredSize(new Dimension(100*columns, 70));
			rowPanel.setBackground(Color.BLACK);
			rowPanel.setLayout(new GridLayout(1, columns + 1));
			rowPanel.setName("Team" + i);
			
			JPanel teamPanel = new JPanel();
			teamPanel.setLayout(new GridLayout(3,1));
			teamPanel.setBorder(BorderFactory.createLineBorder(Color.black));
			
			JLabel speaker1 = new JLabel();
			speaker1.setText("  " + team.member1.name);
			teamPanel.add(speaker1);
			
			JLabel teamNumber = new JLabel();
			teamNumber.setText("  " + team.identifier);
			teamPanel.add(teamNumber);
			
			JLabel speaker2 = new JLabel();
			speaker2.setText("  " + team.member2.name);
			teamPanel.add(speaker2); 
			teamPanel.setPreferredSize(new Dimension(120,70));
			rowMasterPanel.add(teamPanel, gbc);
			gbc.gridx = 1;
			
			for (int j = 0; j < columns; j++){
				JPanel roundPanel = new JPanel();
				roundPanel.setPreferredSize(new Dimension(100, 70));
				roundPanel.setBorder(BorderFactory.createLineBorder(Color.black));
				Round round = new Round(this.frame, roundPanel, team, (i+1));
				rowPanel.add(round.getRoundPanel());
//				System.out.println("Team round size: " + team.teamResults.length);
//				System.out.println(j);
				team.teamResults[j] = round;
			}
			StatPanel statPanel = new StatPanel(team);
			JPanel statisticsPanel = statPanel.getStatPanel();
			statisticsPanel.setBorder(BorderFactory.createLineBorder(Color.black));
			rowPanel.add(statisticsPanel);			
			rowMasterPanel.add(rowPanel, gbc);
			this.tabsheet.add(rowMasterPanel);
		}
		//this.addClubProtectInfo();
		
		JPanel tabsheetMasterPanel = new JPanel();
		tabsheetMasterPanel.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.weightx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		this.topLeftComponent = this.tabsheet;
		tabsheetMasterPanel.add(this.tabsheet, gbc);
		gbc.gridwidth = 2;
		gbc.gridy = 1;
		gbc.weighty = 1;
		JPanel bottomPanel = new JPanel();
		tabsheetMasterPanel.add(bottomPanel, gbc);
		gbc.gridx = 1;
		gbc.gridy = 0;
		gbc.weightx = 1;
		gbc.gridheight = 2;
		JPanel endPanel = new JPanel();
		tabsheetMasterPanel.add(endPanel, gbc);
		this.masterTabSheet = tabsheetMasterPanel;
		
		this.tabbedPane = new JTabbedPane();
		this.tabbedPane.addTab("Main Tabulation Sheet", this.masterTabSheet);
		this.tabbedPane.addTab("Ranking Sheet", this.rankingPanel.getPanel());
		this.tabbedPane.add(this.menu);
		this.tabsheetMainPanel = new JScrollPane(this.tabbedPane);
		this.frame.setJMenuBar(this.menu);
		this.frame.setPreferredSize(new Dimension(1000,1000));
		this.frame.add(this.tabsheetMainPanel);
		this.frame.pack();
		this.frame.setBackground(Color.BLACK);
		this.frame.setResizable(true);
		this.frame.setVisible(true);
		this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public void uploadFileGraphicalRendering(int columns){
		this.frame.getContentPane().removeAll();
		this.tabsheet = new JPanel();
		this.tabsheet.setLayout(new GridLayout(this.teamList.size(), 1));
		for (int i = 0; i < this.teamList.size(); i++){
			JPanel rowMasterPanel = new JPanel();
			rowMasterPanel.setLayout(new GridBagLayout());
			GridBagConstraints gbc = new GridBagConstraints();
			gbc.fill = GridBagConstraints.BOTH;
			gbc.gridx = 0;			
			
			Team team = this.teamList.get(i);
			JPanel rowPanel = new JPanel();
			rowPanel.setBackground(Color.BLACK);
			rowPanel.setLayout(new GridLayout(1, columns + 1));
			rowPanel.setName("Team" + i);
			
			JPanel teamPanel = new JPanel();
			teamPanel.setLayout(new GridLayout(3,1));
			teamPanel.setBorder(BorderFactory.createLineBorder(Color.black));
			
			JLabel speaker1 = new JLabel();
			speaker1.setText("  " + team.member1.name);
			teamPanel.add(speaker1);
			
			JLabel teamNumber = new JLabel();
			teamNumber.setText("  " + team.identifier);
			teamPanel.add(teamNumber);
			
			JLabel speaker2 = new JLabel();
			speaker2.setText("  " + team.member2.name);
			teamPanel.add(speaker2); 
			
			teamPanel.setPreferredSize(new Dimension(130,70));
			rowMasterPanel.add(teamPanel, gbc);
			gbc.gridx = 1;
			
			for (int j = 0; j < columns; j++){
				JPanel roundPanel = new JPanel();
				roundPanel.setPreferredSize(new Dimension(100, 70));
				roundPanel.setBorder(BorderFactory.createLineBorder(Color.black));
				Round round = team.teamResults[j];
				round.addGraphics(this.frame, roundPanel);
				rowPanel.add(round.getRoundPanel());
			}
			StatPanel statPanel = new StatPanel(team);
			JPanel statisticsPanel = statPanel.getStatPanel();
			statisticsPanel.setBorder(BorderFactory.createLineBorder(Color.black));
			rowPanel.add(statisticsPanel);			
			rowMasterPanel.add(rowPanel, gbc);
			this.tabsheet.add(rowMasterPanel);
		}
		//this.addClubProtectInfo();
		//this.getPresets();
		
		JPanel tabsheetMasterPanel = new JPanel();
		
		tabsheetMasterPanel.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.weightx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		this.topLeftComponent = this.tabsheet;
		tabsheetMasterPanel.add(this.tabsheet, gbc);
		gbc.gridwidth = 2;
		gbc.gridy = 1;
		gbc.weighty = 1;
		JPanel bottomPanel = new JPanel();
		tabsheetMasterPanel.add(bottomPanel, gbc);
		gbc.gridx = 1;
		gbc.gridy = 0;
		gbc.weightx = 1;
		gbc.gridheight = 2;
		JPanel endPanel = new JPanel();
		tabsheetMasterPanel.add(endPanel, gbc);
		this.masterTabSheet = tabsheetMasterPanel;
		
		this.tabbedPane = new JTabbedPane();
		this.tabbedPane.addTab("Main Tabulation Sheet", this.masterTabSheet);
		this.tabbedPane.addTab("Ranking Sheet", this.rankingPanel.getPanel());
		this.tabbedPane.add(this.menu);
		this.tabsheetMainPanel = new JScrollPane(this.tabbedPane);
		this.frame.setJMenuBar(this.menu);
		this.frame.setPreferredSize(new Dimension(1000,1000));
		this.frame.add(this.tabsheetMainPanel);
		this.frame.pack();
		this.frame.setBackground(Color.BLACK);
		this.frame.setResizable(true);
		this.frame.setVisible(true);
		this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public void addTeamByID() {
		for (Team team : this.teamList) {
			this.teamIdentifiers.put(team.identifier, team);
		}
	}
	
	public void getColumns() {		
		try {
			this.numRounds = Integer.parseInt(JOptionPane.showInputDialog("How many rounds would you like?"));			
		} catch(Exception NumberFormatException) {
			this.getColumns();
		}
	}
	public void getPresets(){
		try {
			this.presets = Integer.parseInt(JOptionPane.showInputDialog("How many rounds would you like preset?"));			
		} catch(Exception NumberFormatException) {
			this.getPresets();
		}
	}
	
	public void addClubProtectInfo() {
		if (this.hasClubs) {
			String[] values = {"True", "False"};
			String selection = (String) JOptionPane.showInputDialog(null, "Are their Club Protects?", "Selection", JOptionPane.DEFAULT_OPTION, null, values, "0");
			if (selection.equals("True")) {
				this.clubProtects = true;
				this.addClubProtects();
			} else {
				this.clubProtects = false;
			}
		}
	}
	
	public void rankingPanelInstantiate() {
		this.rankingPanel = new RankingPanel(this.teamList, this);		
	}
	
	/*public void readTextFile(File f){		
		try {
            Scanner inScanner = new Scanner(f);
            while(inScanner.hasNextLine()) {
            	
            	String line = inScanner.nextLine();
            	int numTabs = 0;
            	int tabBreak1 = 0;
            	int tabBreak2 = 0;
            	for (int i = 0; i < line.length(); i++) {
            		Character c = new Character(line.charAt(i));
            		if (c.hashCode() == 9) {
            			numTabs += 1;
            			if (numTabs == 1) {
            				tabBreak1 = i;
            			} else {
            				tabBreak2 = i;
            			}
            		}
            	}
            	if (numTabs == 0) {
            		System.out.println("Data input error");
            		return;
            	}
            	if (numTabs == 1) {
            		Competitor comp1 = new Competitor(line.substring(0, tabBreak1).trim());
            		Competitor comp2 = new Competitor(line.substring(tabBreak1+1, line.length()).trim());
            		Team team = new Team(this, comp1, comp2, (this.teamList.size()+1)+"A");
            		this.hasClubs = false;
            		comp1.assignTeam(team);
            		comp2.assignTeam(team);
            		this.speakerList.add(comp1);
            		this.speakerList.add(comp2);
            		this.teamList.add(team);
            	} else if (numTabs == 2) {
            		Competitor comp1 = new Competitor(line.substring(0, tabBreak1).trim(), line.substring(tabBreak2+1, line.length()).trim());
            		Competitor comp2 = new Competitor(line.substring(tabBreak1+1, tabBreak2+1).trim(), line.substring(tabBreak2+1, line.length()).trim());
            		Team team = new Team(this, comp1, comp2, (this.teamList.size()+1)+"A", line.substring(tabBreak2+1, line.length()).trim());
            		this.hasClubs = true;
            		comp1.assignTeam(team);
            		comp2.assignTeam(team);
            		this.speakerList.add(comp1);
            		this.speakerList.add(comp2);
            		this.teamList.add(team);
            	}
            }
            inScanner.close();
       } catch (FileNotFoundException ex){
            System.err.println(f.getAbsolutePath());
       }
	}*/
	
	public void addClubProtects() {
		for (Team team : this.teamList) {
			if (!this.clubMembers.containsKey(team.club)) {
				this.clubMembers.put(team.club, new ArrayList<Team>());
			}
			this.clubMembers.get(team.club).add(team);
		}
		
		for (String s : this.clubMembers.keySet()) {
			ArrayList<Team> tempTeamList = this.clubMembers.get(s);
			for (Team team : this.clubMembers.get(s)) {
				for (Team team2 : tempTeamList) {
					team.TeamConflicts.put(team2, new Integer(1));
				}
			}
		}
	}
	
	
	public void printTeams() {
		for (Team team : this.teamList) {
			System.out.println(team.identifier + ": " + team.member1.name + " and " + team.member2.name);
		}		
	}
	
	public void saveGameToFile() {
		this.revisionNumber += 1;
		String gameData = "";
		gameData += "Number of Revisions: " + this.revisionNumber + "\r\n";
		gameData += "Number of Teams: " + this.teamList.size() + "\r\n";
		gameData += "Number of Rounds: " + this.numRounds + "\r\n";
		gameData += "Has Clubs: " + this.hasClubs + "\r\n";
		gameData += "Number of Tiebreakers: " + this.rankingPanel.rankingMethods.size() + "\r\n";
		gameData += new Boolean(this.clubProtects).toString() + "\r\n";
		gameData += new Integer(this.clubProtectRounds).toString() + "\r\n";
		for (int i = 1; i < this.rankingPanel.rankingMethods.size()+1; i++) {
			gameData += i + ": " + this.rankingPanel.rankingMethods.get(new Integer(i)) + "\r\n";
		}
		//TeamID Mem1 Mem2
		//Round #: OppID AFF/NEG W/L mem1Speak mem1Rank mem2Speak mem2Rank
		for (int i = 0; i < this.teamList.size(); i++) {
			Team team = this.teamList.get(i);
			gameData += team.identifier + "\r\n";
			gameData += team.member1.name + "\r\n";
			gameData += team.member2.name + "\r\n";
			if (team.hasClub){
				gameData += team.club + "\r\n";
			}
			for (int j = 1; j < this.numRounds + 1 ; j++) {
				Round r = team.teamResults[j-1];
				gameData += j + "\n"; 
				gameData += r.opponentID.getText() + "\r\n";
				gameData += r.affNeg.getText() + "\r\n";
				gameData += r.WinLoss.getText() + "\r\n";
				gameData += r.comp1Speaks.getText() + "\r\n";
				gameData += r.comp1Ranks.getText() + "\r\n";
				gameData += r.comp2Speaks.getText() + "\r\n";
				gameData += r.comp2Ranks.getText() + "\r\n";
			}
		}
		JFileChooser chooser = new JFileChooser();
		int returnVal = chooser.showSaveDialog(this.frame);
		File file = null;
		if (returnVal == JFileChooser.APPROVE_OPTION){
			file = chooser.getSelectedFile();
		}
		try {
			File realFile = new File(file.getAbsolutePath()+".debate");
			PrintWriter writer = new PrintWriter(file+".debate");
            try {
            	writer.printf(gameData);
            	realFile.setReadOnly();
            } finally {
                writer.close();
            }
       } catch (FileNotFoundException fnfException) {
            //
       }
	}
	
	public void readCCAFile(File file) {
		File inputFile = file;
		try {
			Scanner inScanner = new Scanner(inputFile);
			String revLine = inScanner.nextLine().substring(21);
			int rev = Integer.parseInt(revLine);
			this.revisionNumber = rev;
			String teamLine = inScanner.nextLine().substring(17);
			int numberOfTeams = Integer.parseInt(teamLine);
			String numOfRounds = inScanner.nextLine().substring(18);
			this.numRounds = Integer.parseInt(numOfRounds);
			
			String hasClubsString = inScanner.nextLine().substring(10);
			boolean hasClubs = Boolean.getBoolean(hasClubsString);
			this.hasClubs = hasClubs;
			String numOfTieBreakers = inScanner.nextLine().substring(23);
			int numTieBreakers = Integer.parseInt(numOfTieBreakers);
			HashMap<Integer, String> methodData = new HashMap<Integer, String>();
			for (int i = 0; i < numTieBreakers; i++) {
				String tiebreakerString = inScanner.nextLine();
				Integer weight = new Integer(Integer.parseInt(tiebreakerString.substring(0, 1)));
				String methodString = tiebreakerString.substring(2).trim();				
				methodData.put(weight, methodString);
			}
//			gameData += r.opponentID.getText() + "\n";
//			gameData += r.affNeg.getText() + "\n";
//			gameData += r.WinLoss.getText() + "\n";
//			gameData += r.comp1Speaks.getText() + "\n";
//			gameData += r.comp1Ranks.getText() + "\n";
//			gameData += r.comp2Speaks.getText() + "\n";
//			gameData += r.comp2Ranks.getText() + "\n";
			for (int i = 0; i < numberOfTeams; i++) {
				String identifier = inScanner.nextLine().trim();
				String member1 = inScanner.nextLine().trim();
				String member2 = inScanner.nextLine().trim();
				Competitor mem1 = new Competitor(member1);
				Competitor mem2 = new Competitor(member2);
				Team team = null;
				if (this.hasClubs) {
					String clubLine = inScanner.nextLine().trim();
					team = new Team(this, mem1, mem2, identifier, clubLine);
				} else {
					team = new Team(this, mem1, mem2, identifier);
				}
				mem1.assignTeam(team);
				mem2.assignTeam(team);
				this.teamList.add(team);
				this.speakerList.add(mem1);
				this.speakerList.add(mem2);
				team.teamResults = new Round[this.numRounds];
				for (int j = 0; j < this.numRounds; j++) {
					int roundNumber = Integer.parseInt(inScanner.nextLine().trim());
					String opID = inScanner.nextLine().trim();
					String affNeg = inScanner.nextLine().trim();
					String WinLoss = inScanner.nextLine().trim();
					double mem1Speaks = Double.parseDouble(inScanner.nextLine().trim());
					double mem1Ranks = Double.parseDouble(inScanner.nextLine().trim());
					double mem2Speaks = Double.parseDouble(inScanner.nextLine().trim());
					double mem2Ranks = Double.parseDouble(inScanner.nextLine().trim());
					Round round = new Round(team, roundNumber, opID, affNeg, WinLoss, mem1Speaks, mem1Ranks, mem2Speaks, mem2Ranks);
					team.teamResults[j] = round;
				}
			}
			inScanner.close();
		} catch (FileNotFoundException exception) {
			// TODO Auto-generated catch-block stub.
			exception.printStackTrace();
		}
		this.rankingPanelInstantiate();
		this.uploadFileGraphicalRendering(this.numRounds);
	}
	
	public void menuInstantiate() {
		this.menu = new JMenuBar();
		JMenu tabulationMenu = new JMenu();
		tabulationMenu.setText("Options");
		MyMenuListener tabulationMenuListener = new MyMenuListener(tabulationMenu, this);
		tabulationMenu.addMenuListener(tabulationMenuListener);
		JMenuItem saveMenu = new JMenuItem("Save File");
		saveMenu.addMouseListener(tabulationMenuListener);
		tabulationMenu.add(saveMenu);
		this.menu.add(tabulationMenu);
		
		JMenu rankMenu = new JMenu();
		rankMenu.setText("Settings");
		
		MyMenuListener rankMenuListener = new MyMenuListener(rankMenu, this);
		rankMenu.addMenuListener(rankMenuListener);
		this.menu.add(rankMenu);
	}
	public Team getRandomTeam(ArrayList<Team> teamArray) {
		Random rand = new Random();
		int randTeamInt = rand.nextInt(teamArray.size());
		return teamArray.get(randTeamInt);
		
	}
	public Team getRandomTeam(ArrayList<Team> teamArray, Team notTeam) {
		Random rand = new Random();
		System.out.println("Team Array Size: " + teamArray.size());
		int randTeamInt = rand.nextInt(teamArray.size());
		if (!teamArray.get(randTeamInt).identifier.equals(notTeam.identifier)) {
			return teamArray.get(randTeamInt);
		}
		return getRandomTeam(teamArray, notTeam);
	}
	public void unassignRounds() {
		for (Team team : this.teamList) {
			for (int i = 1; i < this.numRounds+1; i++) {
				team.removeOpponent(i);
				team.tempOpponents = new ArrayList<String>();
			}
		}
	}
	
	public void assignPresetRounds() {
		RoundSetup assigner = new RoundSetup(this, this.teamList, this.presets);
		assigner.exportResults();
	}
}
