<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8"/>
    <title>Update Feedback</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css"/>
    <style>body { padding: 2rem; }</style>
</head>
<body>
<div class="container bg-white p-4 rounded shadow-sm" style="max-width:600px; margin-top:2rem;">
    <h2>Update Feedback</h2>
    <div class="mb-3">
        <a href="${pageContext.request.contextPath}/main/feedback" class="btn btn-outline-primary">&larr; Back to List</a>
    </div>

    <!-- Display message -->
    <c:if test="${not empty MSG}">
        <div class="alert alert-info">${MSG}</div>
    </c:if>

    <form action="${pageContext.request.contextPath}/main/feedback" method="post" class="row g-3">
        <input type="hidden" name="action" value="update" />
        <input type="hidden" name="feedbackId" value="${feedback.id}" />
        <input type="hidden" name="type" value="${feedback.type}" />

        <div class="col-12">
            <label class="form-label">Type</label>
            <input type="text" class="form-control" value="${feedback.type}" readonly />
        </div>
        <div class="col-12">
            <label class="form-label">User ID</label>
            <input type="text" class="form-control" value="${feedback.userID}" readonly />
        </div>
        <div class="col-12">
            <label for="content" class="form-label">Content</label>
            <textarea id="content" name="content" class="form-control" rows="4" required>${feedback.content}</textarea>
        </div>
        <div class="col-12 d-flex gap-2">
            <button type="submit" class="btn btn-primary w-100">Update</button>
            <a href="${pageContext.request.contextPath}/main/feedback" class="btn btn-secondary w-100">Cancel</a>
        </div>
    </form>
</div>
</body>
</html>
