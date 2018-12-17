package telran.ashkelon2018.forum.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import telran.ashkelon2018.forum.domain.Post;
import telran.ashkelon2018.forum.dto.DataPeriodDto;
import telran.ashkelon2018.forum.dto.NewCommentDto;
import telran.ashkelon2018.forum.dto.NewPostDto;
import telran.ashkelon2018.forum.dto.PostUpdateDto;
import telran.ashkelon2018.forum.service.ForumService;

@RestController
@RequestMapping("/forum")
public class ForumController {

	
	@Autowired //1
	ForumService forumService;
	
	 @PostMapping("/post")
	 public Post addNewPost(@RequestBody NewPostDto newPost) {
		 return forumService.addNewPost(newPost);
	 }
	
	 @GetMapping("/post/{id}") 
	 public Post getPost(@PathVariable String id)	 {
		 return forumService.getPost(id);
	 }
	 
	 @DeleteMapping("/post/{id}")
	 public Post removePost(@PathVariable String id) {
		 return forumService.removePost(id);
	 }
	 
	 @PutMapping("/post/{id}")
	 public Post updatePost(@RequestBody PostUpdateDto postUpdate, @PathVariable String id) {
		 return forumService.updatePost(postUpdate, id);
	 }
	
	 @PutMapping("/post/{id}/like")
	 public boolean addLike(@PathVariable String id) {
		 return forumService.addLike(id);
	 }
	 
	 @PutMapping("/post/{id}/comment")
	 public Post addComment(@PathVariable String id, @RequestBody NewCommentDto newComment) {
		 return forumService.addComment(id, newComment);
	 }
	 
	 
	 @GetMapping("/posts/tag/{tag}")
	 public Iterable<Post> findPostsByTags(@PathVariable String tag) {
		 return forumService.findPostsByTag(tag);
	 }
	 
	 @PostMapping("/posts/tags")
	 public Iterable<Post> findPostsByTags(@RequestBody List<String> tags) {
		 return forumService.findPostsByTags(tags);
	 }
	 
	 @GetMapping("/posts/autor/{author}")
	 public Iterable<Post> findPostsByAuthorPath(@PathVariable String author){
		 return forumService.findPostsByAuthor(author);
	 }
	 
	 @PostMapping("/posts/date")
	 public Iterable<Post> findPostsByDate(@RequestBody DataPeriodDto dateDto){
		 return forumService.findPostsByDate(dateDto);
	 }
	 
	 @GetMapping("/posts/title/{title}")
	 public Iterable<Post> getAllPostByTitlePath(@PathVariable String title) {
		 return forumService.getAllPostByTitle(title);
	 }
	 
	 @GetMapping("/titles")
	 public List<String> getAllTitle() {
		 return forumService.getAllTitle();
	 }
	 
	 /*	 	 	
	@DeleteMapping("/student/{id}") 
	public StudentResponseDto removeStudent(@PathVariable int id, 
		@RequestHeader("Authorization") String token ) {
		return studentService.deleteStudent(id,token);
	}
	
	@PutMapping("/student/{id}") 
	public StudentDto updateStudent(@PathVariable int id, 
			@RequestBody StudentEditDto studentEditDto, 
			@RequestHeader("Authorization") String token
			) {
			return studentService.editStudent(id, studentEditDto, token);
	}
	
	 */
	
}
