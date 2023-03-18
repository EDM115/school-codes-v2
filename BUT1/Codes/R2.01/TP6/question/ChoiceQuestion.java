package question;

import java.util.ArrayList;

/**
 * ChoiceQuestion class (extends Question), represents a question with a text, an answer and a list
 * of choices
 */
public class ChoiceQuestion extends Question {

  /** The list of choices */
  private ArrayList<String> choices;

  /**
   * Default constructor, initializes the question, the answer and the list of choices to empty
   * strings
   */
  public ChoiceQuestion() {
    super();
    this.choices = new ArrayList<String>();
  }

  /**
   * Adds a choice to the list of choices
   *
   * @param choice The answer to add
   * @param correct True if the choice is the correct answer, false otherwise
   */
  public void addChoice(String choice, boolean correct) {
    this.choices.add(choice);

    if (correct) {
      String choiceNumber = "" + this.choices.size();
      this.setAnswer(choiceNumber);
    }
  }

  /** Displays the question and the list of choices */
  public void display() {
    super.display();

    for (int i = 0; i < this.choices.size(); i++) {
      int choiceNumber = i + 1;
      System.out.println(choiceNumber + " : " + this.choices.get(i));
    }
  }
}
