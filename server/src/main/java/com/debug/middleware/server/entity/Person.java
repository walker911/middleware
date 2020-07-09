package com.debug.middleware.server.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * <p>
 *
 * </p>
 *
 * @author mu qin
 * @date 2020/7/9
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Person {

    private Integer id;

    private Integer age;

    private String name;

    private String location;

}
