package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import db.DB;
import db.DBExeption;
import model.dao.DepartmentDao;
import model.entites.Department;

public class DepartmentDaoJDBC implements DepartmentDao {

	private Connection conn;

	public DepartmentDaoJDBC(Connection conn) {
		this.conn = conn;
	}

	@Override
	public Department findById(Integer id) {
		PreparedStatement st = null;
		ResultSet rs = null;

		try {
			st = conn.prepareStatement("SELECT * FROM department WHERE id = ?");
			st.setInt(1, id);

			rs = st.executeQuery();
			if (rs.next()) {
				Department dep = ImpDepatment(rs);
				return dep;
			}

		} catch (Exception e) {
			throw new DBExeption(e.getMessage());
		} finally {
			DB.closeStatement(st);

		}

		return null;
	}

	private Department ImpDepatment(ResultSet rs) throws SQLException {
		Department dep = new Department();
		dep.setId(rs.getInt("id"));
		dep.setName(rs.getString("Name"));

		return dep;
	}

	@Override
	public void deleteByid(Integer id) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement("DELETE FROM department WHERE id = ?");

			st.setInt(1, id);
			st.executeUpdate();

		} catch (Exception e) {
			throw new db.DBExeption(e.getMessage());
		}

	}

	@Override
	public List<Department> findAll() {
		PreparedStatement st = null;
		ResultSet rs = null;

		try {
			st = conn.prepareStatement("SELECT * FROM department ORDER BY Id");
			rs = st.executeQuery();

			List<Department> list = new ArrayList<>();
			Map<Integer, Department> map = new HashMap<>();

			while (rs.next()) {
				int departmentId = rs.getInt("Id");
				Department dep = map.get(departmentId);

				if (dep == null) {
					dep = ImpDepatment(rs);
					map.put(departmentId, dep);
				}

				list.add(dep);
			}

			return list;
		} catch (SQLException e) {
			throw new DBExeption(e.getMessage());
		} finally {
		}

	}

	@Override
	public void insert(Department obj) {
		PreparedStatement st = null;
		try {

			st = conn.prepareStatement("INSERT INTO department(id, Name)VALUES(?,?)", st.RETURN_GENERATED_KEYS);
			st.setInt(1, obj.getId());
			st.setString(2, obj.getName());

			int rowsAffected = st.executeUpdate();

			if (rowsAffected > 0) {
				ResultSet rs = st.getGeneratedKeys();
				if (rs.next()) {

					int gk = rs.getInt(1);

				} else {
					throw new DBExeption("Nenhumas das linhas afetadas");
				}

			}

		} catch (Exception e) {
			throw new DBExeption(e.getMessage());
		} finally {
			DB.closeStatement(st);
		}

	}

	@Override
	public void update(Department obj) {
		PreparedStatement st = null;

		try {

			st = conn.prepareStatement("UPDATE department SET Name = ? WHERE id = ?");
			st.setString(1, obj.getName());
			st.setInt(2, obj.getId());

			st.executeUpdate();

		} catch (Exception e) {
			throw new DBExeption(e.getMessage());
		} finally {
			DB.closeStatement(st);
		}

	}

}
