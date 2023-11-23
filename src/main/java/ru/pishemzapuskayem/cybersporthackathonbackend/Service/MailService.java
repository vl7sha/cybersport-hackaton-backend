package ru.pishemzapuskayem.cybersporthackathonbackend.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import ru.pishemzapuskayem.cybersporthackathonbackend.Model.*;
import ru.pishemzapuskayem.cybersporthackathonbackend.Model.Account.Account;
import ru.pishemzapuskayem.cybersporthackathonbackend.Model.Account.Judge;
import ru.pishemzapuskayem.cybersporthackathonbackend.Model.Account.Player;
import ru.pishemzapuskayem.cybersporthackathonbackend.Model.Tournament.Tournament;

@Service
@RequiredArgsConstructor
public class MailService {

    private final JavaMailSender javaMailSender;

    @Value("${mail.username}")
    private String mailFrom;

    public void sendMessage(Account to, String subject, String msg) {
        MimeMessage mimeMailMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMailMessage, "UTF-8");

        try {
            helper.setFrom(mailFrom);
            helper.setTo(to.getEmail());
            helper.setSubject(subject);
            helper.setText(msg);
            javaMailSender.send(mimeMailMessage);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    public void sendTournamentRequestNotification(Judge to, Tournament tournament, Team team) {
        String subject = "Запрос команды на участие турнира : " + tournament.getName();
        String msg = buildJoinRequestMsg(team, tournament);
        sendMessage(to, subject, msg);
    }

    public void sendRequestApprovedNotification(Player to, Tournament tournament) {
        String subject = "Решение участия на турнире " + tournament.getName();
        String msg = buildApproveMsg(tournament);
        sendMessage(to, subject, msg);
    }

    public void sendRequestRejectedNotification(Player to, Tournament tournament) {
        String subject = "Решение участия на турнире " + tournament.getName();
        String msg = buildRejectMsg(tournament);
        sendMessage(to, subject, msg);
    }

    //todo стили для html
    private String buildJoinRequestMsg(Team team, Tournament tournament) {
        return "<div>" +
                "Команда:" + team.getName() +
                " подала заявку на участие в турнире "
                + tournament.getName() + " " +
                "</div>";
    }

    //todo стили для html
    private String buildApproveMsg(Tournament tournament) {
        return "<div>" +
                "Ваша заявка на турнир " + tournament.getName() + " была принята"+
                "</div>";
    }

    private String buildRejectMsg(Tournament tournament) {
        return "<div>" +
                "Ваша заявка на турнир " + tournament.getName() + " была отклонена"+
                "</div>";
    }

}
