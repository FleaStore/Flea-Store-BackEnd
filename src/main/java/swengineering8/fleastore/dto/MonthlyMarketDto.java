package swengineering8.fleastore.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
@ToString
public class MonthlyMarketDto {

    private Long id;

    private String name;

    private LocalDate endDate;
}
