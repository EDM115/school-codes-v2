public class SlackNotificationDecorator extends NotificationDecorator {
  public SlackNotificationDecorator(Notification notification) {
    super(notification);
  }

  @Override
  public void envoyer(String message) {
    super.envoyer(message);
    System.out.println("Envoi du message Slack : " + message);
  }
}
