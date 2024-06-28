package com.firstversion.socialmedia.dto;
import lombok.Getter;
import lombok.Setter;
import java.util.Date;

@Getter
@Setter
public abstract class BaseDTO {
//    private String createBy;
    private Date createDate;
//    private String modifiedBy;
    private Date modifiedDate;
}
