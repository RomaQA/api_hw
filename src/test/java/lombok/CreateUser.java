package lombok;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.Date;
@Setter
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class CreateUser {


    private String name;

    private String job;


}
