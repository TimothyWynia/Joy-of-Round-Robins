import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import javax.swing.JFileChooser;

public class FileHandler {
	Main tabulationMain;
	File file;
	
	FileHandler(Main main) {
		this.tabulationMain = main;
		this.file = this.getFile();
		this.tabulationMain.gameFile = this.file;
	}
	
	public File getFile() {
		while(true) {
			try {
				JFileChooser chooser = new JFileChooser();
				chooser.showOpenDialog(this.tabulationMain.frame);
				File inputFile = chooser.getSelectedFile();
				String fileName = inputFile.getName();
				if (fileName.substring(fileName.length()-3).equals("txt")) {
					return inputFile;
				}
				else if (fileName.substring(fileName.length()-6).equals("debate")) {
					return inputFile;
				}
			} catch (Exception NullPointerException){
				//
			}
		}
	}
	
	public void readTextFile(){		
		this.tabulationMain.getColumns();
		this.tabulationMain.getPresets();
		File f = this.file;
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
            		Team team = new Team(this.tabulationMain, comp1, comp2, (this.tabulationMain.teamList.size()+1)+"A");
            		this.tabulationMain.hasClubs = false;
            		comp1.assignTeam(team);
            		comp2.assignTeam(team);
            		this.tabulationMain.speakerList.add(comp1);
            		this.tabulationMain.speakerList.add(comp2);
            		this.tabulationMain.teamList.add(team);
            	} else if (numTabs == 2) {
            		Competitor comp1 = new Competitor(line.substring(0, tabBreak1).trim(), line.substring(tabBreak2+1, line.length()).trim());
            		Competitor comp2 = new Competitor(line.substring(tabBreak1+1, tabBreak2+1).trim(), line.substring(tabBreak2+1, line.length()).trim());
            		Team team = new Team(this.tabulationMain, comp1, comp2, (this.tabulationMain.teamList.size()+1)+"A", line.substring(tabBreak2+1, line.length()).trim());
            		this.tabulationMain.hasClubs = true;
            		comp1.assignTeam(team);
            		comp2.assignTeam(team);
            		this.tabulationMain.speakerList.add(comp1);
            		this.tabulationMain.speakerList.add(comp2);
            		this.tabulationMain.teamList.add(team);
            	}
            }
            inScanner.close();
       } catch (FileNotFoundException ex){
            System.err.println(f.getAbsolutePath());
       }
	   this.tabulationMain.addClubProtectInfo();
	   this.tabulationMain.addTeamByID();
	}
	
	public boolean isTextFile(File f) {
		String fileName = f.getName();
		if (fileName.substring(fileName.length()-3).equals("txt")) {
			return true;
		}
		return false;
	}
	
	/*public void readCCAFile(File file) {
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
	}*/
}
