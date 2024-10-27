public class WhatsAppNotificationDecorator extends NotificationDecorator {
  public WhatsAppNotificationDecorator(Notification notification) {
    super(notification);
  }

  @Override
  public void envoyer(String message) {
    super.envoyer(message);
    System.out.println("Envoi du message via WhatsApp : " + message);
  }
}
