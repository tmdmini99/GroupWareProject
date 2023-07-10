<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<c:import url="../temp/header.jsp"></c:import>
<c:import url="../temp/style.jsp"></c:import>

</head>

<body>
	<div id="wrapper">
		<!-- sideBar -->
		<c:import url="../temp/sidebar.jsp"></c:import>
		<!-- Content Wrapper -->
		<div id="content-wrapper" class="d-flex flex-column">
			<div id="content">
				<c:import url="../temp/topbar.jsp"></c:import>

				<!-- contents 작성 -->
				<div class="container-fluid">
					<div class="row">
						<div class="card mb-4" style="width : 100%;">
							<div class="card-header">
								<c:choose>
									<c:when test="${checkNum eq 1}">
										<h3 class="text-center">승인 신청서</h3>
									</c:when>
									<c:otherwise>
										<h3 class="text-center">내 결재 정보</h3>
									</c:otherwise>
								</c:choose>
							</div>
							<div class="card-body mx-auto">
								<div
									class="datatable-wrapper datatable-loading no-footer sortable searchable fixed-columns">

									<div class="datatable-container">
										<div id="dd" class="card-body"></div>
										<form action="./approval" method="post" id="fm">
											<input type="hidden" name="fileName" value="${file}">
											<input type="hidden" name="ddd" id="ddd"> <input
												type="hidden" name="approval" id="approval"> <input
												type="hidden" name="id1" value="${id}">
											<%-- <input type="hidden" name="id2" value="${id}"> --%>
											<c:choose>
												<c:when test="${checkNum eq 1}">
													<button type="button" value="1" class="btn btn1">승인</button>
													<button type="button" value="0" class="btn btn1">거절</button>
													<a href="./information">돌아가기</a>
												</c:when>
												<c:otherwise>
													<c:if test="${confirm eq '대기'|| num >1}">
														<button type="button" id="del" data-id="${id}" class="btn">결재 취소</button>
													</c:if>
													<c:choose>
													<c:when test="${num >1}">
														<a href="./managerInformation">돌아가기</a>
													</c:when>
													<c:otherwise>
														<a href="./myInformation">돌아가기</a>
													</c:otherwise>
													</c:choose>
												</c:otherwise>
											</c:choose>
												<button type="button" id="pdf" data-id="${id}" class="btn">pdf 다운</button>
										</form>
									</div>

								</div>
							</div>
						</div>
					</div>


				</div>
			</div>
		</div>
	</div>




	<c:import url="../temp/footer.jsp"></c:import>
	<c:import url="../temp/logoutModal.jsp"></c:import>
	<c:import url="../temp/common_js.jsp"></c:import>


<script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.1/jquery.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/html2canvas/0.4.1/html2canvas.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/jspdf/1.0.272/jspdf.debug.js"></script>



<script src="https://html2canvas.hertzen.com/dist/html2canvas.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/jspdf/1.5.3/jspdf.debug.js"></script>


	<script type="text/javascript">
		$("#dd").load("/files/approval/${file}")
	</script>
	<script type="text/javascript" src="/js/approvalApproval.js">
		
	</script>
	
</body>
</html>