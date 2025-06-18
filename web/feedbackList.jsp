<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8"/>
    <title>Feedback Management</title>
    <link rel="stylesheet"
          href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css"/>
    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/css/style.css"/>
    <style>body { padding: 2rem; }</style>
</head>
<body>
<div class="container bg-white p-4 rounded shadow-sm">

    <h2>Welcome, <c:out value="${sessionScope.currentUser.fullName}"/></h2>

    <div class="d-flex justify-content-between align-items-center mb-4">
        <!-- Create Feedback -->
        <a href="${pageContext.request.contextPath}/main/feedback?action=create"
           class="btn btn-success">Create Feedback</a>
        <!-- Logout -->
        <form action="${pageContext.request.contextPath}/main/auth/logout"
              method="post">
            <button type="submit" class="btn btn-danger">Logout</button>
        </form>
    </div>

    <!-- Search by ID -->
    <form action="${pageContext.request.contextPath}/main/feedback"
          method="get" class="row g-2 mb-3 align-items-end">
        <input type="hidden" name="action" value="getFeedbackByID"/>
        <div class="col-md-4">
            <label for="feedbackId" class="form-label">Feedback ID</label>
            <input type="text" id="feedbackId" name="feedbackId"
                   class="form-control" placeholder="Enter ID…"/>
        </div>
        <div class="col-md-2">
            <button type="submit" class="btn btn-primary w-100">Search ID</button>
        </div>
    </form>

    <!-- Search by Type -->
    <form action="${pageContext.request.contextPath}/main/feedback"
          method="get" class="row g-2 mb-4 align-items-end">
        <input type="hidden" name="action" value="getFeedbackByType"/>
        <div class="col-md-4">
            <label for="type" class="form-label">Type</label>
            <input type="text" id="type" name="type"
                   class="form-control" placeholder="Enter type…"/>
        </div>
        <div class="col-md-2">
            <button type="submit" class="btn btn-primary w-100">Search Type</button>
        </div>
    </form>

    <!-- No results -->
    <c:if test="${empty feedbackList}">
        <div class="alert alert-warning">No feedback found.</div>
    </c:if>

    <!-- Results table -->
    <c:if test="${not empty feedbackList}">
        <table class="table table-striped table-hover">
            <thead class="table-light">
            <tr>
                <th>#</th>
                <th>ID</th>
                <th>User ID</th>
                <th>Type</th>
                <th>Content</th>
                <th>Actions</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="item" items="${feedbackList}" varStatus="st">
                <tr>
                    <td>${st.count}</td>
                    <td>${item.id}</td>
                    <td>${item.userID}</td>
                    <td>${item.type}</td>
                    <td>${item.content}</td>
                    <td>
                        <!-- Edit -->
                        <a href="${pageContext.request.contextPath}/main/feedback?action=update&feedbackId=${item.id}"
                           class="btn btn-sm btn-warning me-1">Edit</a>
                        <!-- Delete -->
                        <form action="${pageContext.request.contextPath}/main/feedback"
                              method="post" class="d-inline"
                              onsubmit="return confirm('Delete this feedback?');">
                            <input type="hidden" name="action" value="delete"/>
                            <input type="hidden" name="feedbackId" value="${item.id}"/>
                            <button type="submit" class="btn btn-sm btn-danger">Delete</button>
                        </form>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </c:if>

    <!-- Back navigation -->
    <div class="mt-4">
        <c:choose>
            <c:when test="${sessionScope.currentUser.role.name() == 'ADMIN'}">
                <a href="${pageContext.request.contextPath}/admin.jsp"
                   class="btn btn-outline-primary">Back to Admin</a>
            </c:when>
            <c:otherwise>
                <a href="${pageContext.request.contextPath}/welcome.jsp"
                   class="btn btn-outline-primary">Back to Home</a>
            </c:otherwise>
        </c:choose>
    </div>

</div>
</body>
</html>
