import groovy.lang.Binding;
import groovy.lang.GroovyShell;

/** 
 * 
 * @author swethaprasad
 *
 *This class tests the decision tree. 
 *It uses Groovy utilities to run the if else statements, which were constructed while building the decision tree
 */

public class TestDecisionTree {





/**
 * 
 * @param exampleSet : input set - either training set or test set
 * @param decisionTreeExpression : if else statements constructed while building decision tree
 * @return
 */
	public double testDecisionTree(ExampleSet exampleSet, String decisionTreeExpression){
		
		Binding binding = new Binding();
		binding.setVariable("exampleSet", exampleSet);
		GroovyShell shell = new GroovyShell(binding);	
		
		int rightPrediction=0,wrongPrediction=0;
		binding.setVariable("rightPrediction", rightPrediction);
		binding.setVariable("wrongPrediction", wrongPrediction);
		
		String initializationString=" for(TrainingExampleModel example: exampleSet.getTrainingExamples()){ Integer[] attr=example.getAttr();int classvalue=example.getClassLabel();";
		decisionTreeExpression=initializationString.concat(decisionTreeExpression).concat("}");
		
		shell.evaluate(decisionTreeExpression);
	
		rightPrediction=(int)binding.getVariable("rightPrediction");
		wrongPrediction=(int)binding.getVariable("wrongPrediction");
		
		return ((double)rightPrediction/(rightPrediction+wrongPrediction)*100);


	}
}
