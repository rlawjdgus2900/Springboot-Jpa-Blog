<%@page language="java" contentType="text/html; charset=UTF-8"
        pageEncoding="UTF-8" %>

<%@include file="../layout/header.jsp"%>

<div class="container">
    <form action="/auth/loginProc" method="post">
        <div class="form-group">
            <label for="title">제목:</label>
            <input type="text"  class="form-control" placeholder="Enter title" id="title">
        </div>

        <div class="form-group">
            <label for="comment">내용:</label>
            <textarea class="form-control summernote" rows="5" id="comment"></textarea>
        </div>

        <button id="btn-save" class="btn btn-primary">글등록</button>
    </form>
</div>

<script>
    $('.summernote').summernote({
        placeholder: '내용을 입력해주세요.',
        tabsize: 2,
        height: 300
    });
</script>
<%@include file="../layout/footer.jsp"%>
