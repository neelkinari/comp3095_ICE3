package ca.gbc.notificationservice.service;


import ca.gbc.notificationservice.event.OrderPlacedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class NotificationService {
    private final JavaMailSender javaMailSender;
    @KafkaListener(topics = "order-placed")
    private void listen(OrderPlacedEvent orderPlacedEvent){
        log.info("Received message from order-placed topic {}", orderPlacedEvent);
        //send email to customer
        MimeMessagePreparator messagePreparator=mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
            messageHelper.setFrom("comp3095@georgebrown.ca");
            messageHelper.setTo(orderPlacedEvent.getEmail());
            messageHelper.setSubject(String.format("Your Order (%s) was placed successdully",
                    orderPlacedEvent.getOrderNumber()));
            messageHelper.setText(String.format("""
                    
                    Good Day
                    
                    Your order with orderNo %s was successfully placed
                    
                    Thank you for your business
                    Comp3095 Staff Nishita Sachdev
                    """,orderPlacedEvent.getOrderNumber()));
        };
        try {
            javaMailSender.send(messagePreparator);
            log.info("order notification send successfully");
        }catch (MailException e){
            log.error("exeception occured when sending the email", e);
            throw new RuntimeException("exception occured when attempting to send mail",e);
        }


    }

}
