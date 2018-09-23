package com.meetups.userservice.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.Optional;

@Data // lombok @Data encapsula @Getter, @Setter, @ToString, @EqualsAndHashCode and @RequiredArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Document(collection = "Users") //nombre de la conllection que se crea en mongo
@Builder // lombok permite generar los constructores
@Api
public class User {

    @Id // notacion para indicar a mongo que esta id sera unica
    @JsonIgnore // el json generado ignorara este field.
    @ToString.Exclude // se excluira del toString
    private ObjectId _id;
    @Valid // validando los datos
    @NotBlank // validando datos
    @ApiModelProperty(value = "Nombre", required = true)
    private String firstName;
    @ApiModelProperty(value = "Apellido", required = true)
    private String lastName;
    @Valid // validando los datos
    @ApiModelProperty(value = "Edad", required = true)
    private int age;
    @Valid // validando los datos
    @NotBlank // validando datos
    @ApiModelProperty(value = "Mail", required = true)
    private String email;

    public Optional<ObjectId> get_id() {
        return Optional.ofNullable(_id);
    }

    public void set_id(ObjectId _id) {
        this._id = _id;
    }


}
