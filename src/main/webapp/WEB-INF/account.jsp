<!--
Author: W3layouts
Author URL: http://w3layouts.com
License: Creative Commons Attribution 3.0 Unported
License URL: http://creativecommons.org/licenses/by/3.0/
-->
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Shopper an E-Commerce online Shopping Category Flat Bootstarp responsive Website Template| Woman ::
        w3layouts</title>
    <link href="/resources/css/bootstrap.css" rel='stylesheet' type='text/css'/>
    <!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
    <script src="/resources/js/jquery.min.js"></script>
    <script src="/resources/js/custom.js"></script>
    <!-- Custom Theme files -->
    <link href="/resources/css/hover.css" rel="stylesheet" media="all">
    <link href="/resources/css/style.css" rel="stylesheet" type="text/css" media="all"/>
    <!-- Custom Theme files -->
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <meta name="keywords" content="Shopper Responsive web template, Bootstrap Web Templates, Flat Web Templates, Andriod Compatible web template, 
Smartphone Compatible web template, free webdesigns for Nokia, Samsung, LG, SonyErricsson, Motorola web design"/>
    <script type="application/x-javascript"> addEventListener("load", function () {
        setTimeout(hideURLbar, 0);
    }, false);

    function hideURLbar() {
        window.scrollTo(0, 1);
    } </script>
    <!--webfont-->
    <link href='http://fonts.googleapis.com/css?family=Lato:100,300,400,700,900' rel='stylesheet' type='text/css'>
    <script src="http://ajax.googleapis.com/ajax/libs/jquery/2.1.1/jquery.min.js"></script>

</head>
<body>
<!---->
<div class="header men">
    <div class="container">
        <div class="header-left">
            <div class="top-menu">
                <ul>
                    <li><a href="/">HOME</a></li>
                    <c:if test="${not empty sessionScope.currentUser}">
                        <li><a href="/account/">MY ACCOUNT</a></li>
                    </c:if>
                    <li><a href="/evaluate/">GO EVALUATION</a></li>
                </ul>
            </div>
        </div>
        <div class="logo">
            <a href="index.html"><img src="/resources/images/logo.png" alt=""/></a>
        </div>
        <div class="header-right">
            <div class="currency">
                <a href="#"><i class="c1"></i></a>
                <a class="active" href="#"><i class="c2"></i></a>
                <a href="#"><i class="c3"></i></a>
                <a href="#"><i class="c4"></i></a>
            </div>
            <div class="signin">
                <div class="cart-sec">
                    <a href="cart.html"><img href="cart.html" src="/resources/images/cart.png" alt=""/>(0)</a></div>
                <ul>
                    <li><a href="registration.html">REGISTRATION</a> <span>/<span> &nbsp;</li>
                    <li><a href="login.html"> LOGIN</a></li>
                </ul>
            </div>
        </div>
        <div class="clearfix"></div>
    </div>
</div>
<!---->
<div class="men-fashions">
    <div class="container">
        <div class="col-md-9 fashions">
            <div class="title js-rate-table group-ranking">
                <table border="1">
                    <caption>Risk groups</caption>
                    <tr>
                        <th>Group</th>
                        <th>Rate</th>
                    </tr>
                    <form id="group_ranking" action="/account/update/group_rates/" method="POST">
                        <c:forEach items="${requestScope.riskGroupRates}" var="rgr" varStatus="number">
                            <tr>
                                <td><c:out value="${rgr.riskGroup.name}"/></td>
                                <td><input name="rank${rgr.riskGroup.id}" type="number" min="1" max="5"
                                           value="${rgr.rate}"/></td>
                            </tr>
                        </c:forEach>
                    </form>
                </table>
                <input type="submit" form="group_ranking">
            </div>
            <div class="title js-rate-table interval-ranking" style="display: none;">
                <table border="1">
                    <caption>Assessment limits</caption>
                    <tr>
                        <th>Assessment</th>
                        <th>Limit</th>
                    </tr>
                    <form id="assessment_ranking" action="/account/update/assessment_limits/" method="POST">
                        <c:forEach items="${requestScope.assessmentLimits}" var="al">
                            <tr>
                                <td><c:out value="${al.assessment}"/></td>
                                <td><input name="rank${al.assessment}" type="number" min="1" max="100"
                                           value="${al.border}"/></td>
                            </tr>
                        </c:forEach>
                    </form>
                </table>
                <input type="submit" form="assessment_ranking">
            </div>
        </div>
        <div class="col-md-3 side-bar account-menu">
            <div class="categories">
                <h3>MENU</h3>
                <ul>
                    <li><a class="js-group-ranking">Group ranking</a></li>
                    <li><a class="js-interval-ranking">Interval ranking</a></li>
                </ul>
            </div>
        </div>
        <div class="clearfix"></div>
    </div>
</div>
<!---->
<div class="footer">
    <div class="container">
        <p>Copyright &copy; 2015 All rights reserved | Template by <a href="http://w3layouts.com"> W3layouts</a></p>
        <div class="social">
            <a href="#"><span class="icon1"></span></a>
            <a href="#"><span class="icon2"></span></a>
            <a href="#"><span class="icon3"></span></a>
            <a href="#"><span class="icon4"></span></a>
        </div>
        <div class="clearfix"></div>
    </div>
</div>
<!---->
</body>
</html>