package ru.pishemzapuskayem.cybersporthackathonbackend.Export;


import org.jxls.common.Context;
import org.jxls.util.JxlsHelper;
import org.springframework.stereotype.Component;

import ru.pishemzapuskayem.cybersporthackathonbackend.Model.Account.Judge;
import ru.pishemzapuskayem.cybersporthackathonbackend.Model.Tournament.Tournament;

import java.io.*;
import java.util.ArrayList;
import java.util.List;


@Component
public class ExportService {

//    @Value("{path}")
//    private final String path;

    String[] nameSheets = {
            "Титульный лист",
            "Судьи",
            "Участники",
            "ПД",
            "Сетка",
            "Итоги"
    };

    public void export(Tournament tournament) throws FileNotFoundException {
        try (InputStream is = new FileInputStream("Шаблон_Протокола.xlsx")) {
            // Путь куда будет сохранён заполненный шаблон
            try (OutputStream os = new FileOutputStream("output.xlsx")) {

                Context context = new Context();

                titleSheet(context, tournament);
                secondSheet(context, tournament.getChiefJudge(), tournament.getChiefSecretary(),
                        tournament.getJudges(), tournament.getSecretaries());

                // Генерация отчета
                JxlsHelper.getInstance().processTemplate(is, os, context);
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void secondSheet(Context context, Judge chiefJudge, Judge chiefSecretary,
                             List<Judge> judges, List<Judge> secretaries) {

        String[] columns = {
                "№", "ID", "Ф.И.О.", "Пол", "Дата рожд.", "Субъект РФ", "Команда", "Разряд", "Количество побед", "Занятое место", "Контакты", "ГТО"
        };
        List<Object[]> newJudges = new ArrayList<>();

        if (chiefJudge != null) {
            String name = chiefJudge.getMiddleName() + " " +
                    chiefJudge.getFirstName() + " " + chiefJudge.getMiddleName();
            newJudges.add(
                    new Object[]
                            {
                                    1,
                                    name,
                                    "Главный судья соревнований",
                                    chiefJudge.getCertificationLevel(),
                                    chiefJudge.getSubjectOfRF(),
                                    chiefJudge.getBirthDate(),
                                    chiefJudge.getResidence(),
                                    chiefJudge.getEmail()
                            }
            );
        }

        if (chiefSecretary != null) {
            int index = chiefJudge == null ? 1 : 2;
            String name = chiefSecretary.getMiddleName() + " " +
                    chiefSecretary.getFirstName() + " " + chiefSecretary.getMiddleName();
            newJudges.add(
                    new Object[]
                            {
                                    index,
                                    name,
                                    "Главный cекретарь соревнований",
                                    chiefSecretary.getCertificationLevel(),
                                    chiefSecretary.getSubjectOfRF(),
                                    chiefSecretary.getBirthDate(),
                                    chiefSecretary.getResidence(),
                                    chiefSecretary.getEmail()
                            }
            );
        }

        if (!judges.isEmpty()) {
            int index = chiefJudge == null && chiefSecretary == null ? 1 : 3;
            for (int i = 0; i < judges.size() - 1; i++) {
                Judge judge = judges.get(i);
                String name = judge.getMiddleName() + " " +
                        judge.getFirstName() + " " + judge.getMiddleName();
                newJudges.add(
                        new Object[]{
                                index,
                                name,
                                "Матчевый судья",
                                judge.getCertificationLevel(),
                                judge.getSubjectOfRF(),
                                judge.getBirthDate(),
                                judge.getResidence(),
                                judge.getEmail()
                        }
                );
            }
        }

        if (!secretaries.isEmpty()) {
            int index = chiefJudge == null && chiefSecretary == null && judges.isEmpty() ? 1 : judges.size();

            for (int i = 0; i < secretaries.size() - 1; i++) {
                Judge secretary = secretaries.get(i);
                String name = secretary.getMiddleName() + " " +
                        secretary.getFirstName() + " " + secretary.getMiddleName();
                newJudges.add(
                        new Object[]{
                                index,
                                name,
                                "Секретарь соревнований",
                                secretary.getCertificationLevel(),
                                secretary.getSubjectOfRF(),
                                secretary.getBirthDate(),
                                secretary.getResidence(),
                                secretary.getEmail()
                        }
                );
            }
            context.putVar("cell", columns);
            context.putVar("data", newJudges);
        }


    }

    private void titleSheet(Context context, Tournament tournament) {
        String nameChiefJudge = null;
        if (tournament.getChiefJudge() != null) {
            nameChiefJudge = tournament.getChiefJudge().getLastName() + " " +
                    tournament.getChiefJudge().getFirstName() + " " +
                    tournament.getChiefJudge().getMiddleName();
        }

        String nameChiefSecretary = null;
        if (tournament.getChiefSecretary() != null) {
            nameChiefJudge = tournament.getChiefSecretary().getLastName() + " " +
                    tournament.getChiefSecretary().getFirstName() + " " +
                    tournament.getChiefSecretary().getMiddleName();
        }

        // Создание контекста и добавление данных
        context.putVar("competitionName", tournament.getName());
        context.putVar("location", tournament.getLocation());
        context.putVar("organizer", tournament.getOrganizer());
        context.putVar("opening", tournament.getRegistrationDate().toString());
        context.putVar("start", tournament.getStartDate().toString());
        context.putVar("end", tournament.getEndDate().toString());
        context.putVar("closing", tournament.getClosingDate().toString());
        context.putVar("discipline", tournament.getDiscipline());
        context.putVar("chiefJudge", nameChiefJudge);
        context.putVar("chiefSecretary", nameChiefSecretary);
        context.putVar("dateCreated", "Дата составления: " + tournament.getReportDate());
    }

}
