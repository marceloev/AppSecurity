package com.appsecurity.Utils;

public class TesteSenhaArthur {

    public static void main(String[] args) {
        Password password1 = new Password(true, "z445-s456-q021-s412-z354-s456");
        System.out.println(password1.getPassword());
        System.out.println(password1.getUncryptedPassword());
    }
}
