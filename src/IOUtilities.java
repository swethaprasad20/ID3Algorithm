import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * 
 * @author swethaprasad
 *
 *This class deals with reading data from and writing data into a file /  console.
 */

public class IOUtilities {

	// reads the input file and populates data into array list of example module.

	public ExampleSet readAttributes(String urlParam,boolean isTrainingSet) throws MalformedURLException,IOException {
		int count=0;
		URL url;
		BufferedReader reader = null;
		ExampleSet trainingSet = new ExampleSet();
		try {
			url = new URL(urlParam);
			reader = new BufferedReader(new InputStreamReader(url.openStream()));

			String line = null;
			boolean firstLine=true;

			while ((line = reader.readLine()) != null) {
				TrainingExampleModel attr= new TrainingExampleModel();
				if(firstLine){
					//trainingSet.setUniqueValues(line);
					firstLine=false;
				}else{
					attr.populateTrainingSet(line);

					trainingSet.setTrainingExamples(attr);
					count++;
				}
				/*if(count==800 && isTrainingSet){
					break;
				}*/
				 
			}

		} finally {

			if (reader != null){
				reader.close();
			}

		}

		return trainingSet;
	}


	//prints the decision tree in the format required.
	public static void printNode(TreeNode node){
		for(int i=1;i<node.getDepth();i++){
			System.out.print("| ");
		}

		System.out.println("attr"+(node.getAttributeAssociated()+1)+" = "+node.getAssociatedAttributeValue()+" : "+(node.getClassLabel()==-1?"":node.getClassLabel()));

	}
}

