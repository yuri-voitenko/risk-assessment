<!--
Author: W3layouts
Author URL: http://w3layouts.com
License: Creative Commons Attribution 3.0 Unported
License URL: http://creativecommons.org/licenses/by/3.0/
-->
<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Shopper an E-Commerce online Shopping Category Flat Bootstarp responsive Website Template| Man ::
        w3layouts</title>
    <link href="/resources/css/bootstrap.css" rel='stylesheet' type='text/css'/>
    <!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
    <script src="/resources/js/jquery.min.js"></script>
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
            <a href="/"><img src="/resources/images/logo.png" alt=""/></a>
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
                    <li><a href="/login/"> LOGIN</a></li>
                </ul>
            </div>
        </div>
        <div class="clearfix"></div>
    </div>
</div>
<!---->
<div class="men-fashions">
    <div class="container">
        <table border="1">
            <caption>Filled risks table</caption>
            <tr>
                <th>#</th>
                <th>RiskGroup</th>
                <th>Description</th>
                <th>probability</th>
                <th>damage</th>
            </tr>
            <form id="filled_risks" action="/evaluate/process" method="POST">
                <c:forEach items="${requestScope.allRisks}" var="item" varStatus="number">
                    <tr>
                        <td><c:out value="${item.id}"/></td>
                        <td><c:out value="${item.group.name}"/></td>
                        <td><c:out value="${item.description}"/></td>
                        <td><input name="probability${item.id}" type="number" min="1" max="5" value="1"/></td>
                        <td><input name="damage${item.id}" type="number" min="1" max="5" value="1"/></td>
                    </tr>
                </c:forEach>
            </form>
        </table>
        <input type="submit" form="filled_risks">
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
		