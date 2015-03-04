import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 
 * @author swethaprasad
 *
 *this class contains the core functionality of the ID3 algorithm. functionality of this class is to construct decision
 *tree
 */

public class TreeConstruction {
	//saves maximum occuring class value in the training st file.
	private int maxOccuringClassValue;
	// saves total number of attributes in the training set.
	private List<Integer> masterAttributeValues;
	private String resultantTreeExpression="";

	private Map<Integer,Set<Integer>> uniqueValuesOfAttr;


	/**
	 * 
	 * @param trainingSet
	 * 
	 * called from main method . created root node and calls a recursive function to construct rest of the nodes.
	 */
	public String constructTree (ExampleSet trainingSet){
		this.uniqueValuesOfAttr=trainingSet.getUniqueValuesInEachAttr();
		

		// creating root node and saving number of ones and zeros in the training set , entropy of the node , depth.
		TreeNode rootNode = new TreeNode();
		rootNode.setNoOfOnesandZeros(trainingSet.getTrainingExamples());
		rootNode.setEntropy(trainingSet.getTrainingExamples().size());
		rootNode.setDepth(0);

		rootNode.setExpression("if(");
		if(rootNode.getNoOfOnes() > rootNode.getNoOfZeros()){
			this.maxOccuringClassValue=1;
		}else{
			this.maxOccuringClassValue=0;
		}
		//saves number of attributes as an array list. 
		//for example if there are 4 attributes, this list contains Interger objects 0,1,2,3 in it.
		masterAttributeValues= new ArrayList<Integer>();
		for(int i=0;i<trainingSet.getNoOfAttributesInExample();i++){
			masterAttributeValues.add(i);
		}

		//calling a recursive function to construct rest of the tree.
		recursivelyContructTree(rootNode,trainingSet.getTrainingExamples());
		//System.out.println(rootNode);
		//System.out.println(resultantTreeExpression);
		return (resultantTreeExpression.replaceAll("else $", ""));
	}


	/**
	 * recursive function that is called by each node in the tree to find its branches until the node is declared a leaf.
	 * @param parentNode
	 * @param trainingSubSets
	 * @param attrSelectedParam
	 */
	public void recursivelyContructTree(TreeNode parentNode,List<TrainingExampleModel> trainingSubSets){
		//stop growing the tree if a parent node is a leaf
		if(parentNode.isLeaf()){
			return;
		}

		// find an attribute with the maximum information gain
		int attr= findAttrWithMaxInfoGain(parentNode,parentNode.getAttributesEvaluated(),trainingSubSets);

		// group training set of into subsets based on  different values for the selected attribute
		Map<Integer,List<TrainingExampleModel>> branchesOfAttributes = findBranchesOfAttribute(attr,trainingSubSets);

		// distinct values that an attribute can have.
		Set<Integer> attributeDistinctValue=uniqueValuesOfAttr.get(attr);
		int count=0;

		// initializing parent node with number of branches it will have and attribute value and name for each of the branch.
		parentNode.setBranches(attributeDistinctValue.size());
		//parentNode.setAttrAssociatedLink(attributeDistinctValue.size());
		//parentNode.setAttrAssociatedValueLink(attributeDistinctValue.size());


		//creating node for each of the distinct values of the selected attribute.
		for(Integer attrValue : attributeDistinctValue){
			//System.out.println("with in the loop "+parentNode.getAttributesEvaluated());

			TreeNode node=new TreeNode();

			// setting the attribute associated with the node for printing purpose.
			node.setAttributeAssociated(attr);
			node.setAssociatedAttributeValue(attrValue);

			// setting number of zeros and ones in the brancha nand setting entropy and depth.
			if(branchesOfAttributes.get(attrValue)==null){
				//node.setNoOfOnesandZeros();
				node.setEntropy(0);
			}else{
				node.setNoOfOnesandZeros(branchesOfAttributes.get(attrValue));
				node.setEntropy(branchesOfAttributes.get(attrValue).size());
			}
			node.setDepth(parentNode.getDepth()+1);

			/*setting all the attributes that have been evaluated when this node is reached.
			this is equal to all the attributes evaluated when it's parent was reached and the attribute that we are
			evaluating now*/
			node.setAttributesEvaluated(parentNode.getAttributesEvaluated());
			node.addAttributesEvaluated(attr);

			//System.out.println("node evaluated "+node.getAttributesEvaluated());

			// pointing parents node branch to the current node and also setting attribute and value linked to this node.
			parentNode.getBranches()[count]=node;
			//parentNode.getAttrAssociatedLink()[count]=attr;
			//parentNode.getAttrAssociatedValueLink()[count]=attrValue;


			// if the entropy of the node is 0 or all the attributes are evaluated , then set the node as leaf.
			if(node.getEntropy()==0.0 || node.getAttributesEvaluated().size()==masterAttributeValues.size()){
				node.setLeaf(true);

				// setting the class label
				if(node.getNoOfOnes()>node.getNoOfZeros()){
					node.setClassLabel(1);
				}else if(node.getNoOfOnes()<node.getNoOfZeros()) {
					node.setClassLabel(0);
				}else {
					node.setClassLabel(this.maxOccuringClassValue);
				}
				// constructing if else statements that is used later to test the tree
				// since this is a leaf node , class value is tested.
				node.setExpression(parentNode.getExpression().concat("attr["+(new Integer(attr))+"]=="+attrValue+"){ (classvalue=="+node.getClassLabel()+"?rightPrediction++:wrongPrediction++); continue; } else "));
				resultantTreeExpression+=node.getExpression();
			}else{
				// constructing if else statements that is used later to test the tree
				//since this is not a leaf note , and operation is added to if else statement.
				node.setExpression(parentNode.getExpression().concat("attr["+(new Integer(attr))+"]=="+attrValue+"  && "));

			}

			count++;
			//printing the node
			IOUtilities.printNode(node);
			// calling the function to find children of this node.
			recursivelyContructTree(node,branchesOfAttributes.get(attrValue));
		}


	}



