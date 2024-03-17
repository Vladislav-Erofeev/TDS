package vlad.erofeev.sso.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class TokenInfo {
    private Boolean active;
    private String sub;
    private List<String> aud;
    private String clientId;
    private long id;
    private String tokenType;

    private Object principal;
    private List<String> authorities;

}
