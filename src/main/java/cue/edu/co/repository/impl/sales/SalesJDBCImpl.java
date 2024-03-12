package cue.edu.co.repository.impl.sales;

import cue.edu.co.database.DataBaseConnection;
import cue.edu.co.model.Sales;
import cue.edu.co.repository.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SalesJDBCImpl implements Repository<Sales> {

    private Connection getConnection() throws SQLException {
        return DataBaseConnection.getInstance();
    }
    private Sales createSale(ResultSet resultSet) throws SQLException {
        Sales s = new Sales();
        s.setId(resultSet.getInt("id"));
        s.setDate(resultSet.getString("date"));
        s.setClient_id(resultSet.getInt("id_cliente"));
        s.setId_employee(resultSet.getInt("id_employees"));
        return s;
    }
    @Override
    public List<Sales> list() {
        List<Sales>saleList = new ArrayList<>();
        try(Statement statement = getConnection().createStatement();
            ResultSet resultSet = statement.executeQuery(
                    """
                        
                           SELECT s.*, c.id as client_id, c.name as cliente_name, c.address as client_adress, c.phone as client_phone, c.email as client_email, c.gender as client_gender, c.identity as client_identity, c.birthdate as client_birthdate, e.id as employee_id, e.name as employee_user, e.password as employee_password, e.email as employee_email, e.phone as employee_phone, e.address as employee_birthdate, e.post as employee_post, e.salary as employee_salary
                           FROM sales AS s
                           INNER JOIN clients AS c ON s.id_client = c.id
                           INNER JOIN employes AS e ON s.id_employee = e.id;
                        
                        """
            ))
        {
            while (resultSet.next()){
                Sales s = createSale(resultSet);
                saleList.add(s);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return saleList;
    }

    @Override
    public Sales byId(int id) {
        Sales s =null;
        try (PreparedStatement preparedStatement=getConnection()
                .prepareStatement(""" 
                                     SELECT ss.*, c.name AS cliente_name, e.name AS employee_name
                                     FROM sales AS ss
                                     INNER JOIN clients AS c ON ss.id_client = c.id
                                     INNER JOIN employes AS e ON ss.id_employee = e.id;
                            
                                    """)
        ) {
            preparedStatement.setLong(1,id);
            ResultSet resultSet= preparedStatement.executeQuery();
            if (resultSet.next()){
                s = createSale(resultSet);
            }
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return s;
    }


    @Override
    public void save(Sales sale) {
        try (PreparedStatement preparedStatement = getConnection()
                .prepareStatement("""
                                          INSERT INTO sales (date, id_client, id_employee) values (?, ?, ?)
                                          """)


        ){
            preparedStatement.setString(1, sale.getDate());
            preparedStatement.setInt(2, sale.getClient_id());
            preparedStatement.setInt(3, sale.getId_employee());
            preparedStatement.executeUpdate();


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }

    @Override
    public void delete(int id) {
        try(PreparedStatement preparedStatement = getConnection()
                .prepareStatement("""
                                      DELETE FROM sales where id=?
                                      """)
        ){
            preparedStatement.setInt(1,id);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void update(Sales sale) {
        try (PreparedStatement preparedStatement = getConnection()
                .prepareStatement("UPDATE sales SET date = ?, id_client = ?, id_employee = ? WHERE id = ?")) {
            preparedStatement.setString(1, sale.getDate());
            preparedStatement.setInt(2, sale.getClient_id());
            preparedStatement.setInt(3, sale.getId_employee());
            preparedStatement.setInt(4, sale.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error updating sale", e);
        }
    }

}
