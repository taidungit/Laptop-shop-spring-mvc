<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>




<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="utf-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge" />
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no" />
    <meta name="description" content="Dũng Mount - Dự án laptopshop" />
    <meta name="author" content="Dũng Mount" />
    <title>Product - Dũng Mount</title>
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
                    <h1 class="mt-4">Order</h1>
                    <ol class="breadcrumb mb-4">
                        <li class="breadcrumb-item active"><a href="/admin">Dashboard</a></li>
                        <li class="breadcrumb-item active">Order</li>
                    </ol>
                    <div class="row mt-4">
                        <div class=" col-12 mx-auto">
                        
            
                            <hr />
                            <table class="table table-bordered table-hover ">
                                <thead>
                                  <tr>
                                    <th scope="col">ID</th>
                                    <th scope="col">Total price</th>
                                    <th scope="col">User</th>
                                    <th scope="col">Status</th>
                                    <th scope="col">Action</th>
                                  </tr>
                                </thead>
                                <tbody>
                                 
                                  <c:forEach var="order" items="${orders}">
                                    <tr>
                                      <th scope="row">${order.id}</th>
                                      <td>
                                        <fmt:formatNumber type="number" value="${order.totalPrice}"/>đ
                                      </td>
                                      <td>${order.user.fullName}</td>
                                    
                                      <td>${order.status}</td>
                                      <td>
                                          <a href="/admin/order/${order.id}" class="btn btn-success">View</a>
                                          <a href="/admin/order/update/${order.id}" class="btn btn-warning mx-2">Update</a>
                                          <a href="/admin/order/delete/${order.id}" class="btn btn-danger">Delete</a>
              
                                      </td>
                                       
                                    </tr>
                                </c:forEach>
                            
                                </tbody>
                              </table>
                              <nav aria-label="Page navigation example">
                                <ul class="pagination justify-content-center">
                                  <li class="page-item">
                                    <a class="${1 eq currentPage ?'disabled page-link':'page-link'}" href="/admin/order?page=${currentPage-1}" aria-label="Previous">
                                      <span aria-hidden="true">&laquo;</span>
                                    </a>
                                  </li>
  
                                  <c:forEach begin="0" end="${totalPages-1}" varStatus="loop">
                                  <li class="page-item"><a class="${loop.index+1 eq currentPage ?'active page-link':'page-link'}" href="/admin/order?page=${loop.index+1}">${loop.index+1}</a></li>
                                  </c:forEach>
                                 
                                  <li class="page-item">
                                    <a class="${totalPages eq currentPage ?'disabled page-link':'page-link'}" href="/admin/order?page=${currentPage+1}" aria-label="Next">
                                      <span aria-hidden="true">&raquo;</span>
                                    </a>
                                  </li>
                                </ul>
                              </nav>
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
    
</body>

</html>