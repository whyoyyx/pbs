<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="../common/tag.jsp" %>
<%@ include file="../common/include.jsp" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <script src="${ctx}/js/user/user.js" type="text/javascript"></script>
</head>
<body>
<div class="memSuper">
    <div class="memSuper-main">
        <div class="page-content ng-scope">
            <div class="panel panel-default">
                <div class="panel-body">
                    <div class="form-group row mb15">
                        <div class="col-sm-4">
                            <div class="input-group">
                                <span class="input-group-addon input-group-onlytext">名称：</span>
                                <input id="name" type="text" class="form-control dropdown-toggle ng-pristine ng-valid"
                                       placeholder="用户名称">
                            </div>
                        </div>
                        <div class="col-sm-2">
                            <a class="btn btn-primary" style="width: 90px;" id="search-button">
                                <i class="fa fa-search"></i>
                                <span class="btn-text">查询</span>
                            </a>
                        </div>
                        <sec:authorize ifAnyGranted="ADMINISTRATOR,COMPANY_SHAREHOLDER,AREA_AGENT">
                            <div class="col-sm-2">
                                <a class="btn btn-primary" style="width: 90px;" href="${ctx}/user/edit">
                                    <i class="fa fa-edit"></i>
                                    <span class="btn-text">新建</span>
                                </a>
                            </div>
                        </sec:authorize>
                    </div>
                    <div id="result" style="margin: 0 30px;"></div>
                </div>
                <div class="panel-heading">
                    用户列表
                </div>
            </div>
        </div>
    </div>
</div>

</div>
</body>
</html>
<%@ include file="../common/confirm.jsp" %>