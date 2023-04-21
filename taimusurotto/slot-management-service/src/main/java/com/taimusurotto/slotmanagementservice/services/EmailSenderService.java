package com.taimusurotto.slotmanagementservice.services;

import com.taimusurotto.slotmanagementservice.domain.Admin;
import com.taimusurotto.slotmanagementservice.domain.Interviewer;
import com.taimusurotto.slotmanagementservice.domain.MasterTable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.*;
import java.time.LocalDate;
import java.time.LocalTime;
@Service
public class EmailSenderService {
    @Autowired
    private JavaMailSender mailSender;

    public void sendEmail(String toMail,
                          String subject,
                          String body) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("taimusurotto@gmail.com");
        message.setTo(toMail);
        message.setText(body);
        message.setSubject(subject);

        mailSender.send(message);
        System.out.println("Mail Sent successfully ....");
    }

    public void sendEmailFromTemplate1(MasterTable openSlot,
                                      String subject) throws MessagingException, IOException {
        MimeMessage message = mailSender.createMimeMessage();

        message.setFrom(new InternetAddress("taimusurotto@gmail.com"));
        message.setRecipients(MimeMessage.RecipientType.TO, openSlot.getInterviewee().getEmail());
        message.setSubject(subject);
        String filePath = new File("").getAbsolutePath();
        filePath=filePath.concat("//slot-management-service//src//main//resources//template1.html");
        String htmlTemplate = htmlToString(filePath);
        htmlTemplate = htmlTemplate.replace("${name}", openSlot.getInterviewee().getFirstName());
        htmlTemplate = htmlTemplate.replace("${date}", openSlot.getSlot_id().getSlot_date().toString());
        htmlTemplate = htmlTemplate.replace("${fromTime}", openSlot.getSlot_id().getSlot_start_Time().toString());
        htmlTemplate = htmlTemplate.replace("${toTime}", openSlot.getSlot_id().getSlot_end_Time().toString());
        htmlTemplate = htmlTemplate.replace("${meeting link}",openSlot.getLink().toString());
        htmlTemplate = htmlTemplate.replace("${password}",openSlot.getPassword().toString());


        // Set the email's content to be the HTML template
        message.setContent(htmlTemplate, "text/html; charset=utf-8");

        mailSender.send(message);
    }
    public void sendEmailFromTemplate2(MasterTable openSlot,
                                      String subject) throws MessagingException, IOException {
        MimeMessage message = mailSender.createMimeMessage();

        message.setFrom(new InternetAddress("taimusurotto@gmail.com"));
        message.setRecipients(MimeMessage.RecipientType.TO, openSlot.getInterviewee().getEmail());
        message.setSubject(subject);
        String filePath = new File("").getAbsolutePath();
        filePath=filePath.concat("//slot-management-service//src//main//resources//template2.html");
        String htmlTemplate = htmlToString(filePath);
        htmlTemplate = htmlTemplate.replace("${name}", openSlot.getInterviewee().getFirstName());
        htmlTemplate = htmlTemplate.replace("${date}", openSlot.getSlot_id().getSlot_date().toString());
        htmlTemplate = htmlTemplate.replace("${fromTime}", openSlot.getSlot_id().getSlot_start_Time().toString());
        htmlTemplate = htmlTemplate.replace("${toTime}", openSlot.getSlot_id().getSlot_end_Time().toString());


        // Set the email's content to be the HTML template
        message.setContent(htmlTemplate, "text/html; charset=utf-8");

        mailSender.send(message);
    }
    public void sendEmailFromTemplate3(MasterTable openSlot) throws MessagingException, IOException {
        MimeMessage message = mailSender.createMimeMessage();

        message.setFrom(new InternetAddress("taimusurotto@gmail.com"));
        message.setRecipients(MimeMessage.RecipientType.TO, openSlot.getInterviewer().getEmail());
        String filePath = new File("").getAbsolutePath();
        filePath=filePath.concat("//slot-management-service//src//main//resources//template3.html");
//        filePath=filePath.concat("../src//main//resources//template3.html");
        String htmlTemplate = htmlToString(filePath);
        htmlTemplate = htmlTemplate.replace("${name}", openSlot.getInterviewer().getFirstName());
        htmlTemplate = htmlTemplate.replace("${date}", openSlot.getSlot_id().getSlot_date().toString());
        htmlTemplate = htmlTemplate.replace("${fromTime}", openSlot.getSlot_id().getSlot_start_Time().toString());
        htmlTemplate = htmlTemplate.replace("${toTime}", openSlot.getSlot_id().getSlot_end_Time().toString());
        htmlTemplate = htmlTemplate.replace("${meeting link}",openSlot.getLink().toString());
        htmlTemplate = htmlTemplate.replace("${password}",openSlot.getPassword().toString());


        // Set the email's content to be the HTML template
        message.setContent(htmlTemplate, "text/html; charset=utf-8");

        mailSender.send(message);
    }


    public String htmlToString(String filePath) throws FileNotFoundException {
        StringBuilder html = new StringBuilder();

        // Reading html file on local directory
        FileReader fr = new FileReader(filePath);
        String result="";

        // Try block to check exceptions
        try {

            // Initialization of the buffered Reader to get
            // the String append to the String Builder
            BufferedReader br = new BufferedReader(fr);

            String val;

            // Reading the String till we get the null
            // string and appending to the string
            while ((val = br.readLine()) != null) {
                html.append(val);
            }

            // AtLast converting into the string
            result = html.toString();
            System.out.println(result);
            return result;

            // Closing the file after all the completion of
            // Extracting
        }

        // Catch block to handle exceptions
        catch (Exception ex) {

            /* Exception of not finding the location and
            string reading termination the function
            br.close(); */
            System.out.println(ex.getMessage());
        }
        return  result;

    }

    public void sendEmailFromTemplate4(Interviewer interviewer) throws MessagingException, IOException {
        MimeMessage message = mailSender.createMimeMessage();

        message.setFrom(new InternetAddress("taimusurotto@gmail.com"));
        message.setRecipients(MimeMessage.RecipientType.TO, interviewer.getEmail());
        message.setSubject("User Created");
        String filePath = new File("").getAbsolutePath();
        filePath=filePath.concat("//slot-management-service//src//main//resources//template4.html");
        String htmlTemplate = htmlToString(filePath);
        htmlTemplate = htmlTemplate.replace("${name}", interviewer.getFirstName());
        htmlTemplate= htmlTemplate.replace("${userName}",interviewer.getEmail());

        htmlTemplate = htmlTemplate.replace("${password}",interviewer.getPassword());


        // Set the email's content to be the HTML template
        message.setContent(htmlTemplate, "text/html; charset=utf-8");

        mailSender.send(message);
    }

    public void sendEmailToNewAdmin(Admin admin) throws MessagingException, IOException {
        MimeMessage message = mailSender.createMimeMessage();

        message.setFrom(new InternetAddress("taimusurotto@gmail.com"));
        message.setRecipients(MimeMessage.RecipientType.TO, admin.getEmail());
        message.setSubject("User Created");
        String filePath = new File("").getAbsolutePath();
        filePath=filePath.concat("//slot-management-service//src//main//resources//template4.html");
        String htmlTemplate = htmlToString(filePath);
        htmlTemplate = htmlTemplate.replace("${name}", admin.getUserName());
        htmlTemplate= htmlTemplate.replace("${userName}",admin.getEmail());

        htmlTemplate = htmlTemplate.replace("${password}",admin.getPassword());


        // Set the email's content to be the HTML template
        message.setContent(htmlTemplate, "text/html; charset=utf-8");

        mailSender.send(message);
    }

}
