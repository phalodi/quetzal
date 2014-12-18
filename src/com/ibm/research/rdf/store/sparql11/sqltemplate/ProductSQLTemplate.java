package com.ibm.research.rdf.store.sparql11.sqltemplate;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import com.ibm.research.rdf.store.Context;
import com.ibm.research.rdf.store.Store;
import com.ibm.research.rdf.store.config.Constants;
import com.ibm.research.rdf.store.sparql11.model.Variable;
import com.ibm.research.rdf.store.sparql11.planner.PlanNode;
import com.ibm.research.rdf.store.sparql11.sqlwriter.SQLWriterException;
import com.ibm.wala.util.collections.Pair;

public class ProductSQLTemplate extends JoinSQLTemplate {

	public ProductSQLTemplate(String templateName, PlanNode planNode,
			Store store, Context ctx, STPlanWrapper wrapper, PlanNode left,
			PlanNode right) {
		super(templateName, planNode, store, ctx, wrapper, left, right);
	}
	
	@Override
	Set<SQLMapping> populateMappings() throws SQLWriterException {
		
		varMap = new HashMap<String, Pair<String, String>>();
		HashSet<SQLMapping> mappings = new HashSet<SQLMapping>();
		
		List<String> qidSqlParam = new LinkedList<String>();
		qidSqlParam.add(getQIDMapping());
		SQLMapping qidMapping=new SQLMapping("sql_id", qidSqlParam, null);
		mappings.add(qidMapping);
		
		List<String> leftProject = new LinkedList<String>();
		getLeftProjectMapping(leftProject);
		SQLMapping pMapping=new SQLMapping("leftProject", leftProject,null);
		mappings.add(pMapping);
		
		List<String> rightProject = new LinkedList<String>();
		getRightProjectMapping(rightProject);
		SQLMapping rMapping=new SQLMapping("rightProject", rightProject,null);
		mappings.add(rMapping);

		List<String> rightTarget = new LinkedList<String>();
		rightTarget.add(wrapper.getPlanNodeCTE(right, true));
		SQLMapping tMapping=new SQLMapping("rightTarget", rightTarget,null);
		mappings.add(tMapping);
		
		List<String> leftTarget = new LinkedList<String>();
		leftTarget.add(wrapper.getPlanNodeCTE(left, true));
		SQLMapping lMapping=new SQLMapping("leftTarget", leftTarget,null);
		mappings.add(lMapping);
		
		return mappings;
	}
	
	protected void getRightProjectMapping(List<String> projectMapping) {
		Set<Variable> operatorVariables=planNode.getOperatorsVariables();
		String rightSQLCte = wrapper.getPlanNodeCTE(right, false); 
		Set<Variable> iriBoundVariables = wrapper.getIRIBoundVariables();

		Set<Variable> rightProduced = right.getProducedVariables();
		if(rightProduced != null){
			for(Variable v : rightProduced){
				if(operatorVariables.contains(v))continue;
				String vPredName = wrapper.getPlanNodeVarMapping(right,v.getName());
				projectMapping.add(rightSQLCte+"."+vPredName+" AS "+v.getName());
				String vSqlType = null;
				if(!iriBoundVariables.contains(v)){
					projectMapping.add(rightSQLCte+"."+vPredName+Constants.TYP_COLUMN_SUFFIX_IN_SPARQL_RS+" AS "+v.getName()+Constants.TYP_COLUMN_SUFFIX_IN_SPARQL_RS);
					varMap.put(v.getName(), Pair.make(rightSQLCte+"."+vPredName, vSqlType));
				}
				varMap.put(v.getName(), Pair.make(rightSQLCte+"."+vPredName, vSqlType));
			}
		}
	}

}