let index = {
    init: function (){
        $("#btn-save").on("click", ()=>{
            this.save();
        });
        $("#btn-delete").on("click", () =>{
            this.deleteById();
        });
        $("#btn-update").on("click", () =>{
            this.update();
        });
        $("#btn-reply-save").on("click", () =>{
            this.replySave();
        });
    },

    save: function (){
        let data = {
            title: $("#title").val(),
            content: $("#content").val()
        };

        $.ajax({
            type: "POST",
            url: "/api/board",
            data: JSON.stringify(data),
            contentType: "application/json; charset=utf-8",
            dataType: "json"
        }).done(function (resp){
            alert("글쓰기가 완료되었습니다.");
            location.href = "/";
        }).fail(function (xhr, status, error){
            alert("글쓰기 실패: " + error);
        });
    },
    deleteById: function (){
        let id =$("#id").text();

        $.ajax({
            type: "DELETE",
            url: "/api/board/"+id,
            dataType: "json"
        }).done(function (resp){
            alert("삭제가 완료되었습니다.");
            location.href = "/";
        }).fail(function (xhr, status, error){
            alert("삭제 실패 " + error);
        });
    },
    update: function (){
        let id = $("#id").val();
        let data = {
            title: $("#title").val(),
            content: $("#content").val()
        };

        $.ajax({
            type: "PUT",
            url: "/api/board/"+id,
            data: JSON.stringify(data),
            contentType: "application/json; charset=utf-8",
            dataType: "json"
        }).done(function (resp){
            alert("글수정이 완료되었습니다.");
            location.href = "/";
        }).fail(function (xhr, status, error){
            alert("글쓰기 실패: " + error);
        });
    },
    replySave: function (){
        let data = {
            userId: $("#userId").val(),
            boardId: $("#boardId").val(),
            content: $("#reply-content").val()
        };

        $.ajax({
            type: "POST",
            url: `/api/board/${data.boardId}/reply`,
            data: JSON.stringify(data),
            contentType: "application/json; charset=utf-8",
            dataType: "json"
        }).done(function (resp){
            alert("댓글작성이 완료되었습니다.");
            location.href = `/board/${data.boardId}`;
        }).fail(function (xhr, status, error){
            alert("댓글작성 실패: " + error);
        });
    },
    replyDelete: function (boardId, replyId){
        $.ajax({
            type: "DELETE",
            url: `/api/board/${boardId}/reply/${replyId}`,
            dataType: "json"
        }).done(function (resp){
            alert("댓글삭제가 완료되었습니다.");
            location.href = `/board/${boardId}`;
        }).fail(function (xhr, status, error){
            alert("댓글삭제 실패: " + error);
        });
    },

}

index.init();
