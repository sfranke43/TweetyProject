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

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import net.sf.tweety.commons.util.rules.Rule;
import net.sf.tweety.logics.commons.syntax.interfaces.Term;
import net.sf.tweety.logics.fol.syntax.FolSignature;

/**
 * This class models a rule in ASP. A rule consists of a head and a body. The
 * head contains n>=0 classical atoms and the body contains n>=0 literals. Rules
 * with non-empty heads and empty bodies are called facts, rules with empty
 * heads and non-empty bodies are called constraints.
 *
 * 
 * @author Anna Gessler
 * @author Tim Janus
 * @author Thomas Vengels
 */
public class ASPRule implements Rule<ASPHead, ASPBodyElement>, Comparable<ASPRule> {

	/**
	 * The head (conclusion) of a rule. Consists of a disjunction of atoms.
	 */
	private ASPHead head;

	/**
	 * The body (premise) of a rule. Consists of literals.
	 */
	private List<ASPBodyElement> body;

	/**
	 * Additional attributes that can be used for weak constraints (rules with empty
	 * heads).
	 */
	private Term<?> weight;
	private Term<?> level;
	private List<Term<?>> constraint_terms;

	/**
	 * Empty constructor
	 */
	public ASPRule() {
		this.head = new ASPHead();
		this.body = new LinkedList<ASPBodyElement>();
		this.weight = null;
		this.level = null;
		this.constraint_terms = new LinkedList<Term<?>>();
	}

	/**
	 * Creates a fact with the given ASPHead.
	 * 
	 * @param head
	 */
	public ASPRule(ASPHead head) {
		this.head = head;
		this.body = new LinkedList<ASPBodyElement>();
		this.weight = null;
		this.level = null;
		this.constraint_terms = new LinkedList<Term<?>>();
	}

	/**
	 * Creates a fact with the given literal.
	 * 
	 * @param literal
	 */
	public ASPRule(ASPLiteral literal) {
		this();
		this.head = new ASPHead(literal);
	}

	/**
	 * Creates a rule with the given head and body.
	 * 
	 * @param head
	 * @param body
	 */
	public ASPRule(ASPHead head, List<ASPBodyElement> body) {
		this.head = head;
		this.body = body;
		this.weight = null;
		this.level = null;
		this.constraint_terms = new LinkedList<Term<?>>();
	}

	/**
	 * Creates a rule with the given head and a single-element body.
	 * 
	 * @param head
	 * @param b
	 *            a body element
	 */
	public ASPRule(ASPLiteral head, ASPBodyElement b) {
		this(head);
		this.body.add(b);
		this.weight = null;
		this.level = null;
		this.constraint_terms = new LinkedList<Term<?>>();
	}

	/**
	 * Creates a rule with the given head and body.
	 * 
	 * @param head
	 * @param body
	 */
	public ASPRule(ASPLiteral head, List<ASPBodyElement> body) {
		this.head = new ASPHead(head);
		this.body = body;
		this.weight = null;
		this.level = null;
		this.constraint_terms = new LinkedList<Term<?>>();
	}

	/**
	 * Creates a constraint with the given body.
	 * 
	 * @param body
	 */
	public ASPRule(List<ASPBodyElement> body) {
		this();
		this.body = body;
	}

	/**
	 * Creates a constraint with the given weight and terms.
	 * 
	 * @param nafliterals
	 * @param weight
	 * @param terms
	 */
	public ASPRule(List<ASPBodyElement> nafliterals, Term<?> weight, List<Term<?>> terms) {
		this();
		this.body = nafliterals;
		this.weight = weight;
		this.constraint_terms = terms;
	}

	/**
	 * Creates a constraint with the given weight, level and terms.
	 * 
	 * @param body
	 * @param weight
	 * @param level
	 * @param wal
	 */
	public ASPRule(List<ASPBodyElement> body, Term<?> weight, Term<?> level, List<Term<?>> terms) {
		this.body = body;
		this.weight = weight;
		this.level = level;
		this.constraint_terms = terms;
	}

	/**
	 * Copy-Constructor
	 * 
	 * @param other
	 */
	public ASPRule(ASPRule other) {
		this(other.head, other.body);
	}

