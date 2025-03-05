package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import db.DB;
import db.DbException;
import db.DbIntegrityException;
import model.dao.DepartmentDao;
import model.entities.Department;

public class DepartmentDaoJDBC implements DepartmentDao{

    private Connection conn;

    public DepartmentDaoJDBC(Connection conn){
        this.conn = conn;
    }

    @Override
    public void insert(Department obj) {

        PreparedStatement st = null;

        try {

            st = conn.prepareStatement(
                "INSERT department (Name) "
                +"VALUES (?)",
                Statement.RETURN_GENERATED_KEYS);

            st.setString(1, obj.getName());

            int rowsAffcted = st.executeUpdate();

            if(rowsAffcted > 0){
                ResultSet rs = st.getGeneratedKeys();
                if(rs.next()){
                    obj.setId(rs.getInt(1));
                }
                DB.closeResultSet(rs);               
            }

            else{
                throw new DbException("Unexpected error! No rows affected!");
            }

        } 
        catch (SQLException e) {
            throw new DbException(e.getMessage());
        }
        finally{
            DB.closeStatement(st);
        }

    }

    @Override
    public void update(Department obj) {

        PreparedStatement st = null;

        try {

            st = conn.prepareStatement(
                "UPDATE department SET Name = ? "
                +"WHERE Id = ?");

            st.setString(1, obj.getName());

            st.setInt(2, obj.getId());

            st.executeUpdate();
        } 
        catch (SQLException e) {
            throw new DbException(e.getMessage());
        }
        finally{
            DB.closeStatement(st);
        }

    }

    @Override
    public void deleteById(Integer id) {

        PreparedStatement st = null;

        try {
            st = conn.prepareStatement(
                "DELETE FROM department where Id = ?");

            st.setInt(1, id);
            int rows = st.executeUpdate();
            if(rows == 0){
                throw new DbException("Error! User ID does not exist");
            }
        }
        catch (SQLException e) {
            throw new DbIntegrityException(e.getMessage());    
        }
        finally{
            DB.closeStatement(st);
        }

    }

    @Override
    public Department findById(Integer id) {

        PreparedStatement st = null;
        ResultSet rs = null;

        try {
            
            st = conn.prepareStatement(
                "SELECT department.* "
                + "FROM department "
                + "WHERE Id = ?");
            
            st.setInt(1, id);

            rs = st.executeQuery();

            if(rs.next()){
                Department department = new Department();
                department.setId(rs.getInt("Id"));
                department.setName(rs.getString("Name"));
                return department;
            }
            return null;

        } 
        
        catch (SQLException e) {
            throw new DbException(e.getMessage());
        }

        finally{
            DB.closeStatement(st);
            DB.closeResultSet(rs);
        }

    }

    @Override
    public List<Department> findAll() {
         
        PreparedStatement st = null;
        ResultSet rs = null;

        try {

            st = conn.prepareStatement(
                "SELECT * FROM department"
            );

            rs = st.executeQuery();

            List<Department> list = new ArrayList<>();

            while (rs.next()) {
                Department department = new Department(rs.getInt("Id"),rs.getString("Name"));
                
                list.add(department);
            }

            return list;

        } 
        catch (SQLException e) {
            throw new DbException(e.getMessage());
        }
        finally{
            DB.closeStatement(st);
            DB.closeResultSet(rs);
        }

    }

}
