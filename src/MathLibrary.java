import java.math.BigDecimal;
import java.math.MathContext;


/**
 * 
 * @author swethaprasad
 *
 *Gives set of mathematical functions required to calculate entropy.
 */
public class MathLibrary {


		public static double roundToThreeDecimal(double d){

			BigDecimal bd = new BigDecimal(d);
			bd = bd.round(new MathContext(4));
			return (bd.doubleValue());

			
		}
		
		
		public static double getEntropy(double probabilityOfZero,double probabilityOfOne){
			float entropy;
			if(probabilityOfOne==0){
				entropy= (float)((-probabilityOfZero*Math.log(probabilityOfZero))/Math.log(2));
			}else if(probabilityOfZero==0){
				entropy= (float)((-probabilityOfOne*Math.log(probabilityOfOne))/Math.log(2));
			}else{
				entropy= (float)((-probabilityOfOne*Math.log(probabilityOfOne) -probabilityOfZero*Math.log(probabilityOfZero))/Math.log(2));
			}

			return roundToThreeDecimal(entropy);
		}
	}



