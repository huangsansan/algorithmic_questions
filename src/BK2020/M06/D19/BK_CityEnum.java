package BK2020.M06.D19;

import lombok.Getter;
import lombok.Setter;

/**
 * @description:
 * @Author: huangsan
 * @Date: 2020/6/19 4:39 下午
 */
public enum BK_CityEnum {

    ONE(1, "韩", 100),

    TWO(2, "赵", 200),

    THREE(3, "魏", 300),

    FOUR(4, "楚", 400),

    FIVE(5, "燕", 500),

    SIX(6, "齐", 600);

    @Getter
    @Setter
    private Integer ecode;
    @Getter
    @Setter
    private String name;
    @Getter
    @Setter
    private Integer age;

    BK_CityEnum(Integer ecode, String name, Integer age) {
        this.ecode = ecode;
        this.name = name;
        this.age = age;
    }

    public static String getNameByEcode(Integer ecode) {
        for (BK_CityEnum bc : BK_CityEnum.values()) {
            if (bc.getEcode().equals(ecode)) {
                return bc.getName();
            }
        }
        return null;
    }

}
