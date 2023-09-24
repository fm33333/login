package com.example.loginproject.data.entity;

import com.mongodb.lang.Nullable;
import lombok.Data;
import lombok.Generated;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.lang.reflect.Type;

/**
 * 用户实体类（MongoDB）
 */
@Data
@Document(collection = "user") // 指定要对应的文档名（表），注意是collection而不是collation
@Accessors(chain = true)
public class User {

    @Id
    private String id;

    private String userName;

    private String telephone;


}
