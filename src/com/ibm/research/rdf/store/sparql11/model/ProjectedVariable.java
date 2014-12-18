package com.ibm.research.rdf.store.sparql11.model;

public class ProjectedVariable {
	private Variable name;
	private Expression exp;
	private static int id = 0;
	
	public static void resetId() 
	{
		id = 0;
	}
	
	public ProjectedVariable(String s) 
	{
		this.name = new Variable(s);
		this.exp = null;
	}
	
	public ProjectedVariable(String s, Expression e)
	{
		this.name = new Variable(s);
		this.exp = e;
	}
	
	public ProjectedVariable(Expression e)
	{
		this.exp = e;
		id++;
		this.name = new Variable("v_"+id);
	}
	
	public Variable getVariable() {return this.name;}
	
	public Expression getExpression() {return this.exp;}
	
	public void setExpression(Expression e) {this.exp = e;}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((exp == null) ? 0 : exp.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ProjectedVariable other = (ProjectedVariable) obj;
		if (exp == null) {
			if (other.exp != null)
				return false;
		} else if (!exp.equals(other.exp))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	public String toString()
	{
		if ((this.exp != null) && (this.name != null)) {
			return "(" + exp.toString() + " AS " + name.getName() + ")";
		} 
		else if (this.exp != null) {
			return exp.toString();
		} 
		else if (this.name != null) {
			return name.getName();
		}
		else {
			return "";
		}
	}
}