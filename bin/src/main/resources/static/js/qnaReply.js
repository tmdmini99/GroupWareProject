/**
 * 
 */
const replyAdd = document.getElementById("replyAdd");
const replyContents = document.getElementById("replyContents");
const commentListResult = document.getElementById("commentListResult")
const contentsConfirm = document.getElementById("contentsConfirm")
const dropdownButton = document.getElementById("dropdownButton")

// $(document).ready(function() {
//     console.log("시발")
//     $('#dropdownButton').on('click', function() {
//       $(this).siblings('.dropdown-menu').toggle();
//     });
//   });
//  $(document).on("click",".dropdown-toggle",function(e) {
//    console.log("클릭");
    
    
//    $('#dropdownButton').on('click', function(){
 
//         $(this).siblings('.dropdown-menu').toggle();
//     });

    
// });
$(document).on("click", "#dropdownButton", function(e) {
    let dropdownMenu = $(this).siblings('.dropdown-menu');
    dropdownMenu.toggle();
    $(".edit").each(function(){
        $(this).attr('disabled', true);
    })
    $(".upup").each(function(){
        $(this).attr('disabled', true);
    })
    $("input").each(function (upup, item) {
        if($(item).get()==$(this).get()){
        }
    })
});


// $(document).on("click", function(e) {
//     let dropdownMenu = $(".dropdown-menu");
//     if (!dropdownMenu.is(e.target) && dropdownMenu.has(e.target).length === 0) {
//         dropdownMenu.hide();
//         $(".edit").each(function(){
//             $(this).attr('disabled', false);
//         })
//         $(".upup").each(function(){
//             $(this).attr('disabled', false);
//         })
//         $("input").each(function (upup, item) {
//             if($(item).get()==$(this).get()){
//             }
//         })
//     }
// });







$(document).on("click", ".upup", function(e) {
    console.log("clsdf");
    console.log( $(this).parents().prev(".text"));
    console.log( $(this));
    console.log( $(this).parents());
    $(this).parents().prev(".text").html('<textarea class="form-control" id="contents"></textarea>')
    $(this).text("답글쓰기")
    $(this).addClass("down")
    $(this).removeClass("upup")
    $(".edit").each(function(){
        $(this).attr('disabled', true);
    })
    $(".upup").each(function(){
        $(this).attr('disabled', true);
    })
    $("input").each(function (upup, item) {
        if($(item).get()==$(this).get()){
            console.log("클릭");
        }
    
        // var result = '';
    
        // result += index +' : ' + item.title + ', ' + item.url;
    
        // console.log(result);
    
      
    
    })
    // $.ajax({
    //     url: "/qnaComment/delete",
    //     type: "POST",
    //     data: {
    //         id: id
    //     },
    //     success: function() {
    //         alert('댓글이 삭제 되었습니다');
    //         getList();
    //     },
    //     error: function() {
    //         alert('삭제가 실패했습니다');
    //     }
    // });
});
$(".down")
$(document).on("click", ".down", function(e) {
  let id =$(this).attr("data-qna-down")
  
  let contents = $("#contents").val()
  console.log(contents)
  
    $.ajax({
        url: "/qnaComment/reply",
        type: "POST",
         data: {
            id: id,
            contents : contents,


         },
        success: function(result) {
        let userId = result
            Swal.fire({
     icon: 'success',
     title: '성공',
     text: '버튼을 누르면 다음 페이지로 이동합니다.',
     showClass: {
           popup: 'animate__animated animate__fadeInDown'
         },
         hideClass: {
           popup: 'animate__animated animate__fadeOutUp'
         }
       }).then(result => {
            if (result.isConfirmed) {
           
            $.ajax({
                type:"GET",
                url:"/trigger-event",
                data:{
                    userId : userId,
                    
                },
                success : function(data){	
                    console.log(data);
                    getList();
                },
               error : function(request, status, error) { // 결과 에러 콜백함수
                   getList();
               }
            })
            }
            });
             getList();
        },
         error: function() {
             alert('댓글 등록이 실패했습니다');
         }
     });    
});


replyAdd.addEventListener("click", function(){


	console.log(replyAdd.getAttribute('data-qna-qnaId'));
	
    let xhttp = new XMLHttpRequest();

    xhttp.open("POST", "../qnaComment/add");

    xhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");

    xhttp.send("contents="+replyContents.value+"&qnaId="+replyAdd.getAttribute('data-qna-qnaId'));

    xhttp.addEventListener('readystatechange', function(){
        if(this.readyState==4&&this.status==200){
            if(this.responseText.trim()>0){
            let userId = this.responseText.trim();
            	 Swal.fire({
	      icon: 'success',
	      title: '성공',
	      text: '버튼을 누르면 다음 페이지로 이동합니다.',
	      showClass: {
	    	    popup: 'animate__animated animate__fadeInDown'
	    	  },
	    	  hideClass: {
	    	    popup: 'animate__animated animate__fadeOutUp'
	    	  }
	    	}).then(result => {
                 if (result.isConfirmed) {
                
	    		 $.ajax({
	    		 	type:"GET",
	    		 	url:"/trigger-event",
	    		 	data:{
	    		 		userId : userId,
	    		 		
	    		 	},
	    		 	success : function(data){	
	    		 		console.log(data);
	    		 		getList();
	    		 	},
                    error : function(request, status, error) { // 결과 에러 콜백함수
                        getList();
                    }
	    		 })
	    		 }
                 getList();
	    		 });
	    		 
        	}else {
            	alert('댓글 등록에 실패 했습니다')
       		}
        }
    })

})



