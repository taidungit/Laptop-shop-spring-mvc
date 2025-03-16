<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="utf-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge" />
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no" />
    <meta name="description" content="Dũng Mount - Dự án laptopshop" />
    <meta name="author" content="Dũng Mount" />
    <title>User - Dũng Mount</title>
    <link href="/css/styles.css" rel="stylesheet" />
    <script src="https://use.fontawesome.com/releases/v6.3.0/js/all.js" crossorigin="anonymous"></script>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
   

                <script>
                    $(document).ready(() => {
                        const avatarFile = $("#avatarFile");
                        avatarFile.change(function (e) {
                            const imgURL = URL.createObjectURL(e.target.files[0]);
                            $("#avatarPreview").attr("src", imgURL);
                            $("#avatarPreview").css({ "display": "block" });
                        });
                    });
                </script>

</head>

<body class="sb-nav-fixed">
    <jsp:include page="../layout/header.jsp" />
    <div id="layoutSidenav">
    <jsp:include page="../layout/sidebar.jsp" />
      
        <div id="layoutSidenav_content">
            <main>
                <div class="container-fluid px-4">
                    <h1 class="mt-4">Manage User</h1>
                    <ol class="breadcrumb mb-4">
                        <li class="breadcrumb-item active"><a href="/admin">Dashboard</a></li>
                        <li class="breadcrumb-item active"><a href="/admin/user">User</a></li>
                        <li class="breadcrumb-item active">Create</li>
                    </ol>
                    <div class="row mt-4">
                        <div class="col-md-6 col-12 mx-auto">
                            <h3>Create a user</h3>
                            <hr />
                            <form:form method="post" action="/admin/user/create" modelAttribute="newUser" class="row"  enctype="multipart/form-data">

                                    <div class="mb-3 col-12 col-md-6">
                                        <c:set var="errorEmail">
                                            <form:errors path="email" cssClass="invalid-feedback" />
                                        </c:set>
                                        <label class="form-label">Email:</label>
                                        <form:input type="email"
                                            class="form-control ${not empty errorEmail ? 'is-invalid' : ''}"
                                            path="email" />
                                        ${errorEmail}
                                    </div>
                                    <div class="mb-3 col-12 col-md-6">
                                                    <c:set var="errorPassword">
                                                        <form:errors path="password" cssClass="invalid-feedback" />
                                                    </c:set>
                                                    <label class="form-label">Password:</label>
                                                    <form:input type="password"
                                                        class="form-control ${not empty errorPassword ? 'is-invalid' : ''}"
                                                        path="password" />
                                                    ${errorPassword}
                                    </div>
                               
                                    <div class="mb-3 col-12 col-md-6">
                                        <c:set var="errorPhone">
                                                        <form:errors path="phone" cssClass="invalid-feedback" />
                                                    </c:set>
                                                    <label class="form-label">Phone Number:</label>
                                                    <form:input type="text"
                                                        class="form-control ${not empty errorPhone ? 'is-invalid' : ''}"
                                                        path="phone" />
                                                    ${errorPhone}
                                    </div>
                                    <div class="mb-3 col-12 col-md-6">
                                                    <c:set var="errorName">
                                                        <form:errors path="fullName" cssClass="invalid-feedback" />
                                                    </c:set>
                                                    <label class="form-label">Full Name:</label>
                                                    <form:input type="text"
                                                        class="form-control ${not empty errorName ? 'is-invalid' : ''}"
                                                        path="fullName" />
                                                    ${errorName}
                                    </div>
                              
                                <div class="mb-3 col-12">
                                    <c:set var="errorAddress">
                                        <form:errors path="address" cssClass="invalid-feedback" />
                                    </c:set>
                                    <label class="form-label">Address:</label>
                                    <form:input type="text"
                                        class="form-control ${not empty errorAddress ? 'is-invalid' : ''}"
                                        path="address" />
                                    ${errorAddress}
                                </div>
                            
                                    <div class="mb-3 col-12 col-md-6">
                                        <label class="form-label">Role:</label>
                                        <form:select class="form-select " path="role.name">
                                            <form:option value="USER">USER</form:option>
                                            <form:option value="ADMIN">ADMIN</form:option>
                                        
                                          </form:select>
                                    </div>
    
                                    <div class="mb-3 col-12 col-md-6">
                                        <label for="avatarFile" class="form-label">Avatar</label>
                                        <input class="form-control" type="file" id="avatarFile" multiple  accept=".png, .jpg, .jpeg"
                                        name="dungmountFile"
                                        >
                                    </div>
                                    <div class="col-12 mb-3">
                                        <img style="max-height: 250px; display: none;" alt="avatar preview"
                                            id="avatarPreview" />
                                    </div>
                                
                               <div class="col-12 mb-5">
                                    <button type="submit" class="btn btn-primary">Create</button>
                                    <a href="/admin/user" class="btn btn-success mx-4 px-4">Back</a>
                               </div>
                            </form:form>
                        </div>
            
                    </div>
            
                </div>
            </main>
            <jsp:include page="../layout/footer.jsp" />
        </div>
    </div>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js"
        crossorigin="anonymous"></script>
    <script src="/js/scripts.js"></script>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
    

</body>

</html>