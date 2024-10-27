public class NotificationEmail implements Notification {
  @Override
  public void envoyer(String message) {
    System.out.println("Envoi de l'email : " + message);
  }
}
