public class NotificationSMSDecorator extends NotificationDecorator {
  public NotificationSMSDecorator(Notification notification) {
    super(notification);
  }

  @Override
  public void envoyer(String message) {
    super.envoyer(message);
    System.out.println("Envoi du SMS : " + message);
  }
}