getList();

function getList(){
    let xhttp = new XMLHttpRequest();

    xhttp.open("GET", "/qnaComment/list?qnaId="+replyAdd.getAttribute('data-qna-qnaId'));

    xhttp.send();

    xhttp.addEventListener("readystatechange", function(){
        if(this.readyState==4&&this.status==200){
           commentListResult.innerHTML=this.responseText.trim();
        }
    })
}


//댓글 삭제
$("#commentListResult").on("click", ".del", function(e) {
    console.log("클릭");
    let id = $(this).attr("data-qna-qna");
    
    Swal.fire({
        title: '정말로 삭제하시겠습니까?',
        text: '한번 삭제되면 다시 되돌릴 수 없습니다.',
        icon: 'warning',
        
        showCancelButton: true, // cancel버튼 보이기. 기본은 원래 없음
        confirmButtonColor: '#3085d6', // confrim 버튼 색깔 지정
        cancelButtonColor: '#d33', // cancel 버튼 색깔 지정
        confirmButtonText: '승인', // confirm 버튼 텍스트 지정
        cancelButtonText: '취소', // cancel 버튼 텍스트 지정
        
        reverseButtons: true, // 버튼 순서 거꾸로
        
     }).then(result => {
        // 만약 Promise리턴을 받으면,
        if (result.isConfirmed) { // 만약 모달창에서 confirm 버튼을 눌렀다면
            $.ajax({
                url: "/qnaComment/delete",
                 type: "POST",
                  data: {
                      id: id
                },
                success: function() {
                    Swal.fire({
                        title: '삭제 되었습니다',
                        icon: 'success',
                        
                        
                        
                     }).then(result => {
                        // 만약 Promise리턴을 받으면,
                        getList();
                        location.reload(); 
                       
                     });
                 },
                error: function() {
                    alert('삭제가 실패했습니다');
                 }
             });
                       
        }
     });
     
});

$(document).on("click", ".clear",function() {
    // 수정 완료 버튼 클릭 시 동작
    let id = $(this).attr("data-comment-num");
    let contents = $(this).parents().prev().prev().children("span").children("textarea").val();
    console.log(contents)
    let con = $(this).parents().prev().prev().children("span");
    console.log($(this).parents().prev().prev().children("span").children("textarea").val());
    console.log("수정 완료 동작");
    $.ajax({
        url: "/qnaComment/update",
        type: "POST",
        data: {
            id: id,
            contents: contents
        },
        success: function(data) {
           
            if(data == 1){
            alert('댓글이 수정되었습니다.');
            $("#closeModal").click();
            getList();
        }else{
            alert('댓글 수정을 실패했습니다.');
        }
        },
        error: function() {
            alert('관리자 문의 필요.');
        }
    });
});

//수정 버튼 클릭시
$(document).on("click", ".edit", function(e) {
    
    $(this).addClass("clear");

$(this).removeClass("edit");
$(".edit").each(function(){
    $(this).attr('disabled', true);
})
$(".upup").each(function(){
    $(this).attr('disabled', true);
})

    // 수정 가능한 상태로 변경
   console.log($(this).parents().prev().prev().children("span").text());
   $(this).parents().prev().prev().children("span").removeClass("form-control")
    $(this).parents().prev().prev().children("span").html('<textarea class="form-control">'+$(this).parents().prev().prev().children("span").text()+'</textarea>');
    // 버튼 텍스트 변경

    console.log($(this).parents().prev().prev().children("span").children("textarea").text());
    

    $(this).text("수정 완료");
    
    // 수정 완료 버튼 클릭 이벤트 핸들러 등록
   
});


//수정
$("#updatebtn").click(function(){
    $(".change").each(function(index,data){

        $(data).prop("readonly",false)
        $(data).removeClass("change")
    })
    $("#updatebtn").text("수정완료")
    if(!click){
        click=true;
    }
    else{
        $("#updateForm").submit();
    }
    
})
//페이저 처리
$(document).on("click", ".page-link",function(){
    console.log("클릭");
    let on = $(this).attr('data-board-page');
    console.log(on);
function getList(){
    let xhttp = new XMLHttpRequest();

    xhttp.open("GET", "/qnaComment/list?qnaId="+replyAdd.getAttribute('data-qna-qnaId')+"&&page="+on);

    xhttp.send();

    xhttp.addEventListener("readystatechange", function(){
        if(this.readyState==4&&this.status==200){
           commentListResult.innerHTML=this.responseText.trim();
        }
    })}
    getList();
})


