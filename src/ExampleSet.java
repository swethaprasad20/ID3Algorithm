import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 
 * @author swethaprasad
 *
 *this class saves training input file as list of training examples. 
 */

public class ExampleSet {

	// each element in the list defines one example.
	private List<TrainingExampleModel> trainingExamples;
	
	// saves number of attributes in the training set.
	private int NoOfAttributesInExample;
	
	
	// saves the unique value that each of the attribute can have.
	private Map<Integer,Set<Integer>> uniqueValuesInEachAttr;



	public void setTrainingExamples(TrainingExampleModel example){
		
		if(this.trainingExamples==null){
			trainingExamples= new ArrayList<TrainingExampleModel>();
		}
		trainingExamples.add(example);
		
		Integer attr[]=example.getAttr();
		
		int i=0;
		for(int attrValue:attr){
			if(uniqueValuesInEachAttr==null){
				uniqueValuesInEachAttr= new HashMap<Integer, Set<Integer>>();
			}
			if(uniqueValuesInEachAttr.get(i)==null){
				Set<Integer> uniqueValueSet= new HashSet<Integer>();
				uniqueValueSet.add(attrValue);
				uniqueValuesInEachAttr.put(i, uniqueValueSet);
			}else{
				uniqueValuesInEachAttr.get(i).add(attrValue);
			}
			i++;
		}
		
		
	}




	public Map<Integer, Set<Integer>> getUniqueValuesInEachAttr() {
		return uniqueValuesInEachAttr;
	}


	public void setUniqueValuesInEachAttr(
			Map<Integer, Set<Integer>> uniqueValuesInEachAttr) {
		this.uniqueValuesInEachAttr = uniqueValuesInEachAttr;
	}


	public List<TrainingExampleModel> getTrainingExamples() {
		return trainingExamples;
	}


	public int getNoOfAttributesInExample() {
		return NoOfAttributesInExample;
	}


	public void setNoOfAttributesInExample(int noOfAttributesInExample) {
		NoOfAttributesInExample = noOfAttributesInExample;
	}





	
	
}
