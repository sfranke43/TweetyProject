/* Generated By:JavaCC: Do not edit this line. ASPCore2Visitor.java Version 5.0 */
package net.sf.tweety.lp.asp.parser;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import net.sf.tweety.logics.commons.syntax.Constant;
import net.sf.tweety.logics.commons.syntax.FunctionalTerm;
import net.sf.tweety.logics.commons.syntax.Functor;
import net.sf.tweety.logics.commons.syntax.NumberTerm;
import net.sf.tweety.logics.commons.syntax.Predicate;
import net.sf.tweety.logics.commons.syntax.Variable;
import net.sf.tweety.logics.commons.syntax.interfaces.Term;
import net.sf.tweety.lp.asp.semantics.AnswerSet;
import net.sf.tweety.lp.asp.syntax.ASPAtom;
import net.sf.tweety.lp.asp.syntax.ASPHead;
import net.sf.tweety.lp.asp.syntax.ASPLiteral;
import net.sf.tweety.lp.asp.syntax.ASPBodyElement;
import net.sf.tweety.lp.asp.syntax.StrictNegation;
import net.sf.tweety.lp.asp.syntax.ASPOperator;
import net.sf.tweety.lp.asp.syntax.ASPRule;
import net.sf.tweety.lp.asp.syntax.Aggregate;
import net.sf.tweety.lp.asp.syntax.AggregateElement;
import net.sf.tweety.lp.asp.syntax.ArithmeticTerm;
import net.sf.tweety.lp.asp.syntax.ComparativeAtom;
import net.sf.tweety.lp.asp.syntax.DefaultNegation;
import net.sf.tweety.lp.asp.syntax.Program;

public class InstantiateVisitor implements ASPCore2ParserVisitor {
	@Override
	public Object visit(SimpleNode node, Object data) {
		throw new RuntimeException();
	}

	@Override
	public AnswerSet visit(ASTAnswerSet node, Object data) {
		AnswerSet as = new AnswerSet();
		for (int i = 0; i < node.jjtGetNumChildren(); ++i) {
			if (node.jjtGetChild(i) instanceof ASTAtom) {
				ASPLiteral a = visit((ASTAtom) node.jjtGetChild(i), null);
				as.add(a);
			}
		}
		return as;
	}

	@Override
	public Program visit(ASTProgram node, Object data) {
		Program p = new Program();
		Boolean hasQuery = false;

		for (int i = 0; i < node.jjtGetNumChildren(); ++i) {
			if (node.jjtGetChild(i) instanceof ASTRuleList) {
				List<ASPRule> rules = visit((ASTRuleList) node.jjtGetChild(i), null);
				p.addAll(rules);
			} else if (node.jjtGetChild(i) instanceof ASTQuery) {
				if (!hasQuery) {
					ASPLiteral q = visit((ASTQuery) node.jjtGetChild(i), null); // returns an ASPAtom
					p.setQuery(q);
					hasQuery = true;
				} else
					try {
						throw new ParseException(
								"Error: Multiple queries found. There can only be one query per program.");
					} catch (ParseException e) {
						e.printStackTrace();
					}
			}
		}

		return p;
	}

	@Override
	public ASPLiteral visit(ASTQuery node, Object data) {
		ASPLiteral atom = null;
		for (int i = 0; i < node.jjtGetNumChildren(); ++i) {
			if (node.jjtGetChild(i) instanceof ASTAtom)
				atom = visit((ASTAtom) node.jjtGetChild(i), null);
		}
		return atom;
	}

	@Override
	public List<ASPRule> visit(ASTRuleList node, Object data) {
		List<ASPRule> elements = new ArrayList<ASPRule>();
		for (int i = 0; i < node.jjtGetNumChildren(); ++i) {
			if (node.jjtGetChild(i) instanceof ASTRule) {
				ASPRule a = visit((ASTRule) node.jjtGetChild(i), null);
				elements.add(a);
			}
		}
		return elements;
	}

