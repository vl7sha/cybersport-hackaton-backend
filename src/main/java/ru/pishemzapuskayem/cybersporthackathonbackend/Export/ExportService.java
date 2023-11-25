package ru.pishemzapuskayem.cybersporthackathonbackend.Export;


import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.pishemzapuskayem.cybersporthackathonbackend.Model.Account.Account;
import ru.pishemzapuskayem.cybersporthackathonbackend.Model.Account.Judge;
import ru.pishemzapuskayem.cybersporthackathonbackend.Model.Team;
import ru.pishemzapuskayem.cybersporthackathonbackend.Model.Tournament.Tournament;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Date;
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

    public void export(Tournament tournament) throws IOException {
        Workbook workbook = new HSSFWorkbook();
        Sheet sheetOne = workbook.createSheet(nameSheets[0]);
        Sheet sheetSecond = workbook.createSheet(nameSheets[1]);
        Sheet sheetThree = workbook.createSheet(nameSheets[2]);
        Sheet sheetFour = workbook.createSheet(nameSheets[3]);
        Sheet sheetFive = workbook.createSheet(nameSheets[4]);
        Sheet sheetSix = workbook.createSheet(nameSheets[5]);

//        one(sheetOne, tournament);
//        two(sheetSecond, tournament.getChiefJudge(), tournament.getChiefSecretary(),
//                tournament.getJudges(), tournament.getSecretaries());
////        three(sheetThree, tournament.getTeams());
        four(sheetFour, tournament.getTeams());
//        sex(sheetSix, tournament);


        try (FileOutputStream outputStream = new FileOutputStream("Judges.xlsx")) {
//            one(sheetOne, tournament);
//            workbook.write(outputStream);
//            two(sheetSecond, tournament.getChiefJudge(), tournament.getChiefSecretary(),
//                    tournament.getJudges(), tournament.getSecretaries());
//            workbook.write(outputStream);
//            three(sheetThree, tournament.getTeams());
//            workbook.write(outputStream);
//            four(sheetFour, tournament.getTeams());
//            workbook.write(outputStream);
//            sex(sheetSix, tournament);
            workbook.write(outputStream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        workbook.close();
    }

//    private void sex(Sheet sheet, Tournament tournament) {
//        Row zeroRow = sheet.createRow(0);
//        Row firstRow = sheet.createRow(1);
//        Row fiveRow = sheet.createRow(5);
//
//        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 9)); // Объединяем ячейки для заголовка
//        createCell(zeroRow, 0, "Чемпионат города Самара (боевая арена) по компьютерному спорту");
//
//
//        sheet.addMergedRegion(new CellRangeAddress(1, 1, 0, 9)); // Объединяем ячейки для заголовка
//        createCell(zeroRow, 1, "Результаты соревнований");
//
//
//        createCell(zeroRow, 2, "1 место");
//        createCell(zeroRow, 3, "2 место");
//        createCell(zeroRow, 4, "3 место");
//        createCell(zeroRow, 5, "4 место");
//
//        sheet.addMergedRegion(new CellRangeAddress(6, 6, 0, 4));
//        createCell(zeroRow, 6, "Главный судья соревнований");
//
//        sheet.addMergedRegion(new CellRangeAddress(7, 7, 0, 4));
//
//        createCell(zeroRow, 8, tournament.getChiefJudge().getLastName()
//                + tournament.getChiefJudge().getFirstName()
//                + tournament.getChiefJudge().getMiddleName());
//        sheet.addMergedRegion(new CellRangeAddress(8, 8, 0, 4));
//
//        createCell(zeroRow, 10, "___________________________ (подпись)");
//        sheet.addMergedRegion(new CellRangeAddress(10, 10, 0, 4));
//
//
//        sheet.addMergedRegion(new CellRangeAddress(12, 12, 0, 4));
//        createCell(zeroRow, 12, "Дата составления: " + tournament.getReportDate());
//
//
//        sheet.addMergedRegion(new CellRangeAddress(6, 6, 5, 8));
//        createCell(firstRow, 6, "Протокол составил главный секретарь:");
//        sheet.addMergedRegion(new CellRangeAddress(7, 7, 5, 8));
//
//        sheet.addMergedRegion(new CellRangeAddress(8, 8, 5, 8));
//
//        createCell(firstRow, 8, tournament.getChiefSecretary().getLastName()
//                + tournament.getChiefSecretary().getFirstName()
//                + tournament.getChiefSecretary().getMiddleName());
//
//        sheet.addMergedRegion(new CellRangeAddress(10, 10, 5, 8));
//        createCell(firstRow, 10, "___________________________ (подпись)");
//
//        sheet.addMergedRegion(new CellRangeAddress(12, 12, 5, 8));
//        createCell(firstRow, 12, "М.П.");
//    }


    private void four(Sheet sheet, List<Team> teams) {

        Row titleRow = sheet.createRow(0);
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 6)); // Объединяем ячейки для заголовка
        titleRow.createCell(0).setCellValue("Согласие на обработку ПД");


        Row titleSecondRow = sheet.createRow(0);
        sheet.addMergedRegion(new CellRangeAddress(1, 1, 0, 6)); // Объединяем ячейки для заголовка
        titleSecondRow.createCell(1).setCellValue( "Настоящим я даю согласие Общероссийской общественной организации «Федерации компьютерного спорта России» (далее – «Оператор»), расположенной по адресу: 121357, ул. Верейская, д. 29, стр. 151, пом. 2., комн. 3, осуществлять с использованием средств автоматизации и/или без таковых обработку всех моих персональных данных*, указанных мной в настоящем протоколе, включая сбор, запись, систематизацию, накопление, хранение, уточнение (обновление, изменение), извлечение, использование, передачу, обезличивание, блокирование, удаление, уничтожение в целях проведения соревнований по компьютерному спорту, организуемых Оператором.\n" +
                "\n" +
                "Я подтверждаю, что предоставленные мною персональные данные являются полными, актуальными и достоверными, и согласен(-на) с тем, что настоящее согласие является конкретным, информированным и сознательным. Дополнительно подтверждаю, что являюсь лицом старше 18 лет и на момент дачи согласия являюсь дееспособным.\n" +
                "\n" +
                "Согласие на обработку персональных данных в соответствии с указанными выше условиями предоставляется сроком на 50 (пятьдесят) лет. Я уведомлен(-а) о том, что данное согласие может быть мной отозвано посредством направления Оператору письменного заявления почтовым отправлением с описью вложения по адресу Оператора, либо вручения лично под роспись уполномоченному представителю Оператора по адресу: 121357, ул. Верейская, д. 29, стр. 151, пом. 2., комн. 3. \n" +
                "Подтверждаю знание антидопинговых правил, обязуюсь соблюдать антидопинговое законодательство.\n" +
                "\n" +
                "* В рамках настоящего Согласия под персональными данными понимается персональная информация, которую пользователь предоставляет о себе при регистрации на соревнование, организуемое Оператором: фамилия, имя, отчество, никнейм, регион проживания, гражданство, адрес электронной почты, номер телефона, дата рождения, УИН ГТО.\t\t\t\t\t\t");


        // Создаем заголовки колонок
        String[] headers = {"№", "Ф.И.О", "Дата рожд",
                "Субъект РФ", "Команда", "Контакты", "Согласие"};
        Row headerRow = sheet.createRow(2);
        for (int i = 0; i < headers.length; i++) {
            headerRow.createCell(i).setCellValue(headers[i]);
        }
    }

