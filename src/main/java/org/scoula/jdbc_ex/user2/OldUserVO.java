package org.scoula.jdbc_ex.user2;

import java.sql.Timestamp;
import java.util.Objects;

public class OldUserVO {
    private final int id;
    private final String userid;
    private final String name;
    private final String password;
    private final int age;
    private final boolean membership;
    private final Timestamp signUpDate;

    public OldUserVO(int id, String userid, String name, String password, int age, boolean membership, Timestamp signUpDate) {
        this.id = id;
        this.userid = userid;
        this.name = name;
        this.password = password;
        this.age = age;
        this.membership = membership;
        this.signUpDate = signUpDate;
    }

    public int getId() {
        return id;
    }

    public String getUserid() {
        return userid;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public int getAge() {
        return age;
    }

    public boolean isMembership() {
        return membership;
    }

    public Timestamp getSignUpDate() {
        return signUpDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OldUserVO userVO = (OldUserVO) o;
        return id == userVO.id && age == userVO.age && membership == userVO.membership && Objects.equals(userid, userVO.userid) && Objects.equals(name, userVO.name) && Objects.equals(password, userVO.password) && Objects.equals(signUpDate, userVO.signUpDate);
    }
}