	public Boolean isSafe() {
		// TODO
		throw new UnsupportedOperationException("WIP");
	}

	@Override
	public boolean isFact() {
		return (body.isEmpty() && !head.isEmpty());
	}

	@Override
	public boolean isConstraint() {
		return (head.isEmpty() && !body.isEmpty());
	}

	@Override
	public void addPremises(Collection<? extends ASPBodyElement> premises) {
		this.body.addAll(premises);
	}

	@Override
	public FolSignature getSignature() {
		FolSignature sig = new FolSignature();
		sig.add(head.getSignature());
		for (ASPBodyElement a : body)
			sig.add(a.getSignature());
		return sig;
	}

	@Override
	public void setConclusion(ASPHead conclusion) {
		this.head = conclusion;
	}

	public void setConclusion(ASPLiteral head) {
		this.head = new ASPHead(head);
	}

	@Override
	public void addPremise(ASPBodyElement premise) {
		this.body.add(premise);
	}

	@Override
	public List<ASPBodyElement> getPremise() {
		return body;
	}

	@Override
	public ASPHead getConclusion() {
		return head;
	}

	@Override
	public String toString() {
		String ret = "";
		if (!head.isEmpty()) {
			for (int i = 0; i < head.size() - 1; i++)
				ret += head.get(i).toString() + ",";
			ret += head.get(head.size() - 1).toString() + " ";
		}
		if (!body.isEmpty()) {
			ret += ":- " + body.get(0);
			for (int i = 1; i < body.size(); ++i) {
				ret += ", " + body.get(i);
			}
		}
		ret += ".";

		if (weight != null) {
			ret += "[" + weight.toString();
			if (level != null)
				ret += "@" + level.toString();
			if (!this.constraint_terms.isEmpty()) {
				ret += ",";
				for (int i = 1; i < constraint_terms.size() - 1; i++)
					ret += constraint_terms.get(i) + ",";
				ret += constraint_terms.get(constraint_terms.size() - 1).toString();
			}
			ret += "]";
		}

		return ret;
	}

	public Term<?> getWeight() {
		return weight;
	}

	public void setWeight(Term<?> weight) {
		this.weight = weight;
	}

	public Term<?> getLevel() {
		return level;
	}

	public void setLevel(Term<?> level) {
		this.level = level;
	}

	public ASPHead getHead() {
		return head;
	}

	public void setHead(ASPHead head) {
		this.head = head;
	}

	public void addToHead(ASPLiteral h) {
		this.head.add(h);
	}

	public List<ASPBodyElement> getBody() {
		return body;
	}

	public void setBody(List<ASPBodyElement> body) {
		this.body = body;
	}

	public boolean isGround() {
		if (!head.isGround())
			return false;
		for (ASPBodyElement a : body)
			if (!a.isGround())
				return false;
		return true;
	}

	@Override
	public int compareTo(ASPRule arg0) {
		int comp = 0;

		// facts first:
		if (getPremise().size() == 0 && arg0.getPremise().size() != 0) {
			return -1;
		} else if (getPremise().size() != 0 && arg0.getPremise().size() == 0) {
			return 1;
		}

		// then order alphabetically starting by the head.
		comp = getConclusion().toString().compareTo(arg0.getConclusion().toString());
		if (comp != 0)
			return comp;

		// TODO: This implementation is not compatible with equals, because
		// the comparison depends on the order in which the elements
		// have been added to the premise list. Ex.:
		// compareTo(:- a,b., :- b,a.) = -1
		// if the head is the same use the body.
		for (int i = 0; i < body.size() && i < arg0.body.size(); ++i) {
			comp = body.get(i).toString().compareTo(arg0.body.get(i).toString());
			if (comp != 0)
				return comp;
		}

		return comp;
	}

	public Collection<? extends ASPLiteral> getLiterals() {
		SortedSet<ASPLiteral> literals = new TreeSet<ASPLiteral>();
		literals.addAll(head);
		for (ASPBodyElement pe : body) {
			literals.addAll(pe.getLiterals());
		}
		return literals;
	}

}