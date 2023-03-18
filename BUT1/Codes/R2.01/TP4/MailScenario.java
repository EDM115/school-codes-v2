import mail.MailItem;
import mail.MailServer;

/** MailScenario Tests the classes MailItem and MailServer */
public class MailScenario {
  /**
   * Main method
   *
   * @param args the arguments
   */
  public static void main(String[] args) {
    System.out.println("\t\u001B[33mMailScenario :\u001B[0m");

    System.out.println("");
    System.out.println("\t\u001B[33mMailItem :\u001B[0m");

    System.out.println("");
    System.out.println("\u001B[36m*** Création des mails\u001B[0m");

    MailItem mail1 = new MailItem("Pallan", "Pathan", "Ponjour");
    System.out.println("\"Pallan\", \"Pathan\", \"Ponjour\" -> print() =");
    mail1.print();
    MailItem mail2 = new MailItem("Pathan", "Pallan", "Pienvenue");
    System.out.println("\"Pathan\", \"Pallan\", \"Pienvenue\" -> print() =");
    mail2.print();
    MailItem mail3 = new MailItem("Pathan", "Glupandre", "Perle de lait");
    System.out.println("\"Pathan\", \"Glupandre\", \"Perle de lait\" -> print() =");
    mail3.print();

    System.out.println("");
    System.out.println("\t\u001B[33mMailServer :\u001B[0m");

    System.out.println("");
    System.out.println("\u001B[36m*** MailServer()\u001B[0m");
    MailServer server = new MailServer();
    System.out.println("MailServer() = " + server);

    System.out.println("");
    System.out.println("\u001B[36m*** post()\u001B[0m");
    server.post(mail1);
    System.out.println("post(mail1) = " + server.toString());
    server.post(mail2);
    System.out.println("post(mail2) = " + server.toString());
    server.post(mail3);
    System.out.println("post(mail3) = " + server.toString());

    System.out.println("");
    System.out.println("\u001B[36m*** howManyMailItems()\u001B[0m");
    System.out.println("howManyMailItems(\"Pallan\") = " + server.howManyMailItems("Pallan"));
    System.out.println("howManyMailItems(\"Pathan\") = " + server.howManyMailItems("Pathan"));
    System.out.println("howManyMailItems(\"Glupandre\") = " + server.howManyMailItems("Glupandre"));
    System.out.println("howManyMailItems(\"Pathieu\") = " + server.howManyMailItems("Pathieu"));

    System.out.println("");
    System.out.println("\u001B[36m*** getNextMailItem()\u001B[0m");
    System.out.println("getNextMailItem(\"Pallan\") = " + server.getNextMailItem("Pallan"));
    System.out.println("getNextMailItem(\"Pathan\") = " + server.getNextMailItem("Pathan"));
    System.out.println("getNextMailItem(\"Glupandre\") = " + server.getNextMailItem("Glupandre"));
    System.out.println("getNextMailItem(\"Pathieu\") = " + server.getNextMailItem("Pathieu"));
  }

  /** Constructor, useless */
  public MailScenario() {}
}
