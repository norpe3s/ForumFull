package telran.ashkelon2018.forum.dto;

import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostUpdateDto {

	String title;
	String content;
	String author;
	Set<String> tags;
	
	
}
