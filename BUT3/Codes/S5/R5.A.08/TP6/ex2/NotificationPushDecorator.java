public class NotificationPushDecorator extends NotificationDecorator {
  public NotificationPushDecorator(Notification notification) {
    super(notification);
  }

  @Override
  public void envoyer(String message) {
    super.envoyer(message);
    System.out.println("Envoi de la notification Push: " + message);
  }
}
