package kms.dao;

import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import kms.connection.ConnectionManager;
import kms.model.student;

public class studentDAO {
	private static Connection con = null;
	private static PreparedStatement ps = null;
	private static ResultSet rs = null;
	private static String sql;

	// insert student
	public static void addStudent(student stud) {
		try {
			con = ConnectionManager.getConnection();
			sql = "INSERT INTO student(studName, studAge, studGender, studAddress, studBirthDate, parentId, studPhoto, studBirthCert) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
			ps = con.prepareStatement(sql);
			ps.setString(1, stud.getStudName());
			ps.setInt(2, stud.getStudAge());
			ps.setString(3, stud.getStudGender());
			ps.setString(4, stud.getStudAddress());
			ps.setDate(5, stud.getStudBirthDate());
			ps.setInt(6, stud.getParentId());
			ps.setBytes(7, stud.getStudPhoto());
			ps.setBytes(8, stud.getStudBirthCert());
			ps.executeUpdate();
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// update student
	public static void updateStudent(student stud) {
		try {
			con = ConnectionManager.getConnection();
			if (stud.getStudPhoto() != null && stud.getStudBirthCert() != null) {
				sql = "UPDATE student SET studName=?, studAge=?, studGender=?, studAddress=?, studBirthDate=?, parentId=?, studPhoto=?, studBirthCert=? WHERE studId=?";
				ps = con.prepareStatement(sql);
				ps.setString(1, stud.getStudName());
				ps.setInt(2, stud.getStudAge());
				ps.setString(3, stud.getStudGender());
				ps.setString(4, stud.getStudAddress());
				ps.setDate(5, stud.getStudBirthDate());
				ps.setInt(6, stud.getParentId());
				ps.setBytes(7, stud.getStudPhoto());
				ps.setBytes(8, stud.getStudBirthCert());
				ps.setInt(9, stud.getStudId());
			} else {
				sql = "UPDATE student SET studName=?, studAge=?, studGender=?, studAddress=?, studBirthDate=?, parentId=? WHERE studId=?";
				ps = con.prepareStatement(sql);
				ps.setString(1, stud.getStudName());
				ps.setInt(2, stud.getStudAge());
				ps.setString(3, stud.getStudGender());
				ps.setString(4, stud.getStudAddress());
				ps.setDate(5, stud.getStudBirthDate());
				ps.setInt(6, stud.getParentId());
				ps.setInt(7, stud.getStudId());
			}
			ps.executeUpdate();
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// delete student
	public static void deleteStudent(int id) {
		try {
			con = ConnectionManager.getConnection();
			sql = "DELETE FROM student WHERE studId=?";
			ps = con.prepareStatement(sql);
			ps.setInt(1, id);
			ps.executeUpdate();
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// get student by id
	public static student getStudent(int studId) {
		student stud = new student();
		try {
			con = ConnectionManager.getConnection();
			sql = "SELECT * FROM student WHERE studId = ?";
			ps = con.prepareStatement(sql);
			ps.setInt(1, studId);
			rs = ps.executeQuery();
			if (rs.next()) {
				stud.setStudId(rs.getInt("studId"));
				stud.setStudName(rs.getString("studName"));
				stud.setStudAge(rs.getInt("studAge"));
				stud.setStudGender(rs.getString("studGender"));
				stud.setStudAddress(rs.getString("studAddress"));
				stud.setStudBirthDate(rs.getDate("studBirthDate"));
				stud.setParentId(rs.getInt("parentId"));
				Blob blobPhoto = rs.getBlob("studPhoto");
				if (blobPhoto != null) {
					stud.setStudPhoto(blobPhoto.getBytes(1, (int) blobPhoto.length()));
				}
				Blob blobBirthCert = rs.getBlob("studBirthCert");
				if (blobBirthCert != null) {
					stud.setStudBirthCert(blobBirthCert.getBytes(1, (int) blobBirthCert.length()));
				}
			}
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return stud;
	}

	// get student by id (duplicate naming support)
	public static student getStudentById(int studId) {
		return getStudent(studId);
	}

	// get all students
	public static List<student> getAllStudents() {
		List<student> students = new ArrayList<>();
		try {
			con = ConnectionManager.getConnection();
			sql = "SELECT * FROM student";
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next()) {
				student stud = new student();
				stud.setStudId(rs.getInt("studId"));
				stud.setStudName(rs.getString("studName"));
				stud.setStudAge(rs.getInt("studAge"));
				stud.setStudGender(rs.getString("studGender"));
				stud.setStudAddress(rs.getString("studAddress"));
				stud.setStudBirthDate(rs.getDate("studBirthDate"));
				stud.setParentId(rs.getInt("parentId"));
				Blob blobPhoto = rs.getBlob("studPhoto");
				if (blobPhoto != null) {
					stud.setStudPhoto(blobPhoto.getBytes(1, (int) blobPhoto.length()));
				}
				Blob blobBirthCert = rs.getBlob("studBirthCert");
				if (blobBirthCert != null) {
					stud.setStudBirthCert(blobBirthCert.getBytes(1, (int) blobBirthCert.length()));
				}
				students.add(stud);
			}
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return students;
	}

	// get students by parent id
	public static List<student> getStudentsByParentId(int parentId) {
		List<student> students = new ArrayList<>();
		try {
			con = ConnectionManager.getConnection();
			sql = "SELECT * FROM student WHERE parentId = ?";
			ps = con.prepareStatement(sql);
			ps.setInt(1, parentId);
			rs = ps.executeQuery();
			while (rs.next()) {
				student stud = new student();
				stud.setStudId(rs.getInt("studId"));
				stud.setStudName(rs.getString("studName"));
				stud.setStudAge(rs.getInt("studAge"));
				stud.setStudGender(rs.getString("studGender"));
				stud.setStudAddress(rs.getString("studAddress"));
				stud.setStudBirthDate(rs.getDate("studBirthDate"));
				stud.setParentId(rs.getInt("parentId"));
				Blob blobPhoto = rs.getBlob("studPhoto");
				if (blobPhoto != null) {
					stud.setStudPhoto(blobPhoto.getBytes(1, (int) blobPhoto.length()));
				}
				Blob blobBirthCert = rs.getBlob("studBirthCert");
				if (blobBirthCert != null) {
					stud.setStudBirthCert(blobBirthCert.getBytes(1, (int) blobBirthCert.length()));
				}
				students.add(stud);
			}
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return students;
	}

	// count students under parent
	public static int countStudentByParent(int parentId) {
		int count = 0;
		try {
			con = ConnectionManager.getConnection();
			ps = con.prepareStatement("SELECT COUNT(*) FROM student WHERE parentId = ?");
			ps.setInt(1, parentId);
			rs = ps.executeQuery();
			if (rs.next()) {
				count = rs.getInt(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null) rs.close();
				if (ps != null) ps.close();
				if (con != null) con.close();
			} catch (Exception e) {
			}
		}
		return count;
	}

	// count all students
	public static int countAll() {
		int count = 0;
		try {
			con = ConnectionManager.getConnection();
			sql = "SELECT COUNT(*) FROM Student";
			ps = con.prepareStatement(sql);
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

	// get today's birthdays (name + date)
	public static List<student> getTodaysBirthdays() {
		List<student> birthdayList = new ArrayList<>();
		String sql = "SELECT studName, studBirthDate FROM Student WHERE TO_CHAR(studBirthDate, 'MM-DD') = TO_CHAR(SYSDATE, 'MM-DD')";

		try (Connection conn = ConnectionManager.getConnection();
		     PreparedStatement ps = conn.prepareStatement(sql);
		     ResultSet rs = ps.executeQuery()) {

			while (rs.next()) {
				student s = new student();
				s.setStudName(rs.getString("studName"));
				s.setStudBirthDate(rs.getDate("studBirthDate"));
				birthdayList.add(s);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return birthdayList;
	}

	// get today's birthdays (name + date + photo)
	public static List<student> getBirthdaysToday() throws SQLException {
		List<student> list = new ArrayList<>();
		String sql = "SELECT studName, studBirthDate, studPhoto FROM Student WHERE TO_CHAR(studBirthDate, 'MM-DD') = TO_CHAR(SYSDATE, 'MM-DD')";

		try (
			Connection con = ConnectionManager.getConnection();
			PreparedStatement ps = con.prepareStatement(sql);
			ResultSet rs = ps.executeQuery()
		) {
			while (rs.next()) {
				student s = new student();
				s.setStudName(rs.getString("studName"));
				s.setStudBirthDate(rs.getDate("studBirthDate"));

				byte[] photoBytes = rs.getBytes("studPhoto");
				if (photoBytes != null) {
					s.setStudPhoto(photoBytes);
				}
				list.add(s);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
}
