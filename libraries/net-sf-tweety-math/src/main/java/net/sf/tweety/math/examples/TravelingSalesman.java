package net.sf.tweety.math.examples;

import java.util.ArrayList;



import net.sf.tweety.math.term.FloatConstant;
import net.sf.tweety.math.term.Fraction;
import net.sf.tweety.math.term.IntegerConstant;
import net.sf.tweety.math.term.Power;
import net.sf.tweety.math.term.Sum;
import net.sf.tweety.math.term.Term;
import net.sf.tweety.math.term.Difference;
import net.sf.tweety.math.opt.problem.*;
import net.sf.tweety.math.term.AbsoluteValue;

/**
 * implements the traveling salesman problem. Every element has an x- coordinate and a y- coordinate. 
 * Every city  can be connected to each other and the distance is the cartesian distance bewteen them.
 * @author Sebastian Franke
 *
 */
public class TravelingSalesman extends CombinatoricsProblem{


	
	
	public TravelingSalesman(ArrayList<ElementOfCombinatoricsProb> elements) {
		super(elements);
		for(int i = 0; i < elements.size(); i++)
			if(elements.get(i).size() != 2)
				System.err.println("Elements of Traveling Salesman need to have an x-coordinate and a y-coordinate, nothing else");
	}

	private static final long serialVersionUID = 1L;

	Term maxWeight;
	ArrayList<ElementOfCombinatoricsProb> currSol = new ArrayList<ElementOfCombinatoricsProb>();
	ArrayList<ElementOfCombinatoricsProb> bestSol;
	/**
	 * @return the weight of a certain element
	 */
	Term weight(int i, ArrayList<ElementOfCombinatoricsProb> sol) {
		return sol.get(i).get(1) ;
	}
	
	/**
	 * @return the value of a certain element
	 */
	Term value(int i, ArrayList<ElementOfCombinatoricsProb> sol) {
		return sol.get(i).get(0) ;
	}



	@Override
	public ArrayList<ElementOfCombinatoricsProb> createRandomNewSolution(ArrayList<ElementOfCombinatoricsProb> currSol) {
		//chosse two random cities to swap in order

		int random0 = (int)(Math.random() * currSol.size());
		int random1 = (int)(Math.random() * currSol.size());
		while(random0 == random1)
			random1 = (int)(Math.random() * currSol.size());
		//create new solution with cities swapped
		ArrayList<ElementOfCombinatoricsProb> newSol = new ArrayList<ElementOfCombinatoricsProb>();
		for(ElementOfCombinatoricsProb i : currSol)
			newSol.add(i);
		ElementOfCombinatoricsProb tmp0 = currSol.get(random0);
		ElementOfCombinatoricsProb tmp1 = currSol.get(random1);
		newSol.remove(random0);
		newSol.add(random0, tmp1);
		newSol.remove(random1);
		newSol.add(random1, tmp0);

		
		return newSol;
	}


	@Override
	public boolean isValid(ArrayList<ElementOfCombinatoricsProb> sol) {
		if(sol.size() == this.size())		
			return true;
		else
			return false;
	}

	@Override
	public double evaluate(ArrayList<ElementOfCombinatoricsProb> sol) {

		//calculate the sum of distances between the connected cities
		Term sum = new FloatConstant(0);
		//sum from position 0 to n-1
		for(int i = 0; i < sol.size() - 1; i++) {
			Term x = new AbsoluteValue(new Difference(sol.get(i).get(0), sol.get(i+1).get(0)));
			Term y = new AbsoluteValue(new Difference(sol.get(i).get(0), sol.get(i+1).get(0)));
			sum = new Sum(sum, new Power(new Sum(x, y), new IntegerConstant(2)));
		}
		//distance from n-1 to 0
		int lastpos = sol.size() - 1;
		Term x = new AbsoluteValue(new Difference(sol.get(lastpos).get(0), sol.get(0).get(0)));
		Term y = new AbsoluteValue(new Difference(sol.get(lastpos).get(1), sol.get(0).get(1)));
		sum = new Sum(sum, new Power(new Sum(x, y), new IntegerConstant(2)));
		sum = new Fraction(new FloatConstant(1), sum);

		return sum.doubleValue();
	}

	@Override
	public double sumOfWeights(ArrayList<ElementOfCombinatoricsProb> sol) {
		// TODO Auto-generated method stub
		return 0;
	}




}