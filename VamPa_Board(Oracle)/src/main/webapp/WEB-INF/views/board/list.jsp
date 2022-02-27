<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Insert title here</title>
</head>
<body>
<%-- <jsp:include page="../top.jsp" flush="false"/> 동적--%>
<%-- <%@ include file="../top.jsp" %> <!-- body에 가져온다. 정적--> --%>
<c:import url="../top.jsp" />
<h1>목록페이지입니다.</h1>
<div>
	<a href="/board/enroll" class="top_btn">게시판 등록</a>
</div>
	
<div class="container">
	<table class="table table-hover">
		<thead class="table-dark">
			<tr>
				<th scope="col">번호</th>
				<th scope="col">제목</th>
				<th scope="col">작성자</th>
				<th scope="col">작성일</th>
				<th scope="col">수정일</th>
			</tr>
		</thead>
		<!-- 컨트롤러에서 넘겨준list(왼쪽 달러)에 forEach를 적용하여 각 요소 하나의 이름을 list(오른쪽 var)로 쓸거다. -->
		<tbody> 
        <c:forEach items="${list}" var="list">
            <tr>
                <td><c:out value="${list.bno}"/></td>
                <td>
                    <a class="move" href='<c:out value="${list.bno}"/>'>
                        <c:out value="${list.title}"/>
                    </a>
                </td>
                <td><c:out value="${list.writer}"/></td>
                <td><fmt:formatDate pattern="yyyy/MM/dd" value="${list.regdate}"/></td>
                <td><fmt:formatDate pattern="yyyy/MM/dd" value="${list.updateDate}"/></td>
            </tr>
        </c:forEach>
        </tbody>
	</table>
    <div class="pageInfo_wrap" >
        <div class="pageInfo_area">
        	<ul id="pageInfo" class="pageInfo">
                <!-- 이전페이지 버튼 -->
                <c:if test="${pageMaker.prev}">
                    <li class="pageInfo_btn previous"><a href="${pageMaker.startPage-1}">Previous</a></li>
                </c:if>
		        <!-- 각 번호 페이지 버튼 -->
		        <c:forEach var="num" begin="${pageMaker.startPage}" end="${pageMaker.endPage}">
		        	<li class="pageInfo_btn ${pageMaker.cri.pageNum == num? 'acitve' : '' }"><a href="${num}">${num}</a></li>
		        </c:forEach>
                <!-- 다음페이지 버튼 -->
                <c:if test="${pageMaker.next}">
                    <li class="pageInfo_btn next"><a href="${pageMaker.endPage + 1 }">Next</a></li>
                </c:if>    
	        </ul>
        </div>
    </div>
</div>

<form id="moveForm" method="get">
	<input type="hidden" name="pageNum" value="${pageMaker.cri.pageNum }">
	<input type="hidden" name="amount" value="${pageMaker.cri.amount }">   
</form>

<style>
.acitve{
     background-color: #cdd5ec;
 }

</style>

<script>
    $(document).ready(function(){
    	let result = '<c:out value="${result}"/>';
    	checkAlert(result);
    	
    	/* 컨트롤러의 요청이 어떻게 처리되었는지 알려주는 함수 */
        function checkAlert(result){
            if(result === ''){
                return;
            }
            if(result === "enroll success"){
                alert("등록이 완료되었습니다.");
            }
            if(result === "modify success"){
                alert("수정이 완료되었습니다.");
            }
            if(result === "delete success"){
                alert("삭제가 완료되었습니다.");
            }
        }  
    	
    });
    
    
    let moveForm = $("#moveForm");
    
    /* 상세 글 이동 */
    $(".move").on("click", function(e){
        e.preventDefault();
        
        moveForm.append("<input type='hidden' name='bno' value='"+ $(this).attr("href")+ "'>");
        moveForm.attr("action", "/board/get");
        moveForm.submit();
    });
    
    
    /* 페이지 이동 */
    $(".pageInfo a").on("click", function(e){
        e.preventDefault();
        moveForm.find("input[name='pageNum']").val($(this).attr("href"));
        moveForm.attr("action", "/board/list");
        moveForm.submit();
        
    });
 
</script>

</body>


</html>