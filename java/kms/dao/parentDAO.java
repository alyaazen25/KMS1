package kms.dao;

import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.mindrot.jbcrypt.BCrypt;

import kms.connection.ConnectionManager;
import kms.model.parent;

public class parentDAO {

    private static Connection con = null;
    private static PreparedStatement ps = null;
    private static ResultSet rs = null;
    private static String sql;

    // insert parent
    public static void addParent(parent p) {
        try {
            con = ConnectionManager.getConnection();
            sql = "INSERT INTO parent( parentName, parentEmail, parentPass, parentPhone) VALUES ( ?, ?, ?, ?)";
            ps = con.prepareStatement(sql);

            String pass = p.getParentPass();
            if (!pass.startsWith("$2a$")) {
                pass = hashPassword(pass);
            }

            ps.setString(1, p.getParentName());
            ps.setString(2, p.getParentEmail());
            ps.setString(3, pass);
            ps.setString(4, p.getParentPhone());

            ps.executeUpdate();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // update parent
    public static void updateParent(parent p) {
        try {
            con = ConnectionManager.getConnection();

            if (p.getParentPhoto() != null) {
                sql = "UPDATE parent SET parentName=?, parentEmail=?, parentPass=?, parentPhone=?, parentPhoto=? WHERE parentId=?";
                ps = con.prepareStatement(sql);

                String pass = p.getParentPass();
                if (!pass.startsWith("$2a$")) {
                    pass = hashPassword(pass);
                }

                ps.setString(1, p.getParentName());
                ps.setString(2, p.getParentEmail());
                ps.setString(3, pass);
                ps.setString(4, p.getParentPhone());
                ps.setBytes(5, p.getParentPhoto());
                ps.setInt(6, p.getParentId());
            } else {
                sql = "UPDATE parent SET parentName=?, parentEmail=?, parentPass=?, parentPhone=? WHERE parentId=?";
                ps = con.prepareStatement(sql);

                String pass = p.getParentPass();
                if (!pass.startsWith("$2a$")) {
                    pass = hashPassword(pass);
                }

                ps.setString(1, p.getParentName());
                ps.setString(2, p.getParentEmail());
                ps.setString(3, pass);
                ps.setString(4, p.getParentPhone());
                ps.setInt(5, p.getParentId());
            }

            ps.executeUpdate();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // delete parent
    public static void deleteParent(int parentId) {
        try {
            con = ConnectionManager.getConnection();
            sql = "DELETE FROM parent WHERE parentId=?";
            ps = con.prepareStatement(sql);
            ps.setInt(1, parentId);
            ps.executeUpdate();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // get parent by ID
    public static parent getParent(int parentId) {
        parent p = new parent();
        try {
            con = ConnectionManager.getConnection();
            sql = "SELECT * FROM parent WHERE parentId = ?";
            ps = con.prepareStatement(sql);
            ps.setInt(1, parentId);
            rs = ps.executeQuery();

            if (rs.next()) {
                p.setParentId(rs.getInt("parentId"));
                p.setParentName(rs.getString("parentName"));
                p.setParentEmail(rs.getString("parentEmail"));
                p.setParentPass(rs.getString("parentPass"));
                p.setParentPhone(rs.getString("parentPhone"));
                Blob blobPhoto = rs.getBlob("parentPhoto");
                if (blobPhoto != null) {
                    p.setParentPhoto(blobPhoto.getBytes(1, (int) blobPhoto.length()));
                }
            }
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return p;
    }

    // get all parents
    public static List<parent> getAllParents() {
        List<parent> parents = new ArrayList<>();
        try {
            con = ConnectionManager.getConnection();
            sql = "SELECT * FROM parent ORDER BY parentId";
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                parent p = new parent();
                p.setParentId(rs.getInt("parentId"));
                p.setParentName(rs.getString("parentName"));
                p.setParentEmail(rs.getString("parentEmail"));
                p.setParentPass(rs.getString("parentPass"));
                p.setParentPhone(rs.getString("parentPhone"));
                Blob blobPhoto = rs.getBlob("parentPhoto");
                if (blobPhoto != null) {
                    p.setParentPhoto(blobPhoto.getBytes(1, (int) blobPhoto.length()));
                }
                parents.add(p);
            }
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return parents;
    }

    // validate login
    public static parent validate(String email, String password) {
        parent p = null;
        try {
            con = ConnectionManager.getConnection();
            sql = "SELECT * FROM parent WHERE parentEmail = ?";
            ps = con.prepareStatement(sql);
            ps.setString(1, email);
            rs = ps.executeQuery();

            if (rs.next()) {
                String hashedPass = rs.getString("parentPass");
                if (checkPassword(password, hashedPass)) {
                    p = new parent();
                    p.setParentId(rs.getInt("parentId"));
                    p.setParentName(rs.getString("parentName"));
                    p.setParentEmail(rs.getString("parentEmail"));
                    p.setParentPass(hashedPass);
                    p.setParentPhone(rs.getString("parentPhone"));
                }
            }
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return p;
    }

    // update password
    public static int updateParentPassword(String email, String password) {
        int rowCount = 0;
        try {
            con = ConnectionManager.getConnection();
            sql = "UPDATE parent SET parentPass = ? WHERE parentEmail = ?";
            ps = con.prepareStatement(sql);

            String hashedPass = password;
            if (!password.startsWith("$2a$")) {
                hashedPass = hashPassword(password);
            }

            ps.setString(1, hashedPass);
            ps.setString(2, email);
            rowCount = ps.executeUpdate();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rowCount;
    }

    // count all parents
    public static int countAll() {
        int count = 0;
        try {
            con = ConnectionManager.getConnection();
            sql = "SELECT COUNT(*) FROM parent";
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            if (rs.next()) {
                count = rs.getInt(1);
            }
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count;
    }

    // hash password
    private static String hashPassword(String plainTextPassword) {
        return BCrypt.hashpw(plainTextPassword, BCrypt.gensalt());
    }

    // check hashed password
    private static boolean checkPassword(String plainTextPassword, String hashedPassword) {
        return BCrypt.checkpw(plainTextPassword, hashedPassword);
    }
}