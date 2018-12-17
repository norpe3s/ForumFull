package telran.ashkelon2018.forum.service;

import java.util.List;

import telran.ashkelon2018.forum.domain.Post;
import telran.ashkelon2018.forum.dto.DataPeriodDto;
import telran.ashkelon2018.forum.dto.NewCommentDto;
import telran.ashkelon2018.forum.dto.NewPostDto;
import telran.ashkelon2018.forum.dto.PostUpdateDto;

public interface ForumService {
		Post addNewPost(NewPostDto newPost);
		Post getPost(String id);
		Post removePost(String id);
		Post updatePost(PostUpdateDto postUpdate, String id);
		boolean addLike(String id);
		Post addComment(String id, NewCommentDto newComment);
		Iterable<Post> findPostsByTags(List<String> tags);
		Iterable<Post> findPostsByTag(String tag);
		Iterable<Post> findPostsByAuthor(String author);
		Iterable<Post> findPostsByDate(DataPeriodDto dateDto);
		Iterable<Post> getAllPostByTitle(String title);
		List<String> getAllTitle();
		
		
}