//    private void three(Sheet sheet, List<Team> teams) {
//
//        Row titleRow = sheet.createRow(0);
//        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 11)); // Объединяем ячейки для заголовка
//        createCell(titleRow, 0, "Чемпионат города Самара (боевая арена) по компьютерному спорту");
//
//
//        Row titleSecondRow = sheet.createRow(0);
//        sheet.addMergedRegion(new CellRangeAddress(1, 1, 0, 11)); // Объединяем ячейки для заголовка
//        createCell(titleSecondRow, 1, "Участники соревнования");
//
//
//        // Создаем заголовки колонок
//        String[] headers = {"№", "ID", "Ф.И.О", "Пол", "Дата рожд",
//                "Субъект РФ", "Команда", "Разряд", "Количество побед", "Занятое место", "Контакты", "ГТО"};
//        Row headerRow = sheet.createRow(2);
//        for (int i = 0; i < headers.length; i++) {
//            createCell(headerRow, i, headers[i]);
//        }
//    }

//    private void two(Sheet sheet, Judge chiefJudge, Judge chiefSecretary,
//                     List<Judge> judges, List<Judge> secretaries) {
//
//        Row titleRow = sheet.createRow(0);
//        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 7)); // Объединяем ячейки для заголовка
//        createCell(titleRow, 0, "Чемпионат города Самара (боевая арена) по компьютерному спорту");
//
//        Row titleSecondRow = sheet.createRow(0);
//        sheet.addMergedRegion(new CellRangeAddress(1, 1, 0, 7)); // Объединяем ячейки для заголовка
//        createCell(titleSecondRow, 1, "Судейская бригада соревнований");
//
//
//        // Создаем заголовки колонок
//        String[] headers = {"№", "Ф.И.О", "Должность / Специализация", "Категория / Аттестат",
//                "Субъект РФ / Страна", "Дата рожд.", "Проживает", "Контакты"};
//        Row headerRow = sheet.createRow(2);
//        for (int i = 0; i < headers.length; i++) {
//            createCell(headerRow, i, headers[i]);
//        }
//
//        writeChiefJudge(sheet, chiefJudge);
//        writeChiefSecretary(sheet, chiefSecretary);
//        writeJudges(sheet, judges, 5);
//        writeSecretary(sheet, secretaries, 5 + judges.size());
//
//    }
//
//    private void writeSecretary(Sheet sheet, List<Judge> secretaries, int index) {
//        int newIndex = index - 2;
//        for (int i = 0; i < secretaries.size(); i++) {
//            Row row = sheet.createRow(i + index);
//            Judge secratary = secretaries.get(i);
//
//            String name = secratary.getLastName() + secratary.getFirstName() + secratary.getMiddleName();
//
//
//            row.createCell(0).setCellValue(newIndex++);
//            row.createCell(1).setCellValue(name);
//            row.createCell(2).setCellValue("Секретарь");
//            row.createCell(3).setCellValue(secratary.getCertificationLevel());
//            row.createCell(4).setCellValue(secratary.getSubjectOfRF());
//            row.createCell(5).setCellValue(secratary.getBirthDate());
//            row.createCell(6).setCellValue(secratary.getResidence());
//            row.createCell(7).setCellValue(secratary.getContacts().get(0));
//        }
//    }
//
//    private void writeJudges(Sheet sheet, List<Judge> judges, int index) {
//        int newIndex = index - 2;
//        for (int i = 0; i < judges.size(); i++) {
//            Row row = sheet.createRow(i + index);
//            Judge judge = judges.get(i);
//
//            String name = judge.getLastName() + judge.getFirstName() + judge.getMiddleName();
//
//
//            row.createCell(0).setCellValue(newIndex++);
//            row.createCell(1).setCellValue(name);
//            row.createCell(2).setCellValue("Матчевый судья");
//            row.createCell(3).setCellValue(judge.getCertificationLevel());
//            row.createCell(4).setCellValue(judge.getSubjectOfRF());
//            row.createCell(5).setCellValue(judge.getBirthDate());
//            row.createCell(6).setCellValue(judge.getResidence());
//            row.createCell(7).setCellValue(judge.getContacts().get(0));
//        }
//    }
//
//    private void writeChiefJudge(Sheet sheet, Judge chiefJudge) {
//        String name = chiefJudge.getLastName() + chiefJudge.getFirstName() + chiefJudge.getMiddleName();
//
//        Row row = sheet.createRow(3);
//        row.createCell(0).setCellValue(1);
//        row.createCell(1).setCellValue(name);
//        row.createCell(2).setCellValue("Главный судья");
//        row.createCell(3).setCellValue(chiefJudge.getCertificationLevel());
//        row.createCell(4).setCellValue(chiefJudge.getSubjectOfRF());
//        row.createCell(5).setCellValue(chiefJudge.getBirthDate());
//        row.createCell(6).setCellValue(chiefJudge.getResidence());
//        row.createCell(7).setCellValue(chiefJudge.getContacts().get(0));
//    }
//
//    private void writeChiefSecretary(Sheet sheet, Judge chiefSecretary) {
//        String name = chiefSecretary.getLastName() + chiefSecretary.getFirstName() + chiefSecretary.getMiddleName();
//
//        Row row = sheet.createRow(4);
//        row.createCell(0).setCellValue(2);
//        row.createCell(1).setCellValue(name);
//        row.createCell(2).setCellValue("Главный секретарь");
//        row.createCell(3).setCellValue(chiefSecretary.getCertificationLevel());
//        row.createCell(4).setCellValue(chiefSecretary.getSubjectOfRF());
//        row.createCell(5).setCellValue(chiefSecretary.getBirthDate());
//        row.createCell(6).setCellValue(chiefSecretary.getResidence());
//        row.createCell(7).setCellValue(chiefSecretary.getContacts().get(0));
//    }



}
