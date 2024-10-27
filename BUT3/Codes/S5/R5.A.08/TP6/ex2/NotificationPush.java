public class NotificationPush implements Notification {
  @Override
  public void envoyer(String message) {
    System.out.println("Envoi de la notification Push : " + message);
  }
}
