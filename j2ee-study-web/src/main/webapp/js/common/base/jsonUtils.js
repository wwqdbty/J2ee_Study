var jsonUtils = {
    /**
     * 获取Json对象包含的项个数
     *
     * @param jsonObject
     *
     * @returns {number}
     */
    getLength: function (jsonObject) {
        var length = 0;

        for (var jsonItem in jsonObject) {
            length++;
        }

        return length;
    },

    /**
     * 判断是否为空json
     * @param jsonObject
     * @returns {boolean}
     */
    isBlank: function (jsonObject) {
        if (!jsonObject) {
            return true;
        }

        if (jsonUtils.getLength(jsonObject) == 0) {
            return true;
        }
    },

    /**
     * 将form表单项转换为json对象, 未使用过, 未测试 - 也可以对Jquery进行扩展, 如: $.fn.serializeObject = function () {...};
     *
     * @param jqFormElement
     * @returns {{}}
     */
    fromForm: function(jqFormElement) {
        if (!jqFormElement || jqFormElement.length == 0) {
            return;
        }
        // var jsonForForm = {};
        // var arrJsonElementForForm = jqFormElement.serializeArray();
        //
        // var jsonExistsCount = {};
        // for (var jsonElement in arrJsonElementForForm) {
        //     var strName = jsonElement.name;
        //     var jsonTmp = jsonForForm[strName];
        //     // 未设置过, 则设置, 如果已存在, 表示为多项, 则删除原key, 重设多项型key
        //     if (jsonTmp) {
        //         jsonExistsCount[strName] = 1;
        //         jsonForForm[strName] = jsonElement.value;
        //     } else {
        //         var curExistsCount = jsonExistsCount[strName];
        //         if (curExistsCount != 1) {
        //             delete jsonForForm[strName];
        //         }
        //         var iNewExistsCount = curExistsCount + 1;
        //         jsonExistsCount[strName] = iNewExistsCount;
        //         jsonForForm[strName[curExistsCount]] = jsonElement.value;
        //     }
        // }

        var jsonForForm = {};
        var arrJsonElementForForm = this.serializeArray();

        // 根据name对表单项个数进行统计
        var jsonExistsCount = {};
        for (var jsonElement in arrJsonElementForForm) {
            // 表单name
            var strName = jsonElement.name;

            var curExistsCount = jsonExistsCount[strName];
            // 1. 未被统计过, 则将该name的表单项设置值JSON
            if (!curExistsCount) {
                jsonExistsCount[strName] = 1;
                jsonForForm[strName] = jsonElement.value;

                continue;
            }

            // 2. 已经被统计过, 则将该name的表单项存储为数组结构
            curExistsCount = parseInt(curExistsCount);
            if (curExistsCount == 1) {
                // 将原key替换为数组型并删除原key
                if (curExistsCount != 1) {
                    jsonForForm[strName[0]] = jsonForForm.value;
                    delete jsonForForm[strName];
                }
            }
            jsonForForm[strName[curExistsCount]] = jsonElement.value;
            jsonExistsCount[strName] = curExistsCount + 1;
        }

        return jsonForForm;
    }
};