package telran.ashkelon2018.forum.dto;

import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;


@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class NewPostDto {
	
	@NonNull String title;
	@NonNull String content;
	@NonNull String author;
	Set<String> tags;
	
}
