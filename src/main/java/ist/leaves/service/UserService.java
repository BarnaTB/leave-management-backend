package ist.leaves.service;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;

import ist.leaves.security.CustomOAuth2User;
import org.springframework.core.env.Environment;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import ist.leaves.entity.Employee;
import ist.leaves.entity.Role;
import ist.leaves.exception.OAuth2AuthenticationProcessingException;
import ist.leaves.repository.EmployeeRepository;

@Service
public class UserService extends DefaultOAuth2UserService {

    private final EmployeeRepository employeeRepository;
    private final Environment environment;

    public UserService(EmployeeRepository employeeRepository,
                       Environment environment) {
        this.employeeRepository = employeeRepository;
        this.environment = environment;
    }

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);
        try {
            return processOAuth2User(userRequest, oAuth2User.getAttributes());
        } catch (Exception ex) {
            throw new OAuth2AuthenticationProcessingException(ex.getMessage(), ex.getCause());
        }
    }

    private OAuth2User processOAuth2User(OAuth2UserRequest userRequest,
                                         Map<String, Object> attributes) {
        String email = (String) attributes.get("email");

        if (email == null || email.isEmpty()) {
            throw new OAuth2AuthenticationProcessingException("Email not found from OAuth2 provider");
        }

        if (Arrays.asList(environment.getActiveProfiles()).contains("prod") &&
                !email.endsWith("@ist.com")) {
            throw new OAuth2AuthenticationProcessingException("Invalid email domain");
        }

        Optional<Employee> userOptional = employeeRepository.findByEmail(email);
        Employee employee = userOptional.orElseGet(() -> registerNewUser(attributes));
        return new CustomOAuth2User(employee, attributes);
    }

    private Employee registerNewUser(Map<String, Object> attributes) {
        Employee employee = new Employee();
        employee.setMicrosoftId((String) attributes.get("sub"));
        employee.setEmail((String) attributes.get("email"));
        employee.setName((String) attributes.get("name"));
        employee.setAvatarUrl((String) attributes.get("picture"));
        employee.setRole(Role.USER);
        return employeeRepository.save(employee);
    }
}