package controllers;

import constant.Message;
import constant.Role;
import constant.Url;
import dto.Feedback;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import services.FeedbackServices;
import utils.AuthUtils;

@WebServlet(name = "FeedbackController", urlPatterns = { Url.FEEDBACK_CONTROLLER })
public class FeedbackController extends HttpServlet {

    private final FeedbackServices feedbackService = new FeedbackServices();

    private static final String GET_ALL_FEEDBACK = "getAllFeedback";
    private static final String GET_FEEDBACK_BY_ID = "getFeedbackByID";
    private static final String GET_FEEDBACK_BY_USER = "getFeedbackByUser";
    private static final String GET_FEEDBACK_BY_TYPE = "getFeedbackByType";
    private static final String CREATE  = "create";
    private static final String UPDATE = "update";
    private static final String DELETE = "delete";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Uncomment to restrict to ADMIN
        // if (!AuthUtils.checkAuthorization(request, response, Role.ADMIN)) return;

        String action = request.getParameter("action");
        if (action == null) action = GET_ALL_FEEDBACK;

        List<Feedback> feedbacks = null;
        String url = Url.FEEDBACK_LIST_PAGE;

        try {
            switch (action) {
                case CREATE:
                    url = Url.CREATE_FEEDBACK_PAGE;
                    break;
                case UPDATE:
                    feedbacks = new ArrayList<>();
                    feedbacks.add(getFeedbackByID(request, response));
                    url = Url.UPDATE_FEEDBACK_PAGE;
                    break;
                case GET_FEEDBACK_BY_ID:
                    feedbacks = new ArrayList<>();
                    Feedback fbById = getFeedbackByID(request, response);
                    if (fbById != null) feedbacks.add(fbById);
                    break;
                case GET_FEEDBACK_BY_USER:
                    feedbacks = getFeedbackByUser(request, response);
                    break;
                case GET_FEEDBACK_BY_TYPE:
                    feedbacks = getFeedbackByType(request, response);
                    break;
                case GET_ALL_FEEDBACK:
                default:
                    feedbacks = getAllFeedback(request, response);
                    break;
            }
        } catch (SQLException | NumberFormatException ex) {
            ex.printStackTrace();
            request.setAttribute("MSG", Message.SYSTEM_ERROR);
            url = Url.ERROR_PAGE;
        }

        if (UPDATE.equals(action)) {
            request.setAttribute("feedback", feedbacks.get(0));
        } else {
            request.setAttribute("feedbackList", feedbacks);
        }

        request.getRequestDispatcher(url).forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Uncomment to restrict to ADMIN
//         if (!AuthUtils.checkAuthorization(request, response, Role.ADMIN)) return;

        String action = request.getParameter("action");
        if (action == null) action = "";

        String url = Url.FEEDBACK_LIST_PAGE;
        String message = null;

        try {
            switch (action) {
                case CREATE:
                    message = createFeedback(request);
                    url = Url.CREATE_FEEDBACK_PAGE;
                    break;
                case UPDATE:
                    message = updateFeedback(request);
                    request.setAttribute("feedback", getFeedbackByID(request, response));
                    url = Url.UPDATE_FEEDBACK_PAGE;
                    break;
                case DELETE:
                    if (!AuthUtils.checkAuthorization(request, response, Role.ADMIN)) return;
                    message = deleteFeedback(request);
                    request.setAttribute("feedbackList", feedbackService.getAllFeedback());
                    break;
                default:
                    break;
            }
        } catch (SQLException | NumberFormatException ex) {
            ex.printStackTrace();
            message = Message.SYSTEM_ERROR;
            url = Url.ERROR_PAGE;
        }

        request.setAttribute("MSG", message);
        request.getRequestDispatcher(url).forward(request, response);
    }

    private Feedback getFeedbackByID(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            int id = Integer.parseInt(request.getParameter("feedbackId"));
            Feedback fb = feedbackService.getFeedbackById(id);
            if (fb == null) {
                request.setAttribute("MSG", Message.FEEDBACK_NOT_FOUND);
            }
            return fb;
        } catch (SQLException ex) {
            ex.printStackTrace();
            request.setAttribute("MSG", Message.SYSTEM_ERROR);
            return null;
        }
    }

    private List<Feedback> getAllFeedback(HttpServletRequest request, HttpServletResponse response)
            throws SQLException {
        return feedbackService.getAllFeedback();
    }

    private List<Feedback> getFeedbackByUser(HttpServletRequest request, HttpServletResponse response)
            throws SQLException {
        String userID = request.getParameter("userID");
        return feedbackService.getFeedbackByUserId(userID);
    }

    private List<Feedback> getFeedbackByType(HttpServletRequest request, HttpServletResponse response)
            throws SQLException {
        String type = request.getParameter("type");
        return feedbackService.getFeedbackByType(type);
    }

    private String createFeedback(HttpServletRequest request)
            throws SQLException {
        String userID = request.getParameter("userID");
        String content = request.getParameter("content");
        String type = request.getParameter("type");
        return feedbackService.createFeedback(userID, content, type);
    }

    private String updateFeedback(HttpServletRequest request)
            throws SQLException {
        int id = Integer.parseInt(request.getParameter("feedbackId"));
        String content = request.getParameter("content");
        String type = request.getParameter("type");
        return feedbackService.updateFeedback(id, content, type);
    }

    private String deleteFeedback(HttpServletRequest request)
            throws SQLException {
        int id = Integer.parseInt(request.getParameter("feedbackId"));
        return feedbackService.deleteFeedback(id);
    }
}
