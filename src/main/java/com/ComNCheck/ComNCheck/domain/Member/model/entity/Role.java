package com.ComNCheck.ComNCheck.domain.Member.model.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

@Getter
public enum Role {

    ROLE_ADMIN("ROLE_ADMIN"),
    ROLE_MAJOR_PRESIDENT("ROLE_MAJOR_PRESIDENT"),
    ROLE_STUDENT_COUNCIL("ROLE_STUDENT_COUNCIL"),
    ROLE_STUDENT("ROLE_STUDENT"),
    ROLE_GRADUATE_STUDENT("ROLE_GRADUATE_STUDENT");
    private final String value;

    Role(String value) {
        this.value = value;
    }
    @JsonCreator
    public Role deserializeRole(String value){
        for(Role role : Role.values()){
            if(role.getValue().equals(value)){
                return role;
            }
        }
        return null;
    }
    @JsonValue
    public String serializeRole(){
        return this.value;
    }

}
