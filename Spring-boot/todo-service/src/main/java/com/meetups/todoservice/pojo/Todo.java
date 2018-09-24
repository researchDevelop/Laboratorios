package com.meetups.todoservice.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Date;
import java.util.Optional;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "Todos")
@Api
public class Todo implements Serializable {
    @Id
    @JsonIgnore
    @ToString.Exclude
    private ObjectId _id;
    @Valid
    @NotBlank
    @ApiModelProperty(value = "Accion", required = true)
    private String action;
    @Valid
    @NotBlank
    @ApiModelProperty(value = "Descripcion", required = true)
    private String description;
    @Valid
    @NotBlank
    @ApiModelProperty(value = "Id Usuario", required = true)
    private String userId;
    @JsonIgnore
    private Date createdAt = new Date();

    public Optional<ObjectId> get_id() {
        return Optional.ofNullable(_id);
    }

    public void set_id(ObjectId _id) {
        this._id = _id;
    }

}
