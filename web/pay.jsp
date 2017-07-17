<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no">
    <title>js sdk 调起页面</title>

</head>
<body>
<input id="money" name="money" type="text" placeholder="请输入金额"/><br>
<input type="button" id="sub" value="提交">
</body>
</html>
<script type="text/javascript" src="http://code.jquery.com/jquery-1.4.2.min.js"></script>
<script type="text/javascript" src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
<script>
    function submit(){alert(1)
        var data = {a:$('#money').val()};
        $.ajax({
            type: 'POST',
            dataType:"json",
            url: 'TopayServlet',
            data:data,
            success: function(result){
                console.log(result);
                var appId = result.appId1;
                var timeStamp = result.timeStamp1;
                var nonceStr = result.nonceStr1;
                var package = result.package1;
                var paySign = result.finalsign1;
                WeixinJSBridge.invoke(
                    'getBrandWCPayRequest', {
                        "appId":appId,     //公众号名称，由商户传入
                        "timeStamp":timeStamp,         //时间戳，自1970年以来的秒数
                        "nonceStr":nonceStr, //随机串
                        "package":package,
                        "signType":"MD5",         //微信签名方式：
                        "paySign":paySign //微信签名
                    },
                    function(res){
                        WeixinJSBridge.log(res.err_msg);
                        if(res.err_msg == "get_brand_wcpay_request:ok"){
                            <!--支付成功调用-->
                            <!--history.go(0);   -->
                            //alert("成功");
                        }else if(res.err_msg == "get_brand_wcpay_request:cancel"){
                            <!--取消支付调用-->
                            //alert("取消");

                        }else{
                            <!--支付失败-->
                            //alert("失败");

                        }
                    }
                );
            } ,
            dataType: "json"});

    }

    $(function(){
        $("#sub").click(function(){
            submit();
        });
    });

</script>