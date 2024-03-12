package cue.edu.co.repository.impl.employee;

import cue.edu.co.database.DataBaseConnection;
import cue.edu.co.model.Employee;
import cue.edu.co.repository.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmployeeJDBCImpl implements Repository<Employee> {

    private Connection getConnection() throws SQLException {
        return DataBaseConnection.getInstance();
    }
    public Employee createEmployee(ResultSet resultSet) throws SQLException {
        Employee e = new Employee();
        e.setId(resultSet.getInt("id"));
        e.setName(resultSet.getString("name"));
        e.setAddress(resultSet.getString("address"));
        e.setPhone(resultSet.getString("phone"));
        e.setEmail(resultSet.getString("email"));
        e.setPost(resultSet.getString("post"));
        e.setSalary(resultSet.getDouble("salary"));
        e.setPassword(resultSet.getString("password"));
        e.setBirthdate(resultSet.getString("birthdate"));
        return e;

    }
    @Override
    public List<Employee> list() {
        List<Employee>employeesList = new ArrayList<>();
        try(Statement statement = getConnection().createStatement();
            ResultSet resultSet=statement.executeQuery(
                    """
                        SELECT e.* \s
                        FROM employes AS e
                        """
            ))
        {
            while (resultSet.next()){
                Employee e = createEmployee(resultSet);
                employeesList.add(e);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return employeesList;
    }

    @Override
    public Employee byId(int id) {
        Employee employee = null;
        try {
            String sql = "SELECT e.* FROM employes AS e WHERE e.id = ?";
            PreparedStatement preparedStatement = getConnection().prepareStatement(sql);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                employee = createEmployee(resultSet);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error when searching employee by ID", e);
        }
        return employee;
    }

    @Override
    public void save(Employee employees) {
        try (PreparedStatement pst = getConnection()
                .prepareStatement("""
                                          INSERT INTO employes (name, address, phone, email, post, salary, password, birthdate) values (?,?,?,?,?,?,?,?)
                                          """)
        ){
            pst.setString(1,employees.getName());
            pst.setString(2,employees.getAddress());
            pst.setString(3,employees.getPhone());
            pst.setString(4,employees.getEmail());
            pst.setString(5,employees.getPost());
            pst.setDouble(6,employees.getSalary());
            pst.setString(2,employees.getPassword());
            pst.setString(3,employees.getBirthdate());
            pst.executeUpdate();


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void delete(int id) {
        try(PreparedStatement preparedStatement = getConnection()
                .prepareStatement("""
                                       DELETE FROM employes where id = ?
                                       """)
        ){
            preparedStatement.setInt(1,id);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void update(Employee employees) {
        try (PreparedStatement preparedStatement = getConnection()
                .prepareStatement("UPDATE employes SET name = ?, password = ? WHERE id = ?")) {
            preparedStatement.setString(1, employees.getName());
            preparedStatement.setString(2, employees.getPassword());
            preparedStatement.setInt(3, employees.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error updating employee", e);
        }
    }

}
