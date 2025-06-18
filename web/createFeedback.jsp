<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8"/>
    <title>Create Feedback</title>
    <link
        href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css"
        rel="stylesheet"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css"/>
    <style>body { padding: 2rem; }</style>
</head>
<body>
<div class="container bg-white p-4 rounded shadow-sm">
    <h2>Create Feedback</h2>
    <div class="mb-3">
        <a href="${pageContext.request.contextPath}/main/feedback" class="btn btn-outline-primary">&larr; Back to List</a>
    </div>

    <!-- Display message -->
    <c:if test="${not empty MSG}">
        <div class="alert alert-info">${MSG}</div>
    </c:if>

    <!-- Only logged-in user can create, use session userID -->
    <form action="${pageContext.request.contextPath}/main/feedback" method="post" class="row g-3">
        <input type="hidden" name="action" value="create"/>
        <input type="hidden" name="userID" value="${sessionScope.currentUser.userID}" />

        <div class="col-12">
            <label class="form-label">User ID</label>
            <input type="text" class="form-control" value="${sessionScope.currentUser.userID}" readonly />
        </div>

        <div class="col-12">
            <label for="type" class="form-label">Type</label>
            <input type="text" id="type" name="type" class="form-control" placeholder="Enter Type"
                   value="${param.type}" required/>
        </div>

        <div class="col-12">
            <label for="content" class="form-label">Content</label>
            <textarea id="content" name="content" class="form-control" rows="4" placeholder="Enter Content"
                      required>${param.content}</textarea>
        </div>

        <div class="col-12">
            <button type="submit" class="btn btn-success">Submit</button>
            <a href="${pageContext.request.contextPath}/main/feedback" class="btn btn-secondary">Cancel</a>
        </div>
    </form>
</div>
</body>
</html>
