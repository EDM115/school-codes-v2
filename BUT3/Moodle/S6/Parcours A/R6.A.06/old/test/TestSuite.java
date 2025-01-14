import java.util.ArrayList;
import java.util.Iterator;
/**
* la classe TestSuite représente une suite de tests
*/
public abstract class TestSuite implements Test {
 private ArrayList<Test> fTests = new ArrayList<Test>();
 
 public TestSuite(Class theClass) {
  //Construit une TestSuite à partir d'une classe.
 }
 
 public void run(TestResult result) {
   for (Test test : fTests)
         test.run(result);
 }
 public void addTest(Test test){
  fTests.add(test);
 }
 public int testCount(){
  return fTests.size();
 }
}
