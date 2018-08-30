/*
 *  This file is part of "TweetyProject", a collection of Java libraries for
 *  logical aspects of artificial intelligence and knowledge representation.
 *
 *  TweetyProject is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU Lesser General Public License version 3 as
 *  published by the Free Software Foundation.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU Lesser General Public License for more details.
 *
 *  You should have received a copy of the GNU Lesser General Public License
 *  along with this program. If not, see <http://www.gnu.org/licenses/>.
 *
 *  Copyright 2018 The TweetyProject Team <http://tweetyproject.org/contact/>
 */
package net.sf.tweety.lp.asp.syntax;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;

import net.sf.tweety.logics.commons.syntax.Predicate;
import net.sf.tweety.logics.commons.syntax.Variable;
import net.sf.tweety.logics.commons.syntax.interfaces.ComplexLogicalFormula;
import net.sf.tweety.logics.commons.syntax.interfaces.Term;
import net.sf.tweety.logics.fol.syntax.FolSignature;

/**
 * This class models an aggregate element, meaning a set of terms and a set of
 * naf literals (i.e. literals and default negated literals). One or more
 * Aggregate Elements form an Aggregate.
 * 
 * @see net.sf.tweety.lp.asp.syntax.Aggregate
 * 
 * @author Anna Gessler
 */
public class AggregateElement implements ASPElement {

	private List<Term<?>> left;
	private List<ASPBodyElement> right;

	/**
	 * Creates new Aggregate Element with given list of terms and list of naf
	 * literals.
	 * 
	 * @param l
	 * @param r
	 */
	public AggregateElement(List<Term<?>> l, List<ASPBodyElement> r) {
		this.left = l;
		this.right = r;
	}

	/**
	 * Copy-Constructor
	 * 
	 * @param other
	 */
	public AggregateElement(AggregateElement other) {
		this.left = other.left;
		this.right = other.right;
	}

	@Override
	public String toString() {
		String r = "";
		for (int i = 0; i < left.size() - 1; i++)
			r += left.get(i).toString() + ",";
		r += left.get(left.size() - 1);

		if (!right.isEmpty()) {
			r += " : ";
			for (int i = 0; i < right.size() - 1; i++)
				r += right.get(i).toString() + ",";
			r += right.get(right.size() - 1);
		}
		return r;
	}

	@Override
	public boolean isGround() {
		for (Term<?> a : this.left)
			if (a.containsTermsOfType(Variable.class))
				return false;
		for (ASPBodyElement a : this.right)
			if (a.containsTermsOfType(Variable.class))
				return false;
		return true;
	}

	@Override
	public boolean isWellFormed() {
		// TODO
		throw new UnsupportedOperationException("TODO");
	}

	@Override
	public Class<? extends Predicate> getPredicateCls() {
		// TODO
		throw new UnsupportedOperationException("TODO");
	}

	@Override
	public boolean isLiteral() {
		return false;
	}

	@Override
	public Set<Term<?>> getTerms() {
		Set<Term<?>> terms = new HashSet<Term<?>>();
		for (Term<?> t : left)
			terms.addAll(t.getTerms());
		for (ASPBodyElement t : right)
			terms.addAll(t.getTerms());
		return terms;
	}

	@Override
	public <C extends Term<?>> Set<C> getTerms(Class<C> cls) {
		Set<C> terms = new HashSet<C>();
		for (Term<?> t : left)
			terms.addAll(t.getTerms(cls));
		for (ASPBodyElement t : right)
			terms.addAll(t.getTerms(cls));
		return terms;
	}

	@Override
	public <C extends Term<?>> boolean containsTermsOfType(Class<C> cls) {
		for (Term<?> t : left)
			if (t.containsTermsOfType(cls))
				return true;
		for (ASPBodyElement t : right)
			if (t.containsTermsOfType(cls))
				return true;
		return false;
	}

	@Override
	public SortedSet<ASPLiteral> getLiterals() {
		// TODO
		throw new UnsupportedOperationException("TODO");
	}

	@Override
	public Set<Predicate> getPredicates() {
		Set<Predicate> predicates = new HashSet<Predicate>();
		for (ASPBodyElement t : right)
			predicates.addAll(t.getPredicates());
		return predicates;
	}

	@Override
	public Set<ASPAtom> getAtoms() {
		Set<ASPAtom> atoms = new HashSet<ASPAtom>();
		for (ASPBodyElement a : this.right)
			atoms.addAll(a.getAtoms());
		return atoms;
	}

	@Override
	public ASPElement substitute(Term<?> t, Term<?> v) {
		// TODO
		throw new UnsupportedOperationException("TODO");
	}

	@Override
	public FolSignature getSignature() {
		FolSignature sig = new FolSignature();
		for (Term<?> t : left)
			sig.add(t);
		for (ASPBodyElement t : right)
			sig.add(t.getSignature());
		return sig;
	}

	@Override
	public AggregateElement clone() {
		return new AggregateElement(this);
	}

	@Override
	public ComplexLogicalFormula substitute(Map<? extends Term<?>, ? extends Term<?>> map)
			throws IllegalArgumentException {
		// TODO
		throw new UnsupportedOperationException("TODO");
	}

	@Override
	public ComplexLogicalFormula exchange(Term<?> v, Term<?> t) throws IllegalArgumentException {
		// TODO
		throw new UnsupportedOperationException("TODO");
	}

}