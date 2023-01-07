package com.example.inzynierka.mailSender;

import org.springframework.stereotype.Component;

@Component
public class EmailFactory {
    //TODO: mail do zmiany
    //poniższy mail jest atrapa i trzeba go zmienić
    public String buildRegistrationEmail(String name, String link) {
        return "<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" style=\"table-layout:fixed;background-color:#f9f9f9\" id=\"bodyTable\">\n" +
                "\t<tbody>\n" +
                "\t\t<tr>\n" +
                "\t\t\t<td style=\"padding-right:2%;padding-left:2%;\" align=\"center\" valign=\"top\" id=\"bodyCell\">\n" +
                "\t\t\t\t<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" class=\"wrapperBody\" style=\"max-width:800px\">\n" +
                "\t\t\t\t\t<tbody>\n" +
                "\t\t\t\t\t\t<tr>\n" +
                "\t\t\t\t\t\t\t<td align=\"center\" valign=\"top\">\n" +
                "\t\t\t\t\t\t\t\t<table style=\"background-color:#fff;border-color:#e5e5e5;border-style:solid;border-width:0 1px 1px 1px;\">\n" +
                "\t\t\t\t\t\t\t\t\t<tbody>\n" +
                "\t\t\t\t\t\t\t\t\t\t<tr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t<td style=\"background-color:#515151;font-size:1px;line-height:3px\" class=\"topBorder\" height=\"3\">&nbsp;</td>\n" +
                "\t\t\t\t\t\t\t\t\t\t</tr>\n" +
                "\t\t\t\t\t\t\t\t\t\t<tr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t<td style=\"padding-bottom: 1%; padding-top:3%\" align=\"center\" valign=\"top\" class=\"title\">\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<p class=\"text\" style=\"color:#000;font-family:Tahoma;font-size:28px;font-weight:500;line-height:130%;text-align:center\">Cześć " + name + "</p>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t</td>\n" +
                "\t\t\t\t\t\t\t\t\t\t</tr>\n" +
                "\t\t\t\t\t\t\t\t\t\t<tr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t<td style=\"padding-bottom: 30px; padding-left: 20px; padding-right: 20px;\" align=\"center\" valign=\"top\" class=\"subTitle\">\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<h4 class=\"text\" style=\"color:#999;font-family:Poppins,Helvetica,Arial,sans-serif;font-size:16px;font-weight:500;font-style:normal;letter-spacing:normal;line-height:24px;text-transform:none;text-align:center;padding:0;margin:0\">TUTAJ LOGO</h4>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t</td>\n" +
                "\t\t\t\t\t\t\t\t\t\t</tr>\n" +
                "\t\t\t\t\t\t\t\t\t\t<tr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t<td style=\"padding-left:3%;padding-right:3%\" align=\"center\" valign=\"center\" class=\"message\">\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<table border=\"0\" cellspacing=\"0\">\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t<tbody>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t<tr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<td style=\"padding-bottom: 3%;\" align=\"center\" valign=\"center\">\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<p class=\"message\" style=\"color:#666;font-family:Tahoma;font-size:14px;font-weight:400;line-height:22px;;text-align:center;\">Witaj wśród użytkowników Hot Spoon, aby potwierdzić swoje konto kliknij w poniższy przycisk :)</p>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t</td>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t</tr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t</tbody>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t</table>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<table>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t<tbody>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t<tr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<td style=\"padding-top:5%;padding-bottom:13%\" align=\"center\" valign=\"top\">\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<table align=\"center\">\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<tbody>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<tr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<td style=\"background-color: #515151; padding: 7% 15%; border-radius: 50px;\" align=\"center\" class=\"linkButton\"> <a href= " + link + " style=\"color:#fff;font-family:Tahoma;font-size:13px;font-weight:600;letter-spacing:1px;line-height:20px;text-decoration:none;display:block\" target=\"_blank\">POTWIERDŹ KONTO</a>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t</td>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t</tr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t</tbody>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t</table>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t</td>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t</tr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t</tbody>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t</table>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t</td>\n" +
                "\t\t\t\t\t\t\t\t\t\t</tr>\n" +
                "\t\t\t\t\t\t\t\t\t</tbody>\n" +
                "\t\t\t\t\t\t\t\t</table>\n" +
                "\t\t\t\t\t\t\t</td>\n" +
                "\t\t\t\t\t\t</tr>\n" +
                "\t\t\t\t\t</tbody>\n" +
                "\t\t\t\t</table>\n" +
                "\t\t\t\t<table cellspacing=\"13\" width=\"100%\" style=\"max-width:800px\">\n" +
                "\t\t\t\t\t<tbody>\n" +
                "\t\t\t\t\t\t<tr>\n" +
                "\t\t\t\t\t\t\t<td align=\"center\" valign=\"top\">\n" +
                "\t\t\t\t\t\t\t\t<table width=\"100%\" class=\"footer\">\n" +
                "\t\t\t\t\t\t\t\t\t<tbody>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t<td style=\"padding: 1%\" align=\"center\" valign=\"top\" class=\"footerLinks\">\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<p class=\"text\" style=\"color:#bbb;font-family:Tahoma;font-size:12px;font-weight:400;line-height:20px;text-align:center\"> <a href=\"#\" style=\"color:#bbb;text-decoration:underline\" target=\"_blank\">View Web Version </a>&nbsp;|&nbsp; <a href=\"#\" style=\"color:#bbb;text-decoration:underline\" target=\"_blank\">Email Preferences </a>&nbsp;|&nbsp; <a href=\"#\" style=\"color:#bbb;text-decoration:underline\" target=\"_blank\">Privacy Policy</a>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t</p>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t</td>\n" +
                "\t\t\t\t\t\t\t\t\t\t</tr>\n" +
                "\t\t\t\t\t\t\t\t\t\t<tr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t<td style=\"padding: 1%;\" align=\"center\" valign=\"top\" class=\"footerEmailInfo\">\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<p class=\"text\" style=\"color:#bbb;font-family:Tahoma;font-size:12px;font-weight:400;line-height:20px;text-align:center;\">Jeśli masz jakieś pytania, skontaktuj się z nami <a href=\"#\" style=\"color:#bbb;text-decoration:underline\" target=\"_blank\">support@mail.com.</a>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t<br> <a href=\"#\" style=\"color:#bbb;text-decoration:underline\" target=\"_blank\">Unsubscribe</a> from our mailing lists</p>\n" +
                "\t\t\t\t\t\t\t\t\t\t</tr>\n" +
                "\t\t\t\t\t\t\t\t\t</tbody>\n" +
                "\t\t\t\t\t\t\t\t</table>\n" +
                "\t\t\t\t\t\t\t</td>\n" +
                "\t\t\t\t\t\t</tr>\n" +
                "\t\t\t\t\t</tbody>\n" +
                "\t\t\t\t</table>\n" +
                "\t\t\t</td>\n" +
                "\t\t</tr>\n" +
                "\t</tbody>\n" +
                "</table>";
    }


