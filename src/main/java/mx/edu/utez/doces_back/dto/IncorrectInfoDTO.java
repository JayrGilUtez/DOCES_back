package mx.edu.utez.doces_back.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class IncorrectInfoDTO {
    private String errorMessage;
    private String userEmail;
}
