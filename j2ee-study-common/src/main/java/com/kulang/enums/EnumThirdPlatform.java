package com.kulang.enums;

/**
 * Created by dell on 2017/3/2.
 * 资产状态
 */
public enum EnumThirdPlatform implements BaseEnum {
    JXL("聚信立"),
    BR("百融");

    private final String key;

    EnumThirdPlatform(final String key) {
        this.key = key;
    }

    @Override
    public String getKey() {
        return this.key;
    }
}
