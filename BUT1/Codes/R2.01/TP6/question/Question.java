package question;

/** Question class, represents a question with a text and an answer */
public class Question {

  /** The question */
  private String text;

  /** The answer */
  private String answer;

  /** Default constructor, initializes the question and the answer to empty strings */
  public Question() {
    this.text = "";
    this.answer = "";
  }

  /**
   * Constructor, initializes the question and the answer to the given values
   *
   * @param text The question
   * @param answer The answer
   */
  public Question(String text, String answer) {
    if (text == null || answer == null) {
      System.err.println("\u001B[31mQuestion(): les paramètres ne peuvent pas être null\u001B[0m");
      this.text = "";
      this.answer = "";
    } else {
      this.text = text;
      this.answer = answer;
    }
  }

  /**
   * Getter for the question
   *
   * @return The question
   */
  public String getText() {
    String text = this.text;

    return text;
  }

  /**
   * Getter for the answer
   *
   * @return The answer
   */
  public String getAnswer() {
    String answer = this.answer;

    return answer;
  }

  /**
   * Setter for the question
   *
   * @param text The question
   */
  public void setText(String text) {
    if (text == null) {
      System.err.println("\u001B[31msetText(): le paramètre ne peut pas être null\u001B[0m");
    } else {
      this.text = text;
    }
  }

  /**
   * Setter for the answer
   *
   * @param answer The answer
   */
  public void setAnswer(String answer) {
    if (answer == null) {
      System.err.println("\u001B[31msetAnswer(): le paramètre ne peut pas être null\u001B[0m");
    } else {
      this.answer = answer;
    }
  }

  /** Displays the question */
  public void display() {
    System.out.println(getText());
  }

  /**
   * Checks if the given response is correct
   *
   * @param response The response to check
   * @return true if the response is correct, false otherwise
   */
  public boolean checkAnswer(String response) {
    boolean isCorrect = false;

    if (response == null) {
      System.err.println("\u001B[31mcheckAnswer(): le paramètre ne peut pas être null\u001B[0m");
    } else {
      isCorrect = getAnswer().equalsIgnoreCase(response.trim());
    }

    return isCorrect;
  }
}
