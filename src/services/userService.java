/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import entity.user;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import utils.BCrypt;
import utils.session;
import utils.connexion;
import interfaces.userInterface;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import static utils.EmailSender.sendEmailWithAttachments;

/**
 *
 * @author Admin
 */
public class userService implements userInterface{
      public static int code;
    public static String email;

    //var
    connexion instance = connexion.getInstance();
    Connection cnx = instance.getCnx();

  @Override
public void createuser(user u) {

    try {
        // Check if user already exists
        String checkUser = "SELECT * FROM user WHERE email=?";
        PreparedStatement stCheck = cnx.prepareStatement(checkUser);
        stCheck.setString(1, u.getEmail());
        ResultSet rs = stCheck.executeQuery();
        if (rs.next()) {
            // User already exists, throw an exception or handle it appropriately
            throw new IllegalArgumentException("User with this email already exists");
        } else {
            // User doesn't exist, create a new user
            String req = "INSERT INTO `user`(`password`, `email`, `roles`, `is_verified`, `firstname`, `lastname`, `phonenumber`, `profile_picture`, `isbanned`, `country_code`) VALUES (?,?,?,?,?,?,?,?,?,?)";
            PreparedStatement st = cnx.prepareStatement(req);
            st.setString(1, BCrypt.hashpw(u.getPassword(), BCrypt.gensalt()));
            st.setString(2, u.getEmail());
            st.setString(3, "[\"" + u.getRoles() + "\"]");
            st.setBoolean(4, false);
            st.setString(5, u.getFirstname());
            st.setString(6, u.getLastname());
            st.setInt(7, u.getPhonenumber());
            st.setString(8, u.getProfile_picture());
            st.setBoolean(9, false);
            st.setString(10, u.getCountry_code());
            st.executeUpdate();
            System.out.println("Personne ajoutée avec succes.");
            session.setUser(u);
        }
    } catch (SQLException ex) {
        ex.printStackTrace();
    }
}


     @Override
    public List<user> getalluser() {
        List<user> li = new ArrayList<user>();

        try {
            String req = "select * from user";
            Statement s = cnx.createStatement();

            ResultSet rs = s.executeQuery(req);
            while (rs.next()) {
                user l = new user();
                l.setId(rs.getInt("id"));
                l.setEmail(rs.getString("email"));
                l.setRoles(rs.getString("roles"));
                l.setIsVerfied(rs.getBoolean("is_verified"));
                l.setFirstname(rs.getString("firstname"));
                l.setLastname(rs.getString("lastname"));
                l.setPhonenumber(rs.getInt("phonenumber"));
                l.setProfile_picture(rs.getString("profile_picture"));
                l.setIsbanned(rs.getBoolean("isbanned"));
                l.setCountry_code(rs.getString("country_code"));

                li.add(l);
            }
            for (int i = 0; i < li.size(); i++) {
                System.out.println("*********");
                System.out.println(li.get(i).getEmail());
                System.out.println(li.get(i).getFirstname());

            }

        } catch (SQLException ex) {
        }

        return li;
    }

    @Override
    public List<user> pagination(int size, int row) {
        List<user> li = new ArrayList<user>();

        try {
            String req = "select * from `user` LIMIT ? OFFSET ? ";

            PreparedStatement s = cnx.prepareStatement(req);
            s.setInt(1, size);
            s.setInt(2, row);

            ResultSet rs = s.executeQuery();
            while (rs.next()) {

                user l = new user();
                l.setId(rs.getInt("id"));
                l.setEmail(rs.getString("email"));
                l.setRoles(rs.getString("roles"));
                l.setIsVerfied(rs.getBoolean("is_verified"));
                l.setFirstname(rs.getString("firstname"));
                l.setLastname(rs.getString("lastname"));
                l.setPhonenumber(rs.getInt("phonenumber"));
                l.setProfile_picture(rs.getString("profile_picture"));
                l.setIsbanned(rs.getBoolean("isbanned"));
                l.setCountry_code(rs.getString("country_code"));

                li.add(l);
            }
//            for (int i = 0; i < li.size(); i++) {
//                System.out.println("*********");
//                System.out.println(li.get(i).getEmail());
//                System.out.println(li.get(i).getFirstname());
//
//            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return li;

    }

    @Override
    public int getRowCount() {
        int count = 0;
        try {
            String req = "SELECT COUNT(*) FROM `user`";
            PreparedStatement ps = cnx.prepareStatement(req);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                count = rs.getInt(1);
            }
        } catch (SQLException e) {
        }
        return count;
    }

    @Override
    public void deleteuser(int id) {

        try {
            String req = "delete from user where id=?";
            PreparedStatement st = connexion.getInstance().getCnx().prepareStatement(req);

            st.setInt(1, id);
            st.executeUpdate();
        } catch (SQLException ex) {
            System.out.println("user delete error");
            ex.printStackTrace();
        }
    }

    @Override
    public void updateuser(user u) {

        String req = "UPDATE `user` SET `password`='" + u.getPassword() + "',`email`='" + u.getEmail() + "',`roles`='" + u.getRoles() + "',`is_verified`=0,`firstname`='" + u.getFirstname() + "',`lastname`='" + u.getLastname() + "',`phonenumber`='" + u.getPhonenumber() + "',`profile_picture`='" + u.getProfile_picture() + "',`isbanned`=0,`country_code`='" + u.getCountry_code() + "' WHERE id='" + session.getUser().getId() + "'";
        try {
            PreparedStatement st = cnx.prepareStatement(req);

            st.executeUpdate();

        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println("error");
        }
    }

    @Override
    public void updateuserinfo(user u) {

        String req = "UPDATE `user` SET `email`='" + u.getEmail() + "',`firstname`='" + u.getFirstname() + "',`lastname`='" + u.getLastname() + "',`phonenumber`='" + u.getPhonenumber() + "' WHERE id='" + session.getUser().getId() + "'";
        try {
            PreparedStatement st = cnx.prepareStatement(req);

            st.executeUpdate();

        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println("error");
        }
    }