	/**
	 * for a given attribute the function separates the input set into multiple subset based on the distinct values of the attribute.
	 * @param selectedAttr
	 * @param trainingSubSets
	 * @return
	 */
	private Map<Integer,List<TrainingExampleModel>> findBranchesOfAttribute(int selectedAttr, List<TrainingExampleModel> trainingSubSets) {
		// key ==> attribute value
		// value ==> list of training set that have attribute value selected as key for the given attribute
		Map<Integer,List<TrainingExampleModel>> attributeBranches=new HashMap<Integer,List<TrainingExampleModel>>();

		for(TrainingExampleModel example:trainingSubSets){
			Integer[] attribute = example.getAttr();

			if(attributeBranches.get(attribute[selectedAttr])==null){
				List<TrainingExampleModel> trainingSubset = new ArrayList<TrainingExampleModel>();
				trainingSubset.add(example);
				attributeBranches.put(attribute[selectedAttr], trainingSubset);
			}else{
				attributeBranches.get(attribute[selectedAttr]).add(example);
			}
		}



		return attributeBranches;
	}


	// finds the attribute with maximum information gain
	public int findAttrWithMaxInfoGain(TreeNode node, List<Integer> attributesEvaluated,List<TrainingExampleModel> trainingSubSets){

		double maxInfoGain=0;
		double infoGain=0;
		// consider only those attributes that are not yet evaluated in the path of the node.
		List<Integer> attributedYetToBeEvolved = new ArrayList<Integer>();
		attributedYetToBeEvolved.addAll(masterAttributeValues);
		if(attributesEvaluated!=null){
			attributedYetToBeEvolved.removeAll(attributesEvaluated);
		}


		int selectedAttr=attributedYetToBeEvolved.get(0);
		for(int attr : attributedYetToBeEvolved){
			//calculating information gain
			infoGain = calculateInfoGain(attr,node.getEntropy(),trainingSubSets);

			if(infoGain>maxInfoGain){
				maxInfoGain=infoGain;
				selectedAttr=attr;
			}
		}

		return selectedAttr;

	}



	private double calculateInfoGain(int attr, double parentNodeEntropy,List<TrainingExampleModel> trainingSubSets) {

		Map<Integer,Integer> valueCount = new HashMap<Integer, Integer>();
		Map<Integer,Integer> noOfones= new HashMap<Integer,Integer>();
		Map<Integer,Integer> noOfZeros= new  HashMap<Integer,Integer>();


		//for each of the example, calculate the number of examples for each of the distinct values of the attribute
		// calculate the number of examples with zero class value and number of examples with one class value for each of the distinct values of the attribute
		for(TrainingExampleModel example : trainingSubSets){

			Integer[] attribute = example.getAttr();


			if(valueCount.get(attribute[attr])==null){
				valueCount.put(attribute[attr], 1);
			}else{
				int exisitngValue= valueCount.get(attribute[attr]);
				valueCount.put(attribute[attr], ++exisitngValue);
			}

			if(example.getClassLabel()==1){
				if(noOfones.get(attribute[attr])==null){
					noOfones.put(attribute[attr], 1);
				}else{
					int exisitngValue= noOfones.get(attribute[attr]);
					noOfones.put(attribute[attr], ++exisitngValue);
				}
			}else if(example.getClassLabel()==0){
				if(noOfZeros.get(attribute[attr])==null){
					noOfZeros.put(attribute[attr], 1);
				}else{
					int exisitngValue= noOfZeros.get(attribute[attr]);
					noOfZeros.put(attribute[attr], ++exisitngValue);
				}
			}



		}


		Set<Integer> distinctValues=valueCount.keySet();

		float conditionalEntropy=0,totalCount;
		double probabilityOfOne=0,probabilityOfZero=0,entropyOftheBranch=0;
		//calculating conditional probability
		// conditional probability = summation of (E * P) , where E is entropy and P is the probability of occurance of exmample
		for(int atrrValue:distinctValues){

			Integer noOfZerosForAttr=noOfZeros.get(atrrValue)==null?0:noOfZeros.get(atrrValue);
			int noOfOnesforAttr=noOfones.get(atrrValue)==null?0:noOfones.get(atrrValue);
			totalCount=noOfOnesforAttr+noOfZerosForAttr;

			probabilityOfZero=(double) noOfZerosForAttr/totalCount;
			probabilityOfOne=(double) noOfOnesforAttr/totalCount;

			entropyOftheBranch=(float) MathLibrary.getEntropy(MathLibrary.roundToThreeDecimal(probabilityOfZero), MathLibrary.roundToThreeDecimal(probabilityOfOne));

			// finding the summation of E * P
			conditionalEntropy+=MathLibrary.roundToThreeDecimal(totalCount/trainingSubSets.size()*entropyOftheBranch);
		}


		// information gain = entropy of the parent node - conditional entropy;
		return (parentNodeEntropy-conditionalEntropy);
	}

}
