package system.utilities;

import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;

import java.io.IOException;

import org.springframework.stereotype.Component;

import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;

@Component
public class SendEmailBySendGrid {

	public void sendEmailBySendGrid(String newUsername, String newUserEmail) throws IOException {
			
		Email from = new Email(AppConst.defaultCompanyEmail);
		Email to = new Email(newUserEmail);
		String subject = String.format(AppConst.emailSubjectText,newUsername);
		Content content = new Content("text/plain",String.format(AppConst.emailContentText,newUsername));
		Mail mail = new Mail(from,subject,to,content);
		SendGrid sg = new SendGrid(AppConst.SendGredApiKey);
		Request request = new Request();
		
		try {
			request.setMethod(Method.POST);
			request.setEndpoint("mail/send");
			request.setBody(mail.build());
			
			Response response = sg.api(request);
			System.out.println(response.getStatusCode());
			System.out.println(response.getBody());
			System.out.println(response.getHeaders());
			
		}catch (IOException ex){
			throw new IOException(ex.getMessage());
		}
	}
	
}
