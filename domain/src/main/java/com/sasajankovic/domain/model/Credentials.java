package com.sasajankovic.domain.model;

import com.sasajankovic.domain.entities.user.Password;
import com.sasajankovic.domain.entities.user.Username;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class Credentials {
    private final Username username;
    private final Password password;

    @Override
    public int hashCode() {
        int result = 31 * username.hashCode();
        result = 31 * result + password.hashCode();
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (!(obj instanceof Credentials)) return false;
        Credentials credentials = (Credentials) obj;
        return username.equals(credentials.getUsername())
                && password.equals(credentials.getPassword());
    }
}
