public class NotificationSMS implements Notification {
  @Override
  public void envoyer(String message) {
    System.out.println("Envoi du SMS : " + message);
  }
}
