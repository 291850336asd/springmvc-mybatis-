//存放主要交互逻辑
//模块化
var seckill = {
    URL:{
        now: function () {
            return '/seckill/time/now';
        },
        exposer: function(seckillId){
            return '/seckill/' + seckillId + '/exposer';
        },
        execution: function(seckillId, md5){
            return '/seckill/' + seckillId + '/' + md5 + '/execution';
        }
    },

    handleSeckill:function (seckillId, node) {
        node.hide().html('<button class="btn bg-primary btn-lg" id="killBtn">开始秒杀</button>');
        $.post(seckill.URL.exposer(seckillId),{}, function(result){
            if(result && result['success']){
                var exposer = result['data'];

                if(exposer['exposed']){
                    //开启秒杀
                    //获取秒杀地址
                    var md5 = exposer['md5'];

                    var killUrl = seckill.URL.execution(seckillId, md5);
                    //只绑定一次点击事件
                    $('#killBtn').one('click', function(){
                        //禁用点击按钮
                        $(this).addClass('disabled');
                        //发送秒杀请求开始秒杀
                        $.post(killUrl, {}, function(result){
                            if(result && result['success']){
                                var killResult = result['data'];
                                var state = killResult['state'];
                                var stateInfo = killResult['stateInfo'];
                                node.html('<span class="label label-success">' + stateInfo + '</span>');
                            }else{
                                var killResult = result['data'];
                                var stateInfo = killResult['stateInfo'];
                                node.html('<span class="label label-success">' + stateInfo + '</span>');
                            }
                        });
                    });
                    node.show();
                }else{

                    var now = exposer['now'];
                    var start = exposer['start'];
                    var end = exposer['end'];
                    seckill.countDown(seckillId, now, start, end);
                }
            }else{
                console.log('result : ' + result);
            }
        });

    },
    countDown:function (seckillId, nowTime,startTime,endTime) {
        var seckillBox = $('#seckill-box');
        if(nowTime > endTime){
            seckillBox.html('秒杀结束');
        }else if(nowTime < startTime){
            //秒杀未开始，计时事件绑定
            var killTime = new Date(startTime + 1000);
            seckillBox.countdown(killTime, function(event){
                //界面时间格式
                var format = event.strftime('秒杀倒计时： %D天 %H时 %M分 %S秒');
                seckillBox.html(format);
            }).on('finish.countdown', function(){
                //时间完成回调
                seckill.handleSeckill(seckillId, seckillBox);
            });
        }else{
            //秒杀开始
           seckill.handleSeckill(seckillId, seckillBox);
        }
    },

    detail:{
        //详情页初始化
        init: function (params) {
            //用户手机验证登录，计时交互
            //规划交互流程
            //在cooKie中查找手机号
            var killPhone = $.cookie('killPhone');
            var startTime = params['startTime'];
            var endTime = params['endTime'];
            var seckillId = params['seckillId'];

            //验证手机号
            if(!seckill.validatePhone(killPhone)){
                //未登录则绑定手机号
                var killPhoneModal = $('#killPhoneModal');
                //显示弹出层
                killPhoneModal.modal({
                    show:true, //显示弹出层
                    backdrop:'static',//禁止位置关闭
                    keyboard:false//关闭键盘事件
                });
                $('#killPhoneBtn').click(function () {
                    var inputPhone = $('#killphoneKey').val();
                    if (seckill.validatePhone(inputPhone)) {
                        $.cookie('killPhone', inputPhone, {expires: 7, path: '/seckill'});
                        window.location.reload();
                    } else {
                        $('#killphoneMessage').hide().html('<label class="label label-danger">手机号错误！</label>').show(300);
                    }
                });
            }

            //已登录
            //计时交互
            $.get(seckill.URL.now(), {},function (result) {
               if(result && result['success']){
                   var nowTime = result['data'];
                   seckill.countDown(seckillId, nowTime, startTime, endTime);
               }
            });
        }
    },
    //验证手机号
    validatePhone: function (phone) {
        if(phone && phone.length == 11 && !isNaN(phone)){
            return true;
        }
        return false;
    }


}