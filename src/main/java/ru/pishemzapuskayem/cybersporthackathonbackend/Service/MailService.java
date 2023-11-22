package ru.pishemzapuskayem.cybersporthackathonbackend.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import ru.pishemzapuskayem.cybersporthackathonbackend.Model.Account;
import ru.pishemzapuskayem.cybersporthackathonbackend.Model.Team;
import ru.pishemzapuskayem.cybersporthackathonbackend.Model.Tournament;

@Service
@RequiredArgsConstructor
public class MailService {


    @Value("${mail.username}")
    private String mailFrom;

    private final JavaMailSender javaMailSender;

    public void joinRequestEmail(Account account, Tournament tournament, Team team) {
        MimeMessage memeMailMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(memeMailMessage, "UTF-8");

        try {
            helper.setFrom(mailFrom);
            helper.setTo(account.getEmail());
            helper.setSubject("Запрос команды на участие турнира : " + tournament.getName());
            helper.setText(buildJoinRequestText(team, tournament));
            javaMailSender.send(memeMailMessage);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    public void captainEmail(Account account, Tournament tournament, Boolean isApprove) {
        MimeMessage memeMailMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(memeMailMessage, "UTF-8");

        try {
            helper.setFrom(mailFrom);
            helper.setTo(account.getEmail());
            helper.setSubject("Решение участия на турнире " + tournament.getName());
            if (isApprove) helper.setText(buildApproveText(tournament));
            else helper.setText(buildRejectText(tournament));
            javaMailSender.send(memeMailMessage);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    private String buildJoinRequestText(Team team, Tournament tournament) {
        return "<div>" +
                "Команда:" + team.getName() +
                " подала заявку на участие в турнире "
                + tournament.getName() + " " +
                "</div>";
    }


    private String buildApproveText(Tournament tournament) {
        return "<div>" +
                "Ваша заявка на турнир " + tournament.getName() + " была принята"+
                "</div>";
    }

    private String buildRejectText(Tournament tournament) {
        return "<div>" +
                "Ваша заявка на турнир " + tournament.getName() + " была отклонена"+
                "</div>";
    }

}
