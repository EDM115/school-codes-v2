/**
* la classe ActiveTestSuite permet de lancer des tests en parallèle.
* Lance chaque test dans une tâche séparée (thread) et attend que toutes
* les tâches soient terminées.
*/
public class ActiveTestSuite extends TestSuite{
 
 public ActiveTestSuite(Class theClass){
     super(theClass);

 }
 public void run(TestResult result){
   // redéfinit la méthode run héritée
 }
}
