import java.util.ArrayList;
/**
* la classe TestResult est chargée de compter le nombre d’exécution
* de tests et de collecter les échecs
*/
public class TestResult {
	protected int fRunTests;
	protected ArrayList<Test> fFailures;
	
	public TestResult(){
		fRunTests = 0;
		fFailures = new ArrayList<Test>();
	}
	public void startTest(Test test){
		//Informe result qu'un test va démarrer.
		fRunTests = fRunTests + 1;
		
	}
	public void addFailure(Test test, Throwable t){
		// ajoute un échec à fFailures. t est l'exception causée par l'erreur.
	}
	public int failureCount(){
		// retourne le nombre d'échecs détectés.
		return fFailures.size();
	} 
}