    @Override
    public void updateProfilePicture(user u) {

        String req = "UPDATE `user` SET `profile_picture`='" + u.getProfile_picture() + "' WHERE id='" + session.getUser().getId() + "'";
        try {
            PreparedStatement st = cnx.prepareStatement(req);

            st.executeUpdate();

        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println("error");
        }
    }

    @Override
    public user findById(int id) {
        user u = null;
        try {
            String req = "select * from user where id=? ";
            PreparedStatement st = cnx.prepareStatement(req);
            st.setInt(1, id);
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                u = new user(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getBoolean(5),
                        rs.getString(6),
                        rs.getString(7),
                        rs.getInt(8),
                        rs.getString(9),
                        rs.getBoolean(10),
                        rs.getString(11));
            }
        } catch (Exception a) {
            a.printStackTrace();
        }
        return u;
    }

    @Override
    public user findByEmail(String email) {
        user u = null;
        try {
            String req = "select * from user where email=? ";
            PreparedStatement st = cnx.prepareStatement(req);
            st.setString(1, email);
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                u = new user(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getBoolean(5),
                        rs.getString(6),
                        rs.getString(7),
                        rs.getInt(8),
                        rs.getString(9),
                        rs.getBoolean(10),
                        rs.getString(11));
            }
            System.out.println(u.getFirstname());
        } catch (Exception a) {
            a.printStackTrace();
        }
        return u;
    }

    @Override
    public String getRole() {
        String role = null;
        System.out.println(session.getUser().getRoles());
        if (session.getUser().getRoles().contains("ADMIN")) {
            role = "admin";
            System.out.println("admin");
            return role;
        }

        if (session.getUser().getRoles().contains("CLIENT")) {
            role = "coach";
            System.out.println("client");
            return role;
        }

        if (session.getUser().getRoles().contains("COACH")) {
            role = "coach";
            System.out.println("coach");
            return role;
        }

        return role;
    }

    @Override
    public int countBannedAccounts() {
        int i = 0;
        try {
            String req = "SELECT COUNT(*) FROM `user` WHERE isbanned=1 ";
            Statement ps = cnx.createStatement();
            ResultSet result = ps.executeQuery(req);
            result.next();
            i = result.getInt(1);
        } catch (SQLException e) {
        }
        return i;
    }

    @Override
    public HashMap<String, Integer> getStatTypeOfUsers() {
        List<user> list = this.getalluser();
        HashMap<String, Integer> stat = new HashMap<>();
        stat.put("Admins", 0);
        stat.put("Clients", 0);
        stat.put("Coaches", 0);

        list.forEach((user) -> {
            if (user.getRoles().contains("ADMIN")) {
                stat.put("Admins", stat.get("Admins") + 1);
            } else if (user.getRoles().contains("Coach")) {
                stat.put("Coaches", stat.get("Coaches") + 1);
            } else if (user.getRoles().contains("CLIENT")) {
                stat.put("Clients", stat.get("Clients") + 1);
            }
        });
        return stat;
    }

    @Override
    public boolean login(user user) {
        boolean status = false;
        try {
            String req = "SELECT * FROM `user` WHERE email=?";
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setString(1, user.getEmail());
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                if (BCrypt.checkpw(user.getPassword(), rs.getString("password"))) {
                    status = true;
                    user = this.findById(rs.getInt("id"));
                    session.setUser(user);
                    System.out.println("connected");
                } else {
                    status = false;
                    System.out.println("invalid credentials");
                }
            }

        } catch (SQLException e) {
            System.err.println("wrong");
        }
        return status;
    }

    @Override
    public boolean verifyEmailExistance(String email) {
        boolean isFound = false;

        try {

            String req = "select *  from user where email='" + email + "'";
            PreparedStatement ps = cnx.prepareStatement(req);
            ResultSet result = ps.executeQuery();
            while (result.next()) {
                isFound = true;

            }

        } catch (SQLException e) {
        }

        return isFound;
    }

    @Override
    public void banUser(int id) {

        try {
            String req = "update user set isbanned=1 where id='" + id + "'";
            PreparedStatement ps = cnx.prepareStatement(req);

            ps.executeUpdate(req);
            System.out.println("user banned");
            this.logout();

        } catch (SQLException e) {
        }

    }

    @Override
    public void unbanUser(int id) {

        try {
            String req = "update user set isbanned=0 where id='" + id + "'";
            PreparedStatement ps = cnx.prepareStatement(req);

            ps.executeUpdate(req);
            System.out.println("user unbanned");

        } catch (SQLException e) {
        }

    }

    @Override
    public void logout() {

        session.setUser(null);
        System.out.println("logout successful");

    }

    @Override
    public boolean isConnected() {
        boolean connected = false;
        if (session.getUser() != null) {
            connected = true;

        }
        return connected;
    }

    @Override
    public void updatePassword(String oldPassword, String password) {

        if (passisMatched(oldPassword)) {

            try {

                String passwordEnc = BCrypt.hashpw(password, BCrypt.gensalt());
                String req = "update user set password='" + passwordEnc + "' where id='" + session.getUser().getId() + "'";

                PreparedStatement ps = cnx.prepareStatement(req);
                ps.executeUpdate();
                System.out.println("password updated");
            } catch (Exception ex) {
                System.out.println("error");
            }
        } else {
            System.err.println("passdontmatch");
        }
    }

    @Override
    public List<user> searchUser(String search) {
        List<user> li = new ArrayList();

        try {
            String req = "SELECT * FROM user WHERE email LIKE '%" + search + "%'";
            Statement s = cnx.createStatement();

            ResultSet rs = s.executeQuery(req);
            while (rs.next()) {
                user l = new user();
                l.setId(rs.getInt("id"));
                l.setEmail(rs.getString("email"));
                l.setRoles(rs.getString("roles"));
                l.setIsVerfied(rs.getBoolean("is_verified"));
                l.setFirstname(rs.getString("firstname"));
                l.setLastname(rs.getString("lastname"));
                l.setPhonenumber(rs.getInt("phonenumber"));
                l.setProfile_picture(rs.getString("profile_picture"));
                l.setIsbanned(rs.getBoolean("isbanned"));
                l.setCountry_code(rs.getString("country_code"));

                li.add(l);
            }
            for (int i = 0; i < li.size(); i++) {
                System.out.println("*********");
                System.out.println(li.get(i).getEmail());
                System.out.println(li.get(i).getFirstname());
            }

        } catch (SQLException ex) {
        }

        return li;

    }

    @Override
    public List<user> orderUser() {
        List<user> li = new ArrayList();

        try {
            String req = "select * from user order by firstname desc";
            Statement s = cnx.createStatement();

            ResultSet rs = s.executeQuery(req);
            while (rs.next()) {
                user l = new user();
                l.setId(rs.getInt("id"));
                l.setEmail(rs.getString("email"));
                l.setRoles(rs.getString("roles"));
                l.setIsVerfied(rs.getBoolean("is_verified"));
                l.setFirstname(rs.getString("firstname"));
                l.setLastname(rs.getString("lastname"));
                l.setPhonenumber(rs.getInt("phonenumber"));
                l.setProfile_picture(rs.getString("profile_picture"));
                l.setIsbanned(rs.getBoolean("isbanned"));
                l.setCountry_code(rs.getString("country_code"));

                li.add(l);
            }
            for (int i = 0; i < li.size(); i++) {
                System.out.println("*********");
                System.out.println(li.get(i).getEmail());
                System.out.println(li.get(i).getFirstname());
            }

        } catch (SQLException ex) {
        }

        return li;

    }

    public int countuserbyRole(String countitem) {
        int nb = 0;
        try {
            String req = "select count(*) from user where roles LIKE '%" + countitem + "%'";
            Statement s = cnx.createStatement();

            ResultSet rs = s.executeQuery(req);
            rs.next();
            nb = rs.getInt(1);

            System.out.println("*********");
            System.out.println(nb);

        } catch (SQLException ex) {
        }

        return nb;

    }

    @Override
    public boolean passisMatched(String password) {
        boolean passMatched = false;
        if (BCrypt.checkpw(password, session.getUser().getPassword())) {
            passMatched = true;

        }
        return passMatched;

    }

    @Override
    public boolean sendresetCode(String email) {
        boolean isSent = false;

        String subject = "Réinitialiser votre mot de passe";
        String message = "  <!DOCTYPE HTML PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional //EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">\n"
                + "<html xmlns=\"http://www.w3.org/1999/xhtml\" xmlns:v=\"urn:schemas-microsoft-com:vml\" xmlns:o=\"urn:schemas-microsoft-com:office:office\">\n"
                + "<head>\n"
                + "    <!--[if gte mso 9]>\n"
                + "    <xml>\n"
                + "        <o:OfficeDocumentSettings>\n"
                + "            <o:AllowPNG/>\n"
                + "            <o:PixelsPerInch>96</o:PixelsPerInch>\n"
                + "        </o:OfficeDocumentSettings>\n"
                + "    </xml>\n"
                + "    <![endif]-->\n"
                + "    <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">\n"
                + "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n"
                + "    <meta name=\"x-apple-disable-message-reformatting\">\n"
                + "    <!--[if !mso]><!--><meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\"><!--<![endif]-->\n"
                + "    <title></title>\n"
                + "\n"
                + "    <style type=\"text/css\">\n"
                + "        table, td { color: #000000; } a { color: #161a39; text-decoration: underline; }\n"
                + "        @media only screen and (min-width: 620px) {\n"
                + "            .u-row {\n"
                + "                width: 600px !important;\n"
                + "            }\n"
                + "            .u-row .u-col {\n"
                + "                vertical-align: top;\n"
                + "            }\n"
                + "            .u-row .u-col-50 {\n"
                + "                width: 300px !important;\n"
                + "            }\n"
                + "            .u-row .u-col-100 {\n"
                + "                width: 600px !important;\n"
                + "            }\n"
                + "        }\n"
                + "        @media (max-width: 620px) {\n"
                + "            .u-row-container {\n"
                + "                max-width: 100% !important;\n"
                + "                padding-left: 0px !important;\n"
                + "                padding-right: 0px !important;\n"
                + "            }\n"
                + "            .u-row .u-col {\n"
                + "                min-width: 320px !important;\n"
                + "                max-width: 100% !important;\n"
                + "                display: block !important;\n"
                + "            }\n"
                + "            .u-row {\n"
                + "                width: calc(100% - 40px) !important;\n"
                + "            }\n"
                + "            .u-col {\n"
                + "                width: 100% !important;\n"
                + "            }\n"
                + "            .u-col > div {\n"
                + "                margin: 0 auto;\n"
                + "            }\n"
                + "        }\n"
                + "        body {\n"
                + "            margin: 0;\n"
                + "            padding: 0;\n"
                + "        }\n"
                + "        table,\n"
                + "        tr,\n"
                + "        td {\n"
                + "            vertical-align: top;\n"
                + "            border-collapse: collapse;\n"
                + "        }\n"
                + "        p {\n"
                + "            margin: 0;\n"
                + "        }\n"
                + "        .ie-container table,\n"
                + "        .mso-container table {\n"
                + "            table-layout: fixed;\n"
                + "        }\n"
                + "        * {\n"
                + "            line-height: inherit;\n"
                + "        }\n"
                + "        a[x-apple-data-detectors='true'] {\n"
                + "            color: inherit !important;\n"
                + "            text-decoration: none !important;\n"
                + "        }\n"
                + "    </style>\n"
                + "\n"
                + "\n"
                + "\n"
                + "    <!--[if !mso]><!--><link href=\"https://fonts.googleapis.com/css?family=Lato:400,700&display=swap\" rel=\"stylesheet\" type=\"text/css\"><!--<![endif]-->\n"
                + "\n"
                + "</head>\n"
                + "\n"
                + "<body class=\"clean-body u_body\" style=\"margin: 0;padding: 0;-webkit-text-size-adjust: 100%;background-color: #f9f9f9;color: #000000\">\n"
                + "<!--[if IE]><div class=\"ie-container\"><![endif]-->\n"
                + "<!--[if mso]><div class=\"mso-container\"><![endif]-->\n"
                + "<table style=\"border-collapse: collapse;table-layout: fixed;border-spacing: 0;mso-table-lspace: 0pt;mso-table-rspace: 0pt;vertical-align: top;min-width: 320px;Margin: 0 auto;background-color: #f9f9f9;width:100%\" cellpadding=\"0\" cellspacing=\"0\">\n"
                + "    <tbody>\n"
                + "    <tr style=\"vertical-align: top\">\n"
                + "        <td style=\"word-break: break-word;border-collapse: collapse !important;vertical-align: top\">\n"
                + "            <!--[if (mso)|(IE)]><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr><td align=\"center\" style=\"background-color: #f9f9f9;\"><![endif]-->\n"
                + "\n"
                + "\n"
                + "            <div class=\"u-row-container\" style=\"padding: 0px;background-color: #f9f9f9\">\n"
                + "                <div class=\"u-row\" style=\"Margin: 0 auto;min-width: 320px;max-width: 600px;overflow-wrap: break-word;word-wrap: break-word;word-break: break-word;background-color: #f9f9f9;\">\n"
                + "                    <div style=\"border-collapse: collapse;display: table;width: 100%;background-color: transparent;\">\n"
                + "                        <!--[if (mso)|(IE)]><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr><td style=\"padding: 0px;background-color: #f9f9f9;\" align=\"center\"><table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"width:600px;\"><tr style=\"background-color: #f9f9f9;\"><![endif]-->\n"
                + "\n"
                + "                        <!--[if (mso)|(IE)]><td align=\"center\" width=\"600\" style=\"width: 600px;padding: 0px;border-top: 0px solid transparent;border-left: 0px solid transparent;border-right: 0px solid transparent;border-bottom: 0px solid transparent;\" valign=\"top\"><![endif]-->\n"
                + "                        <div class=\"u-col u-col-100\" style=\"max-width: 320px;min-width: 600px;display: table-cell;vertical-align: top;\">\n"
                + "                            <div style=\"width: 100% !important;\">\n"
                + "                                <!--[if (!mso)&(!IE)]><!--><div style=\"padding: 0px;border-top: 0px solid transparent;border-left: 0px solid transparent;border-right: 0px solid transparent;border-bottom: 0px solid transparent;\"><!--<![endif]-->\n"
                + "\n"
                + "                                    <table style=\"font-family:'Lato',sans-serif;\" role=\"presentation\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" border=\"0\">\n"
                + "                                        <tbody>\n"
                + "                                        <tr>\n"
                + "                                            <td style=\"overflow-wrap:break-word;word-break:break-word;padding:15px;font-family:'Lato',sans-serif;\" align=\"left\">\n"
                + "\n"
                + "                                                <table height=\"0px\" align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" style=\"border-collapse: collapse;table-layout: fixed;border-spacing: 0;mso-table-lspace: 0pt;mso-table-rspace: 0pt;vertical-align: top;border-top: 1px solid #f9f9f9;-ms-text-size-adjust: 100%;-webkit-text-size-adjust: 100%\">\n"
                + "                                                    <tbody>\n"
                + "                                                    <tr style=\"vertical-align: top\">\n"
                + "                                                        <td style=\"word-break: break-word;border-collapse: collapse !important;vertical-align: top;font-size: 0px;line-height: 0px;mso-line-height-rule: exactly;-ms-text-size-adjust: 100%;-webkit-text-size-adjust: 100%\">\n"
                + "                                                            <span>&#160;</span>\n"
                + "                                                        </td>\n"
                + "                                                    </tr>\n"
                + "                                                    </tbody>\n"
                + "                                                </table>\n"
                + "\n"
                + "                                            </td>\n"
                + "                                        </tr>\n"
                + "                                        </tbody>\n"
                + "                                    </table>\n"
                + "\n"
                + "                                    <!--[if (!mso)&(!IE)]><!--></div><!--<![endif]-->\n"
                + "                            </div>\n"
                + "                        </div>\n"
                + "                        <!--[if (mso)|(IE)]></td><![endif]-->\n"
                + "                        <!--[if (mso)|(IE)]></tr></table></td></tr></table><![endif]-->\n"
                + "                    </div>\n"
                + "                </div>\n"
                + "            </div>\n"
                + "\n"
                + "\n"
                + "\n"
                + "            <div class=\"u-row-container\" style=\"padding: 0px;background-color: transparent\">\n"
                + "                <div class=\"u-row\" style=\"Margin: 0 auto;min-width: 320px;max-width: 600px;overflow-wrap: break-word;word-wrap: break-word;word-break: break-word;background-color: #ffffff;\">\n"
                + "                    <div style=\"border-collapse: collapse;display: table;width: 100%;background-color: transparent;\">\n"
                + "                        <!--[if (mso)|(IE)]><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr><td style=\"padding: 0px;background-color: transparent;\" align=\"center\"><table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"width:600px;\"><tr style=\"background-color: #ffffff;\"><![endif]-->\n"
                + "\n"
                + "                        <!--[if (mso)|(IE)]><td align=\"center\" width=\"600\" style=\"width: 600px;padding: 0px;border-top: 0px solid transparent;border-left: 0px solid transparent;border-right: 0px solid transparent;border-bottom: 0px solid transparent;\" valign=\"top\"><![endif]-->\n"
                + "                        <div class=\"u-col u-col-100\" style=\"max-width: 320px;min-width: 600px;display: table-cell;vertical-align: top;\">\n"
                + "                            <div style=\"width: 100% !important;\">\n"
                + "                                <!--[if (!mso)&(!IE)]><!--><div style=\"padding: 0px;border-top: 0px solid transparent;border-left: 0px solid transparent;border-right: 0px solid transparent;border-bottom: 0px solid transparent;\"><!--<![endif]-->\n"
                + "\n"
                + "                                    <table style=\"font-family:'Lato',sans-serif;\" role=\"presentation\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" border=\"0\">\n"
                + "                                        <tbody>\n"
                + "                                        <tr>\n"
                + "                                            <td style=\"overflow-wrap:break-word;word-break:break-word;padding:25px 10px;font-family:'Lato',sans-serif;\" align=\"left\">\n"
                + "\n"
                + "                                                <table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\n"
                + "                                                    <tr>\n"
                + "                                                        <td style=\"padding-right: 0px;padding-left: 0px;\" align=\"center\">\n"
                + "\n"
                + "                                                            <img align=\"center\" border=\"0\" src=\"https://s3.amazonaws.com/unroll-images-production/projects%2F0%2F1646598636496-App+logo.png\" alt=\"Image\" title=\"Image\" style=\"outline: none;text-decoration: none;-ms-interpolation-mode: bicubic;clear: both;display: inline-block !important;border: none;height: auto;float: none;width: 37%;max-width: 214.6px;\" width=\"214.6\"/>\n"
                + "\n"
                + "                                                        </td>\n"
                + "                                                    </tr>\n"
                + "                                                </table>\n"
                + "\n"
                + "                                            </td>\n"
                + "                                        </tr>\n"
                + "                                        </tbody>\n"
                + "                                    </table>\n"
                + "\n"
                + "                                    <!--[if (!mso)&(!IE)]><!--></div><!--<![endif]-->\n"
                + "                            </div>\n"
                + "                        </div>\n"
                + "                        <!--[if (mso)|(IE)]></td><![endif]-->\n"
                + "                        <!--[if (mso)|(IE)]></tr></table></td></tr></table><![endif]-->\n"
                + "                    </div>\n"
                + "                </div>\n"
                + "            </div>\n"
                + "\n"
                + "\n"
                + "\n"
                + "            <div class=\"u-row-container\" style=\"padding: 0px;background-color: transparent\">\n"
                + "                <div class=\"u-row\" style=\"Margin: 0 auto;min-width: 320px;max-width: 600px;overflow-wrap: break-word;word-wrap: break-word;word-break: break-word;background-color: #161a39;\">\n"
                + "                    <div style=\"border-collapse: collapse;display: table;width: 100%;background-color: transparent;\">\n"
                + "                        <!--[if (mso)|(IE)]><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr><td style=\"padding: 0px;background-color: transparent;\" align=\"center\"><table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"width:600px;\"><tr style=\"background-color: #161a39;\"><![endif]-->\n"
                + "\n"
                + "                        <!--[if (mso)|(IE)]><td align=\"center\" width=\"600\" style=\"width: 600px;padding: 0px;border-top: 0px solid transparent;border-left: 0px solid transparent;border-right: 0px solid transparent;border-bottom: 0px solid transparent;\" valign=\"top\"><![endif]-->\n"
                + "                        <div class=\"u-col u-col-100\" style=\"max-width: 320px;min-width: 600px;display: table-cell;vertical-align: top;\">\n"
                + "                            <div style=\"width: 100% !important;\">\n"
                + "                                <!--[if (!mso)&(!IE)]><!--><div style=\"padding: 0px;border-top: 0px solid transparent;border-left: 0px solid transparent;border-right: 0px solid transparent;border-bottom: 0px solid transparent;\"><!--<![endif]-->\n"
                + "\n"
                + "                                    <table style=\"font-family:'Lato',sans-serif;\" role=\"presentation\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" border=\"0\">\n"
                + "                                        <tbody>\n"
                + "                                        <tr>\n"
                + "                                            <td style=\"overflow-wrap:break-word;word-break:break-word;padding:35px 10px 10px;font-family:'Lato',sans-serif;\" align=\"left\">\n"
                + "\n"
                + "                                                <table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\n"
                + "                                                    <tr>\n"
                + "                                                        <td style=\"padding-right: 0px;padding-left: 0px;\" align=\"center\">\n"
                + "\n"
                + "                                                            <img align=\"center\" border=\"0\" src=\"https://cdn.templates.unlayer.com/assets/1593141680866-reset.png\" alt=\"Image\" title=\"Image\" style=\"outline: none;text-decoration: none;-ms-interpolation-mode: bicubic;clear: both;display: inline-block !important;border: none;height: auto;float: none;width: 10%;max-width: 58px;\" width=\"58\"/>\n"
                + "\n"
                + "                                                        </td>\n"
                + "                                                    </tr>\n"
                + "                                                </table>\n"
                + "\n"
                + "                                            </td>\n"
                + "                                        </tr>\n"
                + "                                        </tbody>\n"
                + "                                    </table>\n"
                + "\n"
                + "                                    <table style=\"font-family:'Lato',sans-serif;\" role=\"presentation\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" border=\"0\">\n"
                + "                                        <tbody>\n"
                + "                                        <tr>\n"
                + "                                            <td style=\"overflow-wrap:break-word;word-break:break-word;padding:0px 10px 30px;font-family:'Lato',sans-serif;\" align=\"left\">\n"
                + "\n"
                + "                                                <div style=\"line-height: 140%; text-align: left; word-wrap: break-word;\">\n"
                + "                                                    <p style=\"font-size: 14px; line-height: 140%; text-align: center;\"><span style=\"font-size: 28px; line-height: 39.2px; color: #ffffff; font-family: Lato, sans-serif;\">Veuillez réinitialiser votre mot de passe</span></p>\n"
                + "                                                </div>\n"
                + "\n"
                + "                                            </td>\n"
                + "                                        </tr>\n"
                + "                                        </tbody>\n"
                + "                                    </table>\n"
                + "\n"
                + "                                    <!--[if (!mso)&(!IE)]><!--></div><!--<![endif]-->\n"
                + "                            </div>\n"
                + "                        </div>\n"
                + "                        <!--[if (mso)|(IE)]></td><![endif]-->\n"
                + "                        <!--[if (mso)|(IE)]></tr></table></td></tr></table><![endif]-->\n"
                + "                    </div>\n"
                + "                </div>\n"
                + "            </div>\n"
                + "\n"
                + "\n"
                + "\n"
                + "            <div class=\"u-row-container\" style=\"padding: 0px;background-color: transparent\">\n"
                + "                <div class=\"u-row\" style=\"Margin: 0 auto;min-width: 320px;max-width: 600px;overflow-wrap: break-word;word-wrap: break-word;word-break: break-word;background-color: #ffffff;\">\n"
                + "                    <div style=\"border-collapse: collapse;display: table;width: 100%;background-color: transparent;\">\n"
                + "                        <!--[if (mso)|(IE)]><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr><td style=\"padding: 0px;background-color: transparent;\" align=\"center\"><table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"width:600px;\"><tr style=\"background-color: #ffffff;\"><![endif]-->\n"
                + "\n"
                + "                        <!--[if (mso)|(IE)]><td align=\"center\" width=\"600\" style=\"width: 600px;padding: 0px;border-top: 0px solid transparent;border-left: 0px solid transparent;border-right: 0px solid transparent;border-bottom: 0px solid transparent;\" valign=\"top\"><![endif]-->\n"
                + "                        <div class=\"u-col u-col-100\" style=\"max-width: 320px;min-width: 600px;display: table-cell;vertical-align: top;\">\n"
                + "                            <div style=\"width: 100% !important;\">\n"
                + "                                <!--[if (!mso)&(!IE)]><!--><div style=\"padding: 0px;border-top: 0px solid transparent;border-left: 0px solid transparent;border-right: 0px solid transparent;border-bottom: 0px solid transparent;\"><!--<![endif]-->\n"
                + "\n"
                + "                                    <table style=\"font-family:'Lato',sans-serif;\" role=\"presentation\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" border=\"0\">\n"
                + "                                        <tbody>\n"
                + "                                        <tr>\n"
                + "                                            <td style=\"overflow-wrap:break-word;word-break:break-word;padding:40px 40px 30px;font-family:'Lato',sans-serif;\" align=\"left\">\n"
                + "\n"
                + "                                                <div style=\"line-height: 140%; text-align: left; word-wrap: break-word;\">\n"
                + "                                                    <p style=\"font-size: 14px; line-height: 140%;\"><span style=\"font-size: 18px; line-height: 25.2px; color: #666666;\">Bonjour,</span></p>\n"
                + "                                                    <p style=\"font-size: 14px; line-height: 140%;\">&nbsp;</p>\n"
                + "                                                    <p style=\"font-size: 14px; line-height: 140%;\"><span style=\"font-size: 18px; line-height: 25.2px; color: #666666;\">Nous vous avons envoyé cet e-mail en réponse à votre demande de réinitialisation de votre mot de passe.</span></p>\n"
                + "                                                    <p style=\"font-size: 14px; line-height: 140%;\">&nbsp;</p>\n"
                + "                                                    <p style=\"font-size: 14px; line-height: 140%;\"><span style=\"font-size: 18px; line-height: 25.2px; color: #666666;\">Pour réinitialiser votre mot de passe, veuillez taper le code ci-dessous: </span></p>\n"
                + "                                                </div>\n"
                + "\n"
                + "                                            </td>\n"
                + "                                        </tr>\n"
                + "                                        </tbody>\n"
                + "                                    </table>\n"
                + "\n"
                + "                                    <table style=\"font-family:'Lato',sans-serif;\" role=\"presentation\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" border=\"0\">\n"
                + "                                        <tbody>\n"
                + "                                        <tr>\n"
                + "                                            <td style=\"overflow-wrap:break-word;word-break:break-word;padding:0px 40px;font-family:'Lato',sans-serif;\" align=\"left\">\n"
                + "\n"
                + "                                                <div align=\"left\">\n"
                + "                                                    <!--[if mso]><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-spacing: 0; border-collapse: collapse; mso-table-lspace:0pt; mso-table-rspace:0pt;font-family:'Lato',sans-serif;\"><tr><td style=\"font-family:'Lato',sans-serif;\" align=\"left\"><v:roundrect xmlns:v=\"urn:schemas-microsoft-com:vml\" xmlns:w=\"urn:schemas-microsoft-com:office:word\" href=\"\" style=\"height:51px; v-text-anchor:middle; width:205px;\" arcsize=\"2%\" stroke=\"f\" fillcolor=\"#18163a\"><w:anchorlock/><center style=\"color:#FFFFFF;font-family:'Lato',sans-serif;\"><![endif]-->\n"
                + "                                                    <a href=\"{{ url('app_reset_password', {token: resetToken.token}) }}\" target=\"_blank\" style=\"box-sizing: border-box;display: inline-block;font-family:'Lato',sans-serif;text-decoration: none;-webkit-text-size-adjust: none;text-align: center;color: #FFFFFF; background-color: #18163a; border-radius: 1px;-webkit-border-radius: 1px; -moz-border-radius: 1px; width:auto; max-width:100%; overflow-wrap: break-word; word-break: break-word; word-wrap:break-word; mso-border-alt: none;\">\n"
                + "                                                        <span style=\"display:block;padding:15px 40px;line-height:120%;\"><span style=\"font-size: 18px; line-height: 21.6px;\">   " + this.randomNumber() + " </span></span>\n"
                + "                                                    </a>\n"
                + "                                                    <!--[if mso]></center></v:roundrect></td></tr></table><![endif]-->\n"
                + "                                                </div>\n"
                + "\n"
                + "                                            </td>\n"
                + "                                        </tr>\n"
                + "                                        </tbody>\n"
                + "                                    </table>\n"
                + "\n"
                + "                                    <table style=\"font-family:'Lato',sans-serif;\" role=\"presentation\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" border=\"0\">\n"
                + "                                        <tbody>\n"
                + "                                        <tr>\n"
                + "                                            <td style=\"overflow-wrap:break-word;word-break:break-word;padding:40px 40px 30px;font-family:'Lato',sans-serif;\" align=\"left\">\n"
                + "\n"
                + "                                                <div style=\"line-height: 140%; text-align: left; word-wrap: break-word;\">\n"
                + "                                                    <p style=\"font-size: 14px; line-height: 140%;\"><span style=\"color: #888888; font-size: 14px; line-height: 19.6px;\"><em><span style=\"font-size: 16px; line-height: 22.4px;\">Veuillez ignorer cet e-mail si vous n'avez pas demandé de changement de mot de passe.</span></em></span><br /><span style=\"color: #888888; font-size: 14px; line-height: 19.6px;\"><em><span style=\"font-size: 16px; line-height: 22.4px;\">&nbsp;</span></em></span></p>\n"
                + "                                                </div>\n"
                + "\n"
                + "                                            </td>\n"
                + "                                        </tr>\n"
                + "                                        </tbody>\n"
                + "                                    </table>\n"
                + "\n"
                + "                                    <!--[if (!mso)&(!IE)]><!--></div><!--<![endif]-->\n"
                + "                            </div>\n"
                + "                        </div>\n"
                + "                        <!--[if (mso)|(IE)]></td><![endif]-->\n"
                + "                        <!--[if (mso)|(IE)]></tr></table></td></tr></table><![endif]-->\n"
                + "                    </div>\n"
                + "                </div>\n"
                + "            </div>\n"
                + "\n"
                + "\n"
                + "\n"
                + "            <div class=\"u-row-container\" style=\"padding: 0px;background-color: transparent\">\n"
                + "                <div class=\"u-row\" style=\"Margin: 0 auto;min-width: 320px;max-width: 600px;overflow-wrap: break-word;word-wrap: break-word;word-break: break-word;background-color: #18163a;\">\n"
                + "                    <div style=\"border-collapse: collapse;display: table;width: 100%;background-color: transparent;\">\n"
                + "                        <!--[if (mso)|(IE)]><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr><td style=\"padding: 0px;background-color: transparent;\" align=\"center\"><table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"width:600px;\"><tr style=\"background-color: #18163a;\"><![endif]-->\n"
                + "\n"
                + "                        <!--[if (mso)|(IE)]><td align=\"center\" width=\"300\" style=\"width: 300px;padding: 20px 20px 0px;border-top: 0px solid transparent;border-left: 0px solid transparent;border-right: 0px solid transparent;border-bottom: 0px solid transparent;\" valign=\"top\"><![endif]-->\n"
                + "                        <div class=\"u-col u-col-50\" style=\"max-width: 320px;min-width: 300px;display: table-cell;vertical-align: top;\">\n"
                + "                            <div style=\"width: 100% !important;\">\n"
                + "                                <!--[if (!mso)&(!IE)]><!--><div style=\"padding: 20px 20px 0px;border-top: 0px solid transparent;border-left: 0px solid transparent;border-right: 0px solid transparent;border-bottom: 0px solid transparent;\"><!--<![endif]-->\n"
                + "\n"
                + "                                    <table style=\"font-family:'Lato',sans-serif;\" role=\"presentation\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" border=\"0\">\n"
                + "                                        <tbody>\n"
                + "                                        <tr>\n"
                + "                                            <td style=\"overflow-wrap:break-word;word-break:break-word;padding:10px;font-family:'Lato',sans-serif;\" align=\"left\">\n"
                + "\n"
                + "                                                <div style=\"line-height: 140%; text-align: left; word-wrap: break-word;\">\n"
                + "                                                    <p style=\"font-size: 14px; line-height: 140%;\">&lrm;</p>\n"
                + "                                                </div>\n"
                + "\n"
                + "                                            </td>\n"
                + "                                        </tr>\n"
                + "                                        </tbody>\n"
                + "                                    </table>\n"
                + "\n"
                + "                                    <!--[if (!mso)&(!IE)]><!--></div><!--<![endif]-->\n"
                + "                            </div>\n"
                + "                        </div>\n"
                + "                        <!--[if (mso)|(IE)]></td><![endif]-->\n"
                + "                        <!--[if (mso)|(IE)]><td align=\"center\" width=\"300\" style=\"width: 300px;padding: 0px 0px 0px 20px;border-top: 0px solid transparent;border-left: 0px solid transparent;border-right: 0px solid transparent;border-bottom: 0px solid transparent;\" valign=\"top\"><![endif]-->\n"
                + "                        <div class=\"u-col u-col-50\" style=\"max-width: 320px;min-width: 300px;display: table-cell;vertical-align: top;\">\n"
                + "                            <div style=\"width: 100% !important;\">\n"
                + "                                <!--[if (!mso)&(!IE)]><!--><div style=\"padding: 0px 0px 0px 20px;border-top: 0px solid transparent;border-left: 0px solid transparent;border-right: 0px solid transparent;border-bottom: 0px solid transparent;\"><!--<![endif]-->\n"
                + "\n"
                + "\n"
                + "\n"
                + "                                    <table style=\"font-family:'Lato',sans-serif;\" role=\"presentation\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" border=\"0\">\n"
                + "                                        <tbody>\n"
                + "                                        <tr>\n"
                + "                                            <td style=\"overflow-wrap:break-word;word-break:break-word;padding:5px 10px 10px;font-family:'Lato',sans-serif;\" align=\"left\">\n"
                + "\n"
                + "                                                <div style=\"line-height: 140%; text-align: left; word-wrap: break-word;\">\n"
                + "                                                    <p style=\"line-height: 140%; font-size: 14px;\"><span style=\"font-size: 14px; line-height: 19.6px;\"><span style=\"color: #ecf0f1; font-size: 14px; line-height: 19.6px;\"><span style=\"line-height: 19.6px; font-size: 14px;\">Calometre &copy;&nbsp; Tous droits sont réservés</span></span></span></p>\n"
                + "                                                </div>\n"
                + "\n"
                + "                                            </td>\n"
                + "                                        </tr>\n"
                + "                                        </tbody>\n"
                + "                                    </table>\n"
                + "\n"
                + "                                    <!--[if (!mso)&(!IE)]><!--></div><!--<![endif]-->\n"
                + "                            </div>\n"
                + "                        </div>\n"
                + "                        <!--[if (mso)|(IE)]></td><![endif]-->\n"
                + "                        <!--[if (mso)|(IE)]></tr></table></td></tr></table><![endif]-->\n"
                + "                    </div>\n"
                + "                </div>\n"
                + "            </div>\n"
                + "\n"
                + "\n"
                + "\n"
                + "            <div class=\"u-row-container\" style=\"padding: 0px;background-color: #f9f9f9\">\n"
                + "                <div class=\"u-row\" style=\"Margin: 0 auto;min-width: 320px;max-width: 600px;overflow-wrap: break-word;word-wrap: break-word;word-break: break-word;background-color: #1c103b;\">\n"
                + "                    <div style=\"border-collapse: collapse;display: table;width: 100%;background-color: transparent;\">\n"
                + "                        <!--[if (mso)|(IE)]><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr><td style=\"padding: 0px;background-color: #f9f9f9;\" align=\"center\"><table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"width:600px;\"><tr style=\"background-color: #1c103b;\"><![endif]-->\n"
                + "\n"
                + "                        <!--[if (mso)|(IE)]><td align=\"center\" width=\"600\" style=\"width: 600px;padding: 0px;border-top: 0px solid transparent;border-left: 0px solid transparent;border-right: 0px solid transparent;border-bottom: 0px solid transparent;\" valign=\"top\"><![endif]-->\n"
                + "                        <div class=\"u-col u-col-100\" style=\"max-width: 320px;min-width: 600px;display: table-cell;vertical-align: top;\">\n"
                + "                            <div style=\"width: 100% !important;\">\n"
                + "                                <!--[if (!mso)&(!IE)]><!--><div style=\"padding: 0px;border-top: 0px solid transparent;border-left: 0px solid transparent;border-right: 0px solid transparent;border-bottom: 0px solid transparent;\"><!--<![endif]-->\n"
                + "\n"
                + "                                    <table style=\"font-family:'Lato',sans-serif;\" role=\"presentation\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" border=\"0\">\n"
                + "                                        <tbody>\n"
                + "                                        <tr>\n"
                + "                                            <td style=\"overflow-wrap:break-word;word-break:break-word;padding:15px;font-family:'Lato',sans-serif;\" align=\"left\">\n"
                + "\n"
                + "                                                <table height=\"0px\" align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" style=\"border-collapse: collapse;table-layout: fixed;border-spacing: 0;mso-table-lspace: 0pt;mso-table-rspace: 0pt;vertical-align: top;border-top: 1px solid #1c103b;-ms-text-size-adjust: 100%;-webkit-text-size-adjust: 100%\">\n"
                + "                                                    <tbody>\n"
                + "                                                    <tr style=\"vertical-align: top\">\n"
                + "                                                        <td style=\"word-break: break-word;border-collapse: collapse !important;vertical-align: top;font-size: 0px;line-height: 0px;mso-line-height-rule: exactly;-ms-text-size-adjust: 100%;-webkit-text-size-adjust: 100%\">\n"
                + "                                                            <span>&#160;</span>\n"
                + "                                                        </td>\n"
                + "                                                    </tr>\n"
                + "                                                    </tbody>\n"
                + "                                                </table>\n"
                + "\n"
                + "                                            </td>\n"
                + "                                        </tr>\n"
                + "                                        </tbody>\n"
                + "                                    </table>\n"
                + "\n"
                + "                                    <!--[if (!mso)&(!IE)]><!--></div><!--<![endif]-->\n"
                + "                            </div>\n"
                + "                        </div>\n"
                + "                        <!--[if (mso)|(IE)]></td><![endif]-->\n"
                + "                        <!--[if (mso)|(IE)]></tr></table></td></tr></table><![endif]-->\n"
                + "                    </div>\n"
                + "                </div>\n"
                + "            </div>\n"
                + "\n"
                + "\n"
                + "            <!--[if (mso)|(IE)]></td></tr></table><![endif]-->\n"
                + "        </td>\n"
                + "    </tr>\n"
                + "    </tbody>\n"
                + "</table>\n"
                + "<!--[if mso]></div><![endif]-->\n"
                + "<!--[if IE]></div><![endif]-->\n"
                + "</body>\n"
                + "\n"
                + "</html>";//+ this.randomNumber();

        try {
            sendEmailWithAttachments(email,
                    subject, message);
            System.out.println("Email sent.");
            isSent = true;
            System.out.println(isSent);
        } catch (Exception ex) {
            System.out.println("Could not send email.");
            ex.printStackTrace();
        }
        return isSent;

    }

    @Override
    public boolean resetcodeisMatched(int userinput) {
        boolean resetcodeMatched = false;

        if (userService.code == userinput) {
            System.out.println("true");
            resetcodeMatched = true;
        } else {
            System.err.println("wrong");
        }

        return resetcodeMatched;
    }

    @Override
    public boolean resetPassword(String email, String newPassword) {
        boolean reset = false;
        try {
            reset = true;
            String passwordEnc = BCrypt.hashpw(newPassword, BCrypt.gensalt());
            String req = "update user set password='" + passwordEnc + "' where email='" + email + "'";

            PreparedStatement ps = cnx.prepareStatement(req);
            ps.executeUpdate();
            System.out.println("password updated");

        } catch (SQLException e) {
        }
        return reset;
    }

    @Override
    public int randomNumber() {

        Random rand = new Random();

        userService.code = rand.nextInt(999999);

        userService.code += 1;

        System.out.println("random is " + userService.code);
        return userService.code;

    }

    @Override
    public String CreateCaptchaValue() {
        Random random = new Random();
        int length = 7 + (Math.abs(random.nextInt()) % 3);
        StringBuffer captchaStrBuffer = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int baseCharacterNumber = Math.abs(random.nextInt()) % 62;
            int characterNumber = 0;
            if (baseCharacterNumber < 26) {
                characterNumber = 65 + baseCharacterNumber;
            } else if (baseCharacterNumber < 52) {
                characterNumber = 97 + (baseCharacterNumber - 26);
            } else {
                characterNumber = 48 + (baseCharacterNumber - 52);
            }
            captchaStrBuffer.append((char) characterNumber);
        }
        return captchaStrBuffer.toString();

    }

    @Override

    public boolean isUserBanned(user user) {
        boolean isBanned = false;
        if (session.getUser().isIsbanned()) {
            isBanned = true;
            System.err.println("banned");
        }
        return isBanned;
    }
}
