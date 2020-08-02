package com.yulken.workshopmongo.config;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.TimeZone;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import com.yulken.workshopmongo.domain.Post;
import com.yulken.workshopmongo.domain.User;
import com.yulken.workshopmongo.dto.AuthorDTO;
import com.yulken.workshopmongo.dto.CommentDTO;
import com.yulken.workshopmongo.repository.PostRepository;
import com.yulken.workshopmongo.repository.UserRepository;

@Configuration
public class Instantiation implements CommandLineRunner {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PostRepository postRepository;

	@Override
	public void run(String... args) throws Exception {
		userRepository.deleteAll();
		postRepository.deleteAll();

		User maria = new User(null, "Maria Brown", "maria@gmail.com");
		User alex = new User(null, "Alex Green", "alex@gmail.com");
		User bob = new User(null, "Bob Grey", "bob@gmail.com");

		userRepository.saveAll(Arrays.asList(maria, alex, bob));

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
		Post p1 = new Post(null, sdf.parse("2018-03-21"), "Partiu viagem", 
				"Vou viajar para São Paulo. Abraços!", new AuthorDTO(maria));
		Post p2 = new Post(null, sdf.parse("2018-03-23"), "Bom dia", 
				"Acordei feliz hoje!", new AuthorDTO(maria));

		CommentDTO c1 = new CommentDTO("Boa viagem mano!", 
				sdf.parse("2018-03-21"), new AuthorDTO(alex));
		CommentDTO c2 = new CommentDTO("Aproveite!", 
				sdf.parse("2018-03-22"), new AuthorDTO(bob));
		CommentDTO c3 = new CommentDTO("Tenha um ótimo dia!", 
				sdf.parse("2018-03-23"), new AuthorDTO(alex));

		p1.getComments().addAll(Arrays.asList(c1, c2));
		p2.getComments().add(c3);

		postRepository.saveAll(Arrays.asList(p1, p2));

		maria.getPosts().addAll(Arrays.asList(p1, p2));
		userRepository.save(maria);

		postRepository.saveAll(Arrays.asList(p1, p2));
	}

}
