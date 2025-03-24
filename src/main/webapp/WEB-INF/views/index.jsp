<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%@include file="layout/header.jsp"%>

<div class="container">
    <c:forEach var="board" items="${boards.content}">
        <div class="card m-2">
            <div class="card-body">
                <h4 class="card-title">${board.title}</h4>
                <a href="/board/${board.id}" class="btn btn-primary">상세보기</a>
            </div>
        </div>
    </c:forEach>
</div>

<!-- ✅ 페이징 + 모달 버튼 -->
<ul class="pagination justify-content-center mt-4">
    <!-- Previous -->
    <li class="page-item ${boards.first ? 'disabled' : ''}">
        <a class="page-link" href="?page=${boards.number - 1}">Previous</a>
    </li>

    <!-- 페이징 숫자 출력 -->
    <c:set var="startPage" value="${boards.number - (boards.number % 5)}"/>
    <c:set var="endPage" value="${startPage + 4}"/>
    <c:forEach begin="${startPage}" end="${endPage}" var="i">
        <c:if test="${i < boards.totalPages}">
            <li class="page-item ${i == boards.number ? 'active' : ''}">
                <a class="page-link" href="?page=${i}">${i + 1}</a>
            </li>
        </c:if>
    </c:forEach>

    <!-- ... 모달 버튼 -->
    <c:if test="${endPage + 1 < boards.totalPages}">
        <li class="page-item">
            <a class="page-link" href="#" data-toggle="modal" data-target="#pageModal">...</a>
        </li>
    </c:if>

    <!-- Next -->
    <li class="page-item ${boards.last ? 'disabled' : ''}">
        <a class="page-link" href="?page=${boards.number + 1}">Next</a>
    </li>
</ul>

<!-- ✅ 페이지 이동 모달 -->
<div class="modal fade" id="pageModal" tabindex="-1" aria-labelledby="pageModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="pageModalLabel">페이지 이동</h5>
                <button type="button" class="btn-close" data-dismiss="modal" aria-label="닫기"></button>
            </div>
            <div class="modal-body text-center">
                <input type="number" id="pageInput" min="1" max="${boards.totalPages}" class="form-control mb-3"
                       placeholder="1 ~ ${boards.totalPages} 입력" />
                <button type="button" class="btn btn-primary" onclick="goToPage()">이동</button>
            </div>
        </div>
    </div>
</div>

<!-- ✅ 페이지 이동 함수 -->
<script>
    function goToPage() {
        const input = document.getElementById("pageInput").value;
        const totalPages = ${boards.totalPages};
        const pageNum = parseInt(input);

        if (!isNaN(pageNum) && pageNum >= 1 && pageNum <= totalPages) {
            location.href = "?page=" + (pageNum - 1);
        } else {
            alert("1부터 " + totalPages + " 사이 숫자를 입력해주세요.");
        }
    }
</script>

<%@include file="layout/footer.jsp"%>
