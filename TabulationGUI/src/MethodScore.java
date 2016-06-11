
public class MethodScore implements Comparable<MethodScore>{
	int intScore;
	double doubleScore;
	Team owner;
	MethodScore(int score, Team owner) {
		this.intScore = score;
		this.owner = owner;
	}
	MethodScore(double score, Team owner) {
		this.doubleScore = score;
		this.owner = owner;
	}
	@Override
	public int compareTo(MethodScore meth) {
		// TODO Auto-generated method stub.
		return new Double(this.intScore+this.doubleScore).compareTo(new Double(meth.intScore+meth.doubleScore));
	}
}
