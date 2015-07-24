// 身份证验证
var checkIdNumber = (function () {
    var vcity = { 11: "北京", 12: "天津", 13: "河北", 14: "山西", 15: "内蒙古",
        21: "辽宁", 22: "吉林", 23: "黑龙江", 31: "上海", 32: "江苏",
        33: "浙江", 34: "安徽", 35: "福建", 36: "江西", 37: "山东", 41: "河南",
        42: "湖北", 43: "湖南", 44: "广东", 45: "广西", 46: "海南", 50: "重庆",
        51: "四川", 52: "贵州", 53: "云南", 54: "西藏", 61: "陕西", 62: "甘肃",
        63: "青海", 64: "宁夏", 65: "新疆", 71: "台湾", 81: "香港", 82: "澳门", 91: "国外"
    };

    //检查号码是否符合规范，包括长度，类型
    function isCardNo(card) {
        //身份证号码为15位或者18位，15位时全为数字，18位前17位为数字，最后一位是校验位，可能为数字或字符X
        return /(?:^\d{15}$)|(?:^\d{17}[\dX]$)/.test(card);
    }

    //取身份证前两位,校验省份
    function checkProvince(card) {
        var province = card.substr(0, 2);
        return typeof vcity[province] !== 'undefined';
    }

    //检查生日是否正确
    function checkBirthday(card) {
        var len = card.length;
        //身份证15位时，次序为省（3位）市（3位）年（2位）月（2位）日（2位）校验位（3位），皆为数字
        if (len === 15) {
            var re_fifteen = /^(\d{6})(\d{2})(\d{2})(\d{2})(\d{3})$/,
            arr_data = card.match(re_fifteen),
            year = arr_data[2],
            month = arr_data[3],
            day = arr_data[4],
            birthday = new Date('19' + year + '/' + month + '/' + day);
            return verifyBirthday('19' + year, month, day, birthday);
        }
        //身份证18位时，次序为省（3位）市（3位）年（4位）月（2位）日（2位）校验位（4位），校验位末尾可能为X
        else if (len === 18) {
            var re_eighteen = /^(\d{6})(\d{4})(\d{2})(\d{2})(\d{3})([\dX])$/,
            arr_data = card.match(re_eighteen),
            year = arr_data[2],
            month = arr_data[3],
            day = arr_data[4],
            birthday = new Date(year + '/' + month + '/' + day);
            return verifyBirthday(year, month, day, birthday);
        }
        return false;
    };

    //校验日期
    function verifyBirthday(year, month, day, birthday) {
        var now = new Date(),
        now_year = now.getFullYear();
        //年月日是否合理
        if (birthday.getFullYear() == year
            && (birthday.getMonth() + 1) == month
            && birthday.getDate() == day) {
            //判断年份的范围（3岁到100岁之间)
            var time = now_year - year;
            if (time >= 3 && time <= 100) {
                return true;
            }
            return false;
        }
        return false;
    };

    //校验位的检测
    function checkParity(card) {
        //15位转18位
        card = changeFivteenToEighteen(card);
        var arrInt = [7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2],
            arrCh = ['1', '0', 'X', '9', '8', '7', '6', '5', '4', '3', '2'],
            cardTemp = 0, i, valnum;
        for (i = 0; i < 17; i++) {
            cardTemp += card.substr(i, 1) * arrInt[i];
        }
        valnum = arrCh[cardTemp % 11];
        if (valnum == card.substr(17, 1)) {
            return true;
        }
        return false;
    }

    //15位转18位身份证号
    function changeFivteenToEighteen(card) {
        if (card.length === 15) {
            var arrInt = [7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2],
            arrCh = ['1', '0', 'X', '9', '8', '7', '6', '5', '4', '3', '2'],
            cardTemp = 0, i;
            card = card.substr(0, 6) + '19' + card.substr(6, card.length - 6);
            for (i = 0; i < 17; i++) {
                cardTemp += card.substr(i, 1) * arrInt[i];
            }
            card += arrCh[cardTemp % 11];
            return card;
        }
        return card;
    }

    function checkCard(card) {
        var flag = isCardNo(card) && checkProvince(card) && checkBirthday(card) && checkParity(card);
        return flag;
    }
    return {
        check: checkCard
    }
})();

