package ist.leaves.security;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;
import ist.leaves.entity.Employee;

public class CustomOAuth2User extends Employee implements OAuth2User {

    private final Employee employee;
    private Map<String, Object> attributes;

    public CustomOAuth2User(Employee employee, Map<String, Object> attributes) {
        super();
        this.employee = employee;
        this.attributes = attributes;
    }

    public String getEmail() {
        return employee.getEmail();
    }

    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(
                new SimpleGrantedAuthority("ROLE_" + getRole().name())
        );
    }

    @Override
    public String getName() {
        return getEmail();
    }
}
