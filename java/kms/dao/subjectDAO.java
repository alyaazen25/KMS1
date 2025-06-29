package kms.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import kms.connection.ConnectionManager;
import kms.model.subject;

public class subjectDAO {

    private static Connection con = null;
    private static PreparedStatement ps = null;
    private static ResultSet rs = null;
    private static String sql;

    // ✅ Add subject to database
    public static void addSubject(subject sub) {
        try {
            con = ConnectionManager.getConnection();
            sql = "INSERT INTO subject (subjectName, teacherId) VALUES (?, ?)";
            ps = con.prepareStatement(sql);
            ps.setString(1, sub.getSubjectName());
            ps.setInt(2, sub.getTeacherId());

            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("SQL ERROR: " + e.getMessage());
        } finally {
            try { if (ps != null) ps.close(); } catch (Exception e) {}
            try { if (con != null) con.close(); } catch (Exception e) {}
        }
    }

    // ✅ Retrieve all subjects
    public static List<subject> getAllSubjects() {
        List<subject> subjects = new ArrayList<>();
        try {
            con = ConnectionManager.getConnection();
            sql = "SELECT * FROM subject ORDER BY subjectName";
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                subject sub = new subject();
                sub.setSubjectId(rs.getInt("subjectId"));
                sub.setSubjectName(rs.getString("subjectName"));
                sub.setTeacherId(rs.getInt("teacherId"));
                subjects.add(sub);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return subjects;
    }

    // ✅ Update subject
    public static void updateSubject(subject sub) {
        try {
            con = ConnectionManager.getConnection();
            sql = "UPDATE subject SET subjectName = ?, teacherId = ? WHERE subjectId = ?";
            ps = con.prepareStatement(sql);
            ps.setString(1, sub.getSubjectName());
            ps.setInt(2, sub.getTeacherId());
            ps.setInt(3, sub.getSubjectId());

            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try { if (ps != null) ps.close(); } catch (Exception e) {}
            try { if (con != null) con.close(); } catch (Exception e) {}
        }
    }

    // ✅ Delete subject
    public static void deleteSubject(int subjectId) {
        try {
            con = ConnectionManager.getConnection();

            // 1. Delete from Enrollments first
            String deleteEnrollments = "DELETE FROM Enrollments WHERE subjectId = ?";
            ps = con.prepareStatement(deleteEnrollments);
            ps.setInt(1, subjectId);
            ps.executeUpdate();
            ps.close();

            // 2. Then delete the subject
            String deleteSubject = "DELETE FROM Subject WHERE subjectId = ?";
            ps = con.prepareStatement(deleteSubject);
            ps.setInt(1, subjectId);
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try { if (ps != null) ps.close(); } catch (Exception e) {}
            try { if (con != null) con.close(); } catch (Exception e) {}
        }
    }



    // ✅ Get subject by ID
    public static subject getSubjectById(int subjectId) {
        subject sub = new subject();

        try {
            con = ConnectionManager.getConnection();
            sql = "SELECT * FROM subject WHERE subjectId = ?";
            ps = con.prepareStatement(sql);
            ps.setInt(1, subjectId);
            rs = ps.executeQuery();

            if (rs.next()) {
                sub.setSubjectId(rs.getInt("subjectId"));
                sub.setSubjectName(rs.getString("subjectName"));
                sub.setTeacherId(rs.getInt("teacherId"));
                sub.setStudId(rs.getInt("studId")); // optional if present
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try { if (ps != null) ps.close(); } catch (Exception e) {}
            try { if (con != null) con.close(); } catch (Exception e) {}
        }

        return sub;
    }

    // ✅ Assign subject to student using studId
    public static void assignSubjectToStudent(int subjectId, int studId) {
        try {
            con = ConnectionManager.getConnection();
            sql = "INSERT INTO Enrollments (enrollId, studId, subjectId) VALUES (ENROLLMENTS_SEQ.NEXTVAL, ?, ?)";
            ps = con.prepareStatement(sql);
            ps.setInt(1, studId);
            ps.setInt(2, subjectId);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try { if (ps != null) ps.close(); } catch (Exception e) {}
            try { if (con != null) con.close(); } catch (Exception e) {}
        }
    }


    // ✅ Get all subjects by studId
    public static List<subject> getSubjectsByStudentId(int studId) {
        List<subject> subjects = new ArrayList<>();
        try {
            con = ConnectionManager.getConnection();
            // Join Enrollments and Subject table to get subjects registered by a student
            sql = "SELECT s.* FROM subject s JOIN Enrollments e ON s.subjectId = e.subjectId WHERE e.studId = ?";
            ps = con.prepareStatement(sql);
            ps.setInt(1, studId);
            rs = ps.executeQuery();

            while (rs.next()) {
                subject sub = new subject();
                sub.setSubjectId(rs.getInt("subjectId"));
                sub.setSubjectName(rs.getString("subjectName"));
                sub.setTeacherId(rs.getInt("teacherId"));
                subjects.add(sub);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try { if (ps != null) ps.close(); } catch (Exception e) {}
            try { if (con != null) con.close(); } catch (Exception e) {}
        }
        return subjects;
    }


}
