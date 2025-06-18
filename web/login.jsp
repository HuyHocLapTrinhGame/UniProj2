<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Login</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css"
          rel="stylesheet">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<%
    // Retrieve saved UserID from cookie if present
    Cookie[] cookies = request.getCookies();
    if (cookies != null) {
        for (Cookie cookie : cookies) {
            if ("userID".equals(cookie.getName())) {
                request.setAttribute("userIDSaved", cookie.getValue());
            }
        }
    }
%>

<div class="login-container container mt-5" style="max-width: 400px;">
    <h2 class="mb-4 text-center">Login</h2>

    <!-- Display error message if any -->
    <c:if test="${not empty MSG}">
        <div class="alert alert-danger">${MSG}</div>
    </c:if>

    <form action="${pageContext.request.contextPath}/main/auth/login"
          method="POST">
        <div class="mb-3">
            <label for="userID" class="form-label">User ID</label>
            <input type="text"
                   class="form-control"
                   id="userID"
                   name="userID"
                   value="${requestScope.userIDSaved}"
                   required />
        </div>
        <div class="mb-3">
            <label for="password" class="form-label">Password</label>
            <input type="password"
                   class="form-control"
                   id="password"
                   name="password"
                   required />
        </div>
        <div class="form-check mb-3">
            <input class="form-check-input"
                   type="checkbox"
                   name="rememberMe"
                   id="rememberMe" />
            <label class="form-check-label" for="rememberMe">Remember me</label>
        </div>
        <div class="d-grid mb-3">
            <button type="submit" class="btn btn-primary">Login</button>
        </div>
    </form>

    <!-- Feedback section links -->
    <div class="text-center mt-4">
        <p>
            Or you can 
            <a href="${pageContext.request.contextPath}/main/feedback?action=create"
               class="btn btn-link p-0">submit feedback</a>
            <br/>
            View feedback list 
            <a href="${pageContext.request.contextPath}/main/feedback"
               class="btn btn-link p-0">here</a>.
        </p>
    </div>
</div>
</body>
</html>
