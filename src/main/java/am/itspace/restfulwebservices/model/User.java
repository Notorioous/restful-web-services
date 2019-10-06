package am.itspace.restfulwebservices.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "All details about the user.")
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Size(min = 2, message = "Name should have at least 2 characters")
    @ApiModelProperty(notes = "Name should have at least 2 characters")
    private String name;

    @Past(message = "Birth date should be in the past")
    @ApiModelProperty(notes = "Birth date should be in the past")
    private Date birthDate;

    @OneToMany(mappedBy = "user")
    private List<Post> posts;
}
