<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>



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
                        <li class="breadcrumb-item active">User</li>
                    </ol>
                    <div class="row mt-4">
                        <div class=" col-12 mx-auto">
                           <div class="d-flex justify-content-between">
                                <h3>User detail with id = ${id}</h3>
                                
                           </div> 
            
                            <hr />
                            <div class="card" style="width: 70%;">
                                <img src="/images/avatar/${user1.avatar}" alt="">

                                <div class="card-header">
                                    User Information
                                </div>
                                <ul class="list-group list-group-flush">
                                  <li class="list-group-item">ID: ${user1.id}</li>
                                  <li class="list-group-item">Full Name: ${user1.fullName}</li>
                                  <li class="list-group-item">Email: ${user1.email}</li>
                                  <li class="list-group-item">Address: ${user1.address}</li>
                                  <li class="list-group-item">Password: ${user1.password}</li>
                                  <li class="list-group-item">Phone number: ${user1.phone}</li>
                                </ul>
                              </div>
                              
                              <a href="/admin/user" class="btn btn-success mt-4 px-4">Back</a>
                              
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
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>

</body>

</html>