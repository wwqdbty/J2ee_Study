var common = {
    /**
     * 清空parentElement下所有input, select, textarea
     *
     * @param jqParentElement 父节点jquery对象
     *
     * @author WangWenQiang
     */
    clearElements: function (jqParentElement) {
        jqParentElement.find("input, select, textarea").each(function () {
            var type = this.type;
            var tag = this.tagName.toLowerCase();
            if (type == "text" || type == "password" || type == "hidden" || tag == "textarea") {
                this.value = "";
            } else if (type == "checkbox" || type == "radio") {
                this.checked = false;
            } else if (tag == "select") {
                this.selectedIndex = 0;
            }
        });
    },

    /**
     * 取出左右空格并将新值设置至表单文本项
     */
    spaceRemove: function spaceRemove() {
        // 页面所有input标签
        var inputElements = document.getElementsByTagName("INPUT");
        for (var i = 0; i < inputElements.length; i++) {
            // 获取每个input标签
            var inputElement = inputElements[i];
            if (inputElement.type == "text" || inputElement.type == "TEXT" || inputElement.type == "") {
                inputElement.value = inputElement.value.trim();
            }
        }
    },

    /**
     * form数据变更校验, 页面表单项目需预将修改前数据进行缓存, 不存在缓存项则直接返回true
     *
     * @param formId
     *
     * @return {Boolean} 发生了变更:true, 否则返回false
     */
    formDataIsChanged: function (formId) {
        var isChanged = false;

        var jqElement = $("#" + formId + " [data-oldValue]");
        // 不存在缓存项则直接返回true
        if (!jqElement || jqElement.length == 0) {
            return true;
        }

        // 设置修改标志位
        jqElement.each(function (i, currentElement) {
            var value = $(currentElement).val();
            var oldValue = $(currentElement).attr("data-oldValue");

            // 发生了变更
            if (value != oldValue) {
                isChanged = true;
                $(currentElement).attr("data-isChanged", "true");
            }
        });

        return isChanged;
    },

    /**
     * 提交表单时, 选择发生变更的项进行提交
     *
     * @param formId
     */
    saveData: function (formId, fnCallBack) {
        var jsonReqData = {};
        // 存在ID则为修改操作
        var isChangedValidateFlag = $("#hid_id").val() ? true : false;
        // 是否发生变更验证执行时机-修改操作时, 验证表单是否发生过变更
        if (isChangedValidateFlag) {
            if (!common.formDataIsChanged()) {
                // 直接成功
                alert("保存成功");
                return;
            }
            jsonReqData = $("#" + formId + " [isChanged='true'], # + " + formId + " input[type='hidden']").serialize();
            /* 精英英语项目中的写法
             jsonReqData = $("#form_addOrUpdateCategory [isChanged='true'], #form_addOrUpdateCategory input[type='hidden']").serialize(); */
        } else {
            jsonReqData = $("#" + formId).serialize();
            /* 精英英语项目中的写法
             jsonReqData = $("#form_addOrUpdateCategory").serialize(); */
        }
        // ajax提交, data的内容即为jsonReqData
        fnCallBack(jsonReqData);
    }
}


/* ------------------------------------------------------------------------------ String相关START ------------------------------------------------------------------------------ */
/**
 * 去除字符串中左空字符串
 */
String.prototype.trimLeft = function () {
    return this.replace(/(^\s*)/g, "");
};

/**
 * 去除字符串中右空字符串
 */
String.prototype.trimRight = function () {
    return this.replace(/(\s*$)/g, "");
};

/**
 * 去除字符串中左右空字符串
 */
String.prototype.trim = function () {
    return this.replace(/(^\s*)|(\s*$)/g, "");
};

/**
 * 去除字符串中所有空字符串
 */
String.prototype.trimAll = function () {
    return this.replace(/\s/g, "");
};

/* ------------------------------------------------------------------------------ String相关END ------------------------------------------------------------------------------ */

/* ------------------------------------------------------------------------------ Array相关START ------------------------------------------------------------------------------ */

