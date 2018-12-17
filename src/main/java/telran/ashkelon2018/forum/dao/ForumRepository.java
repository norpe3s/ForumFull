package telran.ashkelon2018.forum.dao;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import telran.ashkelon2018.forum.domain.Post;

public interface ForumRepository extends MongoRepository<Post, String> {


//	Stream<Student> findAllBy();
	List<Post> findByAuthorRegex(String regex);
	
	List<Post> findByTitleRegex(String regex);
		
	List<Post> findByTagsIn(List<String> regex);
	
	List<Post> findByTagsRegex(String regex);
	
	List<Post> findByDateCreatedBetween(LocalDateTime from, LocalDateTime to);

	
//
//	// я сам буду это делать
//	@Query("{'?0':{'$gt':?1}}")	// @Query("{'?0':{'$gt':?1}}")
//	List<Student> findByExam(String exam, int minscore);

	

}