    public String buildJoinFamilyPantryEmail(String name, String login, String link){
        return "<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" style=\"table-layout:fixed;background-color:#f9f9f9\" id=\"bodyTable\">\n" +
                "\t<tbody>\n" +
                "\t\t<tr>\n" +
                "\t\t\t<td style=\"padding-right:2%;padding-left:2%;\" align=\"center\" valign=\"top\" id=\"bodyCell\">\n" +
                "\t\t\t\t<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" class=\"wrapperBody\" style=\"max-width:800px\">\n" +
                "\t\t\t\t\t<tbody>\n" +
                "\t\t\t\t\t\t<tr>\n" +
                "\t\t\t\t\t\t\t<td align=\"center\" valign=\"top\">\n" +
                "\t\t\t\t\t\t\t\t<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" class=\"tableCard\" style=\"background-color:#fff;border-color:#e5e5e5;border-style:solid;border-width:0 1px 1px 1px;\">\n" +
                "\t\t\t\t\t\t\t\t\t<tbody>\n" +
                "\t\t\t\t\t\t\t\t\t\t<tr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t<td style=\"background-color:#515151;font-size:1px;line-height:3px\" class=\"topBorder\" height=\"3\">&nbsp;</td>\n" +
                "\t\t\t\t\t\t\t\t\t\t</tr>\n" +
                "\t\t\t\t\t\t\t\t\t\t<tr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t<td style=\"padding-bottom: 1%; padding-left: 20px; padding-right: 20px; padding-top:3%\" align=\"center\" valign=\"top\" class=\"mainTitle\">\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<p class=\"text\" style=\"color:#000;font-family:Poppins,Helvetica,Arial,sans-serif;font-size:28px;font-weight:500;font-style:normal;letter-spacing:normal;line-height:36px;text-transform:none;text-align:center;padding:0;margin:0\">Cześć " + name + "</p>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t</td>\n" +
                "\t\t\t\t\t\t\t\t\t\t</tr>\n" +
                "\t\t\t\t\t\t\t\t\t\t<tr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t<td style=\"padding-bottom: 30px; padding-left: 20px; padding-right: 20px;\" align=\"center\" valign=\"top\" class=\"subTitle\">\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<h4 class=\"text\" style=\"color:#999;font-family:Poppins,Helvetica,Arial,sans-serif;font-size:16px;font-weight:500;font-style:normal;letter-spacing:normal;line-height:24px;text-transform:none;text-align:center;padding:0;margin:0\">TUTAJ LOGO</h4>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t</td>\n" +
                "\t\t\t\t\t\t\t\t\t\t</tr>\n" +
                "\t\t\t\t\t\t\t\t\t\t<tr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t<td style=\"padding-left:20px;padding-right:20px\" align=\"center\" valign=\"top\" class=\"containtTable ui-sortable\">\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" class=\"tableDescription\" style=\"\">\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t<tbody>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t<tr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<td style=\"padding-bottom: 20px;\" align=\"center\" valign=\"top\" class=\"description\">\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<p class=\"text\" style=\"color:#666;font-family:'Open Sans',Helvetica,Arial,sans-serif;font-size:14px;font-weight:400;font-style:normal;letter-spacing:normal;line-height:22px;text-transform:none;text-align:center;padding:0;margin:0\">Użytkownik " + login + " zaprosił Cię do rodzinnej spiżarni. Jeśli chcesz do niej dołączyć kliknij w poniższy przycisk :) <br><br> UWAGA!<br> Można należeć tylko do jednej rodzinnej spiżarni. Jeśli należysz już do innej rodzinnej spiżarni po kliknięciu w poniższy przycisk zostaniesz z niej usunięty.</p>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t</td>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t</tr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t</tbody>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t</table>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" class=\"tableButton\" style=\"\">\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t<tbody>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t<tr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<td style=\"padding-top:20px;padding-bottom:20px\" align=\"center\" valign=\"top\">\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" align=\"center\">\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<tbody>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<tr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<td style=\"background-color: #515151; padding: 12px 35px; border-radius: 50px;\" align=\"center\" class=\"ctaButton\"> <a href=" + link + " style=\"color:#fff;font-family:Poppins,Helvetica,Arial,sans-serif;font-size:13px;font-weight:600;font-style:normal;letter-spacing:1px;line-height:20px;text-transform:uppercase;text-decoration:none;display:block\" target=\"_blank\" class=\"text\">Dołącz</a>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t</td>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t</tr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t</tbody>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t</table>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t</td>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t</tr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t</tbody>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t</table>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t</td>\n" +
                "\t\t\t\t\t\t\t\t\t\t</tr>\n" +
                "\t\t\t\t\t\t\t\t\t</tbody>\n" +
                "\t\t\t\t\t\t\t\t</table>\n" +
                "\t\t\t\t\t\t\t\t<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" class=\"space\">\n" +
                "\t\t\t\t\t\t\t\t\t<tbody>\n" +
                "\t\t\t\t\t\t\t\t\t\t<tr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t<td style=\"font-size:1px;line-height:1px\" height=\"30\">&nbsp;</td>\n" +
                "\t\t\t\t\t\t\t\t\t\t</tr>\n" +
                "\t\t\t\t\t\t\t\t\t</tbody>\n" +
                "\t\t\t\t\t\t\t\t</table>\n" +
                "\t\t\t\t\t\t\t</td>\n" +
                "\t\t\t\t\t\t</tr>\n" +
                "\t\t\t\t\t</tbody>\n" +
                "\t\t\t\t</table>\n" +
                "\t\t\t\t<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" class=\"wrapperFooter\" style=\"max-width:600px\">\n" +
                "\t\t\t\t\t<tbody>\n" +
                "\t\t\t\t\t\t<tr>\n" +
                "\t\t\t\t\t\t\t<td align=\"center\" valign=\"top\">\n" +
                "\t\t\t\t\t\t\t\t<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" class=\"footer\">\n" +
                "\t\t\t\t\t\t\t\t\t<tbody>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t<td style=\"padding: 0px 10px 20px;\" align=\"center\" valign=\"top\" class=\"footerLinks\">\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<p class=\"text\" style=\"color:#bbb;font-family:'Open Sans',Helvetica,Arial,sans-serif;font-size:12px;font-weight:400;font-style:normal;letter-spacing:normal;line-height:20px;text-transform:none;text-align:center;padding:0;margin:0\"> <a href=\"#\" style=\"color:#bbb;text-decoration:underline\" target=\"_blank\">View Web Version </a>&nbsp;|&nbsp; <a href=\"#\" style=\"color:#bbb;text-decoration:underline\" target=\"_blank\">Email Preferences </a>&nbsp;|&nbsp; <a href=\"#\" style=\"color:#bbb;text-decoration:underline\" target=\"_blank\">Privacy Policy</a>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t</p>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t</td>\n" +
                "\t\t\t\t\t\t\t\t\t\t</tr>\n" +
                "\t\t\t\t\t\t\t\t\t\t<tr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t<td style=\"padding: 0px 10px 10px;\" align=\"center\" valign=\"top\" class=\"footerEmailInfo\">\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<p class=\"text\" style=\"color:#bbb;font-family:'Open Sans',Helvetica,Arial,sans-serif;font-size:12px;font-weight:400;font-style:normal;letter-spacing:normal;line-height:20px;text-transform:none;text-align:center;padding:0;margin:0\">Jeśli masz jakieś pytania, skontaktuj się z nami <a href=\"#\" style=\"color:#bbb;text-decoration:underline\" target=\"_blank\">support@mail.com.</a>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t<br> <a href=\"#\" style=\"color:#bbb;text-decoration:underline\" target=\"_blank\">Unsubscribe</a> from our mailing lists</p>\n" +
                "\t\t\t\t\t\t\t\t\t\t</tr>\n" +
                "\t\t\t\t\t\t\t\t\t</tbody>\n" +
                "\t\t\t\t\t\t\t\t</table>\n" +
                "\t\t\t\t\t\t\t</td>\n" +
                "\t\t\t\t\t\t</tr>\n" +
                "\t\t\t\t\t</tbody>\n" +
                "\t\t\t\t</table>\n" +
                "\t\t\t</td>\n" +
                "\t\t</tr>\n" +
                "\t</tbody>\n" +
                "</table>";
    }
}
