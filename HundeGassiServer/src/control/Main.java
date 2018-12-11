package control;

public class Main {

	public static void main(String[] args) {

		MailUtility.prepareMail();
		MailUtility.sendNotificationEmail("eller.martin29@gmail.com", "Martin Eller", "martin.eller29@gmail.com", "Martin Eller", "Test 03", "Testinhalt");
	}

}
