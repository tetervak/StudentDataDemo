package ca.javateacher.studentdata.model;

public interface PasswordGenerator {
    String randomPassword();
    String randomPassword(int length);
}