jQuery.validator.unobtrusive.adapters.addBool('idnumber');

jQuery.validator.addMethod('idnumber',
        function (value, element, params) {
            return checkIdNumber.check(value);
        }, '请输入一个有效的身份证号码！');


// 手机号码验证

jQuery.validator.unobtrusive.adapters.addBool("mobile");

jQuery.validator.addMethod("mobile", function (value, element) {
    var length = value.length;
    var mobile = /^(((13[0-9]{1})|(15[0-9]{1}))+\d{8})$/
    return this.optional(element) || (length == 11 && mobile.test(value));
}, "手机号码格式错误");

// 电话号码验证   

jQuery.validator.unobtrusive.adapters.addBool("phone");

jQuery.validator.addMethod("phone", function (value, element) {
    var tel = /^(0[0-9]{2,3}\-)?([2-9][0-9]{6,7})+(\-[0-9]{1,4})?$/;
    return this.optional(element) || (tel.test(value));
}, "电话号码格式错误");

// 邮政编码验证   

jQuery.validator.unobtrusive.adapters.addBool("zipcode");

jQuery.validator.addMethod("zipcode", function (value, element) {
    var tel = /^[0-9]{6}$/;
    return this.optional(element) || (tel.test(value));
}, "邮政编码格式错误");

// QQ号码验证   

jQuery.validator.unobtrusive.adapters.addBool("qq");

jQuery.validator.addMethod("qq", function (value, element) {
    var tel = /^[1-9]\d{4,9}$/;
    return this.optional(element) || (tel.test(value));
}, "qq号码格式错误");

// IP地址验证

jQuery.validator.unobtrusive.adapters.addBool("ip");

jQuery.validator.addMethod("ip", function (value, element) {
    var ip = /^(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$/;
    return this.optional(element) || (ip.test(value) && (RegExp.$1 < 256 && RegExp.$2 < 256 && RegExp.$3 < 256 && RegExp.$4 < 256));
}, "Ip地址格式错误");

// 字母和数字的验证

jQuery.validator.unobtrusive.adapters.addBool("chrnum");

jQuery.validator.addMethod("chrnum", function (value, element) {
    var chrnum = /^([a-zA-Z0-9]+)$/;
    return this.optional(element) || (chrnum.test(value));
}, "只能输入数字和字母(字符A-Z, a-z, 0-9)");

// 中文的验证

jQuery.validator.unobtrusive.adapters.addBool("chinese");

jQuery.validator.addMethod("chinese", function (value, element) {
    var chinese = /^[\u4e00-\u9fa5]+$/;
    return this.optional(element) || (chinese.test(value));
}, "只能输入中文_你没自定义错误信息！");

// 下拉框验证

jQuery.validator.unobtrusive.adapters.add("selectnone", [],
    function (options) {
        options.rules["selectnone"] = true;
        if (options.message) {
            options.messages["selectnone"] = options.message;
        }
    });

$.validator.addMethod("selectnone", function (value, element) {
    return value == "请选择";
}, "必须选择一项");

//验证一组checkbox必须至少选中一个。
jQuery.validator.unobtrusive.adapters.addBool("selectmore");

jQuery.validator.addMethod("selectmore", function (value, element) {
    var $this = $(element);
    var name = $this.attr("name");
    var selectCount = $("[name='" + name + "']:checked").length;
    alert(selectCount);
    if (selectCount == 0) {
        $("[name='" + name + "']").attr({ style: "background-color:Red" });
        return false;
    }
    else {
        $("[name='" + name + "']").removeClass("input-validation-error");
        return true;
    }

}, "请至少选择一个_默认提示");

// 字节长度验证

jQuery.validator.unobtrusive.adapters.addBool("ip");

jQuery.validator.addMethod("byteRangeLength", function (value, element, param) {
    var length = value.length;
    for (var i = 0; i < value.length; i++) {
        if (value.charCodeAt(i) > 127) {
            length++;
        }
    }
    return this.optional(element) || (length >= param[0] && length <= param[1]);
}, $.validator.format("请确保输入的值在{0}-{1}个字节之间(一个中文字算2个字节)"));