/**
 * 转换Json数组为字符串表示形式 - 支持DOM3的游览器已支持JSON对象, 这个方法就不需要了
 *
 * @return {String} Json数组的字符串表示形式
 *
 * 存在不严谨, 未判断该数组是否为json数组
 */
Array.prototype.toJsonString = function () {
    var strJsonArray = "[";
    for (var i = 0; i < this.length; i++) {
        var jsonOb = this[i];
        strJsonArray += "{";
        for (var key in jsonOb) {
            strJsonArray += key + ":" + jsonOb[key];
            strJsonArray += ",";
        }
        strJsonArray = strJsonArray.substring(0, strJsonArray.length - 1);
        strJsonArray += "},";
    }
    strJsonArray = strJsonArray.substring(0, strJsonArray.length - 1);
    strJsonArray += "]";

    return strJsonArray;
};

/**
 * 查找json数组中指定key的值为value的json对象
 *
 * 存在不严谨, 未考虑如果key相同的json对象, 值同样相同时的处理
 *
 * @author 王文强
 */
Array.prototype.getJsonByKeyValue = function (key, value) {
    if (!key || !value) {
        return;
    }
    for (var i = 0; i < this.length; i++) {
        var curJson = this[i];
        for (var curKey in curJson) {
            if (curKey == key && curJson[key] == value) {
                return curJson;
            }
        }
    }
};

/**
 * 与目标json数组比对, 判断内容中是否存在不一致, 忽略顺序
 *
 * @return {Boolean} true:相同 | false:存在不相同
 *
 * @author 王文强
 */
Array.prototype.jsonContentCompare = function (targetJsonArray) {
    if (this.length != targetJsonArray.length) {
        // 不相同
        return false;
    }

    // 比对内容
    for (var i = 0; i < this.length; i++) {
        var curJson = this[i];
        for (var curKey in curJson) {
            var curValue = curJson[curKey];
            var targetJson = targetJsonArray.getJsonByKeyValue(curKey, curValue);
            // 未找到
            if (!targetJson) {
                return false;
            }
            // 值不相同
            if (curValue != targetJson[curKey]) {
                return false;
            }
        }
    }

    return true;
};

/* ------------------------------------------------------------------------------ Array相关END ------------------------------------------------------------------------------ */

/* ------------------------------------------------------------------------------ 日期相关START ------------------------------------------------------------------------------ */

/**
 * 为指定日期加上指定天数, 能使用但不统一, 真正使用的时候应改写该方放, 使写法有加年、月一致
 *
 * @param num 增加的天数, 必须大于0
 *
 * @returns {*}
 */
Date.prototype.plusDays = function (days) {
    //TODO 未对小数做验证
    if (days <= 0) {
        return;
    }

    return new Date(this.getTime() + days * 24 * 60 * 60 * 1000);
};

/**
 * 为指定日期加上指定小时数, 能使用但不统一, 真正使用的时候应改写该方法, 使写法有加年、月一致
 *
 * @param num 增加的天数, 必须大于0
 *
 * @returns {*}
 */
Date.prototype.plusHours = function (hours) {
    //TODO 未对小数做验证
    if (hours <= 0) {
        return;
    }

    return new Date(this.getTime() + hours * 60 * 60 * 1000);
};

Date.prototype.format = function (format) {
    var o = {
        "M+": this.getMonth() + 1, // month
        "d+": this.getDate(), // day
        "h+": this.getHours(), // hour
        "m+": this.getMinutes(), // minute
        "s+": this.getSeconds(), // second
        "q+": Math.floor((this.getMonth() + 3) / 3), // quarter
        "S": this.getMilliseconds()
    };

    if (/(y+)/.test(format)) {
        format = format.replace(RegExp.$1, (this.getFullYear() + "")
            .substr(4 - RegExp.$1.length));
    }

    for (var k in o) {
        if (new RegExp("(" + k + ")").test(format)) {
            format = format.replace(RegExp.$1, RegExp.$1.length == 1 ? o[k]
                : ("00" + o[k]).substr(("" + o[k]).length));
        }
    }
    return format;
};

/* ------------------------------------------------------------------------------ 日期相关END ------------------------------------------------------------------------------ */