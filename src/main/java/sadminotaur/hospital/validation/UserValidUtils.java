package sadminotaur.hospital.validation;

import org.springframework.stereotype.Component;

@Component
public class UserValidUtils {

    protected static boolean nameReq(String name, int maxLength) {
        if (name == null) {
            return false;
        }
        if (name.length() > maxLength) {
            return false;
        }
        for (char chr : name.toLowerCase().toCharArray()) {
            if (!((chr >= 'а' && chr <= 'я') || chr == ' ' || chr == '-')) {
                return false;
            }
        }
        return true;
    }

    protected static boolean patronymicReq(String patronymic, int maxLength) {
        if (patronymic != null) {
            return patronymic.length() <= maxLength;
        }
        return true;
    }

    protected static boolean loginReq(String login, int maxLength) {
        if (login != null) {
            return login.length() <= maxLength;
        }
        return false;
    }

    protected static boolean passwordReq(String password, int minLength) {
        if (password != null) {
            return password.length() >= minLength;
        }
        return false;
    }
}
