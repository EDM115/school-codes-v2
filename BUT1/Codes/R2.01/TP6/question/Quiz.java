package question;

import java.util.ArrayList;

/** Quiz class, represents a quiz with a list of questions */
public class Quiz {

  /** The list of questions */
  private ArrayList<Question> questionList;

  /** The score of the user, can go negative */
  private int score;

  /** Default constructor, initializes the list of questions to an empty list */
  public Quiz() {
    this.questionList = new ArrayList<Question>();
  }

  /**
   * Constructor, initializes the list of questions to the given list
   *
   * @param list The list of questions
   */
  public Quiz(ArrayList<Question> list) {
    if (list == null) {
      System.err.println("\u001B[31mQuiz(): la liste ne peut pas être nulll\u001B[0m");
      this.questionList = new ArrayList<Question>();
    } else {
      this.questionList = list;
    }
  }

  /**
   * Adds a question to the list of questions
   *
   * @param q The question to add
   */
  public void add(Question q) {
    if (q == null) {
      System.err.println("\u001B[31madd(): la question ne peut pas être null\u001B[0m");
    } else {
      this.questionList.add(q);
    }
  }

  /**
   * Pick a random question from the list of questions
   *
   * @return The random question
   */
  public Question pickAtRandom() {
    int index = (int) (Math.random() * this.questionList.size());
    Question q = this.questionList.get(index);

    return q;
  }

  /**
   * Getter for the question at the given index
   *
   * @param index The index of the question
   * @return The question at the given index
   */
  public String getQuestionText(int index) {
    String text = null;

    if (index < 0 || index >= this.questionList.size()) {
      System.err.println("\u001B[31mgetQuestionText(): l'index est invalide\u001B[0m");
    } else {
      text = this.questionList.get(index).getText();
    }

    return text;
  }

  /**
   * Get the number of questions in the list of questions
   *
   * @return The number of questions
   */
  public int getQNumber() {
    int size = this.questionList.size();

    return size;
  }

  /** Displays the list of questions */
  public void display() {
    for (Question e : this.questionList) {
      e.display();
    }
  }

  /** Displays the score */
  public void displayScore() {
    System.out.println("Score : " + this.score + "/" + getQNumber());
  }

  /**
   * Edits the score
   *
   * @param correct True if the answer was correct, false otherwise
   */
  public void editScore(boolean correct) {
    if (correct) {
      this.score++;
    } else {
      this.score--;
    }
  }
}
