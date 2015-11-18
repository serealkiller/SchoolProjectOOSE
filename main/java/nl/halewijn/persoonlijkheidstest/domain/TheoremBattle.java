package nl.halewijn.persoonlijkheidstest.domain;

import javax.persistence.*;

@Entity
@Table(name = "TheoremBattle")
public class TheoremBattle extends Question {
	
	@ManyToOne
	@JoinColumn(name = "firstTheorem", referencedColumnName = "theoremID", insertable = false, updatable = false)
	private Theorem firstTheorem;
	
	@ManyToOne
	@JoinColumn(name = "secondTheorem", referencedColumnName = "theoremID", insertable = false, updatable = false)
	private Theorem secondTheorem;
	
	public TheoremBattle(PersonalityType personalityType, String questionText, Theorem firstTheorem, Theorem secondTheorem) {
		super(questionText);
		this.firstTheorem = firstTheorem;
		this.secondTheorem = secondTheorem;
	}

	public Theorem getFirstTheorem() {
		return firstTheorem;
	}

	public void setFirstTheorem(Theorem firstTheorem) {
		this.firstTheorem = firstTheorem;
	}

	public Theorem getSecondTheorem() {
		return secondTheorem;
	}

	public void setSecondTheorem(Theorem secondTheorem) {
		this.secondTheorem = secondTheorem;
	}
}
