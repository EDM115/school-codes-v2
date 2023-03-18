import java.util.ArrayList;
import mail.*;

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
    MailItem mail4 = new MailItem("Pathan", "Pathieu", "Pas un spam");
    System.out.println("\"Pathan\", \"Pathieu\", \"Pas un spam\" -> print() =");
    mail4.print();

    System.out.println("");
    System.out.println("\t\u001B[33mAntiSpam :\u001B[0m");

    System.out.println("");
    System.out.println("\u001B[36m*** AntiSpam()\u001B[0m");
    ArrayList<String> filters = new ArrayList<String>();
    AntiSpam spam = new AntiSpam(filters);
    System.out.println("AntiSpam() = " + spam);

    System.out.println("");
    System.out.println("\u001B[36m*** add()\u001B[0m");
    spam.add("Ponjour");
    System.out.println("add(\"Ponjour\") = " + spam.toString());
    spam.add("Pienvenue");
    System.out.println("add(\"Pienvenue\") = " + spam.toString());

    System.out.println("");
    System.out.println("\u001B[36m*** scan()\u001B[0m");
    System.out.println("scan(\"Ponjour\") = " + spam.scan("Ponjour"));
    System.out.println("scan(\"Pienvenue\") = " + spam.scan("Pienvenue"));
    System.out.println("scan(\"Perle de lait\") = " + spam.scan("Perle de lait"));
    System.out.println("scan(\"Pas un spam\") = " + spam.scan("Pas un spam"));

    System.out.println("");
    System.out.println("\t\u001B[33mMailServer :\u001B[0m");

    System.out.println("");
    System.out.println("\u001B[36m*** MailServer()\u001B[0m");
    MailServer server = new MailServer(filters);
    System.out.println("MailServer(" + spam.toString() + ") = " + server);

    System.out.println("");
    System.out.println("\u001B[36m*** post()\u001B[0m");
    server.post(mail1);
    System.out.println("post(mail1) = " + server.toString());
    server.post(mail2);
    System.out.println("post(mail2) = " + server.toString());
    server.post(mail3);
    System.out.println("post(mail3) = " + server.toString());
    server.post(mail4);
    System.out.println("post(mail4) = " + server.toString());

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

    System.out.println("");
    System.out.println("\t\u001B[33mMailClient :\u001B[0m");

    System.out.println("");
    System.out.println("\u001B[36m*** MailClient()\u001B[0m");
    MailClient client = new MailClient(server, "Pallan");
    System.out.println("MailClient(" + server.toString() + ", Pallan) = " + client);

    System.out.println("");
    System.out.println("\u001B[36m*** sendMailItem()\u001B[0m");
    client.sendMailItem("Pathan", "Pello");
    System.out.println("sendMailItem(\"Pallan\", \"Pello\") = " + server.toString());
    client.sendMailItem("Pathan", "Pienvenue");
    System.out.println("sendMailItem(\"Pathieu\", \"Pienvenue\") = " + server.toString());

    server.post(mail3);
    server.post(mail4);
    MailItem mail5 = new MailItem("Test", "Pallan", "Test1");
    server.post(mail5);
    MailItem mail6 = new MailItem("Test", "Pallan", "Test2");
    server.post(mail6);
    MailItem mail7 = new MailItem("Test", "Pallan", "Test3");
    server.post(mail7);

    System.out.println("");
    System.out.println("\u001B[36m*** getNextMailItem()\u001B[0m");
    MailItem item1 = client.getNextMailItem();
    System.out.println(item1);
    System.out.println("getNextMailItem() = " + item1.toString());
    MailItem item2 = client.getNextMailItem();
    System.out.println("getNextMailItem() = " + item2.toString());

    System.out.println("");
    System.out.println("\u001B[36m*** printNextMailItem()\u001B[0m");
    System.out.println("printNextMailItem() =");
    client.printNextMailItem();
    System.out.println("printNextMailItem() =");
    client.printNextMailItem();
    System.out.println("printNextMailItem() =");
    client.printNextMailItem();
    System.out.println("printNextMailItem() =");
    client.printNextMailItem();
    System.out.println("printNextMailItem() =");
    client.printNextMailItem();
  }

  /** Constructor, useless */
  public MailScenario() {}
}
