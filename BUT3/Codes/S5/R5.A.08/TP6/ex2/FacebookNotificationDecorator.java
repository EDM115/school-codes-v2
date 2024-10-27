public class FacebookNotificationDecorator extends NotificationDecorator {
  public FacebookNotificationDecorator(Notification notification) {
    super(notification);
  }

  @Override
  public void envoyer(String message) {
    super.envoyer(message);
    System.out.println("Publication sur Facebook : " + message);
  }
}
