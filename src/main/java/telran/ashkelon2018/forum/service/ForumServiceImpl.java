package telran.ashkelon2018.forum.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import telran.ashkelon2018.forum.dao.ForumRepository;
import telran.ashkelon2018.forum.domain.Comment;
import telran.ashkelon2018.forum.domain.Post;
import telran.ashkelon2018.forum.dto.DataPeriodDto;
import telran.ashkelon2018.forum.dto.NewCommentDto;
import telran.ashkelon2018.forum.dto.NewPostDto;
import telran.ashkelon2018.forum.dto.PostUpdateDto;


@Service
public class ForumServiceImpl implements ForumService {

	@Autowired // 2
	ForumRepository forumRepository;
	
	
	@Override
	public Post addNewPost(NewPostDto newPost) {
		
		Post post = new Post(newPost.getTitle(), newPost.getContent(), newPost.getAuthor(), newPost.getTags());
		forumRepository.save(post);
		return post;
	}

	@Override
	public Post getPost(String id) {
		return forumRepository.findById(id).orElse(null);
	}

	@Override
	public Post removePost(String id) {
		if(!forumRepository.existsById(id)) {
			return null;
		}
		
		Post post = forumRepository.findById(id).get();
		forumRepository.delete(post);
		return post;
	}

	@Override
	public Post updatePost(PostUpdateDto postUpdate, String id) {
		if(!forumRepository.existsById(id)) {
			return null;
		}
		Post oldPpost = forumRepository.findById(id).get();
		Post post = forumRepository.findById(id).get();
		if (postUpdate.getAuthor()!=null) {
			post.setAuthor(postUpdate.getAuthor());
			post.setId(post.getDateCreated().getYear() + "" +  post.getDateCreated().getMonthValue() + "" + post.getDateCreated().getDayOfMonth() + "" + post.getDateCreated().getHour() + "" + post.getDateCreated().getMinute() + "" + post.getDateCreated().getSecond()
			+ "_" +postUpdate.getAuthor());
		}
		if (postUpdate.getContent()!=null) {
			post.setContent(postUpdate.getContent());
		}
		if (postUpdate.getTitle()!=null) {
			post.setTitle(postUpdate.getTitle());
		}
		post.setDateModifed(LocalDateTime.now());
		forumRepository.delete(oldPpost);
		forumRepository.save(post);
		return post;
	}

	@Override
	public boolean addLike(String id) {
		if(!forumRepository.existsById(id)) {
			return false;
		}
		Post post = forumRepository.findById(id).get();
		post.addLike();
		forumRepository.save(post);
		
		return true;
	}

	@Override
	public Post addComment(String id, NewCommentDto newComment) {
		if(!forumRepository.existsById(id)) {
			return null;
		}
		Post post = forumRepository.findById(id).get();
		post.addComment(new Comment(newComment.getUser(), newComment.getMessage()));
		forumRepository.save(post);
		return post;
	}

	@Override
	public Iterable<Post> findPostsByTag(String tag) {
		return forumRepository.findByTagsRegex(tag);
	}

	@Override
	public Iterable<Post> findPostsByTags(List<String> tags) {
		return forumRepository.findByTagsIn(tags);
	}

	@Override
	public Iterable<Post> findPostsByAuthor(String author) {
		return forumRepository.findByAuthorRegex(author);
	}

	@Override
	public Iterable<Post> findPostsByDate(DataPeriodDto dateDto) {
		LocalDateTime fromDate = dateDto.getFromDateTime();
		LocalDateTime toDate = dateDto.getToDateTime();
		return forumRepository.findByDateCreatedBetween(fromDate, toDate);
//		return forumRepository.findAll()
//				.stream()
//				.filter(x -> x.getDateCreated().isAfter(fromDate)&x.getDateCreated().isBefore(toDate))
//				.collect(Collectors.toList());			
	}

	@Override
	public Iterable<Post> getAllPostByTitle(String title) {
		return forumRepository.findByTitleRegex(title);
	}
	
	
	@Override
	public List<String> getAllTitle() {
		return forumRepository.findAll().stream().map(s->s.getTitle()).distinct().collect(Collectors.toList());
	}




}
