var numberUtils = {
    intFormat: function (strValue) {
        if (!strValue) {
            return "";
        }

        strValue = strValue.replace(/[^\d]/g, "");

        return strValue;
    },

    floatFormat: function (strValue) {
        if (!strValue) {
            return "";
        }

        /* 替换非数字及小数点 */
        strValue = strValue.replace(/[^\d|\.]/g, "");
        /* 替换首位出现的小数点 */
        strValue = strValue.replace(/^\.+/, "");
        /* 替换一次性出现的多个小数点 */
        strValue = strValue.replace(/\.{2,}/g, "");
        /* 替换出现次数大于1次的小数点 */
        strValue = strValue.replace(/\./, "#").replace(/\./g, "").replace(/#/, ".");

        return strValue;
    },

    /**
     * 数值格式化, 将currentValue中的有效数进行格式化, 并返回格式化后的数字, 不存在有效数, 则返回""
     *
     * @param currentValue 需格式化的值
     * @param decimalsNum 格式化后保留的小数位数
     *
     * @return String 格式化后的float值
     *
     * @author WangWenQiang
     */
    numberFormat: function (currentValue, decimalsNum) {
        // 最大支持20位小数
        if (!decimalsNum && decimalsNum > 20) {
            decimalsNum = 20;
        }

        if (isNaN(currentValue)) {
            // 浮点数
            if (decimalsNum && decimalsNum > 0) {
                /* 替换非数字及小数点 */
                currentValue = currentValue.replace(/[^\d|\.]/g, "");
                /* 替换首位出现的小数点 */
                currentValue = currentValue.replace(/^\.+/, "");
                /* 替换一次性出现的多个小数点 */
                currentValue = currentValue.replace(/\.{2,}/g, "");
                /* 替换出现次数大于1次的小数点 */
                currentValue = currentValue.replace(/\./, "#").replace(/\./g, "").replace(/#/, ".");

                if (!currentValue) {
                    currentValue = parseFloat(currentValue).toFixed(decimalsNum);
                }

                return currentValue;
            }

            // 替换所有非数字
            currentValue = currentValue.replace(/[^\d]/g, "");

            if (!currentValue) {
                currentValue = parseInt(currentValue);
            }

            return currentValue;
        }

        // 浮点数
        if (decimalsNum && decimalsNum > 0) {
            var iDecimalsIndex = currentValue.indexOf(".");
            // 不存在小数位
            if (iDecimalsIndex == -1) {
                currentValue = parseFloat(currentValue).toFixed(decimalsNum);

                return currentValue;
            }

            // 小数位长度
            var iDecimalsLength = currentValue.substring(iDecimalsIndex + 1, currentValue.length);
            if (iDecimalsLength.length <= decimalsNum) {
                currentValue = parseFloat(currentValue).toFixed(decimalsNum);

                return currentValue;
            }

            eval("var reg = /\\.\\d{" + decimalsNum + "}/;");
            if (reg.test(currentValue)) {
                /* 替换大于decimalsNum位的小数, 不进行四舍五入 */
                var decimals = currentValue.match(reg);
                currentValue = currentValue.replace(/\.\d*/, decimals[0]);

                currentValue = parseFloat(currentValue).toFixed(decimalsNum);

                return currentValue;
            }
        }

        currentValue = parseInt(currentValue);

        return currentValue;
    },

    /**
     * 验证数值正确性
     *
     * @param strNumber 数值
     * @param strMinRate 允许的最小值
     * @param strMaxRate 允许的最大值
     *
     * @returns int 0:必须参数无效/存在无效 | -1:小于最小值 | -2:大于最大值
     */
    validateNumber: function (strNumber, strMinRate, strMaxRate) {
        if (isNaN(strNumber) || (isNaN(strMinRate) && isNaN(strMaxRate))) {
            return 0;
        }

        var numNumber = parseFloat(strNumber);
        if (!isNaN(numMinRate)) {
            var numMinRate = parseFloat(strMinRate);
            if (numNumber < numMinRate) {
                return -1;
            }
        }
        if (!isNaN(strMaxRate)) {
            var numMaxRate = parseFloat(strMaxRate);
            if (numNumber > numMaxRate) {
                return -2;
            }
        }

        return 1;
    },

    /**
     * 验证小数位
     *
     * @param strNumber 数值
     * @param iAllowDecimalsNum 允许的最大小数位
     *
     * @returns int 0:必须参数无效/存在无效 | -1:不存在小数位 | -2:大于允许的小数位 | 其他正整数:存在的小数位数
     */
    validateDecimalsNum: function (strNumber, iAllowDecimalsNum) {
        if (isNaN(strNumber) || isNaN(iAllowDecimalsNum)) {
            return 0;
        }

        var iDecimalPointIndex = strNumber.indexOf(".");
        // 不存在小数位
        if (iDecimalPointIndex == -1) {
            return -1;
        }

        var iTotalLength = strNumber.length;
        // 小数位长度
        var iDecimalsNum = iTotalLength - iDecimalPointIndex - 1;
        if (iDecimalsNum > iAllowDecimalsNum) {
            return -2;
        }

        /*var iDecimalsLength = strNumber.substring(iDecimalPointIndex + 1, strNumber.length);
         if (iDecimalsLength.length > iAllowDecimalsNum) {
         return -2;
         }*/

        return iDecimalsNum;
    }
};