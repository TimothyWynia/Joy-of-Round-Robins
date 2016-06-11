import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class RoundSetup {
	
	HashMap<Integer, HashMap<Team, String[]>> simulatedData = new HashMap<Integer, HashMap<Team, String[]>>(); 
	// round team(opponent affNeg)
	int numRounds;
	Main main;
	ArrayList<Team> teamList;
	ArrayList<Team> unpairedTeams;
	
	RoundSetup(Main main, ArrayList<Team> teamList, int numRounds) {
		this.numRounds = numRounds;
		this.main = main;
		this.teamList = teamList;
		this.unpairedTeams = new ArrayList<Team>(teamList);
		this.handleRoundAssignments();
	}
	public void exportResults() {
		for (Integer i : this.simulatedData.keySet()) {
			HashMap<Team, String[]> roundData = this.simulatedData.get(i);
			for (Team team : roundData.keySet()) {
				if(roundData.get(team)[1].toUpperCase().equals("BYE")) {
					team.addBye(i.intValue());
				} else {
					team.addOpponent(i.intValue(), this.getTeam(roundData.get(team)[0]), roundData.get(team)[1]);
				}
			}
		}
	}
	
	public boolean hasFaced(Team team, Team toCheck){
		for (Integer i : this.simulatedData.keySet()) {
			if (this.simulatedData.get(i).get(team)[0].equals(toCheck.identifier)) {
				return true;
			}
		}
		return false;
	}
	
	
	public Team getTeam(String id) {
		for (Team team : this.teamList) {
			if (team.identifier.equals(id)) {
				return team;
			}
		}
		System.out.println(id + "not located");
		return null;
	}
	
	public void handleRoundAssignments() {
		while (true) {
			if (this.assignRounds()==true) {
				break;
			}
			this.reinstantiater();
		}
	}
	
	public void reinstantiater() {
		this.unpairedTeams = new ArrayList<Team>(this.teamList);
		this.simulatedData = new HashMap<Integer, HashMap<Team, String[]>>(); 
	}
	
	public Team getRandTeam(Team notTeam) {
		ArrayList<Team> teamArray = new ArrayList<Team>(this.unpairedTeams);
		teamArray.remove(notTeam);
		Random rand = new Random();
		//System.out.println("Team Array Size: " + teamArray.size());
		int randTeamInt = rand.nextInt(teamArray.size());
		if (!teamArray.get(randTeamInt).identifier.equals(notTeam.identifier)) {
			return teamArray.get(randTeamInt);
		}
		System.out.println();
		return getRandTeam(notTeam);
	}
	public Team getRandTeam() {
		ArrayList<Team> teamArray = new ArrayList<Team>(this.unpairedTeams);
		Random rand = new Random();
		//System.out.println("Team Array Size: " + teamArray.size());
		int randTeamInt = rand.nextInt(teamArray.size());
		return teamArray.get(randTeamInt);
	}
	
	public boolean hasTeamFaced(Team team, Team team1) {
		for (Integer i : this.simulatedData.keySet()) {
			try {
				String[] valueArray = this.simulatedData.get(i).get(team);
				if (valueArray[0].equals(team1.identifier)) {
					//System.out.println("The teams have faced");
					return true;
				}
				valueArray = this.simulatedData.get(i).get(team1);
				if (valueArray[0].equals(team.identifier)) {
					return true;
				}
			} catch (Exception NullPointerException) {
				System.out.println("A team faced a null");
			}
			 
		}
		return false;
	}
	
	public int getAffRounds(Team team) {
		int numAffs = 0;
		for (Integer i : this.simulatedData.keySet()) {
			try {
				if (this.simulatedData.get(i).get(team)[1].toUpperCase().equals("AFF")) {
					numAffs += 1;
				} 
			} catch (Exception NullPointerException) {
				//
			}
		}
		return numAffs;
	}
	
	public int getNegRounds(Team team) {
		int numNegs = 0;
		for (Integer i : this.simulatedData.keySet()) {
			try {
				if (this.simulatedData.get(i).get(team)[1].toUpperCase().equals("NEG")) {
					numNegs += 1;
				} 
			} catch (Exception NullPointerException) {
				//
			}
		}
		return numNegs;
	}
	 public boolean hasHadBye(Team team) {
		 for (Integer i : this.simulatedData.keySet()) {
			 try {
				if (this.simulatedData.get(i).get(team)[1].toUpperCase().equals("BYE")) {
					return true;
					} 
			 } catch (Exception NullPointerException) {
				 return false;
			 }
		 }
		 return false;
	 }
	
	public String getNextSide(Team team) {
		if (this.getAffRounds(team) == this.getNegRounds(team)) {
			return "BOTH";
		}
		if (this.getAffRounds(team) > this.getNegRounds(team)) {
			return "NEG";
		}
		if (this.getAffRounds(team) < this.getNegRounds(team)) {
			return "AFF";
		}
		return "";
	}
	
	public boolean assignRounds() {
		for (int i = 1; i <  this.numRounds+1; i++) {
			int teamLoop = 0;
			this.unpairedTeams = new ArrayList<Team>(this.teamList);
			HashMap<Team, String[]> teamTempData = new HashMap<Team, String[]>();
			if (this.unpairedTeams.size() % 2 == 1) {
				Team team = null;
				while (true) {
					team = this.getRandTeam();
					if (!this.hasHadBye(team)) {
						String[] stringArray = {"", "BYE"};
						teamTempData.put(team, stringArray);
						this.unpairedTeams.remove(team);
						break;
					}
				}
			}
			while (this.unpairedTeams.size() != 0) {
				Team team = this.unpairedTeams.get(0);
				Team team1 = this.getRandTeam(team);
				teamLoop += 1;
				if (teamLoop > this.numRounds*this.teamList.size()*3) {
					return false;
				}
				if (i % 2 == 1) {
					if (!this.hasTeamFaced(team, team1)) {
						String affNeg = (Math.random() > .5) ? "AFF":"NEG";
						String otherOne = (affNeg.equals("AFF")) ? "NEG":"AFF";
						String[] teamArray = {team1.identifier, affNeg};
						String[] team1Array = {team.identifier, otherOne};
						teamTempData.put(team, teamArray);
						teamTempData.put(team1, team1Array);
						this.unpairedTeams.remove(team);
						this.unpairedTeams.remove(team1);
					}
				}
				else if (!team.hadBYE() && !team1.hadBYE()) {
						if (this.getAffRounds(team1) != this.getAffRounds(team) && !this.hasTeamFaced(team, team1)) {
							String affNeg = (this.getAffRounds(team) < this.getNegRounds(team)) ? "AFF":"NEG";
							String otherOne = (affNeg.equals("AFF")) ? "NEG":"AFF";
							String[] teamArray = {team1.identifier, affNeg};
							String[] team1Array = {team.identifier, otherOne};
							teamTempData.put(team, teamArray);
							teamTempData.put(team1, team1Array);
							this.unpairedTeams.remove(team);
							this.unpairedTeams.remove(team1);
						}
				}
				else if (this.hasHadBye(team) || this.hasHadBye(team1) && !this.hasTeamFaced(team, team1)){
					if (this.hasHadBye(team) && this.hasHadBye(team1)) {
						String affNeg = (this.getAffRounds(team) < this.getNegRounds(team)) ? "AFF":"NEG";
						String otherOne = (affNeg.equals("AFF")) ? "NEG":"AFF";
						String[] teamArray = {team1.identifier, affNeg};
						String[] team1Array = {team.identifier, otherOne};
						teamTempData.put(team, teamArray);
						teamTempData.put(team1, team1Array);
						this.unpairedTeams.remove(team);
						this.unpairedTeams.remove(team1);
					} else {
						Team byeTeam = (this.hasHadBye(team)) ? team:team1;
						Team notByeTeam = (this.hasHadBye(team)) ? team1:team1;
						String notByeTeamSide = (this.getAffRounds(team) < this.getNegRounds(team)) ? "AFF":"NEG";
						String otherOne = (notByeTeamSide.equals("AFF")) ? "NEG":"AFF";
						String[] byeTeamArray = {notByeTeam.identifier, otherOne};
						String[] notByeTeamArray = {byeTeam.identifier, notByeTeamSide};
						teamTempData.put(byeTeam, byeTeamArray);
						teamTempData.put(notByeTeam, notByeTeamArray);
						this.unpairedTeams.remove(notByeTeam);
						this.unpairedTeams.remove(byeTeam);
					}
				}
			}
			this.simulatedData.put(new Integer(i), teamTempData);
		}
		for (Team team : this.teamList) {
			System.out.println(team.identifier);
		}
		return true;
	}
}
