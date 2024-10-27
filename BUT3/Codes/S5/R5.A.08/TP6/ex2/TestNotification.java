public class TestNotification {
  public static void main(String[] args) {
      String message = "Alerte de sécurité : veuillez vérifier votre compte.";

      // Exemple 1 : Email + SMS
      Notification baseEmail = new NotificationEmail();
      Notification emailAndSms = new NotificationSMSDecorator(baseEmail);
      emailAndSms.envoyer(message);

      System.out.println("\n");

      // Exemple 2 : SMS + Notification Push + Slack
      Notification baseSms = new NotificationSMS();
      Notification smsAndPush = new NotificationPushDecorator(baseSms);
      Notification smsPushAndSlack = new SlackNotificationDecorator(smsAndPush);
      smsPushAndSlack.envoyer(message);

      System.out.println("\n");

      // Exemple 3 : Email + WhatsApp + Facebook
      Notification baseEmail2 = new NotificationEmail();
      Notification emailAndWhatsApp = new WhatsAppNotificationDecorator(baseEmail2);
      Notification emailWhatsAppAndFacebook = new FacebookNotificationDecorator(emailAndWhatsApp);
      emailWhatsAppAndFacebook.envoyer(message);
  }
}
