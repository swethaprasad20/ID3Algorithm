import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 
 * @author swethaprasad
 *
 *Defines each node in the tree
 */
public class TreeNode {

	// entropy of the node
	private double entropy;

	// branches from the node
	private TreeNode[] branches;
	
	/*
	// attribute evaluated when a branch is created from the parent node to this node
	private int[] attrAssociatedLink;
	
	// value of the attribute evaluated when a branch is created from the parent node to this node
	private int[] attrAssociatedValueLink;
*/
	// no of ones in the training subset that belongs to the node
	private int noOfOnes=0;

	//no of ones in the training subset that belongs to the node
	private int noOfZeros=0;

	// is the node leaf or now
	private boolean isLeaf=false;

	// attribute evaluated while creating this node. used from printing purpose 
	private int attributeAssociated=-1;

	// value of attribute evaluated while creating this node. used from printing purpose
	private int associatedAttributeValue=-1;
	
	// class label for this node
	private int classLabel=-1;
	
	// attributes that are evaluated from parent path to the current node
	private List<Integer> attributesEvaluated=null;
	
	
	// depth of the node in the tree . used for printing purpose.
	private int depth;
	
	// saves if else statements while constructing the tree that is used to test the decision tree 
	private String expression;
	

	public String getExpression() {
		return expression;
	}
	


	public void setExpression(String expression) {
		this.expression = expression;
	}


	public int getDepth() {
		return depth;
	}


	public void setDepth(int depth) {
		this.depth = depth;
	}


	public int getClassLabel() {
		return classLabel;
	}


	public void setClassLabel(int classLabel) {
		this.classLabel = classLabel;
	}


	public boolean isLeaf() {
		return isLeaf;
	}


	public void setLeaf(boolean isLeaf) {
		this.isLeaf = isLeaf;
	}


	public int getAttributeAssociated() {
		return attributeAssociated;
	}


	public void setAttributeAssociated(int attributeAssociated) {
		this.attributeAssociated = attributeAssociated;
	}


	public int getAssociatedAttributeValue() {
		return associatedAttributeValue;
	}


	public void setAssociatedAttributeValue(int associatedAttributeValue) {
		this.associatedAttributeValue = associatedAttributeValue;
	}


	public void setNoOfOnesandZeros(List<TrainingExampleModel> trainingSet){

		for(TrainingExampleModel attr:trainingSet){
			if(attr.getClassLabel()==0){
				noOfZeros++;
			}else{
				noOfOnes++;
			}
		}
	}


	public double getEntropy() {
		return entropy;
	}


	public void setEntropy(int trainingSetSize) {
		if(trainingSetSize==0){
			this.entropy=0;
			return;
		}
		//System.out.println("No of Ones" + this.noOfOnes+" No of zeros "+this.noOfZeros+" training set "+trainingSetSize);
		float probabilityOfOne = (float)this.noOfOnes/trainingSetSize;
		float probabilityOfZero= (float)this.noOfZeros/trainingSetSize;

		this.entropy=MathLibrary.getEntropy(MathLibrary.roundToThreeDecimal(probabilityOfZero), MathLibrary.roundToThreeDecimal(probabilityOfOne));

		//System.out.println(entropy);
	}




	public TreeNode[] getBranches() {
		return branches;
	}


	public void setBranches(int size) {
		this.branches = new TreeNode[size];
	}


	public int getNoOfOnes() {
		return noOfOnes;
	}


	@Override
	public String toString() {
		return "TreeNode [attributeAssociated=" + attributeAssociated
				+ ", depth "+depth
				+ ", noOfZeros=" + noOfZeros + ", isLeaf=" + isLeaf
				+ ", attributesEvaluated="+attributesEvaluated
				+ ", associatedAttributeValue=" + associatedAttributeValue
				+", classLabel="+classLabel
				+", entropy=" + entropy + ", noOfOnes=" + noOfOnes
				//+", attrAssociatedLink=" + Arrays.toString(attrAssociatedLink)
				//+", attrAssociatedValueLink=" + Arrays.toString(attrAssociatedValueLink) 
				+ ", branches="+ Arrays.toString(branches)+" ]";
	}


	public void setNoOfOnes(int noOfOnes) {
		this.noOfOnes = noOfOnes;
	}


	public int getNoOfZeros() {
		return noOfZeros;
	}


	public void setNoOfZeros(int noOfZeros) {
		this.noOfZeros = noOfZeros;
	}


	public List<Integer> getAttributesEvaluated() {
		return attributesEvaluated;
	}


	public void addAttributesEvaluated(int attr) {
		if(this.attributesEvaluated ==null){
			attributesEvaluated= new ArrayList<Integer>();
		}
		attributesEvaluated.add(attr);
	}


	public void setAttributesEvaluated(List<Integer> attributesEvaluated) {
	
		this.attributesEvaluated = new ArrayList<Integer>();
		if(attributesEvaluated!=null){
			this.attributesEvaluated.addAll(attributesEvaluated);
		}
		
	}


	/*public int[] getAttrAssociatedLink() {
		return attrAssociatedLink;
	}


	public void setAttrAssociatedLink(int size) {
		this.attrAssociatedLink = new int[size];
	}


	public int[] getAttrAssociatedValueLink() {
		return attrAssociatedValueLink;
	}


	public void setAttrAssociatedValueLink(int size) {
		this.attrAssociatedValueLink = new int[size];
	}*/


	public void addExpression(String string) {
		if(this.expression==null){
			this.expression= new String();
		}
		this.expression= this.expression.concat(string);
		
	}
	
	




}
