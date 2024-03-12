package cue.edu.co.repository.impl.salesdetails;

import cue.edu.co.database.DataBaseConnection;
import cue.edu.co.model.*;
import cue.edu.co.repository.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SalesDetailsJDBCImpl implements Repository<SalesDetails> {
    private Connection getConnection() throws SQLException {
        return DataBaseConnection.getInstance();
    }
    private SalesDetails createDetails(ResultSet resultSet) throws SQLException {
        SalesDetails saleDetails = new SalesDetails();
        Sales sale = new Sales();
        sale.setId(resultSet.getInt("id_sale"));
        sale.setId_employee(Employee.builder().name(resultSet.getString("employees_user")).build().getId());
        sale.setClient_id(Client.builder().name(resultSet.getString("cliente_name")).build().getId());
        saleDetails.setId_sale(sale);
        Toy toy = new Toy();
        toy.setId(resultSet.getInt("id"));
        toy.setName(resultSet.getString("name"));
        toy.setPrice(resultSet.getInt("price"));
        saleDetails.setQuantity(resultSet.getInt("cantidad"));
        saleDetails.setPrice(resultSet.getInt("price"));


        return saleDetails;
    }
    @Override
    public List<SalesDetails> list() {
        List<SalesDetails> st = new ArrayList<>();
        try(Statement statement = getConnection().createStatement();
            ResultSet resultSet = statement.executeQuery(
                    """
                           SELECT st.*,
                                  s.id AS sale_id,
                                  t.id AS toy_id,
                                  t.name AS toy_name,
                                  t.price AS toy_price,
                                  c.name AS cliente_name,
                                  e.name AS employees_user,
                                  cat.type AS toy_type,
                                  t.quantity AS toy_stock
                           FROM sales_detail AS st
                           INNER JOIN sales AS s ON st.id_sale = s.id
                           INNER JOIN toys AS t ON st.id_toy = t.id
                           LEFT JOIN category AS cat ON t.id = cat.id
                           LEFT JOIN clients AS c ON s.id_client = c.id
                           LEFT JOIN employes AS e ON s.id_employee = e.id
                        
    
    """
            )) {
            while (resultSet.next()){
                SalesDetails std = createDetails(resultSet);
                st.add(std);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return st;
    }

    @Override
    public SalesDetails byId(int id) {
        SalesDetails sd =null;
        try (PreparedStatement preparedStatement=getConnection()
                .prepareStatement(""" 
                                      SELECT sd.*, s.id AS sale_id, t.id AS toy_id
                        FROM sales_detail AS sd
                        INNER JOIN sales AS s ON sd.id_sale = s.id
                        INNER JOIN toys AS t
                            ON sd.id_toy = t.id
                            where sd.id_sale = ?
                            
                                    """)
        ) {
            preparedStatement.setLong(1,id);
            ResultSet resultSet= preparedStatement.executeQuery();
            if (resultSet.next()){
                sd = createDetails(resultSet);
            }
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return sd;
    }

    @Override
    public void save(SalesDetails saleDetails) {
        try (PreparedStatement pst = getConnection()
                .prepareStatement("""
                                          INSERT INTO sales_detail (id_sale, id_toy, quantity, total_price) values (?,?,?,?)
                                          """)


        ){
            pst.setInt(1, saleDetails.getId_sale().getId());
            pst.setInt(2, saleDetails.getId_toy().getId());
            pst.setInt(3,saleDetails.getQuantity());
            pst.setDouble(4,saleDetails.getPrice());


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void delete(int id) {
        try(PreparedStatement preparedStatement = getConnection()
                .prepareStatement("""
                                      DELETE FROM sales_detail where id_sale = ?
                                      """)
        ){
            preparedStatement.setInt(1,id);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void update(SalesDetails saleDetails) {
        try (PreparedStatement preparedStatement = getConnection()
                .prepareStatement("UPDATE sales_detail SET id_sale = ?, id_toy = ?, quantity = ?, total_price = ? WHERE id_sale = ?")) {
            preparedStatement.setInt(1, saleDetails.getId_sale().getId());
            preparedStatement.setInt(2, saleDetails.getId_toy().getId());
            preparedStatement.setInt(3, saleDetails.getQuantity());
            preparedStatement.setDouble(4, saleDetails.getPrice());
            preparedStatement.setInt(5, saleDetails.getId_sale().getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error updating sales details", e);
        }
    }
}
