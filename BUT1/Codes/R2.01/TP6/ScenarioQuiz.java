import question.*;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * ScenarioQuiz class, represents a quiz with a list of questions
 */
public class ScenarioQuiz {
	/**
	 * Main method
	 * @param args the arguments
	 */
	public static void main(String[] args) {
		Question question1 = new Question("Quelle est la couleur du cheval blanc d'Henri IV ?", "blanc");
		Question question2 = new Question("Comment s'appelle le chat roux dans Garfield ?", "garfield");
		Question question3 = new Question("Quelle est la capitale du Honduras ?", "tegucigalpa");
		Question question4 = new Question();
		Question question5 = new Question();
		question4.setText("Qui a crée Python ?");
		question5.setText("En quelle année a été crée GitHub ?");
		question4.setAnswer("guido van rossum");
		question5.setAnswer("2008");
		ChoiceQuestion choiceQuestion1 = new ChoiceQuestion();
		ChoiceQuestion choiceQuestion2 = new ChoiceQuestion();
		ChoiceQuestion choiceQuestion3 = new ChoiceQuestion();
		choiceQuestion1.setText("Que signifie HTML ?");
		choiceQuestion1.addChoice("How To Meet Ladies", false);
		choiceQuestion1.addChoice("Hyper Text Markup Language", true);
		choiceQuestion1.addChoice("Huge Thing Making Lava", false);
		choiceQuestion1.addChoice("Happy Time Making Lemonade", false);
		choiceQuestion2.setText("Que signifie Google ?");
		choiceQuestion2.addChoice("Globally Organized Online Group Location Engine", false);
		choiceQuestion2.addChoice("Global Organization of Oriented Group Language of Earth", false);
		choiceQuestion2.addChoice("Tire ses origines du nombre Gogol", true);
		choiceQuestion3.setText("Qui a crée Apple ?");
		choiceQuestion3.addChoice("Bill Gates", false);
		choiceQuestion3.addChoice("Steve Jobs", true);
		choiceQuestion3.addChoice("Léonard de Vinci", false);
		choiceQuestion3.addChoice("Elon Musk", false);

		ArrayList<Question> questions = new ArrayList<Question>();
		questions.add(question1); questions.add(question2); questions.add(question3); questions.add(question4); questions.add(question5); questions.add(choiceQuestion1); questions.add(choiceQuestion2); questions.add(choiceQuestion3);

		Quiz quiz = new Quiz(questions);

		Scanner scanner = new Scanner(System.in);
		
		for (int i = 0; i < quiz.getQNumber(); i++) {
			Question question = quiz.pickAtRandom();
			question.display();
			System.out.print("Votre réponse : ");
			String response = scanner.nextLine();
			boolean correct = question.checkAnswer(response);
			if (correct) {
				System.out.println("\u001B[32mRéponse correcte\u001B[0m");
			} else {
				System.out.println("\u001B[31mRéponse incorrecte\u001B[0m");
			}
			quiz.editScore(correct);
			System.out.println();
		}
		quiz.displayScore();
		scanner.close();
	}

	/**
	 * Useless constructor
	 */
	public ScenarioQuiz() {}
}
