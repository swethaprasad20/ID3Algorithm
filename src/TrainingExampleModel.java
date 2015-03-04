import java.util.Arrays;

/**
 * 
 * @author swethaprasad
 *
 *each row in the training set is converted to TrainingExampleModel. 
 */
public class TrainingExampleModel {

	// saves attribute values of a row in training set file
	private Integer[] attr;


	// saves class value of a row in training set file;
	private int classLabel;


	//splits the line read from training set by white space characters and saves the values in instant variables.
	public void populateTrainingSet(String line){

		String[] attributeValues=line.split("\\s+");
		this.attr= new Integer[attributeValues.length-1];

		for (int i=0;i<attributeValues.length;i++){
			if(i==attributeValues.length-1){
				this.classLabel=Integer.parseInt(attributeValues[6]);
			}else{
				this.attr[i]=Integer.parseInt(attributeValues[i]);
			}
		}

	}



	public Integer[] getAttr() {
		return attr;
	}



	@Override
	public String toString() {
		return "AttributeModel [attr=" + Arrays.toString(attr)
				+ ", classLabel=" + classLabel + "]";
	}



	public int getClassLabel() {
		return classLabel;
	}




}
