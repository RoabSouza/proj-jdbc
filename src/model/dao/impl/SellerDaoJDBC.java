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
import model.dao.SellerDao;
import model.entites.Department;
import model.entites.Seller;

public class SellerDaoJDBC implements SellerDao {

	private Connection conn;

	public SellerDaoJDBC(Connection conn) {
		this.conn = conn;
	}

	@Override
	public void insert(Seller obj) {
		PreparedStatement st = null;
		try {

			st = conn.prepareStatement(
					"INSERT INTO seller(Name, Email, BirthDate, BaseSalary, DepartmentId)VALUES(?,?,?,?,?)",
					st.RETURN_GENERATED_KEYS);

			st.setString(1, obj.getName());
			st.setString(2, obj.getEmail());
			st.setDate(3, new java.sql.Date(obj.getBirthday().getDate()));
			st.setDouble(4, obj.getBaseSalary());
			st.setInt(5, obj.getDepartment().getId());

			int rowsAffected = st.executeUpdate();

			if (rowsAffected > 0) {
				ResultSet rs = st.getGeneratedKeys();
				if (rs.next()) {
					int id = rs.getInt(1);
					obj.setId(id);
				}
			} else {
				throw new DBExeption("Nenhuma linha afetada");
			}

		} catch (Exception e) {
			throw new DBExeption(e.getMessage());
		} finally {
			DB.closeStatement(st);
		}

	}

	@Override
	public void update(Seller obj) {
		PreparedStatement st = null;
		try {

			st = conn.prepareStatement(
					"UPDATE seller SET Name = ?, Email = ?, BirthDate = ?, BaseSalary = ?, DepartmentId = ? WHERE id = ?");

			st.setString(1, obj.getName());
			st.setString(2, obj.getEmail());
			st.setDate(3, new java.sql.Date(obj.getBirthday().getDate()));
			st.setDouble(4, obj.getBaseSalary());
			st.setInt(5, obj.getDepartment().getId());
			st.setInt(6, obj.getId());

			st.executeUpdate();

		} catch (Exception e) {
			throw new DBExeption(e.getMessage());
		} finally {
			DB.closeStatement(st);
		}

	}

	@Override
	public void deleteByid(Integer id) {
		PreparedStatement st = null;
		try {

			st = conn.prepareStatement("DELETE FROM seller WHERE id = ?");

			st.setInt(1, id);
			st.executeUpdate();
		} catch (Exception e) {
			throw new db.DBExeption(e.getMessage());

		}
	}

	private Department ImpDepatment(ResultSet rs) throws SQLException {
		Department dep = new Department();
		dep.setId(rs.getInt("DepartmentId"));
		dep.setName(rs.getString("DepName"));
		return dep;
	}

	private Seller impSeller(ResultSet rs, Department dep) throws SQLException {
		Seller sl = new Seller();
		sl.setId(rs.getInt("Id"));
		sl.setName(rs.getString("Name"));
		sl.setEmail(rs.getString("Email"));
		sl.setBaseSalary(rs.getDouble("BaseSalary"));
		sl.setBirthday(rs.getDate("BirthDate"));
		sl.setDepartment(dep);
		return sl;
	}

	@Override

	public Seller findById(Integer id) {

		PreparedStatement st = null;
		ResultSet rs = null;
		try {

			st = conn.prepareStatement("SELECT seller. *, department.Name as DepName FROM seller INNER JOIN department "
					+ "ON seller.DepartmentId = department.Id " + "WHERE seller.Id = ?");

			st.setInt(1, id);

			rs = st.executeQuery();

			if (rs.next()) {

				Department dep = ImpDepatment(rs);

				Seller sl = impSeller(rs, dep);

				return sl;

			}

			return null;
		} catch (Exception e) {
			throw new DBExeption(e.getMessage());
		} finally {
		}

	}

	@Override
	public List<Seller> findByDepartment(Department dp) {

		PreparedStatement st = null;
		ResultSet rs = null;
		try {

			st = conn.prepareStatement("SELECT seller. *, department.Name as DepName FROM seller INNER JOIN department "
					+ "ON seller.DepartmentId = department.Id " + "WHERE department.Id = ? ORDER BY Name");

			st.setInt(1, dp.getId());

			rs = st.executeQuery();

			List<Seller> list = new ArrayList<>();
			Map<Integer, Department> map = new HashMap<>();
			while (rs.next()) {

				Department dep = map.get(rs.getInt("departmentId"));

				if (dep == null) {
					dep = ImpDepatment(rs);
					map.put(rs.getInt("departmentId"), dep);
				}

				Seller sl = impSeller(rs, dep);
				list.add(sl);

			}

			return list;
		} catch (Exception e) {
			throw new DBExeption(e.getMessage());
		} finally {
		}
	}

	@Override
	public List<Seller> findAll() {

		PreparedStatement st = null;
		ResultSet rs = null;
		try {

			st = conn.prepareStatement("SELECT seller. *, department.Name as DepName FROM seller INNER JOIN department "
					+ "ON seller.DepartmentId = department.Id  ORDER BY Name");

			rs = st.executeQuery();

			List<Seller> list = new ArrayList<>();
			Map<Integer, Department> map = new HashMap<>();
			while (rs.next()) {

				Department dep = map.get(rs.getInt("departmentId"));

				if (dep == null) {
					dep = ImpDepatment(rs);
					map.put(rs.getInt("departmentId"), dep);
				}

				Seller sl = impSeller(rs, dep);
				list.add(sl);

			}

			return list;
		} catch (Exception e) {
			throw new DBExeption(e.getMessage());
		} finally {
		}

	}

}
