    package dao;

import dto.Feedback;
import utils.DBContext;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class FeedbackDAO {

    private static final String GET_ALL_FEEDBACK =
        "SELECT id, userID, content, type, createdAt, updatedAt FROM tblFeedback";
    private static final String GET_FEEDBACK_BY_ID =
        "SELECT id, userID, content, type, createdAt, updatedAt FROM tblFeedback WHERE id = ?";
    private static final String GET_FEEDBACK_BY_USERID =
        "SELECT id, userID, content, type, createdAt, updatedAt FROM tblFeedback WHERE userID LIKE ?";
    private static final String GET_FEEDBACK_BY_TYPE =
        "SELECT id, userID, content, type, createdAt, updatedAt FROM tblFeedback WHERE type LIKE ?";
    private static final String CREATE_FEEDBACK =
        "INSERT INTO tblFeedback (userID, content, type) VALUES (?, ?, ?)";
    private static final String UPDATE_FEEDBACK =
        "UPDATE tblFeedback SET content = ?, type = ?, updatedAt = CURRENT_TIMESTAMP WHERE id = ?";
    private static final String DELETE_FEEDBACK =
        "DELETE FROM tblFeedback WHERE id = ?";

    public List<Feedback> getAllFeedback() throws SQLException {
        List<Feedback> list = new ArrayList<>();
        try (Connection conn = DBContext.getConnection();
             PreparedStatement stm = conn.prepareStatement(GET_ALL_FEEDBACK);
             ResultSet rs = stm.executeQuery()) {
            while (rs.next()) {
                list.add(mapRow(rs));
            }
        }
        return list;
    }

    public Feedback getFeedbackById(int id) throws SQLException {
        try (Connection conn = DBContext.getConnection();
             PreparedStatement stm = conn.prepareStatement(GET_FEEDBACK_BY_ID)) {
            stm.setInt(1, id);
            try (ResultSet rs = stm.executeQuery()) {
                return rs.next() ? mapRow(rs) : null;
            }
        }
    }

    public List<Feedback> getFeedbackByUserId(String userId) throws SQLException {
        List<Feedback> list = new ArrayList<>();
        try (Connection conn = DBContext.getConnection();
             PreparedStatement stm = conn.prepareStatement(GET_FEEDBACK_BY_USERID)) {
            stm.setString(1, "%" + userId + "%");
            try (ResultSet rs = stm.executeQuery()) {
                while (rs.next()) {
                    list.add(mapRow(rs));
                }
            }
        }
        return list;
    }

    public List<Feedback> getFeedbackByType(String type) throws SQLException {
        List<Feedback> list = new ArrayList<>();
        try (Connection conn = DBContext.getConnection();
             PreparedStatement stm = conn.prepareStatement(GET_FEEDBACK_BY_TYPE)) {
            stm.setString(1, "%" + type + "%");
            try (ResultSet rs = stm.executeQuery()) {
                while (rs.next()) {
                    list.add(mapRow(rs));
                }
            }
        }
        return list;
    }

    public int create(String userID, String content, String type) throws SQLException {
        try (Connection conn = DBContext.getConnection();
             PreparedStatement stm = conn.prepareStatement(CREATE_FEEDBACK, Statement.RETURN_GENERATED_KEYS)) {
            stm.setString(1, userID);
            stm.setString(2, content);
            stm.setString(3, type);
            stm.executeUpdate();
            try (ResultSet rs = stm.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
            return -1;
        }
    }

    public boolean update(int id, String content, String type) throws SQLException {
        try (Connection conn = DBContext.getConnection();
             PreparedStatement stm = conn.prepareStatement(UPDATE_FEEDBACK)) {
            stm.setString(1, content);
            stm.setString(2, type);
            stm.setInt(3, id);
            return stm.executeUpdate() > 0;
        }
    }

    public boolean delete(int id) throws SQLException {
        try (Connection conn = DBContext.getConnection();
             PreparedStatement stm = conn.prepareStatement(DELETE_FEEDBACK)) {
            stm.setInt(1, id);
            return stm.executeUpdate() > 0;
        }
    }

    private Feedback mapRow(ResultSet rs) throws SQLException {
        int id            = rs.getInt("id");
        String userID     = rs.getString("userID");
        String content    = rs.getString("content");
        String type       = rs.getString("type");
        Timestamp tsCreated = rs.getTimestamp("createdAt");
        Timestamp tsUpdated = rs.getTimestamp("updatedAt");

        LocalDateTime createdAt = tsCreated  != null ? tsCreated.toLocalDateTime() : null;
        LocalDateTime updatedAt = tsUpdated  != null ? tsUpdated.toLocalDateTime() : null;

        return new Feedback(id, userID, content, type, createdAt, updatedAt);
    }
}
