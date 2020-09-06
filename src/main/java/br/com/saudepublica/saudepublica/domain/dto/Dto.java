package br.com.saudepublica.saudepublica.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
public class Dto {

    private List<String> errors;

}
