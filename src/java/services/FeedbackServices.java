package services;

import constant.Message;
import dao.FeedbackDAO;
import dto.Feedback;

import java.sql.SQLException;
import java.util.List;

public class FeedbackServices {
    private final FeedbackDAO dao = new FeedbackDAO();

    public List<Feedback> getAllFeedback() throws SQLException {
        return dao.getAllFeedback();
    }

    public Feedback getFeedbackById(int id) throws SQLException {
        return dao.getFeedbackById(id);
    }

    public List<Feedback> getFeedbackByUserId(String userId) throws SQLException {
        return dao.getFeedbackByUserId(userId);
    }

    public List<Feedback> getFeedbackByType(String type) throws SQLException {
        return dao.getFeedbackByType(type);
    }

    public String createFeedback(String userID, String content, String type) throws SQLException {
        if (isNullOrEmpty(userID) ||
            isNullOrEmpty(content) ||
            isNullOrEmpty(type)) {
            return Message.ALL_FIELDS_ARE_REQUIRED;
        }
        int newId = dao.create(userID, content, type);
        return (newId > 0) ? Message.CREATE_SUCCESS : Message.CREATE_FAIL;
    }

    public String updateFeedback(int id, String content, String type) throws SQLException {
        Feedback existing = dao.getFeedbackById(id);
        if (existing == null) {
            return Message.NOT_FOUND;
        }
        if (isNullOrEmpty(content)) {
            content = existing.getContent();
        }
        if (isNullOrEmpty(type)) {
            type = existing.getType();
        }
        boolean success = dao.update(id, content, type);
        return success ? Message.UPDATE_SUCCESS : Message.UPDATE_FAIL;
    }

    public String deleteFeedback(int id) throws SQLException {
        boolean success = dao.delete(id);
        return success ? Message.DELETE_SUCCESS : Message.NOT_FOUND;
    }

    private boolean isNullOrEmpty(String s) {
        return s == null || s.trim().isEmpty();
    }
}