	@Override
	public ASPRule visit(ASTRule node, Object data) {
		ASPHead head = new ASPHead();
		List<ASPBodyElement> body = new LinkedList<ASPBodyElement>();

		Term<?> weight = null;
		Term<?> level = null;
		List<Term<?>> wal = new ArrayList<Term<?>>();

		if (node.jjtGetChild(0) instanceof ASTHead) {
			head = visit((ASTHead) node.jjtGetChild(0), null);
		}

		for (int i = 0; i < node.jjtGetNumChildren(); ++i) {
			if (node.jjtGetChild(i) instanceof ASTBodyList) {
				body = visit((ASTBodyList) node.jjtGetChild(i), null);
			} else if (node.jjtGetChild(i) instanceof ASTWeight) {
				wal = visit((ASTWeight) node.jjtGetChild(i), null);
				weight = wal.remove(0);
				if (node.hasLevel)
					level = wal.remove(0);
				break;
			}
		}

		if (!head.isEmpty()) {
			return new ASPRule(head, body);
		}

		if (weight == null) {
			return new ASPRule(body);
		}

		if (level != null) {
			return new ASPRule(body, weight, level, wal);
		}

		return new ASPRule(body, weight, level, wal);
	}

	@Override
	public ASPHead visit(ASTHead node, Object data) {
		List<ASPLiteral> head_atoms = new ArrayList<ASPLiteral>();
		for (int i = 0; i < node.jjtGetNumChildren(); ++i) {
			if (node.jjtGetChild(i) instanceof ASTHeadElementsList) {
				head_atoms = visit((ASTHeadElementsList) node.jjtGetChild(i), null);
			}
		}
		return new ASPHead(head_atoms);
	}

	@Override
	public List<ASPLiteral> visit(ASTHeadElementsList node, Object data) {
		List<ASPLiteral> elements = new ArrayList<ASPLiteral>();
		for (int i = 0; i < node.jjtGetNumChildren(); ++i) {
			if (node.jjtGetChild(i) instanceof ASTAtom) {
				ASPLiteral a = visit((ASTAtom) node.jjtGetChild(i), null);
				elements.add(a);
			}
		}
		return elements;
	}

	@Override
	public List<ASPBodyElement> visit(ASTBodyList node, Object data) {
		List<ASPBodyElement> rule_bodies = new ArrayList<ASPBodyElement>();
		for (int i = 0; i < node.jjtGetNumChildren(); ++i) {
			if (node.jjtGetChild(i) instanceof ASTBody) {
				List<ASPBodyElement> rule_body = visit((ASTBody) node.jjtGetChild(i), null);
				rule_bodies.addAll(rule_body);
			}
		}
		return rule_bodies;
	}

	@Override
	public List<ASPBodyElement> visit(ASTBody node, Object data) {
		List<ASPBodyElement> rule_body = new ArrayList<ASPBodyElement>();
		for (int i = 0; i < node.jjtGetNumChildren(); ++i) {
			if (node.jjtGetChild(i) instanceof ASTNAFLiteral) {
				ASPBodyElement l = visit((ASTNAFLiteral) node.jjtGetChild(i), null);
				rule_body.add(l);
			} else if (node.jjtGetChild(i) instanceof ASTAggregate) {
				Aggregate l = visit((ASTAggregate) node.jjtGetChild(i), null);
				rule_body.add(l);
			}
		}
		return rule_body;
	}

	// TODO Add parsing for choice rules
	@Override
	public Object visit(ASTChoice node, Object data) {
		throw new UnsupportedOperationException("Choice rules are not currently supported by this parser.");
	}

	@Override
	public List<ASPRule> visit(ASTChoiceElementList node, Object data) {
		// TODO
		throw new UnsupportedOperationException("Choice rules are not currently supported by this parser.");
	}

	@Override
	public ASPRule visit(ASTChoiceElement node, Object data) {
		// TODO
		throw new UnsupportedOperationException("Choice rules are not currently supported by this parser.");
	}

