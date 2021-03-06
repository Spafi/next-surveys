package com.spaf.surveys.security.registration.service;

import com.spaf.surveys.security.registration.email.EmailSender;
import com.spaf.surveys.security.registration.models.RegistrationRequest;
import com.spaf.surveys.security.registration.token.ConfirmationToken;
import com.spaf.surveys.security.registration.token.ConfirmationTokenService;
import com.spaf.surveys.security.user.models.AppUser;
import com.spaf.surveys.security.user.models.AppUserRole;
import com.spaf.surveys.security.user.services.AppUserService;
import io.github.cdimascio.dotenv.Dotenv;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class RegistrationService {

    private final AppUserService appUserService;
    private final EmailValidator emailValidator;
    private final ConfirmationTokenService confirmationTokenService;
    private final EmailSender emailSender;


    public String register(RegistrationRequest request) {
        boolean isValidEmail = emailValidator.
                test(request.getEmail());

        if (!isValidEmail) {
            throw new IllegalStateException("Email not valid");
        }

        String token = appUserService.signUpUser(
                new AppUser(
                        request.getFirstName(),
                        request.getLastName(),
                        request.getEmail(),
                        request.getPassword(),
                        AppUserRole.valueOf(request.getRole()),
                        request.getIpAddress()
                )
        );

        Dotenv dotenv = Dotenv.load();

        String BASE_URL = dotenv.get("BASE_URL");

        String link = BASE_URL + "/api/v1/registration/confirm?token=" + token;

        emailSender.send(
                request.getEmail(),
                buildEmail(request.getFirstName(), link));
        return token;
    }

    @Transactional
    public String confirmToken(String token) {
        ConfirmationToken confirmationToken = confirmationTokenService
                .getToken(token)
                .orElseThrow(() ->
                        new IllegalStateException("Token not found"));

        if (confirmationToken.getConfirmedAt() != null) {
            throw new IllegalStateException("Email already confirmed");
        }

        LocalDateTime expiredAt = confirmationToken.getExpiresAt();

        if (expiredAt.isBefore(LocalDateTime.now())) {
            throw new IllegalStateException("Token expired");
        }

        confirmationTokenService.setConfirmedAt(token);

        Dotenv dotenv = Dotenv.load();

        String FRONT_END_REDIRECT_URL = dotenv.get("FRONT_END_REDIRECT_URL");

        appUserService.enableAppUser(
                confirmationToken.getUser().getEmail());
        return buildConfirmationPage(FRONT_END_REDIRECT_URL);
    }

    private String buildConfirmationPage(String redirectLink) {
        return "<!DOCTYPE html>" +
                "<html lang=\"en\">" +
                "<head>" +
                "    <meta charset=\"UTF-8\" />" +
                "    <meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\" />" +
                "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\" />" +
                "" +
                "    <title>Email Confirmed</title>" +
                "    <script defer>" +
                "        window.onload = (event) => {" +
                "            setTimeout(()=> {window.location.replace(\"" + redirectLink + "\");}, 3000);" +
                "            let counter = 2;" +
                "            setInterval(() => {" +
                "                document.getElementById('counter').innerText = counter;" +
                "                counter--;" +
                "            }, 1000);" +
                "        };" +
                "    </script>" +
                "</head>" +
                "<body>" +
                "<p>Email Confirmed</p>" +
                "<p>" +
                "    You will be redirected in <span id=\"counter\">3</span> seconds to the login" +
                "    page" +
                "</p>" +
                "</body>" +
                "</html>";


    }

    private String buildEmail(String name, String link) {

        return "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">" +
                "<html xmlns=\"http://www.w3.org/1999/xhtml\" xmlns:o=\"urn:schemas-microsoft-com:office:office\" style=\"width:100%;font-family:helvetica, 'helvetica neue', arial, verdana, sans-serif;-webkit-text-size-adjust:100%;-ms-text-size-adjust:100%;padding:0;Margin:0\">" +
                " <head> " +
                "  <meta charset=\"UTF-8\"> " +
                "  <meta content=\"width=device-width, initial-scale=1\" name=\"viewport\"> " +
                "  <meta name=\"x-apple-disable-message-reformatting\"> " +
                "  <meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\"> " +
                "  <meta content=\"telephone=no\" name=\"format-detection\"> " +
                "  <title>Welcome to Next Surveys</title>" +
                "  <!--[if (mso 16)]>" +
                "    <style type=\"text/css\">" +
                "    a {text-decoration: none;}" +
                "    </style>" +
                "    <![endif]--> " +
                "  <!--[if gte mso 9]><style>sup { font-size: 100% !important; }</style><![endif]--> " +
                "  <!--[if gte mso 9]>" +
                "<xml>" +
                "    <o:OfficeDocumentSettings>" +
                "    <o:AllowPNG></o:AllowPNG>" +
                "    <o:PixelsPerInch>96</o:PixelsPerInch>" +
                "    </o:OfficeDocumentSettings>" +
                "</xml>" +
                "<![endif]--> " +
                "  <!--[if !mso]><!-- -->" +
                "  <link href=\"https://fonts.googleapis.com/css?family=Lato:400,400i,700,700i\" rel=\"stylesheet\">" +
                "  <!--<![endif]--> " +
                "  <style type=\"text/css\">" +
                "#outlook a {" +
                "	padding:0;" +
                "}" +
                ".ExternalClass {" +
                "	width:100%;" +
                "}" +
                ".ExternalClass," +
                ".ExternalClass p," +
                ".ExternalClass span," +
                ".ExternalClass font," +
                ".ExternalClass td," +
                ".ExternalClass div {" +
                "	line-height:100%;" +
                "}" +
                ".es-button {" +
                "	mso-style-priority:100!important;" +
                "	text-decoration:none!important;" +
                "	transition:all 100ms ease-in;" +
                "}" +
                "a[x-apple-data-detectors] {" +
                "	color:inherit!important;" +
                "	text-decoration:none!important;" +
                "	font-size:inherit!important;" +
                "	font-family:inherit!important;" +
                "	font-weight:inherit!important;" +
                "	line-height:inherit!important;" +
                "}" +
                ".es-button:hover {" +
                "	background:#e2bd35!important;" +
                "	border-color:#e2bd35!important;" +
                "}" +
                ".es-desk-hidden {" +
                "	display:none;" +
                "	float:left;" +
                "	overflow:hidden;" +
                "	width:0;" +
                "	max-height:0;" +
                "	line-height:0;" +
                "	mso-hide:all;" +
                "}" +
                "[data-ogsb] .es-button {" +
                "	border-width:0!important;" +
                "	padding:15px 30px 15px 30px!important;" +
                "}" +
                "[data-ogsb] .es-button.es-button-1 {" +
                "	padding:15px 25px!important;" +
                "}" +
                "[data-ogsb] .es-button.es-button-2 {" +
                "	padding:15px!important;" +
                "}" +
                "@media only screen and (max-width:600px) {p, ul li, ol li, a { line-height:150%!important } h1 { font-size:30px!important; text-align:center; line-height:120%!important } h2 { font-size:26px!important; text-align:left; line-height:120%!important } h3 { font-size:20px!important; text-align:left; line-height:120%!important } h1 a { text-align:center } .es-header-body h1 a, .es-content-body h1 a, .es-footer-body h1 a { font-size:30px!important } h2 a { text-align:left } .es-header-body h2 a, .es-content-body h2 a, .es-footer-body h2 a { font-size:20px!important } h3 a { text-align:left } .es-header-body h3 a, .es-content-body h3 a, .es-footer-body h3 a { font-size:20px!important } .es-menu td a { font-size:16px!important } .es-header-body p, .es-header-body ul li, .es-header-body ol li, .es-header-body a { font-size:16px!important } .es-content-body p, .es-content-body ul li, .es-content-body ol li, .es-content-body a { font-size:17px!important } .es-footer-body p, .es-footer-body ul li, .es-footer-body ol li, .es-footer-body a { font-size:17px!important } .es-infoblock p, .es-infoblock ul li, .es-infoblock ol li, .es-infoblock a { font-size:12px!important } *[class=\"gmail-fix\"] { display:none!important } .es-m-txt-c, .es-m-txt-c h1, .es-m-txt-c h2, .es-m-txt-c h3 { text-align:center!important } .es-m-txt-r, .es-m-txt-r h1, .es-m-txt-r h2, .es-m-txt-r h3 { text-align:right!important } .es-m-txt-l, .es-m-txt-l h1, .es-m-txt-l h2, .es-m-txt-l h3 { text-align:left!important } .es-m-txt-r img, .es-m-txt-c img, .es-m-txt-l img { display:inline!important } .es-button-border { display:inline-block!important } a.es-button, button.es-button { font-size:14px!important; display:inline-block!important; border-width:15px 25px 15px 25px!important } .es-btn-fw { border-width:10px 0px!important; text-align:center!important } .es-adaptive table, .es-btn-fw, .es-btn-fw-brdr, .es-left, .es-right { width:100%!important } .es-content table, .es-header table, .es-footer table, .es-content, .es-footer, .es-header { width:100%!important; max-width:600px!important } .es-adapt-td { display:block!important; width:100%!important } .adapt-img { width:100%!important; height:auto!important } .es-m-p0 { padding:0px!important } .es-m-p0r { padding-right:0px!important } .es-m-p0l { padding-left:0px!important } .es-m-p0t { padding-top:0px!important } .es-m-p0b { padding-bottom:0!important } .es-m-p20b { padding-bottom:20px!important } .es-mobile-hidden, .es-hidden { display:none!important } tr.es-desk-hidden, td.es-desk-hidden, table.es-desk-hidden { width:auto!important; overflow:visible!important; float:none!important; max-height:inherit!important; line-height:inherit!important } tr.es-desk-hidden { display:table-row!important } table.es-desk-hidden { display:table!important } td.es-desk-menu-hidden { display:table-cell!important } .es-menu td { width:1%!important } table.es-table-not-adapt, .esd-block-html table { width:auto!important } table.es-social { display:inline-block!important } table.es-social td { display:inline-block!important } }" +
                "</style> " +
                " </head> " +
                " <body style=\"width:100%;font-family:helvetica, 'helvetica neue', arial, verdana, sans-serif;-webkit-text-size-adjust:100%;-ms-text-size-adjust:100%;padding:0;Margin:0\"> " +
                "  <div class=\"es-wrapper-color\" style=\"background-color:transparent\"> " +
                "   <!--[if gte mso 9]>" +
                "			<v:background xmlns:v=\"urn:schemas-microsoft-com:vml\" fill=\"t\">" +
                "				<v:fill type=\"tile\" color=\"transparent\" origin=\"0.5, 0\" position=\"0.5, 0\"></v:fill>" +
                "			</v:background>" +
                "		<![endif]--> " +
                "   <table class=\"es-wrapper\" width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;padding:0;Margin:0;width:100%;height:100%;background-repeat:repeat;background-position:center top;background-color:transparent\"> " +
                "     <tr style=\"border-collapse:collapse\"> " +
                "      <td valign=\"top\" style=\"padding:0;Margin:0\"> " +
                "       <table cellpadding=\"0\" cellspacing=\"0\" class=\"es-content\" align=\"center\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;table-layout:fixed !important;width:100%\"> " +
                "         <tr style=\"border-collapse:collapse\"> " +
                "          <td align=\"center\" style=\"padding:0;Margin:0\"> " +
                "           <table class=\"es-content-body\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;background-color:transparent;width:600px\" cellspacing=\"0\" cellpadding=\"0\" align=\"center\" bgcolor=\"#fef0f8\"> " +
                "             <tr style=\"border-collapse:collapse\"> " +
                "              <td align=\"left\" style=\"Margin:0;padding-left:10px;padding-right:10px;padding-top:15px;padding-bottom:15px\"> " +
                "               <!--[if mso]><table style=\"width:580px\" cellpadding=\"0\" cellspacing=\"0\"><tr><td style=\"width:282px\" valign=\"top\"><![endif]--> " +
                "               <table class=\"es-left\" cellspacing=\"0\" cellpadding=\"0\" align=\"left\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;float:left\"> " +
                "                 <tr style=\"border-collapse:collapse\"> " +
                "                  <td align=\"left\" style=\"padding:0;Margin:0;width:282px\"> " +
                "                   <table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" role=\"presentation\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px\"> " +
                "                     <tr style=\"border-collapse:collapse\"> " +
                "                      <td class=\"es-infoblock es-m-txt-c\" align=\"left\" style=\"padding:0;Margin:0;line-height:14px;font-size:12px;color:#CCCCCC\"><p style=\"Margin:0;-webkit-text-size-adjust:none;-ms-text-size-adjust:none;mso-line-height-rule:exactly;font-family:arial, 'helvetica neue', helvetica, sans-serif;line-height:14px;color:#CCCCCC;font-size:12px\"><br></p></td> " +
                "                     </tr> " +
                "                   </table></td> " +
                "                 </tr> " +
                "               </table> " +
                "               <!--[if mso]></td><td style=\"width:20px\"></td><td style=\"width:278px\" valign=\"top\"><![endif]--> " +
                "               <table class=\"es-right\" cellspacing=\"0\" cellpadding=\"0\" align=\"right\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;float:right\"> " +
                "                 <tr style=\"border-collapse:collapse\"> " +
                "                  <td align=\"left\" style=\"padding:0;Margin:0;width:278px\"> " +
                "                   <table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" role=\"presentation\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px\"> " +
                "                     <tr style=\"border-collapse:collapse\"> " +
                "                      <td align=\"right\" class=\"es-infoblock es-m-txt-c\" style=\"padding:0;Margin:0;line-height:14px;font-size:12px;color:#CCCCCC\"><p style=\"Margin:0;-webkit-text-size-adjust:none;-ms-text-size-adjust:none;mso-line-height-rule:exactly;font-family:helvetica, 'helvetica neue', arial, verdana, sans-serif;line-height:14px;color:#CCCCCC;font-size:12px\"><webversion><p class=\"view\" style=\"-webkit-text-size-adjust:none;-ms-text-size-adjust:none;mso-line-height-rule:exactly;text-decoration:underline;color:#CCCCCC;font-size:12px;font-family:arial, 'helvetica neue', helvetica, sans-serif\">View in browser</p><webversion></p></td> " +
                "                     </tr> " +
                "                   </table></td> " +
                "                 </tr> " +
                "               </table> " +
                "               <!--[if mso]></td></tr></table><![endif]--></td> " +
                "             </tr> " +
                "           </table></td> " +
                "         </tr> " +
                "       </table> " +
                "       <table cellpadding=\"0\" cellspacing=\"0\" class=\"es-header\" align=\"center\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;table-layout:fixed !important;width:100%;background-color:transparent;background-repeat:repeat;background-position:center top\"> " +
                "         <tr style=\"border-collapse:collapse\"> " +
                "          <td align=\"center\" style=\"padding:0;Margin:0\"> " +
                "           <table class=\"es-header-body\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;background-color:#934B74;width:600px\" cellspacing=\"0\" cellpadding=\"0\" bgcolor=\"#934b74\" align=\"center\"> " +
                "             <tr style=\"border-collapse:collapse\"> " +
                "              <td align=\"left\" bgcolor=\"#fef0f8\" style=\"padding:0;Margin:0;padding-top:20px;padding-left:40px;padding-right:40px;background-color:#FEF0F8\"> " +
                "               <table cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px\"> " +
                "                 <tr style=\"border-collapse:collapse\"> " +
                "                  <td align=\"center\" valign=\"top\" style=\"padding:0;Margin:0;width:520px\"> " +
                "                   <table cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" role=\"presentation\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px\"> " +
                "                     <tr style=\"border-collapse:collapse\"> " +
                "                      <td align=\"center\" style=\"padding:0;Margin:0;padding-bottom:10px;font-size:0px\">" +
                "                       <a href=\"" + link + "\"" +
                "                               target=\"_blank\" style=\"-webkit-text-size-adjust:none;-ms-text-size-adjust:none;mso-line-height-rule:exactly;text-decoration:underline;color:#FFFFFF;font-size:14px\"><img src=\"https://i.ibb.co/xJmPJx2/diamond.png\" alt style=\"display:block;border:0;outline:none;text-decoration:none;-ms-interpolation-mode:bicubic\" width=\"41\"></a></td> " +
                "                     </tr> " +
                "                   </table></td> " +
                "                 </tr> " +
                "               </table></td> " +
                "             </tr> " +
                "           </table></td> " +
                "         </tr> " +
                "       </table> " +
                "       <table class=\"es-content\" cellspacing=\"0\" cellpadding=\"0\" align=\"center\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;table-layout:fixed !important;width:100%\"> " +
                "         <tr style=\"border-collapse:collapse\"> " +
                "          <td align=\"center\" style=\"padding:0;Margin:0\"> " +
                "           <table class=\"es-content-body\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;background-color:#FEF0F8;width:600px\" cellspacing=\"0\" cellpadding=\"0\" bgcolor=\"#fef0f8\" align=\"center\"> " +
                "             <tr style=\"border-collapse:collapse\"> " +
                "              <td style=\"padding:40px;Margin:0;background-image:url(https://rfgbej.stripocdn.email/content/guids/CABINET_4648aef935b9aac007d3c243175daeda/images/98821623856374027.png);background-repeat:no-repeat;background-position:center top\" align=\"left\" background=\"https://rfgbej.stripocdn.email/content/guids/CABINET_4648aef935b9aac007d3c243175daeda/images/98821623856374027.png\"> " +
                "               <table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px\"> " +
                "                 <tr style=\"border-collapse:collapse\"> " +
                "                  <td valign=\"top\" align=\"center\" style=\"padding:0;Margin:0;width:520px\"> " +
                "                   <table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" role=\"presentation\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px\"> " +
                "                     <tr style=\"border-collapse:collapse\"> " +
                "                      <td align=\"center\" style=\"padding:0;Margin:0;padding-bottom:10px;padding-top:40px\"><h1 style=\"Margin:0;line-height:36px;mso-line-height-rule:exactly;font-family:lato, 'helvetica neue', helvetica, arial, sans-serif;font-size:30px;font-style:normal;font-weight:bold;color:#FFFFFF\">" +
                "WELCOME " +
                name +
                "</h1></td> " +
                "                     </tr> " +
                "                     <tr style=\"border-collapse:collapse\"> " +
                "                      <td esdev-links-color=\"#757575\" align=\"center\" style=\"Margin:0;padding-top:10px;padding-bottom:20px;padding-left:30px;padding-right:30px\"><p style=\"Margin:0;-webkit-text-size-adjust:none;-ms-text-size-adjust:none;mso-line-height-rule:exactly;font-family:helvetica, 'helvetica neue', arial, verdana, sans-serif;line-height:23px;color:#FFFFFF;font-size:15px\">Click on the link below to activate your account.<br><strong>Hurry Up!</strong> Link will expire in 15 minutes</p></td> " +
                "                     </tr> " +
                "                     <tr style=\"border-collapse:collapse\"> " +
                "                      <td align=\"center\" style=\"padding:0;Margin:0;padding-top:10px;padding-bottom:20px\"><span class=\"es-button-border\" style=\"border-style:solid;border-color:#FFFFFF;background:#FFFFFF;border-width:0px;display:inline-block;border-radius:50px;width:auto\">" +
                "<a href=\"" + link + "\"" +
                "class=\"es-button es-button-2\" target=\"_blank\" style=\"mso-style-priority:100 !important;text-decoration:none;transition:all 100ms ease-in;-webkit-text-size-adjust:none;-ms-text-size-adjust:none;mso-line-height-rule:exactly;color:#000000;font-size:14px;border-style:solid;border-color:#FFFFFF;border-width:15px;display:inline-block;background:#FFFFFF;border-radius:50px;font-family:arial, 'helvetica neue', helvetica, sans-serif;font-weight:bold;font-style:normal;line-height:17px;width:auto;text-align:center\">ACTIVATE ACCOUNT</a></span></td> " +
                "                     </tr> " +
                "                     <tr style=\"border-collapse:collapse\"> " +
                "                      <td esdev-links-color=\"#757575\" align=\"center\" style=\"padding:10px;Margin:0\"><p style=\"Margin:0;-webkit-text-size-adjust:none;-ms-text-size-adjust:none;mso-line-height-rule:exactly;font-family:helvetica, 'helvetica neue', arial, verdana, sans-serif;line-height:23px;color:#FFFFFF;font-size:15px\"><br></p></td> " +
                "                     </tr> " +
                "                   </table></td> " +
                "                 </tr> " +
                "               </table></td> " +
                "             </tr> " +
                "           </table></td> " +
                "         </tr> " +
                "       </table> " +
                "       <table class=\"es-content\" cellspacing=\"0\" cellpadding=\"0\" align=\"center\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;table-layout:fixed !important;width:100%\"> " +
                "         <tr style=\"border-collapse:collapse\"> " +
                "          <td align=\"center\" style=\"padding:0;Margin:0\"> " +
                "           <table class=\"es-content-body\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;background-color:transparent;width:600px\" cellspacing=\"0\" cellpadding=\"0\" align=\"center\" bgcolor=\"#fef0f8\"> " +
                "             <tr style=\"border-collapse:collapse\"> " +
                "              <td align=\"left\" style=\"Margin:0;padding-left:20px;padding-right:20px;padding-top:30px;padding-bottom:30px\"> " +
                "               <table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px\"> " +
                "                 <tr style=\"border-collapse:collapse\"> " +
                "                  <td valign=\"top\" align=\"center\" style=\"padding:0;Margin:0;width:560px\"> " +
                "                   <table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px\"> " +
                "                     <tr style=\"border-collapse:collapse\"> " +
                "                      <td align=\"center\" style=\"padding:0;Margin:0;display:none\"></td> " +
                "                     </tr> " +
                "                   </table></td> " +
                "                 </tr> " +
                "               </table></td> " +
                "             </tr> " +
                "           </table></td> " +
                "         </tr> " +
                "       </table></td> " +
                "     </tr> " +
                "   </table> " +
                "  </div>  " +
                " </body>" +
                "</html>";


    }

}