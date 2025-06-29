package kms.dao;

import java.sql.*;
import java.sql.Blob;
import java.util.*;
import org.mindrot.jbcrypt.BCrypt;

import kms.connection.ConnectionManager;
import kms.model.*;

public class teacherDAO {
    private static Connection con = null;
    private static PreparedStatement ps = null;
    private static ResultSet rs = null;
    private static String sql;

 // insert teacher
 	public static void addTeacher(teacher teach, String teacherType) {
 		try {
 			// call getConnection() method
 			con = ConnectionManager.getConnection();

 			// 3. create statement
 			sql = "INSERT INTO Teacher(teacherName, teacherEmail, teacherPass, teacherPhone, teacherRole, teacherType, adminId) VALUES (?, ?, ?, ?, ?, ?, ?)";

 			ps = con.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS); 
 			
 			String pass = teach.getTeacherPass();
 	        if (!pass.startsWith("$2a$")) {
 	            pass = hashPassword(pass);
 	        }

 			// get values from student object and set parameter values
 			ps.setString(1, teach.getTeacherName());
 			ps.setString(2, teach.getTeacherEmail());
 	        ps.setString(3, pass);
 			ps.setString(4, teach.getTeacherPhone());
 			ps.setString(5, teach.getTeacherRole());
 			ps.setString(6, teach.getTeacherType());

 			if (teach.getAdminId() != null) {
 				ps.setInt(7, teach.getAdminId());
 			} else {
 				ps.setNull(7, java.sql.Types.INTEGER); // ✅ safe for null
 			}

 			// 4. execute query
 			ps.executeUpdate();
 			
 			 // insert ke fulltime/parttime
 			PreparedStatement psGetId = con.prepareStatement("SELECT teacherId FROM teacher WHERE teacherEmail = ?");
 			psGetId.setString(1, teach.getTeacherEmail());
 			ResultSet rsId = psGetId.executeQuery();
 			if (rsId.next()) {
 				int teacherId = rsId.getInt("teacherId");
 				teach.setTeacherId(teacherId);
 			}
 			
 			if ("Admin".equalsIgnoreCase(teach.getTeacherRole())) {
 			    teacherType = "FullTime";
 			}


 			// Also insert into FullTime or PartTime table
 			if ("FullTime".equalsIgnoreCase(teacherType) && teach instanceof fullTime ft) {
 				sql = "INSERT INTO FullTime (teacherId, salary) VALUES (?, ?)";
 				try (PreparedStatement psFT = con.prepareStatement(sql)) {
 					psFT.setInt(1, ft.getTeacherId());
 					psFT.setNull(2, Types.DOUBLE);
 					psFT.executeUpdate();
 				}
 			} else if ("PartTime".equalsIgnoreCase(teacherType) && teach instanceof partTime pt) {
 				sql = "INSERT INTO PartTime (teacherId, contract) VALUES (?, ?)";
 				try (PreparedStatement psPT = con.prepareStatement(sql)) {
 					psPT.setInt(1, pt.getTeacherId());
 					psPT.setNull(2, Types.INTEGER);
 					psPT.executeUpdate();
 				}
 			}

 			// 5. close connection
 			con.close();
 		} catch (SQLException e) {
 			e.printStackTrace();
 		}
 	}

    // update teacher by id
    public static void updateTeacher(teacher teach) {
        try {
            con = ConnectionManager.getConnection();

            String pass = teach.getTeacherPass();
            if (!pass.startsWith("$2a$")) {
                pass = hashPassword(pass);
            }

            if (teach.getTeacherPhoto() != null) {
                sql = "UPDATE teacher SET teacherName=?, teacherEmail=?, teacherPass=?, teacherPhone=?, teacherRole=?, teacherType=?, adminId=?, teacherPhoto=? WHERE teacherId=?";
                ps = con.prepareStatement(sql);
                ps.setString(1, teach.getTeacherName());
                ps.setString(2, teach.getTeacherEmail());
                ps.setString(3, pass);
                ps.setString(4, teach.getTeacherPhone());
                ps.setString(5, teach.getTeacherRole());
                ps.setString(6, teach.getTeacherType());
                if (teach.getAdminId() != null && teach.getAdminId() != 0) {
                    ps.setInt(7, teach.getAdminId());
                } else {
                    ps.setNull(7, Types.INTEGER);
                }
                ps.setBytes(8, teach.getTeacherPhoto());
                ps.setInt(9, teach.getTeacherId());
            } else {
                sql = "UPDATE teacher SET teacherName=?, teacherEmail=?, teacherPass=?, teacherPhone=?, teacherRole=?, teacherType=?, adminId=? WHERE teacherId=?";
                ps = con.prepareStatement(sql);
                ps.setString(1, teach.getTeacherName());
                ps.setString(2, teach.getTeacherEmail());
                ps.setString(3, pass);
                ps.setString(4, teach.getTeacherPhone());
                ps.setString(5, teach.getTeacherRole());
                ps.setString(6, teach.getTeacherType());
                if (teach.getAdminId() != null && teach.getAdminId() != 0) {
                    ps.setInt(7, teach.getAdminId());
                } else {
                    ps.setNull(7, Types.INTEGER);
                }
                ps.setInt(8, teach.getTeacherId());
            }

            ps.executeUpdate();

            if ("FullTime".equalsIgnoreCase(teach.getTeacherType()) && teach instanceof fullTime ft) {
                sql = "UPDATE FullTime SET salary = ? WHERE teacherId = ?";
                try (PreparedStatement psFT = con.prepareStatement(sql)) {
                    if (ft.getSalary() != null) {
                        psFT.setDouble(1, ft.getSalary());
                    } else {
                        psFT.setNull(1, Types.DOUBLE);
                    }
                    psFT.setInt(2, ft.getTeacherId());
                    psFT.executeUpdate();
                }
            } else if ("PartTime".equalsIgnoreCase(teach.getTeacherType()) && teach instanceof partTime pt) {
                sql = "UPDATE PartTime SET contract = ? WHERE teacherId = ?";
                try (PreparedStatement psPT = con.prepareStatement(sql)) {
                    if (pt.getContract() != null) {
                        psPT.setInt(1, pt.getContract());
                    } else {
                        psPT.setNull(1, Types.INTEGER);
                    }
                    psPT.setInt(2, pt.getTeacherId());
                    psPT.executeUpdate();
                }
            }

            con.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void deleteTeacher(int teacherId) {
        try {
            con = ConnectionManager.getConnection();

            ps = con.prepareStatement("DELETE FROM FullTime WHERE teacherId=?");
            ps.setInt(1, teacherId);
            ps.executeUpdate();

            ps = con.prepareStatement("DELETE FROM PartTime WHERE teacherId=?");
            ps.setInt(1, teacherId);
            ps.executeUpdate();

            ps = con.prepareStatement("DELETE FROM teacher WHERE teacherId=?");
            ps.setInt(1, teacherId);
            ps.executeUpdate();

            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
 // get teacher by id
 	public static teacher getTeacher(int teacherId) {
 		teacher teach = new teacher();
 		try {
 			con = ConnectionManager.getConnection();
 			sql = "SELECT * FROM teacher WHERE teacherId = ?";
 			ps = con.prepareStatement(sql);
 			ps.setInt(1, teacherId);
 			rs = ps.executeQuery();

 			if (rs.next()) {

 				String teacherType = rs.getString("teacherType");


 	            if ("FullTime".equalsIgnoreCase(teacherType)) {
 	                fullTime ft = new fullTime();

 	                // Ambil salary dari table FullTime
 	                PreparedStatement ps2 = con.prepareStatement("SELECT salary FROM FullTime WHERE teacherId = ?");
 	                ps2.setInt(1, teacherId);
 	                ResultSet rs2 = ps2.executeQuery();
 	                if (rs2.next()) {
 	                    ft.setSalary(rs2.getDouble("salary"));
 	                }
 	                
 	                // Set semua common fields
 	                ft.setTeacherId(rs.getInt("teacherId"));
 	                ft.setTeacherName(rs.getString("teacherName"));
 	                ft.setTeacherEmail(rs.getString("teacherEmail"));
 	                ft.setTeacherPass(rs.getString("teacherPass"));
 	                ft.setTeacherPhone(rs.getString("teacherPhone"));
 	                ft.setTeacherRole(rs.getString("teacherRole"));
 	                ft.setTeacherType(rs.getString("teacherType"));
 	                ft.setAdminId(rs.getInt("adminId"));
 	                Blob blobPhoto = rs.getBlob("teacherPhoto");
 	                if (blobPhoto != null) {
 	                    ft.setTeacherPhoto(blobPhoto.getBytes(1, (int) blobPhoto.length()));
 	                }

 	                teach = ft;

 	            } else if ("PartTime".equalsIgnoreCase(teacherType)) {
 	                partTime pt = new partTime();

 	                // Ambil contract dari table PartTime
 	                PreparedStatement ps2 = con.prepareStatement("SELECT contract FROM PartTime WHERE teacherId = ?");
 	                ps2.setInt(1, teacherId);
 	                ResultSet rs2 = ps2.executeQuery();
 	                if (rs2.next()) {
 	                    pt.setContract(rs2.getInt("contract"));
 	                }
 	                
 	                // Set semua common fields
 	                pt.setTeacherId(rs.getInt("teacherId"));
 	                pt.setTeacherName(rs.getString("teacherName"));
 	                pt.setTeacherEmail(rs.getString("teacherEmail"));
 	                pt.setTeacherPass(rs.getString("teacherPass"));
 	                pt.setTeacherPhone(rs.getString("teacherPhone"));
 	                pt.setTeacherRole(rs.getString("teacherRole"));
 	                pt.setTeacherType(rs.getString("teacherType"));
 	                pt.setAdminId(rs.getInt("adminId"));
 	                Blob blobPhoto = rs.getBlob("teacherPhoto");
 	                if (blobPhoto != null) {
 	                    pt.setTeacherPhoto(blobPhoto.getBytes(1, (int) blobPhoto.length()));
 	                }


 	                teach = pt;

 	            } else {
 	                teach = new teacher();
 	            }

 				teach.setTeacherId(rs.getInt("teacherId"));
 				teach.setTeacherName(rs.getString("teacherName"));
 				teach.setTeacherEmail(rs.getString("teacherEmail"));
 				teach.setTeacherPass(rs.getString("teacherPass"));
 				teach.setTeacherPhone(rs.getString("teacherPhone"));
 				teach.setTeacherRole(rs.getString("teacherRole"));
 				teach.setTeacherType(rs.getString("teacherType"));
 				teach.setAdminId(rs.getInt("adminId"));
 				Blob blobPhoto = rs.getBlob("teacherPhoto");
 				if (blobPhoto != null) {
 				    teach.setTeacherPhoto(blobPhoto.getBytes(1, (int) blobPhoto.length()));
 				}
 			}
 			con.close();
 		} catch (SQLException e) {
 			e.printStackTrace();
 		}
 		return teach;
 	}

    // Additional from original DAO
    public static Map<Integer, String> getAllTeacherNames() {
        Map<Integer, String> map = new HashMap<>();
        try {
            con = ConnectionManager.getConnection();
            ps = con.prepareStatement("SELECT teacherId, teacherName FROM teacher");
            rs = ps.executeQuery();
            while (rs.next()) {
                map.put(rs.getInt("teacherId"), rs.getString("teacherName"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try { if (rs != null) rs.close(); } catch (Exception e) {}
            try { if (ps != null) ps.close(); } catch (Exception e) {}
            try { if (con != null) con.close(); } catch (Exception e) {}
        }
        return map;
    }

    public static int countExcludingAdmin(int adminId) {
        int count = 0;
        try {
            con = ConnectionManager.getConnection();
            sql = "SELECT COUNT(*) FROM Teacher WHERE adminId != ?";
            ps = con.prepareStatement(sql);
            ps.setInt(1, adminId);
            rs = ps.executeQuery();
            if (rs.next()) count = rs.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try { if (rs != null) rs.close(); } catch (Exception e) {}
            try { if (ps != null) ps.close(); } catch (Exception e) {}
            try { if (con != null) con.close(); } catch (Exception e) {}
        }
        return count;
    }

    public static String getTeacherNameByAdminId(int adminId) {
        String name = "";
        try {
            con = ConnectionManager.getConnection();
            sql = "SELECT teacherName FROM teacher WHERE adminId = ?";
            ps = con.prepareStatement(sql);
            ps.setInt(1, adminId);
            rs = ps.executeQuery();
            if (rs.next()) {
                name = rs.getString("teacherName");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try { if (rs != null) rs.close(); } catch (Exception e) {}
            try { if (ps != null) ps.close(); } catch (Exception e) {}
            try { if (con != null) con.close(); } catch (Exception e) {}
        }
        return name;
    }

    public static int updateTeacherPassword(String email, String password) {
        int rowCount = 0;
        try {
            con = ConnectionManager.getConnection();
            sql = "UPDATE teacher SET teacherPass= ? WHERE teacherEmail = ?";
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

    private static String hashPassword(String plainTextPassword) {
        return BCrypt.hashpw(plainTextPassword, BCrypt.gensalt());
    }

    private static boolean checkPassword(String plainTextPassword, String hashedPassword) {
        return BCrypt.checkpw(plainTextPassword, hashedPassword);
    }

    public static teacher validate(String email, String password) {
        teacher t = null;
        try {
            con = ConnectionManager.getConnection();
            sql = "SELECT * FROM teacher WHERE teacherEmail = ?";
            ps = con.prepareStatement(sql);
            ps.setString(1, email);
            rs = ps.executeQuery();

            if (rs.next()) {
                String hashedPass = rs.getString("teacherPass");
                if (BCrypt.checkpw(password, hashedPass)) {
                    String teacherType = rs.getString("teacherType");

                    if ("FullTime".equalsIgnoreCase(teacherType)) {
                        fullTime ft = new fullTime();
                        ft.setSalary(getSalary(rs.getInt("teacherId")));
                        t = ft;
                    } else if ("PartTime".equalsIgnoreCase(teacherType)) {
                        partTime pt = new partTime();
                        pt.setContract(getContract(rs.getInt("teacherId")));
                        t = pt;
                    } else {
                        t = new teacher();
                    }

                    // Common fields
                    t.setTeacherId(rs.getInt("teacherId"));
                    t.setTeacherName(rs.getString("teacherName"));
                    t.setTeacherEmail(rs.getString("teacherEmail"));
                    t.setTeacherPass(hashedPass);
                    t.setTeacherPhone(rs.getString("teacherPhone"));
                    t.setTeacherRole(rs.getString("teacherRole"));
                    t.setTeacherType(rs.getString("teacherType"));
                    t.setAdminId(rs.getInt("adminId"));
                    Blob blobPhoto = rs.getBlob("teacherPhoto");
                    if (blobPhoto != null) {
                        t.setTeacherPhoto(blobPhoto.getBytes(1, (int) blobPhoto.length()));
                    }
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try { if (rs != null) rs.close(); } catch (Exception e) {}
            try { if (ps != null) ps.close(); } catch (Exception e) {}
            try { if (con != null) con.close(); } catch (Exception e) {}
        }
        return t;
    }

    // Helper method to get salary
    private static Double getSalary(int teacherId) throws SQLException {
        sql = "SELECT salary FROM FullTime WHERE teacherId = ?";
        try (PreparedStatement ps2 = con.prepareStatement(sql)) {
            ps2.setInt(1, teacherId);
            ResultSet rs2 = ps2.executeQuery();
            if (rs2.next()) {
                return rs2.getDouble("salary");
            }
        }
        return null;
    }

    // Helper method to get contract
    private static Integer getContract(int teacherId) throws SQLException {
        sql = "SELECT contract FROM PartTime WHERE teacherId = ?";
        try (PreparedStatement ps2 = con.prepareStatement(sql)) {
            ps2.setInt(1, teacherId);
            ResultSet rs2 = ps2.executeQuery();
            if (rs2.next()) {
                return rs2.getInt("contract");
            }
        }
        return null;
    }


	// Get all teachers
    public static List<teacher> getAllTeacher() {
        List<teacher> teachers = new ArrayList<>();
        try {
            con = ConnectionManager.getConnection();
            sql = "SELECT * FROM teacher ORDER BY teacherId";
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                String teacherType = rs.getString("teacherType");
                teacher teach;

                if ("FullTime".equalsIgnoreCase(teacherType)) {
                    teach = new fullTime();
                    PreparedStatement ps2 = con.prepareStatement("SELECT salary FROM fullTime WHERE teacherId=?");
                    ps2.setInt(1, rs.getInt("teacherId"));
                    ResultSet rs2 = ps2.executeQuery();
                    if (rs2.next()) {
                        ((fullTime) teach).setSalary(rs2.getDouble("salary"));
                    }
                } else if ("PartTime".equalsIgnoreCase(teacherType)) {
                    teach = new partTime();
                    PreparedStatement ps2 = con.prepareStatement("SELECT contract FROM partTime WHERE teacherId=?");
                    ps2.setInt(1, rs.getInt("teacherId"));
                    ResultSet rs2 = ps2.executeQuery();
                    if (rs2.next()) {
                        ((partTime) teach).setContract(rs2.getInt("contract"));
                    }
                } else {
                    teach = new teacher();
                }

                teach.setTeacherId(rs.getInt("teacherId"));
                teach.setTeacherName(rs.getString("teacherName"));
                teach.setTeacherEmail(rs.getString("teacherEmail"));
                teach.setTeacherPass(rs.getString("teacherPass"));
                teach.setTeacherPhone(rs.getString("teacherPhone"));
                teach.setTeacherRole(rs.getString("teacherRole"));
                teach.setTeacherType(rs.getString("teacherType"));
                teach.setAdminId(rs.getInt("adminId"));

                teachers.add(teach);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try { if (rs != null) rs.close(); } catch (Exception e) {}
            try { if (ps != null) ps.close(); } catch (Exception e) {}
            try { if (con != null) con.close(); } catch (Exception e) {}
        }
        return teachers;
    }

    public static List<teacher> getAllAdmins() {
        List<teacher> admins = new ArrayList<>();
        try {
            con = ConnectionManager.getConnection();
            sql = "SELECT * FROM teacher WHERE teacherRole = 'Admin'";
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                teacher t = new teacher();
                t.setTeacherId(rs.getInt("teacherId"));
                t.setTeacherName(rs.getString("teacherName"));
                t.setTeacherEmail(rs.getString("teacherEmail"));
                t.setTeacherPass(rs.getString("teacherPass"));
                t.setTeacherPhone(rs.getString("teacherPhone"));
                t.setTeacherRole(rs.getString("teacherRole"));
                t.setTeacherType(rs.getString("teacherType"));
                t.setAdminId(rs.getInt("adminId"));
                admins.add(t);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try { if (rs != null) rs.close(); } catch (Exception e) {}
            try { if (ps != null) ps.close(); } catch (Exception e) {}
            try { if (con != null) con.close(); } catch (Exception e) {}
        }
        return admins;
    }

}
