package telran.ashkelon2018.forum.domain;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@EqualsAndHashCode(of = { "id" })
@ToString
@Document(collection = "forumSecPosts") // меняем название колекции для монго
public class Post {
	@Setter
	String id;
	@Setter
	String title;
	@Setter
	String content;
	@Setter
	String author;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	LocalDateTime dateCreated;
	@Setter
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	LocalDateTime dateModifed;
	Set<String> tags;
	int likes;
	Set<Comment> comments;

	public Post(String title, String content, String author, Set<String> tags) {
		this.title = title;
		this.content = content;
		this.author = author;
		this.tags = tags;
		dateCreated = LocalDateTime.now();
		dateModifed = LocalDateTime.now();
		id = dateCreated.getYear() + "" +  dateCreated.getMonthValue() + "" + dateCreated.getDayOfMonth() + "" + dateCreated.getHour() + "" + dateCreated.getMinute() + "" + dateCreated.getSecond() + "_" + author ;
		comments = new HashSet<>();
	}

	public void addLike() {
		likes++;
	}

	public boolean addComment(Comment comment) {
		return comments.add(comment);
	}

	public boolean addTags(String tag) {
		return tags.add(tag);
	}

	public boolean removeTags(String tag) {
		return tags.remove(tag);
	}

}
