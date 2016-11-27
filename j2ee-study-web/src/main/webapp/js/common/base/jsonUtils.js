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

        if (common.json.getLength(jsonObject) == 0) {
            return true;
        }
    },

    /**
     * 将form表单项转换为json对象, 未使用过, 未测试
     *
     * @param jqFormElement
     * @returns {{}}
     */
    fromForm: function(jqFormElement) {
        if (!jqFormElement || jqFormElement.length == 0) {
            return;
        }
        var jsonForForm = {};
        var arrJsonElementForForm = jqFormElement.serializeArray();

        var jsonExistsCount = {};
        for (var jsonElement in arrJsonElementForForm) {
            var strName = jsonElement.name;
            var jsonTmp = jsonForForm[strName];
            // 未设置过, 则设置, 如果已存在, 表示为多项, 则删除原key, 重设多项型key
            if (jsonTmp) {
                jsonExistsCount[strName] = 1;
                jsonForForm[strName] = jsonElement.value;
            } else {
                var curExistsCount = jsonExistsCount[strName];
                if (curExistsCount != 1) {
                    delete jsonForForm[strName];
                }
                var iNewExistsCount = curExistsCount + 1;
                jsonExistsCount[strName] = iNewExistsCount;
                jsonForForm[strName[curExistsCount]] = jsonElement.value;
            }
        }

        return jsonForForm;
    }
};