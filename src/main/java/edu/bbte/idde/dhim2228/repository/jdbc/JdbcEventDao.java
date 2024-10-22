package edu.bbte.idde.dhim2228.repository.jdbc;

import edu.bbte.idde.dhim2228.model.EventModel;
import edu.bbte.idde.dhim2228.repository.EventDao;
import edu.bbte.idde.dhim2228.repository.exceptions.RepositoryException;
import lombok.extern.slf4j.Slf4j;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Slf4j
public class JdbcEventDao implements EventDao {
    private EventModel getEventFromResultSet(ResultSet resultSet) throws SQLException {
        if (resultSet.next()) {
            EventModel eventModel = new EventModel();
            eventModel.setId(resultSet.getLong("id"));
            eventModel.setName(resultSet.getString("name"));
            eventModel.setLocation(resultSet.getString("location"));
            eventModel.setDate(resultSet.getTimestamp("date").toLocalDateTime());
            eventModel.setIsOnline(resultSet.getBoolean("is_online"));
            eventModel.setDescription(resultSet.getString("description"));
            eventModel.setAttendeesCount(resultSet.getInt("attendees_count"));

            return eventModel;
        }

        return null;
    }

    @Override
    public EventModel getEventById(Long id) throws RepositoryException {
        log.info("Getting event by id: {}", id);
        try (Connection conn = ConnectionManager.getDataSource().getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(
                    "SELECT * FROM events WHERE id = ?"
            );
            stmt.setLong(1, id);

            ResultSet resultSet = stmt.executeQuery();
            return getEventFromResultSet(resultSet);
        } catch (SQLException e) {
            log.error("Getting event by id {} failed", id, e);
            throw new RepositoryException("Finding all events failed: ", e);
        }
    }

    @Override
    public Collection<EventModel> getAllEvents() throws RepositoryException {
        log.info("Finding all events...");
        try (Connection conn = ConnectionManager.getDataSource().getConnection()) {
            Statement stmt = conn.createStatement();
            ResultSet resultSet = stmt.executeQuery("SELECT * FROM events");

            List<EventModel> events = new ArrayList<>();
            EventModel eventModel = getEventFromResultSet(resultSet);
            while(eventModel != null) {
                events.add(eventModel);
                eventModel = getEventFromResultSet(resultSet);
            }

            return events;
        } catch (SQLException e) {
            log.error("Finding all events failed: ", e);
            throw new RepositoryException("Finding all events failed: ", e);
        }
    }

    @Override
    public void createEvent(EventModel event) {
            log.info("Creating event: {}", event);
        try (Connection conn = ConnectionManager.getDataSource().getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(
                    "INSERT INTO events(name, location, date, is_online, description, attendees_count)" +
                            "VALUES(?, ?, ?, ?, ?, ?)"
            );

            stmt.setString(1, event.getName());
            stmt.setString(2, event.getLocation());
            stmt.setTimestamp(3, java.sql.Timestamp.valueOf(event.getDate()));
            stmt.setBoolean(4, event.getIsOnline());
            stmt.setString(5, event.getDescription());
            stmt.setInt(6, event.getAttendeesCount());

            stmt.executeUpdate();
        } catch (SQLException e) {
            log.error("Creating event failed {}: ", event, e);
            throw new RepositoryException("Creating event failed: ", e);
        }
    }

    @Override
    public void updateEvent(Long id, EventModel event) throws RepositoryException {
        log.info("Updating event with id: {}", id);
        try (Connection conn = ConnectionManager.getDataSource().getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(
                    "UPDATE events SET name = ?, location = ?, date = ?, is_online = ?, description = ?, attendees_count = ? " +
                            "WHERE id = ?"
            );

            stmt.setString(1, event.getName());
            stmt.setString(2, event.getLocation());
            stmt.setTimestamp(3, java.sql.Timestamp.valueOf(event.getDate()));
            stmt.setBoolean(4, event.getIsOnline());
            stmt.setString(5, event.getDescription());
            stmt.setInt(6, event.getAttendeesCount());
            stmt.setLong(7, id);

            int rowsUpdated = stmt.executeUpdate();
            if (rowsUpdated == 0) {
                throw new RepositoryException("Event not found with id: " + id);
            }
        } catch (SQLException e) {
            log.error("Updating event with id {} failed", id, e);
            throw new RepositoryException("Updating event failed: ", e);
        }
    }

    @Override
    public void deleteEvent(Long id) throws RepositoryException {
        log.info("Deleting event: {}", id);
        try (Connection conn = ConnectionManager.getDataSource().getConnection()) {
            PreparedStatement stmt = conn.prepareStatement("DELETE FROM events WHERE id = ?");
            stmt.setLong(1, id);
            int rowsUpdated = stmt.executeUpdate();
            if (rowsUpdated == 0) {
                throw new RepositoryException("Event not found with id: " + id);
            }
        } catch (SQLException e) {
            log.error("Deleting event failed id:{}: ", id, e);
            throw new RepositoryException("Deleting event failed: ", e);
        }
    }
}
