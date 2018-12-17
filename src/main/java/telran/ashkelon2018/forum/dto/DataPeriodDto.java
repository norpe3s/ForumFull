package telran.ashkelon2018.forum.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DataPeriodDto {
	
	LocalDateTime fromDateTime;
	LocalDateTime toDateTime;
	
}
