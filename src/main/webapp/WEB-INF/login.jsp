<!--
Author: W3layouts
Author URL: http://w3layouts.com
License: Creative Commons Attribution 3.0 Unported
License URL: http://creativecommons.org/licenses/by/3.0/
-->
<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Shopper an E-Commerce online Shopping Category Flat Bootstarp responsive Website Template| Login ::
        w3layouts</title>
    <link href="/resources/css/bootstrap.css" rel='stylesheet' type='text/css'/>
    <!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
    <script src="http://ajax.googleapis.com/ajax/libs/jquery/2.1.1/jquery.min.js"></script>
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
    <!---- tabs---->
    <link type="text/css" rel="stylesheet" href="/resources/css/easy-responsive-tabs.css"/>
    <script src="/resources/js/easyResponsiveTabs.js" type="text/javascript"></script>
    <script type="text/javascript">
        $(document).ready(function () {
            $('#horizontalTab').easyResponsiveTabs({
                type: 'default', //Types: default, vertical, accordion
                width: 'auto', //auto or any width like 600px
                fit: true,   // 100% fit in a container
                closed: 'accordion', // Start closed if in accordion view
                activate: function (event) { // Callback function if tab is switched
                    var $tab = $(this);
                    var $info = $('#tabInfo');
                    var $name = $('span', $info);
                    $name.text($tab.text());
                    $info.show();
                }
            });

            $('#verticalTab').easyResponsiveTabs({
                type: 'vertical',
                width: 'auto',
                fit: true
            });
        });
    </script>
    <!---- tabs---->

</head>
<body>
<!---->
<div class="header">
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
                <a href="#"><i class="c2"></i></a>
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
<div class="login">
    <c:if test="${not empty requestScope.errorMessages}">
        <div class="alert alert-danger text-center">
            <c:forEach items="${ requestScope.errorMessages}" var="mesg">
                <strong>Oops! Something got wrong... </strong><c:out value="${mesg}"/>
            </c:forEach>
        </div>
    </c:if>
    <div class="container">
        <h2>Login</h2>
        <div class="col-md-6 log">
            <p>Welcome, please enter the folling to continue.</p>
            <p>If you have previously Login with us, <span>click here</span></p>
            <form:form method="POST" action="/login/process/" modelAttribute="userDTO">
                <h5>User Name:</h5>
                <form:input path="email"/>
                <h5>Password:</h5>
                <form:password path="password"/>
                <input type="submit" value="Login">
                <a href="#">Forgot Password ?</a>
            </form:form>
        </div>
        <div class="col-md-6 login-right">
            <h3>NEW REGISTRATION</h3>
            <p>By creating an account with our store, you will be able to move through the checkout process faster,
                store multiple shipping addresses, view and track your orders in your account and more.</p>
            <a class="acount-btn" href="registration.html">Create an Account</a>
        </div>
        <div class="clearfix"></div>

        <div class="navigation">
            <ul>
                <li><a href="about.html">ABOUT</a></li>
                <li><a href="woman.html">STOCKITS</a></li>
                <li><a href="contact.html">CONTACT</a></li>
                <li><a href="man.html">STORE</a></li>
                <li><a href="terms.html">TERMS & CONDITION</a></li>
                <li><a href="man.html">SHOW TO BUY</a></li>
                <li><a href="404.html">SHIPPING</a></li>
                <li><a href="404.html">RETURNS</a></li>
                <li><a href="single.html">SIZE CHART</a></li>
            </ul>
        </div>
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
		