	@Override
	public Aggregate visit(ASTAggregate node, Object data) {
		String func;
		ASPOperator.AggregateFunction result_op = ASPOperator.AggregateFunction.COUNT;
		List<AggregateElement> agg_elements = new LinkedList<AggregateElement>();

		int i = 0;
		if (node.jjtGetChild(0) instanceof ASTTerm) {
			// TODO Finish implementing this case
			i += 2;
		}

		func = visit((ASTAggrFunc) node.jjtGetChild(i), null);
		if (func.equals("#count"))
			result_op = ASPOperator.AggregateFunction.COUNT;
		else if (func.equals("#max"))
			result_op = ASPOperator.AggregateFunction.MAX;
		else if (func.equals("#min"))
			result_op = ASPOperator.AggregateFunction.MIN;
		else if (func.equals("#sum"))
			result_op = ASPOperator.AggregateFunction.SUM;
		else
			try {
				throw new ParseException("Parser returned unknown operator");
			} catch (ParseException e) {
				e.printStackTrace();
			}

		i++;
		for (; i < node.jjtGetNumChildren(); ++i) {
			if (node.jjtGetChild(i) instanceof ASTAggrElementList) {
				agg_elements = visit((ASTAggrElementList) node.jjtGetChild(i), null);
			} else
				break;
		}

		Aggregate a = new Aggregate(result_op, agg_elements);

		if ((node.jjtGetNumChildren() > i) && node.jjtGetChild(i + 1) instanceof ASTTerm) {
			// TODO Finish implementing this case
		}

		return a;
	}

	@Override
	public List<AggregateElement> visit(ASTAggrElementList node, Object data) {
		List<AggregateElement> agg_elem_list = new LinkedList<AggregateElement>();
		for (int i = 0; i < node.jjtGetNumChildren(); ++i) {
			if (node.jjtGetChild(i) instanceof ASTAggrElement) {
				AggregateElement a = visit((ASTAggrElement) node.jjtGetChild(i), null);
				agg_elem_list.add(a);
			}
		}
		return agg_elem_list;
	}

	@Override
	public AggregateElement visit(ASTAggrElement node, Object data) {
		AggregateElement a;
		List<Term<?>> l = new ArrayList<Term<?>>();
		List<ASPBodyElement> l2 = new ArrayList<ASPBodyElement>();
		for (int i = 0; i < node.jjtGetNumChildren(); ++i) {
			if (node.jjtGetChild(i) instanceof ASTTermList) {
				l = visit((ASTTermList) node.jjtGetChild(i), null);

			} else if (node.jjtGetChild(i) instanceof ASTNAFLiteralList) {
				l2 = visit((ASTNAFLiteralList) node.jjtGetChild(i), null);

			}
		}

		a = new AggregateElement(l, l2);
		return a;
	}

	// TODO Fix parsing of Optimize Statements, possibly add new syntax element
	@Override
	public List<ASPRule> visit(ASTOpt node, Object data) {
		List<ASPRule> optelements = new ArrayList<ASPRule>();
		for (int i = 0; i < node.jjtGetNumChildren(); ++i) {
			if (node.jjtGetChild(i) instanceof ASTOptElementList) {
				optelements.addAll(visit((ASTOptElementList) node.jjtGetChild(i), null));
			}
		}

		if (node.maximize) {
			for (ASPRule w : optelements) {
				Term<?> weight = w.getWeight();
				w.setWeight(new ArithmeticTerm(ASPOperator.ArithmeticOperator.MINUS, weight));
			}
		}
		return optelements;
	}

	@Override
	public List<ASPRule> visit(ASTOptElementList node, Object data) {
		// TODO
		throw new UnsupportedOperationException(
				"Optimize statements are not currently fully supported by this parser.");
	}

	@Override
	public ASPRule visit(ASTOptElement node, Object data) {
		List<Term<?>> terms = visit((ASTWeight) node.jjtGetChild(0), null);
		Term<?> weight = terms.remove(0);
		Term<?> level = null;
		if (node.hasLevel)
			level = terms.remove(0);

		List<ASPBodyElement> nafliterals = new ArrayList<ASPBodyElement>();

		for (int i = 1; i < node.jjtGetNumChildren(); ++i) {
			if (node.jjtGetChild(i) instanceof ASTNAFLiteralList) {
				nafliterals = visit((ASTNAFLiteralList) node.jjtGetChild(i), null);
			}
		}

		ASPRule wc = new ASPRule(nafliterals, weight, terms);
		if (level != null)
			wc.setLevel(level);
		return wc;
	}

