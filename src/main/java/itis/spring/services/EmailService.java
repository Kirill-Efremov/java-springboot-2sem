package itis.spring.services;



public interface EmailService {
    void sendResetCode(String email);
    boolean verifyResetCode(String email, String resetCode);

    void resetPassword(String email, String newPassword);

    boolean isEmailExists(String email);
}
