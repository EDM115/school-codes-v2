import question.*;

/** ScenarioQuestion class, represents a scenario for the Question class */
public class ScenarioQuestion {
  /**
   * Main method
   *
   * @param args the arguments
   */
  public static void main(String[] args) {
    System.out.println("");
    System.out.println("\t\u001B[33mScenarioQuestion :\u001B[0m");

    System.out.println("");
    System.out.println("\u001B[36m*** Question()\u001B[0m");
    System.out.println("");

    Question question1 =
        new Question("Quelle est la couleur du cheval blanc d'Henri IV ?", "blanc");
    System.out.println(
        "Question(\"Quelle est la couleur du cheval blanc d'Henri IV ?\", \"blanc\") -> "
            + question1);
    Question question2 = new Question("Comment s'appelle le chat roux dans Garfield ?", "garfield");
    System.out.println(
        "Question(\"Comment s'appelle le chat roux dans Garfield ?\", \"garfield\") -> "
            + question2);
    Question question3 = new Question("Quelle est la capitale du Honduras ?", "tegucigalpa");
    System.out.println(
        "Question(\"Quelle est la capitale du Honduras ?\", \"tegucigalpa\") -> " + question3);
    Question question4 = new Question();
    System.out.println("Question() -> " + question4);
    Question question5 = new Question();
    System.out.println("Question() -> " + question5);

    System.out.println("");
    System.out.println("\u001B[36m*** getText()\u001B[0m");

    System.out.println("question1.getText() = " + question1.getText());
    System.out.println("question2.getText() = " + question2.getText());
    System.out.println("question3.getText() = " + question3.getText());

    System.out.println("");
    System.out.println("\u001B[36m*** getAnswer()\u001B[0m");

    System.out.println("question1.getAnswer() = " + question1.getAnswer());
    System.out.println("question2.getAnswer() = " + question2.getAnswer());
    System.out.println("question3.getAnswer() = " + question3.getAnswer());

    System.out.println("");
    System.out.println("\u001B[36m*** setText()\u001B[0m");

    question4.setText("Qui a crée Python ?");
    System.out.println("question4.setText(\"Qui a crée Python ?\") -> " + question4.getText());
    question5.setText("En quelle année a été crée GitHub ?");
    System.out.println(
        "question5.setText(\"En quelle année a été crée GitHub ?\") -> " + question5.getText());

    System.out.println("");
    System.out.println("\u001B[36m*** setAnswer()\u001B[0m");

    question4.setAnswer("guido van rossum");
    System.out.println("question4.setAnswer(\"guido van rossum\") -> " + question4.getAnswer());
    question5.setAnswer("2008");
    System.out.println("question5.setAnswer(\"2008\") -> " + question5.getAnswer());

    System.out.println("");
    System.out.println("\u001B[36m*** display()\u001B[0m");

    question1.display();
    question2.display();
    question3.display();
    question4.display();
    question5.display();

    System.out.println("");
    System.out.println("\u001B[36m*** checkAnswer()\u001B[0m");

    System.out.println("question1.checkAnswer(\"Blanc\") = " + question1.checkAnswer("Blanc"));
    System.out.println("question5.checkAnswer(\"2012\") = " + question1.checkAnswer("2012"));

    System.out.println("");
    System.out.println("\u001B[36m*** ChoiceQuestion()\u001B[0m");

    ChoiceQuestion choiceQuestion1 = new ChoiceQuestion();
    System.out.println("ChoiceQuestion() -> " + choiceQuestion1);
    ChoiceQuestion choiceQuestion2 = new ChoiceQuestion();
    System.out.println("ChoiceQuestion() -> " + choiceQuestion2);
    ChoiceQuestion choiceQuestion3 = new ChoiceQuestion();
    System.out.println("ChoiceQuestion() -> " + choiceQuestion3);

    choiceQuestion1.setText("Que signifie HTML ?");
    choiceQuestion2.setText("Que signifie Google ?");
    choiceQuestion3.setText("Qui a crée Apple ?");

    System.out.println("");
    System.out.println("\u001B[36m*** addChoice()\u001B[0m");

    choiceQuestion1.addChoice("How To Meet Ladies", false);
    System.out.println(
        "choiceQuestion1.addChoice(\"How To Meet Ladies\", false) -> " + choiceQuestion1);
    choiceQuestion1.addChoice("Hyper Text Markup Language", true);
    System.out.println(
        "choiceQuestion1.addChoice(\"Hyper Text Markup Language\", true) -> " + choiceQuestion1);
    choiceQuestion1.addChoice("Huge Thing Making Lava", false);
    System.out.println(
        "choiceQuestion1.addChoice(\"Huge Thing Making Lava\", false) -> " + choiceQuestion1);
    choiceQuestion1.addChoice("Happy Time Making Lemonade", false);
    System.out.println(
        "choiceQuestion1.addChoice(\"Happy Time Making Lemonade\", false) -> " + choiceQuestion1);

    choiceQuestion2.addChoice("Globally Organized Online Group Location Engine", false);
    System.out.println(
        "choiceQuestion2.addChoice(\"Globally Organized Online Group Location Engine\", false) -> "
            + choiceQuestion2);
    choiceQuestion2.addChoice("Global Organization of Oriented Group Language of Earth", false);
    System.out.println(
        "choiceQuestion2.addChoice(\"Global Organization of Oriented Group Language of Earth\","
            + " false) -> "
            + choiceQuestion2);
    choiceQuestion2.addChoice("Tire ses origines du nombre Gogol", true);
    System.out.println(
        "choiceQuestion2.addChoice(\"Tire ses origines du nombre Gogol\", true) -> "
            + choiceQuestion2);

    choiceQuestion3.addChoice("Bill Gates", false);
    System.out.println("choiceQuestion3.addChoice(\"Bill Gates\", false) -> " + choiceQuestion3);
    choiceQuestion3.addChoice("Steve Jobs", true);
    System.out.println("choiceQuestion3.addChoice(\"Steve Jobs\", true) -> " + choiceQuestion3);
    choiceQuestion3.addChoice("Léonard de Vinci", false);
    System.out.println(
        "choiceQuestion3.addChoice(\"Léonard de Vinci\", false) -> " + choiceQuestion3);
    choiceQuestion3.addChoice("Elon Musk", false);
    System.out.println("choiceQuestion3.addChoice(\"Elon Musk\", false) -> " + choiceQuestion3);

    System.out.println("");
    System.out.println("\u001B[36m*** display()\u001B[0m");

    choiceQuestion1.display();
    choiceQuestion2.display();
    choiceQuestion3.display();
  }

  /** Useless constructor */
  public ScenarioQuestion() {}
}