	@Override
	public List<Term<?>> visit(ASTWeight node, Object data) {
		List<Term<?>> terms = new ArrayList<Term<?>>();
		Term<?> t1 = visit((ASTTerm) node.jjtGetChild(0), null);
		terms.add(t1);
		if (node.jjtGetNumChildren() <= 1)
			return terms;

		for (int i = 1; i < node.jjtGetNumChildren(); ++i) {
			if (node.jjtGetChild(i) instanceof ASTTerm) {
				Term<?> t2 = visit((ASTTerm) node.jjtGetChild(i), null);
				terms.add(t2);
			} else if (node.jjtGetChild(i) instanceof ASTTermList) {
				List<Term<?>> t3 = visit((ASTTermList) node.jjtGetChild(i), null);
				terms.addAll(t3);
				break;
			}
		}
		return terms;
	}

	@Override
	public List<ASPBodyElement> visit(ASTNAFLiteralList node, Object data) {
		List<ASPBodyElement> naflits = new LinkedList<ASPBodyElement>();
		for (int i = 0; i < node.jjtGetNumChildren(); ++i) {
			if (node.jjtGetChild(i) instanceof ASTNAFLiteral) {
				ASPBodyElement n = visit((ASTNAFLiteral) node.jjtGetChild(i), null);
				naflits.add(n);
			}
		}
		return naflits;
	}

	@Override
	public ASPBodyElement visit(ASTNAFLiteral node, Object data) {
		ASPBodyElement at = null;
		for (int i = 0; i < node.jjtGetNumChildren(); ++i) {
			if (node.jjtGetChild(i) instanceof ASTBuiltInAtom)
				at = visit((ASTBuiltInAtom) node.jjtGetChild(i), null);
			else if (node.jjtGetChild(i) instanceof ASTAtom) {
				at = visit((ASTAtom) node.jjtGetChild(i), null);
				if (node.nafneg)
					return new DefaultNegation(at);
			}
		}
		return at;
	}

	@Override
	public ASPLiteral visit(ASTAtom node, Object data) {
		String name = "";
		List<Term<?>> terms = new ArrayList<Term<?>>();

		for (int i = 0; i < node.jjtGetNumChildren(); ++i) {
			if (node.jjtGetChild(i) instanceof ASTID) {
				name = visit((ASTID) node.jjtGetChild(i), null);
			}
			if (node.jjtGetChild(i) instanceof ASTTermList) {
				terms = visit((ASTTermList) node.jjtGetChild(i), null);
			}
		}
		Predicate p = new Predicate(name, terms.size());
		ASPAtom at = new ASPAtom(p, terms);

		if (node.neg)
			return new StrictNegation(at);
		else
			return at;
	}

	@Override
	public ComparativeAtom visit(ASTBuiltInAtom node, Object data) {
		String op = node.operator;
		ASPOperator.BinaryOperator result_op = ASPOperator.BinaryOperator.EQ;

		if (op.equals("="))
			result_op = ASPOperator.BinaryOperator.EQ;
		else if (op.equals("<>") || op.equals("!="))
			result_op = ASPOperator.BinaryOperator.NEQ;
		else if (op.equals("<"))
			result_op = ASPOperator.BinaryOperator.LT;
		else if (op.equals(">"))
			result_op = ASPOperator.BinaryOperator.GT;
		else if (op.equals("<="))
			result_op = ASPOperator.BinaryOperator.LEQ;
		else if (op.equals(">="))
			result_op = ASPOperator.BinaryOperator.GEQ;
		else
			try {
				throw new ParseException("Parser returned unknown operator");
			} catch (ParseException e) {
				e.printStackTrace();
			}

		List<Term<?>> terms = new ArrayList<Term<?>>();
		for (int i = 0; i < node.jjtGetNumChildren(); ++i) {
			if (node.jjtGetChild(i) instanceof ASTTerm) {
				Term<?> t = visit((ASTTerm) node.jjtGetChild(i), null);
				terms.add(t);
			}
		}

		return new ComparativeAtom(result_op, terms.get(0), terms.get(1));
	}

