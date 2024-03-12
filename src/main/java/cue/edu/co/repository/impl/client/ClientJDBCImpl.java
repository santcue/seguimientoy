package cue.edu.co.repository.impl.client;

import cue.edu.co.database.DataBaseConnection;
import cue.edu.co.model.Client;
import cue.edu.co.repository.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClientJDBCImpl implements Repository<Client> {
    private Connection getConnection() throws SQLException {
        return DataBaseConnection.getInstance();
    }
    @Override
    public List<Client> list() {
        List<Client> customers = new ArrayList<>();
        try (Statement statement = getConnection().createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT * FROM clients")) {
            while (resultSet.next()) {
                Client cliente = createCustomer(resultSet);
                customers.add(cliente);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al listar clientes", e);
        }
        return customers;
    }

    @Override
    public Client byId(int id) {
        Client customer = null;
        try {
            String sql = "SELECT * FROM clients WHERE id = ?";
            PreparedStatement preparedStatement = getConnection().prepareStatement(sql);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                customer = createCustomer(resultSet);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error when searching for customer by ID", e);
        }
        return customer;
    }

    @Override
    public void save(Client customer) {
        try (PreparedStatement preparedStatement = getConnection().prepareStatement(
                "INSERT INTO clients(name, address, phone, email, gender, birthdate, identity) VALUES (?, ?, ?, ?, ?, ?, ?)")) {
            preparedStatement.setString(1, customer.getName());
            preparedStatement.setString(2, customer.getAddress());
            preparedStatement.setString(3, customer.getPhone());
            preparedStatement.setString(4, customer.getEmail());
            preparedStatement.setString(5, customer.getGender());
            preparedStatement.setString(6, customer.getBirthdate());
            preparedStatement.setString(7, customer.getIdentity());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error saving client", e);
        }
    }

    @Override
    public void delete(int id) {
        try (PreparedStatement preparedStatement = getConnection().prepareStatement(
                "DELETE FROM clients WHERE id = ?")) {
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error deleting client", e);
        }
    }

    @Override
    public void update(Client customer) {
        try (PreparedStatement preparedStatement = getConnection()
                .prepareStatement("UPDATE clients SET name = ?, identity = ?, birthdate = ? WHERE id = ?")) {
            preparedStatement.setString(1, customer.getName());
            preparedStatement.setString(2, customer.getIdentity());
            preparedStatement.setString(3, customer.getBirthdate());
            preparedStatement.setInt(4, customer.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error updating client", e);
        }
    }

    private Client createCustomer(ResultSet resultSet) throws SQLException {
        Client customer = new Client();
        customer.setId(resultSet.getInt("id"));
        customer.setName(resultSet.getString("name"));
        customer.setAddress(resultSet.getString("address"));
        customer.setPhone(resultSet.getString("phone"));
        customer.setEmail(resultSet.getString("email"));
        customer.setGender(resultSet.getString("gender"));
        customer.setBirthdate(resultSet.getString("birthdate"));
        customer.setIdentity(resultSet.getString("identity"));

        return customer;
    }
}
