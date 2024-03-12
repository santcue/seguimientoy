package cue.edu.co.service.impl;

import cue.edu.co.database.DataBaseConnection;
import cue.edu.co.model.Category;
import cue.edu.co.model.Toy;
import cue.edu.co.service.ToyRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ToyRepositoryJDBCImpl implements ToyRepository<Toy> {

    private Connection getConnection() throws SQLException {
        return DataBaseConnection.getInstance();
    }
    private Toy createToy(ResultSet resultSet) throws SQLException {
        Toy t = new Toy();
        t.setId(resultSet.getInt("id"));
        t.setName(resultSet.getString("name"));
        t.setPrice(resultSet.getInt("price"));

        t.setType(new Category(
                resultSet.getInt("id_category"),
                resultSet.getString("category_name")

        ));
        t.setQuantity(resultSet.getInt("stock"));
        return t;
    }



    @Override
    public List<Toy> list() {
        List<Toy>toysList=new ArrayList<>();
        try(Statement statement=getConnection().createStatement();
            ResultSet resultSet=statement.executeQuery(
                    """
                        SELECT t.*, c.type as category_type, c.id as category_id
                        FROM toys AS t
                        INNER JOIN category AS c ON t.type = c.id;
                        """
            ))
        {
            while (resultSet.next()){
                Toy t = createToy(resultSet);
                toysList.add(t);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return toysList;
    }

    @Override
    public Toy byId(int id) {
        Toy t =null;
        try (PreparedStatement preparedStatement=getConnection()
                .prepareStatement(""" 
                                    SELECT t.*, c.type as category_type, c.id as category_id
                                    FROM toys AS t 
                                    INNER JOIN category AS c ON t.type = c.id
                                    WHERE t.id= ?
                                    """)
        ) {
            preparedStatement.setLong(1,id);
            ResultSet resultSet= preparedStatement.executeQuery();
            if (resultSet.next()){
                t =createToy(resultSet);
            }
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return t;
    }

    @Override
    public int getTotalStock() {
        int totalStock = 0;
        try (Statement statement = getConnection().createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT SUM(quantity) AS total_stock FROM toys")) {
            if (resultSet.next()) {
                totalStock = resultSet.getInt("total_stock");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return totalStock;
    }

    @Override
    public int getTotalValue() {
        int totalValue = 0;
        try (Statement statement = getConnection().createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT SUM(quantity * price) AS total_value FROM toys")) {
            if (resultSet.next()) {
                totalValue = resultSet.getInt("total_value");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return totalValue;
    }

    @Override
    public String TypeWithMostToys() {
        String typeWithMostToys = null;
        try (Statement statement = getConnection().createStatement();
             ResultSet resultSet = statement.executeQuery(
                     "SELECT c.type, COUNT(*) AS total_toys " +
                             "FROM toys AS t " +
                             "JOIN category AS c ON t.type = c.id " +
                             "GROUP BY c.type " +
                             "ORDER BY total_toys DESC " +
                             "LIMIT 1")) {
            if (resultSet.next()) {
                typeWithMostToys = resultSet.getString("type");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return typeWithMostToys;
    }

    @Override
    public String TypeWithLeastToys() {
        String typeWithLeastToys = null;
        try (Statement statement = getConnection().createStatement();
             ResultSet resultSet = statement.executeQuery(
                     "SELECT c.type, COUNT(*) AS total_toys " +
                             "FROM toys AS t " +
                             "JOIN category AS c ON t.type = c.id " +
                             "GROUP BY c.type " +
                             "ORDER BY total_toys ASC " +
                             "LIMIT 1")) {
            if (resultSet.next()) {
                typeWithLeastToys = resultSet.getString("type");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return typeWithLeastToys;
    }

    @Override
    public List<Toy> ToysWithAnValue(int value) {

        List<Toy> toysWithValueGreaterThan = new ArrayList<>();
        try (PreparedStatement preparedStatement = getConnection().prepareStatement(
                """
                        SELECT t.*, c.type AS category_type, c.id AS category_id
                        FROM toys AS t
                        JOIN category AS c ON t.type = c.id
                        WHERE t.price > ?""")) {
            preparedStatement.setInt(1, value);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                toysWithValueGreaterThan.add(createToy(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return toysWithValueGreaterThan;
    }

    @Override
    public List<Toy> orderByStockQuantity() {

        List<Toy> toysOrderedByStockQuantity = new ArrayList<>();
        try (Statement statement = getConnection().createStatement();
             ResultSet resultSet = statement.executeQuery(
                     "SELECT t.*, c.type AS category_name, COUNT(*) AS total_stock " +
                             "FROM toys AS t " +
                             "JOIN category AS c ON t.type = c.id " +
                             "GROUP BY c.type " +
                             "ORDER BY total_stock ASC")) {
            while (resultSet.next()) {
                toysOrderedByStockQuantity.add(createToy(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return toysOrderedByStockQuantity;
    }

    @Override
    public void updateStock(int toyId, int quantityChange) {
        try (PreparedStatement preparedStatement = getConnection()
                .prepareStatement("UPDATE toys SET quantity = quantity + ? WHERE id = ?")) {
            preparedStatement.setInt(1, quantityChange);
            preparedStatement.setInt(2, toyId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }


    @Override
    public void save(Toy toy) {
        try (PreparedStatement pst = getConnection()
                .prepareStatement("""
                                          INSERT INTO toys (name, price ,type , quantity) values (?,?,?,?)
                                          """)
        ){
            pst.setString(1, toy.getName());
            pst.setDouble(2, toy.getPrice());
            pst.setInt(3, toy.getType().getId());
            pst.setInt(4,toy.getQuantity());
            pst.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void delete(int id) {
        try(PreparedStatement preparedStatement = getConnection()
                .prepareStatement("""
                                      DELETE FROM toys where id = ?
                                      """)
        ){
            preparedStatement.setInt(1,id);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Toy> showByTYpe() {
        List<Toy> typesList = new ArrayList<>();
        try(Statement statement=getConnection().createStatement();
            ResultSet resultSet=statement.executeQuery(
                    """
                        SELECT toys.type, COUNT(*) FROM toys\s
                        JOIN category ON toys.type = category.id\s
                        GROUP BY category.type, toys.type LIMIT 0, 1000;
                        """
            ))
        {
            while (resultSet.next()){
                Toy t = createToy(resultSet);
                typesList.add(t);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return typesList;
    }


    @Override
    public void update(Toy toy) {
        try (PreparedStatement preparedStatement = getConnection()
                .prepareStatement("UPDATE toys SET name = ?, price = ?, type = ?, quantity = ? WHERE id = ?")) {
            preparedStatement.setString(1, toy.getName());
            preparedStatement.setDouble(2, toy.getPrice());
            preparedStatement.setInt(3, toy.getType().getId());
            preparedStatement.setInt(4, toy.getQuantity());
            preparedStatement.setInt(5, toy.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error al actualizar juguete", e);
        }
    }

}
