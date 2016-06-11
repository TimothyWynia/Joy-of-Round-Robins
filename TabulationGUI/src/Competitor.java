import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

@SuppressWarnings("boxing")
public class Competitor {
	public String name;
	String club;
	HashMap<Integer, Double> speakerPoints;
 	HashMap<Integer, Integer> speakerRanks;
 	ArrayList<Double> speaks;
 	ArrayList<Integer> ranks;
 	
 	boolean hasClub;
 	Team team;
 	boolean hasBye = false;
 	 
 	Competitor(String name, String club) {
 		this.name = name;
 		this.club = club;
 		this.hasClub = true;
 		this.speakerPoints = new HashMap<Integer, Double>();
 		this.speakerRanks = new HashMap<Integer, Integer>();
 	}
 	
 	Competitor(String name) {
 		this.name = name;
 		this.speakerPoints = new HashMap<Integer, Double>();
 		this.speakerRanks = new HashMap<Integer, Integer>();
 		this.hasClub = false;
 	}
 	
 	public void assignTeam(Team t) {
 		this.team = t;
 	}
 	
 	@Override
	public String toString() {
 		String returnedString = "";
 		returnedString += "Name: " + this.name + "\n";
 		returnedString += "Club: " + this.club + "\n";
 		returnedString += "Speaker Points: " + this.speakerPoints.toString() + "\n";
 		returnedString += "Speaker Ranks: " + this.speakerRanks.toString() + "\n";
 		return returnedString;
 	}
 	
	public double getPoints() {
 		double points = 0;
 		for (Integer i : this.speakerPoints.keySet()) {
 			points += this.speakerPoints.get(i);
 		}
 		double byePoints = 0.0;
 		if (this.hasBye) {
 			byePoints = points/(this.speakerPoints.size()+.0);
 		}
 		return points + byePoints; 	
	}
	public int getRanks() {
		int ranks = 0;
		for (Integer i : this.speakerRanks.keySet()) {
			ranks += this.speakerRanks.get(i);
		}
		int byeRank = 0;
		if (this.hasBye) {
			byeRank = Math.round(ranks/this.speakerRanks.size());
		}		
		return ranks+byeRank;
	}
	
	public double getHiLowPoints() {
		if (this.speakerPoints.size() < 3){
			return 0.0;
		}
		double points = 0;
 		for (Integer i : this.speakerPoints.keySet()) {
 			points += this.speakerPoints.get(i);
 		}
 		double byePoints = 0.0;
 		if (this.hasBye) {
 			byePoints = Math.round(points/(this.speakerPoints.size()));
 		}		
		ArrayList<Double> compSpeakerPoints = new ArrayList<Double>();
		for (Integer i : this.speakerPoints.keySet()) {
			compSpeakerPoints.add(this.speakerPoints.get(i));
		}
		Collections.sort(compSpeakerPoints);
		return points + byePoints - compSpeakerPoints.get(0)-compSpeakerPoints.get(compSpeakerPoints.size()-1);
	}
	public int getHiLowRanks() {
		if (this.speakerRanks.size() < 3){
			return 0;
		}
		int points = 0;
 		for (Integer i : this.speakerRanks.keySet()) {
 			points += this.speakerRanks.get(i);
 		}
 		int byeRank = 0;
 		if (this.hasBye) {
 			byeRank = Math.round(points/(this.speakerPoints.size()));
 		}	
		ArrayList<Integer> compSpeakerRanks = new ArrayList<Integer>();
		for (Integer i : this.speakerRanks.keySet()) {
			compSpeakerRanks.add(this.speakerRanks.get(i));
		}
		Collections.sort(compSpeakerRanks);
		return points + byeRank - compSpeakerRanks.get(0)-compSpeakerRanks.get(compSpeakerRanks.size()-1);
	}
	
	public void addResult(Double points, Integer rank, Integer roundNumber) {
		this.speakerPoints.put(roundNumber, points);
		this.speakerRanks.put(roundNumber, rank);		
	}
	
	public double getDblHiLowPoints() {
		if (this.speakerPoints.size() < 5) {
			return 0;
		}
		return getHiLowPoints()- this.speakerPoints.get(1) - this.speakerPoints.get(this.speakerPoints.size()-2);
	}
	public int getDblHiLowRanks() {
		if (this.speakerRanks.size() < 5) {
			return 0;
		}
		return getHiLowRanks()- this.speakerRanks.get(1) - this.speakerRanks.get(this.speakerRanks.size()-2);
	}
}
	