	@Override
	public List<Term<?>> visit(ASTTermList node, Object data) {
		List<Term<?>> term_list = new LinkedList<Term<?>>();
		for (int i = 0; i < node.jjtGetNumChildren(); ++i) {
			if (node.jjtGetChild(i) instanceof ASTTerm) {
				Term<?> a = visit((ASTTerm) node.jjtGetChild(i), null);
				term_list.add(a);
			}
		}
		return term_list;
	}

	@Override
	public Term<?> visit(ASTTerm node, Object data) {
		Term<?> t = null;
		if (node.jjtGetChild(0) instanceof ASTID) {
			for (int i = 1; i < node.jjtGetNumChildren(); ++i) {
				if (node.jjtGetChild(i) instanceof ASTTermList) {
					List<Term<?>> terms = visit((ASTTermList) node.jjtGetChild(i), null);
					Functor f = new Functor((((ASTID) node.jjtGetChild(0)).name));
					return new FunctionalTerm(f, terms);
				}
			}
			t = new Constant(((ASTID) node.jjtGetChild(0)).name);
		} else if (node.jjtGetChild(0) instanceof ASTNumber) {
			t = new NumberTerm((((ASTNumber) node.jjtGetChild(0)).number));
		} else if (node.jjtGetChild(0) instanceof ASTVar) {
			t = new Variable(((ASTVar) node.jjtGetChild(0)).name);
		} else if (node.jjtGetChild(0) instanceof ASTString) {
			t = new Constant((((ASTString) node.jjtGetChild(0)).name));
		} else if (node.neg) {
			Term<?> t1 = visit((ASTTerm) node.jjtGetChild(1), null);
			t = new ArithmeticTerm(new ArithmeticTerm(ASPOperator.ArithmeticOperator.MINUS, t1));
		}

		if (node.jjtGetNumChildren() == 0)
			return t;

		for (int i = 1; i < node.jjtGetNumChildren(); ++i) {
			if (node.jjtGetChild(i) instanceof ASTAriTerm) {
				Term<?> t2 = visit((ASTAriTerm) node.jjtGetChild(i), null);

				ASPOperator.ArithmeticOperator op = ASPOperator.ArithmeticOperator.PLUS;
				String sop = ((ASTAriTerm) node.jjtGetChild(i)).op;
				if (sop.equals("+"))
					op = ASPOperator.ArithmeticOperator.PLUS;
				else if (sop.equals("-"))
					op = ASPOperator.ArithmeticOperator.MINUS;
				else if (sop.equals("/"))
					op = ASPOperator.ArithmeticOperator.DIV;
				else if (sop.equals("*"))
					op = ASPOperator.ArithmeticOperator.TIMES;
				else if (sop.equals(""))
					break;
				else
					try {
						throw new ParseException("Unknown arithmetic operator.");
					} catch (ParseException e) {
						e.printStackTrace();
					}

				return new ArithmeticTerm(op, t, t2);
			}
		}
		return t;
	}

	@Override
	public Term<?> visit(ASTAriTerm node, Object data) {
		for (int i = 1; i < node.jjtGetNumChildren(); ++i) {
			if (node.jjtGetChild(i) instanceof ASTTerm)
				return visit((ASTTerm) node.jjtGetChild(i), null);
		}
		return null;
	}

	@Override
	public String visit(ASTBinop node, Object data) {
		return node.operator;
	}

	@Override
	public String visit(ASTArithop node, Object data) {
		return node.operator;
	}

	@Override
	public String visit(ASTAggrFunc node, Object data) {
		return node.func;
	}

	@Override
	public String visit(ASTOptFunc node, Object data) {
		return node.func;
	}

	@Override
	public Integer visit(ASTNumber node, Object data) {
		return node.number;
	}

	@Override
	public String visit(ASTVar node, Object data) {
		return node.name;
	}

	@Override
	public String visit(ASTID node, Object data) {
		return node.name;
	}

	@Override
	public String visit(ASTString node, Object data) {
		return node.name;
	}
}
/*
 * JavaCC - OriginalChecksum=58043862b38d06e3c410ec37ecf72d1e (do not edit this
 * line)
 */