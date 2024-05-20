/**
* la classe TestCase représente un test unitaire
*/
public abstract class TestCase implements Test{
 private final String fName;
 
 public TestCase(String name){
     fName = name;
 }
 public void run(TestResult result){
   result.startTest(this);
   this.setUp();
   this.runTest();
   this.tearDown();
 }

 protected void runTest(){
 }
 protected void setUp(){
 }
 protected void tearDown(){
 }
}
